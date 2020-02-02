package com.microsoft.identity.client.msal.automationapp.broker;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.uiautomator.By;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObject;
import androidx.test.uiautomator.UiObjectNotFoundException;
import androidx.test.uiautomator.UiSelector;

import org.junit.Assert;

import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static com.microsoft.identity.client.msal.automationapp.utils.CommonUtils.TIMEOUT;
import static com.microsoft.identity.client.msal.automationapp.utils.CommonUtils.getResourceId;
import static com.microsoft.identity.common.adal.internal.AuthenticationConstants.Broker.AZURE_AUTHENTICATOR_APP_PACKAGE_NAME;
import static com.microsoft.identity.common.adal.internal.AuthenticationConstants.Broker.COMPANY_PORTAL_APP_PACKAGE_NAME;

public class BrokerCompanyPortal implements ITestBroker {


    @Override
    public String brokerAppName() {
        return "Intune Company Portal";
    }

    @Override
    public boolean isBrokerOpen() {
        final UiDevice mDevice =
                UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return mDevice.hasObject(By.res("com.microsoft.windowsintune.companyportal:id/account_list"));
    }

    @Override
    public void handleAccountPicker(final String username) {
        UiDevice device = UiDevice.getInstance(getInstrumentation());

        UiObject accountSelected = device.findObject(new UiSelector().resourceId(
                getResourceId(COMPANY_PORTAL_APP_PACKAGE_NAME, "account_list")
        ).index(0).childSelector(new UiSelector().textContains(
                username
        )));

        try {
            accountSelected.waitForExists(TIMEOUT);
            accountSelected.click();
        } catch (UiObjectNotFoundException e) {
            Assert.fail(e.getMessage());
        }
    }

}
