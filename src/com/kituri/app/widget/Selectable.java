/**
 * 
 */
package com.kituri.app.widget;

import com.kituri.app.data.Entry;

/**
 * @author Kituri
 *
 */
public interface Selectable<E> {
	
	public void setSelectable(boolean selectable);
	public void setXSelected(boolean selected);
	public void setSelectionListener(SelectionListener<Entry> mListener);
}
