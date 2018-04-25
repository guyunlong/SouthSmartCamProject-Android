//-----------------------------------------------------------------------------
// Author      : ��첨
// Date        : 2017.09.14
// Version     : V 2.00
// Description : www.southipcam.com
//-----------------------------------------------------------------------------
unit libthSDK;

interface

uses
  Windows,Messages;


type
  Int=Integer;
{$DEFINE IS_MP_INIT}
//------------------------------------------------------------------------------
const
  CODEC_NONE=0;//AV_CODEC_ID_NONE
  CODEC_MJPEG=8;//AV_CODEC_ID_MJPEG
  CODEC_MPEG4=13;//AV_CODEC_ID_MPEG4
  CODEC_H264=28;//AV_CODEC_ID_H264
  CODEC_H265=174;//AV_CODEC_ID_H265 AV_CODEC_ID_HEVC
  CODEC_PCM=$10000;//AV_CODEC_ID_PCM_S16LE
  CODEC_G711=$10007;//AV_CODEC_ID_PCM_ALAW
  CODEC_AAC=$15002;//AV_CODEC_ID_AAC

const
  AV_PIX_FMT_YUV420P=0;
  AV_PIX_FMT_RGB565=43;
  AV_PIX_FMT_BGR24=3;
  AV_PIX_FMT_BGR32=30;


type
  PAVPicture=^TAVPicture;
  TAVPicture=packed record
    Data:array[0..7] of PChar;//PByte;
    linesize:array[0..7] of Int;// number of bytes per line
  end;

type
  PencParam=^TencParam;
  TencParam=record
    VideoType:Int;//MPEG4=0, MJPEG=1  H264=2
    FrameRate:Int;//֡�� 1-30 MAX:PAL 25 NTSC 30
    IPIntegererval:Int;//IP֡��� 1-120 default 30
    BitRate:Int;//���� 64K 128K 256K 512K 1024K 1536K 2048K 2560K 3072K

    yuvbuf:PChar;//����������
    encWidth:Int;//�� 720 360 180 704 352 176 640 320 160
    encHeight:Int;//�� 480 240 120 576 288 144
    encBuf:PChar;//����������

    encBufSize:Int;//ָ��encBuf ��С
    encLen:Int;//��������ݴ�С
    IsKeyFrame:Int;//�Ƿ�I֡
    ForceKeyFrame:Int;//Ϊ1ʱ��ǿ�Ʊ����I֡
  end;

//-----------------------------------------------------------------------------
const
  LibName='libthSDK.dll';
//-----------------------------------------------------------------------------
function g711_encode(DstBuf:PChar;const SrcBuf:PChar;srcBufLen:Int):Int;cdecl;external LibName;
function g711_decode(DstBuf:PChar;const SrcBuf:PChar;srcBufLen:Int):Int;cdecl;external LibName;
function pcm_DB(Buf:PChar;Len:Int):Int;cdecl;external LibName;
//-----------------------------------------------------------------------------
function thImgConvertFill(Picture:PAVPicture;Buf:PChar;fmt,Width,Height:Int):Int;cdecl;external LibName;
function thImgConvertScale(d:PChar;dfmt:Int;dw,dh:Int;s:PChar;sfmt:Int;sw,sh:Int;IsFlip:Int=0):Boolean;cdecl external LibName;
function thImgConvertScale1(Dst:PAVPicture;dfmt:Int;dw,dh:Int;Src:PAVPicture;sfmt:Int;sw,sh:Int;IsFlip:Int=0):Boolean;cdecl external LibName;
function thImgConvertScale2(d:PChar;dfmt:Int;dw,dh:Int;s:PAVPicture;sfmt:Int;sw,sh:Int;IsFlip:Int=0):Boolean;cdecl external LibName;
//-----------------------------------------------------------------------------
function thEncodeVideoInit(var Param:TencParam):THandle;cdecl external LibName;
function thEncodeVideoFrame(encHandle:THandle;Param:PencParam):Boolean;cdecl external LibName;
function thEncodeVideoFree(encHandle:THandle):Boolean;cdecl external LibName;
//-----------------------------------------------------------------------------
function thRecordStart(FileName:PChar;VideoType,Width,Height,FrameRate,AudioType,nChannels,nSamplesPerSec,wBitsPerSample:Int):THandle;cdecl external LibName;
function thRecordWriteVideo(RecHandle:THandle;Buf:PChar;Len:Int;pts:Int64):Boolean;cdecl external LibName;//pts = -1 Ϊ�Զ�����
function thRecordWriteAudio(RecHandle:THandle;Buf:PChar;Len:Int;pts:Int64):Boolean;cdecl external LibName;//pts = -1 Ϊ�Զ�����
function thRecordIsRec(RecHandle:THandle):Boolean;cdecl external LibName;
function thRecordStop(RecHandle:THandle):Boolean;cdecl external LibName;
//-----------------------------------------------------------------------------
function thDecodeVideoInit(VideoType:Int):THandle;cdecl external LibName;
function thDecodeVideoFrame(decHandle:THandle;encBuf:PChar;encLen:Int;var decWidth,decHeight:Int;avPicture:PAVPicture):Boolean;cdecl external LibName;
function thDecodeVideoFree(decHandle:THandle):Boolean;cdecl external LibName;
//-----------------------------------------------------------------------------
const
  AV_QUEUE_COUNT=60;

type
  PavNode=^TavNode;
  TavNode=record
    Buf:PChar;
    BufLen:Int;
    Buf1:PChar;
    BufLen1:Int;
  end;

function avQueue_Init(iQueueNum:Int;IsMallocMemory:Boolean=false;IsMallocMemory1:Boolean=false):THandle;cdecl external LibName;
function avQueue_GetCount(h:THandle):Int;cdecl external LibName;
function avQueue_Write(h:THandle;Buf:PChar;BufLen:Int;Buf1:PChar=nil;BufLen1:Int=0):PavNode;cdecl external LibName;
function avQueue_ReadBegin(h:THandle):PavNode;cdecl external LibName;
procedure avQueue_ReadEnd(h:THandle;tmpNode:PavNode);cdecl external LibName;
procedure avQueue_ClearAll(h:THandle);cdecl external LibName;
procedure avQueue_Free(h:THandle);cdecl external LibName;

//-----------------------------------------------------------------------------
function thDDraw_Init(iDDrawMode:Int):THandle;cdecl external LibName;
function thDDraw_Free(Handle:THandle):Boolean;cdecl external LibName;
function thDDraw_InitPrimaryScreen(Handle:THandle):Boolean;cdecl external LibName;
function thDDraw_FreePrimaryScreen(Handle:THandle):Boolean;cdecl external LibName;
function thDDraw_InitOffScreen(Handle:THandle):Boolean;cdecl external LibName;
function thDDraw_FreeOffScreen(Handle:THandle):Boolean;cdecl external LibName;
function thDDraw_SetDspHandle(Handle:THandle;DspHandle:hWnd):Boolean;cdecl external LibName;
function thDDraw_ShowText(Handle:THandle;Text:PChar;x,y,FontSize,Color:Int):Boolean;cdecl external LibName;
function thDDraw_ShowPicture(Handle:THandle;x,y:Int;Text:PChar;Color:Int):Boolean;cdecl external LibName;
function thDDraw_SaveToBmp(Handle:THandle;AFileName:PChar):Boolean;cdecl external LibName;
function thDDraw_SaveToJpg(Handle:THandle;AFileName:PChar):Boolean;cdecl external LibName;
function thDDraw_GetDC(Handle:THandle):hdc;cdecl external LibName;
function thDDraw_ReleaseDC(Handle:THandle;dc:hdc):Boolean;cdecl external LibName;
function thDDrawGetlpSurface(Handle:THandle):Pointer;cdecl external LibName;
function thDDraw_FillMem(Handle:THandle;FrameV:TAVPicture;w,h:Int):Boolean;cdecl external LibName;
function thDDraw_Display(Handle:THandle;DspHandle:hWnd;DspRect:TRect):Boolean;cdecl external LibName;
function thDDrawGetDeviceBitsPixel(Handle:THandle):Int;cdecl external LibName;
//-----------------------------------------------------------------------------

const
  Msg_ReadOver=WM_USER+$1000;
  Msg_PlayOver=WM_USER+$1001;
  Msg_Video=WM_USER+$1002;
  Msg_Audio=WM_USER+$1003;
  Msg_HasVideoData=WM_USER+$1004;
  Msg_Connect=WM_USER+$1005;
  Msg_DisConn=WM_USER+$1006;
  Msg_ReConn=WM_USER+$1007;
  Msg_Load=WM_USER+$1008;
  Msg_Unload=WM_USER+$1009;

type
  TPlayStatus=(
    PS_None=0,//��
    PS_Play=1,//����
    PS_Pause=2,//��ͣ
    PS_Stop=3,//ֹͣ
    PS_Seek=4,//seek
    PS_Close=5//�ر�
    );

type
  TStreamStatus=(
    st_ReadOver,
    st_PlayOver,
    st_Video,
    st_Audio
    );

  PavFormatInfo=^TavFormatInfo;
  TavFormatInfo=record ////sizeof 624 ����packet record
    VideoCodecID:Int;
    FrameRate:Int;
    BitRate:Int;
    Gop:Int;
    Width:Int;
    Height:Int;

    AudioCodecID:Int;
    nSamplesPerSec:Int;//������
    nChannels:Int;//������=0 ������=1
    wBitsPerSample:Int;//8, 16

    VideoHandle:THandle;

    Duration:Int64;
    TimeStamp:Int64;

    videoBuf:PChar;
    videoBufLen:Int;
    videoPts:Int64;
    audioPts:Int64;
    audioBuf:PChar;
    audioBufLen:Int;

    NaluType:Int;//5 6 7 8 4
    Buf_extradataV:array[0..255] of Byte;
    Len_extradataV:Int;
    Buf_extradataA:array[0..255] of Byte;
    Len_extradataA:Int;

    Reserved:Int;//88
  end;

type
  TVideoShowModel=(
    vs_Stretched,//���쵽��ʾ����
    vs_LetterBox//���������ŵ���ʾ����
    );

type
  TavCallback=procedure(Handle:THandle;var avFormatInfo:TavFormatInfo;Status:TStreamStatus;UserData:Pointer);cdecl;
{$IFDEF IS_MP_INIT}
function MP_Init():THandle;cdecl external LibName;
function MP_Free(Handle:THandle):Int;cdecl external LibName;
function MP_LoadVideo(Handle:THandle;FileName:PChar;rtsp_any0_udp1_tcp2:Int;PlayHandle:THandle;ConnTimeOut:Int{ms};IsProcessVideo:Boolean;IsProcessAudio:Boolean):Boolean;cdecl external LibName;
{$ELSE}
function MP_LoadVideo(FileName:PChar;rtsp_any0_udp1_tcp2:Int;PlayHandle:THandle;ConnTimeOut:Int{ms};IsProcessVideo:Boolean;IsProcessAudio:Boolean):THandle;cdecl external LibName;
{$ENDIF}
function MP_SetCallback(Handle:THandle;avCallback:TavCallback;UserData:Pointer):Int;cdecl external LibName;
function MP_SetRect(Handle:THandle;PlayHandle:THandle;x,y,w,h:Int):Int;cdecl external LibName;
function MP_UnloadVideo(Handle:THandle;sleepmsClose:DWORD=300):Int;cdecl external LibName;
function MP_IsPauseDecode(Handle:THandle;IsPauseDecode:Boolean):Int;cdecl external LibName;
function MP_IsEof(Handle:THandle):Int;cdecl external LibName;

function MP_Play(Handle:THandle):Int;cdecl external LibName;
function MP_Pause(Handle:THandle):Int;cdecl external LibName;
function MP_Stop(Handle:THandle):Int;cdecl external LibName;

function MP_GetCurrentPosition(Handle:THandle;var Pos,total:Int64):Int;cdecl external LibName;
function MP_GetCurrentPlayStatus(Handle:THandle;var playStatus:TPlayStatus):Int;cdecl external LibName;
function MP_SeekToPosition(Handle:THandle;Pos:Int64):Int;cdecl external LibName;

function MP_SetPlaySpeed(Handle:THandle;speed:Int):Int;cdecl external LibName;

function MP_SetSyncReferenceTime(Handle:THandle;syncTime:Int64):Int;cdecl external LibName;

function MP_SetVolume(Handle:THandle;vol:Int):Int;cdecl external LibName;
function MP_GetVolume(Handle:THandle;var vol:Int):Int;cdecl external LibName;
function MP_SetMute(Handle:THandle;bMute:Boolean):Int;cdecl external LibName;

function MP_GetVideoShowModel(Handle:THandle;var VideoShowModel:Int):Int;cdecl external LibName;
function MP_SetVideoShowModel(Handle:THandle;VideoShowModel:Int):Int;cdecl external LibName;
function MP_Snapshot(Handle:THandle;FileName:PChar;TimeOut:Int):Int;cdecl external LibName;
function MP_BufLenTotal(Handle:THandle):DWORD;cdecl external LibName;

function MP_GetColor(Handle:THandle;var Brightness:Int;var Contrast:Int;var Saturation:Int):Int;cdecl external LibName;
function MP_SetColor(Handle:THandle;Brightness,Contrast,Saturation:Int):Int;cdecl external LibName;

const
  MEDIA_TYPE_NONE=0;//	δ֪����
  MEDIA_TYPE_AUDIO=1;//	������Ƶ
  MEDIA_TYPE_VIDEO=2;//	������Ƶ
  MEDIA_TYPE_MP_LIVE=3;//	RTSPֱ��
  MEDIA_TYPE_MP_VOD=4;//	RTSP�㲥
  MEDIA_TYPE_RTMP_LIVE=5;//	RTMPֱ��
  MEDIA_TYPE_RTMP_VOD=6;//	RTMP�㲥
  MEDIA_TYPE_HTTP_LIVE=7;//	HTTPֱ��
  MEDIA_TYPE_HTTP_VOD=8;//	HTTP�㲥

function MP_GetMediaType(Handle:THandle):Int;cdecl external LibName;



function thrtsp_Init():THandle;cdecl external LibName;
function thrtsp_Free(Handle:THandle):Int;cdecl external LibName;
function thrtsp_Connect(Handle:THandle;FileName:PChar;ConnTimeOut:Int{ms}):Boolean;cdecl external LibName;
function thrtsp_DisConn(Handle:THandle;sleepmsClose:DWORD=300):Int;cdecl external LibName;

function thrtsp_SetCallback(Handle:THandle;avCallback:TavCallback;UserData:Pointer):Int;cdecl external LibName;
type
  PDspInfo1=^TDspInfo1;//Player��ʾ��Ϣ
  TDspInfo1=packed record
    DspHandle:hWnd;//��ʾ���ھ��
    Channel:Int;//ͨ��
    DspRect:TRect;//��ʾ����
  end;

function thrtsp_AddDspInfo(Handle:THandle;PInfo:PDspInfo1):Int;cdecl external LibName;
function thrtsp_DelDspInfo(Handle:THandle;PInfo:PDspInfo1):Int;cdecl external LibName;


function thrtsp_IsPauseDecode(Handle:THandle;IsPauseDecode:Boolean):Int;cdecl external LibName;
function thrtsp_IsEof(Handle:THandle):Int;cdecl external LibName;

function thrtsp_Play(Handle:THandle):Int;cdecl external LibName;
function thrtsp_BufLenTotal(Handle:THandle):DWORD;cdecl external LibName;

//-----------------------------------------------------------------------------
function ThreadCreate(funcAddr:Pointer;Param:Pointer;IsCloseHandle:Boolean=false):THandle;cdecl external LibName;
function ThreadExit(threadHandle:THandle;TimeOut:DWORD=500):Int;cdecl external LibName;
function Base64Encode(inbuf:PChar;insize:Int;outbuf:PChar;outsize:Int):Int;cdecl external LibName;
function Base64Decode(inbuf:PChar;insize:Int;outbuf:PChar;outsize:Int):Int;cdecl external LibName;
function SHA1Encode(inbuf:PChar;insize:Int;outbuf:PChar;outsize:Int):Int;cdecl external LibName;
function HttpGet(url:PChar;outbuf:PChar;var OutBufLen:Int;IsShowHead:Boolean;TimeOut:Int):Int;cdecl external LibName;
function HttpPost(url:PChar;inbuf:PChar;InBufLen:Int;outbuf:PChar;var OutBufLen:Int;IsShowHead:Boolean;TimeOut:Int):Int;cdecl external LibName;

type
  TTimerCallBack=procedure(mmHandle:THandle;uMsg:DWORD;dwUser:Pointer;dw1:DWORD;dw2:DWORD);stdcall;
function mmTimeSetEvent(uDelayms:DWORD;callback:TTimerCallBack;dwUser:Pointer):THandle;cdecl external LibName;
function mmTimeKillEvent(mmHandle:THandle):Int;cdecl external LibName;
function mmTimeGetTime():DWORD;cdecl external LibName;

//*****************************************************************************
//-----------------------------------------------------------------------------
{$Z4}//��������ö��ȫ��Ϊ4���ֽ�
function thNet_Init(IsInsideDecode:Boolean;IsQueue:Boolean;IsAdjustTime:Boolean;IsAutoReConn:Boolean):THandle;cdecl external LibName;
{-----------------------------------------------------------------------------
������������ʼ�����粥��
����˵����
IsInsideDecode:�Ƿ�SDK�ڲ�������ʾ
IsQueue:�Ƿ񻺴����
IsAdjustTime:�Ƿ�У׼�豸ʱ��Ϊ�ͻ���ʱ��
�� �� ֵ��NetHandle:����������
------------------------------------------------------------------------------}
type
  TAlmType=(
    Alm_None=0,//��
    Alm_MotionDetection=1,//λ�Ʊ���Motion Detection
    Alm_DigitalInput=2,//DI����
    Alm_SoundTrigger=3,////������������
    Net_Disconn=4,//�������
    Net_ReConn=5,//��������
    Alm_HddFill=6,//����
    Alm_VideoBlind=7,//��Ƶ�ڵ�
    Alm_VideoLost=8,//��Ƶ��ʧ
    Alm_Other3=9,//��������3
    Alm_Other4=10,//��������4
    Alm_RF=11,
    Alm_OtherMax=12
    );

type
  TvideoCallBack=procedure(
    UserCustom:Pointer;//�û��Զ�������
    Chl:Int;
    Buf:PAnsiChar;//����Ƶ����ǰ֡����
    Len:Int;//���ݳ���
    IsIFrame:Int
    );cdecl;
  TaudioCallBack=procedure(
    UserCustom:Pointer;//�û��Զ�������
    Chl:Int;
    Buf:PAnsiChar;//����Ƶ����ǰ֡����
    Len:Int//���ݳ���
    );cdecl;
  TalarmCallBack=procedure(
    AlmType:Int;//�������ͣ��μ�TAlmType
    AlmTime:Int;//����ʱ��time_t
    AlmChl:Int;//����ͨ��
    UserCustom:Pointer//�û��Զ�������
    );cdecl;

const
  Decode_None=0;
  Decode_IFrame=1;
  Decode_All=2;
function thNet_SetDecodeStyle(NetHandle:THandle;DecodeStyle:Int):Boolean;cdecl external LibName;//δ��
{-----------------------------------------------------------------------------
����������������Ƶ���ݽ��뷽ʽ���Խ�ʡCPUռ����
����˵����
NetHandle:����������thNet_Init����
DecodeStyle:���뷽ʽ,Decode_None=0 Decode_IFrame=1 Decode_All=2
Decode_None:����������
Decode_IFrame:ֻ��������I֡
Decode_All:������������֡
�� �� ֵ���ɹ�����true��ʧ�ܷ���false
------------------------------------------------------------------------------}
function thNet_SetCallBack(NetHandle:THandle;videoEvent:TvideoCallBack;audioEvent:TaudioCallBack;AlmEvent:TalarmCallBack;UserCustom:Pointer):Boolean;cdecl external LibName;
{-----------------------------------------------------------------------------
�������������粥�����ûص������� �ڵ���thNet_Connect֮ǰ����
����˵����
NetHandle:����������thNet_Init����
avEvent:��Ƶ��Ƶ���ݻص�����
AlmEvent:�豸�����ص�����
UserCustom:�û��Զ�������
�� �� ֵ���ɹ�����true��ʧ�ܷ���false
------------------------------------------------------------------------------}
function thNet_Free(NetHandle:THandle):Boolean;cdecl external LibName;
{-----------------------------------------------------------------------------
�����������ͷ����粥��
����˵����
NetHandle:����������thNet_Init����
�� �� ֵ���ɹ�����true��ʧ�ܷ���false
------------------------------------------------------------------------------}
function thNet_Connect(NetHandle:THandle;UserName,Password,IPUID:PAnsiChar;DataPort:Int;TimeOut:DWORD):Boolean;cdecl external LibName;
{-----------------------------------------------------------------------------
�������������������豸
����˵����
NetHandle:����������thNet_Init����
UserName:�����ʺ�
Password:��������
IPUID:�豸IP������ ���� P2P UID
DataPort:�豸��ת���������˿�
TimeOut:���ӳ�ʱ����λms,ȱʡ 3000 ms
IsQueue:�Ƿ���н���������ʾ
�� �� ֵ���ɹ�����true
ʧ�ܷ���false
------------------------------------------------------------------------------}
function thNet_DisConn(NetHandle:THandle):Boolean;cdecl external LibName;
{-----------------------------------------------------------------------------
�����������Ͽ������豸����
����˵����
NetHandle:����������thNet_Init����
�� �� ֵ���ɹ�����true��ʧ�ܷ���false
------------------------------------------------------------------------------}
function thNet_IsConnect(NetHandle:THandle):Boolean;cdecl external LibName;
function thNet_SendSensePkt(NetHandle:THandle):Boolean;cdecl external LibName;
const
  THNET_CONNSTATUS_NO=0;
  THNET_CONNSTATUS_CONNING=1;
  THNET_CONNSTATUS_SUCCESS=2;
  THNET_CONNSTATUS_FAIL=3;
function thNet_GetConnectStatus(NetHandle:THandle):Boolean;cdecl external LibName;
{-----------------------------------------------------------------------------
�����������豸�Ƿ�����
����˵����
NetHandle:����������thNet_Init����
�� �� ֵ���ɹ�����true��ʧ�ܷ���false
------------------------------------------------------------------------------}
function thNet_Play(NetHandle:THandle;VideoChlMask,AudioChlMask,SubVideoChlMask:DWORD):Boolean;cdecl external LibName;
{-----------------------------------------------------------------------------
������������ʼ����
����˵����
NetHandle:����������thNet_Init����
VideoChlMask:ͨ�����룬
bit:31..19 18 17 16 15..03 02 01 00
0 0 0 0 0 0 0 1
  AudioChlMask:ͨ�����룬
bit:31..19 18 17 16 15..03 02 01 00
0 0 0 0 0 0 0 1
  SubVideoChlMask:������ͨ������
bit:31..19 18 17 16 15..03 02 01 00
0 0 0 0 0 0 0 1
�� �� ֵ���ɹ�����true��ʧ�ܷ���false
------------------------------------------------------------------------------}
function thNet_Stop(NetHandle:THandle):Boolean;cdecl external LibName;
{-----------------------------------------------------------------------------
����������ֹͣ����
����˵����
NetHandle:����������thNet_Init����
�� �� ֵ���ɹ�����true��ʧ�ܷ���false
------------------------------------------------------------------------------}
function thNet_IsPlay(NetHandle:THandle):Boolean;cdecl external LibName;
{-----------------------------------------------------------------------------
�����������豸�Ƿ񲥷�
����˵����
NetHandle:����������thNet_Init����
�� �� ֵ���ɹ�����true��ʧ�ܷ���false
------------------------------------------------------------------------------}
function thNet_GetAllCfg(NetHandle:THandle):PAnsiChar;cdecl external LibName;
{-----------------------------------------------------------------------------
������������ȡ��������
����˵����
NetHandle:����������thNet_Init����
�� �� ֵ��
------------------------------------------------------------------------------}
function thNet_TalkOpen(NetHandle:THandle):Boolean;cdecl external LibName;
{-----------------------------------------------------------------------------
������������ʼ�Խ�
����˵����
NetHandle:����������thNet_Init����
�� �� ֵ���ɹ�����true��ʧ�ܷ���false
------------------------------------------------------------------------------}
function thNet_TalkClose(NetHandle:THandle):Boolean;cdecl external LibName;
{-----------------------------------------------------------------------------
����������ֹͣ�Խ�
����˵����
NetHandle:����������thNet_Init����
�� �� ֵ���ɹ�����true��ʧ�ܷ���false
------------------------------------------------------------------------------}
function net_SetTalk(NetHandle:THandle;Buf:PAnsiChar;Len:Int):Boolean;cdecl external LibName;//old
type
  TSearchDevCallBack=procedure(
    UserCustom:Pointer;//�û��������
    SN:DWORD;//���к�
    DevType:Int;//�豸����
    DevModal:PAnsiChar;//�豸�ͺ�
    SoftVersion:PAnsiChar;//����汾
    DataPort:Int;//���ݶ˿�
    HttpPort:Int;//http�˿�
    rtspPort:Int;//rtsp�˿�
    DevName:PAnsiChar;//�豸����
    DevIP:PAnsiChar;//�豸IP
    DevMAC:PAnsiChar;//�豸MAC��ַ
    SubMask:PAnsiChar;//�豸��������
    Gateway:PAnsiChar;//�豸����
    DNS1:PAnsiChar;//�豸DNS
    DDNSServer:PAnsiChar;//DDNS��������ַ
    DDNSHost:PAnsiChar;//DDNS����
    UID:PAnsiChar//P2P��ʽUID
    );cdecl;
function thSearch_Init(SearchEvent:TSearchDevCallBack;UserCustom:Pointer):THandle;cdecl external LibName;
{-----------------------------------------------------------------------------
������������ʼ����ѯ�豸
����˵����
SearchEvent:��ѯ�豸�ص�����
�� �� ֵ���ɹ�����true��ʧ�ܷ���false
------------------------------------------------------------------------------}
function thSearch_SetWiFiCfg(SearchHandle:THandle;SSID,Password:PAnsiChar):Boolean;cdecl external LibName;
function thSearch_SearchDevice(SearchHandle:THandle):Boolean;cdecl external LibName;
{-----------------------------------------------------------------------------
������������ʼ��ѯ�豸
LocalIP:����ı���IP��ȱʡΪNULL
�� �� ֵ���ɹ�����true��ʧ�ܷ���false
------------------------------------------------------------------------------}
function thSearch_Free(SearchHandle:THandle):Boolean;cdecl external LibName;
{-----------------------------------------------------------------------------
�����������ͷŲ�ѯ�豸
�� �� ֵ���ɹ�����true��ʧ�ܷ���false
------------------------------------------------------------------------------}

function thNet_RemoteFilePlay(NetHandle:THandle;FileName:PAnsiChar):Boolean;cdecl external LibName;
{-----------------------------------------------------------------------------
������������ʼ����Զ���ļ�
����˵����
NetHandle:����������thNet_Init����
FileName:�����Զ��¼���ļ���
�� �� ֵ���ɹ�����true��ʧ�ܷ���false
------------------------------------------------------------------------------}
function thNet_RemoteFileStop(NetHandle:THandle):Boolean;cdecl external LibName;
{-----------------------------------------------------------------------------
����������ֹͣ����Զ���ļ�
����˵����
NetHandle:����������thNet_Init����
�� �� ֵ���ɹ�����true��ʧ�ܷ���false
------------------------------------------------------------------------------}
function thNet_RemoteFilePlayControl(NetHandle:THandle;PlayCtrl,speed,Pos:Int):Boolean;cdecl external LibName;
{-----------------------------------------------------------------------------
����������Զ���ļ����ſ���
����˵����
NetHandle:����������thNet_Init����
PlayCtrl:PS_None=0,//��
PS_Play=1,//����
PS_Pause=2,//��ͣ
PS_Stop=3,//ֹͣ
PS_FastBackward=4,//����
PS_FastForward=5,//���
PS_StepBackward=6,//����
PS_StepForward=7,//����
PS_DragPos=8,//�϶�
speed:���PlayCtrl=PS_StepBackward,PS_FastForward ���򱣴������˱��� 1 2 4 8 16 32 ����
Pos:���PlayCtrl=PS_DragPos���򱣴��ļ��ļ�λ��Pos
�� �� ֵ���ɹ�����true��ʧ�ܷ���false
------------------------------------------------------------------------------}
function thNet_AudioPlayOpen(NetHandle:THandle):Boolean;cdecl external LibName;
function thNet_AudioPlayClose(NetHandle:THandle):Boolean;cdecl external LibName;
function thNet_SetAudioIsMute(NetHandle:THandle;IsAudioMute:Integer):Boolean;cdecl external LibName;
{-----------------------------------------------------------------------------
�����������Ƿ���
����˵����
NetHandle:����������thNet_Init����
IsAudioMute:�Ƿ���
�� �� ֵ���ɹ�����true��ʧ�ܷ���false
------------------------------------------------------------------------------}
type
  PDspInfo=^TDspInfo;//Player��ʾ��Ϣ
  TDspInfo=packed record
    DspHandle:hWnd;//��ʾ���ھ��
    Channel:Int;//ͨ��
    DspRect:TRect;//��ʾ����
  end;
function thNet_AddDspInfo(NetHandle:THandle;PDspInfo:PDspInfo):Boolean;cdecl external LibName;
{-------------------------------------------------------------------------------
��������������һ����������ͬһ��ƵԴ��������������
����˵����
NetHandle:����������thNet_Init����
PInfo:������ʾ��Ϣָ��
DspHandle:hWnd;//��ʾ���ھ��
Channel:Int;//ͨ����ͨ����0��ʼ
DspRect:TRect;//��ʾ����
�� �� ֵ���ɹ�����true��ʧ�ܷ���false
  -------------------------------------------------------------------------------}
function thNet_DelDspInfo(NetHandle:THandle;PDspInfo:PDspInfo):Boolean;cdecl external LibName;
{-------------------------------------------------------------------------------
����������ɾ��һ����������
����˵����
NetHandle:����������thNet_Init����
PInfo:������ʾ��Ϣָ��
DspHandle:hWnd;//��ʾ���ھ��
Channel:Int;//ͨ����ͨ����0��ʼ
DspRect:TRect;//��ʾ����
�� �� ֵ���ɹ�����true��ʧ�ܷ���false
  -------------------------------------------------------------------------------}

function thNet_HttpGet(NetHandle:THandle;url,Buf:PAnsiChar;var BufLen:Int):Boolean;cdecl external LibName;
function thNet_HttpGetStop(NetHandle:THandle):Boolean;cdecl external LibName;

function thNet_SetRecPath(NetHandle:THandle;RecPath:PAnsiChar):Boolean;cdecl external LibName;//linux ��"/"������windows�� "\"����
function thNet_StartRec(NetHandle:THandle;RecFileName:PAnsiChar):Boolean;cdecl external LibName;//RecFileName=NULL,���thNet_SetRecPathʹ��
function thNet_IsRec(NetHandle:THandle):Boolean;cdecl external LibName;
function thNet_StopRec(NetHandle:THandle):Boolean;cdecl external LibName;

function thNet_SetJpgPath(NetHandle:THandle;JpgPath:PAnsiChar):Boolean;cdecl external LibName;//linux ��"/"������windows�� "\"����
function thNet_SaveToJpg(NetHandle:THandle;JpgFileName:PAnsiChar):Boolean;cdecl external LibName;//JpgFileName=NULL,���thNet_SetJpgPathʹ��
{-------------------------------------------------------------------------------
��������������ΪJPGͼƬ
����˵����
NetHandle:����������thNet_Init����
FileName:�ļ����������·��
�� �� ֵ���ɹ�����true��ʧ�ܷ���false
  -------------------------------------------------------------------------------}
function P2P_Init():Boolean;cdecl external LibName;
function P2P_Free():Boolean;cdecl external LibName;
//-----------------------------------------------------------------------------
//nSample only=8000, 32000 nMode 0: Mild, 1: Medium , 2: Aggressive, IsX=1�� IsX=0��
function LowNoiseInit(nSample,nMode,IsX:Int):THandle;cdecl external LibName;
function LowNoiseProcess(Handle:THandle;SrcBuf:PAnsiChar;srcBufLen:Int;DstBuf:PAnsiChar;var dstBufLen:Int):Boolean;cdecl external LibName;//ÿ��10ms
function LowNoiseFree(Handle:THandle):Boolean;cdecl external LibName;


//FileSize = strlen(Str)*2 * (2000(125ms) + 1200(75ms))
function TextToDTMFWavFile(txt:PAnsiChar;Len:Int;WavFileName:PAnsiChar;iVolumn{0~100}:Int;iDelayms{=75ms}:Int):Boolean;cdecl external LibName;
function DTMFWavFileToText(FileName:PAnsiChar;txt:PAnsiChar):Boolean;cdecl external LibName;
function DTMFBufToText(Buf:PAnsiChar;BufLen:Int;iSample{=8000}:Int;iBits{=16}:Int;txt:PAnsiChar):Boolean;cdecl external LibName;


implementation

initialization


finalization

end.

