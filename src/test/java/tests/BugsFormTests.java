package tests;

import base.TestBase;
import pages.BugsFormPage;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class BugsFormTests extends TestBase {
    private BugsFormPage form;

    @BeforeMethod
    public void setupForm() {
        form = new BugsFormPage(page);
        form.navigate();
    }

    // Positive Test
    @Test(description = "Sanity - Valid form submission should show success message")
    public void sanity_ValidFormSubmission() {
        form.fillBasicDetails("H", "H", "emailadd@test.com", "9876543210", "Password1!", "New Zealand");
        //form.clickAgree();
        form.submit();
        form.expectSuccess();
    }


    // 🐞 Bug Validation Tests
    @Test(description = "TC01 - BUG: Last name validation accepts blank value")
    public void tc01_LastNameMandatory() {
        form.fillBasicDetails("H", "", "emailadd@test.com", "9876543210", "Password1!", "New Zealand");
        form.submit();
        form.expectError();
    }

    @Test(description = "TC02 - BUG: Phone Number label misspelled")
    public void tc02_PhoneLabelSpelling() {
        String label = page.locator("xpath=//*[@id='registerForm']/div[3]/label").textContent();
        Assert.assertTrue(label.toLowerCase().contains("number"), "Label misspelled as: " + label);
    }

    @Test(description = "TC03 - BUG: Home button navigates to incorrect url")
    public void tc03_HomeButtonNavigation() {
        form.clickHome();
        Assert.assertEquals(form.getCurrentUrl(),
                "https://qa-practice.netlify.app/bugs-form",
                "Home button did not navigate back properly");
    }

    @Test(description = "TC04 - BUG: Phone Number should accept at least 10 digits")
    public void tc04_PhoneMoreThan10Digits() {
        form.fillBasicDetails("fnamestring", "lstnamestring", "emailadd@test.com", "123456789", "Password1!", "New Zealand");
        form.submit();
        form.expectPhoneError();
    }

    @Test(description = "TC05 - BUG: Phone NUmber should not accept alphabetic characters")
    public void tc05_PhoneAcceptAlpha() {
        form.fillBasicDetails("fnamestring", "lstnamestring", "emailadd@test.com", "12345abcde", "Password1!", "New Zealand");
        Assert.assertFalse(form.getPhoneValue().matches(".*[a-zA-Z].*"));
    }

}
