package com.broker.InvestmentApplication.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import com.broker.InvestmentApplication.model.NewsItem;
import com.broker.InvestmentApplication.model.QuotesBlock;
import com.broker.InvestmentApplication.model.QuotesBlockLine;

@Service
public class NewsService
{
	public List<NewsItem> fetchNews() throws IOException
	{
		String         url          = "https://investfuture.ru/news";
		Document       document     = Jsoup.connect(url).get();

		List<NewsItem> newsList     = new ArrayList<>();
		Element       newsElements = document.getElementById("page_1");
		
		for (Element newsElement : newsElements.children())
		{			
			// получим данные по картинке и ссылку на статью
			Element divImage = newsElement.select("div .col-sm-5").first();
			Element aImage = divImage.select("a").first();
			String articleLink = "https://investfuture.ru" + aImage.attr("href");
			String imageLink = aImage.select("img").first().attr("src");
			
			// получим краткое описание статьи и дату
			Element divInfo = newsElement.select("div .col-sm-7").first();
			String articleTitle = divInfo.getElementsByTag("h3").first().text();
			Element spanInfo = divInfo.selectFirst(".info");
			String dateText = spanInfo.selectFirst(".published").text();
			String articleShortDescription = divInfo.selectFirst(".text").text();
			
			NewsItem item = new NewsItem(dateText, articleShortDescription, articleTitle, articleLink, imageLink);
			newsList.add(item);
		}

		return newsList;
	}
	
	public List<QuotesBlock> fetchQuoteBlocks() throws IOException
	{
		String         url          = "https://investfuture.ru/quote";
		Document       document     = Jsoup.connect(url).get();

		List<QuotesBlock> quoteBlocks     = new ArrayList<>();
		
		// получим заголовок по курсам валют
		Element       quoteElement = document.select(".theiaStickySidebar").first().firstElementChild();
		Element quoteTitleElement = quoteElement.select(".h2").first();
		Element aTag = quoteTitleElement.select("a").first();
		String title = aTag.text();
		
		// получим данные по курсам валют
		QuotesBlock quotesBlock = new QuotesBlock();
		List<QuotesBlockLine> lines = new ArrayList<>();
		Elements tableRows = quoteElement.select("tbody").first().children();
		
		for (Element tableRow : tableRows)
		{
			QuotesBlockLine line = new QuotesBlockLine();
			
			line.setQuoteIndex(tableRow.child(0).text());
			line.setQuoteName(tableRow.child(1).select("a").text());
			line.setQuoteValue(tableRow.child(2).text());
			
			lines.add(line);
		}
		
		quotesBlock.setLines(lines);
		quotesBlock.setQuoteTitle(title);
		
		// проставим заголовки таблицы
		LinkedList<String> tableHeaders = new LinkedList<>();
		tableHeaders.add("Индекс валюты");
		tableHeaders.add("Название валюты");
		tableHeaders.add("Курс на текущий день");
		quotesBlock.setHeaders(tableHeaders);
		quoteBlocks.add(quotesBlock);
		
		//конец парсинга блока курс валют-----------------------
		
		// парсим остальные блоки котировок
		Elements quoteElements = document.select("div [class*=col-sm-6]");
		
		for (Element element : quoteElements)
		{
			quotesBlock = new QuotesBlock();
			tableHeaders = new LinkedList<>();
			
			Element spanNewsLayout = element.selectFirst(".news-layout");
			
			// пропускаем элементы, которые не содержат табличную часть
			if (spanNewsLayout == null || !spanNewsLayout.child(1).is("table"))
			{
				continue;
			}
			
			title = spanNewsLayout.selectFirst(".header-h1").selectFirst("a").text();
			
			quotesBlock.setQuoteTitle(title);
			
			// теперь табличная часть
			lines = new ArrayList<>();
			
			for (Element tableRow : spanNewsLayout.select("table"))
			{
				// заполним заголовоки таблицы
				Elements tHead = tableRow.select("thead");
				Element tr = tHead.select("tr").first();
				
				QuotesBlockLine line = new QuotesBlockLine();
				
				tableHeaders.add(tr.child(0).text());
				tableHeaders.add(tr.child(1).text());
				tableHeaders.add(tr.child(2).text());
				
				quotesBlock.setHeaders(tableHeaders);
				
				// заполним теперь значения табличные
				Elements tBody = tableRow.select("tbody").first().select("tr");
				
				for (Element trElement : tBody)
				{
					line = new QuotesBlockLine();
					
					if (trElement.childrenSize() > 3)
					{
						line.setQuoteIndex(trElement.child(1).select("a").text());
						line.setQuoteName(trElement.child(2).text());
						line.setQuoteValue(trElement.child(3).text());
					}
					else
					{
						line.setQuoteIndex(trElement.child(0).select("a").text());
						line.setQuoteName(trElement.child(1).text());
						line.setQuoteValue(trElement.child(2).text());
					}
					
					lines.add(line);
				}
			}
			
			quotesBlock.setLines(lines);
			
			quoteBlocks.add(quotesBlock);
		}
		
		return quoteBlocks;
	}
	
}
