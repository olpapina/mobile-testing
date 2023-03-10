package com.solvd.mobiletesting.android.page;

import com.qaprosoft.carina.core.foundation.webdriver.decorator.ExtendedWebElement;
import com.solvd.mobiletesting.base.page.ResultPageBase;
import com.solvd.mobiletesting.base.page.ResultSearchPageBase;
import com.zebrunner.carina.utils.factory.DeviceType;
import com.zebrunner.carina.utils.mobile.IMobileUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

@DeviceType(pageType = DeviceType.Type.ANDROID_PHONE, parentClass = ResultSearchPageBase.class)
public class ResultSearchPage extends ResultSearchPageBase implements IMobileUtils {

    @FindBy(xpath = "//*[@id='question-answer-1-expander-button-Physical Gift Card']")
    private ExtendedWebElement mailFilterButton;

    @FindBy(xpath = "//*[contains(@class,'sf-filter-section')]//*[contains(text(),'eGift Card')]")
    private ExtendedWebElement eGiftCardFilterButton;

    @FindBy(xpath = "//*[contains(@class,'sf-filter-section')]//*[contains(text(),'Amazon')]")
    private ExtendedWebElement amazonButton;

    @FindBy(xpath = "//*[contains(@class,'sf-rib-all-filters')]//*[contains(text(),'Filters')]")
    private ExtendedWebElement filterButton;


    @FindBy(xpath = "//*[@id='bxc-grid__button bxc-grid__button--r-c2']")
    private ExtendedWebElement mailButton;

    @FindBy(xpath = "//*[@id='bxc-grid__button bxc-grid__button--r-c1']")
    private ExtendedWebElement eGiftCardButton;

    @FindBy(xpath = "//*[@id='bxc-grid__button bxc-grid__button--r-c3']")
    private ExtendedWebElement printAtHomeButton;

    public ResultSearchPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public ResultPageBase clickFilterOfCard(String type) {
        switch (type) {
            case "Mail":
                mailFilterButton.scrollTo();
                mailFilterButton.click();
                break;
            case "eGIFT":
                eGiftCardFilterButton.scrollTo();
                eGiftCardFilterButton.click();
                break;
            default:
                amazonButton.click();
                break;
        }
        return initPage(getDriver(), ResultPageBase.class);
    }

    @Override
    public ResultPageBase clickTypeOfCard(String type) {
        switch (type) {
            case "MAIL":
                mailButton.click();
                break;
            case "EGIFT":
                eGiftCardButton.click();
                break;
            default:
                eGiftCardButton.clickIfPresent();
                break;
        }
        return initPage(getDriver(), ResultPageBase.class);
    }
}
