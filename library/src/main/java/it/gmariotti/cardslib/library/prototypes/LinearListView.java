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

package it.gmariotti.cardslib.library.prototypes;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import it.gmariotti.cardslib.library.R;

/**
 * An extension of a linear layout that supports the divider API of Android
 * 4.0+. You can populate this layout with data that comes from a
 * {@link android.widget.ListAdapter}
 *
 * @author Gabriele Mariotti (gabri.mariotti@gmail.com)
 */
public class LinearListView extends LinearLayout {

    /**
     * Adapter
     */
    private CardWithList.LinearListAdapter mListAdapter;

    /**
     * ItemView
     */
    private View itemView;

    /**
     * Represents an invalid position. All valid positions are in the range 0 to 1 less than the
     * number of items in the current adapter.
     */
    public static final int INVALID_POSITION = -1;

    // -------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------

    public LinearListView(Context context) {
        super(context);
        initAttrs(null, 0);
    }

    public LinearListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttrs(attrs, 0);
    }

    public LinearListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initAttrs(attrs, defStyle);
    }

    // -------------------------------------------------------------
    // Init
    // -------------------------------------------------------------

    /**
     * Init custom attrs.
     *
     * @param attrs
     * @param defStyle
     */
    protected void initAttrs(AttributeSet attrs, int defStyle) {

        TypedArray a = getContext().getTheme().obtainStyledAttributes(
                attrs, /*com.android.internal.R.styleable.*/R_styleable_LinearLayout, defStyle, defStyle);

        try {
            final Drawable d = a.getDrawable(/*com.android.internal.R.styleable.*/LinearLayout_divider);
            if (d != null) {
                // If a divider is specified use its intrinsic height for divider height
                setDividerDrawable(d);
            }

        } finally {
            a.recycle();
        }

        a = getContext().getTheme().obtainStyledAttributes(
                attrs, R.styleable.card_listItem, defStyle, defStyle);
        try {
            // Use the height specified, zero being the default
            final int dividerHeight = a.getDimensionPixelSize(
                    R.styleable.card_listItem_card_list_item_dividerHeight, 0);
            if (dividerHeight != 0) {
                setDividerHeight(dividerHeight);
            }
        } finally {
            a.recycle();
        }
    }

    // -------------------------------------------------------------
    // Divider
    // -------------------------------------------------------------

    private static final int[] R_styleable_LinearLayout = new int[]{
        /* 0 */ android.R.attr.divider,
        /* 1 */ android.R.attr.measureWithLargestChild,
        /* 2 */ android.R.attr.showDividers,
        /* 3 */ android.R.attr.dividerPadding,
    };
    private static final int LinearLayout_divider = 0;

    private Drawable mDivider;
    protected int mDividerWidth;
    protected int mDividerHeight;

    /**
     * Set a drawable to be used as a divider between items.
     *
     * @param divider Drawable that will divide each item.
     * @see #setShowDividers(int)
     */
    public void setDividerDrawable(Drawable divider) {
        if (divider == mDivider) {
            return;
        }
        mDivider = divider;

        if (divider != null) {
            mDividerWidth = divider.getIntrinsicWidth();
            mDividerHeight = divider.getIntrinsicHeight();
        } else {
            mDividerWidth = 0;
            mDividerHeight = 0;
        }
        setWillNotDraw(divider == null);
        requestLayout();
    }


    @Override
    protected void measureChildWithMargins(View child, int parentWidthMeasureSpec, int widthUsed, int parentHeightMeasureSpec, int heightUsed) {
        final int index = indexOfChild(child);
        final int orientation = getOrientation();
        final LayoutParams params = (LayoutParams) child.getLayoutParams();
        if (hasDividerBeforeChildAt(index)) {
            if (orientation == VERTICAL) {
                //Account for the divider by pushing everything up
                params.topMargin = mDividerHeight;
            } else {
                //Account for the divider by pushing everything left
                params.leftMargin = mDividerWidth;
            }
        }

        final int count = getChildCount();
        if (index == count - 1) {
            if (hasDividerBeforeChildAt(count)) {
                if (orientation == VERTICAL) {
                    params.bottomMargin = mDividerHeight;
                } else {
                    params.rightMargin = mDividerWidth;
                }
            }
        }
        super.measureChildWithMargins(child, parentWidthMeasureSpec, widthUsed, parentHeightMeasureSpec, heightUsed);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (mDivider != null) {
            if (getOrientation() == VERTICAL) {
                drawDividersVertical(canvas);
            } else {
                drawDividersHorizontal(canvas);
            }
        }
        super.onDraw(canvas);
    }

    void drawDividersVertical(Canvas canvas) {
        final int count = getChildCount();
        for (int i = 0; i < count; i++) {
            final View child = getChildAt(i);

            if (child != null && child.getVisibility() != GONE) {
                if (hasDividerBeforeChildAt(i)) {
                    final LayoutParams lp = (LayoutParams) child.getLayoutParams();
                    final int top = child.getTop() - lp.topMargin/* - mDividerHeight*/;
                    drawHorizontalDivider(canvas, top);
                }
            }
        }

        if (hasDividerBeforeChildAt(count)) {
            final View child = getChildAt(count - 1);
            int bottom = 0;
            if (child == null) {
                bottom = getHeight() - getPaddingBottom() - mDividerHeight;
            } else {
                //final LayoutParams lp = (LayoutParams) child.getLayoutParams();
                bottom = child.getBottom()/* + lp.bottomMargin*/;
            }
            drawHorizontalDivider(canvas, bottom);
        }
    }

    void drawDividersHorizontal(Canvas canvas) {
        final int count = getChildCount();
        for (int i = 0; i < count; i++) {
            final View child = getChildAt(i);

            if (child != null && child.getVisibility() != GONE) {
                if (hasDividerBeforeChildAt(i)) {
                    final LayoutParams lp = (LayoutParams) child.getLayoutParams();
                    final int left = child.getLeft() - lp.leftMargin/* - mDividerWidth*/;
                    drawVerticalDivider(canvas, left);
                }
            }
        }

        if (hasDividerBeforeChildAt(count)) {
            final View child = getChildAt(count - 1);
            int right = 0;
            if (child == null) {
                right = getWidth() - getPaddingRight() - mDividerWidth;
            } else {
                //final LayoutParams lp = (LayoutParams) child.getLayoutParams();
                right = child.getRight()/* + lp.rightMargin*/;
            }
            drawVerticalDivider(canvas, right);
        }
    }

    void drawHorizontalDivider(Canvas canvas, int top) {
        mDivider.setBounds(getPaddingLeft() + getDividerPadding(), top,
                getWidth() - getPaddingRight() - getDividerPadding(), top + mDividerHeight);
        mDivider.draw(canvas);

    }

    void drawVerticalDivider(Canvas canvas, int left) {
        mDivider.setBounds(left, getPaddingTop() + getDividerPadding(),
                left + mDividerWidth, getHeight() - getPaddingBottom() - getDividerPadding());
        mDivider.draw(canvas);
    }

    /**
     * Determines where to position dividers between children.
     *
     * @param childIndex Index of child to check for preceding divider
     * @return true if there should be a divider before the child at childIndex
     * @hide Pending API consideration. Currently only used internally by the system.
     */
    protected boolean hasDividerBeforeChildAt(int childIndex) {
        if (childIndex == 0) {
            return (getShowDividers() & SHOW_DIVIDER_BEGINNING) != 0;
        } else if (childIndex == getChildCount()) {
            return (getShowDividers() & SHOW_DIVIDER_END) != 0;
        } else if ((getShowDividers() & SHOW_DIVIDER_MIDDLE) != 0) {
            boolean hasVisibleViewBefore = false;
            for (int i = childIndex - 1; i >= 0; i--) {
                if (getChildAt(i).getVisibility() != GONE) {
                    hasVisibleViewBefore = true;
                    break;
                }
            }
            return hasVisibleViewBefore;
        }
        return false;
    }

    /**
     * @return Returns the height of the divider that will be drawn between each item in the list.
     */
    public int getDividerHeight() {
        return mDividerHeight;
    }

    /**
     * Sets the height of the divider that will be drawn between each item in the list. Calling
     * this will override the intrinsic height as set by {@link #setDividerDrawable(Drawable)}
     *
     * @param height The new height of the divider in pixels.
     */
    public void setDividerHeight(int height) {
        if (getOrientation() == VERTICAL) {
            mDividerHeight = height;
        } else {
            mDividerWidth = height;
        }

        requestLayout();
    }

    @Override
    public void setOrientation(int orientation) {
        if (orientation != getOrientation()) {
            int tmp = mDividerHeight;
            mDividerHeight = mDividerWidth;
            mDividerWidth = tmp;
        }
        super.setOrientation(orientation);
    }

    // -------------------------------------------------------------
    // Adapter
    // -------------------------------------------------------------

    /**
     * Sets the adapter.
     * Sets the data behind this LinearListView.
     *
     * @param listAdapter The ListAdapter which is responsible for maintaining the data
     *                    backing this list and for producing a view to represent an
     *                    item in that data set.
     */
    public void setAdapter(CardWithList.LinearListAdapter listAdapter) {
        this.mListAdapter = listAdapter;
        setOrientation(VERTICAL);

        //Populate the list
        if (mListAdapter != null) {
            for (int i = 0; i < mListAdapter.getCount(); i++) {
                itemView = mListAdapter.getView(i, null, null);
                if (itemView != null)
                    this.addView(itemView);
            }
        }
    }

    /**
     * Returns the adapter
     *
     * @return
     */
    public CardWithList.LinearListAdapter getAdapter() {
        return mListAdapter;
    }


    /**
     * Get the position within the adapter's data set for the view, where view is a an adapter item
     * or a descendant of an adapter item.
     *
     * @param view an adapter item, or a descendant of an adapter item. This must be visible in this
     *             AdapterView at the time of the call.
     * @return the position within the adapter's data set of the view, or {@link #INVALID_POSITION}
     * if the view does not correspond to a list item (or it is not currently visible).
     */
    public int getPositionForView(View view) {
        View listItem = view;
        try {
            View v;
            while (!(v = (View) listItem.getParent()).equals(this)) {
                listItem = v;
            }
        } catch (ClassCastException e) {
            // We made it up to the window without find this list itemView
            return INVALID_POSITION;
        }

        // Search the children for the list item
        final int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            if (getChildAt(i).equals(listItem)) {
                return i;
            }
        }

        // Child not found!
        return INVALID_POSITION;
    }
}