CrashDetect
===========

A simple library that collects crashes of your application and allows you to send crash reports to your mail address

--adding as AAR dependency

1)Just change the repository location in build.gradle file then from command line run, gradlew uploadArchives.

2) Add this dependency to your build.gradle file in your application which you want to add CrashReport 
compile 'com.bee.crashdetect:library:+'

Simply replace 

public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}

to 


public class MainActivity extends CrashReportActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
