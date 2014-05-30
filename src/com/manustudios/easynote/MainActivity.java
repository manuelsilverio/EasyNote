package com.manustudios.easynote;

import java.util.List;

import com.manustudios.supernotes.R;

import android.os.Bundle;
import android.app.ListActivity;
import android.content.Intent;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.ListView;
import android.widget.AdapterView.AdapterContextMenuInfo;

public class MainActivity extends ListActivity {

	private static final int EDITOR_ACTIVITY_REQUEST = 10;
	private static final int MENU_DELETE_ID = 12;
	private int currentNoteId;
	private NoteDataSource dataSource;
	private List<NoteItem> notes;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		registerForContextMenu(getListView());
		
		dataSource = new NoteDataSource(this);
		refreshScreen();
	}

	private void refreshScreen() {
		notes = dataSource.findAll();
		
		//ArrayAdapter<NoteItem> adapter = new ArrayAdapter<NoteItem>(this, R.layout.list_item_layout, notes);
		
		NoteItemAdapter adapter = new NoteItemAdapter(this, R.layout.list_item_layout, notes); 	//THE ADAPTER FOR THE NOTES
		setListAdapter(adapter);	//SETTING THE ADAPTER
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {	//INFLATE THE ACTION BAR
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {	//PRESS THHE ACTION BAR
		
		if(item.getItemId()==R.id.action_create){
			createNote();
		}
		
		return super.onOptionsItemSelected(item);
	}

	private void createNote() {	//CREATE A NEW NOTE
		NoteItem note = NoteItem.getNew();
		Intent intent = new Intent(this, NoteEditorActivity.class);
		intent.putExtra("key", note.getKey());
		intent.putExtra("title", "");
		intent.putExtra("text", note.getText());
		intent.putExtra("selfTitle", false);
		startActivityForResult(intent, EDITOR_ACTIVITY_REQUEST);
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		
		NoteItem note = notes.get(position);
		Intent intent = new Intent(this, NoteEditorActivity.class);
		intent.putExtra("key", note.getKey());
		intent.putExtra("title", note.getTitle());
		intent.putExtra("text", note.getText());
		intent.putExtra("selfTitle", true);
		startActivityForResult(intent, EDITOR_ACTIVITY_REQUEST);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {	//COMING BACK FROM ANY OTHER ACTIVITY
		if (requestCode == EDITOR_ACTIVITY_REQUEST && resultCode == RESULT_OK) {
			NoteItem note = new NoteItem();
			note.setKey(data.getStringExtra("key"));
			note.setTitle(data.getStringExtra("title"));
			note.setSelfTitle(data.getBooleanExtra("selfTitle", false));
			note.setText(data.getStringExtra("text"));	//NEVERMIND THE DATE IT IS UPDATED INSIDE dataSource Object
			dataSource.update(note);
			refreshScreen();
		}
	}
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
	
			AdapterContextMenuInfo info = (AdapterContextMenuInfo) menuInfo;
			currentNoteId = (int)info.id;
			menu.add(0, MENU_DELETE_ID, 0, "Delete");
	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		
		if (item.getItemId() == MENU_DELETE_ID) {
			NoteItem note = notes.get(currentNoteId);
			dataSource.remove(note);
			refreshScreen();
		}
		
		return super.onContextItemSelected(item);
	}
	
	
	
	
}
