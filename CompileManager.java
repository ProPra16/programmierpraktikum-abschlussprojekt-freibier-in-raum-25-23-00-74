import vk.core.api.*;
import org.hamcrest.SelfDescribing;
import org.junit.runner.JUnitCore;


/**
 * Created by Martin on 29.06.2016.
 */

public class CompileManager {
    private static CompilerResult result;
    private static JavaStringCompiler compiler;
    private static CompilationUnit lol;

    private static String classname;
    private static String classContent;
    private static boolean isAtest;

   public CompileManager(String filename, String filecontent, boolean test){
       classname = filename;
       classContent = filecontent;
       isAtest = test;
       modedecider();
   }

    private static void sourceinit(){
        lol = new CompilationUnit(classname,classContent,isAtest);
    }

    private static void initcompiler(){
        compiler =  CompilerFactory.getCompiler(lol);
    }

    private static void runcompiler(){
        compiler.compileAndRunTests();
    }

    private static void output(){ // wird noch auf ein anderes Medium angepasst
      System.out.println(result.hasCompileErrors());
      if(result.hasCompileErrors()){
          System.out.println(result.getCompilerErrorsForCompilationUnit(lol));
      }
    }

    private static void arrangecompilerresult(){
        result = compiler.getCompilerResult();
    }

    private static void doallsteps(){
        sourceinit();
        initcompiler();
        runcompiler();
        arrangecompilerresult();
        output();
    }
    private static void modedecider(){
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


