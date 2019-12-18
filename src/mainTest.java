import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.bcel.classfile.Constant;
import org.openqa.selenium.By;
import org.openqa.selenium.By.ByXPath;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.Select;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class mainTest {
	static final int CHROME = 1;
	static final int FIREFOX = 2;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.setProperty("webdriver.chrome.driver", "E:\\SeleniumDriver\\exe\\chromedriver.exe");
		System.setProperty("webdriver.gecko.driver", "E:\\SeleniumDriver\\exe\\geckodriver.exe");
		
		browserTest(FIREFOX);
		
	}
	
	public static boolean browserTest (int temp) {		
			
		
		BufferedReader br = null;

        try {   
            br = new BufferedReader(new FileReader("D:\\Eclipse\\1612241\\TEST.txt"));       

            // System.out.println("Đọc nội dung file sử dụng phương thức readLine()");
           
            String textInALine;
            int i = 1;
            while ((textInALine = 	br.readLine()) != null) {
            	WebDriver driver = temp == CHROME ?  new ChromeDriver() : new FirefoxDriver();    		
        		WebDriverWait wait = new WebDriverWait(driver, 20);	
        		try {
        			driver.get("http://mantis:8080/signup_page.php");
            		try {
            			driver.findElement(By.id("username")).sendKeys("1612241."+ i);
                		driver.findElement(By.id("email-field")).sendKeys(textInALine);
                		driver.findElement(By.cssSelector(".width-40")).click();
                		System.out.print("Register success!!!");
        				
        			} catch (Exception e) {
        				System.out.print("Register fail: " + e);
        			}
            		
            		driver.close();
         		
//            		// gmail xác thực tài khoản
            		driver = temp == CHROME ?  new ChromeDriver() : new FirefoxDriver();
            		wait = new WebDriverWait(driver, 20);
            		driver.get("https://accounts.google.com/signin/v2/identifier?hl=vi&continue=https%3A%2F%2Fmail.google.com%2Fmail&service=mail&flowName=GlifWebSignIn&flowEntry=AddSession");
            		driver.findElement(By.id("identifierId")).sendKeys(textInALine);
            		driver.findElement(By.cssSelector(".RveJvd")).click();
            		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
//            		wait.until(ExpectedConditions.elementToBeClickable(By.name("password")));
//            		driver.findElement(By.name("password")).sendKeys("0309hung", Keys.RETURN);
            		WebElement password = driver.findElement(By.xpath("//input[@name='password']"));
            		wait.until(ExpectedConditions.elementToBeClickable(password));
            		password.sendKeys("0309hung", Keys.RETURN);
            		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//span[@name='lchung-1612241 Mant.'])[2]")));
            		driver.findElement(By.xpath("(//span[@name='lchung-1612241 Mant.'])[2]")).click();		
            		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(.,'http://mantis:8080/verify.php?id')]")));
            		String pathUrla = driver.findElement(By.xpath("//a[contains(.,'http://mantis:8080/verify.php')]")).getText();
            		System.out.println(pathUrla);
            		driver.close();
            		
            		// mantis thay đổi  mật khẩu
            		driver = temp == CHROME ?  new ChromeDriver() : new FirefoxDriver();
            		driver.get(pathUrla);
            		driver.findElement(By.id("realname")).sendKeys("1612241."+i);
            		driver.findElement(By.id("password")).sendKeys("0309hung");
            		driver.findElement(By.id("password-confirm")).sendKeys("0309hung");
            		driver.findElement(By.xpath("//button[@type='submit']")).click();		
            		driver.close();
            		
            		// mantis admin thay đổi quyền cho user
            		driver = temp == CHROME ?  new ChromeDriver() : new FirefoxDriver();
            		
            		driver.get("http://mantis:8080/login_page.php");
            		driver.findElement(By.id("username")).sendKeys("administrator");
            		driver.findElement(By.cssSelector(".width-40")).click();
            		driver.findElement(By.id("password")).sendKeys("root");
            		driver.findElement(By.cssSelector(".width-40")).click();
            		driver.findElement(By.cssSelector(".fa-gears")).click();
            		driver.findElement(By.xpath("//a[contains(@href, '/manage_user_page.php')]")).click();
            		driver.findElement(By.linkText("1612241."+i)).click();
            		driver.findElement(By.id("edit-access-level")).click();
            		Select dropCountry = new Select(driver.findElement(By.id("edit-access-level")));
            		if(i % 2 != 0) {
            			dropCountry.selectByValue("55");
            		}
            		else {
            			dropCountry.selectByValue("70");
            		}
            		driver.findElement(By.cssSelector(".widget-box:nth-child(1) .btn")).click();        		
            		System.out.print("Đăng kí tài khoản 1612241." + i + "@gmail.com thành công!!\n");
    			} catch (Exception e) {
    				System.out.print("Đăng kí tài khoản 1612241." + i + "@gmail.com thất bại!!\n");
    				// TODO: handle exception
    			} 
        		finally {
        			driver.close();
    			}
        	
        		i++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
		
			//mantis đăng kí
    		
    	
    		return true;
        	
        
    }

}
