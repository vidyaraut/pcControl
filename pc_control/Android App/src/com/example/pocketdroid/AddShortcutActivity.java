package com.example.pocketdroid;

import MyPack.SingleCommand;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.support.v4.app.NavUtils;

public class AddShortcutActivity extends Activity {

	SingleCommand newCommand;
    
    Button save;
    EditText etname, etPath;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_shortcut);
        
        newCommand = new SingleCommand();
        
        etname = (EditText)findViewById(R.id.etSktName);
        etPath = (EditText)findViewById(R.id.etSktPath);
        save = (Button)findViewById(R.id.btnSktSave);
        save.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				newCommand.commandName = etname.getText().toString();
				newCommand.url = etPath.getText().toString();
				newCommand.commandType = 1;
				Intent ii = new Intent();
		  		ii.putExtra("cmd",newCommand);
		  		setResult(Activity.RESULT_OK, ii);
		  		finish();
			}
		});
    }

    @Override
    public void onBackPressed() {
    	// TODO Auto-generated method stub
    	
    	Intent ii = new Intent();
  		ii.putExtra("cmd",newCommand);
  		setResult(Activity.RESULT_CANCELED, ii);
  		super.onBackPressed();
  		//finish();
    }
}

