package com.example.downloadmanager;

import java.io.File;

import android.app.Activity;
import android.app.DownloadManager;
import android.app.DownloadManager.Request;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {
	private Button startDownload;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		startDownload = (Button) findViewById(R.id.startDownload);
		startDownload.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				/*获取download 的service*/
				String serviceString = Context.DOWNLOAD_SERVICE;
				DownloadManager downloadManager;
				downloadManager = (DownloadManager) getSystemService(serviceString);

				Uri uri = Uri
						.parse("http://apps.wandoujia.com/redirect?signature=2be42d0&url=http%3A%2F%"
								+ "2Fapk.wandoujia.com%2F1%2F31%2F409a57aea589d817a0c5fe353bfb0311.apk&pn=com.tenc"
								+ "ent.mm&md5=409a57aea589d817a0c5fe353bfb0311&apkid=12115131&vc=520&size=30996365"
								+ "&pos=t/top/weeklytopapp&appType=APP"); // 这里填写你要下载的资源的地址(我这里填写的是微信)
				DownloadManager.Request request = new Request(uri);
				/*先书写判断书写文件夹*/
				isFolderExist("Downloadmanager");
				/*设置下载基础信息,Downloadmanager为存放的url，后面的<下载的微信.apk> 是下载文件存储的文件名*/
				request.setDestinationInExternalPublicDir("Downloadmanager", "下载的微信.apk");
				
				request.setTitle("下载的微信");//设置下载中通知栏提示的标题
				request.setDescription("微信 introduce");//设置下载中通知栏提示的介绍
				request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
				//表示下载进行中和下载完成的通知栏是否显示。默认只显示下载中通知。
				
				long reference = downloadManager.enqueue(request);
			
				
				Toast.makeText(MainActivity.this, "开始下载", Toast.LENGTH_LONG);
				

			}
		});
	}
	
	private boolean isFolderExist(String dir) {
		File folder = Environment.getExternalStoragePublicDirectory(dir);
		return (folder.exists() && folder.isDirectory()) ? true : folder.mkdirs();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
