# Card Library
Travis master: [![Build Status](https://travis-ci.org/gabrielemariotti/cardslib.svg?branch=master)](https://travis-ci.org/gabrielemariotti/cardslib)
Travis dev: [![Build Status](https://travis-ci.org/gabrielemariotti/cardslib.svg?branch=dev)](https://travis-ci.org/gabrielemariotti/cardslib)


**Card Library** provides an easy way to display a UI Card using the **Official Google CardView** in your Android app.

Before using this library I recommend that you check out the new Google Material Guidelines.Don't over cardify your UI.

![Screen](/demo/images/cardsv2_small.png)


## Examples


![Screen](/demo/images/demo_gplay.png)

* **Sample** application: The demo is a showcase of the functionality of the library.

	 [![Get it on Google Play](http://www.android.com/images/brand/get_it_on_play_logo_small.png)](https://play.google.com/store/apps/details?id=it.gmariotti.cardslib.demo)
	 
* **Extras** application: The demo-extras contains some examples of integration with other libraries
	
	[![Get it on Google Play](http://www.android.com/images/brand/get_it_on_play_logo_small.png)](https://play.google.com/store/apps/details?id=it.gmariotti.cardslib.demo.extras)

  
## Support
Join the [**Google+ Community**](https://plus.google.com/u/0/communities/111800040690738372803): 

[![Join the Google+ Community](/demo/images/g+64.png)](https://plus.google.com/u/0/communities/111800040690738372803)
	
a place to discuss the library, share screenshots, ask for tips, talk with the author....﻿

**If you would like, you can support my work, donating through the demo app.**


## Doc

See the **[Card Library Guide](/doc/GUIDE.md)** to know all card library features and all customizations.
The Guide provides an extensive doc, with all tips and full examples. **Don't miss it**.


## Setup

Card Library is pushed to Maven Central as an AAR, so you just need to add the following dependency to your `build.gradle`.

    dependencies {
        //Core
        compile 'com.github.gabrielemariotti.cards:cardslib-core:2.1.0'
        
        //Optional for built-in cards
        compile 'com.github.gabrielemariotti.cards:cardslib-cards:2.1.0'
                
        //Optional for RecyclerView
        compile 'com.github.gabrielemariotti.cards:cardslib-recyclerview:2.1.0'
          
        //Optional for staggered grid view
        compile 'com.github.gabrielemariotti.cards:cardslib-extra-staggeredgrid:2.1.0'
         
        //Optional for drag and drop
        compile 'com.github.gabrielemariotti.cards:cardslib-extra-dragdrop:2.1.0'
        
        //Optional for twoway  (coming soon)
        //compile 'com.github.gabrielemariotti.cards:cardslib-extra-twoway:2.1.0'
        
    }

If you would like to use the last **v1 stable version** you can use:
    
    dependencies {
        //Core card library
        compile 'com.github.gabrielemariotti.cards:library:1.9.1'

        //Extra card library, it is required only if you want to use integrations with other libraries
        compile 'com.github.gabrielemariotti.cards:library-extra:1.9.1'
    }


## ChangeLog

* [Changelog:](CHANGELOG.md) A complete changelog


Acknowledgements
--------------------

* Thanks to [Roman Nurik][1] for [Android-SwipeToDismiss][2] classes and [UndoBarController][3] classes.
* Thanks to [Niek Haarman][4] for some ideas and code taken from his [ListViewAnimations][5].
* Thanks to [Chris Banes][6] for [ForegroundLinearLayout][7] class (See this [post][8] for more info).
* Thanks to [Taylor Ling][9] for drag and drop icon.
* Thanks to [Frankie Sardo][10] for some ideas and code taken from his [LinearListView][11]
* Thanks to Google for code and idea from [Google IO 14][12]

Credits
-------

Author: Gabriele Mariotti (gabri.mariotti@gmail.com)

<a href="https://plus.google.com/u/0/114432517923423045208">
  <img alt="Follow me on Google+"
       src="https://github.com/gabrielemariotti/cardslib/raw/master/demo/images/g+64.png" />
</a>
<a href="https://twitter.com/GabMarioPower">
  <img alt="Follow me on Twitter"
       src="https://github.com/gabrielemariotti/cardslib/raw/master/demo/images/twitter64.png" />
</a>
<a href="http://it.linkedin.com/in/gabrielemariotti">
  <img alt="Follow me on LinkedIn"
       src="https://github.com/gabrielemariotti/cardslib/raw/master/demo/images/linkedin.png" />
</a>

License
-------

    Copyright 2013-2014 Gabriele Mariotti

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.


---


Google and the Google Maps logo are registered trademarks of Google Inc., used with permission.

 [1]: https://plus.google.com/u/0/+RomanNurik/about
 [2]: https://github.com/romannurik/Android-SwipeToDismiss
 [3]: https://code.google.com/p/romannurik-code/source/browse/#git%2Fmisc%2Fundobar
 [4]: https://plus.google.com/+NiekHaarman
 [5]: https://github.com/nhaarman/ListViewAnimations
 [6]: https://plus.google.com/+ChrisBanes
 [7]: https://gist.github.com/chrisbanes/9091754
 [8]: https://plus.google.com/+AndroidDevelopers/posts/aHPVDtr6mcp
 [9]: https://plus.google.com/+TaylorLing
 [10]: https://plus.google.com/+FrankieSardo
 [11]: https://github.com/frankiesardo/LinearListView
 [12]: https://github.com/google/iosched