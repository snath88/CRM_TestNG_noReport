package com.crm.qa.base;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import com.crm.qa.util.TestUtil;

import io.github.bonigarcia.wdm.WebDriverManager;
//import com.crm.qa.util.WebEventListener;

public class TestBase {
	
	public static WebDriver driver;
	public static Properties prop;
	public static Logger log = Logger.getLogger(TestBase.class);
//	public static EventFiringWebDriver e_driver;
//	public static WebEventListener eventlistner;
	
	public TestBase()
	{
		prop= new Properties();
		
		try {
			///CRMTest/src/main/java/com/crm/qa/config/config.properties
			FileInputStream fp= new FileInputStream(System.getProperty("user.dir")+"/src/main/java/com/crm/qa/config/config.properties");
			
//			FileInputStream fp= new FileInputStream("/CRMTest/src/main/java/com/crm/qa/config/config.properties");
			prop.load(fp);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	public static void initialization()
	{
		String browser= prop.getProperty("browser");
		
		if (browser.equals("chrome"))
		{
			//System.setProperty("webdriver.chrome.driver", prop.getProperty("driver_path"));
			WebDriverManager.chromedriver().setup();
			ChromeOptions options = new ChromeOptions();
			options.addArguments("--headless"); //for headless mode
			options.addArguments("--window-size=800,600");//The invisible browser window is only 800x600 in size
			options.addArguments("start-maximized"); // open Browser in maximized mode
			options.addArguments("disable-infobars"); // disabling infobars
			options.addArguments("--disable-extensions"); // disabling extensions
			options.addArguments("--disable-gpu"); // applicable to windows os only
			options.addArguments("--disable-dev-shm-usage"); // overcome limited resource problems
			options.addArguments("--no-sandbox");
			driver= new ChromeDriver(options);
		}
//		e_driver= new EventFiringWebDriver(driver);
//		eventlistner = new WebEventListener();
//		
//		e_driver.register(eventlistner);
//		driver= e_driver;
		driver.manage().deleteAllCookies();
		driver.manage().window().maximize();
		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(TestUtil.PageTimeout));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(TestUtil.ImplicitWait));
		driver.get(prop.getProperty("url"));
		
	}

}
