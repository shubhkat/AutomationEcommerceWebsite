package com.flipkart;

import java.io.IOException;

import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;

import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.filpkart.FlipkartProduct;

import utility.Screenshot;

public class TestFlipkartProduct {
	
	public static ExtentHtmlReporter htmlreport;
	public static ExtentReports report;
	public static ExtentTest test;
	
	FlipkartProduct f = new FlipkartProduct();
	
	@BeforeSuite
	public void testOpenBrowser() {
		
		String reportpath = "../AutomationEcommerceWebsite/extentReport/report.html";
		htmlreport = new ExtentHtmlReporter(reportpath);
		htmlreport.config().setDocumentTitle("Ecommerce Automation testing report");
		htmlreport.config().setReportName("Automation testing");
		htmlreport.config().setTheme(Theme.STANDARD);
		
		report = new ExtentReports();
		report.attachReporter(htmlreport);
		report.setSystemInfo("Browsername", "Firefox");
		report.setSystemInfo("Tester", "Test Engineer");
		report.setSystemInfo("OS", "windows-10");

		f.openBrowser();	
	}
	
	@AfterSuite
	public void testCloseBrowser() throws InterruptedException {
		f.closeBrowser();
	}
	
	@AfterMethod
	public void captureScreenshot(ITestResult result) throws IOException {
		test = report.createTest(result.getName());
		if(result.getStatus()==ITestResult.FAILURE) {
			String temp = Screenshot.screenshot(f.driver, result.getName());
			test.log(Status.FAIL, MarkupHelper.createLabel("Test Method Failed", ExtentColor.RED));
			test.addScreenCaptureFromPath(temp);
		}
		else if(result.getStatus()==ITestResult.SUCCESS) {
			test.log(Status.PASS, MarkupHelper.createLabel("Test Method Passed", ExtentColor.GREEN));
			/*
			 * String temp = Screenshot.screenshot(f.driver, result.getName());
			 * test.addScreenCaptureFromPath(temp);
			 */
		}
		else {
			test.log(Status.SKIP, MarkupHelper.createLabel("Test Method Skiped", ExtentColor.GREY));
		}
		report.flush();
	}
	
	@Test(groups = "testMethods", description = "this method is used to login in an application")
	public void testLoginPage() throws InterruptedException{
		f.loginPage();
	}
	
	@Test(dependsOnGroups = "testMethods", priority = 1, description = "this method is used to verify logo of application")
	public void testVerifyLogo() {
		f.verifyLogo();
	}
	
	@Test(dependsOnGroups = "testMethods", priority = 2, description = "this method is used to select product from product page")
	public void testProductSelection() throws InterruptedException{
		f.productSelection();
	}
	
	@Test(dependsOnMethods = "testProductSelection", description = "this method is used to add a selected product to the basket")
	public void testAddToBasket() throws InterruptedException{
		f.addToBasket();
	}
	
	@Test(dependsOnMethods = "testAddToBasket", description = "this method is used to compare the price whether it is same or not")
	public void testComparePrice() throws InterruptedException{
		f.comparePrice();
	}
	
	@Test(dependsOnMethods = {"testLoginPage", "testProductSelection", "testAddToBasket", "testComparePrice"})
	public void detailsoftestcases() {
		f.detailsOfTestCases();
	}
}
