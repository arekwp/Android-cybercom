package com.example.restfulclient;

import java.io.File;
import java.util.ArrayList;

import android.annotation.TargetApi;
import android.app.ListActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.restfulclient.helpers.Book;
import com.example.restfulclient.helpers.Category;
import com.example.restfulclient.helpers.ILibraryDAO;
import com.example.restfulclient.helpers.MyApplication;
import com.example.restfulclient.helpers.OnlineLibrary;
import com.example.restfulclient.helpers.PhotoHelper;
import com.example.restfulclient.helpers.SQLiteLibrary;

public class BooksListActivity extends ListActivity
{

	private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
	MyApplication myApp = null;
	Category cat = null;
	String mStorage = null;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_books_list);

		myApp = (MyApplication) getApplication();

		ListView lv = (ListView) findViewById(android.R.id.list);
		registerForContextMenu(lv);

		cat = myApp.c;
		cat.setBooks(new ArrayList<Book>());

		new GetBooksThread(myApp.c).execute(myApp.addr);

		Log.d("odebrano id: ", myApp.c.getCategoryId());
		Log.d("wybrano kat: ", cat.getCategoryId());
		mStorage = (Environment.getExternalStorageDirectory() + "/" + "Category Images")
		        .toString();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		// Handle the back button
		if (keyCode == KeyEvent.KEYCODE_BACK)
		{
			finish();

			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_books_list, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch (item.getItemId())
		{
			case R.id.add_book:
				Intent intent = new Intent(BooksListActivity.this,
				        BookDetailsActivity.class);
				BooksListActivity.this.startActivity(intent);
				return true;

			default:
				return super.onOptionsItemSelected(item);
		}
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id)
	{
		// super.onListItemClick(l, v, position, id);
		Book selection = (Book) l.getItemAtPosition(position);
		myApp.b = selection;
		Log.d("kliknieto: ", selection.getBookName());
	}

	class GetBooksThread extends AsyncTask<String, Void, Category>
	{
		Category c;

		public GetBooksThread(Category cin)
		{
			super();
			c = cin;
		}

		@Override
		protected Category doInBackground(String... url)
		{
			ILibraryDAO library = null;
			if (myApp.isOffline())
			{
				library = new SQLiteLibrary(getApplicationContext());
			} else
			{
				library = new OnlineLibrary(myApp.addr);
			}
			c.setBooks(library.getBooks(myApp.c.getCategoryId()));

			return c;
		}

		@Override
		protected void onPostExecute(Category cat)
		{
			Log.d("added books: ", String.valueOf(cat.getBooks().size()));
			setCategoryWithBooks(cat);
		}
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
	        ContextMenuInfo menuInfo)
	{
		super.onCreateContextMenu(menu, v, menuInfo);
		menu.setHeaderTitle("Ksi��ki");
		menu.add(0, v.getId(), 0, "Edytuj");
		menu.add(0, v.getId(), 0, "Usu�");
	}

	@Override
	public boolean onContextItemSelected(MenuItem item)
	{
		AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item
		        .getMenuInfo();
		myApp.b = myApp.books.get(info.position);
		myApp.b.setCatId(myApp.c.getCategoryId());

		Log.d("kliknieto: ", myApp.b.getBookName());
		if (item.getTitle() == "Edytuj")
		{
			Intent intent = new Intent(BooksListActivity.this,
			        BookDetailsActivity.class);

			BooksListActivity.this.startActivity(intent);

		} else if (item.getTitle() == "Usu�")
		{

			new DeleteBookThread().execute(myApp.addr);
		} else
		{
			myApp.b = null;
			return false;
		}
		return true;
	}

	class DeleteBookThread extends AsyncTask<String, Void, Void>
	{
		ILibraryDAO library;

		@Override
		protected Void doInBackground(String... url)
		{
			if (myApp.isOffline())
				library = new SQLiteLibrary(BooksListActivity.this);
			else
				library = new OnlineLibrary(url[0]);

			library.deleteBook(myApp.b);
			return null;
		}

		@Override
		protected void onPostExecute(Void result)
		{
			new GetBooksThread(myApp.c).execute(myApp.addr);
			Log.v("AsyncTask", "setting result");
		}
	}

	public void setCategoryWithBooks(Category cat)
	{
		myApp.c = cat;

		// myApp.books= cat.getBooks();

		for (Book b : cat.getBooks())
		{
			myApp.books.add(b);
		}

		if (myApp.c.getBooks().size() == 0)
		{
			Toast.makeText(this, "Brak ksi��ek w kategorii", Toast.LENGTH_LONG)
			        .show();
		}

		setListAdapter(new BookAdapter());

	}

	// wywo�anie tej metody spowoduje uruchoimienie activity do robienia zdj��
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

		File image = new File(imgDir.getPath() + "/" + myApp.b.getBookName()
		        + ".jpg");

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

				String img = PhotoHelper.PhotoToString(
				        mStorage + "/" + myApp.b.getBookName() + ".jpg",
				        myApp.b);

				setListAdapter(new BookAdapter());

				// Image captured and saved to fileUri specified in the
				// Intent
				Toast.makeText(this, "Image saved", Toast.LENGTH_LONG).show();

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

	@Override
	public void finish()
	{
		super.finish();
		myApp.b = null;
		myApp.c = null;
	}

	private class BookAdapter extends BaseAdapter
	{
		@Override
		public View getView(int pos, View view, ViewGroup parent)
		{
			LayoutInflater layoutInflayer = getLayoutInflater();
			ViewHolder vHolder = null;

			if (view == null)
			{
				view = layoutInflayer.inflate(R.layout.book_row_layout, null,
				        false);
			}

			vHolder = new ViewHolder();

			Book book = (Book) getItem(pos);

			vHolder.tvTitle = (TextView) view.findViewById(R.id.tvTitle);
			vHolder.tvTitle.setText(book.getBookName());

			vHolder.tvAuthor = (TextView) view.findViewById(R.id.tvAuthor);
			vHolder.tvAuthor.setText(book.getAuthor());

			vHolder.ivBook = (ImageView) view.findViewById(R.id.ivBook);

			if (book.getPhoto().length() < 10)
			{
				vHolder.ivBook.setImageBitmap(PhotoHelper.StringToPhoto(myApp.genericPhoto));
			}
			else
			{
				vHolder.ivBook.setImageBitmap(PhotoHelper.StringToPhoto(book
				        .getPhoto()));
			}
			return view;
		}

		@Override
		public long getItemId(int position)
		{
			return position;
		}

		@Override
		public Object getItem(int position)
		{
			return myApp.c.getBooks().toArray()[position];
		}

		@Override
		public int getCount()
		{
			return myApp.c.getBooks().size();
		}

		private class ViewHolder
		{
			public TextView tvTitle;
			public TextView tvAuthor;
			public ImageView ivBook;
		}
	}
}
