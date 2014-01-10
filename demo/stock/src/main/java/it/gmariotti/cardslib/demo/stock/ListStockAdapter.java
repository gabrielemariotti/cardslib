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

package it.gmariotti.cardslib.demo.stock;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import it.gmariotti.cardslib.demo.R;

/**
 * @author Gabriele Mariotti (gabri.mariotti@gmail.com)
 */
public class ListStockAdapter extends ArrayAdapter<Stock> {


    public ListStockAdapter(Context context, List<Stock> objects) {
        super(context, 0, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Stock item = getItem(position);
        //Without ViewHolder for demo purpose
        View view = convertView;
        if (view == null) {
            LayoutInflater li =
                    (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = li.inflate(R.layout.carddemo_googlenowstock_list_item, parent, false);
        }
        TextView textView1 = (TextView) view.findViewById(R.id.textView1);
        TextView textView2 = (TextView) view.findViewById(R.id.textView2);
        TextView textView3 = (TextView) view.findViewById(R.id.textView3);
        TextView textView4 = (TextView) view.findViewById(R.id.textView4);

        textView1.setText(item.code);
        textView2.setText("" + item.value);
        textView3.setText("" + item.delta);
        textView4.setText("" + item.deltaPerc + "%");


        return view;
    }


    @Override
    public int getViewTypeCount() {
        return 1;
    }
}