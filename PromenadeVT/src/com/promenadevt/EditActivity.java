package com.promenadevt;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
 
import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.promenadevt.android.R;
import com.promenadevt.library.DatabaseHandler;
import com.promenadevt.library.UserFunctions;

public class EditActivity extends Activity 
{
    EditText inputName;
	Button btnChangeName;
	Button btnTakePhoto;
	Button btnAddConnection;
	Button btnViewRoom;
	Button btnDelete;
	
	 private static String KEY_IDPROPERTY = "idProperty";

	private static String username;
	private static String roomName;
	private static String dbID;
	private static String propID;
	private static String addr;
	
	int CAMERA_PIC_REQUEST = 1337; 
	
	private void getPano(){
		//Intent takePanoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		//startActivityForResult(takePanoIntent,CAMERA_PIC_REQUEST);
		
		Intent res = new Intent();
	    String mPackage = "com.google.android.gallery3d";
	    String mClass = "com.google.android.apps.lightcycle.ProtectedPanoramaCaptureActivity";

	    res.setComponent(new ComponentName(mPackage,mClass));
	    startActivityForResult(res,CAMERA_PIC_REQUEST);
		
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data){
		if( requestCode == CAMERA_PIC_REQUEST){
			Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
			ImageView image =(ImageView) findViewById(R.id.PhotoCaptured);
			image.setImageBitmap(thumbnail);
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
	
	 @Override
	    public boolean onKeyDown(int keyCode, KeyEvent event)  {
	        if (keyCode == KeyEvent.KEYCODE_BACK ) {
	            // do something on back.
	        	//DatabaseHandler db = new DatabaseHandler(getApplicationContext());
	        	Intent rooms = new Intent(getApplicationContext(), RoomsActivity.class);
	        	//HashMap<String, String> loginInfo = db.getUserDetails();
	        	rooms.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	        	rooms.putExtra("id",propID);
	        	rooms.putExtra("addr", addr);
	            startActivity(rooms);
	            return true;
	        }

	        return super.onKeyDown(keyCode, event);
	    }
	
	@Override
    public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.edit_room);
		
		
		// may need to account for newly registered user here
		Intent intent = getIntent();
		// pull info from previous page
		username = intent.getStringExtra("user");
		roomName = intent.getStringExtra("name");
		propID = intent.getStringExtra("propID");
		addr = intent.getStringExtra("addr");
		inputName = (EditText) findViewById(R.id.nameRoom);
		inputName.setText(roomName);
		dbID = intent.getStringExtra("id");
		
		btnChangeName = (Button) findViewById(R.id.btnUpdateR);
		btnTakePhoto = (Button) findViewById(R.id.btnPhoto);
		btnAddConnection = (Button) findViewById(R.id.btnConnection);
		btnViewRoom = (Button) findViewById(R.id.btnView);
		btnDelete = (Button) findViewById(R.id.btnDelete);
    	
        btnChangeName.setOnClickListener(new View.OnClickListener() 
		{

			@Override
			public void onClick(View arg0) {
				// change room name in database
				UserFunctions userFunction = new UserFunctions();
				String newName = inputName.getText().toString();
				userFunction.renameRoom(dbID,newName);

			}
			 
		 });
       
        btnTakePhoto.setOnClickListener(new View.OnClickListener() 
		{

			@Override
			public void onClick(View arg0) {
				getPano();
				
			}
			 
		 });
        
        btnAddConnection.setOnClickListener(new View.OnClickListener() 
		{

			@Override
			public void onClick(View arg0) {
				// go to screen to add connections to room
				
			}
			 
		 });
        
        btnViewRoom.setOnClickListener(new View.OnClickListener() 
		{

			@Override
			public void onClick(View arg0) {
				// view room as it is now
				
			}
			 
		 });
        
        btnDelete.setOnClickListener(new View.OnClickListener() 
		{

			@Override
			public void onClick(View arg0) {
				// view room as it is now
				//boolean cont = confirm();
				if(true)//cont)
				{
					//userFunctions.deleteProperty(dbID);
	                Intent rooms = new Intent(getApplicationContext(), RoomsActivity.class);
	                rooms.putExtra("name",username);
	                rooms.putExtra("id",propID);
		        	rooms.putExtra("addr", addr);
	                
	                startActivity(rooms);
				}
				
			}
			 
		 });
		
		 
	}
}

