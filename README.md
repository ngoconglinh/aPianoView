[![](https://jitpack.io/v/ngoconglinh/aPianoView.svg)](https://jitpack.io/#ngoconglinh/aPianoView)

## Quick Start

**aPianoView** is available on jitpack.

Add dependency:

```groovy
implementation "com.github.ngoconglinh:aPianoView:last-release"
```

## Usage

to use **aPianoView**:

in **Setting.gradle**
```groovy
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        mavenCentral()
        maven { url 'https://jitpack.io' }
    }
}
```
```xml

<com.ice.apianoview.view.PianoView
    android:id="@+id/pianoView"
    android:layout_width="match_parent"
    android:layout_height="0dp"
    android:layout_weight="1"
    app:keyWhiteDownDrawable="@drawable/white_down_theme_3"
    app:keyWhiteUpDrawable="@drawable/white_up_theme_3"
    app:keyBlackDownDrawable="@drawable/black_down"
    app:keyBlackUpDrawable="@drawable/black_up"/>
```



<h2 id="creators">Special Thanks :heart:</h2>
