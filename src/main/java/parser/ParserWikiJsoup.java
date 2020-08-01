package parser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class ParserWikiJsoup {

    public Document parseRandomDocument() {
        Document doc = null;
        try {
            doc = Jsoup.connect("https://ru.wikipedia.org/wiki/Служебная:Случайная_страница").get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return doc;
    }

    public Document parseConcreteDocument(String address) {
        Document doc = null;
        try {
            doc = Jsoup.connect("https://ru.wikipedia.org" + address).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return doc;
    }
}
