package com.example.pocketdroid;

import java.util.Vector;

import MyPack.SingleCommand;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.support.v4.app.NavUtils;

public class AddCommandActivity extends Activity {
	Vector<Integer> splKeys;
	Vector<String> lm;
    int commandIndex = -1;
    SingleCommand newCommand;
    
    ListView lvAllOps;
    Button press, save;
    EditText et;
    TextView tvSel, tvselops;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_command);
        
        et = (EditText)findViewById(R.id.etCmdName);
        lvAllOps = (ListView)findViewById(R.id.lVAddCmd);
        tvselops = (TextView)findViewById(R.id.tvselectedcmd);
        tvSel = (TextView)findViewById(R.id.tvSelected);
        
        press = (Button)findViewById(R.id.btnPress);
        save = (Button)findViewById(R.id.btnSave);
         
        lvAllOps.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        	@Override
        	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3) {
        		// TODO Auto-generated method stub
        		commandIndex = arg2;
        		tvSel.setText("Selected  : " + lm.elementAt(commandIndex));
        	}
		});
        
        press.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(commandIndex != -1){

					newCommand.command.addElement((char)(int)(splKeys.elementAt(commandIndex)));
			        newCommand.desc.addElement(lm.elementAt(commandIndex));
					fillCmdList();
				}
			}
		});
        
        save.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(commandIndex != -1){
					newCommand.commandName = et.getText().toString();
					Intent ii = new Intent();
			  		ii.putExtra("cmd",newCommand);
			  		setResult(Activity.RESULT_OK, ii);
			  		finish();
				}
			}
		});

        splKeys = new Vector<Integer>();
        newCommand = new SingleCommand();
        lm = new Vector<String>();
        
        for(int i=0;i<26;i++){
            lm.addElement("" + ((char)(i+65)));
            splKeys.addElement(i+65);
        }
        for(int i=0;i<10;i++){
            lm.addElement("" + ((char)(i+48)));
            splKeys.addElement(i+65);
        }
        splKeys.addElement(18);
        lm.addElement("ALT");
        splKeys.addElement(17);
        lm.addElement("CONTROL");
        splKeys.addElement(16);
        lm.addElement("SHIFT");
        splKeys.addElement(8);
        lm.addElement("BACK_SPACE");
        splKeys.addElement(20);
        lm.addElement("CAPS_LOCK");
        splKeys.addElement(127);
        lm.addElement("DELETE");
        splKeys.addElement(10);
        lm.addElement("ENTER");
        splKeys.addElement(27);
        lm.addElement("ESCAPE");
        splKeys.addElement(112);
        lm.addElement("F1");
        splKeys.addElement(113);
        lm.addElement("F2");
        splKeys.addElement(114);
        lm.addElement("F3");
        splKeys.addElement(115);
        lm.addElement("F4");
        splKeys.addElement(116);
        lm.addElement("F5");
        splKeys.addElement(117);
        lm.addElement("F6");
        splKeys.addElement(118);
        lm.addElement("F7");
        splKeys.addElement(119);
        lm.addElement("F8");
        splKeys.addElement(120);
        lm.addElement("F9");
        splKeys.addElement(121);
        lm.addElement("F10");
        splKeys.addElement(122);
        lm.addElement("F11");
        splKeys.addElement(123);
        lm.addElement("F12");
        splKeys.addElement(32);
        lm.addElement("SPACE");
        splKeys.addElement(38);
        lm.addElement("UP");
        splKeys.addElement(40);
        lm.addElement("DOWN");
        splKeys.addElement(37);
        lm.addElement("LEFT");
        splKeys.addElement(39);
        lm.addElement("RIGHT");
        splKeys.addElement(33);
        lm.addElement("PAGE_UP");
        splKeys.addElement(34);
        lm.addElement("PAGE_DOWN");
        
        String[] descs = new String[lm.size()]; 
        
        for(int i=0;i<lm.size();i++){
            descs[i] = lm.elementAt(i);
        }
        
        lvAllOps.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, descs));
        
    }
    void fillCmdList(){
    	String str = "";
    	for(int i=0;i<newCommand.desc.size();i++){
            str += newCommand.desc.elementAt(i) + " + ";
        }
    	str = str.substring(0,str.length()-3);
    	tvselops.setText(str);
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
