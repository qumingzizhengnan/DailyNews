package example.com.daliynews;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import cn.sharesdk.onekeyshare.OnekeyShare;
import example.com.daliynews.Fragment.TabFragmentHome;
import example.com.daliynews.Fragment.TabFragmentPolitics;
import example.com.daliynews.Fragment.TabFragmentPopular;
import example.com.daliynews.Fragment.TabFragmentVideo;
import example.com.daliynews.database.DBOperation;
import example.com.daliynews.until.NetWorkUtil;
import example.com.daliynews.until.SharedPreferenceCacheUtil;



public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    //these components are used to show pages
    private TabLayout mTabLayout = null;
    private ViewPager mViewPager ;
    public Fragment[] mFragmentArrays = new Fragment[4];
    private String[] mTabTitles = new String[4];
    DBOperation mDbOperate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //if we don't have internet at first ,show some msg
        if(!NetWorkUtil.isNetworkConnected(this)){
            AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
            dialog.setTitle("Warning");
            dialog.setMessage("You don't have the internet connecting, we cann't offer service for you.");
            dialog.setCancelable(false);
            dialog.setPositiveButton("OK", new DialogInterface.
                    OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });

            dialog.show();
        }

        //initial the database
        mDbOperate = new DBOperation(getApplication());

        //init sharedpreference
        SharedPreferenceCacheUtil.init(getApplication());
        Log.d("tag","初始化sharaePreference 成功");

        //Toolbar +  drawerlayout
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        //分页显示 tab + viewpager
        mTabLayout = (TabLayout) findViewById(R.id.tab_layout);
        mViewPager = (ViewPager) findViewById(R.id.tab_viewpager);

        mTabTitles[0] = "Home";
        mTabTitles[1] = "Business";
        mTabTitles[2] = "Politics";
        mTabTitles[3] = "Video";
        //固定三个板块间距
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);

        //实例化三个fragment 每个fragment采用不同的recycleView 适配器，实现不同布局的页面
        mFragmentArrays[0] = TabFragmentHome.newInstance();
        mFragmentArrays[1] = TabFragmentPopular.newInstance();
        mFragmentArrays[2] = TabFragmentPolitics.newInstance();
        mFragmentArrays[3] = TabFragmentVideo.newInstance();

        PagerAdapter pagerAdapter = new MyViewPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(pagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);


    }


    //ViewPager的适配器

    /**
     * adapter for viewpager
     */
    final class MyViewPagerAdapter extends FragmentPagerAdapter {

        public MyViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentArrays[position];
        }

        @Override
        public int getCount() {
            return mFragmentArrays.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTabTitles[position];
        }
    }

    /**
     * the drawerlayout is open when we click back button, close it first
     */
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }



    /**
     * show slide bar and define response for those button
     */
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.clear_cache) {
            mDbOperate.clearCache();
            Toast.makeText(this, "Clear All Cache", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_share) {

            //share my github for this apps
            //Toast.makeText(this, "nav_share", Toast.LENGTH_SHORT).show();
//            Intent textIntent = new Intent(Intent.ACTION_SEND);
//            textIntent.setType("text/plain");
//            textIntent.putExtra(Intent.EXTRA_TEXT, "https://github.com/Alan-CQU/DailyNews");
//            startActivity(Intent.createChooser(textIntent, "分享github到"));

            showShare();

        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * for share to other APPs
     */
    private void showShare() {
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();

        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setTitle("分享此App");
        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
        oks.setTitleUrl("https://github.com/Alan-CQU/DailyNews");
        // text是分享文本，所有平台都需要这个字段
        oks.setText("DailyNews");
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl("https://github.com/Alan-CQU/DailyNews");


        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        //oks.setComment("我是测试评论文本");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite(getString(R.string.app_name));
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl("http://sharesdk.cn");

        // 启动分享GUI
        oks.show(this);
    }


}
