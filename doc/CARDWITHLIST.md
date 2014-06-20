# Cards Library: Card With a List inside

In this page you can find info about:

* [How to build a card with a LinearList](#how-to-build-a-card-with-a-linearlist)
* [Init the CardHeader](#init-the-cardheader)
* [Init the Card](#init-the-card)
* [Init the Children](#init-the-children)
* [Define the layout used by items](#define-the-layout-used-by-items)
* [Setup child view](#setup-child-view)
* [Extending the init method](#extending-the-init-method)
* [How to add and remove items dynamically](#how-to-add-and-remove-items-dynamically)
* [How to customize the divider](#how-to-customize-the-divider)
* [Empty View](#empty-view)
* [How to disable the empty view](#how-to-disable-the-empty-view)
* [How to customize the Empty View](#how-to-customize-the-empty-view)
* [Using a custom inner layout](#using-a-custom-inner-layout)
* [ProgressBar](#progressbar)
* [How to enable the progressbar](#how-to-enable-the-progressbar)
* [How to customize the ProgressBar](#how-to-customize-the-progressbar)
* [Using a custom inner layout](#using-a-custom-inner-layout)
* [Note about CardListView](#note-about-cardlistview)


## How to build a card with a LinearList

The card with a LinearList inside is a particular `Card` that can display items inside the `Card`.
Technically it is a special `LinearLayout` that works with an ArrayAdapter, so you can use your `Card` inside a `ScrollView` for example.

![Screen](/demo/images/card/cwl_1.png)

First, you need an XML layout that will display the `Card`.

``` xml
        <it.gmariotti.cardslib.library.view.CardView
            android:id="@+id/carddemo"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:layout_marginLeft="12dp"
            android:layout_marginRight="12dp"
            android:layout_marginTop="12dp"/>
```

Then create a model. 
In this case you have to extend the `CardWithList` (which extends `Card`) and implement the required methods.

``` java
    public class GoogleNowWeatherCard extends CardWithList {

        public GoogleNowWeatherCard(Context context) {
            super(context);
        }
    }
```

Finally you have to call the `init()` method.

``` java
   GoogleNowWeatherCard card = new GoogleNowWeatherCard(context);
   card.init();
```

**Migration from 1.7.0 - to 1.7.2+**

 The 1.7.2 introduces a breaking change with CardWithList.
 To migrate your code you have to:

 * call `card.init()";`


## Init the CardHeader

You have to specify your `CardHeader` using the method `initCardHeader()`.
It is a normal `CardHeader`, with all its features.

``` java
    @Override
    protected CardHeader initCardHeader() {

        //Add Header
        CardHeader header = new CardHeader(getContext());

        //Add a popup menu. This method set OverFlow button to visible
        header.setPopupMenu(R.menu.popup_item, new CardHeader.OnClickCardHeaderPopupMenuListener() {
            @Override
            public void onMenuItemClick(BaseCard card, MenuItem item) {
                
                switch (item.getItemId()){
                    case R.id.action_add:
                        WeatherObject w1= new WeatherObject(MyCard.this);
                        w1.city ="Madrid";
                        w1.temperature = 24;
                        w1.weatherIcon = R.drawable.ic_action_sun;
                        w1.setObjectId(w1.city);
                        mLinearListAdapter.add(w1);
                        break;
                    case R.id.action_remove:
                        mLinearListAdapter.remove(mLinearListAdapter.getItem(0));
                        break;
                }
            }
        });
        header.setTitle("Weather"); //should use R.string.
        return header;
    }
```

## Init the Card

In this method you can customize your `Card` with its features.
For example:

``` java
    @Override
    protected void initCard() {

        setSwipeable(true);
        setOnSwipeListener(new OnSwipeListener() {
            @Override
            public void onSwipe(Card card) {
                Toast.makeText(getContext(), "Swipe on " + card.getCardHeader().getTitle(), Toast.LENGTH_SHORT).show();
            }
        });
    }
```

## Init the children

You have to initialize your items inside the `Card` using the method `initChildren()`.

``` java
    @Override
    protected List<ListObject> initChildren() {

        List<ListObject> mObjects = new ArrayList<ListObject>();

        WeatherObject w1= new WeatherObject(this);
        w1.city ="London";
        w1.temperature = 16;
        w1.weatherIcon = R.drawable.ic_action_cloud;
        w1.setObjectId(w1.city);
        mObjects.add(w1);
        
        return mObjects;
    }
```

If the list is empty you can return `null`.

Each element in the list has to be a `ListObject`.
To achieve it you can use two ways:

- extending the `DefaultListObject` class 
- implementing the `ListObject` interface.

In particular you can :

- set the item id (it can be very useful to identify the item in the list) with `setObjectId()` method.
- set an `onItemClickListener` to register a callback for click on the single item.

```java
    setOnItemClickListener(new OnItemClickListener() {
        @Override
        public void onItemClick(LinearListView parent, View view, int position, ListObject object) {
            Toast.makeText(getContext(), "Click on " + getObjectId(), Toast.LENGTH_SHORT).show();
        }
    });
```

- set the single item as swipeable using `setSwipeable`

```java
    item.setSwipeable(true);
```

- set an `OnItemSwipeListener` to register a callback for swipe on the single item.

```java
        item.setOnItemSwipeListener(new OnItemSwipeListener() {
            @Override
            public void onItemSwipe(ListObject object,boolean dismissRight) {
                Toast.makeText(getContext(), "Swipe on " + object.getObjectId(), Toast.LENGTH_SHORT).show();
            }
        });
```

![Screen](/demo/images/card/cwl_swipeitem.png)

You can customize the background behind the items cloning this color in your project:

``` xml
    <!-- Used by card with list -->
    <color name="card_base_cardwithlist_background_list_color">#10000000</color>
``` 

Otherwise you can customize this style in your project:
                  
``` xml
      <!-- Style for card with list -->
      <style name="cardwithlist">
            <item name="android:background">@color/card_base_cardwithlist_background_list_color</item>
      </style>                
```

and finally you can provide your [custom inner layout](#using-a-custom-inner-layout)


## Define the layout used by items

You have to specify the layout used by each item.
You have to implement this method:

``` java
    @Override
    public int getChildLayoutId() {
        return R.layout.carddemo_googlenowweather_inner_main;
    }
```

## Setup child view

After declaring the layout used by items, you have to setup the elements inside the item binding it with the `ListObject`.

``` java
    @Override
    public View setupChildView(int childPosition, ListObject object, View convertView, ViewGroup parent) {

        TextView city = (TextView) convertView.findViewById(R.id.carddemo_weather_city);
        ImageView icon = (ImageView) convertView.findViewById(R.id.carddemo_weather_icon);
        TextView temperature = (TextView) convertView.findViewById(R.id.carddemo_weather_temperature);

        WeatherObject weatherObject= (WeatherObject)object;
        icon.setImageResource(weatherObject.weatherIcon);
        city.setText(weatherObject.city);
        temperature.setText(weatherObject.temperature + weatherObject.temperatureUnit);

        return  convertView;
    }
```

## Extending the init method

You can extend your `init` method to set your custom variables.

``` java
    @Override
    public void init() {
        //Insert your code here...
        mField = xxx;

        //Finally call the super.init();
        super.init();
    }
```

## How to add and remove items dynamically

You can add and remove items dynamically, using a method inside your `Card` through the `getLinearListAdapter()` method.

``` java
    public void updateItems(ArrayList myList) {

        //Update the array inside the card
        ArrayList<MyObject> objs = new ArrayList<WeatherObject>();
        //.....
        getLinearListAdapter().addAll(objs);

        //use this line if your are using the progress bar
        //updateProgressBar(true,true);
    }
```

``` java
    public void updateItems(String city,int temp) {
            WeatherObject w1= new WeatherObject(GoogleNowWeatherCard.this);
            w1.city =city;
            w1.temperature = temp;
            w1.weatherIcon = R.drawable.ic_action_sun;
            w1.setObjectId(w1.city);
            mLinearListAdapter.add(w1);
    }
```


## How to customize the divider

You can customize the divider used by the List.

You can override this dimension in your project to change the height of divider. 

``` xml
    <dimen name="card_base_cardwithlist_dividerHeight">1dp</dimen>
``` 

You can change the color of the divider cloning this color in your project:

``` xml
    <!-- Used by card with list -->
    <color name="card_base_cardwithlist_divider_color">#E5E5E5</color>
```

Finally you can clone this style in your project:

``` xml
    <!-- Style for card with list -->
    <style name="cardwithlist">
        <item name="android:layout_marginTop">@dimen/card_base_cardwithlist_list_margin_top</item>
        <item name="android:layout_marginLeft">@dimen/card_base_cardwithlist_list_margin_left</item>
        <item name="android:divider">@color/card_base_cardwithlist_divider_color</item>
        <item name="android:showDividers">middle</item>
        <item name="android:layout_width">match_parent</item>
        <item name="android:layout_height">match_parent</item>
        <item name="android:background">@color/card_base_cardwithlist_background_list_color</item>
    </style>
``` 



## Empty View

The default card provides a built-in feature with an EmptyView as you can find in the standard `ListView`. 
This feature is enabled by default but you can customize it.
The default view used is a `TextView`.


### How to disable the empty view.
To disable the EmptyView you can use `setUseEmptyView(false)`

Example:
``` java
    @Override
    protected void initCard() {
        setUseEmptyView(false);
    }
```

### How to customize the Empty View

You can customize the emptyView in different ways:

You can change the string simply overriding this string in your project:
``` xml
    <string name="card_empty_cardwithlist_text">No data....</string>
```

The default layout [inner_base_main_cardwithlist](/library/library/src/main/res/layout/inner_base_main_cardwithlist) uses a `ViewStub`.
You can dynamically change the the layout resource to inflate when the ViewStub becomes visible, using the method `setEmptyViewViewStubLayoutId`.

Example:
``` java
    @Override
    protected void initCard() {
         setEmptyViewViewStubLayoutId(R.layout.carddemo_extras_base_withlist_empty);
    }
```
![Screen](/demo/images/card/cwl_customnodata.png)


Of course you can provide a complete different view using a custom [innerlayout](#using-a-custom-inner-layout) for your card.
In this you can use your favorite View.


## ProgressBar

The default card provides a built-in feature with a ProgressBar that can be displayed inside the `CardView`. 
This feature is disabled by default but you can customize it.
The default view used is a `ProgressBar` and a TextView.

![Screen](/demo/images/card/cwl_progressbar.png)


### How to enable the ProgressBar.
To enable the ProgressBar you can use `setUseProgressBar(true)`

Example:
``` java
    @Override
    protected void initCard() {
        setUseProgressBar(true);
    }
```

You can display the ProgressBar using this code:
``` java
    card.updateProgressBar(false,false);
``` 

The first parameter indicates to hide the list and display the progressbar, while the second parameter indicates if you want to animate the transition between the views.
When the process is ended you can hide the ProgressBar and display the list using:

``` java
    card.updateProgressBar(true,true);
``` 


### How to customize the ProgressBar

You can customize the ProgressBar in different ways:

You can change the string used simply overriding this string in your project:
``` xml
    <string name="card_progressbar_cardwithlist_text"></string>
```

The default layout [inner_base_main_cardwithlist](/library/library/src/main/res/layout/inner_base_main_cardwithlist) uses a `ViewStub`.
You can dynamically change the the layout resource to inflate when the ViewStub becomes visible, using the method `setProgressBarViewStubLayoutId`.

Example:
``` java
    @Override
    protected void initCard() {
         setProgressBarViewStubLayoutId(R.layout.carddemo_extras_base_withlist_progressbar);
    }
```

Of course you can provide a complete different view using a custom [innerlayout](#using-a-custom-inner-layout) for your card.
In this you can use your favorite View.


## Using a custom inner layout

As other cards you can use a custom inner layout instead of the [default layout](/library/library/src/main/res/layout/inner_base_main_cardwithlist).
You can set it in your constructor for example:
``` java
    public WeatherCard(Context context) {
        super(context,R.layout.carddemo_extras_inner_base_main_cardwithlist);
    }
```

Your layout **have to provide** an element with `
```xml
<it.gmariotti.cardslib.library.prototypes.LinearListView
        android:id="@+id/card_inner_base_main_cardwithlist"
    />
```

Also your layout **can** provide a [EmptyView element](#how-to-customize-the-empty-view).
It is not mandatory.

Pay attention to ids. If you use the same original ids, all built-in features are preserved.
You can use your custom ids. In this case you have to set them in this way:
``` java
    @Override
    protected void initCard() {
        setListViewId(R.id.yourListId);
        setEmptyViewId(R.id.yourEmptyViewId);
    }
```

Also you can add other elements inside you layout. In this case you have to set them with the method
`setupInnerViewElements`. In this case it is very important to call the super method.

``` java
    @Override
    public void setupInnerViewElements(ViewGroup parent, View view) {
        //It is very important call the super method!!
        super.setupInnerViewElements(parent, view);
        
        //Your elements
    }
```


## Note about CardListView

Currently this kind of `Card` is not supported to be used inside a `CardListView`.
