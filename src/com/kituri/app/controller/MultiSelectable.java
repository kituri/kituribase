/**
 * 
 */
package com.kituri.app.controller;

import java.util.ArrayList;

import com.kituri.app.widget.Selectable;

/**
 * @author Kituri
 *
 */
public interface MultiSelectable<E> extends Selectable<E> {
	public void setSelected(E entry, boolean selected);
	public void setSelected(ArrayList<E> entries, boolean selected);
	public ArrayList<E> getSelections();
}
