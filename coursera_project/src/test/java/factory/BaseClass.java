package factory;

import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.time.Duration;
import java.util.Properties;

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

    private static ThreadLocal<WebDriver> tlDriver = new ThreadLocal<>();
    private static ThreadLocal<Properties> tlProps = new ThreadLocal<>();
    private static ThreadLocal<Logger> tlLogger = new ThreadLocal<>();

    public static WebDriver initilizeBrowser() throws IOException {
        Properties p = getProperties();
        String executionEnv = p.getProperty("execution_env");
        String browser = p.getProperty("browser").toLowerCase();
        String os = p.getProperty("os").toLowerCase();

        ChromeOptions options = new ChromeOptions();
        options.setPageLoadStrategy(PageLoadStrategy.EAGER);
        options.addArguments("--disable-notifications");

        WebDriver driverInstance = null;

        if (executionEnv.equalsIgnoreCase("remote")) {
            DesiredCapabilities capabilities = new DesiredCapabilities();
            switch (os) {
                case "windows" -> capabilities.setPlatform(Platform.WINDOWS);
                case "mac"     -> capabilities.setPlatform(Platform.MAC);
                case "linux"   -> capabilities.setPlatform(Platform.LINUX);
                default -> System.out.println("No matching OS");
            }
            switch (browser) {
                case "chrome" -> capabilities.setBrowserName("chrome");
                case "edge"   -> capabilities.setBrowserName("MicrosoftEdge");
                case "firefox"-> capabilities.setBrowserName("firefox");
                default -> System.out.println("No matching browser");
            }
            capabilities.setCapability(ChromeOptions.CAPABILITY, options);
            driverInstance = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), capabilities);
        } else if (executionEnv.equalsIgnoreCase("local")) {
            switch (browser) {
                case "chrome" -> driverInstance = new ChromeDriver(options);
                case "edge"   -> driverInstance = new EdgeDriver();
                case "firefox"-> driverInstance = new FirefoxDriver();
                default -> System.out.println("No matching browser");
            }
        }

        if (driverInstance != null) {
            driverInstance.manage().deleteAllCookies();
            driverInstance.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
            driverInstance.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
            driverInstance.manage().window().maximize();
            tlDriver.set(driverInstance);
        }

        return driverInstance;
    }

    public static WebDriver getDriver() {
        return tlDriver.get();
    }

    public static void quitDriver() {
        WebDriver d = tlDriver.get();
        if (d != null) {
            d.quit();
            tlDriver.remove();
        }
    }

    public static Properties getProperties() throws IOException {
        Properties p = tlProps.get();
        if (p == null) {
            FileReader file = new FileReader(System.getProperty("user.dir")
                    + "/src/test/resources/config.properties");
            p = new Properties();
            p.load(file);
            tlProps.set(p);
        }
        return p;
    }

    public static Logger getLogger() {
        Logger log = tlLogger.get();
        if (log == null) {
            log = LogManager.getLogger();
            tlLogger.set(log);
        }
        return log;
    }
}