package com.example.restfulclient.helpers;

import java.util.List;

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
	public List<Book> getBooks(String catID)
	{
		DatabaseHelper dh = new DatabaseHelper(actv);
		return dh.getBooksByCid(catID);
	}

	@Override
	public void setActivity(Context la)
	{
		actv = la;
	}

	@Override
	public void addCategory(Category category)
	{
		DatabaseHelper dh = new DatabaseHelper(actv);
		dh.addCategory(category);

	}

	@Override
	public void deleteCategory(Category category)
	{
		DatabaseHelper dh = new DatabaseHelper(actv);
		dh.delete(category);
	}

	@Override
	public void updateCategory(Category category)
	{
		DatabaseHelper dh = new DatabaseHelper(actv);
		dh.update(category);

	}

	@Override
	public List<Category> getCatsAndBooks()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addBook(Book book)
	{
		DatabaseHelper dh = new DatabaseHelper(actv);
		dh.addBook(book);
	}

	@Override
	public void updateBook(Book book)
	{
		DatabaseHelper dh = new DatabaseHelper(actv);
		dh.update(book);
	}

	@Override
	public void deleteBook(Book book)
	{
		DatabaseHelper dh = new DatabaseHelper(actv);
		dh.delete(book);
	}
}
