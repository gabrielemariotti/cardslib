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

package it.gmariotti.cardslib.library.internal;

import android.view.View;

/**
 * @author Gabriele Mariotti (gabri.mariotti@gmail.com)
 */
public class ViewToClickToExpand {

    /**
     *  View to Click to enable the expand/collapse action
     */
    protected View viewToClick;

    /**
     *  Indicates if the view will be selected
     */
    protected boolean viewToSelect=false;

    // -------------------------------------------------------------
    //  Constructors
    // -------------------------------------------------------------

    protected ViewToClickToExpand(){}


    public static ViewToClickToExpand builder(){
        return new ViewToClickToExpand();
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

}
