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
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.HashMap;

import it.gmariotti.cardslib.library.R;
import it.gmariotti.cardslib.library.internal.base.BaseCard;

/**
 * Main Card Model
 * </p>
  * You can customize this component. See https://github.com/gabrielemariotti/cardslib/tree/master/doc/CARD.md for more information.
 * </p>
 * You can easily extend and customize this class providing your inner layout and
 * setting your values with {@link #setupInnerViewElements(android.view.ViewGroup, android.view.View)} method.
 * </p>
 * Usage:
 * <pre><code>
 *
 *     //Create a Card
 *     Card card = new Card(getContext());
 *
 *     //Create a CardHeader
 *     CardHeader header = new CardHeader(getContext());
 *
 *     //Add Header to card
 *     card.addCardHeader(header);
 *
 * </code></pre>
 *
 * @author Gabriele Mariotti (gabri.mariotti@gmail.com)
 */
public class Card extends BaseCard {

    // -------------------------------------------------------------

    protected static String TAG = "Card";

    public static int DEFAULT_COLOR= 0;

    /**
     * Used to enable a onClick Action on card
     */
    protected boolean mIsClickable = false;

    /**
     * Used to enable a onLongClick Action on card
     */
    protected boolean mIsLongClickable = false;

    /**
     * Used to enable a swipe gesture and its listener {@link Card#setOnClickListener(OnCardClickListener)}
     */
    protected boolean mIsSwipeable = false;

    /**
     * Indicates to draw a shadow
     */
    protected boolean mShadow = true;

    // Elements

    /**
     * {@link CardHeader}
     */
    protected CardHeader mCardHeader;

    /**
     * {@link CardThumbnail}
     */
    protected CardThumbnail mCardThumbnail;

    /**
     * {@link CardExpand}
     */
    protected CardExpand mCardExpand;

    /**
     * Indicates if card is expanded or collapsed
     */
    protected boolean mIsExpanded=false;

    // Listeners

    /**
     * Listener invoked when the card is swiped
     */
    protected OnSwipeListener mOnSwipeListener;

    /**
     * Listener invoked when the card is clicked
     */
    protected OnCardClickListener mOnClickListener;

    /**
     * Listener invoked when Expand Animator ends
     */
    protected OnExpandAnimatorEndListener mOnExpandAnimatorEndListener;

    /**
     * Listener invoked when Collapse Animator ends
     */
    protected OnCollapseAnimatorEndListener mOnCollapseAnimatorEndListener;

    /**
     * Listener invoked when Expand Animator starts
     */
    protected OnExpandAnimatorStartListener mOnExpandAnimatorStartListener;

    /**
     * Listener invoked when Collapse Animator starts
     */
    protected OnCollapseAnimatorStartListener mOnCollapseAnimatorStartListener;

    /**
     * Partial OnClickListener
     */
    protected HashMap<Integer, OnCardClickListener> mMultipleOnClickListener;

    /**
     *  Global area
     *  It is used by partial click listener
     */
    public static final int CLICK_LISTENER_ALL_VIEW = 0;

    /**
     * Thumbnail area
     *  It is used by partial click listener
     */
    public static final int CLICK_LISTENER_THUMBNAIL_VIEW = 1;

    /**
     * Header Area
     * It is used by partial click listener
     */
    public static final int CLICK_LISTENER_HEADER_VIEW = 2;

    /**
     * Content Main area.
       It is used by partial click listener
     */
    public static final int CLICK_LISTENER_CONTENT_VIEW = 10;

    /**
     * All card area except the supplemental area with actions.
     It is used by partial click listener
     */
    public static final int CLICK_LISTENER_ACTIONAREA1_VIEW = 9;

    /**
     * Listener invoked when the user undo a swipe action in a List
     */
    protected OnUndoSwipeListListener mOnUndoSwipeListListener;

    /**
     * Listener invoked when the Undo controller hides the Undo Bar after a swipe action in a List
     */
    protected OnUndoHideSwipeListListener mOnUndoHideSwipeListListener;

    /**
     * It identifies the background resource of view with this id:
     * android:id="@+id/card_main_layout"
     * <p/>
     * In a native card it identifies the main background.
     */
    private int mBackgroundResourceId =0;

    /**
     * It identifies the background resource of view with this id:
     * android:id="@+id/card_main_layout"
     * <p/>
     * In a native card it identifies the main background.
     */
    private Drawable mBackgroundResource =null;

    /**
     * In a native card it identifies the main background color.
     * Use it only with a NativeCard
     */
    private int mBackgroundColorResourceId =0;

    /**
     * Used to enable a onLongClick Action on multichoiceAdapter
     */
    private boolean mCheckable= true;

    /**
     * Used by multichoice
     */
    protected boolean mMultiChoiceEnabled = false;

    /**
     * The view to click to enable expand/collapse actions
     */
    protected ViewToClickToExpand viewToClickToExpand=null;

    /**
     *  Custom Elevation
     */
    protected Float mCardElevation;


    private boolean couldUseNativeInnerLayout = false;

    // -------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------

    /**
     * Constructor with a base inner layout defined by R.layout.inner_base_main
     *
     * @param context context
     */
    public Card(Context context) {
        this(context, R.layout.inner_base_main);
    }

    /**
     * Constructor with a custom inner layout.
     *
     * @param context     context
     * @param innerLayout resource ID for inner layout
     */
    public Card(Context context, int innerLayout) {
        super(context);
        mParentCard = null;
        mInnerLayout = innerLayout;

        if (innerLayout == R.layout.inner_base_main)
            couldUseNativeInnerLayout = true;
    }

    // -------------------------------------------------------------
    // Setup Inner view Element
    // -------------------------------------------------------------

    /**
     * Inflates the inner layout and adds to parent layout.
     * Then calls {@link #setupInnerViewElements(android.view.ViewGroup, android.view.View)} method
     * to setup all values.
     * </p>
     * You can provide your custom layout.
     * You can use a xml layout with {@link Card#setInnerLayout(int)} or with constructor
     * {@link Card#Card(android.content.Context, int)}
     * <p/>
     * Then customize #setupInnerViewElements to set your values.
     *
     * @param context context
     * @param parent  Inner Layout
     * @return view
     */
    @Override
    public View getInnerView(Context context, ViewGroup parent) {

        //Check if the default inner layout could be the native layout
        setupInnerLayout();

        View view = super.getInnerView(context, parent);

        //This provides a simple implementation with a single title
        if (view != null) {

            if (parent != null)
                //Add inner view to parent
                parent.addView(view);

            //Setup value
            if (mInnerLayout > -1) {
                setupInnerViewElements(parent, view);
            }
        }
        return view;
    }


    /**
     * This method sets values to header elements and customizes view.
     * <p/>
     * Override this method to set your elements inside InnerView.
     *
     * @param parent parent view (Inner Frame)
     * @param view   Inner View
     */
    @Override
    public void setupInnerViewElements(ViewGroup parent, View view) {

        //Add simple title to header
        if (view != null) {
            TextView mTitleView = view.findViewById(R.id.card_main_inner_simple_title);
            if (mTitleView != null)
                mTitleView.setText(mTitle);
        }
    }

    /**
     * Setup the inner layout
     */
    protected void setupInnerLayout(){
        //Check if the default inner layout could be the native layout
        if (couldUseNativeInnerLayout && isNative())
            mInnerLayout = R.layout.native_inner_base_main;
    }

    // -------------------------------------------------------------
    // CardThumbnail
    // -------------------------------------------------------------

    /**
     * Adds a {@link CardThumbnail}
     * Set the parent card in CardThumbnail.
     *
     * @param cardThumbnail
     */
    public void addCardThumbnail(CardThumbnail cardThumbnail) {
        mCardThumbnail = cardThumbnail;
        if (mCardThumbnail != null)
            mCardThumbnail.setParentCard(this);
    }

    /**
     * Returns a {@link CardThumbnail}
     *
     * @return a {@link CardThumbnail}
     */
    public CardThumbnail getCardThumbnail() {
        return mCardThumbnail;
    }

    // -------------------------------------------------------------
    // Header
    // -------------------------------------------------------------

    /**
     * Adds a {@link CardHeader}.
     * Set set ParentCard inside Header.
     *
     * @param cardHeader
     */
    public void addCardHeader(CardHeader cardHeader) {
        mCardHeader = cardHeader;
        if (mCardHeader != null)
            mCardHeader.setParentCard(this);
    }

    /**
     * Returns a {@link CardHeader}
     *
     * @return a {@link CardHeader}
     */
    public CardHeader getCardHeader() {
        return mCardHeader;
    }

    // -------------------------------------------------------------
    // Expand
    // -------------------------------------------------------------

    /**
     * Adds a {@link CardExpand}
     *
     * @param cardExpand a {@link CardExpand}
     */
    public void addCardExpand(CardExpand cardExpand) {
        mCardExpand = cardExpand;
        if (mCardExpand != null)
            mCardExpand.setParentCard(this);
    }

    /**
     * Returns the {@link CardExpand}
     *
     * @return a {@link CardExpand}
     */
    public CardExpand getCardExpand() {
        return mCardExpand;
    }

    // -------------------------------------------------------------
    // Supplemental Actions
    // -------------------------------------------------------------

    public void setupSupplementalActions() {
        //Do Nothing
    }

    // -------------------------------------------------------------
    // On Swipe Interface and Listener
    // -------------------------------------------------------------

    /**
     * Interface to listen for any callbacks when card is swiped.
     */
    public interface OnSwipeListener {
        void onSwipe(Card card);
    }

    /**
     * Called when card is swiped
     */
    public void onSwipeCard() {
        if (isSwipeable() && mOnSwipeListener != null) {
            //mOnSwipeListener.onSwipe(this, mCardView);
            mOnSwipeListener.onSwipe(this);
        }
    }

    /**
     * Returns listener invoked when card is swiped
     *
     * @return listener
     */
    public OnSwipeListener getOnSwipeListener() {
        return mOnSwipeListener;
    }

    /**
     * Sets listener invoked when card is swiped.
     * If listener is <code>null</code> the card is not swipeable.
     *
     * @param onSwipeListener listener
     */
    public void setOnSwipeListener(OnSwipeListener onSwipeListener) {
        mIsSwipeable = onSwipeListener != null;
        this.mOnSwipeListener = onSwipeListener;
    }

    // -------------------------------------------------------------
    // On Undo Action
    // -------------------------------------------------------------

    /**
     * Interface to listen for any callbacks when card is swiped in a List
     */
    public interface OnUndoSwipeListListener {
        void onUndoSwipe(Card card);
    }

    /**
     * Interface to listen for when the Undo controller hides the Undo Bar
     */
    public interface OnUndoHideSwipeListListener {
        void onUndoHideSwipe(Card card);
    }

    /**
     * Called when user undo a swipe action
     */
    public void onUndoSwipeListCard() {
        if (isSwipeable() && mOnUndoSwipeListListener != null) {
            mOnUndoSwipeListListener.onUndoSwipe(this);
        }
    }

    /**
     * Returns listener invoked when user undo a swipe action
     *
     * @return listener
     */
    public OnUndoSwipeListListener getOnUndoSwipeListListener() {
        return mOnUndoSwipeListListener;
    }

    /**
     * Sets listener invoked when user undo a swipe action
     *
     * @param onUndoSwipeListListener listener
     */
    public void setOnUndoSwipeListListener(OnUndoSwipeListListener onUndoSwipeListListener) {
        this.mOnUndoSwipeListListener = onUndoSwipeListListener;
    }

    /**
     * Returns listener invoked the Undo controller hides the Undo Bar
     *
     * @return listener
     */
    public OnUndoHideSwipeListListener getOnUndoHideSwipeListListener() {
        return mOnUndoHideSwipeListListener;
    }

    /**
     * Sets listener invoked when the Undo controller hides the Undo Bar
     *
     * @param onUndoHideSwipeListListener
     */
    public void setOnUndoHideSwipeListListener(OnUndoHideSwipeListListener onUndoHideSwipeListListener) {
        mOnUndoHideSwipeListListener = onUndoHideSwipeListListener;
    }

    // -------------------------------------------------------------
    //  OnClickListener
    // -------------------------------------------------------------

    /**
     * Interface to listen for any callbacks when card is clicked
     */
    public interface OnCardClickListener {
        void onClick(Card card, View view);
    }

    /**
     * Returns listener invoked when card is clicked
     *
     * @return listener
     */
    public OnCardClickListener getOnClickListener() {
        return mOnClickListener;
    }

    /**
     * Sets listener invoked when card is clicked
     * If listener is <code>null</code> then card is not clickable.
     *
     * @param onClickListener listener
     */
    public void setOnClickListener(OnCardClickListener onClickListener) {
        mIsClickable = onClickListener != null;
        mOnClickListener = onClickListener;
    }

    // -------------------------------------------------------------
    //  OnLongClickListener
    // -------------------------------------------------------------

    /**
     * Interface to listen for any callbacks when card is long clicked
     */
    public interface OnLongCardClickListener {
        boolean onLongClick(Card card, View view);
    }

    /**
     * Listener invoked when card is long clicked
     */
    protected OnLongCardClickListener mOnLongClickListener;

    /**
     * Return the listener invoked when card is long clicked
     *
     * @return
     */
    public OnLongCardClickListener getOnLongClickListener() {
        return mOnLongClickListener;
    }

    /**
     * Sets listener invoked when card is long clicked
     * If listener is <code>null</code> then card is not long clickable.
     *
     * @param onLongClickListener
     */
    public void setOnLongClickListener(OnLongCardClickListener onLongClickListener) {
        mIsLongClickable = onLongClickListener != null;
        mOnLongClickListener = onLongClickListener;
    }

    // -------------------------------------------------------------
    //  OnAnimationExpandEnd Interface and Listener
    // -------------------------------------------------------------

    /**
     * Interface to listen any callbacks when expand animation ends
     */
    public interface OnExpandAnimatorEndListener {
        void onExpandEnd(Card card);
    }

    /**
     * Called at the end of Expand Animator
     */
    public void onExpandEnd() {
        if (mOnExpandAnimatorEndListener != null) {
            mOnExpandAnimatorEndListener.onExpandEnd(this);
        }
    }

    /**
     * Returns the listener invoked when expand animation ends
     *
     * @return listener
     */
    public OnExpandAnimatorEndListener getOnExpandAnimatorEndListener() {
        return mOnExpandAnimatorEndListener;
    }

    /**
     * Sets the listener invoked when expand animation ends
     *
     * @param onExpandAnimatorEndListener listener
     */
    public void setOnExpandAnimatorEndListener(OnExpandAnimatorEndListener onExpandAnimatorEndListener) {
        this.mOnExpandAnimatorEndListener = onExpandAnimatorEndListener;
    }

    /**
     * Interface to listen any callbacks when expand animation starts
     */
    public interface OnExpandAnimatorStartListener {
        void onExpandStart(Card card);
    }

    /**
     * Called at the start of Expand Animator
     */
    public void onExpandStart() {
        if (mOnExpandAnimatorStartListener != null) {
            mOnExpandAnimatorStartListener.onExpandStart(this);
        }
    }

    /**
     * Returns the listener invoked when expand animation starts
     *
     * @return listener
     */
    public OnExpandAnimatorStartListener getOnExpandAnimatorStartListener() {
        return mOnExpandAnimatorStartListener;
    }

    /**
     * Sets the listener invoked when expand animation ends
     *
     * @param onExpandAnimatorStartListener listener
     */
    public void setOnExpandAnimatorStartListener(OnExpandAnimatorStartListener onExpandAnimatorStartListener) {
        this.mOnExpandAnimatorStartListener = onExpandAnimatorStartListener;
    }

    // -------------------------------------------------------------
    //  OnAnimationExpandEnd Interface and Listener
    // -------------------------------------------------------------

    /**
     * Interface to listen any callbacks when expand animation ends
     */
    public interface OnCollapseAnimatorEndListener {
        void onCollapseEnd(Card card);
    }

    /**
     * Call at the end of collapse animation
     */
    public void onCollapseEnd() {
        if (mOnCollapseAnimatorEndListener != null) {
            mOnCollapseAnimatorEndListener.onCollapseEnd(this);
        }
    }

    /**
     * Returns the listener invoked when collapse animation ends
     *
     * @return listener
     */
    public OnCollapseAnimatorEndListener getOnCollapseAnimatorEndListener() {
        return mOnCollapseAnimatorEndListener;
    }

    /**
     * Sets the listener when collapse animation ends
     *
     * @param onCollapseAnimatorEndListener
     */
    public void setOnCollapseAnimatorEndListener(OnCollapseAnimatorEndListener onCollapseAnimatorEndListener) {
        this.mOnCollapseAnimatorEndListener = onCollapseAnimatorEndListener;
    }

    /**
     * Interface to listen any callbacks when collapse animation starts
     */
    public interface OnCollapseAnimatorStartListener {
        void onCollapseStart(Card card);
    }

    /**
     * Call at the beginning of collapse animation
     */
    public void onCollapseStart() {
        if (mOnCollapseAnimatorStartListener != null) {
            mOnCollapseAnimatorStartListener.onCollapseStart(this);
        }
    }

    /**
     * Returns the listener invoked when collapse animation starts
     *
     * @return listener
     */
    public OnCollapseAnimatorStartListener getOnCollapseAnimatorStartListener() {
        return mOnCollapseAnimatorStartListener;
    }

    /**
     * Sets the listener when collapse animation starts
     *
     * @param onCollapseAnimatorStartListener
     */
    public void setOnCollapseAnimatorStartListener(OnCollapseAnimatorStartListener onCollapseAnimatorStartListener) {
        this.mOnCollapseAnimatorStartListener = onCollapseAnimatorStartListener;
    }


    public void doExpand(){
        getCardView().doExpand();
    }

    public void doCollapse(){
        getCardView().doCollapse();
    }

    public void doToogleExpand(){
        getCardView().doToggleExpand();
    }

    // -------------------------------------------------------------

    /**
     * @return <code>true</code> if card has a header
     */
    public boolean hasHeader() {
        return getCardHeader() != null;
    }

    // -------------------------------------------------------------
    //  Getters and Setters
    // -------------------------------------------------------------

    /**
     * Sets the shadow elevation.
     * This method works only with the CardViewNative.
     * @param elevation
     */
    public void setCardElevation(float elevation) {
        this.mCardElevation = elevation;
    }

    /**
     * Returns the elevation
     *
     * @return
     */
    public Float getCardElevation() {
        return mCardElevation;
    }

    /**
     * Indicates if card has a shadow
     *
     * @return <code>true</code> if card has a shadow
     */
    public boolean isShadow() {
        return mShadow;
    }

    /**
     * Set the shadow visibility
     *
     * @param shadow
     */
    public void setShadow(boolean shadow) {
        mShadow = shadow;
    }


    public Context getContext() {
        return mContext;
    }

    public void setContext(Context context) {
        mContext = context;
    }

    /**
     * Returns <code>true</code> if the card is clickable
     * If card hasn't a {@link OnCardClickListener} or any partial Listener return <code>true</code> in any cases.
     *
     * @return
     */
    public boolean isClickable() {
        if (mIsClickable) {
            if (mOnClickListener == null && (mMultipleOnClickListener == null || mMultipleOnClickListener.isEmpty())) {
                Log.w(TAG, "Clickable set to true without onClickListener");
                return false;
            }
        }
        return mIsClickable;
    }

    /**
     * Set the card as clickable
     *
     * @param isClickable
     */
    public void setClickable(boolean isClickable) {
        mIsClickable = isClickable;
    }

    /**
     * Indicates if the card is swipeable
     *
     * @return
     */
    public boolean isSwipeable() {
        return mIsSwipeable;
    }

    /**
     * Set the card as swipeable
     *
     * @param isSwipeable
     */
    public void setSwipeable(boolean isSwipeable) {
        mIsSwipeable = isSwipeable;
    }

    /**
     * Indicates if the card is long clickable
     * If card hasn't a {@link OnLongCardClickListener} return <code>false</code> in any cases.
     *
     * @return
     */
    public boolean isLongClickable() {
        if (mOnLongClickListener == null) {
            if (mIsLongClickable)
                Log.w(TAG, "LongClickable set to true without onLongClickListener");
            return false;
        }
        return mIsLongClickable;
    }

    /**
     * Sets the card as longClickable
     *
     * @param isLongClickable
     */
    public void setLongClickable(boolean isLongClickable) {
        mIsLongClickable = isLongClickable;
    }

    /**
     * Adds a clickListener on a specific area
     * </p>
     * You can use one of these values:
     * {@link Card#CLICK_LISTENER_ALL_VIEW}
     * {@link Card#CLICK_LISTENER_HEADER_VIEW}
     * {@link Card#CLICK_LISTENER_THUMBNAIL_VIEW}
     * {@link Card#CLICK_LISTENER_CONTENT_VIEW}
     *
     * @param area
     * @param onClickListener
     */
    public void addPartialOnClickListener(int area, OnCardClickListener onClickListener) {

        if (area < CLICK_LISTENER_ALL_VIEW && area > CLICK_LISTENER_CONTENT_VIEW)
            Log.w(TAG, "area value not valid in addPartialOnClickListner");

        HashMap multipleOnClickListener = getMultipleOnClickListener();
        if (onClickListener != null) {
            multipleOnClickListener.put(area, onClickListener);
            mIsClickable = true;
        } else {
            removePartialOnClickListener(area);
        }
    }

    /**
     * Remove ClickListener from a specif area
     * </p>
     * You can use one of these values:
     * {@link Card#CLICK_LISTENER_ALL_VIEW}
     * {@link Card#CLICK_LISTENER_HEADER_VIEW}
     * {@link Card#CLICK_LISTENER_THUMBNAIL_VIEW}
     * {@link Card#CLICK_LISTENER_CONTENT_VIEW}
     *
     *
     * @param area
     */
    public void removePartialOnClickListener(int area) {

        HashMap multipleOnClickListener = getMultipleOnClickListener();
        multipleOnClickListener.remove(area);

        if (mOnClickListener == null && multipleOnClickListener.isEmpty())
            mIsClickable = false;
    }

    /**
     * Map for all partial listeners
     *
     * @return  a map with partial listeners
     */
    public HashMap<Integer, OnCardClickListener> getMultipleOnClickListener() {
        if (mMultipleOnClickListener != null)
            return mMultipleOnClickListener;
        return mMultipleOnClickListener = new HashMap<Integer, OnCardClickListener>();
    }

    /**
     * Indicates if the card is expanded or collapsed
     *
     * @return <code>true</code> if card is ExpandLayout is visible, otherwise returns <code>false</code>
     */
    public boolean isExpanded() {
        return mIsExpanded;
    }

    /**
     * Set the card as expanded or collapsed
     *
     * @param expanded <code>true</code> to indicates that the card is expanded
     */
    public void setExpanded(boolean expanded) {
        mIsExpanded = expanded;
    }


    /**
     * Checks if 2 cards have the same innerLayouts
     * It can be used to force inner layout redraw
     *
     * @param oldCard
     * @param newCard
     * @return
     */
    public static boolean equalsInnerLayout(Card oldCard,Card newCard){
        if (oldCard==null || newCard==null) return false;

        //Check inner Layout
        if (oldCard.getInnerLayout()!=newCard.getInnerLayout()) return true;

        //Check inner layout of CardHeader
        if (oldCard.getCardHeader()!=null){
            if (newCard.getCardHeader()==null)
                return true;
            else
            if (oldCard.getCardHeader().getInnerLayout()!=newCard.getCardHeader().getInnerLayout())
                return true;
        }else{
            if (newCard.getCardHeader()!=null)
                return true;
        }

        //Check inner layout of CardThumbnail
        if (oldCard.getCardThumbnail()!=null){
            if (newCard.getCardThumbnail()==null)
                return true;
            else
            if (oldCard.getCardThumbnail().getInnerLayout()!=newCard.getCardThumbnail().getInnerLayout())
                return true;
        }else{
            if (newCard.getCardThumbnail()!=null)
                return true;
        }

        //Check inner layout of CardExpand
        if (oldCard.getCardExpand()!=null){
            if (newCard.getCardExpand()==null)
                return true;
            else
            if (oldCard.getCardExpand().getInnerLayout()!=newCard.getCardExpand().getInnerLayout())
                return true;
        }else{
            if (newCard.getCardExpand()!=null)
                return true;
        }

        return false;
    }

    /**
     * Refreshes the card content (it doesn't inflate layouts again)
     */
    public void notifyDataSetChanged(){
        getCardView().refreshCard(this);
    }

    /**
     * Sets the background drawable resource to override the style of MainLayout (card.main_layout)
     *
     * @param drawableResourceId drawable resource Id
     */
    public void setBackgroundResourceId(int drawableResourceId) {
        this.mBackgroundResourceId = drawableResourceId;
    }

    /**
     * Retrieves the background drawable resource
     *
     * @return
     */
    public int getBackgroundResourceId() {
        return mBackgroundResourceId;
    }

    /**
     * Retrieves the background drawable resource
     *
     * @return
     */
    public Drawable getBackgroundResource() {
        return mBackgroundResource;
    }

    /**
     * Sets the background drawable resource to override the style of MainLayout (card.main_layout)
     *
     * @param drawableResource drawable resource
     */
    public void setBackgroundResource(Drawable drawableResource) {
        this.mBackgroundResource = drawableResource;
    }

    /**
     * Changes dynamically the drawable resource to override the style of MainLayout.
     *
     * @param drawableResourceId   drawable resource Id
     */
    public void changeBackgroundResourceId(int drawableResourceId){
        if (mCardView!=null){
            mCardView.changeBackgroundResourceId(drawableResourceId);
        }
    }

    /**
     * Changes dynamically the drawable resource to override the style of MainLayout.
     *
     * @param drawableResource   drawable resource
     */
    public void changeBackgroundResource(Drawable drawableResource){
        if (mCardView!=null){
            mCardView.changeBackgroundResource(drawableResource);
        }
    }

    /**
     * Indicates if the card is clickable on a MultiChoiceAdapter
     *
     * @return <code>true</code> if card is clickable
     */
    public boolean isCheckable() {
        return mCheckable;
    }

    /**
     * Set the card as clickable in a MultiChoiceAdapter
     *
     * @param checkable <code>true</code> to indicates that the card is clickable
     */
    public void setCheckable(boolean checkable) {
        mCheckable = checkable;
    }

    /**
     * Returns true if the multichoice is enabled
     * @return
     */
    public boolean isMultiChoiceEnabled() {
        return mMultiChoiceEnabled;
    }

    /**
     * Returns the view to click to enable expand/collapse actions
     *
     * @return
     */
    public ViewToClickToExpand getViewToClickToExpand() {
        return viewToClickToExpand;
    }

    /**
     * To set the view to click to enable expand/collapse actions
     *
     * @param viewToClickToExpand
     */
    public void setViewToClickToExpand(ViewToClickToExpand viewToClickToExpand) {
        this.viewToClickToExpand = viewToClickToExpand;
    }

    /**
     * Returns true if the card is using the native card
     * @return
     */
    protected boolean isNative(){
        if (mCardView!=null)
            return mCardView.isNative();
        return false;
    }

    /**
     * Set the background color assigned to the Native CardView
     * @param backgroundColorResourceId
     */
    public void setBackgroundColorResourceId(int backgroundColorResourceId) {
        mBackgroundColorResourceId = backgroundColorResourceId;
    }

    /**
     * Returns the background color assigned to the Native CardView
     * @return
     */
    public int getBackgroundColorResourceId() {
        return mBackgroundColorResourceId;
    }
}
