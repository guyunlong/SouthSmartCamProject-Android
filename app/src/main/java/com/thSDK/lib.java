package com.thSDK;

import android.app.Activity;

public class lib extends Activity
{
  static
  {
    try
    {
      System.loadLibrary("IOTCAPIs");
      System.loadLibrary("AVAPIs");
      System.loadLibrary("thSDK");
    }
    catch (UnsatisfiedLinkError e)
    {
      System.out.println("library," + e.getMessage());
    }
  }

  public static native String testGetFfmpeg();

  //new
  public static native boolean P2PInit();

  public static native boolean P2PFree();

  public static native boolean IsConnectWLAN();

  public static native String GetLocalIP();

  public static native long thNetInit(boolean IsInsideDecode, boolean IsQueue, boolean IsAdjustTime, boolean IsAutoReConn, String SN);

  public static native boolean thNetSetDecodeStyle(long NetHandle, int DecodeStyle);

  public static native boolean thNetFree(long NetHandle);

  public static native boolean thNetConnect(long NetHandle, String jUserName, String jPassword, String jIPUID, int DataPort, int TimeOut);

  public static native boolean thNetDisConn(long NetHandle);

  public static native boolean thNetThreadDisConnFree(long NetHandle);

  public static native boolean thNetIsConnect(long NetHandle);

  public static native boolean thNetSendSensePkt(long NetHandle);

  public final static int THNET_CONNSTATUS_NO = 0;
  public final static int THNET_CONNSTATUS_CONNING = 1;
  public final static int THNET_CONNSTATUS_SUCCESS = 2;
  public final static int THNET_CONNSTATUS_FAIL = 3;

  public static native int thNetGetConnectStatus(long NetHandle);

  public static native String thNetGetConnIPUID(long NetHandle);

  public static native boolean thNetPlay(long NetHandle, int VideoChlMask, int AudioChlMask, int SubVideoChlMask);

  public static native boolean thNetStop(long NetHandle);

  public static native boolean thNetIsPlay(long NetHandle);

  public static native String thNetGetAllCfg(long NetHandle);

  public static native boolean thNetTalkOpen(long NetHandle);

  public static native boolean thNetTalkClose(long NetHandle);

  public static native boolean thNetRemoteFilePlay(long NetHandle, String jFileName);

  public static native boolean thNetRemoteFileStop(long NetHandle);

  public static native boolean thNetRemoteFilePlayControl(long NetHandle, int PlayCtrl, int Speed, int Pos);

  public static native boolean thNetAudioPlayOpen(long NetHandle);

  public static native boolean thNetAudioPlayClose(long NetHandle);

  public static native boolean thNetSetAudioIsMute(long NetHandle, boolean IsAudioMute);

  public static native boolean thNetOpenGLUpdateArea(long NetHandle, int Left, int Top, int Right, int Bottom);

  public static native boolean thNetOpenGLRender(long NetHandle);

  public static native String thNetHttpGet(long NetHandle, String url);

  public static native boolean thNetSetRecPath(long NetHandle, String jRecPath);

  public static native boolean thNetStartRec(long NetHandle, String jRecFileName);

  public static native boolean thNetIsRec(long NetHandle);

  public static native boolean thNetStopRec(long NetHandle);

  public static native boolean thNetSetJpgPath(long NetHandle, String jJpgPath);

  public static native boolean thNetSaveToJpg(long NetHandle, String jJpgFileName);

  public static native String thNetSearchDevice(int TimeOut, int IsJson);

  public static native int jsmtInit();

  public static native int jsmtStop();

  public static native int jsmtStart(String SSID, String Password, String Tlv, String Target, int AuthMode);

  //---------------------------------------------------------------------------
  //---------------------------------------------------------------------------
  //---------------------------------------------------------------------------
  public final static int Msg_None = 0;
  public final static int Msg_Login = 1;//用户登录
  public final static int Msg_PlayLive = 2;//开始播放现场
  public final static int Msg_StartPlayRecFile = 3;//播放录影文件
  public final static int Msg_StopPlayRecFile = 4;//停止播放录影文件
  public final static int Msg_GetRecFileLst = 5;//取得录影文件列表
  public final static int Msg_GetDevRecFileHead = 6;//取得设备文件文件头信息
  public final static int Msg_StartUploadFile = 7;//开始上传文件
  public final static int Msg_AbortUploadFile = 8;//取消上传文件
  public final static int Msg_StartUploadFileEx = 9;//开始上传文件tftp
  public final static int Msg_StartTalk = 10;//开始对讲
  public final static int Msg_StopTalk = 11;//停止对讲
  public final static int Msg_PlayControl = 12;//播放控制
  public final static int Msg_PTZControl = 13;//云台控制
  public final static int Msg_Alarm = 14;//警报
  public final static int Msg_ClearAlarm = 15;//关闭警报
  public final static int Msg_GetTime = 16;//取得时间
  public final static int Msg_SetTime = 17;//设置时间
  public final static int Msg_SetDevReboot = 18;//重启设备
  public final static int Msg_SetDevLoadDefault = 19;//系统回到缺省配置 Pkt.Value= 0 不恢复IP; Pkt.Value= 1 恢复IP
  public final static int Msg_DevSnapShot = 20;//设备拍照
  public final static int Msg_DevStartRec = 21;//设备开始录像
  public final static int Msg_DevStopRec = 22;//设备停止录象
  public final static int Msg_GetColors = 23;//取得亮度、对比度、色度、饱和度
  public final static int Msg_SetColors = 24;//设置亮度、对比度、色度、饱和度
  public final static int Msg_SetColorDefault = 25;
  public final static int Msg_GetMulticastInfo = 26;
  public final static int Msg_SetMulticastInfo = 27;
  public final static int Msg_GetAllCfg = 28;//取得所有配置
  public final static int Msg_SetAllCfg = 29;//设置所有配置
  public final static int Msg_GetDevInfo = 30;//取得设备信息
  public final static int Msg_SetDevInfo = 31;//设置设备信息
  public final static int Msg_GetUserLst = 32;//取得用户列表
  public final static int Msg_SetUserLst = 33;//设置用户列表
  public final static int Msg_GetNetCfg = 34;//取得网络配置
  public final static int Msg_SetNetCfg = 35;//设置网络配置
  public final static int Msg_WiFiSearch = 36;
  public final static int Msg_GetWiFiCfg = 37;//取得WiFi配置
  public final static int Msg_SetWiFiCfg = 38;//设置WiFi配置
  public final static int Msg_GetVideoCfg = 39;//取得视频配置
  public final static int Msg_SetVideoCfg = 40;//设置视频配置
  public final static int Msg_GetAudioCfg = 41;//取得音频配置
  public final static int Msg_SetAudioCfg = 42;//设置音频配置
  public final static int Msg_GetHideArea = 43;//秘录
  public final static int Msg_SetHideArea = 44;//秘录
  public final static int Msg_GetMDCfg = 45;//移动侦测配置
  public final static int Msg_SetMDCfg = 46;//移动侦测配置
  public final static int Msg_GetDiDoCfg__Disable = 47;
  public final static int Msg_SetDiDoCfg__Disable = 48;
  public final static int Msg_GetAlmCfg = 49;//取得Alarm配置
  public final static int Msg_SetAlmCfg = 50;//设置Alarm配置
  public final static int Msg_GetRS485Cfg__Disable = 51;
  public final static int Msg_SetRS485Cfg__Disable = 52;
  public final static int Msg_GetDiskCfg = 53;//设置Disk配置
  public final static int Msg_SetDiskCfg = 54;//设置Disk配置
  public final static int Msg_GetRecCfg = 55;//取得录影配置
  public final static int Msg_SetRecCfg = 56;//设置录影配置
  public final static int Msg_GetFTPCfg = 57;
  public final static int Msg_SetFTPCfg = 58;
  public final static int Msg_GetSMTPCfg = 59;
  public final static int Msg_SetSMTPCfg = 60;
  public final static int Msg_GetP2PCfg = 61;
  public final static int Msg_SetP2PCfg = 62;
  public final static int Msg_Ping = 63;
  public final static int Msg_GetRFCfg__Disable = 64;
  public final static int Msg_SetRFCfg__Disable = 65;
  public final static int Msg_RFControl__Disable = 66;
  public final static int Msg_RFPanic__Disable = 67;
  public final static int Msg_EmailTest = 68;
  public final static int Msg_FTPTest = 69;
  public final static int Msg_GetWiFiSTALst = 70;
  public final static int Msg_DeleteFromWiFiSTALst = 71;
  public final static int Msg_IsExistsAlarm = 72;
  public final static int Msg_DOControl = 73;
  public final static int Msg_GetDOStatus = 74;
  public final static int Msg_ReSerialNumber = 75;
  public final static int Msg_HttpGet = 76;
  public final static int Msg_DeleteFile = 77;
  public final static int Msg_HIISPCfg_Save = 78;
  public final static int Msg_HIISPCfg_Download = 79;
  public final static int Msg_HIISPCfg_Load = 80;
  public final static int Msg_HIISPCfg_Default = 81;
  public final static int Msg_GetAllCfgEx = 82;
  public final static int Msg_MulticastSetWIFI = 83;
  public final static int Msg_GetSensors = 84;//读PH 浊度 温度等
  public final static int Msg_DevIsRec = 85;
  public final static int Msg_GetPicFileLst = 86;
  public final static int Msg_GetSnapShotCfg = 87;
  public final static int Msg_SetSnapShotCfg = 88;
  public final static int Msg_GetRecCfgEx = 89;
  public final static int Msg_SetRecCfgEx = 90;
  public final static int Msg_GetRecStartTime = 91;
  public final static int Msg_FormattfCard = 92;
  public final static int Msg_______ = 93;

  public static native boolean thManageAddDevice(String SN, long NetHandle);
  public static native boolean thManageDelDevice(String SN);
  public static native boolean thManageDisconnFreeAll();
  public static native boolean thManageNetworkSwitch(int NetWorkType);//TYPE_NONE=-1 TYPE_MOBILE=0 TYPE_WIFI=1
  public static native boolean thManageForeBackgroundSwitch(int IsForeground);//Background=0 Foreground=1
}
//  Foreground Background