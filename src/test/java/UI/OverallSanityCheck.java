package UI;

import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.*;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class OverallSanityCheck extends StepDefinitions
{



    @BeforeTest
    public void setupTest (){
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.navigate().to(testURL);

    }

    @Test
    public void SanityCheck() {
        WebDriverWait wait = new WebDriverWait(driver, 10);  // you can reuse this one
        driver.findElement(By.xpath("//span[.='Giriş Yap']")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("email")));
        driver.findElement(By.id("email")).sendKeys(loginUserName);
        driver.findElement(By.id("password")).sendKeys(loginPassword);
        driver.findElement(By.id("loginSubmit")).click();
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loginSubmit")));
        ValidateCurrentPage(DefaultURIAfterLogin);
        ValidateAllPages();
        driver.findElement(By.cssSelector("#dynamic-boutiques > div > div > div:nth-child(1)")).click();
        validateInvalidImagesOnPage();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"root\"]/div/ul/li[1]")));
        driver.findElement(By.xpath("//*[@id=\"root\"]/div/ul/li[1]")).click();
        validateInvalidImagesOnPage();
        driver.findElement(By.cssSelector(".add-to-bs-tx")).click();

    }




}
