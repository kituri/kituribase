/**
 * 
 */
package com.kituri.app.data;


/**
 * @author kituri
 * List用这个
 */
public class ListEntry extends Entry{

	private static final long serialVersionUID = 8369729682149235659L;
	
	private EntryList mEntries = new EntryList();
	
	//private Watcher<ListEntry> mWatcher;
	
	//setSelection
	
	public void add(Entry entry) {
		mEntries.add(entry);
//		if (mWatcher != null) {
//			mWatcher.notifyWatchers(this);
//		}
	}
	
	public void add(Entry entry,int index){
		mEntries.add(0, entry);
//		if (mWatcher != null) {
//			mWatcher.notifyWatchers(this);
//		}
	}
	
	public void inSet(int index, Entry entry) {
		mEntries.add(index, entry);
//		if (mWatcher != null) {
//			mWatcher.notifyWatchers(this);
//		}
	}

	public void remove(Entry entry) {
		mEntries.remove(entry);
//		if (mWatcher != null) {
//			mWatcher.notifyWatchers(this);
//		}
	}
	
	public void clear() {
		mEntries.clear();
//		if (mWatcher != null) {
//			mWatcher.notifyWatchers(this);
//		}
	}
	
//	public void setWatcher(Watcher<ListEntry> watcher) {
//		this.mWatcher = watcher;
//	}
	
	public EntryList getEntries() {
		return mEntries;
	}
	
	public void print(){
		for(Entry entry:mEntries){
			entry.print();
		}
	}
	
//	@Override
//	public int getSelectPosition() {
//		// TODO Auto-generated method stub
//		return 0;
//	}

	
}
