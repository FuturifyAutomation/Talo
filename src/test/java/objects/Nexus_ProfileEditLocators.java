package objects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class Nexus_ProfileEditLocators {

    public WebDriver driver;

    /**
     * txt --> text box
     * rad --> radio button
     * btn --> button
     * lbl --> label
     * lnk --> link
     * lst --> list
     * chk --> checkbox
     * img --> image
     */

    @FindBy(xpath = ".//android.widget.TextView[@text='Chỉnh sửa thông tin cá nhân']")
    public WebElement lbl_ProfileTitle;

    @FindBy(className = "android.widget.ImageButton")
    public WebElement btn_Back;

    @FindBy(xpath = ".//android.view.ViewGroup[2]/android.widget.EditText[1]")
    public WebElement txt_FirstName;

    @FindBy(xpath = ".//android.view.ViewGroup[4]/android.widget.EditText[1]")
    public WebElement txt_LastName;

    @FindBy(xpath = ".//android.view.ViewGroup[6]/android.widget.EditText[1]")
    public WebElement txt_BirthDay;

    @FindBy(xpath = ".//android.view.ViewGroup[8]/android.widget.EditText[1]")
    public WebElement txt_Gender;

    @FindBy(xpath = ".//android.view.ViewGroup[10]/android.widget.EditText[1]")
    public WebElement txt_PhoneNumber;

    @FindBy(xpath = ".//android.view.ViewGroup[12]/android.widget.EditText[1]")
    public WebElement txt_Company;

    @FindBy(xpath = ".//android.widget.Button[@text='Lưu']")
    public WebElement btn_Save;

    @FindBy(id = "android:id/message")
    public WebElement lbl_Message;

    @FindBy(id = "android:id/button2")
    public WebElement btn_Ok;

    // This is a constructor, as every page need a base driver to find web elements
    public Nexus_ProfileEditLocators(WebDriver driver){
        this.driver = driver;
    }
}
