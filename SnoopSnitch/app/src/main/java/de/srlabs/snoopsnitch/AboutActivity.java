package de.srlabs.snoopsnitch;

import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;

import de.srlabs.snoopsnitch.R;
import de.srlabs.snoopsnitch.BuildConfig;
import de.srlabs.snoopsnitch.util.MsdLog;

/**
 *  Display content of the About screen
 *  ToDo: [ ] Move  MsdLog.getDeviceProps() to it's own menu item (screen)
 *
 */
public class AboutActivity extends BaseActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		TextView aboutText = (TextView) findViewById (R.id.aboutText);

		String aboutContent =
				"SnoopSnitch " +
				//this.getString(R.string.app_version) +
				BuildConfig.VERSION_NAME + " (" + BuildConfig.VERSION_CODE + ")" +
                "\n\n" +
                "------------------------------------------------------------" + "\n" +
                MsdLog.getDeviceProps() + "\n\n" +
                "-------------------------------------------------------------" +
                "\n\n" +
				this.getString(R.string.about_text) +
				"\n\n" +
				this.getString(R.string.copyright_text);

		aboutText.setText(aboutContent);
	}
}
