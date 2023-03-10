package com.solvd.mobiletesting;

import com.qaprosoft.carina.core.foundation.IAbstractTest;
import com.solvd.mobiletesting.base.AppBase;
import com.solvd.mobiletesting.base.element.LocationPopUpBase;
import com.solvd.mobiletesting.base.element.MenuBarBase;
import com.solvd.mobiletesting.base.element.TopDropMenuBase;
import com.solvd.mobiletesting.base.page.*;
import com.solvd.mobiletesting.base.utils.*;
import com.solvd.mobiletesting.ios.page.HomePage;
import com.zebrunner.carina.utils.R;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.List;

public class GiftCardDeliveryTest implements IAbstractTest {

    @Test(testName = "verify that delivery is impossible to Belarus")
    public void verifyDeliveryImpossibleLocationMessageTest() {
        HomePageBase homePage = initPage(getDriver(), HomePageBase.class);
        homePage.open();
        LocationPopUpBase locationPopUp = homePage.getLocationPopUp();
        if (locationPopUp != null) {
            locationPopUp.clickNotChangeButton();
        }
        MobileContextUtils contextHelper = new MobileContextUtils();
        contextHelper.switchMobileContext(MobileContextUtils.View.NATIVE);
        AppBase appBrowser = initPage(getDriver(), AppBase.class);
        appBrowser.clickSwitchTabButton();
        appBrowser.createNewTab();
        if (R.CONFIG.get("capabilities.platformName").equals("ANDROID")) {
            contextHelper.switchMobileContext(MobileContextUtils.View.WEB);
        } else {
            contextHelper.switchMobileContext(MobileContextUtils.View.WEB1);
        }
        String actualLocation = homePage.getActualDeliveryLocation();
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(actualLocation, R.TESTDATA.get("impossibleDelivery"), "The location is not " + R.TESTDATA.get("impossibleDelivery"));
        MenuBarBase menuBar = homePage.getMenuBar();
        GiftCardPageBase giftCardPage = menuBar.clickGiftCardTabButtonIfPresent();
        ResultSearchPageBase resultSearchPage = giftCardPage.clickOccasionButton();
        ResultPageBase resultPage = resultSearchPage.clickTypeOfCard("MAIL");
        List<String> messages = resultPage.getValidationMessages();

        messages.forEach(resultText -> softAssert.assertEquals(resultText, R.TESTDATA.get("validationMessage"), "Validation message is missing or text is incorrect"));
        softAssert.assertAll();
    }

    @Test(testName = "verify that click Add to cart is impossible from Belarus")
    public void verifyImpossibleClickAddToCartButton() {
        HomePageBase homePage = initPage(getDriver(), HomePageBase.class);
        homePage.open();
        LocationPopUpBase locationPopUp = homePage.getLocationPopUp();
        if (locationPopUp != null) {
            locationPopUp.clickNotChangeButton();
        }
        String actualLocation = homePage.getActualDeliveryLocation();
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(actualLocation, R.TESTDATA.get("impossibleDelivery"), "The location is not " + R.TESTDATA.get("impossibleDelivery"));
        MobileContextUtils contextHelper = new MobileContextUtils();
        contextHelper.switchMobileContext(MobileContextUtils.View.NATIVE);
        AppBase appBrowser = initPage(getDriver(), AppBase.class);
        appBrowser.createNewBookmarkButton("https://www.google.com/");
        appBrowser.clickSwitchTabButton();
        appBrowser.clickCloseTab();
        if (R.CONFIG.get("capabilities.platformName").equals("ANDROID")) {
            contextHelper.switchMobileContext(MobileContextUtils.View.WEB);
        } else {
            contextHelper.switchMobileContext(MobileContextUtils.View.WEB1);
        }
        MenuBarBase menuBar = homePage.getMenuBar();
        GiftCardPageBase giftCardPage = menuBar.clickGiftCardTabButtonIfPresent();
        ResultSearchPageBase resultSearchPage = giftCardPage.clickOccasionButton();
        ResultPageBase resultPage = resultSearchPage.clickTypeOfCard("MAIL");
        List<Boolean> statuses = resultPage.checkButtonStatus("inactive");

        statuses.forEach(resultText -> softAssert.assertTrue(resultText, "Button is active"));
        softAssert.assertAll();
    }

    @Test(testName = "verify that delivery is possible to US with zip code")
    public void verifyDeliveryPossibleLocationTest() {
        HomePageBase homePage = initPage(getDriver(), HomePageBase.class);
        homePage.open();
        LocationPopUpBase locationPopUp = homePage.getLocationPopUp();
        SelectLocationPageBase selectLocationPage;
        if (locationPopUp != null) {
            selectLocationPage = locationPopUp.clickChangeAddressButton();
        } else {
            selectLocationPage = homePage.clickActualDeliveryLocation();
        }
        EnterZipCodePageBase enterZipCodePage = selectLocationPage.clickEnterZipCodeButton();
        enterZipCodePage.enterZipCode();
        homePage = enterZipCodePage.clickApplyButton();
        String actualLocation = homePage.getActualDeliveryLocation();
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(actualLocation, R.TESTDATA.get("possibleDelivery") + " " + R.TESTDATA.get("zipCode"), "The location is not zip code: " + R.TESTDATA.get("zipCode"));
        TopDropMenuBase topDropMenu = homePage.clickTopMenu();
        GiftCardPageBase giftCardPage = topDropMenu.clickGiftCardButton();
        ResultSearchPageBase resultSearchPage = giftCardPage.clickOccasionButton();
        ResultPageBase resultPage = resultSearchPage.clickTypeOfCard("MAIL");
        List<String> statuses = resultPage.getDeliveryStatuses();
        List<String> messages = resultPage.getValidationMessages();

        messages.forEach(message -> softAssert.assertEquals(message, null, "Validation message is present for US"));
        statuses.forEach(status -> softAssert.assertTrue(status.contains(R.TESTDATA.get("zipCode")), "Product delivery is not " + R.TESTDATA.get("zipCode")));
        softAssert.assertAll();
    }

    @Test(testName = "verify eGiftCard type delivery is email")
    public void verifyDeliveryTypeEGiftCardTest() {
        HomePageBase homePage = initPage(getDriver(), HomePageBase.class);
        homePage.open();
        LocationPopUpBase locationPopUp = homePage.getLocationPopUp();
        if (locationPopUp != null) {
            locationPopUp.clickNotChangeButton();
        }
        String actualLocation = homePage.getActualDeliveryLocation();
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(actualLocation, R.TESTDATA.get("impossibleDelivery"), "The location is not " + R.TESTDATA.get("impossibleDelivery"));
        MenuBarBase menuBar = homePage.getMenuBar();
        GiftCardPageBase giftCardPage = menuBar.clickGiftCardTabButtonIfPresent();
        ResultSearchPageBase resultSearchPage = giftCardPage.clickOccasionButton();
        ResultPageBase resultPage = resultSearchPage.clickTypeOfCard("EGIFT");
        List<String> types = resultPage.getDeliveryTypes();

        types.forEach(typeText -> softAssert.assertEquals(typeText, "Email", "Delivery type is not email"));
        softAssert.assertAll();
    }

    @Test(testName = "verify delivery possible Add to card and Buy now from US location")
    public void verifyAddToCardBuyNowButtonArePresentTest() {
        HomePageBase homePage = new HomePage(getDriver());
        homePage.open();
        LocationPopUpBase locationPopUp = homePage.getLocationPopUp();
        SelectLocationPageBase selectLocationPage;
        if (locationPopUp != null) {
            selectLocationPage = locationPopUp.clickChangeAddressButton();
        } else {
            selectLocationPage = homePage.clickActualDeliveryLocation();
        }
        EnterZipCodePageBase enterZipCodePage = selectLocationPage.clickEnterZipCodeButton();
        enterZipCodePage.enterZipCode();
        enterZipCodePage.clickApplyButton();
        homePage = initPage(getDriver(), HomePageBase.class);
        String actualLocation = homePage.getActualDeliveryLocation();
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(actualLocation, R.TESTDATA.get("possibleDelivery") + " " + R.TESTDATA.get("zipCode"), "The location is not zip code: " + R.TESTDATA.get("zipCode"));
        TopDropMenuBase topDropMenu = homePage.clickTopMenu();
        GiftCardPageBase giftCardPage = topDropMenu.clickGiftCardButton();

        ResultSearchPageBase resultSearchPage = giftCardPage.clickOccasionButton();
        ResultPageBase resultPage = resultSearchPage.clickTypeOfCard("MAIL");
        List<Boolean> statuses = resultPage.checkButtonStatus("active");

        statuses.forEach(resultText -> softAssert.assertTrue(resultText, "Button is active"));
        softAssert.assertAll();
    }
}
