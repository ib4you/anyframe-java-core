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

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Iterator;

import org.anyframe.query.QueryService;
import org.anyframe.query.vo.Room;
import org.springframework.test.AbstractDependencyInjectionSpringContextTests;


/**
 * TestCase Name : QueryServiceRoomTest <br>
 * <br>
 * [Description] : Without separate definition of query statement, 
 * by only entering object, data is entered, modified, 
 * deleted and searched and its result is verified.<br>
 * [Main Flow]
 * <ul>
 * <li>#-1 Positive Case : By calling for update() method of QueryService, 
 * without separate definition of query statement, by using object, 
 * specific data is modified. QueryService creates
 * and executes UPDATE query statement based on table mapping information.</li>
 * <li>#-2 Positive Case : By calling for remove() method of QueryService, 
 * without separate definition of query statement, by using object, 
 * specific data is deleted. QueryService creates 
 * and executes DELETE query statement based on table mapping information.</li>
 * </ul>
 * @author SoYon Lim
 */
public class QueryServiceRoomTest extends
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
     * Table TB_ROOM is created for test. 
     */
    public void onSetUp() throws Exception {
        System.out.println("Attempting to drop old table");
        try {
            queryService.updateBySQL("DROP TABLE TB_ROOM", new String[] {},
                new Object[] {});
        } catch (Exception e) {
            System.out.println("Fail to DROP Table.");
        }

        queryService.updateBySQL("CREATE TABLE TB_ROOM ( "
            + "ROOM_NO NUMBER(3) NOT NULL, " + "ROOM_PRICE NUMBER(8), "
            + "ROOM_SIZE NUMBER(3,1), " + "PRIMARY KEY (ROOM_NO))",
            new String[] {}, new Object[] {});
    }

    /**
     * [Flow #-1] Positive Case : By calling for update() method of QueryService, 
     * without separate definition of query statement, by using object, 
     * specific data is modified. QueryService creates 
     * and executes UPDATE query statement based on table mapping information.
     * @throws Exception
     *         throws exception which is from
     *         QueryService
     */
    public void testUpdateRoomByObject() throws Exception {
        // 1. set object value for test
        Room room = insertRoomByObject();
        room.setRoomPrice(new BigDecimal("250000"));
        room.setRoomSize((new BigDecimal("22.7")));

        // 2. execute query
        int results = queryService.update(room);
        assertEquals("Fail to update room.", results, 1);

        // 3. assert
        findRoomByObject(room.getRoomNo(), room.getRoomPrice(), room
            .getRoomSize());
    }

    /**
     * [Flow #-2] Positive Case : By calling for create() method of QueryService, 
     * without separate definition of query statement, by using object,  
     * specific data is deleted. QueryService creates 
     * and executes DELETE query statement based on table mapping information.
     * @throws Exception
     *         throws exception which is from
     *         QueryService
     */
    public void testRemoveRoomByObject() throws Exception {
        // 1. insert for test
        Room room = insertRoomByObject();

        // 2. execute query
        int results = queryService.remove(room);

        // 3. assert
        assertEquals("Fail to remove room.", results, 1);
        Collection rtCollection = (Collection) queryService.find(room);
        assertEquals("Fail to remove room.", 0, rtCollection.size());
    }

    /**
     * By calling for create() method of QueryService, 
     * without separate definition of query statement, by using object, 
     * new data is entered. QueryService creates 
     * and executes INSERT query statement based on table mapping information.
     * @throws Exception
     *         throws exception which is from
     *         QueryService
     */
    private Room insertRoomByObject() throws Exception {
        // 1. set object value for test
        BigDecimal roomNo = new BigDecimal("123");
        BigDecimal roomPrice = new BigDecimal("300000");
        BigDecimal roomSize = new BigDecimal("25.7");

        Room room = new Room(roomNo, roomPrice, roomSize);

        // 2. execute query
        int results = queryService.create(room);

        // 3. assert
        assertEquals("Fail to insert room.", results, 1);
        findRoomByObject(roomNo, roomPrice, roomSize);

        // 4. return for another test
        return room;
    }

    /**
     * Without separate definition of query statement, by using object, 
     * one piece of data is searched and its result is verified. 
     * @param roomNo
     * @param roomPrice
     * @param roomSize
     * @return search result Room object
     * @throws Exception
     *         fail to find Room
     */
    private void findRoomByObject(BigDecimal roomNo, BigDecimal roomPrice,
            BigDecimal roomSize) throws Exception {
        // 1. set object value for test
        Room room = new Room(roomNo, roomPrice, roomSize);

        // 2. execute query
        Collection rtCollection = (Collection) queryService.find(room);

        // 3. assert
        assertEquals("Fail to find room.", 1, rtCollection.size());
        Iterator rtIterator = rtCollection.iterator();

        // 4. assert in detail
        Room result = (Room) rtIterator.next();
        assertEquals("Fail to compare result.", roomPrice, result
            .getRoomPrice());
        assertEquals("Fail to compare result.", roomSize.longValue(), Math
            .round(result.getRoomSize().longValue()));
    }

}
