package com.example.restfulclient;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.example.restfulclient.helpers.Book;
import com.example.restfulclient.helpers.Category;
import com.example.restfulclient.helpers.ILibraryDAO;
import com.example.restfulclient.helpers.MyApplication;
import com.example.restfulclient.helpers.OnlineLibrary;
import com.example.restfulclient.helpers.SQLiteLibrary;


public class BookDetailsActivity extends Activity {

	MyApplication myApp;
    boolean editMode = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_book_details);
		myApp = (MyApplication) getApplication();
	
		if (myApp.b != null)
		{
		    editMode = true;
		    
		    EditText etId = (EditText) findViewById(R.id.editText_ID);
		    EditText etName = (EditText) findViewById(R.id.editText_Nazwa);
		    EditText etAuthor = (EditText) findViewById(R.id.editText_Autor);
		    EditText etISBN = (EditText) findViewById(R.id.editText_ISBN);
		    
		    etId.setText(myApp.b.getBookId());
		    etName.setText(myApp.b.getBookName());
		    etAuthor.setText(myApp.b.getAuthor());
		    etISBN.setText(myApp.b.getBookISBNnumber());
		    
		    etId.setEnabled(false);
		}
	}

    
	public void b_Click(View v)
	{
		
		new BookThread().execute();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_book_details, menu);
		return true;
	}
	
    private class BookThread extends AsyncTask<Void, Void, Void>
    {
	
	@Override
	protected Void doInBackground(Void... params)
	{
	    ILibraryDAO library = null;
	    
	    if(myApp.offline)
		library = new SQLiteLibrary(BookDetailsActivity.this);
	    else
		library = new OnlineLibrary(myApp.addr);
	    
	    EditText etId = (EditText) findViewById(R.id.editText_ID);
	    EditText etName = (EditText) findViewById(R.id.editText_Nazwa);
	    EditText etAuthor = (EditText) findViewById(R.id.editText_Autor);
	    EditText etISBN = (EditText) findViewById(R.id.editText_ISBN);
	    

	    Book b = new Book(etId.getText().toString(), etISBN.getText().toString(),
	    				  etName.getText().toString(),"",myApp.c.getCategoryId().toString(), 
	    				  etAuthor.getText().toString());
	    
	    if (editMode)
	    {
		Log.v("calling", "updateBook");
		library.updateBook(b);
	    } 
	    else
	    	library.addBook(b);

	    
	    return null;
	    
	}
	
	@Override
        protected void onPostExecute(Void result)
	{
	    finish();
	}
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
