package com.yjz.customview._layout;

import android.os.Bundle;
import android.view.ViewGroup;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.yjz.customview.R;

/**
 * @author YJZ
 * @date 2019/7/18
 * @description
 */
public class LayoutActivity extends AppCompatActivity {

    public static final String[] CITY = {
            "北京市", "天津市", "上海市", "重庆市 ",
            "香港特别行政区", "澳门特别行政区", "哈尔滨市",
            "阿城市 ", "双城市", "尚志市", "五常市",
            "齐齐哈尔市", "讷河市", "鸡西市", "虎林市",
            "密山市", "鹤岗市", "双鸭山市", "大庆市",
            "伊春市", "铁力市", "佳木斯市", "同江市",
            "富锦市", "七台河市 ", "牡丹江市", "海林市",
            "宁安市", "穆棱市", "黑河市", "北安市",
            "五大连池市", "绥化市", "安达市", "肇东市",
            "海伦市", "长春市", "九台市", "榆树市", "德惠市",
            "吉林市", "蛟河市", "桦甸市", "舒兰市", "磐石市",
            "四平市", "公主岭市", "双辽市", "辽源市", "通化市"
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout);

        TagLayout tagLayout = findViewById(R.id.tag_layout);

        for (String s : CITY) {
            ColoredTextView coloredTextView = new ColoredTextView(this, null);
            coloredTextView.setLayoutParams(new ViewGroup.MarginLayoutParams(ViewGroup.MarginLayoutParams.WRAP_CONTENT, ViewGroup.MarginLayoutParams.WRAP_CONTENT));
            coloredTextView.setText(s);
            tagLayout.addView(coloredTextView);
        }
    }
}
