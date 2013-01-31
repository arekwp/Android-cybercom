package com.example.restfulclient.helpers;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

import android.util.Xml;

public class Parser
{
    
    // We don't use namespaces
    private static final String ns = null;
    
    public List<Category> getCategories(InputStream in)
	    throws XmlPullParserException, IOException
    {
	try
	{
	    XmlPullParser parser = Xml.newPullParser();
	    parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
	    parser.setInput(in, null);
	    parser.nextTag();
	    return readCategories(parser);
	} finally
	{
	    in.close();
	}
    }
    
    private List<Category> readCategories(XmlPullParser parser)
    {
	String name = "blob";
	String id = null;
	Collection<Book> books = null;
	String pname = null;
	
	List<Category> cats = new ArrayList<Category>();
	
	try
	{
	    parser.next(); // into cats
	    pname = parser.getName();
	    
	    // Log.d("starting pname", pname);
	    
	    while (parser.next() != XmlPullParser.END_DOCUMENT)
	    {
		if (parser.getEventType() != XmlPullParser.START_TAG)
		{
		    continue;
		}
		
		pname = parser.getName();
		
		id = null;
		name = null;
		books = null;
		
		if (pname.equals("coll"))
		{
		    // Log.d("coll", "in");
		    while (parser.next() != XmlPullParser.END_TAG)
		    {
			
			// Log.d("1 parser data catId",
			// parser.getPositionDescription());
			if (parser.getEventType() != XmlPullParser.START_TAG)
			{
			    continue;
			}
			
			pname = parser.getName();
			
			// Log.d("inner pname: ", pname);
			
			if (pname.equals("categoryId"))
			{
			    id = readId(parser);
			    // Log.d("2 parser data catId",
			    // parser.getPositionDescription());
			    // Log.d("read: ", id + " cat");
			} else if (pname.equals("categoryName"))
			{
			    name = readName(parser);
			    // Log.d("3 parser data catName",
			    // parser.getPositionDescription());
			    // Log.d("read: ", name + " cat");
			}
		    }
		    if (name != null && id != null)
		    {
			// Log.d("adding: ", id + " " + name);
			cats.add(new Category(id, name, books));
		    }
		}
	    }
	    
	} catch (XmlPullParserException e)
	{
	    e.printStackTrace();
	} catch (IOException e)
	{
	    e.printStackTrace();
	}
	// Log.d("returned: ", cats.size() + " categories");
	return cats;
    }
    
    public List<Book> getBooks(InputStream in)
    {
	try
	{
	    XmlPullParser parser = Xml.newPullParser();
	    parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
	    parser.setInput(in, null);
	    parser.nextTag();
	    return readBooks(parser);
	} catch (XmlPullParserException e)
	{
	    e.printStackTrace();
	} catch (IOException e)
	{
	    e.printStackTrace();
	} finally
	{
	    try
	    {
		in.close();
	    } catch (IOException e)
	    {
		e.printStackTrace();
	    }
	}
	return null;
    }
    
    public List<Book> readBooks(XmlPullParser parser)
    {
	List<Book> list = new ArrayList<Book>();
	
	try
	{
	    while (parser.next() != XmlPullParser.END_DOCUMENT)
	    {
		if (parser.getEventType() != XmlPullParser.START_TAG)
		{
		    continue;
		}
		
		String pname = parser.getName();
		if (pname.equals("books"))
		    list.add(readBook(parser));
	    }
	} catch (XmlPullParserException e)
	{
	    e.printStackTrace();
	} catch (IOException e)
	{
	    e.printStackTrace();
	}
	
	return list;
    }
    
    public static String getXml(Category c) throws IllegalArgumentException,
	    IllegalStateException, IOException
    {
	XmlSerializer serializer = Xml.newSerializer();
	StringWriter sw = new StringWriter();
	
	serializer.setOutput(sw);
	
	serializer.startDocument("UTF-8", true);
	
	serializer.startTag("", "Category");
	
	serializer.startTag("", "categoryId");
	serializer.text(c.getCategoryId());
	serializer.endTag("", "categoryId");
	
	serializer.startTag("", "categoryName");
	serializer.text(c.getCategoryName());
	serializer.endTag("", "categoryName");
	
	serializer.endTag("", "Category");
	
	serializer.endDocument();
	
	return sw.toString();
    }
   
    public static String getXml(Book b) throws IllegalArgumentException,
    IllegalStateException, IOException
    {
    XmlSerializer serializer = Xml.newSerializer();
    StringWriter sw = new StringWriter();

    serializer.setOutput(sw);

    serializer.startDocument("UTF-8", true);

    serializer.startTag("", "Book");

    serializer.startTag("", "author");
    serializer.text(b.getAuthor());
    serializer.endTag("", "author");

    serializer.startTag("", "bookISBNnumber");
    serializer.text(b.getBookISBNnumber());
    serializer.endTag("", "bookISBNnumber");

    serializer.startTag("", "bookId");
    serializer.text(b.getBookId());
    serializer.endTag("", "bookId");

    serializer.startTag("", "bookName");
    serializer.text(b.getBookName());
    serializer.endTag("", "bookName");
    
    serializer.startTag("", "bookPhoto");
    serializer.text(b.getPhoto() != null && b.getPhoto().length() > 10 ? b.getPhoto() : "" );
    serializer.endTag("", "bookPhoto");

    serializer.endTag("", "Book");

    serializer.endDocument();

    return sw.toString();
    }
    
    public static String getXml(List<Book> lb) throws IllegalArgumentException,
	    IllegalStateException, IOException
    {
	
	if(lb == null)
	    throw new NullPointerException("List<Book> is a null");
	
	if(lb.size() == 0)
	    throw new IllegalArgumentException("List<Book> is empty");
	
	XmlSerializer serializer = Xml.newSerializer();
	StringWriter sw = new StringWriter();
	
	serializer.setOutput(sw);
	
	serializer.startDocument("UTF-8", true);
	
	serializer.startTag("", "Category");
	
	serializer.startTag("", "categoryId");
	serializer.text(lb.get(0).getCatId());
	serializer.endTag("", "categoryId");
	
	serializer.startTag("", "categoryName");
	serializer.endTag("", "categoryName");
	
	for (Book b : lb)
	{
	    serializer.startDocument("UTF-8", true);
	    
	    serializer.startTag("", "Book");
	    
	    serializer.startTag("", "bookId");
	    serializer.text(b.getBookId());
	    serializer.endTag("", "bookId");
	    
	    serializer.startTag("", "bookName");
	    serializer.text(b.getBookName());
	    serializer.endTag("", "bookName");
	    
	    serializer.startTag("", "author");
	    serializer.text(b.getAuthor());
	    serializer.endTag("", "author");
	    
	    serializer.startTag("", "bookISBNnumber");
	    serializer.text(b.getBookISBNnumber());
	    serializer.endTag("", "bookISBNnumber");
	    
	    serializer.endTag("", "Book");
	}
	
	serializer.endTag("", "Category");
	
	serializer.endDocument();
	
	return sw.toString();
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
		}else if (name.equals("bookPhoto"))
		{
		    book.setPhoto(readBookPhoto(parser));
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
    
    private String readBookPhoto(XmlPullParser parser) throws XmlPullParserException, IOException
    {
	parser.require(XmlPullParser.START_TAG, ns, "bookPhoto");
	String name = readText(parser);
	parser.require(XmlPullParser.END_TAG, ns, "bookPhoto");
	return name;
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
