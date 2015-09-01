/**
 * 
 */
package com.kituri.app.widget;

/**
 * @author kituri
 *
 * This interface must be implemented by all classes that wish to support populating. 
 */
public interface Populatable<E> {
	
	public void populate(E data);
}
