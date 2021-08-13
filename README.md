# Project Title

Asteroid Radar

## Getting Started

Asteroid Radar is an app to view the asteroids detected by NASA that pass near Earth, you can view all the detected asteroids in a period of time, their data (Size, velocity, distance to Earth) and if they are potentially hazardous.

The app is consists of two screens: A Main screen with a list of all the detected asteroids and a Details screen that is going to display the data of that asteroid once it´s selected in the Main screen list. The main screen will also show the NASA image of the day to make the app more striking.

This kind of app is one of the most usual in the real world, what you will learn by doing this are some of the most fundamental skills you need to know to work as a professional Android developer, as fetching data from the internet, saving data to a database, and display the data in a clear, clear, compelling UI.


```
implementation fileTree(dir: 'libs', include: ['*.jar'])
implementation "org.jetbrains.kotlin:kotlin-stdlib-jdk7:$version_kotlin"

// Support libraries
implementation "androidx.appcompat:appcompat:$version_appcompat"
implementation "androidx.fragment:fragment-ktx:$version_fragment"
implementation "androidx.constraintlayout:constraintlayout:$version_constraint_layout"

// Android KTX
implementation "androidx.core:core-ktx:$version_core"

// Navigation
implementation "android.arch.navigation:navigation-fragment-ktx:$version_navigation"
implementation "android.arch.navigation:navigation-ui-ktx:$version_navigation"

// Coroutines for getting off the UI thread
implementation "org.jetbrains.kotlinx:kotlinx-coroutines-core:$version_kotlin_coroutines"
implementation "org.jetbrains.kotlinx:kotlinx-coroutines-android:$version_kotlin_coroutines"


// Retrofit for networking
implementation "com.squareup.retrofit2:retrofit:$version_retrofit"
implementation "com.squareup.retrofit2:converter-moshi:$version_retrofit"
implementation "com.squareup.retrofit2:converter-scalars:$version_retrofit"
implementation "com.jakewharton.retrofit:retrofit2-kotlin-coroutines-adapter:$version_retrofit_coroutines_adapter"

// Moshi for parsing the JSON format
implementation "com.squareup.moshi:moshi:$version_moshi"
implementation "com.squareup.moshi:moshi-kotlin:$version_moshi"

// Joda time library for dealing with time
implementation "joda-time:joda-time:$version_joda"

// ViewModel and LiveData (arch components)
implementation "androidx.lifecycle:lifecycle-extensions:$version_lifecycle_extensions"
implementation "androidx.lifecycle:lifecycle-viewmodel-ktx:$version_lifecycle"

// Logging
implementation "com.jakewharton.timber:timber:$version_timber"

// Picasso for images
implementation "com.squareup.picasso:picasso:$version_picasso"

// Room database
implementation "androidx.room:room-runtime:$version_room"
kapt "androidx.room:room-compiler:$version_room"

// Kotlin Extensions and Coroutines support for Room
implementation "androidx.room:room-ktx:$version_room"

// WorkManager
implementation "android.arch.work:work-runtime-ktx:$version_work"

// Testing
testImplementation 'junit:junit:4.13.2'
androidTestImplementation 'androidx.test.ext:junit:1.1.3'
androidTestImplementation 'androidx.test.espresso:espresso-core:3.4.0'
```

### Versions

```
version_core = "1.6.0"
version_coroutine = "1.3.7"
version_retrofit_coroutines_adapter = "0.9.2"
version_navigation = '1.0.0'
version_constraint_layout = "2.1.0"
version_gradle = '4.2.2'
version_kotlin = "1.5.21"
version_lifecycle = "2.3.1"
version_lifecycle_extensions = "2.2.0"
version_room = "2.3.0"
version_appcompat = "1.3.1"
version_fragment = "1.3.6"
version_retrofit = "2.9.0"
version_kotlin_coroutines = "1.4.1"
version_moshi = '1.12.0'
version_picasso = '2.71828'
version_glide = "4.8.0"
version_joda = '2.10.10'
version_work = "1.0.1"
version_timber = "4.7.1"
```


