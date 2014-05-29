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

package it.gmariotti.cardslib.library.prototypes;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

/**
 * @author Gabriele Mariotti (gabri.mariotti@gmail.com)
 */
public class LinearListView extends LinearLayout {

    /**
     * Adapter
     */
    private CardWithList.LinearListAdapter mListAdapter;

    private View view;

    // -------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------

    public LinearListView(Context context) {
        super(context);
    }

    public LinearListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LinearListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    // -------------------------------------------------------------
    // Adapter
    // -------------------------------------------------------------

    public void setAdapter(CardWithList.LinearListAdapter listAdapter) {
        this.mListAdapter = listAdapter;
        setOrientation(VERTICAL);

        //Populate the list
        if (mListAdapter != null) {
            for (int i = 0; i < mListAdapter.getCount(); i++) {
                view = mListAdapter.getView(i, null, null);
                if (view != null)
                    this.addView(view);
            }
        }
    }

}