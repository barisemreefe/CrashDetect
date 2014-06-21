package barisemre.crashdetect;

import android.os.Bundle;
import android.widget.TextView;

/**
 * Created by barisemre on 21/06/2014.
 */
public class MainActivity extends CrashReportActivity {
    TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tv.setText("s");
    }
}
