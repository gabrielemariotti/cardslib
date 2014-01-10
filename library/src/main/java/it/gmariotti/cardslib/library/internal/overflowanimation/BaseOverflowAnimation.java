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

package it.gmariotti.cardslib.library.internal.overflowanimation;

import android.content.Context;
import android.view.View;

import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardHeader;
import it.gmariotti.cardslib.library.view.CardView;

/**
 * Base implementation of CustomOverflowAnimator
 * This class helps to store the card and the selected value on overflow icon
 *
 * @author Gabriele Mariotti (gabri.mariotti@gmail.com)
 */
public abstract class BaseOverflowAnimation implements CardHeader.CustomOverflowAnimation{

    /**
     * Context
     */
    protected Context mContext;

    /**
     * Overflow icon state
     */
    protected boolean selected=false;

    /**
     * Card
     */
    private Card mCard;

    protected static String TAG="BaseOverflowAnimation";

    // -------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------

    public BaseOverflowAnimation(Context context){
        mContext=context;
    }

    // -------------------------------------------------------------
    // Base Animation
    // -------------------------------------------------------------

    @Override
    public void doAnimation(Card card, View imageOverflow) {

        //Store the Card
        if (card==null) return;
        mCard=card;

        //Get the selected value from the Header
        CardHeader header= card.getCardHeader();
        if (header!=null){
            selected=header.isOverflowSelected();
        }
    }

    // -------------------------------------------------------------
    // Select and deselect the overflow icon
    // -------------------------------------------------------------

    /**
     * Selects overflow icon
     */
    protected void selectOverflowIcon(){
      changeOverflowIconSelection(true);
    }

    /**
     * Deselects overflow icon
     */
    protected void deselectOverflowIcon(){
        changeOverflowIconSelection(false);
    }

    /**
     * Toggles the overflow icon
     */
    protected void toggleOverflowIcon(){

        if (mCard==null) return;
        changeOverflowIconSelection(!selected);
    }

    /**
     * Internal method to change the state of overflow icon on the view and on the model
     *
     * @param selected
     */
    protected void changeOverflowIconSelection(boolean selected){

        if (mCard==null) return;

        //Change the value on the card and inside
        CardHeader header = mCard.getCardHeader();
        if (header!=null){
            this.selected=selected;
            header.setOverflowSelected(selected);
        }

        //Change the imageButton state
        CardView cardView = mCard.getCardView();
        if (cardView!=null){
            if (cardView.getInternalHeaderLayout()!=null && cardView.getInternalHeaderLayout().getImageButtonOverflow()!=null)
                cardView.getInternalHeaderLayout().getImageButtonOverflow().setSelected(selected);
        }
    }


    // -------------------------------------------------------------
    // Getters and Setters
    // -------------------------------------------------------------

    /**
     * Return the context
     *
     * @return
     */
    protected Context getContext() {
        return mContext;
    }

    /**
     * Returns the overflow icon state
     * @return
     */
    public boolean isSelected() {
        return selected;
    }

    /**
     * Sets the overflow icon state
     * @param selected
     */
    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    /**
     * Returns the card
     * @return
     */
    public Card getCard() {
        return mCard;
    }

}
