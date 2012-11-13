package com.example.Helpers;

import java.util.Calendar;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import com.example.formularz.MainActivity;

public class DatePickerFragment extends DialogFragment
{
    public static String activity = "MainActivity";
    
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        
        

        // Create a new instance of DatePickerDialog and return it
        if(activity.equals("MainActivity"))
            return new DatePickerDialog(getActivity(), (MainActivity)getActivity(), year, month, day);
        else
            return new DatePickerDialog(getActivity(), (MainActivity)getActivity(), year, month, day);
    }
}
