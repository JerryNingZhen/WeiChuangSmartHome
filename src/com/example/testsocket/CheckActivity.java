package com.example.testsocket;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorListener;
import android.hardware.SensorManager;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class CheckActivity extends Activity {
	int port;
	private TextView tv1 = null; //��Ϣ���տ�
	Handler m_rev_handler;
	Handler m_sent_handler;
	boolean isRun = true;
	EditText edtsendms;
	SharedPreferences sp;
	private Context ctx;
	Socket socket;
	PrintWriter out;
	BufferedReader in;
	SocThread socketThread;	
	public Socket client = null;
	int socketSendFlag =0;
	//======3.24�賿���=====
	TextView Status1 = null;
	TextView Status = null;
	Button Back = null;
	Button UserOn = null;
	Button UserOff = null;
	Button SleepOn = null;
	Button SleepOff = null;
	Button BathOn = null;
	Button BathOff = null;
	Button MotorOn = null;
	Button MotorOff = null;
	Button StOn = null;
	Button StOff = null;
	Button BeeOn = null;
	Button BeeOff = null;
	Button Temperature = null;
	
	String statusShow;
	int statusSwitch;
	
	//===================== ������Ϣ
	SocThread socketThreadRev; //���ڽ��յ��߳�
	
	
	
	///=========================
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_check);
	
		//============================================
		
//		Status1 = (TextView)findViewById(R.id.Status1);
		Status = (TextView)findViewById(R.id.Status);
		Back = (Button)findViewById(R.id.back);
		UserOn = (Button)findViewById(R.id.UserOn);
		UserOff = (Button)findViewById(R.id.UserOff);
		SleepOn = (Button)findViewById(R.id.SleepOn);
		SleepOff = (Button)findViewById(R.id.SleepOff);
	    BathOn = (Button)findViewById(R.id.BathOn);
		BathOff = (Button)findViewById(R.id.BathOff);
		MotorOn = (Button)findViewById(R.id.MotorOn);
		MotorOff = (Button)findViewById(R.id.MotorOff);
		StOn = (Button)findViewById(R.id.StOn);
		StOff = (Button)findViewById(R.id.StOff);
		BeeOn = (Button)findViewById(R.id.BeeOn);
		BeeOff = (Button)findViewById(R.id.BeeOff);
		Temperature = (Button)findViewById(R.id.Temperature);
		
		Back.setBackgroundDrawable(null);
		
		UserOn.setBackgroundDrawable(null);
		UserOff.setBackgroundDrawable(null);
		SleepOn.setBackgroundDrawable(null);
		SleepOff.setBackgroundDrawable(null);
		BathOn.setBackgroundDrawable(null);
		BathOff.setBackgroundDrawable(null);
		MotorOn.setBackgroundDrawable(null);
		MotorOff.setBackgroundDrawable(null);
		StOn.setBackgroundDrawable(null);
		StOff.setBackgroundDrawable(null);
		BeeOn.setBackgroundDrawable(null);
		BeeOff.setBackgroundDrawable(null);
		//Read.setBackgroundDrawable(null);
		//Read.setTextColor(0xffffffff);
		UserOn.setTextColor(0xffffffff);
		UserOff.setTextColor(0xffffffff);
		SleepOff.setTextColor(0xffffffff);
		SleepOn.setTextColor(0xffffffff);
		BathOn.setTextColor(0xffffffff);
		BathOff.setTextColor(0xffffffff);
		MotorOn.setTextColor(0xffffffff);
		MotorOff.setTextColor(0xffffffff);
		StOn.setTextColor(0xffffffff);
		StOff.setTextColor(0xffffffff);
		BeeOn.setTextColor(0xffffffff);
		BeeOff.setTextColor(0xffffffff);

	
		Temperature.setAlpha(20);
		//============================================

		ctx = CheckActivity.this;
		sp = this.getSharedPreferences("SP", MODE_PRIVATE);
		m_rev_handler = new Handler() {
	
			public void handleMessage(Message msg) {
				try {
					//MyLog.i(TAG, "m_rev_handler���յ�msg=" + msg.what);
					if (msg.obj != null) {
						String s = msg.obj.toString();
						if (s.trim().length() > 0) {
					//		MyLog.i(TAG, "m_rev_handler���յ�obj=" + s);
						//	MyLog.i(TAG, "��ʼ����UI");
							Status.append(s);  //�յ�ʲô�ַ���ֱ����ʾʲô�ַ�
						
						//	MyLog.i(TAG, "����UI���");
						} else {
					//		Log.i(TAG, "û�����ݷ��ز�����");
						}
					}
				} catch (Exception ee) {
					//MyLog.i(TAG, "���ع��̳����쳣");
					ee.printStackTrace();
				}
			}
		};
		m_sent_handler = new Handler() {
	
			public void handleMessage(Message msg) {
				try {
				//	MyLog.i(TAG, "m_sent_handler���յ�msg.what=" + msg.what);
					String s = msg.obj.toString();
					if (msg.what == 1) {
						tv1.append("\n ME: " + s + "      ���ͳɹ�");
					} else {
						tv1.append("\n ME: " + s + "     ����ʧ��");
					}
				} catch (Exception ee) {
				//	MyLog.i(TAG, "���ع��̳����쳣");
					ee.printStackTrace();
				}
			}
		};
		
		sp = this.getSharedPreferences("SP", MODE_PRIVATE);
		
		//if(socketSendFlag == 0){
			startSocket();
		//}
	//================================================
		
//		statusSwitch = Integer.parseInt(statusShow);
//		switch(statusSwitch){
//		case 1: Status1.setText("һ�ž��� "); break;
//		case 2: Status1.setText("���ž��� "); break;
//		case 3: Status1.setText("���ž��� "); break;
//		default:Status1.setText("�������� "); break;
//		}
		
		
		
		
	//==================================================	
		
	//	startSocketRev(); //���������߳�
	//	socketThreadRev.run(); //���ý��շ���
	//======================================	�����¼�
			Temperature.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				port = Integer.parseInt(sp.getString("port", String.valueOf(port)));
    			String ip =sp.getString("ipstr", ""); 
    			//startSocket();
    			socketThread.Send("G");
				
				
				
			}
		});
		
		
		Back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				Intent intent = new Intent();
				intent.setClass(CheckActivity.this, UserActivity.class);

				startActivity(intent);
				CheckActivity.this.finish();
				
			}
		});
		
		UserOn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				port = Integer.parseInt(sp.getString("port", String.valueOf(port)));
    			String ip =sp.getString("ipstr", "");   			
    			socketThread.Send("A");
			}
		});
		
		UserOff.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				port = Integer.parseInt(sp.getString("port", String.valueOf(port)));
    			String ip =sp.getString("ipstr", "");   			
    			socketThread.Send("a");
			}
		});
		
		SleepOn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				port = Integer.parseInt(sp.getString("port", String.valueOf(port)));
    			String ip =sp.getString("ipstr", "");   			
    			socketThread.Send("B");
			}
		});
		
		SleepOff.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				port = Integer.parseInt(sp.getString("port", String.valueOf(port)));
    			String ip =sp.getString("ipstr", "");   			
    			socketThread.Send("b");
			}
		});
		
		BathOn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				port = Integer.parseInt(sp.getString("port", String.valueOf(port)));
    			String ip =sp.getString("ipstr", "");   			
    			socketThread.Send("C");
			}
		});
		
		BathOff.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				port = Integer.parseInt(sp.getString("port", String.valueOf(port)));
    			String ip =sp.getString("ipstr", "");   			
    			socketThread.Send("c");
			}
		});
		
		MotorOn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				port = Integer.parseInt(sp.getString("port", String.valueOf(port)));
    			String ip =sp.getString("ipstr", "");   			
    			socketThread.Send("E");
			}
		});
		
		MotorOff.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				port = Integer.parseInt(sp.getString("port", String.valueOf(port)));
    			String ip =sp.getString("ipstr", "");   			
    			socketThread.Send("e");
			}
		});
		
	StOn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				port = Integer.parseInt(sp.getString("port", String.valueOf(port)));
    			String ip =sp.getString("ipstr", "");   			
    			socketThread.Send("D");
			}
		});
		
		StOff.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				port = Integer.parseInt(sp.getString("port", String.valueOf(port)));
    			String ip =sp.getString("ipstr", "");   			
    			socketThread.Send("d");
			}
		});
	
		BeeOn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				port = Integer.parseInt(sp.getString("port", String.valueOf(port)));
    			String ip =sp.getString("ipstr", "");   			
    			socketThread.Send("F");
			}
		});
		
		BeeOff.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				port = Integer.parseInt(sp.getString("port", String.valueOf(port)));
    			String ip =sp.getString("ipstr", "");   			
    			socketThread.Send("f");
			}
		});
		
  //================================================���͵��߳�  ����
	}

	public void startSocket() {
		socketThread = new SocThread(m_rev_handler, m_sent_handler, ctx);
		socketThread.start();
		socketSendFlag =1 ;
	}


	private void stopSocket() {
		socketThread.isRun = false;
		socketThread.close();
		socketThread = null;

	}

	 //===============================================  ���յ��߳�  ����
	

		public void startSocketRev() {
			socketThreadRev = new SocThread(m_rev_handler, m_sent_handler, ctx);
			socketThreadRev.start();
			//socketSendFlag =1 ;
		}


		private void stopSocketRev() {
			socketThreadRev.isRun = false;
			socketThreadRev.close();
			socketThreadRev = null;

		}

	

}

