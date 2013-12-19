# Card Library : Guide

In this page you can find info about:

* [Including in your project and building](#including-in-your-project)
* [Feature](#feature)
* [Quick Usage](#quick-usage)
* [Customization](#customization)


## Including in your project

Card Library is pushed to Maven Central as an AAR, so you just need to add the following dependency to your `build.gradle`.

    dependencies {
        compile 'com.github.gabrielemariotti.cards:library:1.1.0'
    }

[To build the library and demo locally you can see this page for more info](https://github.com/gabrielemariotti/cardslib/tree/master/doc/BUILD.md).


---

## Feature

Card Library provides 3 custom tags:

*  `CardView` to display a UI Card.
*  `CardListView` to display a List Card.
*  `CardGridView` to display a Grid Card.

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

* [See this page for a complete list of use](https://github.com/gabrielemariotti/cardslib/tree/master/doc/USAGE.md).


## Customization

Here you can find some pages to customize these tags.

* [Overview:](https://github.com/gabrielemariotti/cardslib/tree/master/doc/OVERVIEW.md)
* [Card Header:](https://github.com/gabrielemariotti/cardslib/tree/master/doc/HEADER.md) How to customize all header features
* [Card Shadow:](https://github.com/gabrielemariotti/cardslib/tree/master/doc/SHADOW.md) How to customize the shadow
* [Card Expand:](https://github.com/gabrielemariotti/cardslib/tree/master/doc/EXPAND.md) How to use an expandable/collapsible built-in feature
* [Card Thumbnail:](https://github.com/gabrielemariotti/cardslib/tree/master/doc/THUMBNAIL.md) How to display a thumbnail
* [Card:](https://github.com/gabrielemariotti/cardslib/tree/master/doc/CARD.md) How to customize all card features
* [CardList:](https://github.com/gabrielemariotti/cardslib/tree/master/doc/CARDLIST.md) How to work with the `CardListView`
* [CardGrid:](https://github.com/gabrielemariotti/cardslib/tree/master/doc/CARDGRID.md) How to work with the `CardGridView`
* [Integration with other libraries:](https://github.com/gabrielemariotti/cardslib/tree/master/doc/OTHERLIBRARIES.md) How to work with other main libraries
