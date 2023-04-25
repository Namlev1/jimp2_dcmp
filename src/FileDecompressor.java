import java.io.*;

public class FileDecompressor {
    private final Dictionary dictionary;

    public FileDecompressor(Dictionary dictionary){
        this.dictionary = dictionary;
    }

    public void decompress(File input) throws IOException {
        InputStream inputStream = new FileInputStream(input);
        Writer fileWriter = makeOutputWriter(input);

        int bytesInFile = dictionary.getFileLengthInBytes();
        int bitsNumber = 0;
        long currentByte = 0L;

        for(int i = 0; i < bytesInFile; i++){
            currentByte +=  inputStream.read();
            bitsNumber += 8;
            while(bitsNumber > 0){
                Symbol symbol = dictionary.findSymbol(currentByte, bitsNumber);
                if(symbol != null) {
                    fileWriter.append(symbol.getCharacter());
                    currentByte = moveByte(currentByte, bitsNumber, symbol.getLength());
                    bitsNumber -= symbol.getLength();
                }
                else {
                    currentByte = currentByte << bitsNumber;
                    break;
                }
                if(i == bytesInFile-1 && bitsNumber == dictionary.getFreeBits()){
                    break;
                }
            }
        }
        fileWriter.flush();
        fileWriter.close();
        inputStream.close();
    }

    //TODO debug
    public long moveByte(long readByte, int bits, int b){
//        int move = 8 - bits;
        int move;
        if(bits <= 8)
            move = b;
        else
            move = 8 - (bits - b);
//        long newByte = readByte << move;
        long newByte = readByte << move;
        newByte = newByte & 0b11111111;
        return newByte;
    }

    private Writer makeOutputWriter(File input)throws IOException{
        String fileName = input.getName();
        fileName = fileName.substring(0, fileName.length()-4);
        fileName += ".txt";
        return new FileWriter(fileName);
    }
}
