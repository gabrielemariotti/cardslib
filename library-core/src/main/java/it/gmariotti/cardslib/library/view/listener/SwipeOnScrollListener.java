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

package it.gmariotti.cardslib.library.view.listener;

import android.widget.AbsListView;

/**
 * Returns an {@link android.widget.AbsListView.OnScrollListener} to be added to the {@link
 * android.widget.ListView} using {@link android.widget.ListView#setOnScrollListener(android.widget.AbsListView.OnScrollListener)}.
 * If a scroll listener is already assigned, the caller should still pass scroll changes through
 * to this listener. This will ensure that this {@link SwipeDismissListViewTouchListener} is
 * paused during list view scrolling.</p>
 *
 * @author Gabriele Mariotti (gabri.mariotti@gmail.com)
 * @see SwipeDismissListViewTouchListener
 */
public class SwipeOnScrollListener implements AbsListView.OnScrollListener {

    private SwipeDismissListViewTouchListener mTouchListener;

    public void setTouchListener(final SwipeDismissListViewTouchListener touchListener) {
        mTouchListener = touchListener;
    }

    @Override
    public void onScrollStateChanged(final AbsListView view, final int scrollState) {
        if (mTouchListener != null) {
            mTouchListener.setEnabled(scrollState != AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL);
        }
    }

    @Override
    public void onScroll(final AbsListView view, final int firstVisibleItem, final int visibleItemCount, final int totalItemCount) {
    }
}