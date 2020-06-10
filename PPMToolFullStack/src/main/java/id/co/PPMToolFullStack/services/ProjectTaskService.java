package id.co.PPMToolFullStack.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;

import id.co.PPMToolFullStack.domain.Backlog;
import id.co.PPMToolFullStack.domain.Project;
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

	public ProjectTask addProjectTask(String backlog_id, ProjectTask projectTask) {
		// exception project not found
		try {
			// PTs to be added to a specific project, project != null, BL exists
			Backlog backlog = backlogRepository.findByProjectIdentifier(backlog_id);
			// set backlog to project task
			projectTask.setBacklog(backlog);
			// we want our project sequence to be like this : IDPRO-1. IDPRO-2
			Integer backLogSequence = backlog.getPTSequence();
			// update the backlog sequence
			backLogSequence++;
			backlog.setPTSequence(backLogSequence);

			projectTask.setProjectSequence(backlog_id + "-" + backLogSequence);
			projectTask.setProjectIdentifier(backlog_id);

			// Initial priority when priority null
			if (projectTask.getPriority() == 0 || projectTask.getPriority() == null) {
				projectTask.setPriority(3);
			} 
			// Initial status when status null
			if (projectTask.getStatus() == "" || projectTask.getStatus() == null) {
				projectTask.setStatus("TO_DO");
			}

			return projectTaskRepository.save(projectTask);
		} catch (Exception e) {
			throw new ProjectNotFoundException("Project with ID: '" + backlog_id + "' does not exists");
		}

	}

	public Iterable<ProjectTask> findBacklogById(String backlog_id) {

		Project project = projectRepository.findByProjectIdentifier(backlog_id);
		if (project == null) {
			throw new ProjectNotFoundException("Project with ID: '" + backlog_id + "' does not exists");
		}

		return projectTaskRepository.findByProjectIdentifierOrderByPriority(backlog_id);

	}

	public ProjectTask findPTByProjectSequence(String backlog_id, String pt_id) {
		// make sure we are searching on the existing backlog
		Backlog backlog = backlogRepository.findByProjectIdentifier(backlog_id);
		if (backlog == null) {
			throw new ProjectNotFoundException("Project with ID: '" + backlog_id + "' does not exists");
		}

		// make sure that our task exists
		ProjectTask projectTask = projectTaskRepository.findByProjectSequence(pt_id);
		if (projectTask == null) {
			throw new ProjectNotFoundException("Project Task '" + pt_id + "' not found");
		}

		// make sure that the backlog/project id in the path corresponds to the right project
		if (!projectTask.getBacklog().getProjectIdentifier().equals(backlog_id)) {
			throw new ProjectNotFoundException(
					"Project Task '" + pt_id + "' does not exists in project: '" + backlog_id + "'");
		}

		return projectTask;
	}

	public ProjectTask updatePTByProjectSequence(ProjectTask updatedTask, String backlog_id, String pt_id) {
		ProjectTask projectTask = findPTByProjectSequence(backlog_id, pt_id);

		projectTask = updatedTask;

		return projectTaskRepository.save(projectTask);

	}

	public void deletePTByProjectSequence(String backlog_id, String pt_id) {
		ProjectTask projectTask = findPTByProjectSequence(backlog_id, pt_id);
		projectTaskRepository.delete(projectTask);
	}

}
