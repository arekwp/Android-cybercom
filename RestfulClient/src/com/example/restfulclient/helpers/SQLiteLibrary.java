package com.example.restfulclient.helpers;

import java.util.List;

import android.content.Context;
import android.util.Log;

public class SQLiteLibrary implements ILibraryDAO
{

    List<Category> cats = null;
    List<Book> books = null;

    Context actv = null;
    
    DatabaseHelper dh;
    
    public SQLiteLibrary(Context con)
    {
	actv = con;
	dh = new DatabaseHelper(actv);
    }

    @Override
    public List<Category> getCategories(String url)
    {
	List<Category> cats = dh.getAllCats();
	Log.v("cats null? :", (cats == null ? "null" : "not null"));
	return cats;
    }

    @Override
    public List<Book> getBooks(String url)
    {
	return null;
    }

    @Override
    public void setActivity(Context la)
    {
	actv = la;
    }

    @Override
    public void addBook(Book b)
    {

    }

    @Override
    public void AddCategory(Category c)
    {
	// TODO:
	// dopisanie generowania ID dla kategorii
	dh.addCategory(new Category(c));
    }

}
