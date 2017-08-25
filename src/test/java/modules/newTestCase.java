package modules;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import objects.HomeLocators;
import objects.LoginLocators;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.PageFactory;
import org.testng.AssertJUnit;
import org.testng.annotations.*;
import supports.BaseClass;
import supports.WaitForLoading;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class newTestCase extends BaseClass {

    LoginLocators LoginScreen;
    HomeLocators HomeScreen;
    WaitForLoading Waiting;

    @BeforeTest
    public void setUp() throws MalformedURLException {

        DesiredCapabilities caps = new DesiredCapabilities();

//        Device information
        caps.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
        caps.setCapability(MobileCapabilityType.PLATFORM_VERSION, "4.4.2");
        caps.setCapability(MobileCapabilityType.DEVICE_NAME, "LC6B4B402499");
        caps.setCapability(MobileCapabilityType.TAKES_SCREENSHOT, "true");

//        Talo app
        caps.setCapability("appPackage", "com.experian.prosper");
        caps.setCapability("appActivity", "md52667d9a6a34a1dc9b0793dee98632707.MainActivity");

//        Server inform
        driver = new AndroidDriver(new URL("http://0.0.0.0:4723/wd/hub"), caps);
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

        LoginScreen = PageFactory.initElements(driver, objects.LoginLocators.class);
        HomeScreen = PageFactory.initElements(driver, objects.HomeLocators.class);
        Waiting = new WaitForLoading(driver);
    }


    @Test
    public void testcase01() throws InterruptedException {

//        LoginScreen.logIn("01233069234", "qqqqqq");
//        Waiting.waitForTextViewSize(9,60);
//        HomeScreen.lbl_MyProfile.click();
//        LoginScreen.btn_SignIn.click();
    }

    @Test
    public void testcase02(){
//        newTestCase ms = new newTestCase();
//        String re = String.valueOf(ms.getClass().getSimpleName());
//        System.out.println(re);
        AssertJUnit.assertEquals(1,0);
    }

    @AfterTest
    public void tearDown(){
        driver.closeApp();
        driver.quit();
    }
}




