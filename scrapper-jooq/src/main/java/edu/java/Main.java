package edu.java;

import edu.java.codegenerator.JooqGenerator;

public class Main {
    private Main() {
    }

    public static void main(String[] args) throws Exception {
        JooqGenerator jooqGenerator = new JooqGenerator();
        jooqGenerator.generate();
    }
}
