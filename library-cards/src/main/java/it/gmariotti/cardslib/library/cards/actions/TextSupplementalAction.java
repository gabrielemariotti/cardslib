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
public class TextSupplementalAction extends BaseSupplementalAction {

    // -------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------

    public TextSupplementalAction(Context context, @IdRes int id) {
        super(context, id);
    }

    // -------------------------------------------------------------
    // Build
    // -------------------------------------------------------------


    @Override
    public View build(final Card card, View actionsLayout) {

        if (actionsLayout != null) {
            mActionView = actionsLayout.findViewById(mActionId);
            if (mActionView != null) {
                if (mOnActionClickListener != null) {
                    mActionView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            mOnActionClickListener.onClick(card, view);
                        }
                    });
                }
            }
        }
        return mActionView;
    }



}
