package com.example.henry.myapplication1;


import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.henry.myapplication1.util.ChangePsw;
import com.example.henry.myapplication1.util.SysApplication;


public class Drawer_ListActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private SharedPreferences sharedPreferences;
    private String level = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer__list);
        SysApplication.getInstance().addActivity(this);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("云仓仓储物流管理系统");
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        //侧边栏监听器
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        //获取用户权限
        sharedPreferences = getApplicationContext().getSharedPreferences("config", Context.MODE_PRIVATE);
        level = sharedPreferences.getString("level","4");
        //初始化首页
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_content,new Hello()).commit();
        //获取侧边栏View
        final NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        //获取头部View
        View header = navigationView.getHeaderView(0);
        TextView accountName = (TextView) header.findViewById(R.id.textView12);
        accountName.setText(sharedPreferences.getString("account","k"));
        //设置菜单选项监听事件
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener(){
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

                if (id == R.id.nav_camera) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame_content,new Test()).commit();
                    toolbar.setTitle("定仓");
                } else if (id == R.id.nav_gallery) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame_content,new TestTwo()).commit();
                    toolbar.setTitle("调仓");
                } else if (id == R.id.nav_enterpriseInfo) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame_content,new EnterpriseSearch()).commit();
                    toolbar.setTitle("备案企业查询");
                } else if (id == R.id.nav_restart){
                    System.exit(0);
                } else if (id == R.id.nav_exit){
                    SysApplication.getInstance().exit();
                } else if (id == R.id.nav_shopMng){
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame_content,new ShopMng()).commit();
                    toolbar.setTitle("店铺信息管理");
                } else if (id == R.id.nav_chgPsw){
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame_content,new ChangePsw()).commit();
                    toolbar.setTitle("修改用户密码");
                } else if (id == R.id.nav_employeeMng){
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame_content,new EmployeeMng()).commit();
                    toolbar.setTitle("员工信息管理");
                } else if (id == R.id.nav_enterpriseRecord){
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame_content,new EnterpriseRecord()).commit();
                    toolbar.setTitle("企业备案");
                } else if (id == R.id.nav_orderInquiry){
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame_content,new OrderInquiry()).commit();
                    toolbar.setTitle("订单查询");
                } else if (id == R.id.nav_preStorage){
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame_content,new PreStorage()).commit();
                    toolbar.setTitle("预入库查询");
                }
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
        });
        switch (level){
            case "1":
                break;
            case "2":
                hideMenu(navigationView.getMenu().getItem(1).getSubMenu());
                hideMenu(navigationView.getMenu().getItem(2).getSubMenu());
                break;
            case "3":
                hideMenu(navigationView.getMenu().getItem(0).getSubMenu());
                hideMenu(navigationView.getMenu().getItem(2).getSubMenu());
                break;
            case "4":
                hideMenu(navigationView.getMenu().getItem(0).getSubMenu());
                hideMenu(navigationView.getMenu().getItem(1).getSubMenu());
                hideMenu(navigationView.getMenu().getItem(2).getSubMenu());
        }
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }

    //隐藏item
    public void hideMenu(Menu mMenu){
        if(null != mMenu){
            //hidden when first time
            for (int i = 0; i < mMenu.size(); i++){
                mMenu.getItem(i).setVisible(false);
                mMenu.getItem(i).setEnabled(false);
            }
        }
    }
    public void showMenu(Menu mMenu){
        if(null != mMenu){
            for (int i = 0; i < mMenu.size(); i++){
                mMenu.getItem(i).setVisible(true);
                mMenu.getItem(i).setEnabled(true);
            }
        }
    }

    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }
}
