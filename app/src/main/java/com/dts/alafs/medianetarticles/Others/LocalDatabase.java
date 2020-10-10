package com.dts.alafs.medianetarticles.Others;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class LocalDatabase extends SQLiteOpenHelper {


    public LocalDatabase(Context context)
    {
        super(context, "BookmarkDatabases.db", null, 1);
    }

    @Override

    public void onCreate(SQLiteDatabase db) {

   String tableBookmark="create table bookmark(id integer primary key   ,title text,summary text,content text,authorNom text)";
        db.execSQL(tableBookmark);

    }

    @Override

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
       db.execSQL("DROP TABLE IF EXISTS "+ "bookmark");
        // Create tables again
        onCreate(db);
    }

    public void insertData(int id,String title ,String summary  ,String content  ,String authorNom  )
    {
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        ContentValues values=new ContentValues();

        values.put("id",id);
        values.put("title",title);
        values.put("summary",summary);
        values.put("content",content);
        values.put("authorNom",authorNom);

        sqLiteDatabase.insert("bookmark",null,values);
        sqLiteDatabase.close();

        Log.d("add row","row "+id+" added");

    }

    public void removeData(int id)
    {
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        sqLiteDatabase.delete("bookmark", "id"+ " = ?", new String[]{ String.valueOf(id) } );
        sqLiteDatabase.close();
        Log.d("Remove row","row "+id+" removed");

    }




    public JSONArray fetchData() throws JSONException {

        JSONArray jsonArray=new JSONArray();
        JSONObject jsonObject;
        String fetchdata="select Distinct id,title,summary,content,authorNom from bookmark";

        SQLiteDatabase sqLiteDatabase=this.getReadableDatabase();

        Cursor cursor=sqLiteDatabase.rawQuery(fetchdata, null);

        if (cursor != null) {
            while (cursor.moveToNext()) {
                jsonObject=new JSONObject();
                jsonObject.put("id",cursor.getInt(0));
                jsonObject.put("title",cursor.getString(1));
                jsonObject.put("summary",cursor.getString(2));
                jsonObject.put("content",cursor.getString(3));
                jsonObject.put("authorNom",cursor.getString(4));


                jsonArray.put(jsonObject);
            }
        }
        sqLiteDatabase.close();
        Log.d("jsonArray of Bookmark",jsonArray.toString());
        return jsonArray;
    }




}