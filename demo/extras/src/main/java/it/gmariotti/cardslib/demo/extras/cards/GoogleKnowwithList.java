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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import it.gmariotti.cardslib.demo.extras.R;
import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardHeader;
import it.gmariotti.cardslib.library.prototypes.CardWithList;
import it.gmariotti.cardslib.library.prototypes.LinearListView;

/**
 * @author Gabriele Mariotti (gabri.mariotti@gmail.com)
 */
public class GoogleKnowwithList extends CardWithList {

    public GoogleKnowwithList(Context context) {
        super(context);
    }

    @Override
    protected CardHeader initCardHeader() {

        //Add Header
        CardHeader header = new CardHeader(getContext(), R.layout.carddemo_googleknow_withlist_inner_header) {

            @Override
            public void setupInnerViewElements(ViewGroup parent, View view) {
                super.setupInnerViewElements(parent, view);
                TextView subTitle = (TextView) view.findViewById(R.id.carddemo_googleknow_sub_title);
                if (subTitle != null) {
                    subTitle.setText("To see posts from friends and family,add them.");  //Should use strings.xml
                }
            }
        };
        header.setTitle("You may know"); //should use strings.xml
        return header;
    }

    @Override
    protected void initCard() {

    }


    @Override
    protected List<ListObject> initChildren() {

        //Init the list
        List<ListObject> mObjects = new ArrayList<ListObject>();

        //Add an object to the list
        MayKnowObject s1 = new MayKnowObject(this);
        s1.name = "Gabriele Mariotti";
        s1.common = "127 people in common";
        s1.url = "https://lh5.googleusercontent.com/-squZd7FxR8Q/UyN5UrsfkqI/AAAAAAAAbAo/VoDHSYAhC_E/s54/new%2520profile%2520%25282%2529.jpg";
        mObjects.add(s1);

        //Add an object to the list
        MayKnowObject s2 = new MayKnowObject(this);
        s2.name = "Another user";
        s2.common = "3 people in common";
        s2.url = "https://lh5.googleusercontent.com/-squZd7FxR8Q/UyN5UrsfkqI/AAAAAAAAbAo/VoDHSYAhC_E/s54/new%2520profile%2520%25282%2529.jpg";
        mObjects.add(s2);

        return mObjects;
    }

    @Override
    public View setupChildView(int childPosition, ListObject object, View convertView, ViewGroup parent) {

        //Setup the ui elements inside the item
        TextView textViewName = (TextView) convertView.findViewById(R.id.carddemo_know_name);
        TextView textViewPeople = (TextView) convertView.findViewById(R.id.carddemo_know_common);
        ImageView imagePeople = (ImageView) convertView.findViewById(R.id.carddemo_know_image);


        //Retrieve the values from the object
        MayKnowObject stockObject = (MayKnowObject) object;
        textViewName.setText(stockObject.name);
        textViewPeople.setText(""+stockObject.common);

        Picasso.with(getContext()).setIndicatorsEnabled(false);
        Picasso.with(getContext()).load(stockObject.url)
               .into(imagePeople);

        return convertView;
    }

    @Override
    public int getChildLayoutId() {
        return R.layout.carddemo_googleknow_withlist_inner_main;
    }


    // -------------------------------------------------------------
    // Weather Object
    // -------------------------------------------------------------

    public class MayKnowObject extends DefaultListObject {

        public String name;
        public String common;
        public String url;

        public MayKnowObject(Card parentCard) {
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

            //OnItemSwipeListener
            setOnItemSwipeListener(new OnItemSwipeListener() {
                @Override
                public void onItemSwipe(ListObject object, boolean dismissRight) {
                    Toast.makeText(getContext(), "Swipe on " + object.getObjectId(), Toast.LENGTH_SHORT).show();
                }
            });
        }

        @Override
        public String getObjectId() {
            return name;
        }
    }

}
