public class Symbol {
    private final char character;
    private final int length;
    private final long byteCode;

    public Symbol(char character, int length, long byteCode) {
        this.character = character;
        this.length = length;
        this.byteCode = byteCode;
    }

    public char getCharacter() {
        return character;
    }

    public int getLength() {
        return length;
    }

    public long getCodeInBytes() {
        return byteCode;
    }

    public String toString(){
        return character + " " + printBinary(byteCode, length);
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
}
