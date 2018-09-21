package controller;

import java.util.ArrayList;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.mongodb.client.MongoCursor;

import database.ClassDBO;
import database.ExamDBO;
import database.GenericDBO;
import database.HomeworkDBO;
import database.MeetingDBO;
import gui.ClassGO;
import gui.ExamGO;
import gui.GenericGO;
import gui.HomeworkGO;
import gui.MeetingGO;

public class Controller {
	
	private MeetingDBO mdb;
	private ClassDBO cdb;
	private ExamDBO edb;
	private GenericDBO gdb;
	private HomeworkDBO hdb;
	
	private final String MEETING_NAME_KEY = "meetingName";
	private final String CLASS_NAME_KEY = "className";
	private final String EXAM_NAME_KEY = "examName";
	private final String GENERIC_NAME_KEY = "eventName";
	private final String HOMEWORK_NAME_KEY = "homeworkName";

	
	public Controller() {
		this.mdb = new MeetingDBO();
		this.cdb = new ClassDBO();
		this.edb = new ExamDBO();
		this.gdb = new GenericDBO();
		this.hdb = new HomeworkDBO();
	}
	
	public ArrayList<MeetingGO> getAllMeetings(String userName){
		MongoCursor<Document> c = mdb.getMeetingsByUsername(userName);
		ArrayList<MeetingGO> al = new ArrayList<MeetingGO>();
		Document doc;
		if(c == null) {
			return al;
		}
		while(c.hasNext()) {
			doc  = c.next();
			ObjectId oid = (ObjectId)doc.get("_id");
			String id = oid.toString();
			String title = doc.getString(MEETING_NAME_KEY);
			al.add(new MeetingGO(id, title));
		}
		return al;
	}
	
	public ArrayList<ClassGO> getAllClasses(String userName){
		MongoCursor<Document> c = cdb.getAllClasses(userName);
		ArrayList<ClassGO> al = new ArrayList<ClassGO>();
		Document doc;
		if(c == null) {
			return al;
		}
		while(c.hasNext()) {
			doc  = c.next();
			ObjectId oid = (ObjectId)doc.get("_id");
			String id = oid.toString();
			String title = doc.getString(CLASS_NAME_KEY);
			al.add(new ClassGO(id, title));
		}
		return al;
	}
	
	public ArrayList<ExamGO> getAllExams(String userName){
		MongoCursor<Document> c = edb.getAllExams(userName);
		ArrayList<ExamGO> al = new ArrayList<ExamGO>();
		Document doc;
		if(c == null) {
			return al;
		}
		while(c.hasNext()) {
			doc  = c.next();
			ObjectId oid = (ObjectId)doc.get("_id");
			String id = oid.toString();
			String title = doc.getString(EXAM_NAME_KEY);
			al.add(new ExamGO(id, title));
		}
		return al;
	}
	
	public ArrayList<GenericGO> getAllGenerics(String userName){
		MongoCursor<Document> c = gdb.getAllEvents(userName);
		ArrayList<GenericGO> al = new ArrayList<GenericGO>();
		Document doc;
		if(c == null) {
			return al;
		}
		while(c.hasNext()) {
			doc  = c.next();
			ObjectId oid = (ObjectId)doc.get("_id");
			String id = oid.toString();
			String title = doc.getString(GENERIC_NAME_KEY);
			al.add(new GenericGO(id, title));
		}
		return al;
	}
	
	public ArrayList<HomeworkGO> getAllHomeworks(String userName){
		MongoCursor<Document> c = hdb.getHomeworkByUsername(userName);
		ArrayList<HomeworkGO> al = new ArrayList<HomeworkGO>();
		Document doc;
		if(c == null) {
			return al;
		}
		while(c.hasNext()) {
			doc  = c.next();
			ObjectId oid = (ObjectId)doc.get("_id");
			String id = oid.toString();
			String title = doc.getString(HOMEWORK_NAME_KEY);
			al.add(new HomeworkGO(id, title));
		}
		return al;
	}
	
	public GenericGO addEvent(String name, String date, String time, String duration) {
		
		return null;
	}
	
	public ClassGO addEvent(ClassGO cgo) {
		return null;
	}
	
	public String getMeetingName(MeetingGO mgo) {
		Document d = mdb.getMeetingById(mgo.getID());
		String name = (String)d.get(MEETING_NAME_KEY);
		return name;
	}
	
	// Adding Events - Joe
	
	// Return ID of data in database
	public String addEventToDatabase(GenericGO event) {
		
		// TODO : Add event to database
		
		return "0";
	}
	
	// End Joe

}
