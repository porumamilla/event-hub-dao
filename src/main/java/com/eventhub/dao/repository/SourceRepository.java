package com.eventhub.dao.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.eventhub.dao.model.Organization;
import com.eventhub.dao.model.Source;
import com.eventhub.dao.model.SourceType;
import com.eventhub.dao.util.RepositoryUtil;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.firestore.WriteResult;

@Component(value="sourceRepository")
public class SourceRepository extends BaseRepository {

	public void save(Source source) throws Exception {
		DocumentReference docRef = db.collection("sources").document(RepositoryUtil.getDocumentId());
		Map<String, Object> data = new HashMap<>();
        data.put("name", source.getName());
        data.put("orgId", source.getOrgId());
        data.put("protocol", source.getProtocol());
        
        ApiFuture<WriteResult> result = docRef.set(data);
        result.get().getUpdateTime();
	}
	
	public List<Source> findByOrgId(String orgId, String workspace) throws Exception {
		List<Source> sources = new ArrayList<Source>();
		ApiFuture<QuerySnapshot> query = db.collection("sources").whereEqualTo("orgId", orgId).whereEqualTo("workspace", workspace).get();
		QuerySnapshot querySnapshot = query.get();
		List<QueryDocumentSnapshot> documents = querySnapshot.getDocuments();
		for (QueryDocumentSnapshot document : documents) {
			Source source = new Source();
			source.setId(document.getId());
			source.setName(document.getString("name"));
			source.setProtocol(document.getString("protocol"));
			source.setOrgId(orgId);

			sources.add(source);
		}
		return sources;
	}
	
	public List<SourceType> getSourceTypes() throws Exception {
		List<SourceType> sourceTypes = new ArrayList<SourceType>();
		ApiFuture<QuerySnapshot> query = db.collection("source_types").get();
	    QuerySnapshot querySnapshot = query.get();
	    List<QueryDocumentSnapshot> documents = querySnapshot.getDocuments();
	    for (QueryDocumentSnapshot document : documents) {
	    	SourceType sourceType = new SourceType();
	    	sourceType.setId(document.getId());
	    	sourceType.setType(document.getString("type"));
	    	sourceTypes.add(sourceType);
	    }
		return sourceTypes;
	}
}
