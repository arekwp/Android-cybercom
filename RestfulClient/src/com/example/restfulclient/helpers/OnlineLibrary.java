package com.example.restfulclient.helpers;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.xmlpull.v1.XmlPullParserException;

import android.app.ListActivity;

public class OnlineLibrary implements ILibraryDAO
{
    
    ListActivity activ = null;
    
    @Override
    public List<Category> getCategories(String url)
    {
	String webClientUrl = "http://" + url + ":8020/";
	String categoryUrl = "categoryservice/category";
	
	List<Category> list = null;
	
	HttpParams params = new BasicHttpParams();
	
	HttpConnectionParams.setConnectionTimeout(params, 9000);
	HttpConnectionParams.setSoTimeout(params, 9000);
	
	HttpClient hc = new DefaultHttpClient(params);
	
	HttpGet hg = new HttpGet(webClientUrl + categoryUrl);
	try
	{
	    HttpResponse hr = hc.execute(hg);
	    
	    HttpEntity entity = hr.getEntity();
	    
	    if (entity != null)
	    {
		InputStream is = entity.getContent();
		
		Parser parser = new Parser();
		
		try
		{
		    list = parser.getCategories(is);
		} catch (XmlPullParserException e)
		{
		    e.printStackTrace();
		}
		is.close();
	    }
	} catch (ClientProtocolException e)
	{
	    e.printStackTrace();
	} catch (IOException e)
	{
	    System.out.println("service timeout");
	    e.printStackTrace();
	}
	return list;
    }
    
    @Override
    public List<Book> getBooks(String url)
    {
	// TODO Auto-generated method stub
	return null;
    }
    
    @Override
    public void setActivity(ListActivity la)
    {
	activ = la;
    }
    
    @Override
    public void addBook(Book b)
    {
	
    }
    
    @Override
    public void addCategory(Category category)
    {
	
	
    }
    
}
