
package com.cotopaxi.Cotopaxi;

public class Challenge
{
	private String description;
	private int points;

	private String hashTag;

	private boolean completed;

	public String getDescription()
	{
		return description;
	}

	public String getHashTag()
	{
		return hashTag;
	}

	public int getPoints()
	{
		return points;
	}

	public boolean isCompleted()
	{
		return completed;
	}

	public void setCompleted(boolean completed)
	{
		this.completed = completed;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public void setHashTag(String hashTag)
	{
		this.hashTag = hashTag;
	}

	public void setPoints(int points)
	{
		this.points = points;
	}

	@Override
	public String toString()
	{
		return "Challenge [description=" + description + ", points=" + points + ", hashTag=" + hashTag + ", completed=" + completed + "]";
	}
}
