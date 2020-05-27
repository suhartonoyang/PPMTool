/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.co.PPMToolFullStack.services;

import id.co.PPMToolFullStack.domain.Project;
import id.co.PPMToolFullStack.repositories.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author 51974
 */
@Service
public class ProjectService {
    @Autowired
    private ProjectRepository projectRepository;
    
    public Project saveOrUpdateProject(Project project){
        

        return projectRepository.save(project);
    }    
}
