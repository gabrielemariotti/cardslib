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

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.view.View;

public class LPreviewUtilsBase {
    protected Activity mActivity;

    LPreviewUtilsBase(Activity activity) {
        mActivity = activity;
    }

    public boolean hasLPreviewAPIs() {
        return false;
    }

    public void startActivityWithTransition(Intent intent, View clickedView,
            String sharedElementName) {
        mActivity.startActivity(intent);
    }

    public void setViewName(View v, String viewName) {
        // Can't do this pre-L
    }

    public void postponeEnterTransition() {
        // Can't do this pre-L
    }

    public void startPostponedEnterTransition() {
        // Can't do this pre-L
    }

    public int getStatusBarColor() {
        // On pre-L devices, you can have any status bar color so long as it's black.
        return Color.BLACK;
    }

    public void setStatusBarColor(int color) {
        // Only black.
    }

    public void setViewElevation(View v, float elevation) {
        // Can't do this pre-L
    }


}
