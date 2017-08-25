package objects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class Nexus_RegisterLocators {

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

    @FindBy(xpath = ".//android.view.ViewGroup[3]/android.widget.EditText[1]")
    public WebElement txt_Confirm_Password;

    @FindBy(xpath = ".//android.widget.Button[@text='Đăng ký']")
    public WebElement btn_Register;

    @FindBy(className = "android.widget.TextView")
    public WebElement lbl_SignIn;

    @FindBy(id = "android:id/message")
    public WebElement lbl_Message;

    @FindBy(id = "android:id/button2")
    public WebElement btn_Ok;

    // This is a constructor, as every page need a base driver to find web elements
    public Nexus_RegisterLocators(WebDriver driver){
        this.driver = driver;
    }
}
