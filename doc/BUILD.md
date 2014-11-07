# Cards Library: How to build/use library

The library is written with Android Studio and has the Gradle support.

You can add this library to your project adding a dependency to your `build.gradle`, or your can reference this project as a library (from Eclipse) or add it as a module (from Android Studio/IntelliJ).

In this page you can find info about:

* [Including in your project with Gradle](#including-in-your-project-with-gradle)
* [Reference this project as a library in Eclipse](#reference-this-project-as-a-library-in-eclipse)


## Including in your project with Gradle

Card Library is pushed to Maven Central as a AAR, so you just need to add the following dependency to your `build.gradle`.

     dependencies {
        //Core card library
        compile 'com.github.gabrielemariotti.cards:cardslib-core:2.0.0'
        
        //Optional for built-in cards
        compile 'com.github.gabrielemariotti.cards:cardslib-cards:2.0.0'
                
        //Optional for RecyclerView
        compile 'com.github.gabrielemariotti.cards:cardslib-recyclerview:2.0.0'  
          
        //Optional for staggered grid view support
        compile 'com.github.gabrielemariotti.cards:cardslib-extra-staggeredgrid:2.0.0'       
         
        //Optional for drag and drop support
        compile 'com.github.gabrielemariotti.cards:cardslib-extra-dragdrop:2.0.0'  
        
        //Optional for twowayview support (coming soon)
        //compile 'com.github.gabrielemariotti.cards:cardslib-extra-twoway:2.0.0'
          
    }

If you would like to use the last **v1 stable version** you can use:
    
    dependencies {
        //Core card library
        compile 'com.github.gabrielemariotti.cards:library:1.9.1'

        //Extra card library, it is required only if you want to use integrations with other libraries
        compile 'com.github.gabrielemariotti.cards:library-extra:1.9.1'
    }



## Reference this project as a library in Eclipse

If you would like to use this **library-core** in Eclipse you have to do these steps:

- clone a copy of this repository, or download it (outside eclipse workspace)
- import the code in your workspace starting from library folder. The Wizard will import the code in library-core/src/main. I suggest you naming it "cardscore" (or another name) instead of "main".
- mark java(*) folder as source (click on folder -> Build-Path -> use as source folder). You can also remove the src folder, from the project.
- mark cardscore as Android Library (Properties -> Android -> Is library)
- add support library v4 rel.21
- add support cardview library v7 rel.21
- add support annotation library rel.21
- The library targets SDK 21 and works with minSdk=14. In any cases you need to use API>=21 to compile library (Properties -> Android)
- Clean and build

If you would like to use this **library-cards** in Eclipse you have to do these steps:

- clone a copy of this repository, or download it (outside eclipse workspace)
- import the code in your workspace starting from library folder. The Wizard will import the code in library-cards/src/main. I suggest you naming it "cardscore" (or another name) instead of "main".
- mark java(*) folder as source (click on folder -> Build-Path -> use as source folder). You can also remove the src folder, from the project.
- mark cardscore as Android Library (Properties -> Android -> Is library)
- add the cardscore as library
- The library targets SDK 21 and works with minSdk=14. In any cases you need to use API>=21 to compile library (Properties -> Android)
- Clean and build


(*) Eclipse uses src and res as source folders.
Android Studio instead uses src/main/java , src/main/res and src/main/aidl as source folders.

