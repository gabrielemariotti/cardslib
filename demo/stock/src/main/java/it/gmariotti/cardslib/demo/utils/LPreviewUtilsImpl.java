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

package it.gmariotti.cardslib.demo.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityOptions;
import android.app.SharedElementListener;
import android.content.Intent;
import android.os.Build;
import android.text.TextUtils;
import android.view.View;

import java.util.List;
import java.util.Map;

@TargetApi(Build.VERSION_CODES.L)
public class LPreviewUtilsImpl extends LPreviewUtilsBase {

    LPreviewUtilsImpl(Activity activity) {
        super(activity);
    }

    @Override
    public void setViewElevation(View v, float elevation) {
        v.setElevation(elevation);
    }

    @Override
    public boolean hasLPreviewAPIs() {
        return true;
    }

    public void startActivityWithTransition(Intent intent, final View clickedView,
            final String sharedElementName) {
        ActivityOptions options = null;
        if (clickedView != null && !TextUtils.isEmpty(sharedElementName)) {
            options = ActivityOptions.makeSceneTransitionAnimation(
                    mActivity, clickedView, sharedElementName);
        }

        mActivity.setExitSharedElementListener(new SharedElementListener() {
            @Override
            public void remapSharedElements(List<String> names, Map<String, View> sharedElements) {
                super.remapSharedElements(names, sharedElements);
                sharedElements.put(sharedElementName, clickedView);
            }
        });

        mActivity.startActivity(intent, (options != null) ? options.toBundle() : null);
    }

    @Override
    public int getStatusBarColor() {
        return mActivity.getWindow().getStatusBarColor();
    }

    @Override
    public void setStatusBarColor(int color) {
        mActivity.getWindow().setStatusBarColor(color);
    }


}
