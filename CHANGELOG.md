Change Log
===============================================================================

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
