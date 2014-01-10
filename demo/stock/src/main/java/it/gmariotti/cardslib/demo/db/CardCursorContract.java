/*
 * ******************************************************************************
 *   Copyright (c) 2013-2014 Gabriele Mariotti.
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 *  *****************************************************************************
 */

package it.gmariotti.cardslib.demo.db;

import android.content.ContentResolver;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

/**
 * @author Gabriele Mariotti (gabri.mariotti@gmail.com)
 */
public class CardCursorContract {

    private static final String TAG = "CardCursorContract";

    public static final String CONTENT_AUTHORITY = Constants.AUTHORITY_CARDDEMO;
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);


    public static class BASE_PATH_NAME{

        public static final String PATH_CardCursor="CardCursor";

    }



	/*-----------------------------------------------------------------------------------------------*/

    public static class CardCursor{

        /* Table name and Columns name */
        public static final String TABLE_NAME = "CardCursorTable";

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(BASE_PATH_NAME.PATH_CardCursor).build();
        public static final String CONTENT_TYPE =   ContentResolver.CURSOR_DIR_BASE_TYPE +"/vnd.it.gmariotti.cardslib.demo.CardCursor";
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/vnd.it.gmariotti.cardslib.demo.CardCursor";


        //--------------------------------------------------------------------------//

        public static class KeyColumns {

            public static final String KEY_ID = "_id";
            public static final String KEY_TITLE = "title";
            public static final String KEY_SUBTITLE = "subtitle";
            public static final String KEY_HEADER = "header";
            public static final String KEY_THUMBNAIL = "thumb";

        }

        public static class IndexColumns {
            public static final int ID_COLUMN = 0;
            public static final int TITLE_COLUMN = 1;
            public static final int SUBTITLE_COLUMN = 2;
            public static final int HEADER_COLUMN = 3;
            public static final int THUMBNAIL_COLUMN = 4;
        }

        //--------------------------------------------------------------------------//

        // These are the rows that we will retrieve.
        public static final String[] ALL_PROJECTION = new String[]{
                KeyColumns.KEY_ID,
                KeyColumns.KEY_TITLE,
                KeyColumns.KEY_SUBTITLE,
                KeyColumns.KEY_HEADER,
                KeyColumns.KEY_THUMBNAIL

        };


        //--------------------------------------------------------------------------//

        /**
         * Default "ORDER BY" clause.
         */
        public static final String DEFAULT_SORT =
                KeyColumns.KEY_ID + " ASC";


	    /*-----------------------------------------------------------------------------------------------*/

        // SQL statement to create a new database.
        private static final String DATABASE_CREATE = "create table "
                + TABLE_NAME
                + "(" + KeyColumns.KEY_ID + " integer primary key AUTOINCREMENT, "
                + KeyColumns.KEY_TITLE + " TEXT, "
                + KeyColumns.KEY_SUBTITLE + " TEXT, "
                + KeyColumns.KEY_HEADER + " TEXT, "
                + KeyColumns.KEY_THUMBNAIL + " INTEGER "
                + ");";

        public static void onCreate(SQLiteDatabase database) {
            database.execSQL(DATABASE_CREATE);
            populateForTest(database);
        }

        private static void populateForTest(SQLiteDatabase database) {
            for (int i=0;i<500;i++){
                String title= "My Title "+i;
                String subtitle= "My subTitle "+i;
                String header= "My Header "+i;
                int thumb= i%5;
                String sql="INSERT INTO " + TABLE_NAME + " ("+
                        KeyColumns.KEY_TITLE +"," +
                        KeyColumns.KEY_SUBTITLE +"," +
                        KeyColumns.KEY_HEADER + "," +
                        KeyColumns.KEY_THUMBNAIL +
                        ") " +
                        "VALUES ('"+ title +"','" + subtitle +"','" + header + "',"+ thumb + ")";
                database.execSQL(sql);
            }
        }

        public static void onUpgrade(SQLiteDatabase database, int oldVersion,
                                     int newVersion) {
            // Log the version upgrade.
            Log.d(TAG, "Upgrading database from version "
                    + oldVersion + " to " + newVersion
                    + ", which will destroy all old data");

            // Upgrade the existing database to conform to the new version. Multiple
            // previous versions can be handled by comparing oldVersion and newVersion
            // values.

            Log.d(TAG, "Destroying old data during upgrade");

            // The simplest case is to drop the old table and create a new one.
            database.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            // Create a new one.
            onCreate(database);
        }
    }
}
