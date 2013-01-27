package com.example.restfulclient.helpers;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.xmlpull.v1.XmlPullParserException;

import android.app.ListActivity;
import android.util.Log;

public class OnlineLibrary implements ILibraryDAO
{
    
    ListActivity activ = null;
    String webClientUrl = "";
    String categoryUrl = "categoryservice/category";
    
    public OnlineLibrary(String url)
    {
	webClientUrl = "http://" + url + ":8020/";
    }
    
    @Override
    public List<Category> getCategories()
    {
	
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
    public List<Book> getBooks()
    {
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
	HttpParams params = new BasicHttpParams();
	
	HttpConnectionParams.setConnectionTimeout(params, 9000);
	HttpConnectionParams.setSoTimeout(params, 9000);
	
	HttpClient hc = new DefaultHttpClient(params);
	
	HttpPost post = new HttpPost(webClientUrl + categoryUrl);
	
	// Add your data
	
	try
	{
	    StringEntity entity = new StringEntity(Parser.getXml(category),
		    "UTF-8");
	    entity.setContentType("application/xml");
	    post.setEntity(entity);
	    
	    // Execute HTTP Post Request
	    // HttpResponse response = hc.execute(post);
	    hc.execute(post);
	    
	} catch (UnsupportedEncodingException e)
	{
	    e.printStackTrace();
	} catch (ClientProtocolException e)
	{
	    e.printStackTrace();
	} catch (IOException e)
	{
	    e.printStackTrace();
	}
	
    }
    
    @Override
    public void deleteCategory(Category category)
    {
	HttpParams params = new BasicHttpParams();
	
	HttpConnectionParams.setConnectionTimeout(params, 9000);
	HttpConnectionParams.setSoTimeout(params, 9000);
	
	HttpClient hc = new DefaultHttpClient(params);
	
	try
	{
	    String encodedID = URLEncoder.encode(category.getCategoryId(),
		    "UTF-8");
	    Log.v("encodedID", encodedID);
	    HttpDelete delete = new HttpDelete(webClientUrl + categoryUrl + "/"
		    + encodedID);
	    delete.setHeader("Content-Type", "application/xml");
	    
	    hc.execute(delete);
	} catch (ClientProtocolException e)
	{
	    e.printStackTrace();
	} catch (IOException e)
	{
	    e.printStackTrace();
	}
    }
    
    @Override
    public void updateCategory(Category category)
    {
	HttpParams params = new BasicHttpParams();
	
	HttpConnectionParams.setConnectionTimeout(params, 9000);
	HttpConnectionParams.setSoTimeout(params, 9000);
	
	HttpClient hc = new DefaultHttpClient(params);
	
	try
	{
	    String encodedID = URLEncoder.encode(category.getCategoryId(),
		    "UTF-8");
	    Log.v("encodedID", encodedID);
	    HttpPut put = new HttpPut(webClientUrl + categoryUrl);
	    put.setHeader("Content-Type", "application/xml");
	    StringEntity entity = new StringEntity(Parser.getXml(category), "UTF-8");
	    put.setEntity(entity);
	    
	    hc.execute(put);
	} catch (ClientProtocolException e)
	{
	    e.printStackTrace();
	} catch (IOException e)
	{
	    e.printStackTrace();
	}
    }
}
