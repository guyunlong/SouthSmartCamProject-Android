package stcam.stcamproject.Util;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.support.v4.content.LocalBroadcastManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

import stcam.stcamproject.Activity.MainViewPagerActivity;
import stcam.stcamproject.Application.STApplication;
import stcam.stcamproject.R;

/**
 * Created by gyl1 on 12/22/16.
 */

public class SouthUtil
{

  public static String ButtonIsAlignRightValue = "ButtonIsAlignRightValue";//默认按钮靠右
  public static String APP = "SouthUtil";

  public static void showDialog(Context context, String msg)
  {
    AlertDialog.Builder builder = new AlertDialog.Builder(context);
    builder.setMessage(msg)
      .setCancelable(false)
      .setPositiveButton(context.getString(R.string.OK), new DialogInterface.OnClickListener()
      {
        public void onClick(DialogInterface dialog, int id)
        {
        }
      });
    AlertDialog alert = builder.create();
    alert.show();
  }

  public static void setButtonIsAlignRightValue(Context context, boolean type)
  {
    SharedPreferences agPreferences = context.getSharedPreferences(SouthUtil.APP, Activity.MODE_PRIVATE);
    SharedPreferences.Editor editor = agPreferences.edit();
    editor.putBoolean(SouthUtil.ButtonIsAlignRightValue, type);
    editor.commit();
  }

  public static boolean getButtonIsAlignRightValue(Context context)
  {
    SharedPreferences preferences = context.getSharedPreferences(SouthUtil.APP, Activity.MODE_PRIVATE);
    boolean ring_tpe = preferences.getBoolean(SouthUtil.ButtonIsAlignRightValue, true);
    return ring_tpe;

  }

  public static void showToast(Context context, String msg)
  {
    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
  }

  public static int convertDpToPixel(float dp, Context context)
  {
    Resources resources;
    resources = context.getResources();
    DisplayMetrics metrics = resources.getDisplayMetrics();
    float px = dp * ((float) metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    return (int) px;
  }

  /**
   * This method converts device specific pixels to density independent pixels.
   *
   * @param px      A value in px (pixels) unit. Which we need to convert into db
   * @param context Context to get resources and device specific display metrics
   * @return A float value to represent dp equivalent to px value
   */
  public static float convertPixelsToDp(float px, Context context)
  {
    Resources resources = context.getResources();
    DisplayMetrics metrics = resources.getDisplayMetrics();
    float dp = px / ((float) metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    return dp;
  }

  public static String getTimeSSMM(int total)
  {

    return String.format("%02d:%02d", total / 60, total % 60);
  }

  public static String getTimeYyyymmddhhmmss()
  {

    SimpleDateFormat formatter;
    formatter = new SimpleDateFormat("yyyyMMddHHmmss");
    String ctime = formatter.format(new Date());
    return ctime;
  }

  /**
   * 返回当前程序版本名
   */
  public static String getAppVersionName(Context context)
  {
    String versionName = "";
    try
    {
      // ---get the package info---
      PackageManager pm = context.getPackageManager();
      PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
      versionName = pi.versionName;
      // versioncode = pi.versionCode;
      if (versionName == null || versionName.length() <= 0)
      {
        return "";
      }
    }
    catch (Exception e)
    {
      //  Log.e("VersionInfo", "Exception", e);
    }
    return versionName;
  }

  public static int getRandom()
  {
    int max = 100000;
    int min = 1;
    Random random = new Random();

    int s = random.nextInt(max) % (max - min + 1) + min;
    Log.e(tag, "random is " + s);
    return s;
  }

  public static int dpToPx(int dp)
  {
    DisplayMetrics displayMetrics = STApplication.getInstance().getResources().getDisplayMetrics();
    return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
  }

  public static int getScreenWidth(Context context)
  {
    WindowManager wm = (WindowManager) context
      .getSystemService(Context.WINDOW_SERVICE);
    DisplayMetrics outMetrics = new DisplayMetrics();
    wm.getDefaultDisplay().getMetrics(outMetrics);
    return outMetrics.widthPixels;
  }

  public static boolean isEmpty(String s)
  {
    if (null == s)
    {
      return true;
    }
    if (s.length() == 0)
    {
      return true;
    }
    if (s.trim().length() == 0)
    {
      return true;
    }
    return false;
  }

  public static void broadcastLogoutInfo()
  {
    String message = "USER_LOGOUT";
    //String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
    Intent msgIntent = new Intent(MainViewPagerActivity.MESSAGE_RECEIVED_ACTION);
    msgIntent.putExtra(MainViewPagerActivity.KEY_MESSAGE, message);
//			if (!SouthUtil.isEmpty(message)) {
//				try {
//					JSONObject extraJson = new JSONObject(extras);
//					if (extraJson.length() > 0) {
//						msgIntent.putExtra(MainViewPagerActivity.KEY_EXTRAS, extras);
//					}
//				} catch (JSONException e) {
//
//				}
//
//			}
    LocalBroadcastManager.getInstance(STApplication.getInstance()).sendBroadcast(msgIntent);
  }


  public static boolean isZh(Context context) {
    Locale locale = context.getResources().getConfiguration().locale;
    String language = locale.getLanguage();
    if (language.endsWith("zh") || language.endsWith("TW"))
      return true;
    else
      return false;
  }


  static final String tag = "SouthUtil";

}
