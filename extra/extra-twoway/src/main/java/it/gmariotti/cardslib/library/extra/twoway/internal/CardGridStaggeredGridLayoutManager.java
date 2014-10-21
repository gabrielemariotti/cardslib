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

package it.gmariotti.cardslib.library.extra.twoway.internal;

import android.content.Context;
import android.util.AttributeSet;

import org.lucasr.twowayview.widget.StaggeredGridLayoutManager;

/**
 * @author Gabriele Mariotti (gabri.mariotti@gmail.com)
 */
public class CardGridStaggeredGridLayoutManager extends StaggeredGridLayoutManager {


    public CardGridStaggeredGridLayoutManager(Context context) {
        super(context);
    }

    public CardGridStaggeredGridLayoutManager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CardGridStaggeredGridLayoutManager(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public CardGridStaggeredGridLayoutManager(Context context, Orientation orientation, int numColumns, int numRows) {
        super(orientation, numColumns, numRows);
    }


}
