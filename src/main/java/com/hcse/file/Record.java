package com.hcse.file;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.hcse.file.util.Field;

public class Record {
    public ArrayList<Field> fields;
    public HashMap<String, Field> fieldMap;

    public Record() {
        this(40);
    }

    public Record(int size) {
        fields = new ArrayList<Field>(size);
        fieldMap = new HashMap<String, Field>(size);
    }

    public void pushField(Field field) {
        fields.add(field);
        fieldMap.put(field.getName(), field);
    }

    public void pushField(String name, String value, int type) {
        Field field = new Field(name, value, type);

        pushField(field);
    }

    public int getSize() {
        return fields.size();
    }

    public Field getField(String name) {
        return fieldMap.get(name);
    }

    public List<Field> getFields() {
        return fields;
    }

    public Field[] getFieldArray() {
        return fields.toArray(new Field[fields.size()]);
    }
}
