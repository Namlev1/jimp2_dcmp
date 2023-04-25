import java.io.File;

public class Main{
    public static void main(String[] args){
        Dictionary dictionary = new Dictionary();
        // w moim IDE trzeba dać taką ścieżkę, możliwe że u was trzeba dać innną
        File dictFile = new File("src/code.txt");
        File binFile = new File("src/output.bin");
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