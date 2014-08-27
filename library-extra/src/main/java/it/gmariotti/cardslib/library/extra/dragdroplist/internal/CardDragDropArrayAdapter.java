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

package it.gmariotti.cardslib.library.extra.dragdroplist.internal;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.nhaarman.listviewanimations.util.Swappable;

import java.util.List;

import it.gmariotti.cardslib.library.extra.dragdroplist.view.CardListDragDropView;
import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardArrayAdapter;
import it.gmariotti.cardslib.library.view.base.CardViewWrapper;


/**
 * Array Adapter for {@link Card} model with drop and drop support.
 *
 * @author Gabriele Mariotti (gabri.mariotti@gmail.com)
 */
public class CardDragDropArrayAdapter extends CardArrayAdapter implements Swappable {

    /**
     * {@link CardListDragDropView}
     */
    protected CardListDragDropView mCardListView;

    /**
     * Flag to enable the drag and drop feature over the card
     */
    protected boolean enableDragSupportOnLongClickOnCard = true;

    // -------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------


    /**
     * Constructor
     *
     * @param context The current context.
     * @param cards   The cards to represent in the ListView.
     */
    public CardDragDropArrayAdapter(Context context, List<Card> cards) {
        super(context, cards);
    }

    // -------------------------------------------------------------
    // Methods for drag and drop support
    // -------------------------------------------------------------

    @Override
    public boolean hasStableIds() {
        return true;
    }

    private void set(final int location, final Card object) {
        remove(object);
        insert(object,location);
        notifyDataSetChanged();
    }

    @Override
    public void swapItems(final int locationOne, final int locationTwo) {
        Card temp = getItem(locationOne);
        set(locationOne, getItem(locationTwo));
        set(locationTwo, temp);
    }

    @Override
    public long getItemId(int position) {
        Card card = getItem(position);

        if (card.getId()==null)
            throw new IllegalArgumentException("Cards needs a stable id! Use card.setId()");

        return card.getId().hashCode();
    }

    /**
     * Sets SwipeAnimation on List
     *
     * @param card {@link Card}
     * @param cardView {@link it.gmariotti.cardslib.library.view.CardView}
     */
    protected final void setupSwipeableAnimation(final Card card, CardViewWrapper cardView) {
        card.setSwipeable(false);
    }

    /**
     * Overrides the default collapse/expand animation in a List
     *
     * @param cardView {@link it.gmariotti.cardslib.library.view.base.CardViewWrapper}
     */
    protected final void setupExpandCollapseListAnimation(CardViewWrapper cardView) {

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = super.getView(position, convertView, parent);

        if (enableDragSupportOnLongClickOnCard) {
            Card mCard = (Card) getItem(position);
            if (mCard != null) {
                setupLongClickForDragSupport(position, mCard);
            }
        }
        return  view;

    }

    /**
     * Enables the longClick event to drag and drop cards
     *
     * @param position
     * @param mCard
     */
    private void setupLongClickForDragSupport(int position, Card mCard) {

        CardView mCardView = mCard.getCardView();
        mCardView.setLongClickable(true);
    }

    // -------------------------------------------------------------
    //  Getters and Setters
    // -------------------------------------------------------------

    /**
     * @return {@link CardListDragDropView}
     */
    public CardListDragDropView getCardListDragDropView() {
        return mCardListView;
    }

    /**
     * Sets the {@link CardListDragDropView}
     *
     * @param cardListView cardListView
     */
    public void setCardListDragDropView(CardListDragDropView cardListView) {
        this.mCardListView = cardListView;
    }

    /**
     * Indicates if the  drag and drop feature is enabled over the card
     * @return
     */
    public boolean isEnableDragSupportOnLongClickOnCard() {
        return enableDragSupportOnLongClickOnCard;
    }

    /**
     * Enables the drag and drop feature with the long click over the card
     * @param enableDragSupportOnLongClickOnCard
     */
    public void setEnableDragSupportOnLongClickOnCard(boolean enableDragSupportOnLongClickOnCard) {
        this.enableDragSupportOnLongClickOnCard = enableDragSupportOnLongClickOnCard;
    }


}
