package com.example.testsocket;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Timer;
import java.util.TimerTask;

//import com.example.smart.R;
//import com.example.smart.bathactivity;
//import com.example.smart.useractivity;

import android.R.layout;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewTreeObserver.OnTouchModeChangeListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class UserActivity extends FullscreenActivity {

	
	//============================
	private int port = 8888;
	static	public Socket client = null;

	//============================
	private String TAG = "===Client===";
	private String TAG1 = "===Send===";
	Handler m_rev_handler;
	Handler m_sent_handler;
	private String sendstr = "";
	SharedPreferences sp;
	private Context ctx;
		Socket socket;
		PrintWriter out;
		BufferedReader in;
		SocThread socketThread;
		int socketSendFlag ;
	
	/*private void delay(int ms){
		try{
			Thread.currentThread();
			Thread.sleep(ms);
		}catch(InterruptedException e){
			e.printStackTrace();
		}
	}*/
	
	boolean sleeproom = false;
	private int a = 0;
	private int b = 0;
	private int c = 0;
	private int d = 0;
	private int e = 0;
	private int temp = 25;
	private Button light1button,monitor,tvon,water,airdown,airup,aircontrol,suncontrol,sleepcontrol,back1,home,bath,sleep,check;
	private static final int CAMERA_REQUEST = 1888;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user);
		
		ctx = UserActivity.this;
		
		
		//======================
		
		//=======================
		light1button = (Button)findViewById(R.id.light1button);
		light1button.setOnClickListener(new light1button());
		monitor = (Button)findViewById(R.id.monitor);
		monitor.setOnClickListener(new monitor());		
		
		tvon = (Button)findViewById(R.id.tvon);
		tvon.setOnClickListener(new tvon());
		water = (Button)findViewById(R.id.water);
		water.setOnClickListener(new water());
		
		airdown = (Button)findViewById(R.id.airdown);
		airdown.setOnTouchListener(new airdowntouch());
		airup = (Button)findViewById(R.id.airup);
		airup.setOnTouchListener(new airuptouch());
		
		//aircontrol = (Button)findViewById(R.id.aircontrol);
		//aircontrol.setOnClickListener(new aircontrol());
		//suncontrol = (Button)findViewById(R.id.suncontrol);
		//suncontrol.setOnClickListener(new suncontrol());
		//sleepcontrol = (Button)findViewById(R.id.sleepcontrol);
		//sleepcontrol.setOnClickListener(new sleepcontrol());
		aircontrol = (Button)findViewById(R.id.aircontrol);
		aircontrol.setOnClickListener(new clickaircontrol());
		suncontrol = (Button)findViewById(R.id.suncontrol);
		suncontrol.setOnTouchListener(new touchsuncontrol());
		sleepcontrol = (Button)findViewById(R.id.sleepcontrol);
		sleepcontrol.setOnTouchListener(new touchsleepcontrol());

		home = (Button)findViewById(R.id.home);
		home.setOnTouchListener(new touchhome());
		sleep = (Button)findViewById(R.id.sleep);
		sleep.setOnTouchListener(new touchsleep());
		bath = (Button)findViewById(R.id.bath);
		bath.setOnTouchListener(new touchbath());		
		back1 = (Button)findViewById(R.id.back1);
		back1.setBackgroundDrawable(null);
		back1.setOnClickListener(new back());
		
		check = (Button)findViewById(R.id.check);
		check.setOnClickListener(new checkall());
		

		m_rev_handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				try {
	//				MyLog.i(TAG, "m_rev_handler接收到msg=" + msg.what);
					if (msg.obj != null) {
						String s = msg.obj.toString();
						if (s.trim().length() > 0) {
		//					MyLog.i(TAG, "m_rev_handler接收到obj=" + s);
		//					MyLog.i(TAG, "开始更新UI");
							//tv1.append("Server:" + s);
		//					MyLog.i(TAG, "更新UI完毕");
						} else {
							Log.i(TAG, "没有数据返回不更新");
						}
					}
				} catch (Exception ee) {
		//			MyLog.i(TAG, "加载过程出现异常");
					ee.printStackTrace();
				}
			}
		};
		m_sent_handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				try {
		//			MyLog.i(TAG, "m_sent_handler接收到msg.what=" + msg.what);
					String s = msg.obj.toString();
					if (msg.what == 1) {
						//tv1.append("\n ME: " + s + "      发送成功");
					} else {
						//tv1.append("\n ME: " + s + "     发送失败");
					}
				} catch (Exception ee) {
		//			MyLog.i(TAG, "加载过程出现异常");
					ee.printStackTrace();
				}
			}
		};
		
		sp = this.getSharedPreferences("SP", MODE_PRIVATE);
		
		if(socketSendFlag == 0){
			startSocket();
		}
		
}
	
	/*check all*/
	class checkall implements OnClickListener{
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Toast air = Toast.makeText(UserActivity.this, "查看整体监测数据", Toast.LENGTH_LONG);
			air.show();
			Intent intent = new Intent();
			intent.setClass(UserActivity.this, CheckActivity.class);
		//	intent.setClass(UserActivity.this,Client.class);
			
			
			
			startActivity(intent);
			UserActivity.this.finish();
			
			//stopSocket();//跳转到下个界面前，干掉子线程。
		}
	}
	/*All controler*/
    class clickaircontrol implements OnClickListener{
    	@Override
    	public void onClick(View v) {
    		// TODO Auto-generated method stub
    		a = a+1;
    		if(a%2==1){
    			aircontrol.setBackgroundResource(R.drawable.aircontrolon);
    			
    			socketThread.Send("E");
    			
    			Toast air = Toast.makeText(UserActivity.this, "空调开启", Toast.LENGTH_LONG);
				air.show();
    		}else{
    			aircontrol.setBackgroundResource(R.drawable.aircontroloff);
    			
    			socketThread.Send("e");
    			
    			Toast air = Toast.makeText(UserActivity.this, "关闭", Toast.LENGTH_LONG);
				air.show();
    		}
    	}
    }
	class touchsuncontrol implements OnTouchListener{
		@Override
		public boolean onTouch(View v, MotionEvent event) {
			// TODO Auto-generated method stub
			if(event.getAction()==MotionEvent.ACTION_DOWN){
				v.setBackgroundResource(R.drawable.suncontrolon);
				
				socketThread.Send("A");
				socketThread.Send("B");
				socketThread.Send("C");

				
				Toast sun = Toast.makeText(UserActivity.this, "打开所有灯光", Toast.LENGTH_LONG);
				sun.show();
			}
			else if(event.getAction()==MotionEvent.ACTION_UP){
				v.setBackgroundResource(R.drawable.suncontroloff);
				}
			light1button.setBackgroundResource(R.drawable.lightbutton);
			return false;
		}
	}
	class touchsleepcontrol implements OnTouchListener{
		@Override
		public boolean onTouch(View v, MotionEvent event) {
			// TODO Auto-generated method stub
			if(event.getAction()==MotionEvent.ACTION_DOWN){
				v.setBackgroundResource(R.drawable.sleepcontrolon);

				socketThread.Send("a");
		
				socketThread.Send("b");
			
				socketThread.Send("c");
				
				
				Toast sleep = Toast.makeText(UserActivity.this, "关闭所有灯光", Toast.LENGTH_SHORT);
				sleep.show();
			}
			else if(event.getAction()==MotionEvent.ACTION_UP){
				v.setBackgroundResource(R.drawable.sleepcontroloff);
				}
			light1button.setBackgroundResource(R.drawable.lightbuttonoff);
			return false;
		}
	}
	
	/*Down page View*/
	class touchhome implements OnTouchListener{
		@Override
		public boolean onTouch(View v, MotionEvent event) {
			// TODO Auto-generated method stub
			if(event.getAction()==MotionEvent.ACTION_DOWN){
				v.setBackgroundResource(R.drawable.homeclick);
				sleep.setBackgroundResource(R.drawable.sleep);
				bath.setBackgroundResource(R.drawable.bath);
				Toast home = Toast.makeText(UserActivity.this, "您已在客厅界面", Toast.LENGTH_LONG);
				home.show();
			}
			//else if(event.getAction()==MotionEvent.ACTION_UP){
			//	v.setBackgroundResource(R.drawable.home);
			//}
			return false;
		}
	}
	class touchsleep implements OnTouchListener{
		@Override
		public boolean onTouch(View v, MotionEvent event) {
			// TODO Auto-generated method stub
			if(event.getAction()==MotionEvent.ACTION_DOWN){
				v.setBackgroundResource(R.drawable.sleepclick);
				home.setBackgroundResource(R.drawable.home);
				bath.setBackgroundResource(R.drawable.bath);
				Toast sleep = Toast.makeText(UserActivity.this, "已切换到卧室", Toast.LENGTH_LONG);
				sleep.show();

				Intent intent = new Intent();
    			intent.setClass(UserActivity .this, SleepActivity.class);
    			startActivity(intent);
    			UserActivity.this.finish();
			}
			return false;
		}
	}
	class touchbath implements OnTouchListener{
		@Override
		public boolean onTouch(View v, MotionEvent event) {
			// TODO Auto-generated method stub
			if(event.getAction()==MotionEvent.ACTION_DOWN){
				v.setBackgroundResource(R.drawable.bathclick);
				home.setBackgroundResource(R.drawable.home);
				sleep.setBackgroundResource(R.drawable.sleep);
				Toast bath = Toast.makeText(UserActivity.this, "已切换到浴室", Toast.LENGTH_LONG);
				bath.show();
				Intent intent = new Intent();
    			intent.setClass(UserActivity.this, BathActivity.class);
    			startActivity(intent);
    			UserActivity.this.finish();
			}
			return false;
		}
	}
	/*light1*/
    class light1button implements OnClickListener{
    	@Override
    	public void onClick(View v) {
    		// TODO Auto-generated method stub
    		c = c+1;
    		if(c%2==1){
    			light1button.setBackgroundResource(R.drawable.lightbutton);
    			
    //			MyLog.i(TAG, "准备发送数据");
				//sendstr = edtsendms.getText().toString().trim();
 //==============这一坨代码是获取IP和端口，连接服务器，然后发送，暂时这么写，到时候再优化 =================================================
    			port = Integer.parseInt(sp.getString("port", String.valueOf(port)));
    			String ip =sp.getString("ipstr", "");   			
    			socketThread.Send("A");

    		

    			
    		
  //=========================================================================
    			Toast light = Toast.makeText(UserActivity.this, "灯光开启", Toast.LENGTH_LONG);
				light.show();
    		}else{
    			light1button.setBackgroundResource(R.drawable.lightbuttonoff);
    			//关灯，发字符 a
    			port = Integer.parseInt(sp.getString("port", String.valueOf(port)));
    			String ip =sp.getString("ipstr", "");    			
    			socketThread.Send("a");
    			
    			Toast light = Toast.makeText(UserActivity.this, "灯光关闭", Toast.LENGTH_LONG);
				light.show();
    		}
    	}
    }
	/*monitor*/         
    class monitor implements OnClickListener{
    	@Override
    	public void onClick(View v) {
    		// TODO Auto-generated method stub
    		monitor.setBackgroundResource(R.drawable.monitoron);
    		Toast light = Toast.makeText(UserActivity.this, "监控开启", Toast.LENGTH_LONG);
			light.show();
			Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			startActivityForResult(cameraIntent,CAMERA_REQUEST);
			monitor.setBackgroundResource(R.drawable.monitoroff);
    	}
    }          
	/*water*/
    class water implements OnClickListener{
    	@Override
    	public void onClick(View v) {
    		// TODO Auto-generated method stub
    		d = d+1;
    		if(d%2==1){
    			water.setBackgroundResource(R.drawable.certainon);
    			socketThread.Send("D");
    			
    			Toast light = Toast.makeText(UserActivity.this, "窗帘开启", Toast.LENGTH_LONG);
				light.show();
    		}else{
    			water.setBackgroundResource(R.drawable.certainoff);
    			
    			socketThread.Send("d");
    			Toast light = Toast.makeText(UserActivity.this, "窗帘关闭", Toast.LENGTH_LONG);
				light.show();
    		}
    	}
    }
	/*TV*/
    class tvon implements OnClickListener{
    	@Override
    	public void onClick(View v) {
    		// TODO Auto-generated method stub
    		e = e+1;
    		if(e%2==1){
    			tvon.setBackgroundResource(R.drawable.tvon);
    			Toast light = Toast.makeText(UserActivity.this, "电视开启", Toast.LENGTH_LONG);
				light.show();
    		}else{
    			tvon.setBackgroundResource(R.drawable.tvoff);
    			Toast light = Toast.makeText(UserActivity.this, "电视关闭", Toast.LENGTH_LONG);
				light.show();
    		}
    	}
    }
	/*down the Air*/
	class airdowntouch implements OnTouchListener{
		@Override
		public boolean onTouch(View v, MotionEvent event) {
			temp = temp -1;
			// TODO Auto-generated method stub
			if(event.getAction()==MotionEvent.ACTION_DOWN){
				v.setBackgroundResource(R.drawable.airtempdowntouch);
				//Toast sun = Toast.makeText(useractivity.this, "温度降低", Toast.LENGTH_LONG);
				//sun.show();
			}
			else if(event.getAction()==MotionEvent.ACTION_UP){
				v.setBackgroundResource(R.drawable.airtempdown);
				}
            if(temp>=16){
				Toast sun = Toast.makeText(UserActivity.this, "温度降低为"+temp+"℃", Toast.LENGTH_LONG);
				sun.show();
            }else{
            	temp=16;
            	Toast sun = Toast.makeText(UserActivity.this, "温度已调至最低16℃", Toast.LENGTH_LONG);
				sun.show();
            }
			return false;
		}
	}
	/*up the Air*/
	class airuptouch implements OnTouchListener{
		@Override
		public boolean onTouch(View v, MotionEvent event) {
			temp = temp+1;
			// TODO Auto-generated method stub
			if(event.getAction()==MotionEvent.ACTION_DOWN){
				v.setBackgroundResource(R.drawable.airtempuptouch);
				//Toast sun = Toast.makeText(useractivity.this, "温度升高", Toast.LENGTH_LONG);
				//sun.show();
			}
			else if(event.getAction()==MotionEvent.ACTION_UP){
				v.setBackgroundResource(R.drawable.airtempup);
				}
			if(temp<=30){
				Toast sun = Toast.makeText(UserActivity.this, "温度提升为"+temp+"℃", Toast.LENGTH_LONG);
				sun.show();
            }else{
            	temp=30;
            	Toast sun = Toast.makeText(UserActivity.this, "温度已调至最高30℃", Toast.LENGTH_LONG);
				sun.show();
            }
			return false;
		}
	}
	/*back to login*/
	class back implements OnClickListener{
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			UserActivity.this.finish();
		Intent intent = new Intent();
		intent.setClass(UserActivity.this, FullscreenActivity.class);
		startActivity(intent);
		}
	}
	
	public void startSocket() {
		socketThread = new SocThread(m_rev_handler, m_sent_handler, ctx);//连接socket服务器
		socketThread.start();
		socketSendFlag =1 ;
	}
	
	private void stopSocket() {
		socketThread.isRun = false;
		socketThread.close();
		socketThread = null;
		
	}

	
	
}
