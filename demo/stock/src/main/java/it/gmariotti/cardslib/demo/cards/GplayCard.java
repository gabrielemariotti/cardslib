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
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import it.gmariotti.cardslib.demo.R;
import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardHeader;
import it.gmariotti.cardslib.library.internal.CardThumbnail;
import it.gmariotti.cardslib.library.internal.base.BaseCard;

/**
 * This class provides a simple example as Google Play card
 *
 * @author Gabriele Mariotti (gabri.mariotti@gmail.com)
 */
public class GplayCard extends Card {

    public GplayCard(Context context) {
        super(context, R.layout.carddemo_gplay_inner_content);
        init();
    }

    public GplayCard(Context context, int innerLayout) {
        super(context, innerLayout);
        init();
    }

    private void init() {
        CardHeader header = new CardHeader(getContext());
        header.setButtonOverflowVisible(true);
        header.setTitle("Google Maps");
        header.setPopupMenu(R.menu.popupmain, new CardHeader.OnClickCardHeaderPopupMenuListener() {
            @Override
            public void onMenuItemClick(BaseCard card, MenuItem item) {
                Toast.makeText(getContext(), item.getTitle(), Toast.LENGTH_SHORT).show();
            }
        });

        addCardHeader(header);

        GoogleNowBirthThumb thumbnail = new GoogleNowBirthThumb(getContext());
        thumbnail.setDrawableResource(R.drawable.carddemo_ic_gmaps_large);
        addCardThumbnail(thumbnail);
    }

    @Override
    public void setupInnerViewElements(ViewGroup parent, View view) {

        TextView title = (TextView) view.findViewById(R.id.carddemo_gplay_main_inner_title);
        title.setText("FREE");

        TextView subtitle = (TextView) view.findViewById(R.id.carddemo_gplay_main_inner_subtitle);
        subtitle.setText("Very popular with...");

        RatingBar mRatingBar = (RatingBar) parent.findViewById(R.id.carddemo_gplay_main_inner_ratingBar);

        mRatingBar.setNumStars(5);
        mRatingBar.setMax(5);
        mRatingBar.setStepSize(0.5f);
        mRatingBar.setRating(4.7f);
    }

    class GoogleNowBirthThumb extends CardThumbnail {

        public GoogleNowBirthThumb(Context context) {
            super(context);
        }

        @Override
        public void setupInnerViewElements(ViewGroup parent, View viewImage) {


            //viewImage.getLayoutParams().width = 196;
            //viewImage.getLayoutParams().height = 196;
            if (viewImage != null) {
                if (parent!=null && parent.getResources()!=null){
                    DisplayMetrics metrics=parent.getResources().getDisplayMetrics();

                    int base = 98;

                    if (metrics!=null){
                        viewImage.getLayoutParams().width = (int)(base*metrics.density);
                        viewImage.getLayoutParams().height = (int)(base*metrics.density);
                    }else{
                        viewImage.getLayoutParams().width = 196;
                        viewImage.getLayoutParams().height = 196;
                    }
                }
            }

        }
    }

    class GoogleNowBirthHeader extends CardHeader {

        String mName;
        String mSubName;

        public GoogleNowBirthHeader(Context context, int innerLayout) {
            super(context, innerLayout);
        }

        @Override
        public void setupInnerViewElements(ViewGroup parent, View view) {

            TextView txName = (TextView) view.findViewById(R.id.text_birth1);
            TextView txSubName = (TextView) view.findViewById(R.id.text_birth2);

            txName.setText(mName);
            txSubName.setText(mSubName);
        }
    }
}
