package com.kituri.app.data;

public interface Watcher<T> {
	public void notifyWatchers(T item);
}
