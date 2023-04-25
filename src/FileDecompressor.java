import java.io.*;

public class FileDecompressor {
    private final Dictionary dictionary;
    private final int bytesInFile;

    public FileDecompressor(Dictionary dictionary){
        this.dictionary = dictionary;
        bytesInFile = dictionary.getFileLengthInBytes();
    }

    private Writer makeOutputWriter(File input)throws IOException{
        String fileName = input.getName();
        fileName = fileName.substring(0, fileName.length()-4);
        fileName += ".txt";
        return new FileWriter(fileName);
    }

    public void decompress(File input) throws IOException {
        InputStream inputStream = new FileInputStream(input);
        Writer outputWriter = makeOutputWriter(input);

        decompressToFile(inputStream, outputWriter);

        outputWriter.flush();
        outputWriter.close();
        inputStream.close();
    }

    private void decompressToFile(InputStream inputStream, Writer outputWriter) throws IOException {

        int bitsNumber = 0;
        long currentByte = 0L;

        for(int i = 0; i < bytesInFile; i++){
            currentByte +=  inputStream.read();
            bitsNumber += 8;
            while(bitsNumber > 0){

                Symbol symbol = dictionary.findSymbol(currentByte, bitsNumber);
                if(symbol != null) {
                    outputWriter.append(symbol.getCharacter());
                    currentByte = moveByte(currentByte, bitsNumber, symbol.getLength());
                    bitsNumber -= symbol.getLength();
                }
                else {
                    currentByte = currentByte << bitsNumber;
                    break;
                }

                if(i == bytesInFile-1 && bitsNumber==dictionary.getFreeBits()){
                    break;
                }
            }
        }
    }

    public long moveByte(long readByte, int actualBitsInByte, int codeLength){
        int move;
        if(actualBitsInByte <= 8)
            move = codeLength;
        else
            move = 8 - (actualBitsInByte - codeLength);

        long newByte = readByte << move;
        newByte = newByte & 0b11111111;
        return newByte;
    }
}
