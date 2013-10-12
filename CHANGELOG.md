Change Log
===============================================================================

Version 0.5.0 *(2013-XX-XX)*
----------------------------

 * LIB: Now adapters support cards with different Inner Layouts [You can read the section 'Cards with different inner layouts' in this page](https://github.com/gabrielemariotti/cardslib/tree/master/doc/CARDLIST.md)
 * LIB: Added an Undo Action on swipe in `CardArrayAdapter` [You can read the section 'Swipe and Undo' in this page](https://github.com/gabrielemariotti/cardslib/tree/master/doc/CARDLIST.md)
 * LIB: Added a method to replace the inner layout in a card: `cardView.replaceCard(Card card)`. - [You can read the section 'Replace inner layout in a card' in this page](https://github.com/gabrielemariotti/cardslib/tree/master/doc/CARD.md)
 * LIB: Added a `id` field in the `Card` model.
 * LIB: Added a `type` field in the `Card` model. It can be useful with adapters.
 * LIB: Changed the card background. Now cards have a default corner radius = 2dp. You can customize it easily . [You can read the section 'Customize Card background' in this page](https://github.com/gabrielemariotti/cardslib/tree/master/doc/CARD.md)
 * DEMO: Added a list with different inner layouts.
 * DEMO: Added an example with undo action.
 * DEMO: Added an example with "replace inner layout" in Refresh card example.


Version 0.4.0 *(2013-10-06)*
----------------------------

 * LIB: Added `CardGridView` with a `CardGridArrayAdapter`. - [You can read this page](https://github.com/gabrielemariotti/cardslib/tree/master/doc/CARDGRID.md)
 * DEMO: Added a simple Grid View
 * DEMO: Added a grid of Cards as Google Play


Version 0.3.0 *(2013-10-01)*
----------------------------

 * LIB: Added `CardListView` with a `CardArrayAdapter`. - [You can read this page](https://github.com/gabrielemariotti/cardslib/tree/master/doc/CARDLIST.md)
 * LIB: Added a method to refresh card: `cardView.refreshCard(card)`. - [You can read the section 'Refresh a card' in this page](https://github.com/gabrielemariotti/cardslib/tree/master/doc/CARD.md)
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