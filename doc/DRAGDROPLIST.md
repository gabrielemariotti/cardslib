# Cards Library-extra: CardListDragDropView

In this page you can find info about:

* [Intro](#intro)
* [Creating a base CardListDragDropView](#creating-a-base-cardlistdragdropview)
* [How to use an external adapter](#how-to-use-an-external-adapter)

### Intro

> **PAY ATTENTION:** to use this feature you have to use the **library-extra**.
> [for more info](GUIDE.md#including-in-your-project).

This feature is base on [ListViewAnimation](https://github.com/nhaarman/ListViewAnimations) provided by [Niek Haarman](https://plus.google.com/+NiekHaarman).
Check the [`build.gradle`](/library-extra/build.gradle) to know the last version used.

The library-extra provides a `@drawable/card_drag` icon kindly offered by [Taylor Ling](https://plus.google.com/+TaylorLing).

![Screen](/demo/images/dragdrop.png)


### Creating a base CardListDragDropView

Creating a `CardListDragDropView` is pretty simple.

First, you need an XML layout that will display the `CardListDragDropView`.

``` xml
      <it.gmariotti.cardslib.library.extra.dragdroplist.view.CardListDragDropView
           android:layout_width="match_parent"
           android:layout_height="match_parent"
           android:layout_marginTop="10dp"
           android:id="@+id/carddemo_extra_list_dragdrop"
           card:list_card_layout_resourceID="@layout/carddemo_extras_list_card_dragdrop_layout"/>
```
You have to customize the layout used for each item in ListView using the attr: `card:list_card_layout_resourceID="@layout/my_layout` to provide an area or an image where the drag and drop feature is enable.
(You can use the same rules of CardListView. Check [this link](CARDLIST.md#use-your-custom-layout-for-each-row))

I suggest you using a ImageView with a "standard" drag and drop icon.

The library-extra provides a `@drawable/card_drag` icon kindly offered by [Taylor Ling](https://plus.google.com/+TaylorLing).

Example:
``` xml
<LinearLayout   xmlns:android="http://schemas.android.com/apk/res/android"
                xmlns:card="http://schemas.android.com/apk/res-auto"
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:orientation="horizontal">

    <!-- Drag and Drop icon -->
    <ImageView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_gravity="center"
        android:layout_marginRight="16dp"
        android:layout_marginLeft="16dp"
        android:src="@drawable/card_drag"/>


    <!-- You can customize this layout.
     You need to have in your layout a `CardView` with the ID `list_cardId` -->
    <it.gmariotti.cardslib.library.view.CardView
        android:id="@+id/list_cardId"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        style="@style/list_card.thumbnail"
        card:card_layout_resourceID="@layout/carddemo_extras_card_thumbnail_layout"
        />

</LinearLayout>
```

Then create an Array of `Card`s:

It is very important that cards have a stable Id.


``` java
       ArrayList<Card> cards = new ArrayList<Card>();

      //Create a Card
      Card card = new Card(getContext());

      //Card must have a stable Id.
       card.setId("a"+i);

      //Create a CardHeader
      CardHeader header = new CardHeader(getContext());
      ....
      //Add Header to card
      card.addCardHeader(header);

      cards.add(card);
```

Last create a `CardDragDropArrayAdapter`, get a reference to the `CardListDragDropView` from your code and set your adapter.

``` java
   //Set the adapter
   CardDragDropArrayAdapter mCardArrayAdapter = new CardDragDropArrayAdapter(getActivity(), cards);

   CardListDragDropView mListView = (CardListDragDropView) getActivity().findViewById(R.id.carddemo_extra_list_dragdrop);
   if (mListView != null) {
           mListView.setAdapter(mCardArrayAdapter);
   }
```

Currently you can't enable the drag and drop feature clicking on the cards.


You can set a listener to be notified when an item is dropped:
``` java
   //Listener
   mListView.setOnItemMovedListener(new DynamicListView.OnItemMovedListener() {
      @Override
      public void onItemMoved(int newPosition) {
          Card card = mCardArrayAdapter.getItem(newPosition);
          Toast.makeText(getActivity(),"Card "+card.getId() + " moved to position " + newPosition, Toast.LENGTH_SHORT ).show();
      }
   });

```

This kind of View, doesn't support these `Card` features:

 1. swipe action
 2. collapse/expand action


You can see an example in `DragDropListFragment`  [(source)](/demo/extras/src/main/java/it/gmariotti/cardslib/demo/extras/fragment/DragDropListFragment.java).


### How to use an external adapter

Some libraries use a own adapter as [ListViewAnimations](OTHERLIBRARIES.md#using-card-with-listviewanimations)

In this case you can use this code:

``` java
         mListView.setExternalAdapter(ownAdapter,mCardArrayAdapter);
```

Pay attention. You can use this method, if your ownAdapter calls the mCardArrayAdapter#getView() method.

Example with ListViewAnimations:

``` java
        CardDragDropArrayAdapter mCardArrayAdapter = new CardDragDropArrayAdapter(getActivity(), cards);
        CardListDragDropView mListView = (CardListDragDropView) getActivity().findViewById(R.id.carddemo_extra_list_dragdrop);

        //Add an animator
        AnimationAdapter animCardArrayAdapter = new SwingLeftInAnimationAdapter(mCardArrayAdapter);
        animCardArrayAdapter.setAbsListView(staggeredView);
        animCardArrayAdapter.setInitialDelayMillis(500);
        if (mListView != null) {
               mListView.setExternalAdapter(animCardArrayAdapter, mCardArrayAdapter);
        }
```
