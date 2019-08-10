package com.eventhub.dao.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.eventhub.dao.model.EventDefinition;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;

@Component(value="eventRepository")
public class EventRepository extends BaseRepository {
	public List<EventDefinition> findDefinitionsByOrgId(String orgId, String workspace) throws Exception {
		List<EventDefinition> definitions = new ArrayList<EventDefinition>();
		ApiFuture<QuerySnapshot> query = db.collection("org_event_definitions").whereEqualTo("orgId", orgId).whereEqualTo("workspace", workspace).get();
		QuerySnapshot querySnapshot = query.get();
		List<QueryDocumentSnapshot> documents = querySnapshot.getDocuments();
		for (QueryDocumentSnapshot document : documents) {
			EventDefinition definition = new EventDefinition();
			
			definition.setId(document.getId());
			definition.setOrgId(document.getString("orgId"));
			definition.setSourceId(document.getString("sourceId"));
			definition.setEventName(document.getString("eventName"));
			definition.setWorkspace(document.getString("workspace"));
			definition.setSchema(document.getString("schema"));
			definitions.add(definition);
		}
		return definitions;
	}
	
	public EventDefinition findDefinitionByOrgId(String orgId, String workspace, String eventName) throws Exception {
		EventDefinition definition = new EventDefinition();
		ApiFuture<QuerySnapshot> query = db.collection("org_event_definitions")
				.whereEqualTo("orgId", orgId)
				.whereEqualTo("eventName", eventName)
				.whereEqualTo("workspace", workspace).get();
		QuerySnapshot querySnapshot = query.get();
		List<QueryDocumentSnapshot> documents = querySnapshot.getDocuments();
		for (QueryDocumentSnapshot document : documents) {
			
			
			definition.setId(document.getId());
			definition.setOrgId(document.getString("orgId"));
			definition.setSourceId(document.getString("sourceId"));
			definition.setEventName(document.getString("eventName"));
			definition.setWorkspace(document.getString("workspace"));
			definition.setSchema(document.getString("schema"));
			break;
		}
		return definition;
	}
	
	public EventDefinition findDefinition(String id) throws Exception {
		ApiFuture<DocumentSnapshot> future = db.collection("org_event_definitions").document(id).get();
		DocumentSnapshot document = future.get();
		
		EventDefinition definition = new EventDefinition();
		
		definition.setId(document.getId());
		definition.setOrgId(document.getString("orgId"));
		definition.setSourceId(document.getString("sourceId"));
		definition.setEventName(document.getString("eventName"));
		definition.setWorkspace(document.getString("workspace"));
		definition.setSchema(document.getString("schema"));
		
		return definition;
	}
}
