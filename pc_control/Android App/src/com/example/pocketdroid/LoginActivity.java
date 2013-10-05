package com.example.pocketdroid;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Random;
import java.util.StringTokenizer;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v4.app.NavUtils;

public class LoginActivity extends Activity implements OnClickListener{
	Button ok, clear;
	EditText etip, etpass;
	TextView[] tvArr;
	String ip;
	String servletPath = "";
	String[] passVal;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        
        tvArr = new TextView[36];
        tvArr[0] = (TextView)findViewById(R.id.tv00);
        tvArr[1] = (TextView)findViewById(R.id.tv01);
        tvArr[2] = (TextView)findViewById(R.id.tv02);
        tvArr[3] = (TextView)findViewById(R.id.tv03);
        tvArr[4] = (TextView)findViewById(R.id.tv04);
        tvArr[5] = (TextView)findViewById(R.id.tv05);
        
        tvArr[6] = (TextView)findViewById(R.id.tv10);
        tvArr[7] = (TextView)findViewById(R.id.tv11);
        tvArr[8] = (TextView)findViewById(R.id.tv12);
        tvArr[9] = (TextView)findViewById(R.id.tv13);
        tvArr[10] = (TextView)findViewById(R.id.tv14);
        tvArr[11] = (TextView)findViewById(R.id.tv15);
        
        tvArr[12] = (TextView)findViewById(R.id.tv20);
        tvArr[13] = (TextView)findViewById(R.id.tv21);
        tvArr[14] = (TextView)findViewById(R.id.tv22);
        tvArr[15] = (TextView)findViewById(R.id.tv23);
        tvArr[16] = (TextView)findViewById(R.id.tv24);
        tvArr[17] = (TextView)findViewById(R.id.tv25);
        
        tvArr[18] = (TextView)findViewById(R.id.tv30);
        tvArr[19] = (TextView)findViewById(R.id.tv31);
        tvArr[20] = (TextView)findViewById(R.id.tv32);
        tvArr[21] = (TextView)findViewById(R.id.tv33);
        tvArr[22] = (TextView)findViewById(R.id.tv34);
        tvArr[23] = (TextView)findViewById(R.id.tv35);
        
        tvArr[24] = (TextView)findViewById(R.id.tv40);
        tvArr[25] = (TextView)findViewById(R.id.tv41);
        tvArr[26] = (TextView)findViewById(R.id.tv42);
        tvArr[27] = (TextView)findViewById(R.id.tv43);
        tvArr[28] = (TextView)findViewById(R.id.tv44);
        tvArr[29] = (TextView)findViewById(R.id.tv45);
        
        tvArr[30] = (TextView)findViewById(R.id.tv50);
        tvArr[31] = (TextView)findViewById(R.id.tv51);
        tvArr[32] = (TextView)findViewById(R.id.tv52);
        tvArr[33] = (TextView)findViewById(R.id.tv53);
        tvArr[34] = (TextView)findViewById(R.id.tv54);
        tvArr[35] = (TextView)findViewById(R.id.tv55);
        
        passVal = new String[36];
        for(int i=0;i<26;i++){
        	passVal[i] = "" + ((char)(i+65));
        }
        for(int i=0;i<10;i++){
        	passVal[i+26] = "" + ((char)(i+48));
        }
        
        int cnt=0, temp;
        Random r = new Random();
        boolean flag = false;
        int rndIndices[] = new int[36];
        while(cnt < 36){
        	flag = true;
        	temp = r.nextInt(36);
        	for(int i=0;i<cnt;i++){
        		if(rndIndices[i] == temp){
        			flag = false;
        			break;
        		}
        	}
        	if(flag){
        		rndIndices[cnt] = temp;
        		cnt++;
        	}
        }
        
        for(int i=0;i<36;i++){
        	tvArr[i].setText(passVal[rndIndices[i]]);
        	tvArr[i].setOnClickListener(this);
        }
        
        
        
        etip = (EditText)findViewById(R.id.etEnterIP);
        etpass = (EditText)findViewById(R.id.etEnterPass);
        etpass.setEnabled(false);
        clear = (Button)findViewById(R.id.BtnLoginClear);
        clear.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				etpass.setText("");
			}
		});
        ok = (Button)findViewById(R.id.BtnLoginOK);
        ok.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				ip = etip.getText().toString();
				
				String pass = etpass.getText().toString();
				
				if((pass.length()%2) != 0){
					return;
				}
				String empty = "";
				int i1, i2;
				for(int i=0;i<pass.length();i+=2){
					i1 = getIndexOf("" + pass.charAt(i)) / 6;
					i2 = getIndexOf("" + pass.charAt(i + 1)) % 6;
					empty += tvArr[i1*6+i2].getText().toString();
					System.out.println("I1 : " + i1 + "    I2 : " + i2 + "  " + getIndexOf("" + pass.charAt(i)) + "   " + getIndexOf("" + pass.charAt(i+1)));
				}
				pass = empty;
				servletPath = "http://10.0.2.2:8084/ServletsModule/";
				//etpass.setText(servletPath);
				if(!ip.equals("")){
					servletPath = "http://" + ip + ":8084/ServletsModule/";
				}
				
				String resp = (String)callServlet(pass, "Login");
				if(resp == null){
					Toast.makeText(arg0.getContext(), "Error connecting to server.\nPlease check the entered IP Address.", Toast.LENGTH_SHORT).show();
					return;
				}
				if(resp.equals("")){
					Toast.makeText(arg0.getContext(), "Wrong Password.", Toast.LENGTH_SHORT).show();
					return;
				}
				if(resp.equals("OK")){
					Intent ii = new Intent(arg0.getContext(),MainActivity.class);
					ii.putExtra("sp", servletPath);
					startActivity(ii);
					finish();
				}
				
			}
		});
        
    }

    int getIndexOf(String chr){
    	int ret = -1;
    	for(int i=0;i<36;i++){
    		if(chr.equals(tvArr[i].getText().toString())){
    			ret = i;
    			break;
    		}
    	}
    	return ret;
    }
    
    public void onClick(View v){
    	for(int i=0;i<36;i++){
    		if(v.getId() == tvArr[i].getId()){
    			etpass.setText(etpass.getText().toString() + tvArr[i].getText().toString());
    			break;
    		}
    	}
    }
    
    Object callServlet(Object inp, String servletName){
    	Object ret = null;
    	try {
			String urlstr = servletPath + servletName;
			URL url = new URL(urlstr);
			URLConnection connection = url.openConnection();

			connection.setDoOutput(true);
			connection.setDoInput(true);

			// don't use a cached version of URL connection
			connection.setUseCaches(false);
			connection.setDefaultUseCaches(false);
			connection.setRequestProperty("Content-Type","application/x-www-form-urlencoded");

			// specify the content type that binary data is sent
			connection.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
			ObjectOutputStream out = new ObjectOutputStream(
					connection.getOutputStream());
			// send and serialize the object
			out.writeObject(inp);
			out.close();

			ObjectInputStream in = new ObjectInputStream(connection.getInputStream());
			ret = in.readObject();
			in.close();
			
        }catch (Exception e) {
			// TODO: handle exception
        	System.out.println("Error : " + e);
		}
    	return ret; 
    }
    
}
