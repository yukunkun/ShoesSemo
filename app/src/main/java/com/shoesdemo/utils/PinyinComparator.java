package com.shoesdemo.utils;


import com.shoesdemo.data.ShoesModule;

import java.util.Comparator;

/**
 * 
 * @author xiaanming
 *
 */
public class PinyinComparator implements Comparator<ShoesModule> {

	public int compare(ShoesModule o1, ShoesModule o2) {
		if (o1.getShoes().getGoodsNo().equals("@")
				|| o2.getShoes().getGoodsNo().equals("#")) {
			return -1;
		} else if (o1.getShoes().getGoodsNo().equals("#")
				|| o2.getShoes().getGoodsNo().equals("@")) {
			return 1;
		} else {
			return o1.getShoes().getGoodsNo().compareTo(o2.getLetter());
		}
	}

}
