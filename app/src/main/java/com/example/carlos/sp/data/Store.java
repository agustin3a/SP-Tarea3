package com.example.carlos.sp.data;

import android.provider.BaseColumns;

/**
 * Created by Carlos on 24/02/15.
 */
public class Store {

    public int id, favorites;
    public String name,address,phone,schedule,website,email,location;

    public Store(int id, String name, String address, String phone, String schedule, String website, String email, int favorites, String location){
        this.id = id;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.schedule = schedule;
        this.website = website;
        this.email = email;
        this.favorites = favorites;
        this.location = location;
    }

    public static final class StoreEntry implements BaseColumns {
        public static final String TABLE_NAME = "stores";

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
