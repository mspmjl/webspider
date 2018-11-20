package newscrawler;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import java.util.Set;


/**
 * Created by 缪缪同学 on 2018/8/26.
 */
public class WeiboCN {
    public static String getSinaCookie(String username, String password) throws Exception {
        HtmlUnitDriver driver = new HtmlUnitDriver(BrowserVersion.INTERNET_EXPLORER);

        driver.setJavascriptEnabled(true);
        driver.get("https://www.weibo.com/login.php/");
        driver.executeScript("document.getElementById('loginWrapper').style.display = 'block'");
        WebElement mobile = driver.findElementByCssSelector("input#loginName");
        mobile.sendKeys(username);
        WebElement pass = driver.findElementByCssSelector("input#loginPassword");
        pass.sendKeys(password);
        WebElement submit = driver.findElementByCssSelector("a#loginAction");
        submit.click();
        String result = concatCookie(driver);
        System.out.println("Get Cookie: " + result);
        driver.close();

        if (result.contains("SUB")) {
            return result;
        } else {
            throw new Exception("weibo login failed");
        }
    }

    public static String concatCookie(HtmlUnitDriver driver) {
//        driver.manage().getCookies()
        Set<Cookie> cookieSet = driver.manage().getCookies();
        StringBuilder sb = new StringBuilder();
        for (Cookie cookie : cookieSet) {
            sb.append(cookie.getName() + "=" + cookie.getValue() + ";");
        }
        String result = sb.toString();
        return result;
    }

}
