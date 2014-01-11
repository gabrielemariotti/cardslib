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
import android.widget.RatingBar;
import android.widget.TextView;

import it.gmariotti.cardslib.demo.R;
import it.gmariotti.cardslib.library.internal.Card;

/**
 * This class provides an example of custom card with a custom inner layout.
 *
 * @author Gabriele Mariotti (gabri.mariotti@gmail.com)
 */
public class CustomCard extends Card {

    protected TextView mTitle;
    protected TextView mSecondaryTitle;
    protected RatingBar mRatingBar;

    /**
     * Constructor with a custom inner layout
     *
     * @param context
     */
    public CustomCard(Context context) {
        this(context, R.layout.carddemo_mycard_inner_content);
    }

    /**
     * @param context
     * @param innerLayout
     */
    public CustomCard(Context context, int innerLayout) {
        super(context, innerLayout);
        init();
    }

    /**
     * Init
     */
    private void init() {

        //No Header

        /*
        //Set a OnClickListener listener
        setOnClickListener(new OnCardClickListener() {
            @Override
            public void onClick(Card card, View view) {
                Toast.makeText(getContext(), "Click Listener card=", Toast.LENGTH_LONG).show();
            }
        });*/
    }

    @Override
    public void setupInnerViewElements(ViewGroup parent, View view) {

        //Retrieve elements
        mTitle = (TextView) parent.findViewById(R.id.carddemo_myapps_main_inner_title);
        mSecondaryTitle = (TextView) parent.findViewById(R.id.carddemo_myapps_main_inner_secondaryTitle);
        mRatingBar = (RatingBar) parent.findViewById(R.id.carddemo_myapps_main_inner_ratingBar);


        if (mTitle != null)
            mTitle.setText(R.string.demo_custom_card_google_maps);

        if (mSecondaryTitle != null)
            mSecondaryTitle.setText(R.string.demo_custom_card_googleinc);

        if (mRatingBar != null){
            mRatingBar.setNumStars(5);
            mRatingBar.setMax(5);
            mRatingBar.setStepSize(0.5f);
            mRatingBar.setRating(4.7f);
        }

    }
}
