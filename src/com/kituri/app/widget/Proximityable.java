/**
 * 
 */
package com.kituri.app.widget;

/**
 * @author kituri
 *
 * 这接口实现了相近的方式
 */
public interface Proximityable<E> {
	
	public void previousData(E data);
	public void nextData(E data);
	
}
