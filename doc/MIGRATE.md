# Card Library : Migrate from cardslib 1.+ to 2.0

In this page you can find info about:

* [Intro](#intro)
* [Breaking changes](#breaking-changes)
* [Use the new CardViewNative](#use-the-new-cardviewnative)

## Intro

The cardslib 2.+ introduces a new view called `CardViewNative` which extends the `android.support.v7.widget.CardView` released by Google with the support-library
 (`com.android.support:cardview-v7`). 

With this release you can build your `Card`s using one of 2 supported view:
* `it.gmariotti.cardslib.library.view.CardView`: it is the `CardView`-v1. 
* `it.gmariotti.cardslib.library.view.CardViewNative` which extends the Google's CardView.

You can continue to use the old `CardView`-v1 with a few changes in your code, or you can migrate to the `CardViewNative`.
In this page you will find all info to migrate from v1 to v2.


## Breaking changes

The library 2.x introduces some breaking changes in your code. You can fix these issue following this guide:

* if you are using the `getCardView()` you have to change your code using a cast to `CardView`. Now this code returns the `CommonCardView` interface.
``` java
   CardView cardView = (CardView) card.getCardView();
``` 

* If you are using an adapter which implements the `MultiChoiceAdapter`(`CardCursorMultiChoiceAdapter`,`CardArrayMultiChoiceAdapter`,`CardGridArrayMultiChoiceAdapter`) you have to change the signature of this method
From:
``` java
  public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked, CardView cardView, Card card)
```
To
```java
  public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked, CommonCardView cardView, Card card) {
```


## Use the new CardViewNative

You can use the cardslib 2.+ with the `CardView`-v1 and with the new `CardViewNative`.
If you would like to migrate the old cards to new view you have to follow these points:

* Change your xml from `it.gmariotti.cardslib.library.view.CardView` to `it.gmariotti.cardslib.library.view.CardViewNative`

* If you have a custom [(global layout)](/doc/OVERVIEW.md) you should migrate the style of each component:

| Style CardView                          | Style CardViewNative                           | 
| --------------------------------------- |------------------------------------------------| 
| @style/card                             | @style/card.native                             | 
| @style/card.main_layout                 | @style/card.native_main_layout                 | 
| @style/card.header_outer_layout         | @style/card.native.header_outer_layout         | 
| @style/card.header_button_base          | @style/card.native.header_button_base          |
| @style/card.header_button_frame         | @style/card.native.header_button_frame         |
| @style/card.header_button_base.overflow | @style/card.native.header_button_base.overflow |
| @style/card.header_button_base.expand   | @style/card.native.header_button_base.expand   |
| @style/card.header_button_base.other    | @style/card.native.header_button_base.other    |
