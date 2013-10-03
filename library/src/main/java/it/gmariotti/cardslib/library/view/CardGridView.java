package it.gmariotti.cardslib.library.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.ArrayList;

import it.gmariotti.cardslib.library.R;
import it.gmariotti.cardslib.library.internal.Card;

public class CardGridView extends RelativeLayout {
    /**
     * Disables stretching.
     */
    public static final int NO_STRETCH = 0;
    /**
     * Stretches the spacing between columns.
     */
    public static final int STRETCH_SPACING = 1;
    /**
     * Stretches columns.
     */
    public static final int STRETCH_COLUMN_WIDTH = 2;
    /**
     * Creates as many columns as can fit on screen.
     */
    public static final int AUTO_FIT = -1;
    protected static String TAG = "CardGridView";
    //StyledAttributes
    private int mNumColumns;
    private int mHorizontalSpacing;
    private int mVerticalSpacing;
    private int mStretchMode;
    private int mColumnWidth;
    private int mRequestedColumnWidth;
    private int mRequestedNumColumns;
    private int mRequestedHorizontalSpacing;
    private ArrayList<CardView> mViewList;
    private ArrayList<Integer> mHeightCalculation;
    private int[][] mIdStorage;
    private boolean mMeasured = false;
    private Context mContext;

    public CardGridView(Context context) {
        super(context);
        init(context, null, 0);
    }

    public CardGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public CardGridView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs, defStyle);
    }

    //--------------------------------------------------------------------------
    // Init
    //--------------------------------------------------------------------------

    /**
     * Initialize
     *
     * @param context
     * @param attrs
     * @param defStyle
     */
    protected void init(Context context, AttributeSet attrs, int defStyle) {
        //Init attrs
        initAttrs(attrs, defStyle);

        mContext = context;
    }

    /**
     * Init custom attrs.
     *
     * @param attrs
     * @param defStyle
     */
    protected void initAttrs(AttributeSet attrs, int defStyle) {

        TypedArray a = getContext().getTheme().obtainStyledAttributes(
                attrs, R.styleable.card_options, defStyle, defStyle);

        try {
            int numCols = a.getInt(R.styleable.card_options_num_of_columns, -1);
            setNumColumns(numCols);

            int hSpace = a.getDimensionPixelOffset(R.styleable.card_options_horizontal_spacing, 0);
            setHorizontalSpacing(hSpace);

            int vSpace = a.getDimensionPixelOffset(R.styleable.card_options_vertical_spacing, 0);
            setVerticalSpacing(vSpace);

            int index = a.getInt(R.styleable.card_options_stretch_mode, STRETCH_SPACING);
            if (index >= 0) {
                setStretchMode(index);
            }

            int colWidth = a.getDimensionPixelOffset(R.styleable.card_options_column_width, -1);
            if (colWidth > 0) {
                setColumnWidth(colWidth);
            }
        } finally {
            a.recycle();
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // PUBLIC METHODS
    ////////////////////////////////////////////////////////////////////////////////////////////////

    public CardGridView addCard(Card card) {
        CardView view = new CardView(mContext);
        LinearLayout.LayoutParams lay = new LinearLayout.LayoutParams(getColumnWidth(),
                ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(lay);
        view.setCard(card);

        if (mViewList == null) {
            mViewList = new ArrayList<CardView>();
        }
        mViewList.add(view);

        return this;
    }

    public void commitCards() {
        //This is a pretty ugly hack. We need the data from onMeasure() before adding the cards to
        //the layout. onMeasure() is called after onStart() so we're waiting for onMeasure() to finish.
        final View v = this;

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    if (mMeasured) {
                        v.post(new Runnable() { //Run it on the UI Thread
                            @Override
                            public void run() {
                                addCardsToView();
                                requestLayout();
                                invalidate(); //Force redraw with the added cards
                            }
                        });
                        break;
                    } else {
                        try {
                            Thread.sleep(200); //Try again later
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }).start();
    }

    public int getHorizontalSpacing() {
        return mHorizontalSpacing;
    }

    public void setHorizontalSpacing(int horizontalSpacing) {
        if (horizontalSpacing != mRequestedHorizontalSpacing) {
            mRequestedHorizontalSpacing = horizontalSpacing;
            requestLayout();
            invalidate();
        }
    }

    public int getRequestedHorizontalSpacing() {
        return mRequestedHorizontalSpacing;
    }

    public int getVerticalSpacing() {
        return mVerticalSpacing;
    }

    public void setVerticalSpacing(int verticalSpacing) {
        if (verticalSpacing != mVerticalSpacing) {
            mVerticalSpacing = verticalSpacing;
            requestLayout();
            invalidate();
        }
    }

    public int getStretchMode() {
        return mStretchMode;
    }

    public void setStretchMode(int stretchMode) {
        if (stretchMode != mStretchMode) {
            mStretchMode = stretchMode;
            requestLayout();
            invalidate();
        }
    }

    public int getColumnWidth() {
        return mColumnWidth;
    }

    public void setColumnWidth(int columnWidth) {
        if (columnWidth != mRequestedColumnWidth) {
            mRequestedColumnWidth = columnWidth;
            requestLayout();
            invalidate();
        }
    }

    public int getRequestedColumnWidth() {
        return mRequestedColumnWidth;
    }

    public int getNumColumns() {
        return mNumColumns;
    }

    public void setNumColumns(int numColumns) {
        if (numColumns != mRequestedNumColumns) {
            mRequestedNumColumns = numColumns;
            requestLayout();
            invalidate();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int paddingLeft = this.getPaddingLeft();
        int paddingRight = this.getPaddingRight();
        int paddingTop = this.getPaddingTop();
        int paddingBottom = this.getPaddingBottom();

        widthSize += paddingLeft + paddingRight + getVerticalScrollbarWidth();

        int childWidth = widthSize - paddingLeft - paddingRight;
        boolean didNotInitiallyFit = determineColumns(childWidth);

        if (heightMode == MeasureSpec.UNSPECIFIED) {
            mHeightCalculation = new ArrayList<Integer>();
            for (int i = 0; i < mNumColumns; i++) {
                mHeightCalculation.add(0);
            }

            int height = 0;
            int row = 0;
            View child = null;
            for (int i = 0; i < getChildCount(); i++) {
                child = getChildAt(i);
                if (child != null) {
                    row = i % mNumColumns;
                    height = child.getMeasuredHeight();
                    height += mHeightCalculation.get(row);
                    height += getVerticalSpacing();
                    mHeightCalculation.set(row, height);
                }
            }

            heightSize = getMaxRowHeight() + paddingBottom + paddingTop +
                    getVerticalFadingEdgeLength() * 2;
        }

        if (widthMode == MeasureSpec.AT_MOST && mRequestedNumColumns != AUTO_FIT) {
            int ourSize = (mRequestedNumColumns * mColumnWidth)
                    + ((mRequestedNumColumns - 1) * mHorizontalSpacing)
                    + paddingLeft + paddingRight;
            if (ourSize > widthSize || didNotInitiallyFit) {
                widthSize |= MEASURED_STATE_TOO_SMALL;
            }
        }

        setMeasuredDimension(widthSize, heightSize);

        super.onMeasure(getMeasuredWidthAndState(), getMeasuredHeightAndState());
        mMeasured = true;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // OVERWRITTEN METHODS
    ////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Checks if the space is enough for the desired views
     *
     * @param availableSpace the available space for the view
     * @return true if the space was enough else false
     */
    private boolean determineColumns(int availableSpace) {
        final int requestedHorizontalSpacing = mRequestedHorizontalSpacing;
        final int stretchMode = mStretchMode;
        int requestedColumnWidth = mRequestedColumnWidth;
        boolean didNotInitiallyFit = false;
        if ((requestedColumnWidth + requestedHorizontalSpacing * 2) >= availableSpace) { //Fix for too big layouts
            requestedColumnWidth = availableSpace - requestedHorizontalSpacing * 2;
        }

        if (mRequestedNumColumns == AUTO_FIT) {
            if (requestedColumnWidth > 0) {
                mNumColumns = (availableSpace + requestedHorizontalSpacing * 2) /
                        (requestedColumnWidth + requestedHorizontalSpacing * 2);
                mRequestedNumColumns = mNumColumns;
            } else {
                // Just make up a number if we don't have enough info
                mNumColumns = 2;
                mRequestedNumColumns = mNumColumns;
            }
        } else {
            // We picked the columns
            mNumColumns = mRequestedNumColumns;
        }

        if (mNumColumns <= 0) {
            mNumColumns = 1;
        }

        switch (stretchMode) {
            case NO_STRETCH:
                mColumnWidth = requestedColumnWidth;
                mHorizontalSpacing = requestedHorizontalSpacing;
                break;
            default:
                int spaceLeftOver = availableSpace - (mNumColumns * requestedColumnWidth) -
                        (mNumColumns * (requestedHorizontalSpacing * 2));

                if (spaceLeftOver < 0) {
                    didNotInitiallyFit = true;
                }

                switch (stretchMode) {
                    case STRETCH_COLUMN_WIDTH:
                        // Stretch the columns
                        mColumnWidth = requestedColumnWidth + spaceLeftOver / mNumColumns;
                        mHorizontalSpacing = requestedHorizontalSpacing;
                        break;

                    case STRETCH_SPACING:
                        // Stretch the spacing between columns
                        mColumnWidth = requestedColumnWidth;
                        if (mNumColumns > 1) {
                            mHorizontalSpacing = requestedHorizontalSpacing +
                                    spaceLeftOver / mNumColumns / 2;
                        } else {
                            mHorizontalSpacing = requestedHorizontalSpacing + spaceLeftOver / 2;
                        }
                        break;
                }

                break;
        }

        return didNotInitiallyFit;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // PRIVATE METHODS
    ////////////////////////////////////////////////////////////////////////////////////////////////

    private void addCardsToView() {
        //Layout position
        mIdStorage = new int[mNumColumns][mViewList.size()];

        LayoutParams lay;
        for (int i = 0; i < mViewList.size(); i++) {
            final LinearLayout card = mViewList.get(i);
            int id = i + 1 + 100;
            int count = i + 1;
            int totalCardCount = i; //Total Card Count before adding the card

            ViewGroup parent = (ViewGroup) card.getParent();
            if (parent != null) {
                parent.removeView(card);
            }

            card.setId(id);

            lay = new LayoutParams(getColumnWidth(),
                    ViewGroup.LayoutParams.WRAP_CONTENT);

            int marginHorizontal = getHorizontalSpacing();
            int marginVertical = getVerticalSpacing();
            lay.setMargins(marginHorizontal, marginVertical, marginHorizontal, marginVertical);

            int row = totalCardCount % mNumColumns;

            int col = (int) Math.ceil(((double) count) / ((double) mNumColumns)) - 1;
            mIdStorage[row][col] = id;

            if (totalCardCount != 0) {
                if (count <= mNumColumns) { //Add the first cards next to each other
                    int prevId = mIdStorage[row - 1][col];
                    lay.addRule(RIGHT_OF, prevId);
                } else {
                    int prevId = mIdStorage[row][col - 1];
                    lay.addRule(BELOW, prevId);
                    if (row != 0) {
                        prevId = mIdStorage[row - 1][col];
                        lay.addRule(RIGHT_OF, prevId);
                    }
                }
            } else {
                lay.addRule(ALIGN_PARENT_START, TRUE);
            }

            this.addView(card, lay);
        }
    }

    private int getMaxRowHeight() {
        if (mHeightCalculation == null) {
            return 0;
        }
        int toRet = mHeightCalculation.get(0);
        for (int i = 0; i < mHeightCalculation.size(); i++) {
            if (toRet < mHeightCalculation.get(i)) {
                toRet = mHeightCalculation.get(i);
            }
        }
        toRet += getVerticalSpacing() * 2;
        return toRet;
    }
}
