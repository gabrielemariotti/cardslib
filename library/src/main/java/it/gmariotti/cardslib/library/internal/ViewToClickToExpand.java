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

import android.view.View;

/**
 * Use this class to enable the expand/collapse action anywhere in your card.
 * <code>
 *     ViewToClickToExpand.builder()
 *        .setupView(myView)      //view to click
 *        .highlightView(true);   //highlight this view
 *
 * </code>
 *
 * @author Gabriele Mariotti (gabri.mariotti@gmail.com)
 */
public class ViewToClickToExpand {

    /**
     *  View to Click to enable the expand/collapse action.
     *  It has a higher priority than cardElementUIToClick.
     */
    protected View viewToClick;

    /**
     *  Indicates if the view will be selected
     */
    protected boolean viewToSelect=false;

    /**
     *  Card element UI to click to enable the expand/collapse action
     *  It has a lower priority than cardElementUIToClick.
     */
    protected CardElementUI cardElementUIToClick;

    // -------------------------------------------------------------
    //  Constructors
    // -------------------------------------------------------------

    protected ViewToClickToExpand(){}

    /**
     * Builder
     *
     * @return
     */
    public static ViewToClickToExpand builder(){
        return new ViewToClickToExpand();
    }

    // -------------------------------------------------------------
    //  Enum
    // -------------------------------------------------------------

    public enum CardElementUI{

        CARD(0),
        HEADER(1),
        THUMBNAIL(2),
        MAIN_CONTENT(3);

        int mElement;

        CardElementUI(int element){
            mElement = element;
        }

    }

    // -------------------------------------------------------------
    //  Setup
    // -------------------------------------------------------------

    /**
     * Sets the view to click to enable the expand/collpase action
     *
     * @param viewToClick  view to click
     * @return
     */
    public ViewToClickToExpand setupView(View viewToClick){
        this.viewToClick=viewToClick;
        return this;
    }

    /**
     * Indicates if the view clicked will be highlight as selected
     *
     * @param highlight
     * @return
     */
    public ViewToClickToExpand highlightView(boolean highlight){
        this.viewToSelect=highlight;
        return this;
    }

    public ViewToClickToExpand setupCardElement(CardElementUI cardElementUIToClick){
        this.cardElementUIToClick = cardElementUIToClick;
        return this;
    }

    // -------------------------------------------------------------
    //  Getters
    // -------------------------------------------------------------

    /**
     * Returns the view to Click to enable the expand/collapse action
     * @return
     */
    public View getViewToClick() {
        return viewToClick;
    }

    /**
     * Indicates if the view clicked will be highlight as selected
     * @return
     */
    public boolean isViewToSelect() {
        return viewToSelect;
    }

    /**
     * Returns the card element to click to enable the expand/collapse action
     * @return
     */
    public CardElementUI getCardElementUIToClick() {
        return cardElementUIToClick;
    }


}
