package com.sparta.jas;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class QAToolsForm {
    private WebDriver driver;
    private String qaToolsFormURL = "http://toolsqa.com/automation-practice-form/";

    private By continentDropdownListOptionsSelector = By.cssSelector("#continents option");
    private By continentDropdownListID = By.id("continents");
    private By male = By.id("sex-0");
    private By female = By.id("sex-1");

    public QAToolsForm(WebDriver driver) {
        this.driver = driver;
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    public void goToQAFormPage(){
        driver.navigate().to(qaToolsFormURL);
    }

    public void quit(){
        driver.quit();
    }


    //Continent List
    private List<String> continentListAsString(){
        List<WebElement> continentOptions = driver.findElements(continentDropdownListOptionsSelector);
        List<String> continentsStringList = new ArrayList<>();

        for (WebElement continent: continentOptions) {
            continentsStringList.add(continent.getText());
        }

        return continentsStringList;
    }

    public void selectContinent(String continentName){
        List<String> continentOptions = continentListAsString();
        if (continentOptions.contains(continentName)){
            Select continents = new Select(driver.findElement(continentDropdownListID));
            continents.selectByVisibleText(continentName);
        } else {
            System.out.println("Please select one of the options below");
            for (String continent: continentOptions){
                System.out.println(continent);
            }
        }
    }

    public boolean validateContinentValueSelected(String continentName){
        boolean selectedCorrectly;

        Select continents = new Select(driver.findElement(continentDropdownListID));
        selectedCorrectly = continents.getFirstSelectedOption().getText().equals(continentName);
        return selectedCorrectly;
    }

    // Gender Radio Buttons
    public void clickGenderButton(char gender) {
        if (gender == 'm') {
            driver.findElement(male).click();
        } else if (gender == 'f'){
            driver.findElement(female).click();
        }
    }

    public boolean checkGenderButtonClicked(char gender){
        boolean clickedCorrectly;

        if (gender == 'm') {
            clickedCorrectly = driver.findElement(male).isSelected() & !driver.findElement(female).isSelected();
        } else if (gender == 'f'){
            clickedCorrectly = driver.findElement(female).isSelected() & !driver.findElement(male).isSelected();
        } else {
            clickedCorrectly = !driver.findElement(male).isSelected() & !driver.findElement(female).isSelected();
        }
        return clickedCorrectly;
    }

    // First Name Box
    public void fillFirstNameBox(String firstName){
        driver.findElement(By.name("firstname")).sendKeys(firstName);
    }

    public boolean checkFirstNameBoxFilled(String firstName){
        boolean filledCorrectly;

        String filledFirstName = driver.findElement(By.name("firstname")).getAttribute("value");
        filledCorrectly = filledFirstName.equals(firstName);

        return filledCorrectly;
    }

    // Link Tests
    public void followPartialLink() {
        driver.findElement(By.linkText("Partial Link Test")).click();
    }

    public void followLink(){
        driver.findElement(By.linkText("Link Test")).click();
    }

    public boolean checkLinkFollowed(String linkPath){
        boolean linkFollowed;

        String currentURL =  driver.getCurrentUrl();
        linkFollowed = currentURL.equals(linkPath);

        return linkFollowed;
    }

    // Checkbox Testing
}
