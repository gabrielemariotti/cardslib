/*
 * ******************************************************************************
 *   Copyright 2014 Jake Wharton
 *   Copyright (c) 2013-2014 Gabriele Mariotti.
 *
 *   Original file can be found here:
 *   https://github.com/JakeWharton/u2020/blob/master/src/main/java/com/jakewharton/u2020/data/api/Section.java
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

package it.gmariotti.cardslib.demo.extras.staggered.data;

public enum Section {
    STAG("stag");
    //TOP("top"),
    //USER("user");

    public final String value;

    Section(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
