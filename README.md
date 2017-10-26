Android ObjectAdapter
=====================
Adapter that is similar to the ObjectAdapter (ArrayObjectAdapter) from the Leanback support library.

# Presenter
Split presenter logic from adapter logic.
Presenter that will create ViewHolder and bind/unbind object to/from ViewHolder


# DataBinding
This library provide an simple presenter that using the databinding feature.
```java

    new DataBindingPresenter(R.layout.item_sample_databinding, BR.item);

```

# Gradle

```gradle
compile 'de.pfaffenrodt:object-adapter:1.0.1'
```
