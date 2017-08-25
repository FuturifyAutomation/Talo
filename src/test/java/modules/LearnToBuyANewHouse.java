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

public class LearnToBuyANewHouse {

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

//        1.Tap My Goals module
        driver.findElement(By.xpath(".//android.widget.TextView[@text='Mục tiêu cá nhân']")).click();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

//        Plan to buy a new house (2.Tap Buy A New House option)
        driver.findElement(By.xpath(".//android.widget.TextView[@text='BUY A NEW HOUSE']")).click();
        List<WebElement> lEditTextQuestions = driver.findElements(By.className("android.widget.EditText"));
        lEditTextQuestions.get(0).sendKeys("20000000");
        lEditTextQuestions.get(1).click();
        driver.findElement(By.xpath(".//android.widget.Button[@text='OK']")).click();
        lEditTextQuestions.get(2).sendKeys("2000000000");
        driver.hideKeyboard();
        driver.findElement(By.xpath(".//android.widget.Button[@text='Hoàn thành']")).click();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

//        Wait for loading
        List<WebElement> lTextViewSummary = driver.findElements(By.className("android.widget.TextView"));
        int iTextViewCount = lTextViewSummary.size();
        int i = 0;
        Thread.sleep(2000);
        while (iTextViewCount != 8 & i <= 60){
            Thread.sleep(1000);
            lTextViewSummary = driver.findElements(By.className("android.widget.TextView"));
            iTextViewCount = lTextViewSummary.size();
            i++;
        }
    }

    @Test
    public void VerifyCoursesInGoal(){

        List<WebElement> lTextViewSummary = driver.findElements(By.className("android.widget.TextView"));

//        Summary screen of Buy A New House option should appear
        AssertJUnit.assertTrue(lTextViewSummary.get(3).getText().contains("8 năm"));
        AssertJUnit.assertTrue(lTextViewSummary.get(3).getText().contains("4 tháng"));
        AssertJUnit.assertEquals("Bạn sẽ cần 8 năm 4 tháng để mua căn nhà mơ ước. Để thực hiện được ước mơ này sớm hơn, "
                + "bạn phải tiết kiệm nhiều hơn và có thể chọn các gói sản phẩm vay tiêu dùng bên dưới.", lTextViewSummary.get(4).getText());

//        Education section should appear within 2 courses "Buy Cheap House Course" and "Buy Expensive House Course"
        AssertJUnit.assertEquals("HỌC VẤN", lTextViewSummary.get(5).getText());
        AssertJUnit.assertEquals("Buy Cheap House Course", lTextViewSummary.get(6).getText());

//        Scrolls screen to see Products section
        driver.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView("
                + "new UiSelector().text(\"SẢN PHẨM\"));");

//        Products section should appear below summary text
        lTextViewSummary = driver.findElements(By.className("android.widget.TextView"));
        AssertJUnit.assertTrue(lTextViewSummary.get(6).getText().contains("Buy Expensive House"));
    }

    @Test(priority = 1)
    public void VerifyLessonsInCourse() throws InterruptedException {

//        3.Tap on Buy Cheap House Course
        driver.findElement(By.xpath(".//android.widget.TextView[@text='Buy Cheap House Course']")).click();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

//        Get list of text view in Course screen
        List<WebElement> lTextViewCourse = driver.findElements(By.className("android.widget.TextView"));

        int iTextViewCount = lTextViewCourse.size();
        int i = 0;
        while (iTextViewCount != 9 & i <= 60){
            Thread.sleep(1000);
            lTextViewCourse = driver.findElements(By.className("android.widget.TextView"));
            iTextViewCount = lTextViewCourse.size();
            i++;
        }

//        Buy Cheap House Course screen should appear within 2 lessons "Cheap House Lesson 01" and "Cheap House Lesson 02"
        AssertJUnit.assertEquals("Buy Cheap House Course", lTextViewCourse.get(0).getText());
        AssertJUnit.assertEquals("Cheap House Lesson 01", lTextViewCourse.get(1).getText());
        AssertJUnit.assertEquals("Cheap House Lesson 02", lTextViewCourse.get(5).getText());

//        4.Tap Back button
        driver.findElement(By.className("android.widget.ImageButton")).click();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

//        Get list of text view in Summary screen
        List<WebElement> lTextViewSummary = driver.findElements(By.className("android.widget.TextView"));

//        Education section should appear within 2 courses "Buy Cheap House Course" and "Buy Expensive House Course"
        AssertJUnit.assertEquals("Buy Cheap House Course", lTextViewSummary.get(4).getText());
        AssertJUnit.assertTrue(lTextViewSummary.get(6).getText().contains("Buy Expensive House"));

//        5.Tap on Buy Expensive House Course
        lTextViewSummary.get(6).click();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

//        Get list of text view in Course screen
        lTextViewCourse = driver.findElements(By.className("android.widget.TextView"));

        iTextViewCount = lTextViewCourse.size();
        i = 0;
        while (iTextViewCount != 9 & i <= 60) {
            Thread.sleep(1000);
            lTextViewCourse = driver.findElements(By.className("android.widget.TextView"));
            iTextViewCount = lTextViewCourse.size();
            i++;
        }

//        Buy Expensive House Course screen should appear within 2 lessons "Expensive House Lesson 01" and "Expensive House Lesson 02"
        AssertJUnit.assertEquals("Buy Expensive House Course", lTextViewCourse.get(0).getText());
        AssertJUnit.assertEquals("Expensive House Lesson 01", lTextViewCourse.get(1).getText());
        AssertJUnit.assertEquals("Expensive House Lesson 02", lTextViewCourse.get(5).getText());
    }

    @Test(priority = 2)
    public void VerifyLessonContent() throws InterruptedException {

//        6.Tap on Expensive House Lesson 02
        driver.findElement(By.xpath(".//android.widget.TextView[@text='Expensive House Lesson 02']")).click();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

//        Get list of text view in Lesson screen
        List<WebElement> lTextViewLesson = driver.findElements(By.className("android.widget.TextView"));

//        Description of Expensive House Lesson 02 should appear
        AssertJUnit.assertEquals("Expensive House Lesson 02", lTextViewLesson.get(0).getText());
        AssertJUnit.assertEquals("A house is a building that functions as a home, ranging from simple dwellings such as rudimentary huts of nomadic tribes and the improvised "
                + "shacks in shantytowns to complex, fixed structures of wood, brick, concrete or other materials containing plumbing, ventilation and electrical systems. "
                + "Houses use a range of different roofing systems to keep precipitation such as rain from getting into the dwelling space. Houses may have doors or locks to secure the dwelling space "
                + "and protect its inhabitants and contents from burglars or other trespassers.", lTextViewLesson.get(1).getText());

//        Scrolls screen to see Finish button
        driver.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView("
                + "new UiSelector().text(\"Hoàn thành\"));");

//        Finish button should appear
        WebElement btn_Finish = driver.findElement(By.xpath(".//android.widget.Button[@text='Hoàn thành']"));
        AssertJUnit.assertTrue(btn_Finish.isEnabled());

//        Start Quiz button should not appear: Only one button (Finish) appears in Lesson screen
        List<WebElement> lButtons = driver.findElements(By.className("android.widget.Button"));
        AssertJUnit.assertEquals(1, lButtons.size());

//        7.Tap Finish button
        btn_Finish.click();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

//        Get list of text view in Congratulation screen
        List<WebElement> lTextViewCongratulation = driver.findElements(By.className("android.widget.TextView"));

//        Message "Congratulation! You earned 20 TL" should appear
        AssertJUnit.assertEquals("Chúc mừng bạn!", lTextViewCongratulation.get(0).getText());
        AssertJUnit.assertEquals("Chúc mừng bạn!", lTextViewCongratulation.get(1).getText());
        AssertJUnit.assertEquals("Bạn đã nhận được", lTextViewCongratulation.get(2).getText());
        AssertJUnit.assertEquals("20", lTextViewCongratulation.get(3).getText());
        AssertJUnit.assertEquals("TL", lTextViewCongratulation.get(4).getText());

//        8.Tap Back button
        WebElement btn_BackNarrow = driver.findElement(By.className("android.widget.ImageButton"));
        btn_BackNarrow.click();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

//        Get list of text view in Course screen
        List<WebElement> lTextViewCourse = driver.findElements(By.className("android.widget.TextView"));

//        Buy Expensive House Course screen should appear within 2 lessons "Expensive House Lesson 01" and "Expensive House Lesson 02"
        AssertJUnit.assertEquals("Buy Expensive House Course", lTextViewCourse.get(0).getText());
        AssertJUnit.assertEquals("Expensive House Lesson 01", lTextViewCourse.get(1).getText());
        AssertJUnit.assertEquals("Expensive House Lesson 02", lTextViewCourse.get(5).getText());

//        9.Tap Back button
        btn_BackNarrow.click();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

//        Wait for loading
        List<WebElement> lTextViewSummary = driver.findElements(By.className("android.widget.TextView"));
        int iTextViewCount = lTextViewSummary.size();
        int i = 0;
        while (iTextViewCount != 9 & i <= 0){
            Thread.sleep(1000);
            lTextViewSummary = driver.findElements(By.className("android.widget.TextView"));
            iTextViewCount = lTextViewSummary.size();
            i++;
        }

//        Summary screen of Buy A New House option should appear
        AssertJUnit.assertEquals("Buy Cheap House Course", lTextViewSummary.get(4).getText());
        AssertJUnit.assertTrue(lTextViewSummary.get(6).getText().contains("Buy Expensive House"));

//        10.Tap on Buy Cheap House Course
        lTextViewSummary.get(4).click();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

//        11.Tap on Cheap House Lesson 01
        driver.findElement(By.xpath(".//android.widget.TextView[@text='Cheap House Lesson 01']")).click();

//        Get list of text view in Lesson screen
        lTextViewLesson = driver.findElements(By.className("android.widget.TextView"));

//        Description of Expensive House Lesson 01 should appear
        AssertJUnit.assertEquals("Cheap House Lesson 01", lTextViewLesson.get(0).getText());
        AssertJUnit.assertEquals("A house is a building that functions as a home, ranging from simple dwellings such as rudimentary huts of nomadic tribes and the improvised "
                + "shacks in shantytowns to complex, fixed structures of wood, brick, concrete or other materials containing plumbing, ventilation and electrical systems. "
                + "Houses use a range of different roofing systems to keep precipitation such as rain from getting into the dwelling space. Houses may have doors or locks to secure the dwelling space "
                + "and protect its inhabitants and contents from burglars or other trespassers.", lTextViewLesson.get(1).getText());

//        Scrolls screen to see Finish button
        driver.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView("
                + "new UiSelector().text(\"Làm đánh giá\"));");

//        Start Quiz button should appear at the bottom
        AssertJUnit.assertTrue(driver.findElement(By.xpath(".//android.widget.Button[@text='Làm đánh giá']")).isEnabled());

//        Finish button should not appear: Only one button (Start Quiz) appears in Lesson screen
        lButtons = driver.findElements(By.className("android.widget.Button"));
        AssertJUnit.assertEquals(1, lButtons.size());
    }

    @Test(priority = 3)
    public void VerifyQuestionsContent() throws InterruptedException {

//        12.Tap Start Quiz button
        driver.findElement(By.xpath(".//android.widget.Button[@text='Làm đánh giá']")).click();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

//        Get list of text view in question screen
        List<WebElement> lTextViewQuestions = driver.findElements(By.className("android.widget.TextView"));

//        Title of screen should be Cheap House Lesson 01
        AssertJUnit.assertEquals("Cheap House Lesson 01", lTextViewQuestions.get(0).getText());

//        3 questions of Cheap House Lesson 01 should appear:
//        - Type your answer into below text field
        AssertJUnit.assertEquals("Type your answer into below text field (*)", lTextViewQuestions.get(1).getText());

//        - Select only one answer in below list
        AssertJUnit.assertEquals("Select only one answer in below list (*)", lTextViewQuestions.get(2).getText());

//        - Select multiple answers in below list"
        AssertJUnit.assertEquals("Select multiple answers in below list (*)", lTextViewQuestions.get(3).getText());

//        Get list of text edit in question screen
        List<WebElement> lEditTextAnswers = driver.findElements(By.className("android.widget.EditText"));

//        Type answer into first question
        lEditTextAnswers.get(0).sendKeys("testing");

//        Select answer for second question
        driver.findElement(By.xpath(".//android.widget.RadioButton[@text='Option 01']")).click();

//        Scrolls screen to see Finish button
        driver.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView("
                + "new UiSelector().text(\"Hoàn thành\"));");

//        Finish button should appear at the bottom
        WebElement btn_Finish = driver.findElement(By.xpath(".//android.widget.Button[@text='Hoàn thành']"));
        AssertJUnit.assertTrue(btn_Finish.isEnabled());

//        13.Tap Finish button
        btn_Finish.click();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

//        Message "Please fill all required fields." should appear
        AssertJUnit.assertEquals("Vui lòng điền tất cả các trường bắt buộc.", driver.findElement(By.id("android:id/message")).getText());

//        Close message
        driver.findElement(By.xpath(".//android.widget.Button[@text='Đồng ý']")).click();

/*        14.Fill all required fields:
        - Field 1: testing
        - Field 2: Option 01
        - Field 3: Option 02*/
        driver.findElement(By.xpath(".//android.widget.CheckBox[@text='Option 02']")).click();

//        15.Tap Finish button
        btn_Finish.click();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

//        Wait for loading
        List<WebElement> lTextViewCongratulation = driver.findElements(By.className("android.widget.TextView"));
        int iTextViewCount = lTextViewCongratulation.size();
        int i = 0;
        while (iTextViewCount != 5 & i <= 60){
            Thread.sleep(1000);
            lTextViewCongratulation = driver.findElements(By.className("android.widget.TextView"));
            iTextViewCount = lTextViewCongratulation.size();
            i++;
        }

//        Message "Congratulation! You earned 20 TL" should appear
        AssertJUnit.assertEquals("Chúc mừng bạn!", lTextViewCongratulation.get(0).getText());
        AssertJUnit.assertEquals("Chúc mừng bạn!", lTextViewCongratulation.get(1).getText());
        AssertJUnit.assertEquals("Bạn đã nhận được", lTextViewCongratulation.get(2).getText());
        AssertJUnit.assertEquals("20", lTextViewCongratulation.get(3).getText());
        AssertJUnit.assertEquals("TL", lTextViewCongratulation.get(4).getText());

//        16.Tap Back button
        WebElement btn_BackNarrow = driver.findElement(By.className("android.widget.ImageButton"));
        btn_BackNarrow.click();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

//        Wait for loading
        List<WebElement> lTextViewCourse = driver.findElements(By.className("android.widget.TextView"));
        iTextViewCount = lTextViewCongratulation.size();
        i = 0;
        while (iTextViewCount == 5 & i <= 60){
            Thread.sleep(1000);
            lTextViewCongratulation = driver.findElements(By.className("android.widget.TextView"));
            iTextViewCount = lTextViewCongratulation.size();
            i++;
        }

//        Buy Cheap House Course screen should appear within 2 lessons "Cheap House Lesson 01" and "Cheap House Lesson 02"
        AssertJUnit.assertEquals("Buy Cheap House Course", lTextViewCourse.get(0).getText());
        AssertJUnit.assertEquals("Cheap House Lesson 01", lTextViewCourse.get(1).getText());
        AssertJUnit.assertEquals("Cheap House Lesson 02", lTextViewCourse.get(5).getText());

//        17.Tap Back button
        btn_BackNarrow.click();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

//        Wait for loading
        List<WebElement> lTextViewSummary = driver.findElements(By.className("android.widget.TextView"));
        iTextViewCount = lTextViewSummary.size();
        i = 0;
        while (iTextViewCount != 9 & i <= 60){
            Thread.sleep(1000);
            lTextViewSummary = driver.findElements(By.className("android.widget.TextView"));
            iTextViewCount = lTextViewSummary.size();
            i++;
        }

//        Summary screen of Buy A New House option should appear
        AssertJUnit.assertEquals("Buy Cheap House Course", lTextViewSummary.get(4).getText());
        AssertJUnit.assertTrue(lTextViewSummary.get(6).getText().contains("Buy Expensive House"));

//        18.Tap Back button
        btn_BackNarrow.click();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

//        Get list of text view in Summary screen
        List<WebElement> lTextViewGoals = driver.findElements(By.className("android.widget.TextView"));

//        My Goals screen should appear
        AssertJUnit.assertEquals("Mục tiêu cá nhân", lTextViewGoals.get(0).getText());
        AssertJUnit.assertEquals("BUY A NEW HOUSE", lTextViewGoals.get(1).getText());
        AssertJUnit.assertEquals("GET MARRIED", lTextViewGoals.get(2).getText());
        AssertJUnit.assertEquals("PLAN FOR RETIREMENT", lTextViewGoals.get(3).getText());
    }

    @AfterTest
    public void tearDown(){
        driver.closeApp();
        driver.quit();
    }
}
