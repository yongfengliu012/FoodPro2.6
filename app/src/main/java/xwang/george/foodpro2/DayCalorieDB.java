package xwang.george.foodpro2;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by george on 10/11/2017.
 */

public class DayCalorieDB extends SQLiteOpenHelper {
    public static final String DAY_CALORIE_DB= "dayCalorieDB";
    public DayCalorieDB(Context context) {
        super(context, "db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE  dayCalorieDB("+
                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "breakfast REAL DEFAULT \"\"," +
                "lunch REAL DEFAULT \"\"," +
                "dinner REAL DEFAULT \"\"," +
                "total REAL DEFAULT \"\")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
