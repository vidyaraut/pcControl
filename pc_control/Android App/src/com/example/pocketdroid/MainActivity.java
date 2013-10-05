package com.example.pocketdroid;

import it.imgview.android.library.imagezoom.ImageViewTouch;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.ByteBuffer;
import java.util.Calendar;

import MyPack.MyImage;
import MyPack.SingleMouse;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.support.v4.app.NavUtils;

public class MainActivity extends Activity {

	ImageViewTouch mImage;
	boolean Update = false;
	BitmapDrawable bd;
	ByteBuffer bb;
	String servletPath = "";
	boolean once = false;
	int touchX, touchY;
	boolean mouseEnabled = false;
	Paint mousePaint;
	Handler myHandler;
	int cancel=0, closeTimer=0;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature( Window.FEATURE_NO_TITLE );
        setContentView(R.layout.activity_main);
        
        mousePaint = new Paint();
        mousePaint.setColor(Color.BLACK);
        mousePaint.setStyle(Paint.Style.STROKE);
        
        mImage = (ImageViewTouch) findViewById( R.id.image );
        bb = ByteBuffer.allocate((1000 * 1000 * 4));
        Bundle extras = getIntent().getExtras();
        if(extras != null){
        	servletPath = extras.getString("sp");
        }
        
        refreshImage();
		mImage.setOnTouchListener(new View.OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				
				int[] values = new int[2];
	            //v.getLocationOnScreen(values);
	            //v.getLocationInWindow(values);
				float f = mImage.getScale();
				
				RectF rect =  mImage.mBitmapRect;

				if(event.getAction()!=1) {
					return false;
				}
				
				
				
				double xS = rect.left;
				double yS = rect.top;
				
				double xM = event.getX();
				double yM = event.getY();
				
				double xE = xM - xS;
				double yE = yM - yS;
				
				
				double xScale = 640. / rect.width();

				xE = xE * xScale;
				yE = yE * xScale;
				
				if(xE < 0 || xE >= 640 || yE < 0 || yE >= 480) return false;
				
				System.out.println("4> " + xE + " " + yE);
				if(mouseEnabled){
					touchX = (int)xE;
					touchY = (int)yE;
					SingleMouse m = new SingleMouse(touchX,touchY,0);
					propogeteMouse(m);
				}
				return false;
			}
		});
		
		myHandler = new Handler(Looper.getMainLooper());
		myHandler.post(new Runnable() {
		    public void run()
		    {
		    	if(closeTimer == 1){
		    		return;
		    	}
		    	if(cancel == 1){
		    		myHandler.postAtTime(this, SystemClock.uptimeMillis() + 2500);
		    	}else{
		        	refreshImage();
		        	myHandler.postAtTime(this, SystemClock.uptimeMillis() + 2500);
		        }
		    }
		});
		
    }
    

    @Override
    protected void onRestart() {
    	// TODO Auto-generated method stub
    	System.out.println("Resuming......");
    	cancel = 0;
    	super.onResume();
    }
    
    public void refreshImage() {
    	try {
			String urlstr = servletPath + "FetchImage";
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
			out.writeObject("");
			out.close();

			ObjectInputStream in = new ObjectInputStream(connection.getInputStream());
			MyImage mi = (MyImage)in.readObject();
			in.close();
			
			Bitmap b = Bitmap.createBitmap(mi.img, 0, mi.ww, mi.ww, mi.hh, Config.ARGB_8888);
			if(mouseEnabled){
				Canvas c = new Canvas(b);
				c.drawRect(new Rect(touchX-2, touchY-2, touchX + 2, touchY + 2), mousePaint);
			}
			
			if(!once){
				once = true;
				bd = new BitmapDrawable(b);
				mImage.setImageDrawable(bd);
			}else{
				BitmapDrawable bd2 = new BitmapDrawable(b);//imgUrl.openStream());
				bb.clear();
				bd2.getBitmap().copyPixelsToBuffer(bb);
				bd.getBitmap().copyPixelsFromBuffer(bb);
				
				float targetScale = mImage.getScale();
				mImage.mCurrentScaleFactor = targetScale;
				mImage.zoomToNew(targetScale, 100, 100);
				
				mImage.onZoomAnimationCompleted( mImage.getScale() );
//				mImage.center( true, true );
				mImage.invalidate();
			}
        }catch (Exception e) {
			// TODO: handle exception
        	System.out.println("Error : " + e);
        	e.printStackTrace();
		}
    	
		
		
	}
    public void propogeteMouse(SingleMouse m) {
    	try {
			String urlstr = servletPath + "PropogateMouse";
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
			out.writeObject(m);
			out.close();

			ObjectInputStream in = new ObjectInputStream(connection.getInputStream());
			String ret = (String)in.readObject();
			in.close();
			
        }catch (Exception e) {
			// TODO: handle exception
        	System.out.println("Error : " + e);
        	e.printStackTrace();
		}
	}
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent myIntent = null;
        String[] sarr;
        SingleMouse m;
        switch (item.getItemId()) {
        case R.id.KeyBoardSKT:
        	cancel = 1;
        	myIntent = new Intent(this,KeyBoardActivity.class);
        	myIntent.putExtra("sp", servletPath);
        	startActivityForResult(myIntent, 1);
            return true;
        case R.id.textonfly:
        	cancel = 1;
        	myIntent = new Intent(this,PropogateTextActivity.class);
        	myIntent.putExtra("sp", servletPath);
        	startActivityForResult(myIntent, 1);
            return true;
        case R.id.mnuApp_skts:
        	cancel = 1;
        	myIntent = new Intent(this,ShortcutActivity.class);
        	myIntent.putExtra("sp", servletPath);
        	startActivityForResult(myIntent, 1);
            return true;
        case R.id.mnu_EnableMouse:
        	mouseEnabled = true;
            return true;
        case R.id.mnu_disableMouse:
        	mouseEnabled = false;
            return true;
        case R.id.mnu_fetchImage:
        	refreshImage();
            return true;
        case R.id.mnu_Click:
        	m = new SingleMouse(0, 0, 1);
        	propogeteMouse(m);
            return true;
        case R.id.mnu_doubleClick:
        	m = new SingleMouse(0, 0, 2);
        	propogeteMouse(m);
            return true;
        case R.id.mnu_RightClick:
        	m = new SingleMouse(0, 0, 3);
        	propogeteMouse(m);
        	refreshImage();
            return true;
        case R.id.mnu_MousePress:
        	m = new SingleMouse(0, 0, 4);
        	propogeteMouse(m);
        	refreshImage();
            return true;
        case R.id.mnu_mouseRelease:
        	m = new SingleMouse(0, 0, 5);
        	propogeteMouse(m);
        	refreshImage();
            return true;
        }
        
        return false;
    }
    @Override
    public void onBackPressed() {
    	// TODO Auto-generated method stub
    	closeTimer = 1;
    	super.onBackPressed();
    }
}
