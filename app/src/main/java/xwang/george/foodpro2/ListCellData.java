package xwang.george.foodpro2;

import android.content.Context;
import android.content.Intent;

/**
 * Created by george on 10/10/2017.
 */

public class ListCellData {
    private Context context = null;
    private String controlName = "";
    private Intent intent = null;
    public ListCellData(String controlName, Context context, Intent intent ){
        this.controlName = controlName;
        this.context = context;
        this.intent = intent;
    }
    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public String getControlName() {
        return controlName;
    }

    public void setControlName(String controlName) {
        this.controlName = controlName;
    }

    public Intent getIntent() {
        return intent;
    }

    public void setIntent(Intent intent) {
        this.intent = intent;
    }

    @Override
    public String toString() {
        return controlName;
    }
}
