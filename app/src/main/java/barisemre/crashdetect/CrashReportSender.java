package barisemre.crashdetect;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Looper;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;
import java.util.Locale;

/**
 * Created by barisemre on 17/06/2014.
 */
public class CrashReportSender implements Thread.UncaughtExceptionHandler {
    private static final String RECIPIENT = "YOUR_MAIL_HERE";
    private static Context context;
    public static boolean isCrashing=false;
    private String appVersion="",packageName="";
    private PackageManager packageManager;
    private StringBuilder stringBuilder;


    public CrashReportSender(Context ctx) {
        context = ctx;
    }


    public void uncaughtException(Thread t, Throwable throwable) {
        isCrashing=true;
        stringBuilder = new StringBuilder();
        getDeviceInfo(stringBuilder);
        stringBuilder.append('\n').append('\n');

        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        throwable.printStackTrace(pw);


        stringBuilder.append(sw.toString());
        pw.close();
        try {
            sw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        stringBuilder.append('\n');
        showCrashDialog(stringBuilder);

    }

    public void showCrashDialog(final StringBuilder errorContent) {
        final AlertDialog.Builder builder= new AlertDialog.Builder(context);
        new Thread(){
            @Override
            public void run() {
                Looper.prepare();
                builder.setTitle("Send Crash Report");
                builder.create();
                builder.setNegativeButton("Quit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        System.exit(0);
                    }
                });
                builder.setPositiveButton("Send-Mail", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        sendEmail(errorContent.toString());
                    }
                });
                builder.setMessage("Application Crashed ...");
                builder.show();
                Looper.loop();
            }
        }.start();
    }
    private void getDeviceInfo(StringBuilder message) {
        Date date = new Date();
        message.append(date.toString()).append("\n\n\n");

        try {
            packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            packageName = packageInfo.packageName;
            appVersion = packageInfo.versionName;
            message.append("Version: ").append(packageName).append('\n');
            message.append("Package: ").append(appVersion).append('\n');
        } catch (PackageManager.NameNotFoundException e) {

        }
        message.append("Locale: ").append(Locale.getDefault()).append('\n');
        message.append("Android Version: ").append(
                android.os.Build.VERSION.RELEASE).append('\n');
        message.append("Model: ").append(android.os.Build.MODEL).append('\n');
        message.append("Brand: ").append(android.os.Build.BRAND).append('\n');
        message.append("Device: ").append(android.os.Build.DEVICE).append('\n');
        message.append("Product: ").append(android.os.Build.PRODUCT).append('\n');

    }
    private void sendEmail(String mailContent){
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                "mailto",RECIPIENT, null));
        String subject = packageName +" : "+ appVersion;
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
        emailIntent.putExtra(Intent.EXTRA_TEXT,mailContent);
        context.startActivity(Intent.createChooser(emailIntent, "Send email..."));
        System.exit(0);
    }
}
