package xwang.george.foodpro2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends Activity {
    private TextView helloUser;
    private ImageView mainImage;
    private String username;
    private Button btnToMainMenu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        helloUser = (TextView) findViewById(R.id.helloUser);
        username = this.getIntent().getStringExtra("userName");
        helloUser.setText("Hello" + " " + username);
        mainImage = (ImageView) findViewById(R.id.mainImage);
        mainImage.setImageResource(R.drawable.main_image);
        btnToMainMenu = (Button) findViewById(R.id.btnToMainMenu);
        btnToMainMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toMenuIntent = new Intent(MainActivity.this, AtyMainMenu.class);
                startActivity(toMenuIntent);
            }
        });
    }
    public void logout(View view){
        Intent logout = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(logout);
    }
}
