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
public class PS2_2_test {

    private void testResult(String source, double val) throws Exception {
        final double EPS = 1E-10;
        double s = Calculator.start(source);
        assertEquals(source, val, s, EPS);
    }

    @Test
    public void testCalc() throws Exception {
        testResult("42.0", 42.0);
        testResult("123.4", 123.4);
        testResult("20+22.0", 42.0);
        testResult("64-22.0", 42.0);
        testResult("2*21.0", 42.0);
        testResult("84/2.0", 42.0);
        testResult("2 *(10 + 11.0)", 42.0);
        testResult("20 + 2 * 11.0", 42.0);
    }

}
