Change Log
===============================================================================

Version 1.0.0 *(2013-XX-XX)*
----------------------------
* LIB: Fixed an issue on CardHeader.OnClickCardHeaderPopupMenuListener. [(You can see this page to fix your code)](https://github.com/gabrielemariotti/cardslib/issues/35)
* LIB: Added a new method to change dynamically the card background with a Drawable object. [(doc)](https://github.com/gabrielemariotti/cardslib/tree/master/doc/CARD.md#change-dynamically-card-background-with-a-drawable-object)
* DEMO: Added an example to change dynamically the card background


Version 0.9.0 *(2013-11-19)*
----------------------------
 * LIB: Added a CardCursorAdapter [(doc)](https://github.com/gabrielemariotti/cardslib/tree/master/doc/CARDLIST.md#using-a-cursor-adapter)
 * LIB: Fixed an issue collapsing cards when you don't have enough items to fill the screen.
 * DEMO: Added an example with CardCursorAdapter [(source)](https://github.com/gabrielemariotti/cardslib/tree/master/demo/stock/src/main/java/it/gmariotti/cardslib/demo/fragment/ListCursorCardFragment.java)
 * DEMO-extras: Added an example with [Crouton by  Benjamin Weiss](https://github.com/keyboardsurfer/Crouton) [(doc)](https://github.com/gabrielemariotti/cardslib/tree/master/doc/OTHERLIBRARIES.md#using-card-as-a-crouton)
 * DEMO-extras: Updated ActionBar-PullToRefresh example to new API (0.9.1)
 * DEMO-extras: minor fixes


Version 0.8.0 *(2013-11-11)*
----------------------------

 * DEMO-extras: Added an example with [ListViewAnimations by Niek Haarman](https://github.com/nhaarman/ListViewAnimations) [(doc)](https://github.com/gabrielemariotti/cardslib/tree/master/doc/OTHERLIBRARIES.md#using-card-with-listviewanimations)
 * DEMO-extras: Added an example with grid and ListViewAnimations
 * LIB: Added a new method to add external adapter different from CardArrayAdapter. [(doc)](https://github.com/gabrielemariotti/cardslib/tree/master/doc/CARDLIST.md#how-to-use-an-external-adapter)
 * LIB: Fixed margin on shadow according to rounded corners
 * DEMO: Fixed resources for XXHDPI screen
 * Migrated library and demo to sdk 19 (updated gradle files)

Version 0.7.0 *(2013-11-01)*
----------------------------

 * DEMO-extras: Added an example with [ActionBar-PullToRefresh by Chris Banes](https://github.com/chrisbanes/ActionBar-PullToRefresh) [(doc)](https://github.com/gabrielemariotti/cardslib/tree/master/doc/OTHERLIBRARIES.md#using-card-with-actionbar-pulltorefresh)
 * LIB: Added a new method to change dynamically the card background. [(doc)](https://github.com/gabrielemariotti/cardslib/tree/master/doc/CARD.md#change-dynamically-card-background)
 * LIB: Added errorResourceId in CardThumbnail [(doc)](https://github.com/gabrielemariotti/cardslib/tree/master/doc/THUMBNAIL.md#error-resource-id)
 * LIB: Improved Broadcast when the download is finished/cancelled in CardViewThumbnail [(doc)](https://github.com/gabrielemariotti/cardslib/tree/master/doc/THUMBNAIL.md#broadcast-to-know-when-the-download-is-finished)
 * LIB: Improved caching in CardThumbnail
 * DEMO: Added an example with colored cards [(source)](https://github.com/gabrielemariotti/cardslib/tree/master/demo/stock/src/main/java/it/gmariotti/cardslib/demo/fragment/ListColorFragment.java)
 * DEMO: Fixed issues on Google Stock Card (NPE) and NavigationDrawer (Resources$NotFoundException)



Version 0.6.0 *(2013-10-20)*
----------------------------

 * LIB: CardThumbnail can load image with your favorite library [(doc)](https://github.com/gabrielemariotti/cardslib/tree/master/doc/THUMBNAIL.md#using-external-library)
 * DEMO-extras: Added an extra demo with an integration with [Picasso library](https://github.com/square/picasso) in CardThumbnail
 * DEMO-extras: Added an extra demo with an integration with [Ion library](https://github.com/koush/ion) in CardThumbnail
 * DEMO-extras: Added an extra demo with an integration with [Android-Universal-Image-Loader](https://github.com/nostra13/Android-Universal-Image-Loader) in CardThumbnail


Version 0.5.0 *(2013-10-13)*
----------------------------

 * LIB: Now you can export your card as Bitmap [(doc)](https://github.com/gabrielemariotti/cardslib/tree/master/doc/CARD.md#export-card-as-bitmap)
 * LIB: Now adapters support cards with different Inner Layouts [(doc)](https://github.com/gabrielemariotti/cardslib/tree/master/doc/CARDLIST.md#cards-with-different-inner-layouts)
 * LIB: Added an Undo Action on swipe in `CardArrayAdapter` [(doc)](https://github.com/gabrielemariotti/cardslib/tree/master/doc/CARDLIST.md#swipe-and-undo-in-cardlistview)
 * LIB: Added a method to replace the inner layout in a card: `cardView.replaceCard(Card card)`. - [(doc)](https://github.com/gabrielemariotti/cardslib/tree/master/doc/CARD.md#replace-inner-layout-in-a-card)
 * LIB: Added a `id` field in the `Card` model.
 * LIB: Added a `type` field in the `Card` model. It can be useful with adapters.
 * LIB: Changed the card background. Now cards have a default corner radius = 2dp. You can customize it easily . [(doc)](https://github.com/gabrielemariotti/cardslib/tree/master/doc/CARD.md#customize-card-background)
 * LIB: Added Broadcast to know when the download is finished and image is attached to ImageView through CardViewThumbnail [(doc)](https://github.com/gabrielemariotti/cardslib/tree/master/doc/THUMBNAIL.md#broadcast-to-know-when-the-download-is-finished)
 * DEMO: Added the share action in `BirthDayCardFragment` and `StockCardFragment` to export a card in a bitmap format.
 * DEMO: Added a list with different inner layouts.
 * DEMO: Added an example with undo action.
 * DEMO: Added an example with "replace inner layout" in Refresh card example.


Version 0.4.0 *(2013-10-06)*
----------------------------

 * LIB: Added `CardGridView` with a `CardGridArrayAdapter`. - [(doc)](https://github.com/gabrielemariotti/cardslib/tree/master/doc/CARDGRID.md)
 * DEMO: Added a simple Grid View
 * DEMO: Added a grid of Cards as Google Play


Version 0.3.0 *(2013-10-01)*
----------------------------

 * LIB: Added `CardListView` with a `CardArrayAdapter`. - [(doc)](https://github.com/gabrielemariotti/cardslib/tree/master/doc/CARDLIST.md)
 * LIB: Added a method to refresh card: `cardView.refreshCard(card)`. - [(doc)](https://github.com/gabrielemariotti/cardslib/tree/master/doc/CARD.md#refresh-a-card)
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
