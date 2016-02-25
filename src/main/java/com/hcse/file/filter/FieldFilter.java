package com.hcse.file.filter;

import com.hcse.file.Record;
import com.hcse.file.util.Field;

public interface FieldFilter {
    boolean doFilter(Record record, Field feild);
}
