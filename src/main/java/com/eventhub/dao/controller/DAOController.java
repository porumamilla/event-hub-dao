package com.eventhub.dao.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.eventhub.dao.model.Consumer;
import com.eventhub.dao.model.EventDefinition;
import com.eventhub.dao.model.Organization;
import com.eventhub.dao.model.Source;
import com.eventhub.dao.model.Target;
import com.eventhub.dao.model.User;
import com.eventhub.dao.repository.ConsumerRepository;
import com.eventhub.dao.repository.EventRepository;
import com.eventhub.dao.repository.OrganizationRepository;
import com.eventhub.dao.repository.SourceRepository;
import com.eventhub.dao.repository.TargetRepository;
import com.eventhub.dao.repository.UserRepository;

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
	
	@RequestMapping(value="/organization/sources", method=RequestMethod.GET)
	public List<Source> getSources(@RequestParam(name="orgId") String orgId, @RequestParam(name="workspace") String workspace) throws Exception {
		return sourceRepository.findByOrgId(orgId, workspace);
	}
	
	//org releated methods
	@RequestMapping(value="/organization", method=RequestMethod.PUT)
	public void saveOrganization(@RequestBody Organization org) throws Exception {
		orgRepository.save(org);
	}
	
	@RequestMapping(value="/organizations", method=RequestMethod.GET)
	public List<Organization> getOrganizations() throws Exception {
		return orgRepository.findAll();
	}
	
	//User releated methods
	@RequestMapping(value="/user", method=RequestMethod.PUT)
	public void saveUser(@RequestBody User user) throws Exception {
		userRepository.save(user);
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
	public List<EventDefinition> getEvents(@RequestParam(name="orgId") String orgId, @RequestParam(name="workspace") String workspace) throws Exception {
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
}
