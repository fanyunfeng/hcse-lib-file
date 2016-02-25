package com.hcse.file.filter;

import com.hcse.file.Record;

public interface RecordFilter {
    boolean doFilter(Record record);
}
