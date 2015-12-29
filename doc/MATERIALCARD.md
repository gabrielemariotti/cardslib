# Cards Library: Material Card

In this page you can find info about:

* [Intro](#intro)
* [How to build a Material Card with an image](#how-to-build-a-material-card-with-an-image)
* [How to build a Material Card with an image and text](#how-to-build-a-material-card-with-an-image-and-text)
* [Rounded corners on Android 4.x](#rounded-corners-on-android-4.x)
* [Style](#style)

### Intro

> **PAY ATTENTION:** to use this feature you have to use the **library-cards**.
> [for more info](GUIDE.md#including-in-your-project).

![Screen](/demo/images/card/materialcard_small.png)

### How to build a Material Card with an image

This type of Card has:

 - an image
 - a text over the image
 - a customizable area with text
 - a customizable area with text supplemental actions

You can use a layout like this:

```xml
            <it.gmariotti.cardslib.library.view.CardViewNative
                android:id="@+id/carddemo_largeimage"
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                card:card_layout_resourceID="@layout/native_material_largeimage_card"
                style="@style/card_external"
                />
```

Here an example:
``` java
 MaterialLargeImageCard card =
          MaterialLargeImageCard.with(getActivity())
                  .setTextOverImage("Italian Beaches")
                  .useDrawableId(R.drawable.im_beach)
                  .setupSupplementalActions(R.layout.carddemo_native_material_supplemental_actions_large_icon,actions )
                  .build();
```                  


The supplemental actions are built with this code: 

``` java                          
          // Set supplemental actions as icon
          ArrayList<BaseSupplementalAction> actions = new ArrayList<BaseSupplementalAction>();
          
          IconSupplementalAction t1 = new IconSupplementalAction(getActivity(), R.id.ic1);
          t1.setOnActionClickListener(new BaseSupplementalAction.OnActionClickListener() {
              @Override
              public void onClick(Card card, View view) {
                  Toast.makeText(getActivity()," Click on Text SHARE ",Toast.LENGTH_SHORT).show();
              }
          });
          actions.add(t1);
  
          IconSupplementalAction t2 = new IconSupplementalAction(getActivity(), R.id.ic2);
          t2.setOnActionClickListener(new BaseSupplementalAction.OnActionClickListener() {
              @Override
              public void onClick(Card card, View view) {
                  Toast.makeText(getActivity()," Click on Text LEARN ",Toast.LENGTH_SHORT).show();
              }
          });
          actions.add(t2);
```

To build the actions you can clone [this layout](/demo/stock/src/main/res/layout/carddemo_native_material_supplemental_actions_large.xml) or [this layout](/demo/stock/src/main/res/layout/carddemo_native_material_supplemental_actions_large_icon.xml)  or use something like this 

```xml
    <!-- element for a IconSupplementalAction -->
    <ImageButton
        android:id="@+id/ic1"
        android:scaleType="fitCenter"
        android:src="@drawable/l_search"
        android:background="?android:selectableItemBackground"
        style="@style/card.native.actions.button"
        android:layout_marginRight="8dp"
        />
        
     <!-- element for a TextSupplementalAction -->
     <TextView
            android:id="@+id/text1"
            android:text="SHARE"
            android:background="?android:selectableItemBackground"
            android:layout_width="wrap_content"
            style="@style/card.native.actions"
            android:layout_height="wrap_content"/>
```

Currently (this part will be improved with the next updates) you have to provide:

 - a layout with the actions
 - an array of `IconSupplementalAction` or `TextSupplementalAction`
  
By default the clickListener is assigned to the content area (the card except the area with the supplemental actions). 

```java
          card.setOnClickListener(new Card.OnCardClickListener() {
              @Override
              public void onClick(Card card, View view) {
                  Toast.makeText(getActivity()," Click on ActionArea ",Toast.LENGTH_SHORT).show();
              }
          });
```

  
If you would like to use a custom code to load the image you can use this:

```java
        //Create a Card, set the title over the image and set the thumbnail
        MaterialLargeImageCard card =
                MaterialLargeImageCard.with(getActivity())
                        .setTextOverImage("Italian Beaches")
                        .setTitle("This is my favorite local beach")
                        .setSubTitle("A wonderful place")
                        .useDrawableExternal(new MaterialLargeImageCard.DrawableExternal() {
                            @Override
                            public void setupInnerViewElements(ViewGroup parent, View viewImage) {

                                Picasso.with(getActivity()).setIndicatorsEnabled(true);  //only for debug tests
                                Picasso.with(getActivity())
                                        .load("https://lh5.googleusercontent.com/-squZd7FxR8Q/UyN5UrsfkqI/AAAAAAAAbAo/VoDHSYAhC_E/s96/new%2520profile%2520%25282%2529.jpg")
                                        .error(R.drawable.ic_error_loadingsmall)
                                        .into((ImageView) viewImage);
                            }
                        })
                        .setupSupplementalActions(R.layout.carddemo_native_material_supplemental_actions_large, actions)
                        .build();
```

### How to build a Material Card with an image and text

This card has the same features of the card above but it can display a built-in text with a

 - title
 - subtitle


```java
  MaterialLargeImageCard.with(getActivity())
                        .setTitle("This is my favorite local beach")
                        .setSubTitle("A wonderful place")
```                        

You can use a layout like this:

```xml
      <it.gmariotti.cardslib.library.view.CardViewNative
                android:id="@+id/carddemo_largeimage_text"
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                card:card_layout_resourceID="@layout/native_material_largeimage_text_card"
                style="@style/card_external"
                />
```

Here a full example:
```java
        // Set supplemental actions as text
        ArrayList<BaseSupplementalAction> actions = new ArrayList<BaseSupplementalAction>();

        // Set supplemental actions
        TextSupplementalAction t1 = new TextSupplementalAction(getActivity(), R.id.text1);
        t1.setOnActionClickListener(new BaseSupplementalAction.OnActionClickListener() {
            @Override
            public void onClick(Card card, View view) {
                Toast.makeText(getActivity()," Click on Text SHARE ",Toast.LENGTH_SHORT).show();
            }
        });
        actions.add(t1);

        TextSupplementalAction t2 = new TextSupplementalAction(getActivity(), R.id.text2);
        t2.setOnActionClickListener(new BaseSupplementalAction.OnActionClickListener() {
            @Override
            public void onClick(Card card, View view) {
                Toast.makeText(getActivity()," Click on Text LEARN ",Toast.LENGTH_SHORT).show();
            }
        });
        actions.add(t2);

        //Create a Card, set the title over the image and set the thumbnail
        MaterialLargeImageCard card =
                MaterialLargeImageCard.with(getActivity())
                        .setTextOverImage("Italian Beaches")
                        .setTitle("This is my favorite local beach")
                        .setSubTitle("A wonderful place")
                        .useDrawableId(R.drawable.sea)
                        .setupSupplementalActions(R.layout.carddemo_native_material_supplemental_actions_large, actions)
                        .build();

        card.setOnClickListener(new Card.OnCardClickListener() {
            @Override
            public void onClick(Card card, View view) {
                Toast.makeText(getActivity()," Click on ActionArea ",Toast.LENGTH_SHORT).show();
            }
        });

```

### Rounded corners on Android 4.x

The Card provides a built-in method to obtain rounded corners in Android<5.0 for the image.
If you are using a custom method (example Picasso) you have to provide to call this method to transform the image.

``` java
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) 
        RoundCornersDrawable.applyRoundedCorners(materialCardThumbnail,imageView,bitmap);
```


### Style

You can customize your card overriding these values:

**Image**

Height:
```xml
    <dimen name="card_material_largeimage_height">200dp</dimen>
```

**Text over the image**

Padding:
```xml
    <dimen name="card_thumbnail_image_text_over_padding_right">16dp</dimen>
    <dimen name="card_thumbnail_image_text_over_padding_left">16dp</dimen>
    <dimen name="card_thumbnail_image_text_over_padding_bottom">16dp</dimen>
    <dimen name="card_thumbnail_image_text_over_padding_top">16dp</dimen>
```
Font-family:
```xml
 <string name="card_font_fontFamily_image_text_over">sans-serif-medium</string>
```
Color:
```xml
  <color name="card_thumbnail_image_text_over_textcolor">#FFF</color>
```   
TextSize:
```xml
  <dimen name="card_thumbnail_image_text_over_textsize">24sp</dimen>
```

**Text area**

Padding:
```xml
    <dimen name="card_main_content_native_material_large_image_paddingLeft">16dp</dimen>
    <dimen name="card_main_content_native_material_large_image_paddingRight">16dp</dimen>
    <dimen name="card_main_content_native_material_large_image_paddingBottom">16dp</dimen>
    <dimen name="card_main_content_native_material_large_image_paddingTop">16dp</dimen>
```

