package com.example.webprojectgames.api.wiki;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class GameWikiConstants {
    private final Map<String, WikiGamePage> urlIndexMap;

    public GameWikiConstants() {
        urlIndexMap = new HashMap<>();
        urlIndexMap.put("2023", new WikiGamePage("https://ru.wikipedia.org/wiki/2023_%D0%B3%D0%BE%D0%B4_%D0%B2_" +
                "%D0%BA%D0%BE%D0%BC%D0%BF%D1%8C%D1%8E%D1%82%D0%B5%D1%80%D0%BD%D1%8B%D1%85_%D0%B8%D0%B3%D1%80%D0%B0%D1%85"
                , 1, 4));
        urlIndexMap.put("2024", new WikiGamePage("https://ru.wikipedia.org/wiki/2024_%D0%B3%D0%BE%D0%B4_%D0%B2_%D0%" +
                "BA%D0%BE%D0%BC%D0%BF%D1%8C%D1%8E%D1%82%D0%B5%D1%80%D0%BD%D1%8B%D1%85_%D0%B8%D0%B3%D1%80%D0%B0%D1%85"
                , 1, 3));
        urlIndexMap.put("2022", new WikiGamePage("https://ru.wikipedia.org/wiki/2022_%D0%B3%D0%BE%D0%B4_%D0%B2_%D0%" +
                "BA%D0%BE%D0%BC%D0%BF%D1%8C%D1%8E%D1%82%D0%B5%D1%80%D0%BD%D1%8B%D1%85_%D0%B8%D0%B3%D1%80%D0%B0%D1%85"
                , 1, 4));
        urlIndexMap.put("2021", new WikiGamePage("https://ru.wikipedia.org/wiki/2021_%D0%B3%D0%BE%D0%B4_%D0%B2_%D0%" +
                "BA%D0%BE%D0%BC%D0%BF%D1%8C%D1%8E%D1%82%D0%B5%D1%80%D0%BD%D1%8B%D1%85_%D0%B8%D0%B3%D1%80%D0%B0%D1%85"
                , 1, 4));
        urlIndexMap.put("2020", new WikiGamePage("https://ru.wikipedia.org/wiki/2020_%D0%B3%D0%BE%D0%B4_%D0%B2_%D0%" +
                "BA%D0%BE%D0%BC%D0%BF%D1%8C%D1%8E%D1%82%D0%B5%D1%80%D0%BD%D1%8B%D1%85_%D0%B8%D0%B3%D1%80%D0%B0%D1%85"
                , 1, 4));
        urlIndexMap.put("2019", new WikiGamePage("https://ru.wikipedia.org/wiki/2019_%D0%B3%D0%BE%D0%B4_%D0%B2_%D0%" +
                "BA%D0%BE%D0%BC%D0%BF%D1%8C%D1%8E%D1%82%D0%B5%D1%80%D0%BD%D1%8B%D1%85_%D0%B8%D0%B3%D1%80%D0%B0%D1%85"
                , 1, 4));
        urlIndexMap.put("2018", new WikiGamePage("https://ru.wikipedia.org/wiki/2018_%D0%B3%D0%BE%D0%B4_%D0%B2_%D0%" +
                "BA%D0%BE%D0%BC%D0%BF%D1%8C%D1%8E%D1%82%D0%B5%D1%80%D0%BD%D1%8B%D1%85_%D0%B8%D0%B3%D1%80%D0%B0%D1%85"
                , 1, 4));
        urlIndexMap.put("2017", new WikiGamePage("https://ru.wikipedia.org/wiki/2017_%D0%B3%D0%BE%D0%B4_%D0%B2_%D0%" +
                "BA%D0%BE%D0%BC%D0%BF%D1%8C%D1%8E%D1%82%D0%B5%D1%80%D0%BD%D1%8B%D1%85_%D0%B8%D0%B3%D1%80%D0%B0%D1%85"
                , 1, 4));
        urlIndexMap.put("2016", new WikiGamePage("https://ru.wikipedia.org/wiki/2016_%D0%B3%D0%BE%D0%B4_%D0%B2_%D0%" +
                "BA%D0%BE%D0%BC%D0%BF%D1%8C%D1%8E%D1%82%D0%B5%D1%80%D0%BD%D1%8B%D1%85_%D0%B8%D0%B3%D1%80%D0%B0%D1%85"
                , 1, 4));
        urlIndexMap.put("2015", new WikiGamePage("https://ru.wikipedia.org/wiki/2015_%D0%B3%D0%BE%D0%B4_%D0%B2_%D0%" +
                "BA%D0%BE%D0%BC%D0%BF%D1%8C%D1%8E%D1%82%D0%B5%D1%80%D0%BD%D1%8B%D1%85_%D0%B8%D0%B3%D1%80%D0%B0%D1%85"
                , 1, 4));
        urlIndexMap.put("2014", new WikiGamePage("https://ru.wikipedia.org/wiki/2014_%D0%B3%D0%BE%D0%B4_%D0%B2_%D0%" +
                "BA%D0%BE%D0%BC%D0%BF%D1%8C%D1%8E%D1%82%D0%B5%D1%80%D0%BD%D1%8B%D1%85_%D0%B8%D0%B3%D1%80%D0%B0%D1%85"
                , 1, 4));
        urlIndexMap.put("2013", new WikiGamePage("https://ru.wikipedia.org/wiki/2013_%D0%B3%D0%BE%D0%B4_%D0%B2_%D0%" +
                "BA%D0%BE%D0%BC%D0%BF%D1%8C%D1%8E%D1%82%D0%B5%D1%80%D0%BD%D1%8B%D1%85_%D0%B8%D0%B3%D1%80%D0%B0%D1%85"
                , 1, 4));
        urlIndexMap.put("2012", new WikiGamePage("https://ru.wikipedia.org/wiki/2012_%D0%B3%D0%BE%D0%B4_%D0%B2_%D0%" +
                "BA%D0%BE%D0%BC%D0%BF%D1%8C%D1%8E%D1%82%D0%B5%D1%80%D0%BD%D1%8B%D1%85_%D0%B8%D0%B3%D1%80%D0%B0%D1%85"
                , 1, 4));
        urlIndexMap.put("2011", new WikiGamePage("https://ru.wikipedia.org/wiki/2011_%D0%B3%D0%BE%D0%B4_%D0%B2_%D0%" +
                "BA%D0%BE%D0%BC%D0%BF%D1%8C%D1%8E%D1%82%D0%B5%D1%80%D0%BD%D1%8B%D1%85_%D0%B8%D0%B3%D1%80%D0%B0%D1%85"
                , 1, 1));
        urlIndexMap.put("2010", new WikiGamePage("https://ru.wikipedia.org/wiki/2010_%D0%B3%D0%BE%D0%B4_%D0%B2_%D0%" +
                "BA%D0%BE%D0%BC%D0%BF%D1%8C%D1%8E%D1%82%D0%B5%D1%80%D0%BD%D1%8B%D1%85_%D0%B8%D0%B3%D1%80%D0%B0%D1%85"
                , 1, 1));
        urlIndexMap.put("2009", new WikiGamePage("https://ru.wikipedia.org/wiki/2009_%D0%B3%D0%BE%D0%B4_%D0%B2_%D0%" +
                "BA%D0%BE%D0%BC%D0%BF%D1%8C%D1%8E%D1%82%D0%B5%D1%80%D0%BD%D1%8B%D1%85_%D0%B8%D0%B3%D1%80%D0%B0%D1%85"
                , 1, 1));
        urlIndexMap.put("2008", new WikiGamePage("https://ru.wikipedia.org/wiki/2008_%D0%B3%D0%BE%D0%B4_%D0%B2_%D0%" +
                "BA%D0%BE%D0%BC%D0%BF%D1%8C%D1%8E%D1%82%D0%B5%D1%80%D0%BD%D1%8B%D1%85_%D0%B8%D0%B3%D1%80%D0%B0%D1%85"
                , 1, 1));
        urlIndexMap.put("2007", new WikiGamePage("https://ru.wikipedia.org/wiki/2007_%D0%B3%D0%BE%D0%B4_%D0%B2_%D0%" +
                "BA%D0%BE%D0%BC%D0%BF%D1%8C%D1%8E%D1%82%D0%B5%D1%80%D0%BD%D1%8B%D1%85_%D0%B8%D0%B3%D1%80%D0%B0%D1%85"
                , 1, 1));
        urlIndexMap.put("2006", new WikiGamePage("https://ru.wikipedia.org/wiki/2006_%D0%B3%D0%BE%D0%B4_%D0%B2_%D0%" +
                "BA%D0%BE%D0%BC%D0%BF%D1%8C%D1%8E%D1%82%D0%B5%D1%80%D0%BD%D1%8B%D1%85_%D0%B8%D0%B3%D1%80%D0%B0%D1%85"
                , 1, 1));
        urlIndexMap.put("2005", new WikiGamePage("https://ru.wikipedia.org/wiki/2005_%D0%B3%D0%BE%D0%B4_%D0%B2_%D0%" +
                "BA%D0%BE%D0%BC%D0%BF%D1%8C%D1%8E%D1%82%D0%B5%D1%80%D0%BD%D1%8B%D1%85_%D0%B8%D0%B3%D1%80%D0%B0%D1%85"
                , 1, 1));
        urlIndexMap.put("2004", new WikiGamePage("https://ru.wikipedia.org/wiki/2004_%D0%B3%D0%BE%D0%B4_%D0%B2_%D0%" +
                "BA%D0%BE%D0%BC%D0%BF%D1%8C%D1%8E%D1%82%D0%B5%D1%80%D0%BD%D1%8B%D1%85_%D0%B8%D0%B3%D1%80%D0%B0%D1%85"
                , 1, 1));
        urlIndexMap.put("2003", new WikiGamePage("https://ru.wikipedia.org/wiki/2003_%D0%B3%D0%BE%D0%B4_%D0%B2_%D0%" +
                "BA%D0%BE%D0%BC%D0%BF%D1%8C%D1%8E%D1%82%D0%B5%D1%80%D0%BD%D1%8B%D1%85_%D0%B8%D0%B3%D1%80%D0%B0%D1%85"
                , 1, 1));
        urlIndexMap.put("2002", new WikiGamePage("https://ru.wikipedia.org/wiki/2002_%D0%B3%D0%BE%D0%B4_%D0%B2_%D0%" +
                "BA%D0%BE%D0%BC%D0%BF%D1%8C%D1%8E%D1%82%D0%B5%D1%80%D0%BD%D1%8B%D1%85_%D0%B8%D0%B3%D1%80%D0%B0%D1%85"
                , 1, 1));
        urlIndexMap.put("2001", new WikiGamePage("https://ru.wikipedia.org/wiki/2001_%D0%B3%D0%BE%D0%B4_%D0%B2_%D0%" +
                "BA%D0%BE%D0%BC%D0%BF%D1%8C%D1%8E%D1%82%D0%B5%D1%80%D0%BD%D1%8B%D1%85_%D0%B8%D0%B3%D1%80%D0%B0%D1%85"
                , 1, 1));
        urlIndexMap.put("2000", new WikiGamePage("https://ru.wikipedia.org/wiki/2000_%D0%B3%D0%BE%D0%B4_%D0%B2_%D0%" +
                "BA%D0%BE%D0%BC%D0%BF%D1%8C%D1%8E%D1%82%D0%B5%D1%80%D0%BD%D1%8B%D1%85_%D0%B8%D0%B3%D1%80%D0%B0%D1%85"
                , 1, 1));
        urlIndexMap.put("1999", new WikiGamePage("https://ru.wikipedia.org/wiki/1999_%D0%B3%D0%BE%D0%B4_%D0%B2_%D0%" +
                "BA%D0%BE%D0%BC%D0%BF%D1%8C%D1%8E%D1%82%D0%B5%D1%80%D0%BD%D1%8B%D1%85_%D0%B8%D0%B3%D1%80%D0%B0%D1%85"
                , 1, 1));
        urlIndexMap.put("1998", new WikiGamePage("https://ru.wikipedia.org/wiki/1998_%D0%B3%D0%BE%D0%B4_%D0%B2_%D0%" +
                "BA%D0%BE%D0%BC%D0%BF%D1%8C%D1%8E%D1%82%D0%B5%D1%80%D0%BD%D1%8B%D1%85_%D0%B8%D0%B3%D1%80%D0%B0%D1%85"
                , 1, 1));
    }

    // Внутренний класс для хранения страниц
    public class WikiGamePage {
        private final String url;
        private final int startIndex;
        private final int endIndex;

        public WikiGamePage(String url, int startIndex, int endIndex) {
            this.url = url;
            this.startIndex = startIndex;
            this.endIndex = endIndex;
        }

        public String getUrl() {
            return url;
        }

        public int getStartIndex() {
            return startIndex;
        }

        public int getEndIndex() {
            return endIndex;
        }
    }

    public WikiGamePage getGamePage(String year) {
        return this.urlIndexMap.getOrDefault(year,null);
    }
}
