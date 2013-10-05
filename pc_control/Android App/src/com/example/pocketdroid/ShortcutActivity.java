package com.example.pocketdroid;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.net.URLConnection;

import MyPack.CommandDB;
import MyPack.SingleCommand;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v4.app.NavUtils;

public class ShortcutActivity extends Activity {
	ListView lv;
	TextView tvsel;
	CommandDB cdb;
	String[] names, descs;
	int selectedCommand = -1;
	String servletPath = "";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shortcut);
        Bundle extras = getIntent().getExtras();
        if(extras != null){
        	servletPath = extras.getString("sp");
        }
        tvsel = (TextView)findViewById(R.id.tvSelSkt);
        lv = (ListView)findViewById(R.id.lvskts);
        cdb = new CommandDB();
        readFromFile();
        if(cdb == null){
        	cdb = new CommandDB();
        }
        reFillList();
        
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        	@Override
        	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3) {
        		// TODO Auto-generated method stub
        		selectedCommand = arg2;
        		tvsel.setText("Selected Command : " + cdb.myList.elementAt(selectedCommand).commandName);
        		Toast.makeText(arg0.getContext(), "Selected Command : " + cdb.myList.elementAt(selectedCommand).commandName, Toast.LENGTH_SHORT).show();
        	}
		});
    }

    void reFillList(){
    	names = new String[cdb.myList.size()];
    	descs = new String[cdb.myList.size()];
    	String d = "";
    	for(int i=0;i<cdb.myList.size();i++){
    		names[i] = cdb.myList.elementAt(i).commandName;
    		descs[i] = cdb.myList.elementAt(i).url;
    	}
    	
    	lv.setAdapter(new MyArrayAdapter(ShortcutActivity.this, names, descs));
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_shortcut, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent myIntent = null;
        String[] sarr;
        switch (item.getItemId()) {
        case R.id.AddShortcut:
        	myIntent = new Intent(this,AddShortcutActivity.class);
        	startActivityForResult(myIntent, 1);
            return true;
        case R.id.RemoveShortcut:
        	if(selectedCommand != -1){
        		cdb.myList.removeElementAt(selectedCommand);
        		writeToFile();
        		readFromFile();
        		reFillList();
        	}
            return true;
        case R.id.PropogateShortcut:
        	callServlet(cdb.myList.elementAt(selectedCommand));
        	return true;
        case R.id.Closeme:
            finish();
        }
        return false;
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	// TODO Auto-generated method stub
    	super.onActivityResult(requestCode, resultCode, data);
    	switch (requestCode) {
			case (1): {
				if (resultCode == Activity.RESULT_OK) {
					// TODO Extract the data returned from the child Activity.
					Bundle ext = data.getExtras();
					int [] thr = new int[8];
					SingleCommand sc = (SingleCommand)ext.getSerializable("cmd");
					cdb.myList.add(sc);
					writeToFile();
					readFromFile();
					reFillList();
				}
				break;
			}
    	}
    }
    
    void callServlet(SingleCommand cmd){
    	
    	String ret = "Error connecting to server.";
    	
    	try{
            String urlstr = servletPath + "PropogeteSkt";
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
    
    void writeToFile(){
		try{
			FileOutputStream fos = openFileOutput("cmd_file2", Context.MODE_PRIVATE);
    		ObjectOutputStream oo = new ObjectOutputStream(fos);
    		oo.writeObject(cdb);
    		oo.close();
		}catch (Exception e) {
			// TODO: handle exception
			Log.i("ERROR", "Error Writing file : " + e.getMessage());
		}
	}
	void readFromFile(){
		try{
			FileInputStream fin = openFileInput("cmd_file2");
    		ObjectInputStream oi = new ObjectInputStream(fin);
    		cdb = (CommandDB)oi.readObject();
    		oi.close();
		}catch (Exception e) {
			// TODO: handle exception
			Log.i("ERROR", "Error Reading file : " + e.getMessage());
		}
	}

}
