package com.mddsummer.uknowncallrecognizer.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.mddsummer.uknowncallrecognizer.database.model.Msisdn;

import java.sql.SQLException;

/**
 * DatabaseHelper handle creating and updating SQLite db.
 *
 * @author {@link "mailto:honzamusil@honzamusil.info" "Honza Musil"} on 24/3/2016
 */
public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    private static final String DATABASE_NAME = "callblocker.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TAG = DatabaseHelper.class.getSimpleName();

    private RuntimeExceptionDao<Msisdn, Integer> msisdnRuntimeDao = null;


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase,
                         ConnectionSource connectionSource) {
        try {
            Log.v(TAG, "onCreate");
            TableUtils.createTable(connectionSource, Msisdn.class);

        } catch (SQLException e) {
            Log.e(TAG, "Could not create tables: " + e.getMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            Log.v(DatabaseHelper.class.getName(), "onUpgrade");
            TableUtils.dropTable(connectionSource, Msisdn.class, true);

            onCreate(sqLiteDatabase, connectionSource);
        } catch (SQLException e) {
            Log.e(TAG, "Could not upgrade tables.", e);
        }
    }

    public RuntimeExceptionDao<Msisdn, Integer> getMsisdnDaoRuntime() {
        if (msisdnRuntimeDao == null) {
            msisdnRuntimeDao = getRuntimeExceptionDao(Msisdn.class);
        }
        return msisdnRuntimeDao;
    }

    @Override
    public void close() {
        super.close();

        msisdnRuntimeDao = null;
    }
}
