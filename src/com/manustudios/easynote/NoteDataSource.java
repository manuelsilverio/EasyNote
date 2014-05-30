package com.manustudios.easynote;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

public class NoteDataSource {

	private static final String PREFTITLE = "title";
	private static final String PREFKEY = "notes";
	private static final String PREFDATE = "dates";
	
	private SharedPreferences noteTitlePrefs;
	private SharedPreferences notePrefs;
	private SharedPreferences noteDatePrefs;
	
	//EXPLICIT SELFINSTANCE
	public NoteDataSource(Context context){
		noteTitlePrefs = context.getSharedPreferences(PREFTITLE, Context.MODE_PRIVATE);
		notePrefs = context.getSharedPreferences(PREFKEY, Context.MODE_PRIVATE);
		noteDatePrefs = context.getSharedPreferences(PREFDATE, Context.MODE_PRIVATE);
	}
	
	public List<NoteItem> findAll(){
		Map<String, ?> titleMap = noteTitlePrefs.getAll();
		Map<String, ?> notesMap = notePrefs.getAll();		//GET ALL NOTES AS A MAP OBJECT
		Map<String, ?> datesMap = noteDatePrefs.getAll(); 	//GET ALL DATES AS MAP OBJECT
		
		SortedSet<String> keys = new TreeSet<String>(notesMap.keySet()); //ORGANIZE THE KEYS		
		
		List<NoteItem> noteList = new ArrayList<NoteItem>();
		
		for (String key : keys) {
			NoteItem note = new NoteItem();
			note.setKey(key);
			note.setTitle((String) titleMap.get(key));
			note.setText((String) notesMap.get(key));
			note.setDate((String) datesMap.get(key));
			noteList.add(note);
		}
		
		
		
		return orderNotes(noteList);
	}
	
	//UPDATE NOTE
	public boolean update(NoteItem note){
		
		SharedPreferences.Editor titleEditor = noteTitlePrefs.edit();
		titleEditor.putString(note.getKey(), note.getTitle());
		titleEditor.commit();
		
		SharedPreferences.Editor editor = notePrefs.edit();
		editor.putString(note.getKey(), note.getText());
		editor.commit();
		
		SharedPreferences.Editor dateEditor = noteDatePrefs.edit();
		dateEditor.putString(note.getKey(), getDate());
		dateEditor.commit();
		
		return true;
	}
	
	//DELETE NOTE
	public boolean remove(NoteItem note){
		
		if(notePrefs.contains(note.getKey())){
			SharedPreferences.Editor titleEditor = noteTitlePrefs.edit();
			titleEditor.remove(note.getKey());
			titleEditor.commit();
			
			SharedPreferences.Editor editor = notePrefs.edit();
			editor.remove(note.getKey());
			editor.commit();
		}
		return true;
	}
	
	//GET CURRENT DATE
	@SuppressLint("SimpleDateFormat")
	public String getDate(){
		Locale locale = new Locale("en_US");
		Locale.setDefault(locale);
		
		String pattern = "yyyy-MM-dd HH:mm:ss Z";
		SimpleDateFormat formatter = new SimpleDateFormat(pattern);
		String date =formatter.format(new Date());
		
		
		return date;
	}
	
	public List<NoteItem> orderNotes(List<NoteItem> notes){
	
		
		Collections.sort(notes, new Comparator<NoteItem>(){
		      public int compare(NoteItem obj1, NoteItem obj2)
		      {

		            return obj1.getDate().compareToIgnoreCase(obj2.getDate());
		      }
		});
		
		Collections.reverse(notes);
		
		return notes;
	}
	
	
}
