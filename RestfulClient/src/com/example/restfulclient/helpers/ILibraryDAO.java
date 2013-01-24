package com.example.restfulclient.helpers;

import java.util.List;

import android.content.Context;

public interface ILibraryDAO
{

	public List<Category> getCategories(String url);

	public List<Book> getBooks(String url);
	
	public void addBook(Book b);
	
	public void setActivity(Context la);

	public void AddCategory(Category c);
}
