package com.example.carlos.sp.data;

import android.provider.BaseColumns;

/**
 * Created by Carlos on 24/02/15.
 */
public class StoreContract {

    public static final class StoreEntry implements BaseColumns {
        public static final String TABLE_NAME = "store";

        public static final String COLUMN_NAME = "name";

        public static final String COLUMN_ADDRESS= "address";

        public static final String COLUMN_PHONE = "phone";

        public static final String COLUMN_SCHEDULE = "schedule";

        public static final String COLUMN_WEBSITE = "website";

        public static final String COLUMN_EMAIL = "email";

        public static final String COLUMN_FAVORITES_COUNTER = "favorites";

        public static final String COLUMN_LOCATION = "location";

    }

}
