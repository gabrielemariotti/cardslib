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

package it.gmariotti.cardslib.library.extra.staggeredgrid.internal;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import it.gmariotti.cardslib.library.extra.R;
import it.gmariotti.cardslib.library.extra.staggeredgrid.view.CardGridStaggeredView;
import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.base.BaseCardArrayAdapter;
import it.gmariotti.cardslib.library.view.CardView;

/**
 * Array Adapter for {@link it.gmariotti.cardslib.library.internal.Card} model.
 * Use it with a {@link it.gmariotti.cardslib.library.extra.staggeredgrid.view.CardGridStaggeredView}.
 * </p>
 * Usage:
 * <pre><code>
 * ArrayList<Card> cards = new ArrayList<Card>();
 * for (int i=0;i<1000;i++){
 *     CardExample card = new CardExample(getActivity(),"My title "+i,"Inner text "+i);
 *     cards.add(card);
 * }
 * <p/>
 * CardGridStaggeredArrayAdapter mCardArrayAdapter = new CardGridStaggeredArrayAdapter(getActivity(),cards);
 * <p/>
 * CardGridStaggeredView gridView = (CardGridStaggeredView) getActivity().findViewById(R.id.gridId);
 * gridView.setAdapter(mCardArrayAdapter); *
 * </code></pre>
 * It provides a default layout id for each row @layout/list_card_layout
 * Use can easily customize it using card:list_card_layout_resourceID attr in your xml layout:
 * <pre><code>
 *    <it.gmariotti.cardslib.library.extra.staggeredgrid.view.CardGridStaggeredView
 *      android:layout_width="match_parent"
 *      android:layout_height="match_parent"
 *      card:item_margin="8dp"
 *      card:column_count_portrait="3"
 *      card:column_count_landscape="3"
 *      card:grid_paddingRight="8dp"
 *      card:grid_paddingLeft="8dp"
 *      card:list_card_layout_resourceID="@layout/carddemo_extras_staggered_card"
 *      android:id="@+id/carddemo_extras_grid_stag"/>
 * </code></pre>
 * or:
 * <pre><code>
 * adapter.setRowLayoutId(list_card_layout_resourceID);
 * </code></pre>
 * </p>
 *
 * @author Gabriele Mariotti (gabri.mariotti@gmail.com)
 */
public class CardGridStaggeredArrayAdapter extends BaseCardArrayAdapter {

    protected static String TAG = "CardGridStaggeredArrayAdapters";

    /**
     * {@link it.gmariotti.cardslib.library.extra.staggeredgrid.view.CardGridStaggeredView}
     */
    protected CardGridStaggeredView mCardGridView;

    // -------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------

    /**
     * Constructor
     *
     * @param context The current context.
     * @param cards   The cards to represent in the ListView.
     */
    public CardGridStaggeredArrayAdapter(Context context, List<Card> cards) {
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
                if ((mCard.getCardHeader() != null && mCard.getCardHeader().isButtonExpandVisible()) || mCard.getViewToClickToExpand()!=null ){
                    //setupExpandCollapseListAnimation(mCardView);
                    Log.d(TAG, "Expand action not enabled in this type of view");
                }

                //Setup swipeable animation
                //setupSwipeableAnimation(mCard, mCardView);

                //setupMultiChoice
                setupMultichoice(view,mCard,mCardView,position);
            }
        }

        return view;
    }

    @Override
    public int getItemViewType(int position) {
        if (position>=0 && position<getCount()){
            return super.getItemViewType(position);
        }else{
            return position;
        }
    }


    // -------------------------------------------------------------
    //  Getters and Setters
    // -------------------------------------------------------------

    /**
     * @return {@link it.gmariotti.cardslib.library.view.CardGridView}
     */
    public CardGridStaggeredView getCardGridView() {
        return mCardGridView;
    }

    /**
     * Sets the {@link it.gmariotti.cardslib.library.view.CardGridView}
     *
     * @param cardGridView cardGridView
     */
    public void setCardGridView(CardGridStaggeredView cardGridView) {
        this.mCardGridView = cardGridView;
    }
}
