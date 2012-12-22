package com.example.restfulclient.helpers;

import android.graphics.Bitmap;

public class Book {

	private String bookId;
	
	private String bookISBNnumber;
	
	private String bookName;
	
	private Bitmap photo;
	
	private String catId;
	
	//Let assume one author only
	private String author;

	public String getBookId() {
		return bookId;
	}

	public void setBookId(String bookId) {
		this.bookId = bookId;
	}

	public String getBookISBNnumber() {
		return bookISBNnumber;
	}

	public void setBookISBNnumber(String bookISBNnumber) {
		this.bookISBNnumber = bookISBNnumber;
	}

	public String getBookName() {
		return bookName;
	}

	public void setBookName(String bookName) {
		this.bookName = bookName;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public Bitmap getPhoto()
    {
	    return photo;
    }

	public void setPhoto(Bitmap photo)
    {
	    this.photo = photo;
    }

	public String getCatId()
    {
	    return catId;
    }

	public void setCatId(String catId)
    {
	    this.catId = catId;
    }
	
}
