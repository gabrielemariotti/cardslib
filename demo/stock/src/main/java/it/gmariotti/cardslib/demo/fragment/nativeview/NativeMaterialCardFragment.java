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

import it.gmariotti.cardslib.demo.R;
import it.gmariotti.cardslib.demo.fragment.BaseMaterialFragment;
import it.gmariotti.cardslib.library.cards.MaterialLargeImageCard;
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
        init_largeimage();
    }

    private void init_largeimage() {

        //Create a Card
        MaterialLargeImageCard card = new MaterialLargeImageCard(getActivity());
        card.setTextOverImage("Top 10 Australian Beaches");
        card.setDrawableCardThumbnail(R.drawable.sea);
        card.build();

        //Set card in the CardViewNative
        CardViewNative cardView = (CardViewNative) getActivity().findViewById(R.id.carddemo_largeimage);
        cardView.setCard(card);
    }
}
