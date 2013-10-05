package com.example.pocketdroid;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Vector;

import MyPack.SingleCommand;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.support.v4.app.NavUtils;

public class PropogateTextActivity extends Activity {
	EditText etText;
	String servletPath = "";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_propogate_text);

        Bundle extras = getIntent().getExtras();
        if(extras != null){
        	servletPath = extras.getString("sp");
        }
        
        etText = (EditText)findViewById(R.id.etText);

    }

    void callServlet(SingleCommand cmd){
    	
    	String ret = "Error connecting to server.";
    	
    	try{
            String urlstr = servletPath + "PropogateKS";
            URL url = new URL(urlstr);
            URLConnection connection = url.openConnection();

            connection.setDoOutput(true);
            connection.setDoInput(true);

            // don't use a cached version of URL connection
            connection.setUseCaches(false);
            connection.setDefaultUseCaches(false);
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            // specify the content type that binary data is sent
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            ObjectOutputStream out = new ObjectOutputStream(connection.getOutputStream());
            // send and serialize the object
            out.writeObject(cmd);
            out.close();

            // define a new ObjectInputStream on the input stream
            ObjectInputStream in = new ObjectInputStream(connection.getInputStream());
            // receive and deserialize the object, note the cast
            ret = (String)in.readObject();
            in.close();

        }catch(Exception e) {
        	Log.i("ERROR", e.getMessage());
            System.out.println("E:" + e);
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_about, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent myIntent = null;
        String[] sarr;
        switch (item.getItemId()) {
        case R.id.FlyPropogate:
        	SingleCommand cmd = new SingleCommand();
			String s = etText.getText().toString();
			char[] ch = s.toCharArray();
			cmd.command = new Vector<Character>();
			for(int i=0;i<ch.length;i++){
				cmd.command.add(ch[i]);
			}
			cmd.commandName = "";
			callServlet(cmd);
            return true;
        case R.id.flyBack:
        	Intent ii = new Intent();
	  		setResult(Activity.RESULT_OK, ii);
	  		finish();
            return true;
        }
        return false;
    }
    @Override
    public void onBackPressed() {
    	// TODO Auto-generated method stub
    	super.onBackPressed();
    	Intent ii = new Intent();
  		setResult(Activity.RESULT_OK, ii);
    }
}
