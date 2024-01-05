import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

class WikiRacerTests {

	@Test
	void fruitToStrawberry() {
		System.out.println("Fruit to Strawberry");
		System.out.println(WikiRacer.findWikiLadder("Fruit", "Strawberry"));
		System.out.println();
	}
	
	@Test
	void milkshakeToGene() {
		System.out.println("Milkshake to Gene");
		System.out.println(WikiRacer.findWikiLadder("Milkshake", "Gene"));
		System.out.println();
	}
	 
	@Test
	void emuToStanford() {
		System.out.println("Emu to Stanford");
		System.out.println(WikiRacer.findWikiLadder("Emu", "Stanford_University"));
		System.out.println();
	}
	
	@Test
	void johnMayertoMichaelPhelps() {
		System.out.println("John Mayer to Michael Phelps");
		System.out.println(WikiRacer.findWikiLadder("John_Mayer", "Michael_Phelps"));
		System.out.println();
	}
	
	@Test
	void bananaToBanana() {
		System.out.println("Banana to Banana");
		System.out.println(WikiRacer.findWikiLadder("Banana", "Banana"));
		System.out.println();
	}


}
