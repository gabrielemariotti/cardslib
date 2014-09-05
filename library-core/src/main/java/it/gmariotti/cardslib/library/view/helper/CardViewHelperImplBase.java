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

package it.gmariotti.cardslib.library.view.helper;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageButton;

/**
 * @author Gabriele Mariotti (gabri.mariotti@gmail.com)
 */
public class CardViewHelperImplBase implements CardViewHelper {

    protected Context mContext;

    public CardViewHelperImplBase(Context context){
        mContext = context;
    }

    @Override
    public void setBackground(View view, Drawable drawable) {
       if (view != null)
            view.setBackgroundDrawable(drawable);
    }

    @Override
    public void setButtonBackground(ImageButton imageButton, int buttonDrawableResource) {
        setBackground(imageButton, mContext.getResources().getDrawable(buttonDrawableResource));
    }

    @Override
    public void setCardSelector(View viewClickable, Drawable defaultDrawable) {
        setBackground(viewClickable, defaultDrawable);
    }


}
