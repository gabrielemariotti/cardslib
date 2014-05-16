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

package it.gmariotti.cardslib.library.internal;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import it.gmariotti.cardslib.library.R;
import it.gmariotti.cardslib.library.view.CardExpandableListView;
import it.gmariotti.cardslib.library.view.CardView;

/**
 * @author Gabriele Mariotti (gabri.mariotti@gmail.com)
 */
public class CardExpandableListAdapter<T> extends BaseExpandableListAdapter {

    protected final SparseArray<BaseGroupExpandableCard<T>> cards;
    public LayoutInflater mInflater;

    /**
     * Current context
     */
    protected Context mContext;

    /**
     * Default layout used for each row
     */
    protected int mGroupLayoutId = R.layout.list_card_layout;

    /**
     * Default layout used for each row
     */
    protected int mChildLayoutId = R.layout.base_list_expandable_children_layout;

    /**
     * {@link CardExpandableListView}
     */
    protected CardExpandableListView mCardListView;

    // -------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------

    public CardExpandableListAdapter(Context context, SparseArray<BaseGroupExpandableCard<T>> cards) {
        this.cards = cards;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getGroupCount() {
        return cards.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return cards.get(groupPosition).children.size();
    }


    @Override
    public Card getGroup(int groupPosition) {
        return cards.get(groupPosition);
    }


    @Override
    public T getChild(int groupPosition, int childPosition) {
        return cards.get(groupPosition).children.get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        if (getGroup(groupPosition).getId()!=null)
            return getGroup(groupPosition).getId().hashCode();
        else
            return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        View view = convertView;
        CardView mCardView;

        Card mCard = (Card) getGroup(groupPosition);
        if (mCard != null) {
            int layout = mGroupLayoutId;
            boolean recycle = false;

            //Inflate layout
            if (view == null) {
                recycle = false;
                view = mInflater.inflate(layout, parent, false);
            } else {
                recycle = true;
            }

            //Setup card
            mCardView = (CardView) view.findViewById(R.id.list_cardId);
            if (mCardView != null) {
                //It is important to set recycle value for inner layout elements
                mCardView.setForceReplaceInnerLayout(Card.equalsInnerLayout(mCardView.getCard(), mCard));

                //It is important to set recycle value for performance issue
                mCardView.setRecycle(recycle);

                mCard.setSwipeable(false);

                mCardView.setCard(mCard);

            }
        }

        return view;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        T obj = getChild(groupPosition, childPosition);

        if (obj != null && obj instanceof String) {
            final String children = (String) obj;

            TextView text = null;
            if (convertView == null) {
                convertView = mInflater.inflate(mChildLayoutId, null);
            }
            text = (TextView) convertView.findViewById(R.id.card_children_simple_title);
            text.setText(children);

            registerClickListener(convertView, obj, groupPosition, childPosition);
        }
        return convertView;
    }


    protected void registerClickListener(View convertView, T obj, int groupPosition, int childPosition) {
        if (isChildSelectable(groupPosition, childPosition)) {
            final T children = obj;
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(mContext, children.toString(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }


    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    // -------------------------------------------------------------
    //  Getters and Setters
    // -------------------------------------------------------------

    /**
     * Returns current context
     *
     * @return current context
     */
    public Context getContext() {
        return mContext;
    }

    /**
     * Sets layout resource ID used by rows
     *
     * @param groupLayoutId layout resource id
     */
    public void setGroupLayoutId(int groupLayoutId) {
        this.mGroupLayoutId = groupLayoutId;
    }


    public CardExpandableListView getCardListView() {
        return mCardListView;
    }

    public void setCardListView(CardExpandableListView cardListView) {
        mCardListView = cardListView;
    }

}
