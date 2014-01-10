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
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
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
 * This class provides a simple example as Google Play card.
 * The Google maps icon this time is loaded from package manager.
 *
 * @author Gabriele Mariotti (gabri.mariotti@gmail.com), Ronald Ammann (ramdroid.fn@gmail.com)
 */
public class GplayCardCustomSource extends Card {

    public GplayCardCustomSource(Context context) {
        super(context, R.layout.carddemo_gplay_inner_content);
        init();
    }

    public GplayCardCustomSource(Context context, int innerLayout) {
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

        CardThumbnail thumbnail = new CardThumbnail(getContext());
        thumbnail.setCustomSource(new CardThumbnail.CustomSource() {
            @Override
            public String getTag() {
                return "com.google.android.apps.maps";
            }

            @Override
            public Bitmap getBitmap() {
                PackageManager pm = mContext.getPackageManager();
                Bitmap bitmap = null;
                try {
                    bitmap = drawableToBitmap(pm.getApplicationIcon(getTag()));
                } catch (PackageManager.NameNotFoundException e) {
                }
                return bitmap;
            }

            private Bitmap drawableToBitmap(Drawable drawable) {
                if (drawable instanceof BitmapDrawable) {
                    return ((BitmapDrawable) drawable).getBitmap();
                }

                Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(bitmap);
                drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
                drawable.draw(canvas);

                return bitmap;
            }
        });
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


}
