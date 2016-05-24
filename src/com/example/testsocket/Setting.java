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
	
	//=====数据库相关


   


	//======

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setting);
		
		//=================================  数据库相关  ,界面刚开的 时候 创建了数据库
		//创建一个DatabaseHelper对象
//		DbAdapter dbHelper = new DbAdapter(Setting.this,"test.db");
		//只有调用了DatabaseHelper对象的getReadableDatabase()方法，或者是getWritableDatabase()方法之后，才会创建，或打开一个数据库
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
				
				//创建一个DatabaseHelper对象
				//DbAdapter dbHelper = new DbAdapter(Setting.this,"test.db");
				//只有调用了DatabaseHelper对象的getReadableDatabase()方法，或者是getWritableDatabase()方法之后，才会创建，或打开一个数据库
				//SQLiteDatabase db = dbHelper.getReadableDatabase();
				
			}
		});

		btnsave.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
		//		Log.i(TAG,"开始修改");
				account = edtip.getText().toString();//
				password = edtport.getText().toString();//
				
				
				
				
				Editor editor = sp.edit();
				editor.putString("account", account);
				editor.putString("password", password);
			    editor.commit();//保存新数据
			

				
				//=======  第一种插入数据库方法 

				
				
		/*		ContentValues values=new ContentValues();
				
				int AccountInt;  //账户以纯数字形式保存
				String AccountStr; //文本形式的  账户 
				String Password; //密码可以随意 
				AccountStr = edtip.getText().toString();
				Password = edtport.getText().toString();//
				AccountInt=Integer.parseInt(AccountStr); //文本转数字来保存，用于检索 
				//写入values
				values.put("AccountInt",AccountInt);
				values.put("Password",Password);
				DbAdapter dbHelper = new DbAdapter(Setting.this,"test.db");
				SQLiteDatabase db = dbHelper.getWritableDatabase();
				db.insert("person", null, values);
				
				*/
				Toast.makeText(Setting.this, "注册成功", Toast.LENGTH_SHORT).show();
				
                  

        
				
				
				
				
                  
                  
               //========================================   
                  finish();

			}
		});
		
	
		
	}
	


	

}
