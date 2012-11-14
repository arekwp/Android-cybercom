package com.example.Helpers;

import java.util.Calendar;

public class DateHelper
{
    public static boolean isNoFuture(int d, int m, int y)
    {
	Calendar c = Calendar.getInstance();
	
	if(y < c.get(Calendar.YEAR))
	{
	    return true;
	}
	else if (y == c.get(Calendar.YEAR))
	{
	    if(m < c.get(Calendar.MONTH))
	    {
		return true;
	    }
	    else if (m == c.get(Calendar.MONTH))
	    {
		if(d <= c.get(Calendar.DAY_OF_MONTH))
		    return true;
	    }
	}
	return false;
    }
}
