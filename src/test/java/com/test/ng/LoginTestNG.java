package com.test.ng;

import static org.testng.Assert.assertTrue;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.test.lib.LibLogin;

public class LoginTestNG {
	WebDriver driver;

	/**
	 * 测试成功登陆与注销功能
	 * 
	 * @throws InterruptedException
	 */
//	@Test(invocationCount = 2, invocationTimeOut = 10000)//执行两次时间不超过10s
	@Test(groups = { "LoginTestNG.login" })
	public void login() throws InterruptedException {
		System.out.println("test login");
		String username = "songcqs001";
		String password = "cqs0108152535";
		// 登录
		LibLogin libLoogin = new LibLogin(this.driver);
		libLoogin.login(username, password);
		Thread.sleep(2000);
		// 判断URL是否跳转
		assertTrue(this.driver.getCurrentUrl().contains("main"));
		System.out.println(this.driver.getCurrentUrl());
		Thread.sleep(1000);
		// 判断页面是否跳转
		WebElement element = this.driver.findElement(By.xpath("//span[text()='红旗邮件']"));
		assertTrue(element.getText().contains("红旗邮件2"));
		System.out.println(element.getText());

		// 退出
//		new LibLogout(this.driver).logout();
	}

	@BeforeMethod
	public void beforeMethod() {
		System.out.println("start test");
		// 加载浏览器
		System.setProperty("webdriver.chrome.driver", "E:/Program Files/Google/Chrome/Application/chromedriver.exe");
		this.driver = new ChromeDriver();
		// 浏览器最大化
//		driver.manage().window().maximize();
	}

	@AfterMethod
	public void afterMethod() throws InterruptedException {
		System.out.println("end test");
		// 退出浏览器
		this.driver.quit();
	}

}
