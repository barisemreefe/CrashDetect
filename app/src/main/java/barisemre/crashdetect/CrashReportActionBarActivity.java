package barisemre.crashdetect;

import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.ActionBarActivity;


/**
 * Created by barisemre on 17/06/2014.
 */
public abstract class CrashReportActionBarActivity extends ActionBarActivity {
    private CrashReportSender crashReportSender;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

            crashReportSender = new CrashReportSender(this);
            Thread.setDefaultUncaughtExceptionHandler(crashReportSender);
            StrictMode.setThreadPolicy(buildPolicy());

    }
    private StrictMode.ThreadPolicy buildPolicy() {
        return(new StrictMode.ThreadPolicy.Builder().detectAll().penaltyLog().build());
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(!CrashReportSender.isCrashing)
            crashReportSender=null;
    }
}
