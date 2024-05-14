package WizardTD;

import java.util.*;

/**
 * Represents a pathfinding system in the WizardTD game using Breadth-First Search (BFS).
 */
public class Path {

    // BFS Node class
    /**
     * Inner class representing a node in the BFS traversal.
     */
    class Node {
        public int x, y;
        public Node parent;

        /**
         * Constructor for the Node class.
         *
         * @param x X-coordinate of the node.
         * @param y Y-coordinate of the node.
         */
        public Node(int x, int y) {
            this.x = x;
            this.y = y;
        }
        
        /**
         * Determines if two nodes are equal based on their x and y coordinates.
         *
         * @param o Object to compare with.
         * @return True if the nodes are equal, false otherwise.
         */
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Node node = (Node) o;
            return x == node.x && y == node.y;
        }

        /**
         * Generates a hash code for the node based on its x and y coordinates.
         *
         * @return Hash code of the node.
         */
        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }

    /**
     * Finds the shortest path from a start node to a goal node in a given grid using BFS.
     *
     * @param grid 2D array representing the game grid. A value of 0 indicates a walkable cell, and -1 indicates the goal.
     * @param start Starting node for the pathfinding.
     * @param goal Goal node for the pathfinding.
     * @return List of nodes representing the path from start to goal. Returns null if no path is found.
     */
    public List<Node> findPath(int[][] grid, Node start, Node goal) {
        Queue<Node> queue = new LinkedList<>();
        Set<Node> visited = new HashSet<>();
        queue.add(start);

        while (!queue.isEmpty()) {
            Node current = queue.poll();
            visited.add(current);

            if (current.equals(goal)) {
                List<Node> pathNodes = new ArrayList<>();
                while (current != null) {
                    pathNodes.add(current);
                    current = current.parent;
                }
                Collections.reverse(pathNodes);
                return pathNodes;
            }

            // Check neighbors
            for (int[] dir : new int[][]{{0, 1}, {1, 0}, {0, -1}, {-1, 0}}) {
                int newX = current.x + dir[0];
                int newY = current.y + dir[1];

                if (newX >= 0 && newX < grid[0].length && newY >= 0 && newY < grid.length) {
                    Node neighbor = new Node(newX, newY);
                    if (!visited.contains(neighbor) && !queue.contains(neighbor)) {
                        if (grid[newY][newX] == 0 || grid[newY][newX] == -1) {
                            neighbor.parent = current;
                            queue.add(neighbor);
                        }
                    }
                }
            }
        }
        return null; // No path found
    }
}