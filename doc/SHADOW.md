# Cards Library: Shadow

In this page you can find info about:

* [Built-in shadow](#built-in-shadow)
* [Hidden Shadow](#hidden-shadow)
* [Customizing Shadow](#customizing-shadow)
* [Customizing the ShadowLayout](#customizing-the-shadowlayout)
* [Removing Shadow](#removing-shadow)


### Built-in shadow

Your card has a built-in shadow.

The built-in Shadow Layout provides these features:

* a shadow image

You can find it in `res/layout/base_shadow_layout.xml`.

There are many ways you can customize the card shadow.

### Hidden Shadow

If you want a card without shadow  you can use this simple code:

``` java
        //Create a Card
        Card card = new Card(getContext());

        //Hidden shadow
        card.setShadow(false);

        //Set card in the cardView
        CardView cardView = (CardView) getActivity().findViewById(R.id.carddemo_shadow_no);
        cardView.setCard(card);
```

![Screen](/demo/images/shadow/noshadow.png)

### Customizing Shadow

You can use your style files to customize the shadow.

The quickest way to start with this would be to copy `card.shadow_image` style in our project.

``` xml
    <!--Style for shadow image-->
    <style name="card.shadow_image">
        <item name="android:layout_width">match_parent</item>
        <item name="android:layout_height">@dimen/card_shadow_height</item>
        <item name="android:src">@drawable/card_shadow</item>
        <item name="android:layout_below">@+id/card_main_content_layout</item>
    </style>

```

Otherwise you can simply copy `res/drawable/card_shadow.xml` in your project and change it.

``` xml
    <shape xmlns:android="http://schemas.android.com/apk/res/android">
        <gradient
            android:startColor="#33000000"
            android:centerColor="#11000000"
            android:endColor="#00000000"
            android:angle="270" />
    </shape>

```

![Screen](/demo/images/shadow/style.png)

### Customizing the ShadowLayout

You can fully customize your shadow.

You can set your shadow layout inflating your shadow xml layout file.

First of all you have to provide your card layout .The quickest way to start with this would be to copy the `res/layout/card_layout.xml` or `res/layout/card_thumbnail_layout.xml`

You can see `res/layout/carddemo_example_card2_layout.xml`.

``` xml

    <LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
                  xmlns:card="http://schemas.android.com/apk/res-auto"
                  android:orientation="vertical"
                  style="@style/card"
                  android:layout_width="match_parent"
                  android:layout_height="wrap_content">

    .....

    <!-- Compound view for Shadow
         If you want to customize this element use attr card:card_shadow_layout_resourceID -->
        <it.gmariotti.cardslib.library.view.component.CardShadowView
            style="@style/card.shadow_outer_layout"
            android:id="@+id/card_shadow_layout"
            android:layout_width="match_parent"
            card:card_shadow_layout_resourceID="@layout/carddemo_example_card2_shadow_layout"
            android:layout_height="wrap_content"/>


    </LinearLayout>
```

You can specify your shadow layout (compound layout) using this attr:`card:card_shadow_layout_resourceID="@layout/carddemo_example_card2_shadow_layout"`.

Then you have to define your shadow layout:

You can see an exmple in `res/layout/carddemo_example_card2_shadow_layout.xml`

``` xml
    <!-- Image view used for shadow.
         You can customize it with your style file and card.shadow_image style-->
    <ImageView
        xmlns:android="http://schemas.android.com/apk/res/android"
        android:id="@+id/card_shadow_view"
        style="@style/card.shadow_my_image"
        />
```

![Screen](/demo/images/shadow/layout_shadow.png)


### Removing Shadow

You can also remove your shadow from your layout.

To achieve it you have to provide your custom card-layout without the `CardShadowView` tag.

