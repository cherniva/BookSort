import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BookSort {
    private final Logger log = LogManager.getLogger(BookSort.class.getName());
    private final String path;
    private final Integer year;

    public BookSort(String path, Integer year) {
        this.path = path;
        this.year = year;
    }

    public BookSort(String path, String year) {
        this(path, Integer.valueOf(year));
    }

    public BookSort(String[] args) {
        this(args[0], args[1]);
    }


}
