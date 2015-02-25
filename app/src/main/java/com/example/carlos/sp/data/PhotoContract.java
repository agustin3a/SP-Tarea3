package com.example.carlos.sp.data;

import android.provider.BaseColumns;

/**
 * Created by Carlos on 24/02/15.
 */
public class PhotoContract {

    public static final class PhotoEntry implements BaseColumns {
        public static final String TABLE_NAME = "photo";

        public static final String COLUMN_URL = "url";

        public static final String COLUMN_DESCRIPTION = "description";

        public static final String COLUMN_FAVORITES_COUNTER = "favorites";

        public static final String COLUMN_STORE_KEY = "store_id";



    }
}
