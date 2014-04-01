package com.example.androidhive;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
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

public class RoomsActivity extends Activity 
{
	List<Button> rooms;
	Button btnNewRoom;
	Button btnChangeAddr;
	Button btnLogout;
    EditText inputAddr;
    
    UserFunctions userFunctions;
	
	private static String address;
	private static String dbID;
	
	private static String KEY_SUCCESS = "success";
    private static String KEY_ERROR = "error";
    private static String KEY_ERROR_MSG = "error_msg";
    private static String KEY_TUPLE = "tuples";
    private static String KEY_IDROOM = "idRoom";
    private static String KEY_ROOMNAME = "NameRoom";
	
	@Override
    public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.rooms);
		
		rooms = new ArrayList<Button>();
		
		
		// may need to account for newly registered user here
		Intent intent = getIntent();
		// pull info from previous page
		address = intent.getStringExtra("addr");
		inputAddr = (EditText) findViewById(R.id.addr);
		inputAddr.setText(address);
		
		userFunctions = new UserFunctions();
		
		dbID = intent.getStringExtra("id");
		
		btnChangeAddr = (Button) findViewById(R.id.btnUpdateP);
		
		// make database call
		final UserFunctions userFunction = new UserFunctions();
		JSONObject json = userFunction.getRooms(dbID);
    	
    	//lists of recieved data
    	List<Integer> idRoom = new ArrayList<Integer>();
    	List<String> roomName = new ArrayList<String>();
    	
    	try {
                if (json.getString(KEY_SUCCESS) != null) {
                    //loginErrorMsg.setText("");
                    String res = json.getString(KEY_SUCCESS); 
                    if(Integer.parseInt(res) == 1){
                    	JSONArray tuples = json.getJSONArray(KEY_TUPLE);
                    	for(int i = 0; i< tuples.length(); i++){
                    		JSONObject curTuple = tuples.getJSONObject(i);
                    		idRoom.add(curTuple.getInt(KEY_IDROOM));
                    		roomName.add(curTuple.getString(KEY_ROOMNAME));
                    	}
                    }
                        
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        
		// fill xml based on db call
        LinearLayout ll = (LinearLayout) findViewById(R.id.roomsLayout);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        
        // may need separate file
        for(int i = 0; i < idRoom.size(); i++)
        {
        	
            Button btn = new Button(this);
            btn.setId(i);
            final int id_ = btn.getId();
            // set text to address
            btn.setText(roomName.get(i).toString());
            ll.addView(btn, params);
            // fill properties based on db call
            rooms.add((Button) findViewById(id_));
        }
        
        //new room button
        Button newRoom = new Button(this);
        newRoom.setId(idRoom.size());
        newRoom.setText("Add new room +");
        ll.addView(newRoom, params);
        btnNewRoom = (Button) findViewById(idRoom.size());
        
        //logout button
        Button logout = new Button(this);
        logout.setId(idRoom.size()+1);
        logout.setText("Logout");
        ll.addView(logout, params);
        btnLogout = (Button) findViewById(idRoom.size()+1);
       
        btnChangeAddr.setOnClickListener(new View.OnClickListener() 
		{

			@Override
			public void onClick(View arg0) {
				// change address name in database
				String newAddr = inputAddr.getText().toString();
				userFunction.renameHome(dbID,newAddr);
			}
			 
		 });
		
		for(int i = 0; i < rooms.size(); i++)
		{
			final String name = roomName.get(i);
			final int id = idRoom.get(i);
			 rooms.get(i).setOnClickListener(new View.OnClickListener() 
			 {

				@Override
				public void onClick(View arg0) {
					//  go to next page with given room selected
					/*Intent next = new Intent(getApplicationContext(),
	                        EditActivity.class);
					next.putExtra("name", name);
					next.putExtra("id",id);
	                startActivity(next);*/
				}
				 
			 });
		}
		
		 btnNewRoom.setOnClickListener(new View.OnClickListener() 
		 {

			@Override
			public void onClick(View arg0) {
				// create new room and go to edit page
				/* int value = userFunctions.newRoom(dbID);
				
				Intent next = new Intent(getApplicationContext(),
                        EditActivity.class);
				next.putExtra("name", "New Room");
				next.putExtra("id",value);
                startActivity(next);*/
				
			}
			 
		 });
		 
		 btnLogout.setOnClickListener(new View.OnClickListener() 
		 {

			@Override
			public void onClick(View arg0) {
				userFunctions.logoutUser(getApplicationContext());
                Intent login = new Intent(getApplicationContext(), LoginActivity.class);
                login.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(login);
                // Closing rooms screen
                finish();
				
			}
			 
		 });
	}
}