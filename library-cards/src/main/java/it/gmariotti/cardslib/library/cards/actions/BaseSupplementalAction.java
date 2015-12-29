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

package it.gmariotti.cardslib.library.cards.actions;

import android.content.Context;
import android.support.annotation.IdRes;
import android.view.View;

import it.gmariotti.cardslib.library.internal.Card;

/**
 * @author Gabriele Mariotti (gabri.mariotti@gmail.com)
 */
public abstract class BaseSupplementalAction implements SupplementalAction {

    /**
     * Context
     */
    protected Context mContext;

    /**
     * Action identifier
     */
    protected @IdRes int mActionId;

    /**
     * Action View
     */
    protected View mActionView;



    // -------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------

    public BaseSupplementalAction(Context context, @IdRes int id){
        mContext = context;
        mActionId = id;
    }

    // -------------------------------------------------------------
    // On Click Listener
    // -------------------------------------------------------------

    /**
     * Listener invoked when the action is clicked
     */
    protected OnActionClickListener mOnActionClickListener;


    /**
     * Interface to listen for any callbacks when action is clicked
     */
    public interface OnActionClickListener{
        void onClick(Card card, View view);
    }

    /**
     * Returns listener invoked when action is clicked
     * @return
     */
    public OnActionClickListener getOnActionClickListener() {
        return mOnActionClickListener;
    }

    /**
     * Sets listener invoked when action is clicked
     * If listener is <code>null</code> then action is not clickable.
     *
     * @param onActionClickListener listener
     */
    public void setOnActionClickListener(OnActionClickListener onActionClickListener) {
        mOnActionClickListener = onActionClickListener;

        if (onActionClickListener == null)
           mActionView.setClickable(true);
    }


    // -------------------------------------------------------------
    // Getters and Setters
    // -------------------------------------------------------------


    public int getActionId() {
        return mActionId;
    }

    public View getActionView() {
        return mActionView;
    }
}
