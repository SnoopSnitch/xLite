package zz.snsn.xlite;

import zz.snsn.xlite.qdmon.MsdService;
import zz.snsn.xlite.util.MsdConfig;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class BootCompletedIntentReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		if ("android.intent.action.BOOT_COMPLETED".equals (intent.getAction())) {
			if (!MsdConfig.getFirstRun(context) && MsdConfig.getStartOnBoot(context))
			{
				Intent i = new Intent(context, MsdService.class);
				context.startService(i);
			}
		}		
	}
}
