package com.example.pocketdroid;

import java.io.InputStream;
import java.net.URL;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MyArrayAdapter extends ArrayAdapter<String> {
	 private final Activity context;
	 private final String[] names;
	 private final String[] descs;
	 public MyArrayAdapter(Activity context, String[] names,String[] descs) {
		 super(context, R.layout.customlist, names);
		 this.context = context;
		 this.names = names;
		 this.descs=descs;
	 }
	 
	 // static to save the reference to the outer class and to avoid access to
	 // any members of the containing class
	 static class ViewHolder {
		 public TextView textView;
		 public TextView textView2;
		 public TextView textView3;
	 }
	 
	 @Override
	 public View getView(int position, View convertView, ViewGroup parent) {
		 // ViewHolder will buffer the assess to the individual fields of the row
		 // layout
	 
		 ViewHolder holder;
		 // Recycle existing view if passed as parameter
		 // This will save memory and time on Android
		 // This only works if the base layout for all classes are the same
		 View rowView = convertView;
		 if (rowView == null) {
			 LayoutInflater inflater = context.getLayoutInflater();
			 rowView = inflater.inflate(R.layout.customlist, null, true);
			 holder = new ViewHolder();
	 
			 holder.textView = (TextView) rowView.findViewById(R.id.tvName);
			 holder.textView2 = (TextView) rowView.findViewById(R.id.tvDesc);
			 holder.textView3 = (TextView) rowView.findViewById(R.id.tvEmpty);
			 rowView.setTag(holder);
		 } else {
			 holder = (ViewHolder) rowView.getTag();
		 }
	 
		 holder.textView.setText(names[position]);
		 holder.textView2.setText(descs[position]);
		 holder.textView3.setText("");
		 // Change the icon for Windows and iPhone
		 return rowView;
	 }
}