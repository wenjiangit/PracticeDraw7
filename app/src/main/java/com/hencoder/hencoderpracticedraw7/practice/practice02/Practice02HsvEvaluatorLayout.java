package com.hencoder.hencoderpracticedraw7.practice.practice02;

import android.animation.ObjectAnimator;
import android.animation.TypeEvaluator;
import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.hencoder.hencoderpracticedraw7.R;

public class Practice02HsvEvaluatorLayout extends RelativeLayout {
    Practice02HsvEvaluatorView view;
    Button animateBt;

    public Practice02HsvEvaluatorLayout(Context context) {
        super(context);
    }

    public Practice02HsvEvaluatorLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public Practice02HsvEvaluatorLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

        view = (Practice02HsvEvaluatorView) findViewById(R.id.objectAnimatorView);
        animateBt = (Button) findViewById(R.id.animateBt);

        animateBt.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ObjectAnimator animator = ObjectAnimator.ofInt(view, "color", 0xffff0000, 0xff00ff00);
                animator.setEvaluator(new HsvEvaluator()); // 使用自定义的 HsvEvaluator
                animator.setInterpolator(new LinearInterpolator());
                animator.setDuration(2000);
                animator.start();
            }
        });
    }

    private class HsvEvaluator implements TypeEvaluator<Integer> {

        float start_hsv[] = new float[3];
        float end_hsv[] = new float[3];

        float out_hsv[] = new float[3];


        // 重写 evaluate() 方法，让颜色按照 HSV 来变化
        @Override
        public Integer evaluate(float fraction, Integer startValue, Integer endValue) {

            //先将agb格式颜色转化为hsv格式
            Color.colorToHSV(startValue, start_hsv);
            Color.colorToHSV(endValue, end_hsv);

            //进行比例运算
            out_hsv[0] = start_hsv[0] + fraction * (end_hsv[0] - start_hsv[0]);
            out_hsv[1] = start_hsv[1] + fraction * (end_hsv[1] - start_hsv[1]);
            out_hsv[2] = start_hsv[2] + fraction * (end_hsv[2] - start_hsv[2]);


            int alpha = startValue >> 24 + (int) ((endValue >> 24 - startValue >> 24) * fraction);

            //再转化为rgb格式
            return Color.HSVToColor(alpha, out_hsv);
        }
    }
}
