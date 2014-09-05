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
import android.os.Build;

/**
 * @author Gabriele Mariotti (gabri.mariotti@gmail.com)
 */
public class CardViewHelperUtil {


    public static CardViewHelper getInstance(Context context){

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
            return new CardViewHelperImplL(context);
        } else if (Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT) {
            return new CardViewHelperImplKK(context);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            return new CardViewHelperImplJB(context);
        } else {
            return new CardViewHelperImplBase(context);
        }
    }


}
