/**
 * 
 */
package com.kituri.app.model;


/**
 * @author kituri
 * 
 */
public class DataTypeTransformer {


	public static int getTagText(int textLen) {
		final int nBianJu = 32 * 2;// 除字体以外宽度
		final int nTextLen = 26;// (单个字体宽度)
		int len = nBianJu + nTextLen * textLen;
		return len;
	}

}
