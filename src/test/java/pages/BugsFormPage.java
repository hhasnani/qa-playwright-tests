package pages;

import com.microsoft.playwright.Page;
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class BugsFormPage {
    private final Page page;

    // Locators
    private final String firstName = "#firstName";
    private final String lastName = "#lastName";
    private final String email = "#emailAddress";
    private final String phone = "#phone";
    private final String password = "#password";
    private final String country = "#countries_dropdown_menu";
    private final String agreeCheckbox = "#exampleCheck1";
    private final String homeButton = "#home";
    private final String submitButton = "button[type='submit']";

    //private final String successMsg = ".alert-success";
    private final String successMsg = "#message";
    //private final String successMsg = "Successfully registered the following information";
    private final String errorMsg = ".alert-danger";

    public BugsFormPage(Page page) {
        this.page = page;
    }

    public void navigate() {
        page.navigate("https://qa-practice.netlify.app/bugs-form");
    }

    public void fillBasicDetails(String fName, String lName, String mail, String ph, String pwd, String ctry) {
        page.fill(firstName, fName);
        page.fill(lastName, lName);
        page.fill(email, mail);
        page.fill(phone, ph);
        page.fill(password, pwd);
        page.selectOption(country, ctry);
    }

    public void clickAgree() {
        page.click(agreeCheckbox);
    }

    public void submit() {
        page.click(submitButton);
    }

    public void clickHome() {
        page.click(homeButton);
    }

    public void expectSuccess() {
        assertThat(page.locator(successMsg)).isVisible();
        assertThat(page.locator(successMsg)).hasText("Successfully registered the following information");
    }

    public void expectError() {
        assertThat(page.locator(errorMsg)).isVisible();
        assertThat(page.locator(errorMsg)).not().hasText("Successfully registered the following information");
    }

    public void expectPhoneError() {
        assertThat(page.locator(errorMsg)).isVisible();
        assertThat(page.locator(errorMsg)).hasText("The phone number should contain at least 10 characters!\n");
    }
    public String getPhoneValue() {
        return page.inputValue(phone);
    }

    public boolean isAgreeEnabled() {
        return page.isEnabled(agreeCheckbox);
    }

    public String getEmailValue() {
        return page.inputValue(email);
    }

    public String getLastNameValue() {
        return page.inputValue(lastName);
    }

    public String getCurrentUrl() {
        return page.url();
    }

    public String getPasswordValue() {
        return page.inputValue(password);
    }
}
