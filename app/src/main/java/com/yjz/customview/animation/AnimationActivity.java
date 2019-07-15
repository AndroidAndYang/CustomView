package com.yjz.customview.animation;

import android.animation.*;
import android.graphics.Point;
import android.os.Bundle;
import android.view.animation.AnimationSet;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.yjz.customview.R;
import com.yjz.customview.util.Utils;

/**
 * @author: YJZ
 * @date: 2019/7/11 18:11
 * @Des:
 **/
public class AnimationActivity extends AppCompatActivity {

    private PointView view;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_animation);

        view = findViewById(R.id.view);

        // 1.原生控件进行属性动画
        // 原生控件的属性动画
        // imageView.animate().
        //         translationX(200).
        //         translationY(200).
        //         setStartDelay(2000).
        //        setDuration(1000).
        //        start();

        // 2.自定义view进行属性动画并且只有一个属性做动画
        // 自定义view时的属性动画，propertyName一定是自定义View中的属性名，并提供了getter / setter
        // ObjectAnimator animator = ObjectAnimator.ofFloat(view, "radius", 200f);
        // animator.setStartDelay(2000);
        // animator.setDuration(1500);
        // animator.start();


        // 3.自定义view做属性动画，且属性大于1，依次执行
        // ObjectAnimator bottomFlip = ObjectAnimator.ofFloat(view, "bottomFlip", 45f);
        // bottomFlip.setDuration(1000);
        // ObjectAnimator flipRotation = ObjectAnimator.ofFloat(view, "flipRotation", 270f);
        // flipRotation.setDuration(1000);
        // ObjectAnimator topFlip = ObjectAnimator.ofFloat(view, "topFlip", -45);
        // topFlip.setDuration(1000);
        // 当需要对多个属性进行动画时,可以使用AnimatorSet
        // AnimatorSet animatorSet = new AnimatorSet();
        // animatorSet.playSequentially(bottomFlip, flipRotation, topFlip);
        // animatorSet.setStartDelay(1000);
        // animatorSet.start();

        // 4.自定义view做属性动画，且属性大于1（另外一种方法对多个属性动画并发执行）
        // PropertyValuesHolder bottomFlipValueHolder = PropertyValuesHolder.ofFloat("bottomFlip", 45f);
        // PropertyValuesHolder flipRotationValueHolder = PropertyValuesHolder.ofFloat("flipRotation", 270f);
        // PropertyValuesHolder topFlipValueHolder = PropertyValuesHolder.ofFloat("topFlip", -45f);
        // ObjectAnimator objectAnimator = ObjectAnimator.ofPropertyValuesHolder(view, bottomFlipValueHolder, flipRotationValueHolder, topFlipValueHolder);
        // objectAnimator.setStartDelay(1000);
        // objectAnimator.setDuration(1500);
        // objectAnimator.start();

        // 5.回弹动画，对View进行X轴的平移（类似于速度插值器，先加速后减速）
        // loat length = Utils.INSTANCE.dp2px(300f);
        // / 0s X值的位置
        // eyframe keyframe1 = Keyframe.ofFloat(0, 0);
        // / 0.2s X值的位置
        // eyframe keyframe2 = Keyframe.ofFloat(0.2f, 1.5f * length);
        // / 0.8s X值的位置
        // eyframe keyframe3 = Keyframe.ofFloat(0.8f, 0.6f * length);
        // / 0.1s X值的位置
        // eyframe keyframe4 = Keyframe.ofFloat(1, 1 * length);
        // ropertyValuesHolder viewHolder = PropertyValuesHolder.ofKeyframe("translationX", keyframe1, keyframe2, keyframe3, keyframe4);
        // bjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(view, viewHolder);
        // nimator.setStartDelay(1000);
        // nimator.setDuration(2000);
        // nimator.start();

        // 6.自定义view属性不是基本属性时的属性动画
        // Point point = new Point((int) Utils.INSTANCE.dp2px(200f), (int) Utils.INSTANCE.dp2px(200f));
        // ObjectAnimator pointAnimation = ObjectAnimator.ofObject(view, "point", new PointTypeEvaluator(), point);
        // pointAnimation.setStartDelay(1000);
        // pointAnimation.setDuration(1500);
        // pointAnimation.start();
    }

    /**
     * 当自定义View需要进行属性动画且属性不是基本类型的是，需要自定义View实现TypeEvaluator接口
     */
    class PointTypeEvaluator implements TypeEvaluator<Point> {

        /**
         * 当view进行属性动画是的过程计算
         * @param fraction 过程的时间
         * @param startValue 刚开始的point
         * @param endValue 结束的point
         * @return
         */
        @Override
        public Point evaluate(float fraction, Point startValue, Point endValue) {
            // 好比从 (1, 1) 到 (5, 5)  时间为 fraction: 0.2
            //  x: 1 + (5 - 1) * 0.2
            //  y: 1 + (5 - 1) * 0.2
            int x = (int) (startValue.x + (endValue.x - startValue.x) * fraction);
            int y = (int) (startValue.y + (endValue.y - startValue.y) * fraction);
            return new Point(x, y);
        }
    }
}
