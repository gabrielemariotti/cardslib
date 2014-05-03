# Cards Library: Expand

In this page you can find info about:

* [Creating a base CardExpand](#creating-a-base-cardexpand)
* [Standard Expand](#standard-expand)
* [Custom Expand inflating your inner layout](#custom-expand-inflating-your-inner-layout)
* [Expand the card by clicking on different view](#expand-the-card-by-clicking-on-different-view)
* [CardExpand and CardListView](#cardexpand-and-cardlistview)


### Creating a base CardExpand

Creating a base `CardExpand` is pretty simple.

``` java
        //This provide a simple (and useless) expand area
        CardExpand expand = new CardExpand(getContext());

        //Set inner title in Expand Area
        expand.setTitle(getString(R.string.demo_expand_basetitle));

        //Add expand to card
        card.addCardExpand(expand);
```

`CardExpand` provides a base Expand InnerLayout.

You can find it in `res/layout/inner_base_expand.xml`.

The built-in InnerLayout provide these features:

* a title

There are many ways you can customize the card expand.

### Standard Expand

If you want a standard `CardExpand` you can use this simple code:

``` java
        //This provide a simple (and useless) expand area
        CardExpand expand = new CardExpand(getContext());

        //Set inner title in Expand Area
        expand.setTitle(getString(R.string.demo_expand_basetitle));

        //Add expand to a card
        card.addCardExpand(expand);
```

![Screen](/demo/images/header/expand.png)


### Custom Expand inflating your inner layout

If you want to customize the `CardExpand` you can extend the base class.

You can inflate your inner layout, then you can populate your layout with `setupInnerViewElements(ViewGroup parent, View view)` method

``` java
    public class CustomExpandCard extends CardExpand {

        //Use your resource ID for your inner layout
        public CustomExpandCard(Context context) {
            super(context, R.layout.carddemo_example_inner_expand);
        }

        @Override
        public void setupInnerViewElements(ViewGroup parent, View view) {

            if (view == null) return;

            //Retrieve TextView elements
            TextView tx1 = (TextView) view.findViewById(R.id.carddemo_expand_text1);
            TextView tx2 = (TextView) view.findViewById(R.id.carddemo_expand_text2);
            TextView tx3 = (TextView) view.findViewById(R.id.carddemo_expand_text3);
            TextView tx4 = (TextView) view.findViewById(R.id.carddemo_expand_text4);

            //Set value in text views
            if (tx1 != null) {
                tx1.setText(getContext().getString(R.string.demo_expand_customtitle1));
            }

            if (tx2 != null) {
                tx2.setText(getContext().getString(R.string.demo_expand_customtitle2));
            }
        }
}
```

Then you can add this custom `CustomExpandCard` to your `Card`:

``` java
        //This provide a simple (and useless) expand area
        CustomExpandCard expand = new CustomExpandCard(getContext());

        //Add Expand Area to a Card
        card.addCardExpand(expand);
```

![Screen](/demo/images/header/expandCustom.png)

You can use these listeners to listen any callbacks when animations end.

* `Card.OnExpandAnimatorEndListener` invoked  when expand animation ends
* `Card.OnCollapseAnimatorEndListener` invoked when collapse animator ends.

Example:

``` java
        card.setOnExpandAnimatorEndListener(new Card.OnExpandAnimatorEndListener() {
            @Override
            public void onExpandEnd(Card card) {
                //Do something
            }
        });
```

### Expand the card by clicking on different view

You can enable the expand/collapse action on different Views rather than the expand button on header.

Default way: it enables the expand button on the header.

``` java
        //Create a CardHeader
        CardHeader header = new CardHeader(getContext());

        //Set visible the expand/collapse button
        header.setButtonExpandVisible(true);
```

Pay attention: the method `header.setButtonExpandVisible(true)` has a higher priority.

You can enable the expand/collapse action by clicking on a different View.
You have to set the `ViewToClickToExpand` on the `Card`.

``` java
       ViewToClickToExpand.builder()
             .setupView(cardView); //setup the view which enables the expand/collapse action
             .highlightView(true); //true to highlight the view as selected (default=false)

```


How to enable expand/collapse action by clicking on the card.

``` java
        //Create a Card
        CustomCard card = new CustomCard(getActivity());

        //This provides a simple (and useless) expand area
        CardExpand expand = new CardExpand(getActivity());
        //Set inner title in Expand Area
        expand.setTitle(getString(R.string.demo_expand_basetitle));
        card.addCardExpand(expand);


        //Set card in the cardView
        CardView cardView = (CardView) getActivity().findViewById(R.id.carddemo_example_card_expand2);

        ViewToClickToExpand viewToClickToExpand =
                ViewToClickToExpand.builder()
                        .setupView(cardView);
        card.setViewToClickToExpand(viewToClickToExpand);

        cardView.setCard(card);
```

How to enable expand/collapse action by clicking on an element of the Card.

``` java
    public class CustomCard2 extends Card{

        public CustomCard2(Context context) {
            super(context,R.layout.carddemo_example_cardexpand_inner_content);
        }

        @Override
        public void setupInnerViewElements(ViewGroup parent, View view) {

            if (view != null) {
                TextView mTitleView = (TextView) view.findViewById(it.gmariotti.cardslib.library.R.id.card_main_inner_simple_title);
                if (mTitleView != null){
                    mTitleView.setText(mTitle);

                    ViewToClickToExpand viewToClickToExpand =
                            ViewToClickToExpand.builder()
                                    .setupView(mTitleView);
                    setViewToClickToExpand(viewToClickToExpand);
                }
            }
        }
    }

```


How to enable expand/collapse action by clicking on the image on Thumbnail.

``` java
   public class CustomThumbnail extends CardThumbnail{

          public CustomThumbnail(Context context) {
              super(context);
          }

          @Override
          public void setupInnerViewElements(ViewGroup parent, View imageView) {

              ViewToClickToExpand viewToClickToExpand =
                      ViewToClickToExpand.builder()
                              .setupView(imageView);
              getParentCard().setViewToClickToExpand(viewToClickToExpand);
          }
   }

```

You can see some examples in [`CardExpandFragment`](/demo/stock/src/main/java/it/gmariotti/cardslib/demo/fragment/CardExpandFragment.java).


How to enable the custom expand/collapse in a `ListView`.
``` java
   public class MyCard extends Card{

        public MyCard(Context context) {
            super(context);
        }

        @Override
        public void setupInnerViewElements(ViewGroup parent, View view) {
            //Example on the card
            ViewToClickToExpand viewToClickToExpand = ViewToClickToExpand.builder().setupView(getCardView());
            setViewToClickToExpand(viewToClickToExpand);
        }
    }
```

This code requires that your `Card` has a MainContentLayout `android:id="@+id/card_main_content_layout"`, otherwise you can use the new method described below.


Also you can enable the expand/collapse action on the default card elements using this method:


``` java
   ViewToClickToExpand viewToClickToExpand =
                ViewToClickToExpand.builder()
                        .highlightView(false)
                        .setupCardElement(ViewToClickToExpand.CardElementUI.CARD);
   card.setViewToClickToExpand(viewToClickToExpand);
```

Pay attention: the method `ViewToClickToExpand.setupView(imageView);` has a higher priority than the method `ViewToClickToExpand.setupCardElement()`.

You can use this cardElementUI:

- `ViewToClickToExpand.CardElementUI.CARD`: to enable the click on the whole card
- `ViewToClickToExpand.CardElementUI.HEADER`: : to enable the click on the header
- `ViewToClickToExpand.CardElementUI.MAIN_CONTENT`: to enable the click on the main content (not the whole card)
- `ViewToClickToExpand.CardElementUI.THUMBNAIL`: to enable the click on the thumbnail (the whole thumbnail)

You can also use this feature with a `ListView`.

``` java
      public class MyCard extends Card{

           public MyCard(Context context) {
               super(context);
               init();
           }

           protected void init(){
               ViewToClickToExpand viewToClickToExpand =
                           ViewToClickToExpand.builder()
                                   .highlightView(false)
                                   .setupCardElement(ViewToClickToExpand.CardElementUI.CARD);
               card.setViewToClickToExpand(viewToClickToExpand);
           }
       }
```

You can see some examples in [`ExpandPicassoFragment`](/demo/extras/src/main/java/it/gmariotti/cardslib/demo/extras/fragment/ExpandPicassoFragment.java).


### CardExpand and CardListView

You can use the `CardExpand` inside the `CardListView`.

With a **`CardArrayAdapter`** you can use the same code described above.

You can find an example in [`ListExpandCardFragment`](/demo/stock/src/main/java/it/gmariotti/cardslib/demo/fragment/ListExpandCardFragment.java).

``` java
        Card card = new Card(getActivity());

        //Create a CardHeader
        CardHeader header = new CardHeader(getActivity());
        header.setButtonExpandVisible(true);
        //Add Header to card
        card.addCardHeader(header);

        //This provides a simple (and useless) expand area
        CustomExpandCard expand = new CustomExpandCard(getActivity(),i);
        //Add Expand Area to Card
        card.addCardExpand(expand);

        //Just an example to expand a card
        card.setExpanded(true);
```

If you want to set a card as expanded/collapsed, you can use:

``` java
        card.setExpanded(true);
```

If you want to know about your card, you can use

``` java

        card.isExpanded()
```

When you click a card to expand or collapse the method `adapter.notifyDataSetChanged` will be called.


With a **`CardCursorAdapter`** you have to use this code:

``` java
    public class MyCursorCardAdapter extends CardCursorAdapter {


        public MyCursorCardAdapter(Context context) {
            super(context);
        }

        @Override
        protected Card getCardFromCursor(Cursor cursor) {
            MyCursorCard card = new MyCursorCard(super.getContext());
            setCardFromCursor(card,cursor);

            //Create a CardHeader
            CardHeader header = new CardHeader(getActivity());
            //Set the header title
            header.setButtonExpandVisible(true);
            //Add Header to card
            card.addCardHeader(header);

            //This provides a simple (and useless) expand area
            CustomExpandCard expand = new CustomExpandCard(getActivity());
            //Add Expand Area to Card
            card.addCardExpand(expand);

            //Don't use card.setExpanded(true)!
            //Don't use mAdapter.setExpanded(card) here !

            //Animator listener
            card.setOnExpandAnimatorEndListener(new Card.OnExpandAnimatorEndListener() {
                @Override
                public void onExpandEnd(Card card) {
                    Toast.makeText(getActivity(),"Expand "+card.getCardHeader().getTitle(),Toast.LENGTH_SHORT).show();
                }
            });

            card.setOnCollapseAnimatorEndListener(new Card.OnCollapseAnimatorEndListener() {
                @Override
                public void onCollapseEnd(Card card) {
                    Toast.makeText(getActivity(),"Collpase " +card.getCardHeader().getTitle(),Toast.LENGTH_SHORT).show();
                }
            });

            return card;
        }
```

**Pay attention**: To work with a `CardCursorAdapter` and expand/collapse feature you have to set your Id inside your card.

``` java
   card.setId("xxxx)";
```

The `id` must be unique, and it can be useful to set it with your id in the database.


If you want to set a card as expanded/collapsed, you can use one of these methods:

``` java
        //To expand
        mAdapter.setExpanded(card);
        mAdapter.setExpanded(cardId);

        //To collapse
        mAdapter.setCollapsed(card);
        mAdapter.setCollapsed(cardId);
```

**Pay attention**: don't call this method inside `getCardFromCursor` method (because it is called by `bindView` method)


If you want to know about your card, you can use:

``` java
    .mAdapter.isExpanded(Card card)
```

If you want to know  all ids expanded you can use:

``` java
    .mAdapter.getExpandedIds()
```

In this case the method `adapter.notifyDataSetChanged` will NOT be called.


**Migration from 1.4.0 (and previous releases) - to 1.4.2**

The 1.4.2 may introduce a breaking change with CardCursorAdapter and expand feature.
To migrate your code you have to:

* call `card.setId("xxxx)";` in your `getCardFromCursor` method
* remove `card.setExpanded(true/false)` from your adapter code
* use `mAdapter.setExpanded(card);` as described above to expand/collapse your cards.