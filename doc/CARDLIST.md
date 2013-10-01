# Cards Library: CardList

Create a `CardListView` is is pretty simple.

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

![Screen](https://github.com/gabrielemariotti/cardslib/raw/master/demo/images/demo/list_gplay.png)