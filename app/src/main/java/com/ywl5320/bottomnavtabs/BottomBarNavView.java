package com.ywl5320.bottomnavtabs;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by pengganggui on 2018/8/24.
 * 自定义的底部导航栏
 */

public class BottomBarNavView extends RelativeLayout {

    //图片的url
    private String[] imageUrl;
    //底部导航栏的item
    private TabBottomBar[] bottomBars = new TabBottomBar[5];
    private Context context;
    //点击事件
    private OnItemOnclickListener onItemOnclickListener;

    public BottomBarNavView(Context context) {
        this(context, null);
    }

    public BottomBarNavView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BottomBarNavView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        LayoutInflater.from(context).inflate(R.layout.layout_bottom_bar, this, true);
        bottomBars[0] = findViewById(R.id.home_tab);
        bottomBars[1] = findViewById(R.id.classify_tab);
        bottomBars[2] = findViewById(R.id.find_tab);
        bottomBars[3] = findViewById(R.id.store_tab);
        bottomBars[4] = findViewById(R.id.profile_tab);
    }

    /**
     * 设置底部item的标题
     * @param titles 标题数组
     * @return
     */
    public BottomBarNavView setTitles(String[] titles) {
        if (titles != null) {
            for (int i = 0; i < titles.length; i++) {
                bottomBars[i].setName(titles[i]);
                final int finalI = i;
                bottomBars[i].setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        setSelectStyle(finalI);
                        if (onItemOnclickListener != null) {
                            onItemOnclickListener.onItemClick(finalI);
                        }
                    }
                });
            }
        }
        return this;
    }

    /**
     * 设置底部item的图片
     * @param imageUrl 图片url数组
     * @return
     */
    public BottomBarNavView setImageUrls(String[] imageUrl) {
        if(imageUrl!=null){
            this.imageUrl = imageUrl;
            readyToDrawable();
        }
        return this;
    }

    /**
     * 设置是否显示小红点
     * @param index
     * @param isShowDot
     * @return
     */
    public BottomBarNavView setIsShowDot(int index, boolean isShowDot) {
        if (index<bottomBars.length&&index>=0){
            for (int i = 0; i < bottomBars.length; i++) {
                if (i == index) {
                    bottomBars[i].setIsShowDot(isShowDot);
                }
            }
        }
        return this;
    }

    /**
     * 设置底部item文字的color
     * @param colors 文字颜色的数组
     * @return
     */
    public BottomBarNavView setColorStyles(String[] colors) {
        if (colors!=null){
            ColorStateList drawable = new ColorStateList(new int[][]{
                    new int[]{android.R.attr.state_selected}
                    , new int[]{android.R.attr.state_pressed}
                    , new int[]{android.R.attr.state_focused}
                    , new int[]{}}
                    , new int[]{Color.parseColor(colors[0].trim())
                    , Color.parseColor(colors[0].trim())
                    , Color.parseColor(colors[0].trim())
                    , Color.parseColor(colors[1].trim())});
            for (int i = 0; i < bottomBars.length; i++) {
                bottomBars[i].setTvColors(drawable);
            }
        }
        return this;
    }


    /**
     * 设置选中/未选中的style
     *
     * @param index
     */
    public BottomBarNavView setSelectStyle(int index) {
        if (index>=0&&index<bottomBars.length){
            int size = bottomBars.length;
            for (int i = 0; i < size; i++) {
                if (i == index) {
                    bottomBars[i].setIsSelected(true);
                } else {
                    bottomBars[i].setIsSelected(false);
                }
            }
        }
        return this;
    }

    /**
     * 点击事件
     * @param onItemOnclickListener
     */
    public void setOnItemOnclickListener(OnItemOnclickListener onItemOnclickListener) {
        this.onItemOnclickListener = onItemOnclickListener;
    }

    /**
     * 将图片的url转换为Bitmap，再将bitmap转换为drawable，
     * 最后封装到一个stateListDrawable中，转换过程在子线程
     */
    private void readyToDrawable() {
        final int length = imageUrl.length;
        Observable.just(imageUrl)
                .subscribeOn(Schedulers.io())
                .map(new Func1<String[], Drawable[]>() {
                    @Override
                    public Drawable[] call(String[] strings) {
                        Drawable[] drawables = new Drawable[10];
                        for (int i = 0; i < strings.length; i++) {
                            drawables[i] = new BitmapDrawable(getResources(), getImageFormUrl2Bitmap(imageUrl[i]));
                        }
                        return drawables;
                    }
                })
                .map(new Func1<Drawable[], List<StateListDrawable>>() {
                    @Override
                    public List<StateListDrawable> call(Drawable[] drawables) {
                        List<StateListDrawable> list = new ArrayList<>();
                        for (int j = 0; j < length; j = j + 2) {
                            StateListDrawable stateListDrawable = new StateListDrawable();
                            stateListDrawable.addState(new int[]{android.R.attr.state_selected}, drawables[j + 1]);
                            stateListDrawable.addState(new int[]{android.R.attr.state_pressed}, drawables[j + 1]);
                            stateListDrawable.addState(new int[]{android.R.attr.state_focused}, drawables[j + 1]);
                            stateListDrawable.addState(new int[]{}, drawables[j]);
                            list.add(stateListDrawable);
                        }
                        return list;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<StateListDrawable>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        bottomBars[0].setIcon(getResources().getDrawable(R.drawable.tab_home));
                        bottomBars[1].setIcon(getResources().getDrawable(R.drawable.tab_classify));
                        bottomBars[2].setIcon(getResources().getDrawable(R.drawable.tab_find));
                        bottomBars[3].setIcon(getResources().getDrawable(R.drawable.tab_store));
                        bottomBars[4].setIcon(getResources().getDrawable(R.drawable.tab_profile));
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(List<StateListDrawable> stateListDrawables) {
                        for (int i = 0; i < stateListDrawables.size(); i++) {
                            bottomBars[i].setIcon(stateListDrawables.get(i));
                        }
                    }
                });

    }

    /**
     * 使用Glide将url转换为Bitmap
     * @param url 图片url
     * @return
     */
    private Bitmap getImageFormUrl2Bitmap(String url) {
        try {
            return Glide.with(context).load(url).asBitmap() //必须.centerCrop()
                    .into(25, 25)
                    .get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    public interface OnItemOnclickListener {
        void onItemClick(int index);
    }
}
