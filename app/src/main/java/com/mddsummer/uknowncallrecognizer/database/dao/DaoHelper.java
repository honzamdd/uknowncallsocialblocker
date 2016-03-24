package com.mddsummer.uknowncallrecognizer.database.dao;

import android.content.Context;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.mddsummer.uknowncallrecognizer.database.DatabaseHelper;

/**
 * @author {@link "mailto:honzamusil@honzamusil.info" "Honza Musil"} on 24/3/2016
 */
public class DaoHelper {

    private DatabaseHelper databaseHelper = null;
    private Context mContext;

    public DaoHelper(Context mContext) {
        super();
        this.mContext = mContext;
    }

    public DatabaseHelper getDatabaseHelper() {
        if (databaseHelper == null) {
            databaseHelper = OpenHelperManager.getHelper(mContext, DatabaseHelper.class);
        }

        return databaseHelper;
    }
}
