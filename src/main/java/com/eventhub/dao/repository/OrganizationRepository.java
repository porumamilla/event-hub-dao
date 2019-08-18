package com.eventhub.dao.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.eventhub.dao.model.Organization;
import com.eventhub.dao.util.RepositoryUtil;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.firestore.WriteResult;

@Component(value="orgRepository")
public class OrganizationRepository extends BaseRepository {

	public void save(Organization organization) throws Exception {
		DocumentReference docRef = db.collection("organizations").document(RepositoryUtil.getDocumentId());
		Map<String, Object> data = new HashMap<>();
        data.put("name", organization.getName());
        data.put("address", organization.getAddress());
        data.put("address2", organization.getAddress2());
        data.put("city", organization.getCity());
        data.put("country", organization.getCountry());
        data.put("state", organization.getState());
        data.put("postalCode", organization.getPostalCode());
        ApiFuture<WriteResult> result = docRef.set(data);
        result.get().getUpdateTime();
	}
	
	public List<Organization> findAll() throws Exception {
		List<Organization> orgs = new ArrayList<Organization>();
		ApiFuture<QuerySnapshot> query = db.collection("organizations").get();
	    QuerySnapshot querySnapshot = query.get();
	    List<QueryDocumentSnapshot> documents = querySnapshot.getDocuments();
	    for (QueryDocumentSnapshot document : documents) {
	    	Organization org = new Organization();
	    	org.setId(document.getId());
	    	org.setName(document.getString("name"));
	    	org.setAddress(document.getString("address"));
	    	org.setAddress2(document.getString("address2"));
	    	org.setCity(document.getString("city"));
	    	org.setCountry(document.getString("country"));
	    	org.setState(document.getString("state"));
	    	org.setPostalCode(document.getString("postalCode"));
	    	orgs.add(org);
	    }
		return orgs;
	}
}
