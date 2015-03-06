/**
 * Created by ivy on 3/4/15.
 */
import java.io.*;

public interface TokenSource {
    public Token nextToken() throws IOException;
}