package com.hcse.file.filter.tools;

import com.hcse.file.Record;
import com.hcse.file.util.Field;

public class FieldNameExcludeFilter extends FieldNaneIncludeFilter {
    public FieldNameExcludeFilter(String[] fields) {
        super(fields);
    }

    public boolean doFilter(Record record, Field feild) {
        return !super.doFilter(record, feild);
    }
}
