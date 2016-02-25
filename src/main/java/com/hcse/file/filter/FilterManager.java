package com.hcse.file.filter;

import java.util.ArrayList;

import com.hcse.file.Record;
import com.hcse.file.util.Field;

public class FilterManager {
    private ArrayList<RecordHandler> handles = new ArrayList<RecordHandler>();

    private ArrayList<FieldFilter> fieldFilters = new ArrayList<FieldFilter>();
    private ArrayList<RecordFilter> recordFilters = new ArrayList<RecordFilter>();

    public void doHandle(Record record) {
        for (RecordHandler handle : handles) {
            handle.process(record);
        }
    }

    public boolean doFieldFilter(Record record, Field field) {
        for (FieldFilter filter : fieldFilters) {
            if (!filter.doFilter(record, field)) {
                return false;
            }
        }

        return true;
    }

    public boolean doRecordFilter(Record record) {
        for (RecordFilter filter : recordFilters) {
            if (!filter.doFilter(record)) {
                return false;
            }
        }

        return true;
    }

    public void registerRecordHandler(RecordHandler h) {
        handles.add(h);
    }

    public void registerFieldFilter(FieldFilter f) {
        fieldFilters.add(f);
    }

    public void registerRecordFilter(RecordFilter f) {
        recordFilters.add(f);
    }
}
