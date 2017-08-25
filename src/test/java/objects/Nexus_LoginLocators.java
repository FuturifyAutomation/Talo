package objects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class Nexus_LoginLocators {

    public WebDriver driver;

    /**
     * txt --> text box
     * rad --> radio button
     * btn --> button
     * lbl --> label
     * lnk --> link
     * lst --> list
     * chk --> checkbox
     */

    @FindBy(xpath = ".//android.widget.EditText[@text='Số điện thoại']")
    public WebElement txt_PhoneNumber;

    @FindBy(xpath = ".//android.view.ViewGroup[2]/android.widget.EditText[1]")
    public WebElement txt_Password;

    @FindBy(xpath = ".//android.widget.Button[@text='Đăng nhập']")
    public WebElement btn_SignIn;

    @FindBy(xpath = ".//android.widget.TextView[@text='Đăng ký']")
    public WebElement lbl_Register;

    @FindBy(id = "android:id/message")
    public WebElement lbl_Message;

    @FindBy(id = "android:id/button2")
    public WebElement btn_Ok;

    // This is a constructor, as every page need a base driver to find web elements
    public Nexus_LoginLocators(WebDriver driver){
        this.driver = driver;
    }

    public void logIn(String sPhoneNumber, String sPassword) throws InterruptedException {

//        Type phone number
        txt_PhoneNumber.sendKeys(sPhoneNumber);

//        Type password
        txt_Password.sendKeys(sPassword);

//        Tap sign in
        btn_SignIn.click();

//        Wait for loading
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        waitForLoading();
    }

    public  void waitForLoading() throws InterruptedException {

//        Get size of text view list
        List<WebElement> lTextView = driver.findElements(By.className("android.widget.TextView"));
        int iTextViewCount = lTextView.size();
        int i = 0;

//        Wait for size of list
        while (iTextViewCount != 9 & i <= 60){
            Thread.sleep(1000);
            lTextView = driver.findElements(By.className("android.widget.TextView"));
            iTextViewCount = lTextView.size();
            i++;
        }
    }
}
