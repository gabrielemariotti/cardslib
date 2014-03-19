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

package it.gmariotti.cardslib.library.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListAdapter;
import android.widget.ListView;

import it.gmariotti.cardslib.library.R;
import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardArrayAdapter;
import it.gmariotti.cardslib.library.internal.CardCursorAdapter;
import it.gmariotti.cardslib.library.view.listener.SwipeOnScrollListener;

/**
 * Card List View.
 * It uses an {@link CardArrayAdapter} to populate items.
 * </p>
 * Usage:
 * <pre><code>
 *    <it.gmariotti.cardslib.library.view.CardListView
 *      android:layout_width="match_parent"
 *      android:layout_height="match_parent"
 *      android:id="@+id/listId"
 *      card:list_card_layout_resourceID="@layout/list_card_thumbnail_layout" /> *
 * </code></pre>
 * It provides a default layout id for each row @layout/list_card_layout
 * Use can easily customize it using card:list_card_layout_resourceID attr in your xml layout.
 * </p>
 * Use this code to populate the list view
 * <pre><code>
 * CardListView listView = (CardListView) getActivity().findViewById(R.id.listId);
 * listView.setAdapter(mCardArrayAdapter);
 * </code></pre>
 * </p>
 * Currently you have to use the same inner layout for each card in listView.
 * </p>
 * @author Gabriele Mariotti (gabri.mariotti@gmail.com)
 */
public class CardListView extends ListView implements CardView.OnExpandListAnimatorListener {

    protected static String TAG = "CardListView";

    /**
     *  Card Array Adapter
     */
    protected CardArrayAdapter mAdapter;

    /**
     * Card Cursor Adapter
     */
    protected CardCursorAdapter mCursorAdapter;

    /**
     * Custom ScrollListener to be used with a CardListView and cards with swipe action
     */
    protected SwipeOnScrollListener mOnScrollListener;


    //--------------------------------------------------------------------------
    // Custom Attrs
    //--------------------------------------------------------------------------

    /**
     * Default layout to apply to card
     */
    protected int list_card_layout_resourceID = R.layout.list_card_layout;

    //--------------------------------------------------------------------------
    // Constructors
    //--------------------------------------------------------------------------


    public CardListView(Context context) {
        super(context);
        init(null, 0);
    }

    public CardListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public CardListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    //--------------------------------------------------------------------------
    // Init
    //--------------------------------------------------------------------------

    /**
     * Initialize
     *
     * @param attrs
     * @param defStyle
     */
    protected void init(AttributeSet attrs, int defStyle){

        //Init attrs
        initAttrs(attrs,defStyle);

        //Set divider to 0dp
        setDividerHeight(0);

    }


    /**
     * Init custom attrs.
     *
     * @param attrs
     * @param defStyle
     */
    protected void initAttrs(AttributeSet attrs, int defStyle) {

        list_card_layout_resourceID = R.layout.list_card_layout;

        TypedArray a = getContext().getTheme().obtainStyledAttributes(
                attrs, R.styleable.card_options, defStyle, defStyle);

        try {
            list_card_layout_resourceID = a.getResourceId(R.styleable.card_options_list_card_layout_resourceID, this.list_card_layout_resourceID);
        } finally {
            a.recycle();
        }
    }

    //--------------------------------------------------------------------------
    // Adapter
    //--------------------------------------------------------------------------

    /**
     * Set the adapter. You can provide a {@link CardArrayAdapter}, or a {@link it.gmariotti.cardslib.library.internal.CardCursorAdapter}
     * or a generic adapter.
     * Pay attention: your generic adapter has to call {@link CardArrayAdapter#getView} method
     *
     * @param adapter
     */
    @Override
    public void setAdapter(ListAdapter adapter) {
        if (adapter instanceof CardArrayAdapter){
            setAdapter((CardArrayAdapter)adapter);
        }else if (adapter instanceof CardCursorAdapter){
            setAdapter((CardCursorAdapter)adapter);
        }else {
            Log.w(TAG,"You are using a generic adapter. Pay attention: your adapter has to call cardArrayAdapter#getView method" );
            super.setAdapter(adapter);
        }
    }

    /**
     * Set {@link CardArrayAdapter} and layout used by items in ListView
     *
     * @param adapter {@link CardArrayAdapter}
     */
    public void setAdapter(CardArrayAdapter adapter) {
        super.setAdapter(adapter);

        //Set Layout used by items
        adapter.setRowLayoutId(list_card_layout_resourceID);

        adapter.setCardListView(this);
        mAdapter=adapter;
    }

    /**
     * Set {@link CardCursorAdapter} and layout used by items in ListView
     *
     * @param adapter {@link CardCursorAdapter}
     */
    public void setAdapter(CardCursorAdapter adapter) {
        super.setAdapter(adapter);

        //Set Layout used by items
        adapter.setRowLayoutId(list_card_layout_resourceID);

        adapter.setCardListView(this);
        mCursorAdapter=adapter;
    }

    /**
     * You can use this method, if you are using external adapters.
     * Pay attention. The generic adapter#getView() method has to call the cardArrayAdapter#getView() method to work.
     *
     * @param adapter {@link ListAdapter} generic adapter
     * @param cardArrayAdapter    {@link CardArrayAdapter} cardArrayAdapter
     */
    public void setExternalAdapter(ListAdapter adapter, CardArrayAdapter cardArrayAdapter) {

        setAdapter(adapter);

        mAdapter=cardArrayAdapter;
        mAdapter.setCardListView(this);
        mAdapter.setRowLayoutId(list_card_layout_resourceID);
    }

    /**
     * You can use this method, if you are using external adapters.
     * Pay attention. The generic adapter#getView() method has to call the cardCursorAdapter#getView() method to work.
     *
     * @param adapter {@link ListAdapter} generic adapter
     * @param cardCursorAdapter    {@link CardCursorAdapter} cardArrayAdapter
     */
    public void setExternalAdapter(ListAdapter adapter, CardCursorAdapter cardCursorAdapter) {

        setAdapter(adapter);

        mCursorAdapter=cardCursorAdapter;
        mCursorAdapter.setCardListView(this);
        mCursorAdapter.setRowLayoutId(list_card_layout_resourceID);
    }

    /**
     * Returns local scroll event listener
     */
    public OnScrollListener getOnScrollListener( ) {
        return this.mOnScrollListener;
    }

    /**
     * Overrides the set on scroll listener method and registers local reference
     */
    @Override
    public void setOnScrollListener( OnScrollListener mOnScrollListener ) {
        super.setOnScrollListener( mOnScrollListener );
        if (mOnScrollListener instanceof SwipeOnScrollListener)
            this.mOnScrollListener = (SwipeOnScrollListener)mOnScrollListener;
    }

    //--------------------------------------------------------------------------
    // Expand and Collapse animator
    //--------------------------------------------------------------------------

    @Override
    public void onExpandStart(CardView viewCard,View expandingLayout) {

        boolean expandable = true;
        if (mCursorAdapter!=null){
            expandable = mCursorAdapter.onExpandStart(viewCard);
        }

        if (expandable)
            ExpandCollapseHelper.animateExpanding(expandingLayout,viewCard,this);

        if (mCursorAdapter!=null){
            mCursorAdapter.onExpandEnd(viewCard);
        }

    }

    @Override
    public void onCollapseStart(CardView viewCard,View expandingLayout) {
        boolean collapsible = true;
        if (mCursorAdapter!=null){
            collapsible = mCursorAdapter.onCollapseStart(viewCard);
        }

        if (collapsible)
            ExpandCollapseHelper.animateCollapsing(expandingLayout,viewCard,this);

        if (mCursorAdapter!=null){
            mCursorAdapter.onCollapseEnd(viewCard);
        }
    }

    /**
     * Helper to animate collapse and expand animation
     */
    private static class ExpandCollapseHelper {

        /**
         * This method expandes the view that was clicked.
         *
         * @param expandingLayout  layout to expand
         * @param cardView         cardView
         * @param listView         listView
         */
        public static void animateCollapsing(final View expandingLayout, final CardView cardView,final AbsListView listView) {
            int origHeight = expandingLayout.getHeight();

            ValueAnimator animator = createHeightAnimator(expandingLayout, origHeight, 0);
            animator.addListener(new AnimatorListenerAdapter() {

                @Override
                public void onAnimationEnd(final Animator animator) {
                    expandingLayout.setVisibility(View.GONE);

                    cardView.setExpanded(false);//card.setExpanded(true);

                    notifyAdapter(listView);

                    Card card = cardView.getCard();
                    if (card.getOnCollapseAnimatorEndListener()!=null)
                        card.getOnCollapseAnimatorEndListener().onCollapseEnd(card);
                }
            });
            animator.start();
        }

        /**
         * This method collapse the view that was clicked.
         *
         * @param expandingLayout  layout to collapse
         * @param cardView         cardView
         * @param listView         listView
         */
        public static void animateExpanding(final View expandingLayout, final CardView cardView,final AbsListView listView) {
            /* Update the layout so the extra content becomes visible.*/
            expandingLayout.setVisibility(View.VISIBLE);

            View parent = (View) expandingLayout.getParent();
            final int widthSpec = View.MeasureSpec.makeMeasureSpec(parent.getMeasuredWidth() - parent.getPaddingLeft() - parent.getPaddingRight(), View.MeasureSpec.AT_MOST);
            final int heightSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
            expandingLayout.measure(widthSpec, heightSpec);

            ValueAnimator animator = createHeightAnimator(expandingLayout, 0, expandingLayout.getMeasuredHeight());
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                final int listViewHeight = listView.getHeight();
                final int listViewBottomPadding = listView.getPaddingBottom();
                final View v = findDirectChild(expandingLayout, listView);

                @Override
                public void onAnimationUpdate(final ValueAnimator valueAnimator) {
                    final int bottom = v.getBottom();
                    if (bottom > listViewHeight) {
                        final int top = v.getTop();
                        if (top > 0) {
                            listView.smoothScrollBy(Math.min(bottom - listViewHeight + listViewBottomPadding, top), 0);
                        }
                    }
                }
            });
            animator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    cardView.setExpanded(true);//card.setExpanded(true);

                    notifyAdapter(listView);

                    Card card = cardView.getCard();
                    if (card.getOnExpandAnimatorEndListener()!=null)
                        card.getOnExpandAnimatorEndListener().onExpandEnd(card);

                }
            });
            animator.start();
        }

        private static View findDirectChild(final View view, final AbsListView listView) {
            View result = view;
            View parent = (View) result.getParent();
            while (parent != listView) {
                result = parent;
                parent = (View) result.getParent();
            }
            return result;
        }

        public static ValueAnimator createHeightAnimator(final View view, final int start, final int end) {
            ValueAnimator animator = ValueAnimator.ofInt(start, end);
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

                @Override
                public void onAnimationUpdate(final ValueAnimator valueAnimator) {
                    int value = (Integer) valueAnimator.getAnimatedValue();

                    ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
                    layoutParams.height = value;
                    view.setLayoutParams(layoutParams);
                }
            });
            return animator;
        }

        /**
         * This method notifies the adapter after setting expand value inside cards
         *
         * @param listView
         */
        public static void notifyAdapter(AbsListView listView){

            if (listView instanceof CardListView){

                CardListView cardListView = (CardListView) listView;
                if (cardListView.mAdapter!=null){
                    cardListView.mAdapter.notifyDataSetChanged();
                } else if (cardListView.mCursorAdapter!=null){
                    //cardListView.mCursorAdapter.notifyDataSetChanged();
                }
            }
        }
    }
}
