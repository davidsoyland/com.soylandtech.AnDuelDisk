// Project: com.soylandtech.AnDuelDisk
// Copyright (c) 2017 Bjorn David Soyland <davidsoyland@gmail.com>
// This project is licensed under the terms of the
// GNU GENERAL PUBLIC LICENSE Version 3.0
// File Name: CoinFlip.java
// Created: September 26, 2017 01:00 AM EDT

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

public class CoinFlip extends Activity {
	
	static final boolean HEADS = true;
	static final boolean TAILS = false;
	
	private boolean coinState = HEADS;
	private AnimationDrawable coinFlipAnim;
	private ImageView imgCoin;
	private TextView txtCoin;
	private TextView txtCoinMessage;
	private long totalDuration = 0;
	private Handler animHandler = new Handler();



	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.coinflip);
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		txtCoin = (TextView) findViewById(R.id.txtCoinState);
		txtCoinMessage = (TextView) findViewById(R.id.txtCoinMessage);
		imgCoin = (ImageView) findViewById(R.id.imgCoinFlip);
		
		imgCoin.setOnClickListener(new OnClickListener(){
			
			
			
			public void onClick(View arg0) {
				
				final boolean coinFlip = flipCoin();
				totalDuration = 0;
				txtCoin.setVisibility(View.INVISIBLE);
				txtCoinMessage.setVisibility(View.INVISIBLE);
				imgCoin.setClickable(false);
				
				if (coinState == HEADS){
					if (coinFlip == HEADS){
						imgCoin.setImageResource(R.drawable.coinflipheads2headsanim);
						coinState = HEADS;
					}
					else if (coinFlip == TAILS){
						imgCoin.setImageResource(R.drawable.coinflipheads2tailsanim);
						coinState = TAILS;
					}
					
				}
				else if (coinState == TAILS){
					if (coinFlip == HEADS){
						imgCoin.setImageResource(R.drawable.coinfliptails2headsanim);
						coinState = HEADS;
					}
					else if (coinFlip == TAILS){
						imgCoin.setImageResource(R.drawable.coinfliptails2tailsanim);
						coinState = TAILS;
					}
				}
				
				coinFlipAnim = (AnimationDrawable) imgCoin.getDrawable();
				
				for(int i = 0; i < coinFlipAnim.getNumberOfFrames(); i++ ){
					totalDuration += coinFlipAnim.getDuration(i);
				}
				
				Runnable animEnd = new Runnable(){

					public void run() {
						coinFlipAnim.stop();
						if (coinFlip == HEADS){
							imgCoin.setImageResource(R.drawable.coinanim_1);
							txtCoin.setText("HEADS");
							txtCoin.setVisibility(View.VISIBLE);
							txtCoinMessage.setVisibility(View.VISIBLE);
							imgCoin.setClickable(true);

						}
						else if (coinFlip == TAILS){
							imgCoin.setImageResource(R.drawable.coinanim_5);
							txtCoin.setText("TAILS");
							txtCoin.setVisibility(View.VISIBLE);
							txtCoinMessage.setVisibility(View.VISIBLE);
							imgCoin.setClickable(true);
						}
						SoundManager.playSound(SoundManager.COIN_LAND);
					}
				};
				
				coinFlipAnim.start();
				SoundManager.playSound(SoundManager.COIN_TOSS);
				animHandler.postDelayed(animEnd, totalDuration);
				
			}
			
		});
	}
	
	@Override
	public void onConfigurationChanged(Configuration newConfig){
		super.onConfigurationChanged(newConfig);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		
	}
	private boolean flipCoin(){
		Random rand = new Random();
		boolean face = rand.nextBoolean();
		return face;
	}

}
