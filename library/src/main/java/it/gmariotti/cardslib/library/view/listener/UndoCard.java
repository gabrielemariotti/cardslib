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

package it.gmariotti.cardslib.library.view.listener;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * FIXME
 *
 *
 * @author Gabriele Mariotti (gabri.mariotti@gmail.com)
 */
public class UndoCard implements Parcelable{

    public int[] itemPosition;
    public String[] itemId;

    public UndoCard(int[] itemPosition,String[] itemId) {
        this.itemPosition = itemPosition;
        this.itemId= itemId;
    }

    protected UndoCard(Parcel in) {

    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {

    }

    public static final Parcelable.Creator<UndoCard> CREATOR = new Parcelable.Creator<UndoCard>() {
        public UndoCard createFromParcel(Parcel in) {
            return new UndoCard(in);
        }

        public UndoCard[] newArray(int size) {
            return new UndoCard[size];
        }
    };
}
