package com.hcse.file;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.log4j.Logger;

import com.hcse.file.util.Field;

public class RecordOutputStream {
    protected static final Logger logger = Logger.getLogger(RecordOutputStream.class);

    private long flushCount = Long.MAX_VALUE;

    private long count;

    private BufferedWriter writer;

    public RecordOutputStream(String name) throws UnsupportedEncodingException, FileNotFoundException {
        this(name, "GBK");
    }

    public RecordOutputStream(String name, String charsetName) throws UnsupportedEncodingException,
            FileNotFoundException {
        FileOutputStream of = new FileOutputStream(name);
        writer = new BufferedWriter(new OutputStreamWriter(of, charsetName));
    }

    public RecordOutputStream(OutputStream os) throws UnsupportedEncodingException, FileNotFoundException {
        this(os, "GBK");
    }

    public RecordOutputStream(OutputStream os, String charsetName) throws UnsupportedEncodingException,
            FileNotFoundException {
        writer = new BufferedWriter(new OutputStreamWriter(os, charsetName));
    }

    public RecordOutputStream(BufferedWriter writer) throws UnsupportedEncodingException, FileNotFoundException {
        this.writer = writer;
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
                writer.write(" ");
                writer.write(f.getValue());
                break;
            }

            count++;
            writer.newLine();

            if (count > flushCount) {
                writer.flush();
            }
        }
    }

    public void close() throws IOException {
        if (writer != null) {
            writer.newLine();

            writer.close();
            writer = null;
        }
    }

    public long getFlushCount() {
        return flushCount;
    }

    public void setFlushCount(long flushCount) {
        this.flushCount = flushCount;
    }

    public long getCount() {
        return count;
    }
}
