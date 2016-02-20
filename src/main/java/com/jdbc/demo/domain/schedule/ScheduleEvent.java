package com.jdbc.demo.domain.schedule;

import java.util.Date;

/**
 * Created by Mateusz on 20-Feb-16.
 */
public class ScheduleEvent {
	/**
	 * Represents Event_Object used by FullCalendar - JS event
	 * calendar(http://fullcalendar.io). Refer to Event_Object
	 * documentation(http://fullcalendar.io/docs/event_data/Event_Object/) for
	 * description of properties.
	 */
	private long id;
	private String title;

	// Default value: true
	private Boolean allDay = true;
	private Date start;
	private Date end;
	private String url;
	private String className;

	private String color;
	private String backgroundColor;
	private String borderColor;
	private String textColor;

	public ScheduleEvent(String title, Date start) {
		this.title = title;
		this.start = start;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		ScheduleEvent that = (ScheduleEvent) o;

		if (id != that.id)
			return false;
		if (!title.equals(that.title))
			return false;
		if (allDay != null ? !allDay.equals(that.allDay) : that.allDay != null)
			return false;
		if (!start.equals(that.start))
			return false;
		if (end != null ? !end.equals(that.end) : that.end != null)
			return false;
		if (url != null ? !url.equals(that.url) : that.url != null)
			return false;
		if (className != null ? !className.equals(that.className) : that.className != null)
			return false;
		if (color != null ? !color.equals(that.color) : that.color != null)
			return false;
		if (backgroundColor != null ? !backgroundColor.equals(that.backgroundColor) : that.backgroundColor != null)
			return false;
		if (borderColor != null ? !borderColor.equals(that.borderColor) : that.borderColor != null)
			return false;
		return !(textColor != null ? !textColor.equals(that.textColor) : that.textColor != null);

	}

	@Override
	public int hashCode() {
		int result = (int) (id ^ (id >>> 32));
		result = 31 * result + title.hashCode();
		result = 31 * result + (allDay != null ? allDay.hashCode() : 0);
		result = 31 * result + start.hashCode();
		result = 31 * result + (end != null ? end.hashCode() : 0);
		result = 31 * result + (url != null ? url.hashCode() : 0);
		result = 31 * result + (className != null ? className.hashCode() : 0);
		result = 31 * result + (color != null ? color.hashCode() : 0);
		result = 31 * result + (backgroundColor != null ? backgroundColor.hashCode() : 0);
		result = 31 * result + (borderColor != null ? borderColor.hashCode() : 0);
		result = 31 * result + (textColor != null ? textColor.hashCode() : 0);
		return result;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Boolean getAllDay() {
		return allDay;
	}

	public void setAllDay(Boolean allDay) {
		this.allDay = allDay;
	}

	public Date getStart() {
		return start;
	}

	public void setStart(Date start) {
		this.start = start;
	}

	public Date getEnd() {
		return end;
	}

	public void setEnd(Date end) {
		this.end = end;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getBackgroundColor() {
		return backgroundColor;
	}

	public void setBackgroundColor(String backgroundColor) {
		this.backgroundColor = backgroundColor;
	}

	public String getBorderColor() {
		return borderColor;
	}

	public void setBorderColor(String borderColor) {
		this.borderColor = borderColor;
	}

	public String getTextColor() {
		return textColor;
	}

	public void setTextColor(String textColor) {
		this.textColor = textColor;
	}
}
