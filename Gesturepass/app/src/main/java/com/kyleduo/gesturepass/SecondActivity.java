package com.kyleduo.gesturepass;

import android.app.Application;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.view.View;
import android.widget.TextView;

/**
 * Created by kyle on 16/4/6.
 */
public class SecondActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_second);

		findViewById(R.id.push_noti_bt).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				showNotify();
			}
		});
	}

	private void showNotify() {
		NotificationManager manager = (NotificationManager) getSystemService(Application.NOTIFICATION_SERVICE);
		NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
		Intent intent = new Intent(this, MainActivity.class);
		intent.putExtra(MainActivity.KEY_FROM_PUSH, true);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
		PendingIntent pi = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

		builder.setContentTitle("Mock push")
				.setContentText("Push content")
				.setTicker("Ticker")
				.setWhen(System.currentTimeMillis())
				.setPriority(NotificationCompat.PRIORITY_DEFAULT)
				.setAutoCancel(true)
				.setOngoing(false)
				.setDefaults(NotificationCompat.DEFAULT_VIBRATE)
				.setSmallIcon(R.mipmap.ic_launcher)
				.setContentIntent(pi);

		manager.notify(0, builder.build());
	}

	@Override
	protected void onResume() {
		super.onResume();
		((TextView) findViewById(R.id.tv_back_time)).setText(String.format("sLastBackTime: %1$d", sLastBackTime));
	}
}
