package com.mooc.services;

import javax.ejb.Remote;

import com.mooc.domain.Tutor;

@Remote
public interface TutorRemoteService extends EntityRemoteService<Tutor> {

//	List<Tutor> findEnrolledTutorsByCourseId(Integer courseId);
	Tutor findTutorByID(Integer id) ;
}
