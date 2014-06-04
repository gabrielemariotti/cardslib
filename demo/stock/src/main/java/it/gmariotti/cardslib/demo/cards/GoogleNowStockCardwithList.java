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
import java.util.List;

import it.gmariotti.cardslib.demo.R;
import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardHeader;
import it.gmariotti.cardslib.library.prototypes.CardWithList;
import it.gmariotti.cardslib.library.prototypes.LinearListView;

/**
 * @author Gabriele Mariotti (gabri.mariotti@gmail.com)
 */
public class GoogleNowStockCardwithList extends CardWithList {

    public GoogleNowStockCardwithList(Context context) {
        super(context);
    }

    @Override
    protected CardHeader initCardHeader() {

        //Add Header
        CardHeader header = new CardHeader(getContext(), R.layout.carddemo_googlenowstock_withlist_inner_header) {

            @Override
            public void setupInnerViewElements(ViewGroup parent, View view) {
                super.setupInnerViewElements(parent, view);
                TextView subTitle = (TextView) view.findViewById(R.id.carddemo_googlenow_main_inner_lastupdate);
                if (subTitle != null) {
                    subTitle.setText("Updated: 23 minutes ago");  //Should use strings.xml
                }
            }
        };
        header.setTitle("Stocks today"); //should use strings.xml
        return header;
    }

    @Override
    protected void initCard() {

        //Set the whole card as swipeable
        setSwipeable(true);
        setOnSwipeListener(new OnSwipeListener() {
            @Override
            public void onSwipe(Card card) {
                Toast.makeText(getContext(), "Swipe on " + card.getCardHeader().getTitle(), Toast.LENGTH_SHORT).show();
            }
        });

    }


    @Override
    protected List<ListObject> initChildren() {

        //Init the list
        List<ListObject> mObjects = new ArrayList<ListObject>();

        //Add an object to the list
        StockObject s1 = new StockObject(this);
        s1.code = "ENI";
        s1.value = 18.62f;
        s1.delta = -0.24f;
        s1.deltaPerc = -1.27f;
        mObjects.add(s1);

        StockObject s2 = new StockObject(this);
        s2.code = "UCG";
        s2.value = 6.47f;
        s2.delta = 15.19f;
        s2.deltaPerc = 0.09f;
        mObjects.add(s2);

        StockObject s3 = new StockObject(this);
        s3.code = "Down Jones";
        s3.value = 16738f;
        s3.delta = 15.19f;
        s3.deltaPerc = 0.09f;
        mObjects.add(s3);

        StockObject s4 = new StockObject(this);
        s4.code = "GOOG";
        s4.value = 544.66f;
        s4.delta = 0.28f;
        s4.deltaPerc = 0.05f;
        mObjects.add(s4);

        return mObjects;
    }

    @Override
    public View setupChildView(int childPosition, ListObject object, View convertView, ViewGroup parent) {

        //Setup the ui elements inside the item
        TextView textViewCode = (TextView) convertView.findViewById(R.id.textViewCode);
        TextView textViewValue = (TextView) convertView.findViewById(R.id.textViewValue);
        TextView textViewDelta = (TextView) convertView.findViewById(R.id.textViewDelta);
        TextView textViewDeltaPerc = (TextView) convertView.findViewById(R.id.textViewPerc);

        //Retrieve the values from the object
        StockObject stockObject = (StockObject) object;
        textViewCode.setText(stockObject.code);
        textViewValue.setText(""+stockObject.value);
        textViewDelta.setText(""+stockObject.delta);
        textViewDeltaPerc.setText(""+stockObject.deltaPerc+"%");

        if (stockObject.delta<0){
            textViewDelta.setTextColor(getContext().getResources().getColor(R.color.demo_card_stock_negative));
            textViewDeltaPerc.setTextColor(getContext().getResources().getColor(R.color.demo_card_stock_negative));
        }else{
            textViewDelta.setTextColor(getContext().getResources().getColor(R.color.demo_card_stock_positive));
            textViewDeltaPerc.setTextColor(getContext().getResources().getColor(R.color.demo_card_stock_positive));
        }

        return convertView;
    }

    @Override
    public int getChildLayoutId() {
        return R.layout.carddemo_googlenowstock_withlist_inner_main;
    }


    // -------------------------------------------------------------
    // Weather Object
    // -------------------------------------------------------------

    public class StockObject extends DefaultListObject {

        public String code;
        public float value;
        public float delta;
        public float deltaPerc;

        public StockObject(Card parentCard) {
            super(parentCard);
            init();
        }

        private void init() {
            //OnClick Listener
            setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(LinearListView parent, View view, int position, ListObject object) {
                    Toast.makeText(getContext(), "Click on " + getObjectId(), Toast.LENGTH_SHORT).show();
                }
            });
        }

        @Override
        public String getObjectId() {
            return code;
        }
    }

}
