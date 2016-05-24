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
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Client extends Activity {
//	private String TAG = "===Client===";
//	private String TAG1 = "===Send===";

	
	private TextView tv1 = null; //��Ϣ���տ�
	Handler m_rev_handler;
	Handler m_sent_handler;
	boolean isRun = true;
	EditText edtsendms;
	Button btnsend;
	Button btn_clear;
	private String sendstr = "";
	SharedPreferences sp;
	Button btnSetting;
	private Context ctx;
	Socket socket;
	PrintWriter out;
	BufferedReader in;
		SocThread socketThread;

		int socketSendFlag ; // ���Ͷ�һ��ֻ��һ���̣߳���һ�η��͵�ʱ�򣬽����µ����̣߳���Flag��1����һ��Ҫ���ͣ����Flag�Ƿ�Ϊ1�������1�Ͳ�����
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.client);
		tv1 = (TextView) findViewById(R.id.tv1);
		btnsend = (Button) findViewById(R.id.Read);
		ctx = Client.this;
		edtsendms = (EditText) findViewById(R.id.editText1);
		btnSetting = (Button) findViewById(R.id.button2);
		btn_clear=(Button)findViewById(R.id.button3);
		m_rev_handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				try {
			//		MyLog.i(TAG, "m_rev_handler���յ�msg=" + msg.what);
					if (msg.obj != null) {
						String s = msg.obj.toString();
						if (s.trim().length() > 0) {
			//				MyLog.i(TAG, "m_rev_handler���յ�obj=" + s);
			//				MyLog.i(TAG, "��ʼ����UI");
							tv1.append("Server:" + s);
			//				MyLog.i(TAG, "����UI���");
						} else {
			//				Log.i(TAG, "û�����ݷ��ز�����");
						}
					}
				} catch (Exception ee) {
			//		MyLog.i(TAG, "���ع��̳����쳣");
					ee.printStackTrace();
				}
			}
		};
		m_sent_handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				try {
			//		MyLog.i(TAG, "m_sent_handler���յ�msg.what=" + msg.what);
					String s = msg.obj.toString();
					if (msg.what == 1) {
						tv1.append("\n ME: " + s + "      ���ͳɹ�");
					} else {
						tv1.append("\n ME: " + s + "     ����ʧ��");
					}
				} catch (Exception ee) {
			//		MyLog.i(TAG, "���ع��̳����쳣");
					ee.printStackTrace();
				}
			}
		};

		
		//if(socketSendFlag == 0){
			startSocket();
		//}
	
		btnsend.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// ��������
		//		MyLog.i(TAG, "׼����������");
				
				sendstr = edtsendms.getText().toString().trim();
				socketThread.Send(sendstr);
				
		
			}
		});
		btn_clear.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				//tv1.setText("");
				Intent intent = new Intent(Client.this,FullscreenActivity.class);
				//intent.putExtra("extra_data", Data);
				startActivity(intent);
				
			}
		});
		btnSetting.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// ��ת�����ý���
				Intent intent = new Intent();
				intent.setClass(Client.this, Setting.class);
		//		MyLog.i(TAG, "��ת�����ý���");
				ctx.startActivity(intent);// ���½���

			}
		});

	}

	public void startSocket() {
		socketThread = new SocThread(m_rev_handler, m_sent_handler, ctx);
		socketThread.start();
		socketSendFlag = 1;
	}



	private void stopSocket() {
		socketThread.isRun = false;
		socketThread.close();
		socketThread = null;

	}



}
