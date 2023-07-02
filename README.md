Android AnyAdapter
=====================
[![GitHub tag (latest SemVer)](https://img.shields.io/github/v/tag/pfaffenrodt/android-any-adapter)](https://github.com/pfaffenrodt/android-any-adapter/tags)
[![Maven Central](https://img.shields.io/maven-central/v/de.pfaffenrodt/any-adapter)](https://repo1.maven.org/maven2/de/pfaffenrodt/any-adapter/)

Adapter that is similar to the [ObjectAdapter](https://developer.android.com/reference/androidx/leanback/widget/ObjectAdapter) (ArrayObjectAdapter) from the Leanback widget library ([androidx.leanback.widget](https://developer.android.com/reference/androidx/leanback/widget/package-summary)).
Leanbacks package provide many many features and focus on TV. But so many things concludes to complexity.

Focus of this package is to maintain a kotlin lightweight variant.
In contrast to leanbacks ObjectAdapter the AnyAdapter is based on RecyclerViews.Adapter.

## Gradle

```build.gradle.kts
repositories {
    mavenCentral()
}
dependencies {
    implementation("de.pfaffenrodt:any-adapter:x.x.x")
}
```

## ChangeLog

Please see [CHANGELOG](./CHANGELOG.md) for more information on what has changed recently.

## Features
What does it do?
* :boom: You do not have to write RecyclerViews.Adapter or, ViewHolder anymore.
* :lipstick: by implementing a Presenter you can reuse your presentation logic of list items
* databinding support. use DataBindingPresenter and you also do not need write a presenter
* paging support. AnyPagingDataAdapter based on PagingDataAdapter
* ClassPresenterSelector use it to map your domain object to a Presenter

## Presenter
Split presenter logic from adapter logic.
Presenter that will create ViewHolder and bind/unbind object to/from ViewHolder

## Basics
Implement your own Presenter or use DataBindingPresenter.
To use multiple viewTypes use PresenterSelector.

```kotlin
val classPresenterSelector = ClassPresenterSelector()
    .addClassPresenter(
        SampleItemA::class, // item class 
        SamplePresenterA()
)
val adapter = ArrayObjectAdapter(classPresenterSelector)
val items :List<SampleItemA> = ...
adapter.setItems(items);

```

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

```kotlin

    DataBindingPresenter(R.layout.item_sample_databinding, BR.item)
    .bindVariable(BR.eventHandler, mEventHandler)// bind click listener

```

# Heros

Be my Sponsor here on [github](https://github.com/sponsors/pfaffenrodt) .
