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

package it.gmariotti.cardslib.demo.extras.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;
import com.nostra13.universalimageloader.utils.StorageUtils;

import java.io.File;
import java.util.ArrayList;

import it.gmariotti.cardslib.demo.extras.R;
import it.gmariotti.cardslib.demo.extras.cards.UniversalImageLoaderCard;
import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardArrayAdapter;
import it.gmariotti.cardslib.library.view.CardListView;

/**
 * This example uses a list of cards with Thumbnail loaded with built-in method and Android-Universal-Image-Loader library
 * https://github.com/nostra13/Android-Universal-Image-Loader
 *
 * @author Gabriele Mariotti (gabri.mariotti@gmail.com)
 */
public class UniversalImageLoaderFragment extends BaseFragment {

    DisplayImageOptions options;

    @Override
    public int getTitleResourceId() {
        return R.string.carddemo_extras_title_universal_image_loader;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.demo_extras_fragment_picasso, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initUniversalImageLoaderLibrary();
        initCard();
    }

    /**
     * Android-Universal-Image-Loader config.
     *
     * DON'T COPY THIS CODE TO YOUR REAL PROJECT!     *
     * I would recommend doing it in an overloaded Application.onCreate().
     * It is just for test purpose
     *
     *
     */
    private void initUniversalImageLoaderLibrary(){

        File cacheDir = StorageUtils.getCacheDirectory(getActivity());
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getActivity())
                .memoryCache(new LruMemoryCache(2 * 1024 * 1024))
                .defaultDisplayImageOptions(DisplayImageOptions.createSimple())
                .writeDebugLogs()
                .build();

        ImageLoader.getInstance().init(config);

        options = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .displayer(new SimpleBitmapDisplayer())
                .showImageOnFail(R.drawable.ic_error_loadingsmall)
                .build();
    }


    /**
     * This method builds a simple card
     */
    private void initCard() {

        //Init an array of Cards
        ArrayList<Card> cards = new ArrayList<Card>();
        for (int i = 0; i < 200; i++) {
            UniversalImageLoaderCard card = new UniversalImageLoaderCard(this.getActivity(),options);
            card.setTitle("A simple card loaded with Universal-Image-Loader " + i);
            card.setSecondaryTitle("Simple text..." + i);
            card.setCount(i);
            cards.add(card);
        }

        CardArrayAdapter mCardArrayAdapter = new CardArrayAdapter(getActivity(), cards);

        CardListView listView = (CardListView) getActivity().findViewById(R.id.carddemo_extra_list_picasso);
        if (listView != null) {
            listView.setAdapter(mCardArrayAdapter);
        }

    }

}
