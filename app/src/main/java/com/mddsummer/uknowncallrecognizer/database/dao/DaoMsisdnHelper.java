package com.mddsummer.uknowncallrecognizer.database.dao;

import android.content.Context;

import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.mddsummer.uknowncallrecognizer.database.model.Msisdn;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public void createMsisdn(Msisdn msisdn) {
        dao.createOrUpdate(msisdn);
    }

    public List<Msisdn> getAll() {
        return dao.queryForAll();
    }

    public boolean isMsisdnBlocked(String msisdn) {

        Map<String, Object> params = new HashMap<>();
        params.put("msisdn", msisdn);
        params.put("isBlocked", true);
        List<Msisdn> result = dao.queryForFieldValues(params);

        return (result != null && result.size() > 0);
    }
}
