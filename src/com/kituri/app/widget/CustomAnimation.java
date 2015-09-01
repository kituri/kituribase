/**
 * 
 */
package com.kituri.app.widget;


/**
 * @author Kituri
 *
 * 自定义动画接口，作用于widget，若在ListView中的某个widget需要用到加载动画，请实现该接口。
 */
public interface CustomAnimation<Entry> {
     //public Boolean onAnimationPlayed();
     public void onAnimationPlay(Entry entry);
}
