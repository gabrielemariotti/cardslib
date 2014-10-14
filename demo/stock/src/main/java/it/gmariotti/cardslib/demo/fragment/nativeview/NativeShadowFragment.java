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

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;

import it.gmariotti.cardslib.demo.R;
import it.gmariotti.cardslib.demo.fragment.BaseMaterialFragment;
import it.gmariotti.cardslib.library.Constants;
import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardHeader;
import it.gmariotti.cardslib.library.view.CardViewNative;

/**
 * Card Shadow Examples
 *
 * @author Gabriele Mariotti (gabri.mariotti@gmail.com)
 */
public class NativeShadowFragment extends BaseMaterialFragment {

    protected ScrollView mScrollView;

    @Override
    public int getTitleResourceId() {
        return R.string.carddemo_title_shadow;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.demo_fragment_native_shadow, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mScrollView = (ScrollView) getActivity().findViewById(R.id.card_scrollview);

        initCards();
    }

    @Override
    protected int getSubTitleHeaderResourceId() {
        return R.string.header_title_cardshadowsubtitle;
    }

    @Override
    protected int getTitleHeaderResourceId() {
        return R.string.header_title_group1;
    }

    @Override
    protected String getDocUrl() {
        return "https://github.com/gabrielemariotti/cardslib/blob/master/doc/SHADOW.md";
    }

    @Override
    protected String getSourceUrl() {
        return "https://github.com/gabrielemariotti/cardslib/blob/master/demo/stock/src/main/java/it/gmariotti/cardslib/demo/fragment/native/NativeShadowFragment.java";
    }

    private void initCards() {
        init_card_elevation_shadow();
        init_card_animation_shadow();
    }

    /**
     * This method builds a card with a different elevation
     */
    private void init_card_elevation_shadow() {

        //Create a Card
        Card card = new Card(getActivity());

        //Create a CardHeader
        CardHeader header = new CardHeader(getActivity());

        //Set the header title
        header.setTitle(getString(R.string.demo_header_basetitle));

        card.addCardHeader(header);

        //Card elevation
        card.setCardElevation(getResources().getDimension(R.dimen.carddemo_shadow_elevation));

        //Set card in the cardView
        CardViewNative cardView = (CardViewNative) getActivity().findViewById(R.id.carddemo_shadow_elevation);
        cardView.setCard(card);
    }

    /**
     * This method builds a card with an animation
     */
    private void init_card_animation_shadow() {

        //Create a Card
        Card card = new Card(getActivity());

        //Create a CardHeader
        CardHeader header = new CardHeader(getActivity());

        //Set the header title
        header.setTitle(getString(R.string.demo_header_basetitle));

        card.addCardHeader(header);

        //Set card in the cardView
        final CardViewNative cardView = (CardViewNative) getActivity().findViewById(R.id.carddemo_shadow_animation);
        cardView.setCard(card);

        cardView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        if (Build.VERSION.SDK_INT >= Constants.API_L) {
                            cardView.animate().setDuration(100).scaleX(1.1f).scaleY(1.1f).translationZ(10);
                        } else {
                            cardView.animate().setDuration(100).scaleX(1.1f).scaleY(1.1f);
                        }
                        return true;
                    case MotionEvent.ACTION_CANCEL:
                    case MotionEvent.ACTION_UP:
                        if (Build.VERSION.SDK_INT >= Constants.API_L) {
                            cardView.animate().setDuration(100).scaleX(1).scaleY(1).translationZ(0);
                        } else {
                            cardView.animate().setDuration(100).scaleX(1).scaleY(1);
                        }
                        return true;
                }
                return false;
            }
        });
    }

}
