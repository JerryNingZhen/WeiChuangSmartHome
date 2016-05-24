package com.example.testsocket;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
public class DbAdapter extends SQLiteOpenHelper{
		private static final int VERSION = 1;
		public DbAdapter(Context context, String name,
				CursorFactory factory, int version) {
			super(context, name, factory, version);
			// TODO Auto-generated constructor stub
			
		}
		public DbAdapter(Context context,String name){
			this(context,name,VERSION);
		}
		public DbAdapter(Context context,String name,int version){
			this(context,name,null,VERSION);
		}
		public void onCreate(SQLiteDatabase db) {
			// TODO Auto-generated method stub
			System.out.println("create a database");
			db.execSQL("create table person(AccountInt int,Password varchar(20))");
		}


		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub
			System.out.println("update a database");
		}
		
}
