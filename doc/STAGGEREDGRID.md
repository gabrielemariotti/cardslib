# Cards Library-extra: CardGridStaggeredView

In this page you can find info about:

* [Intro](#intro)
* [Creating a base CardGridStaggeredView](#creating-a-base-cardgridstaggeredview)
* [Attributes](#attributes)
* [Width to height ratio](#width-to-height-ratio)
* [Use your custom layout for each row](#use-your-custom-layout-for-each-row)
* [How to use an external adapter](#how-to-use-an-external-adapter)

### Intro

> **PAY ATTENTION:** to use this feature you have to use the **library-extra**.
> [for more info](GUIDE.md#including-in-your-project).

This feature is base on [AndroidStaggeredGrid](https://github.com/etsy/AndroidStaggeredGrid) provided by [Etsy](https://github.com/etsy).
Check the [`build.gradle`](/library-extra/build.gradle) to know the last version used.

![Screen](/demo/images/demo/staggered.png)


### Creating a base CardGridStaggeredView

Creating a `CardGridStaggeredView` is pretty simple.

First, you need an XML layout that will display the `CardGridStaggeredView`.

``` xml
       <it.gmariotti.cardslib.library.extra.staggeredgrid.view.CardGridStaggeredView
           android:layout_width="match_parent"
           android:layout_height="wrap_content"
           card:item_margin="8dp"
           card:column_count_portrait="3"
           card:column_count_landscape="3"
           card:grid_paddingRight="8dp"
           card:grid_paddingLeft="8dp"
           card:list_card_layout_resourceID="@layout/carddemo_extras_base_staggered_card"
           android:id="@+id/carddemo_extras_grid_stag"/>
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

Last create a `CardGridStaggeredArrayAdapter`, get a reference to the `CardGridStaggeredView` from your code and set your adapter.

``` java
   CardGridStaggeredArrayAdapter mCardArrayAdapter = new CardGridStaggeredArrayAdapter(getActivity(), cards);

   CardGridStaggeredView mGridView = (CardGridStaggeredView) getActivity().findViewById(R.id.carddemo_extras_grid_stag);
   if (mGridView != null) {
       mGridView.setAdapter(mCardArrayAdapter);
   }
```

This `CardGridStaggeredView` uses for each row the row-list layout `res/layout/list_card_layout.xml`.


You can see an example in `BaseStaggeredGridFragment`  [(source)](/demo/extras/src/main/java/it/gmariotti/cardslib/demo/extras/fragment/BaseStaggeredGridFragment.java).


### Attributes

The tag uses the original attribute. You can check the original [project](https://github.com/etsy/AndroidStaggeredGrid) for more details.

Configure attributes.
 * `card:item_margin` - The margin around each grid item (default 0dp).
 * `card:column_count` - The number of columns displayed. Will override column_count_portrait and column_count_landscape if present (default 0)
 * `card:column_count_portrait` - The number of columns displayed when the grid is in portrait (default 2).
 * `card:column_count_landscape` - The number of columns displayed when the grid is in landscape (default 3).
 * `card:grid_paddingLeft` - Padding to the left of the grid. Does not apply to headers and footers (default 0).
 * `card:grid_paddingRight` - Padding to the right of the grid. Does not apply to headers and footers (default 0).
 * `card:grid_paddingTop` - Padding to the top of the grid. Does not apply to headers and footers (default 0).
 * `card:grid_paddingBottom` - Padding to the bottom of the grid. Does not apply to headers and footers (default 0).


### Width to height ratio

As column widths change on orientation change, the grid view expects that all children maintain their own width to height ratio.

It can be very important if you are using a CardThumbnail inside your card.

The demo-extra includes the `DynamicHeightPicassoCardThumbnailView` [(source)](/demo/extras/src/main/java/it/gmariotti/cardslib/demo/extras/staggered/DynamicHeightPicassoCardThumbnailView.java), an example of a view that measures its height based on its width.


You can see a full example in `StaggeredGridFragment`  [(source)](/demo/extras/src/main/java/it/gmariotti/cardslib/demo/extras/fragment/StaggeredGridFragment.java).


### Use your custom layout for each row

You can use the same rules of CardGridView. Check [this link](CARDGRID.md#use-your-custom-layout-for-each-row)

This kind of View, doesn't support these `Card` features:

 1. swipe action
 2. collapse/expand action


### How to use an external adapter

Some libraries use a own adapter as [ListViewAnimations](OTHERLIBRARIES.md#using-card-with-listviewanimations)

In this case you can use this code:

``` java
         mGridView.setExternalAdapter(ownAdapter,mCardArrayAdapter);
```

Pay attention. You can use this method, if your ownAdapter calls the mCardArrayAdapter#getView() method.

Example with ListViewAnimations:

``` java
        CardGridStaggeredArrayAdapter mCardArrayAdapter = new CardGridStaggeredArrayAdapter(getActivity(), cards);
        CardGridStaggeredView staggeredView = (CardGridStaggeredView) getActivity().findViewById(R.id.carddemo_extras_grid_stag);

        //Add an animator
        AnimationAdapter animCardArrayAdapter = new SwingLeftInAnimationAdapter(mCardArrayAdapter);
        animCardArrayAdapter.setAbsListView(staggeredView);
        animCardArrayAdapter.setInitialDelayMillis(500);
        if (staggeredView != null) {
            staggeredView.setExternalAdapter(animCardArrayAdapter, mCardArrayAdapter);
        }
```