package com.example.testsocket;

//import com.example.androiddatabase.AdbActivity;
//import com.example.androiddatabase.DbAdapter;
import com.example.smart.util.SystemUiHider;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 * 
 * @see SystemUiHider
 */
public class FullscreenActivity extends Activity {
	/**
	 * Whether or not the system UI should be auto-hidden after
	 * {@link #AUTO_HIDE_DELAY_MILLIS} milliseconds.
	 */
	private Button login,close,register,check;
	private EditText User,Pwd;
	boolean rightlogin = false;
	private static final boolean AUTO_HIDE = true;
	SharedPreferences sp;
	private Context ctx;
	
	int person_Account;
    String person_Password;
	
    String userSaved,pwdSaved;
	
	/**
	 * If {@link #AUTO_HIDE} is set, the number of milliseconds to wait after
	 * user interaction before hiding the system UI.
	 */
	private static final int AUTO_HIDE_DELAY_MILLIS = 3000;

	/**
	 * If set, will toggle the system UI visibility upon interaction. Otherwise,
	 * will show the system UI visibility upon interaction.
	 */
	private static final boolean TOGGLE_ON_CLICK = true;

	/**
	 * The flags to pass to {@link SystemUiHider#getInstance}.
	 */
	private static final int HIDER_FLAGS = SystemUiHider.FLAG_HIDE_NAVIGATION;

	/**
	 * The instance of the {@link SystemUiHider} for this activity.
	 */
	private SystemUiHider mSystemUiHider;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fullscreen);
        
		final View controlsView = findViewById(R.id.fullscreen_content_controls);
		final View contentView = findViewById(R.id.fullscreen_content);
		
		 User = (EditText)findViewById(R.id.User);
		 Pwd= (EditText)findViewById(R.id.Pwd);
		
		
		
		
		
	//=========================================== 这一块是从sharepreference中读取信息的

		 ctx = FullscreenActivity.this;
		 sp = this.getSharedPreferences("SP", MODE_PRIVATE);	
	//	 User.setText(sp.getString("account", ""));
	//	 Pwd.setText(sp.getString("password", ""));
		userSaved =sp.getString("account", "");
    	pwdSaved =sp.getString("password", "");
	//===============================================	 
		// Set up an instance of SystemUiHider to control the system UI for
		// this activity.
		mSystemUiHider = SystemUiHider.getInstance(this, contentView,
				HIDER_FLAGS);
		//mSystemUiHider.setup();
		//up is to close the AUTO_systemUI.
		mSystemUiHider
				.setOnVisibilityChangeListener(new SystemUiHider.OnVisibilityChangeListener() {
					// Cached values.
					int mControlsHeight;
					int mShortAnimTime;

					@Override
					@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
					public void onVisibilityChange(boolean visible) {
						if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
							// If the ViewPropertyAnimator API is available
							// (Honeycomb MR2 and later), use it to animate the
							// in-layout UI controls at the bottom of the
							// screen.
							if (mControlsHeight == 0) {
								mControlsHeight = controlsView.getHeight();
							}
							if (mShortAnimTime == 0) {
								mShortAnimTime = getResources().getInteger(
										android.R.integer.config_shortAnimTime);
							}
							controlsView
									.animate()
									.translationY(visible ? 0 : mControlsHeight)
									.setDuration(mShortAnimTime);
						} else {
							// If the ViewPropertyAnimator APIs aren't
							// available, simply show or hide the in-layout UI
							// controls.
							controlsView.setVisibility(visible ? View.VISIBLE
									: View.GONE);
						}

						if (visible && AUTO_HIDE) {
							// Schedule a hide().
							delayedHide(AUTO_HIDE_DELAY_MILLIS);
						}
					}
				});

		// Set up the user interaction to manually show or hide the system UI.
		contentView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if (TOGGLE_ON_CLICK) {
					mSystemUiHider.toggle();
				} else {
					mSystemUiHider.show();
				}
			}
		});

		// Upon interacting with UI controls, delay any scheduled hide()
		// operations to prevent the jarring behavior of controls going away
		// while interacting with the UI.
		User = (EditText)findViewById(R.id.User);
		Pwd = (EditText)findViewById(R.id.Pwd);
		login = (Button)findViewById(R.id.login);
		close = (Button)findViewById(R.id.close);
        //User.setTextColor(0xff23238e);
        //Pwd.setTextColor(0xff23238e);
		User.setTextColor(0xffffffff);
        Pwd.setTextColor(0xffffffff);
		login.setBackgroundDrawable(null);
		close.setBackgroundDrawable(null);
		User.setBackgroundDrawable(null);
		Pwd.setBackgroundDrawable(null);
		login.setOnClickListener(new Login());
		close.setOnClickListener(new Close());
		register = (Button)findViewById(R.id.register);
		register.setOnClickListener(new register());
		register.setBackgroundDrawable(null);
		check = (Button)findViewById(R.id.check);
		check.setOnClickListener(new check());
		check.setBackgroundDrawable(null);
		check.setTextColor(0xffffffff);
	}
    class Login implements OnClickListener{
    	@Override
    	public void onClick(View v) {
    		// TODO Auto-generated method stub
    		//获取输入字符 
    		String user = User.getText().toString();
    		String pwd = Pwd.getText().toString();

    		if((user.equals(userSaved))&&(pwd.equals(pwdSaved))){
    			Intent intent = new Intent();
    			intent.setClass(FullscreenActivity.this, UserActivity.class);
    			startActivity(intent);
    			FullscreenActivity.this.finish();
    			Toast.makeText(FullscreenActivity.this, "欢迎使用", Toast.LENGTH_LONG).show();
    		}else{
    			Toast.makeText(FullscreenActivity.this, "用户名或密码错误", Toast.LENGTH_LONG).show();
    		}	
    	
    		
    		
    		
    /*		
    		Intent intent = new Intent();
			intent.setClass(FullscreenActivity.this, UserActivity.class);
			startActivity(intent);
			FullscreenActivity.this.finish();
    	*/	
    		
    		
    /*		
    		if((user.equals("hwj")&&pwd.equals("hxy"))||(user.equals("hxy")&&pwd.equals("hwj"))){
    			Log.d("hwj", "Welcome");
    			Toast.makeText(FullscreenActivity.this, "欢迎回来", Toast.LENGTH_LONG).show();
    			rightlogin = true;
    		}else{
    			Log.d("hwj", "Sorry,Please try again");
    	//		Toast.makeText(FullscreenActivity.this, "请检查密码输入", Toast.LENGTH_LONG).show();
    			rightlogin = false;
    		}
    		if(rightlogin=true){
    			Intent intent = new Intent();
    			intent.setClass(FullscreenActivity.this, UserActivity.class);
    			startActivity(intent);
    			FullscreenActivity.this.finish();
    			
    		}
    		
    	*/
    	}
    }
    class Close implements OnClickListener{
    	@Override
    	public void onClick(View v) {
    		// TODO Auto-generated method stub
    		FullscreenActivity.this.finish();
    	}
    }
    class register implements OnClickListener{
    	@Override
    	public void onClick(View v) {
    		// TODO Auto-generated method stub
    		Toast.makeText(FullscreenActivity.this, "欢迎注册", Toast.LENGTH_LONG).show();
    		
    		Intent intent = new Intent(FullscreenActivity.this,Setting.class);
			//intent.putExtra("extra_data", Data);
			startActivity(intent);
    		
    	}
    }
    class check implements OnClickListener{
    	@Override
    	public void onClick(View v) {
    		// TODO Auto-generated method stub
    		//FullscreenActivity.this.finish();
    		Intent intent = new Intent();
			intent.setClass(FullscreenActivity.this, Ipsetting.class);
			startActivity(intent);
			FullscreenActivity.this.finish();
    	}
    }
	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);

		// Trigger the initial hide() shortly after the activity has been
		// created, to briefly hint to the user that UI controls
		// are available.
		delayedHide(100);
	}

	/**
	 * Touch listener to use for in-layout UI controls to delay hiding the
	 * system UI. This is to prevent the jarring behavior of controls going away
	 * while interacting with activity UI.
	 */
	View.OnTouchListener mDelayHideTouchListener = new View.OnTouchListener() {
		@Override
		public boolean onTouch(View view, MotionEvent motionEvent) {
			if (AUTO_HIDE) {
				delayedHide(AUTO_HIDE_DELAY_MILLIS);
			}
			return false;
		}
	};

	Handler mHideHandler = new Handler();
	Runnable mHideRunnable = new Runnable() {
		@Override
		public void run() {
			mSystemUiHider.hide();
		}
	};

	/**
	 * Schedules a call to hide() in [delay] milliseconds, canceling any
	 * previously scheduled calls.
	 */
	private void delayedHide(int delayMillis) {
		mHideHandler.removeCallbacks(mHideRunnable);
		mHideHandler.postDelayed(mHideRunnable, delayMillis);
	}
}
