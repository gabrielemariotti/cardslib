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

package it.gmariotti.cardslib.demo.extras.cards;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import it.gmariotti.cardslib.demo.extras.R;
import it.gmariotti.cardslib.library.internal.Card;

/**
 * Simple colored card
 *
 * @author Gabriele Mariotti (gabri.mariotti@gmail.com)
 */
public class ColorCard extends Card {

    protected String mTitle;
    protected int count;

    public ColorCard(Context context) {
        this(context, R.layout.carddemo_extras_color_inner_base_main);
    }

    public ColorCard(Context context, int innerLayout) {
        super(context, innerLayout);
        init();
    }

    private void init() {

        //Add ClickListener
        setOnClickListener(new OnCardClickListener() {
            @Override
            public void onClick(Card card, View view) {
                Toast.makeText(getContext(), "Click Listener card="+count, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void setupInnerViewElements(ViewGroup parent, View view) {

        //Retrieve elements
        TextView title = (TextView) parent.findViewById(R.id.carddemo_extras_card_color_inner_simple_title);

        if (title != null)
            title.setText(mTitle);

    }


    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

}
