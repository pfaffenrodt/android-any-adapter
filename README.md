Android ObjectAdapter
=====================
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
compile 'de.pfaffenrodt:object-adapter:1.0.1'
```

Kotlin Version
```gradle
compile 'de.pfaffenrodt:object-adapter-kotlin:1.0.1'
```
