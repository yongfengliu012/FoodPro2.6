package xwang.george.foodpro2;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    private EditText loginUser;
    private EditText loginPassword;
    private Cursor loginCursor;
    private Boolean loginValid = false;
    private int failedLogin = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginUser= (EditText) findViewById(R.id.loginUser);
        loginPassword = (EditText) findViewById(R.id.loginPassword);
        findViewById(R.id.btnFindUsernamePassword).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginCursor = getContentResolver().query(UserDBContentProvider.USER_URI, null, null, null, null);
                if(loginCursor!=null && loginCursor.moveToFirst()){
                    String username = loginCursor.getString(loginCursor.getColumnIndex("username"));
                   // String password = loginCursor.getString(loginCursor.getColumnIndex("password"));
                    if(username.equals(loginUser.getText().toString()) ){
                        Intent toFindPasswordPage = new Intent(LoginActivity.this, AtyFindPassword.class);
                        startActivity(toFindPasswordPage);
                    }else{
                        Toast.makeText(LoginActivity.this, "you must enter correct username proceed", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
    public void tryLogin(View view){
        loginCursor = getContentResolver().query(UserDBContentProvider.USER_URI, null, null, null, null);
        if(loginCursor!=null && loginCursor.moveToFirst()){
            String username = loginCursor.getString(loginCursor.getColumnIndex("username"));
            String password = loginCursor.getString(loginCursor.getColumnIndex("password"));
            if(username.equals(loginUser.getText().toString()) && password.equals(loginPassword.getText().toString())){
                loginValid = true;
            }
        }
        if(loginValid) {
            failedLogin = 0;
            Toast.makeText(this, "login is successful", Toast.LENGTH_SHORT).show();
            Intent toMainPage = new Intent(LoginActivity.this, MainActivity.class);
            String userName = loginUser.getText().toString();
            toMainPage.putExtra("userName", userName);
            startActivity(toMainPage);
        }else{
            Toast.makeText(this, "username not match with password. Login failed", Toast.LENGTH_SHORT).show();
            loginUser.setText("");
            loginPassword.setText("");
            failedLogin++;
            try{
                Thread.sleep(1000 * failedLogin );
            }catch (InterruptedException exception){
                exception.printStackTrace();
            }
        }
    }

    public void registerUser(View view){
        loginCursor = getContentResolver().query(UserDBContentProvider.USER_URI, null, null, null, null);
        if(loginCursor!=null && loginCursor.moveToFirst()){
            String username = loginCursor.getString(loginCursor.getColumnIndex("username"));
            String password = loginCursor.getString(loginCursor.getColumnIndex("password"));
            if(!username.equals("") && !password.equals("")){
                Toast.makeText(LoginActivity.this, "username already exist, please login with it", Toast.LENGTH_SHORT).show();
            }
        }else {
            Intent toRegisPage = new Intent(LoginActivity.this, UserRegister.class);
            startActivity(toRegisPage);
        }

    }
}
