//-----------------------------------------------------------------------------
// Author      : ��첨
// Date        : 2017.09.14
// Version     : V 2.00
// Description : www.southipcam.com
//-----------------------------------------------------------------------------
#ifndef thPlayaxdll_H
#define thPlayaxdll_H

#include "../include/th_protocol.h"
#include "../include/TFun.h"

#ifdef __cplusplus
extern "C" {
#endif
#pragma pack(4)//n=1,2,4,8,16

  //-----------------------------------------------------------------------------
  typedef struct Twifi_sta_item {//sizeof 100
    u8 EncryptType;//(Encrypt_None,Encrypt_WEP,Encrypt_WPA);
    u8 wepAuth;
    u8 wpaAuth;
    u8 wpaEnc;
    char32 SSID;
    char64 Password;
  }Twifi_sta_item;
  //-----------------------------------------------------------------------------
#define MAX_WIFICFG_LST_COUNT  8
  typedef struct TwifiCfgLst {//sizeof 804
    i32 Count;
    struct Twifi_sta_item Lst[MAX_WIFICFG_LST_COUNT];
  }TwifiCfgLst;
  //-----------------------------------------------------------------------------
  typedef struct TDevCfg {//sizeof x1 5216
    struct TNetCfgPkt NetCfgPkt;                            //�豸�������ð�sizeof 372
    struct TWiFiCfgPkt WiFiCfgPkt;                          //�������ð� sizeof 200
    struct TDevInfoPkt DevInfoPkt;                          //�豸��Ϣ��sizeof 180
    struct TUserCfgPkt UserCfgPkt;                          //sizeof 1048
    struct TAlmCfgPkt AlmCfgPkt;                            //�������ð�sizeof 52//
    char Reserved[1024];
    struct TVideoCfgPkt VideoCfgPkt;                        //��Ƶ���ð� sizeof 148
    struct TAudioCfgPkt AudioCfgPkt;                        //��Ƶ���ð� sizeof 48
    struct TMDCfgPkt MDCfgPkt;                              //�ƶ����� sizeof 96
    struct THideAreaCfgPkt HideAreaCfgPkt;                  //����¼Ӱ����� sizeof 72
    struct TDiskCfgPkt DiskCfgPkt;                          //888 -> 60
    struct TwifiCfgLst wifiCfgLst;                          //804  add at 20140927
    char Reserved1[24];
    struct TRecCfgPkt RecCfgPkt;                            //¼Ӱ���ð� sizeof 260
    struct TFTPCfgPkt FTPCfgPkt;                            //sizeof 232
    struct Tp2pCfgPkt p2pCfgPkt;                            //sizeof 88
    struct TSMTPCfgPkt SMTPCfgPkt;                          //sizeof 500
    i32 Flag;
    i32 Flag1;
  }TDevCfg;

#define dm_GetDiskSpaceInfo(S) (true)

char* cgi_Get_VideoType(int Value);
char* cgi_Get_Standard(int Value);
bool GetWidthHeightFromStandard(i32 Value, i32* w, i32* h);//��TStandardȡ�ó�����
i32 GetStandardFromWidthHeight(i32 w, i32 h);

EXPORT bool DevCfg_to_NewDevCfg(TDevCfg* DevCfg, TNewDevCfg* NewDevCfg);
EXPORT bool NewDevCfg_to_DevCfg(TNewDevCfg* NewDevCfg, TDevCfg* DevCfg);
EXPORT char* DevCfg_to_Json(TDevCfg* DevCfg);
EXPORT char* NewDevCfg_to_Json(TNewDevCfg* NewDevCfg);

#ifdef __cplusplus
}
#endif

#endif
