package com.example.hackathon.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.hackathon.model.IssuesByRegion;
import com.example.hackathon.repository.IssuesByRegionService;

@RestController
@RequestMapping("/api")
public class IssuesByRegionController {

    @Autowired
    IssuesByRegionService issuesByRegionService;

    @GetMapping("/issuesByRegions")
    public List<IssuesByRegion> getAllIssuesByRegions() {
        return issuesByRegionService.getAll();
    }

}
