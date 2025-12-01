// Performance DFS to solve the N-Queens Problem
// Works up to N=31
// Debug & Clean & Comment 
// Produce Naive Solution -> Benchmark 
// Implement for case >31 -> record and test 

public class F422436 
{

    static int sizeMask;

    public static void main(String[] args) 
    { 
        int n = Integer.parseInt(args[0]); // N-Queeens size 
        sizeMask = (1 << n) - 1;

        int x = 1; // Number of times n-queens will run.
        long totalTime = 0; // System.currentPosrentTimeMillis();

        for (int i = 0; i < x; i++)
        {
            // Time performance of N-Queens
            long startTime = System.currentTimeMillis();

            int[] solution = NQueens(n);

            long endTime = System.currentTimeMillis();

            // Verify & Output Solution.
            //VerifySolution(solution);
            OutputState(solution, n);

            totalTime += endTime - startTime;

            System.out.println("N-Queens Run " + (i + 1) + ": Time Taken: " + (endTime - startTime) + " ms");
        }

        long averageTime = totalTime / x;

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

        return dfs(positions, 0, 0, 0, 0, n);
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
    };
    
}
