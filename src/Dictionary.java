import java.io.*;

public class Dictionary {
    private char[] symbols;
    private int[] codesLength;
    private long[] codes;
    private int codesNum;
    private int wholeBytesInFile;
    private int freeBits;

    public void readCodes(File input) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(input));
        String textLine = reader.readLine();
        wholeBytesInFile = textLine.indexOf(0);
        String[] s = textLine.split(" ");
        wholeBytesInFile = Integer.parseInt(s[0]);
        freeBits = Integer.parseInt(s[1]);
        System.out.println(wholeBytesInFile + " " + freeBits);
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
