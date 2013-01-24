package com.example.restfulclient.helpers;

import java.util.Collection;

public class Category
{
    
    private String categoryId;
    
    private String categoryName;
    
    private Collection<Book> books;
    
    public Category()
    {
	
    }
    
    public Category(String id, String name)
    {
	categoryId = id;
	categoryName = name;
    }
    
    public Category(String id, String name, Collection<Book> books2)
    {
	categoryId = id;
	categoryName = name;
	books = books2;
    }
    
    public Category(Category c)
    {
	this.setCategoryId(c.getCategoryId());
	this.setCategoryName(c.getCategoryName());
    }

    public String getCategoryId()
    {
	return categoryId;
    }
    
    public void setCategoryId(String categoryId)
    {
	this.categoryId = categoryId;
    }
    
    public String getCategoryName()
    {
	return categoryName;
    }
    
    public void setCategoryName(String categoryName)
    {
	this.categoryName = categoryName;
    }
    
    public Collection<Book> getBooks()
    {
	return books;
    }
    
    public void setBooks(Collection<Book> books)
    {
	this.books = books;
    }
    
}
