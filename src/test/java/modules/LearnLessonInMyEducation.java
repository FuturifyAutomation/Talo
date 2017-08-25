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

public class LearnLessonInMyEducation {

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
    }

    @Test
    public void VerifyMyLessonsTab(){

//        1.Tap My Educations module
        driver.findElement(By.xpath(".//android.widget.TextView[@text='Học vấn']")).click();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

//        Get list of text view in My Education screen
        List<WebElement> lTextViewEducation = driver.findElements(By.className("android.widget.TextView"));

//        My Educations screen should appear within 2 tabs "My Courses" and "All Courses" at the bottom
        AssertJUnit.assertEquals("Học vấn",lTextViewEducation.get(0).getText());
        AssertJUnit.assertTrue(driver.findElement(By.xpath(".//android.widget.Button[@text='Tất cả']")).isEnabled());

//        My Courses tab should be opened by default
        AssertJUnit.assertFalse(driver.findElement(By.xpath(".//android.widget.Button[@text='Của tôi']")).isEnabled());

//        My Course tab should not contain any courses
        AssertJUnit.assertEquals(1,lTextViewEducation.size());
    }

    @Test(priority = 1)
    public void VerifyCourseListInAllLessonsTab(){

//        2.Tap All Courses tab
        WebElement btn_AllLessons = driver.findElement(By.xpath(".//android.widget.Button[@text='Tất cả']"));
        btn_AllLessons.click();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

//        Get list of text view in Education screen
        List<WebElement> lTextViewEducation = driver.findElements(By.className("android.widget.TextView"));

//        All Courses tab should be opened
        AssertJUnit.assertEquals("Học vấn", lTextViewEducation.get(0).getText());
        AssertJUnit.assertFalse(btn_AllLessons.isEnabled());
        AssertJUnit.assertTrue(driver.findElement(By.xpath(".//android.widget.Button[@text='Của tôi']")).isEnabled());

        /*All courses should be displayed:
        - Retirement Course
        - Organize Small Wedding Course
        - Organize Big Wedding Course
        - Buy Cheap House Course
        - Buy Expensive House Course*/
        AssertJUnit.assertEquals("Retirement Course", lTextViewEducation.get(1).getText());
        AssertJUnit.assertTrue(lTextViewEducation.get(3).getText().contains("Organize Small Wedding"));
        AssertJUnit.assertEquals("Organize Big Wedding Course", lTextViewEducation.get(5).getText());
        AssertJUnit.assertEquals("Buy Cheap House Course", lTextViewEducation.get(7).getText());
        AssertJUnit.assertEquals("Buy Expensive House Course", lTextViewEducation.get(9).getText());
    }

    @Test(priority = 2)
    public void VerifyCourseContentInAllLessonsTab(){

//        3.Tap Organize Small Wedding Course
        driver.findElement(By.xpath(".//android.widget.TextView[contains(@text,'Organize Small Wedding')]")).click();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

//        Get list of text view in Course screen
        List<WebElement> lTextViewCourse = driver.findElements(By.className("android.widget.TextView"));

//        Organize Small Wedding Course screen should appear within 2 lessons "Small Wedding Lesson 01" and "Small Wedding Lesson 02"
        AssertJUnit.assertEquals("Organize Small Wedding Course", lTextViewCourse.get(0).getText());
        AssertJUnit.assertEquals("Small Wedding Lesson 01", lTextViewCourse.get(1).getText());
        AssertJUnit.assertEquals("Small Wedding Lesson 02", lTextViewCourse.get(5).getText());
    }

    @Test(priority = 3)
    public void VerifyLessonContentInAllLessonsTab(){

//        4.Tap on Small Wedding Lesson 02
        driver.findElement(By.xpath(".//android.widget.TextView[@text='Small Wedding Lesson 02']")).click();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

//        Get list of text view in Lesson screen
        List<WebElement> lTextViewLesson = driver.findElements(By.className("android.widget.TextView"));

//        Description of Small Wedding Lesson 02 should appear
        AssertJUnit.assertEquals("Small Wedding Lesson 02", lTextViewLesson.get(0).getText());
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

//        5.Tap Finish button
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

//        6.Tap Back button
        WebElement btn_BackNarrow = driver.findElement(By.className("android.widget.ImageButton"));
        btn_BackNarrow.click();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

//        Get list of text view in Summary screen
        List<WebElement> lTextViewCourse = driver.findElements(By.className("android.widget.TextView"));

//        Organize Small Wedding Course screen should appear within 2 lessons "Small Wedding Lesson 01" and "Small Wedding Lesson 02"
        AssertJUnit.assertEquals("Organize Small Wedding Course", lTextViewCourse.get(0).getText());
        AssertJUnit.assertEquals("Small Wedding Lesson 01", lTextViewCourse.get(1).getText());
        AssertJUnit.assertEquals("Small Wedding Lesson 02", lTextViewCourse.get(5).getText());

//        7.Tap Back button
        btn_BackNarrow.click();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

//        Get list of text view in Education screen
        List<WebElement> lTextViewEducation = driver.findElements(By.className("android.widget.TextView"));

//        My Educations screen should appear within tab "All Courses" opened
        AssertJUnit.assertEquals("Học vấn", lTextViewEducation.get(0).getText());
        AssertJUnit.assertFalse(driver.findElement(By.xpath(".//android.widget.Button[@text='Tất cả']")).isEnabled());
        AssertJUnit.assertTrue(driver.findElement(By.xpath(".//android.widget.Button[@text='Của tôi']")).isEnabled());
    }

    @Test(priority = 4)
    public void VerifyBuyHouseCoursesInMyLessonsTab() throws InterruptedException {

//        8.Go to My Goals screen
        WebElement btn_BackNarrow = driver.findElement(By.className("android.widget.ImageButton"));
        btn_BackNarrow.click();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

//        Wait for loading
        List<WebElement> lTextView = driver.findElements(By.className("android.widget.TextView"));
        int ilTextViewCount = lTextView.size();
        int i = 0;
        while (ilTextViewCount != 9 & i <= 60){
            Thread.sleep(1000);
            lTextView = driver.findElements(By.className("android.widget.TextView"));
            ilTextViewCount = lTextView.size();
            i++;
        }

        driver.findElement(By.xpath(".//android.widget.TextView[@text='Mục tiêu cá nhân']")).click();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

        /*9.Plan to buy a new house:
        - Income: 10000000
        - Where: 1 year
        - Cost: 1000000000*/
        driver.findElement(By.xpath(".//android.widget.TextView[@text='BUY A NEW HOUSE']")).click();
        List<WebElement> lEditTextQuestions = driver.findElements(By.className("android.widget.EditText"));
        lEditTextQuestions.get(0).sendKeys("10000000");
        lEditTextQuestions.get(1).click();
        driver.findElement(By.xpath(".//android.widget.Button[@text='OK']")).click();
        lEditTextQuestions.get(2).sendKeys("1000000000");
        driver.hideKeyboard();
        driver.findElement(By.xpath(".//android.widget.Button[@text='Hoàn thành']")).click();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

//        Wait for loading
        List<WebElement> lTextViewSummary = driver.findElements(By.className("android.widget.TextView"));
        ilTextViewCount = lTextViewSummary.size();
        i = 0;
        Thread.sleep(2000);
        while (ilTextViewCount != 8 & i <= 60){
            Thread.sleep(1000);
            lTextViewSummary = driver.findElements(By.className("android.widget.TextView"));
            ilTextViewCount = lTextViewSummary.size();
            i++;
        }

//        Summary of Buy A New House should appear
        AssertJUnit.assertEquals("Bạn sẽ cần 8 năm 4 tháng để mua căn nhà mơ ước. Để thực hiện được ước mơ này sớm hơn, "
                + "bạn phải tiết kiệm nhiều hơn và có thể chọn các gói sản phẩm vay tiêu dùng bên dưới.", lTextViewSummary.get(4).getText());

//        10.Go back to My Education screen
        btn_BackNarrow.click();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        btn_BackNarrow.click();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

//        Wait for loading
        lTextView = driver.findElements(By.className("android.widget.TextView"));
        ilTextViewCount = lTextView.size();
        i = 0;
        while (ilTextViewCount != 9 & i <= 60){
            Thread.sleep(1000);
            lTextView = driver.findElements(By.className("android.widget.TextView"));
            ilTextViewCount = lTextView.size();
            i++;
        }

        driver.findElement(By.xpath(".//android.widget.TextView[@text='Học vấn']")).click();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

//        Get list of text view in My Education screen
        List<WebElement> lTextViewEducation = driver.findElements(By.className("android.widget.TextView"));

//        My Lesson tab should be opened
        AssertJUnit.assertFalse(driver.findElement(By.xpath(".//android.widget.Button[@text='Của tôi']")).isEnabled());

//        Buy Cheap House Course should appear in My Lesson tab
        AssertJUnit.assertEquals("Buy Cheap House Course", lTextViewEducation.get(1).getText());

//        Buy Expensive House Course should appear in My Lesson tab
        AssertJUnit.assertEquals("Buy Expensive House Course", lTextViewEducation.get(3).getText());

//        Only 2 courses of Buy New House Courses should appear
        AssertJUnit.assertEquals(5, lTextViewEducation.size());
    }

    @Test(priority = 5)
    public void VerifyGetMarriedCoursesInMyLessonsTab() throws InterruptedException {

//        Go to My Goals screen
        WebElement btn_BackNarrow = driver.findElement(By.className("android.widget.ImageButton"));
        btn_BackNarrow.click();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

//        Wait for loading
        List<WebElement> lTextView = driver.findElements(By.className("android.widget.TextView"));
        int ilTextViewCount = lTextView.size();
        int i = 0;
        while (ilTextViewCount != 9 & i <= 60){
            Thread.sleep(1000);
            lTextView = driver.findElements(By.className("android.widget.TextView"));
            ilTextViewCount = lTextView.size();
            i++;
        }

        driver.findElement(By.xpath(".//android.widget.TextView[@text='Mục tiêu cá nhân']")).click();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

/*        11.Plan to get married:
        - Where: 1 year
        - Cost: 100000000*/
        driver.findElement(By.xpath(".//android.widget.TextView[@text='GET MARRIED']")).click();
        List<WebElement> lEditTextQuestions = driver.findElements(By.className("android.widget.EditText"));
        lEditTextQuestions.get(1).click();
        driver.findElement(By.xpath(".//android.widget.Button[@text='OK']")).click();
        lEditTextQuestions.get(2).sendKeys("100000000");
        driver.hideKeyboard();
        driver.findElement(By.xpath(".//android.widget.Button[@text='Hoàn thành']")).click();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

//        Wait for loading
        List<WebElement> lTextViewSummary = driver.findElements(By.className("android.widget.TextView"));
        ilTextViewCount = lTextViewSummary.size();
        i = 0;
        Thread.sleep(2000);
        while (ilTextViewCount != 8 & i <= 60){
            Thread.sleep(1000);
            lTextViewSummary = driver.findElements(By.className("android.widget.TextView"));
            ilTextViewCount = lTextViewSummary.size();
            i++;
        }

//        Summary of Get Married should appear
        AssertJUnit.assertEquals("Bạn sẽ cần 10 tháng để tiết kiệm đủ tiền cho việc kết hôn. Để thực hiện được ước mơ này sớm hơn, "
                + "bạn phải tiết kiệm nhiều hơn và có thể chọn các gói sản phẩm vay tiêu dùng bên dưới.", lTextViewSummary.get(4).getText());

//        12.Go back to My Education screen
        btn_BackNarrow.click();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        btn_BackNarrow.click();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

//        Wait for loading
        lTextView = driver.findElements(By.className("android.widget.TextView"));
        ilTextViewCount = lTextView.size();
        i = 0;
        while (ilTextViewCount != 9 & i <= 60){
            Thread.sleep(1000);
            lTextView = driver.findElements(By.className("android.widget.TextView"));
            ilTextViewCount = lTextView.size();
            i++;
        }

        driver.findElement(By.xpath(".//android.widget.TextView[@text='Học vấn']")).click();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

//        Get list of text view in My Education screen
        List<WebElement> lTextViewEducation = driver.findElements(By.className("android.widget.TextView"));

//        Organize Small Wedding Course should appear in My Lesson tab
        AssertJUnit.assertTrue(lTextViewEducation.get(1).getText().contains("Organize Small Wedding"));

//        Organize Big Wedding Course should appear in My Lesson tab
        AssertJUnit.assertEquals("Organize Big Wedding Course", lTextViewEducation.get(3).getText());

//        Buy Cheap House Course should appear in My Lesson tab
        AssertJUnit.assertEquals("Buy Cheap House Course", lTextViewEducation.get(5).getText());

//        Buy Expensive House Course should appear in My Lesson tab
        AssertJUnit.assertEquals("Buy Expensive House Course", lTextViewEducation.get(7).getText());

//        Only 2 courses of Buy New House Courses and 2 course of Get Married Course should appear
        AssertJUnit.assertEquals(9, lTextViewEducation.size());
    }

    @Test(priority = 6)
    public void VerifyRetirementCourseInMyLessonsTab() throws InterruptedException {

//        Go to My Goals screen
        WebElement btn_BackNarrow = driver.findElement(By.className("android.widget.ImageButton"));
        btn_BackNarrow.click();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

//        Wait for loading
        List<WebElement> lTextView = driver.findElements(By.className("android.widget.TextView"));
        int ilTextViewCount = lTextView.size();
        int i = 0;
        while (ilTextViewCount != 9 & i <= 60){
            Thread.sleep(1000);
            lTextView = driver.findElements(By.className("android.widget.TextView"));
            ilTextViewCount = lTextView.size();
            i++;
        }

        driver.findElement(By.xpath(".//android.widget.TextView[@text='Mục tiêu cá nhân']")).click();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

/*        13.Plan for retirement:
        - Where: 1 year
        - Cost: 1000000000*/
        driver.findElement(By.xpath(".//android.widget.TextView[@text='PLAN FOR RETIREMENT']")).click();
        List<WebElement> lEditTextQuestions = driver.findElements(By.className("android.widget.EditText"));
        lEditTextQuestions.get(1).click();
        driver.findElement(By.xpath(".//android.widget.Button[@text='OK']")).click();
        lEditTextQuestions.get(2).sendKeys("1000000000");
        driver.hideKeyboard();
        driver.findElement(By.xpath(".//android.widget.Button[@text='Hoàn thành']")).click();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

//        Wait for loading
        List<WebElement> lTextViewSummary = driver.findElements(By.className("android.widget.TextView"));
        ilTextViewCount = lTextViewSummary.size();
        i = 0;
        Thread.sleep(2000);
        while (ilTextViewCount != 8 & i <= 60){
            Thread.sleep(1000);
            lTextViewSummary = driver.findElements(By.className("android.widget.TextView"));
            ilTextViewCount = lTextViewSummary.size();
            i++;
        }

//        Summary of Plan For Retirement should appear
        AssertJUnit.assertEquals("Bạn sẽ cần 8 năm 4 tháng để cho kế hoạch về hưu của mình. Để thực hiện được ước mơ này sớm hơn, "
                + "bạn phải tiết kiệm nhiều hơn và có thể chọn các gói sản phẩm vay tiêu dùng bên dưới.", lTextViewSummary.get(4).getText());
//        14.Go back to My Education screen
        btn_BackNarrow.click();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        btn_BackNarrow.click();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

//        Wait for loading
        lTextView = driver.findElements(By.className("android.widget.TextView"));
        ilTextViewCount = lTextView.size();
        i = 0;
        while (ilTextViewCount != 9 & i <= 60){
            Thread.sleep(1000);
            lTextView = driver.findElements(By.className("android.widget.TextView"));
            ilTextViewCount = lTextView.size();
            i++;
        }

        driver.findElement(By.xpath(".//android.widget.TextView[@text='Học vấn']")).click();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

//        Get list of text view in My Education screen
        List<WebElement> lTextViewEducation = driver.findElements(By.className("android.widget.TextView"));

//        Retirement Course should appear in My Lesson tab
        AssertJUnit.assertEquals("Retirement Course", lTextViewEducation.get(1).getText());

//        Organize Small Wedding Course should appear in My Lesson tab
        AssertJUnit.assertTrue(lTextViewEducation.get(3).getText().contains("Organize Small Wedding"));

//        Organize Big Wedding Course should appear in My Lesson tab
        AssertJUnit.assertEquals("Organize Big Wedding Course", lTextViewEducation.get(5).getText());

//        Buy Cheap House Course should appear in My Lesson tab
        AssertJUnit.assertEquals("Buy Cheap House Course", lTextViewEducation.get(7).getText());

//        Buy Expensive House Course should appear in My Lesson tab
        AssertJUnit.assertEquals("Buy Expensive House Course", lTextViewEducation.get(9).getText());

//        Only 2 courses of Buy New House Courses and 2 course of Get Married Course and 1 course of Plan For Retirement Course should appear
        AssertJUnit.assertEquals(11, lTextViewEducation.size());
    }

    @Test(priority = 7)
    public void VerifyCourseContentInMyLessonsTab(){

//        15.Tap on Buy Cheap House Course
        driver.findElement(By.xpath(".//android.widget.TextView[@text='Buy Cheap House Course']")).click();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

//        Get list of text view in course screen
        List<WebElement> lTextViewCourse = driver.findElements(By.className("android.widget.TextView"));

//        Buy Cheap House Course screen should appear within 2 lessons "Cheap House Lesson 01" and "Cheap House Lesson 02"
        AssertJUnit.assertEquals("Buy Cheap House Course", lTextViewCourse.get(0).getText());
        AssertJUnit.assertEquals("Cheap House Lesson 01", lTextViewCourse.get(1).getText());
        AssertJUnit.assertEquals("Cheap House Lesson 02", lTextViewCourse.get(5).getText());
    }

    @Test(priority = 8)
    public void VerifyLessonContentInMyLessonsTab() throws InterruptedException {

//        16.Tap on Cheap House Lesson 02
        driver.findElement(By.xpath(".//android.widget.TextView[@text='Cheap House Lesson 02']")).click();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

//        Get list of text view in Lesson screen
        List<WebElement> lTextViewLesson = driver.findElements(By.className("android.widget.TextView"));

//        Description of Cheap House Lesson 02 should appear
        AssertJUnit.assertEquals("Cheap House Lesson 02", lTextViewLesson.get(0).getText());
        AssertJUnit.assertEquals("A house is a building that functions as a home, ranging from simple dwellings such as rudimentary huts of nomadic tribes and the improvised shacks in shantytowns "
                + "to complex, fixed structures of wood, brick, concrete or other materials containing plumbing, ventilation and electrical systems. Houses use a range of different roofing systems "
                + "to keep precipitation such as rain from getting into the dwelling space. Houses may have doors or locks to secure the dwelling space and protect "
                + "its inhabitants and contents from burglars or other trespassers.", lTextViewLesson.get(1).getText());

//        Scrolls screen to see Finish button
        driver.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView("
                + "new UiSelector().text(\"Hoàn thành\"));");

//        Start Quiz button should not appear at the bottom: Only one button (Finish) appears in Lesson screen
        List<WebElement> lButtons = driver.findElements(By.className("android.widget.Button"));
        AssertJUnit.assertEquals(1, lButtons.size());

//        Finish button should appear
        WebElement btn_Finish = driver.findElement(By.xpath(".//android.widget.Button[@text='Hoàn thành']"));
        AssertJUnit.assertTrue(btn_Finish.isEnabled());

//        17.Tap Finish button
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

//        18.Tap Back button
        WebElement btn_BackNarrow = driver.findElement(By.className("android.widget.ImageButton"));
        btn_BackNarrow.click();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

//        Get list of text view in Summary screen
        List<WebElement> lTextViewCourse = driver.findElements(By.className("android.widget.TextView"));

//        Buy Cheap House Course screen should appear within 2 lessons "Cheap House Lesson 01" and "Cheap House Lesson 02"
        AssertJUnit.assertEquals("Buy Cheap House Course", lTextViewCourse.get(0).getText());
        AssertJUnit.assertEquals("Cheap House Lesson 01", lTextViewCourse.get(1).getText());
        AssertJUnit.assertEquals("Cheap House Lesson 02", lTextViewCourse.get(5).getText());

//        19.Tap Back button
        btn_BackNarrow.click();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

//        Get list of text view in Education screen
        List<WebElement> lTextViewEducation = driver.findElements(By.className("android.widget.TextView"));

//        My Educations screen should appear within tab "My Courses" opened
        AssertJUnit.assertEquals("Học vấn", lTextViewEducation.get(0).getText());
        AssertJUnit.assertTrue(driver.findElement(By.xpath(".//android.widget.Button[@text='Tất cả']")).isEnabled());
        AssertJUnit.assertFalse(driver.findElement(By.xpath(".//android.widget.Button[@text='Của tôi']")).isEnabled());

//        20.Tap Back button
        btn_BackNarrow.click();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

//        Get text view list in Home screen
        List<WebElement> lTextViewHome = driver.findElements(By.className("android.widget.TextView"));
        int ilTextViewCount = lTextViewHome.size();
        int i = 0;
        while (ilTextViewCount != 9 & i <= 60){
            Thread.sleep(1000);
            lTextViewHome = driver.findElements(By.className("android.widget.TextView"));
            ilTextViewCount = lTextViewHome.size();
            i++;
        }

//        Dashboard screen should appear
        AssertJUnit.assertEquals("Mục tiêu cá nhân", lTextViewHome.get(3).getText());
        AssertJUnit.assertEquals("Tài chính", lTextViewHome.get(4).getText());
        AssertJUnit.assertEquals("Chăm sóc sức khỏe", lTextViewHome.get(5).getText());
        AssertJUnit.assertEquals("Phong cách sống", lTextViewHome.get(6).getText());
        AssertJUnit.assertEquals("Học vấn", lTextViewHome.get(7).getText());
        AssertJUnit.assertEquals("Thông tin cá nhân", lTextViewHome.get(8).getText());
    }

        @AfterTest
    public void tearDown(){
        driver.closeApp();
        driver.quit();
    }
}
