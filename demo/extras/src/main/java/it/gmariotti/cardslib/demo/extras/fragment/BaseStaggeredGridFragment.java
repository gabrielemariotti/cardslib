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

package it.gmariotti.cardslib.demo.extras.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import it.gmariotti.cardslib.demo.extras.R;
import it.gmariotti.cardslib.library.extra.staggeredgrid.internal.CardGridStaggeredArrayAdapter;
import it.gmariotti.cardslib.library.extra.staggeredgrid.view.CardGridStaggeredView;
import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardHeader;
import it.gmariotti.cardslib.library.internal.CardThumbnail;
import it.gmariotti.cardslib.library.internal.base.BaseCard;

/**
 * This example uses a staggered card with different Card height.
 *
 * This example uses cards with a foreground layout.
 * Pay attention to style="@style/card.main_layout_foreground" in card layout.
 *
 *
 * @author Gabriele Mariotti (gabri.mariotti@gmail.com)
 */
public class BaseStaggeredGridFragment extends BaseFragment {

    @Override
    public int getTitleResourceId() {
        return R.string.carddemo_extras_title_base_staggered;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.demo_extras_fragment_basestaggeredgrid, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initCard();
    }

    /**
     * This method builds a simple list of cards
     */
    private void initCard() {

        //Only for test scope, use 6 different header titles
        int[] resTitleId = {R.string.carddemo_extras_title_base_stag1,
                R.string.carddemo_extras_title_base_stag2,
                R.string.carddemo_extras_title_base_stag3,
                R.string.carddemo_extras_title_base_stag4,
                R.string.carddemo_extras_title_base_stag5,
                R.string.carddemo_extras_title_base_stag6};

        //Init an array of Cards
        ArrayList<Card> cards = new ArrayList<Card>();
        for (int i = 0; i < 200; i++) {

            SquareGridCard card = new SquareGridCard(getActivity());

            card.headerTitle = "Card " + i;

            //Only for test scope, use random colors, and random height
            int random = (int) (Math.random() * 6);
            card.mainContent = getString(resTitleId[random]);

            if ((random / 2 % 3 == 0)) {
                card.thumbHeightId = R.dimen.carddemo_extras_basestaggered_height1;
            } else if ((random / 2 % 3 == 1)) {
                card.thumbHeightId = R.dimen.carddemo_extras_basestaggered_height2;
            } else if ((random / 2 % 3 == 2)) {
                card.thumbHeightId = R.dimen.carddemo_extras_basestaggered_height3;
            }


            //Only for test, change background color
            if ((i % 5 == 0)) {
                card.color = R.color.demoextra_card_background_color1;
            } else if ((i % 5 == 1)) {
                card.color = R.color.demoextra_card_background_color2;
            } else if ((i % 5 == 2)) {
                card.color = R.color.demoextra_card_background_color3;
            } else if ((i % 5 == 3)) {
                card.color = R.color.demoextra_card_background_color4;
            } else if ((i % 5 == 4)) {
                card.color = R.color.demoextra_card_background_color5;
            }

            card.init();
            cards.add(card);
        }

        //Set the arrayAdapter
        CardGridStaggeredArrayAdapter mCardArrayAdapter = new CardGridStaggeredArrayAdapter(getActivity(), cards);

        CardGridStaggeredView mGridView = (CardGridStaggeredView) getActivity().findViewById(R.id.carddemo_extras_grid_stag);
        if (mGridView != null) {
            mGridView.setAdapter(mCardArrayAdapter);
        }

    }

    /**
     * Simple card with a colored thumbnail, a basic header and a basic main content.
     */
    public class SquareGridCard extends Card {

        protected String headerTitle;
        protected String mainContent;
        protected int color;
        protected int thumbHeightId;

        public SquareGridCard(Context context) {
            super(context, R.layout.carddemo_extra_basestaggered_inner_base_main);
        }


        private void init() {
            //Add the header
            CardHeader header = new CardHeader(getContext());
            header.setButtonOverflowVisible(true);
            header.setTitle(headerTitle);
            header.setPopupMenu(R.menu.extras_popupmain, new CardHeader.OnClickCardHeaderPopupMenuListener() {
                @Override
                public void onMenuItemClick(BaseCard card, MenuItem item) {
                    Toast.makeText(getContext(), "Item " + item.getTitle(), Toast.LENGTH_SHORT).show();
                }
            });
            addCardHeader(header);

            //Add the thumbnail
            SquareGridThumb thumbnail = new SquareGridThumb(getContext(), color);
            //trick: add external usage to change the background color
            thumbnail.setExternalUsage(true);
            thumbnail.thumbHeightId = thumbHeightId;
            addCardThumbnail(thumbnail);

            //An simple and useless clicklistener
            setOnClickListener(new OnCardClickListener() {
                @Override
                public void onClick(Card card, View view) {
                    //Do something
                    Toast.makeText(getContext(),"Click on card"+headerTitle,Toast.LENGTH_SHORT).show();
                }
            });
        }

        @Override
        public void setupInnerViewElements(ViewGroup parent, View view) {
            //Setup the main content title
            TextView mTitleView = (TextView) view.findViewById(R.id.carddemo_extras_basestaggered_main_inner_simple_title);
            if (mTitleView != null)
                mTitleView.setText(mainContent);
        }


        /**
         * A simple CardThumbnail
         */
        class SquareGridThumb extends CardThumbnail {

            int color;
            protected int thumbHeightId;

            public SquareGridThumb(Context context, int color) {
                super(context);
                this.color = color;
            }

            @Override
            public void setupInnerViewElements(ViewGroup parent, View viewImage) {

                ImageView image = ((ImageView) viewImage);

                //Set the color
                if (color > 0)
                    image.setBackgroundColor(getResources().getColor(color));

                //Change the height
                image.getLayoutParams().height = (int) (getResources().getDimension(thumbHeightId));

            }
        }


    }


}

