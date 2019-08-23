package com.eventhub.dao.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.eventhub.dao.model.Consumer;
import com.eventhub.dao.model.Event;
import com.eventhub.dao.model.EventDefinition;
import com.eventhub.dao.model.Organization;
import com.eventhub.dao.model.Source;
import com.eventhub.dao.model.SourceType;
import com.eventhub.dao.model.Target;
import com.eventhub.dao.model.User;
import com.eventhub.dao.model.Workspace;
import com.eventhub.dao.repository.ConsumerRepository;
import com.eventhub.dao.repository.EventRepository;
import com.eventhub.dao.repository.OrganizationRepository;
import com.eventhub.dao.repository.SourceRepository;
import com.eventhub.dao.repository.TargetRepository;
import com.eventhub.dao.repository.UserRepository;
import com.eventhub.dao.util.RepositoryUtil;

@RestController
public class DAOController {

	@Autowired
	private SourceRepository sourceRepository;
	
	@Autowired
	private OrganizationRepository orgRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private TargetRepository targetRepository;
	
	@Autowired
	private ConsumerRepository consumerRepository;

	@Autowired
	private EventRepository eventRepository;
	
	//Source releated methods
	@RequestMapping(value="/source", method=RequestMethod.PUT)
	public void saveSource(@RequestBody Source source) throws Exception {
		sourceRepository.save(source);
	}
	

	
	@RequestMapping(value="/sourceTypes", method=RequestMethod.GET)
	public List<SourceType> getSourceTypes() throws Exception {
		return sourceRepository.getSourceTypes();
	}
	
	@RequestMapping(value="/organization/sources", method=RequestMethod.GET)
	public List<Source> getSources(@RequestParam(name="orgId") String orgId, @RequestParam(name="workspace") String workspace) throws Exception {
		return sourceRepository.findByOrgId(orgId, workspace);
	}
	
	//org releated methods
	@RequestMapping(value="/organization", method=RequestMethod.POST)
	public String saveOrganization(@RequestBody Organization org) throws Exception {
		String docID = RepositoryUtil.getDocumentId();
		org.setId(docID);
		orgRepository.save(org);
		return docID;
	}
	
	@RequestMapping(value="/organizations", method=RequestMethod.GET)
	public List<Organization> getOrganizations() throws Exception {
		return orgRepository.findAll();
	}
	
	@RequestMapping(value="/organizationBySourceKey", method=RequestMethod.GET)
	public Organization getOrganization(@RequestParam(name="sourceKey") String sourceKey ) throws Exception {
		return orgRepository.findBySourceKey(sourceKey);
	}
	
	//User releated methods
	@RequestMapping(value="/user", method=RequestMethod.POST)
	public String saveUser(@RequestBody User user) throws Exception {
		String docID = RepositoryUtil.getDocumentId();
		user.setId(docID);
		userRepository.save(user);
		
		return docID;
	}
	
	@RequestMapping(value="/user/changeWorkspace", method=RequestMethod.POST)
	public void changeWorkspace(@RequestParam(name="userId") String userId, @RequestParam(name="workspace") String workspace) throws Exception {
		userRepository.changeWorkspace(userId, workspace);
	}
	
	@RequestMapping(value="/organization/users", method=RequestMethod.GET)
	public List<User> getUsers(@RequestParam(name="orgId") String orgId) throws Exception {
		return userRepository.findByOrgId(orgId);
	}
	
	@RequestMapping(value="/organization/user", method=RequestMethod.GET)
	public User getUser(@RequestParam(name="email") String email) throws Exception {
		return userRepository.findByEmail(email);
	}
	
	//Target related methods
	@RequestMapping(value="/targets", method=RequestMethod.GET)
	public List<Target> getTargets() throws Exception {
		return targetRepository.findAll();
	}
	
	@RequestMapping(value="/organization/targets", method=RequestMethod.GET)
	public List<Target> getTargets(@RequestParam(name="orgId") String orgId) throws Exception {
		return targetRepository.findByOrgId(orgId);
	}
	
	//Consumers
	@RequestMapping(value="/organization/consumers", method=RequestMethod.GET)
	public List<Consumer> getConsumers(@RequestParam(name="orgId") String orgId, @RequestParam(name="workspace") String workspace) throws Exception {
		return consumerRepository.findByOrgId(orgId, workspace);
	}
	
	@RequestMapping(value="/organization/eventDefinitions", method=RequestMethod.GET)
	public List<EventDefinition> getEventDefinitions(@RequestParam(name="orgId") String orgId, @RequestParam(name="workspace") String workspace) throws Exception {
		return eventRepository.findDefinitionsByOrgId(orgId, workspace);
	}
	
	@RequestMapping(value="/eventDefinition", method=RequestMethod.GET)
	public EventDefinition getEventDefinition(@RequestParam(name="id") String id) throws Exception {
		return eventRepository.findDefinition(id);
	}
	
	@RequestMapping(value="/organization/eventDefinition", method=RequestMethod.GET)
	public EventDefinition getEventDefinition(@RequestParam(name="eventName") String eventName, @RequestParam(name="orgId") String orgId, @RequestParam(name="workspace") String workspace) throws Exception {
		return eventRepository.findDefinitionByOrgId(orgId, workspace, eventName);
	}
	
	@RequestMapping(value="/organization/eventDefinition", method=RequestMethod.PUT)
	public void saveEventDefinition(@RequestBody EventDefinition eventDefinition)  throws Exception {
		eventDefinition.setId(RepositoryUtil.getDocumentId());
		eventRepository.saveDefinition(eventDefinition);
	}
	
	@RequestMapping(value="/organization/eventDefinition", method=RequestMethod.POST)
	public void updateEventDefinition(@RequestBody EventDefinition eventDefinition)  throws Exception {
		eventRepository.updateDefinition(eventDefinition);
	}
	
	@RequestMapping(value="/organization/eventDefinition", method=RequestMethod.DELETE)
	public void deleteEventDefinition(@RequestParam(name="id") String id)  throws Exception {
		eventRepository.deleteDefinition(id);
	}
	
	@RequestMapping(value="/organization/workspace", method=RequestMethod.PUT)
	public void saveWorkspace(@RequestBody Workspace orgWorkspace)  throws Exception {
		orgRepository.saveWorkspace(orgWorkspace);
	}
	
	@RequestMapping(value="/organization/workspaces", method=RequestMethod.GET)
	public List<Workspace> getWorkspaces(@RequestParam(name="orgId") String orgId)  throws Exception {
		return orgRepository.getWorkspaces(orgId);
	}
	
	@RequestMapping(value="/organization/sourceType", method=RequestMethod.PUT)
	public void saveSourceType(@RequestParam(name="orgId") String orgId, @RequestBody SourceType sourceType)  throws Exception {
		orgRepository.saveSourceType(orgId, sourceType);
	}
	
	@RequestMapping(value="/organization/sourceType", method=RequestMethod.DELETE)
	public void deleteSourceType(@RequestParam(name="orgId") String orgId, @RequestBody SourceType sourceType)  throws Exception {
		orgRepository.deleteSourceType(orgId, sourceType);
	}
	
	@RequestMapping(value="/organization/sourceTypes", method=RequestMethod.GET)
	public List<SourceType> getSourceTypes(@RequestParam(name="orgId") String orgId, @RequestParam(name="workspace") String workspace)  throws Exception {
		List<SourceType> referenceSourceTypes = sourceRepository.getSourceTypes();
		List<SourceType> sourceTypes = orgRepository.getSourceTypes(orgId, workspace);
		for (SourceType sourceType : sourceTypes) {
			if (referenceSourceTypes.contains(sourceType)) {
				sourceType.setType(referenceSourceTypes.get(referenceSourceTypes.indexOf(sourceType)).getType());
			}
		}
		return sourceTypes;
	}
	
	@RequestMapping(value="/organization/events", method=RequestMethod.GET)
	public List<Event> getEvents(@RequestParam(name="orgId") String orgId, @RequestParam(name="workspace") String workspace)  throws Exception {
		return eventRepository.getLatestEvents(orgId, workspace);
	}
}
