package com.hcse.file;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import org.apache.log4j.Logger;

import com.hcse.file.filter.FilterManager;
import com.hcse.file.filter.RecordHandler;
import com.hcse.file.util.Field;

public class RecordInputStream extends FilterManager implements Runnable {
    protected static final Logger logger = Logger.getLogger(RecordInputStream.class);

    static final int STATE_INIT = 0;
    static final int STATE_READ_FIELD = 1;
    static final int STATE_READ_CONTENTS = 2;

    private BufferedReader reader;

    private int state = 0;
    private int lineNumber = 0;

    private Record record = null;
    private String fieldName = null;

    public RecordInputStream(String name) throws FileNotFoundException, UnsupportedEncodingException {
        this(name, "GBK");
    }

    public RecordInputStream(String name, String charsetName) throws FileNotFoundException,
            UnsupportedEncodingException {
        FileInputStream in = new FileInputStream(name);
        reader = new BufferedReader(new InputStreamReader(in, charsetName));
    }

    private void onNewField(String name, String value, int type) {
        Field field = new Field(name, value, type);

        if (!doFieldFilter(record, field)) {
            return;
        }

        record.pushField(field);
    }

    private void onNewRecordComplete() {
        if (!doRecordFilter(record)) {
            record = null;
        }
    }

    public Record readRecord() {
        try {
            do {
                ++lineNumber;
                String line = reader.readLine();

                if (line == null) {
                    Record ret = record;
                    record = null;

                    return ret;
                }

                switch (state) {
                case STATE_INIT:
                    if (line.startsWith("!!")) {
                        record = new Record();
                        state = STATE_READ_FIELD;
                    } else {
                        logger.error(String.format("read header failed. line:%d.", lineNumber));
                    }
                    break;
                case STATE_READ_FIELD: {
                    if (line.length() < 2 || (line.length() >= 3 && line.charAt(2) != ':')) {
                        onNewRecordComplete();

                        Record ret = record;
                        record = null;

                        state = STATE_INIT;

                        if (ret != null) {
                            return ret;
                        }
                    }

                    if (line.length() == 2) {
                        fieldName = line;
                        state = STATE_READ_CONTENTS;
                    } else {
                        if (line.charAt(2) == ':') {
                            fieldName = line.substring(0, 2);
                            onNewField(fieldName, line.substring(3), 0);
                        } else {

                            logger.error(String.format("read field failed. line:%d.", lineNumber));
                        }
                    }

                    break;
                }
                case STATE_READ_CONTENTS:
                    if (line.charAt(0) == ' ') {
                        line = line.substring(1);
                    }

                    onNewField(fieldName, line, 1);
                    state = STATE_READ_FIELD;

                    break;
                default: {
                    logger.error(String.format("unknown state. line:%d.", lineNumber));
                    state = STATE_INIT;
                }
                }
            } while (true);
        } catch (IOException e) {
            logger.error(String.format("read file exception. line:%d.", lineNumber));
            logger.error(e);
        }

        return null;
    }

    public void run() {
        Record record = null;

        while ((record = readRecord()) != null) {
            this.doHandle(record);
        }
    }

    public void close() throws IOException {
        if (reader != null) {
            reader.close();
            reader = null;
        }
    }
}
