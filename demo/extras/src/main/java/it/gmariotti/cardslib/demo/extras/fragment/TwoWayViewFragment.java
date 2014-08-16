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

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import it.gmariotti.cardslib.demo.extras.MainActivity;
import it.gmariotti.cardslib.demo.extras.R;
import it.gmariotti.cardslib.demo.extras.fragment.twowayview.List2wayFragment;
import it.gmariotti.cardslib.demo.extras.fragment.twowayview.SpannableGrid2wayFragment;
import it.gmariotti.cardslib.demo.extras.fragment.twowayview.StaggeredGrid2wayFragment;

/**
 * @author Gabriele Mariotti (gabri.mariotti@gmail.com)
 */
public class TwoWayViewFragment extends BaseFragment {

    @Override
    public int getTitleResourceId() {
        return R.string.carddemo_extras_title_base_twowayview;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.demo_extras_fragment_2wayview, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ImageView img1 = (ImageView) view.findViewById(R.id.carddemo_image_ic_staggered);
        if (img1 != null){
            img1.setColorFilter(setColorAlpha(getResources().getColor(R.color.demo_2way_blue_transparent), 0.75f));
        }

        ImageView img2 = (ImageView) view.findViewById(R.id.carddemo_image_ic_spannable);
        if (img2 != null){
            img2.setColorFilter(setColorAlpha(getResources().getColor(R.color.demo_2way_green_transparent), 0.75f));
        }

        ImageView img3 = (ImageView) view.findViewById(R.id.carddemo_image_ic_grid);
        if (img3 != null){
            img3.setColorFilter(setColorAlpha(getResources().getColor(R.color.demo_2way_red_transparent), 0.75f));
        }

        ImageView img4 = (ImageView) view.findViewById(R.id.carddemo_image_ic_list);
        if (img4 != null){
            img4.setColorFilter(setColorAlpha(getResources().getColor(R.color.demo_2way_yellow_transparent), 0.75f));
        }

        view.findViewById(R.id.onClickStaggered).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentHelper(new StaggeredGrid2wayFragment());
            }
        });

        view.findViewById(R.id.onClickList).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentHelper(new List2wayFragment());
            }
        });

        view.findViewById(R.id.onClickSpannable).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentHelper(new SpannableGrid2wayFragment());
            }
        });
    }



    public void onClickSpannable(View v){

    }

    public void onClickGrid(View v){

    }


    private void fragmentHelper(BaseFragment fragment){

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_main_extras, fragment);
        fragmentTransaction.commit();

        if (fragment.getTitleResourceId() > 0)
            ((MainActivity)getActivity()).mCurrentTitle = fragment.getTitleResourceId();
    }

    public static int setColorAlpha(int color, float alpha) {
        int alpha_int = Math.min(Math.max((int)(alpha * 255.0f), 0), 255);
        return Color.argb(alpha_int, Color.red(color), Color.green(color), Color.blue(color));
    }
}
