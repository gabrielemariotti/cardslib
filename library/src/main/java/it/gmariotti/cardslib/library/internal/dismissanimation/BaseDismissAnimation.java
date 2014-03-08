/*
 * ******************************************************************************
 *   Copyright (c) 2014 Gabriele Mariotti.
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

package it.gmariotti.cardslib.library.internal.dismissanimation;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.os.SystemClock;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardArrayAdapter;
import it.gmariotti.cardslib.library.internal.base.BaseCardArrayAdapter;
import it.gmariotti.cardslib.library.view.CardListView;
import it.gmariotti.cardslib.library.view.CardView;
import it.gmariotti.cardslib.library.view.listener.SwipeDismissListViewTouchListener;

/**
 * @author Gabriele Mariotti (gabri.mariotti@gmail.com)
 */
public abstract class BaseDismissAnimation {

    protected BaseCardArrayAdapter mBaseAdapter;
    protected CardListView mCardListView;
    protected int mListWidth;
    protected int mAnimationTime = 200;
    protected boolean mDismissRight = true;
    protected Context mContext;


    private List<PendingDismissData> mPendingDismisses = new ArrayList<PendingDismissData>();
    private int mDownPosition;
    private int mDismissAnimationRefCount = 0;

    //--------------------------------------------------------------------------
    // Constructor
    //--------------------------------------------------------------------------

    public BaseDismissAnimation(Context context) {
        mContext = context;
    }

    //--------------------------------------------------------------------------
    // Init
    //--------------------------------------------------------------------------

    public BaseDismissAnimation setup(BaseCardArrayAdapter adapter) {
        mBaseAdapter = adapter;
        return this;
    }

    //--------------------------------------------------------------------------
    // Callbacks
    //--------------------------------------------------------------------------

    /**
     * The callback interface used by {@link SwipeDismissListViewTouchListener} to inform its client
     * about a successful dismissal of one or more list item positions.
     */
    public interface DismissCallbacks {
        /**
         * Called to determine whether the given position can be dismissed.
         */
        boolean canDismiss(int position,Card card);

        /**
         * Called when the user has indicated they she would like to dismiss one or more list item
         * positions.
         *
         * @param listView               The originating {@link ListView}.
         * @param reverseSortedPositions An array of positions to dismiss, sorted in descending
         *                               order for convenience.
         */
        void onDismiss(ListView listView, int[] reverseSortedPositions);
    }


    public void animateDismissPosition(int position) {
        if (position > AbsListView.INVALID_POSITION)
            animateDismissPosition(Arrays.asList(position));
    }

    public void animateDismissPosition(final Collection<Integer> positions) {
        if (mBaseAdapter == null) {
            throw new IllegalStateException("Call setup method before animate!");
        }
        prepareAnimation();

        final List<Integer> positionsCopy = new ArrayList<Integer>(positions);
        List<CardView> views = getVisibleViewsForPositions(positionsCopy);

        for (CardView cardView:views){
            dismissiCardWithAnimation(cardView);
        }
    }

    public void animateDismiss(Card card) {
        if (card!=null)
            animateDismiss(Arrays.asList(card));
    }

    public void animateDismiss(Collection<Card> cards) {
        if (mBaseAdapter == null) {
            throw new IllegalStateException("Call setup method before animate!");
        }
        prepareAnimation();

        final List<Card> cardsCopy = new ArrayList<Card>(cards);
        List<CardView> views = getVisibleViewsForCards(cardsCopy);

        for (CardView cardView:views){
            dismissiCardWithAnimation(cardView);
        }
    }

    /**
     * Prepare the animation
     */
    private void prepareAnimation() {
        setupCardListView();
        if (mCardListView != null) {
            mListWidth = mCardListView.getWidth();
        }
    }


    public abstract void animate(Card card, CardView cardView);//, Card.OnDismissAnimationListener onDismissAnimationListener);


    /**
     * Retrieves the cardListView
     */
    private void setupCardListView() {

        if (mBaseAdapter != null) {
            if (mBaseAdapter instanceof CardArrayAdapter) {
                mCardListView = ((CardArrayAdapter) mBaseAdapter).getCardListView();
            }
        }

        if (mCardListView == null) {
            throw new IllegalStateException("BaseDismissAnimation works with a CardListView");
        }
    }


    /**
     * Returns the visible view for the positions
     *
     * @param positions
     * @return
     */
    private List<CardView> getVisibleViewsForPositions(final Collection<Integer> positions) {
        List<CardView> views = new ArrayList<CardView>();
        for (int i = 0; i < mCardListView.getChildCount(); i++) {
            View child = mCardListView.getChildAt(i);
            if (positions.contains(mCardListView.getPositionForView(child))) {
                views.add((CardView) child);
            }
        }
        return views;
    }

    /**
     * Returns the visible view for the cards
     *
     * @param cardsCopy
     * @return
     */
    private List<CardView> getVisibleViewsForCards(List<Card> cardsCopy) {
        List<CardView> originalViews = new ArrayList<CardView>();
        for (Card card:cardsCopy){
            originalViews.add(card.getCardView());
        }

        /*List<CardView> views = new ArrayList<CardView>();
        for (int i = 0; i < mCardListView.getChildCount(); i++) {
            View child = mCardListView.getChildAt(i);
            if (cardsCopy.contains(views)){
                views.add((CardView) child);
            }
        }*/
        return originalViews;
    }

    private void dismissiCardWithAnimation(final CardView cardView) {
        ++mDismissAnimationRefCount;
        int mDownPosition = mCardListView.getPositionForView(cardView);

        animate(cardView.getCard(),cardView);
    }


    protected void invokeCallbak(final View dismissView) {
        // Animate the dismissed list item to zero-height and fire the dismiss callback when
        // all dismissed list item animations have completed. This triggers layout on each animation
        // frame; in the future we may want to do something smarter and more performant.

        final int dismissPosition= mBaseAdapter.getPosition(((CardView) dismissView).getCard());

        final ViewGroup.LayoutParams lp = dismissView.getLayoutParams();
        final int originalHeight = dismissView.getHeight();

        ValueAnimator animator = ValueAnimator.ofInt(originalHeight, 1).setDuration(mAnimationTime);

        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                --mDismissAnimationRefCount;
                if (mDismissAnimationRefCount == 0) {
                    // No active animations, process all pending dismisses.
                    // Sort by descending position
                    Collections.sort(mPendingDismisses);

                    int[] dismissPositions = new int[mPendingDismisses.size()];
                    for (int i = mPendingDismisses.size() - 1; i >= 0; i--) {
                        dismissPositions[i] = mPendingDismisses.get(i).position;
                    }
                    mCallbacks.onDismiss(mCardListView, dismissPositions);

                    // Reset mDownPosition to avoid MotionEvent.ACTION_UP trying to start a dismiss
                    // animation with a stale position
                    mDownPosition = ListView.INVALID_POSITION;

                    ViewGroup.LayoutParams lp;
                    for (PendingDismissData pendingDismiss : mPendingDismisses) {
                        // Reset view presentation
                        pendingDismiss.view.setAlpha(1f);
                        pendingDismiss.view.setTranslationX(0);
                        lp = pendingDismiss.view.getLayoutParams();
                        lp.height = 0;
                        pendingDismiss.view.setLayoutParams(lp);
                    }

                    // Send a cancel event
                    long time = SystemClock.uptimeMillis();
                    MotionEvent cancelEvent = MotionEvent.obtain(time, time,
                            MotionEvent.ACTION_CANCEL, 0, 0, 0);
                    mCardListView.dispatchTouchEvent(cancelEvent);

                    mPendingDismisses.clear();
                }
            }
        });

        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                lp.height = (Integer) valueAnimator.getAnimatedValue();
                dismissView.setLayoutParams(lp);
            }
        });

        mPendingDismisses.add(new PendingDismissData(dismissPosition, dismissView));
        animator.start();
    }


    class PendingDismissData implements Comparable<PendingDismissData> {
        public int position;
        public View view;

        public PendingDismissData(int position, View view) {
            this.position = position;
            this.view = view;
        }

        @Override
        public int compareTo(PendingDismissData other) {
            // Sort by descending position
            return other.position - position;
        }
    }


    DismissCallbacks mCallbacks = new DismissCallbacks() {

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
                Card card = mBaseAdapter.getItem(position);
                if (card!=null){
                    itemPositions[i]=position;
                    itemIds[i]=card.getId();
                    i++;
                    mBaseAdapter.remove(card);
                    //TODO CHANGE
                    if (card.getOnSwipeListener() != null){
                        card.getOnSwipeListener().onSwipe(card);
                    }
                }
            }
            mBaseAdapter.notifyDataSetChanged();

        }
    };





    private Animator createAnimatorForView(final View view) {
        final ViewGroup.LayoutParams lp = view.getLayoutParams();
        final int originalHeight = view.getHeight();

        ValueAnimator animator = ValueAnimator.ofInt(originalHeight, 0);
        animator.addListener(new AnimatorListenerAdapter() {

            @Override
            public void onAnimationEnd(final Animator animator) {
                lp.height = 0;
                view.setLayoutParams(lp);
            }
        });

        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(final ValueAnimator valueAnimator) {
                lp.height = (Integer) valueAnimator.getAnimatedValue();
                view.setLayoutParams(lp);
            }
        });

        return animator;
    }


    //--------------------------------------------------------------------------
    // Getters and setters
    //--------------------------------------------------------------------------

    public boolean isDismissRight() {
        return mDismissRight;
    }

    public void setDismissRight(boolean dismissRight) {
        mDismissRight = dismissRight;
    }

    public int getAnimationTime() {
        return mAnimationTime;
    }
}
