/*
 * ******************************************************************************
 *   Copyright 2014 Jake Wharton
 *   Copyright (c) 2013-2014 Gabriele Mariotti.
 *
 *   Original file can be found here:
 *   https://github.com/JakeWharton/u2020/blob/master/src/debug/java/com/jakewharton/u2020/data/api/ServerDatabase.java
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


import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public final class ServerDatabase {

    private final MockImageLoader mockImageLoader;
    private final Map<Section, List<Image>> imagesBySection = new LinkedHashMap<Section, List<Image>>();

    private boolean initialized;

    public ServerDatabase(MockImageLoader mockImageLoader) {
        this.mockImageLoader = mockImageLoader;
    }

    private synchronized void initializeMockData() {
        if (initialized) return;
        initialized = true;

        List<Image> hotImages = new ArrayList<Image>();
        imagesBySection.put(Section.STAG, hotImages);

        hotImages.add(mockImageLoader.newImage("hill.jpg") //
                .title("Hill") //
                .views(4000) //
                .build()); //
        hotImages.add(mockImageLoader.newImage("snow.jpg") //
                .title("Snow") //
                .views(854) //
                .build());
        hotImages.add(mockImageLoader.newImage("sea.jpg") //
                .title("Sea") //
                .build());
        hotImages.add(mockImageLoader.newImage("water.jpg") //
                .title("waterfalls") //
                .build());
        hotImages.add(mockImageLoader.newImage("mountain.jpg") //
                .title("Mountain") //
                .views(2000) //
                .build());
        hotImages.add(mockImageLoader.newImage("mountain2.jpg") //
                .title("Mountain 1") //
                .build());
        hotImages.add(mockImageLoader.newImage("img2.jpg") //
                .title("Swans") //
                .views(3040) //
                .build());
        hotImages.add(mockImageLoader.newImage("rose.jpg") //
                .title("Rose") //
                .build());
        /*
        hotImages.add(mockImageLoader.newImage("DOGE-10.jpg") //
                .title("Many OkHttp") //
                .views(1500) //
                .build());
        hotImages.add(mockImageLoader.newImage("DOGE-16.jpg") //
                .title("Wow Retrofit") //
                .views(3000) //
                .build());*/
    }

    public List<Image> getImagesForSection(Section section) {
        initializeMockData();
        return imagesBySection.get(section);
    }
}
