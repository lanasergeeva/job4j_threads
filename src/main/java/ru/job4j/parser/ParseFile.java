package ru.job4j.parser;

import java.io.*;

public class ParseFile implements Parsing {
    private final File file;

    public ParseFile(File file) {
        this.file = file;
    }

    @Override
    public synchronized String getContent() {
        return new ParseContent().content((character -> character > 0), file);
    }
}
