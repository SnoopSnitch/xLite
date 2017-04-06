package zz.snsn.xlite;

import android.os.Bundle;
import android.preference.PreferenceActivity;

import zz.snsn.xlite.util.MsdConfig;
import zz.snsn.xlite.util.Utils;

public class SettingsActivity extends PreferenceActivity
{	
	@Override
    protected void onCreate(Bundle savedInstanceState) 
	{
        super.onCreate(savedInstanceState);
        
     // Display the fragment as the main content.
        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new SettingsFragment())
                .commit();
        
        setTitle(getResources().getText(R.string.settings_actionBar_title));
    }
	
	public void refreshAppId ()
	{
		MsdConfig.setAppId(this, Utils.generateAppId());
	}
}
