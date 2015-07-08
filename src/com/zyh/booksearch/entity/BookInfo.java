package com.zyh.booksearch.entity;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

public class BookInfo implements Parcelable {
	private String mTitle = ""; // 书名
	private Bitmap mCover; // 封面
	private String mAuthor = ""; // 作者
	private String mPublisher = ""; // 出版社
	private String mPublishDate = ""; // 出版时间
	private String mISBN = ""; // ISBN
	private String mSummary = ""; // 内容介绍
	
	public static Parcelable.Creator<BookInfo> CREATOR = new Creator<BookInfo>() {

		@Override
		public BookInfo createFromParcel(Parcel source) {
			BookInfo book = new BookInfo();
			book.mTitle = source.readString();
			book.mCover = source.readParcelable(Bitmap.class.getClassLoader());
			book.mAuthor = source.readString();
			book.mPublisher = source.readString();
			book.mPublishDate = source.readString();
			book.mISBN = source.readString();
			book.mSummary = source.readString();
			return book;
		}

		@Override
		public BookInfo[] newArray(int size) {
			return new BookInfo[size];
		}
	};
	
	@Override
	public int describeContents() {
		return 0;
	}
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(mTitle);
		dest.writeParcelable(mCover, flags);
		dest.writeString(mAuthor);
		dest.writeString(mPublisher);
		dest.writeString(mPublishDate);
		dest.writeString(mISBN);
		dest.writeString(mSummary);
	}
	
	public String getmTitle() {
		return mTitle;
	}
	public void setmTitle(String mTitle) {
		this.mTitle = mTitle;
	}
	public Bitmap getmCover() {
		return mCover;
	}
	public void setmCover(Bitmap mCover) {
		this.mCover = mCover;
	}
	public String getmAuthor() {
		return mAuthor;
	}
	public void setmAuthor(String mAuthor) {
		this.mAuthor = mAuthor;
	}
	public String getmPublisher() {
		return mPublisher;
	}
	public void setmPublisher(String mPublisher) {
		this.mPublisher = mPublisher;
	}
	public String getmPublishDate() {
		return mPublishDate;
	}
	public void setmPublishDate(String mPublishDate) {
		this.mPublishDate = mPublishDate;
	}
	public String getmISBN() {
		return mISBN;
	}
	public void setmISBN(String mISBN) {
		this.mISBN = mISBN;
	}
	public String getmSummary() {
		return mSummary;
	}
	public void setmSummary(String mSummary) {
		this.mSummary = mSummary;
	}
}
