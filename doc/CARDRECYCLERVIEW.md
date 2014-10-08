# Cards Library: CardList

In this page you can find info about:

* [Creating a base CardRecyclerView](#creating-a-base-cardrecyclerview)
* [Use your custom layout for each row](#use-your-custom-layout-for-each-row)


### Creating a base CardRecyclerView

Creating a `CardRecyclerView` is pretty simple.

First, you need an XML layout that will display the `CardRecyclerView`.

``` xml
    <it.gmariotti.cardslib.library.view.CardRecyclerView
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        card:list_card_layout_resourceID="@layout/native_list_card_layout"
        android:id="@+id/carddemo_recyclerview"/>
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
        CardArrayRecyclerViewAdapter mCardArrayAdapter = new CardArrayRecyclerViewAdapter(getActivity(), cards);

        //Staggered grid view
        CardRecyclerView mRecyclerView = (CardRecyclerView) getActivity().findViewById(R.id.carddemo_recyclerview);
        mRecyclerView.setHasFixedSize(false);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        //Set the empty view
        if (mRecyclerView != null) {
            mRecyclerView.setAdapter(mCardArrayAdapter);
        }
```

This `CardRecyclerView` uses for each row the row-list layout:
 
 - for the `CardViewNative`: [`res/layout/native_list_card_layout`](/library-core/src/main/res/layout/native_list_card_layout.xml).
 - for the `CardView`: [`res/layout/list_card_layout`](/library-core/src/main/res/layout/list_card_layout.xml).


### Use your custom layout for each row

Card Library provides 2 built-in row-list layouts.

For the `CardViewNative`:
* [`res/layout/native_list_card_layout.xml`](/library-core/src/main/res/layout/native_list_card_layout.xml).
* [`res/layout/native_list_card_thumbnail_layout.xml`](/library-core/src/main/res/layout/native_list_card_thumbnail_layout.xml).

For the `CardView`:
* [`res/layout/list_card_layout.xml`](/library-core/src/main/res/layout/list_card_layout.xml).
* [`res/layout/list_card_thumbnail_layout.xml`](/library-core/src/main/res/layout/list_card_thumbnail_layout.xml).


You can customize the layout used for each item in the RecyclerView using the attr: `card:list_card_layout_resourceID="@layout/my_layout`

``` xml
    <it.gmariotti.cardslib.library.view.CardRecyclerView
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:id="@+id/carddemo_list_gplaycard"
        card:list_card_layout_resourceID="@layout/native_list_card_thumbnail_layout" />
```

In your row-list layout you can use your `CardViewNative` with all its features and its possibilities.
Example `native_list_card_thumbnail_layout.xml`:

``` xml
    <it.gmariotti.cardslib.library.view.CardViewNative
        xmlns:android="http://schemas.android.com/apk/res/android"
        xmlns:card="http://schemas.android.com/apk/res-auto"
        android:id="@+id/list_cardId"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        style="@style/native_list_card.base"
        card:card_layout_resourceID="@layout/native_card_layout"/>
```

You can build your layout, but need to have:

 1. a `CardViewNative` or `CardView` with the ID `list_cardId`

![Screen](/demo/images/demo/native/native_recyclerView.png)

