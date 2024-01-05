
import org.eclipse.swt.*;
import org.eclipse.swt.widgets.*;
import org.eclipse.swt.events.*;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.*;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;

import java.util.List;
import java.util.*;
/*
* AUTHOR: Katana Bierman
* FILE: ConcentrationGameMain.java
* ASSIGNMENT: Programming Assignment 1 Concentration Game
* COURSE: CSc 335; Fall 2022
* PURPOSE: This program creates a graphical user interface that allows the
* user to play the game "Concentration" in which the goal is to match as many
* pairs of cards in the game.
*/ 
 
public class ConcentrationGameMain
{
	
	public static Text text;
	public static Text scoresText;
	public static Text[] allScoresTexts;
	public static int[] scores;
	
	public static void main(String args[]) {
		
		// makes start screen where users choose game settings
		StartScreen start = new StartScreen();
		boolean oneFlip = start.oneFlip;
		// trackers for scores
		scores = new int[start.players];
		allScoresTexts = new Text[start.players];
		int curPlayer = 0;
		
		if (start.gameStart) {
			Display display = new Display();
			List<Image> imList = new ArrayList<Image>();
			
			// make card decks
			if (start.cards.equals("Fruits")) {
				imList = makeFruitCardsList(display);
			} else {
				imList = makeAnimalCardsList(display);
			}
			
			// and a 'blank' image to simulate card flips
			Image blank = new Image(display, "blank.jpg");
			
			List<Image> blankCards = new ArrayList<Image>();
			for (int i=0; i<12; i++) {
				blankCards.add(blank);
			}
			
			Shell shell = new Shell(display);
			shell.setSize(500,600);
	
			shell.setLayout( new GridLayout());
	
			//---- our Widgets start here
			
			// displays text of current players
			text = new Text(shell, SWT.SHADOW_IN | SWT.READ_ONLY);
			text.computeSize(400, 100); 
			text.setFont(new Font(display, "Courier", 12, SWT.BOLD));
			 
			GridData data =  new GridData();
			data.horizontalAlignment = SWT.FILL;
			data.grabExcessHorizontalSpace = true;
			text.setLayoutData(data);
			
			// displays current player's number and score
			scoresText = new Text(shell, SWT.SHADOW_IN | SWT.READ_ONLY);
			scoresText.computeSize(400, 300); 
			scoresText.setFont(new Font(display, "Courier", 12, SWT.BOLD));
			scoresText.setLayoutData(data);
			
			// creates text[] to display all players' scores at end of game
			for (int i=0; i<start.players; i++) {
				Text scoresTextArr = new Text(shell, SWT.SHADOW_IN | SWT.READ_ONLY);
				scoresTextArr.computeSize(400, 300); 
				scoresTextArr.setFont(new Font(display, "Courier", 12, SWT.BOLD));
				scoresTextArr.setLayoutData(data);
				allScoresTexts[i] = scoresTextArr;
			}
			
			// creates canvas
			Composite upperComp = new Composite(shell, SWT.NO_FOCUS);
		    Composite lowerComp = new Composite(shell, SWT.NO_FOCUS);
		    Canvas canvas = new Canvas(upperComp, SWT.NONE);
			canvas.setSize(400,300);
			canvas.addPaintListener(new CanvasPaintListener(shell, blankCards));		
			canvas.addMouseListener(new CanvasMouseListener(scores ,shell, canvas, imList, blank, blankCards, display, curPlayer, oneFlip));
			
			// creates quit button
			Button quitButton = new Button(lowerComp, SWT.PUSH);
			quitButton.setText("Quit");
			quitButton.setSize(100, 50);
			quitButton.addSelectionListener(new ButtonSelectionListener());	   
			
			
			//shell.pack();
			shell.open();
			
			
			
			while( !shell.isDisposed())
			{
				if(!display.readAndDispatch())
					display.sleep();
				
				
			}
			 
			display.dispose();
		} 
	} 
	
	public static int turn(Canvas c) {
		
		return 0;
	}
	
	public static List<Image> makeFruitCardsList(Display display) {
		List<Image> imList = new ArrayList<Image>();
		
		Image apple = new Image(display, "apple.jpg");
		Image pear = new Image(display, "pear.jpg");
		Image peach = new Image(display, "peach.jpg");
		Image pineapple = new Image(display, "pineapple.jpg");
		Image avocado = new Image(display, "avocado.jpg");
		Image greenApple = new Image(display, "greenapple.jpg");
		
		imList.add(apple);
		imList.add(pear);
		imList.add(peach);
		imList.add(pineapple);
		imList.add(avocado);
		imList.add(greenApple);
		imList.add(apple);
		imList.add(pear);
		imList.add(peach);
		imList.add(pineapple);
		imList.add(avocado);
		imList.add(greenApple);
		
		Collections.shuffle(imList);
		return imList;
	}
	
	public static List<Image> makeAnimalCardsList(Display display) {
		List<Image> imList = new ArrayList<Image>();
		
		Image bear = new Image(display, "bear.jpg");
		Image chicken = new Image(display, "chicken.jpg");
		Image cow = new Image(display, "cow.jpg");
		Image dog = new Image(display, "dog.jpg");
		Image duck = new Image(display, "duck.jpg");
		Image cat = new Image(display, "cat.jpg");
		
		imList.add(bear);
		imList.add(cat);
		imList.add(chicken);
		imList.add(cow);
		imList.add(dog);
		imList.add(duck);
		imList.add(bear);
		imList.add(cat);
		imList.add(chicken);
		imList.add(cow);
		imList.add(dog);
		imList.add(duck);
		
		System.out.println(imList.get(0).equals((imList).get(6)));
		
		Collections.shuffle(imList);
		return imList;
	}

}

/**
 * mouse clicks in the canvas
 * 
 */
class CanvasMouseListener implements MouseListener 
{ 
	int[] scores;
	Shell shell;
    Canvas c;
    Image blank;
    Display display;
    List<Image> imList;
    List<Image> blankList;
    List<Integer> indexes;
    public List<Image> drawnCards;
    
	public Image flippedCard;
	int totalFlipped;
	int curPlayer;
	
	boolean oneFlip;
	
	
       
    public CanvasMouseListener(int[] sc, Shell sh, Canvas canvas, List<Image> im, Image blankImage, List<Image> b, Display d, int player, boolean oF)
    {   
    	scores = sc; shell = sh; imList = im; blank = blankImage; c = canvas; blankList = b; 
    	drawnCards = new ArrayList<Image>(); totalFlipped = 0; indexes = new ArrayList<Integer>(); 
    	display = d; curPlayer = player; oneFlip = oF;
    } 
    
	public void mouseDoubleClick(MouseEvent event){}
	
	public void mouseDown(MouseEvent event)
	{  
		Rectangle rect = c.getClientArea();
		ImageData data = imList.get(0).getImageData();
		int col = event.x/data.width;
		int row = event.y/data.height;
		int idx = col + row * (rect.width/data.width); 
		
		if (idx < imList.size() && blankList.get(idx).equals(blank)) {
			totalFlipped += 1;
			flippedCard = imList.get(idx);
			drawnCards.add(flippedCard);
			blankList.set(idx, imList.get(idx));
			indexes.add(idx); 
			
		} 
		c.redraw(); 
		c.update();
		
	}
	  
	public void mouseUp(MouseEvent e){
		if (curPlayer > ConcentrationGameMain.scores.length-1) {
			curPlayer = 0;
		}
		if (!gameOver(blankList)) {
			ConcentrationGameMain.text.setText("Current Player: " + (curPlayer + 1) + " Player's score: " + ConcentrationGameMain.scores[curPlayer] + "\n");
			turn(indexes, blankList, blank);
			
		} 
		else { 
			ConcentrationGameMain.scores[curPlayer] += 1;
			String gameOverStr = "GAME OVER! \n";
			ConcentrationGameMain.scoresText.setText(gameOverStr);
			for (int i=0; i<ConcentrationGameMain.scores.length; i++) { 
				ConcentrationGameMain.allScoresTexts[i].setText("Player " + (i+1) + "'s Final Score: " + ConcentrationGameMain.scores[i] + "\n");
			}
		}
	} 
	 
	private void turn(List<Integer> indexes, List<Image> blankList, Image blank) {
		if (drawnCards.size() == 2) {
			// detects a match
			if (drawnCards.get(0).equals(drawnCards.get(1))) {
				ConcentrationGameMain.scores[curPlayer] += 1;
				indexes.clear();
				drawnCards.clear();
				// changes player even after a match with one-flip
				if (oneFlip) {
					curPlayer += 1;
				}
			} 
			// sets cards blank again if they don't match
			else { 
				blankList.set(indexes.get(0), blank);
				blankList.set(indexes.get(1), blank);
				curPlayer += 1;
				drawnCards.clear();
				indexes.clear();
			}
		}
	}
	
	/**
	 * 
	 * @param blankList- a list of current images on the canvas
	 * @return - a boolean that is true if the game is over
	 */
	private boolean gameOver(List<Image> blankList) {
		for (Image i : blankList) {
			// returns false if there are still blank cards in the game
			if (i.equals(blank)) {
				return false;
			}
		}
		return true;
	}
}	


/**
 * repaints of the canvas
 * 
 */
class CanvasPaintListener implements PaintListener 
{
    Shell shell;
    List<Image> imList; // list of blank cards
    
    public CanvasPaintListener(Shell sh, List<Image> im)
    {
    	shell = sh; imList = im;
    }
    
	public void paintControl(PaintEvent event) 
	{ 
		
		Rectangle rect = shell.getClientArea();
		ImageData data = imList.get(0).getImageData();
		int stride = rect.width/data.width;
 
        for (int i = 0, j = 0; i < imList.size(); i++)
        	event.gc.drawImage(imList.get(i), (i%stride)*data.width, (i/stride)*data.height);
	}
}  

/**
 * Quit button
 * 
 */
class ButtonSelectionListener implements SelectionListener 
{
	public void widgetSelected(SelectionEvent event) {System.exit(0);}
	public void widgetDefaultSelected(SelectionEvent event){}    
}	
 