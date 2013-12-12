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
import android.content.res.Resources;
import android.view.ActionMode;
import android.view.Menu;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;

import java.util.List;

import it.gmariotti.cardslib.library.R;
import it.gmariotti.cardslib.library.internal.multichoice.DefaultOptionMultiChoice;
import it.gmariotti.cardslib.library.internal.multichoice.MultiChoiceAdapter;
import it.gmariotti.cardslib.library.internal.multichoice.MultiChoiceAdapterHelperBase;
import it.gmariotti.cardslib.library.internal.multichoice.OptionMultiChoice;
import it.gmariotti.cardslib.library.view.CardListView;
import it.gmariotti.cardslib.library.view.CardView;

/**
 * @author Gabriele Mariotti (gabri.mariotti@gmail.com)
 */
public abstract class CardArrayMultiChoiceAdapter extends CardArrayAdapter implements MultiChoiceAdapter, AbsListView.MultiChoiceModeListener {

    private MultiChoiceAdapterHelperBase mHelper = new MultiChoiceAdapterHelperBase(this);

    protected ActionMode actionMode;

    protected OptionMultiChoice mOptions;

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
        this(context, cards, new DefaultOptionMultiChoice());
    }

    /**
     * Constructor
     *
     * @param context The current context.
     * @param cards   The cards to represent in the ListView.
     */
    public CardArrayMultiChoiceAdapter(Context context, List<Card> cards, OptionMultiChoice options) {
        super(context, cards);
        this.mOptions = options;
        mHelper.setMultiChoiceModeListener(this);
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
    protected void setupMultichoice(View view, Card mCard, CardView mCardView, long position) {
        super.setupMultichoice(view, mCard, mCardView, position);
        mCardView.setLongClickable(true);
        mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CardView cardView = (CardView) v;
                int position = getPosition(cardView.getCard());
                mHelper.onItemClick(mCardListView, v, position, getItemId(position));
            }
        });
    }

    @Override
    public Card getItem(int position) {
        Card card = super.getItem(position);
        card.mMultiChoiceEnabled = true;
        return card;
    }

    // -------------------------------------------------------------
    // ActionMode
    // -------------------------------------------------------------

    @Override
    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
        actionMode = mode;
        onItemSelectedStateChanged(mode);
        return false;
    }


    @Override
    public void onDestroyActionMode(ActionMode mode) {
        actionMode = null;
    }


    @Override
    public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {
        onItemSelectedStateChanged(mode);
        Card card = getItem(position);
        onItemCheckedStateChanged(mode, position, id, checked, card.getCardView(), card);
    }


    protected void onItemSelectedStateChanged(ActionMode mode) {
        int count = mCardListView.getCheckedItemCount();

        if (count > 0) {
            Resources res = mCardListView.getResources();
            String mTitleSelected = res.getQuantityString(R.plurals.card_selected_items, count, count);
            mode.setTitle(mTitleSelected);
        }
    }

    protected abstract void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked, CardView cardView, Card card);

    // -------------------------------------------------------------
    // MultiChoice
    // -------------------------------------------------------------


    @Override
    public boolean isCardCheckable(int position) {
        Card card = getItem(position);
        if (card != null)
            return card.isCheckable();

        return false;
    }

    @Override
    public boolean isActionModeStarted() {
        if (actionMode != null) {
            return true;
        }
        return false;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Card mCard = getItem(position);
        if (mCard != null && mCard.getOnClickListener() != null)
            mCard.getOnClickListener().onClick(mCard, view);
    }


    @Override
    public OptionMultiChoice getOptionMultiChoice() {
        return mOptions;
    }
}
