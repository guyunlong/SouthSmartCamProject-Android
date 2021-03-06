package stcam.stcamproject.Adapter;

import com.model.PushSettingModel;
import com.model.RecConfigModel;

import java.util.List;

import stcam.stcamproject.Application.STApplication;
import stcam.stcamproject.Config.Config;
import stcam.stcamproject.R;

public class PushSettingAdapter extends BaseAdapter<String>
{
  PushSettingModel mPushSettingModel;
  int MD_Sensitive = -1;
  RecConfigModel mRecConfigModel;
  boolean mAUDIO_IsPlayPromptSound;
  int MD_Active = 0;

  public PushSettingAdapter(List<String> list)
  {
    super(R.layout.list_setting, list);
  }

  public void setPushSettingModel(PushSettingModel pushSettingModel)
  {
    this.mPushSettingModel = pushSettingModel;
  }

  public void setmRecConfigModel(RecConfigModel mRecConfigModel)
  {
    this.mRecConfigModel = mRecConfigModel;
    this.notifyDataSetChanged();
  }

  public void setAUDIO_IsPlayPromptSound(boolean AUDIO_IsPlayPromptSound)
  {
    mAUDIO_IsPlayPromptSound = AUDIO_IsPlayPromptSound;
  }

  public void setMD_Sensitive(int MD_Active, int MD_Sensitive)
  {
    this.MD_Active = MD_Active;
    this.MD_Sensitive = MD_Sensitive;
  }

  protected void convert(BaseHolder holder, String title, int position)
  {
    super.convert(holder, title, position);
    holder.setText(R.id.title_text, title);
    String Str = "";
    if (mPushSettingModel != null)
    {
      if (0 == position)
      {
        Str = mPushSettingModel.getPushIntervalDesc();
        Str = Str + Config.StrOnNextLevel;
        holder.setText(R.id.detail_text, Str);
      }
      else if (1 == position)
      {
        if (MD_Active == 0)
        {
          Str = STApplication.getInstance().getString(R.string.action_close);
          Str = Str + Config.StrOnNextLevel;
          holder.setText(R.id.detail_text, Str);
        }
        else
        {
          if (MD_Sensitive != -1)
          {
            if (MD_Sensitive <= 100)
            {
              Str = STApplication.getInstance().getString(R.string.action_level_high);
            }
            else if (MD_Sensitive <= 150)
            {
              Str = STApplication.getInstance().getString(R.string.action_level_middle);
            }
            else if (MD_Sensitive <= 200)
            {
              Str = STApplication.getInstance().getString(R.string.action_level_low);
            }
            Str = Str + Config.StrOnNextLevel;
            holder.setText(R.id.detail_text, Str);
          }
        }
      }
      else if (2 == position)
      {
        Str = mPushSettingModel.getPIRSensitiveDesc();
        Str = Str + Config.StrOnNextLevel;
        holder.setText(R.id.detail_text, Str);
      }
      else if (3 == position)
      {
        if (mAUDIO_IsPlayPromptSound)
        {
          Str = STApplication.getInstance().getString(R.string.action_open);
        }
        else
        {
          Str = STApplication.getInstance().getString(R.string.action_close);
        }
        Str = Str + Config.StrOnNextLevel;
        holder.setText(R.id.detail_text, Str);
      }
      else if (4 == position)
      {
        if (mRecConfigModel != null)
        {
          if (mRecConfigModel.getRec_AlmTimeLen() != 0)
          {
            Str = mRecConfigModel.getRec_AlmTimeLen() + STApplication.getInstance().getString(R.string
              .string_second);
            Str = Str + Config.StrOnNextLevel;
            holder.setText(R.id.detail_text, Str);
          }
        }
      }
      else if (5 == position)
      {
        //detailArrow.setVisibility(View.VISIBLE);
        Str = Str + Config.StrOnNextLevel;
        holder.setText(R.id.detail_text, Str);
      }

    }


  }
}
