package com.example.demo.workFiles;

import com.example.demo.model.Page;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

public class PageWrapper {

    private static String urlGlobal;
    private static CopyOnWriteArraySet<String> allUrl = new CopyOnWriteArraySet<String>();


    static public Set<Page> getChildrenPage(String url) {
        url = fullUrl(url);

        Set<Page> links = new HashSet<>();

        Connection connection = Jsoup.connect(url)
                .userAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:25.0) Gecko/20100101 Firefox/25.0")
                .referrer("http://www.google.com")
                .ignoreHttpErrors(true);

        Connection.Response response;
        Document doc;

        try {

            response = connection.execute();
            doc = connection.get();


        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Elements elements = doc.select("a");


        for (Element element : elements) {
            String newFullUrl = element.absUrl("href");
            String smaleUrl = element.attr("href");
            if (!smaleUrl.isBlank()) {

                if (checkingCorrectnessUrl(newFullUrl)) {

                    links.add(new Page(smaleUrl, doc.html(), response.statusCode()));
                }
            }
        }

        return links;
    }

    private static boolean checkingCorrectnessUrl(String newUrl) {
        if (newUrl.startsWith(urlGlobal)) {
            if (newUrl.endsWith("jpg") || newUrl.endsWith("#")||newUrl.endsWith("mp4") ) return false;
            if (!allUrl.contains(newUrl)) {
                allUrl.add(newUrl);
                return true;
            }
        }

        return false;
    }

    public static String fullUrl(String url) {
        if (urlGlobal == null) urlGlobal = url;
        return url.startsWith(urlGlobal) ? url : urlGlobal.concat(url);
    }


    public static Page firstPage(String url) {
        Connection connection = Jsoup.connect(url)
                .userAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:25.0) Gecko/20100101 Firefox/25.0")
                .referrer("http://www.google.com")
                .ignoreHttpErrors(true);

        Connection.Response response;
        Document doc;
        try {

            response = connection
                    .execute();
            doc = connection.get();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        allUrl.add(url);
        return new Page(url, doc.html(), response.statusCode());
    }
}
