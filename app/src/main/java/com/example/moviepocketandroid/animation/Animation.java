package com.example.moviepocketandroid.animation;

import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;

public class Animation {
    public static AnimationSet createAnimation() {
    // Создание анимации уменьшения на 20%
    ScaleAnimation shrinkAnimation = new ScaleAnimation(1f, 0.8f, 1f, 0.8f,
            android.view.animation.Animation.RELATIVE_TO_SELF, 0.5f, android.view.animation.Animation.RELATIVE_TO_SELF, 0.5f);
    shrinkAnimation.setDuration(100); // Длительность анимации уменьшения в миллисекундах
    shrinkAnimation.setFillAfter(true); // Установка конечного состояния после анимации

    // Создание анимации возврата к исходному размеру
    ScaleAnimation expandAnimation = new ScaleAnimation(0.8f, 1f, 0.8f, 1f,
            android.view.animation.Animation.RELATIVE_TO_SELF, 0.5f, android.view.animation.Animation.RELATIVE_TO_SELF, 0.5f);
    expandAnimation.setDuration(100); // Длительность анимации увеличения в миллисекундах
    expandAnimation.setFillAfter(true); // Установка конечного состояния после анимации

    // Комбинирование анимаций
    AnimationSet animationSet = new AnimationSet(true);
    animationSet.addAnimation(shrinkAnimation);
    animationSet.addAnimation(expandAnimation);
    return animationSet;
}
}
