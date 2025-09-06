//Don't forget about input validation and potential user errors!!!
import java.util.Scanner;
import java.util.Comparator;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.HashMap;
import java.io.IOException;
import java.io.FileWriter;
import java.io.FileReader;

public class TaskManager {
	static ArrayList<Task> tasks = new ArrayList<>();
	static Map<String, ArrayList<Task>> map = new HashMap<>();
	static int count = 0;
	static String loadFile;
    public static void main(String [] args) {
        Scanner keyboard = new Scanner(System.in);
        System.out.println("Welcome to your Task Manager, allowing you to organize your tasks.");
        System.out.println("Are you loading a task list or creating a new task list? Press 1 for loading and press 2 for new.");
        int taskList = keyboard.nextInt();
        try {
        	if(taskList == 1) {
        		System.out.println("Enter the name of the file that you would like to load? Include the txt extension");
        		String loadFile = keyboard.nextLine();
        		load(loadFile);
        	}
        }
        catch(Exception e) {
        	e.printStackTrace();
        }
        
        menu(keyboard, tasks);
    }
    private static void load(String loadFile) {
    	try(BufferedReader br = new BufferedReader(new FileReader(loadFile))) {
    		Task tempTask = new Task ();
        	String line;
    		while((line = br.readLine()) != null) {
    			String[] a = line.split(": ", 2);
    			
    			if(line.contains("Task ID:")) {
    				tempTask.setTaskID(a[1]);
    			}
    			else if(line.contains("Description:")) {
    				tempTask.setDescription(a[1].trim());
    			}
    			else if(line.contains("Category:")) {
    				tempTask.setCategory(a[1].trim());
    			}
    			else if(line.contains("Priority:")) {
    				tempTask.setPriority(Integer.parseInt(a[1].trim()));
    			}
    			else if(line.contains("Created Date:")) {
    				tempTask.setCreatedDate(ZonedDateTime.parse(a[1].trim()));
    			}
    			else if(line.contains("Due Date:")) {
    				tempTask.setDueDate(ZonedDateTime.parse(a[1].trim()));
    			}
    			else if(line.contains("Status: ")) {
    				if(a[1].equalsIgnoreCase("Completed")) {
    					tempTask.setIsCompleted(true);
    				}
    				else{
    					tempTask.setIsCompleted(false);
    				}
    			}
    			else if(line.contains("Note:")) {
    				tempTask.setExtraNotes(a[1]);
    			}
    			if(line.contains("__________________________________________")) {
    				tasks.add(tempTask);
    				tempTask = new Task();
    			}
    		}
    	}
    	catch(IOException e) {
    		e.printStackTrace();
    	}
    }
	public static void menu(Scanner keyboard, ArrayList<Task> tasks) {
        System.out.println("Enter 1 to add a task");
        System.out.println("Enter 2 to display tasks");
        System.out.println("Enter 3 to delete tasks");
        System.out.println("Enter 4 to update tasks");
        System.out.println("Enter 5 to save task list");
        int o = keyboard.nextInt();
        keyboard.nextLine();
        if(o == 1) {
            addTasks(keyboard, tasks);
        }
        else if(o == 2) {
            displayTasks(keyboard, tasks);
        }
        else if(o == 3) {
            removeTasks(keyboard, tasks);
        }
        else if(o == 4) {
        	updateTasks(keyboard, tasks);
        }
        else if(o == 5) {
        	saveTaskList(keyboard, tasks, loadFile);
        }
    }
    public static void addTasks(Scanner keyboard, ArrayList<Task> tasks) {
    	Task aTask = new Task();
    	aTask.setTaskID("T0" + count);
    	count++;
        System.out.println("Describe the task you would like to add: ");
        String description = keyboard.nextLine();
        aTask.setDescription(description);
        System.out.println("Category would you like to place the task in: ");
        String category = keyboard.nextLine();
        aTask.setCategory(category);
        System.out.println("Numerical priority you would like to assign to this task: ");
        int priority = keyboard.nextInt();
        keyboard.nextLine();
        aTask.setPriority(priority);
        ZonedDateTime createdDate = ZonedDateTime.now(ZoneId.systemDefault());
        aTask.setCreatedDate(createdDate);
        System.out.println("Does this task have a deadline?(Y/N) ");
        String deadline = keyboard.nextLine();
        if(deadline.equalsIgnoreCase("Y")) {
        	System.out.println("What is the deadline? Write in format: YYYY-MM-DDTHH:MM:SS ");
        	String due = keyboard.nextLine();
        	LocalDateTime lDueDate = LocalDateTime.parse(due);
        	ZonedDateTime zDueDate = lDueDate.atZone(ZoneId.systemDefault());
        	aTask.setDueDate(zDueDate);
        }
        aTask.getIsCompleted();
        System.out.println("Any additional information about this task?(Y/N) ");
        String addInfo = keyboard.nextLine();
        if(addInfo.equalsIgnoreCase("Y")) {
        	System.out.println("Enter your notes: ");
        	aTask.setExtraNotes(addInfo);
        }
        tasks.add(aTask);
        sortaMap(aTask);
        Collections.sort(tasks);
        System.out.println("Would you like to add another task? (Y/N)");
        String addTask = keyboard.nextLine();
        if(!addTask.equalsIgnoreCase("N")) {
        	addTasks(keyboard, tasks);
        }
        else {
        	System.out.println("Your task(s) has/have been added.");
        	System.out.println();
            menu(keyboard, tasks);
        }
    }
    public static void displayTasks(Scanner keyboard, ArrayList<Task> tasks) {
    	printTasks(keyboard);
        menu(keyboard, tasks);
    }
    public static void printTasks(Scanner keyboard) {
    	if(tasks.size() == 0) {
			System.out.println("There are no tasks in your list.");
			menu(keyboard, tasks);
		}
    	int n = 0;
    		System.out.println("Sort tasks by:");
    		System.out.println("   1. Priority");
    		System.out.println("   2. Category");
    		System.out.println("   3. Due Date");
    		
    		int sortOpt = keyboard.nextInt();
    		if(sortOpt == 1) {
    			Collections.sort(tasks);
    			for(Task pTask:tasks) {
					System.out.println((n + 1) + ". " + pTask.displayNotCategory());
					System.out.println("__________________________________________");
					System.out.println("");
					n++;
    			}
    			n = 0;
    		}
    		if(sortOpt == 2) {
    			for(String category : map.keySet()) {
    				ArrayList<Task> ctask = map.get(category);
    				System.out.println("Category: " + category);
    				ctask.sort(Comparator.comparing(Task::getCategory));
    				for(Task catTask:ctask) {
    					System.out.println((n + 1) + ". " + catTask.displayNotCategory());
    					System.out.println("__________________________________________");
    					System.out.println("");
    					n++;
    				}
    			}
    			n = 0;
    		}
    		if(sortOpt == 3) {
    			tasks.sort(Comparator.comparing(Task::getDueDate));
    			for(Task dTask:tasks) {
    				System.out.println((n + 1) + ". " + dTask.displayNotCategory());
    				System.out.println("__________________________________________");
    				System.out.println("");
    				n++;
    			}
    			n = 0;
    		}
    }
    
    public static void removeTasks(Scanner keyboard, ArrayList<Task> tasks) {
    	while(true) {
    		printTasks(keyboard);
    		System.out.println("Enter the task id, otherwise press q to quit: ");
    		String n = keyboard.nextLine().trim();
    		if(n.equalsIgnoreCase("q")) {
    			menu(keyboard, tasks);
    			return;
    		}
    		else {
    			Task delete = taskIDLookup(tasks, n);
    			if(delete == null) {
    				System.out.println("Task ID not found.");
    				continue;
    			}
    			ArrayList<Task> catl = map.get(delete.getCategory());
    			catl.remove(delete);
    			tasks.remove(delete);
    			System.out.println("Your task has been removed.");
    			if(catl.isEmpty()) {
    				map.remove(delete.getCategory());
    				System.out.println("Your category is empty and has been removed.");
    			}
    			continue;
    		}
    	}
    }
    public static void updateTasks(Scanner keyboard, ArrayList<Task> tasks) {
    	while(true) {
    		printTasks(keyboard);
    		keyboard.nextLine();
    		System.out.println("Enter the task id, otherwise press q to quit: ");
    		String n = keyboard.nextLine().trim();

    		if(n.equalsIgnoreCase("q")) {
    			menu(keyboard, tasks);
    			break;
    		}
    		else {
    			Task update = taskIDLookup(tasks, n);
    			if(update == null) {
    				System.out.println("Task ID not found.");
    				continue;
    			}
    			ArrayList<Task> catl = map.get(update.getCategory());
    			while(true) {
    				System.out.println("Enter a number from the corresponding options to update: ");
    				System.out.println("	1. Mark task as Complete");
    				System.out.println("	2. Change priority");
    				System.out.println("	3. Change due date");
    				System.out.println("	4. Change description");
    				System.out.println("	5. Modify notes");
    				System.out.println("	6. Leave to Menu");
    				
    				int option = keyboard.nextInt();
    				keyboard.nextLine();
    				
    				switch(option) {
    					case(1):
    						update.setIsCompleted(true);
    						System.out.println("Your task has been completed. Would you like to remove it?(Y/N)");
    						String cRemove = keyboard.nextLine();
    						if(cRemove.equalsIgnoreCase("Y")) {
    							catl.remove(update);
    							tasks.remove(update);
    							System.out.println("Your task has been removed.");
    							if(catl.isEmpty()) {
    								map.remove(update.getCategory());
    								System.out.println("Your category is empty and has been removed.");
    							}
    						}
    						break;
    				
    					case(2):
    						System.out.println("Enter the task's new numerical priority: ");
		    				int newPriority = keyboard.nextInt();
		    				keyboard.nextLine();
		    				update.setPriority(newPriority);
		    				System.out.println("Your priority has been updated.");
		    				break;
    				
    					case(3):
    						System.out.println("Enter the task's updated due date. Write in format: YYYY-MM-DDTHH:MM:SS");
		    				String newDue = keyboard.nextLine();
		    				LocalDateTime lnDeadline = LocalDateTime.parse(newDue);
		    				ZonedDateTime znDeadline = lnDeadline.atZone(ZoneId.systemDefault());
		    				update.setDueDate(znDeadline);
		    				System.out.println("Your due date has been updated.");
		    				break;
		    				
    					case(4):
    						System.out.println("Enter the task's new description: ");
    						String descrpt = keyboard.nextLine();
    						update.setDescription(descrpt);
    						System.out.println("Your description has been updated.");
    						break;
    					
    					case(5):
    						System.out.println("Enter the task's new notes: ");
							String newNotes = keyboard.nextLine();
							update.setExtraNotes(newNotes);
							System.out.println("Your extra notes have been updated.");
    						break;
    
    					case(6):
    						menu(keyboard, tasks);
    						return;
    				
    					default:
    						break;
    				}
    			}
    		}
    	}
    }
    
    public static void saveTaskList(Scanner keyboard, ArrayList<Task> tasks, String loadFile) {
    	String fileName;
    	System.out.println("Are you saving a loaded file?(Y/N)");
    	char saveLoad = keyboard.next().toUpperCase().charAt(0);
		if(saveLoad == 'Y') {
			fileName = loadFile;
		}
		else {
			keyboard.nextLine();
			System.out.println("Choose a name for your file:");
			String saveFile = keyboard.nextLine();
			fileName = saveFile + ".txt";
		}

    	try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))) {
				for(int i = 0; i < tasks.size(); i++) {
					bw.write("TaskID: " + tasks.get(i).getTaskID());
					bw.newLine();
					bw.write("Task: " + tasks.get(i).getDescription());
					bw.newLine();
					bw.write("Category: " + tasks.get(i).getCategory());
					bw.newLine();		
					bw.write("Priority: " + tasks.get(i).getPriority());
					bw.newLine();			
					bw.write("Created: " + tasks.get(i).getCreatedDate());
					bw.newLine();						
					if(tasks.get(i).getDueDate() != null) {
						bw.write("Due Date: " + tasks.get(i).getDueDate());
					}
					bw.newLine();
					if(tasks.get(i).getIsCompleted() == true) {
						bw.write("Status: Completed");
					}
					else {
						bw.write("Status: Not Completed");
					}
					bw.newLine();
					if(tasks.get(i).getExtraNotes() != null) {
						bw.write("Note: " + tasks.get(i).getExtraNotes());
					}
					bw.write("__________________________________________");
					bw.newLine();
				}
			}
    	catch(IOException e) {
    		e.printStackTrace();
    	}
    }
    
    public static void sortaMap(Task task) {
    	String cat = task.getCategory();
    	if(map.containsKey(cat)) {
    		map.get(cat).add(task);
    		Collections.sort(map.get(cat));
    	}
    	else {
    		ArrayList<Task> nTask = new ArrayList<>();
    		nTask.add(task);
    		map.put(cat, nTask);
    	}
    	
    }
    
    public static Task taskIDLookup(ArrayList<Task> tasks, String ID) {
    	for(int i = 0; i < tasks.size(); i++) {
    		if(ID.equals(tasks.get(i).getTaskID())) {
    			return tasks.get(i);
    		}
    	}
    	return null;
    }
}

