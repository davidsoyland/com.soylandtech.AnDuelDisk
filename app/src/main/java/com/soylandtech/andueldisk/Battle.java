// Project: com.soylandtech.AnDuelDisk
// Created by Bjorn David Soyland <davidsoyland@gmail.com>
// on September 22, 2017 05:27 PM EDT
// Copyright (c) 2017
// File Name: Battle.java
//

package com.soylandtech.andueldisk;

import com.soylandtech.andueldisk.libs.MonsterCardView;
import com.soylandtech.andueldisk.libs.MonsterCardView.MonsterCardMenuInfo;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Battle extends Activity {
	
	static final int PLAYER_1 = 1 ;
	static final int PLAYER_2 = 2 ;
	static final int NOBODY = 3;
	
	static final int ATK = 1 ;
	static final int DEF = 2 ;
	
	private String player1Name;
	private String player2Name;
	
	private int p1Amount;
	private int p1Pos = ATK;
	
	private int p2Amount;
	private int p2Pos = ATK;
	
	private Integer damageAmount;
	private MonsterCardView imgBattleP1;
	private MonsterCardView imgBattleP2;
	private Drawable monsterCardDef;
	private Drawable monsterCardAtk;
	private EditText edtxtBattleP1Amount;
	private EditText edtxtBattleP2Amount;
	private TextView txtP1Pos;
	private TextView txtP2Pos;
	
	
	@Override 
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		setContentView(R.layout.battle);
		Bundle extras = getIntent().getExtras();
		
		player1Name = extras.getString("PLAYER1_NAME");
		player2Name = extras.getString("PLAYER2_NAME");
		monsterCardDef = getResources().getDrawable(R.drawable.hmonstereffect);
		monsterCardAtk = getResources().getDrawable(R.drawable.monstereffect);
		imgBattleP1 = (MonsterCardView) findViewById(R.id.imgBattleP1);
		imgBattleP2 = (MonsterCardView) findViewById(R.id.imgBattleP2);
		TextView txtP1Name = (TextView) findViewById(R.id.txtBattleP1Name);
		TextView txtP2Name = (TextView) findViewById(R.id.txtBattleP2Name);
		txtP1Name.setText(player1Name);
		txtP2Name.setText(player2Name);
		txtP1Pos = (TextView) findViewById(R.id.txtBattleP1Pos);
		txtP2Pos = (TextView) findViewById(R.id.txtBattleP2Pos);
		edtxtBattleP1Amount = (EditText) findViewById(R.id.edtxtBattleP1Amount);
		edtxtBattleP2Amount = (EditText) findViewById(R.id.edtxtBattleP2Amount);
		imgBattleP1.setImageDrawable(monsterCardAtk);
		imgBattleP2.setImageDrawable(monsterCardAtk);
		registerForContextMenu(imgBattleP1);
		registerForContextMenu(imgBattleP2);
		
		imgBattleP1.setOnClickListener(new OnClickListener(){

			public void onClick(View arg0) {
				imgBattleP1.showContextMenu();
				
			}
		
			
		});
		
		imgBattleP2.setOnClickListener(new OnClickListener(){

			public void onClick(View v) {
				imgBattleP2.showContextMenu();
				
			}
			
		});
	}
	
	@Override
	public void onConfigurationChanged(Configuration newConfig){
		super.onConfigurationChanged(newConfig);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		
	}
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
	                                ContextMenuInfo menuInfo) {
	    super.onCreateContextMenu(menu, v, menuInfo);
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.monstercard, menu);
	    if ( v.getId() == R.id.imgBattleP1 ){
	    	if ( p1Pos == ATK ){
	    		menu.findItem(R.id.menuAttack).setVisible(true);
	    		menu.findItem(R.id.menuSwitchDef).setVisible(true);
	    		menu.findItem(R.id.menuSwitchAtk).setVisible(false);
	    	}
	    	else if (p1Pos == DEF) {
	    		
	    		menu.findItem(R.id.menuAttack).setVisible(false);
	    		menu.findItem(R.id.menuSwitchDef).setVisible(false);
	    		menu.findItem(R.id.menuSwitchAtk).setVisible(true);
	    	}
	    } 
	    else if ( v.getId() == R.id.imgBattleP2 ){
	    	if ( p2Pos == ATK ){
	    		menu.findItem(R.id.menuAttack).setVisible(true);
	    		menu.findItem(R.id.menuSwitchDef).setVisible(true);
	    		menu.findItem(R.id.menuSwitchAtk).setVisible(false);
	    	}
	    	else if (p2Pos == DEF) {
	    		
	    		menu.findItem(R.id.menuAttack).setVisible(false);
	    		menu.findItem(R.id.menuSwitchDef).setVisible(false);
	    		menu.findItem(R.id.menuSwitchAtk).setVisible(true);
	    	}
	    } 
	    
	}	
	
	@Override
	public boolean onContextItemSelected(MenuItem item){
		MonsterCardMenuInfo info = (MonsterCardMenuInfo) item.getMenuInfo();
		MonsterCardView img = (MonsterCardView) info.targetView;
		switch (item.getItemId()){
			case R.id.menuSwitchAtk:
				if (info.targetView.getId() == R.id.imgBattleP1){
					p1Pos = ATK;
					txtP1Pos.setText("ATK:");
				}
				else if (info.targetView.getId() == R.id.imgBattleP2){
					p2Pos = ATK;
					txtP2Pos.setText("ATK:");
				}
				img.setImageDrawable(monsterCardAtk);
				return true;
			case R.id.menuAttack:
				if (info.targetView.getId() == R.id.imgBattleP1){
					attackMonster(PLAYER_2);
				}
				else if (info.targetView.getId() == R.id.imgBattleP2){
					attackMonster(PLAYER_1);
				}
				return true;
			case R.id.menuSwitchDef:
				if (info.targetView.getId() == R.id.imgBattleP1){
					p1Pos = DEF;
					txtP1Pos.setText("DEF:");
				}
				else if (info.targetView.getId() == R.id.imgBattleP2){
					p2Pos = DEF;
					txtP2Pos.setText("DEF:");
				}
				img.setImageDrawable(monsterCardDef);
				return true;
			default:
				return super.onContextItemSelected(item);
		}
	}

	private void attackMonster(int player) {
		
		if (!edtxtBattleP1Amount.getText().toString().isEmpty()){
			p1Amount = Integer.valueOf(edtxtBattleP1Amount.getText().toString()); 
		}
		else {
			p1Amount = 0;
		}
		if (!edtxtBattleP2Amount.getText().toString().isEmpty()){
			p2Amount = Integer.valueOf(edtxtBattleP2Amount.getText().toString());
		}
		else {
			p2Amount = 0 ;
		}
		Intent returnIntent = new Intent();
		String strToast = new String();
		
		int resultCode = RESULT_CANCELED;
		if (player == PLAYER_1){
			if ( p1Pos == ATK ){
				if ( p2Amount > p1Amount ){
					damageAmount = p2Amount - p1Amount;
					strToast = player1Name + "'s monster is destroyed and takes " + damageAmount.toString() 
							+ " points of damage to his Life Points!";
					returnIntent.putExtra("player", PLAYER_1);
					returnIntent.putExtra("amount", damageAmount);
					returnIntent.putExtra("mondestroyed", true);
					resultCode = RESULT_OK;
				}
				else if (p2Amount < p1Amount){
					damageAmount = p1Amount - p2Amount;
					strToast = player2Name + "'s monster is destroyed and takes " + damageAmount.toString() 
							+ " points of damage to his Life Points!";
					returnIntent.putExtra("player", PLAYER_2);
					returnIntent.putExtra("amount", damageAmount);
					returnIntent.putExtra("mondestroyed", true);
					resultCode = RESULT_OK;
				}
				else if (p2Amount == p1Amount){
					damageAmount = 0;
					strToast = "Double Knock Out, both monsters are destroyed, and nobody takes damage this battle!";
					returnIntent.putExtra("player", NOBODY);
					returnIntent.putExtra("amount", damageAmount);
					returnIntent.putExtra("mondestroyed", true);
					resultCode = RESULT_OK;
				}
				else {
					resultCode = RESULT_CANCELED;
				}
			}
			else if ( p1Pos == DEF ){
				if ( p2Amount > p1Amount ){
					damageAmount = 0;
					strToast = player1Name + "'s monster is destroyed, and nobody takes damage this battle!";
					returnIntent.putExtra("player", NOBODY);
					returnIntent.putExtra("amount", damageAmount);
					returnIntent.putExtra("mondestroyed", true);
					resultCode = RESULT_OK;
				}
				else if (p2Amount < p1Amount){
					damageAmount = p1Amount - p2Amount;
					strToast = "No monster is destroyed, and " + player2Name + " takes " + damageAmount.toString() 
							+ " points of damage to his Life Points!";
					returnIntent.putExtra("player", PLAYER_2);
					returnIntent.putExtra("amount", damageAmount);
					returnIntent.putExtra("mondestroyed", false);
					resultCode = RESULT_OK;
				}
				else if (p2Amount == p1Amount ){
					damageAmount = 0;
					strToast = "No monster is destroyed, and nobody takes damage this battle!";
					returnIntent.putExtra("player", NOBODY);
					returnIntent.putExtra("amount", damageAmount);
					returnIntent.putExtra("mondestroyed", false);
					resultCode = RESULT_OK;
				}
				else {
					resultCode = RESULT_CANCELED;
				}
			}
		}
		else if (player == PLAYER_2){
			if ( p2Pos == ATK ){
				if ( p1Amount > p2Amount ){
					damageAmount = p1Amount - p2Amount;
					strToast = player2Name + "'s monster is destroyed and takes " + damageAmount.toString() 
							+ " points of damage to his Life Points!";
					returnIntent.putExtra("player", PLAYER_2);
					returnIntent.putExtra("amount", damageAmount);
					returnIntent.putExtra("mondestroyed", true);
					resultCode = RESULT_OK;
				}
				else if (p1Amount < p2Amount){
					damageAmount = p2Amount - p1Amount;
					strToast = player1Name + "'s monster is destroyed and takes " + damageAmount.toString() 
							+ " points of damage to his Life Points!";
					returnIntent.putExtra("player", PLAYER_1);
					returnIntent.putExtra("amount", damageAmount);
					returnIntent.putExtra("mondestroyed", true);
					resultCode = RESULT_OK;
				}
				else if (p1Amount == p2Amount){
					damageAmount = 0;
					strToast = "Double Knock Out, both monsters are destroyed, and nobody takes damage this battle!";
					returnIntent.putExtra("player", NOBODY);
					returnIntent.putExtra("amount", damageAmount);
					returnIntent.putExtra("mondestroyed", true);
					resultCode = RESULT_OK;
				}
				else {
					resultCode = RESULT_CANCELED;
				}
			}
			else if ( p2Pos == DEF ){
				if ( p1Amount > p2Amount ){
					damageAmount = 0;
					strToast = player2Name + "'s monster is destroyed, and nobody takes damage this battle!";
					returnIntent.putExtra("player", NOBODY);
					returnIntent.putExtra("amount", damageAmount);
					returnIntent.putExtra("mondestroyed", true);
					resultCode = RESULT_OK;
				}
				else if (p1Amount < p2Amount){
					damageAmount = p2Amount - p1Amount;
					strToast = "No monster is destroyed, and " + player1Name + " takes " + damageAmount.toString() 
							+ " points of damage to his Life Points!";
					returnIntent.putExtra("player", PLAYER_1);
					returnIntent.putExtra("amount", damageAmount);
					returnIntent.putExtra("mondestroyed", false);
					resultCode = RESULT_OK;
				}
				else if (p1Amount == p2Amount){
					damageAmount = 0;
					strToast = "No monster is destroyed, and nobody takes damage this battle!";
					returnIntent.putExtra("player", NOBODY);
					returnIntent.putExtra("amount", damageAmount);
					returnIntent.putExtra("mondestroyed", false);
					resultCode = RESULT_OK;
				}
				else {
					resultCode = RESULT_CANCELED;
				}
			}
		}
		Toast.makeText(getApplicationContext(), strToast, Toast.LENGTH_LONG).show();
		setResult(resultCode, returnIntent);
		finish();
	}

}
