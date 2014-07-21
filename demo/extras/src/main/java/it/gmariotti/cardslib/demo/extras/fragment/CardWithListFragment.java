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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.survivingwithandroid.weather.lib.WeatherClient;
import com.survivingwithandroid.weather.lib.WeatherClientDefault;
import com.survivingwithandroid.weather.lib.WeatherConfig;
import com.survivingwithandroid.weather.lib.exception.WeatherLibException;
import com.survivingwithandroid.weather.lib.model.WeatherForecast;
import com.survivingwithandroid.weather.lib.provider.IWeatherProvider;
import com.survivingwithandroid.weather.lib.provider.WeatherProviderFactory;
import com.survivingwithandroid.weather.lib.provider.openweathermap.OpenweathermapProviderType;

import it.gmariotti.cardslib.demo.extras.R;
import it.gmariotti.cardslib.demo.extras.cards.GoogleKnowwithList;
import it.gmariotti.cardslib.demo.extras.cards.WeatherCard;
import it.gmariotti.cardslib.library.view.CardView;

/**
 * Card Examples
 *
 * @author Gabriele Mariotti (gabri.mariotti@gmail.com)
 */
public class CardWithListFragment extends BaseFragment {

    private WeatherConfig config;
    private WeatherClient client;
    WeatherCard card;

    @Override
    public int getTitleResourceId() {
        return R.string.carddemo_extras_title_carwithlist_card;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.demo_extras_fragment_cardwithlist_card, container, false);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);


        //Provider and Weather Client config
        client = WeatherClientDefault.getInstance();
        client.init(getActivity().getApplicationContext());

        Log.d("App", "Client [" + client + "]");

        // Let's create the WeatherProvider
        config = new WeatherConfig();
        config.lang = "en"; // If you want to use english
        config.unitSystem = WeatherConfig.UNIT_SYSTEM.M;
        config.numDays = 6; // Max num of days in the forecast


        IWeatherProvider provider = null;
        try {
            provider = WeatherProviderFactory.createProvider(new OpenweathermapProviderType(), config);
            client.setProvider(provider);
        }
        catch (Throwable t) {
            t.printStackTrace();
            // There's a problem
        }
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initCard();

        //ROMA-IT cityId=3169070
        String romaCityId="3169070";
        client.getForecastWeather(romaCityId, new WeatherClient.ForecastWeatherEventListener() {
            @Override
            public void onWeatherRetrieved(WeatherForecast forecast) {
                card.updateForecast(forecast);
            }

            @Override
            public void onWeatherError(WeatherLibException t) {
                card.updateProgressBar(true,true);
                Toast.makeText(getActivity(),"Error on connection...",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onConnectionError(Throwable t) {
                card.updateProgressBar(true,true);
                Toast.makeText(getActivity(),"Error on connection...",Toast.LENGTH_SHORT).show();
            }
        });
        card.updateProgressBar(false,false);
    }

    /**
     * This method builds a simple card
     */
    private void initCard() {

        //Weather Card
        card= new WeatherCard(getActivity());
        card.init();

        //Set card in the cardView
        CardView cardView = (CardView) getActivity().findViewById(R.id.carddemo_weathercard);
        cardView.setCard(card);


        //May know card
        GoogleKnowwithList card2= new GoogleKnowwithList(getActivity());
        card2.init();

        //Set card in the cardView
        CardView cardView2 = (CardView) getActivity().findViewById(R.id.carddemo_mayknowcard);
        cardView2.setCard(card2);
    }

}
