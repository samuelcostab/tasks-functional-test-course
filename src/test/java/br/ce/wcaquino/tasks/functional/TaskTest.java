package br.ce.wcaquino.tasks.functional;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;


public class TaskTest {
	
	public WebDriver accessToApplication() throws MalformedURLException {
		// New ChromeDriver() removido por conta que a seleção do Driver vai ser orquestrada pelo Hub
		// WebDriver driver = new ChromeDriver();
		DesiredCapabilities capabilities = DesiredCapabilities.chrome();
		WebDriver driver = new RemoteWebDriver(new URL("http://172.18.0.1:4444/wd/hub"), capabilities);
		
		driver.navigate().to("http://192.168.1.10:8001/tasks");
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		return driver;
	}
	
	@Test
	public void shouldSaveTaskWithSuccess() throws MalformedURLException {
		WebDriver driver = accessToApplication();
		
		try {
			driver.findElement(By.id("addTodo")).click();
			driver.findElement(By.id("task")).sendKeys("Test by Selenium");
			driver.findElement(By.id("dueDate")).sendKeys("10/10/2025");
			driver.findElement(By.id("saveButton")).click();
			String message = driver.findElement(By.id("message")).getText();
			Assert.assertEquals("Success!", message);
			
		} finally {
			driver.quit();
		}
	}
	
	@Test
	public void shouldNotSaveTaskWithoutDescription() throws MalformedURLException {
		WebDriver driver = accessToApplication();
		
		try {
			driver.findElement(By.id("addTodo")).click();
			driver.findElement(By.id("dueDate")).sendKeys("10/10/2025");
			driver.findElement(By.id("saveButton")).click();
			String message = driver.findElement(By.id("message")).getText();
			Assert.assertEquals("Fill the task description", message);
			
		} finally {
			driver.quit();
		}
	}
	
	@Test
	public void shouldNotSaveTaskWithoutDueDate() throws MalformedURLException {
		WebDriver driver = accessToApplication();
		
		try {
			driver.findElement(By.id("addTodo")).click();
			driver.findElement(By.id("task")).sendKeys("Test by Selenium");
			driver.findElement(By.id("saveButton")).click();
			String message = driver.findElement(By.id("message")).getText();
			Assert.assertEquals("Fill the due date", message);
			
		} finally {
			driver.quit();
		}
	}
	
	@Test
	public void shouldNotSaveTaskWithPastDueDate() throws MalformedURLException {
		WebDriver driver = accessToApplication();
		
		try {
			driver.findElement(By.id("addTodo")).click();
			driver.findElement(By.id("task")).sendKeys("Test by Selenium");
			driver.findElement(By.id("dueDate")).sendKeys("10/10/2015");
			driver.findElement(By.id("saveButton")).click();
			String message = driver.findElement(By.id("message")).getText();
			Assert.assertEquals("Due date must not be in past", message);
			
		} finally {
			driver.quit();
		}
	} 

}
