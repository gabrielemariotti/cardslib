# Cards Library: Integration with other libraries

In this page you can find info about:

* [Using card with Picasso](#using-card-with-picasso)
* [Using card with Ion](#using-card-with-ion)
* [Using card with Android-Universal-Image-Loader](#using-card-with-android-universal-image-loader)
* [Using card with ActionBar-PullToRefresh](#using-card-with-actionbar-pulltorefresh)

## Using card with Picasso

[Picasso][1] is provided by [Square, inc][2].

You can find an example in demo-extras in `PicassoCard` [(source)](https://github.com/gabrielemariotti/cardslib/tree/master/demo/extras/src/main/java/it/gmariotti/cardslib/demo/extras/fragment/PicassoFragment.java).

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

## Using card with Ion

[Ion][3] is provided by [Koushik Dutta][4].

You can find an example in demo-extras in `IonCard`  [(source)](https://github.com/gabrielemariotti/cardslib/tree/master/demo/extras/src/main/java/it/gmariotti/cardslib/demo/extras/fragment/IonFragment.java).

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

             Ion.with((ImageView) viewImage)
                     .resize(96, 96)
                     .centerInside()
                     .load("https://plus.google.com/s2/photos/profile/114432517923423045208?sz=96");

             viewImage.getLayoutParams().width = 250;
             viewImage.getLayoutParams().height = 250;
        }
```


## Using card with Android-Universal-Image-Loader

[Android-Universal-Image-Loader][5] is provided by [Sergey Tarasevich][6].

You can find an example in demo-extras in `UniversalImageLoaderCard`  [(source)](https://github.com/gabrielemariotti/cardslib/tree/master/demo/extras/src/main/java/it/gmariotti/cardslib/demo/extras/fragment/UniversalImageLoaderFragment.java) .

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

            //In real case you should config better the imageLoader
            ImageLoader imageLoader = ImageLoader.getInstance();
            imageLoader.init(ImageLoaderConfiguration.createDefault(mContext));


            //Here you have to set your image with an external library
            //Only for test, use a Resource Id and a Url
            if (((UniversalImageLoaderCard) getParentCard()).getCount() % 2 == 0) {
                imageLoader.displayImage("http://plus.google.com/s2/photos/profile/114432517923423045208?sz=96",
                               (ImageView) viewImage,options);
            } else {
                imageLoader.displayImage("drawable://" + R.drawable.ic_tris, (ImageView) viewImage,options);
            }

            viewImage.getLayoutParams().width = 96;
            viewImage.getLayoutParams().height = 96;
        }
```


## Using card with ActionBar-PullToRefresh

[ActionBar PullToRefresh][7] is provided by [Chris Banes][8].

You can find an example in demo-extras in `ActionbarpullFragment`. [(source)](https://github.com/gabrielemariotti/cardslib/tree/master/demo/extras/src/main/java/it/gmariotti/cardslib/demo/extras/fragment/ActionbarpullFragment.java)

The proper way to integrate this library is to use `PullToRefreshLayout` in your layout:

[You can see more info at:](https://github.com/chrisbanes/ActionBar-PullToRefresh#pulltorefreshlayout)

``` xml
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
              xmlns:card="http://schemas.android.com/apk/res-auto"
              android:orientation="vertical"
              android:layout_width="match_parent"
              android:layout_height="match_parent">

    <uk.co.senab.actionbarpulltorefresh.library.PullToRefreshLayout
        android:id="@+id/carddemo_extra_ptr_layout"
        android:layout_width="fill_parent"
        android:layout_height="fill_parent">

        <!-- You can customize this layout.
         You need to have in your layout a `CardView` with the ID `list_cardId` -->
        <it.gmariotti.cardslib.library.view.CardListView
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            android:id="@+id/carddemo_extra_list_actionbarpulltorefresh"
            card:list_card_layout_resourceID="@layout/list_card_thumbnail_layout"/>

    </uk.co.senab.actionbarpulltorefresh.library.PullToRefreshLayout>

</LinearLayout>
```

``` java
        // Now get the PullToRefresh attacher from the Activity. An exercise to the reader
        // is to create an implicit interface instead of casting to the concrete Activity
        mPullToRefreshAttacher = ((MainActivity) getActivity()).getPullToRefreshAttacher();

        // Retrieve the PullToRefreshLayout from the content view
        PullToRefreshLayout ptrLayout = (PullToRefreshLayout) getActivity().findViewById(R.id.carddemo_extra_ptr_layout);

        // Give the PullToRefreshAttacher to the PullToRefreshLayout, along with a refresh listener.
        ptrLayout.setPullToRefreshAttacher(mPullToRefreshAttacher, this);
```



 [1]: https://github.com/square/picasso
 [2]: http://square.github.io/
 [3]: https://github.com/koush/ion
 [4]: http://koush.com/
 [5]: https://github.com/nostra13/Android-Universal-Image-Loader
 [6]: http://nostra13android.blogspot.it/
 [7]: https://github.com/chrisbanes/ActionBar-PullToRefresh
 [8]: http://chris.banes.me/