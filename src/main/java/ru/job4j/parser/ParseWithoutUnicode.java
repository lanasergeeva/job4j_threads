package ru.job4j.parser;

import java.io.File;

public class ParseWithoutUnicode implements Parsing {
    private final File file;

    public ParseWithoutUnicode(File file) {
        this.file = file;
    }

    @Override
    public synchronized String getContent() {
        return new ParseContent().content((ch -> ch > 0x86), file);
    }
}
