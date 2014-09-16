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

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import it.gmariotti.cardslib.library.R;
import it.gmariotti.cardslib.library.internal.base.BaseCardArrayAdapter;
import it.gmariotti.cardslib.library.view.CardListView;
import it.gmariotti.cardslib.library.view.CardView;
import it.gmariotti.cardslib.library.view.listener.SwipeDismissListViewTouchListener;
import it.gmariotti.cardslib.library.view.listener.SwipeOnScrollListener;
import it.gmariotti.cardslib.library.view.listener.UndoBarController;
import it.gmariotti.cardslib.library.view.listener.UndoCard;
import it.gmariotti.cardslib.library.view.listener.dismiss.DefaultDismissableManager;
import it.gmariotti.cardslib.library.view.listener.dismiss.Dismissable;

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
public class CardArrayAdapter extends BaseCardArrayAdapter implements UndoBarController.UndoListener {

    protected static String TAG = "CardArrayAdapter";

    /**
     * {@link CardListView}
     */
    protected CardListView mCardListView;

    /**
     * Listener invoked when a card is swiped
     */
    protected SwipeDismissListViewTouchListener mOnTouchListener;

    /**
     * Used to enable an undo message after a swipe action
     */
    protected boolean mEnableUndo=false;

    /**
     * Undo Controller
     */
    protected UndoBarController mUndoBarController;

    /**
     * Internal Map with all Cards.
     * It uses the card id value as key.
     */
    protected HashMap<String /* id */,Card>  mInternalObjects;

    /**
     * Dismissable Manager
     */
    protected Dismissable mDismissable;

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
                //It is important to set recycle value for inner layout elements
                mCardView.setForceReplaceInnerLayout(Card.equalsInnerLayout(mCardView.getCard(),mCard));

                //It is important to set recycle value for performance issue
                mCardView.setRecycle(recycle);

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
                setupSwipeableAnimation(mCard, mCardView);

                //setupMultiChoice
                setupMultichoice(view,mCard,mCardView,position);
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

                //Configure the default DismissableManager
                if (mDismissable == null) mDismissable = new DefaultDismissableManager();
                mDismissable.setAdapter(this);
                mOnTouchListener.setDismissable(mDismissable);

                // Setting this scroll listener is required to ensure that during
                // ListView scrolling, we don't look for swipes.
                if (mCardListView.getOnScrollListener() == null){
                    SwipeOnScrollListener scrollListener = new SwipeOnScrollListener();
                    scrollListener.setTouchListener(mOnTouchListener);
                    mCardListView.setOnScrollListener(scrollListener);
                }else{
                    AbsListView.OnScrollListener onScrollListener=mCardListView.getOnScrollListener();
                    if (onScrollListener instanceof SwipeOnScrollListener)
                        ((SwipeOnScrollListener) onScrollListener).setTouchListener(mOnTouchListener);

                }

                mCardListView.setOnTouchListener(mOnTouchListener);
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
    //  SwipeListener and undo action
    // -------------------------------------------------------------
    /**
     * Listener invoked when a card is swiped
     */
    SwipeDismissListViewTouchListener.DismissCallbacks mCallback = new SwipeDismissListViewTouchListener.DismissCallbacks() {

        @Override
        public boolean canDismiss(int position, Card card) {
            return mDismissable.isDismissable(position, card);
        }

        @Override
        public void onDismiss(ListView listView, int[] reverseSortedPositions) {

            int[] itemPositions=new int[reverseSortedPositions.length];
            String[] itemIds=new String[reverseSortedPositions.length];
            int i=0;

            // Keep track of the cards that will be removed
            final ArrayList<Card> removedCards = new ArrayList<Card>();

            //Remove cards and notifyDataSetChanged
            for (int position : reverseSortedPositions) {

                Card card = null;
                if (listView.getAdapter() != null && listView.getAdapter().getItem(position) instanceof Card)
                    card = (Card) listView.getAdapter().getItem(position);
                //Card card = getItem(position);

                if (card != null) {
                    itemPositions[i] = position;
                    itemIds[i] = card.getId();
                    i++;

                    /*
                    if (card.isExpanded()){
                        if (card.getCardView()!=null && card.getCardView().getOnExpandListAnimatorListener()!=null){
                            //There is a List Animator.
                            card.getCardView().getOnExpandListAnimatorListener().onCollapseStart(card.getCardView(), card.getCardView().getInternalExpandLayout());
                        }
                    }*/
                    removedCards.add(card);
                    remove(card);
                    if (card.getOnSwipeListener() != null) {
                        card.getOnSwipeListener().onSwipe(card);
                    }
                }else{
                    Log.e(TAG,"Error on swipe action. Impossible to retrieve the card from position");
                }
            }
            notifyDataSetChanged();

            //Check for a undo message to confirm
            if (isEnableUndo() && mUndoBarController!=null){

                //Show UndoBar
                UndoCard itemUndo=new UndoCard(itemPositions,itemIds);

                //MessageUndoBar
                String messageUndoBar=null;
                if (getUndoBarController().getUndoBarUIElements()!=null){
                    messageUndoBar = getUndoBarController().getUndoBarUIElements().getMessageUndo(CardArrayAdapter.this,itemIds,itemPositions);
                }

                //Default message if null
                if (messageUndoBar == null) {
                    if (getContext() != null) {
                        Resources res = getContext().getResources();
                        if (res != null) {
                            messageUndoBar = res.getQuantityString(R.plurals.list_card_undo_items, reverseSortedPositions.length, reverseSortedPositions.length);
                        }
                    }
                }

                mUndoBarController.showUndoBar(
                        false,
                        messageUndoBar,
                        itemUndo,
                        new UndoBarController.UndoBarHideListener() {
                            @Override
                            public void onUndoBarHide(boolean undoOccurred) {
                                // Remove the items from mInternalObjects, if
                                // the undo was not triggered, since they are
                                // now permanently removed from the underlying Array.
                                if (!undoOccurred) {
                                    for (Card card : removedCards) {
                                        if (card.getOnUndoHideSwipeListListener()!=null)
                                            card.getOnUndoHideSwipeListListener().onUndoHideSwipe(card);
                                        mInternalObjects.remove(card.getId());
                                    }
                                }
                            }
                        });
            }
        }
    };

    // -------------------------------------------------------------
    //  Undo Default Listener
    // -------------------------------------------------------------

    @Override
    public void onUndo(Parcelable token) {
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
        }
    }

    /**
     * Indicates if the undo message is enabled after a swipe action
     *
     * @return <code>true</code> if the undo message is enabled
     */
    public boolean isEnableUndo() {
        return mEnableUndo;
    }

    /**
     * Enables an undo message after a swipe action
     *
     * @param enableUndo <code>true</code> to enable an undo message
     */
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

                if (mUndoBarUIElements==null)
                    mUndoBarUIElements=new UndoBarController.DefaultUndoBarUIElements();

                if (mContext!=null && mContext instanceof Activity) {
                    View undobar = ((Activity) mContext).findViewById(mUndoBarUIElements.getUndoBarId());
                    if (undobar != null) {
                        mUndoBarController = new UndoBarController(undobar, this, mUndoBarUIElements);
                    }
                }else{
                    Log.e(TAG,"Undo Action requires a valid Activity context");
                    throw new IllegalArgumentException("Undo Action requires a valid Activity context");
                }
            }
        }else{
            mUndoBarController=null;
        }
    }

    // ---------------------------------------------------------------------
    //  Override Array Manipulation Methods To Keep mInternalObjects in Sync
    // ---------------------------------------------------------------------

    // public void remove() intentionally omitted, since mInternalObjects needs
    // to keep a reference so the remove can be undone, if necessary.

    @Override
    public void add(Card card) {
        super.add(card);
        if (mEnableUndo) {
            mInternalObjects.put(card.getId(), card);
        }
    }

    @Override
    public void addAll(Collection<? extends Card> cardCollection) {
        super.addAll(cardCollection);
        if (mEnableUndo) {
            for (Card card : cardCollection) {
                mInternalObjects.put(card.getId(), card);
            }
        }
    }

    @Override
    public void addAll(Card...cards) {
        super.addAll(cards);
        if (mEnableUndo) {
            for (Card card : cards) {
                mInternalObjects.put(card.getId(), card);
            }
        }
    }

    @Override
    public void clear() {
        super.clear();
        if (mEnableUndo) {
            mInternalObjects.clear();
        }
    }

    @Override
    public void insert(Card card, int index) {
        super.insert(card, index);
        if (mEnableUndo) {
            mInternalObjects.put(card.getId(), card);
        }
    }

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



    /**
     * Return the UndoBarController for undo action
     *
     * @return {@link UndoBarController}
     */
    public UndoBarController getUndoBarController() {
        return mUndoBarController;
    }

    /**
     * Sets a custom DismissableManager
     * @param dismissable
     */
    public void setDismissable(Dismissable dismissable) {
        mDismissable = dismissable;
    }

}
