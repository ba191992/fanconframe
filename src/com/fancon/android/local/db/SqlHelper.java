package com.fancon.android.local.db;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Locale;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.util.Log;

import com.fancon.android.local.device.DeviceFactory;

public class SqlHelper extends SQLiteOpenHelper {
	public static final String DATABASE_PATH = "/data/data/com.fancon.rage.comic.vn/";

	public static final String DATABASE_NAME = "rageviet.sqlite";

	public static final String TABLE_NAME = "tbl_Tuvi";

	public static final String COLUMN_NAME = "Name";
	public static final String COLUMN_FULLNAME = "FullName";
	public static final String COLUMN_DESCRIPTION = "Description";
	public static final String COLUMN_PARENT = "Parent";
	public static final String COLUMN_BORN_START = "born_start";
	public static final String COLUMN_BORN_END = "born_end";
	public static final String COLUMN_IMAGE_ICON = "image_icon";

	public SQLiteDatabase dbSqlite;

	protected final Context mContext;

	public SqlHelper(Context context) {
		super(context, DATABASE_NAME, null, 1);
		this.mContext = context;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// check if exists and copy database from resource
		createDB();
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w("SqlHelper", "Upgrading database from version " + oldVersion
				+ " to " + newVersion + ", which will destroy all old data");
		onCreate(db);
	}

	public void createDatabase() {
		createDB();
	}

	protected void createDB() {
		// boolean dbExist = DBExists();
		boolean dbExist = DBExists();
		if (!dbExist) {
			Log.e("SqlHelper", "database not exist");
			copyDBFromResource();
		}
	}

	public boolean databaseExist() {
		File dbFile = new File(DATABASE_PATH + DATABASE_NAME);
		return dbFile.exists();
	}

	protected boolean DBExists() {
		SQLiteDatabase db = null;
		try {
			String databasePath = DATABASE_PATH + DATABASE_NAME;
			db = SQLiteDatabase.openDatabase(databasePath, null,
					SQLiteDatabase.OPEN_READWRITE);
			db.setLocale(Locale.getDefault());
			db.setLockingEnabled(true);
			db.setVersion(1);
		} catch (SQLiteException e) {
			Log.e("SqlHelper", "database not found");
		}
		if (db != null) {
			db.close();
		}
		return db != null ? true : false;
	}

	protected void copyDBFromResource() {
		InputStream inputStream = null;
		OutputStream outStream = null;
		String dbFilePath = DATABASE_PATH + DATABASE_NAME;
		try {
			AssetManager a = mContext.getAssets();
			inputStream = mContext.getAssets().open(DATABASE_NAME);
			outStream = new FileOutputStream(dbFilePath);
			byte[] buffer = new byte[1024];
			int length;
			while ((length = inputStream.read(buffer)) > 0) {
				outStream.write(buffer, 0, length);
			}
			outStream.flush();
			outStream.close();
			inputStream.close();
		} catch (IOException e) {
			Log.e("SqlHelper", "database not copy");
			throw new Error("Problem copying database from resource file.");
		}

	}

	public void openDataBase() throws SQLException {
		//Close befor open
		if (dbSqlite != null)
			dbSqlite.close();
		String myPath = DATABASE_PATH + DATABASE_NAME;
		dbSqlite = SQLiteDatabase.openDatabase(myPath, null,
				SQLiteDatabase.OPEN_READWRITE);
	}

	@Override
	public synchronized void close() {
		if (dbSqlite != null)
			dbSqlite.close();
		super.close();
	}

	public Cursor getCursor() {
		SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
		queryBuilder.setTables(TABLE_NAME);
		String[] asColumnsToReturn = new String[] { COLUMN_NAME,
				COLUMN_FULLNAME, COLUMN_DESCRIPTION, COLUMN_PARENT };
		Cursor mCursor = queryBuilder.query(dbSqlite, asColumnsToReturn, null,
				null, null, null, COLUMN_NAME + " ASC");
		return mCursor;
	}

	public Cursor getCursor(int id) {
		SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
		queryBuilder.setTables(TABLE_NAME);
		String[] asColumnsToReturn = new String[] { COLUMN_NAME,
				COLUMN_FULLNAME, COLUMN_DESCRIPTION, COLUMN_PARENT,
				COLUMN_BORN_START, COLUMN_BORN_END, COLUMN_IMAGE_ICON };
		Cursor mCursor = queryBuilder.query(dbSqlite, asColumnsToReturn,
				"Parent = 'Ty'", null, null, null, null);
		return mCursor;
	}

	public Cursor getRawCursor(int id) {
		String nam = "Ty";
		switch (id) {
		case 1:
			nam = "Ty";
			break;
		case 2:
			nam = "Suu";
			break;
		case 3:
			nam = "Dan";
			break;
		case 4:
			nam = "Mao";
			break;
		case 5:
			nam = "Thin";
			break;
		case 6:
			nam = "Tyj";
			break;
		case 7:
			nam = "Ngo";
			break;
		case 8:
			nam = "Mui";
			break;
		case 9:
			nam = "Than";
			break;
		case 10:
			nam = "Dau";
			break;
		case 11:
			nam = "Tuat";
			break;
		case 12:
			nam = "Hoi";
			break;
		default:
			break;
		}
		String sql = "select * from tbl_Tuvi where Parent = '" + nam + "'";
		Cursor mCursor = dbSqlite.rawQuery(sql, null);
		return mCursor;
	}

	public Cursor getRawCursor(String parent) {
		String sql = "select * from tbl_Tuvi where Parent = '" + parent + "'";
		Cursor mCursor = dbSqlite.rawQuery(sql, null);
		return mCursor;
	}

	protected Cursor excuteQuery(String sql) {
		Cursor mCursor = dbSqlite.rawQuery(sql, null);
		return mCursor;
	}

	public void clearSelections() {
		ContentValues values = new ContentValues();
		values.put(" selected", 0);
		this.dbSqlite.update(SqlHelper.TABLE_NAME, values, null, null);
	}

	public boolean isUserAdded() {
		String sql = "select * from tbl_IsUserAdded where isUserAdded = 1";
		Cursor mCursor = dbSqlite.rawQuery(sql, null);
		((Activity) mContext).startManagingCursor(mCursor);
		if (mCursor.getCount() > 0) {
			Log.d("result", "Successfully");
			return true;
		}
		Log.d("result", "False");
		// startManagingCursor(mCursor);
		return false;
	}

	public boolean isAdded(String name, int gender) {
		openDataBase();
		String uuid = null;
		try {
			uuid = DeviceFactory.md5(DeviceFactory.getDeviceId(mContext));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String sql = "select * from tbl_added_user where uuid = '" + uuid + "'";
		Cursor mCursor = dbSqlite.rawQuery(sql, null);
		((Activity) mContext).startManagingCursor(mCursor);
		int i = mCursor.getCount();
		if (mCursor.getCount() == 1) {
			return true;
		}
		return false;
	}

	public Cursor getAgeInfo(String name) {
		openDataBase();
		String sql = "select * from tbl_Tuvi where Name = '" + name + "'";
		Cursor mCursor = dbSqlite.rawQuery(sql, null);

		return mCursor;
	}

	public boolean addUser() {
		ContentValues values = new ContentValues();
		values.put("isUserAdded", "1");
		dbSqlite.insert("tbl_IsUserAdded", null, values);
		return true;
	}

	public boolean addCard(String name, int gender, String key,
			String UUIDEncoded) {
		openDataBase();
		ContentValues values = new ContentValues();
		values.put("name", name);
		values.put("gender", gender);
		values.put("key", key);
		values.put("uuid", UUIDEncoded);
		dbSqlite.insert("tbl_added_user", null, values);
		return true;
	}

	public Cursor findAge(String year) {
		openDataBase();
		String sql = "select * from tbl_Tuvi where  born_start <='" + year
				+ "' and born_end >='" + year + "'";
		Cursor mCursor = dbSqlite.rawQuery(sql, null);
		return mCursor;
	}

	public boolean isOtherExist(String key) {
		openDataBase();
		String sql = "select * from tbl_added_user where key = '" + key + "'";
		Cursor mCursor = dbSqlite.rawQuery(sql, null);
		((Activity) mContext).startManagingCursor(mCursor);
		if (mCursor.getCount() >= 1) {
			return false;
		}
		return true;
	}
}