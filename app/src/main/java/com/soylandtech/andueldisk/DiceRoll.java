// Project: com.soylandtech.AnDuelDisk
// Created by Bjorn David Soyland <davidsoyland@gmail.com>
// on September 22, 2017 05:27 PM EDT
// Copyright (c) 2017
// File Name: DiceRoll.java
//

package com.soylandtech.andueldisk;

import java.util.Random;

import com.soylandtech.andueldisk.libs.SoundManager;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class DiceRoll extends Activity {
	
	
	private AnimationDrawable diceRollAnim;
	private ImageView imgDice;
	private TextView txtDice;
	private TextView txtDiceMessage;
	private long totalDuration = 0;
	private Handler animHandler = new Handler();



	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.diceroll);
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		
		txtDice = (TextView) findViewById(R.id.txtDice);
		imgDice = (ImageView) findViewById(R.id.imgDice);
		txtDiceMessage = (TextView) findViewById(R.id.txtDiceMessage);
		
		imgDice.setOnClickListener(new OnClickListener(){
			
			
			
			public void onClick(View arg0) {
				
				final int diceRoll = rollDice();
				txtDice.setVisibility(View.INVISIBLE);
				txtDiceMessage.setVisibility(View.INVISIBLE);
				totalDuration = 0 ;
				imgDice.setClickable(false);
				switch(diceRoll){
				case 1:
					imgDice.setImageResource(R.drawable.dicerollanim_1);
					break;
				case 2:
					imgDice.setImageResource(R.drawable.dicerollanim_2);
					break;
				case 3:
					imgDice.setImageResource(R.drawable.dicerollanim_3);
					break;
				case 4:
					imgDice.setImageResource(R.drawable.dicerollanim_4);
					break;
				case 5:
					imgDice.setImageResource(R.drawable.dicerollanim_5);
					break;
				case 6:
					imgDice.setImageResource(R.drawable.dicerollanim_6);
					break;
				default:
					break;
				}
				diceRollAnim = (AnimationDrawable) imgDice.getDrawable();
				
				for(int i = 0; i < diceRollAnim.getNumberOfFrames(); i++ ){
					totalDuration += diceRollAnim.getDuration(i);
				}
				Runnable animRunnable = new Runnable(){

					public void run() {
						//diceRollAnim.stop();
						switch(diceRoll){
						case 1:
							imgDice.setImageResource(R.drawable.dice_1);
							txtDice.setText("You rolled a \n 1.");
							txtDice.setVisibility(View.VISIBLE);
							txtDiceMessage.setVisibility(View.VISIBLE);
							imgDice.setClickable(true);
							break;
						case 2:
							imgDice.setImageResource(R.drawable.dice_2);
							txtDice.setText("You rolled a \n 2.");
							txtDice.setVisibility(View.VISIBLE);
							txtDiceMessage.setVisibility(View.VISIBLE);
							imgDice.setClickable(true);
							break;
						case 3:
							imgDice.setImageResource(R.drawable.dice_3);
							txtDice.setText("You rolled a \n 3.");
							txtDice.setVisibility(View.VISIBLE);
							txtDiceMessage.setVisibility(View.VISIBLE);
							imgDice.setClickable(true);
							break;
						case 4:
							imgDice.setImageResource(R.drawable.dice_4);
							txtDice.setText("You rolled a \n 4.");
							txtDice.setVisibility(View.VISIBLE);
							txtDiceMessage.setVisibility(View.VISIBLE);
							imgDice.setClickable(true);
							break;
						case 5:
							imgDice.setImageResource(R.drawable.dice_5);
							txtDice.setText("You rolled a \n 5.");
							txtDice.setVisibility(View.VISIBLE);
							txtDiceMessage.setVisibility(View.VISIBLE);
							imgDice.setClickable(true);
							break;
						case 6:
							imgDice.setImageResource(R.drawable.dice_6);
							txtDice.setText("You rolled a \n 6.");
							txtDice.setVisibility(View.VISIBLE);
							txtDiceMessage.setVisibility(View.VISIBLE);
							imgDice.setClickable(true);
							break;
						default:
							break;
						}
						SoundManager.stopSound(SoundManager.DICE_SHAKE);
						SoundManager.playSound(SoundManager.DICE_ROLL);

					}

				};
				diceRollAnim.start();
				SoundManager.playSound(SoundManager.DICE_SHAKE);
				animHandler.postDelayed(animRunnable, totalDuration);
				
				
			}
			
		});
	}
	
	@Override
	public void onConfigurationChanged(Configuration newConfig){
		super.onConfigurationChanged(newConfig);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		
	}
	
	private int rollDice(){
		Random rand = new Random();
		int face = rand.nextInt(6) + 1;
		return face;
	}

}
