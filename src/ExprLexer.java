/**
 * Created by ivy on 3/4/15.
 */
import com.sun.javafx.fxml.expression.Expression;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

/** Match tokens from the grammar + * ( ) and
 ID  : [a-z]+ ;
 INT : [0-9]+ ;
 WS  : [ \r\t\n]+ -> skip ;
 */
public class ExprLexer extends Lexer {
    public static final int ID = 1;
    public static final int INT = 2;
    public static final int PLUS = 3;
    public static final int MULT = 4;
    public static final int LPAREN = 5;
    public static final int RPAREN = 6;

    public ExprLexer(Reader reader) throws IOException {
        super(reader);

    }

    @Override
    public Token nextToken() throws IOException {
        text.setLength(0); // reset text
        int type = Token.INVALID_TYPE;

        // ...
        if (c == -1) {return new Token(Token.EOF_TYPE, null ); }
        if(c == '+') {type = PLUS; text.append('+');}
        else if (c == '*') {type = MULT; text.append('*');}
        else if (c =='(')  {type = LPAREN; text.append('(');}
        else if (c == ')') {type = RPAREN; text.append(')');}
        consume();
        if ( type==0 ) {
            System.err.println("illegal char: "+c);
            nextChar();
            // try again (tail recursion is like a loop)
            return nextToken();
        }
        return new Token(type, text.toString());
    }

    protected int matchINT() throws IOException {
        text.append((char)c);
        consume();
        while(c != -1 && c >='0' && c <='9'){
            text.append((char)c);
            consume();
        }
        return INT;
    }

    protected int matchID() throws IOException {
        return ID;
    }

    public static void main(String[] args) throws IOException {
        readAndPrint(new ExprLexer(new InputStreamReader(System.in)));
    }

    public void consume(){}
}
