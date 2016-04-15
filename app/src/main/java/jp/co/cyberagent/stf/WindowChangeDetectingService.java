package jp.co.cyberagent.stf;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityEvent;
import android.widget.Toast;

@TargetApi(Build.VERSION_CODES.DONUT)
public class WindowChangeDetectingService extends AccessibilityService {
	public static final String TAG = "STFAccService";

	@TargetApi(Build.VERSION_CODES.DONUT)
	@Override
	protected void onServiceConnected() {
		super.onServiceConnected();

		//Configure these here for compatibility with API 13 and below.
		AccessibilityServiceInfo config = new AccessibilityServiceInfo();
		config.eventTypes = AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED;
		config.feedbackType = AccessibilityServiceInfo.FEEDBACK_GENERIC;

		if (Build.VERSION.SDK_INT >= 16)
			//Just in case this helps
			config.flags = AccessibilityServiceInfo.FLAG_INCLUDE_NOT_IMPORTANT_VIEWS;

		setServiceInfo(config);
	}

	@TargetApi(Build.VERSION_CODES.DONUT)
	@Override
	public void onAccessibilityEvent(AccessibilityEvent event) {
		if (event.getEventType() == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) {

			CharSequence packageName = event.getPackageName();
			CharSequence className = event.getClassName();
			if (packageName == null || className == null)
				return;

			ComponentName componentName = new ComponentName(
					packageName.toString(),
					className.toString()
			);

			ActivityInfo activityInfo = tryGetActivity(componentName);
			boolean isActivity = activityInfo != null;
			if (isActivity) {
				Log.i(TAG, componentName.flattenToShortString());

				if (Math.random() < 0.2) {
					Toast.makeText(this, "this is my Toast message!!! =)", Toast.LENGTH_LONG).show();

					// https://stackoverflow.com/questions/5810084/android-alertdialog-single-button
					// https://stackoverflow.com/questions/7918571/how-to-display-a-dialog-from-a-service#answer-19269931
					AlertDialog.Builder builder = new AlertDialog.Builder(this);
					builder.setMessage("Look at this dialog!")
							.setCancelable(false)
							.setPositiveButton("OK", new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog, int id) {
									Intent startMain = new Intent(Intent.ACTION_MAIN);
									startMain.addCategory(Intent.CATEGORY_HOME);
									startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
									startActivity(startMain);
								}
							});
					AlertDialog alert = builder.create();
					alert.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
					alert.show();
				}
			}
		}
	}

	private ActivityInfo tryGetActivity(ComponentName componentName) {
		try {
			return getPackageManager().getActivityInfo(componentName, 0);
		} catch (PackageManager.NameNotFoundException e) {
			return null;
		}
	}

	@Override
	public void onInterrupt() {
	}
}