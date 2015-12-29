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

package it.gmariotti.cardslib.library.view.listener.dismiss;

import android.widget.Adapter;

import it.gmariotti.cardslib.library.internal.Card;

/**
 * An interface to specify if items can or cannot be dismissed and the SwipeDirection
 * @author Gabriele Mariotti (gabri.mariotti@gmail.com)
 */
public interface Dismissable {

    /**
     * Returns whether the item for given id and position can be dismissed.
     * @param position the position of the item.
     * @param card card
     * @return true if the item can be dismissed, false otherwise.
     */
    boolean isDismissable(int position, Card card);

    SwipeDirection getSwipeDirectionAllowed();

    void setAdapter(Adapter adapter);


    public enum SwipeDirection {
        BOTH(0),
        LEFT(1),
        RIGHT(2);

        private final int mValue;

        private SwipeDirection(int value) {
            mValue = value;
        }

        public int getValue() {
            return mValue;
        }
    }
}
