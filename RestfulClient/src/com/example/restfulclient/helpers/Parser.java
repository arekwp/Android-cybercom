package com.example.restfulclient.helpers;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

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
	    
	    //Log.d("starting pname", pname);
	    
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
		    //Log.d("coll", "in");
		    while (parser.next() != XmlPullParser.END_TAG)
		    {
			
			//Log.d("1 parser data catId",
			//        parser.getPositionDescription());
			if (parser.getEventType() != XmlPullParser.START_TAG)
			{
			    continue;
			}
			
			pname = parser.getName();
			
			//Log.d("inner pname: ", pname);
			
			if (pname.equals("categoryId"))
			{
			    id = readId(parser);
			    //Log.d("2 parser data catId",
				 //   parser.getPositionDescription());
			    //Log.d("read: ", id + " cat");
			} else if (pname.equals("categoryName"))
			{
			    name = readName(parser);
			    //Log.d("3 parser data catName",
				//    parser.getPositionDescription());
			    //Log.d("read: ", name + " cat");
			}
		    }
		    if (name != null && id != null)
		    {
			//Log.d("adding: ", id + " " + name);
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
	//Log.d("returned: ", cats.size() + " categories");
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
	    while(parser.next() != XmlPullParser.END_DOCUMENT)
	    {
		if (parser.getEventType() != XmlPullParser.START_TAG)
		{
		    continue;
		}
		
		String pname = parser.getName();
		if(pname.equals("books"))
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
