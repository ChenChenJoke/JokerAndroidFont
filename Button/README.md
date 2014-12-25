> notice:自定义背景的按钮目前有2种方式实现，矢量和位图。
#1. 矢量图形绘制的方式
矢量图形绘制的方式实现简单，适合对于按钮形状和图案要求不高的场合。步骤如下：
###(a) 使用xml定义一个圆角矩形，外围轮廓线实线、内填充渐变色，xml代码如下。
```xml
<?xml version="1.0" encoding="utf-8"?>  
<layer-list xmlns:android="http://schemas.android.com/apk/res/android">  
   <item>  
      <shape android:shape="rectangle">   
         <solid android:color="#FFEC7600" />  
         <corners  
            android:topLeftRadius="5dip"  
            android:topRightRadius="5dip"  
            android:bottomLeftRadius="5dip"  
            android:bottomRightRadius="5dip" />  
      </shape>  
   </item>  
   <item android:top="1px" android:bottom="1px" android:left="1px" android:right="1px">  
     <shape>  
        <gradient   
            android:startColor="#FFEC7600" android:endColor="#FFFED69E"   
            android:type="linear" android:angle="90"  
            android:centerX="0.5" android:centerY="0.5" />  
        <corners  
            android:topLeftRadius="5dip"  
            android:topRightRadius="5dip"  
            android:bottomLeftRadius="5dip"  
            android:bottomRightRadius="5dip" />  
      </shape>  
   </item>    
</layer-list>  
```
同样定义bg_alibuybutton_pressed.xml和bg_alibuybutton_selected.xml，内容相同，就是渐变颜色不同，用于按钮按下后的背景变化效果。
###(b) 定义按钮按下后的效果变化描述文件drawable/bg_alibuybutton.xml，代码如下。
```xml
<?xml version="1.0" encoding="UTF-8"?>  
<selector xmlns:android="http://schemas.android.com/apk/res/android">  
    <item android:state_pressed="true"  
        android:drawable="@drawable/bg_alibuybutton_pressed" />  
    <item android:state_focused="true"  
        android:drawable="@drawable/bg_alibuybutton_selected" />  
    <item android:drawable="@drawable/bg_alibuybutton_default" />  
</selector>  
```
###(c) 在你需要的界面定义文件中，如layout/main.xml中定义一个Button控件。
```xml
<Button  
    android:layout_width="120dip"  
    android:layout_height="40dip"  
    android:text="矢量背景按钮"       android:background="@drawable/bg_alibuybutton" />  
```
这样，自定义背景的按钮就可以使用了，在实现onClick方法后就可以响应操作。
 
 
 

 
 
 