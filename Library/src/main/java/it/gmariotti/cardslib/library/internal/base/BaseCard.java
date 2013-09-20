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

package it.gmariotti.cardslib.library.internal.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.view.CardView;

/**
 * Base Abstract Card  model
 *
 * @author Gabriele Mariotti (gabri.mariotti@gmail.com)
 */
public abstract class BaseCard implements CardUIInferface {

    /**
     * Context
     */
    protected Context mContext;

    /**
     *  The layout resource ID to use for the Inner View inside component
     */
    protected int mInnerLayout=-1;

    /**
     * Outer View
     */
    protected CardView mCardView;

    /**
     * Inner View
     */
    protected View mInnerView;

    /**
     * Parent Card
     */
    protected Card mParentCard;

    /**
     * Main Title
     */
    protected String mTitle;

    // -------------------------------------------------------------
    //  Constructors
    // -------------------------------------------------------------

    public BaseCard(Context context){
        mContext=context;
    }

    // -------------------------------------------------------------
    //  Inner and Outer View
    // -------------------------------------------------------------

    /**
     * This method returns the complete View used
     *
     * @return the complete View component
     */

    public CardView getCardView() {
        return mCardView;
    }

    /**
     * This method returns the View used inside the component.
     * This method will be called runtime by UI components.
     *
     * @param context context
     * @param parent Inner Frame
     * @return the Inner View inside component
     */
    @Override
    public View getInnerView(Context context,ViewGroup parent) {

        if (mInnerLayout>-1){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            mInnerView= inflater.inflate(mInnerLayout,parent,false);
            return mInnerView;
        }
        return null;
    }


    // -------------------------------------------------------------
    //  Getters and Setters
    // -------------------------------------------------------------

    /**
     * Set Context
     * @return the context
     */
    public Context getContext() {
        return mContext;
    }

    /**
     *
     * @param context Context
     */
    public void setContext(Context context) {
        mContext = context;
    }

    /**
     *
     * @return  The layout resource ID to use for the Inner View inside component
     */
    public int getInnerLayout() {
        return mInnerLayout;
    }

    /**
     * Setup a layout resource for inner View
     * Setting the layout resource to -1 to bypass InnerView
     *
     * @param innerLayout  The layout resource ID to use for the Inner View inside component
     */
    public void setInnerLayout(int innerLayout) {
        mInnerLayout = innerLayout;
    }


    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }


    public void setCardView(CardView cardView) {
        mCardView = cardView;
    }

    public Card getParentCard() {
        return mParentCard;
    }

    public void setParentCard(Card parentCard) {
        mParentCard = parentCard;
    }
}
