Android ObjectAdapter
=====================
Version: 
Java [ ![Download](https://api.bintray.com/packages/pfaffenrodt/maven/android-object-adapter/images/download.svg) ](https://bintray.com/pfaffenrodt/maven/android-object-adapter/_latestVersion)
Kotlin [ ![Download](https://api.bintray.com/packages/pfaffenrodt/maven/android-kotlin-object-adapter/images/download.svg) ](https://bintray.com/pfaffenrodt/maven/android-kotlin-object-adapter/_latestVersion)

Adapter that is similar to the ObjectAdapter (ArrayObjectAdapter) from the Leanback support library.

## Presenter
Split presenter logic from adapter logic.
Presenter that will create ViewHolder and bind/unbind object to/from ViewHolder


## DataBinding
This library provide an simple presenter that using the databinding feature.

Java Version
```java

    new DataBindingPresenter(R.layout.item_sample_databinding, BR.item);

```

Kotlin Version
```kotlin

    DataBindingPresenter(R.layout.item_sample_databinding, BR.item)

```

## Gradle
Java Version
```gradle
compile 'de.pfaffenrodt:object-adapter:x.x.x'
```

Kotlin Version
```gradle
compile 'de.pfaffenrodt:object-adapter-kotlin:x.x.x'
```
