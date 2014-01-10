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
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.HashMap;

import it.gmariotti.cardslib.library.R;
import it.gmariotti.cardslib.library.internal.base.BaseCardCursorAdapter;
import it.gmariotti.cardslib.library.view.CardListView;
import it.gmariotti.cardslib.library.view.CardView;

/**
 * Cursor Adapter for {@link it.gmariotti.cardslib.library.internal.Card} model
 *
 *
 * </p>
 * @author Gabriele Mariotti (gabri.mariotti@gmail.com)
 */
public abstract class CardCursorAdapter extends BaseCardCursorAdapter  {

    protected static String TAG = "CardCursorAdapter";

    /**
     * {@link it.gmariotti.cardslib.library.view.CardListView}
     */
    protected CardListView mCardListView;

    /**
     * Listener invoked when a card is swiped
     */
    //protected SwipeDismissListViewTouchListener mOnTouchListener;

    /**
     * Used to enable an undo message after a swipe action
     */
    //protected boolean mEnableUndo=false;

    /**
     * Undo Controller
     */
    //protected UndoBarController mUndoBarController;

    /**
     * Internal Map with all Cards.
     * It uses the card id value as key.
     */
    protected HashMap<String /* id */,Card>  mInternalObjects;

    /**
     * Recycle
     */
    private boolean recycle = false;
    // -------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------

    public CardCursorAdapter(Context context) {
        super(context, null, false);
        mContext= context;
    }

    protected CardCursorAdapter(Context context, Cursor c, boolean autoRequery) {
        super(context, c, autoRequery);
        mContext= context;
    }

    protected CardCursorAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
        mContext= context;
    }

    // -------------------------------------------------------------
    // Views
    // -------------------------------------------------------------


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //Check for recycle
        if (convertView == null) {
            recycle = false;
        } else {
            recycle = true;
        }
        return super.getView(position, convertView, parent);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        int layout = mRowLayoutId;
        LayoutInflater mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        return mInflater.inflate(layout, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        CardView mCardView;
        Card mCard;

        mCard = (Card) getCardFromCursor(cursor);
        if (mCard != null) {
            mCardView = (CardView) view.findViewById(R.id.list_cardId);
            if (mCardView != null) {
                //It is important to set recycle value for inner layout elements
                mCardView.setForceReplaceInnerLayout(Card.equalsInnerLayout(mCardView.getCard(),mCard));

                //It is important to set recycle value for performance issue
                mCardView.setRecycle(recycle);

                //Save original swipeable to prevent cardSwipeListener (listView requires another cardSwipeListener)
                boolean origianlSwipeable = mCard.isSwipeable();
                mCard.setSwipeable(false);

                mCardView.setCard(mCard);

                //Set originalValue
                //mCard.setSwipeable(origianlSwipeable);
                if (origianlSwipeable)
                    Log.d(TAG, "Swipe action not enabled in this type of view");

                //If card has an expandable button override animation
                if (mCard.getCardHeader() != null && mCard.getCardHeader().isButtonExpandVisible()) {
                    setupExpandCollapseListAnimation(mCardView);
                }

                //Setup swipeable animation
                setupSwipeableAnimation(mCard, mCardView);

            }
        }
    }



    /**
     * Sets SwipeAnimation on List
     *
     * @param card {@link it.gmariotti.cardslib.library.internal.Card}
     * @param cardView {@link it.gmariotti.cardslib.library.view.CardView}
     */
    protected void setupSwipeableAnimation(final Card card, CardView cardView) {

        cardView.setOnTouchListener(null);
        /*
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
        }*/
    }

    /**
     * Overrides the default collapse/expand animation in a List
     *
     * @param cardView {@link it.gmariotti.cardslib.library.view.CardView}
     */
    protected void setupExpandCollapseListAnimation(CardView cardView) {

        if (cardView == null) return;
        cardView.setOnExpandListAnimatorListener(mCardListView);
    }

    // -------------------------------------------------------------
    //  SwipeListener and undo action
    // -------------------------------------------------------------
    /**
     * Listener invoked when a card is swiped
     */
    /*SwipeDismissListViewTouchListener.DismissCallbacks mCallback = new SwipeDismissListViewTouchListener.DismissCallbacks() {

        @Override
        public boolean canDismiss(int position, Card card) {
            return card.isSwipeable();
        }

        @Override
        public void onDismiss(ListView listView, int[] reverseSortedPositions) {


            int[] itemPositions=new int[reverseSortedPositions.length];
            String[] itemIds=new String[reverseSortedPositions.length];
            int i=0;

            //Remove cards and notifyDataSetChanged
            for (int position : reverseSortedPositions) {
                Card card = getItem(position);
                itemPositions[i]=position;
                itemIds[i]=card.getId();
                i++;

                if (card.getOnSwipeListener() != null){
                        card.getOnSwipeListener().onSwipe(card);
                }
            }
            //notifyDataSetChanged();

            /*
            //Check for a undo message to confirm
            if (isEnableUndo() && mUndoBarController!=null){

                //Show UndoBar
                UndoCard itemUndo=new UndoCard(itemPositions,itemIds);

                if (getContext()!=null){
                    Resources res = getContext().getResources();
                    if (res!=null){
                        String messageUndoBar = res.getQuantityString(R.plurals.list_card_undo_items, reverseSortedPositions.length, reverseSortedPositions.length);

                        mUndoBarController.showUndoBar(
                                false,
                                messageUndoBar,
                                itemUndo);
                    }
                }

            }
        }
    };*/

    // -------------------------------------------------------------
    //  Undo Default Listener
    // -------------------------------------------------------------

    /*@Override
    public void onUndo(Parcelable token) {
        /*
        //Restore items in lists (use reverseSortedOrder)
        if (token != null) {

            UndoCard item = (UndoCard) token;
            int[] itemPositions = item.itemPosition;
            String[] itemIds = item.itemId;

            if (itemPositions != null) {
                int end = itemPositions.length;

                for (int i = end - 1; i >= 0; i--) {
                    int itemPosition = itemPositions[i];
                    String id= itemIds[i];

                    if (id==null){
                        Log.w(TAG, "You have to set a id value to use the undo action");
                    }else{
                        Card card = mInternalObjects.get(id);
                        if (card!=null){
                            insert(card, itemPosition);
                            notifyDataSetChanged();
                            if (card.getOnUndoSwipeListListener()!=null)
                                card.getOnUndoSwipeListListener().onUndoSwipe(card);
                        }
                    }
                }
            }
        }*
    }*/

    // -------------------------------------------------------------
    //  Getters and Setters
    // -------------------------------------------------------------

    /**
     * @return {@link it.gmariotti.cardslib.library.view.CardListView}
     */
    public CardListView getCardListView() {
        return mCardListView;
    }

    /**
     * Sets the {@link it.gmariotti.cardslib.library.view.CardListView}
     *
     * @param cardListView cardListView
     */
    public void setCardListView(CardListView cardListView) {
        this.mCardListView = cardListView;
    }

    /**
     * Indicates if the undo message is enabled after a swipe action
     *
     * @return <code>true</code> if the undo message is enabled
     */
    /*public boolean isEnableUndo() {
        return mEnableUndo;
    }*/

    /**
     * Enables an undo message after a swipe action
     *
     * @param enableUndo <code>true</code> to enable an undo message
     */
    /*
    public void setEnableUndo(boolean enableUndo) {
        mEnableUndo = enableUndo;
        if (enableUndo) {
            mInternalObjects = new HashMap<String, Card>();
            for (int i=0;i<getCount();i++) {
                Card card = getItem(i);
                mInternalObjects.put(card.getId(), card);
            }

            //Create a UndoController
            if (mUndoBarController==null){
                View undobar = ((Activity)mContext).findViewById(R.id.list_card_undobar);
                if (undobar != null) {
                    mUndoBarController = new UndoBarController(undobar, this);
                }
            }
        }else{
            mUndoBarController=null;
        }
    }*/

    /**
     * Return the UndoBarController for undo action
     *
     * @return {@link it.gmariotti.cardslib.library.view.listener.UndoBarController}
     */
    /*
    public UndoBarController getUndoBarController() {
        return mUndoBarController;
    }*/
}
