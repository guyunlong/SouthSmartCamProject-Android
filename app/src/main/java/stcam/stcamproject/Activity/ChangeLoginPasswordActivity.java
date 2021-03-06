package stcam.stcamproject.Activity;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.model.RetModel;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import stcam.stcamproject.Manager.AccountManager;
import stcam.stcamproject.Manager.JPushManager;
import stcam.stcamproject.R;
import stcam.stcamproject.Util.SouthUtil;
import stcam.stcamproject.View.LoadingDialog;
import stcam.stcamproject.network.ServerNetWork;

public class ChangeLoginPasswordActivity extends BaseAppCompatActivity implements View.OnClickListener
{
  EditText editText_confirm_pwd;
  EditText editText_new_pwd;
  EditText editText_old_pwd;
  Button confirmButton;
  String PasswordOld = null;
  String PasswordNew = null;
  String PasswordNew1 = null;

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

    setContentView(R.layout.activity_change_login_password);

    android.support.v7.app.ActionBar actionBar = getSupportActionBar();
    if (actionBar != null)
    {

      setCustomTitle(getString(R.string.action_change_login_password), true);


    }
    initView();
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

  void initView()
  {
    editText_confirm_pwd = findViewById(R.id.editText_confirm_pwd);
    editText_new_pwd = findViewById(R.id.editText_password);
    editText_old_pwd = findViewById(R.id.editText_old_password);
    confirmButton = findViewById(R.id.button_next);
    confirmButton.setOnClickListener(this);
  }

  @Override
  public void onClick(View view)
  {
    PasswordOld = editText_old_pwd.getText().toString();
    PasswordNew = editText_new_pwd.getText().toString();
    PasswordNew1 = editText_confirm_pwd.getText().toString();

    if (!PasswordOld.equals(AccountManager.getInstance().getDefaultPwd()))
    {
      //原密码不正确
      SouthUtil.showDialog(ChangeLoginPasswordActivity.this, getString(R.string.old_password_wrong));
      return;
    }
    else if (PasswordNew.equals(PasswordOld))
    {
      //新旧密码相同
      SouthUtil.showDialog(ChangeLoginPasswordActivity.this, getString(R.string.confirm_password_same));
      return;
    }
    else if (PasswordNew.length() < 4)
    {
      //密码小于4位
      SouthUtil.showDialog(ChangeLoginPasswordActivity.this, getString(R.string.password_length_limit));
      return;
    }
    else if (!PasswordNew.equals(PasswordNew1))
    {
      SouthUtil.showDialog(ChangeLoginPasswordActivity.this, getString(R.string.confirm_password_nosame));
      //两次密码不一致
      return;
    }

    if (lod == null)
    {
      lod = new LoadingDialog(this);
    }
    lod.dialogShow();


    ServerNetWork.getCommandApi()
      .app_user_changepsd(
        AccountManager.getInstance().getDefaultUsr(),
        AccountManager.getInstance().getDefaultPwd(),
        JPushManager.getJPushRegisterID(),
        editText_new_pwd.getText().toString())
      .subscribeOn(Schedulers.io())
      .observeOn(AndroidSchedulers.mainThread())
      .subscribe(observer);
  }


  Observer<RetModel> observer = new Observer<RetModel>()
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
        SouthUtil.showToast(ChangeLoginPasswordActivity.this, getString(R.string.string_ChangePsdSuccess));
        AccountManager.getInstance().saveAccount(
          AccountManager.getInstance().getDefaultUsr(),
          PasswordNew,
          AccountManager.getInstance().getIsRemeberAccount()
        );
        ChangeLoginPasswordActivity.this.finish();
      }
      else
      {
        SouthUtil.showToast(ChangeLoginPasswordActivity.this, getString(R.string.string_ChangePsdFail));
      }

    }
  };
  LoadingDialog lod;
  final String tag = "ChangePasswordActivity";

}
