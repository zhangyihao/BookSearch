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
	
	public String getTitle() {
		return mTitle;
	}
	public void setTitle(String mTitle) {
		this.mTitle = mTitle;
	}
	public Bitmap getCover() {
		return mCover;
	}
	public void setCover(Bitmap mCover) {
		this.mCover = mCover;
	}
	public String getAuthor() {
		return mAuthor;
	}
	public void setAuthor(String mAuthor) {
		this.mAuthor = mAuthor;
	}
	public String getPublisher() {
		return mPublisher;
	}
	public void setPublisher(String mPublisher) {
		this.mPublisher = mPublisher;
	}
	public String getPublishDate() {
		return mPublishDate;
	}
	public void setPublishDate(String mPublishDate) {
		this.mPublishDate = mPublishDate;
	}
	public String getISBN() {
		return mISBN;
	}
	public void setISBN(String mISBN) {
		this.mISBN = mISBN;
	}
	public String getSummary() {
		return mSummary;
	}
	public void setSummary(String mSummary) {
		this.mSummary = mSummary;
	}
}
