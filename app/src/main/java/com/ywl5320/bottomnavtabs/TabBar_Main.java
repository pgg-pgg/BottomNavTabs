package com.ywl5320.bottomnavtabs;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


/**
 * Created by pgg on 2018/5/3.
 */

public class TabBar_Main extends LinearLayout {

    private String mName;
    private Drawable mIcon;
    private ImageView sIconImgView;
    private TextView sNameTv,sDotTv;
    private boolean isShowDot,isSelected;
    private int imgWidth,imgHeight;
    private ColorStateList tvColors;

    public TabBar_Main(Context context) {
        super(context,null);
    }

    public TabBar_Main(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.tabbar_main_2,this,true);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TabBar_Attr);
        mName = typedArray.getString(R.styleable.TabBar_Attr_name);
        mIcon = typedArray.getDrawable(R.styleable.TabBar_Attr_icon);
        isShowDot=typedArray.getBoolean(R.styleable.TabBar_Attr_isShowDot,false);
        imgWidth= (int) typedArray.getDimension(R.styleable.TabBar_Attr_imgWidth,dip2px(context,25.0f));
        imgHeight= (int) typedArray.getDimension(R.styleable.TabBar_Attr_imgHeight,dip2px(context,25.f));
        isSelected=typedArray.getBoolean(R.styleable.TabBar_Attr_isSelected,false);
        tvColors=typedArray.getColorStateList(R.styleable.TabBar_Attr_tvColors);
        typedArray.recycle();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        sIconImgView = findViewById(R.id.icon_tabbar);
        sNameTv = findViewById(R.id.name_tabbar);
        sDotTv=findViewById(R.id.msg_find);

        if (!TextUtils.isEmpty(mName)) setName(mName);
        if (mIcon != null) {
            setIcon(mIcon);
        }
        if (imgWidth>0&&imgHeight>0){
            setImgSize(imgWidth,imgHeight);
        }
        setIsShowDot(isShowDot);
        setIsSelected(isSelected);
        if (tvColors!=null){
            setTvColors(tvColors);
        }
    }

    public void setTvColors(ColorStateList colorDrawable){
        sNameTv.setTextColor(colorDrawable);
    }

    public void setName(String name) {
        sNameTv.setText(name);
    }

    public void setIcon(Drawable icon) {
        sIconImgView.setImageDrawable(icon);
    }

    public void setImgSize(int imgWidth,int imgHeight){
        sIconImgView.getLayoutParams().width=imgWidth;
        sIconImgView.getLayoutParams().height=imgHeight;
    }

    public void setIsShowDot(boolean isShowDot){
        sDotTv.setVisibility(isShowDot?VISIBLE:GONE);
    }

    public void setIsSelected(boolean isSelected){
        sIconImgView.setSelected(isSelected);
        sNameTv.setSelected(isSelected);
    }

    private int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }
}
