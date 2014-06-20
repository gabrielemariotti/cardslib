# Cards Library: CardList

In this page you can find info about:

* [Creating a base CardList](#creating-a-base-cardlist)
* [Use your custom layout for each row](#use-your-custom-layout-for-each-row)
* [Cards with different inner layouts](#cards-with-different-inner-layouts)
* [Swipe and Undo in `CardListView`](#swipe-and-undo-in-cardlistview)
* [Swipe and Undo with a custom UndoBar](#swipe-and-undo-with-a-custom-undobar)
* [Swipe and custom OnScrollListener](#swipe-and-custom-onscrolllistener)
* [How to use an external adapter](#how-to-use-an-external-adapter)
* [Using a cursor adapter](#using-a-cursor-adapter)
* [Using a CardList in MultiChoiceMode](#using-a-cardlist-in-multichoicemode)
* [Using a CardList in MultiChoiceMode and CursorAdapter](#using-a-cardlist-in-multichoicemode-and-cursoradapter)
* [CardList with Drag and Drop:](DRAGDROPLIST.md)


### Creating a base CardList

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



![Screen](/demo/images/demo/list_gplay.png)

### Cards with different inner layouts

If you want to use cards with different inner layouts you have to:

1. set the number of different cards in your adapter with `mCardArrayAdapter.setInnerViewTypeCount`

``` java
    // Provide a custom adapter.
    // It is important to set the viewTypeCount
    // You have to provide in your card the type value with {@link Card#setType(int)} method.
    CardArrayAdapter mCardArrayAdapter = new CardArrayAdapter(getActivity(),cards);
    mCardArrayAdapter.setInnerViewTypeCount(3);
```
2. set the type inside your cards with `card.setType`

``` java
    MyCard card2= new MyCard(getActivity());
    card2.setType(2); //Very important with different inner layout
``` 

You can also override the `setType` method in your `Card`.

``` java
    public class CardExample extends Card{
        @Override
        public int getType() {
            //Very important with different inner layouts
            return 2;
        }
    }
```
Moreover you can extend `CardArrayAdapter` and provide your logic.
``` java
    /**
     *  With multiple inner layouts you have to set the viewTypeCount with {@link CardArrayAdapter#setInnerViewTypeCount(int)}.
     *  </p>
     *  An alternative is to provide your CardArrayAdapter  where you have to override this method:
     *  </p>
     *  public int getViewTypeCount() {}
     *  </p>
     *  You have to provide in your card the type value with {@link Card#setType(int)} method.
     *
     */
    public class MyCardArrayAdapter extends CardArrayAdapter{

        /**
         * Constructor
         *
         * @param context The current context.
         * @param cards   The cards to represent in the ListView.
         */
        public MyCardArrayAdapter(Context context, List<Card> cards) {
            super(context, cards);
        }

        @Override
        public int getViewTypeCount() {
            return 3;
        }
    }
```
You can see the example in 'ListDifferentInnerBaseFragment'.

![Screen](/demo/images/card/different_inner.png)


### Swipe and Undo in `CardListView`

If you want to enable the swipe action with an Undo Action you have to:

1. enable the swipe action on the single Cards
``` java
        //Create a Card
        Card card = new CustomCard(getActivity());

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

You can see the example in `ListGplayUndoCardFragment`.

![Screen](/demo/images/card/cardWithUndo.png)


### Swipe and Undo with a custom UndoBar

You can provide a custom UndoBar.

This UndoBar has to contains these elements:

1. A `TextView`

2. A `Button`

3. A root element with an id attribute

You should use the same Ids provided in the default layout `list_card_undo_message`, but if you have to use different ids you can use the `CardArrayAdapter.setUndoBarUIElements`:

Example:
``` xml
    <LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
        android:id="@+id/my_undobar"
        style="@style/list_card_UndoBar"
        android:orientation="horizontal">

        <TextView
            android:id="@+id/my_undobar_message"
            style="@style/list_card_UndoBarMessage"/>

        <Button
            android:id="@+id/my_undobar_button"
            style="@style/list_card_UndoBarButton"/>

    </LinearLayout>
```

``` java
      CardArrayAdapter mCardArrayAdapter = new CardArrayAdapter(getActivity(),cards);

      //It is very important to set the UndoBarUIElements before to call the setEnableUndo(true);
      mCardArrayAdapter.setUndoBarUIElements(new UndoBarController.DefaultUndoBarUIElements() {
                  @Override
                  public int getUndoBarId() {
                      return R.id.myid_undobar;
                  }

                  @Override
                  public int getUndoBarMessageId() {
                      return R.id.my_undobar_message;
                  }

                  @Override
                  public int getUndoBarButtonId() {
                      return R.id.my_undobar_button;
                  }
              });
      mCardArrayAdapter.setEnableUndo(true);

      if (mListView!=null){
          mListView.setAdapter(mCardArrayAdapter);
      }

```

If you would like to use more ListViews in the same screen, you have to use the code above.

Also you can customize your Undobar message.

You can clone in your res/values/strings.xml these strings and override them:

```xml
    <!-- Undo Controller-->

    <string name="list_card_undo_title">Undo</string>
    <!--<string name="undo_card">Card removed</string>-->

    <plurals name="list_card_undo_items">
        <item quantity="one">1 card removed</item>
        <item quantity="other">%d cards removed</item>
    </plurals>
```

Otherwise you can override the method `getMessageUndo` in your `UndoBarController.DefaultUndoBarUIElements`.

```java
    //It is very important to set the UndoBarUIElements before to call the setEnableUndo(true);
    mCardArrayAdapter.setUndoBarUIElements(new UndoBarController.DefaultUndoBarUIElements(){

            @Override
            public String getMessageUndo(CardArrayAdapter cardArrayAdapter, String[] itemIds, int[] itemPositions) {

                //It is only an example
                StringBuffer message=new StringBuffer();
                for (int position:itemPositions){
                    Card card = cardArrayAdapter.getItem(position);
                    message.append(card.getTitle());
                }
                return message.toString();
            }
        });
```

**Migration from 1.6.0 (and previous releases) - to 1.7.0**

The 1.7.0 may introduce a breaking change with UndoBar and UndoBarUIElements.
To migrate your code you have to:

* rename `UndoBarController.UndoBarUIElements()` in `UndoBarController.DefaultUndoBarUIElements()` if you are using it.
* extend `UndoBarController.DefaultUndoBarUIElements()` instead of implementing the `UndoBarController.UndoBarUIElements()` interface if you are using a custom undo bar.


### Swipe and custom OnScrollListener

The `CardListView` of cards with swipe action uses a own `SwipeOnScrollListener` to  ensure that the `SwipeDismissListViewTouchListener`
is paused during list view scrolling.

If you want to use your custom `OnScrollListener` you can use this code:

``` java
 listView.setOnScrollListener(

     new SwipeOnScrollListener() {
          @Override
          public void onScrollStateChanged(AbsListView view, int scrollState) {
              //It is very important to call the super method here to preserve built-in functions
              super.onScrollStateChanged(view,scrollState);
          }

         @Override
         public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
             //Do something...
         }
     });
```

It is very important to call the `super.onScrollStateChanged(view,scrollState);` in `onScrollStateChanged` method to preserve the built-in functions in swipe actions.

You can use a default `AbsListView.OnScrollListener` if you are working with a list of cards without swipe action.



### How to use an external adapter

Some libraries use a own adapter as [ListViewAnimations](OTHERLIBRARIES.md#using-card-with-listviewanimations)

In this case you can use this code:

``` java
         mListView.setExternalAdapter(ownAdapter,mCardArrayAdapter);
```

Pay attention. You can use this method, if your ownAdapter calls the mCardArrayAdapter#getView() method.


### Using a cursor adapter

Creating a `CardListView` is pretty simple.

First, you need an XML layout that will display the `CardListView`.

``` xml
    <it.gmariotti.cardslib.library.view.CardListView
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:id="@+id/myList"/>
```

Then create your `CardCursorAdapter`.

You have to extend the `CardCursorAdapter` and override the `getCardFromCursor` method.

``` java
      public class MyCursorCardAdapter extends CardCursorAdapter {

          public MyCursorCardAdapter(Context context) {
              super(context);
          }

          @Override
          protected Card getCardFromCursor(Cursor cursor) {
              MyCursorCard card = new MyCursorCard(super.getContext());
              setCardFromCursor(card,cursor);

              //Create a CardHeader
              CardHeader header = new CardHeader(super.getContext());

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

Last create your `MyCursorCardAdapter` instance, get a reference to the `CardListView` from your code and set your adapter.

``` java
        MyCursorCardAdapter mAdapter = new MyCursorCardAdapter(getActivity());

        CardListView mListView = (CardListView) getActivity().findViewById(R.id.carddemo_list_cursor);
        if (mListView != null) {
            mListView.setAdapter(mAdapter);
        }
```

With the this type of cursor, currently you can't use the swipe and undo actions.


### Using a CardList in MultiChoiceMode

If you would like to have a `CardList` with a MultiChoiceMode built-in feature you can use a `CardArrayMultiChoiceAdapter`.

This class extends `CardArrayAdapter` and preserves all its features.

First of all you have to create your CardArrayMultiChoiceAdapter extending the base class and implementing the missing methods.

``` java

    public class MyCardArrayMultiChoiceAdapter extends CardArrayMultiChoiceAdapter {

            public MyCardArrayMultiChoiceAdapter(Context context, List<Card> cards) {
                super(context, cards);
            }

            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                //It is very important to call the super method
                super.onCreateActionMode(mode, menu);

                mActionMode=mode; // to manage mode in your Fragment/Activity

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

            @Override
            public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked, CardView cardView, Card card) {
                Toast.makeText(getContext(), "Click;" + position + " - " + checked, Toast.LENGTH_SHORT).show();
            }

            private void discardSelectedItems(ActionMode mode) {
                ArrayList<Card> items = getSelectedCards();
                for (Card item : items) {
                    remove(item);
                }
                mode.finish();
            }


            private String formatCheckedCard() {

                SparseBooleanArray checked = mCardListView.getCheckedItemPositions();
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

Then you have to implement this `onLongClickListener` in your cards:

``` java
       card.setOnLongClickListener(new Card.OnLongCardClickListener() {
            @Override
            public boolean onLongClick(Card card, View view) {
                return mCardArrayAdapter.startActionMode(getActivity());

            }
       });
```

Finally get a reference to the `CardListView` from your code and set your adapter.

``` java
        MyCardArrayMultiChoiceAdapter mCardGridArrayAdapter = new MyCardArrayMultiChoiceAdapter(getActivity(), cards);

        CardListView listView = (CardListView) getActivity().findViewById(R.id.myGrid);
        if (listView!=null){
             listView.setAdapter(mCardGridArrayAdapter);
             listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
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

 You can see an example in `ListGplayCardCABFragment`  [(source)](/demo/stock/src/main/java/it/gmariotti/cardslib/demo/fragment/ListGplayCardCABFragment.java).


### Using a CardList in MultiChoiceMode and CursorAdapter

If you would like to have a `CardList` with a CursorAdapter and a MultiChoiceMode  built-in feature you can use a `CardCursorMultiChoiceAdapter`.

This class extends `CardCursorAdapter` and preserves all its features.

All considerations, [written above](#using-a-cardlist-in-multichoicemode), are valid.

``` java

    public class MyCardCursorMultiChoiceAdapter extends CardCursorMultiChoiceAdapter {

           public MyCardCursorMultiChoiceAdapter(Context context) {
               super(context);
           }


           @Override
           protected Card getCardFromCursor(Cursor cursor) {

                MyCursorCard card = new MyCursorCard(super.getContext());

                //Implement you code here

                //It is very important.
                //You have to implement this onLongClickListener in your cards to enable the multiChoice
                card.setOnLongClickListener(new Card.OnLongCardClickListener() {
                    @Override
                    public boolean onLongClick(Card card, View view) {
                        return startActionMode(getActivity());
                    }
                });
           }

    }
```

 You can see an example in `ListGplayCursorCardCABFragment`  [(source)](/demo/stock/src/main/java/it/gmariotti/cardslib/demo/fragment/ListGplayCursorCardCABFragment.java).