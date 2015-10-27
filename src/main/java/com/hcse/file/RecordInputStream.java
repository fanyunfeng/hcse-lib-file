package com.hcse.file;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import org.apache.log4j.Logger;

public class RecordInputStream implements Runnable {
    protected static final Logger logger = Logger.getLogger(RecordInputStream.class);

    static final int STATE_INIT = 0;
    static final int STATE_READ_FIELD = 1;
    static final int STATE_READ_STRING = 2;

    private BufferedReader reader;

    private int state = 0;
    private int lineNumber = 0;

    private Record record = null;
    private String fieldName = null;

    private RecordHandler handler;

    public RecordInputStream(String name) throws FileNotFoundException, UnsupportedEncodingException {
        FileInputStream in = new FileInputStream(name);
        reader = new BufferedReader(new InputStreamReader(in, "GBK"));
    }

    void setRecordHandler(RecordHandler handler) {
        this.handler = handler;
    }

    public Record readRecord() {
        try {
            ++lineNumber;
            String line = reader.readLine();

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
                if (line.isEmpty()) {
                    Record ret = record;
                    record = null;

                    state = STATE_INIT;

                    return ret;
                }

                String[] array = line.split(":");
                if (array.length == 1) {
                    fieldName = array[0];
                    state = STATE_READ_STRING;
                } else if (array.length == 2) {
                    record.pushField(array[0], array[1], 0);
                } else {
                    logger.error(String.format("read field failed. line:%d.", lineNumber));
                }

                break;
            }
            case STATE_READ_STRING:
                if (line.charAt(0) == ' ') {
                    line = line.substring(1);
                }

                record.pushField(fieldName, line, 1);
                state = STATE_READ_FIELD;

                break;
            default: {
                logger.error(String.format("unknown state. line:%d.", lineNumber));
                state = STATE_INIT;
            }
            }

        } catch (IOException e) {
            logger.error(String.format("read file exception. line:%d.", lineNumber));
            logger.error(e);
        }

        return null;
    }

    public void run() {
        if (handler == null) {
            return;
        }

        Record record = null;

        while ((record = readRecord()) != null) {
            handler.process(record);
        }
    }

    public void close() throws IOException {
        if (reader != null) {
            reader.close();
            reader = null;
        }
    }
}
