package stcam.stcamproject.Activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import stcam.stcamproject.Config.Config;
import stcam.stcamproject.R;

public class MainViewPagerActivity extends AppCompatActivity implements View.OnClickListener {

    private PagerAdapter mPagerAdapter;
    private ViewPager mViewPager;
    MainDevListFragment.EnumMainEntry entryType;

    private TextView tx1, tx2, tx3, tx4;
    ImageButton image_1, image_2, image_3, image_4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = this.getIntent().getExtras();
        if (bundle != null)
        {
            entryType = (MainDevListFragment.EnumMainEntry) bundle.getSerializable("entry");
        }


        getSupportActionBar().hide();
        setContentView(R.layout.activity_main_view_pager);
        initView();

    }

    private void initView(){
        FragmentManager fm = getSupportFragmentManager();
        mPagerAdapter = new PagerAdapter(fm);

        // Set up the ViewPager with the sections adapter.
        mViewPager =  findViewById(R.id.container);
        mViewPager.setAdapter(mPagerAdapter);




//        if (entryType == MainDevListFragment.EnumMainEntry.EnumMainEntry_Login){
//            mViewPager.setOffscreenPageLimit(4);
//        }
//        else{
//            mViewPager.setOffscreenPageLimit(1);
//        }


        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                selectedTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });



        // Set up the ViewPager with the sections adapter.

//        TabLayout tabLayout = findViewById(R.id.tabs);
//        tabLayout.setupWithViewPager(mViewPager);


        tx1 = findViewById(R.id.tx_1);
        tx2 = findViewById(R.id.tx_2);
        tx3 = findViewById(R.id.tx_3);
        tx4 = findViewById(R.id.tx_4);

        image_1 = findViewById(R.id.image_1);

        image_2 = findViewById(R.id.image_2);
        image_3 = findViewById(R.id.image_3);
        image_4 = findViewById(R.id.image_4);

        tx1.setOnClickListener(this);


        image_1.setOnClickListener(this);


        if (entryType == MainDevListFragment.EnumMainEntry.EnumMainEntry_Login){

            tx2.setOnClickListener(this);
            tx3.setOnClickListener(this);
            tx4.setOnClickListener(this);

            image_2.setOnClickListener(this);
            image_3.setOnClickListener(this);
            image_4.setOnClickListener(this);
        }

        settab();
        tx1.setTextColor(Config.greenColor);
        image_1.setSelected(true);


    }

    public void selectedTab(int position){
        settab();
        if (0 == position){
            tx1.setTextColor(Config.greenColor);
            image_1.setSelected(true);
        }
        else if(1 == position){
            tx2.setTextColor(Config.greenColor);
            image_2.setSelected(true);
        }
        else if(2 == position){
            tx3.setTextColor(Config.greenColor);
            image_3.setSelected(true);
            MediaFragment.newInstance().refreshUI();
        }
        else if(3 == position){
            tx4.setTextColor(Config.greenColor);
            image_4.setSelected(true);
        }
    }

    public void settab()
    {

        //
        tx1.setTextColor(Color.parseColor("#666666"));
        tx2.setTextColor(Color.parseColor("#666666"));
        tx3.setTextColor(Color.parseColor("#666666"));
        tx4.setTextColor(Color.parseColor("#666666"));
        image_1.setSelected(false);
        image_2.setSelected(false);
        image_3.setSelected(false);
        image_4.setSelected(false);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.tx_1:
            case R.id.image_1:
                mViewPager.setCurrentItem(0);
                selectedTab(0);

                break;
            case R.id.tx_2:
            case R.id.image_2:
                mViewPager.setCurrentItem(1);

                selectedTab(1);

                break;
            case R.id.tx_3:
            case R.id.image_3:
                mViewPager.setCurrentItem(2);
                selectedTab(2);
                break;
            case R.id.tx_4:
            case R.id.image_4:
                mViewPager.setCurrentItem(3);
                selectedTab(3);
                break;
            default:
                break;
        }
    }




    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class PagerAdapter extends FragmentPagerAdapter {

        boolean enabledSlide;
        public PagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            switch (position){
                case 0:
                    return MainDevListFragment.newInstance(entryType);
                case 1:
                    return AlarmListFragment.newInstance();
                case 2:
                    return MediaFragment.newInstance();
                case 3:
                    return SystemSettingFragment.newInstance();
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            if (entryType == MainDevListFragment.EnumMainEntry.EnumMainEntry_Login){
                return 4;
            }
            else{
                return 1;
            }

        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {

        }
    }

}