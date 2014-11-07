# Card Library : Guide

In this page you can find info about:

* [Including in your project and building](#including-in-your-project) : how to include this library in your project
* [Feature](#feature) 
* [Quick Usage](#quick-usage)
* [Customization](/doc/CUSTOMIZATION.md): *it is the real doc! Don't miss it!*

**THIS LIBRARY requires API 14+**


## Including in your project

Card Library is pushed to Maven Central as an AAR, so you just need to add the following dependency to your `build.gradle`.

    dependencies {
        //Core card library
        compile 'com.github.gabrielemariotti.cards:cardslib-core:2.0.0'
        
        //Optional for built-in cards
        compile 'com.github.gabrielemariotti.cards:cardslib-cards:2.0.0'
                
        //Optional for RecyclerView
        compile 'com.github.gabrielemariotti.cards:cardslib-recyclerview:2.0.0'  
          
        //Optional for staggered grid view support
        compile 'com.github.gabrielemariotti.cards:cardslib-extra-staggeredgrid:2.0.0'       
         
        //Optional for drag and drop support
        compile 'com.github.gabrielemariotti.cards:cardslib-extra-dragdrop:2.0.0'
                
        //Optional for twowayview support (coming soon)
        //compile 'com.github.gabrielemariotti.cards:cardslib-extra-twoway:2.0.0'
            
    }
    
 If you would like to use the last **v1 stable version** you can use:
    
    dependencies {
        //Core card library
        compile 'com.github.gabrielemariotti.cards:library:1.9.1'

        //Extra card library, it is required only if you want to use integrations with other libraries
        compile 'com.github.gabrielemariotti.cards:library-extra:1.9.1'
    }


[To build the library and demo locally you can see this page for more info](https://github.com/gabrielemariotti/cardslib/tree/master/doc/BUILD.md).

---

## Feature

Card Library provides several custom tags:

*  `CardView` to display a UI Card.
*  `CardViewNative` to display a UI Card with the **Google CardView**
*  `CardListView` to display a List Card.
*  `CardGridView` to display a Grid Card.
*  `StaggeredGridView` to display a Staggered Grid Card.
*  `CardListDragDropView` to display a List Card with drag and drop support.
*  `CardRecyclerView` to support the RecyclerView
*  `CardTwoWayView` to support the TwoWayView[1] library

**It requires API 14+**



## Quick Usage

Creating a `Card` is pretty simple.

First, you need an XML layout that will display the `Card`.

``` xml
        <it.gmariotti.cardslib.library.view.CardViewNative
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

Last get a reference to the `CardViewNative` from your code, and set your `Card`.

``` java
       //Set card in the cardView
       CardViewNative cardView = (CardViewNative) getActivity().findViewById(R.id.carddemo);
       cardView.setCard(card);
```


## Customization

Check [this page](CUSTOMIZATION.md) to customize your code.



[1] : https://github.com/lucasr/twoway-view