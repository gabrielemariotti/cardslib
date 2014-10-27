# Cards Library: TopColored Card

In this page you can find info about:

* [Intro](#intro)
* [How to build a TopColored Card with an image](#how-to-build-a-topcolored-card-with-an-image)
* [Rounded corners on Android 4.x](#rounded-corners-on-android-4.x)
* [Style](#style)

### Intro

> **PAY ATTENTION:** to use this feature you have to use the **library-cards**.
> [for more info](GUIDE.md#including-in-your-project).

![Screen](/demo/images/card/topcolored.png)

### How to build a TopColored Card with an image

This type of Card has:

 - a colored band with a title and a subtitle
  - a customizable area 
  
You can use a layout like this:

```xml
            <it.gmariotti.cardslib.library.view.CardViewNative
                android:id="@+id/carddemo_largeimage"
                android:layout_width="match_parent"
                android:layout_height="250dp"
                card:card_layout_resourceID="@layout/native_material_topcolored_card"
                style="@style/card_external"
                />
```

Use the builder to build the card:

``` java
  TopColoredCard card = TopColoredCard.with(getActivity())
                 .setColorResId(R.color.carddemo_halfcolored_color)
                 .setTitleOverColor("22 mins to Ancona")
                 .setSubTitleOverColor("Light traffic on SS16")
                 .setupSubLayoutId(R.layout.carddemo_native_halfcolored_simple_title)
                 .setupInnerElements(new TopColoredCard.OnSetupInnerElements() {
                     @Override
                     public void setupInnerViewElementsSecondHalf(View secondHalfView) {
 
                         TextView mSimpleTitleView = (TextView) secondHalfView.findViewById(R.id.carddemo_halfcolored_simple_title);
                         if (mSimpleTitleView!=null) {
                             mSimpleTitleView.setText("It is just an example!");
                         }
                     }
                 })
                 .build();
 
         //Set card in the CardViewNative
         CardViewNative cardView = (CardViewNative) getActivity().findViewById(R.id.carddemo_halfcolored);
         cardView.setCard(card);
```

You can use the `setupSubLayoutId` method and the `setupInnerViewElementsSecondHalf` callback to customize the area below the band.


### Rounded corners on Android 4.x

The Card provides a built-in method to obtain rounded corners in Android<5.0 for the top band.


### Style

You can customize your card overriding these values:


**Title**
```xml
    <style name="card.native.top_colored_title" >
    <dimen name="card_topcolored_title_textsize">22dp</dimen>
    <color name="card_topcolored_title_textcolor">#FFF</color>
```
 
**SubTitle**
 ```xml
    <style name="card.native.top_colored_subtitle" >
    <dimen name="card_topcolored_subtitle_textsize">14dp</dimen>
    <dimen name="card_topcolored_subtitle_paddingtop">18dp</dimen>
    <color name="card_topcolored_subtitle_textcolor">#80FFFFFF</color>
```
 
**Band container**
```xml
    <style name="card.native.top_colored_layout">
    <dimen name="card_topcolored_layout_minHeigth">75dp</dimen>
    <dimen name="card_topcolored_layout_padding">16dp</dimen>
```
