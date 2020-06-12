package id.co.PPMToolFullStack.web;

import java.security.Principal;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import id.co.PPMToolFullStack.domain.ProjectTask;
import id.co.PPMToolFullStack.services.MapValidationErrorService;
import id.co.PPMToolFullStack.services.ProjectTaskService;

@RestController
@RequestMapping("/api/backlog")
@CrossOrigin
public class BacklogController {

	@Autowired
	private ProjectTaskService projectTaskService;

	@Autowired
	private MapValidationErrorService MapValidationErrorService;

	@PostMapping("/{projectIdentifer}")
	public ResponseEntity<?> addPTtoBacklog(@Valid @RequestBody ProjectTask projectTask, BindingResult result,
			@PathVariable String projectIdentifer, Principal principal) {
		ResponseEntity<?> errorMap = MapValidationErrorService.MapValidationService(result);
		if (errorMap != null) {
			return errorMap;
		}

		ProjectTask projectTask1 = projectTaskService.addProjectTask(projectIdentifer, projectTask,
				principal.getName());

		return new ResponseEntity<ProjectTask>(projectTask1, HttpStatus.CREATED);
	}

	@GetMapping("/{projectIdentifer}")
	public Iterable<ProjectTask> getProjectBacklog(@PathVariable String projectIdentifer, Principal principal) {
		return projectTaskService.findBacklogById(projectIdentifer, principal.getName());
	}

	@GetMapping("/{projectIdentifer}/{pt_id}")
	public ResponseEntity<?> getProjectTask(@PathVariable String projectIdentifer, @PathVariable String pt_id,
			Principal principal) {
		ProjectTask projectTask = projectTaskService.findPTByProjectSequence(projectIdentifer, pt_id,
				principal.getName());
		return new ResponseEntity<ProjectTask>(projectTask, HttpStatus.OK);
	}

	@PatchMapping("/{projectIdentifer}/{pt_id}")
	public ResponseEntity<?> updateProjectTask(@Valid @RequestBody ProjectTask projectTask, BindingResult result,
			@PathVariable String projectIdentifer, @PathVariable String pt_id, Principal principal) {
		ResponseEntity<?> errorMap = MapValidationErrorService.MapValidationService(result);
		if (errorMap != null) {
			return errorMap;
		}

		ProjectTask updateTask = projectTaskService.updatePTByProjectSequence(projectTask, projectIdentifer, pt_id,
				principal.getName());

		return new ResponseEntity<ProjectTask>(updateTask, HttpStatus.OK);

	}

	@DeleteMapping("/{projectIdentifer}/{pt_id}")
	public ResponseEntity<?> deleteProjectTask(@PathVariable String projectIdentifer, @PathVariable String pt_id,
			Principal principal) {
		projectTaskService.deletePTByProjectSequence(projectIdentifer, pt_id, principal.getName());
		return new ResponseEntity<String>("Project Task '" + pt_id + "' was deleted successfully", HttpStatus.OK);

	}

}
