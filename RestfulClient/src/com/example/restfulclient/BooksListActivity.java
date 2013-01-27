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

import android.annotation.TargetApi;
import android.app.ListActivity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.restfulclient.helpers.Book;
import com.example.restfulclient.helpers.Category;
import com.example.restfulclient.helpers.MyApplication;
import com.example.restfulclient.helpers.Parser;
import com.example.restfulclient.helpers.PhotoHelper;

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
		        AddBookActivity.class);
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
	    if (myApp.offline)
	    {
	    } else
	    {
		
	    }
	    String webClientUrl = "http://" + url[0] + ":8020/";
	    String categoryUrl = "categoryservice/category/"
		    + c.getCategoryId() + "/books";
	    
	    HttpParams params = new BasicHttpParams();
	    
	    HttpConnectionParams.setConnectionTimeout(params, 8000);
	    HttpConnectionParams.setSoTimeout(params, 8000);
	    
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
    
    //wywo³anie tej metody spowoduje uruchoimienie activity do robienia zdjêæ
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
		
		String img = PhotoHelper.PhotoToString(mStorage + "/" + myApp.b.getBookName()
		        + ".jpg", myApp.b);
		
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
    
    private class BookAdapter extends BaseAdapter
    {
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
	    
	    Book book = (Book) getItem(pos);
	    
	    vHolder.tvTitle = (TextView) view.findViewById(R.id.tvTitle);
	    vHolder.tvTitle.setText(book.getBookName());
	    
	    vHolder.tvAuthor = (TextView) view.findViewById(R.id.tvAuthor);
	    vHolder.tvAuthor.setText(book.getAuthor());
	    
	    vHolder.ivBook = (ImageView) view.findViewById(R.id.ivBook);
	    File img = new File(mStorage + "/" + book.getBookName()
		    + "_mini.jpg");
	    if (img.exists())
	    {
		vHolder.ivBook.setImageBitmap(BitmapFactory.decodeFile(img
		        .getPath()));
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
	    public ImageView ivBook;
	}
    }
}
