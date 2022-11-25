import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.io.*;

// enum for task status
enum TaskStatus {
    NEW,
    DONE
}

// Task class with constructors and getter, setter
class Task {

    private String title;
    private Date dueDate;
    private TaskStatus status;
    private String description;

    public Task(String title, Date dueDate, String description) {
        this.title = title;
        this.dueDate = dueDate;
        this.status = TaskStatus.NEW;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Task \n[\n title=" + title + "\n dueDate=" + dueDate + "\n status=" + status + "\n description="
                + description
                + "\n]\n";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((title == null) ? 0 : title.hashCode());
        result = prime * result + ((dueDate == null) ? 0 : dueDate.hashCode());
        result = prime * result + ((status == null) ? 0 : status.hashCode());
        result = prime * result + ((description == null) ? 0 : description.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Task other = (Task) obj;
        if (title == null) {
            if (other.title != null)
                return false;
        } else if (!title.equals(other.title))
            return false;
        if (dueDate == null) {
            if (other.dueDate != null)
                return false;
        } else if (!dueDate.equals(other.dueDate))
            return false;
        if (status != other.status)
            return false;
        if (description == null) {
            if (other.description != null)
                return false;
        } else if (!description.equals(other.description))
            return false;
        return true;
    }
}

// question does not specify anything, this program uses title for
// tasks operations like removing, marking as done and so on
class TaskStore {
    // Using arraylist to store tasks
    private ArrayList<Task> tasks;
    // expected date format
    private static final String DATE_FORMAT = "dd-MM-yyyy";

    public TaskStore() {
        tasks = new ArrayList<>();
    }

    public TaskStore(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    // function to add new task
    public void addTask(String title, String dueDate, String description) {
        if (title == "") {
            // tile cannot be empty, since that is what we later use to do task operations
            System.out.println("Add Task Failed: title cannot be empty");
            return;
        }
        Date date;
        try {
            SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
            date = formatter.parse(dueDate);
        } catch (Exception e) {
            System.out.println("Add Task Failed: not a valid date format");
            return;
        }
        this.tasks.add(new Task(title, date, description));
        System.out.println("Add Task Success");
    }

    // function to mark task done by its title
    public void markTaskDoneByTitle(String title) {
        boolean done = false;
        for (Task t : this.tasks) {
            if (t.getTitle().equals(title)) {
                t.setStatus(TaskStatus.DONE);
                done = true;
            }
        }
        if (done) {
            System.out.println("Mark Task Done Success");
        } else {
            System.out.println("Mark Task Done Failed: Task Not Found");
        }

    }

    // function to remove task by its title
    public void removeTaskByTitle(String title) {
        boolean done = false;
        Iterator<Task> itr = this.tasks.iterator();
        while (itr.hasNext()) {
            Task t = (Task) itr.next();
            if (t.getTitle().equals(title)) {
                itr.remove();
                done = true;
            }
        }
        if (done) {
            System.out.println("Remove Task Success");
        } else {
            System.out.println("Remove Task Failed: task not found");
        }
    }

    // function to edit task by its title
    public void editTaskByTitle(String title, String newTitle, String newDueDate, String newDescription) {
        boolean done = false;
        for (Task t : this.tasks) {
            if (t.getTitle().equals(title)) {
                if (newDueDate != "") {
                    Date date;
                    try {
                        SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
                        date = formatter.parse(newDueDate);
                    } catch (Exception e) {
                        System.out.println("Edit Task Failed: not a valid date format");
                        return;
                    }
                    t.setDueDate(date);
                }
                if (newTitle != "") {
                    t.setTitle(newTitle);
                }
                if (newDescription != "") {
                    t.setDescription(newDescription);
                }
                done = true;
            }
        }
        if (done) {
            System.out.println("Edit Task Success");
        } else {
            System.out.println("Edit Task Failed: task not found");
        }
    }

    // function to display all tasks
    public void listTask() {
        for (Task t : this.tasks) {
            System.out.print(t);
        }
    }

}

// TodoMain class contains main function
public class Main {
    public static void displayMenu() {
        System.out.println("Enter 1 to Display Menu");
        System.out.println("Enter 2 to Add New Task");
        System.out.println("Enter 3 to Mark a Task Done");
        System.out.println("Enter 4 to Remove a Task");
        System.out.println("Enter 5 to Edit a Task");
        System.out.println("Enter 6 to Display All Tasks");
        System.out.println("Enter 7 to Exit");
    }

    public static void main(String[] args) throws IOException {
        TaskStore TasksList = new TaskStore();
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int mo = 1;
        while (mo != 7) {
            switch (mo) {
                case 1: {
                    displayMenu();
                    break;
                }
                case 2: {
                    System.out.println("Title");
                    String title = br.readLine();
                    System.out.println("DueDate (format dd-MM-yyyy)");
                    String date = br.readLine();
                    System.out.println("Description");
                    String description = br.readLine();
                    TasksList.addTask(title, date, description);
                    break;
                }
                case 3: {
                    System.out.println("Enter Title of Task to Mark Done");
                    String title = br.readLine();
                    TasksList.markTaskDoneByTitle(title);
                    break;
                }
                case 4: {
                    System.out.println("Enter Title of Task to Remove");
                    String title = br.readLine();
                    TasksList.removeTaskByTitle(title);
                    break;
                }
                case 5: {
                    System.out.println("Enter Title of Task to Edit ( leave empty for fields not to update)");
                    String title = br.readLine();
                    System.out.println("New Title");
                    String newTitle = br.readLine();
                    System.out.println("New DueDate (format dd-MM-yyyy)");
                    String date = br.readLine();
                    System.out.println("New Description");
                    String description = br.readLine();
                    TasksList.editTaskByTitle(title, newTitle, date, description);
                    break;
                }
                case 6: {
                    TasksList.listTask();
                    break;
                }
                default: {
                    System.out.println("Not a Valid Input");
                }
            }
            boolean valid = false;
            // ask until user gives a valid input
            while (!valid) {
                String moStr = br.readLine();
                try {
                    mo = Integer.parseInt(moStr);
                    valid = true;
                } catch (Exception e) {
                    System.out.println("Not a Valid Input");
                }
            }

        }
    }
}