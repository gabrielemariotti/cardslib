/*
 * ******************************************************************************
 *   Copyright (c) 2014 Gabriele Mariotti.
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

import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.view.View;

import com.nhaarman.listviewanimations.itemmanipulation.dragdrop.DraggableManager;

/**
 * This class handles the single click on the card with the drag and drop feature enabled.
 * It doesn't enable the drag feature with the single click over the card.
 *
 * @author Gabriele Mariotti (gabri.mariotti@gmail.com)
 */
public class CardTouchViewDraggableManager implements DraggableManager {

    @IdRes
    private final int mTouchViewResId;

    public CardTouchViewDraggableManager(@IdRes final int touchViewResId) {
        mTouchViewResId = touchViewResId;
    }

    @Override
    public boolean isDraggable(@NonNull View view, int position, float x, float y) {
        View touchView = view.findViewById(mTouchViewResId);
        if (touchView != null) {
            boolean xHit = touchView.getLeft() <= x && touchView.getRight() >= x;
            boolean yHit = touchView.getTop() <= y && touchView.getBottom() >= y;
            return !(xHit && yHit);
        } else {
            return true;
        }
    }
}
