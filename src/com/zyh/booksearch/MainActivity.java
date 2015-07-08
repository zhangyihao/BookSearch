package com.zyh.booksearch;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.zyh.booksearch.utils.BookUtil;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {
	
	private Button scanBtn;
	private ProgressDialog dialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		initViews();
	}

	private void initViews() {
		scanBtn = (Button)findViewById(R.id.main_start_scan);
		scanBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startScan();
			}
		});
	}

	/**
     * 通过Intent启动第三方应用"ZXing"进行图书条形码扫描
     */
	private void startScan() {
		IntentIntegrator intentIntegrator = new IntentIntegrator(MainActivity.this);
		intentIntegrator.initiateScan();
	}
	
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
		if(result == null || result.getContents()==null) {
			Log.i(BookUtil.TAG, "用户取消了扫描！");
			return;
		}
		
		dialog = new ProgressDialog(MainActivity.this);
		dialog.setMessage(getString(R.string.communicating));
		dialog.show();
		
		DownloadThread thread = new DownloadThread("");
		thread.start();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	private class DownloadThread extends Thread {

		private String url;
		
		public DownloadThread(String url) {
			super();
			this.url = url;
		}
		
		@Override
		public void run() {
		}
		
	}
}
