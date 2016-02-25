package com.hcse.file.filter.tools;

import java.util.HashSet;

import com.hcse.file.Record;
import com.hcse.file.filter.FieldFilter;
import com.hcse.file.util.Field;

public class FieldNaneIncludeFilter implements FieldFilter {
    private HashSet<String> map = new HashSet<String>();

    public FieldNaneIncludeFilter(String[] fields) {

    }

    @Override
    public boolean doFilter(Record record, Field feild) {
        return map.contains(feild.getName());
    }
}
