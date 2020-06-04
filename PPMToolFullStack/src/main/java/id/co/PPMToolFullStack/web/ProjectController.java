/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.co.PPMToolFullStack.web;

import id.co.PPMToolFullStack.domain.Project;
import id.co.PPMToolFullStack.services.MapValidationErrorService;
import id.co.PPMToolFullStack.services.ProjectService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<?> createNewProject(@Valid @RequestBody Project project, BindingResult result) {

        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
        if (errorMap != null) {
            return errorMap;
        }

        Project project1 = projectService.saveOrUpdateProject(project);
        return new ResponseEntity<Project>(project1, HttpStatus.CREATED);
    }

    @GetMapping("/{projectId}")
    public ResponseEntity<?> getProjectById(@PathVariable String projectId) {

        Project project = projectService.findProjectByIdentifier(projectId.toUpperCase());
        return new ResponseEntity<Project>(project, HttpStatus.OK);
    }

    @GetMapping("/all")
    public Iterable<Project> getAllProjects() {

        return projectService.findAllProjects();
    }

    @DeleteMapping("/{projectId}")
    public ResponseEntity<?> deleteProjectById(@PathVariable String projectId) {

        projectService.deleteProjectByIdentifier(projectId.toUpperCase());

        return new ResponseEntity<String>("Project with ID '" + projectId + "' was deleted", HttpStatus.OK);
    }
}
