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
				/*��ȡdownload ��service*/
				String serviceString = Context.DOWNLOAD_SERVICE;
				DownloadManager downloadManager;
				downloadManager = (DownloadManager) getSystemService(serviceString);

				Uri uri = Uri
						.parse("http://apps.wandoujia.com/redirect?signature=2be42d0&url=http%3A%2F%"
								+ "2Fapk.wandoujia.com%2F1%2F31%2F409a57aea589d817a0c5fe353bfb0311.apk&pn=com.tenc"
								+ "ent.mm&md5=409a57aea589d817a0c5fe353bfb0311&apkid=12115131&vc=520&size=30996365"
								+ "&pos=t/top/weeklytopapp&appType=APP"); // ������д��Ҫ���ص���Դ�ĵ�ַ(��������д����΢��)
				DownloadManager.Request request = new Request(uri);
				/*����д�ж���д�ļ���*/
				isFolderExist("Downloadmanager");
				/*�������ػ�����Ϣ,DownloadmanagerΪ��ŵ�url�������<���ص�΢��.apk> �������ļ��洢���ļ���*/
				request.setDestinationInExternalPublicDir("Downloadmanager", "���ص�΢��.apk");
				
				request.setTitle("���ص�΢��");//����������֪ͨ����ʾ�ı���
				request.setDescription("΢�� introduce");//����������֪ͨ����ʾ�Ľ���
				request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
				//��ʾ���ؽ����к�������ɵ�֪ͨ���Ƿ���ʾ��Ĭ��ֻ��ʾ������֪ͨ��
				
				long reference = downloadManager.enqueue(request);
			
				
				Toast.makeText(MainActivity.this, "��ʼ����", Toast.LENGTH_LONG);
				

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
