//-----------------------------------------------------------------------------
// Author      : ��첨
// Date        : 2017.09.14
// Version     : V 2.00
// Description : www.southipcam.com
//-----------------------------------------------------------------------------
#ifndef th_protocol_H
#define th_protocol_H

#include "cm_types.h"

#pragma pack(4)//n=1,2,4,8,16

#define Port_Ax_Data           2000
#define Port_Ax_http             80
#define Port_Ax_RTSP            554
#define Port_AlarmServer       9001

#define Port_Ax_Multicast      2000
#define Port_Ax_Search_Local   1999
#define Port_onvifSearch       3702
#define IP_Ax_Multicast  "239.255.255.250"//uPnP IP��ѯ �ಥ�Խ�IP
#define REC_FILE_EXT           "av"
#define REC_FILE_EXT_DOT      ".av"

//#pragma option push -b //start C++Builder enum 4 u8
//#pragma option push -b- //start C++Builder enum 1 u8

typedef enum TDevType {// sizeof 4
  dt_None=0,
  dt_DevX1=11,
  dt_DevOther
}TDevType;

/*�����*/
//-----------------------------------------------------------------------------
typedef struct TSMTPCfgPkt {//sizeof 500 u8�ʼ����ð�
  i32 Active;
  char40 SMTPServer;
  i32 SMTPPort;
  char40 FromAddress;
  char ToAddress[320];
  char40 Account;
  char40 Password;
  i32 SSL;
  char Reserved[8];
}TSMTPCfgPkt;
//-----------------------------------------------------------------------------
typedef struct TFTPCfgPkt {//sizeof 232 u8 FTP���ð�
  i32 Active;
  char100 FTPServer;
  i32 FTPPort;
  char40 Account;
  char40 Password;
  char40 UploadPath;
  char Reserved[4];
}TFTPCfgPkt;

typedef struct Tp2pCfgPkt {//sizeof 88 u8 P2P���ð�
  char80 UID;
  u8 Active;
  u8 StreamType;//0 ������ 1 ������
  u8 Version; //20171103
  u8 p2pType;   //tutk=0 self=1
  char Reserved[4];
}Tp2pCfgPkt;

// /usr/sbin/wput ftp://administrator:123456@192.168.1.20:21/ -b -B --proxy=off   --proxy-user=user --proxy-pass=pass  /bin/cp -o cp
// /usr/sbin/wput ftp://administrator:123456@192.168.1.20:21/ -b -B --proxy=http  --proxy-user=user --proxy-pass=pass  /bin/cp -o cp
// /usr/sbin/wput ftp://administrator:123456@192.168.1.20:21/ -b -B --proxy=socks --proxy-user=user --proxy-pass=pass  /bin/cp -o cp
//-----------------------------------------------------------------------------
typedef enum TGroupType{                          //sizeof 4 u8
  pt_Cmd,
  pt_PlayLive,
  pt_PlayHistory,
  pt_PlayMedia
}TGroupType;
//-----------------------------------------------------------------------------
typedef enum TStandardEx {
  StandardExMin=0,
  P720x576=1,
  P720x288=2,
  P704x576=3,
  P704x288=4,
  P352x288=5,
  P176x144=6,
  N720x480=7,
  N720x240=8,
  N704x480=9,
  N704x240=10,
  N352x240=11,
  N176x120=12,
  V640x360=13,
  V320x240=14,
  V640x480=15,
  V800x600=16,
  V1024x768=17,
  V1280x720=18,
  V1280x800=19,
  V1280x960=20,
  V1280x1024=21,
  V1360x768=22,
  V1400x1050=23,
  V1600x1200=24,
  V1920x1080=25,
  V2304x1296=26,
  V2560x1440=27,
  V2592x1520=28,
  V2592x1944=29,
  StandardExMax=30
}TStandardEx;
//-----------------------------------------------------------------------------
typedef enum TVideoType {                        //��Ƶ��ʽsizeof 4 u8
  MPEG4          =0,
  MJPEG          =1,
  H264           =2,
  H265           =3,
}TVideoType;
//-----------------------------------------------------------------------------
typedef enum TStandard {
  NTSC           =0,
  PAL            =1
}TStandard;
//-----------------------------------------------------------------------------
#define CBR    0
#define VBR    1
typedef struct TVideoFormat {                    //��Ƶ��ʽ sizeof 128
  i32  Standard;                                 //��ʽ PAL=1, NTSC=0 default=0xff
  i32  Width;                                    //�� 720 360 180 704 352 176 640 320 160
  i32  Height;                                   //�� 480 240 120 576 288 144 
  TVideoType VideoType;                          //MPEG4=0x00, MJPEG=0x01  H264=0x02
  u8  Brightness;                               //����   0-255
  u8  Contrast;                                 //�Աȶ� 0-255
  u8  Hue;                                      //ɫ��   0-255
  u8  Saturation;                               //���Ͷ� 0-255
  i32  FrameRate;                                //֡�� 1-30 MAX:PAL 25 NTSC 30
  i32  IPInterval;                               //IP֡��� 1-120 default 30
  u8 BitRateType;                              //0������CBR 1������VBR
  u8 BitRateQuant;                             //����  0..4
  u8  BrightnessNight;                          //����   0-255
  u8  ContrastNight;                            //�Աȶ� 0-255

  i32  BitRate;                                  //���� 64K 128K 256K 512K 1024K 1536K 2048K 2560K 3072K
  i32  IsMirror;                                 //ˮƽ��ת false or true
  i32  IsFlip;                                   //��ֱ��ת false or true
  char20 Reserved;                                //OSD���� 20������
  float TitleY;
  float TimeY;
  float FrameRateY;
  float LogoX;
  float LogoY;

  u8 HueNight;
  u8 ShowTitleInDev;
  u8 IsShowTitle;                            //��ʾʱ����� false or true
  u8 TitleColor;
  float TitleX;

  u8 SaturationNight;
  u8 ShowTimeInDev;
  u8 IsShowTime;                               //��ʾˮӡ false or true
  u8 TimeColor;
  float TimeX;

  u8 DeInterlaceType;
  u8 IsDeInterlace;
  u8 IsOSDBigFont;
  u8 IsWDR;
  u8  Sharpness;                               //��� 0-255
  u8 IRCutSensitive;
  u8 IsNewSendStyle;//���̷߳��ͻ��Ƕ��̷߳��� 178 185=1 / old 16c 18e=0
  u8 IsShowLogo;

  u8 SharpnessNight;
  u8 IsOsdTransparent;//ShowFrameRateInDev
  u8 IsShowFrameRate;
  u8 FrameRateColor;
  float FrameRateX;

  struct { //add at 2009/09/02
    u8 BitRateQuant;//����  0..4
    u8 StandardEx;//TStandardEx
    u8 FrameRate;//֡�� 1-30 MAX:PAL 25 NTSC 30
    u8 BitRateType;//0������ 1������
    i32 BitRate;//���� 64K 128K 256K 512K 1024K 1536K 2048K 2560K 3072K
  }Sub;//������

  u8 WDRLevel;
  u8 Reserved1[3];
}TVideoFormat;
//-----------------------------------------------------------------------------
typedef struct TVideoCfgPkt {                    //��Ƶ���ð� sizeof 148 u8
  i32  Chl;                                      //ͨ�� 0..15 ��Ӧ 1..16ͨ��
  i32  Active;                                   //�Ƿ�����(������ʱû���õ�)
  u8 InputType;                                //��������
  u8 Reserved[3];
  struct TVideoFormat VideoFormat;               //��Ƶ��ʽ
  i32 Flag;                                      //������Ч=1
  i32 Flag1;
}TVideoCfgPkt;
//-----------------------------------------------------------------------------
typedef enum TAudioType {                        //��Ƶ��ʽsizeof 4 u8
  Audio_NULL            =0x00,
  PCM                   =0x01,
  G711                  =0x02,
}TAudioType;
//-----------------------------------------------------------------------------
typedef struct TAudioFormat {                    //��Ƶ��ʽ = TWaveFormatEx = sizeof 32
  u32 wFormatTag;                              //PCM=0X0001, ADPCM=0x0011, MP2=0x0050, MP3=0X0055, GSM610=0x0031
  u32 nChannels;                               //������=0 ������=1
  u32 nSamplesPerSec;                          //������ 
  u32 nAvgbytesPerSec;                         //for buffer estimation 
  u32 nBlockAlign;                             //block size of data 
  u32 wBitsPerSample;                          //number of bits per sample of mono data 
  u32 cbSize;                                  //������С
  i32 Reserved;                                      
}TAudioFormat;
//-----------------------------------------------------------------------------
typedef struct TAudioCfgPkt {                    //��Ƶ���ð� sizeof 48 u8
  i32  Chl;                                      //ͨ��0..15 ��Ӧ 1..16ͨ��
  i32  Active;                                   //�Ƿ���������
  struct TAudioFormat AudioFormat;               //��Ƶ��ʽ
#define MIC_IN  0
#define LINE_IN 1
  u8 InputType;                                 //0 MIC����, 1 LINE����
  u8 IsPlayPromptSound;
  u8 VolumeLineIn;
  u8 VolumeLineOut;
  u8 SoundTriggerActive;
  u8 SoundTriggerSensitive;
  u8 IsAudioGain;
  u8 AudioGainLevel;
}TAudioCfgPkt;
//-----------------------------------------------------------------------------
typedef enum TPlayCtrl {                         //���ſ���sizeof 4 u8
  PS_None               =0,                 //��
  PS_Play               =1,                 //����
  PS_Pause              =2,                 //��ͣ
  PS_Stop               =3,                 //ֹͣ
  PS_FastBackward       =4,                 //����
  PS_FastForward        =5,                 //���
  PS_StepBackward       =6,                 //����
  PS_StepForward        =7,                 //����
  PS_DragPos            =8,                 //�϶�
}TPlayCtrl; 
//-----------------------------------------------------------------------------
typedef struct TPlayCtrlPkt {
  TPlayCtrl PlayCtrl;
  u32 Speed;//���PlayCtrl=PS_StepBackward, PS_FastForward ���򱣴������˱��� 1 2 4 8 16 32����
  u32 Pos;//���PlayCtrl=PS_DragPos���򱣴��ļ��ļ�λ��Pos
}TPlayCtrlPkt;
//-----------------------------------------------------------------------------
typedef struct TRecFilePkt {                     //������ʷ�� SizeOf 100
  char20 DevIP;
  u8 Chl;
  u8 RecType;                               //0:��ͨ¼Ӱ 1:����¼Ӱ 2ý���ļ�
  u8 Reserved[2];
  i32 StartTime;                              //��ʼʱ��
  i32 EndTime;                                //����ʱ��
  char64 FileName;                            //�ļ���
  i32 Reserved1;                              //����
}TRecFilePkt;
//-----------------------------------------------------------------------------
//  /sd/rec/20091120/20091120_092749_0.ra2
typedef struct TRecFileIdx {                     //¼Ӱ�ļ������� sizeof 80
  char64 FileName;
  u8 Chl;
  u8 RecType;
  u8 Reserved;
  u8 Flag;
  time_t StartTime;
  time_t EndTime;
  u32 FileSize;
}TRecFileIdx;

#define RECFILELSTCOUNT 16
typedef struct TRecFileLst { //sizeof 1288
  i32 Total;
  i32 SubTotal;
  TRecFileIdx Lst[RECFILELSTCOUNT];
}TRecFileLst;
//-----------------------------------------------------------------------------
typedef struct TRecFileHead {                    //¼Ӱ�ļ�ͷ��ʽ sizeof 256 u8
  u32 DevType;                                 //�豸���� = DEV_Ax
  u32 FileSize;                                //�ļ���С
  i32 StartTime;                              //��ʼʱ��
  i32 EndTime;                                //ֹͣʱ��
  char20 DevName;                                //�豸ID
  char20 DevIP;                                  //�豸IP
  u32 VideoChannel;                            //��Ƶͨ����ͳ��
  u32 AudioChannel;                            //��Ƶͨ����ͳ��
  struct TVideoFormat VideoFormat;               //��Ƶ��ʽ
  struct TAudioFormat AudioFormat;               //��Ƶ��ʽ
  i32 IndexType;                                 //û��=0 ���ļ���С = 1 ��ʱ��=2
  char Reserved[28];                             //����
}TRecFileHead;
//-----------------------------------------------------------------------------
typedef struct TFilePkt {                        //�ϴ��ļ��� sizeof 532
#define FILETYPE_BURNIN_UBOOT  7 //x1 burn in
#define FILETYPE_BIN           2 //x1 x2 bin ������
#define FILETYPE_UPDATE_PKT    3
#define FILETYPE_FILESYSTEM    4
  i32 FileType;
  u32 FileSize;
  char256 FileName;
  i32 Handle;

#define UPGRAD_UPLOAD_FAIL      0
#define UPGRAD_UPLOAD_OVER_ING  1
#define UPGRAD_UPLOAD_OVER_OK   2
#define UPGRAD_UPLOAD_FLASHING  3
#define UPGRAD_UPLOAD_ING       4
#define UPGRAD_UPLOAD_FS_ERROR  5
#define UPGRAD_UPLOAD_CRC       6
  i32 Result;//0=�ϴ��ļ�ʧ�� 1=�ϴ��ļ����,��������,�벻Ҫ�ϵ�  2=�ϴ��ļ��ɹ� (3=�����ϴ��ļ� x5add)
  char256 DstFile;
  u32 crc;
}TFilePkt;
//-----------------------------------------------------------------------------
#ifndef ANDROID
typedef enum TAlmType {
  Alm_None             =0,//��
  Alm_MotionDetection  =1,//λ�Ʊ���Motion Detection
  Alm_DigitalInput     =2,//DI����
  Alm_SoundTrigger     =3,////������������
  Net_Disconn          =4,//�������
  Net_ReConn           =5,//��������
  Alm_HddFill          =6,//����
  Alm_VideoBlind       =7,//��Ƶ�ڵ�
  Alm_VideoLost        =8,//��Ƶ��ʧ
  Alm_DoorBell         =9,//���崥��
  Alm_Other            =10,//��������
  Alm_RF               =11,
  Alm_OtherMax         =12,
}TAlmType;
#endif
//-----------------------------------------------------------------------------
typedef struct TAlmSendPkt {                     //�����ϴ���sizeof 36
  TAlmType AlmType;                              //��������
  i32 AlmTime;                                   //����ʱ��
  i32 AlmPort;                                   //�����˿�
  char20 DevIP;
  i32 Flag;                                      //MD ��������
}TAlmSendPkt;
//-----------------------------------------------------------------------------
typedef struct TDoControlPkt {                 //do���ư���sizeof 16
  i32 Chl;
  i32 Value;                                   // 0 �ء�1 ��
  i32 Reserved;
}TDoControlPkt;
//-----------------------------------------------------------------------------
typedef enum TTaskDayType{w_close,w_1,w_2,w_3,w_4,w_5,w_6,w_7,w_1_5,w_1_6,w_6_7,w_1_7,w_Interval} TTaskDayType;
typedef struct TTaskhm {
  u8 w;
  u8 Days;
  u8 Reserved[2];
  u8 start_h;//ʱ 0-23
  u8 start_m;//�� 0-59
  u8 stop_h;//ʱ 0-23
  u8 stop_m;//�� 0-59
}TTaskhm;
//-----------------------------------------------------------------------------
typedef struct THideAreaCfgPkt {                 //����¼Ӱ����� sizeof 72
  char Reserved0[8];
  u8 Active;                                    //false or true
  char Reserved1[23];
  struct {
    float left,top,right,bottom;
  }NewRect;
  char Reserved2[24];
}THideAreaCfgPkt;
//-----------------------------------------------------------------------------
typedef struct TMDCfgPkt {                       //�ƶ����� sizeof 96
  u8 Reserved0[8];
  u8 Active;
  u8 Reserved1[1];
  u8 Sensitive;                              //��������� 0-255
  u8 Reserved2[57];
  struct TTaskhm hm;
  struct {
    float left, top, right, bottom;
  }NewRect;
  u8 Reserved3[4];
}TMDCfgPkt;
//-----------------------------------------------------------------------------
typedef struct TAlmCfgItem {
  u8 AlmType;//u8(TAlmType)
  u8 Channel;
  u8 Active;//only di
  u8 IsAlmRec;
  u8 IsFTPUpload;//NetSend
  u8 ActiveDO;//DI����DOͨ�� false close
  u8 IsSendEmail;//u8 DOChannel;
  u8 Reserved;
}TAlmCfgItem;

typedef struct TAlmCfgPkt {                   //�������ð� sizeof 268 -> 52 20140928
  i32 AlmOutTimeLen;                    //�������ʱ��(��) 0 ..600 s
  i32 AutoClearAlarm;
  i32 Reserved;
  TAlmCfgItem DIAlm;
  TAlmCfgItem MDAlm;
  TAlmCfgItem SoundAlm;
  TAlmCfgItem ReservedItem[2];
}TAlmCfgPkt;
//-----------------------------------------------------------------------------
#define USER_GUEST     1 
#define USER_OPERATOR  2
#define USER_ADMIN     3
#define GROUP_GUEST    1
#define GROUP_OPERATOR 2
#define GROUP_ADMIN    3

#define MAXUSERCOUNT             20              //����û�����
typedef struct TUserCfgPkt {                     //sizeof 1048
  i32 Count;
  struct {
    i32 UserGroup;                                 //Guest=1 Operator=2 Administrator=3
    i32 Authority;                                 //3Ϊadmin ,
    char20 UserName;                               //�û��� admin���ܸ���
    char20 Password;                               //����
    i32 Flag;
  }Lst[MAXUSERCOUNT];
  i32 Reserved;
}TUserCfgPkt;
//-----------------------------------------------------------------------------
typedef enum TPTZCmd {                           //sizeof 4 u8
  PTZ_None             = 0,
  PTZ_Up               = 1,//��
  PTZ_Up_Stop          = 2,//��ֹͣ
  PTZ_Down             = 3,//��
  PTZ_Down_Stop        = 4,//��ֹͣ
  PTZ_Left             = 5,//��
  PTZ_Left_Stop        = 6,//��ֹͣ
  PTZ_Right            = 7,//��
  PTZ_Right_Stop       = 8,//��ֹͣ
  PTZ_LeftUp           = 9,//����
  PTZ_LeftUp_Stop      =10,//����ֹͣ
  PTZ_RightUp          =11,//����
  PTZ_RightUp_Stop     =12,//����ֹͣ
  PTZ_LeftDown         =13,//����
  PTZ_LeftDown_Stop    =14,//����ֹͣ
  PTZ_RightDown        =15,//����
  PTZ_RightDown_Stop   =16,//����ֹͣ
  PTZ_IrisIn           =17,//��ȦС
  PTZ_IrisInStop       =18,//��Ȧֹͣ
  PTZ_IrisOut          =19,//��Ȧ��
  PTZ_IrisOutStop      =20,//��Ȧֹͣ
  PTZ_ZoomIn           =21,//����С
  PTZ_ZoomInStop       =22,//����ֹͣ
  PTZ_ZoomOut          =23,//���ʴ�
  PTZ_ZoomOutStop      =24,//����ֹͣ
  PTZ_FocusIn          =25,//����С
  PTZ_FocusInStop      =26,//����ֹͣ
  PTZ_FocusOut         =27,//�����
  PTZ_FocusOutStop     =28,//����ֹͣ
  PTZ_LightOn          =29,//�ƹ�С
  PTZ_LightOff         =30,//�ƹ��
  PTZ_RainBrushOn      =31,//��ˢ��
  PTZ_RainBrushOff     =32,//��ˢ��
  PTZ_AutoOn           =33,//�Զ���ʼ  //Rotation
  PTZ_AutoOff          =34,//�Զ�ֹͣ
  PTZ_TrackOn          =35,
  PTZ_TrackOff         =36,
  PTZ_IOOn             =37,
  PTZ_IOOff            =38,
  PTZ_ClearPoint       =39,//��̨��λ
  PTZ_SetPoint         =40,//�趨��̨��λ
  PTZ_GotoPoint        =41,//��̨��λ

  PTZ_GetStatus        =42,
  PTZ_AbsoluteMoveXY   =43,//Anto SIP����
  PTZ_AbsoluteZoomIn   =44,//Anto 
  PTZ_AbsoluteZoomOut  =45,//Anto
  PTZ_RelativeMoveXY   =46,//Anto SIP���
  PTZ_RelativeZoomIn   =47,//Anto 
  PTZ_RelativeZoomOut  =48,//Anto

  PTZ_RunTour          =49,//Anto Ѳ��
  PTZ_GuardSetPoint    =50,//Anto ����λ
  PTZ_GuardGotoPoint   =51,//Anto ����λ

  PTZ_Max              =52
}TPTZCmd;
//-----------------------------------------------------------------------------
typedef struct TPTZStatus {//sizeof 16
  int x;
  int y;
  int zoom;
  int flag;
}TPTZStatus;

typedef enum TPTZProtocol {                      //��̨Э�� sizeof 4
  Pelco_P               =0,
  Pelco_D               =1,
  Pelco_D_Anto          =2,                      //��;
  Protocol_Custom       =2,
}TPTZProtocol;

typedef struct TPTZPkt {                         //PTZ ��̨����  sizeof 108
  TPTZCmd PTZCmd;                                    //=PTZ_None Ϊ͸������
  union {
    struct {
      TPTZProtocol Protocol;                         //��̨Э��
      i32 Address;                                   //��̨��ַ
      i32 wParam;                                    //��̨�ٶȻ�����
      i32 lParam;                                    //������Ԥ��λ������
      i32 Autotype;
      i32 SleepMS;//20140722 x3 ptz add
    };
    struct {
      char100 TransBuf;
      i32 TransBufLen;
    };
  };
}TPTZPkt;
//-----------------------------------------------------------------------------
typedef struct TPlayLivePkt {                    //�����ֳ���//sizeof 20
  u32 VideoChlMask;//ͨ������ 
  //  31 .. 19 18 17 16   15 .. 03 02 01 00
  //         0  0  0  0          0  0  0  1     
  u32 AudioChlMask;
  //  31 .. 19 18 17 16   15 .. 03 02 01 00
  //         0  0  0  0          0  0  0  1
  i32 Value;                                     //Value=0��������֡��Value=1ֻ������ƵI֡
  //begin add at 2009/09/02
  u32 SubVideoChlMask;
  //11  i32 IsRecvAlarm;                               //0�����豸���� 1�������豸����
  //end add
  i32 Flag;                                      //���� 
}TPlayLivePkt;
//-----------------------------------------------------------------------------
typedef struct TPlayBackPkt {                    //sizeof 20
  i32 Chl;
  i32 FileType;                                  //0:��ͨ¼Ӱ 1:����¼Ӱ 2ý���ļ�
  i32 StartTime;                                 //��ʼʱ��
  i32 EndTime;                                   //����ʱ��
  i32 Flag;
}TPlayBackPkt;
//-----------------------------------------------------------------------------
typedef enum TMsgID {
  Msg_None                   =  0,
  Msg_Login                  =  1,//�û���¼
  Msg_PlayLive               =  2,//��ʼ�����ֳ�
  Msg_StartPlayRecFile       =  3,//����¼Ӱ�ļ�
  Msg_StopPlayRecFile        =  4,//ֹͣ����¼Ӱ�ļ�
  Msg_GetRecFileLst          =  5,//ȡ��¼Ӱ�ļ��б�
  Msg_GetDevRecFileHead      =  6,//ȡ���豸�ļ��ļ�ͷ��Ϣ
  Msg_StartUploadFile        =  7,//��ʼ�ϴ��ļ�
  Msg_AbortUploadFile        =  8,//ȡ���ϴ��ļ�
  Msg_StartUploadFileEx      =  9,//��ʼ�ϴ��ļ�tftp
  Msg_StartTalk              = 10,//��ʼ�Խ�
  Msg_StopTalk               = 11,//ֹͣ�Խ�
  Msg_PlayControl            = 12,//���ſ���
  Msg_PTZControl             = 13,//��̨����
  Msg_Alarm                  = 14,//����
  Msg_ClearAlarm             = 15,//�رվ���
  Msg_GetTime                = 16,//ȡ��ʱ��
  Msg_SetTime                = 17,//����ʱ��
  Msg_SetDevReboot           = 18,//�����豸
  Msg_SetDevLoadDefault      = 19,//ϵͳ�ص�ȱʡ���� Pkt.Value= 0 ���ָ�IP, Pkt.Value= 1 �ָ�IP
  Msg_DevSnapShot            = 20,//�豸����
  Msg_DevStartRec            = 21,//�豸��ʼ¼��
  Msg_DevStopRec             = 22,//�豸ֹͣ¼��
  Msg_GetColors              = 23,//ȡ�����ȡ��Աȶȡ�ɫ�ȡ����Ͷ�
  Msg_SetColors              = 24,//�������ȡ��Աȶȡ�ɫ�ȡ����Ͷ�
  Msg_SetColorDefault        = 25,
  Msg_GetMulticastInfo       = 26,
  Msg_SetMulticastInfo       = 27,
  Msg_GetAllCfg              = 28,//ȡ����������
  Msg_SetAllCfg              = 29,//������������
  Msg_GetDevInfo             = 30,//ȡ���豸��Ϣ
  Msg_SetDevInfo             = 31,//�����豸��Ϣ
  Msg_GetUserLst             = 32,//ȡ���û��б�
  Msg_SetUserLst             = 33,//�����û��б�
  Msg_GetNetCfg              = 34,//ȡ����������
  Msg_SetNetCfg              = 35,//������������
  Msg_WiFiSearch             = 36,
  Msg_GetWiFiCfg             = 37,//ȡ��WiFi����
  Msg_SetWiFiCfg             = 38,//����WiFi����
  Msg_GetVideoCfg            = 39,//ȡ����Ƶ����
  Msg_SetVideoCfg            = 40,//������Ƶ����
  Msg_GetAudioCfg            = 41,//ȡ����Ƶ����
  Msg_SetAudioCfg            = 42,//������Ƶ����
  Msg_GetHideArea            = 43,//��¼
  Msg_SetHideArea            = 44,//��¼
  Msg_GetMDCfg               = 45,//�ƶ��������
  Msg_SetMDCfg               = 46,//�ƶ��������
  Msg_GetDiDoCfg__Disable    = 47,
  Msg_SetDiDoCfg__Disable    = 48,
  Msg_GetAlmCfg              = 49,//ȡ��Alarm����
  Msg_SetAlmCfg              = 50,//����Alarm����
  Msg_GetRS485Cfg__Disable   = 51,
  Msg_SetRS485Cfg__Disable   = 52,
  Msg_GetDiskCfg             = 53,//����Disk����
  Msg_SetDiskCfg             = 54,//����Disk����
  Msg_GetRecCfg              = 55,//ȡ��¼Ӱ����
  Msg_SetRecCfg              = 56,//����¼Ӱ����
  Msg_GetFTPCfg              = 57,
  Msg_SetFTPCfg              = 58,
  Msg_GetSMTPCfg             = 59,
  Msg_SetSMTPCfg             = 60,
  Msg_GetP2PCfg              = 61,
  Msg_SetP2PCfg              = 62,
  Msg_Ping                   = 63,
  Msg_GetRFCfg__Disable      = 64,
  Msg_SetRFCfg__Disable      = 65,
  Msg_RFControl__Disable     = 66,
  Msg_RFPanic__Disable       = 67,
  Msg_EmailTest              = 68,
  Msg_FTPTest                = 69,
  Msg_GetWiFiSTALst          = 70,
  Msg_DeleteFromWiFiSTALst   = 71,
  Msg_IsExistsAlarm          = 72,
  Msg_DOControl              = 73,
  Msg_GetDOStatus            = 74,
  Msg_ReSerialNumber         = 75,
  Msg_HttpGet                = 76,
  Msg_DeleteFile             = 77,
  Msg_HIISPCfg_Save          = 78,
  Msg_HIISPCfg_Download      = 79,
  Msg_HIISPCfg_Load          = 80,
  Msg_HIISPCfg_Default       = 81,
  Msg_GetAllCfgEx            = 82,
  Msg_MulticastSetWIFI       = 83,
  Msg_GetSensors             = 84,//��PH �Ƕ� �¶ȵ�  
  Msg_DevIsRec               = 85,
  Msg_GetPicFileLst          = 86,
  Msg_GetSnapShotCfg         = 87,
  Msg_SetSnapShotCfg         = 88,
  Msg_GetRecCfgEx            = 89,
  Msg_SetRecCfgEx            = 90,
  Msg_GetRecStartTime        = 91,
  Msg_FormattfCard           = 92,

  Msg_CheckUpgradeBin        = 93,
  Msg_TestModeStart          = 94,
  Msg_TestModeStop           = 95,
  Msg_GetLightCfg            = 96,
  Msg_SetLightCfg            = 97,
  Msg_GetPushCfg             = 98,
  Msg_SetPushCfg             = 99,
  Msg_PlayWavFile            =100,

  Msg_______                 =100,
}TMsgID;
//-----------------------------------------------------------------------------
#define RECPLANLST 4
typedef struct TPlanRecPkt {                        //�ų�¼Ӱ�ṹ sizeof 224
  struct {
    u8 Active;
    u8 start_h;    //ʱ 0-23
    u8 start_m;    //�� 0-59
    u8 stop_h;     //ʱ 0-23��ȡ�豸�Ƿ��о�������
    u8 stop_m;     //�� 0-59
    u8 IsRun;      //��ǰ�ƻ��Ƿ�����
    u8 Reserved[2];
  }Week[7][RECPLANLST];                                 //��һ���������� ÿ�����4������
}TPlanRecPkt;
//-----------------------------------------------------------------------------
typedef enum TRecStyle {
  rs_RecManual,
  rs_RecAuto,
  rs_RecPlan,
  rs_RecAlarm
}TRecStyle;

typedef struct TRecCfgPkt {                      //¼Ӱ���ð� sizeof 260
  i32 ID;
  i32 DevID;//PC�˹������ֻ���ڴ洢���ݿ����豸��š��豸�˱���
  i32 Chl;
  u8 IsLoseFrameRec;//�Ƿ�֡¼Ӱ
  u8 RecStreamType;//0 ������ 1 ������
  u8 Reserved;
  u8 IsRecAudio;//¼����Ƶ ��û���õ�
  u32 Rec_AlmPrevTimeLen;//��ǰ¼Ӱʱ��     5 s
  u32 Rec_AlmTimeLen;//����¼Ӱʱ��        10 s
  u32 Rec_NmlTimeLen;//һ��¼Ӱ�ָ�ʱ��   600 s
  TRecStyle RecStyle;//¼Ӱ����
  TPlanRecPkt Plan;
  i32 bFlag;
}TRecCfgPkt;
//-----------------------------------------------------------------------------
typedef struct TDiskCfgPkt {   //sizeof 60
  char Reserved1[24];
  char20 DiskName;      // ���� 
  i32 Active;           // �Ƿ���Ϊ¼Ӱ���� false or true
  u32 DiskSize;       // M ReadOnly
  u32 FreeSize;       // M
  u32 MinFreeSize;    // M
}TDiskCfgPkt;
//-----------------------------------------------------------------------------
typedef struct TAxInfo {//sizeof 40
  union {
    char40 BufValue;
    struct {
      u8 ExistWiFi;
      u8 ExistSD;
      u8 IsEtherLink;      //���������Ƿ�����
      u8 HardType;      //Ӳ������
      u32 VideoTypeMask;// 8
      u64 StandardMask;//16
      u8 ReservedExistFlash;
      u8 PlatformType;//TPlatFormType
      u8 SDKVersion;
      u8 Reserved[1];
      u8 wifiStatus;
      u8 upnpStatus;
      u8 IsConnWLAN;
      u8 p2pStatus;
      u64 SubStandardMask;//32
      struct {
        i32 FirstDate;
        u16 TrialDays;
        u16 RunDays;
      } Sys;
    };
  };
}TAxInfo;

typedef struct TDevInfoPkt {                     //�豸��Ϣ��sizeof 180
  char DevModal[12];                             //�豸�ͺ�  ='7xxx'
  u32 SN;
  i32 DevType;                                   //�豸����
  char20 SoftVersion;                            //����汾
  char20 FileVersion;                            //�ļ��汾
  char60 DevName;                                //�豸��ʶ
  struct TAxInfo Info;
  i32 VideoChlCount;
  u8 AudioChlCount;
  u8 DiChlCount;
  u8 DoChlCount;
  u8 RS485DevCount;
  signed char TimeZone;
  u8 MaxUserConn;                               //����û������� default 10
  u8 OEMType;
  u8 DoubleStream;                              //�Ƿ�˫���� add at 2009/09/02
  struct {
    u8 w;//TTaskDayType;
    u8 start_h;//ʱ 0-23
    u8 start_m;//�� 0-59
    u8 Days;
  }RebootHM;
  i32 ProcRunningTime;
}TDevInfoPkt;
//-----------------------------------------------------------------------------
typedef struct TWiFiSearchPkt {//sizeof 40
  char32 SSID;
  u8 Siganl;//�ź� 0..100 ���� �� һ�� �� ����
  u8 Channel;
  u8 EncryptType; //0=None 1=WEP 2=WPA
  u8 NetworkType;//0=Infra 1=Adhoc
  union {
    struct {
      u8 Auth;//0=AUTO 1=OPEN 2=SHARED
      u8 Reserved[3];
    }WEP;
    struct {
      u8 Auth;//0=AUTO 1=WPA-PSK 2=WPA2-PSK
      u8 Enc;//0=AUTO 1=TKIP 2=AES
      u8 Reserved[2];
    }WPA;
  };
}TWiFiSearchPkt;
//-----------------------------------------------------------------------------
typedef struct TWiFiCfgPkt {                     //�������ð� sizeof 200
  u8 Active;
#define WIFIMODE_STA   0
#define WIFIMODE_AP    1
#define WIFIMODE_SMT   2

  u8 WifiMode;//sta=0  ap=1 smt=2
  u8 Reserved[2];
  char SSID_AP[30];
  char Password_AP[30];

  char32 SSID_STA;
  i32 Channel;//Ƶ��1..14 default 1=Auto
#define Encrypt_None   0
#define Encrypt_WEP    1
#define Encrypt_WPA    2
  i32 EncryptType;//(Encrypt_None,Encrypt_WEP,Encrypt_WPA);
  char64 Password_STA;
  i32 NetworkType;//0=Infra 1=Adhoc
  union {
    struct {
      char ValueStr[28];
    };
#define AUTH_AUTO      0
#define AUTH_OPEN      1
#define AUTH_SHARED    2
#define AUTH_TKIP      1
#define AUTH_AES       2
    struct {
      i32 Auth;//0=AUTO 1=OPEN 2=SHARED
    }WEP;
    struct {
      i32 Auth;//0=AUTO 1=WPA-PSK 2=WPA2-PSK
      i32 Enc;//0=AUTO 1=TKIP 2=AES
    }WPA;
  };
}TWiFiCfgPkt;
//-----------------------------------------------------------------------------
typedef struct TNetCfgPkt {                      //�豸�������ð�sizeof 372
  i32 DataPort;                                   //�������ݶ˿�
  i32 rtspPort;                                  //rtsp�˿�
  i32 HttpPort;                                  //http��ҳ�˿�
  struct {
#define IP_STATIC   0
#define IP_DYNAMIC  1

    i32 IPType;
    char20 DevIP;
    char20 DevMAC;
    char20 SubMask;
    char20 Gateway;
    char20 DNS1;
    char20 DNS2;
    u8 IsManualChange;//�Ƿ����ֶ��޸ĵģ������Զ��޸�IP //20171030
    char Reserved[3];//20171030
  }Lan;
  struct {
    i32 Active;
#define DDNS_3322     0
#define DDNS_dynDNS   1
#define DDNS_MyDDNS   2
#define DDNS_9299     3
    i32 DDNSType;                               //0=3322.ORG 1=dynDNS.ORG 2=MyDDNS 3=9299.org
    char40 DDNSDomain;                           //��DDNS SERVER IP
    char40 DDNSServer;
    char Reserved[44];
  }DDNS;
  struct {
    i32 Active;
    char40 Account;
    char40 Password;
    i32 Reserved;
  }PPPOE;
  struct {
    i32 Active;
    i32 Reserved;
  }uPnP;
  i32 Reserved;
}TNetCfgPkt;
//-----------------------------------------------------------------------------
typedef enum TBaudRate{
  BaudRate_1200  =    1200,
  BaudRate_2400  =    2400,
  BaudRate_4800  =    4800,
  BaudRate_9600  =    9600,
  BaudRate_19200  =  19200,
  BaudRate_38400  =  38400,
  BaudRate_57600  =  57600,
  BaudRate_115200 = 115200
}TBaudRate;

typedef enum TDataBit{
  DataBit_5 = 5,
  DataBit_6 = 6,
  DataBit_7 = 7,
  DataBit_8 = 8
}TDataBit;

typedef enum TParityCheck{
  ParityCheck_None  = 0,
  ParityCheck_Odd   = 1,
  ParityCheck_Even  = 2,
  ParityCheck_Mask  = 3,
  ParityCheck_Space = 4
}TParityCheck;

typedef enum TStopBit{
  StopBit_1   = 0,
  StopBit_1_5 = 1,
  StopBit_2   = 2
}TStopBit;

typedef struct TRS485CfgPkt__Disable {                       //485ͨ�Ű� sizeof 280
  i32 Chl;
  TBaudRate BPS;//������
  TDataBit DataBit;//����λ
  TParityCheck ParityCheck;//��żУ��
  TStopBit StopBit;//ֹͣλ
  struct {
    u8 Address;
    u8 PTZProtocol;//��̨Э��
    u8 PTZSpeed;
    u8 Reserved;
  }Lst[32];//��Ӧ��Ӧ����Ƶͨ��

  //char PTZNameLst[128];//��ʱδ�õ� format "Pelco_P\nPelco_D\nProtocol_Custom"

  i32 PTZCount;
  char20 PTZNameLst[6];
  i32 Reserved;
  i32 Flag;
}TRS485CfgPkt__Disable;
//-----------------------------------------------------------------------------
typedef struct TColorsPkt {
  i32 Chl;
  u8  Brightness;                               //����   0-255
  u8  Contrast;                                 //�Աȶ� 0-255
  u8  Hue;                                      //ɫ��   0-255
  u8  Saturation;                               //���Ͷ� 0-255
  u8  Sharpness;                                //���Ͷ� 0-255
  u8 Reserved[3];
}TColorsPkt;
//-----------------------------------------------------------------------------
typedef struct TMulticastInfoPkt {               //�ಥ������Ϣ��sizeof 556->588->628
  TDevInfoPkt DevInfo;
  TNetCfgPkt NetCfg;
  i32 Flag;// sendfrom client=0 sendfrom device=1
  TWiFiCfgPkt WiFiCfg;
  Tp2pCfgPkt p2pCfg;
  TVideoCfgPkt VideoCfg;
  TAudioCfgPkt AudioCfg;
  char20 UserName;                               //�û��� admin���ܸ���
  char20 Password;                               //����
}TMulticastInfoPkt;
//-----------------------------------------------------------------------------
#define Head_CmdPkt           0xAAAAAAAA         //�������ͷ
#define Head_VideoPkt         0xBBBBBBBB         //��Ƶ����ͷ
#define Head_AudioPkt         0xCCCCCCCC         //��Ƶ����ͷ
#define Head_TalkPkt          0xDDDDDDDD         //�Խ�����ͷ
#define Head_UploadPkt        0xEEEEEEEE         //�ϴ���
#define Head_DownloadPkt      0xFFFFFFFF         //���ذ�//δ��
#define Head_CfgPkt           0x99999999         //���ð�
#define Head_SensePkt         0x88888888         //����//δ��
#define Head_MotionInfoPkt    0x77777777         //�ƶ���ֵⷧ��ͷ
#define Head_GPIOStatusPkt    0x66666666         //GPIOStatus
//-----------------------------------------------------------------------------
typedef struct THeadPkt{                         //sizeof 8
  u32 VerifyCode;                              //У���� = 0xAAAAAAAA 0XBBBBBBBB 0XCCCCCCCC 0XDDDDDDDD 0XEEEEEEEE
  u32 PktSize;                                 //������С=1460-8
}THeadPkt;
//-----------------------------------------------------------------------------
typedef struct TTalkHeadPkt {                    //�Խ�����ͷ  sizeof 32
  u32 VerifyCode;                              //У���� = 0XDDDDDDDD
  u32 PktSize;                                 
  char20 TalkIP;
  u32 TalkPort;
}TTalkHeadPkt;
//-----------------------------------------------------------------------------
typedef struct TFrameInfo { //¼Ӱ�ļ�����֡ͷ  16 u8
  i64 FrameTime;                               //֡ʱ�䣬time_t*1000000 +us
  u8 Chl;                                      //ͨ�� 0..15 ��Ӧ 1..16ͨ��
  u8 IsIFrame;                                 //�Ƿ�I֡
  u16 FrameID;                                  //֡����,��0 ��ʼ,��65535���ܶ���?
  union {
    u32 PrevIFramePos;                         //ǰһ��I֡�ļ�ָ�룬�����ļ��д�������������
    i32 StreamType;                              //�����˫�������ֳ��� 0Ϊ������ 1Ϊ������ add at 2009/09/02
  };
}TFrameInfo;

typedef struct TDataFrameInfo { //¼Ӱ�ļ�����֡ͷ  24 u8
  THeadPkt Head;
  TFrameInfo Frame;
}TDataFrameInfo;
//-----------------------------------------------------------------------------
typedef struct TMotionInfoPkt { //�ƶ���ֵⷧ  sizeof 4
  u8 AreaIndex;
  u8 ActiveNum;
  u8 Sensitive;
  u8 Tag;
}TMotionInfoPkt;
//-----------------------------------------------------------------------------
typedef struct TGPIOStatusPkt {  //GPIOStatus sizeof 16
  THeadPkt Head;
  u64 DOStatus000_063;
}TGPIOStatusPkt;
//-----------------------------------------------------------------------------
//�������
#define ERR_FAIL           0
//#define ERR_OK             1
#define ERR_MAXUSERCONN    10001//�����û�����������趨
//-----------------------------------------------------------------------------
typedef struct TLoginPkt {                       //�û���¼�� sizeof 252->892
  char20 UserName;                               //�û�����
  char20 Password;                               //�û�����
  char20 DevIP;                                  //Ҫ���ӵ��豸IP,�� host
  i32 UserGroup;                                 //Guest=1 Operator=2 Administrator=3  
  i32 Reserved;                                 //����
  TDevInfoPkt DevInfoPkt;
  //2009-05-12 add begin
  TVideoFormat v[4];
  TAudioFormat a[4];
  //2009-05-12 add end
  //i32 Flag;//�����Ƿ����ߡ�0�����ߡ�1����
#define SENDFROM_CLIENT    1
#define SENDFROM_NVRMOBILE 0
  i32 SendFrom;// (x2 0=�ֻ�NVR 1=�ͻ��� )
}TLoginPkt;
//-----------------------------------------------------------------------------
typedef struct TLightCfgPkt1 { //sizeof 24
#define LIGHT_CONTROLTYPE_NULL    0
#define LIGHT_CONTROLTYPE_AUTO    1
#define LIGHT_CONTROLTYPE_MANUAL  2
#define LIGHT_CONTROLTYPE_TIMER   3
#define LIGHT_CONTROLTYPE_D2D     4
  u8 Mode;//auto=1 manual=2 timer=3 d2d=4
  u8 Flag[3];
  union {
    struct {
      u16 Delay;
      u8 Lux;
      u8 Brightness;
      u8 Reserved[4];
    }Auto;
    struct {
      u8 Brightness;
      u8 Reserved[7];
    }Manual;
    struct {
      u8 Brightness;
      u8 StartH;
      u8 StartM;
      u8 StopH;
      u8 StopM;
      u8 Reserved[3];
    }Timer;
    struct {
      u8 Brightness;
      u8 Flag;
      u8 Lux;
      u8 Reserved[5];
    }D2D;
  };
  u8 Reserved[12];
}TLightCfgPkt1;

typedef struct TLightCfgPkt { //sizeof 36
#define LIGHT_CONTROLTYPE_NULL    0
#define LIGHT_CONTROLTYPE_AUTO    1
#define LIGHT_CONTROLTYPE_MANUAL  2
#define LIGHT_CONTROLTYPE_TIMER   3
#define LIGHT_CONTROLTYPE_D2D     4
  struct {
    u8 Mode;//auto=1 manual=2 timer=3 d2d=4
    u8 Flag[3];

    struct {
      u16 Delay;
      u8 Lux;
      u8 Brightness;
      u8 Reserved[4];
    }Auto;
    struct {
      u8 Brightness;
      u8 Reserved[7];
    }Manual;
    struct {
      u8 Brightness;
      u8 StartH;
      u8 StartM;
      u8 StopH;
      u8 StopM;
      u8 Reserved[3];
    }Timer;
    struct {
      u8 Brightness;
      u8 Flag;
      u8 Lux;
      u8 Reserved[5];
    }D2D;
  };
}TLightCfgPkt;
//-----------------------------------------------------------------------------
typedef struct TPushCfgPkt {//sizeof 4
  u16 PushInterval;
  u8 PushActive;
  u8 PIRSensitive;
}TPushCfgPkt;
//-----------------------------------------------------------------------------
typedef enum TTestStep {
  ts_NULL,
  ts_TestSD,
  ts_TestNetwork,
  ts_TestWiFi,
  ts_TestVideo,
  ts_TestAudioCollect,
  ts_TestAudioPlay,
  ts_TestDI,
  ts_TestDO,
  ts_TestMCU,
  ts_TestOver,
}TTestStep;

typedef struct TTestPkt {//sizeof 12
  int IsTestMode;
  TTestStep TestStep;
  int Result;
}TTestPkt;
//-----------------------------------------------------------------------------
typedef struct TCmdPkt {                         //sizeof 1460-8
  u32 PktHead;                                 //��ͷУ���� =Head_CmdPkt 0xAAAAAAAA
  TMsgID MsgID;                                  //��Ϣ
  u32 Session;                                 //�����û���ɣ������������¼��ʱ��ֵΪ0�����ڷ��ص�¼����Session  //����Ϊ�����ڲ�ͨѶ��ʱ����ֵ����
  u32 Value;                                   //���Ի򷵻�ֵ 0 or 1 or ErrorCode
  union {
    char ValueStr[1460 - 4*4 - 8];
    struct TLoginPkt LoginPkt;                   //��¼��
    struct TPlayLivePkt LivePkt;                 //�����ֳ���
    struct TRecFilePkt RecFilePkt;               //�ط�¼Ӱ��
    struct TPTZPkt PTZPkt;                       //��̨����̨
    struct TRecFileLst RecFileLst;
    struct TPlayCtrlPkt PlayCtrlPkt;             //�ط�¼Ӱ���ư�

    struct TAlmSendPkt AlmSendPkt;               //�����ϴ���
    struct TDevInfoPkt DevInfoPkt;               //�豸��Ϣ��
    struct TNetCfgPkt NetCfgPkt;                 //�豸�������ð�
    struct TWiFiCfgPkt WiFiCfgPkt;               //�����������ð�
    struct TDiskCfgPkt DiskCfgPkt;               //�������ð�
    struct TUserCfgPkt UserCfgPkt;               //�û����ð�
    struct TRecCfgPkt RecCfgPkt;                 //¼Ӱ���ð�
    struct TMDCfgPkt MDCfgPkt;                   //�ƶ�����--��ͨ��
    //    struct TDiDoCfgPkt DiDoCfgPkt;               //DIDO���ð� 528
    struct TDoControlPkt DoControlPkt;           //DO���ư�    
    struct THideAreaCfgPkt HideAreaCfgPkt;       //����¼Ӱ�����--��ͨ��
    struct TAlmCfgPkt AlmCfgPkt;                 //�������ð�
    struct TVideoCfgPkt VideoCfgPkt;             //��Ƶ���ð�--��ͨ��
    struct TAudioCfgPkt AudioCfgPkt;             //��Ƶ���ð�--��ͨ��    
    struct TRecFileHead FileHead;                //ȡ���豸�ļ��ļ�ͷ��Ϣ
    struct TFilePkt FilePkt;                     //�ϴ��ļ���
    //struct TRS485CfgPkt RS485CfgPkt;             //485ͨ�Ű�--��ͨ��
    struct TColorsPkt Colors;                    //����ȡ�����ȡ��Աȶȡ�ɫ�ȡ����Ͷ�

    struct TMulticastInfoPkt MulticastInfo;      //�ಥ��Ϣ

    struct TFTPCfgPkt FTPCfgPkt;
    struct TSMTPCfgPkt SMTPCfgPkt;
    struct TWiFiSearchPkt WiFiSearchPkt[30];
    struct Tp2pCfgPkt p2pCfgPkt;
    struct TLightCfgPkt LightCfgPkt;
  };
}TCmdPkt;
//-----------------------------------------------------------------------------
typedef struct TNetCmdPkt {                      //���緢�Ͱ� sizeof 1460
  struct THeadPkt HeadPkt;
  struct TCmdPkt CmdPkt;
}TNetCmdPkt;
//-----------------------------------------------------------------------------

//*****************************************************************************
//*****************************************************************************
//*****************************************************************************
//*****************************************************************************
//*****************************************************************************
//*****************************************************************************
//*****************************************************************************
//*****************************************************************************
//*****************************************************************************
//*****************************************************************************
typedef struct TNewDevInfo {//sizeof 80
  char DevModal[8];                             //�豸�ͺ�  ='7xxx'
  u32 SN;
  char16 SoftVersion;                            //����汾
  char20 DevName;                                //�豸��ʶ
  u64 StandardMask;//16
  u64 SubStandardMask;//32
  u8 DevType;                                   //�豸����
  u8 ExistWiFi;
  u8 ExistSD;
  u8 IsEtherLink;      //���������Ƿ�����
  u8 wifiStatus;
  u8 upnpStatus;
  u8 IsConnWLAN;
  u8 p2pStatus;
  u8 HardType;      //Ӳ������
  signed char TimeZone;
  u8 DoubleStream;                              //�Ƿ�˫���� add at 2009/09/02
  u8 ReservedExistFlash;
  u8 SDKVersion;
  u8 Reserved[3];
}TNewDevInfo;

typedef struct TNewNetCfg{//sizeof 132
  u16 DataPort;                                   //�������ݶ˿�
  u16 rtspPort;                                  //rtsp�˿�
  u16 HttpPort;                                  //http��ҳ�˿�
  u8 IPType;
  u8 Reserved0;
  i32 DevIP;
  i32 SubMask;
  i32 Gateway;
  i32 DNS1;
  char20 DevMAC;
  u8 ActiveuPnP;
  u8 ActiveDDNS;
  u8 DDNSType;                               //0=3322.ORG 1=dynDNS.ORG 2=MyDDNS 3=9299.org
  u8 Reserved1;
  char40 DDNSDomain;                           //��DDNS SERVER IP
  char40 DDNSServer;
  i32 Reserved2;
}TNewNetCfg;

typedef struct TNewwifiCfg{//sizeof 92
  u8 ActiveWIFI;
  u8 WifiMode;//sta=0  ap=1
  u8 Reserved0;
  u8 Reserved1;
  char20 SSID_AP;
  char20 Password_AP;
  char20 SSID_STA;
  char20 Password_STA;
  u8 Channel;//Ƶ��1..14 default 1=Auto
  u8 EncryptType;//(Encrypt_None,Encrypt_WEP,Encrypt_WPA);
  u8 Auth;//WEP(0=AUTO 1=OPEN 2=SHARED)  WPA(0=AUTO 1=WPA-PSK 2=WPA2-PSK)
  u8 Enc;//WPA(0=AUTO 1=TKIP 2=AES)
  i32 Reserved2;
}TNewwifiCfg;

typedef struct TNewp2pCfg{//sizeof 64
  u8 ActiveP2P;
  u8 Version;//0 ������ 1 ������
  u8 p2pType;   //tutk=0 self=1
  char UID[21];
  char40 Reserved;
}TNewp2pCfg;

typedef struct TNewVideoCfg {//sizeof 16
  u8 StandardEx0;//TStandardEx
  u8 FrameRate0;                                //֡�� 1-30 MAX:PAL 25 NTSC 30
  u16 BitRate0;
  u8 StandardEx1;//TStandardEx
  u8 FrameRate1;//֡�� 1-30 MAX:PAL 25 NTSC 30
  u16 BitRate1;//���� 64K 128K 256K 512K 1024K 1536K 2048K 2560K 3072K
  u8 IsMirror;                           //ˮƽ��ת false or true
  u8 IsFlip;                             //��ֱ��ת false or true
  u8 IsShowFrameRate;
  u8 VideoType;                            //MPEG4=0x0000, MJPEG=0x0001  H264=0x0002
  u8 BitRateType0;                              //0������CBR 1������VBR
  u8 BitRateQuant0; 
  u8 BitRateType1;                              //0������CBR 1������VBR
  u8 BitRateQuant1; 
}TNewVideoCfg;

typedef struct TNewAudioCfg{//sizeof 12
  u8  ActiveAUDIO;                                   //�Ƿ���������
  u8 InputTypeAUDIO;                                 //0 MIC����, 1 LINE����
  u8 VolumeLineIn;
  u8 VolumeLineOut;
  u8 nChannels;                               //������=0 ������=1
  u8 wBitsPerSample;                          //number of bits per sample of mono data 
  u16 nSamplesPerSec;                          //������
  u8 wFormatTag;
  u8 Reserved[3];
}TNewAudioCfg;

typedef struct TNewUserCfg {//sizeof 128
  char20 UserName[3];                               //�û��� admin���ܸ���
  char20 Password[3];                               //����
  u8 Authority[3];                                 //3Ϊadmin
  u8 Reserved[5];
}TNewUserCfg;

typedef struct TNewDIAlm {//sizeof 12
  u8 ActiveDI;
  u8 Reserved0;
  u8 IsAlmRec;
  u8 IsFTPUpload;
  u8 ActiveDO;//DI����DOͨ�� false close
  u8 IsSendEmail;
  u8 Reserved1;// >0ΪԤ��λ
  u8 AlmOutTimeLen;//�������ʱ��(��) 0 ..255 s
  //struct TTaskhm hm;
  i32 Reserved2;
}TNewDIAlm;

typedef struct TNewMDAlm {//sizeof 28                       //�ƶ�����
  u8 ActiveMD;
  u8 Sensitive;                              //��������� 0-255
  u8 IsAlmRec;
  u8 IsFTPUpload;
  u8 ActiveDO;//DI����DOͨ�� false close
  u8 IsSendEmail;
  u8 Reserved2;
  u8 AlmOutTimeLen;                    //�������ʱ��(��) 0 ..255 s
  struct {
    float left,top,right,bottom;
  }Rect;
  i32 Reserved;
}TNewMDAlm;

typedef struct TNewSoundAlm{//sizeof 12
  u8 ActiveSoundTrigger;
  u8 Sensitive;
  u8 IsAlmRec;
  u8 IsFTPUpload;
  u8 ActiveDO;//DI����DOͨ�� false close
  u8 IsSendEmail;
  u8 Reserved1;// >0ΪԤ��λ
  u8 AlmOutTimeLen;                    //�������ʱ��(��) 0 ..255 s
  i32 Reserved2;
}TNewSoundAlm;

typedef struct TNewRecCfg{//sizeof 236                      //¼Ӱ���ð�
  u8 RecStreamType;//0 ������ 1 ������
  u8 IsRecAudio;//¼����Ƶ ��û���õ�
  u8 RecStyle;//¼Ӱ����
  u8 Reserved;
  TPlanRecPkt Plan;
  u16 Rec_AlmTimeLen;//����¼Ӱʱ��        10 s
  u16 Rec_NmlTimeLen;//һ��¼Ӱ�ָ�ʱ��   600 s
  i32 Reserved1;
}TNewRecCfg;

typedef struct TNewExtractCfg{// sizeof 188 //20171219
  u32 VerifyCode;                      //0xAAAAAAAA
  char60 DevName;                      //�豸��ʶ
  u32 DiskSize;                        // M ReadOnly
  u32 FreeSize;                        //72
  u8 PlatformType;
  u8 IsAudioGain;
  u8 AudioGainLevel;
  u8 IRCutSensitive;                   //76
  u8 HideAreaActive;
  u8 HideAreaLeft;
  u8 HideAreaTop;
  u8 HideAreaRight;                    //80
  u8 HideAreaBottom;
  u8 VideoTypeMask;
  u8 Standard;
  u8 IPInterval;                       //84
  u8 Brightness;
  u8 Contrast;
  u8 Hue;
  u8 Saturation;                       //88
  u8 Sharpness; 
  u8 BrightnessNight;
  u8 ContrastNight;
  u8 HueNight;                         //92
  u8 SaturationNight; 
  u8 SharpnessNight;
  u8 IsWDR;
  u8 WDRLevel;                         //96
  u8 IsShowTime;
  u8 IsShowTitle;
  u8 IsShowFrameRate;
  u8 IsShowLogo;                     //100
  u8 TimeX;                            //0=0.00 100=1.00
  u8 TimeY;
  u8 TitleX;
  u8 TitleY;                           //104
  u8 FrameRateX;
  u8 FrameRateY;
  u8 LogoX;
  u8 LogoY;                            //108
  u8 IsOsdTransparent;
  u8 IsOSDBigFont;                   //110
  u8 IsNewSendStyle;                 //111
  u8 MaxUserConn;                      //112 ����û������� default 10
  struct {
    u8 w;//TTaskDayType;
    u8 start_h;//ʱ 0-23
    u8 start_m;//�� 0-59
    u8 Days;
  }RebootHM;                           //116
  i32 ProcRunningTime;                 //120
  u8 OEMType;                          //121
  u8 Reserved[67];                     //188
}TNewExtractCfg;

typedef struct TNewDevCfg {//812->1000 || 952->1000
  union {
    char ValueStr[1000];
    struct {
      struct TNewDevInfo DevInfo;//sizeof 80
      struct TNewNetCfg NetCfg;//sizeof 132
      struct TNewwifiCfg wifiCfg;//sizeof 92
      struct TNewp2pCfg p2pCfg;//sizeof 64
      struct TNewVideoCfg VideoCfg;//sizeof 16
      struct TNewAudioCfg AudioCfg;//sizeof 12
      struct TNewUserCfg UserCfg;//sizeof 128
      struct TNewDIAlm DIAlm;//sizeof 12
      struct TNewMDAlm MDAlm;//sizeof 28
      struct TNewSoundAlm SoundAlm;//sizeof 12
      struct TNewRecCfg RecCfg;//sizeof 236
      struct TNewExtractCfg ExtractCfg;//sizeof 188
    };
  };
}TNewDevCfg;

typedef struct TNewCmdPkt {//maxsize sizeof 1008
  u32 VerifyCode;//У���� = 0xAAAAAAAA
  u8 MsgID;//TMsgID
  u8 Result;
  u16 PktSize;
  union {
    i32 Value;
    char Buf[1000];
    struct TNewDevCfg NewDevCfg;

    struct TLoginPkt LoginPkt;                   //��¼�� 892
    struct TPlayLivePkt LivePkt;                 //�����ֳ���
    struct TRecFilePkt RecFilePkt;               //�ط�¼Ӱ��
    struct TPTZPkt PTZPkt;                       //��̨����̨
    struct TPlayCtrlPkt PlayCtrlPkt;             //�ط�¼Ӱ���ư�

    struct TAlmSendPkt AlmSendPkt;               //�����ϴ���
    struct TDevInfoPkt DevInfoPkt;               //�豸��Ϣ��
    struct TNetCfgPkt NetCfgPkt;                 //�豸�������ð�
    struct TWiFiCfgPkt WiFiCfgPkt;               //�����������ð�
    struct TDiskCfgPkt DiskCfgPkt;               //�������ð� 888
    struct TRecCfgPkt RecCfgPkt;                 //¼Ӱ���ð�
    struct TMDCfgPkt MDCfgPkt;                   //�ƶ�����--��ͨ��
    struct TDoControlPkt DoControlPkt;           //DO���ư�    
    struct THideAreaCfgPkt HideAreaCfgPkt;       //����¼Ӱ�����--��ͨ��
    struct TAlmCfgPkt AlmCfgPkt;                 //�������ð�
    struct TVideoCfgPkt VideoCfgPkt;             //��Ƶ���ð�--��ͨ��
    struct TAudioCfgPkt AudioCfgPkt;             //��Ƶ���ð�--��ͨ��    
    struct TRecFileHead FileHead;                //ȡ���豸�ļ��ļ�ͷ��Ϣ
    struct TFilePkt FilePkt;                     //�ϴ��ļ���
    struct TFTPCfgPkt FTPCfgPkt;
    struct TSMTPCfgPkt SMTPCfgPkt;
    struct Tp2pCfgPkt p2pCfgPkt;
    struct TLightCfgPkt LightCfgPkt;
  };  
}TNewCmdPkt;

//#pragma option pop //end C++Builder enum 4 u8

#endif //end Ax_protocol_H
