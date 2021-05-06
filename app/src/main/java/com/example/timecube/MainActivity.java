package com.example.timecube;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import Tool.TablayoutAdapter;
import Tool.ViewPagerScale;

public class MainActivity extends AppCompatActivity {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private List<Fragment> fragmentList =new ArrayList<>();
    private List<String> fragmenttitle = new ArrayList<>();
    private TablayoutAdapter tablayoutAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        View decorview =getWindow().getDecorView();
        decorview.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                |View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        getWindow().setStatusBarColor(Color.TRANSPARENT);

        tabLayout=findViewById(R.id.tab_layout);
        viewPager=findViewById(R.id.view_pager);
        fragmentList.add(new HomeFragment());
        fragmentList.add(new SettingFragment());
        fragmenttitle.add("");
        fragmenttitle.add("");
        tablayoutAdapter=new TablayoutAdapter(getSupportFragmentManager(),TablayoutAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT,
                fragmentList,fragmenttitle);
        viewPager.setAdapter(tablayoutAdapter);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setPageTransformer(true,new ViewPagerScale());
        tabLayout.setTabRippleColor(ColorStateList.valueOf(Color.BLUE));
        tabLayout.getTabAt(0).setIcon(R.drawable.todo_select);
        tabLayout.getTabAt(1).setIcon(R.drawable.setting_unselect);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int positin=tab.getPosition();
                if(positin==0){
                    tab.setIcon(R.drawable.todo_select);
                }
                else if(positin==1){
                    tab.setIcon(R.drawable.setting_select);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                int position =tab.getPosition();
                if(position==0){
                    tab.setIcon(R.drawable.todo_unselect);
                }
                else if(position==1){
                    tab.setIcon(R.drawable.setting_unselect);
                }
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
}