package pl.sdacademy.app;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.widget.Toast;

public class BatteryStatusReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();

        Intent batteryIntent = context.registerReceiver(null, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
        int chargePlug = batteryIntent != null ? batteryIntent.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1) : 0;

        String plugType = "";
        switch (chargePlug) {
            case BatteryManager.BATTERY_PLUGGED_AC:
                plugType = context.getString(R.string.power_source_ac);
                break;
            case BatteryManager.BATTERY_PLUGGED_USB:
                plugType = context.getString(R.string.power_source_usb);
                break;
            case BatteryManager.BATTERY_PLUGGED_WIRELESS:
                plugType = context.getString(R.string.power_source_wireless);
                break;
        }

        String message = "";
        switch (action) {
            case Intent.ACTION_POWER_CONNECTED:
                message = context.getString(R.string.power_connected, plugType);
                break;
            case Intent.ACTION_POWER_DISCONNECTED:
                message = context.getString(R.string.power_disconnected);
                break;
        }

        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }
}
