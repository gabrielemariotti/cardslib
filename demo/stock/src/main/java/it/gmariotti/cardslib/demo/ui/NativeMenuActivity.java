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

package it.gmariotti.cardslib.demo.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import it.gmariotti.cardslib.demo.R;
import it.gmariotti.cardslib.demo.fragment.NativeDashFragment;

/**
 * @author Gabriele Mariotti (gabri.mariotti@gmail.com)
 */
public class NativeMenuActivity extends BaseActivity implements NativeDashFragment.Callbacks {

    @Override
    protected int getSelfNavDrawerItem() {
        return NAVDRAWER_ITEM_NATIVE_CARDSLIB;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (isFinishing()) {
            return;
        }

        setContentView(R.layout.carddemo_activity_native);

        if (getSupportActionBar() != null)
            getSupportActionBar().setTitle(R.string.navdrawer_item_native_cardslib);

        if (null == savedInstanceState) {
            getFragmentManager().beginTransaction()
                    .replace(R.id.container, NativeDashFragment.newInstance())
                    .commit();
        }

        overridePendingTransition(0, 0);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //invalidateOptionsMenu();
    }

    @Override
    public void onTopicSelected(NativeDashFragment.MenuEntry menuEntry, View clickedView) {

        Intent i = new Intent(this,DemoSingleTopicActivity.class);
        i.putExtra(DemoSingleTopicActivity.EXTRA_FRAGMENT_NAME,menuEntry.mClass.getName());
        i.putExtra(Intent.EXTRA_TITLE,getString(R.string.navdrawer_item_native_cardslib));
        i.putExtra(DemoSingleTopicActivity.EXTRA_FRAGMENT_COLOR,menuEntry.colorId);

        getLPreviewUtils().startActivityWithTransition(
                i,
                clickedView,
                NativeDashFragment.VIEW_COLOR
        );
    }
}
