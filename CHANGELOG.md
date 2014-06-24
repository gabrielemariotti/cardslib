Change Log
===============================================================================

Version 1.7.3 *(2014-06-20)*
----------------------------
* LIB: Removed labels app_name to prevent issue with the new Manifest merger


Version 1.7.2 *(2014-06-18)*
----------------------------
* LIB: Changed the visibility of the`init() method` and its call.

It contains a **BREAKING CHANGE** .* [(doc)](/doc/CARDWITHLIST.md#how-to-build-a-card-with-a-linearlist)
* LIB: Fixed the issue with Observer when the CardWithList works in a CardListView


Version 1.7.1 *(2014-06-14)*
----------------------------
* LIB: Removed application labels from android-libs manifest to prevent issues with the new Manifest merger


Version 1.7.0 *(2014-06-10)*
----------------------------

* LIB: Added a new feature to display a list of items inside a Card [(doc)](/doc/CARDWITHLIST.md)
* LIB: Added a feature to customize dynamically the undoBar message [(doc)](/doc/CARDLIST.md#swipe-and-undo-with-a-custom-undobar)

  It contains a **BREAKING CHANGE** with UndoBar and UndoBarUIElements feature. Read the above link to migrate your code.
* LIB-EXTRA: Updated Etsy-Grid dependency to 1.0.5
* DEMO: Added a new example for Card with List 
* DEMO-Extra: Added a new example for Card with List integrated with WeatherLib
* DEMO-Extra: Improved staggered grid examples.
* DEMO-Extra: updated all libraries.


Version 1.6.0 *(2014-05-06)*
----------------------------
* LIB: Added a `CardCursorMultiChoiceAdapter` [(doc)](/doc/CARDLIST.md#using-a-cardlist-in-multichoicemode-and-cursoradapter)
* LIB-EXTRA: Added a `CardListDragDropView`, a cardList with drag and drop support: [(doc)](/doc/DRAGDROPLIST.md)
* LIB-EXTRA: Added a new method to add external adapter different from `CardGridStaggeredArrayAdapter` [(doc)](/doc/STAGGEREDGRID.md#how-to-use-an-external-adapter)
* Added Travis CI support
* DEMO: Added an example with CardCursorMultiChoiceAdapter
* DEMO-Extra: Added an example with CardListDragDropView
* DEMO-Extra: Added an example with CardGridStaggered + ListViewAnimation


Version 1.5.0 *(2014-03-21)*
----------------------------
* LIB-EXTRA: Added a `CardGridStaggeredView` [(doc)](/doc/STAGGEREDGRID.md)
* LIB: Added the chance to use a `ForegroundLinearLayout` in your Card [(doc)](/doc/CARD.md#using-a-foregroundlinearlayout)
* LIB: Added a feature to use a own `OnScrollListener` with CardListView and Swipe Action [(doc)](/doc/CARDLIST.md#swipe-and-custom-onscrolllistener)
* LIB: Added the method `ViewToClickToExpand.setupCardElement()` to enable the expand/collapse action on the default card elements  [(doc)](/doc/EXPAND.md#expand-the-card-by-clicking-on-different-view)
* LIB: Introduced a flag to disable the default broadcast after a bitmap is attached to an imageView in `CardThumbnail` [(doc)](/doc/THUMBNAIL.md#broadcast-to-know-when-the-download-is-finished)
* DEMO-Extra: Added examples with StaggeredGridView
* DEMO-Extra: Added an example with a List with expand-inside.[(source)](/demo/extras/src/main/java/it/gmariotti/cardslib/demo/extras/fragment/ExpandPicassoFragment.java)


Version 1.4.2 *(2014-03-07)*
----------------------------
* LIB: new expand/collapse methods are now available also for `CardCursorAdapter` [(doc)](/doc/EXPAND.md#cardexpand-and-cardlistview)

  It contains a **BREAKING CHANGE** with CardCursorApdater and Expand feature. Read the above link to migrate your code.


Version 1.4.0 *(2014-03-02)*
----------------------------
* LIB: expand/collapse methods are completely new.(Fixed issues with smooth scroll, proper height measure, parent lose when expanded view isn't entirely visible...)
* LIB: Added a feature to add the PopupMenu in CardHeader entirely from code [(doc)](/doc/HEADER.md#standard-header-with-the-overflow-botton-and-popupmenu-built-programmatically)
* DEMO: Added an example with PopupMenu in CardHeader built programmatically (in Header screen)
* DEMO-Extras: updated all libraries.


Version 1.3.0 *(2014-01-25)*
----------------------------
* LIB: Added a `CardGridArrayMultiChoiceAdapter` [(doc)](/doc/CARDGRID.md#using-a-cardgrid-in-multichoicemode)
* LIB: Added a `CardArrayMultiChoiceAdapter` [(doc)](/doc/CARDLIST.md#using-a-cardlist-in-multichoicemode)
* LIB: Added a `CardHeader.OnPrepareCardHeaderPopupMenuListener`  to customize the popupMenu dynamically.[(doc)](/doc/HEADER.md#standard-header-with-the-overflow-botton-and-popup-menu)
* DEMO: Added an example with CardGridArrayMultiChoiceAdapter
* DEMO: Added an example with CardArrayMultiChoiceAdapter
* DEMO: Added an example with the popupMenu changed dynamically (in Header screen)
* Migrated library and demo to gradle 1.10 and gradle-plugin 0.8.0


Version 1.2.0 *(2014-01-07)*
----------------------------
* LIB: Added a feature to enable the expand/collapse action anywhere not only on button header [(doc)](/doc/EXPAND.md#expand-the-card-by-clicking-on-different-view)
* LIB: Added a feature to provide a custom undoBar [(doc)](/doc/CARDLIST.md#swipe-and-undo-with-a-custom-undobar)
* DEMO: Added a new section CardExpand with new feature to enable the expand/collapse action anywhere
* LIB: Fixed some issues with swipe action: swipe with ExpandCard, swipe with cards with different height, un-highlight items on swipe, swipe and onClickListener events
* LIB: Fixed overflow rounded icon in xxhdpi resources
* LIB: Fixed margin to default title on Header
* LIB: Added a right margin on header buttons
* Migrated library and demo to gradle 1.9 and gradle-plugin 0.7.0


Version 1.1.0 *(2013-12-16)*
----------------------------
* LIB: Added a `CustomSource` interface to load the CardThumbnails from your custom source preserving the built-in feature as AsyncTask and LRUCache. [(doc)](/doc/THUMBNAIL.md#thumbnail-from-custom-source)
* LIB: Added a method to modify your bitmap before it is attached to the image View. In this way you can build rounded or circular images for example[(doc)](/doc/THUMBNAIL.md#how-to-modify-bitmap-and-create-circular-or-rounded-images)
* LIB: Changed the overflow icon in Header with 3 rounded dots. If you would like to use the old icon read [this.](/doc/HEADER.md#standard-header-with-the-overflow-botton-and-popup-menu)
* LIB: Added state_activated in default card_selector
* DEMO: Added an example with CustomSource (in Thumbnail screen)
* DEMO: Added examples with circular and rounded images (in Misc and Google Birthday screen)
* DEMO: Added an example with a Card with contextual action mode in Card page [(doc)](/doc/CARD.md#using-card-with-contextual-action-mode)
* DEMO: Added an example with Header with buttons on the left (in Header screen)



Version 1.0.0 *(2013-12-03)*
----------------------------
* DEMO-extras: Added an example with [StickyListHeaders by Emil Sjölander](https://github.com/emilsjolander/StickyListHeaders) [(doc)](/doc/OTHERLIBRARIES.md#using-card-with-stickylistheaders)
* LIB: Added a CardGridCursorAdapter [(doc)](/doc/CARDGRID.md#using-a-cursor-adapter)
* LIB: Added a new method to change dynamically the card background with a Drawable object. [(doc)](/doc/CARD.md#change-dynamically-card-background-with-a-drawable-object)
* LIB: Fixed an issue on CardHeader.OnClickCardHeaderPopupMenuListener. [(You can see this page to fix your code)](https://github.com/gabrielemariotti/cardslib/issues/35)
* LIB: Minor fixes (removed clickable warning, removed scaleType in base thumbnail layout)
* DEMO: Added an example to change dynamically the card background
* DEMO: Added an example with CardGridCursorAdapter [(source)](/demo/stock/src/main/java/it/gmariotti/cardslib/demo/fragment/GridCursorCardFragment.java)


Version 0.9.0 *(2013-11-19)*
----------------------------
 * LIB: Added a CardCursorAdapter [(doc)](/doc/CARDLIST.md#using-a-cursor-adapter)
 * LIB: Fixed an issue collapsing cards when you don't have enough items to fill the screen.
 * DEMO: Added an example with CardCursorAdapter [(source)](/demo/stock/src/main/java/it/gmariotti/cardslib/demo/fragment/ListCursorCardFragment.java)
 * DEMO-extras: Added an example with [Crouton by  Benjamin Weiss](https://github.com/keyboardsurfer/Crouton) [(doc)](/doc/OTHERLIBRARIES.md#using-card-as-a-crouton)
 * DEMO-extras: Updated ActionBar-PullToRefresh example to new API (0.9.1)
 * DEMO-extras: minor fixes


Version 0.8.0 *(2013-11-11)*
----------------------------

 * DEMO-extras: Added an example with [ListViewAnimations by Niek Haarman](https://github.com/nhaarman/ListViewAnimations) [(doc)](/doc/OTHERLIBRARIES.md#using-card-with-listviewanimations)
 * DEMO-extras: Added an example with grid and ListViewAnimations
 * LIB: Added a new method to add external adapter different from CardArrayAdapter. [(doc)](/doc/CARDLIST.md#how-to-use-an-external-adapter)
 * LIB: Fixed margin on shadow according to rounded corners
 * DEMO: Fixed resources for XXHDPI screen
 * Migrated library and demo to sdk 19 (updated gradle files)

Version 0.7.0 *(2013-11-01)*
----------------------------

 * DEMO-extras: Added an example with [ActionBar-PullToRefresh by Chris Banes](https://github.com/chrisbanes/ActionBar-PullToRefresh) [(doc)](/doc/OTHERLIBRARIES.md#using-card-with-actionbar-pulltorefresh)
 * LIB: Added a new method to change dynamically the card background. [(doc)](/doc/CARD.md#change-dynamically-card-background)
 * LIB: Added errorResourceId in CardThumbnail [(doc)](/doc/THUMBNAIL.md#error-resource-id)
 * LIB: Improved Broadcast when the download is finished/cancelled in CardViewThumbnail [(doc)](/doc/THUMBNAIL.md#broadcast-to-know-when-the-download-is-finished)
 * LIB: Improved caching in CardThumbnail
 * DEMO: Added an example with colored cards [(source)](/demo/stock/src/main/java/it/gmariotti/cardslib/demo/fragment/ListColorFragment.java)
 * DEMO: Fixed issues on Google Stock Card (NPE) and NavigationDrawer (Resources$NotFoundException)



Version 0.6.0 *(2013-10-20)*
----------------------------

 * LIB: CardThumbnail can load image with your favorite library [(doc)](/doc/THUMBNAIL.md#using-external-library)
 * DEMO-extras: Added an extra demo with an integration with [Picasso library](https://github.com/square/picasso) in CardThumbnail
 * DEMO-extras: Added an extra demo with an integration with [Ion library](https://github.com/koush/ion) in CardThumbnail
 * DEMO-extras: Added an extra demo with an integration with [Android-Universal-Image-Loader](https://github.com/nostra13/Android-Universal-Image-Loader) in CardThumbnail


Version 0.5.0 *(2013-10-13)*
----------------------------

 * LIB: Now you can export your card as Bitmap [(doc)](/doc/CARD.md#export-card-as-bitmap)
 * LIB: Now adapters support cards with different Inner Layouts [(doc)](/doc/CARDLIST.md#cards-with-different-inner-layouts)
 * LIB: Added an Undo Action on swipe in `CardArrayAdapter` [(doc)](/doc/CARDLIST.md#swipe-and-undo-in-cardlistview)
 * LIB: Added a method to replace the inner layout in a card: `cardView.replaceCard(Card card)`. - [(doc)](/doc/CARD.md#replace-inner-layout-in-a-card)
 * LIB: Added a `id` field in the `Card` model.
 * LIB: Added a `type` field in the `Card` model. It can be useful with adapters.
 * LIB: Changed the card background. Now cards have a default corner radius = 2dp. You can customize it easily . [(doc)](/doc/CARD.md#customize-card-background)
 * LIB: Added Broadcast to know when the download is finished and image is attached to ImageView through CardViewThumbnail [(doc)](/doc/THUMBNAIL.md#broadcast-to-know-when-the-download-is-finished)
 * DEMO: Added the share action in `BirthDayCardFragment` and `StockCardFragment` to export a card in a bitmap format.
 * DEMO: Added a list with different inner layouts.
 * DEMO: Added an example with undo action.
 * DEMO: Added an example with "replace inner layout" in Refresh card example.


Version 0.4.0 *(2013-10-06)*
----------------------------

 * LIB: Added `CardGridView` with a `CardGridArrayAdapter`. - [(doc)](/doc/CARDGRID.md)
 * DEMO: Added a simple Grid View
 * DEMO: Added a grid of Cards as Google Play


Version 0.3.0 *(2013-10-01)*
----------------------------

 * LIB: Added `CardListView` with a `CardArrayAdapter`. - [(doc)](/doc/CARDLIST.md)
 * LIB: Added a method to refresh card: `cardView.refreshCard(card)`. - [(doc)](/doc/CARD.md#refresh-a-card)
 * DEMO: Added a card refresh example
 * DEMO: Added a simple list example
 * DEMO: Added a list with expandable and collapsible cards
 * DEMO: Added a list of Cards as Google Play


Version 0.2.0 *(2013-09-26)*
----------------------------

 * Card Library is now pushed to Maven Central as a AAR


Version 0.1.1 *(2013-09-14)*
----------------------------

 * LIB: Rename folder in demo and library
 * DEMO: Removed addToBackStack in Demo


Version 0.1.0 *(2013-09-19)*
----------------------------
Initial release.
