package com.example.promenadevt;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.content.Intent;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

	Button btnLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnLogin = (Button)findViewById(R.id.Button01);
    
        btnLogin.setOnClickListener(new View.OnClickListener(){
        	
        	@Override
        	public void onClick(View view){
        		Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(i);
        	
        	}
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}
