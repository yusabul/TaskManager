import java.time.*;
import java.util.Scanner;
public class Task implements Comparable<Task> {
	Scanner keyboard = new Scanner(System.in);
	private String taskid;
	private String description;
	private int priority;
	private ZonedDateTime createdDate;
	private ZonedDateTime dueDate;
	private boolean isCompleted;
	private String category;
	private String extraNotes;
	
	public Task() {
		this.taskid = null;
		this.description = null;
		this.category = null;
		this.priority = 0;
		this.createdDate = null;
		this.dueDate = null;
		this.isCompleted = false;
		this.extraNotes = null;
	}
	
	public Task(String taskid, String description, String category, int priority, ZonedDateTime createdDate, boolean isCompleted) {
		this.taskid = taskid;
		this.description = description;
		this.category = category;
		this.priority = priority;
		this.createdDate = createdDate;
		this.isCompleted = isCompleted;
	}
	
	public Task(String taskid, String description, int priority, ZonedDateTime createdDate, boolean isCompleted) {
		this.taskid = taskid;
		this.description = description;
		this.priority = priority;
		this.createdDate = createdDate;
		this.isCompleted = isCompleted;
	}
	
	public Task(String taskid, String description, String category, int priority, ZonedDateTime createdDate, boolean isCompleted, String extraNotes) {
		this.taskid = taskid;
		this.description = description;
		this.category = category;
		this.priority = priority;
		this.createdDate = createdDate;
		this.isCompleted = isCompleted;
		this.extraNotes = extraNotes;
	}
	
	public Task(String taskid, String description, int priority, ZonedDateTime createdDate, boolean isCompleted, String extraNotes) {
		this.taskid = taskid;
		this.description = description;
		this.priority = priority;
		this.createdDate = createdDate;
		this.isCompleted = isCompleted;
		this.extraNotes = extraNotes;
	}
	
	public Task(String taskid, String description, String category, int priority, ZonedDateTime createdDate, ZonedDateTime dueDate, boolean isCompleted) {
		this.taskid = taskid;
		this.description = description;
		this.category = category;
		this.priority = priority;
		this.createdDate = createdDate;
		this.dueDate = dueDate;
		this.isCompleted = isCompleted;
	}
	
	public Task(String taskid, String description, int priority, ZonedDateTime createdDate, ZonedDateTime dueDate, boolean isCompleted) {
		this.taskid = taskid;
		this.description = description;
		this.priority = priority;
		this.createdDate = createdDate;
		this.dueDate = dueDate;
		this.isCompleted = isCompleted;
	}
	
	public Task(String taskid, String description, String category, int priority, ZonedDateTime createdDate, ZonedDateTime dueDate, boolean isCompleted, String extraNotes) {
		this.taskid = taskid;
		this.description = description;
		this.category = category;
		this.priority = priority;
		this.createdDate = createdDate;
		this.dueDate = dueDate;
		this.isCompleted = isCompleted;
		this.extraNotes = extraNotes;
	}
	
	public Task(String taskid, String description, int priority, ZonedDateTime createdDate, ZonedDateTime dueDate, boolean isCompleted, String extraNotes) {
		this.taskid = taskid;
		this.description = description;
		this.priority = priority;
		this.createdDate = createdDate;
		this.dueDate = dueDate;
		this.isCompleted = isCompleted;
		this.extraNotes = extraNotes;
	}
	
	public void setTaskID(String taskid) {
		this.taskid = taskid;
	}
	
	public void setDescription(String description) {
		this.description = description; 
	}
	
	public void setCategory(String category) {
		this.category = category;
	}
	
	public void setPriority(int priority) {
		this.priority = priority;
	}
	
	public void setCreatedDate(ZonedDateTime createdDate) {
		this.createdDate = createdDate;
	}
	
	public void setDueDate(ZonedDateTime dueDate) {
		this.dueDate = dueDate;
	}
	
	public void setIsCompleted(boolean isCompleted) {
		this.isCompleted = isCompleted;
	}
	
	public void setExtraNotes(String extraNotes) {
		this.extraNotes = extraNotes;
	}
	
	public String getTaskID() {
		return taskid; 
	}
	
	public String getDescription() {
		return description; 
	}
	
	public String getCategory() {
		return category; 
	}
	
	public int getPriority() {
		return priority;
	}
	
	public ZonedDateTime getCreatedDate() {
		return createdDate;
	}
	
	public ZonedDateTime getDueDate() {
		return dueDate;
	}
	
	public boolean getIsCompleted() {
		return isCompleted;
	}
	
	public String getExtraNotes() {
		return extraNotes;
	}
	
	public String displayNotCategory() {
		String otask = "";
		otask += "TaskID: " + taskid + "\n";
		otask += "   Task: " + description + "\n";
		otask += "   Priority: " + priority + "\n";
		otask += "   Created: " + createdDate + "\n";
		if(dueDate != null) {
			otask += "   Due Date: " + dueDate + "\n";
			if(dueDate.isBefore(ZonedDateTime.now()) && !isCompleted) {
				otask += "     OVERDUE!!!\n";
			}
		}
		if(isCompleted) {
			otask += "   Status: Completed" + "\n";
		}
		else {
			otask += "   Status: Not Completed" + "\n";
		}
		if(extraNotes != null) {
			otask+= "   Note: " + extraNotes + "\n";
		}
		return otask;
	}

	public String toString() {
		String otask = "";
		otask += "TaskID: " + taskid + "\n";
		otask += "   Task: " + description + "\n";
		otask += "   Category: " + category + "\n";
		otask += "   Priority: " + priority + "\n";
		otask += "   Created: " + createdDate + "\n";
		if(dueDate != null) {
			otask += "   Due Date: " + dueDate + "\n";
			if(dueDate.isBefore(ZonedDateTime.now()) && !isCompleted) {
				otask += "     OVERDUE!!!\n";
			}
		}
		if(isCompleted) {
			otask += "   Status: Completed" + "\n";
		}
		else {
			otask += "   Status: Not Completed" + "\n";
		}
		if(extraNotes != null) {
			otask+= "   Note: " + extraNotes + "\n";
		}
		return otask;
	}
	
	@Override
	public int compareTo(Task o) {
		return Integer.compare(this.priority, o.getPriority());
	}
}
