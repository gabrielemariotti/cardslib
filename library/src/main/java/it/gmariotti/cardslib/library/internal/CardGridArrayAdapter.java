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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import it.gmariotti.cardslib.library.R;
import it.gmariotti.cardslib.library.internal.base.BaseCardArrayAdapter;
import it.gmariotti.cardslib.library.view.CardGridView;
import it.gmariotti.cardslib.library.view.CardView;
import it.gmariotti.cardslib.library.view.listener.SwipeDismissListViewTouchListener;

/**
 * Array Adapter for {@link Card} model.
 * Use it with a {@link CardGridView}.
 * </p>
 * Usage:
 * <pre><code>
 * ArrayList<Card> cards = new ArrayList<Card>();
 * for (int i=0;i<1000;i++){
 *     CardExample card = new CardExample(getActivity(),"My title "+i,"Inner text "+i);
 *     cards.add(card);
 * }
 * <p/>
 * CardGridArrayAdapter mCardArrayAdapter = new CardGridArrayAdapter(getActivity(),cards);
 * <p/>
 * CardGridView gridView = (CardGridView) getActivity().findViewById(R.id.gridId);
 * gridView.setAdapter(mCardArrayAdapter); *
 * </code></pre>
 * It provides a default layout id for each row @layout/list_card_layout
 * Use can easily customize it using card:list_card_layout_resourceID attr in your xml layout:
 * <pre><code>
 *    <it.gmariotti.cardslib.library.view.CardGridView
 *     android:layout_width="match_parent"
 *     android:layout_height="match_parent"
 *     android:columnWidth="190dp"
 *     android:numColumns="auto_fit"
 *     android:verticalSpacing="3dp"
 *     android:horizontalSpacing="2dp"
 *     android:stretchMode="columnWidth"
 *     android:gravity="center"
 *     card:list_card_layout_resourceID="@layout/carddemo_grid_gplay"
 *     android:id="@+id/carddemo_grid_base1"/>
 * </code></pre>
 * or:
 * <pre><code>
 * adapter.setRowLayoutId(list_card_layout_resourceID);
 * </code></pre>
 * </p>
 *
 * @author Gabriele Mariotti (gabri.mariotti@gmail.com)
 */
public class CardGridArrayAdapter extends BaseCardArrayAdapter {

    protected static String TAG = "CardGridArrayAdapter";

    /**
     * {@link CardGridView}
     */
    protected CardGridView mCardGridView;

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
    public CardGridArrayAdapter(Context context, List<Card> cards) {
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
                //It is important to set recycle value for inner layout elements
                mCardView.setForceReplaceInnerLayout(Card.equalsInnerLayout(mCardView.getCard(),mCard));

                //It is important to set recycle value for performance issue
                mCardView.setRecycle(recycle);

                //Save original swipeable value
                boolean origianlSwipeable = mCard.isSwipeable();
                //Set false to avoid swape card default action
                mCard.setSwipeable(false);
                mCardView.setCard(mCard);

                //mCard.setSwipeable(origianlSwipeable);
                if (origianlSwipeable)
                    Log.d(TAG, "Swipe action not enabled in this type of view");

                //If card has an expandable button override animation
                if (mCard.getCardHeader() != null && mCard.getCardHeader().isButtonExpandVisible()) {
                    //setupExpandCollapseListAnimation(mCardView);
                    Log.d(TAG, "Expand action not enabled in this type of view");
                }

                //Setup swipeable animation
                setupSwipeableAnimation(mCard, mCardView);

                //setupMultiChoice
                setupMultichoice(view,mCard,mCardView,position);
            }
        }

        return view;
    }

    /**
     * Removes SwipeAnimation on Grid
     *
     * @param card     {@link Card}
     * @param cardView {@link CardView}
     */
    protected void setupSwipeableAnimation(final Card card, CardView cardView) {

        cardView.setOnTouchListener(null);
    }

    /**
     * Overrides the default collapse/expand animation in a List
     *
     * @param cardView {@link it.gmariotti.cardslib.library.view.CardView}
     */
    protected void setupExpandCollapseListAnimation(CardView cardView) {

        if (cardView == null) return;
        cardView.setOnExpandListAnimatorListener(mCardGridView);
    }


    // -------------------------------------------------------------
    //  Getters and Setters
    // -------------------------------------------------------------

    /**
     * @return {@link CardGridView}
     */
    public CardGridView getCardGridView() {
        return mCardGridView;
    }

    /**
     * Sets the {@link CardGridView}
     *
     * @param cardGridView cardGridView
     */
    public void setCardGridView(CardGridView cardGridView) {
        this.mCardGridView = cardGridView;
    }
}
