package de.ralleytn.simple.audio.tests;

import java.net.URL;

import org.junit.jupiter.api.Assertions;

final class TestUtil {

	public static final String RESOURCE_LOCATION = "de/ralleytn/simple/audio/tests/";
	
	private TestUtil() {}
	
	public static final URL getResource(String name) {
		
		return LoadingAndPlayTest.class.getClassLoader().getResource(String.format("%s%s", RESOURCE_LOCATION, name));
	}
	
	public static final void fail(Exception exception) {
		
		Assertions.fail(String.format("%s: %s", exception.getClass().getName(), exception.getMessage()));
	}
}
