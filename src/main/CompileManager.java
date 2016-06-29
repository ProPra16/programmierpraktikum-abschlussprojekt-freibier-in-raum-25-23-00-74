import vk.core.api.*;
import org.hamcrest.SelfDescribing;
import org.junit.runner.JUnitCore;


/**
 * Created by Martin on 29.06.2016.
 */

public class CompileManager {
    private CompilerResult result;
    private JavaStringCompiler compiler;
    private CompilationUnit lol;

    private String classname;
    private String classContent;
    private boolean isAtest;

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

    private void output(){ // wird noch auf ein anderes Medium angepasst
      System.out.println(result.hasCompileErrors());
      if(result.hasCompileErrors()){
          System.out.println(result.getCompilerErrorsForCompilationUnit(lol));
      }
    }

    private void arrangecompilerresult(){
        result = compiler.getCompilerResult();
    }

    private void doallsteps(){
        sourceinit();
        initcompiler();
        runcompiler();
        arrangecompilerresult();
        output();
    }
    private void modedecider(){
        if(isAtest) doallsteps();
        if(!isAtest) System.out.println("not implemented yet, but it will be...someday.....somehow......maybe.....");
    }

    public void storageofmytest(){
        /*"public class Test { \n" +
               "public static int methode(){ \n" +
               "return 2; \n" +
               "}" +
               "}"; */
    }
}


