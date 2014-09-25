# Card Library: Overview

**Card Library** provides an easy way to display a UI Card in your Android app.

The main purpose of the library is to build UI Cards through a object-model.

The cardslib 2.+ introduces a new view called `CardViewNative` which extends the `android.support.v7.widget.CardView` released by Google with the support-library
 (`com.android.support:cardview-v7`). 

With this release you can build your `Cards` using one of 2 supported views:
* `it.gmariotti.cardslib.library.view.CardViewNative` which extends the Google's CardView.
* `it.gmariotti.cardslib.library.view.CardView`: it is the `CardView`-v1. 

**Note**: *in this doc, the term `CardView` is referred to the view `CardView` or `CardViewNative`*


Each `Card` has:

* a **global layout** which binds to `CardView` 
* a **model** which populates and manages the `CardView` 


A `CardView` has a global layout which defines the appearance and layout of the elements.

![Screen](/demo/images/model.png)

It has these basic parts:

* Header
* Thumbnail
* MainContent
* Shadow (it is not available in the `CardViewNative` because it is natively supported)
* Expand Area

This library provides 2 global layouts:

For the `CardViewNative`:
* **Default** : [`res/layout/native_card_layout.xml`](/library-core/src/main/res/layout/native_card_layout.xml) (without a `CardThumbnail`)  
* [`res/layout/native_card_thumbnail_layout.xml`](/library-core/src/main/res/layout/native_card_thumbnail_layout.xml) 

For the `CardView`:
* **Default** : [`res/layout/card_layout.xml`](/library-core/src/main/res/layout/card_layout.xml) (without a `CardThumbnail`) 
* [`res/layout/card_thumbnail_layout.xml`](/library-core/src/main/res/layout/ard_thumbnail_layout.xml)



You can build **your custom global** layout as you like and inflate in `CardView` with `card_layout_resourceID` attr.

 ``` xml
         <it.gmariotti.cardslib.library.view.CardViewNative
             android:id="@+id/carddemo_thumb_url"
             android:layout_width="match_parent"
             android:layout_height="wrap_content"  
             style="@style/card_external"
             card:card_layout_resourceID="@layout/native_card_thumbnail_layout"
/>
 ```

In this way you can build different type of cards.

You do not need to use all the parts, you can use only the parts needed.

![Screen](/demo/images/model/mexample.png)


Each part is a Compound View or a Layout with an **inner layout** which you can easily inflate and populate with your `Card` model.

In this way you can build many types of `Card` and preserve all built-in features provided by the library.

See the other pages to know how to build each part.

