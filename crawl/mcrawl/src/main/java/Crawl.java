//package com.sandbox.crawl;
import crawl.Config;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.*;
import java.util.List;

public class Crawl {
    public static void main(String[] args) throws Exception {
        System.out.println("mew");

        CookieHandler.setDefault(new CookieManager(null, CookiePolicy.ACCEPT_ALL));

        URL url= new URL(Config.urlAddress);
        URLConnection conn = url.openConnection();
        conn.addRequestProperty(Config.requestPropertyName, Config.requestPropertyValue);
        BufferedReader in = new BufferedReader(
                new InputStreamReader(conn.getInputStream()));


        String inputLine;
        StringBuilder sb = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
//            System.out.println(inputLine);
            sb.append(inputLine);

        }

        in.close();
        Document doc = Jsoup.parseBodyFragment(sb.toString());
        List<String> titles = doc.select("[title^=Details]").eachText();
        System.out.println(titles);


    }
}
