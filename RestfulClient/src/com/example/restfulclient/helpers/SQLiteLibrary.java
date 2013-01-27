package com.example.restfulclient.helpers;

import java.util.List;

import android.app.ListActivity;
import android.content.Context;

public class SQLiteLibrary implements ILibraryDAO
{
    
    List<Category> cats = null;
    List<Book> books = null;
    
    Context actv = null;
    
    public SQLiteLibrary(Context mainActivity)
    {
	actv = mainActivity;
    }

    @Override
    public List<Category> getCategories()
    {
	DatabaseHelper dh = new DatabaseHelper(actv);
	
	return dh.getAllCats();
    }
    
    @Override
    public List<Book> getBooks()
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
	DatabaseHelper dh = new DatabaseHelper(actv);
	dh.addBook(b);
    }
    
    @Override
    public void addCategory(Category category)
    {
	DatabaseHelper dh = new DatabaseHelper(actv);
	dh.addCategory(category);
	
    }

    @Override
    public void deleteCategory(Category category, Context applicationContext)
    {
	DatabaseHelper dh = new DatabaseHelper(actv);
	dh.delete(category);
    }
    
}
