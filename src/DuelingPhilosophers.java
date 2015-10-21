/**
 *
 * @author Evan Glazer
 *
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class DuelingPhilosophers {

    public Scanner sc;

    public static final int MAXCOUNT = 1000000;

    public void TopoSort() throws Exception
    {
        sc = new Scanner( System.in ); 
        
      /*
        try
        {
            sc = new Scanner(new File("src/duelingphilosophers.judge.txt"));
        }
        catch(FileNotFoundException e)
        {
            System.out.println("Sorry, input file was not found!  Exiting...");
            System.exit(0);
        } 
         */
        
        
        // it's a count of all the essays that essay i needs to come before it.
        int beforeEssay[] = new int[MAXCOUNT];
        
        // Using hash set so we dont have to worry about cycles because
        // Hash sets dont take in duplicates
        // List of all the essays that essay i links with
        HashSet<Integer> linkSet[] = new HashSet[MAXCOUNT];
        
        // Initialize the links so we can have them made
        for( int i=0; i<MAXCOUNT; i++ )
        {
            linkSet[i] = new HashSet<Integer>();
        }
        
        // This will be a list of all of the essays with 0 
        // That means that they could come next in then ordering.
        LinkedList<Integer> zeros = new LinkedList<Integer>();
        
        // loop that waits for the break of 00
        for(;;)
        {
            // n = # of essays 1-1000
            // m = # of relationships between essays caused by terms sharing terms
            int n = sc.nextInt();
            int m = sc.nextInt();
           
            // if n has 0 essays then were done!
            if( n == 0 ) 
                break;
            
            // Create all the essays and set them to null/0
            for( int i=0; i<n; i++ )
            {
                beforeEssay[i] = 0;
                linkSet[i].clear();
            }
            

            // d links to u
            for(int i=0; i<m; i++)
            {

                int d = sc.nextInt()-1;
                int u = sc.nextInt()-1;                
                
                //increase u value
                ++beforeEssay[u];   
                // add the defined term linking with the one used in
                linkSet[d].add(u);           
            }
            
            // get the set of zeros
            zeros.clear();
            for( int i=0; i<n; i++ ) 
            {
                if( beforeEssay[i]==0 ) 
                        zeros.add( i );
            }
            
            // change return value, that if we find otherwise.
            int retVal = 1;
            
            // We should be able to find a spot for every essay.
            for( int i=0; i<n; i++ )
            {
                // If there are no essays with zero needs,
                if( zeros.isEmpty() )
                {
                    retVal = 0;
                    break;
                }
                else if( zeros.size() > 1 )
                {
                    retVal = 2;
                }
                
                // get the next essay
                int essay = zeros.removeFirst();
                
                // Look at all of the essays that this one linsk with
                for( Integer j : linkSet[essay] )
                {
                    // decrease j value
                    --beforeEssay[j];
                    if( beforeEssay[j]==0 )
                    {
                        zeros.add( j );
                    }
                }
            }
            
            System.out.println(retVal);
        }
    }
    
    public static void main( String[] args ) throws Exception
    {
        new DuelingPhilosophers().TopoSort();
    }
}