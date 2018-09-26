Android ObjectAdapter
=====================
Version: 
Java [ ![Download](https://api.bintray.com/packages/pfaffenrodt/maven/android-object-adapter/images/download.svg) ](https://bintray.com/pfaffenrodt/maven/android-object-adapter/_latestVersion)
Kotlin [ ![Download](https://api.bintray.com/packages/pfaffenrodt/maven/android-kotlin-object-adapter/images/download.svg) ](https://bintray.com/pfaffenrodt/maven/android-kotlin-object-adapter/_latestVersion)

Adapter that is similar to the ObjectAdapter (ArrayObjectAdapter) from the Leanback support library.

## ChangeLog

1.4
   - migrate to androidx


## Gradle
Java Version
```gradle
compile 'de.pfaffenrodt:object-adapter:x.x.x'
```

Kotlin Version
```gradle
compile 'de.pfaffenrodt:object-adapter-kotlin:x.x.x'
```


## Presenter
Split presenter logic from adapter logic.
Presenter that will create ViewHolder and bind/unbind object to/from ViewHolder

## DataBindingPresenter
What is Data Binding? [Doku](https://developer.android.com/topic/libraries/data-binding/index.html)

Data Binding allows to use ObjectAdapter without implementing an Presenter.
DataBindingPresenter will bind and unbind item to/from view.

```xml

<?xml version="1.0" encoding="utf-8"?>
<layout xmlns:android="http://schemas.android.com/apk/res/android">
    <data>
        <variable
            name="item"
            type="de.pfaffenrodt.adapter.sample.SampleItem" />
    </data>
    <TextView
        android:id="@+id/text"
        android:orientation="vertical"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:text="@{item.text}"
        />
</layout>

```

Java Version
```java

    new DataBindingPresenter(R.layout.item_sample_databinding, BR.item)
    .bindVariable(BR.eventHandler, mEventHandler);// bind click listener

```

Kotlin Version
```kotlin

    DataBindingPresenter(R.layout.item_sample_databinding, BR.item)
    .bindVariable(BR.eventHandler, mEventHandler)// bind click listener

```

## Basics
Implement your own Presenter or use DataBindingPresenter.
To use multiple viewTypes use PresenterSelector. 

```java
ClassPresenterSelector classPresenterSelector = new ClassPresenterSelector();
classPresenterSelector.addClassPresenter(
        String.class, // item class 
        new SamplePresenterA() // Presenter
);
ArrayObjectAdapter adapter = new ArrayObjectAdapter(classPresenterSelector);
List<String> items = ...;
adapter.setItems(items, null);

```

```kotlin
classPresenterSelector.addClassPresenter(
        SampleItemA::class, // item class 
        SamplePresenterA()
)
val adapter = ArrayObjectAdapter(classPresenterSelector)
val items :List<SampleItemA> items = ...
adapter.setItems(items);

```