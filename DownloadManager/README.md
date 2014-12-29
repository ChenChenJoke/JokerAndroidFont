

从Android 2.3（API level 9）开始Android用系统服务(Service)的方式提供了Download Manager来优化处理长时间的下载操作。Download Manager处理HTTP

连接并监控连接中的状态变化以及系统重启来确保每一个下载任务顺利完成。

在大多数涉及到下载的情况中使用Download Manager都是不错的选择，特别是当用户切换不同的应用以后下载需要在后台继续进行，以及当下载任务顺利完

成非常重要的情况（DownloadManager对于断点续传功能支持很好）。

要想使用Download Manager，使用getSystemService方法请求系统的DOWNLOAD_SERVICE服务，代码片段如下：




###下载文件

在我们开始之前，我们先赋予我们应用两个权限，一个是访问网络，一个是访问手机的SD卡。

```xml
<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    package="com.example.downloadmanager"
    android:versionCode="1"
    android:versionName="1.0" >

    <uses-sdk
        android:minSdkVersion="18"
        android:targetSdkVersion="21" />

    <application
        android:allowBackup="true"
        android:icon="@drawable/ic_launcher"
        android:label="@string/app_name"
        android:theme="@style/AppTheme" >
        <activity
            android:name=".MainActivity"
            android:label="@string/app_name" >
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />
                <category android:name="android.intent.category.LAUNCHER" />
                <data android:mimeType="application/cn.trinea.download.file" /><!-- 下载完成打开文件 -->
            </intent-filter>
        </activity>
    </application>
    <uses-permission android:name="android.permission.INTERNET" ></uses-permission>
    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"></uses-permission>
</manifest>


```


要请求一个下载操作，需要创建一个DownloadManager.Request对象，将要请求下载的文件的Uri传递给Download Manager的enqueue方法，代码片段如下所示：


```java
String serviceString = Context.DOWNLOAD_SERVICE; 
DownloadManager downloadManager; 
downloadManager = (DownloadManager)getSystemService(serviceString); 

 //这里填写你要下载的资源的地址
Uri uri = Uri.parse("http://developer.android.com/shareables/icon_templates-v4.0.zip"); 
DownloadManager.Request request = new Request(uri); 
  isFolderExist("Downloadmanager");
  /*设置下载基础信息,Downloadmanager为存放的url，后面的<下载的微信.apk> 是下载文件存储的文件名*/
  request.setDestinationInExternalPublicDir("Downloadmanager", "下载的微信.apk");
  
  request.setTitle("下载的微信");//设置下载中通知栏提示的标题
  request.setDescription("微信 introduce");//设置下载中通知栏提示的介绍
  request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
  //表示下载进行中和下载完成的通知栏是否显示。默认只显示下载中通知。
long reference = downloadManager.enqueue(request); 
```

###参数介绍###

request.allowScanningByMediaScanner();表示允许MediaScanner扫描到这个文件，默认不允许。
request.setTitle(“下载的微信”);设置下载中通知栏提示的标题
request.setDescription(“微信 introduce”);设置下载中通知栏提示的介绍
request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
表示下载进行中和下载完成的通知栏是否显示。默认只显示下载中通知。VISIBILITY_VISIBLE_NOTIFY_COMPLETED表示下载完成后显示通知栏提示。VISIBILITY_HIDDEN表示不显示任何通知栏提示，这个需要在AndroidMainfest中添加权限android.permission.DOWNLOAD_WITHOUT_NOTIFICATION.
 
request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI);
表示下载允许的网络类型，默认在任何网络下都允许下载。有NETWORK_MOBILE、NETWORK_WIFI、NETWORK_BLUETOOTH三种及其组合可供选择。如果只允许wifi下载，而当前网络为3g，则下载会等待。
request.setAllowedOverRoaming(boolean allow)移动网络情况下是否允许漫游。
 
request.setMimeType(“application/cn.trinea.download.file”);
设置下载文件的mineType。因为下载管理Ui中点击某个已下载完成文件及下载完成点击通知栏提示都会根据mimeType去打开文件，所以我们可以利用这个属性。比如上面设置了mimeType为application/cn.trinea.download.file，我们可以同时设置某个Activity的intent-filter为application/cn.trinea.download.file，用于响应点击的打开文件。
我们回头看一下头部的xml里面的activity里面多了一个，data标签就是做这个用的。



###未完待续,Mark下一步完成下载进度的查询和更新，以及下载之后自动打开文件###

> notice :项目是上面写的demo可以直接运行，主页有我的联系方式。