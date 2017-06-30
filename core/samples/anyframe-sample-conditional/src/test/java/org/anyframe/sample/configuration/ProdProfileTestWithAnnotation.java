/*
 * Copyright 2008-2012 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.anyframe.sample.configuration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.anyframe.sample.domain.Movie;
import org.anyframe.sample.resolver.MovieActiveProfileResolver;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;

/**
 * This class is an annotation based Test Case class for "Prod" profile defined
 * XML.
 * 
 * @author Heewon Jung
 */
@ActiveProfiles("Prod")
public class ProdProfileTestWithAnnotation extends
		DefaultProfileTestWithAnnotation {

	@Autowired
	private Movie movie;
	
	@Test
	@Override
	public void getMovie() {
		assertNotNull(movie);
		assertEquals("Prod", movie.getTitle());
	}

}