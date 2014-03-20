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

package it.gmariotti.cardslib.demo.extras.iabutils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import it.gmariotti.cardslib.demo.extras.MainActivity;
import it.gmariotti.cardslib.demo.extras.R;


/**
 * @author Gabriele Mariotti (gabri.mariotti@gmail.com)
 */
public class IabUtil {

    public static String key = "MY_KEY";

    public static String SMALL_BEER_SKU = "small_beer";
    public static String MEDIUM_BEER_SKU = "medium_beer";
    public static String LARGE_BEER_SKU = "large_beer";
    public static String XLARGE_BEER_SKU = "xlarge_beer";

    private static String TAG="IabUtil";

    private HashMap<String, DonationEntry> items;
    private static IabUtil sInstance;

    String p1="1.00€";
    String p2="2.00€";
    String p3="5.00€";
    String p4="10.00€";

    private IabUtil() {
        items = new HashMap<String, DonationEntry>();
        items.put(SMALL_BEER_SKU, new DonationEntry(SMALL_BEER_SKU, R.string.demo_item_small_beer, p1));
        items.put(MEDIUM_BEER_SKU, new DonationEntry(MEDIUM_BEER_SKU, R.string.demo_item_medium_beer, p2));
        items.put(LARGE_BEER_SKU, new DonationEntry(LARGE_BEER_SKU, R.string.demo_item_large_beer, p3));
        items.put(XLARGE_BEER_SKU, new DonationEntry(XLARGE_BEER_SKU, R.string.demo_item_xlarge_beer, p4));
    }

    public static IabUtil getInstance() {
        if (sInstance != null)
            return sInstance;
        else
            return sInstance = new IabUtil();
    }


    public class DonationEntry {

        public String sku;
        public int nameId;
        public String price;
        public boolean purchased;

        public DonationEntry(String sku, int nameId, String price) {
            this.sku = sku;
            this.nameId = nameId;
            this.price = price;
            purchased = false;
        }
    }

    /**
     * Retrieves data and consumes item purchase
     *
     * @param mHelper
     */
    public void retrieveData(final IabHelper mHelper) {

        if (mHelper == null) return;


        List additionalSkuList = new ArrayList();
        additionalSkuList.add(SMALL_BEER_SKU);
        additionalSkuList.add(MEDIUM_BEER_SKU);
        additionalSkuList.add(LARGE_BEER_SKU);
        additionalSkuList.add(XLARGE_BEER_SKU);

        IabHelper.QueryInventoryFinishedListener
                mQueryFinishedListener = new IabHelper.QueryInventoryFinishedListener() {
            public void onQueryInventoryFinished(IabResult result, Inventory inventory) {

                // Have we been disposed of in the meantime? If so, quit.
                if (mHelper == null) return;
                
                if (result==null || result.isFailure()) {
                    //Log.e(TAG,"Error refreshing items " +result);
                    // handle error
                    return;
                }

                boolean b1=false;
                boolean b2=false;
                boolean b3=false;
                boolean b4=false;

                if (inventory!=null){
                    SkuDetails d1 = inventory.getSkuDetails(SMALL_BEER_SKU);
                    if (d1!=null){
                        p1 = d1.getPrice();
                    }
                    b1 = inventory.hasPurchase(SMALL_BEER_SKU);
                }

                if (inventory!=null){
                    SkuDetails d2 = inventory.getSkuDetails(MEDIUM_BEER_SKU);
                    if (d2!=null){
                        p2 = d2.getPrice();
                    }
                    b2 = inventory.hasPurchase(MEDIUM_BEER_SKU);
                }

                if (inventory!=null){
                    SkuDetails d3 = inventory.getSkuDetails(LARGE_BEER_SKU);
                    if (d3!=null){
                        p3 = d3.getPrice();
                    }
                    b3 = inventory.hasPurchase(LARGE_BEER_SKU);
                }

                if (inventory!=null){
                    SkuDetails d4 = inventory.getSkuDetails(XLARGE_BEER_SKU);
                    if (d4!=null){
                        p4 = d4.getPrice();
                    }
                    b4 = inventory.hasPurchase(XLARGE_BEER_SKU);
                }

                // update data
                IabUtil iabutil = getInstance();
                if (iabutil != null) {
                    DonationEntry itemSmall = iabutil.items.get(SMALL_BEER_SKU);
                    if (itemSmall != null) {
                        itemSmall.purchased = b1;
                        itemSmall.price = p1;
                        items.put(SMALL_BEER_SKU, itemSmall);
                        //Log.i(TAG,"Price = "+p1);
                        if (b1) {
                            if (mHelper!=null && inventory!=null)
                                consumeItem(mHelper,SMALL_BEER_SKU,inventory.getPurchase(SMALL_BEER_SKU));
                        }
                    }

                    DonationEntry itemMedium = iabutil.items.get(MEDIUM_BEER_SKU);
                    if (itemMedium != null) {
                        itemMedium.purchased = b2;
                        itemMedium.price = p2;
                        items.put(MEDIUM_BEER_SKU, itemMedium);

                        if (b2) {
                            if (mHelper!=null  && inventory!=null)
                                consumeItem(mHelper,MEDIUM_BEER_SKU,inventory.getPurchase(MEDIUM_BEER_SKU));
                        }
                    }

                    DonationEntry itemLarge = iabutil.items.get(LARGE_BEER_SKU);
                    if (itemLarge != null) {
                        itemLarge.purchased = b3;
                        itemLarge.price = p3;
                        items.put(LARGE_BEER_SKU, itemLarge);

                        if (b3) {
                            if (mHelper!=null  && inventory!=null)
                                 consumeItem(mHelper,LARGE_BEER_SKU,inventory.getPurchase(LARGE_BEER_SKU));
                        }
                    }


                    DonationEntry itemxLarge = iabutil.items.get(XLARGE_BEER_SKU);
                    if (itemxLarge != null) {
                        itemxLarge.purchased = b4;
                        itemxLarge.price = p4;
                        items.put(XLARGE_BEER_SKU, itemxLarge);

                        if (b4) {
                            if (mHelper!=null  && inventory!=null)
                                consumeItem(mHelper,XLARGE_BEER_SKU,inventory.getPurchase(XLARGE_BEER_SKU));
                        }
                    }
                }
            }
        };

        try{
            mHelper.queryInventoryAsync(true, additionalSkuList,
                    mQueryFinishedListener);
        } catch(IllegalStateException il){
            Log.e("Purchase","Error ",il);
        } catch(NullPointerException ne){
            //it is bad, but it is due to a bug in IabHelper
            Log.e("Purchase","Error ",ne);
        }
    }


    public static void showBeer(Activity activity, IabHelper helper) {

        FragmentManager fm = activity.getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Fragment prev = fm.findFragmentByTag("dialog_purchase");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        new PurchaseDialog(helper).show(ft, "dialog_purchase");
    }

    /**
     * Purchase Dialog
     */
    public static class PurchaseDialog extends DialogFragment {

        private static final String VERSION_UNAVAILABLE = "N/A";

        private IabHelper mHelper;

        public PurchaseDialog(){
            if (mHelper==null && getActivity()!=null)
                mHelper= ((MainActivity)getActivity()).getHelper();
        };

        public PurchaseDialog(IabHelper mHelper) {
            this.mHelper = mHelper;
        }

        private void updateUI(){

        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {

            LayoutInflater layoutInflater = getActivity().getLayoutInflater();
            View rootView = layoutInflater.inflate(R.layout.demo_extras_purchase_about, null);

            if (mHelper==null)
                mHelper= ((MainActivity)getActivity()).getHelper();

            IabUtil iabUtil = getInstance();

            TextView nameAndVersionView = (TextView) rootView.findViewById(
                    R.id.purchase_app_name_and_version);
            nameAndVersionView.setText(Html.fromHtml(
                    getString(R.string.demo_purchase)));

            //final TextView errorMessage = (TextView) rootView.findViewById(R.id.purchase_error);

            final RelativeLayout lsmall = (RelativeLayout) rootView.findViewById(R.id.purchase_small_layout);
            TextView lsmallText = (TextView) rootView.findViewById(R.id.purchase_small_layout_text);
            lsmallText.setText(iabUtil.items.get(SMALL_BEER_SKU).nameId);
            TextView lsmallTextPrice = (TextView) rootView.findViewById(R.id.purchase_small_layout_text_price);
            lsmallTextPrice.setText(iabUtil.items.get(SMALL_BEER_SKU).price);
            if (!iabUtil.items.get(SMALL_BEER_SKU).purchased) {
                lsmall.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mHelper.launchPurchaseFlow(getActivity(), SMALL_BEER_SKU, 10001,
                                new IabHelper.OnIabPurchaseFinishedListener() {
                                    @Override
                                    public void onIabPurchaseFinished(IabResult result, Purchase info) {
                                        if (result.isFailure()) {
                                            Log.i(TAG,"Failure = "+result);
                                            //Toast.makeText(getActivity(), "Error purchasing: " + result, Toast.LENGTH_LONG).show();
                                            return;
                                        } else if (info.getSku().equals(SMALL_BEER_SKU)) {
                                            IabUtil.getInstance().items.get(SMALL_BEER_SKU).purchased = true;
                                            lsmall.setClickable(false);
                                            IabUtil.getInstance().retrieveData(mHelper);
                                            Toast.makeText(getActivity(), "Thank you!", Toast.LENGTH_LONG).show();
                                        }
                                    }
                                }, "bGoa+V7g/yqDXvKRqq+JTFn4uQZbPiQJo4pf9RzJ");
                    }
                });
            }

            final RelativeLayout lmedium = (RelativeLayout) rootView.findViewById(R.id.purchase_medium_layout);
            TextView lmediumText = (TextView) rootView.findViewById(R.id.purchase_medium_layout_text);
            lmediumText.setText(iabUtil.items.get(MEDIUM_BEER_SKU).nameId);
            TextView lmediumTextPrice = (TextView) rootView.findViewById(R.id.purchase_medium_layout_text_price);
            lmediumTextPrice.setText(iabUtil.items.get(MEDIUM_BEER_SKU).price);
            if (!iabUtil.items.get(MEDIUM_BEER_SKU).purchased) {
                lmedium.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mHelper.launchPurchaseFlow(getActivity(), MEDIUM_BEER_SKU, 10001,
                                new IabHelper.OnIabPurchaseFinishedListener() {
                                    @Override
                                    public void onIabPurchaseFinished(IabResult result, Purchase info) {
                                        if (result.isFailure()) {
                                            //Toast.makeText(getActivity(), "Error purchasing: " + result, Toast.LENGTH_LONG).show();
                                            return;
                                        } else if (info.getSku().equals(MEDIUM_BEER_SKU)) {
                                            IabUtil.getInstance().items.get(MEDIUM_BEER_SKU).purchased = true;
                                            lmedium.setClickable(false);
                                            IabUtil.getInstance().retrieveData(mHelper);
                                            Toast.makeText(getActivity(), "Thank you!", Toast.LENGTH_LONG).show();
                                        }
                                    }
                                }, "bGoa+V7g/yqDXvKRqq+JTFn4uQZbPiQJo4pf9RzJ");
                    }
                });
            }


            final RelativeLayout llarge = (RelativeLayout) rootView.findViewById(R.id.purchase_large_layout);
            TextView llargeText = (TextView) rootView.findViewById(R.id.purchase_large_layout_text);
            llargeText.setText(iabUtil.items.get(LARGE_BEER_SKU).nameId);
            TextView llargeTextPrice = (TextView) rootView.findViewById(R.id.purchase_large_layout_text_price);
            llargeTextPrice.setText(iabUtil.items.get(LARGE_BEER_SKU).price);
            if (!iabUtil.items.get(LARGE_BEER_SKU).purchased) {
                llarge.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mHelper.launchPurchaseFlow(getActivity(), LARGE_BEER_SKU, 10001,
                                new IabHelper.OnIabPurchaseFinishedListener() {
                                    @Override
                                    public void onIabPurchaseFinished(IabResult result, Purchase info) {
                                        if (result.isFailure()) {
                                            //Toast.makeText(getActivity(), "Error purchasing: " + result, Toast.LENGTH_LONG).show();
                                            return;
                                        } else if (info.getSku().equals(LARGE_BEER_SKU)) {
                                            IabUtil.getInstance().items.get(LARGE_BEER_SKU).purchased = true;
                                            llarge.setClickable(false);
                                            IabUtil.getInstance().retrieveData(mHelper);
                                            Toast.makeText(getActivity(), "Thank you!", Toast.LENGTH_LONG).show();
                                        }
                                    }
                                }, "bGoa+V7g/yqDXvKRqq+JTFn4uQZbPiQJo4pf9RzJ");
                    }
                });
            }

            final RelativeLayout lxlarge = (RelativeLayout) rootView.findViewById(R.id.purchase_xlarge_layout);
            TextView lxlargeText = (TextView) rootView.findViewById(R.id.purchase_xlarge_layout_text);
            lxlargeText.setText(iabUtil.items.get(XLARGE_BEER_SKU).nameId);
            TextView lxlargeTextPrice = (TextView) rootView.findViewById(R.id.purchase_xlarge_layout_text_price);
            lxlargeTextPrice.setText(iabUtil.items.get(XLARGE_BEER_SKU).price);
            if (!iabUtil.items.get(XLARGE_BEER_SKU).purchased) {
                lxlarge.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mHelper.launchPurchaseFlow(getActivity(), XLARGE_BEER_SKU, 10001,
                                new IabHelper.OnIabPurchaseFinishedListener() {
                                    @Override
                                    public void onIabPurchaseFinished(IabResult result, Purchase info) {
                                        if (result.isFailure()) {
                                            //Toast.makeText(getActivity(), "Error purchasing: " + result, Toast.LENGTH_LONG).show();
                                            return;
                                        } else if (info.getSku().equals(XLARGE_BEER_SKU)) {
                                            IabUtil.getInstance().items.get(XLARGE_BEER_SKU).purchased = true;
                                            lxlarge.setClickable(false);
                                            IabUtil.getInstance().retrieveData(mHelper);
                                            Toast.makeText(getActivity(), "Thank you!", Toast.LENGTH_LONG).show();
                                        }
                                    }
                                }, "bGoa+V7g/yqDXvKRqq+JTFn4uQZbPiQJo4pf9RzJ");
                    }
                });
            }

            return new AlertDialog.Builder(getActivity())
                    //.setTitle(R.string.title_about)
                    .setView(rootView)
                    .setPositiveButton(R.string.purchase_cancel,
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                    dialog.dismiss();
                                    IabUtil.getInstance().retrieveData(mHelper);
                                }
                            }
                    )
                    .create();
        }
    }


    private void setItemConsumed(String keyItem){

        IabUtil iabutil = getInstance();
        if (iabutil != null) {
            DonationEntry itemSmall = iabutil.items.get(keyItem);
            if (itemSmall != null) {
                itemSmall.purchased = false;
                items.put(keyItem, itemSmall);
            }
        }
    }

    private void consumeItem(IabHelper helper,final String keyItem,Purchase purchase){

        if (helper==null) return;

        helper.consumeAsync(purchase,
                new IabHelper.OnConsumeFinishedListener() {
            @Override
            public void onConsumeFinished(Purchase purchase, IabResult result) {
                setItemConsumed(keyItem);
            }
        });
    }

}
