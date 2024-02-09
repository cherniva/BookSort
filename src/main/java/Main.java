import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {

    private static final Logger log = LogManager.getLogger(Main.class.getName());

    public static void main(String[] args) {
        BookSort bookSort = null;
        try {
            bookSort = new BookSort(args);
        }
        catch(NumberFormatException e) {
            log.error("Wrong year value. Please try again");
            System.exit(1);
        }
        catch(ArrayIndexOutOfBoundsException e) {
            log.error("Please provide two arguments");
            System.exit(2);
        }


    }
}
