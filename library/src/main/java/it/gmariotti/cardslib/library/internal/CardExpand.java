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

package it.gmariotti.cardslib.library.internal;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import it.gmariotti.cardslib.library.R;
import it.gmariotti.cardslib.library.internal.base.BaseCard;

/**
 * Card Expand model.
 * <p>
 * You can customize this component. See https://github.com/gabrielemariotti/cardslib/tree/master/doc/EXPAND.md for more information.
 * </p>
 * You can easily extend and customize this class providing your inner layout and
 * setting your values with {@link #setupInnerViewElements(android.view.ViewGroup, android.view.View)} method.
 * </p>
 * <b>Usege example:</b>
 * <pre>
 * <code>
 *  //This provide a simple (and useless) expand area
 *   CardExpand expand = new CardExpand(getContext());
 *
 *  //Set inner title in Expand Area
 *  expand.setTitle(getString(R.string.demo_expand_basetitle));
 *
 *  //Add expand to card
 *  card.addCardExpand(expand);
 * </code>
 * </pre>
 * </p>
 * <b>Custom example:</b>
 * <pre>
 * <code>
 * public class CustomExpandCard extends CardExpand {
 *
 *
 *    public CustomExpandCard(Context context) {
 *       super(context, R.layout.carddemo_standard_inner_expand);
 *    }
 *
 *    public void setupInnerViewElements(ViewGroup parent, View view) {
 *
 *         if (view == null) return;
 *
 *         //Retrieve TextView elements
 *         TextView tx1 = (TextView) view.findViewById(R.id.carddemo_expand_text1);
 *         TextView tx2 = (TextView) view.findViewById(R.id.carddemo_expand_text2);
 *         TextView tx3 = (TextView) view.findViewById(R.id.carddemo_expand_text3);
 *         TextView tx4 = (TextView) view.findViewById(R.id.carddemo_expand_text4);
 *
 *         //Set value in text views
 *         if (tx1 != null) {
 *           tx1.setText(getContext().getString(R.string.demo_expand_customtitle1));
 *         }
 *    }
 *}
 *</code>
 *</pre>
 *
 *  See this page for more information https://github.com/gabrielemariotti/cardslib/tree/master/EXPAND.md
 *
 * @author Gabriele Mariotti (gabri.mariotti@gmail.com)
 */
public class CardExpand extends BaseCard {


    // -------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------

    /**
     * Constructor with a inner base layout defined by R.layout.inner_base_expand
     *
     * @param context  context
     */
    public CardExpand(Context context) {
        this(context, R.layout.inner_base_expand);
    }

    /**
     * Constructor with resource ID for inner layout
     *
     * @param context   context
     * @param innerLayout resource ID for inner layout
     */
    public CardExpand(Context context, int innerLayout) {
        super(context);
        mInnerLayout= innerLayout;
    }

    // -------------------------------------------------------------
    //  Inner View and elements
    // -------------------------------------------------------------

    /**
     * Inflates the inner layout and adds to parent layout.
     * Then calls {@link #setupInnerViewElements(android.view.ViewGroup, android.view.View)} method
     * to setup all values.
     *
     * @param context context
     * @param parent Inner Frame
     * @return
     */
    @Override
    public View getInnerView(Context context, ViewGroup parent) {

        //Inflate the inner layout
        View view= super.getInnerView(context, parent);

        //This provides a simple implementation with a single title
        if (view!=null){

            //Add inner view to parent
            parent.addView(view);

            //Setup values
            if (mInnerLayout>-1 ){
                setupInnerViewElements(parent,view);
            }
        }
        return view;
    }

    /**
     * This method sets values to expand elements and customizes view.
     *
     * Override this method to customize your Expand View
     *
     * @param parent  Expand external Layout
     * @param view  inner-expand view
     */
    @Override
    public void setupInnerViewElements(ViewGroup parent, View view) {

        //Add simple title to expand area
        if (view!=null){
            TextView mTitleView=(TextView) view.findViewById(R.id.card_expand_inner_simple_title);
            if (mTitleView!=null)
                mTitleView.setText(mTitle);
        }
    }

}
