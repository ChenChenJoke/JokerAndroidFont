

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
            </intent-filter>
        </activity>
    </application>
    <!--我是访问网络-->
    <uses-permission android:name="android.permission.INTERNET" ></uses-permission> 
    <!--我是访问SD卡-->
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
long reference = downloadManager.enqueue(request); 
```

这里返回的reference变量是系统为当前的下载请求分配的一个唯一的ID，我们可以通过这个ID重新获得这个下载任务，进行一些自己想要进行的操作或者查询

下载的状态以及取消下载等等。

我们可以通过addRequestHeader方法为DownloadManager.Request对象request添加HTTP头，也可以通过setMimeType方法重写从服务器返回的mime type。

我们还可以指定在什么连接状态下执行下载操作。setAllowedNetworkTypes方法可以用来限定在WiFi还是手机网络下进行下载，setAllowedOverRoaming方法

可以用来阻止手机在漫游状态下下载。

下面的代码片段用于指定一个较大的文件只能在WiFi下进行下载：

```java
request.setAllowedNetworkTypes(Request.NETWORK_WIFI); 
```

###下载完成回调函数###
Android API level 11 介绍了getRecommendedMaxBytesOverMobile类方法（静态方法），返回一个当前手机网络连接下的最大建议字节数，可以来判断下载

是否应该限定在WiFi条件下。

调用enqueue方法之后，只要数据连接可用并且Download Manager可用，下载就会开始。

要在下载完成的时候获得一个系统通知（notification）,注册一个广播接受者来接收ACTION_DOWNLOAD_COMPLETE广播，这个广播会包含一个

EXTRA_DOWNLOAD_ID信息在intent中包含了已经完成的这个下载的ID,代码片段如下所示：

```java
IntentFilter filter = new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE); 
     
BroadcastReceiver receiver = new BroadcastReceiver() { 
  @Override 
  public void onReceive(Context context, Intent intent) { 
    long reference = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1); 
    if (myDownloadReference == reference) { 
      
    } 
  } 
}; 
registerReceiver(receiver, filter); 
```

###打开已下载的文件###
使用Download Manager的openDownloadedFile方法可以打开一个已经下载完成的文件，返回一个ParcelFileDescriptor对象。我们可以通过Download Manager来

查询下载文件的保存地址，如果在下载时制定了路径和文件名，我们也可以直接操作文件。

我们可以为ACTION_NOTIFICATION_CLICKED action注册一个广播接受者，当用户从通知栏点击了一个下载项目或者从Downloads app点击可一个下载的项目的

时候，系统就会发出一个点击下载项的广播。

代码片段如下：

```java
IntentFilter filter = new IntentFilter(DownloadManager.ACTION_NOTIFICATION_CLICKED); 
 
BroadcastReceiver receiver = new BroadcastReceiver() { 
  @Override 
  public void onReceive(Context context, Intent intent) { 
    String extraID = DownloadManager.EXTRA_NOTIFICATION_CLICK_DOWNLOAD_IDS; 
    long[] references = intent.getLongArrayExtra(extraID); 
    for (long reference : references) 
      if (reference == myDownloadReference) { 
        // Do something with downloading file. 
      } 
  } 
}; 
 
registerReceiver(receiver, filter); 
```