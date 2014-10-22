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

package it.gmariotti.cardslib.demo.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import it.gmariotti.cardslib.demo.R;
import it.gmariotti.cardslib.demo.ui.BaseActivity;
import it.gmariotti.cardslib.demo.utils.LPreviewUtils;
import it.gmariotti.cardslib.demo.utils.LPreviewUtilsBase;

/**
 * @author Gabriele Mariotti (gabri.mariotti@gmail.com)
 */
public abstract class BaseMaterialFragment extends BaseFragment {

    private View mHeaderBox;
    private View mHeaderContentBox;
    private View mHeaderBackgroundBox;
    private View mHeaderShadow;

    TextView mTitleHeader;
    TextView mSubTitleHeader;

    ImageButton mSourceButton;
    ImageButton mDocButton;

    private int colorResId = -1;
    
    @Override
    public int getTitleResourceId() {
        return TITLE_NONE;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*
        final Intent intent = BaseActivity.fragmentArgumentsToIntent(getArguments());
        colorResId = intent.getIntExtra(DemoSingleTopicActivity.EXTRA_FRAGMENT_COLOR, -1);
        */
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupBarHeader(view);
        setupBarButton(view);

        //((BaseActivity) getActivity()).getLPreviewUtils().setViewName(mHeaderBox, NativeDashFragment.VIEW_COLOR);
    }

    protected void setupBarHeader(View rootView){
        mHeaderBox = rootView.findViewById(R.id.header_recap);
        mHeaderContentBox = rootView.findViewById(R.id.header_contents);
        mHeaderBackgroundBox = rootView.findViewById(R.id.header_background);
        mHeaderShadow = rootView.findViewById(R.id.header_shadow);
        
        mTitleHeader = (TextView) rootView.findViewById(R.id.header_title);
        mSubTitleHeader = (TextView) rootView.findViewById(R.id.header_subtitle);
    }

    protected void setupBarButton(View rootView) {
        mSourceButton = (ImageButton) rootView.findViewById(R.id.bar_button_source);
        //mDocButton = (ImageButton) rootView.findViewById(R.id.bar_button_doc);

        final String sourceUrl = getSourceUrl();
        final String docUrl = getDocUrl();

        if (mSourceButton != null) {

            ((BaseActivity) getActivity()).getLPreviewUtils().setupCircleButton(mSourceButton);

            if (sourceUrl == null) {
                mSourceButton.setEnabled(false);
            } else {

                mSourceButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent i = new Intent(Intent.ACTION_VIEW);
                        i.setData(Uri.parse(sourceUrl));
                        startActivity(i);
                    }
                });
            }
        }

        if (mDocButton != null) {
            if (docUrl == null) {
                mDocButton.setEnabled(false);
            } else {

                mDocButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent i = new Intent(Intent.ACTION_VIEW);
                        i.setData(Uri.parse(docUrl));
                        startActivity(i);
                    }
                });
            }
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        LPreviewUtilsBase lpu = LPreviewUtils.getInstance(((BaseActivity)getActivity()));
        getActivity().getWindow().getDecorView().setBackgroundColor(getResources().getColor(R.color.native_background));

        float mMaxHeaderElevation = getResources().getDimensionPixelSize(
                R.dimen.carddemo_barheader_elevation);

        if (mHeaderShadow != null)
            mHeaderShadow.setVisibility(lpu.hasL() ? View.GONE : View.VISIBLE);
        if (mHeaderBackgroundBox != null)
            lpu.setViewElevation(mHeaderBackgroundBox,  mMaxHeaderElevation);
        if (mHeaderContentBox != null)
          lpu.setViewElevation(mHeaderContentBox,  mMaxHeaderElevation + 0.1f);

        if (mTitleHeader != null)
            mTitleHeader.setText(getString(getTitleHeaderResourceId()));

        if (mSubTitleHeader != null)
            mSubTitleHeader.setText(getString(getSubTitleHeaderResourceId()));


        if (colorResId != -1) {
            // make sure it's opaque
            //((mSessionColor = UIUtils.setColorAlpha(mSessionColor, 255);

            mHeaderBackgroundBox.setBackgroundColor(getResources().getColor(colorResId));
            ((BaseActivity) getActivity()).getLPreviewUtils().setStatusBarColor(
                    scaleColor(getResources().getColor(colorResId), 0.8f, false));
        }
    }

    public static int scaleColor(int color, float factor, boolean scaleAlpha) {
        return Color.argb(scaleAlpha ? (Math.round(Color.alpha(color) * factor)) : Color.alpha(color),
                Math.round(Color.red(color) * factor), Math.round(Color.green(color) * factor),
                Math.round(Color.blue(color) * factor));
    }


    protected abstract int getSubTitleHeaderResourceId();

    protected abstract int getTitleHeaderResourceId();

    protected abstract String getDocUrl();

    protected abstract String getSourceUrl();
}
