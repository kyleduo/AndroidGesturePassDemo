package com.kyleduo.gesturepass;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		findViewById(R.id.next_bt).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(MainActivity.this, SecondActivity.class));
			}
		});
	}

	@Override
	protected void onResume() {
		super.onResume();
		((TextView) findViewById(R.id.tv_back_time)).setText(String.format("sLastBackTime: %1$d", sLastBackTime));
	}
}
