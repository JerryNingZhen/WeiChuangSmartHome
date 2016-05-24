package com.example.testsocket;

//import com.example.smart.useractivity;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;

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


public class BathActivity extends FullscreenActivity {

	private int port = 8888;
	public Socket client = null;

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
	
	boolean sleeproom = false;
	private int a = 0;
	private int b = 0;
	private int c = 0;
	private int d = 0;
	private int e = 0;
	private int temp = 26;
	private Button check,light1button,monitor,tvon,water,airdown,airup,aircontrol,suncontrol,sleepcontrol,back1,home,bath,sleep;
	private static final int CAMERA_REQUEST = 1888;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bath);
		
		ctx = BathActivity.this;
		
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
	//				MyLog.i(TAG, "m_rev_handler���յ�msg=" + msg.what);
					if (msg.obj != null) {
						String s = msg.obj.toString();
						if (s.trim().length() > 0) {
		//					MyLog.i(TAG, "m_rev_handler���յ�obj=" + s);
		//					MyLog.i(TAG, "��ʼ����UI");
							//tv1.append("Server:" + s);
		//					MyLog.i(TAG, "����UI���");
						} else {
							Log.i(TAG, "û�����ݷ��ز�����");
						}
					}
				} catch (Exception ee) {
		//			MyLog.i(TAG, "���ع��̳����쳣");
					ee.printStackTrace();
				}
			}
		};
		m_sent_handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				try {
		//			MyLog.i(TAG, "m_sent_handler���յ�msg.what=" + msg.what);
					String s = msg.obj.toString();
					if (msg.what == 1) {
						//tv1.append("\n ME: " + s + "      ���ͳɹ�");
					} else {
						//tv1.append("\n ME: " + s + "     ����ʧ��");
					}
				} catch (Exception ee) {
		//			MyLog.i(TAG, "���ع��̳����쳣");
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
			Toast air = Toast.makeText(BathActivity.this, "�鿴����������", Toast.LENGTH_LONG);
			air.show();
			Intent intent = new Intent();
			intent.setClass(BathActivity.this, CheckActivity.class);
			startActivity(intent);
			BathActivity.this.finish();
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

    			Toast air = Toast.makeText(BathActivity.this, "�յ�����", Toast.LENGTH_LONG);
				air.show();
    		}else{
    			aircontrol.setBackgroundResource(R.drawable.aircontroloff);
    			
    			socketThread.Send("e");
	
    			Toast air = Toast.makeText(BathActivity.this, "�ر�", Toast.LENGTH_LONG);
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
				
				Toast sun = Toast.makeText(BathActivity.this, "�����еƹ�", Toast.LENGTH_LONG);
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
				
				Toast sleep = Toast.makeText(BathActivity.this, "�ر����еƹ�", Toast.LENGTH_LONG);
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
				Toast home = Toast.makeText(BathActivity.this, "���л�������", Toast.LENGTH_LONG);
				home.show();
				Intent intent = new Intent();
    			intent.setClass(BathActivity .this, UserActivity.class);
    			startActivity(intent);
    			BathActivity.this.finish();
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
				Toast sleep = Toast.makeText(BathActivity.this, "���л�������", Toast.LENGTH_LONG);
				sleep.show();
				Intent intent = new Intent();
    			intent.setClass(BathActivity .this, SleepActivity.class);
    			startActivity(intent);
    			BathActivity.this.finish();
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
				Toast bath = Toast.makeText(BathActivity.this, "������ԡ�ҽ���", Toast.LENGTH_LONG);
				bath.show();
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
    			
    			port = Integer.parseInt(sp.getString("port", String.valueOf(port)));
    			String ip =sp.getString("ipstr", "");   			
    			socketThread.Send("C");
    			
    			Toast light = Toast.makeText(BathActivity.this, "�ƹ⿪��", Toast.LENGTH_LONG);
				light.show();
    		}else{
    			light1button.setBackgroundResource(R.drawable.lightbuttonoff);
    			
    			port = Integer.parseInt(sp.getString("port", String.valueOf(port)));
    			String ip =sp.getString("ipstr", "");    			
    			socketThread.Send("c");
    			
    			Toast light = Toast.makeText(BathActivity.this, "�ƹ�ر�", Toast.LENGTH_LONG);
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
    		Toast light = Toast.makeText(BathActivity.this, "��ؿ���", Toast.LENGTH_LONG);
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
    			
    			Toast light = Toast.makeText(BathActivity.this, "��������", Toast.LENGTH_LONG);
				light.show();
    		}else{
    			water.setBackgroundResource(R.drawable.certainoff);
    			
    			socketThread.Send("d");
    			Toast light = Toast.makeText(BathActivity.this, "�����ر�", Toast.LENGTH_LONG);
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
    			Toast light = Toast.makeText(BathActivity.this, "���ӿ���", Toast.LENGTH_LONG);
				light.show();
    		}else{
    			tvon.setBackgroundResource(R.drawable.tvoff);
    			Toast light = Toast.makeText(BathActivity.this, "���ӹر�", Toast.LENGTH_LONG);
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
				//Toast sun = Toast.makeText(useractivity.this, "�¶Ƚ���", Toast.LENGTH_LONG);
				//sun.show();
			}
			else if(event.getAction()==MotionEvent.ACTION_UP){
				v.setBackgroundResource(R.drawable.airtempdown);
				}
            if(temp>=16){
				Toast sun = Toast.makeText(BathActivity.this, "�¶Ƚ���Ϊ"+temp+"��", Toast.LENGTH_LONG);
				sun.show();
            }else{
            	temp=16;
            	Toast sun = Toast.makeText(BathActivity.this, "�¶��ѵ������16��", Toast.LENGTH_LONG);
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
				//Toast sun = Toast.makeText(useractivity.this, "�¶�����", Toast.LENGTH_LONG);
				//sun.show();
			}
			else if(event.getAction()==MotionEvent.ACTION_UP){
				v.setBackgroundResource(R.drawable.airtempup);
				}
			if(temp<=30){
				Toast sun = Toast.makeText(BathActivity .this, "�¶�����Ϊ"+temp+"��", Toast.LENGTH_LONG);
				sun.show();
            }else{
            	temp=30;
            	Toast sun = Toast.makeText(BathActivity .this, "�¶��ѵ������30��", Toast.LENGTH_LONG);
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
			BathActivity.this.finish();
		Intent intent = new Intent();
		intent.setClass(BathActivity.this, FullscreenActivity.class);
		startActivity(intent);
		}
	}
	
	public void startSocket() {
		socketThread = new SocThread(m_rev_handler, m_sent_handler, ctx);//����socket������
		socketThread.start();
		socketSendFlag =1 ;
	}
	
	private void stopSocket() {
		socketThread.isRun = false;
		socketThread.close();
		socketThread = null;
		
	}

	
}