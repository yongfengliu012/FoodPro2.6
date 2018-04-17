package xwang.george.foodpro2;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by george on 10/12/2017.
 */

public class NutritiousFoodDB extends SQLiteOpenHelper {
    public static final String NUTRITIOUS_FOOD_TABLE="nutritiousFood";
    public NutritiousFoodDB(Context context) {
        super(context, "db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE  nutritiousFood("+
                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT DEFAULT \"\")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
