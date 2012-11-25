package com.example.Helpers;


public class ContentHelper
{
    public static String[] GENDERS = new  String[] {"n/a", "m", "f"}; 
    
    
    public static String[] COLOURS = new String[]
	    { "niebieski", "czerwony", "czarny", "¿ó³ty", "zielony" };


    public static int getId(String[] array, String gender)
    {	
	for (int i = 0; i < array.length; i++)
	{
	    if(array[i].equals(gender))
	    {
		return i;
	    }
	}
	
	return -1;
    }
}
