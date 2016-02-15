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

    private static final String DATABASE_NAME = "crescendo.db";
    private static final int DATABASE_VERSION = 1;

    public DatabaseManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

}