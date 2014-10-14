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



package it.gmariotti.cardslib.demo.extras.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityOptions;
import android.app.SharedElementListener;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Outline;
import android.os.Build;
import android.support.v4.widget.DrawerLayout;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toolbar;

import java.util.List;
import java.util.Map;

import it.gmariotti.cardslib.demo.extras.R;


@TargetApi(Build.VERSION_CODES.L)
public class LPreviewUtilsImpl extends LPreviewUtilsBase {

    private ActionBarDrawerToggleWrapper mDrawerToggleWrapper;
    private DrawerLayout mDrawerLayout;
    private Toolbar mActionBarToolbar;

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

    //----------------------------------------------------------------------------
    // ActionBar
    //----------------------------------------------------------------------------

    @Override
    public void trySetActionBar() {
        mActionBarToolbar = (Toolbar) mActivity.findViewById(R.id.toolbar_actionbar);
        if (mActionBarToolbar != null) {
            mActivity.setActionBar(mActionBarToolbar);
        }
    }

    @Override
    public void showHideActionBarIfPartOfDecor(boolean show) {
        if (mActionBarToolbar != null) {
            // Action bar is part of the layout
            return;
        }

        // Action bar is part of window decor
        super.showHideActionBarIfPartOfDecor(show);
    }

    public boolean shouldChangeActionBarForDrawer() {
        return false;
    }

    //----------------------------------------------------------------------------
    // Circle Button
    //----------------------------------------------------------------------------

    public void setupCircleButton(ImageButton sourceButton) {
        if (sourceButton != null){
            int size = mActivity.getResources().getDimensionPixelSize(R.dimen.hd_fab_size);
            Outline outline = new Outline();
            outline.setOval(0, 0, size, size);
            sourceButton.setOutline(outline);
            sourceButton.setClipToOutline(true);
        }
    }

    //----------------------------------------------------------------------------
    // Navigation Drawer
    //----------------------------------------------------------------------------

    @Override
    public ActionBarDrawerToggleWrapper setupDrawerToggle(DrawerLayout drawerLayout, DrawerLayout.DrawerListener drawerListener) {

        // On L, use a different drawer indicator
        if (mActionBarToolbar != null) {
            mActionBarToolbar.setNavigationIcon(R.drawable.ic_navigation_drawer);
            mActionBarToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mDrawerLayout.isDrawerOpen(Gravity.START)) {
                        mDrawerLayout.closeDrawer(Gravity.START);
                    } else {
                        mDrawerLayout.openDrawer(Gravity.START);
                    }
                }
            });
        } else {
            mActivity.getActionBar().setHomeAsUpIndicator(R.drawable.ic_navigation_drawer);
        }
        // On L, stub out the ActionBarDrawerToggle
        mDrawerLayout = drawerLayout;
        mDrawerLayout.setDrawerListener(drawerListener);
        mDrawerToggleWrapper = new ActionBarDrawerToggleWrapper();
        return mDrawerToggleWrapper;
    }

    public class ActionBarDrawerToggleWrapper extends LPreviewUtilsBase.ActionBarDrawerToggleWrapper {
        public void syncState() {
        }

        public void onConfigurationChanged(Configuration newConfig) {
        }

        public boolean onOptionsItemSelected(MenuItem item) {
            // Toggle drawer
            if (item.getItemId() == android.R.id.home) {
                if (mDrawerLayout.isDrawerOpen(Gravity.START)) {
                    mDrawerLayout.closeDrawer(Gravity.START);
                } else {
                    mDrawerLayout.openDrawer(Gravity.START);
                }
                return true;
            }
            return false;
        }
    }

    //----------------------------------------------------------------------------
    // Methods
    //----------------------------------------------------------------------------

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
