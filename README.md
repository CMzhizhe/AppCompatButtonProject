# AppCompatButtonProject
button 按钮，设置左边的小图标，与文字居中

[参考博客地址](https://www.jianshu.com/p/82110d3c2e20)

#### 功能点
- 支持自定义图标与文字的距离
- 支持小图标宽高自定义
- 支持左右自定义小图标

![效果图](https://github.com/CMzhizhe/AppCompatButtonProject/blob/master/pic/pic.jpg)
```

maven { url 'https://jitpack.io' }

implementation 'com.github.CMzhizhe:AppCompatButtonProject:1.0.0'
```
```
 <com.gxx.buttonlibrary.DrawableCenterButton
        android:layout_marginTop="10dp"
        android:clickable="true" 
        android:layout_width="172dp"
        android:layout_height="55dp"
        app:dl_dis="10dp" //文字图片距离
        app:dl_width="30dp" //宽高
        app:dl_height="30dp"
        android:gravity="center" //居中
        android:background="@drawable/ripple_c899fc_solid_7904fd_radius_50"
        android:drawableStart="@drawable/down" //设置左边，右边就用 android:drawableRight
        android:text="Download"
        android:textColor="@color/white"
        android:textSize="18sp" />

```
