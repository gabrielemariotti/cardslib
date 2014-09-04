package it.gmariotti.cardslib.library.cards.material;
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
import android.support.annotation.LayoutRes;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewStub;

import it.gmariotti.cardslib.library.cards.R;
import it.gmariotti.cardslib.library.cards.actions.BaseSupplementalAction;
import it.gmariotti.cardslib.library.cards.actions.SupplementalAction;
import it.gmariotti.cardslib.library.internal.Card;

/**
 * @author Gabriele Mariotti (gabri.mariotti@gmail.com)
 */
public abstract class MaterialCard extends Card {

    /**
     *  Supplemental Action
     */
    private SparseArray<BaseSupplementalAction> mSupplementalActions;

    /**
     * Layout id for supplemental actions
     */
    protected @LayoutRes int layout_supplemental_actions_id  = INVALID_SUPPLEMENTAL_ACTIONS_LAYOUT;

    protected static final int INVALID_SUPPLEMENTAL_ACTIONS_LAYOUT = 0;

    // -------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------

    public MaterialCard(Context context) {
        super(context);
    }

    public MaterialCard(Context context, int innerLayout) {
        super(context, innerLayout);
    }

    // -------------------------------------------------------------
    // Build
    // -------------------------------------------------------------

    /**
     * Implements this method to build the custom card
     */
    public abstract void build();


    /**
     * Build the
     * @return
     */
    protected View buildSupplementalActions() {

        if (getLayout_supplemental_actions_id() != INVALID_SUPPLEMENTAL_ACTIONS_LAYOUT) {
            ViewStub stub = (ViewStub) ((View) getCardView()).findViewById(R.id.card_supplemental_actions_vs);
            if (stub != null) {
                stub.setLayoutResource(getLayout_supplemental_actions_id());
                return stub.inflate();
            }
        }
        return null;
    }

    // -------------------------------------------------------------
    // Supplemental Action
    // -------------------------------------------------------------

    @Override
    public void setupSupplementalActions() {
        super.setupSupplementalActions();

        View actionsLayout =  buildSupplementalActions();

        if (actionsLayout!= null && mSupplementalActions != null){
            for(int i = 0, nsize = mSupplementalActions.size(); i < nsize; i++) {
                SupplementalAction action = mSupplementalActions.valueAt(i);
                action.build(this,actionsLayout);
            }
        }
    }

    /**
     *  Adds a Supplemental Action
     * @param action
     */
    public void addSupplementalAction(BaseSupplementalAction action){
        buildActionsHelper();
        mSupplementalActions.put(action.getActionId(), action);
    }

    /**
     *
     */
    private void buildActionsHelper() {
        if (mSupplementalActions == null)
            mSupplementalActions = new SparseArray<BaseSupplementalAction>();
    }

    /**
     * Sets the layout id for the supplemental actions
     * @param layout_supplemental_actions_id
     */
    public void setLayout_supplemental_actions_id(int layout_supplemental_actions_id) {
        this.layout_supplemental_actions_id = layout_supplemental_actions_id;
    }

    /**
     * Returns the layout id for the supplemental actions
     * @return
     */
    public int getLayout_supplemental_actions_id() {
        return layout_supplemental_actions_id;
    }
}
