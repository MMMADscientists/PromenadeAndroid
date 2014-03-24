package com.example.androidhive;

import java.util.HashMap;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;
 
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.androidhive.library.DatabaseHandler;
import com.example.androidhive.library.UserFunctions;

public class HomesActivity extends Activity 
{
	List<Button> properties;
	Button btnNewProp;
	private static String username;
	
	// JSON Response node names
    private static String KEY_SUCCESS = "success";
    private static String KEY_ERROR = "error";
    private static String KEY_ERROR_MSG = "error_msg";
    private static String KEY_TUPLE = "tuples";
    private static String KEY_IDPROPERTY = "idProperty";
    private static String KEY_ADDRESS = "address";
    private static String KEY_HOUSEURL = "houseUrl";
    private static String KEY_NAME = "name";
	
	@Override
    public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.homes);
		
		
		// may need to account for newly registered user here
		Intent intent = getIntent();
		username = intent.getStringExtra("name");// pull from previous page
		//dbID = intent.getStringExtra("id");
		
		// make database call
		UserFunctions userFunction = new UserFunctions();
        	JSONObject json = userFunction.getHomes(username);
        	
        	//lists of recieved data
        	List<int> idProperty = new List<int>();
        	List<String> address = new List<String>();
        	List<String> houseURL = new List<String>();
        	
        	 try {
                    if (json.getString(KEY_SUCCESS) != null) {
                        //loginErrorMsg.setText("");
                        String res = json.getString(KEY_SUCCESS); 
                        if(Integer.parseInt(res) == 1){
                        	JSONArray tuples = json.getJSONArray(KEY_TUPLE);
                        	for int i = 0; i< tuples.length(); i++){
                        		JSONObject curTuple = tuples.get(i);
                        		idProperty.add(curTuple.getInt(KEY_IDPROPERTY);
                        		address.add(curTuple.getString(KEY_ADDRESS);
                        		houseURL.add(curTuple.getString(KEY_HOUSEURL);
                        	}
                        }
                            
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
         
        
		// fill xml based on db call
        LinearLayout ll = (LinearLayout) findViewById(R.id.homesLayout);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        
        // may need separate file
        for(int i = 0; i < idProperty.length(); i++)
        {
        	
            Button btn = new Button(this);
            btn.setId(i);
            final int id_ = btn.getId();
            // set text to address
            btn.setText("property " + id_);
            ll.addView(btn, params);
            // fill properties based on db call
            properties.add((Button) findViewById(id_));
        }
        */
        
        Button btn = new Button(this);
        btn.setId(idProperty.length());
        btn.setText("Add new property +");
        ll.addView(btn, params);
        btnNewProp = (Button) findViewById(idProperty.length());
       
		
		
		for(int i = 0; i < properties.size(); i++)
		{
			 properties.get(i).setOnClickListener(new View.OnClickListener() 
			 {

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					
				}
				 
			 });
		}
		
		 btnNewProp.setOnClickListener(new View.OnClickListener() 
		 {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i = new Intent(getApplicationContext(),
                        RoomsActivity.class);
				//i.putExtra("addr", value);
				//i.putExtra("id",value);
                startActivity(i);
			}
			 
		 });
	}
}
