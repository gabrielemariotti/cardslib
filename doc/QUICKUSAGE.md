# Cards Library: QUICK USAGE

In this page you can find info about:

* [Card Usage](#card-usage)
* [CardList Usage](#cardlist-usage)
* [CardGrid Usage](#cardgrid-usage)


## Card Usage

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

Last get a reference to the `CardView` from your code, and set your `Card`.

``` java
       //Set card in the cardView
       CardView cardView = (CardView) getActivity().findViewById(R.id.carddemo);

       cardView.setCard(card);
```

See this [page](CUSTOMIZATION.md) for more info.


## CardList Usage

To create a `CardListView` , first, you need an XML layout that will display the `CardListView`.

``` xml
    <it.gmariotti.cardslib.library.view.CardListView
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:id="@+id/myList"/>
```

Then create an array of `Card`s and build a `CardArrayAdapter`

``` java
        ArrayList<Card> cards = new ArrayList<Card>();
        CardArrayAdapter mCardArrayAdapter = new CardArrayAdapter(context,cards);
```

Last get a reference to the `CardListView` from your code, and set your adapter:

``` java
        CardListView listView = (CardListView) getActivity().findViewById(R.id.myList);
        listView.setAdapter(mCardArrayAdapter);
```

Also the `CardListView` can work with a `CardCursorAdapter`.


See this [page](CARDLIST.md) for more info.


## CardGrid Usage

To create a `CardGridView` , first, you need an XML layout that will display the `CardGridView`.

``` xml
   <it.gmariotti.cardslib.library.view.CardGridView
       android:layout_width="match_parent"
       android:layout_height="match_parent"
       android:columnWidth="190dp"
       android:numColumns="auto_fit"
       android:verticalSpacing="3dp"
       android:horizontalSpacing="2dp"
       android:stretchMode="columnWidth"
       android:gravity="center"
       card:list_card_layout_resourceID="@layout/carddemo_grid_gplay"
       android:id="@+id/myGrid"/>
```

Then create an array of `Card`s and build a `CardGridArrayAdapter`

``` java
        ArrayList<Card> cards = new ArrayList<Card>();
        CardGridArrayAdapter mCardArrayAdapter = new CardGridArrayAdapter(context,cards);
```

Last get a reference to the `CardGridView` from your code, and set your adapter:

``` java
        CardGridView gridView = (CardGridView) getActivity().findViewById(R.id.myGrid);
        gridView.setAdapter(mCardArrayAdapter);
```

See this [page](CARDGRID.md) for more info.