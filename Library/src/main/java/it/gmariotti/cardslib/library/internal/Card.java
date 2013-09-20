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
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.HashMap;

import it.gmariotti.cardslib.library.R;
import it.gmariotti.cardslib.library.internal.base.BaseCard;

/**
 * Main Card Model
 * <p/>
 * FIXME
 *
 * @author Gabriele Mariotti (gabri.mariotti@gmail.com)
 */
public class Card extends BaseCard {

    // -------------------------------------------------------------

    protected static String TAG = "Card";

    /**
     * Used to enable a onClick Action on card
     */
    protected boolean mIsClickable = true;

    /**
     * Used to enable a onLongClick Action on card
     */
    protected boolean mIsLongClickable = true;

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
            TextView mTitleView = (TextView) view.findViewById(R.id.card_main_inner_simple_title);
            if (mTitleView != null)
                mTitleView.setText(mTitle);
        }
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
    // On Swipe Interface and Listener
    // -------------------------------------------------------------

    /**
     * Interface to listen for any callbacks when card is swiped.
     */
    public interface OnSwipeListener {
        public void onSwipe(Card card);
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
        if (onSwipeListener != null)
            mIsSwipeable = true;
        else
            mIsSwipeable = false;
        this.mOnSwipeListener = onSwipeListener;
    }


    // -------------------------------------------------------------
    //  OnClickListener
    // -------------------------------------------------------------

    /**
     * Interface to listen for any callbacks when card is clicked
     */
    public interface OnCardClickListener {
        public void onClick(Card card, View view);
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
        if (onClickListener != null)
            mIsClickable = true;
        else
            mIsClickable = false;
        mOnClickListener = onClickListener;
    }

    // -------------------------------------------------------------
    //  OnLongClickListener
    // -------------------------------------------------------------

    /**
     * Interface to listen for any callbacks when card is long clicked
     */
    public interface OnLongCardClickListener {
        public boolean onLongClick(Card card, View view);
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
        if (onLongClickListener != null)
            mIsLongClickable = true;
        else
            mIsLongClickable = false;
        mOnLongClickListener = onLongClickListener;
    }

    // -------------------------------------------------------------
    //  OnAnimationExpandEnd Interface and Listener
    // -------------------------------------------------------------

    /**
     * Interface to listen any callbacks when expand animation ends
     */
    public interface OnExpandAnimatorEndListener {
        public void onExpandEnd(Card card);
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

    // -------------------------------------------------------------
    //  OnAnimationExpandEnd Interface and Listener
    // -------------------------------------------------------------

    /**
     * Interface to listen any callbacks when expand animation ends
     */
    public interface OnCollapseAnimatorEndListener {
        public void onCollapseEnd(Card card);
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

    // -------------------------------------------------------------

    /**
     * @return <code>true</code> if card has a header
     */
    public boolean hasHeader() {
        if (getCardHeader() != null)
            return true;
        else return false;
    }

    // -------------------------------------------------------------
    //  Getters and Setters
    // -------------------------------------------------------------

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
     * Return <code>true</code> if the card is clickable
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
     * Set the card as longClickable
     *
     * @param isLongClickable
     */
    public void setLongClickable(boolean isLongClickable) {
        mIsLongClickable = isLongClickable;
    }

    /**
     * Add a clickListener on a specific area
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


}
