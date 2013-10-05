package com.example.pocketdroid;


import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.support.v4.app.NavUtils;

public class WelcomeActivity extends Activity {
	Button ok;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        
        ok = (Button)findViewById(R.id.btnWelcomeOK);
        ok.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent ii = new Intent(arg0.getContext(),AboutActivity.class);
				startActivity(ii);
				finish();
			}
		});
        
    }

    
}
