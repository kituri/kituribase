package com.kituri.app.data;

import java.util.Comparator;


public class ComparatorRevereEntry implements Comparator<Entry>{

	@Override
	public int compare(Entry entry01, Entry entry02) {
		// TODO Auto-generated method stub
		long m1 = entry01.entryCompare();
		long m2 = entry02.entryCompare();
		int result = 0;
		if (m1 > m2) {
			result = -1;
		}
		if (m1 < m2) {
			result = 1;
		}
		
		if(m1==m2){
			if (entry01.getIndex() > entry01.getIndex()) {
				result = -1;
			}
			if (entry01.getIndex() < entry01.getIndex()) {
				result = 1;
			}
		}
		return result;
	}

}
