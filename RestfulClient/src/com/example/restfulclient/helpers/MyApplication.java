package com.example.restfulclient.helpers;

import java.util.ArrayList;
import java.util.List;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class MyApplication extends Application
{
    public List<Category> categories;
    
    public Category c;
    public Book b;
    
    public String addr = "192.168.1.1"; // telefon
//    public String addr = "10.0.2.2"; // emulator
    
    public boolean offline = false;
    
    public MyApplication()
    {
	categories = new ArrayList<Category>();
    }
    
    public int getById(String id)
    {
	for (int i = 0; i < categories.size(); i++)
	{
	    if (categories.get(i).getCategoryId().equals(id))
		return i;
	}
	
	return -1;
    }
    
    public boolean isOffline()
    {
	ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
	NetworkInfo netInfo = cm.getActiveNetworkInfo();
	if (netInfo != null && netInfo.isConnectedOrConnecting())
	    offline = false;
	else
	    offline = true;
	return offline;
    }
}
