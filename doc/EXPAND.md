# Cards Library: Expand

In this page you can find info about:

* [Creating a base CardExpand](#creating-a-base-cardexpand)
* [Standard Expand](#standard-expand)
* [Custom Expand inflating your inner layout](#custom-expand-inflating-your-inner-layout)
* [Expand the card by clicking on different view](#expand-the-card-by-clicking-on-different-view)


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

![Screen](https://github.com/gabrielemariotti/cardslib/raw/master/demo/images/header/expand.png)


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

![Screen](https://github.com/gabrielemariotti/cardslib/raw/master/demo/images/header/expandCustom.png)

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

You can enable the expand/collapse action by clicking on a different Views.
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


How to enable expand/collapse action by clicking on the Thumbnail.

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

You can see some examples in [`CardExpandFragment`]((https://github.com/gabrielemariotti/cardslib/tree/master/demo/stock/src/main/java/it/gmariotti/cardslib/demo/fragment/CardExpandFragment.java).)


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