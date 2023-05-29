package app;

import java.io.File;
import java.io.IOException;

public class DecompressorInitializer{
    public static Dictionary dictionary;
    public static void initializeDecompressor(File dictFile, File binFile) throws IOException {
        dictionary = new Dictionary();
        dictionary.readCodes(dictFile);
        System.out.println(dictionary);
        FileDecompressor decompressor = new FileDecompressor(dictionary);
        decompressor.decompress(binFile);
    }
}