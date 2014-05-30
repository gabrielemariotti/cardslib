# Cards Library: Card With a List inside

In this page you can find info about:

* [How to build a card with a LinearList](#how-to-build-a-card-with-a-linearlist)
* [Init the CardHeader](#init-the-cardheader)
* [Init the Card](#init-the-card)
* [Init the Children](#init-the-children)
* [Define the layout used by items](#define-the-layout-used-by-items)
* [Setup child view](#setup-child-view)
* [Empty View](#empty-view)
* [How to disable the empty view](#how-to-disable-the-empty-view)
* [How to customize the Empty View](#how-to-customize-the-empty-view)
* [Using a custom inner layout](#using-a-custom-inner-layout)

## How to build a card with a LinearList

The card with a LinearList inside is a particular `Card` that can display items inside the `Card`.
Technically it is a special `LinearLayout` that works with an ArrayAdapter, so you can use your `Card` inside a `ScrollView` for example.

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
                        WeatherObject w1= new WeatherObject();
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

        WeatherObject w1= new WeatherObject();
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

You can provide a different view using a custom [innerlayout](#using-a-custom-inner-layout).
In this you can use your favorite View.
``` xml
    <TextView
        android:text="@string/new_empty_view"
        android:id="@+id/card_inner_base_empty_cardwithlist"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_gravity="center"
       />
```


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

