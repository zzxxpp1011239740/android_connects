package com.example.connect;

import java.io.DataOutputStream;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {
	
	public static Button connect_id;
	public static String su ="su";
	public static String set ="setprop service.adb.tcp.port 5555\n";
	public static String stop ="stop adbd\n";
	public static String start="start adbd\n";
	public static final String TAG="MainActivity";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		connect_id =(Button)findViewById(R.id.connect_id);
		connect_id.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				connect_id.setText("connecting....");
				run();
				
			}
		});
	}
	
	public static void run(){
		try{
			Process process = Runtime.getRuntime().exec(su);
			DataOutputStream dataOutputStream = new DataOutputStream(process.getOutputStream());
			dataOutputStream.write(set.getBytes());
			dataOutputStream.write(stop.getBytes());
			dataOutputStream.write(start.getBytes());
			dataOutputStream.flush();
			dataOutputStream.close();
			if(process.waitFor()==0){
				if(process.exitValue()==0){
					Log.d(TAG, "success");
					connect_id.setText("connect success");
				}else{
					Log.d(TAG, "error");
					connect_id.setText("connect error");
				}
			}else{
				Log.d(TAG, "error");
				connect_id.setText("connect error");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
