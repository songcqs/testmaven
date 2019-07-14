package com.test.lib;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;
import org.testng.Reporter;

//描述：重写testngRetry接口，设置场景恢复
public class MyRetry implements IRetryAnalyzer {
/*
	private int retryCount = 0;
	private static final int maxRetryCount = 3;// #控制失败跑几次

	@Override
	public boolean retry(ITestResult iTestResult) {
		if (retryCount < maxRetryCount) {
			retryCount++;
			return true;
		}
		return false;
	}
*/	
	private static int maxRetryCount = 3;// 最大重新执行场景的次数
	private int retryCount = 1;


	/*
	 * ITestResult是TestNG提供的一个接口 结合@AfterMethod使用类似监听器 可以监听@Test方法的执行状态等信息。(non-Javadoc)
	 * @see org.testng.IRetryAnalyzer#retry(org.testng.ITestResult)
	 */
	// 场景恢复设置，重新执行失败用例的次数
	public boolean retry(ITestResult result) {
		System.out.println(result);
		if (retryCount <= maxRetryCount) {
			String message = "Running retry for '" + result.getName() + "' on class " 
							+ this.getClass().getName()	+ " Retrying " + retryCount + " times";
			Reporter.setCurrentTestResult(result);
			Reporter.log("RunCount=" + (retryCount + 1));// 报告中输出日志
			retryCount++;
			return true;
		}
		return false;
	}
}
