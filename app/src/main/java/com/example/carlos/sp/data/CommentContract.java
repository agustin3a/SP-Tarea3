package com.example.carlos.sp.data;

import android.provider.BaseColumns;

/**
 * Created by Carlos on 24/02/15.
 */
public class CommentContract {

    public static final class CommentEntry implements BaseColumns {
        public static final String TABLE_NAME = "comment";

        public static final String COLUMN_TEXT = "comment_text";

        public static final String COLUMN_LOC_KEY = "store_id";

    }
}
