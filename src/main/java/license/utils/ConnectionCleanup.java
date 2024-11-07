package license.utils;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import com.mysql.cj.jdbc.AbandonedConnectionCleanupThread;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
//
// it is supposed to do db connection cleanup before shutdown
// it is not helping
//
@WebListener
public class ConnectionCleanup implements ServletContextListener {

    static Logger logger = LogManager.getLogger(ConnectionCleanup.class);
    public void contextInitialized(ServletContextEvent sce) {
    }

    public void contextDestroyed(ServletContextEvent sce) {
	Enumeration<Driver> drivers = DriverManager.getDrivers();
	Driver d = null;
	while (drivers.hasMoreElements()) {
	    try {
		d = drivers.nextElement();
		DriverManager.deregisterDriver(d);
		logger.warn(String.format("Driver %s deregistered", d));
	    }
	    catch (SQLException ex) {
		logger.warn(String.format("Error deregistering driver %s", d), ex);
	    }
	}
	AbandonedConnectionCleanupThread.checkedShutdown();
    }
}
    
