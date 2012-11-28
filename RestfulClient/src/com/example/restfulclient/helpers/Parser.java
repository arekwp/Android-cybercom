package com.example.restfulclient.helpers;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.util.Log;
import android.util.Xml;

public class Parser
{
    
    // We don't use namespaces
    private static final String ns = null;
    
    public Category parse(InputStream in) throws XmlPullParserException,
	    IOException
    {
	try
	{
	    XmlPullParser parser = Xml.newPullParser();
	    parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
	    parser.setInput(in, null);
	    parser.nextTag();
	    return readFeed(parser);
	} finally
	{
	    in.close();
	}
    }
    
    private Category readFeed(XmlPullParser parser)
	    throws XmlPullParserException, IOException
    {
	
	Category c = new Category();
	
	//parser.require(XmlPullParser.START_TAG, ns, "Category");
	// wymaga
	// glownego
	// tagu o
	// nazwie
	// Category
	c = readCategory(parser);
	return c;
    }
    
    private Category readCategory(XmlPullParser parser)
    {
	String name = "blob";
	String id = null;
	Collection<Book> books = new ArrayList<Book>();
	String pname = null;
	
	try
	{
	    while (parser.next() != XmlPullParser.END_DOCUMENT)
	    {
		pname = parser.getName();
		
		if(parser.getText() != null)
		    Log.d("getText", parser.getText());
		
		Log.d("pos desc", parser.getPositionDescription());
		
		if (parser.getEventType() != XmlPullParser.START_TAG)
		{
		    continue;
		} 
		
		
		
		Log.d("pname: ", pname);
		if (pname.equals("categoryId"))
		{
		    id = readId(parser);
		} else if (pname.equals("categoryName"))
		{
		    name = readName(parser);
		} else
		{
		    books.add(readBook(parser));
		}
	    }
	} catch (XmlPullParserException e)
	{
	    e.printStackTrace();
	} catch (IOException e)
	{
	    e.printStackTrace();
	}
	Log.d("Parser readCat:", id + " " + name + " and " + books.size()
	        + " books");
	return new Category(id, name, books);
    }
    
    private Book readBook(XmlPullParser parser)
    {
	Book book = new Book();
	
	try
	{
	    while (parser.next() != XmlPullParser.END_TAG)
	    {
		if (parser.getEventType() != XmlPullParser.START_TAG)
		{
		    continue;
		}
		
		String name = parser.getName();
		
		if (name.equals("author"))
		{
		    book.setAuthor(readText(parser));
		} else if (name.equals("bookISBNnumber"))
		{
		    book.setBookISBNnumber(readText(parser));
		} else if (name.equals("bookId"))
		{
		    book.setBookId(readText(parser));
		} else if (name.equals("bookName"))
		{
		    book.setBookName(readBookName(parser));
		}
		
	    }
	    
	} catch (XmlPullParserException e)
	{
	    e.printStackTrace();
	} catch (IOException e)
	{
	    e.printStackTrace();
	}
	
	return book;
    }
    
    private String readBookName(XmlPullParser parser) throws IOException,
	    XmlPullParserException
    {
	parser.require(XmlPullParser.START_TAG, ns, "bookName");
	String name = readText(parser);
	parser.require(XmlPullParser.END_TAG, ns, "bookName");
	return name;
    }
    
    private String readName(XmlPullParser parser) throws IOException,
	    XmlPullParserException
    {
	parser.require(XmlPullParser.START_TAG, ns, "categoryName");
	String name = readText(parser);
	parser.require(XmlPullParser.END_TAG, ns, "categoryName");
	return name;
    }
    
    private String readId(XmlPullParser parser) throws IOException,
	    XmlPullParserException
    {
	parser.require(XmlPullParser.START_TAG, ns, "categoryId");
	String id = readText(parser);
	parser.require(XmlPullParser.END_TAG, ns, "categoryId");
	return id;
    }
    
    // For the tags title and summary, extracts their text values.
    private String readText(XmlPullParser parser) throws IOException,
	    XmlPullParserException
    {
	String result = "";
	if (parser.next() == XmlPullParser.TEXT)
	{
	    result = parser.getText();
	    parser.nextTag();
	}
	return result;
    }
}
