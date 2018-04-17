package xwang.george.foodpro2;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

/**
 * Created by george on 10/17/2017.
 */

public class AtyFindPassword extends Activity {
    private Spinner securityQuestionSpinner;
    private EditText securityAnswerEditText;
   //private Button btnFindPasswordSubmit, btnFindPasswordCancel;
    private ArrayAdapter<String> securityQuestionSpinnerAdapter;
    private String  securityQuestionSelected, username, password, securityQuestion, securityAnswer;
    private Cursor loginCursor;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_password);
        loginCursor = getContentResolver().query(UserDBContentProvider.USER_URI, null, null, null, null);
        if(loginCursor!=null && loginCursor.moveToFirst()){
            username = loginCursor.getString(loginCursor.getColumnIndex("username"));
            password = loginCursor.getString(loginCursor.getColumnIndex("password"));
            securityQuestion = loginCursor.getString(loginCursor.getColumnIndex("securityQuestion"));
            securityAnswer = loginCursor.getString(loginCursor.getColumnIndex("securityAnswer"));
        }
       // Toast.makeText(AtyFindPassword.this, "/"+ securityQuestion + "/"+ securityAnswer, Toast.LENGTH_LONG).show();
        securityQuestionSpinner = (Spinner) findViewById(R.id.securityQuestionSpinner2);
        securityQuestionSpinnerAdapter = new ArrayAdapter<String>(AtyFindPassword.this, android.R.layout.simple_list_item_1);
        securityQuestionSpinner.setAdapter(securityQuestionSpinnerAdapter);
        securityQuestionSpinnerAdapter.add("Where did your parents first meet?");
        securityQuestionSpinnerAdapter.add("What's your mom's middle name?");
        securityQuestionSpinnerAdapter.add("What's the brand of your first car?");
        securityQuestionSpinnerAdapter.add("What's the name of your first pet?");
        securityQuestionSpinnerAdapter.add("What's the name of your primary school?");
        securityQuestionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                securityQuestionSelected = securityQuestionSpinnerAdapter.getItem(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        securityAnswerEditText = (EditText) findViewById(R.id.securityAnswerEditText2);
        findViewById(R.id.btnFindPasswordSubmit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(securityQuestionSelected.equals(securityQuestion) && securityAnswerEditText.getText().toString().toUpperCase().equals(securityAnswer.toUpperCase())){
                    Toast.makeText(AtyFindPassword.this, String.format("password = %s", password), Toast.LENGTH_SHORT).show();
                    Intent toLoginPage = new Intent(AtyFindPassword.this, LoginActivity.class);
                    startActivity(toLoginPage);
                }else{
                    Toast.makeText(AtyFindPassword.this, "No match find, please try again", Toast.LENGTH_SHORT).show();
                }
            }
        });
        findViewById(R.id.btnFindPasswordCancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toLoginPage2 = new Intent(AtyFindPassword.this, LoginActivity.class);
                startActivity(toLoginPage2);
            }
        });
    }
}
