package com.koreaIT.BAM;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Util {
	public static String getDate() {
        Date now = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return formatter.format(now);
    }
}
