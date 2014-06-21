package barisemre.crashdetect;


import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;

/**
 * Created by barisemre on 17/06/2014.
 */
public abstract class CrashReportFragment extends Fragment {
    private CrashReportSender crashReportSender;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

            crashReportSender = new CrashReportSender(getActivity());
            Thread.setDefaultUncaughtExceptionHandler(crashReportSender);
            StrictMode.setThreadPolicy(buildPolicy());
    }
    private StrictMode.ThreadPolicy buildPolicy() {
        return(new StrictMode.ThreadPolicy.Builder().detectAll().penaltyLog().build());
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        if(!CrashReportSender.isCrashing)
            crashReportSender=null;
    }
}
