/*
 * ******************************************************************************
 *   Copyright 2014 Google Inc. All rights reserved.
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
import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Outline;
import android.os.Build;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.widget.ImageButton;

import it.gmariotti.cardslib.demo.R;

@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class LPreviewUtilsBase {

    protected ActionBarActivity mActivity;
    private ActionBarDrawerToggle mDrawerToggle;
    private Toolbar mActionBarToolbar;

    LPreviewUtilsBase(ActionBarActivity activity) {
        mActivity = activity;
    }

    public static LPreviewUtilsBase getInstance(ActionBarActivity activity) {
        return new LPreviewUtilsBase(activity);
    }

    public boolean hasL() {
        return  Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
    }

    //----------------------------------------------------------------------------
    // ActionBar
    //----------------------------------------------------------------------------

    public boolean shouldChangeActionBarForDrawer() {
        return false;
    }

    //----------------------------------------------------------------------------
    // Circle Button
    //----------------------------------------------------------------------------

    public void setupCircleButton(ImageButton sourceButton) {
       if (hasL()){
           if (sourceButton != null){
               final int size = mActivity.getResources().getDimensionPixelSize(R.dimen.hd_fab_size);
               sourceButton.setOutlineProvider(
               new ViewOutlineProvider(){
                   @Override
                   public void getOutline(View view, Outline outline) {
                       outline.setOval(0, 0, size, size);
                   }
               });
               sourceButton.setClipToOutline(true);
           }
       }
    }
    //----------------------------------------------------------------------------
    // Navigation Drawer
    //----------------------------------------------------------------------------

    public ActionBarDrawerToggle setupDrawerToggle(DrawerLayout drawerLayout,
                                                   final DrawerLayout.DrawerListener drawerListener){
        mDrawerToggle = new ActionBarDrawerToggle(
                mActivity,                  /* host Activity */
                drawerLayout,          /* DrawerLayout object */
                mActionBarToolbar,     /* nav drawer icon to replace 'Up' caret */
                R.string.app_name,  /* "open drawer" description */
                R.string.app_name  /* "close drawer" description */
        );

        drawerLayout.setDrawerListener(mDrawerToggle);
        return mDrawerToggle;
    }

    //----------------------------------------------------------------------------
    // Methods
    //----------------------------------------------------------------------------


    public void startActivityWithTransition(Intent intent, final View clickedView,
                                            final String transitionName) {
        ActivityOptions options = null;
//        if (hasL() && clickedView != null && !TextUtils.isEmpty(transitionName)) {
//            options = ActivityOptions.makeSceneTransitionAnimation(
//                    mActivity, clickedView, transitionName);
//        }

        mActivity.startActivity(intent, (options != null) ? options.toBundle() : null);
    }


    public int getStatusBarColor() {
        if (!hasL()) {
            // On pre-L devices, you can have any status bar color so long as it's black.
            return Color.BLACK;
        }

        return mActivity.getWindow().getStatusBarColor();
    }

    public void setStatusBarColor(int color) {
        if (!hasL()) {
            return;
        }

        mActivity.getWindow().setStatusBarColor(color);
    }

    public void setViewElevation(View v, float elevation) {
        if (hasL()) {
            v.setElevation(elevation);
        }
    }


}
