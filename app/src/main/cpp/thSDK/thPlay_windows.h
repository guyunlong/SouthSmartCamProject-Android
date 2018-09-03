//-----------------------------------------------------------------------------
// Author      : 朱红波
// Date        : 2017.09.14
// Version     : V 2.00
// Description : www.southipcam.com
//-----------------------------------------------------------------------------
#include "axdll.h"
#include "TFun.h"
#include "../include/avDecode.h"
#include "../include/libthSDK.h"
#include "../include/av_Queue.h"

//-----------------------------------------------------------------------------
void STDCALL Time_QueueDraw(HANDLE mmHandle, u32 uMsg, void *dwUser, u32 dw1, u32 dw2)
{
  TavNode *tmpNode;
  int i, ret, iPktCount;
  TavPicture *pFrameV;
  TPlayParam *Play = (TPlayParam *) dwUser;
  if (!Play) return;

  if (mmHandle != Play->hTimerIDQueueDraw) return;
  if (Play->IsExit) return;
  if (Play->hTimerIDQueueDraw)
  {
    mmTimeKillEvent(Play->hTimerIDQueueDraw);
    Play->hTimerIDQueueDraw = NULL;
    Play->iSleepTime = 0;
    return;
  }

  //0
  __try
  {
    iPktCount = avQueue_GetCount(Play->hQueueDraw);
    if (iPktCount == 0) return;
    if (iPktCount > 5 && iPktCount <= 10)
    {
      tmpNode = avQueue_ReadBegin(Play->hQueueDraw);
      if (tmpNode != NULL)
      {
        if (tmpNode->Buf1 != NULL) free(tmpNode->Buf1);
      }
      avQueue_ReadEnd(Play->hQueueDraw, tmpNode);
    }

    //1
    iPktCount = avQueue_GetCount(Play->hQueueDraw);
    if (iPktCount == 0) return;
    if (iPktCount > 10)
    {
      for (i = 0; i < 1; i++)
      {
        tmpNode = avQueue_ReadBegin(Play->hQueueDraw);
        if (tmpNode != NULL)
        {
          if (tmpNode->Buf1 != NULL) free(tmpNode->Buf1);
        }
        avQueue_ReadEnd(Play->hQueueDraw, tmpNode);
      }
    }
    //2
    tmpNode = avQueue_ReadBegin(Play->hQueueDraw);
    if (tmpNode == NULL) return;
    if (tmpNode->Buf == NULL)
    {
      if (tmpNode->Buf1 != NULL) free(tmpNode->Buf1);
      avQueue_ReadEnd(Play->hQueueDraw, tmpNode);
      return;
    }
    //3
    pFrameV = (TavPicture *) tmpNode->Buf;
    if (Play->ImgWidth > 0 && Play->ImgHeight > 0)
    {
      //zhb ThreadLock(&Play->Lock);
      ret = thRender_FillMem(Play->RenderHandle, *pFrameV, Play->ImgWidth, Play->ImgHeight);
      for (i = 0; i < MAX_DSPINFO_COUNT; i++)
      {
        TDspInfo *PDspInfo = &Play->DspInfoLst[i];
        if (PDspInfo->DspHandle == NULL) continue;
        ret = thRender_Display(Play->RenderHandle, PDspInfo->DspHandle, PDspInfo->DspRect);
      }
      //zhb ThreadUnlock(&Play->Lock);
    }

    if (tmpNode->Buf1 != NULL) free(tmpNode->Buf1);
    avQueue_ReadEnd(Play->hQueueDraw, tmpNode);
  }
  __except(1)
  {
    if (Play->hTimerIDQueueDraw)
    {
      mmTimeKillEvent(Play->hTimerIDQueueDraw);
      Play->hTimerIDQueueDraw = NULL;
      Play->iSleepTime = 0;
    }
  }
}

