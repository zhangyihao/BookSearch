package com.zyh.booksearch;

import com.zyh.booksearch.entity.BookInfo;

import android.app.Activity;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

public class BookInfoDetailActivity extends Activity {

	private TextView mTitle;
	private ImageView mCover;
	private TextView mAuthor;
	private TextView mPublisher;
	private TextView mPublishDate;
	private TextView mISBN;
	private TextView mSummary;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_book_info_detail);
		
		initViews();
		Parcelable data = getIntent().getParcelableExtra(BookInfo.class.getName());
		initData(data);
	}
	
	private void initViews() {
		mTitle = (TextView) findViewById(R.id.book_detail_title);
		mCover = (ImageView) findViewById(R.id.book_detail_cover);
		mAuthor = (TextView) findViewById(R.id.book_detail_author);
		mPublisher = (TextView) findViewById(R.id.book_detail_publisher);
		mPublishDate = (TextView) findViewById(R.id.book_detail_pubdate);
		mISBN = (TextView) findViewById(R.id.book_detail_isbn);
		mSummary = (TextView) findViewById(R.id.book_detail_summary);
	}

	private void initData(Parcelable data) {
		if(data==null) {
			return;
		}
		
		BookInfo bookInfo = (BookInfo)data;
		mTitle.setText(bookInfo.getTitle());
		mCover.setImageBitmap(bookInfo.getCover());
		mAuthor.setText(bookInfo.getAuthor());
		mPublisher.setText(bookInfo.getPublisher());
		mPublishDate.setText(bookInfo.getPublishDate());
		mISBN.setText(bookInfo.getISBN());
		mSummary.setText(bookInfo.getSummary());
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.book_info_detail, menu);
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
