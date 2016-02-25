package com.hcse.file;

import java.io.IOException;

import org.apache.log4j.Logger;

import com.hcse.file.filter.RecordHandler;

public class DumpHandler implements RecordHandler {
    protected static final Logger logger = Logger.getLogger(DumpHandler.class);

    private RecordOutputStream stream;

    public DumpHandler(RecordOutputStream stream) {
        this.stream = stream;
    }

    @Override
    public void process(Record record) {
        try {
            stream.writeRecord(record);
        } catch (IOException e) {
            logger.error("write record failed.", e);
        }
    }
}
