package com.broker.InvestmentApplication.model;

public class NewsItem
{
	private String time;
	private String section;
	private String title;
	private String link;
	private String imageUrl;

	public NewsItem()
	{
	}

	public NewsItem(String time, String section, String title, String link, String imageUrl)
	{
		this.time = time;
		this.section = section;
		this.title = title;
		this.link = link;
		this.imageUrl = imageUrl;
	}

	public String getImageUrl()
	{
		return imageUrl;
	}

	public void setImageUrl(String imageUrl)
	{
		this.imageUrl = imageUrl;
	}

	public String getTime()
	{
		return time;
	}

	public void setTime(String time)
	{
		this.time = time;
	}

	public String getSection()
	{
		return section;
	}

	public void setSection(String section)
	{
		this.section = section;
	}

	public String getTitle()
	{
		return title;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

	public String getLink()
	{
		return link;
	}

	public void setLink(String link)
	{
		this.link = link;
	}

}
