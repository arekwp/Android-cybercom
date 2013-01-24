package com.example.restfulclient.helpers;

import java.util.List;

import android.app.ListActivity;

public class SQLiteLibrary implements ILibraryDAO
{

	List<Category> cats = null;
	List<Book> books = null;
	
	ListActivity actv = null;

	@Override
    public List<Category> getCategories(String url)
    {
		DatabaseHelper dh = new DatabaseHelper(actv);

		return dh.getAllCats();	    
    }

	@Override
    public List<Book> getBooks(String url)
    {
	    return null;
    }

	@Override
    public void setActivity(ListActivity la)
    {
	    actv = la;
    }
	
	@Override
    public void addBook(Book b)
    {
	   
    }

	
	
}
