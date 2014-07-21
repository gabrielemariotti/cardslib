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

package it.gmariotti.cardslib.library.prototypes;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.SoundEffectConstants;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;

import it.gmariotti.cardslib.library.R;
import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardHeader;

/**
 * A card with a LinearList inside.
 * It is a particular Card that can display items inside the Card.
 *
 * @author Gabriele Mariotti (gabri.mariotti@gmail.com)
 */
@SuppressWarnings({"JavaDoc", "UnusedDeclaration"})
public abstract class CardWithList extends Card {

    /**
     * The cardHeader
     */
    protected CardHeader mCardHeader;

    /**
     * The "listView"
     */
    protected LinearListView mListView;

    /**
     * The listAdapter
     */
    protected LinearListAdapter mLinearListAdapter;

    /**
     * Default layout used for each row
     */
    protected int mChildLayoutId;

    /**
     * Empty View
     */
    private View mEmptyView;

    /**
     * Progress View
     */
    private View mProgressView;

    /**
     * Resource Id used which identifies the empty view
     */
    protected int emptyViewId = R.id.card_inner_base_empty_cardwithlist;

    /**
     * An identifier for the layout resource to inflate when the ViewStub becomes visible
     */
    protected int emptyViewViewStubLayoutId = R.layout.base_withlist_empty;

    /**
     * Resource Id used which identifies the progressBar
     */
    protected int progressBarId = R.id.card_inner_base_progressbar_cardwithlist;


    /**
     * An identifier for the layout resource to inflate when the ViewStub becomes visible
     */
    protected int progressBarViewStubLayoutId = R.layout.base_withlist_progress;

    /**
     * Indicates if the empty view feature is enabled
     */
    protected boolean useEmptyView = true;

    /**
     * Indicates if the progressBar feature is enabled
     */
    protected boolean useProgressBar = false;

    /**
     * Internal flag to indicate if the list is shown
     */
    private boolean mListShown;

    /**
     * Resource Id used which identifies the list
     */
    protected int listViewId = R.id.card_inner_base_main_cardwithlist;

    /**
     *  Flag to set the observer as registered
     */
    private boolean observerRegistered = false;

    private DataSetObserver mDataObserver = new DataSetObserver() {

        @Override
        public void onChanged() {
            internalSetupChildren();
        }

        @Override
        public void onInvalidated() {
            internalSetupChildren();
        }

    };

    // -------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------

    /**
     * Constructor with a base inner layout defined by R.layout.inner_base_main_cardwithlist
     *
     * @param context context
     */
    public CardWithList(Context context) {
        this(context, R.layout.inner_base_main_cardwithlist);
    }

    /**
     * Constructor with a custom inner layout.
     *
     * @param context     context
     * @param innerLayout resource ID for inner layout
     */
    public CardWithList(Context context, int innerLayout) {
        super(context, innerLayout);
    }

    // -------------------------------------------------------------
    // Init
    // -------------------------------------------------------------

    /**
     * Init the card
     */
    public void init() {

        //Init the CardHeader
        mCardHeader = initCardHeader();
        if (mCardHeader != null)
            addCardHeader(mCardHeader);

        //Init the Card
        initCard();

        //Init the children
        List<ListObject> mChildren = initChildren();
        if (mChildren == null)
            mChildren = new ArrayList<ListObject>();
        mLinearListAdapter = new LinearListAdapter(super.getContext(), mChildren);

        //Retrieve the layoutId use by children
        mChildLayoutId = getChildLayoutId();

    }

    // -------------------------------------------------------------
    // Abstract method to be implemented in your class
    // -------------------------------------------------------------

    /**
     * Implement this method to initialize your CardHeader.
     * <p/>
     * An example:
     * <pre><code>
     *     //Add Header
     *     CardHeader header = new CardHeader(getContext());
     *     //Add a popup menu. This method set OverFlow button to visible
     *     header.setPopupMenu(R.menu.popupmain, new CardHeader.OnClickCardHeaderPopupMenuListener() {
     *          @Override
     *          public void onMenuItemClick(BaseCard card, MenuItem item) {
     *              Toast.makeText(getContext(), "Click on " + item.getTitle(), Toast.LENGTH_SHORT).show();
     *          }
     *      });
     *      header.setTitle("Weather"); //should use R.string.
     *      return header;
     * <p/>
     * </code></pre>
     *
     * @return the {@link CardHeader}
     */
    protected abstract CardHeader initCardHeader();

    /**
     * Implement this method to initialize your Card.
     * <p/>
     * An example:
     * <pre><code>
     *      setSwipeable(true);
     *      setOnSwipeListener(new OnSwipeListener() {
     *          @Override
     *              public void onSwipe(Card card) {
     *                  Toast.makeText(getContext(), "Swipe on " + card.getCardHeader().getTitle(), Toast.LENGTH_SHORT).show();
     *              }
     *      });
     *  </code></pre>
     */
    protected abstract void initCard();

    /**
     * Implement this method to initialize the list of objects
     * <p/>
     * An example:
     * <pre><code>
     * <p/>
     *      List<ListObject> mObjects = new ArrayList<ListObject>();
     * <p/>
     *      WeatherObject w1= new WeatherObject();
     *      mObjects.add(w1);
     * <p/>
     *      return mObjects;
     * </code></pre>
     *
     * @return the List of ListObject. Return <code>null</code> if the list is empty.
     */
    protected abstract List<ListObject> initChildren();

    /**
     * This method is called by the {@link it.gmariotti.cardslib.library.prototypes.CardWithList.LinearListAdapter} for each row.
     * You can provide your layout and setup your ui elements.
     *
     * @param childPosition position inside the list of objects
     * @param object        {@link it.gmariotti.cardslib.library.prototypes.CardWithList.ListObject}
     * @param convertView   view used by row
     * @param parent        parent view
     * @return
     */
    public abstract View setupChildView(int childPosition, ListObject object, View convertView, ViewGroup parent);

    /**
     * Implement this method to specify the layoutId used for each row in the list
     *
     * @return the layoutId
     */
    public abstract int getChildLayoutId();


    // -------------------------------------------------------------
    // View
    // -------------------------------------------------------------

    /**
     * Returns the id that identifies the {@link LinearListView}.
     *
     * @return
     */
    protected int getListViewId() {
        return listViewId;
    }

    /**
     * Setup the listAdapter.
     *
     * @param parent parent view (Inner Frame)
     * @param view   Inner View
     */
    @Override
    public void setupInnerViewElements(ViewGroup parent, View view) {

        mListView = (LinearListView) view.findViewById(getListViewId());
        if (mListView != null) {

            internalSetupProgressBar(parent, view);

            if (mLinearListAdapter != null) {
                internalSetupChildren();
                mLinearListAdapter.registerDataSetObserver(mDataObserver);
            }
        }

        internalSetupEmptyView(parent, view);

    }

    /**
     * Setup the children
     */
    private void internalSetupChildren() {
        if (mListView != null) {

            mListView.removeAllViews();

            updateEmptyStatus((mLinearListAdapter == null) || mLinearListAdapter.isEmpty());

            if (mLinearListAdapter == null) {
                return;
            }
            mListView.setAdapter(mLinearListAdapter);
        }
    }

    /**
     * Setup the empty view.
     *
     * @param parent mainContentLayout
     * @param view   innerView
     */
    @SuppressWarnings("UnusedParameters")
    private void internalSetupEmptyView(ViewGroup parent, View view) {
        if (useEmptyView) {
            mEmptyView = (View) parent.findViewById(getEmptyViewId());
            if (mEmptyView != null) {
                if (mEmptyView instanceof ViewStub)
                    ((ViewStub) mEmptyView).setLayoutResource(getEmptyViewViewStubLayoutId());
                setEmptyView(mEmptyView);
            }
        }
    }

    /**
     * Setup the Progress Bar view.
     *
     * @param parent mainContentLayout
     * @param view   innerView
     */
    @SuppressWarnings("UnusedParameters")
    private void internalSetupProgressBar(ViewGroup parent, View view) {
        if (useProgressBar) {
            mProgressView = (View) parent.findViewById(getProgressBarId());
            mListShown=true;
            if (mProgressView != null) {
                if (mProgressView instanceof ViewStub)
                    ((ViewStub) mProgressView).setLayoutResource(getProgressBarViewStubLayoutId());
                setProgressView(mProgressView);
            }
        }
    }

    /**
     * Use this method to unregister the observer
     */
    public void unregisterDataSetObserver(){
        if (mLinearListAdapter!=null)
            mLinearListAdapter.unregisterDataSetObserver(mDataObserver);
    }

    // -------------------------------------------------------------
    // Interface to be used by children
    // -------------------------------------------------------------


    /**
     * Children have to implement this interface
     */
    public interface ListObject {

        /**
         * Returns the object id
         *
         * @return
         */
        public String getObjectId();

        /**
         * Returns the parent card
         */
        public Card getParentCard();

        /**
         * Register a callback to be invoked when an item in this LinearListView has
         * been clicked.
         *
         * @return The callback to be invoked with an item in this LinearListView has
         * been clicked, or null id no callback has been set.
         */
        public void setOnItemClickListener(OnItemClickListener onItemClickListener);

        /**
         * @return The callback to be invoked with an item in this LinearListView has
         * been clicked, or null id no callback has been set.
         */
        public OnItemClickListener getOnItemClickListener();

        /**
         * Indicates if the item is swipeable
         */
        public boolean isSwipeable();

        /**
         * Set the item as swipeable
         *
         * @param isSwipeable
         */
        public void setSwipeable(boolean isSwipeable);

        /**
         * Returns the callback to be invoked when item has been swiped
         *
         * @return listener
         */
        public OnItemSwipeListener getOnItemSwipeListener();

        /**
         * Register a callback to be invoked when an item in this LinearListView has
         * been swiped.
         *
         * @param onSwipeListener listener
         */
        public void setOnItemSwipeListener(OnItemSwipeListener onSwipeListener);

    }


    // -------------------------------------------------------------
    // Interface definition for a callback to be invoked when an item in this
    // LinearListView has been clicked.
    // -------------------------------------------------------------

    /**
     * Interface definition for a callback to be invoked when an item in this
     * LinearListView has been clicked.
     */
    public interface OnItemClickListener {

        /**
         * Callback method to be invoked when an item in this LinearListView has
         * been clicked.
         * <p/>
         * Implementers can call getItemAtPosition(position) if they need to
         * access the data associated with the selected item.
         *
         * @param parent   The LinearListView where the click happened.
         * @param view     The view within the LinearListView that was clicked (this
         *                 will be a view provided by the adapter)
         * @param position The position of the view in the adapter.
         * @param object   The object that was clicked.
         */
        void onItemClick(LinearListView parent, View view, int position, ListObject object);
    }

    // -------------------------------------------------------------
    // On Item Swipe Interface
    // -------------------------------------------------------------

    /**
     * Interface definition for a callback to be invoked when an item in this
     * LinearListView has been swiped
     */
    public interface OnItemSwipeListener {

        /**
         * Callback method to be invoked when an item in this LinearListView has
         * been swiped.
         *
         * @param object The object that was clicked.
         */
        public void onItemSwipe(ListObject object,boolean dismissRight);

    }


    /**
     * Returns listener invoked when card is swiped
     *
     * @return listener
     */
    public OnSwipeListener getOnSwipeListener() {
        return mOnSwipeListener;
    }

    /**
     * Sets listener invoked when card is swiped.
     * If listener is <code>null</code> the card is not swipeable.
     *
     * @param onSwipeListener listener
     */
    public void setOnSwipeListener(OnSwipeListener onSwipeListener) {
        if (onSwipeListener != null)
            mIsSwipeable = true;
        else
            mIsSwipeable = false;
        this.mOnSwipeListener = onSwipeListener;
    }

    // -------------------------------------------------------------
    // Default Implementation for ListObject
    // -------------------------------------------------------------

    /**
     * Default ListObject
     */
    public class DefaultListObject implements ListObject {

        /**
         * Object Id
         */
        protected String mObjectId;

        /**
         * Parent Card
         */
        protected Card mParentCard;

        /*
         * Item click listener
         */
        protected OnItemClickListener mOnItemClickListener;

        /**
         *
         */
        protected boolean mItemSwipeable = false;

        /**
         * Item swipe listener
         */
        protected OnItemSwipeListener mOnItemSwipeListener;

        // -------------------------------------------------------------
        // Constructor
        // -------------------------------------------------------------
        public DefaultListObject(Card parentCard) {
            this.mParentCard = parentCard;
        }

        // -------------------------------------------------------------
        // Default Implementation for getters and setters
        // -------------------------------------------------------------

        @Override
        public String getObjectId() {
            return mObjectId;
        }

        @Override
        public Card getParentCard() {
            return null;
        }

        public void setObjectId(String objectId) {
            mObjectId = objectId;
        }


        @Override
        public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
            mOnItemClickListener = onItemClickListener;
        }

        @Override
        public OnItemClickListener getOnItemClickListener() {
            return mOnItemClickListener;
        }

        @Override
        public boolean isSwipeable() {
            return mItemSwipeable;
        }

        @Override
        public void setSwipeable(boolean isSwipeable) {
            mItemSwipeable = isSwipeable;
        }

        @Override
        public OnItemSwipeListener getOnItemSwipeListener() {
            return mOnItemSwipeListener;
        }

        @Override
        public void setOnItemSwipeListener(OnItemSwipeListener onItemSwipeListener) {
            if (onItemSwipeListener != null)
                mItemSwipeable = true;
            else
                mItemSwipeable = false;
            this.mOnItemSwipeListener = onItemSwipeListener;
        }


    }

    // -------------------------------------------------------------
    // Empty View
    // -------------------------------------------------------------

    /**
     * Sets the view to show if the adapter is empty
     */
    public void setEmptyView(View emptyView) {
        mEmptyView = emptyView;

        useEmptyView = emptyView != null ? true : false;

        final LinearListAdapter adapter = getLinearListAdapter();
        final boolean empty = ((adapter == null) || adapter.isEmpty());
        updateEmptyStatus(empty);
    }

    /**
     * When the current adapter is empty, the LinearListView can display a special
     * view call the empty view. The empty view is used to provide feedback to
     * the user that no data is available in this LinearListView.
     *
     * @return The view to show if the adapter is empty.
     */
    public View getEmptyView() {
        return mEmptyView;
    }

    /**
     * Update the status of the list based on the empty parameter. If empty is
     * true and we have an empty view, display it. In all the other cases, make
     * sure that the layout is VISIBLE and that the empty view is GONE (if
     * it's not null).
     */
    private void updateEmptyStatus(boolean empty) {
        if (isUseEmptyView()) {
            if (empty) {
                if (mEmptyView != null) {
                    mEmptyView.setVisibility(View.VISIBLE);
                    mListView.setVisibility(View.GONE);
                } else {
                    // If the caller just removed our empty view, make sure the list
                    // view is visible
                    mListView.setVisibility(View.VISIBLE);
                }
            } else {
                if (mEmptyView != null)
                    mEmptyView.setVisibility(View.GONE);
                mListView.setVisibility(View.VISIBLE);
            }
        }
    }



    // -------------------------------------------------------------
    // Progress bar
    // -------------------------------------------------------------

    /**
     * When the current adapter is loading data, the LinearListView can display a special
     * progress Bar.
     *
     * @return The view to show if the adapter is the progress bar is enabled.
     */
    public View getProgressView() {
        return mProgressView;
    }

    /**
     * Sets the view to show as progress bar
     */
    public void setProgressView(View progressView) {
        mProgressView = progressView;
        useProgressBar = progressView != null;
    }

    /**
     * Updates the status of the list and the progress bar.
     *
     * @param shownList indicates if the list has to be shown
     * @param animate indicates to use an animation between view transition
     */
    public void updateProgressBar(boolean shownList, boolean animate) {
        if (isUseProgressBar()) {
            if (mListShown == shownList) {
                return;
            }
            mListShown = shownList;

            if (shownList) {
                if (animate) {
                    mProgressView.startAnimation(AnimationUtils.loadAnimation(
                            getContext(), android.R.anim.fade_out));
                    mListView.startAnimation(AnimationUtils.loadAnimation(
                            getContext(), android.R.anim.fade_in));
                    if (useEmptyView && mEmptyView!=null){
                        mEmptyView.startAnimation(AnimationUtils.loadAnimation(
                                getContext(), android.R.anim.fade_in));
                    }
                }
                mProgressView.setVisibility(View.GONE);

                final LinearListAdapter adapter = getLinearListAdapter();
                final boolean empty = ((adapter == null) || adapter.isEmpty());
                updateEmptyStatus(empty);


            } else {
                if (animate) {
                    mProgressView.startAnimation(AnimationUtils.loadAnimation(
                            getContext(), android.R.anim.fade_in));
                    mListView.startAnimation(AnimationUtils.loadAnimation(
                            getContext(), android.R.anim.fade_out));
                    if (useEmptyView && mEmptyView!=null){
                        mEmptyView.startAnimation(AnimationUtils.loadAnimation(
                                getContext(), android.R.anim.fade_out));
                    }
                }
                mProgressView.setVisibility(View.VISIBLE);
                mListView.setVisibility(View.INVISIBLE);
                if (useEmptyView && mEmptyView!=null){
                    mEmptyView.setVisibility(View.INVISIBLE);
                }
            }
        }
    }

    // -------------------------------------------------------------
    // Internal Adapter
    // -------------------------------------------------------------

    /**
     * ListAdapter used to populate the LinearLayout inside the Card.
     */
    @SuppressWarnings("JavaDoc")
    protected class LinearListAdapter extends ArrayAdapter<ListObject> {

        LayoutInflater mLayoutInflater;

        /**
         * Listener invoked when a card is swiped
         */
        protected SwipeDismissListItemViewTouchListener mOnTouchListener;

        /**
         * Constructor
         *
         * @param context context
         * @param objects objects
         */
        public LinearListAdapter(Context context, List<ListObject> objects) {
            super(context, 0, objects);
            //mObjects = objects;
            mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            //Object
            final ListObject object = getItem(position);

            View view = convertView;
            if (view == null) {
                view = mLayoutInflater.inflate(getChildLayoutId(), parent, false);
            }

            //Setup the elements
            final View viewChild = setupChildView(position, object, view, parent);

            //ClickItem Listener
            if (viewChild != null && object.getOnItemClickListener() != null) {
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mListView.playSoundEffect(SoundEffectConstants.CLICK);
                        object.getOnItemClickListener().onItemClick(mListView, viewChild, position, object);
                    }
                });
            }

            //SwipeItem Listener
            setupItemSwipeAnimation(object, viewChild);

            return viewChild;
        }


        @Override
        public int getViewTypeCount() {
            return 1;
        }

        /**
         * Sets SwipeAnimation on items List
         *
         * @param item     {@link ListObject}
         * @param itemView itemView
         */
        protected void setupItemSwipeAnimation(final ListObject item, View itemView) {

            if (item.isSwipeable()) {
                if (mOnTouchListener == null) {
                    mOnTouchListener = new SwipeDismissListItemViewTouchListener(mListView, mCallback);
                }
                itemView.setOnTouchListener(mOnTouchListener);
            }
        }


        /**
         * Returns the Object Id
         *
         * @param position  position in the list
         * @return          the object Id
         */
        @SuppressWarnings("UnusedDeclaration")
        public String getChildId(int position) {
            //Object
            ListObject object = getItem(position);
            return object.getObjectId();
        }


        // -------------------------------------------------------------
        //  SwipeListener and undo action
        // -------------------------------------------------------------
        /**
         * Listener invoked when a card is swiped
         */
        SwipeDismissListItemViewTouchListener.DismissCallbacks mCallback = new SwipeDismissListItemViewTouchListener.DismissCallbacks() {
            @Override
            public boolean canDismiss(int position, Card card, ListObject listObject) {
                return listObject.isSwipeable();
            }

            @Override
            public void onDismiss(LinearListView listView, int position, boolean dismissRight) {

                int i = 0;

                //Remove cards and notifyDataSetChanged
                ListObject object = getItem(position);
                remove(object);
                if (object.getOnItemSwipeListener() != null) {
                    object.getOnItemSwipeListener().onItemSwipe(object,dismissRight);
                }
            }
        };

        @Override
        public void registerDataSetObserver(DataSetObserver observer) {
            if (!observerRegistered) {
                super.registerDataSetObserver(observer);
            }
            observerRegistered = true;
        }

        @Override
        public void unregisterDataSetObserver(DataSetObserver observer) {
            if (observer == null) {
                observerRegistered = false;
                return;
            }
            super.unregisterDataSetObserver(observer);
            observerRegistered = false;
        }
    }

    // -------------------------------------------------------------
    // Getter and setter
    // -------------------------------------------------------------

    /**
     * Returns the adapter
     *
     * @return the adapter
     */
    public LinearListAdapter getLinearListAdapter() {
        return mLinearListAdapter;
    }

    /**
     * Sets the adapter
     *
     * @param linearListAdapter  adapter
     */
    public void setLinearListAdapter(LinearListAdapter linearListAdapter) {
        mLinearListAdapter = linearListAdapter;
    }

    /**
     * Returns the resource Id used which identifies the empty view
     *
     * @return the resource Id used which identifies the empty view
     */
    public int getEmptyViewId() {
        return emptyViewId;
    }

    /**
     * Sets the resource Id used which identifies the empty view
     *
     * @param emptyViewId resource Id used which identifies the empty view
     */
    public void setEmptyViewId(int emptyViewId) {
        this.emptyViewId = emptyViewId;
    }

    /**
     * Returns the resource Id used which identifies the ProgressBar
     * @return the resource Id used which identifies the ProgressBar
     */
    public int getProgressBarId() {
        return progressBarId;
    }

    /**
     * Sets the resource Id used which identifies the ProgressBar
     * @param progressBarId  resource Id used which identifies the ProgressBar
     */
    public void setProgressBarId(int progressBarId) {
        this.progressBarId = progressBarId;
    }

    /**
     * Return if the card uses the empty view built-in feature
     *
     * @return  true if the card uses the empty view
     */
    private boolean isUseEmptyView() {
        if (mEmptyView != null)
            return useEmptyView;
        else return false;
    }

    /**
     * Sets the flag to enable and disable the empty view feature
     *
     * @param useEmptyView  flag
     */
    public void setUseEmptyView(boolean useEmptyView) {
        this.useEmptyView = useEmptyView;
    }

    private boolean isUseProgressBar() {
        if (mProgressView != null)
            return useProgressBar;
        else
            return false;
    }

    /**
     * Sets the flag to enable and disable the progress bar
     * @param useProgressBar
     */
    public void setUseProgressBar(boolean useProgressBar) {
        this.useProgressBar = useProgressBar;
    }


    /**
     * Sets the resource Id used which identifies the list
     *
     * @param listViewId  resourceId which identifies the list
     */
    public void setListViewId(int listViewId) {
        this.listViewId = listViewId;
    }

    /**
     * Returns the identifier for the layout resource to inflate when the ViewStub becomes visible
     * It is used only if the {@see useEmptyView) is setted to true and the {@see mEmptyView} is a {@link android.view.ViewStub}.
     *
     * @return
     */
    public int getEmptyViewViewStubLayoutId() {
        return emptyViewViewStubLayoutId;
    }

    /**
     * Sets the identifier for the layout resource to inflate when the ViewStub becomes visible
     *
     * @param emptyViewViewStubLayoutId
     */
    public void setEmptyViewViewStubLayoutId(int emptyViewViewStubLayoutId) {
        this.emptyViewViewStubLayoutId = emptyViewViewStubLayoutId;
    }

    /**
     * Returns the identifier for the layout resource to inflate when the ViewStub used by the ProgressBar becomes visible
     * It is used only if the {@see useProgressBar) is setted to true and the {@see mProgressView} is a {@link android.view.ViewStub}.
     *
     * @return
     */
    public int getProgressBarViewStubLayoutId() {
        return progressBarViewStubLayoutId;
    }

    /**
     * Sets the identifier for the layout resource to inflate when the ViewStub used by the ProgressBar becomes visible
     *
     * @param progressBarViewStubLayoutId
     */
    public void setProgressBarViewStubLayoutId(int progressBarViewStubLayoutId) {
        this.progressBarViewStubLayoutId = progressBarViewStubLayoutId;
    }
}


