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
			String title = doc.getString("meetingName");
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
			String title = doc.getString("className");
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
			String title = doc.getString("teacherName");
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
			String title = doc.getString("meetingName");
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
			String title = doc.getString("meetingName");
			al.add(new HomeworkGO(id, title));
		}
		return al;
	}
	public String getMeetingName(MeetingGO mgo) {
		Document d = mdb.getMeetingById(mgo.getID());
		String name = (String)d.get("meetingName");
		return name;
	}
}
