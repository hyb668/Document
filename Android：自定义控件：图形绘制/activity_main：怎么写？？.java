public class PressView extends View
values/press_view_attrs.xml

  xmlns:app="http://schemas.android.com/apk/res-auto"


<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    xmlns:app="http://schemas.android.com/apk/res-auto"
	//固定的 = http://schemas.android.com/apk/res/
	//自己的包名 = com.alex.pressview
	//tools:ignore="UnusedNamespace" 
	//否则会引起，没有使用命名空间的警告
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".MainActivity"
    tools:ignore="UnusedNamespace" >

    <github.alexcheung.viewlib.PressView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_margin="20dip"
        android:padding="10dip"
        app:textBackground="#C0C0C0"
        app:textColor="#808080"
        app:textSize="36sp" />

</RelativeLayout>