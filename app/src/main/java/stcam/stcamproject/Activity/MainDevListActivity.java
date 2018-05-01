package stcam.stcamproject.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.github.nuptboyzhb.lib.SuperSwipeRefreshLayout;
import com.model.DevModel;
import com.thSDK.TMsg;

import java.util.List;

import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import stcam.stcamproject.Adapter.DeviceListAdapter;
import stcam.stcamproject.Application.STApplication;
import stcam.stcamproject.Manager.AccountManager;
import stcam.stcamproject.R;
import stcam.stcamproject.Util.SouthUtil;
import stcam.stcamproject.View.LoadingDialog;
import stcam.stcamproject.network.ServerNetWork;

public class MainDevListActivity extends AppCompatActivity implements DeviceListAdapter.OnItemClickListener {

    RecyclerView mRecyclerView;
    DeviceListAdapter mAdapter;
    List<DevModel>mDevices;
    SuperSwipeRefreshLayout refreshLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_dev_list);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(R.string.title_main_dev_list);
        initView();
       // mAdapter = new DeviceListAdapter(this,null);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadDevList(false);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    String SearchMsg;
    boolean IsSearching;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.action_add) {
            Intent intent = new Intent(this, AddDeviceActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    void initView(){
        refreshLayout = findViewById(R.id.swipeRefreshLayout);
        mRecyclerView = findViewById(R.id.recyler_device);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);

         mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        refreshLayout
                .setOnPullRefreshListener(new SuperSwipeRefreshLayout.OnPullRefreshListener() {

                    @Override
                    public void onRefresh() {
                        //TODO 开始刷新
                        loadDevList(true);
                    }

                    @Override
                    public void onPullDistance(int distance) {
                        //TODO 下拉距离
                    }

                    @Override
                    public void onPullEnable(boolean enable) {
                        //TODO 下拉过程中，下拉的距离是否足够出发刷新
                    }
                });
        refreshLayout
                .setOnPushLoadMoreListener(new SuperSwipeRefreshLayout.OnPushLoadMoreListener() {

                    @Override
                    public void onLoadMore() {
                        loadDevList(true);
                    }

                    @Override
                    public void onPushEnable(boolean enable) {
                        //TODO 上拉过程中，上拉的距离是否足够出发刷新
                    }

                    @Override
                    public void onPushDistance(int distance) {
                        // TODO 上拉距离

                    }

                });
        refreshLayout.setFooterView(createFooterView());

    }

    View createFooterView(){
        View view = LayoutInflater.from(this).inflate(R.layout.load_more, null);

        return view;
    }
    void loadDevList(boolean refresh){
        if (lod == null){
            lod = new LoadingDialog(this);
        }
        if (!refresh)
            lod.dialogShow();
        subscription = ServerNetWork.getCommandApi()
//                .app_user_get_devlst("4719373@qq.com","admin111")
                .app_user_get_devlst(AccountManager.getInstance().getDefaultUsr(), AccountManager.getInstance().getDefaultPwd())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer_get_devlst);
    }

    Observer<List<DevModel>> observer_get_devlst = new Observer<List<DevModel>>() {
        @Override
        public void onCompleted() {
            lod.dismiss();
            refreshLayout.setRefreshing(false);
            refreshLayout.setLoadMore(false);

           // refreshLayout.setLoading(false);

            Log.e(tag,"---------------------2");
            subscription.unsubscribe();
        }
        @Override
        public void onError(Throwable e) {
            lod.dismiss();
            refreshLayout.setRefreshing(false);
            refreshLayout.setLoadMore(false);
            Log.e(tag,"---------------------1:"+e.getLocalizedMessage());
        }

        @Override
        public void onNext(List<DevModel> mlist) {
            lod.dismiss();

            if (mlist.size() > 0){
              //  mDevices = mlist;
                //DevModel model =  mlist.get(0);
                //SouthUtil.showToast(STApplication.getInstance(),"dev0 name"+model.DevName);

                if (mDevices == null){
                    mDevices = mlist;
                }
                else{
                    for (DevModel model : mlist){
                        boolean exist = false;
                        for (DevModel existModel : mDevices){

                            if (model.SN.equals(existModel.SN)){
                                exist = true;
                                break;
                            }
                        }
                        if (!exist){
                            mDevices.add(model);
                        }
                    }
                }

                for (DevModel model : mDevices){

                    Log.e(tag,"---------------------1 dev0 name"+model.DevName);
                    if (!model.IsConnect())
                    DevModel.threadConnect(ipc,model,false);
                }

                if (mAdapter == null){
                    mAdapter = new DeviceListAdapter(MainDevListActivity.this,mlist);
                    mAdapter.setOnItemClickListener(MainDevListActivity.this);
                    mRecyclerView.setAdapter(mAdapter);
                }
                else{
                    mAdapter.setmDatas(mlist);
                }

            }
            else{
                //MyContext.getInstance()
                Log.e(tag,"---------------------1:no dev");
                SouthUtil.showToast(STApplication.getInstance(),"No dev");
            }

        }
    };

    protected Subscription subscription;

    final String tag = "MainDevListActivity";
    LoadingDialog lod;

    @Override
    public void onItemClick(View view, int position,int tpe) {
        DevModel model = mDevices.get(position);
        if (!model.IsConnect()){
            SouthUtil.showToast(STApplication.getInstance(),getString(R.string.action_net_not_connect));
            return;
        }
        if (0 == tpe){


            Intent intent = new Intent(STApplication.getInstance(), PlayLiveActivity.class);

            Bundle bundle = new Bundle();
            bundle.putSerializable("devModel",model);

            intent.putExtras(bundle);
            Log.e(tag,"to vid devModel NetHandle:"+model.NetHandle);

            startActivity(intent);
        }
        else  if (1 == tpe){


            Intent intent = new Intent(STApplication.getInstance(), DeviceShareActivity.class);

            Bundle bundle = new Bundle();
            bundle.putSerializable("devModel",model);

            intent.putExtras(bundle);
            Log.e(tag,"to DeviceShareActivity NetHandle:"+model.NetHandle);

            startActivity(intent);
        }

        else  if (2 == tpe){

            if(model.ExistSD == 0){
                SouthUtil.showToast(STApplication.getInstance(),getString(R.string.action_not_exist_sd));
                return;
            }
            Intent intent = new Intent(STApplication.getInstance(), PlayBackListActivity.class);

            Bundle bundle = new Bundle();
            bundle.putSerializable("devModel",model);

            intent.putExtras(bundle);
            Log.e(tag,"to PlayBackListActivity NetHandle:"+model.NetHandle);

            startActivity(intent);
        }

    }
    public final Handler ipc = new Handler()
    {
        @Override
        public void handleMessage(Message msg)
        {


            super.handleMessage(msg);
            DevModel model;
            switch (msg.what)
            {
                case TMsg.Msg_NetConnSucceed:
                    model = (DevModel) msg.obj;
                    Log.e(tag,"NetConnSucceed:"+model.SN+"DevNode.NetHandle:"+model.NetHandle);
                    break;
                case TMsg.Msg_NetConnFail:
                    model = (DevModel) msg.obj;
                    Log.e(tag,"NetConnFail:"+model.SN+"DevNode.NetHandle:"+model.NetHandle);
                    break;

                default:
                    break;
            }
        }
    };

    @Override
    public void onLongClick(View view, int position) {

    }


}
