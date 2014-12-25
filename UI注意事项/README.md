> Notice: 一下为ui的常用定义和调用方法，主要是xml文档


##获取string.xml文件里面的值有几个不同的地方。
###1.在AndroidManifest.xml与layout等xml文件里:
android:text="@string/resource_name" 
  
###2.在activity里：
方法一:this.getString(R.string.resource_name);  
方法二:getResources().getString(R.string.resource_name); 
 
###3.在其他java文件（必须有Context或pplication）
方法一: context.getString(R.string.resource_name); 
方法二: application.getString(R.string.resource_name);  


###android中string.xml文件的使用
###1.在程序中获取string.xml中字符串和数值

```xml
<?xml version="1.0" encoding="utf-8"?>
<resources>
    <string name="hello">Hello World, MainActivity!</string>
    <string name="app_name">TestExample01</string>
</resources>
```

##在Activity中使用:
String appName=(String) this.getResources().getText(R.string.app_name);
或者：
String appName=(String) this.getResources().getString(R.string.app_name);

###2.定义string数组(arrays.xml)
```xml
<?xml version="1.0" encoding="utf-8"?>
<resources>
    <string-array name="sports">
  <item>足球</item>
  <item>篮球</item>
  <item>太极</item>
  <item>冰球</item>
    </string-array>
</resources>
```
获取方法如下:
getResources().getStringArray(R.string.sports);


###3.定义颜色(colors.xml)
```xml
<?xml version="1.0" encoding="utf-8"?>
<resources>
    <color name="black">#FFFFFF</color>
</resources>
```

获取方法如下:
getResources().getDrawable(R.string.black);
getResources().getColor(R.string.black);


###4.定义尺寸(dimens.xml)
```xml
<?xml version="1.0" encoding="utf-8"?>
<resources>
   <dimen name="height">80dip</dimen>
</resources>
```
获取方法如下:
getResource().getDimension(R.string.height);


###5.定义样式(styles.xml)
```xml
<?xml version="1.0" encoding="utf-8"?>
<resources>
    <style name="sharpText">
  <item name="android:textSize">18sp</item>
  <item name="android:textColor">#000000</item>
    </style>
</resources>
```


> Notice: 以下是我们在布局的时候长用到的布局方式。

### 调用函数 ###

| 函数名        | 参数 | 描述 |
| ------------- |:--------:| -----:|
| pushState     | `String` | 计入历史记录 |
