/* Jonathan Collins 70-245
 * This program pulls in a list of words for a MadLibs text document. It  replaces the noted replaceable characters with
 * a random word from the list  of strings pulled from a file and then prints it out to be viewed.
 */

import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;
import java.util.Random;

class WordList{//creates word lists to place each of the replaceable words into.
	private ArrayList<String> nouns;
	private ArrayList<String> verbs;
	private ArrayList<String> name;
	private ArrayList<String> adj;
	private ArrayList<String> adv;
	private ArrayList<String> place;
	private Random rnd;
	public WordList(){
		nouns = new ArrayList<String>();
		verbs = new ArrayList<String>();
		name = new ArrayList<String>();
		adj = new ArrayList<String>();
		adv = new ArrayList<String>();
		place = new ArrayList<String>();
	}
	public void addWord(String wordType, String word){ //adds a word to the specified location depending on the word within the text file.
		if (wordType.equalsIgnoreCase("noun")) {
			nouns.add(word);
		} else if (wordType.equalsIgnoreCase("verb")){
			verbs.add(word);
		} else if (wordType.equalsIgnoreCase("name")){
			name.add(word);
		} else if (wordType.equalsIgnoreCase("adj")){
			adj.add(word);
		} else if (wordType.equalsIgnoreCase("adv")){
			adv.add(word);
		} else if (wordType.equalsIgnoreCase("place")){
			place.add(word);
		}
	}
	public String chooseRandomWord(String wordType) { //This is used within the replacement part of the program. It  randomly  selects
		int index; 									  //a word and returns it.
		if (wordType.equals("<noun>")) {
			index = rnd.nextInt(nouns.size());
			return nouns.get(index);
		} else if (wordType.equals("<verb>")) {
			index = rnd.nextInt(verbs.size());
			return verbs.get(index);
		} else if (wordType.equals("<name>")) {
			index = rnd.nextInt(name.size());
			System.out.println(name.get(index));
			return name.get(index);
		} else if (wordType.equals("<adj>")) {
			index = rnd.nextInt(adj.size());
			return adj.get(index);
		} else if (wordType.equals("<adv>")) {
			index = rnd.nextInt(adv.size());
			return adv.get(index);
		} else if (wordType.equals("<place>")) {
			index = rnd.nextInt(place.size());
			return place.get(index);
		} else {
			return "";
		}
	}
}
class MadLibsFileReader{//this has two different read functions. One to read in the words, the other to read in the MadLib.
	public void readFileWords(String fname){
		Scanner sc;
		String line;
		String[] parts;
		String wordIOType, wordIO;
		WordList w = new WordList();
		try{
			sc = new Scanner(new File(fname));
			while (sc.hasNextLine()) {
				line = sc.nextLine();
				parts = line.split(" ");
				wordIOType = parts[0];
				wordIO = parts[1];
				w.addWord(wordIOType,wordIO);
			}
		} catch (FileNotFoundException e) {
			System.out.println("File could not be found.");
		} catch (Exception e) {
			System.out.println("Something went wrong.");
		}	
		
	}
	public void readFileML(String fname){
		Scanner sc;
		String line;
		MadLibs m = new MadLibs();
		try {
			sc = new Scanner(new File(fname));
			while (sc.hasNextLine()){
				line = sc.nextLine();
				m.addLine(line);
			}
		} catch(FileNotFoundException e) {
			System.out.println("File could not be found.");
		} catch (Exception e) {
			System.out.println("Something went wrong.");
		}
	}
}
class MadLibs {
	public ArrayList<String> lines;
	public MadLibs(){
		lines = new ArrayList<String>();
	}
	public void addLine(String line) { 
		lines.add(line);
	}
	public ArrayList<String> getArrayList(){
		return lines;
	}
}
class MadLibsGenerator{
	public ArrayList<String> generateStory(ArrayList<String> lines){
		ArrayList<String> results = new ArrayList<String>();
		WordList wl = new WordList();
		for (String c : lines){
			System.out.println(c);
			c.replace("<noun>",wl.chooseRandomWord("<noun>"));
			c.replace("<verb>",wl.chooseRandomWord("<verb>"));
			c.replace("<name>",wl.chooseRandomWord("<name>"));
			c.replace("<adj>",wl.chooseRandomWord("<adj>"));
			c.replace("<adv>",wl.chooseRandomWord("<adv>"));
			c.replace("<noun>",wl.chooseRandomWord("<noun>"));
			results.add(c);
		}
		return results;
	}
}
class MadLibsOutput{
	public void greetPerson(){
		System.out.println("****");
		System.out.println("Welcome to Madlibs-OO");
		System.out.println("****");
		System.out.println("This is a computerized version of the classic\nMadlibs game. You will enter the name of a file");
		System.out.println("containing a story with certain words removed, as\nwell as a file containing lists of different kinds");
		System.out.println("of words. We'll randomly substitute words for each\nof the placeholders. Hilarity will ensue\n\n");
	}
	public void readFileWords(){
		MadLibsFileReader mlfr = new MadLibsFileReader();
		String fileName;
		System.out.println("Please enter the name of the file containing the substitution words: ");
		Scanner scan = new Scanner(System.in);
		fileName = scan.nextLine();
		mlfr.readFileWords(fileName);
	}
	public void readFileMadLib(){
		MadLibsFileReader mlfr = new MadLibsFileReader();
		String fileName;
		System.out.println("Please enter the name of the file containing the story: ");
		Scanner scan = new Scanner(System.in);
		fileName = scan.nextLine();
		mlfr.readFileML(fileName);
	}
}
public class MadLibsMain_Collins{
	public void run(){//Running.
		ArrayList<String> generatedStory;
		MadLibsOutput mlo = new MadLibsOutput();
		MadLibsGenerator mlg = new MadLibsGenerator();
		MadLibs ml = new MadLibs();
		mlo.greetPerson();
		mlo.readFileWords();
		mlo.readFileMadLib();
		System.out.println(ml.getArrayList());
		generatedStory = mlg.generateStory(ml.getArrayList());
		System.out.println(generatedStory);
	}
	
	public static void main(String[] args) {
		MadLibsMain_Collins me = new MadLibsMain_Collins();
		me.run();
		
	}
}		


