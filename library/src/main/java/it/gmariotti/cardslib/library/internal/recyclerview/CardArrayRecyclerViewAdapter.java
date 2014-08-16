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

package it.gmariotti.cardslib.library.internal.recyclerview;

import android.content.Context;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import it.gmariotti.cardslib.library.internal.Card;

/**
 * @author Gabriele Mariotti (gabri.mariotti@gmail.com)
 */
public class CardArrayRecyclerViewAdapter extends BaseRecyclerViewAdapter {

    protected List<Card> mCards;

    // -------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------

    /**
     * Constructor
     *
     * @param context The current context.
     * @param cards   The cards to represent in the ListView.
     */
    public CardArrayRecyclerViewAdapter(Context context, List<Card> cards) {
        super(context);
        if (mCards != null) {
            mCards = cards;
        } else {
            mCards = new ArrayList<Card>();
        }
    }

    // -------------------------------------------------------------
    // Methods
    // -------------------------------------------------------------

    @Override
    public int getItemCount() {
        return mCards.size();
    }


    @Override
    public Card getItem(int position) {
        return mCards.get(position);
    }

    public void setCards(List<Card> cards) {
        mCards = cards;
    }


    /**
     * Appends the specified element to the end of the {@code List}.
     *
     * @param card the object to add.
     *
     * @return always true.
     */
    public boolean add(@NonNull final Card card) {
        boolean result = mCards.add(card);
        notifyDataSetChanged();
        return result;
    }

    public void add(final int index, @NonNull final Card card) {
        mCards.add(index, card);
        notifyItemInserted(index);
    }

    /**
     * Adds the objects in the specified collection to the end of this List. The objects are added in the order in which they are returned from the collection's iterator.
     *
     * @param collection the collection of objects.
     *
     * @return {@code true} if this {@code List} is modified, {@code false} otherwise.
     */
    public boolean addAll(@NonNull final Collection<? extends Card> collection) {
        boolean result = mCards.addAll(collection);
        notifyDataSetChanged();
        return result;
    }

    public boolean contains(final Card object) {
        return mCards.contains(object);
    }

    public void clear() {
        mCards.clear();
        notifyDataSetChanged();
    }

    public boolean remove(@NonNull final Card card) {
        boolean result = mCards.remove(card);
        notifyDataSetChanged();
        return result;
    }

    @NonNull
    public Card remove(final int position) {
        Card result = mCards.remove(position);
        notifyItemRemoved(position);
        return result;
    }
}
