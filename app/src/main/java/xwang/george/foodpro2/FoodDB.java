package xwang.george.foodpro2;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by george on 10/10/2017.
 */

public class FoodDB extends SQLiteOpenHelper {
    public static final String FOOD_TABLE="food";
    public FoodDB(Context context) {
        super(context, "db", null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE  food("+
                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "foodname TEXT DEFAULT \"\"," +
                "calorie REAL DEFAULT \"\"," +
                "unit TEXT DEFAULT \"\")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
