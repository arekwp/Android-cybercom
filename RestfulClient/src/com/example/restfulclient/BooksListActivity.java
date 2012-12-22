package com.example.restfulclient;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import android.app.ListActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.restfulclient.helpers.Book;
import com.example.restfulclient.helpers.Category;
import com.example.restfulclient.helpers.MyApplication;
import com.example.restfulclient.helpers.Parser;

public class BooksListActivity extends ListActivity
{

	private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
	MyApplication myApp = null;
	Category cat = null;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_books_list);

		myApp = (MyApplication) getApplication();
		cat = myApp.c;
		cat.setBooks(new ArrayList<Book>());

		new GetBooksThread(myApp.c).execute("192.168.1.2");

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
		Log.d("kliknieto: ", selection.getAuthor());
	}

	class GetBooksThread extends AsyncTask<String, Void, Category>
	{
		Category c;

		public GetBooksThread(Category cin)
		{
			super();
			c = cin;
		}

		protected Category doInBackground(String... url)
		{
			String webClientUrl = "http://" + url[0] + ":8020/";
			String categoryUrl = "categoryservice/category/"
			        + c.getCategoryId() + "/books";

			HttpParams params = new BasicHttpParams();

			HttpConnectionParams.setConnectionTimeout(params, 2000);
			HttpConnectionParams.setSoTimeout(params, 2000);

			HttpClient hc = new DefaultHttpClient(params);

			HttpGet hg = new HttpGet(webClientUrl + categoryUrl);
			try
			{
				HttpResponse hr = hc.execute(hg);

				HttpEntity entity = hr.getEntity();

				if (entity != null)
				{
					InputStream is = entity.getContent();

					Parser parser = new Parser();

					c.setBooks(parser.getBooks(is));
					is.close();
				}
			} catch (ClientProtocolException e)
			{
				e.printStackTrace();
			} catch (IOException e)
			{
				System.out.println("service timeout");
				e.printStackTrace();
			}
			return c;
		}

		@Override
		protected void onPostExecute(Category cat)
		{
			Log.d("added books: ", String.valueOf(cat.getBooks().size()));
			setCategoryWithBooks(cat);
		}
	}

	public void setCategoryWithBooks(Category cat)
	{
		myApp.c = cat;

		setListAdapter(new BookAdapter());

	}

	private void dispatchTakePictureIntent()
	{
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

		Uri fileUri = getOutputMediaFileUri();

		intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);

		startActivityForResult(intent, 100);
	}

	private Uri getOutputMediaFileUri()
	{
		return Uri.fromFile(getOutputPath());
	}

	private File getOutputPath()
	{
		File imgDir = new File(Environment.getExternalStorageDirectory(),
		        "Category Images");

		if (!imgDir.exists())
			if (!imgDir.mkdirs())
				Log.d("Making image dirs failed", "getOutputPath");

		File image = new File(imgDir.getPath() + myApp.b.getBookName() + ".jpg");

		return image;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE)
		{
			if (resultCode == RESULT_OK)
			{
				// Image captured and saved to fileUri specified in the Intent
				Toast.makeText(this, "Image saved to:\n" + data.getData(),
				        Toast.LENGTH_LONG).show();
			} else if (resultCode == RESULT_CANCELED)
			{
				// User cancelled the image capture
			} else
			{
				// Image capture failed, advise user
			}
		}

		if (requestCode == CAPTURE_VIDEO_ACTIVITY_REQUEST_CODE)
		{
			if (resultCode == RESULT_OK)
			{
				// Video captured and saved to fileUri specified in the Intent
				Toast.makeText(this, "Video saved to:\n" + data.getData(),
				        Toast.LENGTH_LONG).show();
			} else if (resultCode == RESULT_CANCELED)
			{
				// User cancelled the video capture
			} else
			{
				// Video capture failed, advise user
			}
		}
	}

	private class BookAdapter extends BaseAdapter
	{
		public BookAdapter()
		{

		}

		@Override
		public View getView(int pos, View view, ViewGroup parent)
		{
			LayoutInflater layoutInflayer = (LayoutInflater) getLayoutInflater();
			ViewHolder vHolder = null;

			if (view == null)
			{
				view = layoutInflayer.inflate(R.layout.book_row_layout, null,
				        false);
			}

			vHolder = new ViewHolder();

			Book cat = (Book) getItem(pos);

			vHolder.tvTitle = (TextView) view.findViewById(R.id.tvTitle);
			vHolder.tvTitle.setText(cat.getBookName());

			vHolder.tvAuthor = (TextView) view.findViewById(R.id.tvAuthor);
			vHolder.tvAuthor.setText(cat.getAuthor());

			vHolder.bTakePhoto = (Button) view.findViewById(R.id.bTakePhoto);
			vHolder.bTakePhoto.setOnClickListener(new OnClickListener()
			{

				@Override
				public void onClick(View v)
				{
					dispatchTakePictureIntent();

				}
			});
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
			return (Book) myApp.c.getBooks().toArray()[position];
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
			public Button bTakePhoto;
		}
	}
}
