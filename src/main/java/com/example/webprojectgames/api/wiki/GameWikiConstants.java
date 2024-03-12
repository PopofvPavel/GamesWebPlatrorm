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
