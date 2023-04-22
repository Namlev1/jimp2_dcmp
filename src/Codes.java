import java.io.FileInputStream;
import java.io.FileReader;

public class Codes {
    private char[] symbols;
    private int[] codesLength;
    private long[] codes;
    private int codesNum;
    private int wholeBytesInFile;
    private int freeBits;

    public void readCodes(FileInputStream fileInput){
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
