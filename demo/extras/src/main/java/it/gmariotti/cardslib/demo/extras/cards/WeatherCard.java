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

package it.gmariotti.cardslib.demo.extras.cards;

import android.content.Context;
import android.os.SystemClock;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.survivingwithandroid.weather.lib.model.DayForecast;
import com.survivingwithandroid.weather.lib.model.Weather;
import com.survivingwithandroid.weather.lib.model.WeatherForecast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import it.gmariotti.cardslib.demo.extras.R;
import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardHeader;
import it.gmariotti.cardslib.library.internal.base.BaseCard;
import it.gmariotti.cardslib.library.prototypes.CardWithList;
import it.gmariotti.cardslib.library.prototypes.LinearListView;

/**
 * @author Gabriele Mariotti (gabri.mariotti@gmail.com)
 */
public class WeatherCard extends CardWithList {

    private Weather.WeatherUnit units;
    private final static SimpleDateFormat sdfDay = new SimpleDateFormat("E");
    private final static SimpleDateFormat sdfMonth = new SimpleDateFormat("dd/MMM");

    public WeatherCard(Context context) {
        super(context);
    }

    @Override
    protected CardHeader initCardHeader() {

        //Add Header
        CardHeader header = new CardHeader(getContext());
        //Add a popup menu. This method set OverFlow button to visible
        header.setPopupMenu(R.menu.extras_popupmain, new CardHeader.OnClickCardHeaderPopupMenuListener() {
            @Override
            public void onMenuItemClick(BaseCard card, MenuItem item) {
                Toast.makeText(getContext(), "Click on " + item.getTitle(), Toast.LENGTH_SHORT).show();
            }
        });
        header.setTitle("ROMA (IT)"); //should use R.string.
        return header;
    }

    @Override
    protected void initCard() {

        setSwipeable(true);
        setOnSwipeListener(new OnSwipeListener() {
            @Override
            public void onSwipe(Card card) {
                Toast.makeText(getContext(), "Swipe on " + card.getCardHeader().getTitle(), Toast.LENGTH_SHORT).show();
            }
        });

        //Provide a custom view for the ViewStud EmptyView
        setEmptyViewViewStubLayoutId(R.layout.carddemo_extras_base_withlist_empty);

        setUseProgressBar(true);
    }



    @Override
    protected List<ListObject> initChildren() {
        //The default list is empty
        return null;
    }

    @Override
    public View setupChildView(int childPosition, ListObject object, View convertView, ViewGroup parent) {

        //Setup the elements inside each row
        TextView dayText = (TextView) convertView.findViewById(R.id.carddemo_weather_dayName);
        TextView dayDate = (TextView) convertView.findViewById(R.id.carddemo_weather_dayDate);
        ImageView icon = (ImageView) convertView.findViewById(R.id.carddemo_weather_dayIcon);
        TextView minTempText = (TextView) convertView.findViewById(R.id.carddemo_weather_dayTempMin);
        TextView maxTempText = (TextView) convertView.findViewById(R.id.carddemo_weather_dayTempMax);
        TextView dayDescr = (TextView) convertView.findViewById(R.id.carddemo_weather_dayDescr);

        WeatherObject weatherObject= (WeatherObject)object;
        Date d = new Date();
        Calendar gc =  new GregorianCalendar();
        gc.setTime(d);
        gc.add(GregorianCalendar.DAY_OF_MONTH, childPosition + 1);
        dayText.setText(sdfDay.format(gc.getTime()));
        dayDate.setText(sdfMonth.format(gc.getTime()));

        icon.setImageResource(WeatherIconMapper.getWeatherResource(weatherObject.mDayForecast.weather.currentCondition.getIcon(), weatherObject.mDayForecast.weather.currentCondition.getWeatherId()));
        Log.d("SwA", "Min [" + minTempText + "]");

        minTempText.setText( Math.round(weatherObject.mDayForecast.forecastTemp.min) + units.tempUnit);
        maxTempText.setText( Math.round(weatherObject.mDayForecast.forecastTemp.max) + units.tempUnit);
        dayDescr.setText(weatherObject.mDayForecast.weather.currentCondition.getDescr());

        return convertView;
    }

    @Override
    public int getChildLayoutId() {
        return R.layout.carddemo_extras_weather_inner_main;
    }


    /**
     * This method receives the data from the forecast service and updates the adapter to display the list inside the card.
     * @param forecast
     */
    public void updateForecast(WeatherForecast forecast) {
        SystemClock.sleep(1000);

        //Update the data
        if (forecast == null) return;
        List<DayForecast> dayForecastList = forecast.getForecast();

        units = forecast.getUnit();

        //Update the array inside the card
        ArrayList<WeatherObject> objs = new ArrayList<WeatherObject>();
        for (DayForecast dayForecast:dayForecastList){
            WeatherObject weatherObject = new WeatherObject(this,dayForecast);
            objs.add(weatherObject);
        }
        getLinearListAdapter().addAll(objs);
        updateProgressBar(true,true);
    }


    // -------------------------------------------------------------
    // Weather Object
    // -------------------------------------------------------------

    public class WeatherObject extends DefaultListObject {

        public DayForecast mDayForecast;

        public WeatherObject(Card parentCard,DayForecast forecast) {
            super(parentCard);
            mDayForecast = forecast;
            init();
        }

        private void init() {
            setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(LinearListView parent, View view, int position, ListObject object) {
                    Toast.makeText(getContext(), "Click on " + mDayForecast.getStringDate(), Toast.LENGTH_SHORT).show();
                }
            });
        }

    }

    /**
     * Utility class used to map the icon
     */
    public static class WeatherIconMapper {

        public static int getWeatherResource(String id, int wId) {
            // Log.d("App", "Id ["+id+"]");
            if (wId == 500)
                return R.drawable.w500d;

            if (wId == 501)
                return R.drawable.w501d;

            if (wId == 212)
                return R.drawable.w212d;

            if (id.equals("01d"))
                return R.drawable.w01d;
            else if (id.equals("01n"))
                return R.drawable.w01n;
            else if (id.equals("02d") || id.equals("02n"))
                return R.drawable.w02d;
            else if (id.equals("03d") || id.equals("03n"))
                return R.drawable.w03d;
            else if (id.equals("03d") || id.equals("03n"))
                return R.drawable.w03d;
            else if (id.equals("04d") || id.equals("04n"))
                return R.drawable.w04d;
            else if (id.equals("09d") || id.equals("09n"))
                return R.drawable.w500d;
            else if (id.equals("10d") || id.equals("10n"))
                return R.drawable.w501d;
            else if (id.equals("11d") || id.equals("11n"))
                return R.drawable.w212d;
            else if (id.equals("13d") || id.equals("13n"))
                return R.drawable.w13d;
            else if (id.equals("50d") || id.equals("50n"))
                return R.drawable.w50d;


            return R.drawable.w01d;

        }

    }


}
