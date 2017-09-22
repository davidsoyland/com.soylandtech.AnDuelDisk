// Project: com.soylandtech.AnDuelDisk
// Created by Bjorn David Soyland <davidsoyland@gmail.com>
// on September 22, 2017 05:27 PM EDT
// Copyright (c) 2017
// File Name: AnDuelDisk.java
//

package com.soylandtech.andueldisk;

import com.soylandtech.andueldisk.database.PlayerDBAdapter;
import com.soylandtech.andueldisk.libs.SoundManager;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;

public class AnDuelDisk extends Activity {

	private int intLPSelected = 4000;
	private long player1ID = -1;
	private long player2ID = -1;
	private PlayerDBAdapter playerDB;
	private Cursor playersCursor;

	
	/** Activity LifeCylce Overrides */
	
	// Called when the activity is first created.
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.startup);
		
		//Load the SoundManger
		SoundManager.getInstance();
		SoundManager.initSounds(getApplicationContext());
		SoundManager.loadSounds();
		
		playerDB = new PlayerDBAdapter(this);
		playerDB.open();
		playersCursor = playerDB.getAllPlayers(PlayerDBAdapter.KEY_NAME, PlayerDBAdapter.SORT_ASCENDING);
		startManagingCursor(playersCursor);
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		//Setup Player Spinners
		Spinner spinPlayer1 = (Spinner)findViewById(R.id.spinPlayer1);
		Spinner spinPlayer2 = (Spinner)findViewById(R.id.spinPlayer2);
		
		SimpleCursorAdapter spinPlayer1Adapter = new SimpleCursorAdapter(this,
				android.R.layout.simple_spinner_item,
				playersCursor,
				new String[]{PlayerDBAdapter.KEY_NAME},
				new int[]{android.R.id.text1});
		spinPlayer1Adapter.setDropDownViewResource(
				android.R.layout.simple_spinner_dropdown_item);
		spinPlayer1.setAdapter(spinPlayer1Adapter);
		spinPlayer1.setOnItemSelectedListener(new Player1OnItemSelected());

		SimpleCursorAdapter spinPlayer2Adapter = new SimpleCursorAdapter(this,
				android.R.layout.simple_spinner_item,
				playersCursor,
				new String[]{PlayerDBAdapter.KEY_NAME},
				new int[]{android.R.id.text1});
		spinPlayer2Adapter.setDropDownViewResource(
				android.R.layout.simple_spinner_dropdown_item);
		spinPlayer2.setAdapter(spinPlayer2Adapter);
		spinPlayer2.setOnItemSelectedListener(new Player2OnItemSelected());
		
		//Setup Life Point Spinner
		Spinner spinLifePoints = (Spinner)findViewById(R.id.spinLifePoints);
		ArrayAdapter<CharSequence> adaptLifePoints = ArrayAdapter.createFromResource(this, 
				R.array.lifepointsel, android.R.layout.simple_spinner_item);
		adaptLifePoints.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinLifePoints.setAdapter(adaptLifePoints);
		spinLifePoints.setOnItemSelectedListener(new LifePointsOnItemSelected());

		//Set Up Player Manager Button
		Button btnManagePlayers = (Button)findViewById(R.id.btnManagePlayers);
		btnManagePlayers.setOnClickListener(new OnClickListener(){

			public void onClick(View view) {
				Intent managePlayers = new Intent(AnDuelDisk.this, ManagePlayers.class);
				startActivity(managePlayers);

			}

		});
		//Set Up Start Button
		Button btnDuelCalculatorStart = (Button)findViewById(R.id.btnDuelCalculatorStart);
		btnDuelCalculatorStart.setOnClickListener(new OnClickListener(){
			public void onClick(View view) {
				Intent duelCalculator = new Intent(AnDuelDisk.this, Calculator.class);
				duelCalculator.putExtra("intLPStart", intLPSelected);
				duelCalculator.putExtra("player1ID", player1ID);
				duelCalculator.putExtra("player2ID", player2ID);
				startActivity(duelCalculator);

			}

		});

	}
	
	// The activity is about to become visible.
	@Override
	protected void onStart() {
		super.onStart();
		
	}
	
	// The activity has become visible (it is now "resumed").
	@Override
	protected void onResume() {
		super.onResume();
		
	}
	
	// Another activity is taking focus (this activity is about to be "paused").
	@Override
	protected void onPause() {
		super.onPause();
		
	}
	
	// The activity is no longer visible (it is now "stopped")
	@Override
	protected void onStop() {
		super.onStop();
	}
	
	// The activity is about to be destroyed.
	@Override
	protected void onDestroy() {
		super.onDestroy();
		if(playerDB != null){
			playerDB.close();
		}
		SoundManager.cleanup();
	}
	
	@Override
	public void onConfigurationChanged(Configuration newConfig){
		super.onConfigurationChanged(newConfig);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		
	}
	
	//Spinner Item Selected Listeners
	protected class LifePointsOnItemSelected implements OnItemSelectedListener {

		public void onItemSelected(AdapterView<?> parent, View view, int pos,
				long id){
			intLPSelected = Integer.parseInt(parent.getItemAtPosition(pos).toString());

		}

		public void onNothingSelected(AdapterView<?> arg0) {
			// TODO Auto-generated method stub

		}	
	}
	protected class Player1OnItemSelected implements OnItemSelectedListener {

		public void onItemSelected(AdapterView<?> parent, View view, int pos,
				long id) {
			player1ID = id;

		}

		public void onNothingSelected(AdapterView<?> arg0) {
			// TODO Auto-generated 

		}

	}
	protected class Player2OnItemSelected implements OnItemSelectedListener {
		public void onItemSelected(AdapterView<?> parent, View view, int pos,
				long id) {
			player2ID = id;
		}
		public void onNothingSelected(AdapterView<?> arg0) {
			// TODO Auto-generated method stub

		}

	}
}

