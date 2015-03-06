/**
 * Created by ivy on 3/4/15.
 */
import java.io.*;

public abstract class Lexer implements TokenSource {
    protected Reader reader = null;

    /** Lookahead char */
    protected int c;

    /** Text of currently matched token */
    protected StringBuffer text = new StringBuffer(100);

    public Lexer(Reader reader) throws IOException {
        this.reader = reader;
        nextChar();
    }

    protected void nextChar() throws IOException {
        c = (char)reader.read();
    }

    public abstract Token nextToken() throws IOException;

    public static void readAndPrint(Lexer scanner) throws IOException {
        Token t = scanner.nextToken();
        while ( t.type!=Token.EOF_TYPE ) {
            System.out.println(t);
            t = scanner.nextToken();
        }
    }
}