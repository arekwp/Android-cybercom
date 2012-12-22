package com.example.restfulclient.helpers;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

/* TODO add method:
 * update -> form
 * 
 */

public class DatabaseHelper extends SQLiteOpenHelper
{

	// FormDatas table name
	private static final String TABLE_CATS = "categories";
	private static final String TABLE_BOOKS = "books";

	private static final String DATABASE_NAME = "cats";

	private static final int DATABASE_VERSION = 1;

	// FormDatas Table Columns names
	private static final String KEY_ID = "id";
	
	private static final String KEY_CAT_ID = "cid";
	private static final String KEY_CAT_NAME = "name";

	private static final String KEY_BOOK_ID = "bid";
	private static final String KEY_BOOK_NAME = "name";
	private static final String KEY_BOOK_AUTHOR = "author";
	private static final String KEY_BOOK_ISBN = "isbn";
	private static final String KEY_BOOK_CAT = "cid";

	public DatabaseHelper(Context context, String name, CursorFactory factory,
	        int version)
	{
		super(context, name, factory, version);
	}

	public DatabaseHelper(Context context)
	{
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db)
	{
		String CREATE_CAT_TABLE = "CREATE TABLE " + TABLE_CATS + "("
		        + KEY_ID + " INTEGER PRIMARY KEY,"
		        + KEY_CAT_ID + " TEXT,"
				+ KEY_CAT_NAME + " TEXT"
		        + ")";

		String CREATE_BOOK_TABLE = "CREATE TABLE " + TABLE_BOOKS + "("
		        + KEY_ID + " INTEGER PRIMARY KEY," 
		        + KEY_BOOK_NAME + " TEXT"
				+ KEY_BOOK_NAME	+ " TEXT, "
		        + KEY_BOOK_AUTHOR + " TEXT, "
				+ KEY_BOOK_ISBN
		        + " TEXT, " + KEY_BOOK_CAT + " TEXT " + ")";

		db.execSQL(CREATE_CAT_TABLE);
		db.execSQL(CREATE_BOOK_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
	{
		// Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_BOOKS);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_CATS);

		// Create tables again
		onCreate(db);

	}

	public void addCategory(Category c)
	{
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = packToValues(c);

		// Inserting Row
		db.insert(TABLE_CATS, null, values);
		// db.close(); // Closing database connection
	}

	public List<Category> getAllCats()
	{
		List<Category> catList = new ArrayList<Category>();
		// Select All Query
		String selectQuery = "SELECT  * FROM " + TABLE_CATS;

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		if (cursor.moveToFirst())
		{
			do
			{
				Category cat = packToCat(cursor);

				catList.add(cat);
			} while (cursor.moveToNext());
		}
		cursor.close();
		db.close();

		return catList;
	}
	
	public List<Book> getAllBooks()
	{
		List<Book> formList = new ArrayList<Book>();
		// Select All Query
		String selectQuery = "SELECT  * FROM " + TABLE_BOOKS;

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		if (cursor.moveToFirst())
		{
			do
			{
				Book form = packToBook(cursor);

				formList.add(form);
			} while (cursor.moveToNext());
		}
		cursor.close();
		db.close();

		return formList;
	}

	public int getCatCount()
	{

		String countQuery = "SELECT  * FROM " + TABLE_CATS;
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.rawQuery(countQuery, null);

		int count = cursor.getCount();
		cursor.close();
		db.close();

		// return count
		return count;
	}
	
	public int getBookCount()
	{

		String countQuery = "SELECT  * FROM " + TABLE_BOOKS;
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.rawQuery(countQuery, null);

		int count = cursor.getCount();
		cursor.close();
		db.close();

		// return count
		return count;
	}

	public void dropDb()
	{
		SQLiteDatabase db = this.getReadableDatabase();

		db.execSQL("DROP TABLE IF EXISTS " + TABLE_CATS);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_BOOKS);

		onCreate(db);

		db.close();
	}

	public Category getCatByCid(String cid)
	{
		Category c = new Category();
		// Select All Query

		String selectQuery = "SELECT  * FROM " + TABLE_CATS + " WHERE cid = "
		        + cid;

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		if (cursor.moveToFirst())
		{
			c = packToCat(cursor);
		}
		cursor.close();
		db.close();

		return c;
	}
	
	public Book getBookByBid(String bid)
	{
		Book b = new Book();
		// Select All Query

		String selectQuery = "SELECT  * FROM " + TABLE_BOOKS + " WHERE bid = "
		        + bid;

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		if (cursor.moveToFirst())
		{
			b = packToBook(cursor);
		}
		cursor.close();
		db.close();

		return b;
	}
	
	public Book getBookByCid(String cid)
	{
		Book b = new Book();
		// Select All Query

		String selectQuery = "SELECT  * FROM " + TABLE_BOOKS + " WHERE cid = "
		        + cid;

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		if (cursor.moveToFirst())
		{
			b = packToBook(cursor);
		}
		cursor.close();
		db.close();

		return b;
	}

	private Category packToCat(Cursor cursor)
    {
		Category c = new Category();
		
		c.setCategoryId(cursor.getString(1));
		c.setCategoryName(cursor.getString(2));
		
	    return c;
    }
	
	private Book packToBook(Cursor cursor)
    {
		Book b = new Book();
		
		b.setBookId(cursor.getString(1));
		b.setBookName(cursor.getString(2));
		b.setAuthor(cursor.getString(3));
		b.setBookISBNnumber(cursor.getString(4));

	    return b;
    }

	public int update(Category c)
	{
		SQLiteDatabase db = getWritableDatabase();

		ContentValues cv = packToValues(c);

		return db.update(TABLE_CATS, cv, KEY_CAT_ID + " = ?", new String[]
		{ c.getCategoryId() });
	}

	public int update(Book b)
	{
		SQLiteDatabase db = getWritableDatabase();

		ContentValues cv = packToValues(b);

		return db.update(TABLE_BOOKS, cv, KEY_BOOK_ID + " = ?", new String[]
		{ b.getBookId() });
	}

	private ContentValues packToValues(Category c)
	{
		ContentValues values = new ContentValues();
		values.put(KEY_CAT_NAME, c.getCategoryName());
		values.put(KEY_CAT_ID, c.getCategoryId());
		return values;
	}

	private ContentValues packToValues(Book b)
	{
		ContentValues values = new ContentValues();
		values.put(KEY_BOOK_ID, b.getBookId());
		values.put(KEY_BOOK_NAME, b.getBookName());
		values.put(KEY_BOOK_AUTHOR, b.getAuthor());
		values.put(KEY_BOOK_ISBN, b.getBookISBNnumber());
		values.put(KEY_BOOK_CAT, b.getBookName());

		return values;
	}
}
