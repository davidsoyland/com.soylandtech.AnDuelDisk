// Project: com.soylandtech.AnDuelDisk
// Copyright (c) 2017 Bjorn David Soyland <davidsoyland@gmail.com>
// This project is licensed under the terms of the
// GNU GENERAL PUBLIC LICENSE Version 3.0
// File Name: PlayerDetails.java
// Created: September 26, 2017 01:00 AM EDT

package com.soylandtech.andueldisk;

import com.soylandtech.andueldisk.database.PlayerDBAdapter;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class PlayerDetails extends Activity {
	private PlayerDBAdapter playerDB;
	private EditText edTxtPlayerName;
	private EditText edTxtPlayerNotes;
	private TextView txtPlayerWins;
	private TextView txtPlayerLosses;
	private Long rowId;
	
	@Override
	protected void onCreate(Bundle bundle){
		super.onCreate(bundle);
		setContentView(R.layout.playerdetails);
		playerDB = new PlayerDBAdapter(this);
		playerDB.open();
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		
		Bundle extras = getIntent().getExtras();
		if(bundle != null){
			rowId = bundle.getLong(PlayerDBAdapter.KEY_ROWID);
		}
		else {
			rowId = null;
		}
		if(extras != null){
			rowId = extras.getLong(PlayerDBAdapter.KEY_ROWID);
		}
		
		
		edTxtPlayerName = (EditText) findViewById(R.id.edTxtPlayerName);
		edTxtPlayerNotes = (EditText) findViewById(R.id.edTxtPlayerNotes);
		txtPlayerWins = (TextView) findViewById(R.id.txtPlayerWins);
		txtPlayerLosses = (TextView) findViewById(R.id.txtPlayerLosses);
		
		Button btnSavePlayer = (Button) findViewById(R.id.btnSavePlayer);
		Button btnCancelPlayer = (Button) findViewById(R.id.btnCancelPlayer);
		
		populateFields();
		
		btnSavePlayer.setOnClickListener(new OnClickListener(){

			public void onClick(View v) {
				savePlayer();
				finish();
				
			}
			
		});
		btnCancelPlayer.setOnClickListener(new OnClickListener(){

			public void onClick(View v) {
				finish();
				
			}
			
		});
		
		
	}
	
	@Override
	public void onConfigurationChanged(Configuration newConfig){
		super.onConfigurationChanged(newConfig);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		
	}
	
	private void savePlayer() {
		String name = edTxtPlayerName.getText().toString();
		String notes = edTxtPlayerNotes.getText().toString();
		Integer wins = Integer.valueOf(txtPlayerWins.getText().toString());
		Integer losses = Integer.valueOf(txtPlayerLosses.getText().toString());
		
		
		if (rowId == null){
			long id = playerDB.createPlayer(name, notes);
			if (id > 0){
				rowId = id;
			}
		}
		else {
			playerDB.updatePlayer(rowId, name, notes, wins, losses);
		}
		
	}
	
	private void populateFields() {
		if (rowId != null){
			Cursor player = playerDB.getPlayer(rowId);
			Integer playerwins = player.getInt(player
					.getColumnIndexOrThrow(PlayerDBAdapter.KEY_WINS));
			Integer playerlosses = player.getInt(player
					.getColumnIndexOrThrow(PlayerDBAdapter.KEY_LOSSES));
			
			edTxtPlayerName.setText(player.getString(player
					.getColumnIndexOrThrow(PlayerDBAdapter.KEY_NAME)));
			edTxtPlayerNotes.setText(player.getString(player
					.getColumnIndexOrThrow(PlayerDBAdapter.KEY_NOTES)));
			txtPlayerWins.setText(playerwins.toString());
			txtPlayerLosses.setText(playerlosses.toString());
		}
	}
}