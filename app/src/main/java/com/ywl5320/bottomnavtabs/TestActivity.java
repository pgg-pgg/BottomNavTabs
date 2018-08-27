package com.ywl5320.bottomnavtabs;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pengganggui on 2018/8/24.
 */

public class TestActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private BottomBarNavView bottomBarNavView;

    private ViewPagerAdapter viewPagerAdapter;

    private List<Fragment> fragmentPages;
    private FragmentPage fragmentPage1;
    private FragmentPage fragmentPage2;
    private FragmentPage fragmentPage3;
    private FragmentPage fragmentPage4;
    private FragmentPage fragmentPage5;

    private String[] titles = {"附近", "动态", "消息", "发现", "我的"};
    private String[] colorStyle={"#303F9F","#333333"};

    private String[] image_url = {
            "https://img1.tuhu.org/activity/image/85f6/71b3/234a37a56b19b7ef4d1080c6_w150_h150.png",
            "https://img1.tuhu.org/activity/image/521c/9f50/c1e5c3acc537beac2ae4c3b1_w150_h150.png",
            "https://img4.tuhu.org/activity/image/3576/66dc/2c3d0ec1aae9d4279103adf5_w150_h150.png",
            "https://img1.tuhu.org/activity/image/60f2/ff19/6f17519372632f7ca5a65a19_w150_h150.png",
            "https://img1.tuhu.org/activity/image/254f/b96e/9f21d4bb31cda01f2a22e00e_w186_h180.png",
            "https://img1.tuhu.org/activity/image/1409/c4ab/bde033f4d5dc60bc35607ad0_w186_h180.png",
            "https://img4.tuhu.org/activity/image/fd4f/e799/11d00c371ab595161e9145e7_w150_h150.png",
            "https://img1.tuhu.org/activity/image/cdee/7483/bf92579b61b0c483b9917efe_w150_h150.png",
            "https://img1.tuhu.org/activity/image/4d50/99cc/790dc127d0f28ba65e2bdc1f_w150_h150.png",
            "https://img1.tuhu.org/activity/image/c4d5/39c0/3c8c15d449c8fe3a51c7a7df_w150_h156.png"
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);
        bottomBarNavView= (BottomBarNavView) findViewById(R.id.bottomBar);
        bottomBarNavView.setImageUrls(image_url).setTitles(titles).setColorStyles(colorStyle).setIsShowDot(2,false).setSelectStyle(0);
        viewPager = (ViewPager) findViewById(R.id.vp_fragment);
        initFragments();

        bottomBarNavView.setOnItemOnclickListener(new BottomBarNavView.OnItemOnclickListener() {
            @Override
            public void onItemClick(int index) {
                viewPager.setCurrentItem(index, true);
            }
        });

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                viewPager.setCurrentItem(position, false);
                bottomBarNavView.setSelectStyle(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void initFragments()
    {
        fragmentPages = new ArrayList<>();
        fragmentPage1 = new FragmentPage();
        fragmentPage1.setTitle("1");
        fragmentPage2 = new FragmentPage();
        fragmentPage2.setTitle("2");
        fragmentPage3 = new FragmentPage();
        fragmentPage3.setTitle("3");
        fragmentPage4 = new FragmentPage();
        fragmentPage4.setTitle("4");
        fragmentPage5 = new FragmentPage();
        fragmentPage5.setTitle("5");

        fragmentPages.add(fragmentPage1);
        fragmentPages.add(fragmentPage2);
        fragmentPages.add(fragmentPage3);
        fragmentPages.add(fragmentPage4);
        fragmentPages.add(fragmentPage5);

        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), fragmentPages);
        viewPager.setAdapter(viewPagerAdapter);
    }
}
