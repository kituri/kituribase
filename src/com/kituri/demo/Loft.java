package com.kituri.demo;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import com.kituri.app.controller.EntryAdapter;
import com.kituri.app.data.demo.DemoItemData;
import com.kituri.app.ui.BaseFragmentActivity;


public class Loft extends BaseFragmentActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}
	
}
