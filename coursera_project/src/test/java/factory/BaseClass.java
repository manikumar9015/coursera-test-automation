package factory;

import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.time.Duration;
import java.util.Properties;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

public class BaseClass {

    static ChromeOptions options;
    static WebDriver driver;
    static Properties p;
    static Logger logger;

    public static WebDriver initilizeBrowser() throws IOException {
        p = getProperties();
        String executionEnv = p.getProperty("execution_env");
        String browser = p.getProperty("browser").toLowerCase();
        String os = p.getProperty("os").toLowerCase();

        // Initialize options first so they can be used for both local and remote
        options = new ChromeOptions();
        options.setPageLoadStrategy(PageLoadStrategy.EAGER);
        options.addArguments("--disable-notifications");

        if (executionEnv.equalsIgnoreCase("remote")) {
            DesiredCapabilities capabilities = new DesiredCapabilities();

            // OS Switch
            switch (os) {
                case "windows" -> capabilities.setPlatform(Platform.WINDOWS);
                case "mac"     -> capabilities.setPlatform(Platform.MAC);
                case "linux"   -> capabilities.setPlatform(Platform.LINUX);
                default -> {
                    System.out.println("No matching OS");
                    return null;
                }
            }

            // Browser Switch - Merging options with capabilities
            switch (browser) {
                case "chrome" -> capabilities.setBrowserName("chrome");
                case "edge"   -> capabilities.setBrowserName("MicrosoftEdge");
                case "firefox"-> capabilities.setBrowserName("firefox");
                default -> {
                    System.out.println("No matching browser");
                    return null;
                }
            }
            // Use options even in remote to keep behavior consistent
            capabilities.setCapability(ChromeOptions.CAPABILITY, options);
            driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), capabilities);

        } else if (executionEnv.equalsIgnoreCase("local")) {
            switch (browser) {
                case "chrome" -> driver = new ChromeDriver(options); // Uses the pre-configured options
                case "edge"   -> driver = new EdgeDriver();
                case "firefox"-> driver = new FirefoxDriver();
                default -> {
                    System.out.println("No matching browser");
                    driver = null;
                }
            }
        }

        if (driver != null) {
            driver.manage().deleteAllCookies();
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
            // 30 seconds is the "sweet spot" for Coursera
            driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
            driver.manage().window().maximize(); // Good practice to ensure elements aren't hidden
        }

        return driver;
    }

    public static WebDriver getDriver() {
        return driver;
    }

    public static Properties getProperties() throws IOException
    {
        FileReader file=new FileReader(System.getProperty("user.dir")+"\\src\\test\\resources\\config.properties");
        p=new Properties();
        p.load(file);
        return p;
    }

    public static Logger getLogger()
    {
        logger=LogManager.getLogger(); //Log4j
        return logger;
    }

}
