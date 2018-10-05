package com.example.hackathon.repository;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import com.example.hackathon.exception.ResourceNotFoundException;
import com.example.hackathon.model.IssuesByRegion;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class IssuesByRegionServiceImpl implements IssuesByRegionService {

	@SuppressWarnings("unchecked")
	@Override
	public List<IssuesByRegion> getAll() {
		List<IssuesByRegion> issuesByRegions = null;
		try {
			Resource resource = new ClassPathResource(
					"issuesbycount.json");
			InputStream resourceInputStream = resource.getInputStream();
			ObjectMapper mapper = new ObjectMapper();
			issuesByRegions = (List<IssuesByRegion>) mapper.readValue(
					resourceInputStream, new TypeReference<List<IssuesByRegion>>(){});
		} catch (IOException e) {
			throw new ResourceNotFoundException("configuration.json", null,
					null);
		}
		return issuesByRegions;
	}

}
