package com.drodriguezhdez.samples.quartzwithakka.domain;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class Event implements Serializable {

	private final String id;
	private final String payload;
	private final Date scheduledDate;

	public Event(String id, String payload, final Date scheduledDate) {
		super();
		this.id = id;
		this.payload = payload;
		this.scheduledDate = scheduledDate;
	}

	public String getId() {
		return id;
	}

	public String getPayload() {
		return payload;
	}
	
	public Date getScheduledTime() {
		return new Date(scheduledDate.getTime());
	}
	

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(id).append(payload).toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof Event)){
			return false;
		}
		
		final Event anotherObj = (Event) obj;
		return new EqualsBuilder().append(id, anotherObj.id).append(payload, anotherObj.payload).isEquals();
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
