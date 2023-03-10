package com.solvd.mobiletesting.ios.element;

import com.qaprosoft.carina.core.foundation.webdriver.decorator.ExtendedWebElement;
import com.solvd.mobiletesting.base.element.LocationPopUpBase;
import com.solvd.mobiletesting.base.page.SelectLocationPageBase;
import com.zebrunner.carina.utils.factory.DeviceType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

@DeviceType(pageType = DeviceType.Type.IOS_PHONE, parentClass = LocationPopUpBase.class)
public class LocationPopUp extends LocationPopUpBase {

    @FindBy(xpath = "//*[contains(@class,'glow-toaster-theme-default')]//*[contains(@class,'glow-toaster-button-submit')]")
    private ExtendedWebElement changeAddressButton;

    @FindBy(xpath = "//*[contains(@class,'glow-toaster-theme-default')]//*[contains(@class,'glow-toaster-button-dismiss')]")
    private ExtendedWebElement notChangeButton;

    public LocationPopUp(WebDriver driver) {
        super(driver);
    }

    @Override
    public SelectLocationPageBase clickChangeAddressButton() {
        tap(changeAddressButton);
        return initPage(getDriver(), SelectLocationPageBase.class);
    }

    @Override
    public void clickNotChangeButton() {
        tap(notChangeButton);
    }
}
