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

in **xml**
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

Progress View for **PianoView**
```xml

<com.ice.apianoview.extention.PianoBar
    android:layout_width="0dp"
    android:layout_height="match_parent"
    app:imageBar="@drawable/piano_bar"
    app:progress="0"
    android:layout_weight="1"/>

```

Add PianoBar Listener to **PianoView**
```kotlin

    class MainActivity : AppCompatActivity(), PianoBar.ProgressListener {
        
        //Another code
        
        override fun onPianoInitFinish() {
            binding.pianoBar.addListener(this)
        }

        override fun onUserChangedProgress(progress: Int) {
            binding.pianoView.scroll(progress)
        }
    }
```


<h2 id="creators">Special Thanks :heart:</h2>
