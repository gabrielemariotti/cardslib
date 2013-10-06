/*
 * ******************************************************************************
 *   Copyright (c) 2013 Gabriele Mariotti.
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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.List;

import it.gmariotti.cardslib.library.R;
import it.gmariotti.cardslib.library.internal.base.BaseCardArrayAdapter;
import it.gmariotti.cardslib.library.view.CardListView;
import it.gmariotti.cardslib.library.view.CardView;
import it.gmariotti.cardslib.library.view.listener.SwipeDismissListViewTouchListener;

/**
 * Array Adapter for {@link Card} model
 * <p/>
 * Usage:
 * <pre><code>
 * ArrayList<Card> cards = new ArrayList<Card>();
 * for (int i=0;i<1000;i++){
 *     CardExample card = new CardExample(getActivity(),"My title "+i,"Inner text "+i);
 *     cards.add(card);
 * }
 *
 * CardArrayAdapter mCardArrayAdapter = new CardArrayAdapter(getActivity(),cards);
 *
 * CardListView listView = (CardListView) getActivity().findViewById(R.id.listId);
 * listView.setAdapter(mCardArrayAdapter); *
 * </code></pre>
 * It provides a default layout id for each row @layout/list_card_layout
 * Use can easily customize it using card:list_card_layout_resourceID attr in your xml layout:
 * <pre><code>
 *    <it.gmariotti.cardslib.library.view.CardListView
 *      android:layout_width="match_parent"
 *      android:layout_height="match_parent"
 *      android:id="@+id/carddemo_list_gplaycard"
 *      card:list_card_layout_resourceID="@layout/list_card_thumbnail_layout" />
 * </code></pre>
 * or:
 * <pre><code>
 * adapter.setRowLayoutId(list_card_layout_resourceID);
 * </code></pre>
 * </p>
 * @author Gabriele Mariotti (gabri.mariotti@gmail.com)
 */
public class CardArrayAdapter extends BaseCardArrayAdapter {

    /**
     * {@link CardListView}
     */
    protected CardListView mCardListView;

    /**
     * Listener invoked when a card is swiped
     */
    protected SwipeDismissListViewTouchListener mOnTouchListener;


    // -------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------

    /**
     * Constructor
     *
     * @param context The current context.
     * @param cards   The cards to represent in the ListView.
     */
    public CardArrayAdapter(Context context, List<Card> cards) {
        super(context, cards);
    }

    // -------------------------------------------------------------
    // Views
    // -------------------------------------------------------------

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;
        CardView mCardView;
        Card mCard;

        LayoutInflater mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //Retrieve card from items
        mCard = (Card) getItem(position);
        if (mCard != null) {

            int layout = mRowLayoutId;
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
                //It is important to set recycle value for performance issue
                mCardView.setRecycle(recycle);

                //Save original swipeable to prevent cardSwipeListener (listView requires another cardSwipeListener)
                boolean origianlSwipeable = mCard.isSwipeable();
                mCard.setSwipeable(false);

                mCardView.setCard(mCard);

                //Set originalValue
                mCard.setSwipeable(origianlSwipeable);

                //If card has an expandable button override animation
                if (mCard.getCardHeader() != null && mCard.getCardHeader().isButtonExpandVisible()) {
                    setupExpandCollapseListAnimation(mCardView);
                }

                //Setup swipeable animation
                setupSwipeableAnimation(mCard, mCardView);

            }
        }

        return view;
    }

    /**
     * Sets SwipeAnimation on List
     *
     * @param card {@link Card}
     * @param cardView {@link CardView}
     */
    protected void setupSwipeableAnimation(final Card card, CardView cardView) {

        if (card.isSwipeable()){
            if (mOnTouchListener == null){
                mOnTouchListener = new SwipeDismissListViewTouchListener(mCardListView, mCallback);
                // Setting this scroll listener is required to ensure that during
                // ListView scrolling, we don't look for swipes.
                mCardListView.setOnScrollListener(mOnTouchListener.makeScrollListener());
            }

            cardView.setOnTouchListener(mOnTouchListener);
        }else{
            //prevent issue with recycle view
            cardView.setOnTouchListener(null);
        }
    }

    /**
     * Overrides the default collapse/expand animation in a List
     *
     * @param cardView {@link CardView}
     */
    protected void setupExpandCollapseListAnimation(CardView cardView) {

        if (cardView == null) return;
        cardView.setOnExpandListAnimatorListener(mCardListView);
    }

    // -------------------------------------------------------------
    //  SwipeListener
    // -------------------------------------------------------------
    /**
     * Listener invoked when a card is swiped
     */
    SwipeDismissListViewTouchListener.DismissCallbacks mCallback = new SwipeDismissListViewTouchListener.DismissCallbacks() {

        @Override
        public boolean canDismiss(int position, Card card) {
            return card.isSwipeable();
        }

        @Override
        public void onDismiss(ListView listView, int[] reverseSortedPositions) {
            for (int position : reverseSortedPositions) {
                Card card = getItem(position);
                if (card.getOnSwipeListener() != null)
                    card.getOnSwipeListener().onSwipe(card);
                remove(card);
            }
            notifyDataSetChanged();
        }
    };

    // -------------------------------------------------------------
    //  Getters and Setters
    // -------------------------------------------------------------

    /**
     * @return {@link CardListView}
     */
    public CardListView getCardListView() {
        return mCardListView;
    }

    /**
     * Sets the {@link CardListView}
     *
     * @param cardListView cardListView
     */
    public void setCardListView(CardListView cardListView) {
        this.mCardListView = cardListView;
    }
}
