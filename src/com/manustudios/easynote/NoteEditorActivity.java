package com.manustudios.easynote;

import com.manustudios.supernotes.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;

import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

public class NoteEditorActivity extends Activity {

	private NoteItem note;
	private EditText titleText;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_note_editor);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		Intent intent = this.getIntent();
		note = new NoteItem();
		note.setTitle(intent.getStringExtra("title"));
		note.setKey(intent.getStringExtra("key"));
		note.setText(intent.getStringExtra("text"));
		note.setSelfTitle(intent.getBooleanExtra("selfTitle", false));
	
		//View  abView = (View) getActionBar().getCustomView();
		
		
		
		EditText et = (EditText) findViewById(R.id.noteText);
		et.setText(note.getText());
		et.setSelection(note.getText().length());
		
		
		
		et.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				
				//Log.i("Debug", content.getText().toString());
				invalidateOptionsMenu();
				EditText content = (EditText) findViewById(R.id.noteText);
				content.setFocusableInTouchMode(true);
				content.requestFocus();
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				
			}
		});
		
		
		showKeyBoard(et);
		//getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setDisplayShowTitleEnabled(false);
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {	//INFLATE THE ACTION BAR
		// Inflate the menu; this adds items to the action bar if it is present.
		
		getMenuInflater().inflate(R.menu.edit, menu);
		titleText = (EditText) menu.findItem(R.id.TitleContainer).getActionView().findViewById(R.id.titleNoteEditText);
		titleText.setText(note.getTitle());
		titleText.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				note.setTitle(titleText.getText().toString());
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				
			}
		});
		
		
		titleText.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				titleText.setFocusable(true);
				titleText.setFocusableInTouchMode(true);
				titleText.requestFocus();
				titleText.requestFocusFromTouch();
				note.setSelfTitle(true);
				Log.i("Menu Debug", "clicked");
			}
		});
		return true;
	}
	

	
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		
		EditText content = (EditText) findViewById(R.id.noteText);
		if(content.getLineCount()>1){
			note.setSelfTitle(true);
		}
		if(!note.isSelfTitle()){
			titleText = (EditText) menu.findItem(R.id.TitleContainer).getActionView().findViewById(R.id.titleNoteEditText);
			titleText.setText(content.getText().toString());;
			
		}
		
		return super.onPrepareOptionsMenu(menu);
	}

	
	
	

	private void showKeyBoard(View view){
		
		 if (view.requestFocus()) {
		        InputMethodManager imm = (InputMethodManager)
		                getSystemService(Context.INPUT_METHOD_SERVICE);
		        imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
		       // Toast.makeText(this, "message", Toast.LENGTH_SHORT).show();
		    }
	}
	
	
	
	private void saveAndFinish(){
		EditText content = (EditText) findViewById(R.id.noteText);
		String noteText = content.getText().toString();
		if(!noteText.equals("")){
			Intent intent = new Intent();
			intent.putExtra("key", note.getKey());
			intent.putExtra("title", note.getTitle());
			intent.putExtra("text", noteText);
			intent.putExtra("selfTitle", note.isSelfTitle());
			setResult(RESULT_OK, intent);
		}
		
		
		Toast.makeText(this, getResources().getString(R.string.saved), Toast.LENGTH_SHORT).show();
		finish();
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		if (item.getItemId()== android.R.id.home || item.getItemId()== R.id.abNoteTitle) {
			saveAndFinish();
		}
		return false;
	}
	
	@Override
	public void onBackPressed() {
		saveAndFinish();
	}
	
	
}
