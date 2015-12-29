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

package it.gmariotti.cardslib.library.internal;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Gabriele Mariotti (gabri.mariotti@gmail.com)
 */
public class BaseGroupExpandableCard<T> extends Card{

    protected List<T> children = new ArrayList<T>();

    //--------------------------------------------------------------------------
    // Constructors
    //--------------------------------------------------------------------------

    public BaseGroupExpandableCard(Context context,List<T> children) {
        super(context);
        this.children = children;
    }

    public BaseGroupExpandableCard(Context context, int innerLayout,List<T> children) {
        super(context, innerLayout);
        this.children = children;
    }

}
