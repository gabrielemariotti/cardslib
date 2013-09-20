# Cards Library: Thumbnail

Create a base `CardThumbnail` is pretty simple.

``` java
        //Create thumbnail
        CardThumbnail thumb = new CardThumbnail(getActivity().getApplicationContext());

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
        Card card = new Card(getActivity().getApplicationContext());

        //Create thumbnail
        CardThumbnail thumb = new CardThumbnail(getActivity().getApplicationContext());

        //Set ID resource
        thumb.setDrawableResource(R.drawable.carddemo_ic_gmaps_large);

        //Add thumbnail to a card
        card.addCardThumbnail(thumb);

        //Set card in the cardView
        CardView cardView = (CardView) getActivity().findViewById(R.id.carddemo_thumb_id);
        cardView.setCard(card);
```

![Screen](https://github.com/gabrielemariotti/cardslib/raw/master/CardsDemo/images/thumb/resourceId.png)

### Thumbnail from Resource URL

If you want to load an image from an url:

``` java
         //Create a Card
         Card card = new Card(getActivity().getApplicationContext());

        //Create thumbnail
        CardThumbnail thumb = new CardThumbnail(getActivity().getApplicationContext());

        //Set URL resource
        thumb.setUrlResource("https://lh5.googleusercontent.com/-N8bz9q4Kz0I/AAAAAAAAAAI/AAAAAAAAAAs/Icl2bQMyK7c/s265-c-k-no/photo.jpg");

        //Add thumbnail to a card
        card.addCardThumbnail(thumb);

        //Set card in the cardView
        CardView cardView = (CardView) getActivity().findViewById(R.id.carddemo_thumb_url);
        cardView.setCard(card);
```

![Screen](https://github.com/gabrielemariotti/cardslib/raw/master/CardsDemo/images/thumb/resourceURL.png)

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
                viewImage.getLayoutParams().width=250;
                viewImage.getLayoutParams().height=250;
            }
        }
    }
```

![Screen](https://github.com/gabrielemariotti/cardslib/raw/master/CardsDemo/images/thumb/thumb_style.png)
