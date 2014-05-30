package com.manustudios.easynote;

import android.annotation.SuppressLint;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class NoteItem {

	private String key;
	private String title;
	private String text;
	private String date;
	private boolean selfTitle;
	
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public boolean isSelfTitle() {
		return selfTitle;
	}
	public void setSelfTitle(boolean selfTitle) {
		this.selfTitle = selfTitle;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	@SuppressLint("SimpleDateFormat")
	public static NoteItem getNew(){
		
		Locale locale = new Locale("en_US");
		Locale.setDefault(locale);
		
		String pattern = "yyyy-MM-dd HH:mm:ss Z";
		SimpleDateFormat formatter = new SimpleDateFormat(pattern);
		String key =formatter.format(new Date());
		
		NoteItem note = new NoteItem();
		note.setKey(key);
		note.setText("");
		return note;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.getText();
	}
	
	
	
}
