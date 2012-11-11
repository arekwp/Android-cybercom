package com.example.formularz;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class FormListActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_list);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_form_list, menu);
        return true;
    }
}
