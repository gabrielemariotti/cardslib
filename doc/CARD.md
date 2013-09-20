# Cards Library: Card

Create a `Card` is is pretty simple.

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

### Use your custom card-layout

Card Library provides 2 built-in layout card.

* `res/layout/card_layout.xml`
* `res/layout/card_thumbnail_layout.xml`

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


### Use your content inner layout

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

![Screen](https://github.com/gabrielemariotti/cardslib/raw/master/CardsDemo/images/card/title.png)

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

![Screen](https://github.com/gabrielemariotti/cardslib/raw/master/CardsDemo/images/card/inner_content.png)

### Extending Card class

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
        Card card = new CustomCard(getActivity().getApplicationContext());

        //Set card in the cardView
        CardView cardView = (CardView) getActivity().findViewById(R.id.carddemo_example_card3);
        cardView.setCard(card);
```

![Screen](https://github.com/gabrielemariotti/cardslib/raw/master/CardsDemo/images/card/mycard.png)

### Listeners

The `Card` class provides some listeners to handle callbacks.

* `OnSwipeListener` : invoked when the card is swiped
* `OnCardClickListener` : invoked when the card is clicked
* `OnExpandAnimatorEndListener` : invoked when the expand animation ends
* `OnCollapseAnimatorEndListener` : invoked when the collapse animation ends
* `OnLongCardClickListener` : invoked when the card is long clicked

See [Card Header:](https://github.com/gabrielemariotti/cardslib/tree/master/doc/HEADER.md) for Header listeners.

### Card with a swipe action

If you want to enable the swipe action on a `Card` is very simple:

``` java
        //Create a Card
        Card card = new CustomCard(getActivity().getApplicationContext());

        //Enable a swipe action
        card.setSwipeable(true);

        //Set card in the cardView
        CardView cardView = (CardView) getActivity().findViewById(R.id.carddemo_example_card3);
        cardView.setCard(card);
```

It is not mandatory. You can set a SwipeListener to listen the swipe action.

``` java
        //You can set a SwipeListener.
        card.setOnSwipeListener(new Card.OnSwipeListener() {
            @Override
            public void onSwipe(Card card) {
                //Do something
            }
        });
```

### Clickable card

If you want a clickable card, enable a `Card.OnCardClickListener`

``` java
        //Create a Card
        Card card = new CustomCard(getActivity().getApplicationContext());

        //Set onClick listener
        card.setOnClickListener(new Card.OnCardClickListener() {
            @Override
            public void onClick(Card card, View view) {
                Toast.makeText(getActivity().getApplicationContext(),"Clickable card", Toast.LENGTH_LONG).show();
            }
        });

        //Set card in the cardView
        CardView cardView = (CardView) getActivity().findViewById(R.id.carddemo_example_card3);
        cardView.setCard(card);
```
![Screen](https://github.com/gabrielemariotti/cardslib/raw/master/CardsDemo/images/card/clickable.png)

### ClickListener on a specific area

With `OnCardClickListener` you can set your card as clickable.

If you want to set only a part (or more parts) as clickable you can use this code:

``` java
        //Set a clickListener on ContentArea
        card1.addPartialOnClickListener(Card.CLICK_LISTENER_CONTENT_VIEW, new Card.OnCardClickListener() {
            @Override
            public void onClick(Card card, View view) {
                Toast.makeText(getActivity().getApplicationContext(),"Click on Content Area", Toast.LENGTH_LONG).show();
            }
        });

        //Set a clickListener on Header Area
        card2.addPartialOnClickListener(Card.CLICK_LISTENER_HEADER_VIEW, new Card.OnCardClickListener() {
            @Override
            public void onClick(Card card, View view) {
                Toast.makeText(getActivity().getApplicationContext(),"Click on Header Area", Toast.LENGTH_LONG).show();
            }
        });

        //Set a clickListener on Thumbnail area
        card3.addPartialOnClickListener(Card.CLICK_LISTENER_THUMBNAIL_VIEW, new Card.OnCardClickListener() {
            @Override
            public void onClick(Card card, View view) {
                Toast.makeText(getActivity().getApplicationContext(),"Click on Thumbnail", Toast.LENGTH_LONG).show();
            }
        });
```

![Screen](https://github.com/gabrielemariotti/cardslib/raw/master/CardsDemo/images/card/partial_listeners.png)

