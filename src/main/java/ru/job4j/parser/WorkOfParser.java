package ru.job4j.parser;

import java.io.File;

public class WorkOfParser {
    public static void main(String[] args) {
        ParseFile ps = new ParseFile(new File("src/main/java/ru/job4j/parser/Ex.txt"));
        System.out.println(ps.getContent());
        ParseWithoutUnicode pu = new ParseWithoutUnicode(new File("src/main/java/ru/job4j/parser/Ex.txt"));
        System.out.println(pu.getContent());
        new Out().saveContent("All in a good time", new File("src/main/java/ru/job4j/parser/Out.txt"));

    }
}
