package com.sandbox.crawl;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

public class Crawl {
    public static void main(String[] args) throws Exception {
        System.out.println("mew");

        URL oracle = new URL("https://ww2.heidelberg.de/wwwopac/index.asp?detsuche_Schlagwort=filmklassiker++dvd");
        BufferedReader in = new BufferedReader(
                new InputStreamReader(oracle.openStream()));

        String inputLine;
        while ((inputLine = in.readLine()) != null)
            System.out.println(inputLine);
        in.close();
    }
}
