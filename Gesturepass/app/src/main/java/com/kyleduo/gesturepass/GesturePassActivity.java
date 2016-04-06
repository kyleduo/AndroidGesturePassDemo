package com.kyleduo.gesturepass;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;

/**
 * Created by kyle on 16/4/6.
 */
public class GesturePassActivity extends BaseActivity implements View.OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gesture_pass);
		findViewById(R.id.gp_verify_ok_bt).setOnClickListener(this);
		findViewById(R.id.gp_exit_bt).setOnClickListener(this);
	}

	@Override
	protected boolean shouldCheckGesturePass() {
		return false;
	}

	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			exitApp();
			return true;
		}
		return super.onKeyUp(keyCode, event);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.gp_verify_ok_bt:
				finish();
				break;
			case R.id.gp_exit_bt:
				exitApp();
				break;
		}
	}

	private void exitApp() {
		moveTaskToBack(true);
		System.exit(0);
	}
}
