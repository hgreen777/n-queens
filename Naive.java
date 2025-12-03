// Performance DFS to solve the N-Queens Problem
// Works up to N=31
// Debug & Clean & Comment 
// Produce Naive Solution -> Benchmark 
// Implement for case >31 -> record and test 

// Solution is in an integer array format.

import java.util.LinkedList;
import java.util.Queue;

public class Naive 
{
    
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
        boolean[][] board = new boolean[n][n];

        return dfs(positions, board, 0, n);
    }

    static int[] dfs (int[] pos, boolean[][] board, int row, int N)
    {

        if (row == N)
        {
            // All queens have been placed. We only need 1 solution so return.
            return pos;
        } 
        
        // find adjacent nodes (nodes on frontier) (ie valid nodes)
        Queue<Integer> freePositions = new LinkedList<>();
        for (int col = 0; col < N; col++) {
            boolean isSafe = true;

            // Check all previous rows for conflicts
            for (int prevRow = 0; prevRow < row; prevRow++) {
                int prevCol = pos[prevRow];
                if (prevCol == col || // Same column
                    prevCol - prevRow == col - row || // Same diagonal
                    prevCol + prevRow == col + row) { // Same anti-diagonal
                    isSafe = false;
                    break;
                }
            }

            if (isSafe) {
                freePositions.add(col);
            }
        }



        // for all adjacent nodes 
        while (!freePositions.isEmpty())
        {
            int currentPos = freePositions.poll();


            // record the placement (will be overwritten if wrong)
            pos[row] = currentPos;
            board[row][currentPos] = true;

            //dfs newNode, depth + 1 
            int[] result = dfs(pos, board, row +1, N);

            if (result != null)
            {
                return result;
            } else 
            {
                board[row][currentPos] = false;
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
