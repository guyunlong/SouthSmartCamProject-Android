package stcam.stcamproject.Activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.thSDK.lib;

import stcam.stcamproject.Application.STApplication;
import stcam.stcamproject.R;

import static android.content.Context.CONNECTIVITY_SERVICE;
import static android.net.ConnectivityManager.TYPE_MOBILE;
import static android.net.ConnectivityManager.TYPE_WIFI;

class NetworkChangeReceiver extends BroadcastReceiver
{
  private static String tag = "NetworkChangeReceiver";
  OnNetWorkBreakListener mNetWorkBreakListener;

  public void setNetWorkBreakListener(OnNetWorkBreakListener netWorkBreakListener)
  {
    mNetWorkBreakListener = netWorkBreakListener;
  }

  boolean switchingNetwork = false;

  public int NetConnType = -1;

  Handler handler_reset = new Handler();
  Runnable runnable_reset = new Runnable()
  {
    @Override
    public void run()
    {
      //

      switchingNetwork = false;


    }
  };

  @Override
  public void onReceive(Context context, Intent intent)
  {
    ConnectivityManager connectionManager = (ConnectivityManager) context.getSystemService(CONNECTIVITY_SERVICE);
    NetworkInfo networkInfo = connectionManager.getActiveNetworkInfo();
    if (networkInfo != null && networkInfo.isAvailable())
    {
      if (STApplication.getInstance().getIsRunInBackground())
      {
        return;
      }
      if (switchingNetwork)
      {
        return;
      }
      switchingNetwork = true;
      handler_reset.postDelayed(runnable_reset, 1500);
      switch (networkInfo.getType())
      {
        case TYPE_MOBILE:
          this.NetConnType = TYPE_MOBILE;
          if (mNetWorkBreakListener != null)
          {
            mNetWorkBreakListener.OnNetWorkBreakListener();
          }
          lib.P2PFree();
          lib.P2PInit();
          if (mNetWorkBreakListener != null)
          {
            mNetWorkBreakListener.OnNetWorkChangeListener(TYPE_MOBILE);
          }
          Toast.makeText(context, context.getString(R.string.string_net_4G), Toast.LENGTH_SHORT).show();
          break;

        case TYPE_WIFI:
          this.NetConnType = TYPE_WIFI;
          if (mNetWorkBreakListener != null)
          {
            mNetWorkBreakListener.OnNetWorkBreakListener();
          }
          lib.P2PFree();
          lib.P2PInit();
          if (mNetWorkBreakListener != null)
          {
            mNetWorkBreakListener.OnNetWorkChangeListener(TYPE_WIFI);
          }
          Toast.makeText(context, context.getString(R.string.string_net_Wifi), Toast.LENGTH_SHORT).show();
          break;

        default:
          this.NetConnType = -1;
          break;
      }
    }
    else
    {
      this.NetConnType = -1;
      //断开所有连接
      Toast.makeText(context, context.getString(R.string.string_net_None), Toast.LENGTH_SHORT).show();
      if (mNetWorkBreakListener != null)
      {
        mNetWorkBreakListener.OnNetWorkBreakListener();
      }
    }
  }

  public interface OnNetWorkBreakListener
  {
    //监听网络断开
    void OnNetWorkBreakListener();

    void OnNetWorkChangeListener(int type);//0-手机网络，1-wlan
  }
}

