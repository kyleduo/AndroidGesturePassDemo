package com.kyleduo.gesturepass;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

/**
 * BaseActivity
 * Created by kyle on 16/4/6.
 */
public class BaseActivity extends AppCompatActivity {
	private static final int BACK_TIME_INITIAL = 0; // 初始值，遇到0必须检查手势密码时间
	private static final int BACK_TIME_CLEAR = -1; // 不需要检查手势密码时间

	public static final String KEY_FROM_PUSH = "from_push";
	public static final int GESTURE_PASS_TIMEOUT = 2 * 1000;

	protected boolean mFromPush;

	protected static long sLastBackTime = BACK_TIME_INITIAL; // 0: 初始值 -1: 当前在前台

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mFromPush = getIntent().getBooleanExtra(KEY_FROM_PUSH, false);
		Log.d(tag(), "from push: " + mFromPush);
		boolean fromHistory = (getIntent().getFlags() & Intent.FLAG_ACTIVITY_LAUNCHED_FROM_HISTORY) != 0;
		Log.d(tag(), "fromHistory: " + fromHistory + "  sLastBackTime: " + sLastBackTime);
		if (sLastBackTime != BACK_TIME_INITIAL && shouldCheckGesturePass() && !isFromPush() && fromHistory) {
			sLastBackTime = BACK_TIME_CLEAR;
			Log.e(tag(), "onCreate >>>>>>>>>>>>>>>> reset sLastBackTime <<<<<<<<<<<<<<<");
		}
	}

	private String tag() {
		return getClass().getName();
	}

	public boolean isFromPush() {
		return mFromPush;
	}

	@Override
	protected void onResume() {
		super.onResume();
		if (shouldCheckGesturePass()) {
			//noinspection ConstantConditions
			if (checkGestureTime() && gesturePassIsAvailable()) {
				Log.d(tag(), "show gesture pass");
				Intent intent = new Intent(this, GesturePassActivity.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
				overridePendingTransition(0, 0);
				sLastBackTime = BACK_TIME_CLEAR;
			}
		} else {
			sLastBackTime = BACK_TIME_CLEAR;
			Log.d(tag(), "onResume >>>>>>>>>>>>>>>> reset sLastBackTime <<<<<<<<<<<<<<<");
		}

		if (checkGestureTime()) {
			Log.d(tag(), "showGesture");
		}
	}

	/**
	 * 检查时间是否满足手势密码的间隔
	 *
	 * @return true 时间满足
	 */
	protected boolean checkGestureTime() {
		Log.d(tag(), "checkGestureTime: sLastBackTime: " + sLastBackTime + " isPush: " + isFromPush());
		double diff = Math.abs(SystemClock.elapsedRealtime() - sLastBackTime);
		boolean timeSatisfy = diff > GESTURE_PASS_TIMEOUT;
		Log.d(tag(), "checkGestureTime: diff: " + diff);
		return sLastBackTime != BACK_TIME_CLEAR && timeSatisfy;
	}

	@Override
	protected void onPause() {
		super.onPause();
		if (shouldCheckGesturePass()) {
			if (this instanceof GesturePassActivity) {
				sLastBackTime = BACK_TIME_CLEAR;
			} else {
				sLastBackTime = SystemClock.elapsedRealtime();
			}
			Log.e(tag(), "onPause: sLastBackTime: " + sLastBackTime);
		}
	}

	/**
	 * 测试方法，始终返回true
	 *
	 * @return true
	 */
	private boolean gesturePassIsAvailable() {
		return true;
	}

	protected boolean shouldCheckGesturePass() {
		return true;
	}
}
