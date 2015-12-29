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

/**
 * An adapter to build a CardList with sections.
 *
 * @author Gabriele Mariotti (gabri.mariotti@gmail.com)
 */
public class SectionedCardAdapter extends BaseAdapter {

    private boolean mValid = true;

    /**
     * Default layout used for sections
     */
    private int mSectionResourceId = R.layout.base_section_layout;

    /**
     * Inflater
     */
    private LayoutInflater mLayoutInflater;

    /**
     * Adapter with cards.
     */
    private BaseAdapter mBaseAdapter;

    /**
     * Array with Card Sections
     */
    private SparseArray<CardSection> mCardSections = new SparseArray<CardSection>();

    // -------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------

    /**
     * Constructor
     *
     * @param context
     * @param cardArrayAdapter
     */
    public SectionedCardAdapter(Context context, CardArrayAdapter cardArrayAdapter) {
        this(context,R.layout.base_section_layout,cardArrayAdapter);
    }

    /**
     *
     * @param context             context
     * @param sectionResourceId   layout used by sections
     * @param cardArrayAdapter    cardArrayAdapter
     */
    public SectionedCardAdapter(Context context, int sectionResourceId, CardArrayAdapter cardArrayAdapter) {
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

    /**
     * Sets the Card sections
     *
     * @param cardSections
     */
    public void setCardSections(CardSection[] cardSections) {
        mCardSections.clear();

        Arrays.sort(cardSections, new Comparator<CardSection>() {
            @Override
            public int compare(CardSection o, CardSection o1) {
                return (o.firstPosition == o1.firstPosition)
                        ? 0
                        : ((o.firstPosition < o1.firstPosition) ? -1 : 1);
            }
        });

        int offset = 0; // offset positions for the headers we're adding
        for (CardSection cardSection : cardSections) {
            cardSection.sectionedPosition = cardSection.firstPosition + offset;
            mCardSections.append(cardSection.sectionedPosition, cardSection);
            ++offset;
        }

        notifyDataSetChanged();
    }

    // -------------------------------------------------------------
    // Adapter's methods
    // -------------------------------------------------------------

    /**
     * Returns the sectioned position from the position
     *
     * @param position
     * @return
     */
    public int positionToSectionedPosition(int position) {
        int offset = 0;
        for (int i = 0; i < mCardSections.size(); i++) {
            if (mCardSections.valueAt(i).firstPosition > position) {
                break;
            }
            ++offset;
        }
        return position + offset;
    }

    /**
     * Returns the position from the sectioned position
     * @param sectionedPosition
     * @return
     */
    public int sectionedPositionToPosition(int sectionedPosition) {
        if (isSectionHeaderPosition(sectionedPosition)) {
            return ListView.INVALID_POSITION;
        }

        int offset = 0;
        for (int i = 0; i < mCardSections.size(); i++) {
            if (mCardSections.valueAt(i).sectionedPosition > sectionedPosition) {
                break;
            }
            --offset;
        }
        return sectionedPosition + offset;
    }

    /**
     * Returns true if the position is a Section
     *
     * @param position
     * @return
     */
    public boolean isSectionHeaderPosition(int position) {
        return mCardSections.get(position) != null;
    }

    @Override
    public int getCount() {
        return (mValid ? mBaseAdapter.getCount() + mCardSections.size() : 0);
    }

    @Override
    public Object getItem(int position) {
        return isSectionHeaderPosition(position)
                ? mCardSections.get(position)
                : mBaseAdapter.getItem(sectionedPositionToPosition(position));
    }

    @Override
    public long getItemId(int position) {
        return isSectionHeaderPosition(position)
                ? Integer.MAX_VALUE - mCardSections.indexOfKey(position)
                : mBaseAdapter.getItemId(sectionedPositionToPosition(position));
    }

    @Override
    public int getItemViewType(int position) {
        return isSectionHeaderPosition(position)
                ? getViewTypeCount() - 1
                : mBaseAdapter.getItemViewType(sectionedPositionToPosition(position));
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
            textView.setText(mCardSections.get(position).title);

        return view;
    }

    /**
     * Use this method to add a single {@link CardSection}.</p>
     * If you want to add more CardSections use the method {@link #addCardSections(CardSection[])}
     *
     * @param cardSection to be added
     */
    public void addCardSection(CardSection cardSection) {

        if (cardSection != null) {
            int oldSize = mCardSections.size();
            CardSection[] newCardSections = new CardSection[oldSize + 1];

            //Get current sections
            for (int i = 0; i < mCardSections.size(); i++) {
                newCardSections[i] = mCardSections.valueAt(i);
            }
            //Add new section
            newCardSections[oldSize] = cardSection;
            setCardSections(newCardSections);
        }
    }

    /**
     * Adds card Sections
     *
     * @param cardSections card sections to be added
     */
    public void addCardSections(CardSection[] cardSections) {

        if (cardSections != null && cardSections.length>0) {

            int oldSize = mCardSections.size();

            //Get current sections
            CardSection[] newCardSections = new CardSection[oldSize + cardSections.length];
            for (int i = 0; i < mCardSections.size(); i++) {
                newCardSections[i] = mCardSections.valueAt(i);
            }

            //Add new sections
            for (int i = 0; i < cardSections.length; i++){
                newCardSections[i + oldSize] = cardSections[i];
            }

            setCardSections(newCardSections);
        }
    }

    // -------------------------------------------------------------
    // Getters and setters
    // -------------------------------------------------------------

    /**
     * Returns the sections
     * @return
     */
    public SparseArray<CardSection> getCardSections() {
        return mCardSections;
    }

}
