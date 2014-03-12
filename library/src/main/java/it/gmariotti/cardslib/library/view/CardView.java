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
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import java.util.HashMap;

import it.gmariotti.cardslib.library.R;
import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardExpand;
import it.gmariotti.cardslib.library.internal.CardHeader;
import it.gmariotti.cardslib.library.internal.CardThumbnail;
import it.gmariotti.cardslib.library.internal.ViewToClickToExpand;
import it.gmariotti.cardslib.library.view.component.CardHeaderView;
import it.gmariotti.cardslib.library.view.component.CardThumbnailView;
import it.gmariotti.cardslib.library.view.listener.SwipeDismissViewTouchListener;

/**
 * Card view
 * </p>
 * Use an XML layout file to display it.
 * </p>
 * First, you need an XML layout that will display the Card.
 * <pre><code>
 *  <it.gmariotti.cardslib.library.view.CardView
 *     android:id="@+id/carddemo_example_card3"
 *     android:layout_width="match_parent"
 *     android:layout_height="wrap_content"
 *     android:layout_marginLeft="12dp"
 *     android:layout_marginRight="12dp"
 *     android:layout_marginTop="12dp"/>
 * </code></pre>
 * Then create a model:
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
 * Last get a reference to the `CardView` from your code, and set your `Card.
 * <pre><code>
 *     //Set card in the cardView
 *     CardView cardView = (CardView) getActivity().findViewById(R.id.carddemo);
 *
 *     cardView.setCard(card);
 * </code></pre>
 * You can easily build your layout.
 * </p>
 * The quickest way to start with this would be to copy one of this files and create your layout.
 * Then you can inflate your layout in the `CardView` using the attr: `card:card_layout_resourceID="@layout/my_layout`
 *  Example:
 * <pre><code>
 *      <it.gmariotti.cardslib.library.view.CardView
 *       android:id="@+id/carddemo_thumb_url"
 *        android:layout_width="match_parent"
 *        android:layout_height="wrap_content"
 *        android:layout_marginLeft="12dp"
 *        android:layout_marginRight="12dp"
 *        card:card_layout_resourceID="@layout/card_thumbnail_layout"
 *        android:layout_marginTop="12dp"/>
 * </code></pre>
 * </p>
 * @author Gabriele Mariotti (gabri.mariotti@gmail.com)
 */
public class CardView extends BaseCardView {

    //--------------------------------------------------------------------------
    //
    //--------------------------------------------------------------------------

    /**
     * {@link CardHeader} model
     */
    protected CardHeader mCardHeader;

    /**
     * {@link CardThumbnail} model
     */
    protected CardThumbnail mCardThumbnail;

    /**
     * {@link CardExpand} model
     */
    protected CardExpand mCardExpand;


    //--------------------------------------------------------------------------
    // Layout
    //--------------------------------------------------------------------------


    /**
     *  Main Layout
     */
    protected View mInternalMainCardLayout;

    /**
     *  Content Layout
     */
    protected View mInternalContentLayout;

    /**
     * Inner View.
     */
    protected View mInternalInnerView;

    /**
     *  Hidden layout used by expand/collapse action
     */
    protected View mInternalExpandLayout;

    /**
     * Expand Inner view
     */
    protected View mInternalExpandInnerView;


    /** Animator to expand/collapse */
    protected Animator mExpandAnimator;

    /**
     * Listener invoked when Expand Animator starts
     * It is used internally
     */
    protected OnExpandListAnimatorListener mOnExpandListAnimatorListener;

    //--------------------------------------------------------------------------
    // Constructor
    //--------------------------------------------------------------------------

    public CardView(Context context) {
        super(context);
    }

    public CardView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CardView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    //--------------------------------------------------------------------------
    // Init
    //--------------------------------------------------------------------------


    /**
     * Init custom attrs.
     *
     * @param attrs
     * @param defStyle
     */
    protected void initAttrs(AttributeSet attrs, int defStyle) {

        card_layout_resourceID = R.layout.card_layout;

        TypedArray a = getContext().getTheme().obtainStyledAttributes(
                attrs, R.styleable.card_options, defStyle, defStyle);

        try {
            card_layout_resourceID = a.getResourceId(R.styleable.card_options_card_layout_resourceID, this.card_layout_resourceID);
        } finally {
            a.recycle();
        }
    }

    //--------------------------------------------------------------------------
    // Card
    //--------------------------------------------------------------------------

    /**
     * Add a {@link Card}.
     * It is very important to set all values and all components before launch this method.
     *
     * @param card {@link Card} model
     */
    @Override
    public void setCard(Card card){

        super.setCard(card);
        if (card!=null){
            mCardHeader=card.getCardHeader();
            mCardThumbnail=card.getCardThumbnail();
            mCardExpand=card.getCardExpand();
        }

        //Retrieve all IDs
        if (!isRecycle()){
            retrieveLayoutIDs();
        }

        //Build UI
        buildUI();
    }

    /**
     * Refreshes the card content (it doesn't inflate layouts again)
     *
     * @param card
     */
    public void refreshCard(Card card) {
        mIsRecycle=true;
        setCard(card);
        mIsRecycle=false;
    }

    /**
     * Refreshes the card content and replaces the inner layout elements (it inflates layouts again!)
     *
     * @param card
     */
    public void replaceCard(Card card) {
        mForceReplaceInnerLayout=true;
        refreshCard(card);
        mForceReplaceInnerLayout=false;
    }

    //--------------------------------------------------------------------------
    // Setup methods
    //--------------------------------------------------------------------------

    @Override
    protected void buildUI() {

        super.buildUI();

        mCard.setCardView(this);

        //Setup Header view
        setupHeaderView();

        //Setup Main View
        setupMainView();

        //setup Thumbnail
        setupThumbnailView();

        //Setup Expand View
        setupExpandView();

        //Setup Listeners
        setupListeners();

        //Setup Expand Action
        setupExpandAction();

        //Setup Drawable Resources
        setupDrawableResources();
    }


    /**
     * Retrieve all Layouts IDs
     */
    @Override
    protected void retrieveLayoutIDs(){

        super.retrieveLayoutIDs();

        //Main Layout
        mInternalMainCardLayout = (View) findViewById(R.id.card_main_layout);

        //Get HeaderLayout
        mInternalHeaderLayout = (CardHeaderView) findViewById(R.id.card_header_layout);

        //Get ExpandHiddenView
        mInternalExpandLayout = (View) findViewById(R.id.card_content_expand_layout);

        //Get ContentLayout
        mInternalContentLayout = (View) findViewById(R.id.card_main_content_layout);

        //Get ThumbnailLayout
        mInternalThumbnailLayout = (CardThumbnailView) findViewById(R.id.card_thumbnail_layout);
    }

    /**
     * Setup Header View
     */
    protected void setupHeaderView(){

        if (mCardHeader!=null){

            if (mInternalHeaderLayout !=null){
                mInternalHeaderLayout.setVisibility(VISIBLE);

                //Set recycle value (very important in a ListView)
                mInternalHeaderLayout.setRecycle(isRecycle());
                mInternalHeaderLayout.setForceReplaceInnerLayout(isForceReplaceInnerLayout());
                //Add Header View
                mInternalHeaderLayout.addCardHeader(mCardHeader);

            }
        }else{
            //No header. Hide layouts
            if (mInternalHeaderLayout !=null){
                mInternalHeaderLayout.setVisibility(GONE);
                //mInternalExpandLayout.setVisibility(View.GONE);

                if (isForceReplaceInnerLayout()){
                    mInternalHeaderLayout.addCardHeader(null);
                    //mInternalHeaderLayout.removeAllViews();
                }
            }
        }
    }

    /**
     * Setup the Main View
     */
    protected void setupMainView(){
        if (mInternalContentLayout !=null){

            ViewGroup mParentGroup=null;
            try{
                mParentGroup = (ViewGroup) mInternalContentLayout;
            }catch (Exception e){
                setRecycle(false);
            }

            //Check if view can be recycled
            //It can happen in a listView, and improves performances
            if (!isRecycle() || isForceReplaceInnerLayout()){

                if (isForceReplaceInnerLayout() && mInternalContentLayout!=null && mInternalInnerView!=null)
                    ((ViewGroup)mInternalContentLayout).removeView(mInternalInnerView);

                mInternalInnerView=mCard.getInnerView(getContext(), (ViewGroup) mInternalContentLayout);
            }else{
                //View can be recycled.
                //Only setup Inner Elements
                if (mCard.getInnerLayout()>-1)
                    mCard.setupInnerViewElements(mParentGroup,mInternalInnerView);
            }
        }
    }


    /**
     * Setup the Thumbnail View
     */
    protected void setupThumbnailView() {
        if (mInternalThumbnailLayout!=null){
            if (mCardThumbnail!=null){
                mInternalThumbnailLayout.setVisibility(VISIBLE);
                mInternalThumbnailLayout.setRecycle(isRecycle());
                mInternalThumbnailLayout.setForceReplaceInnerLayout(isForceReplaceInnerLayout());
                mInternalThumbnailLayout.addCardThumbnail(mCardThumbnail);
            }else{
                mInternalThumbnailLayout.setVisibility(GONE);
            }
        }
    }

    /**
     * Setup Drawable Resources
     */
    protected void setupDrawableResources() {

        //Card
        if (mCard!=null){
            if (mCard.getBackgroundResourceId()!=0){
                changeBackgroundResourceId(mCard.getBackgroundResourceId());
            }else if (mCard.getBackgroundResource()!=null){
                changeBackgroundResource(mCard.getBackgroundResource());
            }
        }
    }

    //--------------------------------------------------------------------------
    // Listeners
    //--------------------------------------------------------------------------

    protected void setupExpandAction(){

        //Config ExpandLayout and its animation
        if ( mInternalExpandLayout !=null && ( (mCardHeader!=null && mCardHeader.isButtonExpandVisible()) ||
                mCard.getViewToClickToExpand()!=null)  ){

            //Create the expand/collapse animator
            mInternalExpandLayout.getViewTreeObserver().addOnPreDrawListener(
                    new ViewTreeObserver.OnPreDrawListener() {

                        @Override
                        public boolean onPreDraw() {
                            mInternalExpandLayout.getViewTreeObserver().removeOnPreDrawListener(this);

                            View parent = (View) mInternalExpandLayout.getParent();
                            final int widthSpec = View.MeasureSpec.makeMeasureSpec(parent.getMeasuredWidth() - parent.getPaddingLeft() - parent.getPaddingRight(), View.MeasureSpec.AT_MOST);
                            final int heightSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
                            mInternalExpandLayout.measure(widthSpec, heightSpec);

                            mExpandAnimator = createSlideAnimator(0, mInternalExpandLayout.getMeasuredHeight());
                            return true;
                        }
                    });
        }

        //Setup action and callback
        setupExpandCollapseActionListener();
    }

    /**
     * Setup All listeners
     */
    @SuppressWarnings("deprecation")
    @SuppressLint("NewApi")
    protected void setupListeners(){

        //Swipe listener
        if (mCard.isSwipeable()){
            this.setOnTouchListener(new SwipeDismissViewTouchListener(this, mCard,new SwipeDismissViewTouchListener.DismissCallbacks() {
                @Override
                public boolean canDismiss(Card card) {
                    return card.isSwipeable();
                }

                @Override
                public void onDismiss(CardView cardView, Card card) {
                    final ViewGroup vg = (ViewGroup)(cardView.getParent());
                    if (vg!=null){
                        vg.removeView(cardView);
                        card.onSwipeCard();
                    }
                }
            }));
        }else{
            this.setOnTouchListener(null);
        }

        //OnClick listeners and partial listener

        //Reset Partial Listeners
        resetPartialListeners();

        if (mCard.isClickable()){
            //Set the onClickListener
            if(!mCard.isMultiChoiceEnabled()){
                if (mCard.getOnClickListener() != null) {
                    this.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (mCard.getOnClickListener()!=null)
                                mCard.getOnClickListener().onClick(mCard,v);
                        }
                    });

                    //Prevent multiple events
                    //if (!mCard.isSwipeable() && mCard.getOnSwipeListener() == null) {
                    //    this.setClickable(true);
                    //}

                }else{
                    HashMap<Integer,Card.OnCardClickListener> mMultipleOnClickListner=mCard.getMultipleOnClickListener();
                    if (mMultipleOnClickListner!=null && !mMultipleOnClickListner.isEmpty()){

                        for (int key:mMultipleOnClickListner.keySet()){
                            View viewClickable= decodeAreaOnClickListener(key);
                            final Card.OnCardClickListener mListener=mMultipleOnClickListner.get(key);
                            if (viewClickable!=null){
                                //Add listener to this view
                                viewClickable.setOnClickListener(new OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        //Callback to card listener
                                        if (mListener!=null)
                                            mListener.onClick(mCard,v);
                                    }
                                });

                                //Add Selector to this view
                                if (key > Card.CLICK_LISTENER_ALL_VIEW) {
                                    if (Build.VERSION.SDK_INT >= 16){
                                        viewClickable.setBackground(getResources().getDrawable(R.drawable.card_selector));
                                    } else {
                                        viewClickable.setBackgroundDrawable(getResources().getDrawable(R.drawable.card_selector));
                                    }
                                }
                            }
                        }
                    }else{
                        //There aren't listners
                        this.setClickable(false);
                    }
                }
            }
        }else{
            this.setClickable(false);
        }

        //LongClick listener
        if(mCard.isLongClickable()){
            this.setOnLongClickListener(new OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (mCard.getOnLongClickListener()!=null)
                        return mCard.getOnLongClickListener().onLongClick(mCard,v);
                    return false;
                }
            });
        }else{
            this.setLongClickable(false);
        }
    }

    /**
     * Reset all partial listeners
     */
    protected void resetPartialListeners() {
        View viewClickable= decodeAreaOnClickListener(Card.CLICK_LISTENER_HEADER_VIEW);
        if (viewClickable!=null)
            viewClickable.setClickable(false);

        viewClickable= decodeAreaOnClickListener(Card.CLICK_LISTENER_THUMBNAIL_VIEW);
        if (viewClickable!=null)
            viewClickable.setClickable(false);

        viewClickable= decodeAreaOnClickListener(Card.CLICK_LISTENER_CONTENT_VIEW);
        if (viewClickable!=null)
            viewClickable.setClickable(false);
    }

    /**
     *
     * @param area
     * @return
     */
    protected View decodeAreaOnClickListener(int area){

        if (area<Card.CLICK_LISTENER_ALL_VIEW && area>Card.CLICK_LISTENER_CONTENT_VIEW)
            return null;

        View view = null;

        switch (area){
            case Card.CLICK_LISTENER_ALL_VIEW :
                view=this;
                break;
            case Card.CLICK_LISTENER_HEADER_VIEW :
                view=mInternalHeaderLayout;
                break;
            case Card.CLICK_LISTENER_THUMBNAIL_VIEW:
                view=mInternalThumbnailLayout;
                break;
            case Card.CLICK_LISTENER_CONTENT_VIEW:
                view=mInternalContentLayout;
                break;
            default:
                break;
        }
        return view;
    }

    //--------------------------------------------------------------------------
    // Expandable Actions and Listeners
    //--------------------------------------------------------------------------

    /**
     * Add ClickListener to expand and collapse hidden view
     */
    protected void setupExpandCollapseActionListener() {
        if (mInternalExpandLayout != null) {
            mInternalExpandLayout.setVisibility(View.GONE);


            ViewToClickToExpand viewToClickToExpand = null;

            //ButtonExpandVisible has a priority to viewClickToExpand
            if (mCardHeader != null && mCardHeader.isButtonExpandVisible()) {

                viewToClickToExpand = ViewToClickToExpand.builder()
                        .setupView(mInternalHeaderLayout.getImageButtonExpand())
                        .highlightView(true);

            } else if (mCard.getViewToClickToExpand() != null) {

                viewToClickToExpand = mCard.getViewToClickToExpand();
            }

            if (viewToClickToExpand != null) {

                TitleViewOnClickListener titleViewOnClickListener = new TitleViewOnClickListener(mInternalExpandLayout, mCard, viewToClickToExpand.isViewToSelect());

                if (mCardHeader!=null && mCardHeader.isButtonExpandVisible() && mInternalHeaderLayout != null) {
                    mInternalHeaderLayout.setOnClickExpandCollapseActionListener(titleViewOnClickListener);
                }

                View viewToClick = viewToClickToExpand.getViewToClick();
                if (viewToClick != null) {
                    viewToClick.setOnClickListener(titleViewOnClickListener);
                }else{
                    ViewToClickToExpand.CardElementUI cardElementUI=viewToClickToExpand.getCardElementUIToClick();
                    if (cardElementUI!=null){
                        switch (cardElementUI){
                            case CARD:
                                viewToClick = this;
                                break;
                            case HEADER:
                                viewToClick = getInternalHeaderLayout();
                                break;
                            case THUMBNAIL:
                                viewToClick = getInternalThumbnailLayout();
                                break;
                            case MAIN_CONTENT:
                                viewToClick = getInternalContentLayout();
                                break;
                        }
                        if (viewToClick != null) {
                            viewToClick.setOnClickListener(titleViewOnClickListener);
                        }
                    }
                }

                if (isExpanded()) {
                    //Make layout visible and button selected
                    mInternalExpandLayout.setVisibility(View.VISIBLE);
                    if (viewToClick != null) {
                        if (viewToClickToExpand.isViewToSelect())
                            viewToClick.setSelected(true);
                    }
                } else {
                    //Make layout hidden and button not selected
                    mInternalExpandLayout.setVisibility(View.GONE);
                    if (viewToClick != null) {
                        if (viewToClickToExpand.isViewToSelect())
                            viewToClick.setSelected(false);
                    }
                }
            }

        }
    }

    /**
     * Setup Expand View
     */
    protected void setupExpandView(){
        if (mInternalExpandLayout!=null && mCardExpand!=null){

            //Check if view can be recycled
            //It can happen in a listView, and improves performances
            if (!isRecycle() || isForceReplaceInnerLayout()){

                if (isForceReplaceInnerLayout() && mInternalExpandLayout!=null && mInternalExpandInnerView!=null)
                    ((ViewGroup)mInternalExpandLayout).removeView(mInternalExpandInnerView);

                mInternalExpandInnerView=mCardExpand.getInnerView(getContext(),(ViewGroup) mInternalExpandLayout);
            }else{
                //View can be recycled.
                //Only setup Inner Elements
                if (mCardExpand.getInnerLayout()>-1)
                    mCardExpand.setupInnerViewElements((ViewGroup)mInternalExpandLayout,mInternalExpandInnerView);
            }

            ViewGroup.LayoutParams layoutParams = mInternalExpandLayout.getLayoutParams();
            layoutParams.height = LayoutParams.WRAP_CONTENT;
            mInternalExpandLayout.setLayoutParams(layoutParams);
        }
    }


    /**
     * Listener to expand/collapse hidden Expand Layout
     * It starts animation
     */
    protected class TitleViewOnClickListener implements View.OnClickListener {

        private View mContentParent;
        private Card mCard;
        private boolean viewToSelect=true;

        private TitleViewOnClickListener(View contentParent,Card card) {
            this (contentParent, card,true);
        }

        private TitleViewOnClickListener(View contentParent,Card card,boolean viewToSelect) {
            this.mContentParent = contentParent;
            this.mCard=card;
            this.viewToSelect=viewToSelect;
        }

        @Override
        public void onClick(View view) {
            boolean isVisible = mContentParent.getVisibility() == View.VISIBLE;
            if (isVisible) {
                animateCollapsing();
                if (viewToSelect)
                    view.setSelected(false);
            } else {
                animateExpanding();
                if (viewToSelect)
                    view.setSelected(true);
            }
        }

        /**
         * Expanding animator.
         */
        private void animateExpanding() {

            if (getOnExpandListAnimatorListener()!=null){
                //List Animator
                getOnExpandListAnimatorListener().onExpandStart(mCard.getCardView(), mContentParent);
            }else{
                //Std animator
                mContentParent.setVisibility(View.VISIBLE);
                mExpandAnimator.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        mCard.setExpanded(true);
                        //Callback
                        if (mCard.getOnExpandAnimatorEndListener()!=null)
                            mCard.getOnExpandAnimatorEndListener().onExpandEnd(mCard);
                    }
                });
                mExpandAnimator.start();
            }
        }

        /**
         * Collapse animator
         */
        private void animateCollapsing() {

            if (getOnExpandListAnimatorListener()!=null){
                //There is a List Animator.
                getOnExpandListAnimatorListener().onCollapseStart(mCard.getCardView(), mContentParent);
            }else{
                //Std animator
                int origHeight = mContentParent.getHeight();

                ValueAnimator animator = createSlideAnimator(origHeight, 0);
                animator.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animator) {
                    }

                    @Override
                    public void onAnimationEnd(Animator animator) {
                        mContentParent.setVisibility(View.GONE);
                        mCard.setExpanded(false);
                        //Callback
                        if (mCard.getOnCollapseAnimatorEndListener()!=null)
                            mCard.getOnCollapseAnimatorEndListener().onCollapseEnd(mCard);
                    }

                    @Override
                    public void onAnimationCancel(Animator animator) {
                    }

                    @Override
                    public void onAnimationRepeat(Animator animator) {
                    }
                });
                animator.start();
            }
        }
    }

    /**
     * Create the Slide Animator invoked when the expand/collapse button is clicked
     */
    protected ValueAnimator createSlideAnimator(int start, int end) {
        ValueAnimator animator = ValueAnimator.ofInt(start, end);

        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int value = (Integer) valueAnimator.getAnimatedValue();

                ViewGroup.LayoutParams layoutParams = mInternalExpandLayout.getLayoutParams();
                layoutParams.height = value;
                mInternalExpandLayout.setLayoutParams(layoutParams);
            }
        });
        return animator;
    }

    @Override
    protected void onSizeChanged(int xNew, int yNew, int xOld, int yOld)
    {
        super.onSizeChanged(xNew, yNew, xOld, yOld);
    }

    // -------------------------------------------------------------
    //  OnExpandListAnimator Interface and Listener
    // -------------------------------------------------------------

    /**
     * Interface to listen any callbacks when expand/collapse animation starts
     */
    public interface OnExpandListAnimatorListener {
        public void onExpandStart(CardView viewCard,View expandingLayout);
        public void onCollapseStart(CardView viewCard,View expandingLayout);
    }

    /**
     * Returns the listener invoked when expand/collpase animation starts
     * It is used internally
     *
     * @return listener
     */
    public OnExpandListAnimatorListener getOnExpandListAnimatorListener() {
        return mOnExpandListAnimatorListener;
    }

    /**
     * Sets the listener invoked when expand/collapse animation starts
     * It is used internally. Don't override it.
     *
     * @param onExpandListAnimatorListener listener
     */
    public void setOnExpandListAnimatorListener(OnExpandListAnimatorListener onExpandListAnimatorListener) {
        this.mOnExpandListAnimatorListener = onExpandListAnimatorListener;
    }

    // -------------------------------------------------------------
    //  Bitmap export
    // -------------------------------------------------------------

    /**
     * Create a {@link android.graphics.Bitmap} from CardView
     * @return
     */
    public Bitmap createBitmap(){

        if (getWidth()<=0 && getHeight()<=0){
            int spec = MeasureSpec.makeMeasureSpec( 0,MeasureSpec.UNSPECIFIED);
            measure(spec,spec);
            layout(0, 0, getMeasuredWidth(), getMeasuredHeight());
        }

        Bitmap b = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(b);
        draw(c);
        return b;
    }

    // -------------------------------------------------------------
    //  Getter and Setter
    // -------------------------------------------------------------

    /**
     * Returns the view used by Expand Layout
     *
     * @return {@link View} used by Expand Layout
     */
    public View getInternalExpandLayout() {
        return mInternalExpandLayout;
    }

    /**
     * FIXME
     * @return
     */
    public View getInternalContentLayout() {
        return mInternalContentLayout;
    }

    /**
     * FIXME
     * @return
     */
    public View getInternalInnerView() {
        return mInternalInnerView;
    }

    /**
     * Indicates if the card is expanded or collapsed
     *
     * @return <code>true</code> if the card is expanded
     */
    public boolean isExpanded() {
        if (mCard!=null){
               return mCard.isExpanded();
        }else
            return false;
    }

    /**
     * Sets the card as expanded or collapsed
     *
     * @param expanded  <code>true</code> if the card is expanded
     */
    public void setExpanded(boolean expanded) {
        if (mCard!=null){
            mCard.setExpanded(expanded);
        }
    }

    /**
     * Retrieves the InternalMainCardGlobalLayout.
     * Background style is applied here.
     *
     * @return
     */
    public View getInternalMainCardLayout() {
        return mInternalMainCardLayout;
    }

    /**
     * Changes dynamically the drawable resource to override the style of MainLayout.
     *
     * @param drawableResourceId drawable resource Id
     */
    public void changeBackgroundResourceId(int drawableResourceId) {
        if (drawableResourceId!=0){
            if (mInternalMainCardLayout!=null){
                mInternalMainCardLayout.setBackgroundResource(drawableResourceId);
            }
        }
    }

    /**
     * Changes dynamically the drawable resource to override the style of MainLayout.
     *
     * @param drawableResource drawable resource
     */
    public void changeBackgroundResource(Drawable drawableResource) {
        if (drawableResource!=null){
            if (mInternalMainCardLayout!=null){
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
                    mInternalMainCardLayout.setBackground(drawableResource);
                else
                    mInternalMainCardLayout.setBackgroundDrawable(drawableResource);
            }
        }
    }
}
