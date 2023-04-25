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

    public long getByteCode() {
        return byteCode;
    }
}
