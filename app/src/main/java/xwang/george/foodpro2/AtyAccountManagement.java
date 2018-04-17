package xwang.george.foodpro2;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by george on 10/10/2017.
 */

public class AtyAccountManagement extends Activity {
    private EditText userNameUpdate, passwordInfoUpdate, passwordInfo2Update;
    private Cursor accountManagementCursor;
    private String username;
    private int itemId;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_management);
        accountManagementCursor = getContentResolver().query(UserDBContentProvider.USER_URI, null, null, null, null);
        if(accountManagementCursor!=null && accountManagementCursor.moveToFirst()){
            username = accountManagementCursor.getString(accountManagementCursor.getColumnIndex("username"));
            itemId = accountManagementCursor.getInt(accountManagementCursor.getColumnIndex("_id"));
           // Toast.makeText(AtyAccountManagement.this, " " + username + "" + itemId, Toast.LENGTH_SHORT).show();
        }
        userNameUpdate = (EditText) findViewById(R.id.userNameInfoUpdate);
        passwordInfoUpdate = (EditText) findViewById(R.id.passwordInfoUpdate);
        passwordInfo2Update = (EditText) findViewById(R.id.passwordInfo2Update);
        findViewById(R.id.btnClearUserInfo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(AtyAccountManagement.this).setTitle("Delete User Info?").setMessage("You sure to delete all user info?")
                        .setNegativeButton("No", new AlertDialog.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        }).setPositiveButton("Yes", new AlertDialog.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        getContentResolver().delete(UserDBContentProvider.USER_URI, "_id = ?", new String[]{itemId+""});
                        dialog.cancel();
                        Intent kickoutIntent = new Intent(AtyAccountManagement.this, LoginActivity.class);
                        startActivity(kickoutIntent);
                    }
                }).show();
            }
        });
        findViewById(R.id.btnUserInfoUpdate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!userNameUpdate.getText().toString().equals("") && !passwordInfoUpdate.getText().toString().equals("")
                        && passwordInfoUpdate.getText().toString().equals(passwordInfo2Update.getText().toString())){
                    Toast.makeText(AtyAccountManagement.this, "ready to update", Toast.LENGTH_SHORT).show();
                    ContentValues contentValuesUpdate = new ContentValues();
                    contentValuesUpdate.put("username", userNameUpdate.getText().toString());
                    contentValuesUpdate.put("password", passwordInfoUpdate.getText().toString());
                    getContentResolver().update(UserDBContentProvider.USER_URI, contentValuesUpdate, "_id = ?", new String[]{itemId+""});
                    Cursor newAccountManagementCursor = getContentResolver().query(UserDBContentProvider.USER_URI, null, null, null, null);
                    if(newAccountManagementCursor!=null && accountManagementCursor.moveToFirst()){
                        String newUsername = accountManagementCursor.getString(newAccountManagementCursor.getColumnIndex("username"));
                        Toast.makeText(AtyAccountManagement.this, "user updated with username: " + newUsername, Toast.LENGTH_SHORT).show();
                        Intent toMainPage = new Intent(AtyAccountManagement.this, MainActivity.class);
                        toMainPage.putExtra("userName", newUsername);
                        startActivity(toMainPage);
                    }
                    Toast.makeText(AtyAccountManagement.this, "update successful", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
