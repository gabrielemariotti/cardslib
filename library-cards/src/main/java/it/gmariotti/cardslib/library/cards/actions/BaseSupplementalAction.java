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

/**
 * @author Gabriele Mariotti (gabri.mariotti@gmail.com)
 */
public abstract class BaseSupplementalAction implements SupplementalAction {

    protected Context mContext;

    protected @IdRes int mActionId;

    protected View mActionView;

    // -------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------

    public BaseSupplementalAction(Context context, @IdRes int id){
        mContext = context;
        mActionId = id;
    }


    public int getActionId() {
        return mActionId;
    }

    public View getActionView() {
        return mActionView;
    }
}
