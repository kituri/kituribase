/**
 * 
 */
package com.kituri.app.data;

import com.kituri.app.model.Intent;

/**
 * @author Kituri
 *
 */
public interface Actionable {
	
	public void setIntent(Intent intent);
	public Intent getIntent();
}
