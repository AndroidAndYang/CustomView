package com.yjz.customview.animation;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.yjz.customview.R;

/**
 * @author: YJZ
 * @date: 2019/7/11 18:11
 * @Des:
 **/
public class AnimationActivity extends AppCompatActivity {

    private FancyFlipView view;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_animation);

        view = findViewById(R.id.view);

        // 原生控件的属性动画
        // imageView.animate().
        //         translationX(200).
        //         translationY(200).
        //         setStartDelay(2000).
        //        setDuration(1000).
        //        start();

        // 自定义view时的属性动画，propertyName一定是自定义View中的属性名，并提供了getter / setter
        // ObjectAnimator animator = ObjectAnimator.ofFloat(view, "radius", 200f);
        // animator.setStartDelay(2000);
        // animator.setDuration(1500);
        // animator.start();


//        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(view, "bottomFlip",45f);
//        objectAnimator.setDuration(1000);
//        objectAnimator.start();


        PropertyValuesHolder topFlip = PropertyValuesHolder.ofFloat("topFlip", -45);
        PropertyValuesHolder flipRotation = PropertyValuesHolder.ofFloat("flipRotation", 360);
        PropertyValuesHolder bottomFlip = PropertyValuesHolder.ofFloat("bottomFlip", 45);
        ObjectAnimator objectAnimator = ObjectAnimator.ofPropertyValuesHolder(view, bottomFlip,flipRotation,topFlip);
        objectAnimator.setDuration(1000);
        objectAnimator.setDuration(3000);
        objectAnimator.start();

    }
}
