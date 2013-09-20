# Card Library: Overview

A `CardView` has a global layout with some parts:

![Screen](https://github.com/gabrielemariotti/cardslib/raw/master/CardsDemo/images/model.png)

* Header
* Thumbnail
* MainContent
* Shadow
* Expand Area

This library provides 2 global layout:

* `res/layout/card_layout.xml` (default)
* `res/layout/card_thumbnail_layout.xml`

You can build **your custom global** layout as you like and inflate in `CardView` with `card_layout_resourceID` attr.

 ``` xml
         <it.gmariotti.cardslib.library.view.CardView
             android:id="@+id/carddemo_thumb_url"
             android:layout_width="match_parent"
             android:layout_height="wrap_content"
             android:layout_marginLeft="12dp"
             android:layout_marginRight="12dp"
             card:card_layout_resourceID="@layout/card_thumbnail_layout"
             android:layout_marginTop="12dp"/>
 ```

Each part is a Compound View or a Layout with an **inner layout** which you can easily inflate with your `Card` model.

In this way you can build many types of `Card` and preserve all built-in features provided by the library.

See the other pages to know how to build each part.

