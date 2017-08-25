package modules;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import objects.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.PageFactory;
import org.testng.AssertJUnit;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import supports.BaseClass;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Nexus_ManageAccount extends BaseClass{

    Nexus_LoginLocators LoginScreen;
    Nexus_RegisterLocators RegisterScreen;
    Nexus_VerifyPinLocators VerifyPinScreen;
    Nexus_HomeLocators HomeScreen;
    Nexus_ProfileDetailsLocators ProfileDetailsScreen;
    Nexus_ProfileEditLocators ProfileEditScreen;

    @BeforeTest
    @Parameters({"sPlatformVersion", "sDeviceName", "sURL"})
    public void setUp(String sPlatformVersion, String sDeviceName, String sURL) throws MalformedURLException{

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

//      Instantiate Elements Locator class
        LoginScreen = PageFactory.initElements(driver, Nexus_LoginLocators.class);
        RegisterScreen = PageFactory.initElements(driver, Nexus_RegisterLocators.class);
        VerifyPinScreen = PageFactory.initElements(driver, Nexus_VerifyPinLocators.class);
        HomeScreen = PageFactory.initElements(driver, Nexus_HomeLocators.class);
        ProfileDetailsScreen = PageFactory.initElements(driver, Nexus_ProfileDetailsLocators.class);
        ProfileEditScreen = PageFactory.initElements(driver, Nexus_ProfileEditLocators.class);
    }

    @Test
    public void VerifyFieldsInRegisterScreen() {

//      1.Tap Register label
        LoginScreen.lbl_Register.click();

//        Get list of edit text in Register screen
        List<WebElement> lEditTextRegister = driver.findElements(By.className("android.widget.EditText"));

//        Register screen should appear within registration fields
        AssertJUnit.assertEquals(3, lEditTextRegister.size());
        AssertJUnit.assertTrue(RegisterScreen.btn_Register.isEnabled());

//        2.Tap Register button
        RegisterScreen.btn_Register.click();

//        Message "The format of phone number is not correct" should appear
        AssertJUnit.assertEquals("Định dạng số điện thoại không đúng.", RegisterScreen.lbl_Message.getText());

//        Close message
        RegisterScreen.btn_Ok.click();

////        3.Tap Sign In label
//        RegisterScreen.lbl_SignIn.click();
//
////        Login screen should appear
//        AssertJUnit.assertTrue(LoginScreen.btn_SignIn.isEnabled());
    }

    @Test(priority = 1)
    @Parameters({"sPhoneNumber", "sPassword"})
    public void VerifyValidationsInRegisterScreen(String sPhoneNumber, String sPassword) {

////        4.Tap Register label
//        LoginScreen.lbl_Register.click();

//        5.Input ""0123456789"" into phone number field
        RegisterScreen.txt_PhoneNumber.sendKeys(sPhoneNumber);

//        6.Tap Register button
        RegisterScreen.btn_Register.click();

//        Message "The password must contains at least 6 characters." should appear
        AssertJUnit.assertEquals("Mật khẩu phải chứa ít nhất 6 kí tự.", RegisterScreen.lbl_Message.getText());

//        Close message
        RegisterScreen.btn_Ok.click();

//        7.Input ""qqqqqq"" into Password field
        RegisterScreen.txt_Password.sendKeys(sPassword);

//        8.Tap Register button
        RegisterScreen.btn_Register.click();

//        Message "The password and confirm password do not match." should appear
        AssertJUnit.assertEquals("Mật khẩu và mật khẩu xác nhận không khớp.", RegisterScreen.lbl_Message.getText());

//        Close message
        RegisterScreen.btn_Ok.click();

//        9.Input ""qqqqqq"" into Confirm Password field
        RegisterScreen.txt_Confirm_Password.sendKeys(sPassword);

//        10.Tap Register button
        RegisterScreen.btn_Register.click();

//        Message "Account is registered successfully. Please check SMS message and enter confirmation code." should appear
        AssertJUnit.assertEquals("Tài khoản đã được tạo. Vui lòng kiểm tra tin nhắn SMS và nhập mã xác nhận.", RegisterScreen.lbl_Message.getText());

//        Close message
        RegisterScreen.btn_Ok.click();
    }

    @Test(priority = 2)
    @Parameters({"sPhoneNumber", "sPassword"})
    public void VerifyValidatingPinCode(String sPhoneNumber, String sPassword) {

//        PIN screen should appear within phone number text on top
        AssertJUnit.assertTrue(VerifyPinScreen.lbl_PhoneNumber.getText().contains(sPhoneNumber.substring(sPhoneNumber.length() - 1)));

//        PIN text field should be enabled
        AssertJUnit.assertTrue(VerifyPinScreen.txt_Pin.isEnabled());

//        Confirm button should appear
        AssertJUnit.assertTrue(VerifyPinScreen.btn_Confirm.isEnabled());

//        Re-send label should appear
        AssertJUnit.assertTrue(VerifyPinScreen.lbl_Resend.isEnabled());

//        11.Tap Confirm button
        VerifyPinScreen.btn_Confirm.click();

//        Message "Can't connect to server." should appear
        AssertJUnit.assertEquals("Không thể kết nối tới máy chủ.", VerifyPinScreen.lbl_Message.getText());

//        Close message
        VerifyPinScreen.btn_Ok.click();

//        12.Tap Sign In label
        VerifyPinScreen.lbl_SignIn.click();

//        Login screen should appear
        AssertJUnit.assertTrue(LoginScreen.btn_SignIn.isEnabled());

//        13.Login with just registered account
        LoginScreen.txt_PhoneNumber.sendKeys(sPhoneNumber);
        LoginScreen.txt_Password.sendKeys(sPassword);
        LoginScreen.btn_SignIn.click();

//        Message "You have not verified phone number yet. Please check SMS message and enter PIN code." should appear
        AssertJUnit.assertEquals("Bạn chưa xác nhận số điện thoại. Vui lòng kiểm tra tin nhắn SMS và nhập mã PIN.", LoginScreen.lbl_Message.getText());

//        14.Closing warning popup
        LoginScreen.btn_Ok.click();
    }

    @Test(priority = 3)
    @Parameters({"sPhoneNumber"})
    public void VerifyRegisteringAccountSuccessfully(String sPhoneNumber) throws InterruptedException {

//        PIN screen should appear
        AssertJUnit.assertTrue(VerifyPinScreen.lbl_PhoneNumber.getText().contains(sPhoneNumber.substring(sPhoneNumber.length() - 1)));
        AssertJUnit.assertTrue(VerifyPinScreen.btn_Confirm.isEnabled());

//        15.Input ""123456"" into Pin field
        VerifyPinScreen.txt_Pin.sendKeys("123456");

//        16.Tap Confirm button
        VerifyPinScreen.btn_Confirm.click();

//        Message "Congratulation! You have just registered account successfully."
        AssertJUnit.assertEquals("Chúc mừng bạn! Bạn vừa  đăng kí tài khoản thành công.", VerifyPinScreen.lbl_Message.getText());

//        17.Close message popup
        VerifyPinScreen.btn_Ok.click();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

//        Get list of text view in Home screen
        List<WebElement> lTextView = driver.findElements(By.className("android.widget.TextView"));

        int iTextViewCount = lTextView.size();
        int i = 0;
        while (iTextViewCount != 9 & i <= 60){
            Thread.sleep(1000);
            lTextView = driver.findElements(By.className("android.widget.TextView"));
            iTextViewCount = lTextView.size();
            i++;
        }

//        Home screen should appear within modules: My Goals, My Finance, My Health, My Life-Style, My Education, My Profile
        AssertJUnit.assertTrue(HomeScreen.lbl_AppTitle.isDisplayed());
        AssertJUnit.assertEquals(" ", HomeScreen.lbl_Username.getText());
        AssertJUnit.assertEquals("0 TL", HomeScreen.lbl_TLPoint.getText());
        AssertJUnit.assertTrue(HomeScreen.lbl_MyGoals.isEnabled());
        AssertJUnit.assertTrue(HomeScreen.lbl_MyHealth.isEnabled());
        AssertJUnit.assertTrue(HomeScreen.lbl_MyEducations.isEnabled());
        AssertJUnit.assertTrue(HomeScreen.lbl_MyFinace.isEnabled());
        AssertJUnit.assertTrue(HomeScreen.lbl_MyLifeStyle.isEnabled());
        AssertJUnit.assertTrue(HomeScreen.lbl_MyProfile.isEnabled());
    }

    @Test(priority = 4)
    public void VerifyInformationInMyProfileScreen() {

//        Open My Profile screen
        HomeScreen.lbl_MyProfile.click();

//        After tapping "My Profile" module, Profile screen should appear within avatar, full name, birthday, gender, phone and company information
        AssertJUnit.assertTrue(ProfileDetailsScreen.lbl_ProfileTitle.isDisplayed());
        AssertJUnit.assertTrue(ProfileDetailsScreen.img_Avatar.isDisplayed());
        AssertJUnit.assertTrue(ProfileDetailsScreen.lbl_FullNameLabel.isDisplayed());
        AssertJUnit.assertTrue(ProfileDetailsScreen.lbl_BirthDayLabel.isDisplayed());
        AssertJUnit.assertTrue(ProfileDetailsScreen.lbl_GenderLabel.isDisplayed());
        AssertJUnit.assertTrue(ProfileDetailsScreen.lbl_PhoneNumberLabel.isDisplayed());
        AssertJUnit.assertTrue(ProfileDetailsScreen.lbl_CompanyLabel.isDisplayed());
    }

    @Test(priority = 5)
    @Parameters({"sFirstName", "sLastName"})
    public void VerifyValidatingFieldsInMyProfileScreen(String sFirstName, String sLastName) {

//        Tap Edit button
        ProfileDetailsScreen.btn_Edit.click();

//        Tap Save button without typing name
        ProfileEditScreen.btn_Save.click();

//        "First Name" and "Last Name" field should be required
        AssertJUnit.assertEquals("Tên và họ bắt buộc phải nhập", ProfileEditScreen.lbl_Message.getText());

//        Close message
        ProfileEditScreen.btn_Ok.click();

//        Type last name
        ProfileEditScreen.txt_LastName.sendKeys(sLastName);

//        Hide keyboard
        driver.hideKeyboard();

//        Tap Save button
        ProfileEditScreen.btn_Save.click();

//        "First Name" field should be required
        AssertJUnit.assertEquals("Tên bắt buộc phải nhập", ProfileEditScreen.lbl_Message.getText());

//        Close message
        ProfileEditScreen.btn_Ok.click();

//        Clear "Last Name" field and type first name
        ProfileEditScreen.txt_LastName.clear();
        ProfileEditScreen.txt_FirstName.sendKeys(sFirstName);

//        Hide keyboard
        driver.hideKeyboard();

//        Tap Save button
        ProfileEditScreen.btn_Save.click();

//        "Last Name" field should be required
        AssertJUnit.assertEquals("Họ bắt buộc phải nhập", ProfileEditScreen.lbl_Message.getText());

//        Close message
        ProfileEditScreen.btn_Ok.click();

//        Type last name
        ProfileEditScreen.txt_LastName.sendKeys(sLastName);

//        Hide keyboard
        driver.hideKeyboard();

//        User cannot change the phone number
        AssertJUnit.assertFalse(ProfileEditScreen.txt_PhoneNumber.isEnabled());

//        Tap Save button
        ProfileEditScreen.btn_Save.click();

//        After editing profile within valid information, profile should be saved
        AssertJUnit.assertEquals(sFirstName + " " + sLastName, ProfileDetailsScreen.lbl_FullNameView.getText());
    }

    @Test(priority = 6)
    @Parameters({"sFirstName", "sLastName"})
    public void VerifyCancelingInformationEditionInMyProfileScreen(String sFirstName, String sLastName){

//        Tap Edit button
        ProfileDetailsScreen.btn_Edit.click();

//        Type name
        ProfileEditScreen.txt_FirstName.sendKeys(sLastName);
        ProfileEditScreen.txt_LastName.sendKeys(sFirstName);

//        Tap Back icon
        ProfileEditScreen.btn_Back.click();

//        After canceling to edit profile, information should not be changed
        AssertJUnit.assertEquals(sFirstName + " " + sLastName, ProfileDetailsScreen.lbl_FullNameView.getText());
    }

    @Test(priority = 7)
    public void VerifyLoggingOut(){

//        Tap Logout button on My Profile screen
        ProfileDetailsScreen.btn_LogOut.click();

//        Confirm logout should appear after tapping "Logout" button
        AssertJUnit.assertEquals("Xác nhận đăng xuất", ProfileDetailsScreen.lbl_LogOutConfirmation.getText());

//        Tap Logout button on Confirmation popup
        ProfileDetailsScreen.btn_LogOutConfirmation.click();

//        LoginAccount screen should appear after tapping "Logout" button
        AssertJUnit.assertEquals("Đăng nhập", LoginScreen.btn_SignIn.getText());
    }

    @Test(priority = 8)
    @Parameters({"sPhoneNumber", "sPassword"})
    public void VerifyRegisteringExistingAccount(String sPhoneNumber, String sPassword){

//      Tap Register label
        LoginScreen.lbl_Register.click();

//        Input registered account
        RegisterScreen.txt_PhoneNumber.sendKeys(sPhoneNumber);
        RegisterScreen.txt_Password.sendKeys(sPassword);
        RegisterScreen.txt_Confirm_Password.sendKeys(sPassword);

//        Tap Register button
        RegisterScreen.btn_Register.click();

//        Message "The phone number has already existed." should appear
        AssertJUnit.assertEquals("Số điện thoại đã được sử dụng.", RegisterScreen.lbl_Message.getText());

//        Close message
        RegisterScreen.btn_Ok.click();

//        Tap Sign In label
        RegisterScreen.lbl_SignIn.click();
    }

    @Test(priority = 9)
    @Parameters({"sPhoneNumber"})
    public void VerifyLoggingInWithInvalidAccount(String sPhoneNumber) throws InterruptedException {

//        Type phone number
        LoginScreen.txt_PhoneNumber.sendKeys(sPhoneNumber);

//        Type password
        LoginScreen.txt_Password.sendKeys("111111");

//        Tap Sign In button
        LoginScreen.btn_SignIn.click();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

//        When logging in within incorrect phone number or incorrect password, error "Your phone number or password is not correct. Please try again." should appear
        AssertJUnit.assertEquals("Số điện thoại hoặc mật khẩu đã nhập không đúng. Vui lòng thử lại.", LoginScreen.lbl_Message.getText());

//        Close popup
        LoginScreen.btn_Ok.click();
    }

    @Test(priority = 10)
    @Parameters({"sPassword", "sFirstName", "sLastName"})
    public void VerifyLoggingInWithValidAccount(String sPassword, String sFirstName, String sLastName) throws InterruptedException {

//        Type password
        LoginScreen.txt_Password.clear();
        LoginScreen.txt_Password.sendKeys(sPassword);

//        Tap Sign In button
        LoginScreen.btn_SignIn.click();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

//        Get text view list in Home screen
        List<WebElement> lTextView = driver.findElements(By.className("android.widget.TextView"));

        int iTextViewCount = lTextView.size();
        int i = 0;
        while (iTextViewCount != 9 & i <= 60){
            Thread.sleep(1000);
            lTextView = driver.findElements(By.className("android.widget.TextView"));
            iTextViewCount = lTextView.size();
            i++;
        }

//        Home screen should contain 6 components: "My Goals", "My Finance", "My Health", "My Life style", "My Education" and "My Profile".
//        Username should be updated
        AssertJUnit.assertTrue(HomeScreen.lbl_AppTitle.isDisplayed());
        AssertJUnit.assertEquals(sFirstName + " " + sLastName, HomeScreen.lbl_Username.getText());
        AssertJUnit.assertEquals("0 TL", HomeScreen.lbl_TLPoint.getText());
        AssertJUnit.assertTrue(HomeScreen.lbl_MyGoals.isEnabled());
        AssertJUnit.assertTrue(HomeScreen.lbl_MyHealth.isEnabled());
        AssertJUnit.assertTrue(HomeScreen.lbl_MyEducations.isEnabled());
        AssertJUnit.assertTrue(HomeScreen.lbl_MyFinace.isEnabled());
        AssertJUnit.assertTrue(HomeScreen.lbl_MyLifeStyle.isEnabled());
        AssertJUnit.assertTrue(HomeScreen.lbl_MyProfile.isEnabled());
    }

    @AfterTest
    public void tearDown(){
        driver.closeApp();
        driver.quit();
    }
}




