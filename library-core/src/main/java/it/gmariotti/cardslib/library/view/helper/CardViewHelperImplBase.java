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
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.v4.view.ViewCompat;
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

    @Override
    public void setElevation(View view, float elevation) {
        ViewCompat.setElevation(view, elevation);
    }

    @Override
    public Drawable getResourceFromAttrs(Context themedContext, int attr){
        // Create an array of the attributes we want to resolve
        // using values from a theme
        int[] attrs = new int[] { attr /* index 0 */};

        // Obtain the styled attributes. 'themedContext' is a context with a
        // theme, typically the current Activity (i.e. 'this')
        TypedArray ta = themedContext.obtainStyledAttributes(attrs);

        // To get the value of the 'listItemBackground' attribute that was
        // set in the theme used in 'themedContext'. The parameter is the index
        // of the attribute in the 'attrs' array. The returned Drawable
        // is what you are after
        Drawable drawableFromTheme = ta.getDrawable(0 /* index */);

        // Finally, free the resources used by TypedArray
        ta.recycle();

        return drawableFromTheme;
    }

}
