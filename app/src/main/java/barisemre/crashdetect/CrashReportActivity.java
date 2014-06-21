package barisemre.crashdetect;

import android.app.Activity;
import android.os.Bundle;
import android.os.StrictMode;

/**
 * Created by barisemre on 17/06/2014.
 */
public abstract class CrashReportActivity extends Activity {
    private CrashReportSender crashReportSender;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

            crashReportSender = new CrashReportSender(this);
            Thread.setDefaultUncaughtExceptionHandler(crashReportSender);
            StrictMode.setThreadPolicy(buildPolicy());

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(!CrashReportSender.isCrashing)
            crashReportSender=null;
    }
    private StrictMode.ThreadPolicy buildPolicy() {
        return(new StrictMode.ThreadPolicy.Builder().detectAll().penaltyLog().build());
    }
}
