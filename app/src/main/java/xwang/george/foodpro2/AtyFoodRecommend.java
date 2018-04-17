package xwang.george.foodpro2;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

/**
 * Created by george on 10/10/2017.
 */

public class AtyFoodRecommend extends Activity {
    private Spinner lowFatSpinner, heartHealthySpinner, quickSpinner,nutritiousSpinner;
    private ArrayAdapter<String> lowFatAdapter;
    private ArrayAdapter<String> heartHealthyAdapter;
    private ArrayAdapter<String> quickAdapter;
    private ArrayAdapter<String> nutritiousAdapter;
    private EditText addToLowFatSpinnerEdit, addToHealthySpinnerEdit, addToNutritiousSpinnerEdit, addToQuickSpinnerEdit;
    //private Button btnAddToLowFatSpinner, btnAddToHealthySpinner, btnAddToNutriousSpinner, btnAddToQuickSpinner;
    /*
    private String [] lowfat = new String[]{"Apples, raw, with skin", "Beans, kidney, red, mature seeds, canned", "Beans, snap, green, frozen, cooked, boiled, drained without salt", "Beef, bottom sirloin, tri-tip, separable lean only, trimmed"};
    private String [] healthy = new String[]{"Alcoholic beverage, wine, table, red", "Asparagus, cooked, boiled, drained", "Avocados, raw, all commercial varieties", "Beans, black, mature seeds, cooked, boiled, without salt"};
    private String [] quick = new String[]{"Apples, raw, with skin", "Apricots, dried, sulfured, uncooked", "Broccoli, frozen, chopped, cooked, boiled, drained, without salt", "Burger King salad: Side Garden Salad without dressing"};
    private String [] nutritious = new String[]{"Acerola, (west indian cherry), raw", "Amaranth, uncooked", "Apricots, raw", "Arugula, raw"};
    */
   // private ArrayList<String> lowFatArrayList, healthyArrayList, quickArrayList, nutritiousArrayList;
    private HealthyFoodDB healthyFoodDB;
    private SQLiteDatabase healthyDBReader, healthyDBWriter;
    private LowFatFoodDB lowFatFoodDB;
    private SQLiteDatabase lowFatDBReader, lowFatDBWriter;
    private NutritiousFoodDB nutritiousFoodDB;
    private SQLiteDatabase nutritiousDBReader, nutritiousDBWriter;
    private QuickFoodDB quickFoodDB;
    private SQLiteDatabase quickDBReader, quickDBWriter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_recommend);
        healthyFoodDB =new HealthyFoodDB(this);
        healthyDBReader=healthyFoodDB.getReadableDatabase();
        healthyDBWriter=healthyFoodDB.getWritableDatabase();
        lowFatFoodDB = new LowFatFoodDB(this);
        lowFatDBReader = lowFatFoodDB.getReadableDatabase();
        lowFatDBWriter = lowFatFoodDB.getWritableDatabase();
        nutritiousFoodDB = new NutritiousFoodDB(this);
        nutritiousDBReader= nutritiousFoodDB.getReadableDatabase();
        nutritiousDBWriter = nutritiousFoodDB.getWritableDatabase();
        quickFoodDB = new QuickFoodDB(this);
        quickDBReader = quickFoodDB.getReadableDatabase();
        quickDBWriter = quickFoodDB.getWritableDatabase();
        /*
        lowFatArrayList = new ArrayList<String>();
        healthyArrayList = new ArrayList<String>();
        quickArrayList = new ArrayList<String>();
        nutritiousArrayList = new ArrayList<String>();
        */
        lowFatSpinner= (Spinner) findViewById(R.id.lowFatSpinner);
        lowFatAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        lowFatSpinner.setAdapter(lowFatAdapter);
        heartHealthySpinner = (Spinner) findViewById(R.id.heartHealthySpinner);
        heartHealthyAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        heartHealthySpinner.setAdapter(heartHealthyAdapter);
        quickSpinner = (Spinner) findViewById(R.id.quickSpinner);
        quickAdapter= new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        quickSpinner.setAdapter(quickAdapter);
        nutritiousSpinner = (Spinner) findViewById(R.id.nutritiousSpinner);
        nutritiousAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        nutritiousSpinner.setAdapter(nutritiousAdapter);
        addDataToSpinnerAdapter();
        addToLowFatSpinnerEdit = (EditText) findViewById(R.id.addToLowFatSpinnerEdit);
        addToHealthySpinnerEdit = (EditText) findViewById(R.id.addToHealthySpinnerEdit);
        addToNutritiousSpinnerEdit = (EditText) findViewById(R.id.addToNutritiousSpinnerEdit);
        addToQuickSpinnerEdit = (EditText) findViewById(R.id.addToQucickSpinnerEdit);
        findViewById(R.id.btnAddToLowFatSpinner).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!addToLowFatSpinnerEdit.getText().toString().equals("")){
                    //lowFatArrayList.add(addToLowFatSpinnerEdit.getText().toString());
                    ContentValues values = new ContentValues();
                    values.put("name", addToLowFatSpinnerEdit.getText().toString() );
                    lowFatDBWriter.insert(LowFatFoodDB.LOW_FAT_FOOD_TABLE, null, values);
                    lowFatAdapter.add(addToLowFatSpinnerEdit.getText().toString());
                    addToLowFatSpinnerEdit.setText("");

                }
            }
        });
        findViewById(R.id.btnAddToHealthySpinner).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!addToHealthySpinnerEdit.getText().toString().equals("")){
                    ContentValues values = new ContentValues();
                    values.put("name", addToHealthySpinnerEdit.getText().toString() );
                    healthyDBWriter.insert(HealthyFoodDB.HEALTHY_FOOD_TABLE, null, values);
                    heartHealthyAdapter.add(addToHealthySpinnerEdit.getText().toString());
                    addToHealthySpinnerEdit.setText("");

                }
            }
        });
        findViewById(R.id.btnAddToNutritiousSpinner).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!addToNutritiousSpinnerEdit.getText().toString().equals("")){
                    ContentValues values = new ContentValues();
                    values.put("name", addToNutritiousSpinnerEdit.getText().toString() );
                    nutritiousDBWriter.insert(NutritiousFoodDB.NUTRITIOUS_FOOD_TABLE, null, values);
                    nutritiousAdapter.add(addToNutritiousSpinnerEdit.getText().toString());
                    addToNutritiousSpinnerEdit.setText("");
                }
            }
        });
        findViewById(R.id.btnAddToQuickSpinner).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!addToQuickSpinnerEdit.getText().toString().equals("")){
                    ContentValues values = new ContentValues();
                    values.put("name", addToQuickSpinnerEdit.getText().toString() );
                    quickDBWriter.insert(QuickFoodDB.QUICK_FOOD_TABLE, null, values);
                    quickAdapter.add(addToQuickSpinnerEdit.getText().toString());
                    addToQuickSpinnerEdit.setText("");
                }
            }
        });
    }

    private void addDataToSpinnerAdapter() {
        Cursor lowFatCursor, healthyCursor, nutritiousCursor, quickCursor;
        lowFatCursor = lowFatDBReader.query(LowFatFoodDB.LOW_FAT_FOOD_TABLE, null, null, null, null, null, null);
        healthyCursor = healthyDBReader.query(HealthyFoodDB.HEALTHY_FOOD_TABLE, null, null, null, null, null, null);
        nutritiousCursor= nutritiousDBReader.query(NutritiousFoodDB.NUTRITIOUS_FOOD_TABLE, null, null, null, null, null, null);
        quickCursor = quickDBReader.query(QuickFoodDB.QUICK_FOOD_TABLE, null, null, null, null, null, null);
        while (lowFatCursor!=null && lowFatCursor.moveToNext()){
            String name = lowFatCursor.getString(lowFatCursor.getColumnIndex("name"));
            lowFatAdapter.add(name);
        }
        while (healthyCursor!=null && healthyCursor.moveToNext()){
            String name = healthyCursor.getString(healthyCursor.getColumnIndex("name"));
            heartHealthyAdapter.add(name);
        }
        while (nutritiousCursor!=null && nutritiousCursor.moveToNext()){
            String name = nutritiousCursor.getString(nutritiousCursor.getColumnIndex("name"));
            nutritiousAdapter.add(name);
        }
        while (quickCursor!=null && quickCursor.moveToNext()){
            String name = quickCursor.getString(quickCursor.getColumnIndex("name"));
            quickAdapter.add(name);
        }

    }
}
