package stcam.stcamproject.Activity;

import android.app.TimePickerDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.model.DevModel;
import com.model.LedStatusModel;
import com.model.RetModel;
import com.thSDK.lib;

import info.hoang8f.android.segmented.SegmentedGroup;
import stcam.stcamproject.R;
import stcam.stcamproject.Util.GsonUtil;
import stcam.stcamproject.View.LoadingDialog;

public class DevLedControlActivity extends BaseAppCompatActivity implements RadioGroup.OnCheckedChangeListener, SeekBar
  .OnSeekBarChangeListener, View.OnClickListener
{
  DevModel devModel;
  static final String tag = "DevLedControlActivity";

  RadioButton btn_mode_1;
  RadioButton btn_mode_2;
  RadioButton btn_mode_3;
  RadioButton btn_mode_4;

  SegmentedGroup modeGroup;
  RelativeLayout relativelayout_time_span;
  RelativeLayout layout_sensitive;
  RelativeLayout layout_time;
  RelativeLayout layout_brintness;

  TextView light_time_span;
  SeekBar seekBarLightTime;//亮灯时间
  SegmentedGroup segmented3;//光感灵敏度
  RadioButton btn_sensitive_1;
  RadioButton btn_sensitive_2;
  RadioButton btn_sensitive_3;

  //亮度
  TextView light_brintness;
  SeekBar seekBar_brintness;

  Button button_time_start;
  Button button_time_stop;


  LedStatusModel statusModel;
  LoadingDialog lod;

  ImageView imageView_light;

  private FirebaseAnalytics mFirebaseAnalytics;

  private int minLightBrintness = 10;
  private int minLightSpan = 20;
  Handler handler = new Handler();
  Runnable runnable = new Runnable()
  {
    @Override
    public void run()
    {
      // TODO Auto-generated method stub
      SetLedStatusTask task = new SetLedStatusTask();
      task.execute(0);
    }
  };

  //

  Handler handler_refresh = new Handler();
  Runnable runnable_fresh = new Runnable()
  {
    @Override
    public void run()
    {
      //
      RefreshLedStatusTask task = new RefreshLedStatusTask();
      task.execute(0);
      handler_refresh.postDelayed(runnable_fresh, 1000);


    }
  };

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
    setContentView(R.layout.activity_led_control);
    android.support.v7.app.ActionBar actionBar = getSupportActionBar();

    mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
    if (actionBar != null)
    {


      setCustomTitle(getString(R.string.action_led_control), true);

    }
    Bundle bundle = this.getIntent().getExtras();
    if (bundle != null)
    {
      devModel = (DevModel) bundle.getParcelable("devModel");
      Log.e(tag, "NetHandle:" + devModel.NetHandle + ",SN:" + devModel.SN);
    }
    statusModel = new LedStatusModel();
    initView();

    initValue();


  }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_led, menu);
//        return true;
//    }


  @Override
  protected void onResume()
  {
    super.onResume();
    handler_refresh.postDelayed(runnable_fresh, 1000);
  }

  @Override
  protected void onPause()
  {
    super.onPause();

    handler_refresh.removeCallbacks(runnable_fresh);
  }


  void initValue()
  {
    if (lod == null)
    {
      lod = new LoadingDialog(this);
    }
    lod.dialogShow();
    GetLedStatusTask task = new GetLedStatusTask();
    task.execute(0);
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item)
  {
    switch (item.getItemId())
    {
      case android.R.id.home:
//                SetLedStatusTask task1 = new SetLedStatusTask();
//                task1.execute(0);
        this.finish(); // back button
        return true;
      case R.id.action_set:
        SetLedStatusTask task = new SetLedStatusTask();
        task.execute(0);
        // lod.dialogShow();
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

//            SetLedStatusTask task1 = new SetLedStatusTask();
//            task1.execute(0);
      this.finish(); // back button

      return true;
    }
    return super.onKeyDown(keyCode, event);
  }

  public void initView()
  {

    imageView_light = findViewById(R.id.imageView_light);

    btn_mode_1 = findViewById(R.id.btn_mode_1);
    btn_mode_2 = findViewById(R.id.btn_mode_2);
    btn_mode_3 = findViewById(R.id.btn_mode_3);
    btn_mode_4 = findViewById(R.id.btn_mode_4);
    modeGroup = findViewById(R.id.segmented2);

    relativelayout_time_span = findViewById(R.id.relativelayout_time_span);
    layout_sensitive = findViewById(R.id.layout_sensitive);
    layout_time = findViewById(R.id.layout_time);
    layout_brintness = findViewById(R.id.layout_brintness);


    modeGroup.setOnCheckedChangeListener(this);


    seekBarLightTime = findViewById(R.id.seekBarLightTime);
    //seekBarLightTime.setMin(minLightSpan);
    seekBarLightTime.setOnSeekBarChangeListener(this);
    light_time_span = findViewById(R.id.light_time_span);

    segmented3 = findViewById(R.id.segmented3);
    btn_sensitive_1 = findViewById(R.id.btn_sensitive_1);
    btn_sensitive_2 = findViewById(R.id.btn_sensitive_2);
    btn_sensitive_3 = findViewById(R.id.btn_sensitive_3);
    segmented3.setOnCheckedChangeListener(this);

    light_brintness = findViewById(R.id.light_brintness);
    seekBar_brintness = findViewById(R.id.seekBar_brintness);
    seekBar_brintness.setOnSeekBarChangeListener(this);

    button_time_start = findViewById(R.id.button_time_start);
    button_time_stop = findViewById(R.id.button_time_stop);
    button_time_start.setOnClickListener(this);
    button_time_stop.setOnClickListener(this);
    modeGroup.check(R.id.btn_mode_1);


  }

  void hideAllLayout()
  {
    relativelayout_time_span.setVisibility(View.GONE);
    layout_sensitive.setVisibility(View.GONE);
    layout_time.setVisibility(View.GONE);
    layout_brintness.setVisibility(View.GONE);
  }

  @Override
  public void onCheckedChanged(RadioGroup group, int checkedId)
  {
    if (group == modeGroup)
    {
      hideAllLayout();
      switch (checkedId)
      {
        case R.id.btn_mode_1:
          statusModel.setMode(1);
          relativelayout_time_span.setVisibility(View.VISIBLE);
          layout_sensitive.setVisibility(View.VISIBLE);
          layout_brintness.setVisibility(View.VISIBLE);

          break;
        case R.id.btn_mode_2:
          statusModel.setMode(2);
          layout_brintness.setVisibility(View.VISIBLE);
          break;
        case R.id.btn_mode_3:
          statusModel.setMode(3);
          layout_time.setVisibility(View.VISIBLE);
          layout_brintness.setVisibility(View.VISIBLE);
          break;
        case R.id.btn_mode_4:
          statusModel.setMode(4);
          layout_brintness.setVisibility(View.VISIBLE);
          layout_sensitive.setVisibility(View.VISIBLE);
          break;
        default:
          break;
      }
      reloadView();
      handler.removeCallbacks(runnable);
      handler.postDelayed(runnable, 1000);
    }
    else if (group == segmented3)
    {
      //光感灵敏度
      if (statusModel.getMode() == 1)
      {
        switch (checkedId)
        {
          case R.id.btn_sensitive_1:
            statusModel.getAuto().setLux(0);

            break;
          case R.id.btn_sensitive_2:
            statusModel.getAuto().setLux(1);
            break;
          case R.id.btn_sensitive_3:
            statusModel.getAuto().setLux(2);
            break;

          default:
            break;
        }
        handler.removeCallbacks(runnable);
        handler.postDelayed(runnable, 1000);
      }
      else if (statusModel.getMode() == 4)
      {
        switch (checkedId)
        {
          case R.id.btn_sensitive_1:
            statusModel.getD2D().setLux(0);
            break;
          case R.id.btn_sensitive_2:
            statusModel.getD2D().setLux(1);
            break;
          case R.id.btn_sensitive_3:
            statusModel.getD2D().setLux(2);
            break;

          default:
            break;
        }
        handler.removeCallbacks(runnable);
        handler.postDelayed(runnable, 1000);
      }
    }
  }

  void reloadView()
  {
    if (statusModel != null)
    {

      if (statusModel.getStatus() == 1)
      {
        imageView_light.setImageResource(R.drawable.light_open);
      }
      else
      {
        imageView_light.setImageResource(R.drawable.light_close);
      }

      switch (statusModel.getMode())
      {
        case 1:
          modeGroup.check(R.id.btn_mode_1);
          seekBarLightTime.setProgress(statusModel.getAuto().getDelay() - minLightSpan);
          light_time_span.setText("" + statusModel.getAuto().getDelay());
          if (statusModel.getAuto().getLux() == 0)
          {
            segmented3.check(R.id.btn_sensitive_1);
          }
          else if (statusModel.getAuto().getLux() == 1)
          {
            segmented3.check(R.id.btn_sensitive_2);
          }
          else if (statusModel.getAuto().getLux() == 2)
          {
            segmented3.check(R.id.btn_sensitive_3);
          }
          light_brintness.setText("" + statusModel.getAuto().getBrightness());
          seekBar_brintness.setProgress(statusModel.getAuto().getBrightness() - minLightBrintness);
          break;
        case 2:
          modeGroup.check(R.id.btn_mode_2);
          seekBar_brintness.setProgress(statusModel.getManual().getBrightness() - minLightBrintness);
          light_brintness.setText("" + statusModel.getManual().getBrightness());
          break;
        case 3:
          modeGroup.check(R.id.btn_mode_3);
          button_time_start.setText(statusModel.getTimer().getStartH() + ":" + statusModel.getTimer().getStartM());
          button_time_stop.setText(statusModel.getTimer().getStopH() + ":" + statusModel.getTimer().getStopM());
          seekBar_brintness.setProgress(statusModel.getTimer().getBrightness() - minLightBrintness);
          light_brintness.setText("" + statusModel.getTimer().getBrightness());
          break;
        case 4:
          modeGroup.check(R.id.btn_mode_4);
          seekBar_brintness.setProgress(statusModel.getD2D().getBrightness() - minLightBrintness);
          light_brintness.setText("" + statusModel.getD2D().getBrightness());
          if (statusModel.getD2D().getLux() == 0)
          {
            segmented3.check(R.id.btn_sensitive_1);
          }
          else if (statusModel.getD2D().getLux() == 1)
          {
            segmented3.check(R.id.btn_sensitive_2);
          }
          else if (statusModel.getD2D().getLux() == 2)
          {
            segmented3.check(R.id.btn_sensitive_3);
          }
          break;
        default:
          break;
      }
      //if (statusModel.getMode() == 1)
    }


  }

  @Override
  public void onProgressChanged(SeekBar bar, int i, boolean b)
  {
    if (bar == seekBarLightTime)
    {
      int value = i + minLightSpan;
      statusModel.getAuto().setDelay(value);
      light_time_span.setText("" + value);
    }
    else if (bar == seekBar_brintness)
    {
      int value = i + minLightBrintness;
      light_brintness.setText("" + value);
      switch (statusModel.getMode())
      {
        case 1:
          statusModel.getAuto().setBrightness(value);
          break;
        case 2:
          statusModel.getManual().setBrightness(value);
          break;
        case 3:
          statusModel.getTimer().setBrightness(value);
          break;
        case 4:
          statusModel.getD2D().setBrightness(value);
          break;
        default:
          break;
      }
    }
    handler.removeCallbacks(runnable);
    handler.postDelayed(runnable, 1000);
  }

  @Override
  public void onStartTrackingTouch(SeekBar seekBar)
  {

  }

  @Override
  public void onStopTrackingTouch(SeekBar seekBar)
  {

  }

  @Override
  public void onClick(View view)
  {
    if (view.getId() == R.id.button_time_stop)
    {
      showTimePickerDialog(false);
    }
    else if (view.getId() == R.id.button_time_start)
    {
      showTimePickerDialog(true);
    }
  }

  public void showTimePickerDialog(final boolean start)
  {
// Calendar c = Calendar.getInstance();
    // 创建一个TimePickerDialog实例，并把它显示出来
    // 解释一哈，Activity是context的子类
    new TimePickerDialog(this, android.R.style.Theme_DeviceDefault_Light_Dialog,
      // 绑定监听器
      new TimePickerDialog.OnTimeSetListener()
      {
        @Override
        public void onTimeSet(TimePicker view,
                              int hourOfDay, int minute)
        {
//                        tv.setText("您选择了：" + hourOfDay + "时" + minute
//                                + "分");
          if (start)
          {
            statusModel.getTimer().setStartH(hourOfDay);
            statusModel.getTimer().setStartM(minute);
            button_time_start.setText(statusModel.getTimer().getStartH() + ":" + statusModel.getTimer().getStartM());

          }
          else
          {
            statusModel.getTimer().setStopH(hourOfDay);
            statusModel.getTimer().setStopM(minute);
            button_time_stop.setText(statusModel.getTimer().getStopH() + ":" + statusModel.getTimer().getStopM());
          }

          handler.removeCallbacks(runnable);
          handler.postDelayed(runnable, 1000);
        }
      }
      // 设置初始时间
      , start ? statusModel.getTimer().getStartH() : statusModel.getTimer().getStopH()
      , start ? statusModel.getTimer().getStartM() : statusModel.getTimer().getStopM()
      // true表示采用24小时制
      , true).show();
  }


  class RefreshLedStatusTask extends AsyncTask<Integer, Void, String>
  {
    // AsyncTask<Params, Progress, Result>
    //后面尖括号内分别是参数（例子里是线程休息时间），进度(publishProgress用到)，返回值类型
    @Override
    protected void onPreExecute()
    {
      //第一个执行方法
      super.onPreExecute();
    }

    @Override
    protected String doInBackground(Integer... params)
    {
      //return "1";
      //http://192.168.0.100/cfg1.cgi?User=admin&Psd=admin&MsgID=73&Chl=0&Value=0 开灯关灯
      //第二个执行方法,onPreExecute()执行完后执行
      String url = devModel.getDevURL(lib.Msg_GetLightCfg);
      Log.e(tag, "url " + url);
      String ret = lib.thNetHttpGet(devModel.NetHandle, url);
      return ret;
    }

    @Override
    protected void onPostExecute(String result)
    {
      /*
      int Status = lib.thNetGetGPIOStatus(devModel.NetHandle, 0);
      Log.e(tag, "thNetGetGPIOStatus:"+ Status);
      if (Status == 1)
      {
        imageView_light.setImageResource(R.drawable.light_open);
      }
      else
      {
        imageView_light.setImageResource(R.drawable.light_close);
      }
*/
      LedStatusModel model = GsonUtil.parseJsonWithGson(result, LedStatusModel.class);
      Log.e(tag, "RefreshLedStatusTask status model is " + result);

      if (model != null)
      {
        statusModel.setStatus(model.getStatus());
        if (model.getStatus() == 1)
        {
          imageView_light.setImageResource(R.drawable.light_open);
        }
        else
        {
          imageView_light.setImageResource(R.drawable.light_close);
        }

      }
      // get status model is {"Mode":1,"Auto":{"Delay":90,"Lux":2},"Manual":{"Brightness":0},
      // "Timer":{"Brightness":0,"StartH":0,"StartM":0,"StopH":0,"StopM":0},"D2D":{"Brightness":0,"Lux":0}}

      super.onPostExecute(result);
    }
  }


  class GetLedStatusTask extends AsyncTask<Integer, Void, String>
  {
    // AsyncTask<Params, Progress, Result>
    //后面尖括号内分别是参数（例子里是线程休息时间），进度(publishProgress用到)，返回值类型
    @Override
    protected void onPreExecute()
    {
      //第一个执行方法
      super.onPreExecute();
    }

    @Override
    protected String doInBackground(Integer... params)
    {
      //第二个执行方法,onPreExecute()执行完后执行
      String url = devModel.getDevURL(lib.Msg_GetLightCfg);
      Log.e(tag, "url " + url);
      String ret = lib.thNetHttpGet(devModel.NetHandle, url);
      return ret;
    }

    @Override
    protected void onPostExecute(String result)
    {

      LedStatusModel model = GsonUtil.parseJsonWithGson(result, LedStatusModel.class);
      Log.e(tag, "get status model is " + result);

      if (model != null)
      {
        statusModel = model;
      }
      // get status model is {"Mode":1,"Auto":{"Delay":90,"Lux":2},"Manual":{"Brightness":0},
      // "Timer":{"Brightness":0,"StartH":0,"StartM":0,"StopH":0,"StopM":0},"D2D":{"Brightness":0,"Lux":0}}
      reloadView();
      lod.dismiss();
      super.onPostExecute(result);
    }
  }

  class SetLedStatusTask extends AsyncTask<Integer, Void, String>
  {
    // AsyncTask<Params, Progress, Result>
    //后面尖括号内分别是参数（例子里是线程休息时间），进度(publishProgress用到)，返回值类型
    @Override
    protected void onPreExecute()
    {
      //第一个执行方法
      super.onPreExecute();
    }

    @Override
    protected String doInBackground(Integer... params)
    {
      //第二个执行方法,onPreExecute()执行完后执行
      String url = devModel.getDevURL(lib.Msg_SetLightCfg);
      if (statusModel.getMode() == 1)
      {
        url += "&Mode=1&Delay=" + statusModel.getAuto().getDelay() + "&Lux=" + statusModel.getAuto().getLux() + "&Brightness=" +
          statusModel.getAuto().getBrightness();
      }
      else if (statusModel.getMode() == 2)
      {
        url += "&Mode=2&Brightness=" + statusModel.getManual().getBrightness();
      }
      else if (statusModel.getMode() == 3)
      {
        url += "&Mode=3&Brightness=" + statusModel.getTimer().getBrightness() + "&StartH=" + statusModel.getTimer().getStartH() + "" +
          "&StartM=" + statusModel.getTimer().getStartM() + "&StopH=" + statusModel.getTimer().getStopH() + "&StopM=" + statusModel
          .getTimer().getStopM();
      }
      else if (statusModel.getMode() == 4)
      {
        url += "&Mode=4&Brightness=" +
          "" + statusModel.getD2D().getBrightness() + "&Lux=" + statusModel.getD2D().getLux();
      }
      Log.e(tag, "url " + url);
      String ret = lib.thNetHttpGet(devModel.NetHandle, url);
      return ret;
    }

    @Override
    protected void onPostExecute(String result)
    {

      if (result != null && result.length() > 0)
      {
        Log.e(tag, "set status model is " + result);
        RetModel retmodel = GsonUtil.parseJsonWithGson(result, RetModel.class);

        // get status model is {"Mode":1,"Auto":{"Delay":90,"Lux":2},"Manual":{"Brightness":0},
        // "Timer":{"Brightness":0,"StartH":0,"StartM":0,"StopH":0,"StopM":0},"D2D":{"Brightness":0,"Lux":0}}
        // lod.dismiss();
        Bundle params = new Bundle();
        params.putString("SetLedResult", result);


        if (retmodel != null)
        {
          params.putBoolean("SetLedResultModel", true);
          if (retmodel.ret == 1)
          {
            // SouthUtil.showDialog(DevLedControlActivity.this,"set successfully");
          }
          else
          {
            // SouthUtil.showDialog(DevLedControlActivity.this,"set failed");
          }
        }
        else
        {
          params.putBoolean("SetLedResultModel", false);
          // SouthUtil.showDialog(DevLedControlActivity.this,"set failed");
        }

        mFirebaseAnalytics.logEvent("SetLed", params);
      }
      super.onPostExecute(result);
    }
  }
}
