package com.broker.InvestmentApplication.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Блоки котировок
 */
public class QuotesBlock
{
	private String quoteTitle;
	private List<String> headers = new ArrayList<>();
	private List<QuotesBlockLine> lines = new ArrayList<>();
	
	public QuotesBlock()
	{
	}

	public QuotesBlock(String quoteTitle, List<QuotesBlockLine> lines, List<String> headers)
	{
		this.quoteTitle = quoteTitle;
		this.lines = lines;
		this.headers = headers;
	}
	
	public void addHeader(String header)
	{
		headers.add(header);
	}

	public List<String> getHeaders()
	{
		return headers;
	}

	public void setHeaders(List<String> headers)
	{
		this.headers = headers;
	}

	public String getQuoteTitle()
	{
		return quoteTitle;
	}

	public void setQuoteTitle(String quoteTitle)
	{
		this.quoteTitle = quoteTitle;
	}

	public List<QuotesBlockLine> getLines()
	{
		return lines;
	}

	public void setLines(List<QuotesBlockLine> lines)
	{
		this.lines = lines;
	}

}
