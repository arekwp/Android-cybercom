package com.example.restfulclient;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.example.restfulclient.helpers.Book;
import com.example.restfulclient.helpers.ILibraryDAO;
import com.example.restfulclient.helpers.MyApplication;
import com.example.restfulclient.helpers.OnlineLibrary;
import com.example.restfulclient.helpers.SQLiteLibrary;


public class AddBookActivity extends Activity {

	MyApplication myApp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_book);
		myApp = (MyApplication) getApplication();
		
	}

	public void b_Click(View v)
	{
		ILibraryDAO library;
	    if(myApp.offline)
		library = new SQLiteLibrary(AddBookActivity.this);
	    else
		library = new OnlineLibrary(myApp.addr);
	    
	    Book b = new Book();
	    
	    b.setBookId("0");
	    EditText et = (EditText) findViewById(R.id.editText_ISBN);
	    b.setBookISBNnumber(et.getText().toString());
	    
	    et = (EditText) findViewById(R.id.editText_Nazwa);
	    b.setBookName(et.getText().toString());
	    
	 //   b.setAuthor(author);
	    
	    b.setCatId("001");
	    
	    
	    library.addBook(b);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_add_book, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch (item.getItemId())
		{

			default:
				return super.onOptionsItemSelected(item);
		}
	}


}
