import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.PendingException;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.concurrent.TimeUnit;

public class StepDefinations {


    TestUtil testUtilObj = new TestUtil();

    WebDriver driver=null;

    @Before
    public void setup(Scenario scenario)
    {
        String scenarioName = scenario.getName();
        WebDriverManager.chromedriver().setup();
        driver= new ChromeDriver();
        testUtilObj.setDriver(driver);

        if(testUtilObj.getHtmlReporter()==null)
        {
            testUtilObj.extentReportInitialize("chrome","Test","Test"
            ,"Test","Test","Test");
        }
        testUtilObj.extentTestInitialize(scenarioName);

    }

    @After
    public void tearDown()
    {
        testUtilObj.closeExtentReport();
    }


    @Given("^User launch the url$")
    public void user_launch_the_url() throws Throwable {
        System.out.println("Launching chrome");
       driver.get("https://mvnrepository.com/");
        driver.manage().window().maximize();
       driver.manage().timeouts().implicitlyWait(5000, TimeUnit.MILLISECONDS);
        testUtilObj.addtoReport("Pass", "Launch Maven ", "");
    }

    @Then("^User enter the text and capture screeenshot post entering the text$")
    public void user_enter_the_text_and_capture_screeenshot_post_entering_the_text() throws Throwable {
    driver.findElement(By.xpath(".//input[@class='textfield']")).sendKeys("TestNg");

        testUtilObj.addtoReport("Pass", "entered test text ", "");

    }

    @Then("^User click search button$")
    public void user_click_search_button() throws Throwable {
       driver.findElement(By.xpath(".//input[@class='button']")).click();
        Thread.sleep(5000);
    }

    @Then("^user close the browser$")
    public void user_close_the_browser() throws Throwable {
        testUtilObj.addtoReport("Pass ", " Screenshot post clicking search button ", "");
        testUtilObj.addtoReport("Pass","closing browser","");
        System.out.println("Quit the browser");

    }

}
