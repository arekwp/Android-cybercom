package com.example.restfulclient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity implements OnClickListener
{
    static EditText etData;
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_main);
	
	Button button = (Button) findViewById(R.id.bSend);
	
	button.setOnClickListener(this);
	
	etData = (EditText) findViewById(R.id.etData);
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
	// Inflate the menu; this adds items to the action bar if it is present.
	getMenuInflater().inflate(R.menu.activity_main, menu);
	return true;
    }
    
    @Override
    public void onClick(View v)
    {
	Log.d("Click", "uruchamiam nowy watek");
	new LongRunningIO().execute("192.168.1.2");
    }
    
    static class LongRunningIO extends AsyncTask<String, String, String>
    {
	protected String doInBackground(String... url)
	{
	    String data="BLOB";
	    String webClientUrl = "http://" + url[0] + ":8020/";
	    String categoryUrl = "categoryservice/category/001";
	    
	    HttpClient hc = new DefaultHttpClient();
	    HttpGet hg = new HttpGet(webClientUrl + categoryUrl);
	    try
	    {
		HttpResponse hr = hc.execute(hg);
		
		HttpEntity entity = hr.getEntity();
		
		if (entity != null)
		{
		    InputStream is = entity.getContent();
		    data = toString(is);
		    
		    is.close();
		}
	    } catch (ClientProtocolException e)
	    {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    } catch (IOException e)
	    {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }
	    Log.d("returned: ", data);
	    return data;
	}
	
	private static String toString(InputStream is)
	{
	    /*
	     * To convert the InputStream to String we use the
	     * BufferedReader.readLine() method. We iterate until the
	     * BufferedReader return null which means there's no more data to
	     * read. Each line will appended to a StringBuilder and returned as
	     * String.
	     */
	    BufferedReader reader = new BufferedReader(
		    new InputStreamReader(is));
	    StringBuilder sb = new StringBuilder();
	    
	    String line = null;
	    try
	    {
		while ((line = reader.readLine()) != null)
		{
		    sb.append(line + "\n");
		}
	    } catch (IOException e)
	    {
		e.printStackTrace();
	    } finally
	    {
		try
		{
		    is.close();
		} catch (IOException e)
		{
		    e.printStackTrace();
		}
	    }
	    return sb.toString();
	}
	
	protected void onPostExecute(String result)
	{
	    etData.setText(result);
	}
    }
}
