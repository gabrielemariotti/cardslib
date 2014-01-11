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
import android.widget.Toast;

import it.gmariotti.cardslib.demo.R;
import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardThumbnail;

/**
 * This class provides a simple card as Google Play
 *
 * @author Gabriele Mariotti (gabri.mariotti@gmail.com)
 */
public class GooglePlaySmallCard extends Card {

    protected TextView mTitle;
    protected TextView mSecondaryTitle;
    protected RatingBar mRatingBar;

    public GooglePlaySmallCard(Context context) {
        this(context, R.layout.carddemo_mycard_inner_content);
    }

    public GooglePlaySmallCard(Context context, int innerLayout) {
        super(context, innerLayout);
        init();
    }

    private void init() {

        //Add thumbnail
        CardThumbnail cardThumbnail = new CardThumbnail(mContext);
        cardThumbnail.setDrawableResource(R.drawable.carddemo_ic_gmaps);
        addCardThumbnail(cardThumbnail);

        //Add ClickListener
        setOnClickListener(new OnCardClickListener() {
            @Override
            public void onClick(Card card, View view) {
                Toast.makeText(getContext(), "Click Listener card=", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void setupInnerViewElements(ViewGroup parent, View view) {

        //Retrieve elements
        mTitle = (TextView) parent.findViewById(R.id.carddemo_myapps_main_inner_title);
        mSecondaryTitle = (TextView) parent.findViewById(R.id.carddemo_myapps_main_inner_secondaryTitle);
        mRatingBar = (RatingBar) parent.findViewById(R.id.carddemo_myapps_main_inner_ratingBar);

        if (mTitle != null)
            mTitle.setText("Google Maps");

        if (mSecondaryTitle != null)
            mSecondaryTitle.setText("Google Inc.");

        if (mRatingBar != null) {
            mRatingBar.setNumStars(5);
            mRatingBar.setMax(5);
            mRatingBar.setStepSize(0.5f);
            mRatingBar.setRating(4.7f);
        }

    }
}
