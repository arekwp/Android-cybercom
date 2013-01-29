package com.example.restfulclient.helpers;

public class Book
{
	public Book(){}
    
	public Book (String id, String isbn, String name, String photo,  String catid, String bauthor)
	{
	    bookId = id;    
	    bookISBNnumber = isbn;    
	    bookName = name;
	    bookPhoto = photo;
	    catId = catid;
	    author = bauthor;
	}
	
    private String bookId;
    
    private String bookISBNnumber;
    
    private String bookName;
    
    private String bookPhoto;
    
    private String catId;
    
    private String author;
    
    
    public String getBookId()
    {
	return bookId;
    }
    
    public void setBookId(String bookId)
    {
	this.bookId = bookId;
    }
    
    public String getBookISBNnumber()
    {
	return bookISBNnumber;
    }
    
    public void setBookISBNnumber(String bookISBNnumber)
    {
	this.bookISBNnumber = bookISBNnumber;
    }
    
    public String getBookName()
    {
	return bookName;
    }
    
    public void setBookName(String bookName)
    {
	this.bookName = bookName;
    }
    
    public String getAuthor()
    {
	return author;
    }
    
    public void setAuthor(String author)
    {
	this.author = author;
    }
    
    public String getPhoto()
    {
	return bookPhoto;
    }
    
    public void setPhoto(String photo)
    {
	this.bookPhoto = photo;
    }
    
    public String getCatId()
    {
	return catId;
    }
    
    public void setCatId(String catId)
    {
	this.catId = catId;
    }
    
    @Override
    public boolean equals(Object o)
    {
	Book b2 = (Book) o;
	if (bookId.equals(b2.bookId))
	{
	    if (bookName.equals(b2.bookName))
	    {
		if (bookISBNnumber.equals(b2.bookISBNnumber))
		{
		    if (author.equals(b2.author))
		    {
			if (bookPhoto.equals(b2.bookPhoto))
			{
			    return true;
			}
		    }
		}
	    }
	}
	return false;
    }
    
    @Override
    public int hashCode()
    {
	int hash = 1;
	hash = hash * 11 + bookId.hashCode();
	hash = hash * 13 + bookName.hashCode();
	hash = hash * 17 + author.hashCode();
	hash = hash * 23 + bookISBNnumber.hashCode();
	hash = hash * 29 + bookPhoto.hashCode();
	return hash;
    }
}
