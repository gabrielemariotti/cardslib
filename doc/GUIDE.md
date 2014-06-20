# Card Library : Guide

In this page you can find info about:

* [Including in your project and building](#including-in-your-project)
* [Feature](#feature)
* [Quick Usage](#quick-usage)
* [Customization](https://github.com/gabrielemariotti/cardslib/tree/master/doc/CUSTOMIZATION.md)

**THIS LIBRARY requires API 14+**

## Including in your project

Card Library is pushed to Maven Central as an AAR, so you just need to add the following dependency to your `build.gradle`.

    dependencies {
        //Core card library
        compile 'com.github.gabrielemariotti.cards:library:1.7.3'

        //Extra card library, it is required only if you want to use integrations with other libraries
        compile 'com.github.gabrielemariotti.cards:library-extra:1.7.3'
    }

The library-extra is optional. It contains code to use integrations with other libraries, as StaggeredGridView and CardListDragDropView.

[To build the library and demo locally you can see this page for more info](https://github.com/gabrielemariotti/cardslib/tree/master/doc/BUILD.md).


---

## Feature

Card Library provides 5 custom tags:

*  `CardView` to display a UI Card.
*  `CardListView` to display a List Card.
*  `CardGridView` to display a Grid Card.
*  `StaggeredGridView` to display a Staggered Grid Card.
*  `CardListDragDropView` to display a List Card with drag and drop support.

**It requires API 14+**

--------------------------------------

`CardView`  displays a UI Card.

* It provides different parts as a Header, a Thumbnail, a Shadow, a MainContentArea where you can inflate your custom layout
* You can customize the global layout as you like
* You can have some built-in features as OnClickListener, OnSwipeListener , OnLongClickListener
* `CardHeader` provides an overflow button with a PopupMenuListener, a button to expand/collapse an area, or a customizable button with its listener.
* `CardThumbnail` loads a Bitmap with a resource ID or with a URL using `LRUCache` and an `AsyncTask`

---------------------------------------

`CardListView` displays a List Card.

* It uses `CardView` tag and all its properties.
* It works with an `CardArrayAdapter`
* It works with an `CardCursorAdapter`

---------------------------------------

`CardGridView` displays a Grid Card.

* It uses `CardView` tag and some its properties.
* It works with an `CardGridArrayAdapter`
* It works with an `CardGridCursorAdapter`


## Quick Usage

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

* [See this page for a complete list of use](QUICKUSAGE.md).


## Customization

Check [this page](CUSTOMIZATION.md) to customize these tags.

