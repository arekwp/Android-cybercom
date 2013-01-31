package com.example.restfulclient;

import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
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
	MyApplication myApp = null;
	Category cat = null;

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
		//Log.d("kliknieto: ", selection.getBookName());
		Intent intent = new Intent(BooksListActivity.this,
		        BookDetailsActivity.class);
		BooksListActivity.this.startActivity(intent);
		
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
		menu.add(0, 1, 0, "Edytuj");
		menu.add(0, 2, 0, "Usuń");
	}

	@Override
	public boolean onContextItemSelected(MenuItem item)
	{
		AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item
		        .getMenuInfo();
		myApp.b = myApp.books.get(info.position);
		myApp.b.setCatId(myApp.c.getCategoryId());

		Log.d("kliknieto: ", myApp.b.getBookName());
		if (item.getItemId() == 1)
		{
			Intent intent = new Intent(BooksListActivity.this,
			        BookDetailsActivity.class);

			BooksListActivity.this.startActivity(intent);

		} else if (item.getItemId() == 2)
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
			myApp.b = null;
			new GetBooksThread(myApp.c).execute(myApp.addr);
			Log.v("AsyncTask", "setting result");
		}
	}

	public void setCategoryWithBooks(Category cat)
	{
		myApp.c = cat;

		myApp.books = (List<Book>) cat.getBooks();

		if (myApp.c.getBooks().size() == 0)
		{
			Toast.makeText(this, "Brak książek w kategorii", Toast.LENGTH_LONG)
			        .show();
		}

		setListAdapter(new BookAdapter());

	}

	@Override
	public void finish()
	{
		super.finish();
		myApp.b = null;
		myApp.c = null;
	}

	@Override
	public void onResume()
	{
		super.onResume();
		new GetBooksThread(myApp.c).execute(myApp.addr);
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
				vHolder.ivBook.setImageBitmap(PhotoHelper
				        .StringToPhoto(myApp.genericPhoto));
			} else
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
