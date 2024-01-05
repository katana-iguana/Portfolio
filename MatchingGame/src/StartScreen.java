

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

/*
* AUTHOR: Katana Bierman
* FILE: StartScreen.java
* ASSIGNMENT: Programming Assignment 1 Concentration Game
* COURSE: CSc 335; Fall 2022
* PURPOSE: This program creates a StartScreen object. This object creates a 
* new shell where the user selects the number of players, card deck to play with,
* and "one-flip" which, if selected, means the turn changes after each 
* player regardless if they get a match. Otherwise, a player keeps
* taking turns until they don't make a match.
*/

public class StartScreen {
	
	public boolean gameStart = false;
	public  int players = 0;
	public  String cards = "";
	public boolean oneFlip = false;
	private Display display;
	
	/**
	 * Constructor for StartScreen object.
	 */
	public StartScreen() {
		display = new Display();
		Shell shell = new Shell(display);
		shell.setSize(500,600);
		shell.setLayout( new GridLayout());
		
		// creates buttons for selection options
		playerSelect(shell); 
		cardChoice(shell);
		oneFlipChoice(shell);
		startButton(shell);
		 
		shell.pack();
		shell.open();
		while( !shell.isDisposed())
		{
			if(!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();
		 
	}
	
	/**
	 * Creates the start button on shell.
	 * @param shell - the shell display object.
	 */
	private void startButton(Shell shell) {
		Group startB = new Group(shell, SWT.NONE);
        startB.setLayout(new RowLayout(SWT.VERTICAL));
        
		Button start = new Button(startB, SWT.PUSH | SWT.CENTER);
		start.setText("Start");
		start.setSize(100, 50);
		
		start.addSelectionListener(new StartButtonListener());
	}
	
	/**
	 * This method makes the buttons to select the number of players.
	 * @param shell - the shell object
	 */  
	private void playerSelect(Shell shell) {
		Label p = new Label(shell, SWT.BORDER);
		p.setText("How many players? ");
		
		Group players = new Group(shell, SWT.NONE);
        players.setLayout(new RowLayout(SWT.VERTICAL));
        
        // players can choose between 2-4 players.
		Button two = new Button(players, SWT.RADIO);
		Button three = new Button(players, SWT.RADIO);
		Button four = new Button(players, SWT.RADIO);
		 
		two.setSize(100, 50);
		three.setSize(100, 50);
		four.setSize(100, 50);
		 
		two.addSelectionListener(new PlayerButtonListener());
		three.addSelectionListener(new PlayerButtonListener());
		four.addSelectionListener(new PlayerButtonListener());

		two.setText("2 players");
		three.setText("3 players");
		four.setText("4 players");
	}
	
	/**
	 * This method makes the buttons to select the card deck.
	 * @param shell - the shell object
	 */
	private void cardChoice(Shell shell) {
		Label c = new Label(shell, SWT.BORDER);
		c.setText("Which card deck? ");
		
		Group cards = new Group(shell, SWT.NONE);
        cards.setLayout(new RowLayout(SWT.VERTICAL));
		
        // players can choose between a deck of fruits and a deck of animals
		Button fruits = new Button(cards, SWT.RADIO);
		Button animals = new Button(cards, SWT.RADIO);
		
		fruits.setSize(100, 50);
		animals.setSize(100, 50);
		 
		fruits.addSelectionListener(new CardsButtonSelectionListener());
		animals.addSelectionListener(new CardsButtonSelectionListener());

		fruits.setText("Fruits");
		animals.setText("Animals");
	} 
	
	/**
	 * Creates one-flip option button.
	 * @param shell- the shell object
	 */
	private void oneFlipChoice(Shell shell) {
		Label f = new Label(shell, SWT.BORDER);
		f.setText("One-Flip?");
		
		Button oneFlip = new Button(shell, SWT.CHECK);
		
		oneFlip.setSize(100, 50);
		
		oneFlip.addSelectionListener(new OneFlipButtonListener());
		
		
	}
	
	/**
	 * Creates action when start button is selected.
	 *
	 */
	class StartButtonListener implements SelectionListener {
		
		@Override
		public void widgetSelected(SelectionEvent e) {
			// verifies all fields have been selected and starts game
			if (!cards.equals("")) {
				gameStart = true;
				display.dispose();
			}
		}

		@Override
		public void widgetDefaultSelected(SelectionEvent e) {}
		
	}
	
	/**
	 * Creates action when one-flip button is selected.
	 *
	 */
	class OneFlipButtonListener implements SelectionListener {

		@Override
		public void widgetSelected(SelectionEvent e) {
			Button b = (Button) e.getSource();
			oneFlip = b.getSelection();
		}

		@Override
		public void widgetDefaultSelected(SelectionEvent e) {}
		
	}
	
	/**
	 * Creates action when player button is selected.
	 *
	 */
	class PlayerButtonListener implements SelectionListener {
		
		@Override
		public void widgetSelected(SelectionEvent e) {
			Button b = (Button) e.getSource();
			if (b.getText() == "2 players") {
				players = 2;
			} else if (b.getText() == "3 players") {
				players = 3;
			} else {
				players = 4;
			}
		}

		@Override
		public void widgetDefaultSelected(SelectionEvent e) {}
		
	}
	
	/**
	 * Creates action when card button is selected.
	 *
	 */
	class CardsButtonSelectionListener implements SelectionListener {
		
		@Override
		public void widgetSelected(SelectionEvent e) {
			Button b = (Button) e.getSource();
			cards = b.getText();
		}

		@Override
		public void widgetDefaultSelected(SelectionEvent e) {}
		
	}
	 
}
 

 

