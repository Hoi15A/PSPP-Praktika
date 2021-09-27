/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import static org.junit.Assert.*;
import org.junit.Test;

/**
 *
 * @author Karl Rege
 */
public class PS2_3_test {

    private void testToken(String source, int... tokens) {
        Scanner.init(source);
        for (int t : tokens) {
            Token s = Scanner.next();
            assertEquals(source, t, s.kind);
        }
    }

    private void testStr(String source, String... strs) {
        Scanner.init(source);
        for (String t : strs) {
            Token s = Scanner.next();
            assertEquals(source, t, s.str);
        }
    }

    @Test
    public void testNext() {
        testToken("PI", Token.IDENT, Token.EOF);
        testStr("PI", "PI");
        testToken("E", Token.IDENT, Token.EOF);
        testStr("E", "E");
        testToken("PI E", Token.IDENT, Token.IDENT, Token.EOF);
        testStr("PI E", "PI", "E");
    }

}
