package id.co.PPMToolFullStack.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import id.co.PPMToolFullStack.domain.Backlog;
import id.co.PPMToolFullStack.domain.ProjectTask;
import id.co.PPMToolFullStack.repositories.BacklogRepository;
import id.co.PPMToolFullStack.repositories.ProjectTaskRepository;

@Service
public class ProjectTaskService {
	@Autowired
	private BacklogRepository backlogRepository;

	@Autowired
	private ProjectTaskRepository projectTaskRepository;

	public ProjectTask addProjectTask(String projectIdentifier, ProjectTask projectTask) {
		// exception project not found

		// PTs to be added to a specific project, project != null, BL exists
		Backlog backlog = backlogRepository.findByProjectIdentifier(projectIdentifier);
		// set backlog to project task
		projectTask.setBacklog(backlog);
		// we want our project sequence to be like this : IDPRO-1. IDPRO-2
		Integer backLogSequence = backlog.getPTSequence();
		// update the backlog sequence
		backLogSequence++;
		backlog.setPTSequence(backLogSequence);

		projectTask.setProjectSequence(projectIdentifier + "-" + backLogSequence);
		projectTask.setProjectIdentifier(projectIdentifier);

		// Initial priority when priority null
		if (projectTask.getPriority() == null) {
			projectTask.setPriority(3);
		} else if (projectTask.getPriority() == 0) {
			projectTask.setPriority(3);
		}
		// Initial status when status null
		if (projectTask.getStatus() == "" || projectTask.getStatus() == null) {
			projectTask.setStatus("TO_DO");
		}

		return projectTaskRepository.save(projectTask);
	}

	public Iterable<ProjectTask> findBacklogById(String id){
		return projectTaskRepository.findByProjectIdentifierOrderByPriority(id);
	}
}
