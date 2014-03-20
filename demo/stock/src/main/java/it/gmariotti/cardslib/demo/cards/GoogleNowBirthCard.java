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
import android.graphics.Bitmap;
import android.os.Build;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import it.gmariotti.cardslib.demo.R;
import it.gmariotti.cardslib.demo.drawable.CircleDrawable;
import it.gmariotti.cardslib.demo.drawable.RoundCornersDrawable;
import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardExpand;
import it.gmariotti.cardslib.library.internal.CardHeader;
import it.gmariotti.cardslib.library.internal.CardThumbnail;

/**
 * This class provides a simple card as Google Now Birthday
 *
 * @author Gabriele Mariotti (gabri.mariotti@gmail.com)
 */
public class GoogleNowBirthCard extends Card {

    public int USE_VIGNETTE=0;

    public GoogleNowBirthCard(Context context) {
        super(context,R.layout.carddemo_googlenowbirth_inner_main);
        init();
    }

    public GoogleNowBirthCard(Context context, int innerLayout) {
        super(context, innerLayout);
        init();
    }

    private void init() {

        //Add Header
        GoogleNowBirthHeader header = new GoogleNowBirthHeader(getContext(), R.layout.carddemo_googlenowbirth_inner_header);
        header.setButtonExpandVisible(true);
        header.mName = "Gabriele Mariotti";
        header.mSubName = "Birthday today";
        addCardHeader(header);

        //Add Expand Area
        CardExpand expand = new GoogleNowExpandCard(getContext());
        addCardExpand(expand);

        //Set clickListener
        setOnClickListener(new OnCardClickListener() {
            @Override
            public void onClick(Card card, View view) {
                Toast.makeText(getContext(), "Click Listener card", Toast.LENGTH_LONG).show();
            }
        });

        //Add Thumbnail
        GoogleNowBirthThumb thumbnail = new GoogleNowBirthThumb(getContext());
        float density = getContext().getResources().getDisplayMetrics().density;
        int size= (int)(125*density);
        thumbnail.setUrlResource("https://lh5.googleusercontent.com/-squZd7FxR8Q/UyN5UrsfkqI/AAAAAAAAbAo/VoDHSYAhC_E/s"+size+"/new%2520profile%2520%25282%2529.jpg");
        thumbnail.setErrorResource(R.drawable.ic_ic_error_loading);
        addCardThumbnail(thumbnail);
    }

    @Override
    public void setupInnerViewElements(ViewGroup parent, View view) {

        TextView title = (TextView) view.findViewById(R.id.card_main_inner_simple_title);
        title.setText("Wish Happy Birthday");
        title.setTextColor(mContext.getResources().getColor(R.color.carddemo_googlenowbirth_title));
        title.setGravity(Gravity.CENTER_VERTICAL);

    }


    class GoogleNowBirthThumb extends CardThumbnail {

        public GoogleNowBirthThumb(Context context) {
            super(context);
        }

        @Override
        public void setupInnerViewElements(ViewGroup parent, View viewImage) {
            /*
            viewImage.getLayoutParams().width = 250;
            viewImage.getLayoutParams().height = 250;
            */
        }

        @Override
        public boolean applyBitmap(View imageView, Bitmap bitmap) {
            switch (USE_VIGNETTE){
                case  0:
                    return false;
                case 1:
                    //RounderImage
                    int CORNER_RADIUS = 12; // dips
                    //int MARGIN = 12; // dips

                    float density = getContext().getResources().getDisplayMetrics().density;
                    int mCornerRadius = (int) (CORNER_RADIUS * density + 0.5f);
                    int mMargin = 0;

                    RoundCornersDrawable round = new RoundCornersDrawable(bitmap,mCornerRadius , mMargin);
                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
                        imageView.setBackground(round);
                    else
                        imageView.setBackgroundDrawable(round);
                    return true;
                case 2:

                    CircleDrawable circle = new CircleDrawable(bitmap,true);
                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
                        imageView.setBackground(circle);
                    else
                        imageView.setBackgroundDrawable(circle);
                    return true;
                default:
                    return false;
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
