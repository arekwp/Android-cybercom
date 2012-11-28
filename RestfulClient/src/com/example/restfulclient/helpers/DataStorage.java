package com.example.restfulclient.helpers;

import java.util.ArrayList;
import java.util.List;

public class DataStorage
{
    static DataStorage instance = null;
    
    private DataStorage()
    {
	categories = new ArrayList<Category>();
    }
    
    public static DataStorage getInstance()
    {
	if (instance == null)
	    return new DataStorage();
	else
	    return instance;
    }
    
    public List<Category> categories;
    
}
