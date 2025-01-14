package com.broker.InvestmentApplication.model;

public class QuotesBlockLine
{
	private String quoteIndex;
	private String quoteName;
	private String quoteValue;

	public QuotesBlockLine()
	{

	}

	public QuotesBlockLine(String quoteIndex, String quoteName, String quoteValue)
	{
		this.quoteIndex = quoteIndex;
		this.quoteName = quoteName;
		this.quoteValue = quoteValue;
	}

	public String getQuoteIndex()
	{
		return quoteIndex;
	}

	public void setQuoteIndex(String quoteIndex)
	{
		this.quoteIndex = quoteIndex;
	}

	public String getQuoteName()
	{
		return quoteName;
	}

	public void setQuoteName(String quoteName)
	{
		this.quoteName = quoteName;
	}

	public String getQuoteValue()
	{
		return quoteValue;
	}

	public void setQuoteValue(String quoteValue)
	{
		this.quoteValue = quoteValue;
	}

}
