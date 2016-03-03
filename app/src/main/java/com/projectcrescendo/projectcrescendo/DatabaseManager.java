package com.projectcrescendo.projectcrescendo;

import android.content.Context;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

/**
 * A database manager to open the crescendo.db SQLite file and get a readable copy.
 * Using the SQLiteAssetHelper Library; originally written by Jeff Gilfelt and taken from GitHub
 * (https://github.com/jgilfelt/android-sqlite-asset-helper) on the 15/02/2016.
 * Created by Dylan McKee on 15/02/16.
 */
public class DatabaseManager extends SQLiteAssetHelper {

    /**
     * The name of the SQLite database, as in the Assets directory.
     */
    private static final String DATABASE_NAME = "crescendo.db";

    /**
     * The current version of the SQLite database.
     */
    private static final int DATABASE_VERSION = 1;

    /**
     * A constructor to create a new database manager
     *
     * @param context the context calling the constructor
     */
    public DatabaseManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

}