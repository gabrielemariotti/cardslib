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

package it.gmariotti.cardslib.demo.extras.stickylist;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import it.gmariotti.cardslib.library.R;
import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardArrayAdapter;
import it.gmariotti.cardslib.library.view.CardView;
import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;
import se.emilsjolander.stickylistheaders.StickyListHeadersListView;

/**
 * @author Gabriele Mariotti (gabri.mariotti@gmail.com)
 */
public class StickyCardListView extends StickyListHeadersListView implements CardView.OnExpandListAnimatorListener{

    protected static String TAG = "CardListView";

    /**
     * Stycky Card Array Adapter
     */
    protected CardArrayAdapter mAdapter;


    //--------------------------------------------------------------------------
    // Fields for expand/collapse animation
    //--------------------------------------------------------------------------

    private boolean mShouldRemoveObserver = false;

    private List<View> mViewsToDraw = new ArrayList<View>();

    private int[] mTranslate;

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

    public StickyCardListView(Context context) {
        super(context);
        init(null, 0);
    }

    public StickyCardListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public StickyCardListView(Context context, AttributeSet attrs, int defStyle) {
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
    protected void init(AttributeSet attrs, int defStyle) {

        //Init attrs
        initAttrs(attrs, defStyle);

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
    public void setAdapter(StickyListHeadersAdapter adapter) {
        if (adapter instanceof StickyCardArrayAdapter) {
            setAdapter((StickyCardArrayAdapter) adapter);
        } else {
            Log.e(TAG, "You are using a generic adapter. Pay attention: your adapter has to call cardArrayAdapter#getView method");
            super.setAdapter(adapter);
        }
    }

    /**
     * Set {@link CardArrayAdapter} and layout used by items in ListView
     *
     * @param adapter {@link CardArrayAdapter}
     */
    public void setAdapter(StickyCardArrayAdapter adapter) {
        super.setAdapter(adapter);

        //Set Layout used by items
        adapter.setRowLayoutId(list_card_layout_resourceID);

        adapter.setCardListView(this);
        mAdapter = adapter;
    }


    //--------------------------------------------------------------------------
    // Expand and Collapse animator
    //--------------------------------------------------------------------------


    @Override
    public void onExpandStart(CardView viewCard, View expandingLayout) {
        ExpandCollapseHelper.animateExpanding(expandingLayout, viewCard, this);
    }

    @Override
    public void onCollapseStart(CardView viewCard, View expandingLayout) {
        ExpandCollapseHelper.animateCollapsing(expandingLayout, viewCard, this);
    }

    /**
     * Helper to animate collapse and expand animation
     */
    private static class ExpandCollapseHelper {

        /**
         * This method expandes the view that was clicked.
         *
         * @param expandingLayout layout to expand
         * @param cardView        cardView
         * @param listView        listView
         */
        public static void animateCollapsing(final View expandingLayout, final CardView cardView, final StickyCardListView listView) {
            int origHeight = expandingLayout.getHeight();

            ValueAnimator animator = createHeightAnimator(expandingLayout, origHeight, 0);
            animator.addListener(new AnimatorListenerAdapter() {

                @Override
                public void onAnimationEnd(final Animator animator) {
                    expandingLayout.setVisibility(View.GONE);

                    cardView.setExpanded(false);//card.setExpanded(true);

                    notifyAdapter(listView);

                    Card card = cardView.getCard();
                    if (card.getOnCollapseAnimatorEndListener() != null)
                        card.getOnCollapseAnimatorEndListener().onCollapseEnd(card);
                }
            });
            animator.start();
        }

        /**
         * This method collapse the view that was clicked.
         *
         * @param expandingLayout layout to collapse
         * @param cardView        cardView
         * @param listView        listView
         */
        public static void animateExpanding(final View expandingLayout, final CardView cardView, final StickyCardListView listView) {
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
                    if (card.getOnExpandAnimatorEndListener() != null)
                        card.getOnExpandAnimatorEndListener().onExpandEnd(card);

                }
            });
            animator.start();
        }

        private static View findDirectChild(final View view, final StickyCardListView listView) {
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
        public static void notifyAdapter(StickyCardListView listView) {

            if (listView.mAdapter != null) {
                listView.mAdapter.notifyDataSetChanged();
            }
        }

    }
}
