package objects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ProfileDetailsLocators {

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

    @FindBy(xpath = ".//android.widget.TextView[@text='Thông tin cá nhân']")
    public WebElement lbl_ProfileTitle;

    @FindBy(className = "android.widget.ImageButton")
    public WebElement btn_Back;

    @FindBy(xpath = ".//android.support.v7.widget.LinearLayoutCompat/android.widget.TextView[1]")
    public WebElement btn_Edit;

    @FindBy(className = "android.widget.ImageView")
    public WebElement img_Avatar;

    @FindBy(xpath = ".//android.widget.TextView[@text='Họ và tên']")
    public WebElement lbl_FullNameLabel;

    @FindBy(xpath = ".//android.widget.TextView[@text='Ngày sinh']")
    public WebElement lbl_BirthDayLabel;

    @FindBy(xpath = ".//android.widget.TextView[@text='Giới tính']")
    public WebElement lbl_GenderLabel;

    @FindBy(xpath = ".//android.widget.TextView[@text='Điện thoại']")
    public WebElement lbl_PhoneNumberLabel;

    @FindBy(xpath = ".//android.widget.TextView[@text='Công ty']")
    public WebElement lbl_CompanyLabel;

    @FindBy(xpath = ".//android.view.View[3]/android.widget.TextView[1]")
    public WebElement lbl_FullNameView;

    @FindBy(xpath = ".//android.view.View[6]/android.widget.TextView[1]")
    public WebElement lbl_BirthDayView;

    @FindBy(xpath = ".//android.view.View[9]/android.widget.TextView[1]")
    public WebElement lbl_GenderView;

    @FindBy(xpath = ".//android.view.View[12]/android.widget.TextView[1]")
    public WebElement lbl_PhoneNumberView;

    @FindBy(xpath = ".//android.view.View[14]/android.widget.TextView[1]")
    public WebElement lbl_CompanyView;

    @FindBy(xpath = ".//android.widget.Button[@text='Đăng xuất']")
    public WebElement btn_LogOut;

    @FindBy(id = "android:id/message")
    public WebElement lbl_Message;

    @FindBy(id = "com.experian.prosper:id/alertTitle")
    public WebElement lbl_LogOutConfirmation;

    @FindBy(xpath = ".//android.widget.Button[@text='ĐĂNG XUẤT']")
    public WebElement btn_LogOutConfirmation;

    @FindBy(xpath = ".//android.widget.Button[@text='HỦY']")
    public WebElement btn_Cancel;

    // This is a constructor, as every page need a base driver to find web elements
    public ProfileDetailsLocators(WebDriver driver){
        this.driver = driver;
    }
}