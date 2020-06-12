package id.co.PPMToolFullStack.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import id.co.PPMToolFullStack.domain.Backlog;

@Repository
public interface BacklogRepository extends CrudRepository<Backlog, Long> {
	Backlog findByProjectIdentifier(String projectIdentifier);
}
