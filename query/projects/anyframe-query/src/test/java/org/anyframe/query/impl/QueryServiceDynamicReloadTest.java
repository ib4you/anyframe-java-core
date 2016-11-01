/*
 * Copyright 2002-2008 the original author or authors.
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
package org.anyframe.query.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import org.anyframe.query.QueryService;
import org.springframework.test.AbstractDependencyInjectionSpringContextTests;

/**
 * TestCase Name : QueryServiceDynamicReloadTest <br>
 * <br>
 * [Description] : Dynamic Reload function is checked. Dynamic Reload requires
 * SQLLoader property definition in the same format as bellows. <br>
 * 
 * <pre>
 * &lt;sqlload dynamic=&quot;true&quot; frequency=&quot;5&quot; /&gt;
 * </pre>
 * 
 * [Main Flow]
 * <ul>
 * <li>#-1 Positive Case : In the case where mapping XML file is changed by
 * using Dynamic Reload of QueryService, it is verified whether contents
 * modified by defined interval are reflected.</li>
 * </ul>
 * 
 * @author SoYon Lim
 */
public class QueryServiceDynamicReloadTest extends
        AbstractDependencyInjectionSpringContextTests {
    private QueryService queryService = null;

    public void setQueryService(QueryService queryService) {
        this.queryService = queryService;
    }

    protected String[] getConfigLocations() {
        setAutowireMode(AbstractDependencyInjectionSpringContextTests.AUTOWIRE_BY_NAME);
        return new String[] {"classpath*:/spring/context-*.xml" };
    }

	/**
	 * [Flow #-1] Positive Case : In the case where mapping XML file is changed
	 * by using Dynamic Reload of QueryService, it is verified whether contents
	 * modified by defined interval are reflected.
	 * 
     * @throws Exception
     *         throws exception which is from
     *         QueryService
     */
    public void testDynamicReload() throws Exception {
//        dynamicReload();
    }

	/**
	 * By coping pre-defined testcase-queries-update.xml on to
	 * testcase-queries-dynamicreload.xml file, it is verified whether Dynamic
	 * Reload is well carried out by QueryService.
	 * 
     * @throws Exception
     *         fail to dynamic reload
     */
    private void dynamicReload() throws Exception {
        // 1. set data
        File targetFile =
            new File("./testdynamicreload/testcase-queries-dynamicreload.xml");
        File updatedFile =
            new File("./testdynamicreload/testcase-queries-update.xml");

        // 2. get query statement from original xml
        // file
        String query = queryService.getStatement("dynamicReload");

        // 3. change contents of original file
        changeFileContent(updatedFile, targetFile);

        // 4. wait for dynamic reloading
        Thread.sleep(10000);

        // 5. get query statement from changed xml file
        String changedQuery = queryService.getStatement("dynamicReload");

        // 6. assert
        assertTrue("Fail to change xml contents", changedQuery.toString()
            .startsWith(
                "insert into TB_UPDATED_DYNAMIC_RELOAD(col1, col2, col3)"));

        assertNotSame("Fail to change query information.", query.toString(),
            changedQuery.toString());
    }

	/**
	 * Source file contents are copied on to destination file. 
	 * 
	 * @param source
	 *            modification file 
	 * @param destination
	 *            original file
     * @throws Exception
     *         fail to change file contents.
     */
    private void changeFileContent(File source, File destination)
            throws Exception {
		InputStream in = null;
		OutputStream out = null;

		try {
			in = new FileInputStream(source);
			out = new FileOutputStream(destination);

			byte[] buf = new byte[1024];
			int len;
			while ((len = in.read(buf)) > 0) {
				out.write(buf, 0, len);
			}
		} finally {
			in.close();
			out.close();
		}
    }
}
