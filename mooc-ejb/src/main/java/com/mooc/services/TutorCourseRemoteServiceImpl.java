package com.mooc.services;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.TypedQuery;

import com.mooc.domain.Tutor;

@Stateless
public class TutorCourseRemoteServiceImpl extends GenericRemoteService<Tutor> implements TutorRemoteService {

	protected TutorCourseRemoteServiceImpl() {
		super(Tutor.class);
	}
	@Override
	public Tutor findTutorByID(Integer id ) {
		TypedQuery<Tutor> q = entityManager
				.createQuery("select u from Tutor u where u.id=?0 ", Tutor.class)
				.setParameter(0, id);
		List<Tutor> list = q.getResultList();
		if (list.isEmpty()) {
			return null;
		}
		return list.get(0);
	}
	
	
//	@Override
//	public List<Tutor> findEnrolledTutorsByCourseId(Integer courseId) {
//		// TODO Auto-generated method stub
//		return null;
//	}

}
