/*
 * ******************************************************************************
 *   Copyright 2014 Jake Wharton
 *   Copyright (c) 2013-2014 Gabriele Mariotti.
 *
 *   Original file can be found here:
 *   https://github.com/JakeWharton/u2020/blob/master/src/main/java/com/jakewharton/u2020/data/api/model/Image.java
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

public final class Image {
  public final String id;

  public final String link;
  public final String title;

  public final int width;
  public final int height;
  public final long datetime;
  public final int views;


  public Image(String id, String link, String title, int width, int height, long datetime,
               int views) {
    this.id = id;
    this.link = link;
    this.title = title;
    this.width = width;
    this.height = height;
    this.datetime = datetime;
    this.views = views;
  }

}
