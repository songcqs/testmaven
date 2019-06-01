package com.test.ng;

import static org.testng.Assert.assertTrue;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

public class DownloadTest {

	WebDriver driver;

	@Test
	public void testOne() throws Exception {
		// 使用Chrome浏览器自动下载文件并保存到指定的文件路径
		// 或 使用Selenium更改Chrome默认下载存储路径
		System.setProperty("webdriver.chrome.driver",
				"E:/Program Files/Google/Chrome/Application/chromedriver.exe");// 设置驱动的路径
		DesiredCapabilities caps = setDownloadsPath();// 更改默认下载路径
		driver = new ChromeDriver(caps);
		driver.manage().window().maximize();
		driver.get("http://chromedriver.storage.googleapis.com/index.html?path=2.40/");// 到目标网页
		Thread.sleep(1000);

		// 如果文件已存在将其删除
		File file = new File("G:\\Downloads\\chromedriver_win32.zip");
		if (file.exists()) {
			file.delete();
			System.out.println("file.delete");
		}

		WebElement myElement = driver.findElement(By.linkText("chromedriver_win32.zip"));
		Actions action = new Actions(driver);
		myElement.click();// 点击下载
		Thread.sleep(1000);
		// 判断是否下载完成，没玩继续等待
		Boolean bool = null;
		do {
			ArrayList<String> files = getFiles("G:\\Downloads");
			bool = files.contains("chromedriver_win32.zip");
			Thread.sleep(1000);
		} while (!bool);// 如果为false（没下载完）继续等待
		assertTrue(bool);
	}

	// 获取一个路径下的所有文件、文件夹名称
	public static ArrayList<String> getFiles(String path) {
		ArrayList<String> files = new ArrayList<String>();
		File file = new File(path);
		/*
		 * java.io.File类用于表示文件或目录。
		 * File类只用于表示目标文件(目录)的大小，名称，路径，并可进行是否存在的判断，还有创建和修改等，不能用于文件内容的访问 创建File对象: File
		 * file = new File("E:/...");//文件/文件夹路径对象 File file = new File("..."
		 * ,""...);//父目录绝对路径 + 子目录名称 File file = new File("...","...");//父目录File对象 +
		 * 子目录名称
		 * 
		 * file.exists():判断文件/文件夹是否存在 file.delete():删除文件/文件夹 file.isDirectory():判读是否为目录
		 * file.isFile():判读是否为文件夹 file.mkdir():创建文件夹(仅限一级目录)
		 * file.mkdirs():创建多及目录文件夹(包括但不限一级目录) file.createNewFile():创建文件
		 * file.getAbsolutePath():得到文件/文件夹的绝对路径 file.getName():得到文件/文件夹的名字
		 * file.String():同样是得到文件/文件夹的绝对路径等于file.getAbsolutePath()
		 * file.getParent():得到父目录的绝对路径
		 * 
		 * String[] gdir.list():得到目录的子目录\文件的名称(不是绝对路径) File[]
		 * dir.listFiles():得到目录的子目录\文件事是否存在。
		 */
		String[] temp = file.list();
		File[] tempList = file.listFiles();

		for (int i = 0; i < tempList.length; i++) {
			if (tempList[i].isFile()) {
				System.out.println("文     件：" + tempList[i]);
				files.add(temp[i]);
			}
			if (tempList[i].isDirectory()) {
				System.out.println("文件夹：" + tempList[i]);
			}
		}
		return files;
	}

	// 单独重构成一个方法，然后调用
	// DesiredCapabilities的作用: 负责启动服务端时的参数设置，启动session的时候是必须提供的
	public DesiredCapabilities setDownloadsPath() {
		String downloadsPath = "G:\\Downloads";
		HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
		chromePrefs.put("download.default_directory", downloadsPath);
		// chromeOptions 是一个配置 chrome 启动时属性的类。
		ChromeOptions options = new ChromeOptions();
		// setExperimentalOption(String name, Object value)设置实验选项。
		// 详见：http://javadox.com/org.seleniumhq.selenium/selenium-chrome-driver/2.40.0/org/openqa/selenium/chrome/ChromeOptions.html
		options.setExperimentalOption("prefs", chromePrefs);
		// 设置自动化相关参数（设置的过程中只需更改value值）
		DesiredCapabilities caps = new DesiredCapabilities();
		// 设置测试的web浏览器，如果是测试app则忽略
		caps.setCapability(ChromeOptions.CAPABILITY, options);
		return caps;
	}

	@AfterClass
	public void tearDown() {
		driver.quit();
	}

}
