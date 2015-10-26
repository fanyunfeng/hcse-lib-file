package com.hcse.file.util;

import java.util.ArrayList;
import java.util.HashMap;

public class FieldsMap {
    private ArrayList<String> fields = new ArrayList<String>();
    private HashMap<String, Integer> fieldsMap = new HashMap<String, Integer>();

    static FieldsMap[] masp = new FieldsMap[2];

    public static FieldsMap create(int type) {
        if (masp[type] == null) {
            masp[type] = new FieldsMap(type);
        }

        return masp[type];
    }

    private FieldsMap(int type) {
        if (type == 0) {
            createField4Common();
        } else {
            createFields4Logistics();
        }
    }

    public void createFields4Logistics() {
        addField("RQ");
        addField("CQ");
        addField("PC");
        addField("ZS");
        addField("AS");
        addField("BZ");
        addField("JY");
        addField("MI");
        addField("TI");
        addField("TP");
        addField("TT");
        addField("IM");
        addField("CM");
        addField("CY");
        addField("CZ");
        addField("CC");
        addField("CV");
        addField("VE");
        addField("CO");
        addField("CB");
        addField("LA");
        addField("ID");
        addField("QY");
        addField("UN");
        addField("VN");
        addField("TM");
        addField("UP");
        addField("BA");
        addField("BB");
        addField("GN");
        addField("SN");
        addField("ZF");
        addField("BQ");
        addField("ET");
        addField("PQ");
        addField("GD");
        addField("SS");
        addField("TB");
        addField("ZY");
        addField("PA");
        addField("QU");
        addField("SL");
        addField("AG");
        addField("SU");
        addField("TY");
        addField("ME");
        addField("IB");
        addField("CT");
        addField("BD");
        addField("HD");
        addField("MO");
        addField("VU");
        addField("PT");
        addField("MM");
        addField("UL");
        addField("PM");
        addField("QQ");
        addField("BU");
        addField("PR");
        addField("TA");
        addField("TC");
        addField("YP");
        addField("XP");
    }

    public void createField4Common() {
        addField("RQ");
        addField("CQ");
        addField("PC");
        addField("ZS");
        addField("AS");
        addField("BZ");
        addField("JY");
        addField("MI");
        addField("TI");
        addField("TP");
        addField("TT");
        addField("IM");
        addField("CM");
        addField("CY");
        addField("CZ");
        addField("CC");
        addField("CV");
        addField("VE");
        addField("CO");
        addField("CB");
        addField("LA");
        addField("ID");
        addField("QY");
        addField("UN");
        addField("VN");
        addField("TM");
        addField("UP");
        addField("BA");
        addField("BB");
        addField("GN");
        addField("SN");
        addField("ZF");
        addField("BQ");
        addField("ET");
        addField("PQ");
        addField("GD");
        addField("SS");
        addField("TB");
        addField("ZY");
        addField("PA");
        addField("QU");
        addField("SL");
        addField("AG");
        addField("SU");
        addField("TY");
        addField("ME");
        addField("IB");
        addField("CT");
        addField("BD");
        addField("HD");
        addField("MO");
        addField("VU");
        addField("PT");
        addField("MM");
        addField("UL");
        addField("PM");
        addField("QQ");
        addField("BU");
        addField("TV");
        addField("TU");
        addField("CA");
        addField("GY");
        addField("UD");
        addField("YP");
        addField("XP");
        addField("ML");
        addField("HE");
        addField("HB");
    }

    public void addField(String name) {
        fields.add(name);

        fieldsMap.put(name, fieldsMap.size());
    }

    public int getIndex(String name) {
        return fieldsMap.get(name);
    }

    public String getFieldName(int index) {
        return fields.get(index);
    }

    public int size() {
        return fields.size();
    }
}
