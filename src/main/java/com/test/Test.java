package com.test;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import com.test.config.CollectionVo;
import com.test.config.ConfigVo;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;


public class Test {
	
	private static final String _WEB_DRIVER_ID = "webdriver.chrome.driver";
	private static final String _WEB_DRIVER_PATH = "D:\\workspace\\egov3.8_workspace\\crawler\\src\\lib\\chromedriver.exe";
//	private static final String _WEB_DRIVER_ID = "webdriver.gecko.driver";
//	private static final String _WEB_DRIVER_PATH = "D:\\workspace\\egov3.8_workspace\\crawler\\src\\lib\\geckodriver.exe";
	
	
	
//	private static final String _WEB_DRIVER_PATH = "/users/shinys/Coding/workspace/workspace_finally/crawler/src/lib/chromedriver";
	private WebDriver _driver;
	private String _base_url;
	
	List<String> _tossUrlList;
	List<CollectionVo> _colList;
	private ConfigVo _configVo;
	private String _threadName;

	public Agent(String webName, String threadName) {
		
		_threadName = threadName;
		_configVo = LoadConfig._configMap.get(webName);
		
		System.setProperty(_WEB_DRIVER_ID, _WEB_DRIVER_PATH);
		
		//Chrome Driver SetUp
        ChromeOptions options = new ChromeOptions();
        options.setCapability("ignoreProtectedModeSettings", true);
        //브라우저 켜지 않고 진행
        options.addArguments("headless");
        this._driver = new ChromeDriver(options);
		
		//firefox
//		FirefoxOptions options = new FirefoxOptions();
//		options.setCapability("ignoreProtectedModeSettings", true);
////		options.addArguments("--headless");
//		options.addArguments("user-agent:Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/43.0.2357.134");
//        this._driver = new FirefoxDriver(options);
        
        
        
	}


	/**
	 * Method : execute
	 * 작성자 : ShinYS
	 * 변경이력 :
	 * @return
	 * Method 설명 : Agent로직 실행
	 */
	public void execute(String getUrl){

		this._base_url = getUrl;
		
		
//		System.out.println("FetchUrl...	(" + getUrl +")");
		
		//1. 수집
		fetch();

		//2. 변환
		if(parser()) {
			analyzer();	//3. 분석 - DB통신 - ReadyQueueList
		}else {
			System.out.println("--- deadUrl Add ---");
		}
		
		
	}
	
	
	
	/**
	 * Method : fetch
	 * 작성자 : ShinYS
	 * 변경이력 :
	 * Method 설명 : 웹 수집
	 */
	public void fetch() {
		_driver.get(_base_url);
	}
	
	
	/**
	 * Method : parser
	 * 작성자 : ShinYS
	 * 변경이력 :
	 * Method 설명 :
	 */
	public boolean parser() {
		
		
		//deadUrl 체크
		if(_driver.getTitle().contains(_configVo.getxError1()) || _driver.getTitle().contains(_configVo.getxError2())) {
			
			_configVo.getDeadUrlList().add(_base_url);
			
			return false;
		}
		
		
		//중복리스트에 추가
//		_configVo.getOverlapUrlList().add(_base_url);
		
		//링크 추출
		this._tossUrlList = new ArrayList<>();
		List<WebElement> webElements = _driver.findElements(By.tagName("a"));
        for(WebElement webElement : webElements) {
        	
        	String url = webElement.getAttribute("href"); 
        	
        	try {
        		if(!url.isEmpty()) {        	
        			String[] refinedUrl = url.split("#");
        			boolean flag = true;
        			//중복 검사
        			for(String overLapUrl : _configVo.getOverlapUrlList()) {       				
        				if(refinedUrl[0].equals(overLapUrl)) {
        					flag = false;
        					break;
        				}
        			}
        			//deadUrl 검사
        			for(String deadUrl : _configVo.getDeadUrlList()) {       				
        				if(refinedUrl[0].equals(deadUrl)) {
        					flag = false;
        					break;
        				}
        			}
        			
        			//결과List 추가
        			if(flag) {
        				this._tossUrlList.add(refinedUrl[0]);
        			}
        		}
			} catch (Exception e) {
				System.out.println("--- url Null ---");
			}
        	
        }
        
        //데이터 추출
        this._colList = new ArrayList<CollectionVo>();
        CollectionVo colVo = new CollectionVo();
        
        webElements = _driver.findElements(By.xpath(_configVo.getxContent()));
        for(WebElement webElement : webElements) {
        	
//        	colVo.setContents(webElement.getText());
        
        	//hidden이나 display:none 전체 텍스트 가져와보자
        	JavascriptExecutor js = (JavascriptExecutor) _driver;
        	String getTxt = (String) js.executeScript("return arguments[0].innerText", webElement);
        	colVo.setContents(getTxt);
        	
        }
        
        String title1;
        String title2;
        
        try {
        	
//        	title1 = _driver.findElement(By.xpath(_configVo.getxTitle())).getText();
        	
        	WebElement webElement = _driver.findElement(By.xpath(_configVo.getxTitle()));
        	JavascriptExecutor js = (JavascriptExecutor) _driver;
        	title1 = (String) js.executeScript("return arguments[0].innerText", webElement);
        	
		} catch (Exception e) {
			title1 = _driver.getTitle();
		}
        
        
        try {
//        	title2 = " - " + _driver.findElement(By.xpath(_configVo.getxTitle2())).getText();

        	WebElement webElement = _driver.findElement(By.xpath(_configVo.getxTitle2()));
        	JavascriptExecutor js = (JavascriptExecutor) _driver;
        	title2 = " - " + (String) js.executeScript("return arguments[0].innerText", webElement);
        	
		} catch (Exception e) {
			title2 = "";
		}
        
        colVo.setTitle(title1 + title2);
        colVo.setSeed(_configVo.getSeed());
        colVo.setCol_url(_base_url);
        
        
        colVo.setTitle(colVo.getTitle().replaceAll("\n", " "));
        colVo.setTitle(colVo.getTitle().replaceAll("'", "''"));
        colVo.setContents(colVo.getContents().replaceAll("\n", " "));			
        colVo.setContents(colVo.getContents().replaceAll("'", "''"));
		
        _colList.add(colVo);
        
        return true;
        
	}
	
	
	/**
	 * Method : analyzer
	 * 작성자 : ShinYS
	 * 변경이력 :
	 * Method 설명 : 웹페이지 분석
	 */
	public void analyzer() {

		
		try {
			if(_tossUrlList.size() > 0)
			_configVo.getReadyUrlQueue().put(_tossUrlList);
			_configVo.setResultSize(_configVo.getResultSize()+1);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
//		Db Connect
		Storage.connect();
		
		System.out.println("FetchUrl["+_threadName+"]...	 (" + _base_url +")");

		try {
			for(CollectionVo colVo : _colList) {
				Storage.collectionInsert(colVo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
//		System.out.println(" -> urlQueue : " + _configVo.getUrlQueue());
		System.out.println(" -> 작업_urlQueue_size 	: " + _configVo.getUrlQueue().size());
		System.out.println(" -> 결과_ReadyQueueSize 	: " + _configVo.getReadyUrlQueue().size());
		LoadConfig.LineType(LoadConfig.LineType.LIGHT);
		
	}
	
	
	
	/**
	 * Method : close
	 * 작성자 : ShinYS
	 * 변경이력 :
	 * Method 설명 : Driver Close
	 */
	public void close() {
		try {
			this._driver.close();
		} catch (Exception e) {
			System.out.println("!-- GoogleDriver_close error [ "+ _threadName + "] --!");
		}
		
		try {
			this._driver.quit();
		} catch (Exception e) {
			System.out.println("!-- GoogleDriver_quit error [ "+ _threadName + "] --!");
		}
	}
	
}

