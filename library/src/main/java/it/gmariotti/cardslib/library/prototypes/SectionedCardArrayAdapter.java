/*
 * ******************************************************************************
 *   Copyright (c) 2013-2014 Gabriele Mariotti.
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 *  *****************************************************************************
 */

package it.gmariotti.cardslib.library.prototypes;

import android.content.Context;
import android.database.DataSetObserver;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Arrays;
import java.util.Comparator;

import it.gmariotti.cardslib.library.R;
import it.gmariotti.cardslib.library.internal.CardArrayAdapter;
import it.gmariotti.cardslib.library.internal.base.BaseCardArrayAdapter;

/**
 * @author Gabriele Mariotti (gabri.mariotti@gmail.com)
 */
public class SectionedCardArrayAdapter extends BaseAdapter {

    private boolean mValid = true;

    /**
     * Default layout used for sections
     */
    private int mSectionResourceId = R.layout.base_section_layout;
    private LayoutInflater mLayoutInflater;
    private BaseCardArrayAdapter mBaseAdapter;
    private SparseArray<Section> mSections = new SparseArray<Section>();

    // -------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------

    /**
     * Constructor
     *
     * @param context
     * @param cardArrayAdapter
     */
    public SectionedCardArrayAdapter(Context context,CardArrayAdapter cardArrayAdapter) {
        this(context,R.layout.base_section_layout,cardArrayAdapter);
    }

    /**
     *
     * @param context             context
     * @param sectionResourceId   layout used by sections
     * @param cardArrayAdapter    cardArrayAdapter
     */
    public SectionedCardArrayAdapter(Context context,int sectionResourceId,CardArrayAdapter cardArrayAdapter) {
        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mBaseAdapter = cardArrayAdapter;
        mSectionResourceId = sectionResourceId;

        mBaseAdapter.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                mValid = !mBaseAdapter.isEmpty();
                notifyDataSetChanged();
            }

            @Override
            public void onInvalidated() {
                mValid = false;
                notifyDataSetInvalidated();
            }
        });
    }

    // -------------------------------------------------------------
    // Section
    // -------------------------------------------------------------

    public static class Section {
        int firstPosition;
        int sectionedPosition;
        CharSequence title;

        public Section(int firstPosition, CharSequence title) {
            this.firstPosition = firstPosition;
            this.title = title;
        }

        public CharSequence getTitle() {
            return title;
        }
    }

    public void setSections(Section[] sections) {
        mSections.clear();

        Arrays.sort(sections, new Comparator<Section>() {
            @Override
            public int compare(Section o, Section o1) {
                return (o.firstPosition == o1.firstPosition)
                        ? 0
                        : ((o.firstPosition < o1.firstPosition) ? -1 : 1);
            }
        });

        int offset = 0; // offset positions for the headers we're adding
        for (Section section : sections) {
            section.sectionedPosition = section.firstPosition + offset;
            mSections.append(section.sectionedPosition, section);
            ++offset;
        }

        notifyDataSetChanged();
    }

    // -------------------------------------------------------------
    // Adapter's methods
    // -------------------------------------------------------------

    public int positionToSectionedPosition(int position) {
        int offset = 0;
        for (int i = 0; i < mSections.size(); i++) {
            if (mSections.valueAt(i).firstPosition > position) {
                break;
            }
            ++offset;
        }
        return position + offset;
    }

    /**
     *
     * @param sectionedPosition
     * @return
     */
    public int sectionedPositionToPosition(int sectionedPosition) {
        if (isSectionHeaderPosition(sectionedPosition)) {
            return ListView.INVALID_POSITION;
        }

        int offset = 0;
        for (int i = 0; i < mSections.size(); i++) {
            if (mSections.valueAt(i).sectionedPosition > sectionedPosition) {
                break;
            }
            --offset;
        }
        return sectionedPosition + offset;
    }

    /**
     *
     * @param position
     * @return
     */
    public boolean isSectionHeaderPosition(int position) {
        return mSections.get(position) != null;
    }

    @Override
    public int getCount() {
        return (mValid ? mBaseAdapter.getCount() + mSections.size() : 0);
    }

    @Override
    public Object getItem(int position) {
        return isSectionHeaderPosition(position)
                ? mSections.get(position)
                : mBaseAdapter.getItem(sectionedPositionToPosition(position));
    }

    @Override
    public long getItemId(int position) {
        return isSectionHeaderPosition(position)
                ? Integer.MAX_VALUE - mSections.indexOfKey(position)
                : mBaseAdapter.getItemId(sectionedPositionToPosition(position));
    }

    @Override
    public int getItemViewType(int position) {
        return isSectionHeaderPosition(position)
                ? getViewTypeCount() - 1
                : mBaseAdapter.getItemViewType(position);
    }

    @Override
    public boolean isEnabled(int position) {
        //noinspection SimplifiableConditionalExpression
        return isSectionHeaderPosition(position)
                ? false
                : mBaseAdapter.isEnabled(sectionedPositionToPosition(position));
    }

    @Override
    public int getViewTypeCount() {
        return mBaseAdapter.getViewTypeCount() + 1; // the section headings
    }

    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public boolean hasStableIds() {
        return mBaseAdapter.hasStableIds();
    }

    @Override
    public boolean isEmpty() {
        return mBaseAdapter.isEmpty();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (isSectionHeaderPosition(position)) {
           return internalSectionView(position, convertView, parent);
        } else {
            return mBaseAdapter.getView(sectionedPositionToPosition(position), convertView, parent);
        }
    }

    /**
     * Returns the view used by the section at position
     *
     * @param position    section's position
     * @param convertView
     * @param parent
     * @return
     */
    protected View internalSectionView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = mLayoutInflater.inflate(mSectionResourceId, parent, false);
        }
        getSectionView(position,view,parent);
        return view;
    }

    /**
     * Returns the view used by the section at position.
     * Override this method to set your ui elements.
     *
     * @param position    section's position
     * @param view
     * @param parent
     * @return
     */
    protected View getSectionView(int position, View view, ViewGroup parent) {

        TextView textView = (TextView) view.findViewById(R.id.card_section_simple_title);
        if (textView != null)
            textView.setText(mSections.get(position).title);

        return view;
    }

    // -------------------------------------------------------------
    // Getters and setters
    // -------------------------------------------------------------

    public SparseArray<Section> getSections() {
        return mSections;
    }


}
