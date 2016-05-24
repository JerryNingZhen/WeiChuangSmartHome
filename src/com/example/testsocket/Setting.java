package com.example.testsocket;



import android.app.Activity;
import android.content.ContentValues;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Setting extends Activity {
	private Button btnsave;
	private Button btn_cancel;
	private EditText edtip;
	private EditText edtport;
	SharedPreferences sp;
	private String TAG="=Setting=";

	String account ;
	String password ;
	
	//=====���ݿ����


   


	//======

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setting);
		
		//=================================  ���ݿ����  ,����տ��� ʱ�� ���������ݿ�
		//����һ��DatabaseHelper����
//		DbAdapter dbHelper = new DbAdapter(Setting.this,"test.db");
		//ֻ�е�����DatabaseHelper�����getReadableDatabase()������������getWritableDatabase()����֮�󣬲Żᴴ�������һ�����ݿ�
//		SQLiteDatabase db = dbHelper.getReadableDatabase();
		//================================
		
		
		//==============================
		btnsave = (Button) findViewById(R.id.Read);
		btn_cancel=(Button) findViewById(R.id.button2);
		edtip = (EditText) findViewById(R.id.editText1);
		edtport = (EditText) findViewById(R.id.editText2);
		sp = this.getSharedPreferences("SP", MODE_PRIVATE);
	//	edtip.setText(sp.getString("ipstr", ""));
	//	edtport.setText(sp.getString("port", ""));
		
		btnsave.setBackgroundDrawable(null);
		btnsave.setTextColor(0xffffffff);
		
		btn_cancel.setBackgroundDrawable(null);
		btn_cancel.setTextColor(0xffffffff);
		
		edtip.setBackgroundDrawable(null);
		edtip.setTextColor(0xffffffff);
		
		edtport.setBackgroundDrawable(null);
		edtport.setTextColor(0xffffffff);
		



   


		
		btn_cancel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
				
				//����һ��DatabaseHelper����
				//DbAdapter dbHelper = new DbAdapter(Setting.this,"test.db");
				//ֻ�е�����DatabaseHelper�����getReadableDatabase()������������getWritableDatabase()����֮�󣬲Żᴴ�������һ�����ݿ�
				//SQLiteDatabase db = dbHelper.getReadableDatabase();
				
			}
		});

		btnsave.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
		//		Log.i(TAG,"��ʼ�޸�");
				account = edtip.getText().toString();//
				password = edtport.getText().toString();//
				
				
				
				
				Editor editor = sp.edit();
				editor.putString("account", account);
				editor.putString("password", password);
			    editor.commit();//����������
			

				
				//=======  ��һ�ֲ������ݿⷽ�� 

				
				
		/*		ContentValues values=new ContentValues();
				
				int AccountInt;  //�˻��Դ�������ʽ����
				String AccountStr; //�ı���ʽ��  �˻� 
				String Password; //����������� 
				AccountStr = edtip.getText().toString();
				Password = edtport.getText().toString();//
				AccountInt=Integer.parseInt(AccountStr); //�ı�ת���������棬���ڼ��� 
				//д��values
				values.put("AccountInt",AccountInt);
				values.put("Password",Password);
				DbAdapter dbHelper = new DbAdapter(Setting.this,"test.db");
				SQLiteDatabase db = dbHelper.getWritableDatabase();
				db.insert("person", null, values);
				
				*/
				Toast.makeText(Setting.this, "ע��ɹ�", Toast.LENGTH_SHORT).show();
				
                  

        
				
				
				
				
                  
                  
               //========================================   
                  finish();

			}
		});
		
	
		
	}
	


	

}
