package com.mooc.bean;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import com.mooc.domain.CommitteeMember;
import com.mooc.domain.Course;
import com.mooc.domain.Person;
import com.mooc.domain.Student;
import com.mooc.domain.Tutor;
import com.mooc.services.CourseRemoteService;
import com.mooc.services.TutorRemoteService;
import com.mooc.services.UserRemoteService;

@ManagedBean
@SessionScoped
public class UserBean {

	@EJB
	private UserRemoteService userService;
	@EJB
	private TutorRemoteService tutorRemoteService;
	@EJB
	private CourseRemoteService courseRemoteService;

	private Person currentUser = new Person();
	private Tutor currentNewtutor = new Tutor();
	private String errorMessage;
	private Course course= new Course();
	private List<Course> courseList = new ArrayList<>();

	public Person getCurrentUser() {
		return currentUser;
	}

	public Tutor getCurrentNewtutor() {
		return currentNewtutor;
	}
	public Course getCourse() {
		return course;
	}
	public String authenticate() {
		Person user = userService.findUser(currentUser.getEmail(), currentUser.getPassword());
		if (user == null) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Incorrect Email or Passowrd", "Incorrect Email or Passowrd"));
			return "/views/login";
		}
		currentUser = user;
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("user", currentUser);
		if (currentUser instanceof CommitteeMember) {
			return "/views/admin";
		} else if (currentUser instanceof Tutor) {
			return "/trainer/trainer?faces-redirect=true";
		}
		return "/trainer/index?faces-redirect=true";
	}

	public String addTrainer() {

		boolean b = tutorRemoteService.create(currentNewtutor);
		if (b) {
			return "/trainer/trainer?faces-redirect=true";
		}

		return "/trainer/NewTrainer?faces-redirect=true";
	}

	public String addCours() {
		Tutor tutorCurrent = tutorRemoteService.findTutorByID(2);
		course.setTutor(tutorCurrent);
		boolean b = courseRemoteService.create(course);
		if (b) {
			return "/trainer/my-courses?faces-redirect=true";
		}

		return "/trainer/NewTCours?faces-redirect=true";
	}
	

	public String logOut() {
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		currentUser = new Student();
		return "/views/login?faces-redirect=true";
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public CourseRemoteService getCourseRemoteService() {
		return courseRemoteService;
	}

	public void setCourseRemoteService(CourseRemoteService courseRemoteService) {
		this.courseRemoteService = courseRemoteService;
	}

	public List<Course> getCourseList() {
		return courseRemoteService.findAll();
	}

}
