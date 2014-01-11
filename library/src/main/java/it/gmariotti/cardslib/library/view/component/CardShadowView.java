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

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import it.gmariotti.cardslib.library.R;
import it.gmariotti.cardslib.library.view.base.CardViewInterface;

/**
 * Compound View for Shadow Component.
 * </p>
 * It is built with base_shadow_layout xml file.
 * You can customize this component. See https://github.com/gabrielemariotti/cardslib/tree/master/SHADOW.md for more information.
 * </p>
 * You can customize it with your style files. Please see card.shadow_image style.
 * </p>
 * Also you can use a custom layout for Shadow Component in your xml layout.
 * <pre><code>
 *
 *    <it.gmariotti.cardslib.library.view.component.CardShadowView
 *       style="@style/card.shadow_outer_layout"
 *       android:id="@+id/card_shadow_layout"
 *       android:layout_width="match_parent"
 *       card:card_shadow_layout_resourceID="@layout/my_shadow_layout"
 *       android:layout_height="wrap_content"/>
 *
 * </code></pre>
 *
 *
 * @author Gabriele Mariotti (gabri.mariotti@gmail.com)
 */
public class CardShadowView extends FrameLayout implements CardViewInterface {

    //--------------------------------------------------------------------------
    // Custom Attrs
    //--------------------------------------------------------------------------

    /**
     * Default Layout for ShadowView
     */
    protected int card_shadow_layout_resourceID = R.layout.base_shadow_layout;

    /** Global View for this Component */
    protected View mInternalOuterView;

    //--------------------------------------------------------------------------
    // Constructors
    //--------------------------------------------------------------------------

    public CardShadowView(Context context) {
        super(context);
        init(null,0);
    }

    public CardShadowView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs,0);
    }

    public CardShadowView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs,defStyle);
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

        //Init View
        if(!isInEditMode())
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
            card_shadow_layout_resourceID= a.getResourceId(R.styleable.card_options_card_shadow_layout_resourceID, card_shadow_layout_resourceID);
        } finally {
            a.recycle();
        }
    }

    /**
     * Init view
     */
    protected void initView() {

        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mInternalOuterView = inflater.inflate(card_shadow_layout_resourceID,this,true);

     }

    //--------------------------------------------------------------------------
    // Getters and Setters
    //--------------------------------------------------------------------------

    @Override
    public View getInternalOuterView() {
        return mInternalOuterView;
    }




}
