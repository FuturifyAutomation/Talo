package objects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Created by F03-QuangDo on 5/12/2017.
 */
public class Nexus_HomeLocators {

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

    @FindBy(xpath = ".//android.widget.TextView[@text='T   A   L   O']")
    public WebElement lbl_AppTitle;

    @FindBy(xpath = ".//android.view.ViewGroup[1]/android.view.ViewGroup[1]/android.view.ViewGroup[1]/android.view.ViewGroup[2]/android.widget.TextView[1]")
    public WebElement lbl_Username;

    @FindBy(xpath = ".//android.view.ViewGroup[1]/android.view.ViewGroup[1]/android.view.ViewGroup[1]/android.view.ViewGroup[3]/android.view.ViewGroup[2]/android.widget.TextView[1]")
    public WebElement lbl_TLPoint;

    @FindBy(xpath = ".//android.widget.TextView[@text='Mục tiêu cá nhân']")
    public WebElement lbl_MyGoals;

    @FindBy(xpath = ".//android.widget.TextView[@text='Chăm sóc sức khỏe']")
    public WebElement lbl_MyHealth;

    @FindBy(xpath = ".//android.widget.TextView[@text='Học vấn']")
    public WebElement lbl_MyEducations;

    @FindBy(xpath = ".//android.widget.TextView[@text='Tài chính']")
    public WebElement lbl_MyFinace;

    @FindBy(xpath = ".//android.widget.TextView[@text='Phong cách sống']")
    public WebElement lbl_MyLifeStyle;

    @FindBy(xpath = ".//android.widget.TextView[@text='Thông tin cá nhân']")
    public WebElement lbl_MyProfile;

    // This is a constructor, as every page need a base driver to find web elements
    public Nexus_HomeLocators(WebDriver driver){
        this.driver = driver;
    }
}
