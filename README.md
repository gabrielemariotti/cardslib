# Card Library

Card Library provides an easy way to display a UI Card in your Android app.

![Screen](https://github.com/gabrielemariotti/cardslib/raw/master/demo/images/screen.png)


---
## Feature

Card Library provides a custom tag `CardView` to display a UI Card.

* It provides different parts as a Header, a Thumbnail, a Shadow, a MainContentArea where you can inflate your custom layout
* You can customize the global layout as you like
* You can have some built-in features as OnClickListener, OnSwipeListener , OnLongClickListener
* `CardHeader` provides an overflow button with a PopupMenuListener, a button to expand/collapse an area, or a customizable button with its listener.
* `CardThumbnail` load a Bitmap with a resource ID or with a URL using `LRUCache` and an `AsyncTask`

* It requires API 14+

Please note that this is currently in a preview state. This means that the API is not fixed and you should expect changes between releases.

## Usage

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

Last get a reference to the `CardView` from your code, and set your `Card.

``` java
       //Set card in the cardView
       CardView cardView = (CardView) getActivity().findViewById(R.id.carddemo);

       cardView.setCard(card);
```


## Customization

Here you can find some pages to customize this tag.

* [Overview:](https://github.com/gabrielemariotti/cardslib/tree/master/doc/OVERVIEW.md)
* [Card Header:](https://github.com/gabrielemariotti/cardslib/tree/master/doc/HEADER.md)
* [Card Shadow:](https://github.com/gabrielemariotti/cardslib/tree/master/doc/SHADOW.md)
* [Card Expand:](https://github.com/gabrielemariotti/cardslib/tree/master/doc/EXPAND.md)
* [Card Thumbnail:](https://github.com/gabrielemariotti/cardslib/tree/master/doc/THUMBNAIL.md)
* [Card:](https://github.com/gabrielemariotti/cardslib/tree/master/doc/CARD.md)

## Examples

* [Example:](https://github.com/gabrielemariotti/cardslib/tree/master/doc/EXAMPLE.md)

---

## Gradle

Card Library is now pushed to Maven Central as a AAR, so you just need to add the following dependency to your `build.gradle`.

    dependencies {
        compile 'com.github.gabrielemariotti.cards:library:0.2.0'
    }

Credits
-------

Author: Gabriele Mariotti (gabri.mariotti@gmail.com)

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