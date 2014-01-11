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

package it.gmariotti.cardslib.library.internal.multichoice;

/**
 * @author Gabriele Mariotti (gabri.mariotti@gmail.com)
 */
public abstract class OptionMultiChoice{

    /**
     * You can customize the way the adapter behaves when an item is clicked and the action mode was already active.
     * True changes the selection state of the clicked item, just as if it had been long clicked. This is what the native MULTICHOICE_MODAL mode of ListView does.
     * False uses the onClickListener defined on the clicked item
     */
    protected boolean selectItemClickInActionMode = true;

    public boolean isSelectItemClickInActionMode() {
        return selectItemClickInActionMode;
    }

}
