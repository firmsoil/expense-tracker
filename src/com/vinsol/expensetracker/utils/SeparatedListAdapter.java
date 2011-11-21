package com.vinsol.expensetracker.utils;

import java.util.LinkedHashMap;
import java.util.Map;

import com.vinsol.expensetracker.R;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;

public class SeparatedListAdapter extends BaseAdapter {

	public final Map<String,Adapter> sections = new LinkedHashMap<String,Adapter>();
	public final ArrayAdapter<String> headers;
	public final ArrayAdapter<String> footers;
	public final static int TYPE_SECTION_HEADER = 0;
	public final static int TYPE_SECTION_FOOTER = 0;
	private Context mContext;
	
	public SeparatedListAdapter(Context context) {
		mContext = context;
		headers = new ArrayAdapter<String>(context, R.layout.mainlist_header_view);
		footers = new ArrayAdapter<String>(context, R.layout.main_list_footerview);
	}

	public void addSection(String section, Adapter adapter) {
		this.headers.add(section);
		this.footers.add(section);
		this.sections.put(section, adapter);
		
	}

	public Object getItem(int position) {
		for(Object section : this.sections.keySet()) {
			Adapter adapter = sections.get(section);
			int size = adapter.getCount() + 2;

			// check if position inside this section
			if(position == 0) return section;
			if(position < size-1) return adapter.getItem(position - 1);
			if(position < size) return section;

			// otherwise jump into next section
			position -= size;
		}
		return null;
	}

	public int getCount() {
		// total together all sections, plus one for each section header
		int total = 0;
		for(Adapter adapter : this.sections.values())
			total += adapter.getCount() + 2;
		return total;
	}

	public int getViewTypeCount() {
		// assume that headers count as one, then total all sections
		int total = 2;
		for(Adapter adapter : this.sections.values())
			total += adapter.getViewTypeCount();
		Log.v("total", total+"");
		return total;
	}

	public int getItemViewType(int position) {
		int type = 1;
		for(Object section : this.sections.keySet()) {
			Adapter adapter = sections.get(section);
			int size = adapter.getCount() + 2;

			// check if position inside this section
			if(position == 0) return TYPE_SECTION_HEADER;
			if(position < size-1) return type + adapter.getItemViewType(position - 1);
			if(position < size) return TYPE_SECTION_FOOTER;
			// otherwise jump into next section
			position -= size;
			type += adapter.getViewTypeCount();
		}
		return -1;
	}

	public boolean areAllItemsSelectable() {
		return false;
	}

	public boolean isEnabled(int position) {
		return (getItemViewType(position) != TYPE_SECTION_HEADER);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
//		int sectionnum = 0;
		for(Object section : this.sections.keySet()) {
			Adapter adapter = sections.get(section);
			int size = adapter.getCount() + 2;
			Log.v("size",""+size);
			// check if position inside this section
			LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			
//			if(position == 0) return headers.getView(sectionnum, convertView, parent);
			
			View header = inflater.inflate(R.layout.mainlist_header_view, null);
//			header.
			View row = inflater.inflate(R.layout.expense_listing_inflated_row, null);
			View footer = inflater.inflate(R.layout.main_list_footerview, null);
//			footer.setLayoutParams(params);
//			if(position == 0) return headers.getView(sectionnum, convertView, parent);
			if(position == 0) return header;
			if(position < size-1) return row;
			if(position < size) return footer;
			// otherwise jump into next section
			position -= size;
//			sectionnum++;
		}
		return null;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

}