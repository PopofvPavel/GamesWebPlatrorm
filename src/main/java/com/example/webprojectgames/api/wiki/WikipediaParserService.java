package com.example.webprojectgames.api.wiki;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class WikipediaParserService {

    final
    GameWikiConstants gameWikiConstants;

    public WikipediaParserService(GameWikiConstants gameWikiConstants) {
        this.gameWikiConstants = gameWikiConstants;
    }

    public List<String> extractGameTitles(String year) {
        GameWikiConstants.WikiGamePage gamePage = gameWikiConstants.getGamePage(year);
        return extractGameTitles(gamePage.getUrl(), gamePage.getStartIndex(), gamePage.getEndIndex());
    }

    public List<String> extractGameTitles(String url, int startIndex, int endIndex) {
        List<String> gameTitles = new ArrayList<>();
        try {
            // Загрузка HTML страницы Википедии
            Document doc = Jsoup.connect(url).get();

            // Проход по всем таблицам в указанном диапазоне индексов
            for (int i = startIndex; i <= endIndex; i++) {
                Element table = doc.select("table.wikitable").get(i);
                System.out.println("Table " + i + ": " + table);

                // Поиск всех строк в текущей таблице
                Elements rows = table.select("tr");

                // Пропускаем заголовок таблицы
                for (int j = 1; j < rows.size(); j++) {
                    Element row = rows.get(j);
                    Elements cols = row.select("td");

                    // Получаем название игры из второго столбца
                    if (cols.size() > 1) {
                        String title = cols.get(1).text();
                        gameTitles.add(title);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return gameTitles;
    }

}
