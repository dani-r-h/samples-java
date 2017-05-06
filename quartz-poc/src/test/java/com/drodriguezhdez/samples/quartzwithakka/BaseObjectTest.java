package com.drodriguezhdez.samples.quartzwithakka;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public abstract class BaseObjectTest<T> {

	protected T obj;
	protected T equalObj;
	protected T anotherObj;

	@Before
	public abstract void init();

	@Test
	public void should_return_same_hash_code_given_same_object() {
		assertThat(this.obj.hashCode()).isEqualTo(this.obj.hashCode());
	}

	@Test
	public void should_return_same_hash_code_given_same_objects() {
		assertThat(this.obj.hashCode()).isEqualTo(this.equalObj.hashCode());
	}

	@Test
	public void should_return_different_hash_code_given_different_objects() {
		assertThat(this.obj.hashCode()).isNotEqualTo(this.anotherObj.hashCode());
	}

	@Test
	public void should_return_false_when_equals_with_different_instances() {
		assertFalse(this.obj.equals(new Object()));
	}

	@Test
	public void should_return_true_when_equals_with_same_object() {
		assertTrue(this.obj.equals(this.obj));
	}

	@Test
	public void should_return_true_when_equals_with_same_objects() {
		assertTrue(this.obj.equals(this.equalObj));
	}

	@Test
	public void should_return_false_when_equals_with_different_objects() {
		assertFalse(this.obj.equals(this.anotherObj));
	}

	@Test
	public void should_return_false_when_equals_with_null() {
		assertFalse(obj.equals(null));
	}

	@Test
	public void shouldHaveADescriptiveToStringMethod() {
		assertTrue(obj.toString().indexOf(obj.getClass().getSimpleName()) != -1);
	}

	@Test
	public abstract void should_have_accessors_for_attributes();
}