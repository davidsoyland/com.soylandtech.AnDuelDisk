// Project: com.soylandtech.AnDuelDisk
// Copyright (c) 2017 Bjorn David Soyland <davidsoyland@gmail.com>
// This project is licensed under the terms of the
// GNU GENERAL PUBLIC LICENSE Version 3.0
// File Name: MonsterCardView.java
// Created: September 26, 2017 01:00 AM EDT


package com.soylandtech.andueldisk.libs;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View;
import android.widget.ImageView;

public class MonsterCardView extends ImageView {

	/**
	 * @param context
	 */
	public MonsterCardView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param context
	 * @param attrs
	 */
	public MonsterCardView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param context
	 * @param attrs
	 * @param defStyle
	 */
	public MonsterCardView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected ContextMenuInfo getContextMenuInfo() {  
        return new MonsterCardMenuInfo(this);  
    }  
	
	public static class MonsterCardMenuInfo implements ContextMenu.ContextMenuInfo {  
        public ImageView targetView;
		public MonsterCardMenuInfo(View targetView){
        	this.targetView = (ImageView) targetView ;
        }
	}    

}
