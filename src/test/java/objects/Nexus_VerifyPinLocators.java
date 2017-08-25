package objects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class Nexus_VerifyPinLocators {

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

    @FindBy(xpath = ".//android.view.ViewGroup[1]/android.widget.TextView[1]")
    public WebElement lbl_PhoneNumber;

    @FindBy(xpath = ".//android.widget.EditText[@text='PIN']")
    public WebElement txt_Pin;

    @FindBy(xpath = ".//android.widget.Button[@text='Xác nhận']")
    public WebElement btn_Confirm;

    @FindBy(xpath = ".//android.widget.TextView[@text='Gửi lại']")
    public WebElement lbl_Resend;

    @FindBy(xpath = ".//android.widget.TextView[@text='Đăng nhập']")
    public WebElement lbl_SignIn;

    @FindBy(id = "android:id/message")
    public WebElement lbl_Message;

    @FindBy(id = "android:id/button2")
    public WebElement btn_Ok;

    // This is a constructor, as every page need a base driver to find web elements
    public Nexus_VerifyPinLocators(WebDriver driver){
        this.driver = driver;
    }
}
