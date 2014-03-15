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

package it.gmariotti.cardslib.demo.extras.fragment;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nhaarman.listviewanimations.swinginadapters.AnimationAdapter;
import com.nhaarman.listviewanimations.swinginadapters.prepared.AlphaInAnimationAdapter;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import it.gmariotti.cardslib.demo.extras.R;
import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardArrayAdapter;
import it.gmariotti.cardslib.library.internal.CardExpand;
import it.gmariotti.cardslib.library.internal.CardHeader;
import it.gmariotti.cardslib.library.internal.ViewToClickToExpand;
import it.gmariotti.cardslib.library.view.CardListView;

/**
 * This example uses a list card with expand area inside the Card.
 * Pay attention to style="@style/card.main_layout_expandinside" in card layout.
 * This style doesn't use a selector as background, but a simple color background.
 *
 * The adapter is also animated with ListViewAnimations library.
 * Please refer to https://github.com/nhaarman/ListViewAnimations for full doc
 *
 * In expand card it uses a thumbnail loaded with Picasso library.
 * Please refer to https://github.com/square/picasso for full doc
 *
 * @author Gabriele Mariotti (gabri.mariotti@gmail.com)
 */
public class ExpandPicassoFragment extends BaseFragment {

    @Override
    public int getTitleResourceId() {
        return R.string.carddemo_extras_title_expandinside;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.demo_extras_fragment_mix, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initCard();
    }

    /**
     * This method builds a simple cards list
     */
    private void initCard() {

        //Only for test scope, use images on assets folder
        String[] fileName = {"file:///android_asset/images/sea.jpg",
                "file:///android_asset/images/snow.jpg",
                "file:///android_asset/images/water.jpg",
                "file:///android_asset/images/img2.jpg",
                "file:///android_asset/images/rose.jpg"};

        //Only for test scope, use 5 different header titles
        int[] resTitleId = {R.string.carddemo_extras_header_expand_area_inside_sea,
                R.string.carddemo_extras_header_expand_area_inside_snow,
                R.string.carddemo_extras_header_expand_area_inside_water,
                R.string.carddemo_extras_header_expand_area_inside_img2,
                R.string.carddemo_extras_header_expand_area_inside_rose};

        //Remove debugging from Picasso
        Picasso.with(getActivity()).setDebugging(false);

        //Init an array of Cards
        ArrayList<Card> cards = new ArrayList<Card>();
        for (int j = 1; j < 30; j++) {

            for (int i = 0; i < 10; i++) {

                //Card
                CardInside card = new CardInside(this.getActivity());

                //Create a CardHeader
                CardHeader header = new CardHeader(getActivity(),R.layout.carddemo_extras_expandinside_inner_base_header);

                if (i % 2 == 0) {

                    //Set the header title
                    header.setTitle(getString(resTitleId[(int) (i / 2) % 5]));
                    //Add Header to card
                    card.addCardHeader(header);

                    //Add an expand area
                    CardExpandInside expand = new CardExpandInside(getActivity(), fileName[(int) (i / 2) % 5]);
                    card.addCardExpand(expand);

                } else {

                    //Set the header title
                    header.setTitle(getString(R.string.carddemo_extras_header_expand_area_inside));
                    //Add Header to card
                    card.addCardHeader(header);

                    //Only for test scope, use a random number
                    int randomNumber = (int) (Math.random() * 6);

                    //Add an expand area
                    CardExpandInsideSquare expand = new CardExpandInsideSquare(getActivity(),randomNumber);
                    card.addCardExpand(expand);

                }


                //Add a viewToClickExpand to enable click on whole card
                ViewToClickToExpand viewToClickToExpand =
                        ViewToClickToExpand.builder()
                                .highlightView(false)
                                .setupCardElement(ViewToClickToExpand.CardElementUI.CARD);
                card.setViewToClickToExpand(viewToClickToExpand);

                cards.add(card);
            }
        }

        //Set the arrayAdapter
        CardArrayAdapter mCardArrayAdapter = new CardArrayAdapter(getActivity(), cards);
        CardListView listView = (CardListView) getActivity().findViewById(R.id.carddemo_extra_list_mixinside);

        //Add an animator
        AnimationAdapter animCardArrayAdapter = new AlphaInAnimationAdapter(mCardArrayAdapter);
        animCardArrayAdapter.setAbsListView(listView);
        animCardArrayAdapter.setInitialDelayMillis(500);
        if (listView != null) {
            listView.setExternalAdapter(animCardArrayAdapter, mCardArrayAdapter);
        }

    }


    /**
     * Main Card
     */
    public class CardInside extends Card {

        public CardInside(Context context) {
            super(context);
        }

    }

    /**
     *  A CardExpand with a image loaded with Picasso from assets folder
     */
    class CardExpandInside extends CardExpand {

        //Image's name
        String mFileName;

        public CardExpandInside(Context context, String fileName) {
            super(context, R.layout.carddemo_extras_list_card_expandinside_expand_inner);
            mFileName = fileName;
        }

        @Override
        public void setupInnerViewElements(ViewGroup parent, View view) {

            ImageView img = (ImageView) view.findViewById(R.id.carddemo_inside_image);

            //You could use a singleton instance of picasso!
            if (img != null) {
                Picasso.with(getContext())
                        .load(Uri.parse(mFileName))
                        .fit()
                        .into(img);
            }

        }
    }


    /**
     * A CardExpand with Texts and other ui elements
     */
    class CardExpandInsideSquare extends CardExpand {

        int randomNumber;

        public CardExpandInsideSquare(Context context,int randomNumber) {
            super(context, R.layout.carddemo_extras_list_card_expandinside_expand_square_inner);
            this.randomNumber = randomNumber;
        }

        @Override
        public void setupInnerViewElements(ViewGroup parent, View view) {


            TextView tx = (TextView) view.findViewById(R.id.carddemo_inside_text);
            TextView tx1 = (TextView) view.findViewById(R.id.carddemo_inside_image_title1);
            TextView tx2 = (TextView) view.findViewById(R.id.carddemo_inside_image_title2);

            if (tx != null)
                tx.setText(""+randomNumber);

            //Rectangle Color. Only for test scope, use different colors
            final GradientDrawable rectangle=(GradientDrawable)tx.getBackground();
            if (rectangle!=null){
                if (randomNumber<1){
                    rectangle.setColor(getResources().getColor(R.color.demoextra_card_background_color1));
                }else if (randomNumber<2){
                    rectangle.setColor(getResources().getColor(R.color.demoextra_card_background_color2));
                }else if (randomNumber<3){
                    rectangle.setColor(getResources().getColor(R.color.demoextra_card_background_color3));
                }else if (randomNumber<4){
                    rectangle.setColor(getResources().getColor(R.color.demoextra_card_background_color4));
                }else if (randomNumber<5){
                    rectangle.setColor(getResources().getColor(R.color.demoextra_card_background_color5));
                }else {
                    rectangle.setColor(getResources().getColor(R.color.demoextra_card_background_color6));
                }
            }


            if (tx1 != null)
                tx1.setText("Detail............");

            if (tx2 != null)
                tx2.setText("xxxx xxx xxxx xxxxx");

        }
    }

}
