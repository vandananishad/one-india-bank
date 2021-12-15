package stepdefination;

import java.util.Map;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.cucumber.java.After;
import io.cucumber.java.AfterStep;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.*;
import junit.framework.Assert;
import pageObjectModel.CustomerAccountInterface;
import pageObjectModel.CustomerLoginInterface;
import pageObjectModel.SavingsAccount;
import pageObjectModel.Withdraw;

@SuppressWarnings("deprecation")
public class TC_Withdraw {

	public WebDriver driver;
	public CustomerLoginInterface cli;
	public CustomerAccountInterface cai;
	public SavingsAccount sa;
	public Withdraw wd;
	Actions action;
	Map<String, String> map;
	WebDriverWait myWait;
	Wait<WebDriver> wait;

	@Given("c-user is  on Saving Account interface {string}")
	public void c_user_is_on_saving_account_interface(String url) {
		// Write code here that turns the phrase above into concrete actions
		System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "//lib/chromedriver.exe");
		driver = new ChromeDriver();
		driver.get(url);
		cli = new CustomerLoginInterface(driver);
		sa = new SavingsAccount(driver);
	}

	@Given("c-user   clicks on Customer  link")
	public void c_user_clicks_on_customer_link() throws InterruptedException {
		// Write code here that turns the phrase above into concrete actions
		sa.openCustomer();
		Thread.sleep(5000);
		myWait = new WebDriverWait(driver, 5);
	}

	@When("c-user  Enters valid credential  {string} and {string}")
	public void c_user_enters_valid_credential_and(String username, String pwd) {
		// Write code here that turns the phrase above into concrete actions
		cli.enterCredentials(username, pwd);
	}

	@Then("c-user  Click on Login  buttons")
	public void c_user_click_on_login_buttons() {
		// Write code here that turns the phrase above into concrete actions
		cli.clickOnLogin();
		cai = new CustomerAccountInterface(driver);
	}

	@When("c-user  move  mouse Over  Account")
	public void c_user_move_mouse_over_account() {
		// Write code here that turns the phrase above into concrete actions
		action = new Actions(driver);
		action.moveToElement(cai.moveToTransaction()).perform();
	}

	@When("c-user  click On Withdraw  Sub-menu")
	public void c_user_click_on_withdraw_sub_menu() {
		// Write code here that turns the phrase above into concrete actions
		cai.clickWithDraw();
		wd = new Withdraw(driver);
	}

	@Then("c-user  verify   title  {string}")
	public void c_user_verify_Title(String title) {
		// Write code here that turns the phrase above into concrete actions
		try {
			if (title.equalsIgnoreCase(driver.getTitle()))
				Assert.assertTrue(true);
			else
				Assert.assertTrue(false);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
//			driver.close();
		}
	}

	@When("C-user provided account num as {string} and amount as {int}")
	public void c_user_provided_account_num_as_and_amount_as(String accNum, Integer amt) {
		// Write code here that turns the phrase above into concrete actions
		wd.selectAccount(accNum);
		wd.enterAmount(amt);
	}

	@When("C-user provided account num as {int}-SBI-SBI Wardha-Saving and amount as {int}")
	public void c_user_provided_account_num_as_sbi_sbi_wardha_saving_and_amount_as(Integer acc, Integer amt)
			throws InterruptedException {
		// Write code here that turns the phrase above into concrete actions
		wd.selectAccount(acc.toString() + "-SBI-SBI Wardha-Saving");
		Thread.sleep(3000);
		wd.enterAmount(amt);
		wd.clickConfirmBtn();
	}

	@Then("C-user verifys the Successful in step")
	public void c_user_verifys_the_successful_in_step() {
		// Write code here that turns the phrase above into concrete actions
		try {
			if (driver.getPageSource().contains("Successful")) {
				Assert.assertTrue(true);
			} else {
				Assert.assertTrue(false);
			}
		} finally {
//			driver.close();
		}
	}

	@When("C-user click on cancel button")
	public void c_user_click_on_cancel_button() {
		// Write code here that turns the phrase above into concrete actions
		wd.clickCancelBtn();
	}

	@Then("C-user verify the Amount field as empty")
	public void c_user_verify_the_amount_field_as_empty() {
		// Write code here that turns the phrase above into concrete actions
		try {
		if (wd.getAmtFeild().equals("")) {
			Assert.assertTrue(true);
		} else {
			Assert.assertTrue(false);
		}
		}
		finally {
//			driver.close();
		}
	}

	@After
	 public void addScreenshot(Scenario scenario) {

		// validate if scenario has failed
		if (driver!=null && scenario.isFailed()) {
			PageFactory.initElements(driver, this);
			final byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
			scenario.attach(screenshot, "image/png", "image");
			driver.quit();
		}
		if(!scenario.isFailed() && driver!=null)
		{
			driver.quit();
		}

	}
}
