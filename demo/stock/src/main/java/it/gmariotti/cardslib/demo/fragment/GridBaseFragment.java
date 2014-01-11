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

package it.gmariotti.cardslib.demo.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.Toast;

import java.util.ArrayList;

import it.gmariotti.cardslib.demo.R;
import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardGridArrayAdapter;
import it.gmariotti.cardslib.library.internal.CardHeader;
import it.gmariotti.cardslib.library.internal.CardThumbnail;
import it.gmariotti.cardslib.library.view.CardGridView;

/**
 * List base example
 *
 * @author Gabriele Mariotti (gabri.mariotti@gmail.com)
 */
public class GridBaseFragment extends BaseFragment {

    protected ScrollView mScrollView;

    @Override
    public int getTitleResourceId() {
        return R.string.carddemo_title_grid_base;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.demo_fragment_grid_base, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initCards();
    }


    private void initCards() {

        ArrayList<Card> cards = new ArrayList<Card>();
        for (int i=0;i<200;i++){
            GdriveGridCard card = new GdriveGridCard(getActivity());

            card.headerTitle="Folder "+i;
            card.init();
            cards.add(card);
        }

        CardGridArrayAdapter mCardArrayAdapter = new CardGridArrayAdapter(getActivity(),cards);

        CardGridView gridView = (CardGridView) getActivity().findViewById(R.id.carddemo_grid_base);
        if (gridView!=null){
            gridView.setAdapter(mCardArrayAdapter);
        }
    }


    public class GdriveGridCard extends Card {

        protected String headerTitle;

        public GdriveGridCard(Context context) {
            super(context);
        }

        private void init() {
            CardHeader header = new CardHeader(getContext(),R.layout.carddemo_gdrive_header_inner);
            header.setTitle(headerTitle);
            header.setOtherButtonVisible(true);

            //Add a callback
            header.setOtherButtonClickListener(new CardHeader.OnClickCardHeaderOtherButtonListener() {
                @Override
                public void onButtonItemClick(Card card, View view) {
                    Toast.makeText(getActivity(), "Click on Other Button", Toast.LENGTH_SHORT).show();
                }
            });

            //Use this code to set your drawable
            header.setOtherButtonDrawable(R.drawable.card_menu_button_expand);

            addCardHeader(header);

            GdriveGridThumb thumbnail = new GdriveGridThumb(getContext());
            thumbnail.setDrawableResource(R.drawable.ic_action_folder_closed);
            addCardThumbnail(thumbnail);

            setOnClickListener(new OnCardClickListener() {
                @Override
                public void onClick(Card card, View view) {
                    Toast.makeText(getContext(), "Click Listener card=" + headerTitle, Toast.LENGTH_SHORT).show();
                }
            });

            setSwipeable(true);


        }

        class GdriveGridThumb extends CardThumbnail {

            public GdriveGridThumb(Context context) {
                super(context);
            }

            @Override
            public void setupInnerViewElements(ViewGroup parent, View viewImage) {
                viewImage.getLayoutParams().width = 32;
                viewImage.getLayoutParams().height = 32;

            }
        }

    }

}
