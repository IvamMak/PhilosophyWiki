import linkSearcher.WikiLinkSearcher;
import org.jsoup.nodes.Document;
import parser.ParserWikiJsoup;
import service.ConsoleHelper;
import service.FileWorker;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class Solution {
    public static void main(String[] args) {
        Map<String, Integer> map = new HashMap<>();
        ParserWikiJsoup parser = new ParserWikiJsoup();
        WikiLinkSearcher wikiLinkSearcher = new WikiLinkSearcher(map, parser);

        for (int i = 0; i < 10; i++) {
            Document doc = parser.parseRandomDocument();
            wikiLinkSearcher.findConcreteLinkInPage(doc, "Философия");
            System.out.println(i);
        }

        ConsoleHelper.printResult(map, 1);
        ConsoleHelper.printResult(map, 3);
        ConsoleHelper.printResult(map, 10);
        ConsoleHelper.printMaxResult(map);
        FileWorker.writeResultToFile(map);
    }
}
