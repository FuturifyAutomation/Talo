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

public class LearnToPlanRetirement {

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

//        Plan for retirement (1.Tap Plan For Retirement option)
        driver.findElement(By.xpath(".//android.widget.TextView[@text='PLAN FOR RETIREMENT']")).click();
        List<WebElement> lEditTextQuestions = driver.findElements(By.className("android.widget.EditText"));
        lEditTextQuestions.get(0).sendKeys("5000000");
        lEditTextQuestions.get(1).click();
        driver.findElement(By.xpath(".//android.widget.Button[@text='OK']")).click();
        lEditTextQuestions.get(2).sendKeys("1000000000");
        driver.hideKeyboard();
        driver.findElement(By.xpath(".//android.widget.Button[@text='Hoàn thành']")).click();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

//        Wait for loading
        List<WebElement> lTextView = driver.findElements(By.className("android.widget.TextView"));
        int ilTextViewCount = lTextView.size();
        int i = 0;
        Thread.sleep(2000);
        while (ilTextViewCount != 8 & i <= 60){
            Thread.sleep(1000);
            lTextView = driver.findElements(By.className("android.widget.TextView"));
            ilTextViewCount = lTextView.size();
            i++;
        }
    }

    @Test
    public void VerifyCoursesInGoal(){

        List<WebElement> lTextViewSummary = driver.findElements(By.className("android.widget.TextView"));

//        Summary screen of Plan For Retirement option should appear
        AssertJUnit.assertTrue(lTextViewSummary.get(3).getText().contains("16 năm"));
        AssertJUnit.assertTrue(lTextViewSummary.get(3).getText().contains("8 tháng"));
        AssertJUnit.assertEquals("Bạn sẽ cần 16 năm 8 tháng để cho kế hoạch về hưu của mình. Để thực hiện được ước mơ này sớm hơn, "
                + "bạn phải tiết kiệm nhiều hơn và có thể chọn các gói sản phẩm vay tiêu dùng bên dưới.", lTextViewSummary.get(4).getText());

//        Education section should appear within course "Retirement Course"
        AssertJUnit.assertEquals("HỌC VẤN", lTextViewSummary.get(5).getText());
        AssertJUnit.assertEquals("Retirement Course", lTextViewSummary.get(6).getText());
    }

    @Test(priority = 1)
    public void VerifyLessonsInCourse() throws InterruptedException {

//        2.Tap on Retirement Course
        driver.findElement(By.xpath(".//android.widget.TextView[@text='Retirement Course']")).click();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

//        Get list of text view in Course screen
        List<WebElement> lTextViewCourse = driver.findElements(By.className("android.widget.TextView"));

        int iTextViewCount = lTextViewCourse.size();
        int i = 0;
        while (iTextViewCount != 9 & i <= 60) {
            Thread.sleep(1000);
            lTextViewCourse = driver.findElements(By.className("android.widget.TextView"));
            iTextViewCount = lTextViewCourse.size();
            i++;
        }

//        Retirement Course screen should appear within 2 lessons "Plan Retirement Lesson 01" and "Plan Retirement Lesson 02"
        AssertJUnit.assertEquals("Retirement Course", lTextViewCourse.get(0).getText());
        AssertJUnit.assertEquals("Plan Retirement Lesson 01", lTextViewCourse.get(1).getText());
        AssertJUnit.assertEquals("Plan Retirement Lesson 02", lTextViewCourse.get(5).getText());
    }

    @Test(priority = 2)
    public void VerifyLessonContent() throws InterruptedException {

//        Get list of text view in Course screen
        List<WebElement> lTextViewCourse = driver.findElements(By.className("android.widget.TextView"));

//        3.Tap on Plan Retirement Lesson 02
        lTextViewCourse.get(5).click();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

//        Get list of text view in Lesson screen
        List<WebElement> lTextViewLesson = driver.findElements(By.className("android.widget.TextView"));

//        Description of Plan Retirement Lesson 02 should appear
        AssertJUnit.assertEquals("Plan Retirement Lesson 02", lTextViewLesson.get(0).getText());
        AssertJUnit.assertEquals("Many people choose to retire when they are eligible for private or public pension benefits, although some are forced to retire "
                + "when physical conditions no longer allow the person to work any longer (by illness or accident) "
                + "or as a result of legislation concerning their position. In most countries, the idea of retirement is of recent origin, being introduced "
                + "during the late 19th and early 20th centuries. Previously, low life expectancy and the absence of pension arrangements meant that most workers continued to work until death. "
                + "Germany was the first country to introduce retirement, in 1889.", lTextViewLesson.get(1).getText());

//        Scrolls screen to see Finish button
        driver.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView("
                + "new UiSelector().text(\"Hoàn thành\"));");

//        Start Quiz button should not appear at the bottom: Only one button (Finish) appears in Lesson screen
        List<WebElement> lButtons = driver.findElements(By.className("android.widget.Button"));
        AssertJUnit.assertEquals(1, lButtons.size());

//        Finish button should appear
        WebElement btn_Finish = driver.findElement(By.xpath(".//android.widget.Button[@text='Hoàn thành']"));
        AssertJUnit.assertTrue(btn_Finish.isEnabled());

//        4.Tap Finish button
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

//        5.Tap Back button
        driver.findElement(By.className("android.widget.ImageButton")).click();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

//        Get list of text view in Summary screen
        lTextViewCourse = driver.findElements(By.className("android.widget.TextView"));

//        Retirement Course screen should appear within 2 lessons "Plan Retirement Lesson 01" and "Plan Retirement Lesson 02"
        AssertJUnit.assertEquals("Retirement Course", lTextViewCourse.get(0).getText());
        AssertJUnit.assertEquals("Plan Retirement Lesson 01", lTextViewCourse.get(1).getText());
        AssertJUnit.assertEquals("Plan Retirement Lesson 02", lTextViewCourse.get(5).getText());

//        6.Tap on Plan Retirement Lesson 01
        lTextViewCourse.get(1).click();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

//        Get list of text view in Lesson screen
        lTextViewLesson = driver.findElements(By.className("android.widget.TextView"));

//        Description of Plan Retirement Lesson 01 should appear
        AssertJUnit.assertEquals("Plan Retirement Lesson 01", lTextViewLesson.get(0).getText());
        AssertJUnit.assertEquals("Many people choose to retire when they are eligible for private or public pension benefits, although some are forced to retire "
                + "when physical conditions no longer allow the person to work any longer (by illness or accident) "
                + "or as a result of legislation concerning their position. In most countries, the idea of retirement is of recent origin, being introduced "
                + "during the late 19th and early 20th centuries. Previously, low life expectancy and the absence of pension arrangements meant that most workers continued to work until death. "
                + "Germany was the first country to introduce retirement, in 1889.", lTextViewLesson.get(1).getText());

//        Scrolls screen to see Finish button
        driver.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView("
                + "new UiSelector().text(\"Làm đánh giá\"));");

//        Finish button should not appear at the bottom: Only one button (Start Quiz) appears in Lesson screen
        lButtons = driver.findElements(By.className("android.widget.Button"));
        AssertJUnit.assertEquals(1, lButtons.size());

//        Start Quiz button should appear
        AssertJUnit.assertTrue(driver.findElement(By.xpath(".//android.widget.Button[@text='Làm đánh giá']")).isEnabled());
    }

    @Test(priority = 3)
    public void VerifyQuestionsContent() throws InterruptedException {

//        7.Tap Start Quiz button
        driver.findElement(By.xpath(".//android.widget.Button[@text='Làm đánh giá']")).click();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

//        Get list of text view in question screen
        List<WebElement> lTextViewQuestions = driver.findElements(By.className("android.widget.TextView"));

//        Title of screen should be Plan Retirement Lesson 01
        AssertJUnit.assertEquals("Plan Retirement Lesson 01", lTextViewQuestions.get(0).getText());

//        3 questions of Plan Retirement Lesson 01 should appear:
//        - Type your answer into below text field
        AssertJUnit.assertEquals("Type your answer into below text field (*)", lTextViewQuestions.get(1).getText());

//        - Select only one answer in below list
        AssertJUnit.assertEquals("Select only one answer in below list (*)", lTextViewQuestions.get(2).getText());

//        - Select multiple answers in below list"
        AssertJUnit.assertEquals("Select mutiple answers in below list (*)", lTextViewQuestions.get(3).getText());

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

//        8.Tap Finish button
        btn_Finish.click();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

//        Message "Please fill all required fields." should appear
        AssertJUnit.assertEquals("Vui lòng điền tất cả các trường bắt buộc.", driver.findElement(By.id("android:id/message")).getText());

//        Close message
        driver.findElement(By.xpath(".//android.widget.Button[@text='Đồng ý']")).click();

/*        9.Fill all required fields:
        - Field 1: testing
        - Field 2: Option 01
        - Field 3: Option 02*/
        driver.findElement(By.xpath(".//android.widget.CheckBox[@text='Option 02']")).click();

//        10.Tap Finish button
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

//        11.Tap Back button
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

//        Retirement Course screen should appear within 2 lessons "Plan Retirement Lesson 01" and "Plan Retirement Lesson 02"
        AssertJUnit.assertEquals("Retirement Course", lTextViewCourse.get(0).getText());
        AssertJUnit.assertEquals("Plan Retirement Lesson 01", lTextViewCourse.get(1).getText());
        AssertJUnit.assertEquals("Plan Retirement Lesson 02", lTextViewCourse.get(5).getText());

//        12.Tap Back button
        btn_BackNarrow.click();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

//        Wait for loading
        List<WebElement> lTextViewSummary = driver.findElements(By.className("android.widget.TextView"));
        iTextViewCount = lTextViewSummary.size();
        i = 0;
        while (iTextViewCount != 8 & i <= 60){
            Thread.sleep(1000);
            lTextViewSummary = driver.findElements(By.className("android.widget.TextView"));
            iTextViewCount = lTextViewSummary.size();
            i++;
        }

//        Summary screen of Plan For Retirement option should appear
        AssertJUnit.assertEquals("Retirement Course", lTextViewSummary.get(6).getText());

//        13.Tap Back button
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
