# Cards Library: Integration with other libraries

In this page you can find info about:

* [Using card with Picasso](#using-card-with-picasso)
* [Using card with Ion](#using-card-with-ion)
* [Using card with Android-Universal-Image-Loader](#using-card-with-android-universal-image-loader)
* [Using card with ActionBar-PullToRefresh](#using-card-with-actionbar-pulltorefresh)
* [Using card with ListViewAnimations](#using-card-with-listviewanimations)
* [Using card as a Crouton](#using-card-as-a-crouton)
* [Using card with StickyListHeaders](#using-card-with-stickylistheaders)
* [StaggeredGrid](STAGGEREDGRID.md)
* [Using a Card List with Drag and Drop support](DRAGDROPLIST.md)

## Using card with Picasso

[Picasso][1] is provided by [Square, inc][2].

You can find an example in demo-extras in `PicassoCard` [(source)](/demo/extras/src/main/java/it/gmariotti/cardslib/demo/extras/fragment/PicassoFragment.java).

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

You can find an example in demo-extras in `IonCard`  [(source)](/demo/extras/src/main/java/it/gmariotti/cardslib/demo/extras/fragment/IonFragment.java).

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

You can find an example in demo-extras in `UniversalImageLoaderCard`  [(source)](/demo/extras/src/main/java/it/gmariotti/cardslib/demo/extras/fragment/UniversalImageLoaderFragment.java) .

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

You can find an example in demo-extras in `ActionbarpullFragment`. [(source)](/demo/extras/src/main/java/it/gmariotti/cardslib/demo/extras/fragment/ActionbarpullFragment.java)

The proper way to integrate this library is to use `PullToRefreshLayout` in your layout:

[You can see more info about `PullToRefreshLayout` at:](https://github.com/chrisbanes/ActionBar-PullToRefresh#pulltorefreshlayout)

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

        // Retrieve the PullToRefreshLayout from the content view
        mPullToRefreshLayout = (PullToRefreshLayout) getActivity().findViewById(R.id.carddemo_extra_ptr_layout);

       // Now setup the PullToRefreshLayout
       ActionBarPullToRefresh.from(this.getActivity())
               // Mark All Children as pullable
               .allChildrenArePullable()
               // Set the OnRefreshListener
               .listener(this)
               // Finally commit the setup to our PullToRefreshLayout
               .setup(mPullToRefreshLayout);

```

### Using card with ListViewAnimations

[ListViewAnimations][9] is provided by [Niek Haarman][10].

You can find an example in demo-extras in `ListViewAnimationsFragment`. [(source)](/demo/extras/src/main/java/it/gmariotti/cardslib/demo/extras/fragment/ListViewAnimationsFragment.java)

``` java
        CardArrayAdapter mCardArrayAdapter = new CardArrayAdapter(getActivity(), cards);
        CardListView mListView = (CardListView) getActivity().findViewById(R.id.carddemo_extra_list_viewanimations);

        AnimationAdapter animCardArrayAdapter = new AlphaInAnimationAdapter(mCardArrayAdapter);
        animCardArrayAdapter.setAbsListView(mListView);
        mListView.setExternalAdapter(animCardArrayAdapter,mCardArrayAdapter);
```

You can find another example in demo-extras in `ListViewGridAnimationsFragment`. [(source)](/demo/extras/src/main/java/it/gmariotti/cardslib/demo/extras/fragment/ListViewGridAnimationsFragment.java)

``` java
        CardGridArrayAdapter mCardArrayAdapter = new CardGridArrayAdapter(getActivity(), cards);
        CardGridView mListView = (CardGridView) getActivity().findViewById(R.id.carddemo_extra_grid_viewanimations);

        AnimationAdapter animCardArrayAdapter = new AlphaInAnimationAdapter(mCardArrayAdapter);
        animCardArrayAdapter.setAbsListView(mListView);
        mListView.setExternalAdapter(animCardArrayAdapter,mCardArrayAdapter);
```


### Using card as a Crouton

[Crouton][11] is provided by [Benjamin Weiss][12].

You can find an example in demo-extras in `CroutonFragment`. [(source)](/demo/extras/src/main/java/it/gmariotti/cardslib/demo/extras/fragment/CroutonFragment.java)

``` java

    CardView cardView= (CardView)view.findViewById(R.id.carddemo_card_crouton_id);

    Card card = new Card(getActivity());
    card.setTitle("Crouton Card");
    card.setBackgroundResourceId(R.color.demoextra_card_background_color2);

    CardThumbnail thumb = new CardThumbnail(getActivity());
    thumb.setDrawableResource(R.drawable.ic_action_bulb);
    card.addCardThumbnail(thumb);

    cardView.setCard(card);

    Crouton.make(getActivity(), cardView).show();

```

### Using card with StickyListHeaders

[StickyListHeaders][13] is provided by [Emil Sjölander][14].

You can find an example in demo-extras in `StickyListHeadersFragment`. [(source)](/demo/extras/src/main/java/it/gmariotti/cardslib/demo/extras/fragment/StickyListHeadersFragment.java)

In order to use this library you have to:

  1. Extend your `CardArrayAdapter` and implement `StickyListHeadersAdapter`.
  2. Use or extend the `StickyCardListView` provided in this demo [(source)](/demo/extras/src/main/java/it/gmariotti/cardslib/demo/extras/stickylist/StickyCardListView.java)
  3. Provide a layout with a `StickyCardListView`.

  The `StickyCardListView` extends the standard `StickyListHeadersListView` merged with the `CardListView` methods.


In this example:

``` java
     public class StickyCardArrayAdapter extends CardArrayAdapter implements StickyListHeadersAdapter {

        @Override
        public View getHeaderView(int position, View convertView, ViewGroup viewGroup) {
           // return your header view.
        }

        @Override
        public long getHeaderId(int position) {
           // use your logic
        }

    }


    StickyCardArrayAdapter adapter = new StickyCardArrayAdapter(getActivity(), cards);

    StickyCardListView stickyList = (StickyCardListView) getActivity().findViewById(R.id.carddemo_extra_sticky_list);
    //stickyList.setAreHeadersSticky(false);
    if (stickyList != null) {
        stickyList.setAdapter(adapter);
    }
```

``` xml
    <it.gmariotti.cardslib.demo.extras.stickylist.StickyCardListView
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:id="@+id/carddemo_extra_sticky_list"
        card:list_card_layout_resourceID="@layout/carddemo_extras_list_card_thumbnail_layout">
    </it.gmariotti.cardslib.demo.extras.stickylist.StickyCardListView>

```

You can see a [video](http://youtu.be/3d4Tvb9FPuM)




 [1]: https://github.com/square/picasso
 [2]: http://square.github.io/
 [3]: https://github.com/koush/ion
 [4]: http://koush.com/
 [5]: https://github.com/nostra13/Android-Universal-Image-Loader
 [6]: http://nostra13android.blogspot.it/
 [7]: https://github.com/chrisbanes/ActionBar-PullToRefresh
 [8]: http://chris.banes.me/
 [9]: https://github.com/nhaarman/ListViewAnimations
 [10]: https://plus.google.com/+NiekHaarman
 [11]: https://github.com/keyboardsurfer/Crouton
 [12]: https://plus.google.com/u/0/117509657298845443204
 [13]: https://github.com/emilsjolander/StickyListHeaders
 [14]: http://emilsjolander.se/

