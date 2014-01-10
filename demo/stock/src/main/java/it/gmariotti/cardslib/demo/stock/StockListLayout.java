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

package it.gmariotti.cardslib.demo.stock;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

/**
 * @author Gabriele Mariotti (gabri.mariotti@gmail.com)
 */
public class StockListLayout extends LinearLayout implements
        View.OnClickListener {

    private ListStockAdapter mList;
    private View.OnClickListener mListener;
    private View view;

    public StockListLayout(Context context) {
        super(context);
    }

    public StockListLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public StockListLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setAdapter(ListStockAdapter list) {
        this.mList = list;
        setOrientation(VERTICAL);

        //Popolute list
        if (mList != null) {
            for (int i = 0; i < mList.getCount(); i++) {
                view = mList.getView(i, null, null);
                this.addView(view);
            }
        }
    }

    public void setListener(View.OnClickListener listener) {
        this.mListener = listener;
    }

    @Override
    public void onClick(View v) {

    }


}