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
import android.widget.ArrayAdapter;

import java.util.List;

import it.gmariotti.cardslib.library.R;
import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.view.CardView;
import it.gmariotti.cardslib.library.view.listener.UndoBarController;

/**
 * Base Array Adapter
 *
 * @author Gabriele Mariotti (gabri.mariotti@gmail.com)
 */
public abstract class BaseCardArrayAdapter extends ArrayAdapter<Card> {

    /**
     * Current context
     */
    protected Context mContext;

    /**
     * Default layout used for each row
     */
    protected int mRowLayoutId = R.layout.list_card_layout;

    /**
     * Used to set the viewTypeCount
     */
    protected int innerviewTypeCount=1;

    /**
     * Class to define undobar ui elements
     */
    protected UndoBarController.UndoBarUIElements mUndoBarUIElements;

    // -------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------

    /**
     * Constructor
     *
     * @param context The current context.
     * @param cards   The cards to represent in the ListView.
     */
    public BaseCardArrayAdapter(Context context, List<Card> cards) {
        super(context, 0, cards);
        mContext = context;
    }

    // -------------------------------------------------------------
    // Views
    // -------------------------------------------------------------

    @Override
    public int getViewTypeCount() {
        return innerviewTypeCount;
    }

    @Override
    public int getItemViewType(int position) {
        Card card = (Card) getItem(position);
        return card.getType();
    }

    @Override
    public boolean isEnabled(int position) {
        //Disable card if it is not clickable or longClickable
        Card card = (Card) getItem(position);
        if (card.isClickable() || card.isLongClickable())
            return true;
        else
            return false;
    }

    /**
     * This method is used in with multichoice
     * @param mCard
     * @param mCardView
     */
    protected void setupMultichoice(View view,Card mCard,CardView mCardView,long position){
        //empty
    }

    // -------------------------------------------------------------
    //  Getters and Setters
    // -------------------------------------------------------------

    /**
     * Returns current context
     *
     * @return current context
     */
    public Context getContext() {
        return mContext;
    }

    /**
     * Sets layout resource ID used by rows
     *
     * @param rowLayoutId layout resource id
     */
    public void setRowLayoutId(int rowLayoutId) {
        this.mRowLayoutId = rowLayoutId;
    }

    /**
     * Sets the viewTypeCount inside the adapter.
     * It is very important in a adapter with different inner layouts
     *
     * @param viewTypeCount
     */
    public void setInnerViewTypeCount(int viewTypeCount) {
        this.innerviewTypeCount = viewTypeCount;
    }


    /**
     * Set the undobar ui elements
     *
     * @param undoBarUIElements
     */
    public void setUndoBarUIElements(UndoBarController.UndoBarUIElements undoBarUIElements) {
        mUndoBarUIElements = undoBarUIElements;
    }
}
