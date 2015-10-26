package com.hcse.file;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.List;

import com.hcse.file.util.Field;

public class RecordOutputStream {
    private BufferedWriter writer;

    public RecordOutputStream(String name) throws UnsupportedEncodingException, FileNotFoundException {
        FileOutputStream in = new FileOutputStream(name);
        writer = new BufferedWriter(new OutputStreamWriter(in, "GBK"));
    }

    public void writeRecord(Record record) throws IOException {
        List<Field> fields = record.getFields();

        if (fields.isEmpty()) {
            return;
        }

        writer.write("!!");
        writer.newLine();

        for (Field f : fields) {
            switch (f.getType()) {
            case 0:
                writer.write(f.getName());
                writer.write(':');
                writer.write(f.getValue());
                break;
            case 1:
                writer.write(f.getName());
                writer.newLine();
                writer.write(f.getValue());
                break;
            }

            writer.newLine();
        }
    }

    public void close() throws IOException {
        if (writer != null) {
            writer.close();
            writer = null;
        }
    }
}
