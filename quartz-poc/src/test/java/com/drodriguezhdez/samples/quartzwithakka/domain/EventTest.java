package com.drodriguezhdez.samples.quartzwithakka.domain;

import com.drodriguezhdez.samples.quartzwithakka.BaseObjectTest;
import com.drodriguezhdez.samples.quartzwithakka.domain.Event;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Instant;
import java.util.Date;

public class EventTest extends BaseObjectTest<Event> {

	private static final String SAMPLE_ID = "sampleId";
	private static final String SAMPLE_PAYLOAD = "samplePayload";
	
	private static final String ANOTHER_SAMPLE_ID = "anotherSampleId";
	private static final String ANOTHER_SAMPLE_PAYLOAD = "anotherSamplePayload";

	@Override
	public void init() {
		this.obj = new Event(SAMPLE_ID, SAMPLE_PAYLOAD, Date.from(Instant.now()));
		this.equalObj = new Event(SAMPLE_ID, SAMPLE_PAYLOAD, Date.from(Instant.now()));
		this.anotherObj = new Event(ANOTHER_SAMPLE_ID, ANOTHER_SAMPLE_PAYLOAD, Date.from(Instant.now()));
	}

	@Override
	public void should_have_accessors_for_attributes() {
		assertThat(this.obj.getId()).isEqualTo(SAMPLE_ID);
		assertThat(this.obj.getPayload()).isEqualTo(SAMPLE_PAYLOAD); 
	}

}
