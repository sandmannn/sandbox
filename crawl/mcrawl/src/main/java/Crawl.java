//package com.sandbox.crawl;
import crawl.Config;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.*;
import java.util.List;

public class Crawl {

    static List<String> getCrudeContent() throws Exception {
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
        return titles
    }

    static void getImdbValues(String descr) {

    }

    public static void main(String[] args) throws Exception {
        System.out.println("mew");
        List<String> titles = getCrudeContent();
        System.out.println(titles);


//"... denn sie wissen nicht, was sie tun / Regie: Nicholas Ray. Darst.: James Dean ; Natalie Wood ; Sal Mineo ... (Focus Edition ; 19) - 1955, USA"
//   8 1/2 / Regie und Drehb.: Federico Fellini. Darst.: Marcello Mastroianni ; Claudia Cardinale ; Anouk Aim�e... (Arthaus Close-Up) - 1961, Italien
// Alfred Hitchcock Collector's Edition : Gute Reise! ; Landung auf Madagaskar ; Nummer 17 ; Sabotage ; Der Weltmeister / Regie: Alfred Hitchcock ... - 1927-1944, GB

    }
}
