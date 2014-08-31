package it.gmariotti.cardslib.library.cards;
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

import android.content.Context;
import android.util.SparseArray;

import it.gmariotti.cardslib.library.cards.actions.BaseSupplementalAction;
import it.gmariotti.cardslib.library.cards.actions.SupplementalAction;
import it.gmariotti.cardslib.library.internal.Card;

/**
 * @author Gabriele Mariotti (gabri.mariotti@gmail.com)
 */
public class MaterialCard extends Card {

    private SparseArray<BaseSupplementalAction> mSupplementalActions;

    // -------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------

    public MaterialCard(Context context) {
        super(context);
    }

    public MaterialCard(Context context, int innerLayout) {
        super(context, innerLayout);
    }


    public void addSupplementalAction(BaseSupplementalAction action){
        buildActions();
        mSupplementalActions.put(action.getActionId(), action);
    }

    private void buildActions() {
        if (mSupplementalActions == null)
            mSupplementalActions = new SparseArray<BaseSupplementalAction>();
    }

    @Override
    public void setupSupplementalActions() {
        super.setupSupplementalActions();

        if (mSupplementalActions != null){
            for(int i = 0, nsize = mSupplementalActions.size(); i < nsize; i++) {
                SupplementalAction action = mSupplementalActions.valueAt(i);
                action.build(getCardView());
            }
        }
    }


}
