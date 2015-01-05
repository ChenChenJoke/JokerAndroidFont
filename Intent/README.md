
###Intent的基本用法：###

**1、**使用Action跳转，如果有一个程序的AndroidManifest.xml中的某一个 Activity的IntentFilter段中 定义了包含了相同的Action那么这个Intent就与这个目标Action匹配。如果这个IntentFilter段中没有定义 Type,Category，那么这个 Activity就匹配了。但是如果手机中有两个以上的程序匹配，那么就会弹出一个对话可框来提示说明。 
Action 的值在Android中有很多预定义，如果你想直接转到你自己定义的Intent接收者，你可以在接收者的IntentFilter 中加入一个自定义的Action值（同时要设定 Category值为"android.intent.category.DEFAULT"），在你的Intent中设定该值为Intent的 Action,就直接能跳转到你自己的Intent接收者中。因为这个Action在系统中是唯一的。


**2、**data/type，你可以用Uri 来做为data,比如Uri uri = Uri.parse(http://www.google.com);
Intent i = new Intent(Intent.ACTION_VIEW,uri);手机的Intent分发过程中，会根据http://www.google.com 的scheme判断出数据类型type 。手机的Brower则能匹配它，在Brower的Manifest.xml中的IntenFilter中 首先有ACTION_VIEW Action,也能处理http:的type，

**3、** 至于分类Category，一般不要去在Intent中设置它，如果你写Intent的接收者，就在Manifest.xml的Activity的 IntentFilter中包含android.category.DEFAULT,这样所有不设置 Category（Intent.addCategory(String c);）的Intent都会与这个Category匹配。

**4、**extras（附 加信息），是其它所有附加信息的集合。使用extras可以为组件提供扩展信息，比如，如果要执行“发送电子邮件”这个动 作，可以将电子邮件的标题、正文等保存在extras里，传给电子邮件发送组件。

**5、**用类名跳转(最常用)
    Intent负责对应用中一次操作的动作、动作涉及数据、附加数据进行描 述，Android则根据此Intent的描述， 负责找到对应的组件，将 Intent传递给调用的组件，并完成组件的调用。Intent在这里起着实现调用者与被调用者之间的解耦作用。Intent传递过程中，要找 到目标消费者（另一个Activity,IntentReceiver或Service），也就是Intent的响 应者。

```java
Intent intent = new Intent();
intent.setClass(context, targetActivy.class);
//或者直接用 Intent intent = new Intent(context, targetActivity.class);
startActivity(intent);

```

> 不过注意用类名跳转，需要在AndroidManifest.xml中申明activity  


```xml
<activity android:name="targetActivity"></activity>

```
###几种Intent的进阶用法###
android 中intent是经常要用到的。不管是页面牵转，还是传递数据，或是调用外部程序，系统功能都要用到intent。在做了一些intent的例子之后，整理了一下intent，希望对大家有用。由于intent内容太多，不可能真的写全，难免会有遗落，以后我会随时更新。如果你们有疑问或新的intent内容，希望交流。 
 
######1.从google搜索内容 ######

```java
Intent intent = new Intent(); 
intent.setAction(Intent.ACTION_WEB_SEARCH); 
intent.putExtra(SearchManager.QUERY,"searchString") 
startActivity(intent); 
```

######2.浏览网页###### 
```java
Uri uri = Uri.parse("http://www.google.com"); 
Intent it  = new Intent(Intent.ACTION_VIEW,uri); 
startActivity(it); 
```

######3.显示地图###### 
```java
Uri uri = Uri.parse("geo:38.899533,-77.036476"); 
Intent it = new Intent(Intent.Action_VIEW,uri); 
startActivity(it); 
```

######4.路径规划######
```java
Uri uri = Uri.parse("http://maps.google.com/maps?f=dsaddr=startLat%20startLng&daddr=endLat%20endLng&hl=en"); 
Intent it = new Intent(Intent.ACTION_VIEW,URI); 
startActivity(it); 
```

######5.拨打电话###### 

```java
Uri uri = Uri.parse("tel:xxxxxx"); 
Intent it = new Intent(Intent.ACTION_DIAL, uri);   
startActivity(it); 
```

######6.调用发短信的程序###### 
```java
Intent it = new Intent(Intent.ACTION_VIEW);    
it.putExtra("sms_body", "The SMS text");    
it.setType("vnd.android-dir/mms-sms");    
startActivity(it); 
```

######7.发送短信###### 

```java
Uri uri = Uri.parse("smsto:0800000123");    
Intent it = new Intent(Intent.ACTION_SENDTO, uri);    
it.putExtra("sms_body", "The SMS text");    
startActivity(it); 
String body="this is sms demo"; 
Intent mmsintent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("smsto", number, null)); 
mmsintent.putExtra(Messaging.KEY_ACTION_SENDTO_MESSAGE_BODY, body); 
mmsintent.putExtra(Messaging.KEY_ACTION_SENDTO_COMPOSE_MODE, true); 
mmsintent.putExtra(Messaging.KEY_ACTION_SENDTO_EXIT_ON_SENT, true); 
startActivity(mmsintent); 
```

######8.发送彩信###### 

```java
Uri uri = Uri.parse("content://media/external/images/media/23");    
Intent it = new Intent(Intent.ACTION_SEND);    
it.putExtra("sms_body", "some text");    
it.putExtra(Intent.EXTRA_STREAM, uri);    
it.setType("image/png");    
startActivity(it); 
StringBuilder sb = new StringBuilder(); 
sb.append("file://"); 
sb.append(fd.getAbsoluteFile()); 
Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mmsto", number, null)); 
// Below extra datas are all optional. 
intent.putExtra(Messaging.KEY_ACTION_SENDTO_MESSAGE_SUBJECT, subject); 
intent.putExtra(Messaging.KEY_ACTION_SENDTO_MESSAGE_BODY, body); 
intent.putExtra(Messaging.KEY_ACTION_SENDTO_CONTENT_URI, sb.toString()); 
intent.putExtra(Messaging.KEY_ACTION_SENDTO_COMPOSE_MODE, composeMode); 
intent.putExtra(Messaging.KEY_ACTION_SENDTO_EXIT_ON_SENT, exitOnSent); 
startActivity(intent); 
```

######9.发送Email###### 
```java
Uri uri = Uri.parse("mailto:xxx@abc.com"); 
Intent it = new Intent(Intent.ACTION_SENDTO, uri); 
startActivity(it); 
Intent it = new Intent(Intent.ACTION_SEND);    
it.putExtra(Intent.EXTRA_EMAIL, "me@abc.com");    
it.putExtra(Intent.EXTRA_TEXT, "The email body text");    
it.setType("text/plain");    
startActivity(Intent.createChooser(it, "Choose Email Client")); 
Intent it=new Intent(Intent.ACTION_SEND);      
String[] tos={"me@abc.com"};      
String[] ccs={"you@abc.com"};      
it.putExtra(Intent.EXTRA_EMAIL, tos);      
it.putExtra(Intent.EXTRA_CC, ccs);      
it.putExtra(Intent.EXTRA_TEXT, "The email body text");      
it.putExtra(Intent.EXTRA_SUBJECT, "The email subject text");      
it.setType("message/rfc822");      
startActivity(Intent.createChooser(it, "Choose Email Client"));    

Intent it = new Intent(Intent.ACTION_SEND);    
it.putExtra(Intent.EXTRA_SUBJECT, "The email subject text");    
it.putExtra(Intent.EXTRA_STREAM, "file:///sdcard/mysong.mp3");    
sendIntent.setType("audio/mp3");    
startActivity(Intent.createChooser(it, "Choose Email Client")); 
```


######10.播放多媒体######   

```java
Intent it = new Intent(Intent.ACTION_VIEW); 
Uri uri = Uri.parse("file:///sdcard/song.mp3"); 
it.setDataAndType(uri, "audio/mp3"); 
startActivity(it); 
Uri uri = Uri.withAppendedPath(MediaStore.Audio.Media.INTERNAL_CONTENT_URI, "1");    
Intent it = new Intent(Intent.ACTION_VIEW, uri);    
startActivity(it); 
```


######11.uninstall apk###### 
```java
Uri uri = Uri.fromParts("package", strPackageName, null);    
Intent it = new Intent(Intent.ACTION_DELETE, uri);    
startActivity(it); 
```

######12.install apk###### 

```java
Uri installUri = Uri.fromParts("package", "xxx", null); 
returnIt = new Intent(Intent.ACTION_PACKAGE_ADDED, installUri); 
```

######13. 打开照相机###### 
```java
Intent i = new Intent(Intent.ACTION_CAMERA_BUTTON, null); 
this.sendBroadcast(i); 

long dateTaken = System.currentTimeMillis(); 
String name = createName(dateTaken) + ".jpg"; 
fileName = folder + name; 
ContentValues values = new ContentValues(); 
values.put(Images.Media.TITLE, fileName); 
values.put("_data", fileName); 
values.put(Images.Media.PICASA_ID, fileName); 
values.put(Images.Media.DISPLAY_NAME, fileName); 
values.put(Images.Media.DESCRIPTION, fileName); 
values.put(Images.ImageColumns.BUCKET_DISPLAY_NAME, fileName); 
Uri photoUri = getContentResolver().insert( 
        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values); 
 
Intent inttPhoto = new Intent(MediaStore.ACTION_IMAGE_CAPTURE); 
inttPhoto.putExtra(MediaStore.EXTRA_OUTPUT, photoUri); 
startActivityForResult(inttPhoto, 10); 
```

######14.从gallery选取图片###### 

```java
  Intent i = new Intent(); 
            i.setType("image/*"); 
            i.setAction(Intent.ACTION_GET_CONTENT); 
            startActivityForResult(i, 11); 
```

######15. 打开录音机###### 
```java
   Intent mi = new Intent(Media.RECORD_SOUND_ACTION); 
  startActivity(mi); 
  ```

######16.显示应用详细列表######      
```java 
Uri uri = Uri.parse("market://details?id=app_id");         
Intent it = new Intent(Intent.ACTION_VIEW, uri);         
startActivity(it);         
//where app_id is the application ID, find the ID          
//by clicking on your application on Market home          
//page, and notice the ID from the address bar      

刚才找app id未果，结果发现用package name也可以 
Uri uri = Uri.parse("market://details?id=<packagename>"); 
这个简单多了 
  ```

######17寻找应用######   

```java   
Uri uri = Uri.parse("market://search?q=pname:pkg_name");         
Intent it = new Intent(Intent.ACTION_VIEW, uri);         
startActivity(it); 
//where pkg_name is the full package path for an application       

```
######18打开联系人列表###### 
```java           
           Intent i = new Intent(); 
           i.setAction(Intent.ACTION_GET_CONTENT); 
           i.setType("vnd.android.cursor.item/phone"); 
           startActivityForResult(i, REQUEST_TEXT); 

         
            Uri uri = Uri.parse("content://contacts/people"); 
            Intent it = new Intent(Intent.ACTION_PICK, uri); 
            startActivityForResult(it, REQUEST_TEXT); 
```
######19 打开另一程序######
```java
Intent i = new Intent(); 
            ComponentName cn = new ComponentName("com.yellowbook.android2", 
                    "com.yellowbook.android2.AndroidSearch"); 
            i.setComponent(cn); 
            i.setAction("android.intent.action.MAIN"); 
            startActivityForResult(i, RESULT_OK); 
```

######20.调用系统编辑添加联系人（高版本SDK有效）：######
```java
Intent it = newIntent(Intent.ACTION_INSERT_OR_EDIT);
               it.setType("vnd.android.cursor.item/contact");
                //it.setType(Contacts.CONTENT_ITEM_TYPE);
                it.putExtra("name","myName");
               it.putExtra(android.provider.Contacts.Intents.Insert.COMPANY,  "organization");
               it.putExtra(android.provider.Contacts.Intents.Insert.EMAIL,"email");
                it.putExtra(android.provider.Contacts.Intents.Insert.PHONE,"homePhone");
                it.putExtra(android.provider.Contacts.Intents.Insert.SECONDARY_PHONE,
                               "mobilePhone");
                it.putExtra(  android.provider.Contacts.Intents.Insert.TERTIARY_PHONE,
                               "workPhone");
               it.putExtra(android.provider.Contacts.Intents.Insert.JOB_TITLE,"title");
                startActivity(it);
 ```
######21.调用系统编辑添加联系人（全有效）：######
```java
Intent intent = newIntent(Intent.ACTION_INSERT_OR_EDIT);
           intent.setType(People.CONTENT_ITEM_TYPE);
           intent.putExtra(Contacts.Intents.Insert.NAME, "My Name");
           intent.putExtra(Contacts.Intents.Insert.PHONE, "+1234567890");
           intent.putExtra(Contacts.Intents.Insert.PHONE_TYPE,Contacts.PhonesColumns.TYPE_MOBILE);
           intent.putExtra(Contacts.Intents.Insert.EMAIL, "com@com.com");
           intent.putExtra(Contacts.Intents.Insert.EMAIL_TYPE,                    Contacts.ContactMethodsColumns.TYPE_WORK);
           startActivity(intent);
 
 ```
 

 
 
 