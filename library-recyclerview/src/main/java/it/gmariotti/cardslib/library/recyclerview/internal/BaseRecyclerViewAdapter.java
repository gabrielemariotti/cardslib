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

package it.gmariotti.cardslib.library.recyclerview.internal;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import it.gmariotti.cardslib.library.recyclerview.R;
import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.recyclerview.view.CardRecyclerView;
import it.gmariotti.cardslib.library.view.base.CardViewWrapper;

/**
 * Base RecyclerViewAdapter for RecyclerView and its implemetations.
 *
 * @author Gabriele Mariotti (gabri.mariotti@gmail.com)
 */
public abstract class BaseRecyclerViewAdapter extends RecyclerView.Adapter<BaseRecyclerViewAdapter.CardViewHolder> implements ViewAdapterImpl {

    /**
     * Current context
     */
    protected Context mContext;

    /**
     * Default layout used for each row
     */
    protected @LayoutRes int mRowLayoutId = R.layout.list_card_layout;

    /**
     * Used to set the viewTypeCount
     */
    protected int typeCardCount = 1;

    /**
     *  Array of layout resource ids
     */
    protected @LayoutRes int[] mRowLayoutIds;

    /**
     * {@link it.gmariotti.cardslib.library.view.CardRecyclerView}
     */
    protected CardRecyclerView mCardRecyclerView;

    // -------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------

    /**
     * Constructor
     *
     * @param context The current context.
     */
    public BaseRecyclerViewAdapter(Context context) {
        super();
        mContext = context;
    }

    // -------------------------------------------------------------
    // ViewHolder
    // -------------------------------------------------------------

    public static class CardViewHolder extends RecyclerView.ViewHolder {

        public final CardViewWrapper mCardView;
        public boolean recycled = false;

        public CardViewHolder(View view) {
            super(view);
            mCardView = (CardViewWrapper) view.findViewById(R.id.list_cardId);
        }
    }

    @Override
    public CardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (mRowLayoutIds == null || mRowLayoutIds.length == 0) {
            final View view = LayoutInflater.from(mContext).inflate(mRowLayoutId, parent, false);
            return new CardViewHolder(view);
        } else {
            final View view = LayoutInflater.from(mContext).inflate(mRowLayoutIds[viewType], parent, false);
            return new CardViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(CardViewHolder cardViewHolder, int position) {

        CardViewWrapper mCardView = cardViewHolder.mCardView;
        Card mCard = getItem(position);

        //Setup card
        if (mCardView != null) {
            //It is important to set recycle value for inner layout elements
            mCardView.setForceReplaceInnerLayout(Card.equalsInnerLayout(mCardView.getCard(),mCard));

            //It is important to set recycle value for performance issue
            mCardView.setRecycle(cardViewHolder.recycled);

            //Save original swipeable to prevent cardSwipeListener (listView requires another cardSwipeListener)
            boolean origianlSwipeable = mCard.isSwipeable();
            mCard.setSwipeable(false);

            mCardView.setCard(mCard);

            //Set originalValue
            mCard.setSwipeable(origianlSwipeable);

            //If card has an expandable button override animation
            if ((mCard.getCardHeader() != null && mCard.getCardHeader().isButtonExpandVisible()) || mCard.getViewToClickToExpand()!=null ){
                setupExpandCollapseListAnimation(mCardView);
            }

            //Setup swipeable animation
            //setupSwipeableAnimation(mCard, mCardView);
        }
    }

    /**
     * Overrides the default collapse/expand animation in a List
     *
     * @param cardView {@link it.gmariotti.cardslib.library.view.base.CardViewWrapper}
     */
    protected void setupExpandCollapseListAnimation(CardViewWrapper cardView) {

        if (cardView == null) return;
        cardView.setOnExpandListAnimatorListener(mCardRecyclerView);
    }

    // -------------------------------------------------------------
    // Views
    // -------------------------------------------------------------

    public int getTypeCardCount() {
        return typeCardCount;
    }

    @Override
    public int getItemViewType(int position) {
        Card card = (Card) getItem(position);
        return card.getType();
    }

    /**
     * Override this method to obtain a Card from a position
     *
     * @param position
     * @return
     */
    public abstract Card getItem(int position);

    /**
     * Sets layout resource ID used by rows
     *
     * @param rowLayoutId layout resource id
     */
    public void setRowLayoutId(@LayoutRes int rowLayoutId) {
        this.mRowLayoutId = rowLayoutId;
    }

    /**
     * Sets layouts resource ID used by rows
     *
     * @param rowLayoutIds array of layout resource ids
     */
    public void setRowLayoutIds(@LayoutRes int[] rowLayoutIds) {
        mRowLayoutIds = rowLayoutIds;
        if (rowLayoutIds != null)
            typeCardCount = rowLayoutIds.length;
        else
            typeCardCount = 1;
    }

    /**
     *
     * @return the RecyclerView
     */
    public CardRecyclerView getCardRecyclerView() {
        return mCardRecyclerView;
    }

    /**
     * Sets the RecyclerView
     *
     * @param cardRecyclerView
     */
    public void setCardRecyclerView(CardRecyclerView cardRecyclerView) {
        mCardRecyclerView = cardRecyclerView;
    }

}
