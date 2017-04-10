package zz.snsn.xlite.views.adapter;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.util.Vector;

import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.support.v4.content.ContextCompat;

import zz.snsn.xlite.R;
import zz.snsn.xlite.DetailChartActivity;
import zz.snsn.xlite.analysis.Event;
import zz.snsn.xlite.analysis.Event.Type;
import zz.snsn.xlite.util.MsdDialog;

/**
 * ToDO: WARNING:
 *       It's possible we need to rewrite this entire block using ArrayLists instead of Vectors.
 *       Apparently Vector is outdated and deprecated since 19 years and causes cast problems
 *       for the compiler and ClassCastException during runtime. See:
 *       http://www.w3resource.com/java-tutorial/java-arraylist-and-vector.php
 *
 *  "Earlier versions of Java have one legacy collection class called Vector
 *   which is very much similar to ArrayList. Vector implements a dynamic
 *   array. A Vector is basically the same as an ArrayList, but Vector
 *   methods are synchronized for thread safety. You'll normally want to use
 *   ArrayList instead of Vector because the synchronized methods add a
 *   performance hit you might not need. In this tutorial, we will discuss
 *   ArrayList only considering all is applicable to Vector as well."
 *
 */

public class ListViewEventAdapter extends ArrayAdapter<Event> implements Filterable {
	// Attributes
	private final Context context;
	private final Vector<Event> allEvents;
	private Vector<Event> values;
	private DetailChartActivity host;
	
	public ListViewEventAdapter (Context context, Vector<Event> values) {
	    super(context, R.layout.custom_row_layout_sms, values);
	    this.context = context;
	    this.allEvents = values;
	    this.values = values;
	    this.host = (DetailChartActivity) context;
	}
	
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// We should probably use a ViewHolder:
		// http://stackoverflow.com/questions/25381435/unconditional-layout-inflation-from-view-adapter-should-use-view-holder-patter
		//View rowView = convertView;
		//if (rowView == null) {
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			final View rowView = inflater.inflate(R.layout.custom_row_layout_sms, parent, false);
			//rowView = inflater.inflate(R.layout.custom_row_layout_sms, parent, false);
		//}

		// Set type
		if (values.get(position).getType().name().equals(Type.BINARY_SMS.name())) {
			((TextView) rowView.findViewById(R.id.txtSmsRowTypeValue)).setText(context.getResources().getString(R.string.common_binary_sms));			
		} else if (values.get(position).getType().name().equals(Type.SILENT_SMS.name())) {
			((TextView) rowView.findViewById(R.id.txtSmsRowTypeValue)).setText(context.getResources().getString(R.string.common_silent_sms));			
		} else if (values.get(position).getType().name().equals(Type.NULL_PAGING.name()))	{
			((TextView) rowView.findViewById(R.id.txtSmsRowTypeValue)).setText(context.getResources().getString(R.string.common_null_paging));					
		}
		
		// Set date/time
		TextView txtDateTime = (TextView) rowView.findViewById(R.id.txtSmsRowTimeValue);
		Timestamp stamp = new Timestamp(values.get(position).getTimestamp() + 1000L);
		txtDateTime.setText(DateFormat.getDateTimeInstance().format(stamp.getTime()));
	
		// Set position
		((TextView) rowView.findViewById(R.id.txtSmsRowPositionValue)).setText(values.get(position).getLocation());
		
		// Set cell id
		((TextView) rowView.findViewById(R.id.txtSmsRowCellIdValue)).setText(String.valueOf(values.get(position).getFullCellID()));
		
		// Set phone number
		((TextView) rowView.findViewById(R.id.txtSmsRowSmsCValue)).setText(values.get(position).getSmsc());
		
		// Set source
		((TextView) rowView.findViewById(R.id.txtSmsRowSourceValue)).setText(values.get(position).getSender());
		
		// Check upload state and set button
		final Button btnUpload = (Button) rowView.findViewById(R.id.btnUploadSms);
		
		switch (values.get(position).getUploadState()) {
			case STATE_UPLOADED:
				btnUpload.setBackgroundResource(R.drawable.ic_content_checkmark);
				btnUpload.setText("");
				btnUpload.setEnabled(false);
				btnUpload.setVisibility(View.VISIBLE);
				//rowView.setBackgroundColor(context.getResources().getColor(R.color.common_custom_row_background_disabled));
				rowView.setBackgroundColor(ContextCompat.getColor(context, R.color.common_custom_row_background_disabled));
				break;
			case STATE_AVAILABLE:
				btnUpload.setBackgroundResource(R.drawable.bt_content_contributedata_enable);
				btnUpload.setText(context.getResources().getString(R.string.common_button_upload));
				btnUpload.setEnabled(true);
				btnUpload.setVisibility(View.VISIBLE);
				btnUpload.setOnClickListener(new View.OnClickListener()
				{
					@Override
					public void onClick(View v)
					{
						MsdDialog.makeConfirmationDialog(host, host.getResources().getString(R.string.alert_upload_message),
								new OnClickListener()
						{
							@Override
							public void onClick(DialogInterface dialog, int which)
							{
								values.get(position).upload();
								btnUpload.setBackgroundResource(R.drawable.ic_content_checkmark);
								btnUpload.setText("");
								btnUpload.setEnabled(false);
								btnUpload.setVisibility(View.VISIBLE);
								//rowView.setBackgroundColor(context.getResources().getColor(R.color.common_custom_row_background_disabled));
								rowView.setBackgroundColor(ContextCompat.getColor(context, R.color.common_custom_row_background_disabled));
								host.refreshView();
							}
						}, null, false).show();
					}
				});
				break;
			case STATE_DELETED:
				btnUpload.setBackgroundResource(R.drawable.bt_content_contributedata_disable);
				btnUpload.setText(context.getResources().getString(R.string.common_button_nodata));
				btnUpload.setEnabled(false);
				btnUpload.setVisibility(View.VISIBLE);
				//rowView.setBackgroundColor(context.getResources().getColor(R.color.common_custom_row_background_disabled));
				rowView.setBackgroundColor(ContextCompat.getColor(context, R.color.common_custom_row_background_disabled));
				break;
			case STATE_INVALID:
				btnUpload.setBackgroundResource(R.drawable.bt_content_contributedata_disable);
				btnUpload.setText(context.getResources().getString(R.string.common_button_nodata));
				btnUpload.setEnabled(false);
				btnUpload.setVisibility(View.VISIBLE);
				//rowView.setBackgroundColor(context.getResources().getColor(R.color.common_custom_row_background_disabled));
				rowView.setBackgroundColor(ContextCompat.getColor(context, R.color.common_custom_row_background_disabled));
				break;
			default:
				btnUpload.setEnabled(false);
				btnUpload.setVisibility(View.GONE);
				break;
		}
		
		return rowView;
	 }
	
	@Override
	public Filter getFilter() {
		return new Filter() {
			@Override
			//@SuppressWarnings("unchecked")
			protected void publishResults(CharSequence constraint, FilterResults results) {
				// ToDo: Fix this! What is it supposed to do? (See original code ~2016-11)
				// In addition, this gives an [unchecked] cast warning.
				//if (results.count == 0) {
				//	notifyDataSetInvalidated();
					//values = (Vector<Event>) results.values;
					//notifyDataSetChanged();
				//} else {
				if (results.count != 0) {
					values = (Vector<Event>) results.values;
					notifyDataSetChanged();
				}
			}
		
			@Override
			protected FilterResults performFiltering(CharSequence smsType) {
				FilterResults results = new FilterResults();
				Vector<Event> eventList = new Vector<>();
				
				if (smsType.equals("ALL")) {
					results.values = allEvents;
					results.count = allEvents.size();
					return results;
				}
				for (Event event : allEvents) {
					if (event.getType().toString().equals(smsType))	{
						eventList.add(event);
					}
				}
				results.values = eventList;
				results.count = eventList.size();
				return results;
			}
		};
	}
	
	@Override
	public int getCount() 
	{
		return values.size();
	}
}
