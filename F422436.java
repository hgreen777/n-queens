// Performance DFS to solve the N-Queens Problem
// Works up to N=31
// Debug & Clean & Comment 
// Produce Naive Solution -> Benchmark 
// Implement for case >31 -> record and test 
// Update the algorithm so x is gradually increased until average > 1minute

// 1. Implement BitMask Solution to work with an arbitrary n>31.
// 2. Verify all solutions and clean up. 
// 3. Benchmark & Comment Code -> make it very readable
// 4. Update the algorithm so x is gradually increased until average > 1minute

// Solution is in an integer array format.

// Need to explain how the board is stored. long[] and how it works.
// How the operations work on it explaining bitwise operations

public class F422436 
{

    static long sizeMask;
    
    // Timing Variables
    static int x = 1; // Number of time N-Queens will run.
    static long totalTime = 0, startTime = 0, endTime = 0;
    static long averageTime = 0;
    

    public static void main(String[] args) 
    { 
        int n = Integer.parseInt(args[0]); // N-Queeens size 

        for (int i = 0; i < x; i++)
        {
            // Time performance of N-Queens
            startTime = System.currentTimeMillis();

            int[] solution = NQueens(n);

            endTime = System.currentTimeMillis();

            // Verify & Output Solution.
            //VerifySolution(solution);
            OutputState(solution, n);

            totalTime += endTime - startTime;

            System.out.println("N-Queens Run " + (i + 1) + ": Time Taken: " + (endTime - startTime) + " ms");
        }


        // Workout the average and output if it passes the performance check (ie executes in under a minute).
        averageTime = totalTime / x;

        System.out.println("N-Queens Average for N=" + n + ": " + averageTime + " ms");

        if (averageTime <= 60000)
        {
            System.out.println("\u001B[32mN-Queens Average for N=" + n + ": Passed Performance Check\u001B[0m");
        } else 
        {
            System.err.println("\u001B[31mN-Queens Average for N=" + n + ": Failed Performance Check\u001B[0m");
        }

    }

    //static void VerifySolution (State s) {};

    static int[] NQueens (int n) { 
        int[] positions = new int[n];

        // Setup the size mask to ensure it can work with any n not just 32.
        int boardCount = ((int) Math.ceil(n / 32)) + 1; // how many 32 (long is 32 bits)
        sizeMask = (1L << n) - 1;


        return dfs(positions, 0L, 0L, 0L, 0, n);
    }

    static int[] dfs (int[] pos, long col, long diag1, long diag2, int row, int N)
    {

        if (row == N)
        {
            // All queens have been placed. We only need 1 solution so return.
            return pos;
        } 
        
        // find adjacent nodes (nodes on frontier) (ie valid nodes)
        long free = ~(col | diag1 | diag2) & sizeMask;


        // for all adjacent nodes 
        while (free != 0)
        {
            long currentPos = free & -free;
            free -= currentPos;


            // record the placement (will be overwritten if wrong)
            pos[row] = Long.numberOfTrailingZeros(currentPos);

            //dfs newNode, depth + 1 
            int[] result = dfs(pos, col | currentPos,
               (diag1 | currentPos) << 1,
               (diag2 | currentPos) >> 1, row +1, N);

            if (result != null)
            {
                return result;
            }
        }

        // Error -> Unsolvable? Return empty array.
        //return new int[N];
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
    };
    
}
