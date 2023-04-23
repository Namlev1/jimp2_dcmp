import java.io.*;

public class FileDecompressor {
    private Dictionary dictionary;

    public FileDecompressor(Dictionary dictionary){
        this.dictionary = dictionary;
    }

    public void decompress(File input) throws IOException {
        InputStream inputStream = new FileInputStream(input);
        Writer fileWriter = makeOutputWriter(input);
        int byteNum = dictionary.getFileLengthInBytes();

        for(int i = 0; i < byteNum; i++){
            long currentByte = inputStream.read();

        }

        inputStream.close();
    }

    private long makeMask(int n, int byteNum) {
        return (1L << (8-n*byteNum)) - 1;
    }

    private Writer makeOutputWriter(File input)throws IOException{
        String fileName = input.getName();
        fileName = fileName.substring(0, fileName.length()-4);
        fileName += ".txt";
        return new FileWriter(fileName);
    }
}
