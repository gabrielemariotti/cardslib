# Cards Library: Thumbnail

In this page you can find info about:

* [Quick Start](#quick-start)
* [Basic usage](#basic-usage)
* [Thumbnail from Resource ID](#thumbnail-from-resource-id)
* [Thumbnail from Resource URL](#thumbnail-from-resource-url)
* [Thumbnail from Custom Source](#thumbnail-from-custom-source)
* [Using external library](#using-external-library)
* [Using with a Bitmap](#using-with-a-bitmap)
* [Error resource id](#error-resource-id)
* [Customizing the Thumbnail Layout](#customizing-the-thumbnail-layout)
* [Customize Thumbnail](#customize-thumbnail)
* [Broadcast to know when the download is finished](#broadcast-to-know-when-the-download-is-finished)
* [How to modify the bitmap and create circular or rounded images](#how-to-modify-the-bitmap-and-create-circular-or-rounded-images)


### Quick Start

* The built-in thumbnail uses an `AsyncTask` and `LRUCache` to improve performance.
* You have to provide a layout with a thumbnail

The default layout doesn't contain the thumbnail. You to use the `res/layout/card_thumbnail_layout.xml` or a your custom layout. Read [Customizing the Thumbnail Layout](#customizing-the-thumbnail-layout) paragraph for more info.

Example:

``` xml
        <it.gmariotti.cardslib.library.view.CardView
            card:card_layout_resourceID="@layout/card_thumbnail_layout" />
```


### Basic usage

Creating a base `CardThumbnail` is pretty simple.

``` java
        //Create thumbnail
        CardThumbnail thumb = new CardThumbnail(getActivity());

        //Set resource
        thumb.setDrawableResource(R.drawable.carddemo_ic_gmaps_large);

        //Add thumbnail to a card
        card.addCardThumbnail(thumb);
```

`CardThumbnail` provides a base ThumbnailLayout with an ImageView.

You can find it in `res/layout/base_thumbnail_layout.xml`.

With your `CardThumbnail` you can:

* load a image from a resource ID
* load a image from a URL (of course it requires INTERNET  permission)

### Thumbnail from Resource ID

If you want to load an image from a resource ID:

``` java
        //Create a Card
        Card card = new Card(getActivity());

        //Create thumbnail
        CardThumbnail thumb = new CardThumbnail(getActivity());

        //Set ID resource
        thumb.setDrawableResource(R.drawable.carddemo_ic_gmaps_large);

        //Add thumbnail to a card
        card.addCardThumbnail(thumb);

        //Set card in the cardView
        CardView cardView = (CardView) getActivity().findViewById(R.id.carddemo_thumb_id);
        cardView.setCard(card);
```

![Screen](/demo/images/thumb/resourceId.png)

### Thumbnail from Resource URL

If you want to load an image from an url:

``` java
         //Create a Card
         Card card = new Card(getActivity());

        //Create thumbnail
        CardThumbnail thumb = new CardThumbnail(getActivity());

        //Set URL resource
        thumb.setUrlResource("https://lh5.googleusercontent.com/-N8bz9q4Kz0I/AAAAAAAAAAI/AAAAAAAAAAs/Icl2bQMyK7c/s265-c-k-no/photo.jpg");

        //Add thumbnail to a card
        card.addCardThumbnail(thumb);

        //Set card in the cardView
        CardView cardView = (CardView) getActivity().findViewById(R.id.carddemo_thumb_url);
        cardView.setCard(card);
```

![Screen](/demo/images/thumb/resourceURL.png)


### Thumbnail from Custom Source

If you would like to add thumbnails from custom source, instead of a resource ID or URL, you can use the `thumbnail.setCustomSource()` method.

Pay attention. Using this method, the image will be loaded using the built-in `AsyncTask` and `LRUCache`.

When creating the CardThumbnail you have to implement the `CustomSource` interface.

In this example thumbnail is using the Package Manager.

``` java

        CardThumbnail thumbnail = new CardThumbnail(getContext());
        thumbnail.setCustomSource(new CardThumbnail.CustomSource() {
            @Override
            public String getTag() {
                return "com.google.android.apps.maps";
            }

            @Override
            public Bitmap getBitmap() {
                PackageManager pm = mContext.getPackageManager();
                Bitmap bitmap = null;
                try {
                    bitmap = drawableToBitmap(pm.getApplicationIcon(getTag()));
                } catch (PackageManager.NameNotFoundException e) {
                }
                return bitmap;
            }

            private Bitmap drawableToBitmap(Drawable drawable) {
                if (drawable instanceof BitmapDrawable) {
                    return ((BitmapDrawable) drawable).getBitmap();
                }

                Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(bitmap);
                drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
                drawable.draw(canvas);

                return bitmap;
            }
        });
        addCardThumbnail(thumbnail);
    }

```

### Using external library

The `CardThumbnail` loads a Bitmap with a built-in feature which uses `LRUCache` and an `AsyncTask`.

If you want to improve this feature using your favorite networking library you can do this:

First you need to set  `thumbnail.setExternalUsage(true);`

``` java
        Card mCard= new Card(getContext());

        //Add Thumbnail
        MyThumbnail thumbnail = new MyThumbnail(getContext());
        //You need to set true to use an external library
        thumbnail.setExternalUsage(true);
        addCardThumbnail(thumbnail);
```

Then you have to implement your logic in `setupInnerViewElements(ViewGroup parent, View viewImage)` method:

``` java
        public class MyThumbnail extends CardThumbnail {

        @Override
        public void setupInnerViewElements(ViewGroup parent, View viewImage) {

            //Here you have to set your image with an external library
            Picasso.with(getContext())
                   .load(myUrl)
                   .into((ImageView)viewImage);

            viewImage.getLayoutParams().width = 250;
            viewImage.getLayoutParams().height = 250;
        }
```

In Demo/Extra you can find 3 example with Picasso, Ion and Android-Universal-Image-Loader libraries.

You can see `PicassoCard` , `IonCard` , `UniversalImageLoaderCard` sources in demo-extras.

[You can read more info in this page](OTHERLIBRARIES.md).


### Using with a Bitmap

If you want to use the CardThumbnail with a Bitamp or other DrawableResource you can use a [custom source](#thumbnail-from-custom-source).

Pay attention. Using this method, the image will be loaded using the built-in `AsyncTask` and `LRUCache`.

Otherwise you can use the method above `thumbnail.setExternalUsage(true);` and use the ImageView in `setupInnerViewElements` method:

``` java
        Card mCard= new Card(getContext());

        //Add Thumbnail
        MyThumbnail thumbnail = new MyThumbnail(getContext());
        //You need to set true to use an external library
        thumbnail.setExternalUsage(true);
        addCardThumbnail(thumbnail);

        public class MyThumbnail extends CardThumbnail {

        @Override
        public void setupInnerViewElements(ViewGroup parent, View viewImage) {

            ImageView image= (ImageView)viewImage ;

            //...image.setImageBitmap();
            //...image.setImageDrawable();
        }
```


### Error Resource Id

You can set a drawable resource Id to display when the image can't be attached to ImageView (for example an error occurs while downloading).

``` java
        //Create thumbnail
        CustomThumbCard thumb = new CustomThumbCard(getActivity());

        //Set URL resource
        thumb.setUrlResource(myUrl);

        //Error Resource ID
        thumb.setErrorResource(R.drawable.ic_error_loading);

        //Add thumbnail to a card
        card.addCardThumbnail(thumb);
```

![Screen](/demo/images/thumb/errorID.png)


### Customizing the Thumbnail Layout

You can fully customize your thumbnail layout.

You can set your thumbnail layout, customizing card layout and inflating your thumbnail xml layout file.

First of all you have to provide your card layout .The quickest way to start with this would be to copy the `res/layout/card_thumbnail_layout.xml`

You can see `res/layout/carddemo_googlenownbirth_layout.xml`.

``` xml
   <LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
                 xmlns:card="http://schemas.android.com/apk/res-auto">



         <it.gmariotti.cardslib.library.view.component.CardThumbnailView
                style="@style/card_thumbnail_outer_layout"
                android:id="@+id/card_thumbnail_layout"
                android:layout_width="0dp"
                android:paddingTop="2dp"
                android:layout_gravity="right"
                card:card_thumbnail_layout_resourceID="@layout/carddemo_googlenowbirth_thumbnail_layout"
                android:layout_weight="1"
                android:layout_height="wrap_content"/>



   </LinearLayout>
```

When you use a custom card layout or compound layout is important to use the **same IDs** to preserve built-in features. When you use a custom inner layout you can change everything.

You can specify your thumbnail layout (compound layout) using this attr:`card:card_thumbnail_layout_resourceID="@layout/carddemo_googlenowbirth_thumbnail_layout"`.

Then you have to define your thumbnail layout:

You can see an exmple in `res/layout/carddemo_googlenowbirth_thumbnail_layout`

``` xml
<ImageView
    xmlns:android="http://schemas.android.com/apk/res/android"
    android:id="@+id/card_thumbnail_image"
    android:layout_width="125dp"
    android:layout_height="125dp"
    style="@style/card_thumbnail_image"/>
```



### Customize Thumbnail

If you want to customize your Thumbnail you can use your style files.
The quickest way to start with this would be to copy the specific style or resources in your project and
change them.

``` xml

    <style name="card_thumbnail_image">
        <item name="android:layout_width">@dimen/card_thumbnail_width</item>
        <item name="android:layout_height">@dimen/card_thumbnail_height</item>
        <item name="android:layout_gravity">center</item>
        <item name="android:gravity">center</item>
        <item name="android:scaleType">centerCrop</item>
    </style>

    <style name="card_thumbnail_outer_layout">
        <item name="android:layout_width">wrap_content</item>
        <item name="android:layout_height">wrap_content</item>
    </style>

```

Otherwise you can extend your `CardThumbmail` and override the `setupInnerViewElements` method.

``` java
    public class CustomThumbCard extends CardThumbnail {

        public CustomThumbCard(Context context) {
            super(context);
        }

        @Override
        public void setupInnerViewElements(ViewGroup parent, View viewImage) {
            if (viewImage!=null){
                //viewImage.getLayoutParams().width=250;
                //viewImage.getLayoutParams().height=250;

                DisplayMetrics metrics=parent.getResources().getDisplayMetrics();
                viewImage.getLayoutParams().width= (int)(250*metrics.density);
                viewImage.getLayoutParams().height = (int)(250*metrics.density);
            }
        }
    }
```

![Screen](/demo/images/thumb/thumb_style.png)


### Broadcast to know when the download is finished

The `CardThumbnail` loads a Bitmap with a resource ID or with a URL using `LRUCache` and an `AsyncTask`.
If you would like to know when the image is downloaded and attached to ImageView you can create a Broadcast Receiver that listens for
`Constants.IntentManager.INTENT_ACTION_IMAGE_DOWNLOADED` Broadcast Intents, as shown:

``` java
    activity.registerReceiver(mReceiver,new IntentFilter(Constants.IntentManager.INTENT_ACTION_IMAGE_DOWNLOADED));
```

This Intent includes extras that provide additional details:

1. `Constants.IntentManager.INTENT_ACTION_IMAGE_DOWNLOADED_EXTRA_RESULT` is a boolean. It is `true` when the image is downloaded and attached successfully, `false` otherwise.
2. `Constants.IntentManager.INTENT_ACTION_IMAGE_DOWNLOADED_EXTRA_CARD_ID` contains the card id which contains the imageView.
3. `Constants.IntentManager.INTENT_ACTION_IMAGE_DOWNLOADED_EXTRA_ERROR_LOADING` is a boolean. It is `true` when the error image is attached.

``` java
    /**
     * Broadcast for image downloaded by CardThumbnail
     */
    private class ImageBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle extras = intent.getExtras();
            if (extras!=null){
                boolean result = extras.getBoolean(Constants.IntentManager.INTENT_ACTION_IMAGE_DOWNLOADED_EXTRA_RESULT);
                String id = extras.getString(Constants.IntentManager.INTENT_ACTION_IMAGE_DOWNLOADED_EXTRA_CARD_ID);
                boolean processError = extras.getBoolean(Constants.IntentManager.INTENT_ACTION_IMAGE_DOWNLOADED_EXTRA_ERROR_LOADING);
                if (result){
                    if (id!=null && id.equalsIgnoreCase(cardGmap.getId())){
                        updateIntentToShare();
                    }
                }
            }
        }
    }
```

You can disable this default feature in your `CardThumbnail` with:

``` java
     cardthumbnail.setSendBroadcastAfterAttach(false);
```


### How to modify the bitmap and create circular or rounded images

You can modify your bitmap before it is attached to the ImageView.

You need to override the `thumbnail#applyBitmap` method.

``` java

  public class CardThumbnailCircle extends CardThumbnail{

      @Override
      public boolean applyBitmap(View imageView, Bitmap bitmap) {

         //Put your code here
         //Return true if your callback attaches the bitmap to the ImageView, false otherwise

         // Example:
         CircleDrawable circle = new CircleDrawable(bitmap,true);
         if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
             imageView.setBackground(circle);
         else
             imageView.setBackgroundDrawable(circle);
         return true;

      }

  }
```

This method is called only with a standard implementation. If you use an external library, you don't need this method to access to your bitmap.


You can see an example [here](/demo/stock/src/main/java/it/gmariotti/cardslib/demo/fragment/BirthDayCardFragment.java)

![Screen](/demo/images/thumb/circle.png)


---

Google and the Google Maps logo are registered trademarks of Google Inc.