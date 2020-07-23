package com.shoesdemo.utils;


import com.shoesdemo.data.ShoesModule;

import java.util.Comparator;
import java.util.regex.Pattern;

/**
 * @author xiaanming
 */
public class PinyinComparator implements Comparator<ShoesModule> {

    public int compare(ShoesModule o1, ShoesModule o2) {
        if (o1.getLetter().equals("#")
                || o2.getLetter().equals("@")) {
            return 1;
        } else if (o1.getLetter().equals("@")
                || o2.getLetter().equals("#")) {
            return -1;
        } else if (isNumeric(o1.getLetter()) || isNumeric(o2.getLetter())) {
            return 1;
        } else {
            return o1.getLetter().compareTo(o2.getLetter());
        }
    }

    public boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        return pattern.matcher(str).matches();
    }

}
