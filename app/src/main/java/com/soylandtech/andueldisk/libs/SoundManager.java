// Project: com.soylandtech.AnDuelDisk
// Created by Bjorn David Soyland <davidsoyland@gmail.com>
// on September 22, 2017 05:27 PM EDT
// Copyright (c) 2017
// File Name: SoundManager.java
//

package com.soylandtech.andueldisk.libs;

import java.util.HashMap;

import com.soylandtech.andueldisk.R;
import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Handler;

public class SoundManager {
	
	private static SoundManager _instance;
	private static SoundPool mSoundPool;
	private static HashMap<Integer, Integer> mSoundMap;
	
	private static AudioManager mAudioManager;
	private static Context mContext;
	private static Handler mSoundHandler = new Handler();
	
	public static final int COIN_TOSS = 1;
	public static final int COIN_LAND = 2;
	public static final int DICE_SHAKE = 3;
	public static final int DICE_ROLL = 4;
	public static final int  MONSTER_DESTROYED = 5;
	public static final int LP_COUNTER = 6;
	public static final int LP_ZERO = 7;
	public static final int DUEL_DISK_ACTIVATE = 8;
	public static final int LP_COUNTER_APPEARS = 9;
	
	
	private SoundManager(){
		
	}
	
	/**
	 * Requests the instance of the Sound Manager and creates it
	 * if it does not exist.
	 *
	 * @return Returns the single instance of the SoundManager
	 */
	static synchronized public SoundManager getInstance(){
		if (_instance == null){
			_instance = new SoundManager();
		}
		return _instance;
		
	}
	
	/**
	 * Initializes the storage for the sounds
	 * 
	 * @param theContext The Application context
	 */
	public static void initSounds(Context theContext) {
		mContext = theContext;
		mSoundPool = new SoundPool(9, AudioManager.STREAM_MUSIC, 0);
		mSoundMap = new HashMap<Integer, Integer>();
		mAudioManager = (AudioManager)mContext.getSystemService(Context.AUDIO_SERVICE);
		
	}
	
	
	/**
	 * Loads the various sound assets
	 * Currently hardcoded but could easily be changed to be flexible.
	 */
	public static void loadSounds()
	{
		mSoundMap.put(COIN_TOSS, mSoundPool.load(mContext, R.raw.cointoss, 1));
		mSoundMap.put(COIN_LAND, mSoundPool.load(mContext, R.raw.coinland, 1));
		mSoundMap.put(DICE_SHAKE, mSoundPool.load(mContext, R.raw.shakedice, 1));
		mSoundMap.put(DICE_ROLL, mSoundPool.load(mContext, R.raw.rolldice, 1));
		mSoundMap.put(MONSTER_DESTROYED, mSoundPool.load(mContext, R.raw.mondestroyed, 1));
		mSoundMap.put(LP_COUNTER, mSoundPool.load(mContext, R.raw.lpcounter, 1));
		mSoundMap.put(LP_ZERO, mSoundPool.load(mContext, R.raw.lpcounterzero, 1));
		mSoundMap.put(DUEL_DISK_ACTIVATE, mSoundPool.load(mContext, R.raw.dueldiskactivate, 1));
		mSoundMap.put(LP_COUNTER_APPEARS, mSoundPool.load(mContext, R.raw.counterappears, 1));
		
	}
	
	/**
	 * Plays a Sound
	 * 
	 * @param index - The Index of the Sound to be played
	 *
	 */
	public static void playSound(int index) 
	{ 		
		     playSound(index, 0);
	}
	
	/**
	 * Plays a Sound with a delay
	 * 
	 * @param index - The Index of the Sound to be played
	 * @param delay - The Amount of Time to Delay playing the sound 
	 *
	 */
	public static void playSound(final int index, long delay)
	{
		
		Runnable soundRunnable  = new Runnable(){

			public void run() {
				float streamVolume = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
				streamVolume = streamVolume / mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
				mSoundPool.play(mSoundMap.get(index), streamVolume, streamVolume, 1, 0, 1f); 
				
			}
			
		};
		mSoundHandler.postDelayed(soundRunnable, delay);
		
	}
	
	
	/**
	 * Stop a Sound
	 * @param index - index of the sound to be stopped
	 */
	public static void stopSound(int index)
	{
		mSoundPool.stop(mSoundMap.get(index));
	}
	
	public static void stopAll(){
		for(int i = 0; i <= mSoundMap.size(); i++ ){
			mSoundPool.stop(mSoundMap.get(i));
		}
	}
	
	public static void cleanup()
	{
		mSoundPool.release();
		mSoundPool = null;
	    mSoundMap.clear();
	    mAudioManager.unloadSoundEffects();
	    _instance = null;
	    
	}
	

}
