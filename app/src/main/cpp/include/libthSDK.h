//-----------------------------------------------------------------------------
// Author      : ��첨
// Date        : 2017.09.14
// Version     : V 2.00
// Description : www.southipcam.com
//-----------------------------------------------------------------------------
#ifndef libthSDK_h
#define libthSDK_h

#include "cm_types.h"

#ifdef __cplusplus
extern "C"
{
#endif

#pragma pack(4)//n=1,2,4,8,16

//*****************************************************************************
//-----------------------------------------------------------------------------
EXPORT HANDLE thNet_Init(bool IsInsideDecode, bool IsQueue, bool IsAdjustTime, bool IsAutoReConn, u32 SN);
/*-----------------------------------------------------------------------------
������������ʼ�����粥��
����˵����
IsInsideDecode:�Ƿ�SDK�ڲ�������ʾ
IsQueue:�Ƿ񻺴����
IsAdjustTime:�Ƿ�У׼�豸ʱ��Ϊ�ͻ���ʱ��
�� �� ֵ��NetHandle:����������
------------------------------------------------------------------------------*/

//-----------------------------------------------------------------------------
typedef void(TvideoCallBack)(void *UserCustom,         //�û��Զ�������
                             i32 Chl, char *Buf,                //����Ƶ����ǰ֡����
                             i32 Len,                  //���ݳ���
                             int IsIFrame);

typedef void(TaudioCallBack)(void *UserCustom,         //�û��Զ�������
                             i32 Chl, char *Buf,                //����Ƶ����ǰ֡����
                             i32 Len                   //���ݳ���
);

typedef void(TalarmCallBack)(i32 AlmType,             //�������ͣ��μ�TAlmType
                             i32 AlmTime,             //����ʱ��time_t
                             i32 AlmChl,              //����ͨ��
                             void *UserCustom         //�û��Զ�������
);


#define Decode_None   0
#define Decode_IFrame 1
#define Decode_All    2
EXPORT bool thNet_SetDecodeStyle(HANDLE NetHandle, int DecodeStyle);//δ��
/*-----------------------------------------------------------------------------
����������������Ƶ���ݽ��뷽ʽ���Խ�ʡCPUռ����
����˵����
NetHandle:����������thNet_Init����
DecodeStyle:���뷽ʽ,Decode_None=0 Decode_IFrame=1 Decode_All=2
Decode_None:  ����������
Decode_IFrame:ֻ��������I֡
Decode_All:   ������������֡
�� �� ֵ���ɹ�����true��ʧ�ܷ���false
------------------------------------------------------------------------------*/
EXPORT bool
thNet_SetCallBack(HANDLE NetHandle, TvideoCallBack videoEvent, TaudioCallBack audioEvent, TalarmCallBack AlmEvent,
                  void *UserCustom);
/*-----------------------------------------------------------------------------
�������������粥�����ûص������� �ڵ���thNet_Connect֮ǰ����
����˵����
NetHandle:����������thNet_Init����
avEvent:��Ƶ��Ƶ���ݻص�����
AlmEvent:�豸�����ص�����
UserCustom:�û��Զ�������
�� �� ֵ���ɹ�����true��ʧ�ܷ���false
------------------------------------------------------------------------------*/
EXPORT bool thNet_Free(HANDLE NetHandle);
/*-----------------------------------------------------------------------------
�����������ͷ����粥��
����˵����
NetHandle:����������thNet_Init����
�� �� ֵ���ɹ�����true��ʧ�ܷ���false
------------------------------------------------------------------------------*/
EXPORT bool thNet_Connect(HANDLE NetHandle, char *UserName, char *Password, char *IPUID, i32 DataPort, u32 TimeOut);
/*-----------------------------------------------------------------------------
�������������������豸
����˵����
NetHandle:����������thNet_Init����
UserName:�����ʺ�
Password:��������
IPUID:�豸IP������ ���� P2P UID
DataPort:�豸��ת���������˿�
TimeOut:���ӳ�ʱ����λms,ȱʡ 3000ms
�� �� ֵ���ɹ�����true
ʧ�ܷ���false
------------------------------------------------------------------------------*/
EXPORT bool thNet_DisConn(HANDLE NetHandle);
/*-----------------------------------------------------------------------------
�����������Ͽ������豸����
����˵����
NetHandle:����������thNet_Init����
�� �� ֵ���ɹ�����true��ʧ�ܷ���false
------------------------------------------------------------------------------*/
EXPORT bool thNet_IsConnect(HANDLE NetHandle);

EXPORT bool thNet_SendSensePkt(HANDLE NetHandle);

#define THNET_CONNSTATUS_NO       0
#define THNET_CONNSTATUS_CONNING  1
#define THNET_CONNSTATUS_SUCCESS  2
#define THNET_CONNSTATUS_FAIL     3
EXPORT int thNet_GetConnectStatus(HANDLE NetHandle);
/*-----------------------------------------------------------------------------
�����������豸�Ƿ�����
����˵����
NetHandle:����������thNet_Init����
�� �� ֵ���ɹ�����true��ʧ�ܷ���false
------------------------------------------------------------------------------*/
EXPORT bool thNet_Play(HANDLE NetHandle, u32 VideoChlMask, u32 AudioChlMask, u32 SubVideoChlMask);
/*-----------------------------------------------------------------------------
������������ʼ����
����˵����
NetHandle:����������thNet_Init����
VideoChlMask:ͨ�����룬
bit: 31 .. 19 18 17 16   15 .. 03 02 01 00
0  0  0  0          0  0  0  1
AudioChlMask:ͨ�����룬
bit: 31 .. 19 18 17 16   15 .. 03 02 01 00
0  0  0  0          0  0  0  1
SubVideoChlMask:������ͨ������
bit: 31 .. 19 18 17 16   15 .. 03 02 01 00
0  0  0  0          0  0  0  1
�� �� ֵ���ɹ�����true��ʧ�ܷ���false
------------------------------------------------------------------------------*/
EXPORT bool thNet_Stop(HANDLE NetHandle);
/*-----------------------------------------------------------------------------
����������ֹͣ����
����˵����
NetHandle:����������thNet_Init����
�� �� ֵ���ɹ�����true��ʧ�ܷ���false
------------------------------------------------------------------------------*/
EXPORT bool thNet_IsPlay(HANDLE NetHandle);
/*-----------------------------------------------------------------------------
�����������豸�Ƿ񲥷�
����˵����
NetHandle:����������thNet_Init����
�� �� ֵ���ɹ�����true��ʧ�ܷ���false
------------------------------------------------------------------------------*/
EXPORT char *thNet_GetAllCfg(HANDLE NetHandle);
/*-----------------------------------------------------------------------------
������������ȡ��������
����˵����
NetHandle:����������thNet_Init����
�� �� ֵ��
------------------------------------------------------------------------------*/
EXPORT bool thNet_TalkOpen(HANDLE NetHandle);
/*-----------------------------------------------------------------------------
������������ʼ�Խ�
����˵����
NetHandle:����������thNet_Init����
�� �� ֵ���ɹ�����true��ʧ�ܷ���false
------------------------------------------------------------------------------*/
EXPORT bool thNet_TalkClose(HANDLE NetHandle);

EXPORT bool net_SetTalk(HANDLE NetHandle, char *Buf, int Len);//old
/*-----------------------------------------------------------------------------
����������ֹͣ�Խ�
����˵����
NetHandle:����������thNet_Init����
�� �� ֵ���ɹ�����true��ʧ�ܷ���false
------------------------------------------------------------------------------*/
typedef void(TSearchDevCallBack)(void *UserCustom,  //�û��������
                                 u32 SN,            //���к�
                                 i32 DevType,       //�豸����
                                 char *DevModal,    //�豸�ͺ�
                                 char *SoftVersion, //����汾
                                 i32 DataPort,      //���ݶ˿�
                                 i32 HttpPort,      //http�˿�
                                 i32 rtspPort,      //rtsp�˿�
                                 char *DevName,     //�豸����
                                 char *DevIP,       //�豸IP
                                 char *DevMAC,      //�豸MAC��ַ
                                 char *SubMask,     //�豸��������
                                 char *Gateway,     //�豸����
                                 char *DNS1,        //�豸DNS
                                 char *DDNSServer,  //DDNS��������ַ
                                 char *DDNSHost,    //DDNS����
                                 char *UID          //P2P��ʽUID
);

EXPORT HANDLE thSearch_Init(TSearchDevCallBack SearchEvent, void *UserCustom);
/*-----------------------------------------------------------------------------
������������ʼ����ѯ�豸
����˵����
SearchEvent:��ѯ�豸�ص�����
�� �� ֵ���ɹ�����true��ʧ�ܷ���false
------------------------------------------------------------------------------*/
EXPORT bool thSearch_SetWiFiCfg(HANDLE SearchHandle, char *SSID, char *Password);

EXPORT bool thSearch_SearchDevice(HANDLE SearchHandle);
/*-----------------------------------------------------------------------------
������������ʼ��ѯ�豸
LocalIP:����ı���IP��ȱʡΪNULL
�� �� ֵ���ɹ�����true��ʧ�ܷ���false
------------------------------------------------------------------------------*/
EXPORT bool thSearch_Free(HANDLE SearchHandle);
/*-----------------------------------------------------------------------------
�����������ͷŲ�ѯ�豸
�� �� ֵ���ɹ�����true��ʧ�ܷ���false
------------------------------------------------------------------------------*/

//*****************************************************************************
EXPORT bool thNet_RemoteFilePlay(HANDLE NetHandle, char *FileName);
/*-----------------------------------------------------------------------------
������������ʼ����Զ���ļ�
����˵����
NetHandle:����������thNet_Init����
FileName:�����Զ��¼���ļ���
�� �� ֵ���ɹ�����true��ʧ�ܷ���false
------------------------------------------------------------------------------*/
EXPORT bool thNet_RemoteFileStop(HANDLE NetHandle);
/*-----------------------------------------------------------------------------
����������ֹͣ����Զ���ļ�
����˵����
NetHandle:����������thNet_Init����
�� �� ֵ���ɹ�����true��ʧ�ܷ���false
------------------------------------------------------------------------------*/
EXPORT bool thNet_RemoteFilePlayControl(HANDLE NetHandle, i32 PlayCtrl, i32 Speed, i32 Pos);
/*-----------------------------------------------------------------------------
����������Զ���ļ����ſ���
����˵����
NetHandle:����������thNet_Init����
PlayCtrl:   PS_None               =0,                 //��
PS_Play               =1,                 //����
PS_Pause              =2,                 //��ͣ
PS_Stop               =3,                 //ֹͣ
PS_FastBackward       =4,                 //����
PS_FastForward        =5,                 //���
PS_StepBackward       =6,                 //����
PS_StepForward        =7,                 //����
PS_DragPos            =8,                 //�϶�
Speed:���PlayCtrl=PS_StepBackward, PS_FastForward ���򱣴������˱��� 1 2 4 8 16 32����
Pos:���PlayCtrl=PS_DragPos���򱣴��ļ��ļ�λ��Pos
�� �� ֵ���ɹ�����true��ʧ�ܷ���false
------------------------------------------------------------------------------*/
EXPORT int thNet_RemoteFileGetPosition(HANDLE NetHandle);//ʱ�������λms

EXPORT bool thNet_RemoteFileSetPosition(HANDLE NetHandle, int Pos);//ʱ�������λms

EXPORT int thNet_RemoteFileGetDuration(HANDLE NetHandle);//�ļ����ȣ���λms

EXPORT bool thNet_AudioPlayOpen(HANDLE NetHandle);

EXPORT bool thNet_AudioPlayClose(HANDLE NetHandle);

EXPORT bool thNet_SetAudioIsMute(HANDLE NetHandle, int IsAudioMute);

/*-----------------------------------------------------------------------------
�����������Ƿ���
����˵����
NetHandle:����������thNet_Init����
IsAudioMute:�Ƿ���
�� �� ֵ���ɹ�����true��ʧ�ܷ���false
------------------------------------------------------------------------------*/
typedef struct TDspInfo
{//Player��ʾ��Ϣ
  HWND DspHandle;//��ʾ���ھ��
  int Channel;//ͨ��
  TRect DspRect;//��ʾ����
} TDspInfo;

EXPORT bool thNet_AddDspInfo(HANDLE NetHandle, TDspInfo *PDspInfo);
/*-------------------------------------------------------------------------------
��������������һ����������ͬһ��ƵԴ��������������
����˵����
NetHandle:����������thNet_Init����
PInfo:������ʾ��Ϣָ��
DspHandle:hWnd;//��ʾ���ھ��
Channel:Int;//ͨ����ͨ����0��ʼ
DspRect:TRect;//��ʾ����
�� �� ֵ���ɹ�����true��ʧ�ܷ���false
-------------------------------------------------------------------------------*/
EXPORT bool thNet_DelDspInfo(HANDLE NetHandle, TDspInfo *PDspInfo);
/*-------------------------------------------------------------------------------
����������ɾ��һ����������
����˵����
NetHandle:����������thNet_Init����
PInfo:������ʾ��Ϣָ��
DspHandle:hWnd;//��ʾ���ھ��
Channel:Int;//ͨ����ͨ����0��ʼ
DspRect:TRect;//��ʾ����
�� �� ֵ���ɹ�����true��ʧ�ܷ���false
-------------------------------------------------------------------------------*/
EXPORT bool thNet_HttpGet(HANDLE NetHandle, char *url, char *Buf, i32 *BufLen);

EXPORT bool thNet_HttpGetStop(HANDLE NetHandle);

EXPORT bool thNet_SetRecPath(HANDLE NetHandle, char *RecPath);//linux ��"/"������windows�� "\"����
EXPORT bool thNet_StartRec(HANDLE NetHandle, char *RecFileName/*RecFileName=NULL,���thNet_SetRecPathʹ��*/);

EXPORT bool thNet_IsRec(HANDLE NetHandle);

EXPORT bool thNet_StopRec(HANDLE NetHandle);

EXPORT bool thNet_SetJpgPath(HANDLE NetHandle, char *JpgPath);//linux ��"/"������windows�� "\"����
EXPORT bool thNet_SaveToJpg(HANDLE NetHandle, char *JpgFileName/*JpgFileName=NULL,���thNet_SetJpgPathʹ��*/);
/*-------------------------------------------------------------------------------
��������������ΪJPGͼƬ
����˵����
NetHandle:����������thNet_Init����
FileName:�ļ����������·��
�� �� ֵ���ɹ�����true��ʧ�ܷ���false
-------------------------------------------------------------------------------*/
EXPORT bool P2P_Init();

EXPORT bool P2P_Free();
//FileSize = strlen(Str)*2 * (2000(125ms) + 1200(75ms))
EXPORT bool TextToDTMFWavFile(char *txt, int len, char *WavFileName, int iVolumn/*0~100*/, int iDelayms/*=10ms*/);

EXPORT bool DTMFWavFileToText(char *FileName, char *txt);

EXPORT bool DTMFBufToText(char *Buf, int BufLen, int iSample/*=8000*/, int iBits/*=16*/, char *txt);

#if defined(ANDROID)
EXPORT bool th_OpenGLRenderRGB565(HANDLE NetHandle);

EXPORT bool th_OpenGLCreateEGL(HANDLE NetHandle, void *Window);

EXPORT bool th_OpenGLFreeEGL(HANDLE NetHandle);
EXPORT bool thNet_IsVideoDecodeSuccess(HANDLE NetHandle);

#endif
EXPORT bool thManage_AddDevice(u32 SN, HANDLE NetHandle);

EXPORT bool thManage_DelDevice(u32 SN);

EXPORT bool thManage_DisconnFreeAll();

EXPORT bool thManage_NetworkSwitch(int NetWorkType);//TYPE_NONE=-1 TYPE_MOBILE=0 TYPE_WIFI=1
EXPORT bool thManage_ForeBackgroundSwitch(int IsForeground);//Background=0 Foreground=1

#ifdef __cplusplus
}
#endif

#endif 
