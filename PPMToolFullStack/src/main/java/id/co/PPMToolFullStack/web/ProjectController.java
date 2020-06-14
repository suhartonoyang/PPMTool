/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.co.PPMToolFullStack.web;

import java.security.Principal;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import id.co.PPMToolFullStack.domain.Project;
import id.co.PPMToolFullStack.dto.Pagination;
import id.co.PPMToolFullStack.services.MapValidationErrorService;
import id.co.PPMToolFullStack.services.ProjectService;

/**
 *
 * @author 51974
 */
@RestController
@RequestMapping("/api/project")
@CrossOrigin
public class ProjectController {

	@Autowired
	private ProjectService projectService;

	@Autowired
	private MapValidationErrorService mapValidationErrorService;

	@PostMapping("")
	public ResponseEntity<?> createNewProject(@Valid @RequestBody Project project, BindingResult result,
			Principal principal) {

		ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
		if (errorMap != null) {
			return errorMap;
		}

		Project project1 = projectService.saveOrUpdateProject(project, principal.getName());
		return new ResponseEntity<Project>(project1, HttpStatus.CREATED);
	}

	@GetMapping("/{projectId}")
	public ResponseEntity<?> getProjectById(@PathVariable String projectId, Principal principal) {

		Project project = projectService.findProjectByIdentifier(projectId.toUpperCase(), principal.getName());
		return new ResponseEntity<Project>(project, HttpStatus.OK);
	}

	@GetMapping("/all")
	public Iterable<Project> getAllProjects(Principal principal) {

		return projectService.findAllProjects(principal.getName());
	}

	@GetMapping("/all/pagination")
	public ResponseEntity<?> getAllProjectsPaging(Principal principal, Pageable pageable) {
		Pagination pagination = projectService.findAllProjectsPaging(principal.getName(), pageable);

//		List<Project> pagination = projectService.findAllProjectsPaging(principal.getName(), pageable);

		return new ResponseEntity<Pagination>(pagination, HttpStatus.OK);
	}

	@DeleteMapping("/{projectId}")
	public ResponseEntity<?> deleteProjectById(@PathVariable String projectId, Principal principal) {

		projectService.deleteProjectByIdentifier(projectId.toUpperCase(), principal.getName());

		return new ResponseEntity<String>("Project with ID '" + projectId + "' was deleted", HttpStatus.OK);
	}
}
