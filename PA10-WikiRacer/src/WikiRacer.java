import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/*
 * AUTHOR: Katana Bierman
 * FILE: WikiRacer.java
 * ASSIGNMENT: PA 10 WikiRacer
 * PURPOSE: This file finds the shortest path of links to follow from one Wiki page
 * to another. It uses a max priority queue to find the most common links between pages.
 */

public class WikiRacer {

	/*
	 * Do not edit this main function
	 */
	public static void main(String[] args) {
		List<String> ladder = findWikiLadder(args[0], args[1]);
		System.out.println(ladder);
	}

	/*
	 * This function finds the ladder from the start link to the end link
	 * in the least steps
	 * 
	 * param start: a string of the start link
	 * end: a string of the end link
	 * 
	 * return: a list of strings of the wiki ladder
	 */
	public static List<String> findWikiLadder(String start, String end) {
		// makes queue and scrapes start and end links
		MaxPQ queue = new MaxPQ();
		Set<String> endLinks = WikiScraper.findWikiLinks(end);
		Set<String> allLinks = new HashSet<String>();
		// enqueues start ladder to queue
		Ladder startLad = new Ladder(start, 0);
		queue.enqueue(startLad);
		// loops through queue for common links
		while (!queue.isEmpty()) { 
			// finds frontmost ladder
			Ladder curr = queue.dequeue();
			String page = curr.pages.get(curr.pages.size()-1);
			Set<String> pageLinks = WikiScraper.findWikiLinks(page);
			// returns list when end link is found
			if (pageLinks.contains(end)) {
				curr.pages.add(end);
				return curr.pages;
			} 
			pageLinks.parallelStream().forEach(link -> {
				WikiScraper.findWikiLinks(link);
			});
			// loops through links in current page
			for (String link : pageLinks) {
				if (!allLinks.contains(link)) {
					Ladder newLad = new Ladder(curr);
					newLad.pages.add(link);
					Set<String> linkSet = WikiScraper.findWikiLinks(link);
					// checks if current link has end in its links
					if (linkSet.contains(end)) {
						newLad.pages.add(end);
						return newLad.pages;
					}
					// enqueues new ladder if end is not found
					int newPrior = calcPriority(linkSet, endLinks);
					newLad.priority = newPrior;
					allLinks.add(link);
					queue.enqueue(newLad);
				}
			}
		}
		return new LinkedList<String>();
	}
	
	/*
	 * Determines the priority of a given page compared to the number of links shared with 
	 * the end page
	 * 
	 * return priority: an int of the page's priority
	 */
	private static int calcPriority(Set<String> curr, Set<String> end) {
		int priority = 0;
		for (String link : curr) {
			if (end.contains(link)) {
				priority += 1;
			}
		}
		return priority;
	}

}
