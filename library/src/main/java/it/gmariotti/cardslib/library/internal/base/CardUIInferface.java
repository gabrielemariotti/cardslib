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

package it.gmariotti.cardslib.library.internal.base;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

/**
 * Card Interface
 *
 * @author Gabriele Mariotti (gabri.mariotti@gmail.com)
 */
public interface CardUIInferface {

    /**
     * Implement this method to draw or inflate the Layout View of a Card or a Component Card.
     * This method will be called runtime by UI components.
     *
     * @param context  context
     * @param parent parent view
     * @return   layout view
     */
    public View getInnerView(Context context,ViewGroup parent);


    /**
     * Implement this method to setup all view elements
     *
     * @param parent  parent view
     * @param view  view
     */
    public void setupInnerViewElements(ViewGroup parent,View view);


}
