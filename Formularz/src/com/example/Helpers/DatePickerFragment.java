package com.example.Helpers;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import com.example.formularz.FormDetailsActivity;
import com.example.formularz.MainActivity;

public class DatePickerFragment extends DialogFragment
{
    public static String activity = "MainActivity";
    
    public static int YEAR;
    public static int MONTH;
    public static int DAY;
    
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        if(activity.equals("MainActivity"))
            return new DatePickerDialog(getActivity(), (MainActivity)getActivity(), YEAR, MONTH, DAY);
        else
            return new DatePickerDialog(getActivity(), (FormDetailsActivity)getActivity(), YEAR, MONTH, DAY);
    }
}
