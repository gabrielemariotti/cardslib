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

import android.content.Context;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;
import java.util.Set;

import it.gmariotti.cardslib.library.internal.multichoice.MultiChoiceAdapter;
import it.gmariotti.cardslib.library.internal.multichoice.MultiChoiceAdapterHelper;
import it.gmariotti.cardslib.library.view.CardListView;
import it.gmariotti.cardslib.library.view.CardView;

/**
 * @author Gabriele Mariotti (gabri.mariotti@gmail.com)
 */
public abstract class CardArrayMultiChoiceAdapter extends CardArrayAdapter implements MultiChoiceAdapter,ActionMode.Callback{

    private MultiChoiceAdapterHelper mHelper = new MultiChoiceAdapterHelper(this);

    // -------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------

    /**
     * Constructor
     *
     * @param context The current context.
     * @param cards   The cards to represent in the ListView.
     */
    public CardArrayMultiChoiceAdapter(Context context, List<Card> cards) {
        super(context, cards);
    }

    /**
     * Constructor
     *
     * @param savedInstanceState  saveInstanceState
     * @param context             The current context.
     * @param cards               The cards to represent in the ListView.
     */
    public CardArrayMultiChoiceAdapter(Bundle savedInstanceState,Context context, List<Card> cards) {
        this(context, cards);
        mHelper.restoreSelectionFromSavedInstanceState(savedInstanceState);
    }

    // -------------------------------------------------------------
    // Adapter
    // -------------------------------------------------------------

    @Override
    public void setCardListView(CardListView cardListView) {
        super.setCardListView(cardListView);
        mHelper.setAdapterView(cardListView);
    }

    @Override
    protected void setupMultichoice(Card mCard, CardView mCardView,long position) {
        super.setupMultichoice(mCard,mCardView,position);
        mCardView.setLongClickable(true);
        //mCardView.setFocusable(false);
    }

    public boolean onItemLongClick(View view, int position, long id){
        Card mCard = getItem(position);
        if (mCard.getOnLongClickListener()!=null)
            return mCard.getOnLongClickListener().onLongClick(mCard,view);
        return true;
    }

    @Override
    public final View getView(int position, View convertView, ViewGroup parent) {
        View viewWithoutSelection = getViewImpl(position, convertView, parent);
        return mHelper.getView(position, viewWithoutSelection);
    }

    protected View getViewImpl(int position, View convertView, ViewGroup parent) {
        return super.getView(position, convertView, parent);
    }

    // -------------------------------------------------------------
    // ActionMode
    // -------------------------------------------------------------

    protected void finishActionMode() {
        mHelper.finishActionMode();
    }

    @Override
    public void onDestroyActionMode(ActionMode mode) {
        mHelper.onDestroyActionMode();
    }

    // -------------------------------------------------------------
    // MultiChoice
    // -------------------------------------------------------------

    @Override
    public void save(Bundle outState) {
        mHelper.save(outState);
    }

    @Override
    public void setCardChecked(long position, boolean checked) {
        mHelper.setItemChecked(position, checked);
    }

    @Override
    public Set<Long> getCheckedCards() {
        return mHelper.getCheckedItems();
    }

    @Override
    public int getCheckedCardCount() {
        return mHelper.getCheckedItemCount();
    }

    @Override
    public boolean isChecked(long position) {
        return mHelper.isChecked(position);
    }

    @Override
    public boolean isCardCheckable(int position) {
        return true;
    }
}
