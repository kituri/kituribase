/**
 * 
 */
package com.kituri.app.controller;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.kituri.app.data.Entry;
import com.kituri.app.data.EntryList;
import com.kituri.app.data.ListEntry;
import com.kituri.app.widget.CustomAnimation;
import com.kituri.app.widget.Populatable;
import com.kituri.app.widget.Proximityable;
import com.kituri.app.widget.Selectable;
import com.kituri.app.widget.SelectionListener;


/**
 * @author Kituri
 */
public class EntryAdapter extends ArrayAdapter<Entry> implements MultiSelectable<Entry>{

	//private Context mContext;
	public static final int MODE_NORMAL = 0;
//	public static final int MODE_RANKING = 1;
	public static final int MODE_SELECTION = 2;
	public static final int STATE_LOADING = 3;
	public static final int STATE_NORMAL = 4;
	
	private int mState;
//	private int mRankType;
	protected int mMode = MODE_NORMAL;
	private SelectionListener<Entry> mCallbackListener;
	protected EntryList mSelectedItems = new EntryList();
	private FragmentManager mFragmentManager;
	private ListEntry mObjects = new ListEntry();
	
	protected SelectionListener<Entry> mListener = new SelectionListener<Entry>() {

		@Override
		public void onSelectionChanged (Entry entry, boolean selected) {
			setSelected(entry, selected);
			if (mCallbackListener != null) {
				mCallbackListener.onSelectionChanged(entry, selected);
			}
		}};
	
//	public EntryAdapter() {
//		super();
//	}
	
		
	public EntryAdapter(Context context) {
		super(context, 0);
	}
	
	public EntryAdapter(Context context, Entry[] objects) {
		super(context, 0, objects);
	}
	
	public EntryAdapter(Context context, List<Entry> objects) {
		super(context, 0, objects);
	}
	
	public void setMode(int mode) {
		if (mode < 0 || mode > 2) {
			throw new IllegalArgumentException("Unsupported mode!");
		}
		mMode = mode;
		notifyDataSetChanged();
	}
	
//	public void setRankType(int type) {
//		mRankType = type;
//	}
	
	public void setState(int state) {
		mState = state;
	}
	
	@Override
	public int getCount() {
		if (mState == STATE_LOADING && super.getCount() > 0) {
			return super.getCount() + 1;
		}
		return super.getCount();
	}

	@Override
	public Entry getItem(int position) {
		if (getCount() <= 0) {
			return null;
		}
		if (position < 0 || position >= getCount()) {
			return null;
		}
		return super.getItem(position);
	}
	
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		//if (mState == STATE_LOADING && position == getCount() - 1) {
			
//			View loadingBar = LayoutInflater.from(getContext()).inflate(R.layout.loading, null);
//			return loadingBar;
		//}
		//long time = System.currentTimeMillis();
		Entry item = this.getItem(position);
		item.setIndex(position);
		Class<?> itemViewClass;
		try {
			itemViewClass = Class.forName(item.getViewName());
		} catch (ClassNotFoundException e) {
			//if(TextUtils.isEmpty(item.getViewName())){				
			////Logger.e("","Class.forName error!!!");
			item.print();
			//}
			throw new RuntimeException(e);
		}
		Object itemView;
		if (convertView != null && itemViewClass.isInstance(convertView)) {
			////Logger.e("costTime", "本次操作复用了item");		
			itemView = convertView;
		} else {
			////Logger.e("costTime", "本次操作new了item");		
//			try {
//				Constructor<?> constructor = itemViewClass.getConstructor(android.content.Context.class);
//				itemView = constructor.newInstance(this.getContext());
//			} catch (Exception e) {
//				itemView = getView(item.getViewName());
//				//throw new RuntimeException(e);
//			}
			try {
				Constructor<?>  con = itemViewClass.getDeclaredConstructor(
						new Class[]{android.content.Context.class,
						android.util.AttributeSet.class});
			//con.setAccessible(true); //设置可访问的权限
			itemView = con.newInstance(getContext(), null);	
			} catch (Exception e) {
				// TODO Auto-generated catch block
				throw new RuntimeException("Can't find view to show the data.---> " + item.getViewName());
			}
			//itemView = getView(item.getViewName());
			
		}
		if (!(itemView instanceof View)) {
			throw new RuntimeException("Can't find specified view to show the data.");
		}
		
		if(itemView instanceof Proximityable){
			((Proximityable<Entry>)itemView).previousData(this.getItem(position - 1));
			((Proximityable<Entry>)itemView).nextData(this.getItem(position + 1));
		}
		
		if (itemView instanceof Populatable) {
			((Populatable<Entry>)itemView).populate(item);
		} else {
			throw new RuntimeException("Can't populate data to specified view.");
		}
//		if (mMode == MODE_RANKING && itemView instanceof Rankable) {
//			((Rankable)itemView).setRankable(true);
//			((Rankable)itemView).setRankType(mRankType);
//			((Rankable)itemView).setRank(position + 1);
//		}
		if (itemView instanceof Selectable) {
			((Selectable<?>)itemView).setSelectable(mMode == MODE_SELECTION);
			((Selectable)itemView).setXSelected(mSelectedItems.contains(item));
			((Selectable<Entry>)itemView).setSelectionListener(mListener);
		}
		
//		if (itemView instanceof Selectable) {
//			((Selectable<?>)itemView).setSelectable(mMode == MODE_SELECTION);
//			((Selectable)itemView).setXSelected(mSelectedItems.contains(item));
//			((Selectable<Entry>)itemView).setSelectionListener(mListener);
//		}
		
		if(itemView instanceof CustomAnimation){
			//KituriAnimation.ScaleOutAnimation((View)itemView);
			((CustomAnimation<Entry>)itemView).onAnimationPlay(item);
		}
		
		if(itemView instanceof FragmentManagerable){
			//KituriAnimation.ScaleOutAnimation((View)itemView);
			((FragmentManagerable)itemView).setFragmentManager(mFragmentManager);
		}
		

		//long costTime = System.currentTimeMillis() - time;
		
		//Logger.e("costTime", "position:" + position + "消耗：" + costTime);
		////Logger.e("costTime", "costTime:" + costTime);		
		////Logger.e("costTime", "position:" + position);		
		////Logger.e("costTime", "viewname:" + item.getViewName());		
		
		//System.out.println("costTime:" + position + "|" + costTime);
		
//		if (itemView instanceof ViewFactory) {
//			return ((ViewFactory)itemView).getView();
//		}
		return (View) itemView;
	}
	
	public ListEntry getListEntry(){
		return mObjects;
	}
	
	public void add(Entry entry) {
		synchronized (entry) {
			mObjects.add(entry);
			super.add(entry);
		}
	}
	
	public void add(Entry entry,int index) {
		synchronized (entry) {
			mObjects.inSet(index, entry);
			super.add(entry);
		}
	}
	
	public void add(Entry[] entries) {
		synchronized (entries) {
			for (Entry entry : entries) {
				add(entry);
			}
		}
	}
	
	public void add(EntryList entries) {
		synchronized (entries) {
			for (Entry entry : entries) {
				add(entry);
			}
		}
	}

	@Override
	public void clear() {
		super.clear();
		mObjects.clear();
		mSelectedItems.clear();
	}

	@Override
	public void remove(Entry object) {
		super.remove(object);
		if(mObjects.getEntries().contains(object)){
			mObjects.getEntries().remove(object);
		}
		mSelectedItems.remove(object);
	}

	@Override
	public void setSelected(Entry entry, boolean selected) {
		if (selected) {
			if (!mSelectedItems.contains(entry)) {
				mSelectedItems.add(entry);
			}
		} else {
			mSelectedItems.remove(entry);
		}
	}

	@Override
	public void setSelectable(boolean selectable) {
		mMode = selectable ? MODE_SELECTION : MODE_NORMAL;
	}

	@Override
	public void setSelectionListener(SelectionListener<Entry> listener) {
		mCallbackListener = listener;
	}

	@Override
	public void setXSelected(boolean selected) {
		for (int position = 0; position < getCount(); position++) {
			setSelected(getItem(position), selected);
		}
	}

	public void setFragmentManager(FragmentManager mFragmentManager) {
		this.mFragmentManager = mFragmentManager;
	}
	
	//单选
	public void setSingleSelected(Entry entry) {
		mSelectedItems.clear();
		mSelectedItems.add(entry);
	}
	
	@Override
	public void setSelected(ArrayList<Entry> entries, boolean selected) {
		if (entries == null) {
			return;
		}
		synchronized (entries) {
			for (Entry entry : entries) {
				setSelected(entry, selected);
			}
		}
	}
	
	@Override
	public EntryList getSelections() {
		return mSelectedItems;
	}

}
