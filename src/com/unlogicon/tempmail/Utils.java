package com.unlogicon.tempmail;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.ViewConfiguration;
import android.webkit.WebView;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

/**
 * Created by nik on 12.08.14.
 */
public class Utils {

    public static final String computeMD5(final String s) {
        final String MD5 = "MD5";
        try {
            MessageDigest digest = java.security.MessageDigest
                    .getInstance(MD5);
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            StringBuilder hexString = new StringBuilder();
            for (byte aMessageDigest : messageDigest) {
                String h = Integer.toHexString(0xFF & aMessageDigest);
                while (h.length() < 2)
                    h = "0" + h;
                hexString.append(h);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static void addMenuButton(Activity activity){
        try {
            ViewConfiguration config = ViewConfiguration.get(activity);
            Field menuKeyField = ViewConfiguration.class.getDeclaredField("sHasPermanentMenuKey");
            if(menuKeyField != null) {
                menuKeyField.setAccessible(true);
                menuKeyField.setBoolean(config, false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getDateCurrentTimeZone(long timestamp) {
        try{
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(timestamp * 1000);
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
            Date currenTimeZone = (Date) calendar.getTime();
            return sdf.format(currenTimeZone);
        }catch (Exception e) {
        }
        return "";
    }

    public static void copyToClipboard(Activity activity, String text) {
        ClipboardManager clipboard = (ClipboardManager) activity.getSystemService(activity.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("text", text);
        clipboard.setPrimaryClip(clip);
        Toast.makeText(activity, activity.getString(R.string.copied), Toast.LENGTH_LONG).show();
    }

    public static String generateRandomEmail(Activity activity){
        char[] chars = "abcdefghijklmnopqrstuvwxyz1234567890".toCharArray();
        String[] array = activity.getResources().getStringArray(R.array.emailDomains);
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < random.nextInt(10) + 4; i++) {
            char c = chars[random.nextInt(chars.length)];
            sb.append(c);
        }
        return sb.toString() + "@" + array[random.nextInt(array.length)];
    }

    public static void shareEmail(Activity activity, String email) {
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, email);
        activity.startActivity(Intent.createChooser(sharingIntent, activity.getString(R.string.share_via)));
    }

    public static void showAboutDialog(Activity activity) {
        AlertDialog.Builder alert = new AlertDialog.Builder(activity);
        alert.setTitle(activity.getString(R.string.about));

        WebView wv = new WebView(activity);
        wv.loadUrl("file:///android_asset/about.html");

        alert.setView(wv);
        alert.setNegativeButton(activity.getString(R.string.close), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        alert.show();
    }


}
