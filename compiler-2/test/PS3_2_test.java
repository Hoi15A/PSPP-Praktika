/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import de.inetsoftware.jwebassembly.JWebAssembly;
import static org.junit.Assert.*;
import de.inetsoftware.jwebassembly.module.*;
import org.junit.Test;
import java.io.*;

/**
 *
 * @author Karl Rege
 */
public class PS3_2_test {
    
    private void testResult(String source, double val) throws Exception {
        final double EPS = 1E-5;
        Scanner.init(source);
        Scanner.scan();
        EmitHelper.silentEmit(ICalculator.class, new Calculator2());
        String res = Command.exec("wasm-interp --run-all-exports ICalculator.wasm", 0);
        double result = 0;
        if (res.contains(":")) 
             result = Double.parseDouble(res.split(":")[1]);
        assertEquals(res + source, val, result, EPS);
    }

    @Test
    public void testCalc() throws Exception {
        testResult("value = 42.0;", 42.0);
        testResult("a = 20.0; b = 22.0; value = a + b;", 42.0);
        double b = 4, c = 2;
        testResult("b = 4; c = 2; value = 2*PI*c*c + 2*PI*b*c;",2*Math.PI*c*c + 2*Math.PI*b*c );
        
    }

}
