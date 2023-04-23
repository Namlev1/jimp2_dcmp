import java.io.*;
import java.math.BigInteger;

public class Dictionary {
    private char[] symbols;
    private int[] codesLength;
    private long[] codes;
    private int codesNum;
    private int wholeBytesInFile;
    private int freeBits;

    public Dictionary(){
        symbols = new char[256];
        codesLength = new int[256];
        codes = new long[256];
    }

    public void readCodes(File input) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(input));

        String textLine = reader.readLine();
        String[] firstLine = textLine.split(" ");
        wholeBytesInFile = Integer.parseInt(firstLine[0]);
        freeBits = Integer.parseInt(firstLine[1]);

        textLine = reader.readLine();
        while(textLine != null){
            symbols[codesNum] = textLine.charAt(0);
            String code = textLine.substring(2);
            BigInteger bigInteger = new BigInteger(code, 2);
            codes[codesNum] = bigInteger.longValue();
            codesLength[codesNum++] = code.length();
            textLine = reader.readLine();
        }
    }

    public String toString(){
        if(codesNum == 0)
            return "No codes";

        StringBuilder result = new StringBuilder();
        result.append("Codes:");
        for(int i = 0; i < codesNum; i++){
            result.append("\n");
            result.append(symbols[i]).append(" ");
            result.append(printBinary(codes[i], codesLength[i]));
        }
        return result.toString();
    }

    //TODO not working
    private String printBinary(long code, int length){
        StringBuilder result = new StringBuilder();
        int start = (length % 8 == 0) ? length : ((length / 8) * 8 + 8);
        for (int i = start - 1; i >= (start - length) ; i--) {
            if ((code & (1L << i)) != 0) {
                result.append("1");
            } else {
                result.append("0");
            }
        }
        return result.toString();
    }
}
