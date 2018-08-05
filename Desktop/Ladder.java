/* Ladder.java
   CSC 225 - Summer 2018
   
   Starter code for Programming Assignment 3

   B. Bird - 06/30/2018
*/

import java.io.*;
import java.util.LinkedList;

public class Ladder{

	
	public static void showUsage(){
		System.err.printf("Usage: java Ladder <word list file> <start word> <end word>\n");
	}
	
	public static  boolean BFS_findPath(String v, String w, LinkedList<String> PathStack,LinkedList<String> wordList){
		int n=wordList.size();
        String word=wordList.get(n-1);
        StringBuffer end = new StringBuffer(w);
		StringBuffer sb = new StringBuffer(word);
		int x= end.length();
		for (int j = 0; j < end.length(); j++) {
            char find = end.charAt(j);
            char curr=sb.charAt(j);
            if(curr!=find){
                x--;
            }
        }
        if(x==end.length()-1){
            wordList.add(w);
            return true;
		}else{
        	word=wordList.get(n-1);
			while(!PathStack.isEmpty()){
					sb = new StringBuffer(word);
					for (int i = 0; i < sb.length(); i++) {
                		char temp=sb.charAt(i);
                		for (char c = 'a'; c <= 'z'; c++) {
                			if (temp == c) {
                        		continue;
                    		}
                    		sb.setCharAt(i, c);
                    		String newWord = sb.toString();
                    		if (PathStack.remove(newWord)) {
                        		wordList.add(newWord);
                        		word=newWord;
                        	//use back package to check the word I choose could reach end word or not.
                        		if(BFS_findPath(v,w,PathStack,wordList)==true){
                    				return true;
                    			}else{
                    				int m=wordList.size();
                    				wordList.remove(m-1);
                    				word=wordList.get(m-2);
                    			}
                        	}
                    	}
                		sb.setCharAt(i, temp);
                }
            }
          return false;  
        }
	}
	
	
	
	
	
	
	public static void main(String[] args){
		
		//At least four arguments are needed
		if (args.length < 3){
			showUsage();
			return;
		}
		String wordListFile = args[0];
		String startWord = args[1].trim();
		String endWord = args[2].trim();
		
		//Read the contents of the word list file into a LinkedList (requires O(nk) time for
		//a list of n words whose maximum length is k).
		//(Feel free to use a different data structure)
		BufferedReader br = null;
		LinkedList<String> words = new LinkedList<String>();
		
		try{
			br = new BufferedReader(new FileReader(wordListFile));
		}catch( IOException e ){
			System.err.printf("Error: Unable to open file %s\n",wordListFile);
			return;
		}
		
		try{
			for (String nextLine = br.readLine(); nextLine != null; nextLine = br.readLine()){
				nextLine = nextLine.trim();
				if (nextLine.equals(""))
					continue; //Ignore blank lines
				//Verify that the line contains only lowercase letters
				for(int ci = 0; ci < nextLine.length(); ci++){
					//The test for lowercase values below is not really a good idea, but
					//unfortunately the provided Character.isLowerCase() method is not
					//strict enough about what is considered a lowercase letter.
					if ( nextLine.charAt(ci) < 'a' || nextLine.charAt(ci) > 'z' ){
						System.err.printf("Error: Word \"%s\" is invalid.\n", nextLine);
						return;
					}
				}
				words.add(nextLine);
			}
		} catch (IOException e){
			System.err.printf("Error reading file\n");
			return;
		}

		/* Find a word ladder between the two specified words. Ensure that the output format matches the assignment exactly. */
			
		/* Your code here */
		
		//if cannot find start and end word, return.
		int find_start_words=words.indexOf(startWord);
		int find_end_words=words.indexOf(endWord);
		if( find_start_words==-1 || find_end_words==-1){
			System.out.print("No word ladder found.\n");
			return;
		}
		
		// if word length are diff, return.
		if(startWord.length()!=endWord.length()){
			System.out.print("No word ladder found.\n");
			return;
		}
		words.remove(find_start_words);
		find_end_words=words.indexOf(endWord);
		words.remove(find_end_words);
		LinkedList<String> Path = new LinkedList<String>();
		Path.add(startWord);
		BFS_findPath(startWord, endWord, words,Path);
		// if not find path, return
		int x= Path.size();
		if(!Path.get(x-1).equals(endWord)){
			System.out.print("No word ladder found.\n");
			return;
		}
		
		while(!Path.isEmpty()){
			System.out.print(Path.get(0));
			System.out.print("\n");
			Path.remove();
		}
		
	}

}