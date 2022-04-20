import java.io.*;
public class Solver
{
	public static void main(String[] a)
	{
		Number[][] grid;
		if(a.length<1)
		{
			System.out.println("Usage:\njava Solver puzzleFilename");
			return;
		}
		for(String f: a)
		{
			grid=grid(f);
			if(grid==null)
				continue;
			System.out.println(f);
			print(grid);
			if(!solvable(grid))
				System.out.println("Error: your puzzle is impossible after this point!");
			print(grid);
		}
	}
	public static Number[][] grid(String f)
	{
		BufferedReader br;
		int[] lines=new int[9];
		Number[][] grid=new Number[9][9];
		int rMax, cMin, cMax;
		try
		{
			br=new BufferedReader(new FileReader(f));
		}
		catch(FileNotFoundException e)
		{
			System.out.println("Error: "+f+" is not a real file!");
			return null;
		}
		for(int i=0; i<9; i++)
			try
			{
				lines[i]=Integer.parseInt(br.readLine().replaceAll("[^0-9]", ""));
			}
			catch(IOException e)
			{
				System.out.println("Error: line "+(i+1)+" is not a valid sudoku row!");
				return null;
			}
			catch(NullPointerException e)
			{
				System.out.println("Error: file only has "+i+" rows, sudoku puzzles require 9!");
				return null;
			}
			catch(NumberFormatException e)
			{
				System.out.println("Error: row "+(i+1)+" is blank, please fill in its columns!");
				return null;
			}
		for(int r=0; r<9; r++)
			for(int c=8; c>-1; c--)
			{
				grid[r][c]=new Number(lines[r]%10);
				lines[r]/=10;
			}
		for(int r=0; r<9; r++)
			for(int c=0; c<9; c++)
			{
				if(grid[r][c].value()>0)
				{
					for(int row=0; row<9; row++)
						if(r!=row&&grid[r][c].value()==grid[row][c].value())
						{
							System.out.println("Error: ("+r+", "+c+") and ("+row+", "+c+") are both "+grid[r][c].value());
							return null;
						}
					for(int col=0; col<9; col++)
						if(c!=col&&grid[r][c].value()==grid[r][col].value())
						{
							System.out.println("Error: ("+r+", "+c+") and ("+r+", "+col+") are both "+grid[r][c].value());
							return null;
						}
					rMax=r/3*3+3;
					cMin=c/3*3;
					cMax=cMin+3;
					for(int row=rMax-3; row<rMax; row++)
						for(int col=cMin; col<cMax; col++)
							if((r!=row||c!=col)&&grid[r][c].value()==grid[row][col].value())
							{
								System.out.println("Error: ("+r+", "+c+") and ("+row+", "+col+") are both "+grid[r][c].value());
								return null;
							}
				}
			}
		for(int r=0; r<9; r++)
			for(int c=0; c<9; c++)
				remove(grid, grid[r][c].value(), r, c);
		return grid;
	}
	public static void remove(Number[][] grid, int value, int row, int col)
	{
		int rMax=row/3*3+3, cMin=col/3*3, cMax=cMin+3;
		for(int r=0; r<9; r++)
			grid[r][col].remove(value);
		for(int c=0; c<9; c++)
			grid[row][c].remove(value);
		for(int r=rMax-3; r<rMax; r++)
			for(int c=cMin; c<cMax; c++)
				grid[r][c].remove(value);
	}
	public static void becomes(Number[][] g1, Number[][] g2)
	{
		for(int r=0; r<9; r++)
			for(int c=0; c<9; c++)
				g1[r][c]=new Number(g2[r][c]);
	}
	public static void print(Number[][] grid)
	{
		String dashes="-------------";
		System.out.println(dashes);
		for(int r=0; r<9; r++)
		{
			System.out.print("|");
			for(int c=0; c<9; c++)
			{
				System.out.print(grid[r][c]);
				if((c+1)%3==0)
					System.out.print("|");
			}
			System.out.println();
			if((r+1)%3==0)
				System.out.println(dashes);
		}
		System.out.println();
	}
	public static boolean solvable(Number[][] grid)
	{
		boolean change=true;
		while(change)
		{
			change=false;
			for(int r=0; r<9; r++)
				for(int c=0; c<9; c++)
					if(grid[r][c].shouldSet())
					{
						grid[r][c].set();
						remove(grid, grid[r][c].value(), r, c);
						change=true;
					}
					else if(!grid[r][c].valid())
						return false;
			/*if(!change)//recursion
			{
				Number[][] temp=new Number[9][9];
				becomes(temp, grid);
				for(int r=0; !change&&r<9; r++)
					for(int c=0; !change&&c<9; c++)
						if(temp[r][c].unresolved())
						{
							change=true;
							temp[r][c].set();
							remove(temp, temp[r][c].value(), r, c);
							if(solvable(temp))
								becomes(grid, temp);
							else
								grid[r][c].remove(temp[r][c].value());
						}
			}*/
		}
		return true;
	}
}
