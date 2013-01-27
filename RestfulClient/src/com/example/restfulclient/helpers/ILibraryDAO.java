package com.example.restfulclient.helpers;

import java.util.List;

import android.app.ListActivity;
import android.content.Context;

public interface ILibraryDAO
{

	public List<Category> getCategories();

	public List<Book> getBooks();
	
	public void addBook(Book b);
	
	public void setActivity(ListActivity la);

	public void addCategory(Category category);

	public void deleteCategory(Category category, Context applicationContext);
}
