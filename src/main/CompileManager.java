import vk.core.api.*;
import java.util.Collection;


/**
 * Created by Martin on 29.06.2016 + 06.07.2016.
 */

public class CompileManager {
    private CompilerResult result;
    private TestResult testresult;
    private TestFailure testfail;
    private JavaStringCompiler compiler;
    private CompilationUnit lol;

    private String classname;
    private String classContent;
    private boolean isAtest;
    private String solution = "";
    private String testerrors = "";

   public CompileManager(String filename, String filecontent, boolean test){
       classname = filename;
       classContent = filecontent;
       isAtest = test;
       modedecider();
   }

    private void sourceinit(){
        lol = new CompilationUnit(classname,classContent,isAtest);
    }

    private void initcompiler(){
        compiler =  CompilerFactory.getCompiler(lol);
    }

    private void runcompiler(){
        compiler.compileAndRunTests();
    }

    private void arrangecompilerresult(){
        result = compiler.getCompilerResult();
    }

    public boolean IncludeCompileErrors(){
       return result.hasCompileErrors();
    }

    private void runcompilermode(){
        sourceinit();
        initcompiler();
        runcompiler();
        arrangecompilerresult();
    }

    private void modedecider(){
        runcompilermode();
        if(isAtest) prepareTestData();
    }

    private void arrangeTestresult(){
        testresult = compiler.getTestResult();
    }

    private void prepareTestData(){
        if(!result.hasCompileErrors()){
            arrangeTestresult();
        }
    }

    public int returnFailedTestsnumber(){
        return testresult.getNumberOfFailedTests();
    }

    public String getCompilingErrors(){
        if(IncludeCompileErrors()){
            CompilingErrors();
            return solution;
        }
        return "no Errors";
    }
    private String CompilingErrors(){
        Collection<CompileError> coll;
        for(CompileError t : result.getCompilerErrorsForCompilationUnit(lol)){
            solution +=t.toString();
        }
        return solution;
    }

    public String getTestErrors(){
        Collection<TestFailure> coll;
        for(TestFailure t : testresult.getTestFailures()){
            testerrors += "Class: " + t.getTestClassName() + "\nFunction: " + t.getMethodName()+ " \nThat is the outcum: " + t.getMessage() + "\n"+t.getExceptionStackTrace();
        }
        return testerrors;
    }

}



