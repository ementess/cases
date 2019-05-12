package UI;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import java.util.List;


public class StepDefinitions extends VariableDefinitions
{

    public WebDriver driver;


    public void ValidateCurrentPage(String expected)
    {
        Assert.assertEquals(expected, driver.getCurrentUrl());

    }

    public void ValidateAllPages()
    {

        for (int i = 0; i < tabCount; i++) {

            clickToLink(tabs[i]);
            validateInvalidImagesOnPage();
            ValidateCurrentPage(listURL+pages[i]);
        }

    }


    public void validateInvalidImagesOnPage() {
        try {
            List<WebElement> imagesList = driver.findElements(By.tagName("img"));
            for (WebElement imgElement : imagesList) {
                if (imgElement != null) {
                    verifyimageActive(imgElement);
                }
            }
        } catch (Exception e) {
        }
    }
    public void verifyimageActive(WebElement imgElement) {
        try {
            HttpClient client = HttpClientBuilder.create().build();
            HttpGet request = new HttpGet(imgElement.getAttribute("src"));
            HttpResponse response = client.execute(request);


            if (response.getStatusLine().getStatusCode() != 200)
                System.out.println("An image can't be loaded unexpectedly :" + imgElement);
        } catch (Exception e) {
        }
    }

    public void clickToLink(String linkText){

        WebElement linkByText = driver.findElement(By.linkText(linkText));
        linkByText.click();
    }

}
