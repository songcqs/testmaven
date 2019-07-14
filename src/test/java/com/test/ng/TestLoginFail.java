package com.test.ng;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.test.lib.LibLogin;

public class TestLoginFail {
	WebDriver driver;

	@BeforeMethod
	public void setUp() throws Exception {
		System.out.println("start test");
		// 加载浏览器
		System.setProperty("webdriver.chrome.driver", "E:/Program Files/Google/Chrome/Application/chromedriver.exe");
		this.driver = new ChromeDriver();
		// 浏览器最大化
//		driver.manage().window().maximize();
	}

	@AfterMethod
	public void tearDown() throws Exception {
		System.out.println("end test");
		Thread.sleep(2000);
		// 退出浏览器
		this.driver.quit();
	}

	String[][] data = { { "xx", "yy", "帐号或密码错误" }, { "chengqingsong01", "", "请输入密码" },
			{ "", "cqs0108152535", "请输入帐号" } };

	/**
	 * 登陆失败测试
	 * 
	 * @throws InterruptedException
	 */
	@Test(groups = { "TestLoginFail.testLoginfail" })
	public void testLoginfail() throws InterruptedException {
		System.out.println("loginfail test");
		for (String[] item : this.data) {
			String username = item[0];
			String password = item[1];
			String message = item[2];
			new LibLogin(this.driver).login(username, password);

			Thread.sleep(2000);
			assertTrue(this.driver.getCurrentUrl().contains("mail.163.com"));

			WebDriverWait wait = new WebDriverWait(driver, 30);
			WebElement wl = wait.until(new ExpectedCondition<WebElement>() {
				public WebElement apply(WebDriver d) {
					return d.findElement(By.xpath("//div[@id='nerror']/div[2]"));
				}
			});

			String error = wl.getText();
//			String error = this.driver.findElement(By.xpath("//div[@id='nerror']/div[2]")).getText();
			if (error == "请先进行验证") {
				message = "请先进行验证";
				Thread.sleep(2000);
				assertEquals("error:" + error, "expected:" + message);
			}
		}
	}

}
