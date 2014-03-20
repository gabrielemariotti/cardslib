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

package it.gmariotti.cardslib.library.view.component;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.PopupMenu;

import it.gmariotti.cardslib.library.R;
import it.gmariotti.cardslib.library.internal.CardHeader;
import it.gmariotti.cardslib.library.view.base.CardViewInterface;

/**
 * Compound View for Header Component.
 * It is built with base_header_layout xml file.
 * </p>
 * The base layout has two elements:
 * <ul>
 * <li><i>card_header_inner_frame</i>: frame which contains custom content.</li>
 * <li><i>card_header_button_frame</i>: frame which contains button.</li>
 * </ul>
 * </p>
 * You can populate and customize this layout using your CardHeader class.
 * See {@link CardHeader} for more info.
 * </p>
 * <b>You can use a custom layout for Header Component in your xml layout.</b>
 * <pre>
 *  <it.gmariotti.cardslib.library.view.component.CardHeaderView
 *       android:id="@+id/card_header_layout"
 *       android:layout_width="match_parent"
 *       android:layout_height="wrap_content"
 *       card:card_header_layout_resourceID="@layout/my_header_layout" />
 * </pre>
 * </p>
 *
 * @author Gabriele Mariotti (gabri.mariotti@gmail.com)
 */
public class CardHeaderView extends FrameLayout implements CardViewInterface {

    //--------------------------------------------------------------------------
    // Custom Layout
    //--------------------------------------------------------------------------
    /**
     * Header Layout
     */
    protected int card_header_layout_resourceID = R.layout.base_header_layout;

    //--------------------------------------------------------------------------
    // View
    //--------------------------------------------------------------------------
    /**
     * Compound View for this Component
     */
    protected View mInternalOuterView;

    /**
     * Inner View.
     */
    protected View mInternalInnerView;

    //--------------------------------------------------------------------------
    // Frame
    //--------------------------------------------------------------------------
    /**
     * InnerContent Frame
     */
    protected ViewGroup mFrameInner;

    /**
     * Button Frame
     */
    protected ViewGroup mFrameButton;

    //--------------------------------------------------------------------------
    // Button
    //--------------------------------------------------------------------------
    /**
     * Overflow
     */
    protected ImageButton mImageButtonOverflow;

    /**
     * Expand/Collapse button
     */
    protected ImageButton mImageButtonExpand;

    /**
     * Other Button
     */
    protected ImageButton mImageButtonOther;

    /**
     * Card Model *
     */
    protected CardHeader mCardHeader;

    /**
     * Listener invoked when expand/collapse button is clicked
     */
    protected OnClickListener mOnClickExpandCollapseActionListener;

    /**
     * Used to recycle ui elements.
     */
    protected boolean mIsRecycle = false;

    /**
     * Used to replace inner layout elements.
     */
    protected boolean mForceReplaceInnerLayout = false;

    /**
     * Popup Menu for overflow button
     */
    protected PopupMenu mPopupMenu;

    //--------------------------------------------------------------------------
    // Constructors
    //--------------------------------------------------------------------------

    public CardHeaderView(Context context) {
        super(context);
        init(null, 0);
    }

    public CardHeaderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public CardHeaderView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    //--------------------------------------------------------------------------
    // Init
    //--------------------------------------------------------------------------

    /**
     * Initializes component
     *
     * @param attrs
     * @param defStyle
     */
    protected void init(AttributeSet attrs, int defStyle) {
        //Init attrs
        initAttrs(attrs, defStyle);

        //Init View
        if (!isInEditMode())
            initView();
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
            card_header_layout_resourceID = a.getResourceId(R.styleable.card_options_card_header_layout_resourceID, card_header_layout_resourceID);
        } finally {
            a.recycle();
        }
    }

    /**
     * Inits view
     */
    protected void initView() {

        //Inflate the root view (outerView)
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mInternalOuterView = inflater.inflate(card_header_layout_resourceID, this, true);

        //Get buttons from layout
        mImageButtonExpand = (ImageButton) findViewById(R.id.card_header_button_expand);
        mImageButtonOverflow = (ImageButton) findViewById(R.id.card_header_button_overflow);
        mImageButtonOther = (ImageButton) findViewById(R.id.card_header_button_other);

        //Get frames
        mFrameInner = (FrameLayout) findViewById(R.id.card_header_inner_frame);
        mFrameButton = (FrameLayout) findViewById(R.id.card_header_button_frame);

    }

    @Override
    public View getInternalOuterView() {
        return mInternalOuterView;
    }

    /**
     * Adds a {@link CardHeader}.
     * <b>It is important to set all header values before launch this method</b>
     *
     * @param cardHeader CardHeader model
     */
    public void addCardHeader(CardHeader cardHeader) {
        //Set header
        mCardHeader = cardHeader;
        //build ui
        buildUI();
    }

    /**
     * This method builds UI.
     * If you are using standard base layout it sets up buttons and innerView.
     * If you are using your custom layout it sets your elements.
     */
    protected void buildUI() {
        if (mCardHeader == null) return;

        //Set button visibility
        setupButtons();

        //Setup InnerView
        setupInnerView();
    }

    /**
     * Sets Buttons visibility
     */
    @SuppressWarnings("deprecation")
    @SuppressLint("NewApi")
    protected void setupButtons() {

        if (mCardHeader.isButtonOverflowVisible()) {
            visibilityButtonHelper(VISIBLE, GONE, GONE);

            addPopup();
            if (mPopupMenu==null && mCardHeader.getCustomOverflowAnimation() != null) {
                addCustomOverflowAnimation();
            }

        } else {

            if (mCardHeader.isButtonExpandVisible()) {
                visibilityButtonHelper(GONE, VISIBLE, GONE);
            } else {

                if (mCardHeader.isOtherButtonVisible() && mImageButtonOther != null) {
                    visibilityButtonHelper(GONE, GONE, VISIBLE);
                    //Check if button is not null
                    if (mImageButtonOther != null) {
                        if (mCardHeader.getOtherButtonDrawable() > 0) {
                            if (Build.VERSION.SDK_INT >= 16) {
                                mImageButtonOther.setBackground(getResources().getDrawable(mCardHeader.getOtherButtonDrawable()));
                            } else {
                                mImageButtonOther.setBackgroundDrawable(getResources().getDrawable(mCardHeader.getOtherButtonDrawable()));
                            }
                        }
                        addOtherListener();
                    }
                } else {
                    visibilityButtonHelper(GONE, GONE, GONE);
                }
            }
        }
    }

    /**
     * Add Custom Overflow Animation
     */
    private void addCustomOverflowAnimation() {

        final CardHeader.CustomOverflowAnimation animation = mCardHeader.getCustomOverflowAnimation();
        if (animation != null && mImageButtonOverflow != null) {

            //Add a PopupMenu and its listener
            mImageButtonOverflow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    animation.doAnimation(mCardHeader.getParentCard(), v);
                }
            });

        } else {
            if (mImageButtonOverflow != null)
                mImageButtonOverflow.setVisibility(GONE);
        }

    }

    /**
     * Sets listener for OtherButtonAction
     */
    protected void addOtherListener() {

        if (mCardHeader.getOtherButtonClickListener() != null) {
            if (mImageButtonOther != null) {
                mImageButtonOther.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mCardHeader.getOtherButtonClickListener() != null)
                            mCardHeader.getOtherButtonClickListener().onButtonItemClick(mCardHeader.getParentCard(), v);
                    }
                });
            }
        } else {
            if (mImageButtonOther != null) {
                mImageButtonOther.setClickable(false);
            }
        }
    }

    /**
     * Sets the inner view.
     */
    protected void setupInnerView() {
        if (mFrameInner != null) {
            //Check if view can be recycled
            //It can happen in a listView to improve performances or while refreshing a card
            if (!isRecycle() || isForceReplaceInnerLayout()) {

                if (isForceReplaceInnerLayout() && mFrameInner != null && mInternalInnerView != null)
                    mFrameInner.removeView(mInternalInnerView);

                //Inflate inner view
                mInternalInnerView = mCardHeader.getInnerView(getContext(), mFrameInner);
            } else {
                //View can be recycled.
                //Only setup Inner Elements
                if (mCardHeader.getInnerLayout() > -1)
                    mCardHeader.setupInnerViewElements(mFrameInner, mInternalInnerView);
            }
        }
    }


    /**
     * Helper method to setup buttons visibility
     *
     * @param overflowButtonVisibility <code>VISIBLE</code> to make visibile this button , otherwise <code>GONE</code>
     * @param expandButtonVisibility   <code>VISIBLE</code> to make visibile this button , otherwise <code>GONE</code>
     * @param otherButtonVisibility    <code>VISIBLE</code> to make visibile this button , otherwise <code>GONE</code>
     */
    protected void visibilityButtonHelper(int overflowButtonVisibility, int expandButtonVisibility, int otherButtonVisibility) {

        if (overflowButtonVisibility == VISIBLE || overflowButtonVisibility == GONE) {
            if (mImageButtonOverflow != null)
                mImageButtonOverflow.setVisibility(overflowButtonVisibility);
        }
        if (expandButtonVisibility == VISIBLE || expandButtonVisibility == GONE) {
            if (mImageButtonExpand != null)
                mImageButtonExpand.setVisibility(expandButtonVisibility);
        }
        if (otherButtonVisibility == VISIBLE || otherButtonVisibility == GONE) {
            if (mImageButtonOther != null)
                mImageButtonOther.setVisibility(otherButtonVisibility);
        }
    }

    /**
     * Adds Popup menu
     */
    protected void addPopup() {

        //To prevent recycle
        mPopupMenu = null;

        if (mImageButtonOverflow != null) {

            // allow dynamic customization on popup menu
            boolean prepareMenu = mCardHeader.getPopupMenu() > CardHeader.NO_POPUP_MENU ? true : false;
            if (mCardHeader.getPopupMenuPrepareListener() != null) {

                //Build the popupMenu
                mPopupMenu = _buildPopupMenu();

                //Dynamic customization
                prepareMenu = mCardHeader.getPopupMenuPrepareListener().onPreparePopupMenu(mCardHeader.getParentCard(), mPopupMenu);

                //Check if the menu has visible items
                if (mPopupMenu.getMenu()==null || !mPopupMenu.getMenu().hasVisibleItems())
                    prepareMenu = false;
            }

            if (prepareMenu) {
                //Add a PopupMenu and its listener
                mImageButtonOverflow.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mPopupMenu==null){
                            //It is null if the PopupMenuPrepareListener is null
                            //PopupMenu is built inside onClick() method to avoid building the menu when it is not necessary
                            mPopupMenu = _buildPopupMenu();
                        }
                        if (mPopupMenu!=null)
                            mPopupMenu.show();
                    }
                });
            } else {
                if (mCardHeader.getCustomOverflowAnimation()==null) {
                    mImageButtonOverflow.setVisibility(GONE);
                }
            }

        } else {
            if (mImageButtonOverflow != null)
                mImageButtonOverflow.setVisibility(GONE);
        }
    }


    /**
     * Build the menu
     * @return
     */
    private PopupMenu _buildPopupMenu(){

        PopupMenu popup = new PopupMenu(getContext(), mImageButtonOverflow);
        if (mCardHeader.getPopupMenu()> CardHeader.NO_POPUP_MENU){
            MenuInflater inflater = popup.getMenuInflater();
            inflater.inflate(mCardHeader.getPopupMenu(), popup.getMenu());
        }
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (mCardHeader.getPopupMenuListener() != null) {
                    // This individual card has it unique menu
                    mCardHeader.getPopupMenuListener().onMenuItemClick(mCardHeader.getParentCard(), item);
                }
                return false;
            }
        });

        return popup;
    }

    //--------------------------------------------------------------------------
    // Getters and Setters
    //--------------------------------------------------------------------------

    /**
     * Returns Listener invoked when expand/collpse button is clicked
     *
     * @return listener
     */
    public OnClickListener getOnClickExpandCollapseActionListener() {
        return mOnClickExpandCollapseActionListener;
    }

    /**
     * Attaches Listener to expand/collapse button
     *
     * @param onClickExpandCollapseActionListener listener
     */
    public void setOnClickExpandCollapseActionListener(OnClickListener onClickExpandCollapseActionListener) {
        this.mOnClickExpandCollapseActionListener = onClickExpandCollapseActionListener;
        /*if (mImageButtonExpand != null)
            mImageButtonExpand.setOnClickListener(onClickExpandCollapseActionListener);
            */
    }

    /**
     * Indicates if view can recycle ui elements.
     *
     * @return <code>true</code> if views can recycle ui elements
     */
    public boolean isRecycle() {
        return mIsRecycle;
    }

    /**
     * Sets if view can recycle ui elements
     *
     * @param isRecycle <code>true</code> to recycle
     */
    public void setRecycle(boolean isRecycle) {
        this.mIsRecycle = isRecycle;
    }

    /**
     * Indicates if inner layout have to be replaced
     *
     * @return <code>true</code> if inner layout can be recycled
     */
    public boolean isForceReplaceInnerLayout() {
        return mForceReplaceInnerLayout;
    }

    /**
     * Sets if inner layout have to be replaced
     *
     * @param forceReplaceInnerLayout <code>true</code> to recycle
     */
    public void setForceReplaceInnerLayout(boolean forceReplaceInnerLayout) {
        this.mForceReplaceInnerLayout = forceReplaceInnerLayout;
    }

    /**
     * Returns the {@link ImageButton} used for overflow menu
     *
     * @return {@link ImageButton}
     */
    public ImageButton getImageButtonOverflow() {
        return mImageButtonOverflow;
    }

    /**
     * Returns the {@link ImageButton} used for expand/collapse
     *
     * @return {@link ImageButton}
     */
    public ImageButton getImageButtonExpand() {
        return mImageButtonExpand;
    }

    /*
     *Returns the {@link ImageButton} used for other Button
     *
     * @return {@link ImageButton}
     */
    public ImageButton getImageButtonOther() {
        return mImageButtonOther;
    }

    /**
     * Returns the Frame which contains the buttons
     *
     * @return
     */
    public ViewGroup getFrameButton() {
        return mFrameButton;
    }

}
