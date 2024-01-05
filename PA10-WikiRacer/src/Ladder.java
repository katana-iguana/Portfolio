import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/*
* AUTHOR: Katana Bierman
* FILE: Ladder.java
* ASSIGNMENT: Programming Assignment 10 WikiRacer
* COURSE: CSc 210; Spring 2022
* PURPOSE: This program creates a ladder object. Each ladder is used in this
* project as a connection of links in the form of a linked list of strings. 
* Each ladder is assigned a priority value which is used to determine its place
* in the queue.
 */

public class Ladder {
	
	public List<String> pages;
    public int priority;
    public int size;
   
    /*
     * Constructor of a ladder
     * 
     * param link: a string of the link
     * priority: an int of the priority
     */
    public Ladder(String link, int priority) {
        this.pages = new LinkedList<String>(); 
        this.pages.add(link);
        this.size = 1;
        this.priority = priority;
    }
    
    /*
     * Copy constructor of a ladder
     * 
     * param copy: a ladder object
     */
    public Ladder(Ladder copy) {
    	this.size = copy.size;
    	this.priority = copy.priority;
    	this.pages = new LinkedList<String>();
    	this.pages.addAll(copy.pages);
    }

	public String toString() {
		return pages.toString();
	}
}
