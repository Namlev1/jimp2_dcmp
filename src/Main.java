import java.io.File;

public class Main{
    public static void main(String[] args){
        Dictionary dictionary = new Dictionary();
        File file = new File("src/code.txt");
        try{
            dictionary.readCodes(file);
        } catch(Exception e) {
            e.printStackTrace();
        }
        System.out.println(dictionary);
    }
}