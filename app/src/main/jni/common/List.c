//-----------------------------------------------------------------------------
// Author      : ��첨
// Date        : 2017.09.14
// Version     : V 2.00
// Description : www.southipcam.com
//-----------------------------------------------------------------------------
#include "List.h"

//---------------------------------------------------------------------------����
TList* LstInit()
{
  TList* Lst = (TList*)malloc(sizeof(TList));
  memset(Lst, 0, sizeof(TList));
  return Lst;
}
//---------------------------------------------------------------------------�ͷ�
void LstFree(TList* Lst)
{
  LstClear(Lst);
}
//---------------------------------------------------------------------------ͳ������
int LstCount(TList* Lst)
{
  return Lst->FCount;
}
//---------------------------------------------------------------------------����
int LstAdd(TList* Lst, void* Item)
{
  int Result;
  if (Lst->FCount >= MAX_LST_SIZE) return -1;

  Result = Lst->FCount;
  Lst->FList[Result] = Item;
  Lst->FCount++;
  return Result;
}
//---------------------------------------------------------------------------�������
void LstClear(TList* Lst)
{
  memset(Lst, 0, sizeof(TList));
}
//---------------------------------------------------------------------------ɾ��
void LstDelete(TList* Lst, int Index)
{
  if ((Index < 0) || (Index >= Lst->FCount)) return;
  Lst->FCount--;
  if (Index < Lst->FCount)
  {
    memcpy(&Lst->FList[Index], &Lst->FList[Index + 1], (Lst->FCount - Index) * sizeof(void*));
  }
}
//---------------------------------------------------------------------------����
int LstIndexOf(TList* Lst, void* Item)
{
  int Result = 0;

  while ((Result < Lst->FCount) && (Lst->FList[Result] != Item))
  {
    Result++;
  }
  if (Result == Lst->FCount)
    Result = -1;
  return Result;
}

//---------------------------------------------------------------------------����
void LstInsert(TList* Lst, int Index, void* Item)
{
  if ((Index < 0) || (Index > Lst->FCount)) return;

  if (Index < Lst->FCount)
  {
    memcpy(&Lst->FList[Index + 1], &Lst->FList[Index], (Lst->FCount - Index) * sizeof(void*));
  }
  Lst->FList[Index] = Item;
  Lst->FCount++;
}
//---------------------------------------------------------------------------����
void LstExchange(TList* Lst, int CurIndex, int NewIndex)
{
  void* tmp = Lst->FList[CurIndex];
  Lst->FList[CurIndex] = Lst->FList[NewIndex];
  Lst->FList[NewIndex] = tmp;
}
//---------------------------------------------------------------------------�ƶ�
void LstMove(TList* Lst, int CurIndex, int NewIndex)
{
  void* Item;
  if (CurIndex != NewIndex)
  {
    if ((NewIndex < 0) || (NewIndex >= Lst->FCount)) return;
    Item = Lst->FList[CurIndex];
    Lst->FList[CurIndex] = NULL;
    LstDelete(Lst, CurIndex);
    LstInsert(Lst, NewIndex, NULL);
    Lst->FList[NewIndex] = Item;
  }
}
//---------------------------------------------------------------------------ɾ��
int LstRemove(TList* Lst, void* Item)
{
  int Result = LstIndexOf(Lst, Item);
  if (Result >= 0)
    LstDelete(Lst, Result);
  return Result;
}

//---------------------------------------------------------------------------���нڵ�
void* LstItems(TList* Lst, int Index)
{
  if(Index<0) return NULL;
  if(Index>=Lst->FCount) return NULL;
  return Lst->FList[Index];
}
//-------------------------------------------------------------------------
