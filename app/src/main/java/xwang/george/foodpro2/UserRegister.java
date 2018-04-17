package xwang.george.foodpro2;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.Toast;

public class UserRegister extends AppCompatActivity {
    private EditText userInfo, passwordInfo, passwordInfo2, ageInfo, weightInfo, heightInfo, securityAnswerEditText;
    private Spinner securityQuestionSpinner;
    private RadioButton femaleRadio, maleRadio;
    private RatingBar exerciseRating;
    private Button btnSaveUserInfo;
    private String userGender;
    private Cursor userCursor;
    private String username;
    public static final String MALE="male";
    public static final String FEMALE="female";
    private boolean userInfoSaved = false;
    private ArrayAdapter<String> securityQuestionSpinnerAdapter;
    private String securityQuestionSelected = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_register);
        securityAnswerEditText = (EditText) findViewById(R.id.securityAnswerEditText);
        securityQuestionSpinner = (Spinner) findViewById(R.id.securityQuestionSpinner);
        securityQuestionSpinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
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
        userInfo = (EditText) findViewById(R.id.emailInfo);
        passwordInfo = (EditText) findViewById(R.id.passwordInfo);
        passwordInfo2 = (EditText) findViewById(R.id.passwordInfo2);
        ageInfo = (EditText) findViewById(R.id.ageInfo);
        weightInfo = (EditText) findViewById(R.id.weightInfo);
        heightInfo = (EditText) findViewById(R.id.heightInfo);
        femaleRadio = (RadioButton) findViewById(R.id.femaleRadio);
        maleRadio = (RadioButton) findViewById(R.id.maleRadio);
        exerciseRating = (RatingBar) findViewById(R.id.exerciseRating);
        btnSaveUserInfo = (Button) findViewById(R.id.registerSave);
    }
    public void registerSave(View view){
        saveUserInformation();
        if(userInfoSaved) {
            Intent toMainPage = new Intent(UserRegister.this, MainActivity.class);
            toMainPage.putExtra("userName", username);
            startActivity(toMainPage);
        }
    }

    private void saveUserInformation() {
        if(!passwordInfo.getText().toString().equals( passwordInfo2.getText().toString())){
            Toast.makeText(this, "passwords not match, type again", Toast.LENGTH_SHORT).show();
            passwordInfo.setText("");
            passwordInfo2.setText("");
        }else{
            ContentValues userValues = new ContentValues();
            userValues.put("username", userInfo.getText().toString());
            userValues.put("password", passwordInfo.getText().toString());
            userValues.put("age", Integer.parseInt(ageInfo.getText().toString()));
            userValues.put("weight", Double.parseDouble(weightInfo.getText().toString()));
            userValues.put("height", Double.parseDouble(heightInfo.getText().toString()));
            if (maleRadio.isChecked()){
                userGender = MALE;
            }else if(femaleRadio.isChecked()){
                userGender = FEMALE;
            }
            userValues.put("gender", userGender);
            userValues.put("securityQuestion", securityQuestionSelected);
            userValues.put("securityAnswer", securityAnswerEditText.getText().toString());
            userValues.put("exerciseIndex", exerciseRating.getRating());
            Uri userUri = getContentResolver().insert(UserDBContentProvider.USER_URI, userValues);

            userCursor= getContentResolver().query(UserDBContentProvider.USER_URI, null, null, null, null);
            if (userCursor!=null && userCursor.moveToFirst()) {
                username = userCursor.getString(userCursor.getColumnIndex("username"));
                    userInfoSaved = true;
                    Toast.makeText(this, "user saved with: " + String.format("username= %s", username), Toast.LENGTH_SHORT).show();
                }
            }

    }
}
