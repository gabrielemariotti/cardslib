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

package it.gmariotti.cardslib.demo.fragment.nativeview;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import it.gmariotti.cardslib.demo.R;
import it.gmariotti.cardslib.demo.fragment.BaseMaterialFragment;
import it.gmariotti.cardslib.library.cards.actions.BaseSupplementalAction;
import it.gmariotti.cardslib.library.cards.actions.IconSupplementalAction;
import it.gmariotti.cardslib.library.cards.actions.TextSupplementalAction;
import it.gmariotti.cardslib.library.cards.material.MaterialLargeImageCard;
import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.view.CardViewNative;

/**
 * @author Gabriele Mariotti (gabri.mariotti@gmail.com)
 */
public class NativeMaterialCardFragment extends BaseMaterialFragment {

    @Override
    protected int getSubTitleHeaderResourceId() {
        return 0;
    }

    @Override
    protected int getTitleHeaderResourceId() {
        return 0;
    }

    @Override
    protected String getDocUrl() {
        return null;
    }

    @Override
    protected String getSourceUrl() {
        return null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View mRootView = inflater.inflate(R.layout.demo_fragment_native_materialcard, container, false);
        return mRootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initCards();
    }

    private void initCards() {

        init_largeimage_text();
        init_largeimage();
    }

    /**
     * Builds a Material Card with Large Image and Text
     */
    private void init_largeimage_text() {

        //Create a Card, set the title over the image and set the thumbnail
        MaterialLargeImageCard card = new MaterialLargeImageCard(getActivity());
        card.setTextOverImage("Italian Beaches");
        card.setDrawableCardThumbnail(R.drawable.sea);

        //Set the title and subtitle in the card
        card.setTitle("This is my favorite local beach");
        card.setSubTitle("A wonderful place");

        // Set supplemental actions
        TextSupplementalAction t1 = new TextSupplementalAction(getActivity(), R.id.text1);
        t1.setOnActionClickListener(new BaseSupplementalAction.OnActionClickListener() {
            @Override
            public void onClick(Card card, View view) {
                Toast.makeText(getActivity()," Click on Text SHARE ",Toast.LENGTH_SHORT).show();
            }
        });
        card.addSupplementalAction(t1);

        TextSupplementalAction t2 = new TextSupplementalAction(getActivity(), R.id.text2);
        t2.setOnActionClickListener(new BaseSupplementalAction.OnActionClickListener() {
            @Override
            public void onClick(Card card, View view) {
                Toast.makeText(getActivity()," Click on Text LEARN ",Toast.LENGTH_SHORT).show();
            }
        });
        card.addSupplementalAction(t2);

        //Set the layout for supplemental actions
        card.setLayout_supplemental_actions_id(R.layout.carddemo_native_material_supplemental_actions_large);

        //Very important call: build the card
        card.build();

        //Set card in the CardViewNative
        CardViewNative cardView = (CardViewNative) getActivity().findViewById(R.id.carddemo_largeimage_text);
        cardView.setCard(card);
    }

    /**
     * Builds a Material Card with Large and small icons as supplemental actions
     */
    private void init_largeimage() {

        //Create a Card, set the title over the image and set the thumbnail
        MaterialLargeImageCard card = new MaterialLargeImageCard(getActivity());
        card.setTextOverImage("Italian Beaches");
        card.setDrawableCardThumbnail(R.drawable.im_beach);

        // Set supplemental actions as icon
        IconSupplementalAction t1 = new IconSupplementalAction(getActivity(), R.id.ic1);
        t1.setOnActionClickListener(new BaseSupplementalAction.OnActionClickListener() {
            @Override
            public void onClick(Card card, View view) {
                Toast.makeText(getActivity()," Click on Text SHARE ",Toast.LENGTH_SHORT).show();
            }
        });
        card.addSupplementalAction(t1);

        IconSupplementalAction t2 = new IconSupplementalAction(getActivity(), R.id.ic2);
        t2.setOnActionClickListener(new BaseSupplementalAction.OnActionClickListener() {
            @Override
            public void onClick(Card card, View view) {
                Toast.makeText(getActivity()," Click on Text LEARN ",Toast.LENGTH_SHORT).show();
            }
        });
        card.addSupplementalAction(t2);

        //Set the layout for supplemental actions
        card.setLayout_supplemental_actions_id(R.layout.carddemo_native_material_supplemental_actions_large_icon);

        //Very important call: build the card
        card.build();

        //Set card in the CardViewNative
        CardViewNative cardView = (CardViewNative) getActivity().findViewById(R.id.carddemo_largeimage);
        cardView.setCard(card);
    }

    /*
    //
        //Create a Card
        MaterialLargeImageCard cardx = new MaterialLargeImageCard(getActivity());
        cardx.setTextOverImage("Italian Beaches");
        cardx.addCardThumbnail(new MyMaterialCardThumbnail(getActivity()));

        cardx.setTitle("This is my favorite local beach");

        TextSupplementalAction t1 = new TextSupplementalAction(getActivity(), R.id.text1);
        t1.setOnActionClickListener(new BaseSupplementalAction.OnActionClickListener() {
            @Override
            public void onClick(Card card, View view) {
                Toast.makeText(getActivity()," Click on Text SHARE ",Toast.LENGTH_SHORT).show();
            }
        });
        cardx.addSupplementalAction(t1);

        TextSupplementalAction t2 = new TextSupplementalAction(getActivity(), R.id.text2);
        t2.setOnActionClickListener(new BaseSupplementalAction.OnActionClickListener() {
            @Override
            public void onClick(Card card, View view) {
                Toast.makeText(getActivity()," Click on Text LEARN ",Toast.LENGTH_SHORT).show();
            }
        });
        cardx.addSupplementalAction(t2);

        cardx.setLayout_supplemental_actions_id(R.layout.carddemo_native_material_supplemental_actions_large);

        cardx.build();

        //Set card in the CardViewNative
        CardViewNative cardViewz = (CardViewNative) getActivity().findViewById(R.id.carddemo_largeimage);
        cardViewz.setCard(cardx);
    }

    public class MyMaterialCardThumbnail extends MaterialLargeImageCardThumbnail {

        public MyMaterialCardThumbnail(Context context) {
            super(context);
            setExternalUsage(true);
        }

        @Override
        public void setupInnerViewElements(ViewGroup parent, View imageView) {
            super.setupInnerViewElements(parent, imageView);

            Picasso.with(getContext())
                    .load("https://lh5.googleusercontent.com/-squZd7FxR8Q/UyN5UrsfkqI/AAAAAAAAbAo/VoDHSYAhC_E/s96/new%2520profile%2520%25282%2529.jpg")
                    .into((ImageView) imageView);

        }
    }
     */
}
