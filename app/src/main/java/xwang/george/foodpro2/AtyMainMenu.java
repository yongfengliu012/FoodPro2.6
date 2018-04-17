package xwang.george.foodpro2;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * Created by george on 10/10/2017.
 */

public class AtyMainMenu extends ListActivity {
    private ArrayAdapter<ListCellData> arrayAdapter = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        arrayAdapter = new ArrayAdapter<ListCellData>(this, android.R.layout.simple_list_item_1);
        setListAdapter(arrayAdapter);
        arrayAdapter.add(new ListCellData("Account Management", this, new Intent(this, AtyAccountManagement.class)));
        arrayAdapter.add(new ListCellData("Adding New Food", this, new Intent(this, AtyAddingNewFood.class)));
        arrayAdapter.add(new ListCellData("Daily Log", this, new Intent(this, AtyDailyLog.class)));
        arrayAdapter.add(new ListCellData("Exercise Reminder", this, new Intent(this, AtyExerciseReminder.class)));
        arrayAdapter.add(new ListCellData("Food Recommend", this, new Intent(this, AtyFoodRecommend.class)));
        arrayAdapter.add(new ListCellData("Meal Analyzing", this, new Intent(this, AtyMealAnalyzing.class)));
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        ListCellData data = arrayAdapter.getItem(position);
        startActivity(data.getIntent());
        super.onListItemClick(l, v, position, id);
    }

}
