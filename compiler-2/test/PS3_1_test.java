/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import de.inetsoftware.jwebassembly.JWebAssembly;

import static org.junit.Assert.*;
import org.junit.Test;


/**
 *
 * @author Karl Rege
 */
public class PS3_1_test {

    private void testResult(String source, double val) throws Exception {
        final double EPS = 1E-5;
        Scanner.init(source);
        Scanner.scan();
        EmitHelper.silentEmit(ICalculator.class, new Calculator());
        String res = Command.exec("wasm-interp --run-all-exports ICalculator.wasm", 0);
        double result = 0;
        if (res.contains(":")) 
             result = Double.parseDouble(res.split(":")[1]);
        assertEquals(res + source, val, result, EPS);
    }
    
    @Test 
    public void testConstants() throws Exception {
        testResult("PI", Math.PI);
        testResult("E", Math.E);       
    }
    
    @Test
    public void testSimpleValues() throws Exception {
        testResult("42.0,45", 42.0);
        testResult("123.4", 123.4);
    }   
   
    @Test
    public void testPrecedence() throws Exception {
        testResult("2 *(10 + 11.0)", 42.0);
        testResult("20 + 2 * 11.0)", 42.0);
    }  
        
    @Test
    public void testSimpleCalc() throws Exception {
        testResult("20+22.0", 42.0);
        testResult("64-22.0", 42.0);
        testResult("2*21.0", 42.0);
        testResult("84/2.0", 42.0);
    }

}
