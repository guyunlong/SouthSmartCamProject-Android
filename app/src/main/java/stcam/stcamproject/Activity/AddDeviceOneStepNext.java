package stcam.stcamproject.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.github.ybq.android.spinkit.SpinKitView;
import com.thSDK.TMsg;
import com.thSDK.lib;

import stcam.stcamproject.Application.STApplication;
import stcam.stcamproject.Config.Config;
import stcam.stcamproject.R;

public class AddDeviceOneStepNext extends BaseAppCompatActivity implements View.OnClickListener
{
  Button button_cancel;
  final static String tag = "AddDeviceOneStepNext";
  String SSID;
  String Password;
  SpinKitView spin_kit;
  boolean isConfig;

  @Override
  public boolean onCreateOptionsMenu(Menu menu)
  {
    getMenuInflater().inflate(R.menu.blank_menu, menu);
    return true;
  }

  @Override
  protected void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_add_device_one_step_next);

    android.support.v7.app.ActionBar actionBar = getSupportActionBar();
    if (actionBar != null)
    {

      setCustomTitle(getString(R.string.SmartConfig_connect), true);


    }
    Bundle bundle = this.getIntent().getExtras();
    if (bundle != null)
    {
      SSID = bundle.getString("ssid");
      Password = bundle.getString("ssid_pwd");
    }

    button_cancel = findViewById(R.id.button_cancel);
    button_cancel.setOnClickListener(this);
    spin_kit = findViewById(R.id.spin_kit);
    smartConfig();
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item)
  {
    switch (item.getItemId())
    {
      case android.R.id.home:
        isConfig = false;
        this.finish(); // back button

        return true;
    }
    return super.onOptionsItemSelected(item);
  }

  @Override
  public boolean onKeyDown(int keyCode, KeyEvent event)
  {
    if (keyCode == KeyEvent.KEYCODE_BACK)
    {
      Log.e(tag, "---------------------onKeyDown");
      isConfig = false;
      this.finish(); // back button
      return true;
    }
    return super.onKeyDown(keyCode, event);
  }

  public void SmartConfigStop()
  {
    isConfig = false;
    spin_kit.setVisibility(View.INVISIBLE);
    lib.jsmtStop();

  }

  void smartConfig()
  {
    isConfig = true;
    lib.jsmtInit();
    lib.jsmtStart(SSID, Password, "", "", 0);
    new Thread()
    {
      public void run()
      {
        long dt = System.currentTimeMillis();

        while (true)
        {
          try
          {
            sleep(500);
          }
          catch (InterruptedException e)
          {
          }
          if (!isConfig)
          {
            handler.sendEmptyMessage(TMsg.Msg_SmartConfigClose);
          }


          if (System.currentTimeMillis() - dt > Config.TIMEOUT_SMARTLINKSEARCH)
          {
            handler.sendEmptyMessage(TMsg.Msg_SmartConfigOver);
            break;
          }
        }
      }
    }.start();

  }

  Handler handler = new Handler()
  {
    public void handleMessage(Message msg)
    {
      switch (msg.what)
      {


        case TMsg.Msg_SmartConfigClose:
          //AddDeviceOneStepNext.this.mydialog.dismiss();
          AddDeviceOneStepNext.this.SmartConfigStop();
          break;

        case TMsg.Msg_SmartConfigOver:
          AddDeviceOneStepNext.this.SmartConfigStop();
          //actSmartConfig.this.mydialog.dismiss();
          Toast.makeText(AddDeviceOneStepNext.this, R.string.SmartConfigOver, Toast.LENGTH_SHORT).show();
          Intent intent4 = new Intent(STApplication.getInstance(), AddDeviceWlanActivity.class);
          startActivity(intent4);
          break;
      }
      super.handleMessage(msg);
    }
  };

  @Override
  public void onClick(View view)
  {
    isConfig = false;
    this.finish();
  }
}
