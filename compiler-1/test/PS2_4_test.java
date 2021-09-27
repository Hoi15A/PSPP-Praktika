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
public class PS2_4_test {

    private void testResult(String source, double val) throws Exception {
        final double EPS = 1E-10;
        double s = Calculator.start(source);
        assertEquals(source, val, s, EPS);
    }

    @Test
    public void testCalc() throws Exception {
        testResult("PI", Math.PI);
        testResult("E", Math.E);
        testResult("PI+E", Math.PI+Math.E);
        testResult("PI/E", Math.PI/Math.E);
        testResult("4*PI/E", 4*Math.PI/Math.E);
    }

}
