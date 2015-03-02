package com.example.carlos.sp.data;

import android.provider.BaseColumns;

/**
 * Created by Carlos on 24/02/15.
 */
public class Comment{

    public String text;
    public int store_id;

    public Comment(String text, int store_id) {
        this.text = text;
        this.store_id = store_id;
    }

    public static final class CommentEntry implements BaseColumns {
        public static final String TABLE_NAME = "comments";

        public static final String COLUMN_TEXT = "comment_text";

        public static final String COLUMN_STORE_KEY = "store_id";

    }
}
