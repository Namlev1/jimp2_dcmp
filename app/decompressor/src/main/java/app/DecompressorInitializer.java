package app;

import java.io.File;

public class DecompressorInitializer{
    public static void initializeDecompressor(File dictFile, File binFile){
        Dictionary dictionary = new Dictionary();
        try{
            dictionary.readCodes(dictFile);
            System.out.println(dictionary);
            FileDecompressor decompressor = new FileDecompressor(dictionary);
            decompressor.decompress(binFile);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}