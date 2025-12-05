// Performance DFS to solve the N-Queens Problem
// Works up to N=31
// Debug & Clean & Comment 


// 2. clean up. 
// 3. Benchmark & Comment Code -> make it very readable
// 4. Update the algorithm so x is gradually increased until average > 1minute

// Solution is in an integer array format.

// Need to explain how the board is stored. long[] and how it works.
// How the operations work on it explaining bitwise operations

public class F422436 
{

    static long sizeMask;

    public static void main(String[] args) 
    { 
        // Variable Initialisation
        // n = N var for N-Queens, x = No. of times N-Queens will run for average.
        int n, x;
        // Timing variables.
        long totalTime = 0, startTime = 0, endTime = 0, averageTime = 0;

        // Try pull program args else set to a default val.
        try {
            n = Integer.parseInt(args[0]);
            x = Integer.parseInt(args[1]);
        } catch (Exception e) {
            n = 12;
            x = 1;
        } 

        // Run N-Queens X times and measure average performacne.
        for (int i = 0; i < x; i++)
        {
            startTime = System.currentTimeMillis();

            int[] solution = NQueens(n);

            endTime = System.currentTimeMillis();

            // Verify & Output Solution.
            VerifySolution(solution); // Will throw error and "crash program" if N-Queens failed to find solution.
            OutputState(solution, n);

            totalTime += endTime - startTime;

            System.out.println("N-Queens Run " + (i + 1) + ": Time Taken: " + (endTime - startTime) + " ms");
        }


        // Workout the average and output if it passes the performance check (ie executes in under a minute).
        averageTime = totalTime / x;

        System.out.println("N-Queens Average for N=" + n + ": " + averageTime + " ms.");

        if (averageTime <= 60000)
        {
            System.out.println("\u001B[32mN-Queens Average for N=" + n + ": Passed Performance Check\u001B[0m");
        } else 
        {
            System.err.println("\u001B[31mN-Queens Average for N=" + n + ": Failed Performance Check\u001B[0m");
        }

    }

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

    static int[] NQueens (int n) { 

        int[] positions = new int[n];

        // Setup the size mask to ensure it can work with any n not just 64.
        sizeMask = (1 << n) - 1; // Optimisation for n significantly smaller then 64 -> casts to int.
        if (n > 31) sizeMask = (1L << n) - 1;


        return dfs(positions, 0L, 0L, 0L, 0, n);
    }

    static int[] dfs (int[] pos, long col, long diag1, long diag2, int row, int N)
    {

        if (row == N)
        {
            // All queens have been placed. We only need 1 solution so return.
            return pos;
        } 
 
        // find nodes on frontier (all free spaces not attacked by queen)
        long free = ~(col | diag1 | diag2) & sizeMask;


        while (free != 0)
        {
            long currentPos = free & -free;
            free -= currentPos;


            // record the placement (will be overwritten if wrong)
            pos[row] = Long.numberOfTrailingZeros(currentPos);


            // Runs with current bitmasks + currentlyTesting Position
            int[] result = dfs(
                pos, 
                col | currentPos,  
                (diag1 | currentPos) << 1,
                (diag2 | currentPos) >> 1, 
                row + 1, // Depth
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
    
}
