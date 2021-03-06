import vk.core.api.*;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Yannick on 14.07.2016.
 */
public class RealCompileManager {
    Code code;
    private String testname;
    private String testcontent;
    CompilationUnit cunit;
    CompilationUnit testunit;
    JavaStringCompiler compiler;
    CompilerResult result;
    TestResult tr;

    public RealCompileManager(Code c, String testName, String testContent){
        code = c;
        this.testname=testName;
        this.testcontent=testContent;
    }

    public void runCompiler(){
        cunit = new CompilationUnit(code.getDateiname(),code.getKlasse(),false);
        testunit = new CompilationUnit(testname,testcontent,true);
        compiler = CompilerFactory.getCompiler(cunit,testunit);
        compiler.compileAndRunTests();
        tr = compiler.getTestResult();
        result = compiler.getCompilerResult();
        System.out.printf("");
    }

    public void compileTest(){
        testunit = new CompilationUnit(testname,testcontent,true);
        compiler = CompilerFactory.getCompiler(testunit);
        compiler.compileAndRunTests();
        tr = compiler.getTestResult();
        result = compiler.getCompilerResult();
    }

    public boolean compiles(){
        if(result.hasCompileErrors()) return false;
        return true;
    }

    public String getCompileErrors() {
        String solution = "";
        for (CompileError t : result.getCompilerErrorsForCompilationUnit(cunit)) {
            solution += t.toString();
        }
        return solution;
    }

    public String getTestErrors() {
        String testerrors = "";
        Collection<TestFailure> coll;
        if (tr == null)
            return "";
        for (TestFailure t : tr.getTestFailures()) {
            testerrors += "Class: " + t.getTestClassName() + "\nFunction: " + t.getMethodName() + " \nThat is the outcum: " + t.getMessage() + "\n";
        }

        return testerrors;
    }

    public int getNumberOfFailedTests(){
        return tr.getNumberOfFailedTests();
    }
}
