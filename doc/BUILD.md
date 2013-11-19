# Cards Library: How to build/use library

The library is written with Android Studio and has Gradle support.

You can add this library to your project adding a dependency to your `build.gradle`, or your can reference this project as a library (from Eclipse) or add it as a module (from IntelliJ).


## Including in your project with Gradle

Card Library is pushed to Maven Central as a AAR, so you just need to add the following dependency to your `build.gradle`.

    dependencies {
        compile 'com.github.gabrielemariotti.cards:library:0.9.0'
    }


## Reference this project as a library in Eclipse

If you would like to use this library in Eclipse you have to do these steps:

- clone a copy of this repository, or download it (outside eclipse workspace)
- import the code in your workspace starting from library folder. The Wizard will import the code in library/src/main. I suggest you to name it "cardslib" (or another name) instead of "main".
- mark java(*) folder as source (click on folder -> Build-Path -> use as source folder). You can also remove the src folder, from the project.
- mark cardslib as Android Library (Properties -> Android -> Is library)
- The library targets SDK 19 and works with minSdk=14. In any cases you need to use API>=16 to compile library (Properties -> Android)


If you would like to build the demo-stock you have to do these additional steps:

- import the demo-code in your workspace starting from demo/stock folder.
- mark java(*) folder as source
- mark aidl(*) folder as source
- add support library v4 rel.19 ( click -> Android Tools -> Add support library , or just copy android-support-v4.jar in libs folder)
- add cardslib as dependency ( click -> Properties -> Android -> Add library)
- The demo-stock targets SDK 19 and works with minSdk=14. In any cases you need to use API>=16 to compile it


(*) Eclipse uses src and res as source folders.
Android Studio instead uses src/main/java , src/main/res and src/main/aidl as source folders.

