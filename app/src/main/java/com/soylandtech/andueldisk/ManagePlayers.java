package com.soylandtech.andueldisk;

import com.soylandtech.andueldisk.database.PlayerDBAdapter;

import android.app.ListActivity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnClickListener;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class ManagePlayers extends ListActivity {
	private PlayerDBAdapter playerDB;
	private static final int PLAYER_CREATE = 0;
	private static final int PLAYER_EDIT = 1;
	private static final int PLAYER_DELETE_ID = (Menu.FIRST + 1);
	private Cursor cursor;
	private int curSortOrd = PlayerDBAdapter.SORT_ASCENDING;
	private String curSortKey = PlayerDBAdapter.KEY_NAME;
	private TextView txtHeadWins;
	private TextView txtHeadName;
	private TextView txtHeadLosses;
	private TextView txtNoPlayers;
	private FrameLayout framePlayerList; 
	private Drawable imgArrowUp;
	private Drawable imgArrowDown;
	
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		playerDB = new PlayerDBAdapter(this);
		playerDB.open();
		setContentView(R.layout.players_list);
		registerForContextMenu(getListView());
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		txtHeadName = (TextView) findViewById(R.id.txtHeadName);
		txtHeadWins = (TextView) findViewById(R.id.txtHeadWins);
		txtHeadLosses = (TextView) findViewById(R.id.txtHeadLosses);
		framePlayerList = (FrameLayout) findViewById(R.id.layPlayerList);
		txtNoPlayers = (TextView) findViewById(R.id.txtNoPlayers);
		imgArrowUp = (Drawable) getResources().getDrawable(android.R.drawable.arrow_up_float);
	    imgArrowDown = (Drawable) getResources().getDrawable(android.R.drawable.arrow_down_float);
	    
	    imgArrowUp.setBounds(0, 0, 15, 15);
	    imgArrowDown.setBounds(0, 0, 15, 15);
	    
	    txtHeadName.setCompoundDrawables(null, null, imgArrowUp, null);
		txtHeadWins.setCompoundDrawables(null, null, null, null);
		txtHeadLosses.setCompoundDrawables(null, null, null, null);
		
		txtHeadName.setOnClickListener(new OnClickListener(){

			public void onClick(View arg0) {
				txtHeadName.setCompoundDrawables(null, null, null, null);
				txtHeadWins.setCompoundDrawables(null, null, null, null);
				txtHeadLosses.setCompoundDrawables(null, null, null, null);
				
				if (curSortOrd == PlayerDBAdapter.SORT_ASCENDING){
					curSortOrd = PlayerDBAdapter.SORT_DECENDING;
					txtHeadName.setCompoundDrawables(null, null, imgArrowDown, null);
				}
				else {
					curSortOrd = PlayerDBAdapter.SORT_ASCENDING;
					txtHeadName.setCompoundDrawables(null, null, imgArrowUp, null);
				}
				
				curSortKey = PlayerDBAdapter.KEY_NAME;
				fillData();
			}
			
		});
		
		txtHeadWins.setOnClickListener(new OnClickListener(){

			public void onClick(View arg0) {
				txtHeadName.setCompoundDrawables(null, null, null, null);
				txtHeadWins.setCompoundDrawables(null, null, null, null);
				txtHeadLosses.setCompoundDrawables(null, null, null, null);
				if (curSortOrd == PlayerDBAdapter.SORT_ASCENDING){
					curSortOrd = PlayerDBAdapter.SORT_DECENDING;
					txtHeadWins.setCompoundDrawables(null, null, imgArrowDown, null);
				}
				else {
					curSortOrd = PlayerDBAdapter.SORT_ASCENDING;
					txtHeadWins.setCompoundDrawables(null, null, imgArrowUp, null);
				}
				
				curSortKey = PlayerDBAdapter.KEY_WINS;
				fillData();
			}
			
		});
		
		txtHeadLosses.setOnClickListener(new OnClickListener(){

			public void onClick(View arg0) {
				txtHeadName.setCompoundDrawables(null, null, null, null);
				txtHeadWins.setCompoundDrawables(null, null, null, null);
				txtHeadLosses.setCompoundDrawables(null, null, null, null);
				if (curSortOrd == PlayerDBAdapter.SORT_ASCENDING){
					curSortOrd = PlayerDBAdapter.SORT_DECENDING;
					txtHeadLosses.setCompoundDrawables(null, null, imgArrowDown, null);
				}
				else {
					curSortOrd = PlayerDBAdapter.SORT_ASCENDING;
					txtHeadLosses.setCompoundDrawables(null, null, imgArrowUp, null);
				}
				
				curSortKey = PlayerDBAdapter.KEY_LOSSES;
				fillData();
			}
			
		});
		
		
		fillData();
	}
	
	@Override
	public void onConfigurationChanged(Configuration newConfig){
		super.onConfigurationChanged(newConfig);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		
	}
	
	// Create an option menu to add players
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.playersmenu, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.addplayer:
			createPlayer();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	//Create a context Menu to Delete Players
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		menu.add(0, PLAYER_DELETE_ID, 0, R.string.delete_player);
	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case PLAYER_DELETE_ID:
			AdapterContextMenuInfo info = (AdapterContextMenuInfo) item
					.getMenuInfo();
			playerDB.deletePlayer(info.id);
			fillData();
			return true;
		}
		return super.onContextItemSelected(item);
	}
	
	// Opens the second activity if an entry is clicked
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		Intent i = new Intent(this, PlayerDetails.class);
		i.putExtra(PlayerDBAdapter.KEY_ROWID, id);
		// Activity returns an result if called with startActivityForResult
		
		startActivityForResult(i, PLAYER_EDIT);
	}
	
	//method to create a new player
	private void createPlayer() {
		Intent i = new Intent(this, PlayerDetails.class);
		startActivityForResult(i, PLAYER_CREATE);
		
	}
	

	//method to fill up the list view
	private void fillData() {
		
		
		cursor = playerDB.getAllPlayers(curSortKey, curSortOrd);
		
		if ( cursor.getCount() > 0 ){
			startManagingCursor(cursor);
			
			String[] from = new String[] {PlayerDBAdapter.KEY_NAME , PlayerDBAdapter.KEY_WINS, PlayerDBAdapter.KEY_LOSSES};
			int[] to = new int[] {R.id.txtListPlayerName, R.id.txtListPlayerWins, R.id.txtListPlayerLoss};
			
			SimpleCursorAdapter players = new SimpleCursorAdapter(this, 
					R.layout.player_list_row, cursor, from, to);
			setListAdapter(players);
			txtNoPlayers.setVisibility(View.GONE);
			framePlayerList.setVisibility(View.VISIBLE);
			
			
		}
		else {
			txtNoPlayers.setVisibility(View.VISIBLE);
			framePlayerList.setVisibility(View.GONE);
		}
		
		
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (playerDB != null) {
			playerDB.close();
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data){
		fillData();
	}
}
