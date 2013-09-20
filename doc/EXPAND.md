# Cards Library: Expand

Create a base `CardExpand` is pretty simple.

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

![Screen](https://github.com/gabrielemariotti/cardslib/raw/master/CardsDemo/images/header/expand.png)


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

![Screen](https://github.com/gabrielemariotti/cardslib/raw/master/CardsDemo/images/header/expandCustom.png)

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