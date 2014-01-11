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

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import it.gmariotti.cardslib.library.R;
import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.view.base.CardViewInterface;
import it.gmariotti.cardslib.library.view.component.CardHeaderView;
import it.gmariotti.cardslib.library.view.component.CardShadowView;
import it.gmariotti.cardslib.library.view.component.CardThumbnailView;

/**
 * BaseView for Card
 *
 * @author Gabriele Mariotti (gabri.mariotti@gmail.com)
 */
public class BaseCardView extends LinearLayout implements CardViewInterface {

    protected static String TAG = "BaseCardView";

    //--------------------------------------------------------------------------
    //
    //--------------------------------------------------------------------------

    /**
     * Card Model
     */
    protected Card mCard;

    /**
     * Default layout to apply to card
     */
    protected int card_layout_resourceID = R.layout.card_base_layout;

    /**
     * Global View for this Component
     */
    protected View mInternalOuterView;

    /**
     * Shadow Compound View
     */
    protected CardShadowView mInternalShadowLayout;

    /**
     * Header Compound View
     */
    protected CardHeaderView mInternalHeaderLayout;


    /**
     * Thumbnail Compound View
     */
    protected CardThumbnailView mInternalThumbnailLayout;

    /**
     * Used to recycle ui elements.
     */
    protected boolean mIsRecycle=false;

    /**
     * Used to replace inner layout elements.
     */
    protected boolean mForceReplaceInnerLayout =false;

    //--------------------------------------------------------------------------
    // Constructor
    //--------------------------------------------------------------------------

    public BaseCardView(Context context) {
        super(context);
        init(null, 0);
    }

    public BaseCardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public BaseCardView(Context context, AttributeSet attrs, int defStyle) {
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

        //Init view
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
            card_layout_resourceID = a.getResourceId(R.styleable.card_options_card_layout_resourceID, card_layout_resourceID);
        } finally {
            a.recycle();
        }
    }

    /**
     * Init View
     */
    protected void initView() {

        //Inflate outer view
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mInternalOuterView = inflater.inflate(card_layout_resourceID, this, true);
    }


    //--------------------------------------------------------------------------
    // Card
    //--------------------------------------------------------------------------

    /**
     * Sets Card Model
     * </p>
     * It is important to set all card values and all card components before launch this method.
     *
     * @param card {@link Card} model
     */
    public void setCard(Card card) {
        mCard = card;
    }

    /**
     * Builds UI
     *
     */
    protected void buildUI() {

        if (mCard == null) {
            Log.e(TAG, "No card model found. Please use setCard(card) to set all values.");
            return;
        }

        setupShadowView();
    }

    /**
     * Sets up Shadow visibility
     *
     * @return
     */
    protected void setupShadowView() {
        if (mInternalShadowLayout != null) {
            if (mCard != null) {
                if (!mCard.isShadow()) {
                    mInternalShadowLayout.setVisibility(GONE);
                } else {
                    mInternalShadowLayout.setVisibility(VISIBLE);
                }
            }
        }
    }

    /**
     * Retrieves all IDs
     */
    protected void retrieveLayoutIDs() {

        // Get Shadow Layout
        mInternalShadowLayout = (CardShadowView) findViewById(R.id.card_shadow_layout);
    }

    //--------------------------------------------------------------------------
    // Getters and Setters
    //--------------------------------------------------------------------------

    @Override
    public View getInternalOuterView() {
        return mInternalOuterView;
    }

    /**
     * Returns {@link Card} model
     *
     * @return  {@link Card} model
     */
    public Card getCard() {
        return mCard;
    }

    /**
     * Returns the view used for Shadow
     *
     * @return {@link CardShadowView}
     */
    public CardShadowView getInternalShadowLayout() {
        return mInternalShadowLayout;
    }

    /**
     * Returns the view used for Header
     *
     * @return {@link CardHeaderView}
     */
    public CardHeaderView getInternalHeaderLayout() {
        return mInternalHeaderLayout;
    }

    /**
     * Returns the view used by Thumbnail
     *
     * @return {@link CardThumbnailView}
     */
    public CardThumbnailView getInternalThumbnailLayout() {
        return mInternalThumbnailLayout;
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
     * @param isRecycle  <code>true</code> to recycle
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
     * @param forceReplaceInnerLayout  <code>true</code> to recycle
     */
    public void setForceReplaceInnerLayout(boolean forceReplaceInnerLayout) {
        this.mForceReplaceInnerLayout = forceReplaceInnerLayout;
    }
}
