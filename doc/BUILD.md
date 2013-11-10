# Cards Library: How to build/use library

The library is written with Android Studio and has Gradle support.

You can add this library to your project adding a dependency to your `build.gradle`, or your can reference this project as a library (from Eclipse) or add it as a module (from IntelliJ).


## Including in your project with Gradle

Card Library is pushed to Maven Central as a AAR, so you just need to add the following dependency to your `build.gradle`.

    dependencies {
        compile 'com.github.gabrielemariotti.cards:library:0.8.0'
    }


## Reference this project as a library in Eclipse

if you would like to use this library in Eclipse you have to do these steps:

- clone a copy of this repository, or download it.
- import the code in your workspace
- mark java(*) folder as source (click on folder -> Build-Path -> use as source folder)
- mark Library as Android Library (Properties -> Android -> Is library)
- The library targets SDK 19 and works with minSdk=14. In any cases you need to use API>=16 to compile library (Properties -> Android)


If you would like to build the demo-stock you have to do these additional steps:

- import the demo-code in your workspace
- mark java(*) folder as source
- mark aidl(*) folder as source
- add support library v4 rel.18 ( click -> Android Tools -> Add support library , or just copy android-support-v4.jar in libs folder)
- add Library as dependency ( click -> Properties -> Android -> Add library)
- The demo-stock targets SDK 19 and works with minSdk=14. In any cases you need to use API>=16 to compile it


(*) Eclipse uses src and res as source folders.
Android Studio instead uses src/main/java , src/main/res and src/main/aidl as source folders.

