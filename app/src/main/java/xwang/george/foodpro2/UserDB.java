package xwang.george.foodpro2;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by george on 10/10/2017.
 */

public class UserDB extends SQLiteOpenHelper {
    public UserDB(Context context){
        super(context, "db", null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE  user("+
                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "username TEXT DEFAULT \"\"," +
                "password TEXT DEFAULT \"\"," +
                "age INTEGER DEFAULT \"\"," +
                "weight REAL DEFAULT \"\"," +
                "height REAL DEFAULT \"\"," +
                "gender TEXT DEFAULT \"\"," +
                "securityQuestion TEXT DEFAULT \"\"," +
                "securityAnswer TEXT DEFAULT \"\"," +
                "exerciseIndex REAL DEFAULT \"\")");
        db.execSQL("CREATE TABLE  food("+
                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "foodname TEXT DEFAULT \"\"," +
                "calorie REAL DEFAULT \"\"," +
                "unit TEXT DEFAULT \"\")");
        db.execSQL("CREATE TABLE  dayCalorieDB("+
                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "breakfast REAL DEFAULT \"\"," +
                "lunch REAL DEFAULT \"\"," +
                "dinner REAL DEFAULT \"\"," +
                "total REAL DEFAULT \"\")");
        db.execSQL("CREATE TABLE  healthyFood("+
                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT DEFAULT \"\")");
        db.execSQL("CREATE TABLE  lowFatFood("+
                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT DEFAULT \"\")");
        db.execSQL("CREATE TABLE  nutritiousFood("+
                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT DEFAULT \"\")");
        db.execSQL("CREATE TABLE  quickFood("+
                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT DEFAULT \"\")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
