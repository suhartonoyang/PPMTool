package id.co.PPMToolFullStack.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import id.co.PPMToolFullStack.domain.Backlog;
import id.co.PPMToolFullStack.domain.ProjectTask;
import id.co.PPMToolFullStack.exceptions.ProjectNotFoundException;
import id.co.PPMToolFullStack.repositories.BacklogRepository;
import id.co.PPMToolFullStack.repositories.ProjectRepository;
import id.co.PPMToolFullStack.repositories.ProjectTaskRepository;

@Service
public class ProjectTaskService {
	@Autowired
	private BacklogRepository backlogRepository;

	@Autowired
	private ProjectTaskRepository projectTaskRepository;

	@Autowired
	private ProjectRepository projectRepository;

	@Autowired
	private ProjectService projectService;

	public ProjectTask addProjectTask(String projectIdentifer, ProjectTask projectTask, String username) {

		// PTs to be added to a specific project, project != null, BL exists
//		Backlog backlog = backlogRepository.findByProjectIdentifier(projectIdentifer);
		Backlog backlog = projectService.findProjectByIdentifier(projectIdentifer, username).getBacklog();

		// set backlog to project task
		projectTask.setBacklog(backlog);
		// we want our project sequence to be like this : IDPRO-1. IDPRO-2
		Integer backLogSequence = backlog.getPTSequence();
		// update the backlog sequence
		backLogSequence++;
		backlog.setPTSequence(backLogSequence);

		projectTask.setProjectSequence(projectIdentifer + "-" + backLogSequence);
		projectTask.setProjectIdentifier(projectIdentifer);

		// Initial priority when priority null
		if (projectTask.getPriority() == null || projectTask.getPriority() == 0) {
			projectTask.setPriority(3);
		}
		// Initial status when status null
		if (projectTask.getStatus() == "" || projectTask.getStatus() == null) {
			projectTask.setStatus("TO_DO");
		}

		return projectTaskRepository.save(projectTask);

	}

	public Iterable<ProjectTask> findBacklogById(String projectIdentifer, String username) {

//		Project project = projectRepository.findByProjectIdentifier(projectIdentifer);
//		if (project == null) {
//			throw new ProjectNotFoundException("Project with ID: '" + projectIdentifer + "' does not exists");
//		}

		projectService.findProjectByIdentifier(projectIdentifer, username);
		return projectTaskRepository.findByProjectIdentifierOrderByPriority(projectIdentifer);

	}

	public ProjectTask findPTByProjectSequence(String projectIdentifer, String pt_id, String username) {
		// make sure we are searching on the existing backlog
//		Backlog backlog = backlogRepository.findByProjectIdentifier(projectIdentifer);
//		if (backlog == null) {
//			throw new ProjectNotFoundException("Project with ID: '" + projectIdentifer + "' does not exists");
//		}

		projectService.findProjectByIdentifier(projectIdentifer, username);

		// make sure that our task exists
		ProjectTask projectTask = projectTaskRepository.findByProjectSequence(pt_id);
		if (projectTask == null) {
			throw new ProjectNotFoundException("Project Task '" + pt_id + "' not found");
		}

		// make sure that the backlog/project id in the path corresponds to the right project
		if (!projectTask.getBacklog().getProjectIdentifier().equals(projectIdentifer)) {
			throw new ProjectNotFoundException(
					"Project Task '" + pt_id + "' does not exists in project: '" + projectIdentifer + "'");
		}

		return projectTask;
	}

	public ProjectTask updatePTByProjectSequence(ProjectTask updatedTask, String projectIdentifer, String pt_id,
			String username) {
		ProjectTask projectTask = findPTByProjectSequence(projectIdentifer, pt_id, username);

		projectTask = updatedTask;

		return projectTaskRepository.save(projectTask);

	}

	public void deletePTByProjectSequence(String projectIdentifer, String pt_id, String username) {
		ProjectTask projectTask = findPTByProjectSequence(projectIdentifer, pt_id, username);
		projectTaskRepository.delete(projectTask);
	}

}
