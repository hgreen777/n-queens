// DFS to solve the N-Queens Problem for 25COB107
/*
The following explains the Data structures, input and output for the N-Queens algorithm, this is the same as for section a.
Data Structure: 
    Assessing what locations are available (empty spaces not under threat by a queen) and for storing a “state”, longs are used as the data structure within the algorithm. This allows for 64bits (in java standards) of data to be used to store a state. 
    A “state” is stored through keeping track of columns, diagonals (\) and anti-diagonals (/) not a full board. The solutions array of integer is the best representation of the board and location of a queen.
    This works by when a queen is placed in column x, bit x in column’s long is set to 1. Similarly, the x’th bit in diagonal and anti-diagonal is also set to 1 for column x. 
    When making the next recursive call, and going down a row in the DFS, the diagonal long is shifted by 1 bit, this will mean when checking available spaces on the next row (due to the shifting) it will show the available columns not under threat by a diagonal. 
    Furthermore, this means using bitwise operations the nodes on the frontier which are not invalid states can be found simply by combining the column and diagonal bitmasks (including the size bitmask). 
    The size bitmask ensures that the for n < 64 where the whole long will not be used, the algorithm still works as all bits up to n will be 1.

Input: 
    The input to this algorithm is simply N, the algorithm will generate/instantiate any necessary further inputs.
Output: 
    The output of the N-Queens will be an array of size n (also where each cell is associated to a row), where each cell will contain an int which is the location (column) of the queen for that row. 
    This can be processed into a more human-readable format by going through the whole array and putting it into format (cell entry, row (cell index)). 
    For the java program, the output function has been expanded to include a visual representation of the board with the queens placed in it, so solutions can be verified manually as well as in the program to ensure they are right (a screenshot example is in section c).  
    Eg for n=4 : Queen Positions (x,y): (1, 0), (3, 1), (0, 2), (2, 3). The positions for the board are 0-indexed.

*/

public class F422436 
{
    /* 
    *********************************

        RUN BENCHMARKING FOR ALGORITHM

    *********************************
    */
    public static void main(String[] args) 
    { 
        int n, x; // n = N var for N-Queens, x = No. of times N-Queens will run for average.
        
        // Timing variables.
        long totalTime = 0, startTime = 0, endTime = 0, averageTime = 0, instanceTime = 0, minTime = 0, maxTime = 0;

        // Try pull program args else set to a default val.
        try {
            if (args.length == 2)
            {
                n = Integer.parseInt(args[0]);
                x = Integer.parseInt(args[1]);
            } else
            {
                n = Integer.parseInt(args[0]);
                x = 1;
            }
        } catch (Exception e) {
            n = 12;
            x = 1;
        } 

        // Will exit program if n will not work in program.
        if (n > 63) throw new IllegalArgumentException("n must be <= 63 (max supported size due to bitmask limits)");

        // Run N-Queens X times and measure average performacne.
        for (int i = 0; i < x; i++)
        {
            startTime = System.nanoTime();

            int[] solution = NQueens(n);

            endTime = System.nanoTime();

            // Verify & Output Solution.
            VerifySolution(solution); // Will throw error and "crash program" if N-Queens failed to find solution.
            OutputState(solution, n);

            instanceTime = endTime - startTime;
            totalTime += instanceTime;

            // Keep track of minimum and maximum execution time.
            if (i == 0) 
            {
                maxTime = instanceTime;
                minTime = instanceTime;
            }
            else if (instanceTime > maxTime)
            {
                maxTime = instanceTime;
            }

            if (instanceTime < minTime)
            {
                minTime = instanceTime;
            }


            System.out.println("N-Queens Run " + (i + 1) + ": Time Taken: " + instanceTime + " ns");
        }


        averageTime = totalTime / x;

        System.out.println("N-Queens Average for N=" + n + ": " + averageTime + " ns. Minimum Time: " + instanceTime + "ns. Maximimum Time : " + maxTime + "ns.");


        if (averageTime <= 60000000000L) // check if smaller then 1 min in nanoseconds. 
        {
            System.out.println("\u001B[32mN-Queens Average for N=" + n + ": Passed Performance Check\u001B[0m"); // Changes colour of terminal (didn't work when testing on windows cmd)
        } else 
        {
            System.err.println("\u001B[31mN-Queens Average for N=" + n + ": Failed Performance Check\u001B[0m");
        }

    }

    /* 
    *********************************

        N-Queens Algorithm

    *********************************
    */
    static long sizeMask;

    static int[] NQueens (int n) { 

        int[] solution = new int[n];

        // Setup the size mask to ensure it can work with any n not just 64. w/ slight optimisation for small n, cast to int.
        sizeMask = (n < 32) ? (1 << n) - 1 : (1L << n) - 1;


        return dfs(solution, 0L, 0L, 0L, 0, n);
    }

    static int[] dfs (int[] solution, long col, long diag1, long diag2, int depth, int N)
    {

        if (depth == N) return solution; // All Queens placed so return 1st solution.
 
        // find nodes on frontier (all free spaces not attacked by queen)
        long free = ~(col | diag1 | diag2) & sizeMask;


        while (free != 0)
        {
            long currentPos = free & -free; // Isolate LSB.
            free -= currentPos; // Set node as visited. 


            // record the placement (will be overwritten if wrong). depth == row.
            solution[depth] = Long.numberOfTrailingZeros(currentPos);


            // Runs with current bitmasks + currently testing Position
            int[] result = dfs(
                solution,
                col | currentPos,  
                (diag1 | currentPos) << 1, // Shifting diagonals so when checking on next row correct columns are blocked.
                (diag2 | currentPos) >> 1, 
                depth + 1, 
                N
            );

            if (result != null)
            {
                return result;
            }
        }

        // Error -> Unsolvable? Will cause error and program to crash.
        return null;
    } 

    /* 
    *********************************

        Driver Functions for verifying & outputting algorithm.

    *********************************
    */
    static void OutputState(int[] pos, int N) 
    {
        for (int y = 0; y < pos.length; y++)
        {
            for (int x = 0; x < N; x++)
            {
                if (pos[y] == x)
                {
                    System.out.print("|X|");
                }
                else
                {
                    System.out.print("| |");
                }
            }
            System.out.println();
        }

        System.out.print("Queen Positions (x,y): ");
        for (int i = 0; i < pos.length; i++)
        {
            System.out.print("(" + pos[i] + ", " + i + "), ");
        }

        System.out.println();
    };
    
    static void VerifySolution (int[] s) 
    {
        for (int i = 0; i < (s.length - 1); i++)
        {
            for (int j = i+1; j < s.length; j++)
            {
                // Check if two queens are in the same column
                if (s[i] == s[j]) {
                    throw new IllegalStateException("Queens in the same column at rows " + i + " and " + j);
                }
                // Check if two queens are in the same diagonal
                if (Math.abs(s[i] - s[j]) == Math.abs(i - j)) {
                    throw new IllegalStateException("Queens in the same diagonal at rows " + i + " and " + j);
                }
            }
        }
    };
}
