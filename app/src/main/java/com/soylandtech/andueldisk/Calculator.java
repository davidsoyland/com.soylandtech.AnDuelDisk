// Project: com.soylandtech.AnDuelDisk
// Created by Bjorn David Soyland <davidsoyland@gmail.com>
// on September 22, 2017 05:27 PM EDT
// Copyright (c) 2017
// File Name: Calculator.java
//

package com.soylandtech.andueldisk;

import com.soylandtech.andueldisk.database.PlayerDBAdapter;
import com.soylandtech.andueldisk.libs.SoundManager;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Color;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;


public class Calculator extends Activity {
	
	/**
	 * Constants
	 */
	static final int MAX_NUMBER = 999999 ;
	static final int MAX_NUM_CHAR = 6 ;
	
	static final int DIALOG_ENDDUEL = 1 ;
	
	static final int NOBODY = 0 ;
	static final int PLAYER_1 = 1 ;
	static final int PLAYER_2 = 2 ;
	
	/**
	 * Variables
	 */
	private PlayerDBAdapter playerDB;
	private long player1ID = -1;
	private long player2ID = -1;
	private Integer lpStart = 8000;
	
	private Integer damageAmount = 0;
	private String inputBuffer = new String("");
	
	
	private String player1Name;
	private String player2Name;
	private Integer player1LP = 8000 ;
	private Integer player2LP = 8000 ;
	private int selectedPlayer = PLAYER_1;
	private String winnerOfDuel;
	
	@Override 
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.calculator);
		Bundle extras = getIntent().getExtras();
		//this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		this.setVolumeControlStream(AudioManager.STREAM_MUSIC);
		playerDB = new PlayerDBAdapter(this);
		
		player1ID = extras.getLong("player1ID");
		player2ID = extras.getLong("player2ID");
		lpStart = extras.getInt("intLPStart");
		
		player1Name = getPlayerName(player1ID);
		player2Name = getPlayerName(player2ID);
		
		//Set up Player Buttons
		Button btnPlayer1 = (Button) findViewById(R.id.btnPlayer1);
		Button btnPlayer2 = (Button) findViewById(R.id.btnPlayer2);
		
		btnPlayer1.setText(player1Name);
		btnPlayer2.setText(player2Name);
		
		resetDuel();
		
		
		btnPlayer1.setOnClickListener(new OnClickListener(){

			public void onClick(View arg0) {
				selectPlayer(PLAYER_1);
			}
			
		});
		
		btnPlayer2.setOnClickListener(new OnClickListener(){

			public void onClick(View arg0) {
				selectPlayer(PLAYER_2);
			}
			
		});
		
		//Setup the Number Buttons
		Button btnNum0 = (Button) findViewById(R.id.btnNum0);
		Button btnNum00 = (Button) findViewById(R.id.btnNum00);
		Button btnNum000 = (Button) findViewById(R.id.btnNum000);
		Button btnNum1 = (Button) findViewById(R.id.btnNum1);
		Button btnNum2 = (Button) findViewById(R.id.btnNum2);
		Button btnNum3 = (Button) findViewById(R.id.btnNum3);
		Button btnNum4 = (Button) findViewById(R.id.btnNum4);
		Button btnNum5 = (Button) findViewById(R.id.btnNum5);
		Button btnNum6 = (Button) findViewById(R.id.btnNum6);
		Button btnNum7 = (Button) findViewById(R.id.btnNum7);
		Button btnNum8 = (Button) findViewById(R.id.btnNum8);
		Button btnNum9 = (Button) findViewById(R.id.btnNum9);
		
		btnNum0.setOnClickListener(new OnClickListener(){

			public void onClick(View v) {
				appendDamage("0");
				
			}
			
		});
		
		btnNum00.setOnClickListener(new OnClickListener(){

			public void onClick(View v) {
				appendDamage("00");
				
			}
			
		});
		
		btnNum000.setOnClickListener(new OnClickListener(){

			public void onClick(View v) {
				appendDamage("000");
				
			}
			
		});
		
		btnNum1.setOnClickListener(new OnClickListener(){

			public void onClick(View v) {
				appendDamage("1");
				
			}
			
		});
		
		btnNum2.setOnClickListener(new OnClickListener(){

			public void onClick(View v) {
				appendDamage("2");
				
			}
			
		});
		
		btnNum3.setOnClickListener(new OnClickListener(){

			public void onClick(View v) {
				appendDamage("3");
				
			}
			
		});
		
		btnNum4.setOnClickListener(new OnClickListener(){

			public void onClick(View v) {
				appendDamage("4");
				
			}
			
		});
		
		btnNum5.setOnClickListener(new OnClickListener(){

			public void onClick(View v) {
				appendDamage("5");
				
			}
			
		});
		
		btnNum6.setOnClickListener(new OnClickListener(){

			public void onClick(View v) {
				appendDamage("6");
				
			}
			
		});
		
		btnNum7.setOnClickListener(new OnClickListener(){

			public void onClick(View v) {
				appendDamage("7");
				
			}
			
		});
		
		btnNum8.setOnClickListener(new OnClickListener(){

			public void onClick(View v) {
				appendDamage("8");
				
			}
			
		});
		
		btnNum9.setOnClickListener(new OnClickListener(){

			public void onClick(View v) {
				appendDamage("9");
				
			}
			
		});
		
		
		//Setup Operation Buttons
		
		Button btnAdd = (Button) findViewById(R.id.btnAdd);
		Button btnSubtract = (Button) findViewById(R.id.btnSubtract);
		Button btnClear = (Button)findViewById(R.id.btnClear);
		
		btnAdd.setOnClickListener(new OnClickListener(){

			public void onClick(View arg0) {
				increasePlayerLP(selectedPlayer, damageAmount);
				clearDamage();
				
			}
			
		});
		
		btnSubtract.setOnClickListener(new OnClickListener(){

			public void onClick(View v) {
				decreasePlayerLP(selectedPlayer, damageAmount,0);
				clearDamage();
				
			}
			
		});
		
		btnClear.setOnClickListener(new OnClickListener(){

			public void onClick(View v) {
				clearDamage();
				
			}
			
		});
		
		Button btnHalfLP = (Button) findViewById(R.id.btnHalfLP);
		Button btnDamage = (Button) findViewById(R.id.btnDamage);
		Button btnOTK = (Button) findViewById(R.id.btnOTK);
		Button btnDice = (Button) findViewById(R.id.btnDice);
		Button btnCoin = (Button) findViewById(R.id.btnCoin);
		
		btnHalfLP.setOnClickListener(new OnClickListener(){

			public void onClick(View arg0) {
				halfLP(selectedPlayer);
				
			}
			
		});
		
		btnDamage.setOnClickListener(new OnClickListener(){

			public void onClick(View arg0) {
				Intent battlestep = new Intent(Calculator.this, Battle.class);
				battlestep.putExtra("PLAYER1_NAME", player1Name);
				battlestep.putExtra("PLAYER2_NAME", player2Name);
				startActivityForResult(battlestep, 1);
			}
			
		});
		
		btnOTK.setOnClickListener(new OnClickListener(){

			public void onClick(View arg0) {
				oneTurnKill(selectedPlayer);
				
			}
			
		});
		
		btnDice.setOnClickListener(new OnClickListener(){

			public void onClick(View arg0) {
				Intent diceroll = new Intent(Calculator.this, DiceRoll.class);
				startActivity(diceroll);
			}
			
		});
		
		btnCoin.setOnClickListener(new OnClickListener(){

			public void onClick(View arg0) {
				Intent coinflip = new Intent(Calculator.this, CoinFlip.class);
				startActivity(coinflip);
				
			}
			
		});
	}
	
	@Override
	public void onSaveInstanceState(Bundle savedInstanceState){
		savedInstanceState.putLong("player1ID", player1ID);
		savedInstanceState.putLong("player2ID", player2ID);
		savedInstanceState.putInt("lpStart", lpStart);
		savedInstanceState.putInt("damageAmount", damageAmount);
		savedInstanceState.putString("player1Name", player1Name);
		savedInstanceState.putString("player2Name", player2Name);
		savedInstanceState.putInt("player1LP", player1LP);
		savedInstanceState.putInt("player2LP", player2LP);
		savedInstanceState.putInt("selectedPlayer", selectedPlayer);
		super.onSaveInstanceState(savedInstanceState);
		
	}
	
	@Override
	public void onRestoreInstanceState(Bundle savedInstanceState){
		super.onRestoreInstanceState(savedInstanceState);
		player1ID = savedInstanceState.getLong("player1ID");
		player2ID = savedInstanceState.getLong("player2ID");
		lpStart = savedInstanceState.getInt("lpStart");
		damageAmount = savedInstanceState.getInt("damageAmount");
		player1Name = savedInstanceState.getString("player1Name");
		player2Name = savedInstanceState.getString("player2Name");
		player1LP = savedInstanceState.getInt("player1LP");
		player2LP = savedInstanceState.getInt("player2LP");
		selectedPlayer = savedInstanceState.getInt("selectedPlayer");
		
	}
	
	@Override
	public void onConfigurationChanged(Configuration newConfig){
		super.onConfigurationChanged(newConfig);
		//setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		
	}
	
	protected void oneTurnKill(int player) {
		if ( selectedPlayer == PLAYER_1 ) {
			player1LP = player1LP - player1LP ;
			SoundManager.playSound(SoundManager.LP_ZERO);
			TextView txtPlayerLP = (TextView) findViewById(R.id.txtPlayer1LP);
			txtPlayerLP.setText(player1LP.toString());
			endDuel(PLAYER_2);
		}
		else{
			player2LP = player2LP - player2LP ;
			SoundManager.playSound(SoundManager.LP_ZERO);
			TextView txtPlayerLP = (TextView) findViewById(R.id.txtPlayer2LP);
			txtPlayerLP.setText(player1LP.toString());
			endDuel(PLAYER_1);
		}
		
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data){
		if (requestCode == 1){
			if(resultCode == RESULT_OK){
				int player = data.getIntExtra("player", NOBODY);
				int amount = data.getIntExtra("amount", 0);
				boolean mondestroyed = data.getBooleanExtra("mondestroyed", false);
				long delay = 0;
				if (mondestroyed == true){
					SoundManager.playSound(SoundManager.MONSTER_DESTROYED, 0);
					delay  = 2000;
				}
				if (player != NOBODY ){
					decreasePlayerLP(player, amount, delay);
				}
				
			}
		}
	}
	
	protected Dialog onCreateDialog(int id){
		AlertDialog dialog;
		switch(id){
		case DIALOG_ENDDUEL:
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setCancelable(false)
		           .setMessage(winnerOfDuel + " has won the duel. Play agian?")
			       .setPositiveButton("Yes", new Dialog.OnClickListener(){

					public void onClick(DialogInterface dialog, int arg1) {
						resetDuel();
						dialog.dismiss();
						
					}
			    	   
			       })
			       .setNegativeButton("No", new Dialog.OnClickListener(){

					public void onClick(DialogInterface dialog, int which) {
						Calculator.this.finish();
						
					}
			    	   
			       });
			dialog = builder.create();
			break;
		default:
			dialog = null;
		}
		return dialog;
	}
	
	private void halfLP(int selectedPlayer) {
		if ( selectedPlayer == PLAYER_1 ) {
			player1LP = player1LP / 2 ;
			SoundManager.playSound(SoundManager.LP_COUNTER);
			TextView txtPlayerLP = (TextView) findViewById(R.id.txtPlayer1LP);
			txtPlayerLP.setText(player1LP.toString());
		}
		else{
			player2LP = player2LP / 2 ;
			SoundManager.playSound(SoundManager.LP_COUNTER);
			TextView txtPlayerLP = (TextView) findViewById(R.id.txtPlayer2LP);
			txtPlayerLP.setText(player1LP.toString());
		}
		
	}

	private void resetDuel() {
		selectPlayer(PLAYER_1);
		clearDamage();
		resetLP();
		
	}

	private void resetLP() {
		TextView txtPlayer1LP = (TextView) findViewById(R.id.txtPlayer1LP);
		TextView txtPlayer2LP = (TextView) findViewById(R.id.txtPlayer2LP);
		player1LP = lpStart;
		player2LP = lpStart;
		SoundManager.playSound(SoundManager.DUEL_DISK_ACTIVATE);
		SoundManager.playSound(SoundManager.LP_COUNTER_APPEARS, 1000);
		txtPlayer1LP.setText(player1LP.toString());
		txtPlayer2LP.setText(player2LP.toString());
		
	}

	private void decreasePlayerLP(int player, int amount, long delay) {
		
		if( player == PLAYER_1 ){
			
			if(player1LP < MAX_NUMBER){
				TextView txtPlayerLP = (TextView) findViewById(R.id.txtPlayer1LP);
				player1LP = player1LP - amount ;
				txtPlayerLP.setText(player1LP.toString());
				if (player1LP <= 0){
					SoundManager.playSound(SoundManager.LP_ZERO,delay);
					endDuel(PLAYER_2);
				}
				else {
					SoundManager.playSound(SoundManager.LP_COUNTER,delay);
				}
			}
			
		}
		else if (player == PLAYER_2){
			if(player2LP < MAX_NUMBER){
				TextView txtPlayerLP = (TextView) findViewById(R.id.txtPlayer2LP);
				player2LP = player2LP - amount ;
				txtPlayerLP.setText(player2LP.toString());
				if (player2LP <= 0){
					SoundManager.playSound(SoundManager.LP_ZERO, 0);
					endDuel(PLAYER_1);
				}
				else {
					SoundManager.playSound(SoundManager.LP_COUNTER, 0);
				}
			}
		}
		
	}

	private void endDuel(int winner) {
		
		playerDB.open();
		Cursor player1 = playerDB.getPlayer(player1ID);
		Cursor player2 = playerDB.getPlayer(player2ID);
		switch(winner){
		case PLAYER_1:
			int player1wins = player1.getInt(player1.getColumnIndexOrThrow(PlayerDBAdapter.KEY_WINS));
			int player2losses = player2.getInt(player2.getColumnIndexOrThrow(PlayerDBAdapter.KEY_LOSSES));
			playerDB.updatePlayerWin(player1ID, player1wins + 1);
			playerDB.updatePlayerLosses(player2ID, player2losses + 1);
			winnerOfDuel = player1Name;
			break;
		case PLAYER_2:
			int player2wins = player2.getInt(player2.getColumnIndexOrThrow(PlayerDBAdapter.KEY_WINS));
			int player1losses = player1.getInt(player1.getColumnIndexOrThrow(PlayerDBAdapter.KEY_LOSSES));
			playerDB.updatePlayerWin(player2ID, player2wins + 1);
			playerDB.updatePlayerLosses(player1ID, player1losses + 1);
			winnerOfDuel = player2Name;
			break;
		}
		playerDB.close();
		showDialog(DIALOG_ENDDUEL);
	}

	private void clearDamage() {
		TextView txtDamageAmount = (TextView) findViewById(R.id.txtDamageAmount);
		damageAmount = 0 ;
		inputBuffer = "";
		txtDamageAmount.setText(damageAmount.toString());
	}

	private void increasePlayerLP(int player, int amount) {
		
		
		if( player == PLAYER_1 ){
			
			if(player1LP < MAX_NUMBER){
				SoundManager.playSound(SoundManager.LP_COUNTER);
				TextView txtPlayerLP = (TextView) findViewById(R.id.txtPlayer1LP);
				player1LP = player1LP + amount ;
				txtPlayerLP.setText(player1LP.toString());
			}
			
		}
		else if (player == PLAYER_2){
			if(player2LP < MAX_NUMBER){
				SoundManager.playSound(SoundManager.LP_COUNTER);
				TextView txtPlayerLP = (TextView) findViewById(R.id.txtPlayer2LP);
				player2LP = player2LP + amount ;
				txtPlayerLP.setText(player2LP.toString());
			}
		}
	}

	private void appendDamage(String num) {
		
		if(inputBuffer.length() < MAX_NUM_CHAR){
			TextView txtDamageAmount = (TextView) findViewById(R.id.txtDamageAmount);
			inputBuffer = inputBuffer + num;
			damageAmount = Integer.valueOf(inputBuffer);
			txtDamageAmount.setText(inputBuffer);	
		}
		
	}

	private void selectPlayer(int player) {
		Button btnPlayer1 = (Button) findViewById(R.id.btnPlayer1);
		Button btnPlayer2 = (Button) findViewById(R.id.btnPlayer2);
		selectedPlayer = player;
		if ( player == PLAYER_2 ){
			btnPlayer2.setTextColor(Color.RED);
			btnPlayer1.setTextColor(Color.BLACK);
		}
		else {
			btnPlayer2.setTextColor(Color.BLACK);
			btnPlayer1.setTextColor(Color.RED);
		}
		
	}

	private String getPlayerName(long playerid) {
		playerDB.open();
		Cursor player = playerDB.getPlayer(playerid);
		String playername = player.getString(player.getColumnIndexOrThrow(PlayerDBAdapter.KEY_NAME));
		playerDB.close();
		return playername;
	}
	
}
