package linkSearcher;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import parser.ParserWikiJsoup;

import java.util.*;

public class WikiLinkSearcher {
    private Map<String, Integer> resultMap;
    private ParserWikiJsoup parser;

    public void findConcreteLinkInPage(Document doc, String nameOfFoundLink) {
        int countOfGenerations = 0;

        Elements aTagElements = getElementsByTagAFromDoc(doc);

        Set<String> oldLinks = new LinkedHashSet<>(findNewTenLinksOnPage(aTagElements));
        Set<String> newLinks = new LinkedHashSet<>();
        Queue<String> linksGeneration = new LinkedList<>(oldLinks);

        while (!linksGeneration.isEmpty()) {

            if (checkConcreteIdFromATagElements(aTagElements, nameOfFoundLink)) {
                if (countOfGenerations == 0) countOfGenerations++;
                finalizeSearch(doc, countOfGenerations);
                return;
            }

            doc = parser.parseConcreteDocument(linksGeneration.remove());
            aTagElements = getElementsByTagAFromDoc(doc);
            newLinks.addAll(findNewTenLinksOnPage(aTagElements));

            if (linksGeneration.isEmpty()) {
                updateNewLinksAndRemoveRepeats(oldLinks, newLinks);
                linksGeneration.addAll(newLinks);
                countOfGenerations++;
                newLinks.clear();
            }
        }
    }

    private void updateNewLinksAndRemoveRepeats(Set<String> oldLinks, Set<String> youngLinks) {
        youngLinks.removeAll(oldLinks);
        oldLinks.addAll(youngLinks);
    }

    private boolean checkConcreteIdFromATagElements(Elements aElements, String nameOfFoundLink) {
        Element philosophy = aElements.stream()
                .filter(element -> element.attr("title").equals(nameOfFoundLink))
                .findFirst().orElse(null);

        return philosophy != null;
    }

    private Set<String> findNewTenLinksOnPage(Elements aElements) {
        Set<String> tempSet = new LinkedHashSet<>();

        aElements.stream().filter(element -> element.hasAttr("title"))
                .filter(element -> element.attr("href").startsWith("/wiki"))
                .filter(element -> !element.attr("href").endsWith(".ogg"))
                .filter(element -> element.attr("href").startsWith("/wiki"))
                .filter(element -> !element.attr("href").endsWith(".oga"))
                .filter(element -> !element.hasAttr("class"))
                .filter(element -> !element.attr("title").startsWith("Википедия:"))
                .filter(element -> !element.attr("title").startsWith("Категория:"))
                .limit(10)
                .forEach(element -> tempSet.add(element.attr("href")));

        return tempSet;
    }

    private Elements getElementsByTagAFromDoc(Document doc) {
        Elements elementsByClass = doc.getElementsByClass("mw-parser-output");

        Elements pElements = new Elements();
        Elements aElements = new Elements();

        elementsByClass.stream()
                .map(element -> element.getElementsByTag("p"))
                .forEach(pElements::addAll);

        pElements.stream()
                .map(element -> element.getElementsByTag("a"))
                .forEach(aElements::addAll);

        return aElements;
    }

    private void finalizeSearch(Document doc, int countOfGenerations) {
        countOfGenerations++;
        resultMap.put(doc.title(), countOfGenerations);
    }

    public WikiLinkSearcher(Map<String, Integer> resultMap, ParserWikiJsoup parser) {
        this.resultMap = resultMap;
        this.parser = parser;
    }
}
