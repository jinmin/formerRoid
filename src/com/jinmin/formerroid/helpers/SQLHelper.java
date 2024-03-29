package com.jinmin.formerroid.helpers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLHelper extends SQLiteOpenHelper {

   public final static String DB_NAME = "formerRoid.db";
   public final static int DB_VERSION = 2;

   public final static String STORED_CONTACT_TB = "STORED_CONTACT_TB";

   public SQLHelper(Context context) {
      super(context, DB_NAME, null, DB_VERSION);
   }

   /**
    * 생성시
    */
   @Override
   public void onCreate(SQLiteDatabase db) {
      db.execSQL("create table " + STORED_CONTACT_TB + " (id INTEGER PRIMARY KEY AUTOINCREMENT" + ", name TEXT " + ", tel TEXT " + ", ring_mode INTEGER " + ", ring_mode_option INTEGER );");
   }

   @Override
   public void onOpen(SQLiteDatabase db) {
      super.onOpen(db);
   }

   /**
    * 업그레이드 호출
    */
   @Override
   public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
   }

}
