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

public class LearnToGetMarried {

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

//        Plan to get married (1.Tap Get Married option)
        driver.findElement(By.xpath(".//android.widget.TextView[@text='GET MARRIED']")).click();
        List<WebElement> lEditTextQuestions = driver.findElements(By.className("android.widget.EditText"));
        lEditTextQuestions.get(0).sendKeys("20000000");
        lEditTextQuestions.get(1).click();
        driver.findElement(By.xpath(".//android.widget.Button[@text='OK']")).click();
        lEditTextQuestions.get(2).sendKeys("2000000000");
        driver.hideKeyboard();
        driver.findElement(By.xpath(".//android.widget.Button[@text='Hoàn thành']")).click();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

//        Wait for loading
        List<WebElement> lTextView = driver.findElements(By.className("android.widget.TextView"));
        int iTextViewCount = lTextView.size();
        int i = 0;
        Thread.sleep(2000);
        while (iTextViewCount != 8 & i <= 60){
            Thread.sleep(1000);
            lTextView = driver.findElements(By.className("android.widget.TextView"));
            iTextViewCount = lTextView.size();
            i++;
        }
    }

    @Test
    public void VerifyCoursesInGoal(){

        List<WebElement> lTextViewSummary = driver.findElements(By.className("android.widget.TextView"));

//        Summary screen of Get Married option should appear
        AssertJUnit.assertTrue(lTextViewSummary.get(3).getText().contains("8 năm"));
        AssertJUnit.assertTrue(lTextViewSummary.get(3).getText().contains("4 tháng"));
        AssertJUnit.assertEquals("Bạn sẽ cần 8 năm 4 tháng để tiết kiệm đủ tiền cho việc kết hôn. Để thực hiện được ước mơ này sớm hơn, "
                + "bạn phải tiết kiệm nhiều hơn và có thể chọn các gói sản phẩm vay tiêu dùng bên dưới.", lTextViewSummary.get(4).getText());

//        Education section should appear within 2 courses "Organize Big Wedding Course" and "Organize Small Wedding Course"
        AssertJUnit.assertEquals("HỌC VẤN", lTextViewSummary.get(5).getText());
        AssertJUnit.assertTrue(lTextViewSummary.get(6).getText().contains("Organize Small Wedding"));

//        Scrolls screen to see Products section
        driver.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView("
                + "new UiSelector().text(\"SẢN PHẨM\"));");

//        Products section should appear below summary text
        lTextViewSummary = driver.findElements(By.className("android.widget.TextView"));
        AssertJUnit.assertTrue(lTextViewSummary.get(6).getText().contains("Organize Big Wedding"));
    }

    @Test(priority = 1)
    public void VerifyLessonsInCourse() throws InterruptedException {

//        2.Tap on Organize Small Wedding Course
        driver.findElement(By.xpath(".//android.widget.TextView[contains(@text,'Organize Small Wedding')]")).click();
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

//        Organize Small Wedding Course screen should appear within 2 lessons "Small Wedding Lesson 01" and "Small Wedding Lesson 02"
        AssertJUnit.assertEquals("Organize Small Wedding Course", lTextViewCourse.get(0).getText());
        AssertJUnit.assertEquals("Small Wedding Lesson 01", lTextViewCourse.get(1).getText());
        AssertJUnit.assertEquals("Small Wedding Lesson 02", lTextViewCourse.get(5).getText());
    }

    @Test(priority = 2)
    public void VerifyLessonContent() throws InterruptedException {

//        Get list of text view in Course screen
        List<WebElement> lTextViewCourse = driver.findElements(By.className("android.widget.TextView"));

//        3.Tap on Small Wedding Lesson 01
        lTextViewCourse.get(1).click();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

//        Get list of text view in Lesson screen
        List<WebElement> lTextViewLesson = driver.findElements(By.className("android.widget.TextView"));

//        Description of Small Wedding Lesson 01 should appear
        AssertJUnit.assertEquals("Small Wedding Lesson 01", lTextViewLesson.get(0).getText());
        AssertJUnit.assertEquals("A wedding is a ceremony where two people are united in marriage. Wedding traditions and customs vary greatly between cultures, ethnic groups, religions, countries, and social classes. Most wedding "
                + "ceremonies involve an exchange of marriage vows by the couple, presentation of a gift (offering, ring(s), symbolic item, "
                + "flowers, money), and a public proclamation of marriage by an authority figure. Special wedding garments are often worn, "
                + "and the ceremony is sometimes followed by a wedding reception. Music, poetry, prayers or readings from religious texts "
                + "or literature are also commonly incorporated into the ceremony.", lTextViewLesson.get(1).getText());

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

//        Message "Congratulation! You earned 15 TL" should appear
        AssertJUnit.assertEquals("Chúc mừng bạn!", lTextViewCongratulation.get(0).getText());
        AssertJUnit.assertEquals("Chúc mừng bạn!", lTextViewCongratulation.get(1).getText());
        AssertJUnit.assertEquals("Bạn đã nhận được", lTextViewCongratulation.get(2).getText());
        AssertJUnit.assertEquals("15", lTextViewCongratulation.get(3).getText());
        AssertJUnit.assertEquals("TL", lTextViewCongratulation.get(4).getText());

//        5.Tap Back button
        WebElement btn_BackNarrow = driver.findElement(By.className("android.widget.ImageButton"));
        btn_BackNarrow.click();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

//        Get list of text view in Summary screen
        lTextViewCourse = driver.findElements(By.className("android.widget.TextView"));

//        Organize Small Wedding Course screen should appear within 2 lessons "Small Wedding Lesson 01" and "Small Wedding Lesson 02"
        AssertJUnit.assertEquals("Organize Small Wedding Course", lTextViewCourse.get(0).getText());
        AssertJUnit.assertEquals("Small Wedding Lesson 01", lTextViewCourse.get(1).getText());
        AssertJUnit.assertEquals("Small Wedding Lesson 02", lTextViewCourse.get(5).getText());

//        6.Tap Back button
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

//        Summary screen of Get Married option should appear
        AssertJUnit.assertTrue(lTextViewSummary.get(4).getText().contains("Organize Small Wedding"));
        AssertJUnit.assertTrue(lTextViewSummary.get(6).getText().contains("Organize Big Wedding"));

//        7.Tap on Organize Big Wedding Course
        lTextViewSummary.get(6).click();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

//        Get list of text view in Course screen
        lTextViewCourse = driver.findElements(By.className("android.widget.TextView"));

//        Organize Big Wedding Course screen should appear within 2 lessons "Big Wedding Lesson 01" and "Big Wedding Lesson 02"
        AssertJUnit.assertEquals("Organize Big Wedding Course", lTextViewCourse.get(0).getText());
        AssertJUnit.assertEquals("Big Wedding Lesson 01", lTextViewCourse.get(1).getText());
        AssertJUnit.assertEquals("Big Wedding Lesson 02", lTextViewCourse.get(5).getText());

//        8.Tap on Big Wedding Lesson 01
        lTextViewCourse.get(1).click();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

//        Get list of text view in Lesson screen
        lTextViewLesson = driver.findElements(By.className("android.widget.TextView"));

//        Description of Big Wedding Lesson 01 should appear
        AssertJUnit.assertEquals("Big Wedding Lesson 01", lTextViewLesson.get(0).getText());
        AssertJUnit.assertEquals("A wedding is a ceremony where two people are united in marriage. Wedding traditions and customs vary greatly between cultures, ethnic groups, religions, countries, and social classes. Most wedding "
                + "ceremonies involve an exchange of marriage vows by the couple, presentation of a gift (offering, ring(s), symbolic item, "
                + "flowers, money), and a public proclamation of marriage by an authority figure. Special wedding garments are often worn, "
                + "and the ceremony is sometimes followed by a wedding reception. Music, poetry, prayers or readings from religious texts "
                + "or literature are also commonly incorporated into the ceremony.", lTextViewLesson.get(1).getText());

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

//        9.Tap Start Quiz button
        driver.findElement(By.xpath(".//android.widget.Button[@text='Làm đánh giá']")).click();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

//        Get list of text view in question screen
        List<WebElement> lTextViewQuestions = driver.findElements(By.className("android.widget.TextView"));

//        Title of screen should be Big Wedding Lesson 01
        AssertJUnit.assertEquals("Big Wedding Lesson 01", lTextViewQuestions.get(0).getText());

//        3 questions of Big Wedding Lesson 01 should appear:
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

//        10.Tap Finish button
        btn_Finish.click();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

//        Message "Please fill all required fields." should appear
        AssertJUnit.assertEquals("Vui lòng điền tất cả các trường bắt buộc.", driver.findElement(By.id("android:id/message")).getText());

//        Close message
        driver.findElement(By.xpath(".//android.widget.Button[@text='Đồng ý']")).click();

/*        11.Fill all required fields:
        - Field 1: testing
        - Field 2: Option 01
        - Field 3: Option 02*/
        driver.findElement(By.xpath(".//android.widget.CheckBox[@text='Option 02']")).click();

//        12.Tap Finish button
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

//        13.Tap Back button
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

//        Organize Big Wedding Course screen should appear within 2 lessons "Big Wedding Lesson 01" and "Big Wedding Lesson 02"
        AssertJUnit.assertEquals("Organize Big Wedding Course", lTextViewCourse.get(0).getText());
        AssertJUnit.assertEquals("Big Wedding Lesson 01", lTextViewCourse.get(1).getText());
        AssertJUnit.assertEquals("Big Wedding Lesson 02", lTextViewCourse.get(5).getText());

//        14.Tap Back button
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

//        Summary screen of Get Married option should appear
        AssertJUnit.assertTrue(lTextViewSummary.get(4).getText().contains("Organize Small Wedding"));
        AssertJUnit.assertTrue(lTextViewSummary.get(6).getText().contains("Organize Big Wedding"));

//        15.Tap Back button
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
