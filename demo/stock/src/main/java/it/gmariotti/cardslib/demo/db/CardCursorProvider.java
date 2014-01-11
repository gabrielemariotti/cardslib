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

import android.app.SearchManager;
import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import java.util.Arrays;

/**
 * @author Gabriele Mariotti (gabri.mariotti@gmail.com)
 */
public class CardCursorProvider extends ContentProvider {

    private CardCursorSQLiteOpenHelper myOpenHelper;
    private static final String TAG = "CardCursorProvider";

    //-----------------------------------------------------------------------------------------------
    //private static final String AUTHORITY = Constants.AUTHORITY_QUAKE;
    //private static final String BASE_PATH = "quakes";
    //public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY+ "/" + BASE_PATH);

    //-----------------------------------------------------------------------------------------------

    private static final int CardCursor_ALLROWS = 1;
    private static final int CardCursor_SINGLE_ROW = 2;
    private static final int CardCursor_SEARCH = 3;

    // Creates a UriMatcher object.
    private static final UriMatcher uriMatcher;

		 /*
		  * The calls to addURI() go here, for all of the content URI patterns that the provider
		  * should recognize. For this snippet, only the calls for messages are shown.
   	      */

    //Populate the UriMatcher object, where a URI ending in 'messages' will
    //correspond to a request for all items, and 'messages/[rowID]'
    //represents a single row.
    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

		   /*
		     * Sets the integer value for multiple rows in messages 3 to 1. Notice that no wildcard is used
		     * in the path
		     */
        uriMatcher.addURI(CardCursorContract.CONTENT_AUTHORITY, CardCursorContract.BASE_PATH_NAME.PATH_CardCursor, CardCursor_ALLROWS);

		    /*
		     * Sets the code for a single row to 2. In this case, the "#" wildcard is
		     * used. "content://com.example.mesmessage.db/messages/3" matches, but
		     * "content://com.example.mesmessage.db/messages doesn't.
		     */
        uriMatcher.addURI(CardCursorContract.CONTENT_AUTHORITY, CardCursorContract.BASE_PATH_NAME.PATH_CardCursor +"/#", CardCursor_SINGLE_ROW);

        //TODO
        uriMatcher.addURI(CardCursorContract.CONTENT_AUTHORITY,
                SearchManager.SUGGEST_URI_PATH_QUERY, CardCursor_SEARCH);
        uriMatcher.addURI(CardCursorContract.CONTENT_AUTHORITY,
                SearchManager.SUGGEST_URI_PATH_QUERY + "/*", CardCursor_SEARCH);
        uriMatcher.addURI(CardCursorContract.CONTENT_AUTHORITY,
                SearchManager.SUGGEST_URI_PATH_SHORTCUT, CardCursor_SEARCH);
        uriMatcher.addURI(CardCursorContract.CONTENT_AUTHORITY,
                SearchManager.SUGGEST_URI_PATH_SHORTCUT + "/*", CardCursor_SEARCH);
    }


    @Override
    public String getType(Uri uri) {

        // Return a string that identifies the MIME type
        // for a Content Provider URI

        switch (uriMatcher.match(uri)) {
            //Type part: vnd
            //Subtype part:
            //If the URI pattern is for a single row: android.cursor.item/
            //If the URI pattern is for more than one row: android.cursor.dir/
            //Provider-specific part: vnd.<name>.<type>
            //You supply the <name> and <type>. The <name> value should be globally unique,
            //and the <type> value should be unique to the corresponding URI pattern.
            //A good choice for <name> is your company's name or some part of your application's
            //Android package name. A good choice for the <type> is a string that identifies the table associated with the URI.
            case CardCursor_ALLROWS: return CardCursorContract.CardCursor.CONTENT_TYPE;
            case CardCursor_SINGLE_ROW: return CardCursorContract.CardCursor.CONTENT_ITEM_TYPE;
            case CardCursor_SEARCH  : return SearchManager.SUGGEST_MIME_TYPE;

            default: throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
    }

    //-----------------------------------------------------------------------------------------------


    @Override
    public boolean onCreate() {
        // Construct the underlying database.
        // Defer opening the database until you need to perform
        // a query or transaction.
        myOpenHelper = new CardCursorSQLiteOpenHelper(getContext(),
                CardCursorSQLiteOpenHelper.DATABASE_NAME, null,
                CardCursorSQLiteOpenHelper.DATABASE_VERSION);

        return true;
    }


    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {

        Log.d(TAG, "query(uri=" + uri + ", proj=" + Arrays.toString(projection) + ")");
        // Open a read-only database.
        SQLiteDatabase db = myOpenHelper.getWritableDatabase();

        // Replace these with valid SQL statements if necessary.
        String groupBy = null;
        String having = null;

        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();

        String rowID="";
        // If this is a row query, limit the result set to the passed in row.
        switch (uriMatcher.match(uri)) {
            case CardCursor_SINGLE_ROW :
                queryBuilder.setTables(CardCursorSQLiteOpenHelper.Tables.CardCursor);
                rowID = uri.getPathSegments().get(1);
                queryBuilder.appendWhere(CardCursorContract.CardCursor.KeyColumns.KEY_ID + "=" + rowID);
                break;
            case CardCursor_ALLROWS :
                queryBuilder.setTables(CardCursorSQLiteOpenHelper.Tables.CardCursor);
                if (TextUtils.isEmpty(sortOrder)) {
                    sortOrder = CardCursorContract.CardCursor.DEFAULT_SORT;
                }
                break;
            default:
                throw new IllegalArgumentException(
                        "Unsupported URI: " + uri);

        }

        Cursor cursor = queryBuilder.query(db, projection, selection,
                selectionArgs, groupBy, having, sortOrder);

        // Make sure that potential listeners are getting notified
        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        return cursor;
    }




    @Override
    public Uri insert(Uri uri, ContentValues values) {

        Log.d(TAG, "insert(uri=" + uri + ", values=" + values.toString() + ")");

        // Open a read / write database to support the transaction.
        SQLiteDatabase db = myOpenHelper.getWritableDatabase();

        // To add empty rows to your database by passing in an empty Content Values
        // object, you must use the null column hack parameter to specify the name of
        // the column that can be set to null.
        String nullColumnHack = null;
        long id=-1;

        if (uriMatcher.match(uri) == CardCursor_ALLROWS) {
            // Insert the values into the table
            id = db.insert(CardCursorSQLiteOpenHelper.Tables.CardCursor, nullColumnHack, values);
        }

        if (id > -1) {
            // Construct and return the URI of the newly inserted row.
            // Equals to Uri.parse(BASE_PATH + "/" + id);
            Uri insertedId = ContentUris.withAppendedId(uri, id);

            // Notify any observers of the change in the data set.
            getContext().getContentResolver().notifyChange(insertedId, null);

            return insertedId;
        }
        else
            throw new SQLException(
                    "Problem while inserting into uri: " + uri);

    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {

        Log.d(TAG, "delete(uri=" + uri + ")");

        // Open a read / write database to support the transaction.
        SQLiteDatabase db = myOpenHelper.getWritableDatabase();
        String rowID="";
        int deleteCount=0;

        // If this is a row URI, limit the deletion to the specified row.
        switch (uriMatcher.match(uri)) {
            case CardCursor_ALLROWS:
                deleteCount = db.delete(
                        CardCursorSQLiteOpenHelper.Tables.CardCursor,
                        selection,
                        selectionArgs);
                break;
            case CardCursor_SINGLE_ROW :
                rowID = uri.getPathSegments().get(1);
                selection = CardCursorContract.CardCursor.KeyColumns.KEY_ID + "=" + rowID
                        + (!TextUtils.isEmpty(selection) ?
                        " AND (" + selection + ')' : "");
                deleteCount = db.delete(CardCursorSQLiteOpenHelper.Tables.CardCursor, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }

        // To return the number of deleted items, you must specify a where
        // clause. To delete all rows and return a value, pass in "1".
        //if (selection == null)
        //    selection = "1";

        if (deleteCount > 0) {
            // Notify any observers of the change in the data set.
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return deleteCount;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {

        Log.d(TAG, "update(uri=" + uri + ", values=" + values.toString() + ")");

        // Open a read / write database to support the transaction.
        SQLiteDatabase db = myOpenHelper.getWritableDatabase();
        String rowID="";
        int updateCount=0;

        // If this is a row URI, limit the deletion to the specified row.
        switch (uriMatcher.match(uri)) {
            case CardCursor_SINGLE_ROW :
                rowID = uri.getPathSegments().get(1);
                selection = CardCursorContract.CardCursor.KeyColumns.KEY_ID + "=" + rowID
                        + (!TextUtils.isEmpty(selection) ?  " AND (" + selection + ')' : "");
                updateCount = db.update(CardCursorSQLiteOpenHelper.Tables.CardCursor,
                        values, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }


        if (updateCount>0){
            // Notify any observers of the change in the data set.
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return updateCount;
    }


    //---------------------------------------------------------------------------------------------------------------------

    private static class CardCursorSQLiteOpenHelper extends SQLiteOpenHelper {

        private static final String DATABASE_NAME = "cardDemoDatabase.db";

        interface Tables {
            String CardCursor = CardCursorContract.CardCursor.TABLE_NAME;

        }

        private static final int DATABASE_VERSION = 1;
        //private static final String DATABASE_TABLE = CardCursorContract.CardCursor.TABLE_NAME;

        public CardCursorSQLiteOpenHelper(Context context, String name,
                                     SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        // Called when no database exists in disk and the helper class needs
        // to create a new one.
        @Override
        public void onCreate(SQLiteDatabase database) {
            CardCursorContract.CardCursor.onCreate(database);

        }

        // Called when there is a database version mismatch, meaning that the version
        // of the database on disk needs to be upgraded to the current version.
        @Override
        public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
            CardCursorContract.CardCursor.onUpgrade(database, oldVersion, newVersion);

        }

    }
}
