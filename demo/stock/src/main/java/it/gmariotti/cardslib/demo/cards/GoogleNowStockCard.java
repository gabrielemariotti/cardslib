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

package it.gmariotti.cardslib.demo.cards;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import it.gmariotti.cardslib.demo.R;
import it.gmariotti.cardslib.demo.stock.ListStockAdapter;
import it.gmariotti.cardslib.demo.stock.Stock;
import it.gmariotti.cardslib.demo.stock.StockListLayout;
import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardExpand;
import it.gmariotti.cardslib.library.internal.CardHeader;

/**
 * This class provides a simple card as Google Now Stock
 *
 * @author Gabriele Mariotti (gabri.mariotti@gmail.com)
 */
public class GoogleNowStockCard extends Card {

    public GoogleNowStockCard(Context context) {
        this(context, R.layout.carddemo_googlenowstock_inner_content);
    }

    public GoogleNowStockCard(Context context, int innerLayout) {
        super(context, innerLayout);
        init();

    }

    private void init() {
        //Add Header
        CardHeader header = new CardHeader(getContext());
        header.setButtonExpandVisible(true);
        header.setTitle("Stocks today"); //should use R.string.
        addCardHeader(header);

        //Add expand
        CardExpand expand = new GoogleNowExpandCard(getContext());
        addCardExpand(expand);

        //Add onClick Listener
        setOnClickListener(new OnCardClickListener() {
            @Override
            public void onClick(Card card, View view) {
                Toast.makeText(getContext(), "Click Listener card=" , Toast.LENGTH_LONG).show();
            }
        });

        //Add swipe Listener
        setOnSwipeListener(new OnSwipeListener() {
            @Override
            public void onSwipe(Card card) {
                Toast.makeText(getContext(), "Card removed", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void setupInnerViewElements(ViewGroup parent, View view) {

        TextView textView = (TextView) view.findViewById(R.id.carddemo_googlenow_main_inner_lastupdate);
        textView.setText("Update 14:57, 16 September"); //should use R.string.

        StockListLayout list = (StockListLayout) view.findViewById(R.id.carddemo_googlenow_main_inner_list);
        ListStockAdapter mAdapter = new ListStockAdapter(super.getContext(), buildArrayHelper());


        list.setAdapter(mAdapter);

    }


    //------------------------------------------------------------------------------------------


    public ArrayList<Stock> buildArrayHelper() {

        Stock s1 = new Stock("GOOG", 889.07f, 0.00f, 0.00f);
        Stock s2 = new Stock("AAPL", 404.27f, 0.00f, 0.00f);
        Stock s3 = new Stock("ENI", 17.59f, 0.06f, 0.34f);
        Stock s4 = new Stock("Don Jones", 15.376f, 0.00f, 0.00f);

        ArrayList<Stock> list = new ArrayList<Stock>();
        list.add(s1);
        list.add(s2);
        list.add(s3);
        list.add(s4);
        return list;
    }


}
