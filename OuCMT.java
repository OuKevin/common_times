import edu.truman.ou.kevin.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import java.util.Scanner;

/* Reads in a file and stores the times in three separate lists
 * Stores references to the lists in instance variables in each thread frame
 * and then starts the threads
 */
public class OuCMT
{
  /**Obtains a file name from the command line 
   * @param args the file name
   */
  public static void main(String[] args) 
  {
    String fileName = args[0]; //stores filename from command line input
    BufferedReader br = null;
    int currentLineNumber = 1; //the current line in file 
    ArrayList<Integer> firstList = new ArrayList<Integer>(); //lists with the meeting times
    ArrayList<Integer> secondList = new ArrayList<Integer>(); 
    ArrayList<Integer> thirdList = new ArrayList<Integer>(); 
    
    //reads the file and stores the times in the lists
    try{
	  String sCurrentLine;
	  br = new BufferedReader(new FileReader(fileName));
	  int i;
	  while ((sCurrentLine = br.readLine()) != null) {
	    if(currentLineNumber == 1)
	    {
	      Scanner sc = new Scanner(sCurrentLine);
	      
	      while(sc.hasNext())
	      {
			i = sc.nextInt();
			firstList.add(i);
	      }
	      currentLineNumber++;
	      sc.close();
	    } 
	    else if(currentLineNumber == 2)
	    {
	      Scanner sc = new Scanner(sCurrentLine);
	     
	      while(sc.hasNext())
	      {
			i = sc.nextInt();
			secondList.add(i);
	      }
	      currentLineNumber++;
	      sc.close();
	    }
	    else
	    {
	      Scanner sc = new Scanner(sCurrentLine);
	      
	      while(sc.hasNext())
	      {
			i = sc.nextInt();
			thirdList.add(i);
	      }
	      currentLineNumber++;
	      sc.close();
	    }
	  }
	} catch (IOException e) {
	    System.out.println("Could not open file");
	} finally {
	      try {
	    	 if (br != null) br.close();
		   } catch (IOException ex) {
		    ex.printStackTrace();
		    }
	}
    //creates arrays to hold the frames and threads
    int nthreads = firstList.get(0);
    TimeSearcher [] frame = new TimeSearcher[nthreads];
    Thread [] t = new Thread[nthreads];
    
    //creates the objects
    for(int i = 0; i < nthreads; i++)
    {
      frame[i] = new TimeSearcher();
    }
    
    //Gives each thread a time to search and the lists that it will search through
    for(int i = 0; i < nthreads; i++)
    {
    	frame[i].setTime(firstList.get(i+1));
    	frame[i].setSecondList(secondList);
    	frame[i].setThirdList(thirdList);
    	
    }
    
    //Creates the threads
    for(int i = 0; i < nthreads; i++)
    {
    	t[i] = new Thread(frame[i]);
    }   
    
    //Start the threads
    for(int i = 0; i < nthreads; i++)
    {
    	t[i].start();
    }
    
    //Join the threads
    for(int i = 0; i < nthreads; i++)
    {
    	try {
    		t[i].join();
    	} catch (InterruptedException e) {
    	}
    }
  }
}