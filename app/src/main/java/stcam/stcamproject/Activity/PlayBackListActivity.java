package stcam.stcamproject.Activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.github.nuptboyzhb.lib.SuperSwipeRefreshLayout;
import com.model.DevModel;
import com.model.SDVideoModel;
import com.thSDK.lib;

import java.util.ArrayList;
import java.util.List;

import stcam.stcamproject.Adapter.BaseAdapter;
import stcam.stcamproject.Adapter.PlayBackListAdapter;
import stcam.stcamproject.Application.STApplication;
import stcam.stcamproject.Manager.DataManager;
import stcam.stcamproject.R;
import stcam.stcamproject.Util.GsonUtil;
import stcam.stcamproject.View.LoadingDialog;

public class PlayBackListActivity extends BaseAppCompatActivity implements BaseAdapter.OnItemClickListener
{
  static final String tag = "PlayBackListActivity";
  DevModel devModel;
  PlayBackListAdapter adapter;
  RecyclerView rv;
  List<SDVideoModel> videoArray = new ArrayList<>();
  SuperSwipeRefreshLayout refreshLayout;


  boolean refresh;//true:执行刷新 false:loadmore
  int page;//当前正在加载的页数
  MainDevListFragment.TUserMode UserMode;
  LoadingDialog lod;

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
    setContentView(R.layout.activity_play_back_list);
    Bundle bundle = this.getIntent().getExtras();
    if (bundle != null)
    {
      devModel = (DevModel) bundle.getParcelable("devModel");
      UserMode = (MainDevListFragment.TUserMode) bundle.getSerializable("entry");
      Log.e(tag, "NetHandle:" + devModel.NetHandle + ",SN:" + devModel.SN);
    }
    android.support.v7.app.ActionBar actionBar = getSupportActionBar();
    if (actionBar != null)
    {
//            actionBar.setHomeButtonEnabled(true);
//            actionBar.setDisplayHomeAsUpEnabled(true);
//            actionBar.setTitle(getString(R.string.string_record_list));

      setCustomTitle(getString(R.string.string_record_list), true);

    }

    refreshLayout = findViewById(R.id.swipeRefreshLayout);
    rv = findViewById(R.id.play_back_list_view);
    rv.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    rv.setLayoutManager(new LinearLayoutManager(this));
    page = 0;
    refresh = true;
    if (lod == null)
    {
      lod = new LoadingDialog(this);
    }
    lod.dialogShow();

    GetPlayBackListloadTask task = new GetPlayBackListloadTask();
    task.execute();


    refreshLayout
      .setOnPullRefreshListener(new SuperSwipeRefreshLayout.OnPullRefreshListener()
      {

        @Override
        public void onRefresh()
        {
          //TODO 开始刷新
          refresh = true;
          page = 0;
          GetPlayBackListloadTask task = new GetPlayBackListloadTask();
          task.execute();
        }

        @Override
        public void onPullDistance(int distance)
        {
          //TODO 下拉距离
        }

        @Override
        public void onPullEnable(boolean enable)
        {
          //TODO 下拉过程中，下拉的距离是否足够出发刷新
        }
      });
    refreshLayout
      .setOnPushLoadMoreListener(new SuperSwipeRefreshLayout.OnPushLoadMoreListener()
      {

        @Override
        public void onLoadMore()
        {
          refresh = false;
          GetPlayBackListloadTask task = new GetPlayBackListloadTask();
          task.execute();
        }

        @Override
        public void onPushEnable(boolean enable)
        {
          //TODO 上拉过程中，上拉的距离是否足够出发刷新
        }

        @Override
        public void onPushDistance(int distance)
        {
          // TODO 上拉距离

        }

      });
    refreshLayout.setFooterView(createFooterView());


  }

  @Override
  protected void onResume()
  {
    super.onResume();


    if (adapter != null)
    {
      adapter.notifyDataSetChanged();
    }
  }

  View createFooterView()
  {
    View view = LayoutInflater.from(this).inflate(R.layout.load_more, null);

    return view;
  }

  @Override
  public void onConfigurationChanged(Configuration newConfig)
  {
    super.onConfigurationChanged(newConfig);

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


  void onGetPlayBackListResponse(String json)
  {
    if (json != null && json.length() > 0)
    {
      List<SDVideoModel> lists = GsonUtil.parseJsonArrayWithGson(json,
        SDVideoModel[].class);
      if (lists.size() > 0)
      {
        if (refresh)
        {
          videoArray.clear();
          videoArray.addAll(lists);
        }
        else
        {
          videoArray.addAll(lists);
        }
      }
      if (adapter == null)
      {
        adapter = new PlayBackListAdapter(lists);
        adapter.setOnItemClickListener(PlayBackListActivity.this);
        rv.setAdapter(adapter);
      }
      else
      {
        adapter.resetMList(videoArray);
        adapter.notifyDataSetChanged();
      }

    }

  }

  @Override
  public void onItemClick(View view, int position)
  {
    Log.e(tag, "onItemClick,item:" + position);

    Intent intent = new Intent(STApplication.getInstance(), PlayBackActivity.class);

    Bundle bundle = new Bundle();
    SDVideoModel model = videoArray.get(position);
    model.viewed = true;
    DataManager.getInstance().addSDVideoModel(model);
    bundle.putSerializable("model", model);
    bundle.putParcelable("devModel", devModel);
    bundle.putSerializable("entry", UserMode);
    intent.putExtras(bundle);

    Log.e(tag, "to PlayBackActivity sdVideo:" + model.sdVideo);

    startActivity(intent);

  }

  @Override
  public void onLongClick(View view, int position)
  {

  }

  class GetPlayBackListloadTask extends AsyncTask<Integer, Void, String>
  {
    // AsyncTask<Params, Progress, Result>
    //后面尖括号内分别是参数（例子里是线程休息时间），进度(publishProgress用到)，返回值类型
    @Override
    protected void onPreExecute()
    {
      //第一个执行方法
      super.onPreExecute();
    }

    @Override
    protected String doInBackground(Integer... params)
    {
      //第二个执行方法,onPreExecute()执行完后执行
      String url = devModel.getDevURL( lib.Msg_GetRecFileLst) + "&p=" + (page++) + "&l=20";
      String ret = lib.thNetHttpGet(devModel.NetHandle, url);
      return ret;
    }

    @Override
    protected void onPostExecute(String result)
    {
      //doInBackground返回时触发，换句话说，就是doInBackground执行完后触发
      //这里的result就是上面doInBackground执行后的返回值，所以这里是"执行完毕"
      Log.e(tag, "get playback list :" + result);
      refreshLayout.setRefreshing(false);
      refreshLayout.setLoadMore(false);
      lod.dismiss();
      onGetPlayBackListResponse(result);


      super.onPostExecute(result);
    }
  }


}
