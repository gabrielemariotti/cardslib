# Cards Library: How to build/use library

The library is written with Android Studio and has Gradle support.

You can add this library to your project adding a dependency to your `build.gradle`, or your can reference this project as a library (from Eclipse) or add it as a module (from Android Studio/IntelliJ).

In this page you can find info about:

* [Including in your project with Gradle](#including-in-your-project-with-gradle)
* [Building locally in Android Studio with Gradle](#building-locally-in-android-studio-with-gradle)
* [Reference this project as a library in Eclipse](#reference-this-project-as-a-library-in-eclipse)


## Including in your project with Gradle

Card Library is pushed to Maven Central as a AAR, so you just need to add the following dependency to your `build.gradle`.

    dependencies {
        //Core card library
        compile 'com.github.gabrielemariotti.cards:library:1.7.3'

        //Extra card library, it is required only if you want to use integrations with other libraries
        compile 'com.github.gabrielemariotti.cards:library-extra:1.7.3'
    }

The library-extra is optional. It contains code to use integrations with other libraries, as StaggeredGridView and CardListDragDropView.


## Building locally in Android Studio with Gradle

 If you would like to use a local copy of this **library** in Android Studio you have to do these steps:

 You should have a structure like this:

 ```
   root
      MyModule
          build.gradle
      libraries
          cardslib
              library
                  build.gradle
      build.gradle
      settings.gradle
 ```

 - clone a copy of this repository inside your project in a libraries folder.
 - modify your `settings.gradle`
 ```
 include ':MyModule', ':libraries:cardslib:library'
 ```

 - modify `MyModule/build.gradle`
 ```
  dependencies {
      // Cards Library
      compile project(':libraries:cardslib:library')
  }
 ```

 - remove `libraries/cardslib/build.gradle` (pay attention, I am not referring to `libraries/cardslib/library/build.gradle`).
 This is my main gradle file, but you don't use it.
 With this file you can have an error while building the project (compileReleaseAidl FAILED).


If you would like to build also the **library-extra** module (it is optional,it contains code to use integrations with other libraries, as StaggeredGridView and CardListDragDropView):

- add the extra-library folder
 ```
      libraries
          cardslib
              library
                  build.gradle
              library-extra
                  build.gradle
 ```

 - modify your `settings.gradle`
 ```
 include ':MyModule', ':libraries:cardslib:library' , ':libraries:cardslib:library-extra'
 ```

 - check your `library-extra/build.gradle`. It requires external dependencies.
 ```
  dependencies {
      // Cards Library
      compile project(':libraries:cardslib:library')

     //StaggeredGrid
     compile 'com.etsy.android.grid:library:1.0.5'

     //Drag and drop list base on DynamicList
     compile 'com.nhaarman.listviewanimations:library:2.6.0'
  }
 ```

 - modify `MyModule/build.gradle`
  ```
   dependencies {
       // Cards Library
       compile project(':libraries:cardslib:library')
       // Cards Library-extra
       compile project(':libraries:cardslib:library-extra')
   }
  ```


## Reference this project as a library in Eclipse

If you would like to use this **library** in Eclipse you have to do these steps:

- clone a copy of this repository, or download it (outside eclipse workspace)
- import the code in your workspace starting from library folder. The Wizard will import the code in library/src/main. I suggest you to name it "cardslib" (or another name) instead of "main".
- mark java(*) folder as source (click on folder -> Build-Path -> use as source folder). You can also remove the src folder, from the project.
- mark cardslib as Android Library (Properties -> Android -> Is library)
- The library targets SDK 19 and works with minSdk=14. In any cases you need to use API>=16 to compile library (Properties -> Android)
- Clean and build

If you would like to use this **library-extra** in Eclipse you have to do these steps:
(this part is optional,it contains code to use integrations with other libraries, as StaggeredGridView and CardListDragDropView)

- import the code in your workspace starting from library-extra folder. The Wizard will import the code in library-extra/src/main. I suggest you to name it "cardslib-extra" (or another name) instead of "main".
- mark java(*) folder as source (click on folder -> Build-Path -> use as source folder). You can also remove the src folder, from the project.
- mark cardslib-extra as Android Library (Properties -> Android -> Is library)
- add cardslib as dependency ( click -> Properties -> Android -> Add library)
- add [Etsy-StaggeredGrid](https://github.com/etsy/AndroidStaggeredGrid) as dependency ( click -> Properties -> Android -> Add library) as dependency. Check the AndroidStaggeredGrid-library readme for instruction.
- add [ListViewAnimation](https://github.com/nhaarman/ListViewAnimations) as dependency ( click -> Properties -> Android -> Add library) as dependency. Check the ListViewAnimations-library readme for instruction.
- The library targets SDK 19 and works with minSdk=14. In any cases you need to use API>=16 to compile library (Properties -> Android)
- Clean and build

If you would like to build the **demo-stock** you have to do these additional steps:

- import the demo-code in your workspace starting from demo/stock folder.
- mark java(*) folder as source
- mark aidl(*) folder as source
- add support library v4 rel.19 ( click -> Android Tools -> Add support library , or just copy android-support-v4.jar in libs folder)
- add cardslib as dependency ( click -> Properties -> Android -> Add library)
- The demo-stock targets SDK 19 and works with minSdk=14. In any cases you need to use API>=16 to compile it
- Clean and build


(*) Eclipse uses src and res as source folders.
Android Studio instead uses src/main/java , src/main/res and src/main/aidl as source folders.

