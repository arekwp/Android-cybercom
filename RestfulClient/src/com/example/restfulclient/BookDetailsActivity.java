package com.example.restfulclient;

import java.io.File;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.restfulclient.helpers.Book;
import com.example.restfulclient.helpers.ILibraryDAO;
import com.example.restfulclient.helpers.MyApplication;
import com.example.restfulclient.helpers.OnlineLibrary;
import com.example.restfulclient.helpers.PhotoHelper;
import com.example.restfulclient.helpers.SQLiteLibrary;

public class BookDetailsActivity extends Activity
{

	private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
	MyApplication myApp;
	boolean editMode = false;
	String mStorage = null;

	Book book = null;

	EditText etId;
	EditText etName;
	EditText etAuthor;
	EditText etISBN;
	ImageView ivPhoto;

    @Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_book_details);
		myApp = (MyApplication) getApplication();

		etId = (EditText) findViewById(R.id.editText_ID);
		etName = (EditText) findViewById(R.id.editText_Nazwa);
		etAuthor = (EditText) findViewById(R.id.editText_Autor);
		etISBN = (EditText) findViewById(R.id.editText_ISBN);
		ivPhoto = (ImageView)findViewById(R.id.ivPhoto);

		if (myApp.b != null)
		{
			editMode = true;

			etId.setText(myApp.b.getBookId());
			etName.setText(myApp.b.getBookName());
			etAuthor.setText(myApp.b.getAuthor());
			etISBN.setText(myApp.b.getBookISBNnumber());

			ivPhoto.setImageBitmap(PhotoHelper.StringToPhoto(myApp.b.getPhoto()));
			
			etId.setEnabled(false);
			
			Button b = (Button)findViewById(R.id.bSend);
			b.setText("Zapisz zmiany");
		}

		mStorage = (Environment.getExternalStorageDirectory() + "/" + "Category Images")
		        .toString();

		if (myApp.b != null)
		{
			
		}

		book = new Book();
	}

	public void b_Click(View v)
	{
		if (v.getId() == R.id.bTakePhoto)
		{
			dispatchTakePictureIntent();
		} else
		{
			new BookThread().execute();
		}
	}

	@Override
	public void finish()
	{
		super.finish();
		myApp.b = null;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
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

			if (myApp.offline)
				library = new SQLiteLibrary(BookDetailsActivity.this);
			else
				library = new OnlineLibrary(myApp.addr);

			book.setAuthor(etAuthor.getText().toString());
			book.setBookId(etId.getText().toString());
			book.setBookName(etName.getText().toString());
			book.setBookISBNnumber(etISBN.getText().toString());
			book.setCatId(myApp.c.getCategoryId());

			if (editMode)
			{
				Log.v("calling", "updateBook");
				library.updateBook(book);
			} else
				library.addBook(book);

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

	private void dispatchTakePictureIntent()
	{
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

		Uri fileUri = getOutputMediaFileUri();

		Log.d("fileuri: ", fileUri.getPath());

		intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);

		startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
	}

	private Uri getOutputMediaFileUri()
	{
		return Uri.fromFile(getOutputPath());
	}

	private File getOutputPath()
	{
		File imgDir = new File(mStorage);

		if (!imgDir.exists())
			if (!imgDir.mkdirs())
				Log.d("Making image dirs failed", "getOutputPath");

		File image = null;

		image = new File(imgDir.getPath() + "/" + "tmp_photo" + ".jpg");

		return image;
	}

	@TargetApi(Build.VERSION_CODES.FROYO)
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE)
		{
			if (resultCode == RESULT_OK)
			{
				String img = PhotoHelper.PhotoToString(mStorage + "/"
				        + "tmp_photo" + ".jpg");
				book.setPhoto(img);
				
				ivPhoto.setImageBitmap(PhotoHelper.StringToPhoto(img));

			} else if (resultCode == RESULT_CANCELED)
			{
				Toast.makeText(this, "Action canceled", Toast.LENGTH_LONG)
				        .show();
			} else
			{
				Toast.makeText(this, "Action failed, check log",
				        Toast.LENGTH_LONG).show();
			}
		}
	}
}
