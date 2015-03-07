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
        while(c != -1){
            switch(c){
                case ' ': case '\t': case '\n': case '\r': WS(); continue;
                case '+': nextChar(); return new Token(PLUS,"+");
                case '*': nextChar(); return new Token(MULT,"*");
                case '(': nextChar(); return new Token(LPAREN,"(");
                case ')': nextChar(); return new Token(RPAREN,")");
                default:
                    if(isLetter()) return matchID();
                    if(isNumber()) return matchINT();
                    else return INVALID();
            }
        }
        return new Token(Token.EOF_TYPE, "<EOF>");
    }

    Token matchINT() throws IOException{
        StringBuilder buf = new StringBuilder();
        do{
            buf.append((char) c);
            nextChar();
        }while(isNumber());
        return new Token(INT, buf.toString());
    }

    boolean isNumber(){
        return c>='0' && c<='9';
    }

    Token matchID() throws IOException {
        StringBuilder buf = new StringBuilder();
        do{
            buf.append((char)c);
            nextChar();
        }while(isLetter());
        return new Token(ID, buf.toString());
    }

    boolean isLetter(){
        return c>='a' && c<='z'
                || c>='A'&& c<='Z';
    }

    Token INVALID() throws IOException{
        System.err.println("invalid character: " + c);
        Token invalid =  new Token(Token.INVALID_TYPE, String.valueOf((char)c));
        nextChar();
        return invalid;


    }

    void WS()throws IOException{
        while(c == ' '|| c == '\t'
                || c == '\n' || c =='\r')
            nextChar();
    }

    public static void main(String[] args) throws IOException {
        readAndPrint(new ExprLexer(new InputStreamReader(System.in)));
    }

}
