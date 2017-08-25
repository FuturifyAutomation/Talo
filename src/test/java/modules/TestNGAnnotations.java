package modules;

import org.testng.annotations.*;

public class TestNGAnnotations {

    @AfterMethod
    public void CloseBrowser(){
        System.out.println("AfterMethod will be executed after executing each Test");
    }

    @Test
    public void testCase01(){
        System.out.println("First testcase");
    }

    @AfterTest
    public void CloseDBConnection(){ System.out.println("AfterTest will be executed after all Test method/case executes");}

    @BeforeMethod
    public void openBrowser(){
        System.out.println("BeforeMethod will be executed before executing each Test");
    }

    @Test
    public void testCase03(){
        System.out.println("Third testcase");
    }

    @BeforeTest
    public void openDBConnection(){ System.out.println("BeforeTest will be executed before any Test method/case executes");}

    @Test
    public void testCase02(){
        System.out.println("Second testcase");
    }
}