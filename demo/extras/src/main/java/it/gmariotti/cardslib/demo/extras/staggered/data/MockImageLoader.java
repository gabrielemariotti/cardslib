/*
 * ******************************************************************************
 *   Copyright 2014 Jake Wharton
 *   Copyright (c) 2013-2014 Gabriele Mariotti.
 *
 *   Original file can be found here:
 *   https://github.com/JakeWharton/u2020/blob/master/src/debug/java/com/jakewharton/u2020/data/api/model/MockImageLoader.java
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

import android.app.Application;
import android.content.res.AssetManager;
import android.graphics.BitmapFactory;

/**
 *
 */
public final class MockImageLoader {

    private final AssetManager assetManager;

    protected static MockImageLoader mInstance;

    /**
     * Singleton
     */
    public static MockImageLoader getInstance(Application application){
        if (mInstance==null)
            mInstance = new MockImageLoader(application);
        return  mInstance;
    }

    MockImageLoader(Application application) {
        assetManager = application.getAssets();
    }


    /**
     * A filename like {@code abc123.jpg} inside the {@code mock/images/} asset folder.
     */
    public ImageBuilder newImage(String filename) {

        String path = "images/" + filename;

        int width;
        int height;
        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;

            BitmapFactory.decodeStream(assetManager.open(path), null, options);

            width = options.outWidth;
            height = options.outHeight;
        } catch (Exception e) {
            throw new RuntimeException("Unable to load " + filename, e);
        }

        String id = filename.substring(0, filename.lastIndexOf('.'));
        String link = "file:///android_asset/" + path;
        return new ImageBuilder(id, link, id /* default title == id */, width, height);
    }


    public static class ImageBuilder {
        private final String id;
        private final String link;
        private final int width;
        private final int height;
        private String title;
        private long datetime = System.currentTimeMillis();
        private int views;


        private ImageBuilder(String id, String link, String title, int width, int height) {
            this.id = id;
            this.link = link;
            this.title = title;
            this.width = width;
            this.height = height;
        }

        public ImageBuilder title(String title) {
            this.title = title;
            return this;
        }

        public ImageBuilder datetime(int datetime) {
            this.datetime = datetime;
            return this;
        }

        public ImageBuilder views(int views) {
            this.views = views;
            return this;
        }

        public Image build() {
            return new Image(id, link, title, width, height, datetime, views);
        }
    }
}
