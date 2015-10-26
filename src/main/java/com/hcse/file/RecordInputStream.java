package com.hcse.file;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

public class RecordInputStream implements Runnable {

    private BufferedReader reader;

    int state = 0;

    public RecordInputStream(String name) throws FileNotFoundException, UnsupportedEncodingException {
        FileInputStream in = new FileInputStream(name);
        reader = new BufferedReader(new InputStreamReader(in, "GBK"));
    }

    public void dataHandler(Record record) {

    }

    public Record readRecord() {
        return null;
    }

    @SuppressWarnings("null")
    @Override
    public void run() {
        Record record = null;
        String fieldName = null;

        try {
            String line = reader.readLine();

            switch (state) {
            case 0:
                if (line.startsWith("!!")) {
                    record = new Record();
                    state = 1;
                }
                break;
            case 1: {
                if (line.isEmpty()) {
                    dataHandler(record);
                    record = null;

                    state = 0;
                    break;
                }

                String[] array = line.split(":");
                if (array.length == 1) {
                    fieldName = array[0];
                    state = 2;
                } else if (array.length == 2) {
                    record.pushField(array[0], array[1], 0);
                }

                break;
            }
            case 2:
                record.pushField(fieldName, line, 1);
                state = 1;

                break;
            }

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return;
    }

    public void close() throws IOException {
        if (reader != null) {
            reader.close();
            reader = null;
        }
    }
}
