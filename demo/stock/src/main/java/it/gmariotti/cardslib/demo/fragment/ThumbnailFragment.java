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

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;

import it.gmariotti.cardslib.demo.R;
import it.gmariotti.cardslib.demo.cards.CustomThumbCard;
import it.gmariotti.cardslib.demo.cards.GplayCardCustomSource;
import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardHeader;
import it.gmariotti.cardslib.library.internal.CardThumbnail;
import it.gmariotti.cardslib.library.view.CardView;

/**
 * Card Thumbnail Examples
 *
 * @author Gabriele Mariotti (gabri.mariotti@gmail.com)
 */
public class ThumbnailFragment extends BaseFragment {

    protected ScrollView mScrollView;

    @Override
    public int getTitleResourceId() {
        return R.string.carddemo_title_thumbnail;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.demo_fragment_thumbnail, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mScrollView = (ScrollView) getActivity().findViewById(R.id.card_scrollview);

        initCards();
    }


    private void initCards() {
        init_card_thumb_resourceId();
        init_card_thumb_resourceURL();
        init_card_thumb_resourceURL_style();
        init_card_thumb_custom_source();
    }

    /**
     * This method builds a card with a thumbnail with a resource ID
     */
    private void init_card_thumb_resourceId() {

        //Create a Card
        Card card = new Card(getActivity());

        //Create a CardHeader
        CardHeader header = new CardHeader(getActivity());

        //Set the header title
        header.setTitle(getString(R.string.demo_header_basetitle));

        //Add header to a card
        card.addCardHeader(header);

        //Create thumbnail
        CardThumbnail thumb = new CardThumbnail(getActivity());

        //Set ID resource
        thumb.setDrawableResource(R.drawable.carddemo_ic_gmaps_large);

        //Add thumbnail to a card
        card.addCardThumbnail(thumb);

        //Set card in the cardView
        CardView cardView = (CardView) getActivity().findViewById(R.id.carddemo_thumb_id);
        cardView.setCard(card);
    }

    /**
     * This method builds a card with a thumbnail with a resource URL
     */
    private void init_card_thumb_resourceURL() {

        //Create a Card
        Card card = new Card(getActivity());

        //Create a CardHeader
        CardHeader header = new CardHeader(getActivity());

        //Set the header title
        header.setTitle(getString(R.string.demo_header_basetitle));

        card.addCardHeader(header);

        //Create thumbnail
        CardThumbnail thumb = new CardThumbnail(getActivity());

        //Set URL resource
        thumb.setUrlResource("https://lh5.googleusercontent.com/-N8bz9q4Kz0I/AAAAAAAAAAI/AAAAAAAAAAs/Icl2bQMyK7c/s265-c-k-no/photo.jpg");

        //Error Resource ID
        thumb.setErrorResource(R.drawable.ic_error_loadingorangesmall);

        //Add thumbnail to a card
        card.addCardThumbnail(thumb);

        //Set card in the cardView
        CardView cardView = (CardView) getActivity().findViewById(R.id.carddemo_thumb_url);
        cardView.setCard(card);
    }

    /**
     * This method builds a card with a thumbnail with a resource URL with a custom style
     */
    private void init_card_thumb_resourceURL_style() {

        //Create a Card
        Card card = new Card(getActivity());

        //Create a CardHeader
        CardHeader header = new CardHeader(getActivity());

        //Set the header title
        header.setTitle(getString(R.string.demo_header_basetitle));

        card.addCardHeader(header);

        //Create thumbnail
        CustomThumbCard thumb = new CustomThumbCard(getActivity());

        //Set URL resource
        thumb.setUrlResource("https://lh5.googleusercontent.com/-N8bz9q4Kz0I/AAAAAAAAAAI/AAAAAAAAAAs/Icl2bQMyK7c/s265-c-k-no/photo.jpg");

        //Error Resource ID
        thumb.setErrorResource(R.drawable.ic_error_loadingorangesmall);

        //Add thumbnail to a card
        card.addCardThumbnail(thumb);

        //Set card in the cardView
        CardView cardView = (CardView) getActivity().findViewById(R.id.carddemo_thumb_style);
        cardView.setCard(card);
    }

    /**
     * This method builds a card with a custom source thumbnail
     */
    private void init_card_thumb_custom_source() {

        //Create a Card
        Card card = new GplayCardCustomSource(getActivity());

        //Set card in the cardView
        CardView cardView = (CardView) getActivity().findViewById(R.id.carddemo_thumb_customsource);
        cardView.setCard(card);
    }
}
