package com.yh.parkingpartner.util;

import java.text.DecimalFormat;

public class Util {
    public static final int MAP_MY_ARROUND=0;
    public static final int MAP_DESTINATION_ARROUND=1;
    public static final int MAP_ONE_PICK=2;

    public static final int AUTOCOMPLETE_REQUEST_CODE = 1;
    public static final int SEARCH_ACTIVITY_REQUEST_CODE = 2;

    public static DecimalFormat myDecFormatter = new DecimalFormat("###,###");
    public static DecimalFormat myFloatFormatter = new DecimalFormat("###,###.##");
}
