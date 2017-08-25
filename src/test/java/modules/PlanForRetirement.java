package modules;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.AssertJUnit;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class PlanForRetirement {

    public AndroidDriver driver;

    @BeforeTest
    @Parameters({"sPlatformVersion", "sDeviceName", "sURL", "sPhoneNumber", "sPassword"})
    public void setUp(String sPlatformVersion, String sDeviceName, String sURL, String sPhoneNumber, String sPassword) throws MalformedURLException, InterruptedException {

        DesiredCapabilities caps = new DesiredCapabilities();

//        Device information
        caps.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
        caps.setCapability(MobileCapabilityType.PLATFORM_VERSION, sPlatformVersion);
        caps.setCapability(MobileCapabilityType.DEVICE_NAME, sDeviceName);

//        Talo app
        caps.setCapability("appPackage", "com.experian.prosper");
        caps.setCapability("appActivity", "md52667d9a6a34a1dc9b0793dee98632707.MainActivity");

//        Server inform
        driver = new AndroidDriver(new URL(sURL), caps);
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

//      Pre-condition: register new account
        driver.findElement(By.xpath(".//android.widget.TextView[@text='Đăng ký']")).click();
        List<WebElement> lEditText = driver.findElements(By.className("android.widget.EditText"));
        lEditText.get(0).sendKeys(sPhoneNumber);
        lEditText.get(1).sendKeys(sPassword);
        lEditText.get(2).sendKeys(sPassword);
        driver.findElement(By.xpath(".//android.widget.Button[@text='Đăng ký']")).click();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.findElement(By.xpath(".//android.widget.Button[@text='Đồng ý']")).click();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.findElement(By.xpath(".//android.widget.EditText[@text='PIN']")).sendKeys("123456");
        driver.findElement(By.xpath(".//android.widget.Button[@text='Xác nhận']")).click();
        driver.findElement(By.xpath(".//android.widget.Button[@text='Đồng ý']")).click();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

//        Tap My Goals module
        driver.findElement(By.xpath(".//android.widget.TextView[@text='Mục tiêu cá nhân']")).click();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

//        Plan to buy a new house
        driver.findElement(By.xpath(".//android.widget.TextView[@text='BUY A NEW HOUSE']")).click();
        List<WebElement> lEditTextQuestions = driver.findElements(By.className("android.widget.EditText"));
        lEditTextQuestions.get(0).sendKeys("1");
        lEditTextQuestions.get(1).click();
        driver.findElement(By.xpath(".//android.widget.Button[@text='OK']")).click();
        lEditTextQuestions.get(2).sendKeys("2000000000");
        driver.hideKeyboard();
        driver.findElement(By.xpath(".//android.widget.Button[@text='Hoàn thành']")).click();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.findElement(By.className("android.widget.ImageButton")).click();

//        Wait for loading
        List<WebElement> lTextView = driver.findElements(By.className("android.widget.TextView"));
        int ilTextViewCount = lTextView.size();
        int i = 0; int j = 1000;
        while (ilTextViewCount != 4 & i <= j*60){
            Thread.sleep(j);
            i+= j;
            lTextView = driver.findElements(By.className("android.widget.TextView"));
            ilTextViewCount = lTextView.size();
        }

//        Plan to get married
        driver.findElement(By.xpath(".//android.widget.TextView[@text='GET MARRIED']")).click();
        lEditTextQuestions = driver.findElements(By.className("android.widget.EditText"));
        lEditTextQuestions.get(1).click();
        driver.findElement(By.xpath(".//android.widget.Button[@text='OK']")).click();
        lEditTextQuestions.get(2).sendKeys("100000000");
        driver.hideKeyboard();
        driver.findElement(By.xpath(".//android.widget.Button[@text='Hoàn thành']")).click();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.findElement(By.className("android.widget.ImageButton")).click();

//        Wait for loading
        lTextView = driver.findElements(By.className("android.widget.TextView"));
        ilTextViewCount = lTextView.size();
        i = 0;
        while (ilTextViewCount != 4 & i <= j*60){
            Thread.sleep(j);
            i+= j;
            lTextView = driver.findElements(By.className("android.widget.TextView"));
            ilTextViewCount = lTextView.size();
        }
    }

    @Test
    public void VerifyGoalQuestions(){

//       1.Tap Plan For Retirement button
        driver.findElement(By.xpath(".//android.widget.TextView[@text='PLAN FOR RETIREMENT']")).click();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

//        Get list of text edit in Plan For Retirement screen
        List<WebElement> lTextViewQuestions = driver.findElements(By.className("android.widget.TextView"));

/*      Header of screen should be "PLAN FOR RETIREMENT"
        Questions of Plan For Retirement option should appear
        - Monthly Income
        - Location to plan
        - How much plan
        - Deadline"*/
        AssertJUnit.assertEquals("PLAN FOR RETIREMENT", lTextViewQuestions.get(0).getText());
        AssertJUnit.assertEquals("Thu nhập hàng tháng của bạn là bao nhiêu?", lTextViewQuestions.get(1).getText());
        AssertJUnit.assertEquals("Where are you planning to buy?", lTextViewQuestions.get(2).getText());
        AssertJUnit.assertEquals("How much is the house?", lTextViewQuestions.get(3).getText());
        AssertJUnit.assertEquals("When do you finish?", lTextViewQuestions.get(4).getText());
    }

    @Test(priority = 1)
    public void VerifyQuestionsValidation(){

//        2.Tap Finish button
        WebElement btn_Finish = driver.findElement(By.xpath(".//android.widget.Button[@text='Hoàn thành']"));
        btn_Finish.click();

//        Message "Please fill all fields in the form." should appear
        WebElement msg_Warning = driver.findElement(By.id("android:id/message"));
        AssertJUnit.assertEquals("Vui lòng điền tất cả các trường thông tin.", msg_Warning.getText());

//        Close message
        WebElement btn_Ok = driver.findElement(By.xpath(".//android.widget.Button[@text='Đồng ý']"));
        btn_Ok.click();

//        Get list of text edit in Plan For Retirement screen
        List<WebElement> lEditTextQuestions = driver.findElements(By.className("android.widget.EditText"));

//        3.Change value in Income field to "0"
        lEditTextQuestions.get(0).clear();
        lEditTextQuestions.get(0).sendKeys("0");

//        User should not be able to type "0" first
        AssertJUnit.assertEquals("", lEditTextQuestions.get(0).getText());

  /*      4.Input information:
        - Type ""9999999999999"" into Income field*/
        lEditTextQuestions.get(0).sendKeys("9999999999999");

//        - Select "1 year" in Location to plan
        lEditTextQuestions.get(1).click();
        driver.findElement(By.xpath(".//android.widget.Button[@text='OK']")).click();

//        - Type "1000000000" into How much plan field
        lEditTextQuestions.get(2).sendKeys("1000000000");

//        Hide keyboard
        driver.hideKeyboard();

//        5.Tap Finish button
        btn_Finish.click();

//        Message "Please check invalid data" should appear
        AssertJUnit.assertEquals("Vui lòng kiểm tra các dữ liệu không hợp lệ.", msg_Warning.getText());

//        Close message
        btn_Ok.click();
    }

    @Test(priority = 2)
    public void VerifyGoalSummary(){

//        Get list of text edit in Plan For Retirement screen
        List<WebElement> lEditTextAnswers = driver.findElements(By.className("android.widget.EditText"));

//        6.Change value in Income field to "2000000"
        lEditTextAnswers.get(0).clear();
        lEditTextAnswers.get(0).sendKeys("2000000");

//        Hide keyboard
        driver.hideKeyboard();

//        7.Tap Finish button
        driver.findElement(By.xpath(".//android.widget.Button[@text='Hoàn thành']")).click();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

//        Get list text view in Plan For Retirement screen
        List<WebElement> lTextViewSummary = driver.findElements(By.className("android.widget.TextView"));

//        Summary screen should appear with number of month as "41 years 8 months"
        AssertJUnit.assertEquals("PLAN FOR RETIREMENT", lTextViewSummary.get(0).getText());
        AssertJUnit.assertEquals("PLAN FOR RETIREMENT", lTextViewSummary.get(2).getText());
        AssertJUnit.assertTrue(lTextViewSummary.get(3).getText().contains("41 năm"));
        AssertJUnit.assertTrue(lTextViewSummary.get(3).getText().contains("8 tháng"));
        AssertJUnit.assertEquals("Bạn sẽ cần 41 năm 8 tháng để cho kế hoạch về hưu của mình. Để thực hiện được ước mơ này sớm hơn, "
                        + "bạn phải tiết kiệm nhiều hơn và có thể chọn các gói sản phẩm vay tiêu dùng bên dưới.", lTextViewSummary.get(4).getText());

//        Educations section should appear below summary text
        AssertJUnit.assertEquals("HỌC VẤN", lTextViewSummary.get(5).getText());

//        Scrolls screen to see Products section
        driver.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView("
                + "new UiSelector().text(\"SẢN PHẨM\"));");

//        Products section should appear below summary text
        AssertJUnit.assertTrue(driver.findElement(By.xpath(".//android.widget.TextView[@text='SẢN PHẨM']")).isDisplayed());
    }

    @Test(priority = 3)
    public void VerifyInComeValue() throws InterruptedException {

//        8.Tap Back icon
        WebElement btn_BackNarrow = driver.findElement(By.className("android.widget.ImageButton"));
        btn_BackNarrow.click();

//        Wait for loading
        List<WebElement> lTextView = driver.findElements(By.className("android.widget.TextView"));
        int ilTextViewCount = lTextView.size();
        int i = 0; int j = 1000;
        while (ilTextViewCount != 4 & i <= j*60){
            Thread.sleep(j);
            i+= j;
            lTextView = driver.findElements(By.className("android.widget.TextView"));
            ilTextViewCount = lTextView.size();
        }

//        9.Tap Buy A New House button
        driver.findElement(By.xpath(".//android.widget.TextView[@text='BUY A NEW HOUSE']")).click();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

//        Get list of text view in Buy A New House screen
        List<WebElement> lTextViewSummary = driver.findElements(By.className("android.widget.TextView"));

//        Summary screen should appear with number of month as "Over 50 years"
        AssertJUnit.assertTrue(lTextViewSummary.get(3).getText().contains("Trên"));
        AssertJUnit.assertTrue(lTextViewSummary.get(3).getText().contains("50 năm"));
        AssertJUnit.assertEquals("Bạn sẽ cần hơn 50 năm để mua căn nhà mơ ước. Để thực hiện được ước mơ này sớm hơn, "
                + "bạn phải tiết kiệm nhiều hơn và có thể chọn các gói sản phẩm vay tiêu dùng bên dưới.", lTextViewSummary.get(4).getText());

//        10.Tap Back icon
        btn_BackNarrow.click();

//        Wait for loading
        lTextView = driver.findElements(By.className("android.widget.TextView"));
        ilTextViewCount = lTextView.size();
        i = 0;
        while (ilTextViewCount != 4 & i <= j*60){
            Thread.sleep(j);
            i+= j;
            lTextView = driver.findElements(By.className("android.widget.TextView"));
            ilTextViewCount = lTextView.size();
        }

//        11.Tap Get Married button
        driver.findElement(By.xpath(".//android.widget.TextView[@text='GET MARRIED']")).click();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

//        Summary screen should appear with number of month as "4 years 2 months"
        AssertJUnit.assertTrue(lTextViewSummary.get(3).getText().contains("4 năm"));
        AssertJUnit.assertTrue(lTextViewSummary.get(3).getText().contains("2 tháng"));
        AssertJUnit.assertTrue(driver.findElement(By.xpath(".//android.widget.TextView[@text='Bạn sẽ cần 4 năm 2 tháng để tiết kiệm đủ tiền cho việc kết hôn. Để thực hiện được ước mơ này sớm hơn, "
                + "bạn phải tiết kiệm nhiều hơn và có thể chọn các gói sản phẩm vay tiêu dùng bên dưới.']")).isDisplayed());

//        12.Tap Back icon
        btn_BackNarrow.click();

//        Wait for loading
        lTextView = driver.findElements(By.className("android.widget.TextView"));
        ilTextViewCount = lTextView.size();
        i = 0;
        while (ilTextViewCount != 4 & i <= j*60){
            Thread.sleep(j);
            i+= j;
            lTextView = driver.findElements(By.className("android.widget.TextView"));
            ilTextViewCount = lTextView.size();
        }

//        13.Tap ""Plan For Retirement"" option
        driver.findElement(By.xpath(".//android.widget.TextView[@text='PLAN FOR RETIREMENT']")).click();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

//        Summary screen should appear again
        AssertJUnit.assertTrue(lTextViewSummary.get(3).getText().contains("41 năm"));
        AssertJUnit.assertTrue(lTextViewSummary.get(3).getText().contains("8 tháng"));
        AssertJUnit.assertEquals("Bạn sẽ cần 41 năm 8 tháng để cho kế hoạch về hưu của mình. Để thực hiện được ước mơ này sớm hơn, "
                + "bạn phải tiết kiệm nhiều hơn và có thể chọn các gói sản phẩm vay tiêu dùng bên dưới.", lTextViewSummary.get(4).getText());
    }

    @Test(priority = 4)
    public void VerifyAnswersEdition() throws InterruptedException {

//        14.Tap Edit icon
        WebElement btn_Edit = driver.findElement(By.xpath(".//android.support.v7.widget.LinearLayoutCompat/android.widget.TextView[1]"));
        btn_Edit.click();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

//        Get list of text view in Plan For Retirement screen
        List<WebElement> lTextViewQuestions = driver.findElements(By.className("android.widget.TextView"));

//        Get list of text edit in Plan For Retirement screen
        List<WebElement> lEditTextAnswers = driver.findElements(By.className("android.widget.EditText"));

//        Questions list of Plan For Retirement option should appear
        AssertJUnit.assertEquals("Thu nhập hàng tháng của bạn là bao nhiêu?", lTextViewQuestions.get(1).getText());
        AssertJUnit.assertEquals("Where are you planning to buy?", lTextViewQuestions.get(2).getText());
        AssertJUnit.assertEquals("How much is the house?", lTextViewQuestions.get(3).getText());
        AssertJUnit.assertEquals("When do you finish?", lTextViewQuestions.get(4).getText());

//        Data should not be changed
        AssertJUnit.assertEquals("2.000.000", lEditTextAnswers.get(0).getText());
        AssertJUnit.assertEquals("1 year", lEditTextAnswers.get(1).getText());
        AssertJUnit.assertEquals("1.000.000.000", lEditTextAnswers.get(2).getText());

//        15.Change value in Income field to "2000000000"
        lEditTextAnswers.get(0).clear();
        lEditTextAnswers.get(0).sendKeys("2000000000");

//        Hide keyboard
        driver.hideKeyboard();

//        16.Tap Finish button
        driver.findElement(By.xpath(".//android.widget.Button[@text='Hoàn thành']")).click();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

//        Get list of text view in Summary screen
        List<WebElement> lTextViewSummary = driver.findElements(By.className("android.widget.TextView"));

//        Summary screen should appear with number of month as "0 month"
        AssertJUnit.assertEquals("0 tháng", lTextViewSummary.get(3).getText());
        AssertJUnit.assertEquals("Bạn sẽ cần 0 tháng để cho kế hoạch về hưu của mình. Để thực hiện được ước mơ này sớm hơn, "
                + "bạn phải tiết kiệm nhiều hơn và có thể chọn các gói sản phẩm vay tiêu dùng bên dưới.", lTextViewSummary.get(4).getText());

//        17.Tap Back icon
        WebElement btn_BackNarrow = driver.findElement(By.className("android.widget.ImageButton"));
        btn_BackNarrow.click();

//        Wait for loading
        List<WebElement>lTextView = driver.findElements(By.className("android.widget.TextView"));
        int ilTextViewCount = lTextView.size();
        int i = 0; int j = 1000;
        while (ilTextViewCount != 4 & i <= j*60){
            Thread.sleep(j);
            i+= j;
            lTextView = driver.findElements(By.className("android.widget.TextView"));
            ilTextViewCount = lTextView.size();
        }

//        18.Tap "Buy A New House" option
        driver.findElement(By.xpath(".//android.widget.TextView[@text='BUY A NEW HOUSE']")).click();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

//        Get list of text view in Summary screen
        lTextViewSummary = driver.findElements(By.className("android.widget.TextView"));

//        Summary screen should appear with number of month as "1 month"
        AssertJUnit.assertEquals("1 tháng", lTextViewSummary.get(3).getText());
        AssertJUnit.assertEquals("Bạn sẽ cần 1 tháng để mua căn nhà mơ ước. Để thực hiện được ước mơ này sớm hơn, "
                + "bạn phải tiết kiệm nhiều hơn và có thể chọn các gói sản phẩm vay tiêu dùng bên dưới.", lTextViewSummary.get(4).getText());

//        19.Tap Edit button
        btn_Edit.click();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

//        Income of "Buy A New House" option should be "2000000000"
        AssertJUnit.assertEquals("2.000.000.000", lEditTextAnswers.get(0).getText());

//        20.Tap Back icon
        btn_BackNarrow.click();
//        Wait for loading
        lTextView = driver.findElements(By.className("android.widget.TextView"));
        ilTextViewCount = lTextView.size();
        i = 0;
        while (ilTextViewCount != 4 & i <= j*60){
            Thread.sleep(j);
            i+= j;
            lTextView = driver.findElements(By.className("android.widget.TextView"));
            ilTextViewCount = lTextView.size();
        }

//        21.Tap Get Married button
        driver.findElement(By.xpath(".//android.widget.TextView[@text='GET MARRIED']")).click();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

//        Get list of text view in Summary screen
        lTextViewSummary = driver.findElements(By.className("android.widget.TextView"));

//        Summary screen should appear with number of month as "0 month"
        AssertJUnit.assertEquals("0 tháng", lTextViewSummary.get(3).getText());
        AssertJUnit.assertEquals("Bạn sẽ cần 0 tháng để tiết kiệm đủ tiền cho việc kết hôn. Để thực hiện được ước mơ này sớm hơn, "
                + "bạn phải tiết kiệm nhiều hơn và có thể chọn các gói sản phẩm vay tiêu dùng bên dưới.", lTextViewSummary.get(4).getText());

//        22.Tap Edit button
        btn_Edit.click();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

//        Income of "Get Married" option should be "2000000000"
        AssertJUnit.assertEquals("2.000.000.000", lEditTextAnswers.get(0).getText());
    }

    @AfterTest
    public void tearDown(){
        driver.closeApp();
        driver.quit();
    }
}
