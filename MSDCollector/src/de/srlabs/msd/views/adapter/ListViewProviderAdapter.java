package de.srlabs.msd.views.adapter;

import java.util.List;
import java.util.Vector;

import de.srlabs.msd.R;
import de.srlabs.msd.analysis.SMS;
import de.srlabs.msd.util.ProviderScore;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class ListViewProviderAdapter extends ArrayAdapter<SMS>
{
	private Context context;
	private Vector<SMS> values;

	public ListViewProviderAdapter(Context context, Vector<SMS> values) 
	{
		super(context, R.layout.custom_row_layout_provider);
		
		this.context = context;
		this.values = values;
	}

	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) 
	{
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View rowView = inflater.inflate(R.layout.custom_row_layout_provider, parent, false);
			
		// Set provider name
		((TextView) rowView.findViewById(R.id.txtProviderName)).setText("Vodafone");
		
		Toast.makeText(context, "Reihe hinzugefuegt...!", 2).show();
		
		return rowView;
	}
	
	@Override
	public int getCount() 
	{
		return values.size();
	}
}
