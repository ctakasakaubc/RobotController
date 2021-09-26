package chapter3;

import java.util.ArrayList;

public class Maze {
	private final int maze[][];
	private int startPosition[] = {-1,-1};
	
	public Maze(int maze[][]) {
		this.maze = maze;
	}
	
	public int[] getStartPosition() {
		// if we already know, just tell them
		if(this.startPosition[0] != -1 && this.startPosition[1] != -1)
			return this.startPosition;
		// or else we find it
		int startPosition[] = {0,0};
		
		for(int i=0; i<this.maze.length; i++) {
			for(int j=0; j<this.maze[i].length; j++) {
				if(this.maze[j][i] == 2) {
					this.startPosition = new int[] {i,j};
					return this.startPosition;
				}
			}
		}
		// or, in the darkest hour, return the origin
		return startPosition;
	}
	
	public int getPositionValue(int x, int y) {
		if(x < 0 || y < 0 || x >= this.maze.length || y>= this.maze[0].length) {
			return 1;
		}
		return this.maze[y][x];
	}
	
	public boolean isWall(int x, int y) {
		return (this.getPositionValue(x, y) == 1);
	}
	
	public int getMaxX() {
		return this.maze[0].length - 1;
	}
	
	public int getMaxY() {
		return this.maze.length - 1;
	}
	
	// score a robot's route... yikes that was redundant
	public int scoreRoute(ArrayList<int[]> route) {
		int score = 0;
		boolean visited[][] = new boolean[this.getMaxY() + 1][this.getMaxX() + 1];
		
		for(Object routeStep : route) {
			int step[] = (int[])routeStep;
			if(this.maze[step[1]][step[0]] == 3 && visited[step[1]][step[0]] == false) {
				score++;
				visited[step[1]][step[0]] = true;
			}
		}
		return score;
	}
}
