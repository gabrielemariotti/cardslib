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
            if (mCard != null && !mCard.isShadow()) {
                mInternalShadowLayout.setVisibility(GONE);
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

    public Card getCard() {
        return mCard;
    }

    public CardShadowView getInternalShadowLayout() {
        return mInternalShadowLayout;
    }

    public void setInternalShadowLayout(CardShadowView internalShadowLayout) {
        mInternalShadowLayout = internalShadowLayout;
    }

    public CardHeaderView getInternalHeaderLayout() {
        return mInternalHeaderLayout;
    }

    public void setInternalHeaderLayout(CardHeaderView internalHeaderLayout) {
        mInternalHeaderLayout = internalHeaderLayout;
    }

    public CardThumbnailView getInternalThumbnailLayout() {
        return mInternalThumbnailLayout;
    }

    public void setInternalThumbnailLayout(CardThumbnailView internalThumbnailLayout) {
        mInternalThumbnailLayout = internalThumbnailLayout;
    }

    public boolean isRecycle() {
        return mIsRecycle;
    }

    public void setRecycle(boolean isRecycle) {
        this.mIsRecycle = isRecycle;
    }
}
