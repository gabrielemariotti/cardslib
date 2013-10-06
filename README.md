# Card Library

Card Library provides an easy way to display a UI Card in your Android app.

![Screen](https://github.com/gabrielemariotti/cardslib/raw/master/demo/images/screen.png)

You can display single cards, list of cards and a grid of Cards.

---
## Feature

Card Library provides 3 custom tags:

*  `CardView` to display a UI Card.
*  `CardListView` to display a List Card.
*  `CardGridView` to display a Grid Card.

**It requires API 14+**

`CardView`  displays a UI Card.

* It provides different parts as a Header, a Thumbnail, a Shadow, a MainContentArea where you can inflate your custom layout
* You can customize the global layout as you like
* You can have some built-in features as OnClickListener, OnSwipeListener , OnLongClickListener
* `CardHeader` provides an overflow button with a PopupMenuListener, a button to expand/collapse an area, or a customizable button with its listener.
* `CardThumbnail` load a Bitmap with a resource ID or with a URL using `LRUCache` and an `AsyncTask`


`CardListView` displays a List Card.

* It uses `CardView` tag and all its properties.
* It works with an `CardArrayAdapter`

`CardGridView` displays a Grid Card.

* It uses `CardView` tag and some its properties.
* It works with an `CardGridArrayAdapter


Please note that this is currently in a preview state. This means that the API is not fixed and you should expect changes between releases.

## Card Usage

Create a `Card` is is pretty simple.

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

* [See this page for a complete list of usage:](https://github.com/gabrielemariotti/cardslib/tree/master/doc/USAGR.md)


## Customization

Here you can find some pages to customize this tag.

* [Overview:](https://github.com/gabrielemariotti/cardslib/tree/master/doc/OVERVIEW.md)
* [Card Header:](https://github.com/gabrielemariotti/cardslib/tree/master/doc/HEADER.md) How to customize all header features
* [Card Shadow:](https://github.com/gabrielemariotti/cardslib/tree/master/doc/SHADOW.md) How to customize the shadow
* [Card Expand:](https://github.com/gabrielemariotti/cardslib/tree/master/doc/EXPAND.md) How to use an expandable/collapsible built-in feature
* [Card Thumbnail:](https://github.com/gabrielemariotti/cardslib/tree/master/doc/THUMBNAIL.md) How to display a thumbnail
* [Card:](https://github.com/gabrielemariotti/cardslib/tree/master/doc/CARD.md) How to customize all card features
* [CardList:](https://github.com/gabrielemariotti/cardslib/tree/master/doc/CARDLIST.md) How to work with the `CardListView`
* [CardGrid:](https://github.com/gabrielemariotti/cardslib/tree/master/doc/CARDGRID.md) How to work with the `CardGridView`

## Examples

Try out the apk with the [sample application](https://github.com/gabrielemariotti/cardslib/tree/master/apk/demo040.apk) or browse the [source code of the sample application](https://github.com/gabrielemariotti/cardslib/tree/master/demo) for a complete example of use.

* [Example:](https://github.com/gabrielemariotti/cardslib/tree/master/doc/EXAMPLE.md)

---

## Including in your project

Card Library is pushed to Maven Central as a AAR, so you just need to add the following dependency to your `build.gradle`.

    dependencies {
        compile 'com.github.gabrielemariotti.cards:library:0.4.0'
    }

ChangeLog
-------

* [Changelog:](https://github.com/gabrielemariotti/cardslib/tree/master/CHANGELOG.md) A complete changelog


Acknowledgements
--------------------

* Thanks to [Roman Nurik][1] for [Android-SwipeToDismiss][2] classes


Credits
-------

Author: Gabriele Mariotti (gabri.mariotti@gmail.com)

<a href="https://plus.google.com/u/0/114432517923423045208">
  <img alt="Follow me on Google+"
       src="https://github.com/gabrielemariotti/cardslib/raw/master/demo/images/g+64.png" />
</a>
<a href="https://twitter.com/GabMarioPower">
  <img alt="Follow me on Twitter"
       src="https://github.com/gabrielemariotti/cardslib/raw/master/demo/images/twitter64.png" />
</a>


License
-------

    Copyright 2013 Gabriele Mariotti

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.


---

Google and the Google Maps logo are registered trademarks of Google Inc., used with permission.

 [1]: https://plus.google.com/u/0/+RomanNurik/about
 [2]: https://github.com/romannurik/Android-SwipeToDismiss