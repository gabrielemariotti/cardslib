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
import android.widget.TextView;

import it.gmariotti.cardslib.demo.R;
import it.gmariotti.cardslib.demo.fragment.BaseMaterialFragment;
import it.gmariotti.cardslib.library.cards.topcolored.TopColoredCard;
import it.gmariotti.cardslib.library.view.CardViewNative;

/**
 * @author Gabriele Mariotti (gabri.mariotti@gmail.com)
 */
public class NativeTopColoredCardFragment extends BaseMaterialFragment {

    public static final int SIMULATED_REFRESH_LENGTH = 3000;

    @Override
    protected int getSubTitleHeaderResourceId() {
        return R.string.header_title_subtitle_ex_topcolored;
    }

    @Override
    protected int getTitleHeaderResourceId() {  return R.string.header_title_group2;
    }

    @Override
    protected String getDocUrl() {
        return null;
    }

    @Override
    protected String getSourceUrl() {
        return "https://github.com/gabrielemariotti/cardslib/blob/master/demo/stock/src/main/java/it/gmariotti/cardslib/demo/fragment/nativeview/NativeMaterialCardFragment.java";
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return  inflater.inflate(R.layout.demo_fragment_native_topcolored, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initCards();
    }

    private void initCards() {
        init_HalfColoredCard();
        init_BlackTopColoredCard();
    }


    /**
     * Builds a Material HalfColored Card
     */
    private void init_HalfColoredCard() {

        TopColoredCard card = TopColoredCard.with(getActivity())
                .setColorResId(R.color.carddemo_halfcolored_color)
                .setTitleOverColor("22 mins to Ancona")
                .setSubTitleOverColor("Light traffic on SS16")
                .setupSubLayoutId(R.layout.carddemo_native_halfcolored_simple_title)
                .setupInnerElements(new TopColoredCard.OnSetupInnerElements() {
                    @Override
                    public void setupInnerViewElementsSecondHalf(View secondHalfView) {

                        TextView mSimpleTitleView = (TextView) secondHalfView.findViewById(R.id.carddemo_halfcolored_simple_title);
                        if (mSimpleTitleView!=null) {
                            mSimpleTitleView.setText("It is just an example!");
                        }
                    }
                })
                .build();

        //Set card in the CardViewNative
        CardViewNative cardView = (CardViewNative) getActivity().findViewById(R.id.carddemo_halfcolored);
        cardView.setCard(card);
    }

    /**
     * Builds a Material HalfColored Card
     */
    private void init_BlackTopColoredCard() {

        TopColoredCard card = TopColoredCard.with(getActivity())
                .setColorResId(R.color.carddemo_blackcolored_color)
                .setTitleOverColor(R.string.carddemo_match)
                .setupSubLayoutId(R.layout.carddemo_native_blackcolored_simple_title)
                .setupInnerElements(new TopColoredCard.OnSetupInnerElements() {
                    @Override
                    public void setupInnerViewElementsSecondHalf(View secondHalfView) {

                        TextView mSimpleTitleView = (TextView) secondHalfView.findViewById(R.id.carddemo_blackcolored_simple_title);
                        if (mSimpleTitleView!=null) {
                            mSimpleTitleView.setText("It is just an example!");
                        }
                    }
                })
                .build();

        //Set card in the CardViewNative
        CardViewNative cardView = (CardViewNative) getActivity().findViewById(R.id.carddemo_blackcolored);
        cardView.setCard(card);
    }
}
