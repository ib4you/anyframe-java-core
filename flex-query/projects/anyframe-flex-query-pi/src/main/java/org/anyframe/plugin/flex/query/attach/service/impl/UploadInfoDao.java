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
package org.anyframe.plugin.flex.query.attach.service.impl;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.anyframe.plugin.flex.query.domain.Attached;
import org.anyframe.query.QueryService;
import org.anyframe.query.dao.QueryServiceDaoSupport;
import org.springframework.stereotype.Repository;

@Repository("flexQueryUploadInfoDao")
public class UploadInfoDao extends QueryServiceDaoSupport{
	
	@Inject
	@Named("queryService")
	public void setQueryService(QueryService queryService) {
		super.setQueryService(queryService);
	}
	
	public int create(Attached attached) throws Exception {
		return create("createFlexQueryAttached", attached);
	}
	
	@SuppressWarnings("unchecked")
	public List<Attached> getList(String refId) throws Exception {
		Attached attached = new Attached();
		attached.setRefId(refId);
		return (List<Attached>) this.findList("findFlexQueryAttachedList", attached);
	}
	
	public int remove(Attached attached) throws Exception {
		return remove("removeFlexQueryAttached", attached);
	}
}
