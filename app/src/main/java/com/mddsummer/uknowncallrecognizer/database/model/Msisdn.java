package com.mddsummer.uknowncallrecognizer.database.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * @author  {@link "mailto:honzamusil@honzamusil.info" "Honza Musil"} on 24/3/2016
 */
@DatabaseTable
public class Msisdn {

    @DatabaseField(generatedId = true)
    private long id;

    @DatabaseField
    private String msisdn;

    @DatabaseField
    private boolean isBlocked;

    public Msisdn() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    public boolean isBlocked() {
        return isBlocked;
    }

    public void setBlocked(boolean blocked) {
        isBlocked = blocked;
    }

    @Override
    public String toString() {
        return "Msisdn{" +
                "id=" + id +
                ", msisdn='" + msisdn + '\'' +
                ", isBlocked=" + isBlocked +
                '}';
    }
}
