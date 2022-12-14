package com.gini.scheduling.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;


public class SgschId implements Serializable {
    private String uno;
    private LocalDate schdate;
    private String hid;

    public SgschId() {
    }

    public SgschId(String uno, LocalDate schdate, String hid) {
        this.uno = uno;
        this.schdate = schdate;
        this.hid = hid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SgschId sgschId = (SgschId) o;
        return uno.equals(sgschId.uno) &&
                schdate.equals(sgschId.schdate) &&
                hid.equals(sgschId.hid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uno, schdate, hid);
    }
}
