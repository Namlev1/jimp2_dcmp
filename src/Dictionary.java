import java.io.*;
import java.math.BigInteger;

public class Dictionary {
    private final Symbol[] symbols;
    private int codesNum;
    private int wholeBytesInFile;
    private int freeBits;

    public Dictionary(){
        symbols = new Symbol[256];
    }

    public void readCodes(File input) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(input));

        String textLine = reader.readLine();
        if(textLine == null)
            throw new IOException("Empty file");

        String[] firstLine = textLine.split(" ");
        wholeBytesInFile = Integer.parseInt(firstLine[0]);
        freeBits = 8-Integer.parseInt(firstLine[1]);

        textLine = reader.readLine();
        while(textLine != null){
//            symbols[codesNum] = textLine.charAt(0);
//            String code = textLine.substring(2);
//            BigInteger bigInteger = new BigInteger(code, 2);
//            codes[codesNum] = bigInteger.longValue();
//            codeLength[codesNum++] = code.length();

            char c = textLine.charAt(0);
            String code = textLine.substring(2);
            BigInteger bigInteger = new BigInteger(code, 2);
            symbols[codesNum++] = new Symbol(c, code.length(), bigInteger.longValue());


            textLine = reader.readLine();
        }
        reader.close();
    }

    private String printBinary(long code, int length){
        StringBuilder result = new StringBuilder();
        for(int i = length-1; i >= 0; i--){
            if ((code & (1L << i)) != 0)
                result.append("1");
            else
                result.append("0");
        }
        return result.toString();
    }

    public Symbol findSymbol(long readByte, int bitsNum){
        for(int i = 0; i < codesNum; i++){
            if(bitsNum >= symbols[i].getLength()){
                int move;
                if(bitsNum <= 8)
                    move = 8 - symbols[i].getLength();
                else
                    move = bitsNum - symbols[i].getLength();
                long byteTmp = readByte >> move;
                if(byteTmp == symbols[i].getByteCode()) {
//                    moveByte(readByte, bitsNum);
                    return symbols[i];
                }
            }

        }
        return null;
    }

    public int getFileLengthInBytes(){
        return freeBits==0 ? wholeBytesInFile : wholeBytesInFile+1;
    }

    public int getFreeBits() {
        return freeBits;
    }

    public String toString(){
        if(codesNum == 0)
            return "No codes";

        StringBuilder result = new StringBuilder();
        result.append("Codes:");
        for(int i = 0; i < codesNum; i++){
            result.append("\n");
            result.append(symbols[i].getCharacter()).append(" ");
            result.append(printBinary(symbols[i].getByteCode(), symbols[i].getLength()));
        }
        return result.toString();
    }
}
