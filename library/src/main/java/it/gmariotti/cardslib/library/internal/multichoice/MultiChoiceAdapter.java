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

package it.gmariotti.cardslib.library.internal.multichoice;

import android.view.ActionMode;

import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.view.CardView;

/**
 * A base interface for multi choice adapter
 *
 * @author Gabriele Mariotti (gabri.mariotti@gmail.com)
 */
public interface MultiChoiceAdapter {

    /**
     * Implement to indicate if actionMode is started
     *
     * @return
     */
    boolean isActionModeStarted();

    /**
     * Implement to set the Default Option for multi choice
     *
     * @return
     */
    OptionMultiChoice getOptionMultiChoice();

    /**
     * Implement to get Card from position and set multi choice
     *
     * @param position
     * @return
     */
    Card getItem(int position);

    /**
     *  Called when an item is checked or unchecked during selection mode.
     *
     * @param mode The {@link ActionMode} providing the selection mode
     * @param position Adapter position of the item that was checked or unchecked
     * @param id Adapter ID of the item that was checked or unchecked
     * @param checked <code>true</code> if the item is now checked, <code>false</code>
     *                if the item is now unchecked.
     * @param cardView
     * @param card
     */
    void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked, CardView cardView, Card card);



    // -------------------------------------------------------------
    // Default methods. You haven't to define it in your classes
    // -------------------------------------------------------------

    /**
     * Returns the position of the specified item in the array.
     *
     * @param card The item to retrieve the position of.
     *
     * @return The position of the specified item.
     */
    int getPosition(Card card);

    /**
     * Get the row id associated with the specified position in the list.
     *
     * @param position The position of the item within the adapter's data set whose row id we want.
     * @return The id of the item at the specified position.
     */
    long getItemId(int position);
}
