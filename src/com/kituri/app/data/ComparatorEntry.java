package com.kituri.app.data;

import java.util.Comparator;


public class ComparatorEntry implements Comparator<Entry> {
	// public int compare(Object arg0, Object arg1) {
	// User user0=(User)arg0;
	// User user1=(User)arg1;
	//
	// //首先比较年龄，如果年龄相同，则比较名字
	//
	// int flag=user0.getAge().compareTo(user1.getAge());
	// if(flag==0){
	// return user0.getName().compareTo(user1.getName());
	// }else{
	// return flag;
	// }
	// }

	// 本类提供排序作用

	@Override
	public int compare(Entry entry01, Entry entry02) {
		// TODO Auto-generated method stub
		long m1 = entry01.entryCompare();
		long m2 = entry02.entryCompare();
		int result = 0;
		if (m1 > m2) {
			result = 1;
		}
		if (m1 < m2) {
			result = -1;
		}
		return result;
	}

}
