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
interface ISimpleCalculator {
    double calc();
}

public class PS3_3_test {
    
    private void testResult(double arg, String source, double val) throws Exception {
        final double EPS = 1E-5;
        String argString = "$arg0 = "+arg+";\n";
        Scanner.init(argString+source);
        Scanner.scan();
        EmitHelper.silentEmit(ISimpleCalculator.class, new Calculator2());
        String res = Command.exec("wasm-interp --run-all-exports ISimpleCalculator.wasm", 0);
        
        double result = 0;
        if (res.contains(":")) 
             result = Double.parseDouble(res.split(":")[1]);
        assertEquals(res + source, val, result, EPS);
    }

    @Test
    public void testCalc() throws Exception {
        testResult(2,"x = $arg0;a = 1;b = 2;c = 3;value = a*x*x + b*x + c;", 11.0);
        testResult(4,"x = $arg0;a = 1;b = 2;c = 3;value = a*x*x + b*x + c;", 27.0);
    }

}
