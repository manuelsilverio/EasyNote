package com.manustudios.easynote;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import com.manustudios.supernotes.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class NoteItemAdapter extends ArrayAdapter<NoteItem>{

	public NoteItemAdapter(Context context, int textViewResourceId,
			List<NoteItem> objects) {
		super(context, textViewResourceId, objects);
		// TODO Auto-generated constructor stub
	}

	//THIS PIECE OF CODE IS VERY IMPORTANT SINCE  IT SHOWS ALL THE NOTES
	// GET VIEW Is CALLED AS MANY TIMES AS NECESARY IN ORDER TO SHOW ALL THE NOTES
	public View getView(int position, View convertView, ViewGroup parent) {

		if(convertView==null){
			//GET THE VIEW
			convertView = ((LayoutInflater) getContext().getSystemService
					(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.list_item_layout, null);
		}

		//THIS IS THE NOTE IN TURN
		NoteItem note = getItem(position);

		//DECLARE AND CAST THE DIFERENT PARTS OF THE LAYOUT

		TextView noteTitle = (TextView) convertView.findViewById(R.id.textTitle);
		TextView noteDate = (TextView) convertView.findViewById(R.id.textDate);


		//ASSIGN VALUES
		noteTitle.setText(note.getTitle());
		noteDate.setText(convertDate(note.getDate()));

		//RETURN THE VIEW
		return convertView;
	}
	
	@SuppressLint("SimpleDateFormat")
	private String convertDate(String date){
		
		int year = Integer.parseInt(date.substring(0, 4));
		int month = Integer.parseInt(date.substring(5, 7));
		int day = Integer.parseInt(date.substring(8, 10));
		
		Locale locale = new Locale("en_US");
		Locale.setDefault(locale);
		
		String pattern = "yyyy-MM-dd HH:mm:ss Z";
		SimpleDateFormat formatter = new SimpleDateFormat(pattern);
		String today =formatter.format(new Date());
		
		int thisyear = Integer.parseInt(today.substring(0, 4));
		int thismonth = Integer.parseInt(today.substring(5, 7));
		int thisday = Integer.parseInt(today.substring(8, 10));
		
		if(year== thisyear){
			if(month==thismonth){
				if(day==thisday){
					date = "today";
				}
				else if(day+1==thisday){
					date = "yesterday";
				}
				else{
					date = day +" "+ convertMonth(month);
				}
				
			}
			else{
				date = day +" "+ convertMonth(month);
			}
			
		}else{
			date = day +" "+convertMonth(month)+ " " + year;
		}
		
		return date;
	}
	
	private String convertMonth(int month){
		
		String newMonth="";
		switch(month){
			
			case 1: newMonth = getContext().getString(R.string.january); break;
			case 2: newMonth = getContext().getString(R.string.february); break;
			case 3: newMonth = getContext().getString(R.string.march); break;
			case 4: newMonth = getContext().getString(R.string.april); break;
			case 5: newMonth = getContext().getString(R.string.may); break;
			case 6: newMonth = getContext().getString(R.string.june); break;
			case 7: newMonth = getContext().getString(R.string.july); break;
			case 8: newMonth = getContext().getString(R.string.august); break;
			case 9: newMonth = getContext().getString(R.string.september); break;
			case 10: newMonth = getContext().getString(R.string.october); break;
			case 11: newMonth = getContext().getString(R.string.november); break;
			case 12: newMonth = getContext().getString(R.string.december); break;
			default: newMonth = "?"; break;
		}
		
		return newMonth;
		
	}
		
}
