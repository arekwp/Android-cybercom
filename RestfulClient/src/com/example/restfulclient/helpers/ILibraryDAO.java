package com.example.restfulclient.helpers;

import java.util.List;

import android.app.ListActivity;

public interface ILibraryDAO
{

	public List<Category> getCategories(String url);

	public List<Book> getBooks(String url);
	
	public void setActivity(ListActivity la);
}
