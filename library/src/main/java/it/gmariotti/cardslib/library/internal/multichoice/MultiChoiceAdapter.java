/*
 * ******************************************************************************
 *   Copyright (c) 2013 Gabriele Mariotti.
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

import android.view.View;
import android.widget.AdapterView;

/**
 * @author Gabriele Mariotti (gabri.mariotti@gmail.com)
 */
public interface MultiChoiceAdapter {

    /*
    void save(Bundle outState);

    void setCardChecked(long position, boolean checked);

    Set<Long> getCheckedCards();

    int getCheckedCardCount();

    boolean isChecked(long position);
*/
    boolean isCardCheckable(int position);


    boolean isActionModeStarted();

    void onItemClick(AdapterView<?> parent, View view, int position, long id);
}
