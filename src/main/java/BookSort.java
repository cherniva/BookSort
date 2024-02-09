import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

    private Document getParsedDocument(String path) {
        Document document = null;
        try {
            File xmlFile = new File(path);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            document = dBuilder.parse(xmlFile);
        }
        catch (ParserConfigurationException e) {}
        catch (IOException e) {}
        catch (SAXException e) {}

        return document;
    }

    private List<Book> getBookList(Document document) {
        List<Book> bookList = new ArrayList<>();
        NodeList nList = document.getElementsByTagName("Kniha");

        for(int i = 0; i < nList.getLength(); i++) {
            Node node = nList.item(i);
            Element book = (Element) node;
            Element author = (Element) book.getElementsByTagName("Autor").item(0);

            String ISBN = book.getAttribute("ISBN");
            String releaseYear = book.getAttribute("Vydano");
            String bookName = book.getElementsByTagName("Nazev").item(0).getTextContent();
            String authorFirstName = author.getAttribute("Jmeno");
            String authorLastName = author.getAttribute("Prijmeni");

            //TODO: NumberFormatException
            bookList.add(new Book(ISBN, Integer.valueOf(releaseYear), bookName, authorFirstName, authorLastName));
        }

        return bookList;
    }

    public void sortBooks() {
        Document document = getParsedDocument(this.path);
        document.getDocumentElement().normalize();
        List<Book> bookList = getBookList(document);
        List<Book> booksBefore = bookList.stream()
                .filter(book -> book.releaseYear() < this.year)
                .toList();
        List<Book> booksAfter = bookList.stream()
                .filter(book -> book.releaseYear() >= this.year)
                .toList();

        System.out.println(booksBefore);
        System.out.println(booksAfter);
    }
}
