# Cards Library: Card

In this page you can find info about:

* [Creating a base Card](#creating-a-base-card)
* [Use your custom card-layout](#use-your-custom-card-layout)
* [Use your content inner layout](#use-your-content-inner-layout)
* [Extending Card class](#extending-card-class)
* [Listeners](#listeners)
* [Card with a swipe action](#card-with-a-swipe-action)
* [Clickable card](#clickable-card)
* [ClickListener on a specific area](#clicklistener-on-a-specific-area)
* [Refresh a card](#refresh-a-card)
* [Replace inner layout in a card](#replace-inner-layout-in-a-card)
* [Customize Card background](#customize-card-background)
* [Change Dynamically card background](#change-dynamically-card-background)
* [Change dynamically Card background with a Drawable object](#change-dynamically-card-background-with-a-drawable-object)
* [Export card as bitmap](#export-card-as-bitmap)
* [Using Card with contextual action mode](#using-card-with-contextual-action-mode)
* [Using a ForegroundLinearLayout](#using-a-foregroundlinearlayout)


### Creating a base Card

Creating a `Card` is pretty simple.

First, you need an XML layout that will display the `Card`.

``` xml
        <it.gmariotti.cardslib.library.view.CardView
            android:id="@+id/carddemo"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:layout_marginLeft="12dp"
            android:layout_marginRight="12dp"
            android:layout_marginTop="12dp"/>
```

Then create a model:

``` java
      //Create a Card
      Card card = new Card(getContext());

      //Create a CardHeader
      CardHeader header = new CardHeader(getContext());
      ....
      //Add Header to card
      card.addCardHeader(header);
```

Last get a reference to the `CardView` from your code, and set your `Card.

``` java
       //Set card in the cardView
       CardView cardView = (CardView) getActivity().findViewById(R.id.carddemo);

       cardView.setCard(card);
```

#### Use your custom card-layout

Card Library provides 2 built-in card layouts.

* `res/layout/card_layout.xml`: this is the default layout and doesn't contain the thumbnail.
* `res/layout/card_thumbnail_layout.xml` : this contains the thumbnail


You can easily *build your layout*.

The quickest way to start with this would be to copy one of this files and create your layout.

Then you can inflate your layout in the `CardView` using the attr: `card:card_layout_resourceID="@layout/my_layout`

Example:

``` xml
        <it.gmariotti.cardslib.library.view.CardView
            android:id="@+id/carddemo_thumb_url"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:layout_marginLeft="12dp"
            android:layout_marginRight="12dp"
            card:card_layout_resourceID="@layout/card_thumbnail_layout"
            android:layout_marginTop="12dp"/>
```

If you want to preserve built-in features you have to use the same IDs for Compound Views (Header, Shadow, Thumbnail) and Built-in Frame (Content, Expand).

Each component (view or frame) can be easily customize with style/drawable resources or inflating custom inner layout.(See doc about Header,Shadow,Thumbnail,Expand).


#### Use your content inner layout

Each Card has a content Frame area, where you can display your custom info.

The built-in content inner layout is quite useless, and provides a simple title.

``` java
      //Create a Card
      Card card = new Card(getContext());

      //Set the card inner text
      card.setTitle("My Title");


      //Set card in the cardView
      CardView cardView = (CardView) getActivity().findViewById(R.id.carddemo_card_id);
      cardView.setCard(card);
```

![Screen](/demo/images/card/title.png)

Surely you have to custom this area.

To do this you can use the `Card` constructor

``` java
        //Create a Card
        Card card = new Card(getContext(),R.layout.carddemo_example_inner_content);

        //Set the card inner text
        card.setTitle(getString(R.string.demo_card_basetitle));

        //Set card in the cardView
        CardView cardView = (CardView) getActivity().findViewById(R.id.carddemo_card_id);
        cardView.setCard(card);
```

This is the layout:

``` xml
    <RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
                    android:layout_width="match_parent"
                    android:layout_height="wrap_content">

        <ImageView
            android:layout_marginTop="2dp"
            android:id="@+id/colorBorder"
            android:background="@drawable/rectangle"
            android:layout_width="10dp"
            android:layout_height="@dimen/card_base_empty_height"/>

        <!-- Use same ID to use built-in features -->
        <TextView
            android:layout_toRightOf="@id/colorBorder"
            android:id="@+id/card_main_inner_simple_title"
            android:layout_marginLeft="10dp"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"/>

    </RelativeLayout>
```

![Screen](/demo/images/card/inner_content.png)

#### Extending Card class

The layout above is quite simple, and doesn't need to set values.

If you want to have a full control on the main content, you can extend `Card` class, inflate your custom inner layout and then set your values with `setupInnerViewElements` method.

``` java
    public class CustomCard extends Card {

        protected TextView mTitle;
        protected TextView mSecondaryTitle;
        protected RatingBar mRatingBar;

        /**
         * Constructor with a custom inner layout
         * @param context
         */
        public CustomCard(Context context) {
            this(context, R.layout.carddemo_mycard_inner_content);
        }

        /**
         *
         * @param context
         * @param innerLayout
         */
        public CustomCard(Context context, int innerLayout) {
            super(context, innerLayout);
            init();
        }

        /**
         * Init
         */
        private void init(){

            //No Header

            //Set a OnClickListener listener
            setOnClickListener(new OnCardClickListener() {
                @Override
                public void onClick(Card card, View view) {
                    Toast.makeText(getContext(), "Click Listener card=", Toast.LENGTH_LONG).show();
                }
            });
        }

        @Override
        public void setupInnerViewElements(ViewGroup parent, View view) {

            //Retrieve elements
            mTitle = (TextView) parent.findViewById(R.id.carddemo_myapps_main_inner_title);
            mSecondaryTitle = (TextView) parent.findViewById(R.id.carddemo_myapps_main_inner_secondaryTitle);
            mRatingBar = (RatingBar) parent.findViewById(R.id.carddemo_myapps_main_inner_ratingBar);


            if (mTitle!=null)
                mTitle.setText(R.string.demo_custom_card_google_maps);

            if (mSecondaryTitle!=null)
                mSecondaryTitle.setText(R.string.demo_custom_card_googleinc);

            if (mRatingBar!=null)
                mRatingBar.setNumStars(5);
                mRatingBar.setMax(5);
                mRatingBar.setRating(4.7f);

        }
    }
```

Then you can simply do:

``` java
        //Create a Card
        Card card = new CustomCard(getActivity());

        //Set card in the cardView
        CardView cardView = (CardView) getActivity().findViewById(R.id.carddemo_example_card3);
        cardView.setCard(card);
```

![Screen](/demo/images/card/mycard.png)

#### Listeners

The `Card` class provides some listeners to handle callbacks.

* `OnSwipeListener` : invoked when the card is swiped
* `OnCardClickListener` : invoked when the card is clicked
* `OnExpandAnimatorEndListener` : invoked when the expand animation ends
* `OnCollapseAnimatorEndListener` : invoked when the collapse animation ends
* `OnLongCardClickListener` : invoked when the card is long clicked

See [Card Header:](HEADER.md) for Header listeners.

#### Card with a swipe action

If you want to enable the swipe action on a `Card` is very simple:

``` java
        //Create a Card
        Card card = new CustomCard(getActivity());

        //Enable a swipe action
        card.setSwipeable(true);

        //Set card in the cardView
        CardView cardView = (CardView) getActivity().findViewById(R.id.carddemo_example_card3);
        cardView.setCard(card);
```

It is not mandatory. You can set a `Card.OnSwipeListener` to listen the swipe action.

``` java
        //You can set a SwipeListener.
        card.setOnSwipeListener(new Card.OnSwipeListener() {
            @Override
            public void onSwipe(Card card) {
                //Do something
            }
        });
```

#### Clickable card

If you want a clickable card, enable a `Card.OnCardClickListener`

``` java
        //Create a Card
        Card card = new CustomCard(getActivity());

        //Set onClick listener
        card.setOnClickListener(new Card.OnCardClickListener() {
            @Override
            public void onClick(Card card, View view) {
                Toast.makeText(getActivity(),"Clickable card", Toast.LENGTH_LONG).show();
            }
        });

        //Set card in the cardView
        CardView cardView = (CardView) getActivity().findViewById(R.id.carddemo_example_card3);
        cardView.setCard(card);
```
![Screen](/demo/images/card/clickable.png)

#### ClickListener on a specific area

With `OnCardClickListener` you can set your card as clickable.

If you want to set only a part (or more parts) as clickable you can use this code:

``` java
        //Set a clickListener on ContentArea
        card1.addPartialOnClickListener(Card.CLICK_LISTENER_CONTENT_VIEW, new Card.OnCardClickListener() {
            @Override
            public void onClick(Card card, View view) {
                Toast.makeText(getActivity(),"Click on Content Area", Toast.LENGTH_LONG).show();
            }
        });

        //Set a clickListener on Header Area
        card2.addPartialOnClickListener(Card.CLICK_LISTENER_HEADER_VIEW, new Card.OnCardClickListener() {
            @Override
            public void onClick(Card card, View view) {
                Toast.makeText(getActivity(),"Click on Header Area", Toast.LENGTH_LONG).show();
            }
        });

        //Set a clickListener on Thumbnail area
        card3.addPartialOnClickListener(Card.CLICK_LISTENER_THUMBNAIL_VIEW, new Card.OnCardClickListener() {
            @Override
            public void onClick(Card card, View view) {
                Toast.makeText(getActivity(),"Click on Thumbnail", Toast.LENGTH_LONG).show();
            }
        });
```

![Screen](/demo/images/card/partial_listeners.png)


#### Refresh a card

If you need to change same value in a card, you can use your `Card` model:

``` java
    card.setTitle("New title");

    //Change Header
    card.getCardHeader().setTitle("New image");

    //Change Thumbnail
    card.getCardThumbnail().setDrawableResource(R.drawable.ic_std_launcher);

    //Remove click listener
    card.setOnClickListener(null);
    card.setClickable(false);
```
and then call `refreshCard` method on `cardView`:

``` java
    //Call refresh
    cardView = (CardView) getActivity().findViewById(R.id.carddemo_card_changevalue_id);
    cardView.refreshCard(card);
```
You can see the example in 'ChangeValueCardFragment'.


#### Replace inner layout in a card

If you need to replace the inner layout in a card, you can use your `Card` model and call `refreshCard` method on `cardView`:

``` java
    //Call replace
    card3.setInnerLayout(R.layout.carddemo_suggested_inner_content);
    cardView3 = (CardView) getActivity().findViewById(R.id.carddemo_card_changevalue_id3);
    cardView3.replaceCard(card3);
```

In your `setupInnerViewElements()` method you should provide the correct way to put values in your ui elements according to the layout.
You can see the example in `ChangeValueCardFragment`.


#### Customize Card background

The quickest way to customize the card background would be to copy the styles/drawables in your project.

Default cards use `res/drawable/card_selector.xml`.

``` xml
    <selector xmlns:android="http://schemas.android.com/apk/res/android"
              android:exitFadeDuration="@android:integer/config_mediumAnimTime">
        <item android:state_pressed="true" android:drawable="@drawable/pressed_background_card"/>
        <item android:drawable="@drawable/card_background"/>
    </selector>
```

In `res/drawable/card_background.xml` and `res/drawable/pressed_background_card.xml` you can easy customize the color and shape.

``` xml
    <shape xmlns:android="http://schemas.android.com/apk/res/android"
           android:shape="rectangle">
        <solid android:color="@color/card_background"/>
        <corners android:radius="@dimen/card_background_default_radius"/>
    </shape>
```

If you want to change the color, the quickest way would be to customize this value in your project:

``` xml
    <color name="card_background">#FFF</color>
```

If you want to change the rounded corners, the quickest way would be to customize this value in your project:

``` xml
    <dimen name="card_background_default_radius">2dip</dimen>
```

Pay attention. The default card has a min height.

Check this style:
``` xml
 <style name="card.content_outer_layout">
```

You can override this value in your dimens.xml:
``` xml
    <dimen name="card_base_empty_height">96dp</dimen>
```

#### Change dynamically Card background

If you need to change dynamically the card background you can use this code:

``` java
    ColorCard card = new ColorCard(this.getActivity());

    //Set Background resource
    card.setBackgroundResourceId(R.drawable.demo_card_selector_color1);
```

Where R.drawable.demo_card_selector_color1 can be a selector or a drawable.

Example:

``` xml
    <selector xmlns:android="http://schemas.android.com/apk/res/android"
                         android:exitFadeDuration="@android:integer/config_mediumAnimTime">
        <item android:state_pressed="true" android:drawable="@drawable/pressed_background_card"/>
        <item android:drawable="@drawable/demo_card_background_color1"/>
    </selector>
```

You can see this example:  Colored cards example [(source)](/demo/stock/src/main/java/it/gmariotti/cardslib/demo/fragment/ListColorFragment.java).

![Screen](/demo/images/demo/colors.png)


#### Change dynamically Card background with a Drawable object

Also you can customize your background using a Drawable object:

``` java
     StateListDrawable newDrawable = new StateListDrawable();
     newDrawable.addState(new int[]{android.R.attr.state_pressed},
            getResources().getDrawable(R.drawable.pressed_background_card));
     newDrawable.addState(new int[] {}, getResources().getDrawable(R.drawable.demo_card_background_color2));

     card4setBackgroundResource(newDrawable);
```

You can see this example:  [(source)](/demo/stock/src/main/java/it/gmariotti/cardslib/demo/fragment/ChangeValueCardFragment.java#L122).



#### Export card as bitmap

You can export your `Card` as a Bitmap.

It is very simple.

``` java
    CardView cardView = (CardView) getActivity().findViewById(R.id.myCard);
    Bitmap bitmap = cardView.createBitmap();
```

![Screen](/demo/images/card/exportCard.png)


You can use this bitmap for your scopes.

In `BitmapUtils` class there are some built-in methods:

1. You can use `File photofile= BitmapUtils.createFileFromBitmap(bitmap)` to save the image in `Environment.DIRECTORY_PICTURES` folder.
2. You can use `Intent intent = BitmapUtils.createIntentFromImage(photofile)` to put the image as `EXTRA_STREAM` in a `Intent`.

You can see the example in `BirthDayCardFragment` and `StockCardFragment` where you can share the card as a bitmap.


#### Using Card with contextual action mode

If you would like to use a card with  you can use a code like this:

``` java

        ActionMode mActionMode;

        //Create a Card
        Card mCardCab = new Card(getActivity());

        //Set the card inner text
        mCardCab.setTitle(getString(R.string.demo_card_basetitle));

        //Set onClick listener
        mCardCab.setOnLongClickListener(new Card.OnLongCardClickListener() {
            @Override
            public boolean onLongClick(Card card, View view) {
                if (mActionMode != null) {
                    view.setActivated(false);
                    mActionMode.finish();
                    return false;
                }
                // Start the CAB using the ActionMode.Callback defined below
                mActionMode = getActivity().startActionMode(mActionModeCallback);
                view.setActivated(true);
                return true;
            }
        });

        //Set card in the cardView
        cardViewCab = (CardView) getActivity().findViewById(R.id.carddemo_example_card_cab);
        cardViewCab.setCard(mCardCab);
    }


    private ActionMode.Callback mActionModeCallback = new ActionMode.Callback() {

        // Called when the action mode is created; startActionMode() was called
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            // Inflate a menu resource providing context menu items
            MenuInflater inflater = mode.getMenuInflater();
            inflater.inflate(R.menu.carddemo_cab_example, menu);
            return true;
        }

        // Called each time the action mode is shown. Always called after onCreateActionMode, but
        // may be called multiple times if the mode is invalidated.
        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false; // Return false if nothing is done
        }

        // Called when the user selects a contextual menu item
        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            switch (item.getItemId()) {
                case R.id.carddemo_toast:
                    Toast.makeText(getActivity(), "Change Text",
                            Toast.LENGTH_LONG).show();
                    if (mCardCab!=null && cardViewCab!=null){
                        //Example to change dinamically your card
                        mCardCab.setTitle(getString(R.string.demo_title_cab2));
                        cardViewCab.refreshCard(mCardCab);
                    }
                    mode.finish(); // Action picked, so close the CAB
                    return true;
                default:
                    return false;
            }
        }

        // Called when the user exits the action mode
        @Override
        public void onDestroyActionMode(ActionMode mode) {
            mActionMode = null;
            if (mCardCab!=null)
                cardViewCab.setActivated(false);
        }
    };
```

You can see this example:  [(source)](/demo/stock/src/main/java/it/gmariotti/cardslib/demo/fragment/CardFragment.java#L259).

### Using a ForegroundLinearLayout

You can draw the stateful drawable on top, using a foreground selector.

To achieve this behaviour you have to do these steps:

- change the `android:id="@+id/card_main_layout"` element in your card layout

Default layout:
```xml
    <!-- Standard Card visible layout -->
    <LinearLayout
        android:id="@+id/card_main_layout"
        style="@style/card.main_layout"
        android:orientation="vertical"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        >
 ```
Foreground layout:

```xml
    <!-- Foreground Card visible layout -->
    <it.gmariotti.cardslib.library.view.ForegroundLinearLayout
        android:id="@+id/card_main_layout"
        style="@style/card.main_layout_foreground"
        android:orientation="vertical"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        >
 ```

- change the default style with something like the `style="@style/card.main_layout_foreground"`

This style uses a foreground selector using the `android:foreground` attribute, and a simple drawable(or color) for background.

```xml
    <!-- Style for Main Layout with foreground selector-->
    <style name="card.main_layout_foreground">
        <item name="android:background">@drawable/card_background</item>
        <item name="android:foreground">@drawable/card_foreground_selector</item>
    </style>
 ```

You can see this example:  [(source)](/demo/extras/src/main/res/layout/carddemo_extras_base_staggered_layout.xml).