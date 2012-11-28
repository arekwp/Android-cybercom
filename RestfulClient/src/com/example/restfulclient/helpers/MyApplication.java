package com.example.restfulclient.helpers;

import java.util.ArrayList;
import java.util.List;

import android.app.Application;
import android.util.Log;

public class MyApplication extends Application
{
    public List<Category> categories;
    
    public MyApplication()
    {
	categories = new ArrayList<Category>();
    }
    
    public void ShowCategories()
    {
	for(int i =0; i < categories.size(); i++)
	{
	    Log.v(categories.get(i).getCategoryId(), categories.get(i).getCategoryName());
	}
    }
}
