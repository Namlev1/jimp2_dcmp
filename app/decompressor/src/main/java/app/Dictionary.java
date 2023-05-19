package app;

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
        {
            reader.close(); // przed wyrzuceniem IOException
            throw new IOException("Empty file");
        }
        String[] firstLine = textLine.split(" ");
        wholeBytesInFile = Integer.parseInt(firstLine[0]);
        freeBits = 8-Integer.parseInt(firstLine[1]);

        textLine = reader.readLine();
        while(textLine != null){
            char c = textLine.charAt(0);
            String code = textLine.substring(2);
            BigInteger bigInteger = new BigInteger(code, 2);
            symbols[codesNum++] = new Symbol(c, code.length(), bigInteger.longValue());

            textLine = reader.readLine();
        }
        reader.close();
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
                if(byteTmp == symbols[i].getCodeInBytes()) {
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
            result.append(System.lineSeparator())
                    .append(symbols[i].toString());
        }
        return result.toString();
    }
}
