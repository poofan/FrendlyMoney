package com.broker.InvestmentApplication.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.broker.InvestmentApplication.model.QuotesBlock;
import com.broker.InvestmentApplication.service.NewsService;

@Controller
public class HomeController
{
	@Autowired
	private NewsService newsService;

	@GetMapping("/")
	public String index(Model model)
	{
		try
		{
			List<QuotesBlock> quotesBlocks = newsService.fetchQuoteBlocks();
		    model.addAttribute("quotesBlocks", quotesBlocks);
			model.addAttribute("newsList", newsService.fetchNews());
		}
		catch (IOException e)
		{
			model.addAttribute("newsError", "Не удалось загрузить новости");
		}
		return "index";
	}
}
