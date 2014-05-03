# Cards Library: CardGrid

In this page you can find info about:

* [Creating a base CardGrid](#creating-a-base-cardgrid)
* [Use your custom layout for each row](#use-your-custom-layout-for-each-row)
* [How to use an external adapter](#How-to-use-an-external-adapter)
* [Using a cursor adapter](#using-a-cursor-adapter)
* [Using a CardGrid in MultiChoiceMode](#using-a-cardgrid-in-multichoicemode)


### Creating a base CardGrid

Creating a `CardGridView` is pretty simple.

First, you need an XML layout that will display the `CardGridView`.

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
          android:id="@+id/myGrid"/>
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

Last create a `CardGridArrayAdapter`, get a reference to the `CardGridView` from your code and set your adapter.

``` java
        CardGridArrayAdapter mCardArrayAdapter = new CardGridArrayAdapter(context,cards);

        CardGridView gridView = (CardGridView) getActivity().findViewById(R.id.myGrid);
        if (gridView!=null){
             gridView.setAdapter(mCardArrayAdapter);
        }
```

This `CardGridView` uses for each row the row-list layout `res/layout/list_card_layout.xml`.


### Use your custom layout for each row

Card Library provides 2 built-in row-list layouts.

* `res/layout/list_card_layout.xml`.
* `res/layout/list_card_thumbnail_layout.xml`.

You can customize the layout used for each item in ListView using the attr: `card:list_card_layout_resourceID="@layout/my_layout`

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

In your row-list layout you can use your `CardView` with some its features and its possibilities.
Example `carddemo_grid_gplay.xml`:

``` xml
    <it.gmariotti.cardslib.library.view.CardView
        xmlns:android="http://schemas.android.com/apk/res/android"
        xmlns:card="http://schemas.android.com/apk/res-auto"
        android:id="@+id/list_cardId"
        android:layout_width="190dp"
        android:layout_height="wrap_content"
        style="@style/grid_card"
        card:card_layout_resourceID="@layout/carddemo_googleplay_layout"
        />
```

You can build your layout, but need to have:

 1. a `CardView` with the ID `list_cardId`


This kind of View, doesn't support these `Card` features:

 1. swipe action
 2. collapse/expand action


Currently you have to use the same inner layouts for each card in `CardGridView`


![Screen](/demo/images/demo/grid_gplay.png)

### How to use an external adapter

Some libraries use a own adapter as [ListViewAnimations](OTHERLIBRARIES.md#using-card-with-listviewanimations)

In this case you can use this code:

``` java
         mGridView.setExternalAdapter(ownAdapter,mCardGridArrayAdapter);
```

Pay attention. You can only use this method if your ownAdapter calls the mCardGridArrayAdapter#getView() method.

### Using a cursor adapter

Creating a `CardGridView` is pretty simple.

First, you need an XML layout that will display the `CardGridView`.

``` xml
    <it.gmariotti.cardslib.library.view.CardGridView
           android:layout_width="match_parent"
           android:layout_height="match_parent"
           android:numColumns="auto_fit"
           android:columnWidth="100dp"
           android:verticalSpacing="3dp"
           android:horizontalSpacing="2dp"
           android:stretchMode="columnWidth"
           android:gravity="center"
           card:list_card_layout_resourceID="@layout/carddemo_grid_cursor_layout"
           android:id="@+id/carddemo_grid_cursor"/>
```

Then create your `CardGridCursorAdapter`.

You have to extend the `CardGridCursorAdapter` and override the `getCardFromCursor` method.

``` java
      public class MyCursorCardAdapter extends CardGridCursorAdapter {

          public MyCursorCardAdapter(Context context) {
              super(context);
          }

          @Override
          protected Card getCardFromCursor(Cursor cursor) {
              MyCursorCard card = new MyCursorCard(super.getContext());
              setCardFromCursor(card,cursor);

              //Create a CardHeader
              CardHeader header = new CardHeader(getActivity());
              //Set the header title

              header.setTitle(card.mainHeader);

              //Add Header to card
              card.addCardHeader(header);

              return card;
          }

          private void setCardFromCursor(MyCursorCard card,Cursor cursor) {

              card.mainTitle=cursor.getString(CardCursorContract.CardCursor.IndexColumns.TITLE_COLUMN);
              card.secondaryTitle=cursor.getString(CardCursorContract.CardCursor.IndexColumns.SUBTITLE_COLUMN);
              card.mainHeader=cursor.getString(CardCursorContract.CardCursor.IndexColumns.HEADER_COLUMN);
              card.setId(""+cursor.getInt(CardCursorContract.CardCursor.IndexColumns.ID_COLUMN));
          }
      }

```

Last create your `MyCursorCardAdapter` instance, get a reference to the `CardGridView` from your code and set your adapter.

``` java
        MyCursorCardAdapter mAdapter = new MyCursorCardAdapter(getActivity());

        CardGridView mGridView = (CardGridView) getActivity().findViewById(R.id.carddemo_grid_cursor);
        if (mGridView != null) {
            mGridView.setAdapter(mAdapter);
        }
```

With the this type of adapter, currently you can't use the swipe and undo actions.


### Using a CardGrid in MultiChoiceMode

If you would like to have a `CardGrid` with a MultiChoiceMode built-in feature you can use a `CardGridArrayMultiChoiceAdapter`.

This class extends `CardGridArrayAdapter` and preserves all its features.

First of all you have to create your CardGridArrayMultiChoiceAdapter extending the base class and implementing the missing methods.

``` java

    public class MyCardArrayMultiChoiceAdapter extends CardGridArrayMultiChoiceAdapter {

        public MyCardArrayMultiChoiceAdapter(Context context, List<Card> cards) {
            super(context, cards);
        }

        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            //It is very important to call the super method!
            super.onCreateActionMode(mode, menu);


            mActionMode=mode; // to manage in your Fragment/Activity

            //If you would like to inflate your menu
            MenuInflater inflater = mode.getMenuInflater();
            inflater.inflate(R.menu.carddemo_multichoice, menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            if (item.getItemId() == R.id.menu_share) {
                Toast.makeText(getContext(), "Share;" + formatCheckedCard(), Toast.LENGTH_SHORT).show();
                return true;
            }
            if (item.getItemId() == R.id.menu_discard) {
                discardSelectedItems(mode);
                return true;
            }
            return false;
        }

        /**
         *  Implements this method to handle the click on a item
         */
        @Override
        public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked, CardView cardView, Card card) {
            Toast.makeText(getContext(), "Click;" + position + " - " + checked, Toast.LENGTH_SHORT).show();
        }

        /*
         * Example
         */
        private void discardSelectedItems(ActionMode mode) {
            ArrayList<Card> items = getSelectedCards();  //Use this method to get the selected cards
            for (Card item : items) {
                remove(item);
            }
            mode.finish();
        }

        /*
         * Example
         */
        private String formatCheckedCard() {

            SparseBooleanArray checked = mCardGridView.getCheckedItemPositions();
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < checked.size(); i++) {
                if (checked.valueAt(i) == true) {
                    sb.append("\nPosition=" + checked.keyAt(i));
                }
            }
            return sb.toString();
        }

    }
```
It is very important, if you override the `onCreateActionMode()` method, to call the `super.onCreateActionMode()`.

Then you have implement this `onLongClickListener` in your cards:

``` java
       card.setOnLongClickListener(new Card.OnLongCardClickListener() {
                @Override
                public boolean onLongClick(Card card, View view) {
                    return mCardGridArrayAdapter.startActionMode(getActivity());

                }
       });
```

Finally get a reference to the `CardGridView` from your code and set your adapter.

``` java
        MyCardArrayMultiChoiceAdapter mCardGridArrayAdapter = new MyCardArrayMultiChoiceAdapter(getActivity(), cards);

        CardGridView gridView = (CardGridView) getActivity().findViewById(R.id.myGrid);
        if (gridView!=null){
             gridView.setAdapter(mCardGridArrayAdapter);
             gridView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        }
```

When an item is clicked and the action mode was already active, changes the selection state of the clicked item, just as if it had been long clicked.

As you can see in code above use `ArrayList<Card> items = getSelectedCards();`  to get the selected cards.

You need to implement the `onItemCheckedStateChanged` method if you would like to handle the click on a Card.

If you would like to customize the sentence "item selected", you can do it in your project overriding these strings in `res/values-XX/strings.xml`.

``` xml
    <!-- Card selected item with CAB -->
    <plurals name="card_selected_items">
        <item quantity="one">1 item selected</item>
        <item quantity="other">%d items selected</item>
    </plurals>

```

 You can see an example in `GridGplayCABFragment`  [(source)](/demo/stock/src/main/java/it/gmariotti/cardslib/demo/fragment/GridGplayCABFragment.java).