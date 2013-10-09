# Cards Library: CardList

Creating a `CardListView` is pretty simple.

First, you need an XML layout that will display the `CardListView`.

``` xml
    <it.gmariotti.cardslib.library.view.CardListView
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:id="@+id/myList"/>
```

Then create an Array of `Card`s:

``` java
       ArrayList<Card> cards = new ArrayList<Card>();

      //Create a Card
      Card card = new Card(getContext());

      //Create a CardHeader
      CardHeader header = new CardHeader(getContext());
      ....
      //Add Header to card
      card.addCardHeader(header);

      cards.add(card);
```

Last create a `CardArrayAdapter`, get a reference to the `CardListView` from your code and set your adapter.

``` java
        CardArrayAdapter mCardArrayAdapter = new CardArrayAdapter(getActivity(),cards);

        CardListView listView = (CardListView) getActivity().findViewById(R.id.myList);
        if (listView!=null){
            listView.setAdapter(mCardArrayAdapter);
        }
```

This `CardListView` uses for each row the row-list layout `res/layout/list_card_layout.xml`.


### Use your custom layout for each row

Card Library provides 2 built-in row-list layouts.

* `res/layout/list_card_layout.xml`.
* `res/layout/list_card_thumbnail_layout.xml`.

You can customize the layout used for each item in ListView using the attr: `card:list_card_layout_resourceID="@layout/my_layout`

``` xml
    <it.gmariotti.cardslib.library.view.CardListView
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:id="@+id/carddemo_list_gplaycard"
        card:list_card_layout_resourceID="@layout/list_card_thumbnail_layout" />
```

In your row-list layout you can use your `CardView` with all its features and its possibilities.
Example `list_card_thumbnail_layout.xml`:

``` xml
    <it.gmariotti.cardslib.library.view.CardView
        xmlns:android="http://schemas.android.com/apk/res/android"
        xmlns:card="http://schemas.android.com/apk/res-auto"
        android:id="@+id/list_cardId"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        style="@style/list_card.thumbnail"
        card:card_layout_resourceID="@layout/card_thumbnail_layout"
        />
```

You can build your layout, but need to have:

 1. a `CardView` with the ID `list_cardId`


Currently you have to use the same inner layouts for each card in `CardListView`


![Screen](https://github.com/gabrielemariotti/cardslib/raw/master/demo/images/demo/list_gplay.png)


### Swipe and Undo in `CardListView`

If you want to enable the swipe action with an Undo Action you have to:

1. enable the swipe action on the single Cards

``` java
        //Create a Card
        Card card = new CustomCard(getActivity().getApplicationContext());

        //Enable a swipe action
        card.setSwipeable(true);
```

2. provide a id for each card

``` java
        card.setId("xxxx");
```

3. enable the undo action on the List

``` java
        CardArrayAdapter mCardArrayAdapter = new CardArrayAdapter(getActivity(),cards);

        //Enable undo controller!
        mCardArrayAdapter.setEnableUndo(true);
 ```

4. include the undo bar in your layout. You can use the build-in layout `res/layout/list_card_undo_message.xml'.

``` xml
  <FrameLayout xmlns:android="http://schemas.android.com/apk/res/android"
               xmlns:card="http://schemas.android.com/apk/res-auto"
               android:layout_width="match_parent"
               android:layout_height="match_parent">

      <RelativeLayout
          android:layout_width="match_parent"
          android:layout_height="match_parent">

          <!-- You can customize this layout.
           You need to have in your layout a `CardView` with the ID `list_cardId` -->
          <it.gmariotti.cardslib.library.view.CardListView
              android:layout_width="match_parent"
              android:layout_height="match_parent"
              android:id="@+id/carddemo_list_gplaycard"
              card:list_card_layout_resourceID="@layout/list_card_thumbnail_layout"/>

      </RelativeLayout>

      <!-- Include undo message layout -->
      <include layout="@layout/list_card_undo_message"/>

  </FrameLayout>
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

Then you can set a `Card.OnUndoSwipeListListener` to listen the undo action.

``` java
            card.setOnUndoSwipeListListener(new OnUndoSwipeListListener() {
                @Override
                public void onUndoSwipe(Card card) {
                    //Do something
                }
            });
```

You can customize the undo bar. The easiest way is to copy the styles inside `res/values/styles_undo.xml` in your project.
