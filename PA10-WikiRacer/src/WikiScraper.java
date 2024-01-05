import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
 * AUTHOR: Katana Bierman
 * FILE: WikiScraper.java
 * ASSIGNMENT: PA 10 WikiRacer
 * PURPOSE: This file takes in the link of a Wikipedia page and creates
 * a set of all links within the given page. It scrapes the HTML for valid links.
 * This file uses memoization with the usedLinks variable of a hashMap data type.
 * 
 */

public class WikiScraper {

	private static Map<String, Set<String>> usedLinks = new HashMap<String, Set<String>>();
	
	/*
	 * This function returns the set of links represented by strings 
	 * within a given wiki page link as a set.
	 * 
	 * param link: a string of the link name
	 * 
	 * return links: a string set of all wiki links on a given
	 * wiki page
	 */
	public static Set<String> findWikiLinks(String link) {
		// returns a string of html from wiki link
		if (usedLinks.containsKey(link)) {
			return usedLinks.get(link);
		}
		String html = fetchHTML(link);  
		// returns links in page 
		Set<String> links = scrapeHTML(html);
		usedLinks.put(link, links);
		
		return links;
	}
	
	/*
	 * This function uses a given link of a wiki page and 
	 * returns a string of the html of that URL. It uses the 
	 * getURL(String link) method below it to determine the URL to
	 * open. 
	 * 
	 * param link: a string of the link name
	 *  
	 * return: a string of the given page's html
	 */
	private static String fetchHTML(String link) {
		StringBuffer buffer = null;
		try {
			// makes new URL object
			URL url = new URL(getURL(link));
			// opens URL content
			InputStream is = url.openStream();
			int ptr = 0;
			buffer = new StringBuffer();
			// adds data from URL to string buffer
			while ((ptr = is.read()) != -1) {
			    buffer.append((char)ptr);
			}
		} 
		// checks for exceptions when getting html of page
		catch (Exception ex) {
			System.out.println(ex.getMessage());
			ex.printStackTrace();
		}
		return buffer.toString();
	}
	
	/*
	 * Returns a string of the URL of a given wiki
	 * page's link name as a string
	 * 
	 * return: a string of the page's URL
	 * 
	 */
	private static String getURL(String link) {
		return "https://en.wikipedia.org/wiki/" + link;
	}
	
	/*
	 * Adds all links within the html of a wiki page as a set
	 * of strings. 
	 * 
	 * param html: a string of the html of a wiki page
	 * 
	 * return linkSet: a string HashSet where each element is the name of 
	 * a link in the given html.
	 *   
	 */ 
	private static Set<String> scrapeHTML(String html) {
		Set<String> linkSet = new HashSet<String>();
		if (html.length() == 0) {
			return linkSet;
		}  
		
		int commandOpen = html.indexOf('<');
		
		if (html.length() < commandOpen + 15) {
				return linkSet;
		}
		// loops through string from first command opening
		for (int i=commandOpen; i<html.length()-15; i++) {
			// finds link commands
			if (html.substring(i, i + 15).equals("<a href=\"/wiki/")) {
				for (int j=i+15; j<html.length(); j++) {
					if (html.charAt(j) == ':' || html.charAt(j) == '#') {
						break;
					}
					// adds valid article names to set
					if (html.charAt(j) == '"') {
						linkSet.add(html.substring(i+15, j));
						break;
					}
				}
			}
		}
		return linkSet;
	}
}
