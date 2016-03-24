package com.mddsummer.uknowncallrecognizer.database.dao;

import android.content.Context;

import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.mddsummer.uknowncallrecognizer.database.model.Msisdn;

import java.util.List;

/**
 * @author {@link "mailto:honzamusil@honzamusil.info" "Honza Musil"} on 24/3/2016
 */
public class DaoMsisdnHelper extends DaoHelper {

    private static final String TAG = DaoMsisdnHelper.class.getSimpleName();
    private RuntimeExceptionDao<Msisdn, Integer> dao;

    public DaoMsisdnHelper(Context context) {
        super(context);
        dao = getDatabaseHelper().getMsisdnDaoRuntime();
    }

    public void createMenu(Msisdn msisdn) {
        dao.createOrUpdate(msisdn);
    }

    public long getCount() {
        return dao.countOf();
    }

    public List<Msisdn> getAll() {
        return dao.queryForAll();
    }

    public Msisdn getById(int id) {
        return dao.queryForId(id);
    }

    public void deleteAll() {
        dao.delete(getAll());
    }
}
