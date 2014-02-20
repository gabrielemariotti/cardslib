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

package it.gmariotti.cardslib.library.internal;

import android.content.Context;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;

import it.gmariotti.cardslib.library.R;
import it.gmariotti.cardslib.library.internal.base.BaseCard;

/**
 * Card Header Model.
 * </p>
 * You can customize this component. See https://github.com/gabrielemariotti/cardslib/tree/master/doc/HEADER.md for more information.
 * </p>
 * You can fully customize this component with:
 * <ul>
 * <li>using your innerLayout in constructor</li>
 * <li>setting your elements with {@link #setupInnerViewElements(android.view.ViewGroup, android.view.View)} method.</li>
 *</ul>
 * </p>
 * <b>Usage:</b>
 * <pre><code>
 *       //Create a Card
 *       Card card = new Card(getContext());
 *
 *       //Create a CardHeader
 *       CardHeader header = new CardHeader(getContext());
 *
 *       //Add header to card
 *       card.addCardHeader(header);
 *
 * </code></pre>
 *
 * </p>
 * You can customize buttons behaviour with {@link #setButtonOverflowVisible(boolean)} ,{@link #setButtonExpandVisible(boolean)}, {@link #setOtherButtonVisible(boolean)}
 * </p>
 * You can attach a Popup Menu to overflowMenu with {@link #setPopupMenu(int, CardHeader.OnClickCardHeaderPopupMenuListener)}
 * <pre><code>
 * header.setPopupMenu(R.menu.popupmain, new CardHeader.OnClickCardHeaderPopupMenuListener(){
 *
 *       public void onMenuItemClick(BaseCard card, MenuItem item) {
 *                 mPopupMenuListener.doSomething(...);
 *       }
 *  });
 * </code></pre>
 * </p>
 * You can attach a {@link CardHeader.OnClickCardHeaderOtherButtonListener} to button to listen any callback when
 * other button is clicked.
 * <pre><code>
 *     header.setOtherButtonClickListener(new CardHeader.OnClickCardHeaderOtherButtonListener() {
 *
 *         public void onButtonItemClick(Card card, View view) {
 *                //do something...
 *         }
 *      });
 * </code></pre>
 * </p>
 * Use your style and drawable files to customize buttons style.
 * </p>
 * Also you can use a different header layout using a custom attr in main card layout.
 * <pre><code>
 *
 *  <it.gmariotti.cardslib.library.view.component.CardHeaderView
 *       android:id="@+id/card_header_layout"
 *       android:layout_width="match_parent"
 *       android:layout_height="wrap_content"
 *       card:card_header_layout_resourceID="@layout/my_header_layout" />
 * </code></pre>
 * </p>
 * @author Gabriele Mariotti (gabri.mariotti@gmail.com)
 */
public class CardHeader extends BaseCard {

    /**
     * Indicates to make visible the Button to expand/collapse
     */
    protected boolean mIsButtonExpandVisible=false;

    /**
     * Indicates to make visible the Overflow Button to open a popup menu
     */
    protected boolean mIsButtonOverflowVisible=false;

    /**
     * Indicates to make visible another Button
     */
    protected boolean mIsOtherButtonVisible=false;

    /**
     *  Resource ID for PopMenu
     */
    protected int mPopupMenu=NO_POPUP_MENU;

    /**
     * Listener invoked when a item in PopupMenu is clicked
     */
    protected OnClickCardHeaderPopupMenuListener mPopupMenuListener;

    /**
     * Listener invoked when the PopupMenu being prepared
     */
    protected OnPrepareCardHeaderPopupMenuListener mPopupMenuPrepareListener;

    /**
     * Listener invoked when Other Button is clicked
     */
    protected OnClickCardHeaderOtherButtonListener mOtherButtonClickListener;


    /**
     * Drawable used by otherButton.
     * </p>
     * Otherwise you can use this your xml style.
     * <pre>
     * <code>
     *  <style name="card.header_button_base.other">
     *      <item name="android:background">@drawable/card_menu_button_other</item>
     *  </style>
     *  </code>
     *  </pre>
     */
    protected int mOtherButtonDrawable=0;

    /**
     * Use this value to remove popup on overflow button
     */
    public static int NO_POPUP_MENU = -1;


    /**
     * Interface for custom overflow animation
     */
    public interface CustomOverflowAnimation {

        /**
         * @return the bitmap from custom source
         */
       void doAnimation(Card card,View imageOverflow);
    }

    protected CustomOverflowAnimation mCustomOverflowAnimation = null;

    /**
     * Indicates if overflow icon is selected
     */
    protected boolean mIsOverflowSelected=false;

    // -------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------

    /**
     * Constructor with a base inner layout defined by R.layout.inner_base_header
     *
     * @param context  context
     */
    public CardHeader(Context context) {
        this(context,R.layout.inner_base_header);
    }

    /**
     * Constructor with a custom inner layout.
     *
     * @param context context
     * @param innerLayout   layout resource ID
     */
    public CardHeader(Context context,int innerLayout) {
        super(context);
        mInnerLayout= innerLayout;
    }


    // -------------------------------------------------------------
    // Interfaces
    // -------------------------------------------------------------

    /**
     * Interface to handle any callback when a item in popup menu is clicked
     */
    public interface OnClickCardHeaderPopupMenuListener {
        public void onMenuItemClick(BaseCard card, MenuItem item);
    }

    /**
     * Interface to handle the callback when a popup menu being prepared
     */
    public interface OnPrepareCardHeaderPopupMenuListener {

        /**
         * This is called right before the menu is shown.
         * You can use this method to efficiently enable/disable items or otherwise
         * dynamically modify the contents.
         *
         * @param card
         * @param popupMenu
         *
         * @return You must return true for the menu to be displayed;
         *          if you return false it will not be shown.
         */
        public boolean onPreparePopupMenu(BaseCard card, PopupMenu popupMenu);
    }

    /**
     * Interface to handle callbacks when ExpandButton is clicked
     * Currently is never used
     */
    public interface OnClickExpandListener {
        public void onButtonExpandClick(BaseCard card, MenuItem item);
    }

    /**
     * Interface to handle callbacks when Other Button is clicked
     */
    public interface OnClickCardHeaderOtherButtonListener {
        public void onButtonItemClick(Card card, View view);
    }


    // -------------------------------------------------------------
    // Popup Menu
    // -------------------------------------------------------------

    /**
     * Sets a popup menu for overflow button.
     * <p/>
     * Setting the menu resource to {@linl NO_POPUP_MENU} disables the menu for this card.
     *
     * @param menuRes  The menu resource ID to use for the card's popup menu.
     * @param listener A listener invoked when an option in the popup menu is tapped by the user.
     * @param prepareListener A listener invoked to change dynamically menu items.
     */
    public void setPopupMenu(int menuRes, OnClickCardHeaderPopupMenuListener listener, OnPrepareCardHeaderPopupMenuListener prepareListener) {
        mPopupMenu = menuRes;
        mPopupMenuListener = listener;
        mPopupMenuPrepareListener = prepareListener;

        if (menuRes==NO_POPUP_MENU){
            mIsButtonOverflowVisible=false;
            listener=null;
        }else{
            mIsButtonOverflowVisible=true;
        }
    }

    /**
     * Sets a popup menu for overflow button.
     * <p/>
     * Setting the menu resource to {@linl NO_POPUP_MENU} disables the menu for this card.
     *
     * @param menuRes  The menu resource ID to use for the card's popup menu.
     * @param listener A listener invoked when an option in the popup menu is tapped by the user.
     */
    public void setPopupMenu(int menuRes, OnClickCardHeaderPopupMenuListener listener) {
        setPopupMenu(menuRes, listener, null);
    }

    // -------------------------------------------------------------
    // Other Button
    // -------------------------------------------------------------



    // -------------------------------------------------------------
    // CustomOverflowAnimator
    // -------------------------------------------------------------


    /**
     * Returns the custom animation on Overflow icon
     *
     * @return the listener
     */
    public CustomOverflowAnimation getCustomOverflowAnimation() {
        return  mCustomOverflowAnimation;
    }

    /**
     * Sets the listener for a custom animation on Overflow icon
     *
     * @param customAnimation
     */
    public void setCustomOverflowAnimation(CustomOverflowAnimation customAnimation) {
        this.mCustomOverflowAnimation = customAnimation;

        if (mCustomOverflowAnimation==null){
            mIsButtonOverflowVisible=false;
        }else{
            mIsButtonOverflowVisible=true;
        }
    }


    public boolean isOverflowSelected() {
        return mIsOverflowSelected;
    }

    public void setOverflowSelected(boolean isOverflowSelected) {
        mIsOverflowSelected = isOverflowSelected;
    }



    // -------------------------------------------------------------
    // Inner View
    // -------------------------------------------------------------

    /**
     * Inflates the inner layout and adds to parent layout.
     * Then calls {@link #setupInnerViewElements(android.view.ViewGroup, android.view.View)} method
     * to setup all values.
     * </p>
     * You can provide your custom layout.
     * You can use a xml layout with {@link CardHeader#setInnerLayout(int)} or with constructor
     * {@link CardHeader#CardHeader(android.content.Context, int)}.
     *
     * Then customize #setupInnerViewElements to set your values.
     *
     * @param context context
     * @param parent  Inner Layout
     * @return view
     */
    @Override
    public View getInnerView(Context context, ViewGroup parent) {

        View view= super.getInnerView(context, parent);

        //This provide a simple implementation with a single title
        if (view!=null){
            //Add inner view to parent
            parent.addView(view);

            //Setup value
            if (mInnerLayout>-1 ){
                setupInnerViewElements(parent,view);
            }
        }
        return view;
    }

    // -------------------------------------------------------------
    // Setup Inner view Elements
    // -------------------------------------------------------------

    /**
     * This method sets values to header elements and customizes view.
     *
     * Override this method to set your elements inside InnerView.
     *
     * @param parent  parent view (Inner Frame)
     * @param view   Inner View
     */
    @Override
    public void setupInnerViewElements(ViewGroup parent,View view){

        //Add simple title to header
        if (view!=null){
            TextView mTitleView=(TextView) view.findViewById(R.id.card_header_inner_simple_title);
            if (mTitleView!=null)
                mTitleView.setText(mTitle);
        }

    }

    // -------------------------------------------------------------
    //  Getters and Setters
    // -------------------------------------------------------------

    /**
     * Return the popup listener invoked when a item in PopupMenu is clicked
     *
     * @return  popup listener
     */
    public OnClickCardHeaderPopupMenuListener getPopupMenuListener() {
        return mPopupMenuListener;
    }

    /**
     * Return the popup prepare listener invoked when the PopupMenu is being prepared
     *
     * @return  popup listener
     */
    public OnPrepareCardHeaderPopupMenuListener getPopupMenuPrepareListener() {
        return mPopupMenuPrepareListener;
    }

    /**
     * Sets the popup listener invoked when a item in PopupMenu is clicked
     *
     * @param popupMenuListener  popup listener
     */
    public void setPopupMenuListener(OnClickCardHeaderPopupMenuListener popupMenuListener) {
        mPopupMenuListener = popupMenuListener;
    }

    /**
     * Sets the popup listener invoked when the PopupMenu is being prepared
     *
     * @param popupMenuListener  popup prepare listener
     */
    public void setPopupMenuPrepareListener(OnPrepareCardHeaderPopupMenuListener popupMenuListener) {
        mPopupMenuPrepareListener = popupMenuListener;
    }

    /**
     * Indicates if expand/collapse button is visible
     *
     * @return <code>true</code> if the button is visible
     */
    public boolean isButtonExpandVisible() {
        return mIsButtonExpandVisible;
    }

    /**
     * Sets visibility to expand/collapse button
     *
     * @param buttonExpandVisible
     */
    public void setButtonExpandVisible(boolean buttonExpandVisible) {
        mIsButtonExpandVisible = buttonExpandVisible;
    }

    /**
     * Indicates if overflow button is visible.
     *
     * @return <code>true</code> if the button is visible
     */
    public boolean isButtonOverflowVisible() {

        return mIsButtonOverflowVisible;
    }

    /**
     * Sets visibility to overflow button
     *
     * @param buttonOverflowVisible
     */
    public void setButtonOverflowVisible(boolean buttonOverflowVisible) {
        mIsButtonOverflowVisible = buttonOverflowVisible;
    }

    /**
     * Returns Resource ID for Popup menu
     *
     * @return resource ID for Popup Menu
     */
    public int getPopupMenu() {
        return mPopupMenu;
    }

    /**
     * Indicates if other button is visible.
     * If any {@link #mOtherButtonClickListener} is assigned return in any case <code>false</code>
     *
     * @return <code>true</code> if other button is visible and {@link #mOtherButtonClickListener} is assigned
     */
    public boolean isOtherButtonVisible() {
        if (mOtherButtonClickListener == null) {
            if (mIsOtherButtonVisible)
                Log.w("CardHeader", "You set visible=true to other button menu, but you don't add any listener");
            return false;
        }
        return mIsOtherButtonVisible;
    }

    /**
     * Sets visibility to other button
     *
     * @param isOtherButtonVisible
     */
    public void setOtherButtonVisible(boolean isOtherButtonVisible) {
        mIsOtherButtonVisible = isOtherButtonVisible;
    }

    /**
     * Returns listener invoked when Other Button is clicked
     *
     * @return listener
     */
    public OnClickCardHeaderOtherButtonListener getOtherButtonClickListener() {
        return mOtherButtonClickListener;
    }

    /**
     * Sets listener invoked when Other Button is clicked
     *
     * @param otherButtonClickListener listener
     */
    public void setOtherButtonClickListener(OnClickCardHeaderOtherButtonListener otherButtonClickListener) {
        mOtherButtonClickListener = otherButtonClickListener;
    }

    /**
     * Returns resource drawable ID for Other Button
     *
     * @return resource ID
     */
    public int getOtherButtonDrawable() {
        return mOtherButtonDrawable;
    }

    /**
     * Set resource drawable ID for other Button
     * You can customize this button also with your styles.xml file using:
     * <pre><code>
     *     <style name="card.header_button_base.other">
     *      <item name="android:background">@drawable/card_menu_button_other</item>
     *     </style>
     * </code></pre>
     *
     * @param otherButtonDrawable
     */
    public void setOtherButtonDrawable(int otherButtonDrawable) {
        mOtherButtonDrawable = otherButtonDrawable;
    }


}
