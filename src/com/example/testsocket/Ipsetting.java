package com.example.testsocket;

//import com.example.smart.useractivity.light1button;
//import com.example.smart.useractivity.monitor;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Ipsetting extends FullscreenActivity {

	private Button sure,back;
	private EditText edtip,edtport;
	private TextView textip,textdk;
	
	SharedPreferences sp;
	private String TAG="=Setting=";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_ipsetting);
	    
	    sure = (Button)findViewById(R.id.sure);
	    sure.setOnClickListener(new sure());
		back = (Button)findViewById(R.id.back);
		back.setOnClickListener(new back());
		sure.setBackgroundDrawable(null);
		sure.setTextColor(0xffffffff);
		back.setBackgroundDrawable(null);
		back.setTextColor(0xffffffff);
		edtip = (EditText)findViewById(R.id.ip);
		edtport = (EditText)findViewById(R.id.windows);
		edtip.setTextColor(0xffffffff);
		edtport.setTextColor(0xffffffff);
		edtip.setBackgroundDrawable(null);
		edtport.setBackgroundDrawable(null);

		sp = this.getSharedPreferences("SP", MODE_PRIVATE);
		//下面这两行应该是读取上次保存的IP 和 PORT 
		edtip.setText(sp.getString("ipstr", ""));
		edtport.setText(sp.getString("port", ""));
		
	}
	class sure implements OnClickListener{
		
		public void onClick(View v) {
			
			//Log.i(TAG,"开始修改");
			String ip = edtip.getText().toString();//
			String port = edtport.getText().toString();//
			Editor editor = sp.edit();
			editor.putString("ipstr", ip);
			editor.putString("port", port);
			editor.commit();//保存新数据
		//	Log.i(TAG, "保存成功"+sp.getString("ipstr", "")+";"+sp.getString("port", ""));
			
			
			Intent intent = new Intent();
			intent.setClass(Ipsetting.this, FullscreenActivity.class);
			startActivity(intent);
			Ipsetting.this.finish();
			
			Toast sure = Toast.makeText(Ipsetting.this, "保存成功", Toast.LENGTH_LONG);
			sure.show();
		}
	}
	class back implements OnClickListener{
		
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent = new Intent();
			intent.setClass(Ipsetting.this, FullscreenActivity.class);
			startActivity(intent);
			Ipsetting.this.finish();
		}
	}
}
