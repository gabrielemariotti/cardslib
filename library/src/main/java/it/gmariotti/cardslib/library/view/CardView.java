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

package it.gmariotti.cardslib.library.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
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
import it.gmariotti.cardslib.library.view.component.CardHeaderView;
import it.gmariotti.cardslib.library.view.component.CardThumbnailView;
import it.gmariotti.cardslib.library.view.listener.SwipeDismissViewTouchListener;

/**
 * Card view
 * </p>
 * Use an XML layout file to use it.
 * <pre><code>
 *  <it.gmariotti.cardslib.library.view.CardView
 *     android:id="@+id/carddemo_example_card3"
 *     android:layout_width="match_parent"
 *     android:layout_height="wrap_content"
 *     android:layout_marginLeft="12dp"
 *     android:layout_marginRight="12dp"
 *     android:layout_marginTop="12dp"/>
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
     *
     */
    protected CardThumbnail mCardThumbnail;

    /**
     *
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

        buildUI();
    }




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

                //Set recycle value (very important in a ListView)
                mInternalHeaderLayout.setRecycle(isRecycle());
                //Add Header View
                mInternalHeaderLayout.addCardHeader(mCardHeader);

                //Config ExpandLayout and its animation
                if (mInternalExpandLayout !=null && mCardHeader.isButtonExpandVisible() ){

                    //Create the expand/collapse animator
                    mInternalExpandLayout.getViewTreeObserver().addOnPreDrawListener(
                            new ViewTreeObserver.OnPreDrawListener() {

                                @Override
                                public boolean onPreDraw() {
                                    mInternalExpandLayout.getViewTreeObserver().removeOnPreDrawListener(this);
                                    mInternalExpandLayout.setVisibility(View.GONE);

                                    final int widthSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
                                    final int heightSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
                                    mInternalExpandLayout.measure(widthSpec, heightSpec);

                                    mExpandAnimator = createSlideAnimator(0, mInternalExpandLayout.getMeasuredHeight());
                                    return true;
                                }
                            });
                }

                //Setup action and callback
                setupExpandCollapseAction();
            }
        }else{
            //No header. Hide layouts
            if (mInternalHeaderLayout !=null){
                mInternalHeaderLayout.setVisibility(GONE);
                mInternalExpandLayout.setVisibility(View.GONE);
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
            if (!isRecycle()){
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
                mInternalThumbnailLayout.setRecycle(isRecycle());
                mInternalThumbnailLayout.addCardThumbnail(mCardThumbnail);
            }else{
                mInternalThumbnailLayout.setVisibility(GONE);
            }
        }
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
        }

        //OnClick listeners and partial listener
        if (mCard.isClickable()){
            //Set the onClickListener
            if (mCard.getOnClickListener() != null) {
                this.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
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
        }else{
            this.setClickable(false);
        }

        //LongClick listener
        if(mCard.isLongClickable()){
            this.setOnLongClickListener(new OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    return mCard.getOnLongClickListener().onLongClick(mCard,v);
                }
            });

        }
    }


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


    /**
     * Add ClickListener to expand and collapse hidden view
     */
    protected void setupExpandCollapseAction() {
        if (mInternalExpandLayout!=null){
            mInternalExpandLayout.setVisibility(View.GONE);
            if (mCardHeader!=null){
                if (mCardHeader.isButtonExpandVisible())
                    mInternalHeaderLayout.setOnClickExpandCollapseActionListener(new TitleViewOnClickListener(mInternalExpandLayout,mCard));
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
            if (!isRecycle()){
                mInternalExpandInnerView=mCardExpand.getInnerView(getContext(),(ViewGroup) mInternalExpandLayout);
            }else{
                //View can be recycled.
                //Only setup Inner Elements
                if (mCardExpand.getInnerLayout()>-1)
                    mCardExpand.setupInnerViewElements((ViewGroup)mInternalExpandLayout,mInternalExpandInnerView);
            }
        }
    }

    /**
     * Listener to expand/collapse hidden Expand Layout
     * It starts animation
     */
    protected class TitleViewOnClickListener implements View.OnClickListener {

        private View mContentParent;
        private Card mCard;

        private TitleViewOnClickListener(View contentParent,Card card) {
            this.mContentParent = contentParent;
            this.mCard=card;
        }

        @Override
        public void onClick(View view) {
            boolean isVisible = mContentParent.getVisibility() == View.VISIBLE;
            if (isVisible) {
                animateCollapsing();
                view.setSelected(false);
            } else {
                animateExpanding();
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

    /**
     * Refreshes the card content (except layouts)
     *
     * @param card
     */
    public void refreshCard(Card card) {
        mIsRecycle=true;
        setCard(card);
        mIsRecycle=false;
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
     *
     * @return listener
     */
    public OnExpandListAnimatorListener getOnExpandListAnimatorListener() {
        return mOnExpandListAnimatorListener;
    }

    /**
     * Sets the listener invoked when expand/collapse animation starts
     *
     * @param onExpandListAnimatorListener listener
     */
    public void setOnExpandListAnimatorListener(OnExpandListAnimatorListener onExpandListAnimatorListener) {
        this.mOnExpandListAnimatorListener = onExpandListAnimatorListener;
    }


    // -------------------------------------------------------------
    //  Getter and Setter
    // -------------------------------------------------------------

    public View getmInternalExpandLayout() {
        return mInternalExpandLayout;
    }


}
