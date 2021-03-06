package stcam.stcamproject.Activity;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.model.DevModel;
import com.model.RetModel;
import com.model.SearchDevModel;
import com.model.ShareModel;
import com.thSDK.TMsg;
import com.uuzuche.lib_zxing.activity.CaptureActivity;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.FuncN;
import rx.schedulers.Schedulers;
import stcam.stcamproject.Application.STApplication;
import stcam.stcamproject.Config.Config;
import stcam.stcamproject.Manager.AccountManager;
import stcam.stcamproject.Manager.DataManager;
import stcam.stcamproject.Manager.JPushManager;
import stcam.stcamproject.R;
import stcam.stcamproject.Util.DeviceParseUtil;
import stcam.stcamproject.Util.GsonUtil;
import stcam.stcamproject.Util.SouthUtil;
import stcam.stcamproject.View.LoadingDialog;
import stcam.stcamproject.network.ServerNetWork;

public class AddDeviceActivity extends BaseAppCompatActivity implements View.OnClickListener
{

  static final String tag = "AddDeviceActivity";

  final int REQUEST_CODE = 10001;


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
    android.support.v7.app.ActionBar actionBar = getSupportActionBar();
    if (actionBar != null)
    {

      setCustomTitle(getString(R.string.action_add_device), true);


    }

    setContentView(R.layout.activity_add_device);
    TextView btn_search = findViewById(R.id.btn_add_device_search);
    TextView btn_add_device_share = findViewById(R.id.btn_add_device_share);
    btn_search.setOnClickListener(this);
    btn_add_device_share.setOnClickListener(this);
    findViewById(R.id.btn_add_device_one_key).setOnClickListener(this);
    findViewById(R.id.btn_add_device_ap_sta).setOnClickListener(this);
    //findViewById(R.id.btn_add_device_ap).setOnClickListener(this);
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data)
  {
    int iResultType;
    Log.e(tag, "onActivityResult requestCode:" + requestCode + " resultCode:" + resultCode);
    if (requestCode == REQUEST_CODE)
    {
      //处理扫描结果（在界面上显示）
      if (null != data)
      {
        Log.e(tag, "onActivityResult data != null");
        Bundle bundle = data.getExtras();
        if (bundle == null)
        {
          Log.e(tag, "onActivityResult bundle = null");
          return;
        }

        iResultType = bundle.getInt(CodeUtils.RESULT_TYPE);
        Log.e(tag, "onActivityResult iResultType:" + iResultType);

        if (iResultType == CodeUtils.RESULT_SUCCESS)
        {
          String result = bundle.getString(CodeUtils.RESULT_STRING);
          Log.e(tag, "addDevice_share json:" + result);
          //Toast.makeText(this, "解析结果:" + result, Toast.LENGTH_LONG).show();
          addDevice_share(result);
        }
        else if (iResultType == CodeUtils.RESULT_FAILED)
        {
          Toast.makeText(AddDeviceActivity.this, getString(R.string.string_decodeQRFail), Toast.LENGTH_LONG).show();
        }
      }
      else
      {
        Log.e(tag, "onActivityResult data == null");
      }
    }
    super.onActivityResult(requestCode, resultCode, data);
  }


  @Override
  public boolean onOptionsItemSelected(MenuItem item)
  {
    switch (item.getItemId())
    {
      case android.R.id.home:
        this.finish(); // back button
        return true;
    }
    return super.onOptionsItemSelected(item);
  }


  @Override
  public void onClick(View view)
  {
    switch (view.getId())
    {
      case R.id.btn_add_device_one_key:
        if (MainDevListFragment.networkChangeReceiver.NetConnType != ConnectivityManager.TYPE_WIFI)
        {
          SouthUtil.showToast(STApplication.getInstance(), getString(R.string.string_OperationMustWifi));
          return;
        }
        Intent intent1 = new Intent(STApplication.getInstance(), AddDeviceOneStepActivity.class);
        startActivity(intent1);
        break;

      case R.id.btn_add_device_ap_sta:
        Intent intent2 = new Intent(STApplication.getInstance(), AddDeviceAP2StaActivity.class);
        startActivity(intent2);
        break;

      case R.id.btn_add_device_search:
        if (MainDevListFragment.networkChangeReceiver.NetConnType != ConnectivityManager.TYPE_WIFI)
        {
          SouthUtil.showToast(STApplication.getInstance(), getString(R.string.string_OperationMustWifi));
          return;
        }
        Intent intent4 = new Intent(STApplication.getInstance(), AddDeviceWlanActivity.class);
        startActivity(intent4);
        break;

      case R.id.btn_add_device_share:
        Intent intent = new Intent(AddDeviceActivity.this, CaptureActivity.class);
        startActivityForResult(intent, REQUEST_CODE);
        break;

      default:
        break;
    }

  }

  void back2TopActivity()
  {
    Intent intent = new Intent(this, MainViewPagerActivity.class);
    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
    startActivity(intent);

  }


  public final Handler ipc = new Handler()
  {
    @Override
    public void handleMessage(Message msg)
    {


      super.handleMessage(msg);
      switch (msg.what)
      {
        case TMsg.Msg_SearchOver:
          if (SearchMsg.equals("[]"))
          {
            SouthUtil.showDialog(AddDeviceActivity.this, AddDeviceActivity.this.getString(R.string.string_search_no_device));
            return;
          }
          Log.e(tag, SearchMsg);
          //[{"SN":"80005556","DevModal":"401H","DevName":"IPCAM_80005556","DevMAC":"00:C1:A1:62:55:56",
          // "DevIP":"192.168.0.199","SubMask":"255.255.255.0","Gateway":"192.168.0.1","DNS1":"192.168.0.1",
          // "SoftVersion":"V7.113.1759.00","DataPort":7556,"HttpPort":8556,"rtspPort":554,
          // "DDNSServer":"211.149.199.247","DDNSHost":"80005556.southtech.xyz","UID":"NULL"}]
          List<SearchDevModel> lists = DeviceParseUtil.parseSearchMsg(SearchMsg);
          if (lists.size() > 0)
          {
            addDevice(lists);
          }
          else
          {
            lod.dismiss();
          }


          break;

        default:
          break;
      }
    }
  };


  void addDevice_share(String json)//扫描添加共享
  {
    ShareModel model = GsonUtil.parseJsonWithGson(json, ShareModel.class);
    if (model != null)
    {
      //判断是是否已经添加
      for (DevModel tmpNode : MainDevListFragment.mDevices)
      {
        if (model.SN.equals(tmpNode.SN))
        {
          SouthUtil.showDialog(this, getString(R.string.error_device_added));
          return;
        }
      }

      //Log.e(tag, "model.uid:" + model.UID);
      DevModel devModel = new DevModel();
      devModel.SN = model.SN;
      devModel.usr = Config.DEFAULT_DEV_USERNAME;//默认填写admin
      devModel.pwd = model.Pwd;

      boolean ret = DataManager.getInstance().addDev(devModel);

      if (lod == null)
      {
        lod = new LoadingDialog(this);
      }
      lod.dialogShow();
      ServerNetWork.getCommandApi().app_share_add_dev(
        AccountManager.getInstance().getDefaultUsr(),
        AccountManager.getInstance().getDefaultPwd(),
        model.From,
        JPushManager.getJPushRegisterID(),
        Config.mbtype,
        Config.apptype,
        Config.pushtype,
        model.SN,
        model.IsVideo,
        model.IsHistory,
        model.IsPush,
        1,//model.IsSetup,
        model.IsControl).subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(observer_add_dev);
    }
    else
    {
      Log.e(tag, "parseJsonWithGson failed");
    }
  }

  void addDevice(List<SearchDevModel> devices)
  {
    List<Observable<RetModel>> observables = new ArrayList<>();
    for (SearchDevModel device : devices)
    {
      observables.add(ServerNetWork.getCommandApi().app_user_add_dev(AccountManager.getInstance().getDefaultUsr(),
        AccountManager.getInstance().getDefaultPwd(),
        JPushManager.getJPushRegisterID(),
        Config.mbtype,
        Config.apptype,
        Config.pushtype,
        device.getSN(),
        0));
    }
    Observable.combineLatest(observables, new FuncN<RetModel>()
    {

      @Override
      public RetModel call(Object... args)
      {
        RetModel model = new RetModel();
        model.ret = 1;
        for (Object i : args)
        {
          RetModel retModel = (RetModel) i;
          model.ret &= retModel.ret;

        }
        return model;
      }
    }).subscribeOn(Schedulers.io())
      .observeOn(AndroidSchedulers.mainThread())
      .subscribe(observer_add_dev);
  }

  Observer<RetModel> observer_add_dev = new Observer<RetModel>()
  {
    @Override
    public void onCompleted()
    {
      lod.dismiss();
      Log.e(tag, "---------------------2");
    }

    @Override
    public void onError(Throwable e)
    {
      lod.dismiss();
      Log.e(tag, "---------------------1:" + e.getLocalizedMessage());
    }

    @Override
    public void onNext(RetModel m)
    {
      lod.dismiss();
      if (ServerNetWork.RESULT_SUCCESS == m.ret)
      {
        back2TopActivity();
      }
      else if (ServerNetWork.RESULT_USER_ISBIND == m.ret)
      {
        String Str;
        if (!TextUtils.isEmpty(m.Info))
        {
          String sFormat = getString(R.string.string_user_IsBind1);
          Str = String.format(sFormat, m.Info);
        }
        else
        {
          Str = getString(R.string.string_user_IsBind);
        }
        SouthUtil.showToast(AddDeviceActivity.this, Str);
      }
      else if (ServerNetWork.RESULT_USER_LOGOUT == m.ret)
      {
        //RESULT_USER_LOGOUT 为收不到推送的情况下，访问服务器时的返回值，收到
        //返回登录界面，取消保存的AutoLogin
        // SouthUtil.showToast(AddDeviceActivity.this, getString(R.string.string_user_logout));
        //需要同时处理推送消息，内容为 "USER_LOGOUT"

        SouthUtil.broadcastLogoutInfo();
//todo
      }
      else
      {

      }

    }
  };
  LoadingDialog lod;
  String SearchMsg;
  boolean IsSearching;
}
