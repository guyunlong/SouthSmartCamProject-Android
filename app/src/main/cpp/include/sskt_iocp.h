//-----------------------------------------------------------------------------
// Author      : ��첨
// Date        : 2017.09.14
// Version     : V 2.00
// Description : www.southipcam.com
//-----------------------------------------------------------------------------
#ifndef sskt_iocp_h
#define sskt_iocp_h

#ifdef WIN32
#include <winsock2.h>
#include <windows.h>
#include "TFun.h"
#include "List.h"

#ifndef EXPORT
#ifdef WIN32
#define EXPORT __declspec(dllexport)
#else
#define EXPORT
#endif
#endif

#pragma comment(lib,"ws2_32.lib")
#ifdef __cplusplus
extern "C"
{
#endif

  //-----------------------------------------------------------------------------������
#define MAX_BUFSIZE 1024*64        //���ݻ�������С  
  //-------------------------------------
  typedef enum TSocketEvent{
    seInitIOPort, 
    seInitSocket,
    seConnect,
    seDisconnect,
    seListen,
    seAccept,
    seWrite,
    seRead,
  }TSocketEvent;
  //-------------------------------------
  typedef struct TPerHandleData{
    OVERLAPPED Overlapped; //�ص��ṹ  
    char buf[MAX_BUFSIZE]; //���ݻ�����  
    TSocketEvent Event;    //��������  
  }TPerHandleData;
  //-------------------------------------
  typedef struct TcltNode{
    SOCKET hSocket; //�׽��־��  
    struct sockaddr_in cAddr; //�Է��ĵ�ַ  
    char FromIP[16];
    int FromPort;
    char UserID[100];
    TPerHandleData Block;
    WSABUF wsaBuffer;
  } TcltNode;
  //-----------------------------------------------------------------------------������
  typedef void(__cdecl* TOnConnNotify)(const HANDLE Handle, void* Sender, TcltNode* cltNode, char* FromIP, int FromPort); //�����¼�
  typedef void(__cdecl* TOnDisConnNotify)(const HANDLE Handle, void* Sender, TcltNode* cltNode, char* FromIP, int FromPort); //�Ͽ��¼�
  typedef void(__cdecl* TOnRecvNotify)(const HANDLE Handle, void* Sender, TcltNode* cltNode, char* FromIP, int FromPort, char* Buf, int BufLen); //��ȡ�¼�

  EXPORT HANDLE __cdecl iocp_sskt_Init(int ServerPort, TOnConnNotify OnConnNotify, TOnDisConnNotify OnDisConnNotify, TOnRecvNotify OnRecvNotify, void* Sender);
  EXPORT bool __cdecl iocp_sskt_Free(HANDLE Handle);
  EXPORT bool __cdecl iocp_sskt_SendBuf(HANDLE FromHandle, char* Buf, int BufLen);
  //-----------------------------------------------------------------------------������

#ifdef __cplusplus
}
#endif 
#endif
#endif
