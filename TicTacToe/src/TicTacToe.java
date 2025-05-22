import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class TicTacToe {
	int boardWidth = 750;
	int boardHeight = 700;
	
	JFrame frame = new JFrame("Tic-Tac-Toe");
	JLabel textLabel = new JLabel();
	JPanel textPanel = new JPanel();
	JPanel boardPanel = new JPanel();
	
	JButton[][] board = new JButton[3][3];
	String playerX = "X";
	String playerO = "O";
	String currentPlayer = playerX;
	
	boolean gameOver = false;
	
	int turns = 0;
	
	int scoreX = 0;
	int scoreO = 0;
	
	JPanel bottomPanel = new JPanel();
	JLabel scoreLabel = new JLabel();
	JButton resetBoardButton = new JButton("Reset Board");
	JButton resetFullButton = new JButton("Reset Full Game");
	
	TicTacToe()
	{
		frame.setVisible(true);
		frame.setSize(boardWidth, boardHeight);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		
		textLabel.setBackground(Color.darkGray);
		textLabel.setForeground(Color.white);
		textLabel.setFont(new Font("Arial", Font.BOLD, 50));
		textLabel.setHorizontalAlignment(JLabel.CENTER);
		textLabel.setText("Tic-Tac-Toe");
		textLabel.setOpaque(true);
		
		textPanel.setLayout(new BorderLayout());
		textPanel.add(textLabel);
		frame.add(textPanel, BorderLayout.NORTH);
		
		boardPanel.setLayout(new GridLayout(3, 3));
		boardPanel.setBackground(Color.darkGray);
		frame.add(boardPanel);
		
		bottomPanel.setLayout(new GridLayout(1, 3));
		bottomPanel.setBackground(Color.darkGray);
		
		scoreLabel.setForeground(Color.white);
		scoreLabel.setFont(new Font("Arial", Font.BOLD, 25));
		scoreLabel.setHorizontalAlignment(JLabel.CENTER);
		updateScoreLabel();
		
		resetBoardButton.setFont(new Font("Arial", Font.BOLD, 20));
		resetFullButton.setFont(new Font("Arial", Font.BOLD, 20));
		
		bottomPanel.add(scoreLabel);
		bottomPanel.add(resetBoardButton);
		bottomPanel.add(resetFullButton);
		frame.add(bottomPanel, BorderLayout.SOUTH);
		
		for(int row = 0; row < 3; row++)
		{
			for(int column = 0; column < 3; column++)
			{
				JButton tile = new JButton();
				board[row][column] = tile;
				boardPanel.add(tile);
				
				tile.setBackground(Color.darkGray);
				tile.setForeground(Color.white);
				tile.setFont(new Font("Arial", Font.BOLD, 120));
				tile.setFocusable(false);
//				title.setText(currentPlayer);
				
				tile.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if(gameOver) return;
						JButton tile = (JButton) e.getSource();
						if(tile.getText() == "")
						{
							tile.setText(currentPlayer);
							turns++;
							checkWinner();
							if(!gameOver)
							{
								currentPlayer = currentPlayer == playerX ? playerO : playerX;
								textLabel.setText(currentPlayer + "'s turn.");
							}
						}
					}
				});
			}
		}
		
		resetBoardButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				resetBoard();
			}
		});
		
		resetFullButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				resetFullGame();
				
			}
		});
		
	}
	
	void checkWinner() {
		//Checking for Horizontal(row).
		for(int row = 0; row < 3; row++)
		{
			if(board[row][0].getText() == "") continue;
			
			if(board[row][0].getText() == board[row][1].getText() &&
			   board[row][1].getText() == board[row][2].getText())
			{
				for(int i = 0; i < 3; i++)
				{
					setWinner(board[row][i], false);
				}
				updateScope();
				gameOver = true;
				return;
			}
		}
		
		// Checking for Vertical(column).
		for(int column = 0; column < 3; column++)
		{
			if(board[0][column].getText() == "") continue;
			
			if(board[0][column].getText() == board[1][column].getText() &&
			   board[1][column].getText() == board[2][column].getText())
			{
				for(int i = 0; i < 3; i++)
				{
					setWinner(board[i][column], false);
				}
				updateScope();
				gameOver = true;
				return;
			}
		}
		
		// Checking for Diagonally (\).
		if(board[0][0].getText() == board[1][1].getText() &&
		   board[1][1].getText() == board[2][2].getText() &&
		   board[0][0].getText() != "")
		{
			for(int i = 0; i < 3; i++)
			{
				setWinner(board[i][i], false);
			}
			
			updateScope();
			gameOver = true;
			return;
		}
		
		// Checking for Anti-Diagonally (/).
		if (board[0][2].getText() == board[1][1].getText() && board[1][1].getText() == board[2][0].getText()
				&& board[0][2].getText() != "") {
			setWinner(board[0][2], false);
			setWinner(board[1][1], false);
			setWinner(board[2][0], false);

			updateScope();
			gameOver = true;
			return;
		}
		
		if(turns == 9)
		{
			for(int row = 0; row < 3; row++)
			{
				for(int column = 0; column < 3; column++)
				{
					setTie(board[row][column]);
				}
			}
			gameOver = true;
		}
				
	}
	
	void setWinner(JButton tile, boolean updateScope)
	{
		tile.setForeground(Color.green);
		tile.setBackground(Color.gray);
		textLabel.setText(currentPlayer + " is the winner!");
	}
	
	void updateScope()
	{
		if(currentPlayer.equals(playerX))
		{
			scoreX++;
		}
		else
		{
			scoreO++;
		}
		updateScoreLabel();
	}
	
	void setTie(JButton tile)
	{
		tile.setForeground(Color.red);
		tile.setBackground(Color.gray);
		textLabel.setText("Tie!");
	}
	
	void updateScoreLabel()
	{
		scoreLabel.setText("Score - X: " + scoreX + "| O: " + scoreO);
	}
	
	void resetBoard() {
		for(int row = 0; row < 3; row++)
		{
			for(int column = 0; column < 3; column++)
			{
				board[row][column].setText("");
				board[row][column].setBackground(Color.darkGray);
				board[row][column].setForeground(Color.white);
			}
		}
		currentPlayer = playerX;
		textLabel.setText("Tic-Tac-Toe");
		gameOver = false;
		turns = 0;
	}
	
	void resetFullGame()
	{
		resetBoard();
		scoreX = 0;
		scoreO = 0;
		updateScoreLabel();
	}
}
