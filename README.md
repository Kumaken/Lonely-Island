# Lonely-Island
Divide and Conquer: Depth-First Search

Implementing DFS to directed graph to find dead-end vertexes.
Enumeration of possible moves are done in tandem with DFS process, so DFS does not simply end when a solution is find.

Interesting hurdle passed:
1. I just discovered that Java has a very small stack memory size. This does not allow many recursive function calls.
One way to get around this is through multi-threading & setting Java stack size to 1MB using console commands.
2. Also because memory is limited, less parameter recursive function is better. Therefore, a lot of variables are made global
to prevent redundant parameter calls. This, I admit is not a good practice, but it does the job.
3. Anticipate large input numbers. Instead of using adjacency matrix, use adjacency dynamic lists!
