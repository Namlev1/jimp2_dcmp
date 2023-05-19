package tests;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        new TestDecompressor(100);
        new TestDecompressor(500);
        new TestDecompressor(1000);
    }
}
