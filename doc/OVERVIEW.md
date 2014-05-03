# Card Library: Overview

**Card Library** provides an easy way to display a UI Card in your Android app.

The main purpose of the library is to build UI Cards through a object-model.

Each `Card` has:

* a **global layout** which binds to `CardView`
* a **model** which populates and manages the `CardView`


A `CardView` has a global layout which defines the appearance and layout of the elements.

![Screen](/demo/images/model.png)

It has these basic parts:

* Header
* Thumbnail
* MainContent
* Shadow
* Expand Area

This library provides 2 global layouts:

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

In this way you can build different type of cards.

You do not need to use all the parts, you can use only the parts needed.

![Screen](/demo/images/model/mexample.png)


Each part is a Compound View or a Layout with an **inner layout** which you can easily inflate and populate with your `Card` model.

In this way you can build many types of `Card` and preserve all built-in features provided by the library.

See the other pages to know how to build each part.

