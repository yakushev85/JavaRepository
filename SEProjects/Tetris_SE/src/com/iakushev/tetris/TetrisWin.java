package com.iakushev.tetris;

import java.awt.event.*;
import java.awt.*;
import java.io.File;
import java.util.Formatter;
import java.util.Scanner;
import javax.swing.*;
import javax.swing.table.AbstractTableModel;

public class TetrisWin extends JFrame implements ActionListener {
	private final static long serialVersionUID = 0001;
	private final int MAX_SCOREUSERS = 20;
	private final String TEXT_TITLE = "TETRIS";
	private final String TEXT_STARTBTN = "Start game";
	private final String TEXT_PAUSEOFF = "Pause game";
	private final String TEXT_PAUSEON = "Resume game";
	private final String TEXT_EXITBTN = "Exit&Save";
	private final String TEXT_BACKBTN = "Restart";
	private final String FILE_WITH_SCORES = "scores.dat";
	private final String TEXT_SCORE = "Score: ";
	private final String[] TEXT_COLS = {"Name","Score"};
	private final String DEFAULT_NAME = "NONAME";
	private final String TEXT_DLGNAME = "Enter name:";
	private final String TEXT_TABLESCORE = "Table of scores!";
	
	private JPanel pnlIntro, pnlGame, pnlScore;
	private GameCanvas gameCanvas;
	private GameSpace gameLogSpace;
	private JButton btnStart, btnPause, btnExit, btnBack;
	private JPanel pnlMain;
	private CardLayout crdMain;
	private DataUserScore[] userScore;
	
	class DataUserScore {
		private String playerName;
		private int playerScore;
		
		DataUserScore() {
			playerName = DEFAULT_NAME;
			playerScore = 0;
		}
		
		public void setDataUser(String name,int scr) {
			if (name != null) playerName = ((name.trim() !=  ""))?name:DEFAULT_NAME;
			playerScore = scr;
		}
		
		public void setDataUser(int scr) {
			playerName = DEFAULT_NAME;
			playerScore = scr;
		}
		
		public String getPlayerName() {
			return playerName;
		}
		
		public int getPlayerScore()	{
			return playerScore;
		}
	}
	
	private DataUserScore[] sortedScores(DataUserScore[] uScore) {
		DataUserScore[] res = uScore;
		for (int i=0;i<MAX_SCOREUSERS-1;i++) {
			for (int j=i+1;j<MAX_SCOREUSERS;j++)
				if (res[i].getPlayerScore()<res[j].getPlayerScore()) {
					DataUserScore tmp = res[i];
					res[i] = uScore[j];
					res[j] = tmp;
				}
		}
		return res;
	}
	
	private DataUserScore[] loadScoresFromFile(String filename) {
		DataUserScore[] res = new DataUserScore[MAX_SCOREUSERS];
		
		try	{
			Scanner scn = new Scanner(new File(filename));
			int index = 0;
			
			while (scn.hasNext()) {
				String pName = scn.next();
				int pScore = scn.nextInt();
				
				if (index<MAX_SCOREUSERS) {
					res[index] = new DataUserScore();
					res[index].setDataUser(pName, pScore);
					index++;
				}
			}
			
			while (index<MAX_SCOREUSERS) {
				res[index] = new DataUserScore();
				index++;
			}
			
			scn.close();
		} catch (Exception e) {
			for (int i=0;i<MAX_SCOREUSERS;i++) res[i] = new DataUserScore();
		}
		
		return res;
	}
	
	private void saveScoresToFile(DataUserScore[] uScores,String filename) throws Exception {
		Formatter fmt = new Formatter(new File(filename));
		
		for (int i=0;i<MAX_SCOREUSERS;i++) {
			fmt.format("%S%n%d%n", uScores[i].getPlayerName(), uScores[i].getPlayerScore());
		}
		
		fmt.close();
	}

	private void showScoreDialog() {
		int minIndex = 0;
		
		for (int i=0;i<MAX_SCOREUSERS;i++)
			if (userScore[i].getPlayerScore() < userScore[minIndex].getPlayerScore())
				minIndex = i;

		String nameCurPlayer = JOptionPane.showInputDialog(this, TEXT_DLGNAME);
		
		if (userScore[minIndex].getPlayerScore() < gameLogSpace.getLinesScore()) {
			userScore[minIndex].setDataUser(nameCurPlayer, gameLogSpace.getLinesScore());
			userScore = sortedScores(userScore);
			try {
				saveScoresToFile(userScore,FILE_WITH_SCORES);
			} catch (Exception e) {
				JOptionPane.showMessageDialog(this, "IOException in file "+FILE_WITH_SCORES+"!");
			}
		}
	}
	
	class GameCanvas extends JPanel implements Runnable {
		private final static long serialVersionUID = 0001;
		final int MAX_TIME_PAUSE = 500;
		final int DT_TIME_PAUSE = 20;
		final int DIV_WIDTH = 27;
		final int LINES_PER_LEVEL = 20;
		
		private int maxWidth,maxHeight,sizeCub;
		private Thread t;
		private boolean isPause;
		private GameSpace gameSpace;
		
		GameCanvas(GameSpace gs) {		
			gameSpace = gs;
			
			setGameKeyAction("rotate",KeyEvent.VK_UP,GameSpace.MOVE_FIGURE_ROTATE);
			setGameKeyAction("left",KeyEvent.VK_LEFT,GameSpace.MOVE_FIGURE_LEFT);
			setGameKeyAction("right",KeyEvent.VK_RIGHT,GameSpace.MOVE_FIGURE_RIGHT);
			setGameKeyAction("down",KeyEvent.VK_DOWN,GameSpace.MOVE_FIGURE_DOWN);
			setGameKeyAction("topdown",KeyEvent.VK_SPACE,GameSpace.MOVE_FIGURE_TOPDOWN);
		}
		
		class GameAbstractAction extends AbstractAction {
			private final static long serialVersionUID = 0001;
			private int gEvent;
			
			GameAbstractAction(int ge) {
				gEvent = ge;
			}
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (isPause) return;
				gameSpace.moveFigure(gEvent);
				repaint();
			}
		}
		
		private void setGameKeyAction(String nameAction,int keyCode,int gameEvent) {
			getActionMap().put(nameAction,new GameAbstractAction(gameEvent));
			getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(
					keyCode, 0), nameAction);
		}
		
		public void startGame()	{
			isPause = false;
			t = new Thread(this);
			t.start();
		}
		
		public void setPauseGame(boolean b)	{
			isPause = b;
		}
		
		public void run() {
			while (!gameSpace.isEnd()) {
				if (!isPause) {
					try	{
						int timePause = MAX_TIME_PAUSE - (gameSpace.getLinesScore()/LINES_PER_LEVEL)
								*DT_TIME_PAUSE;
						
						if (timePause>0) Thread.sleep(timePause);
						gameSpace.moveFigure(GameSpace.MOVE_FIGURE_DOWN);						
						
						repaint();
					} catch (InterruptedException e) {
						System.out.println(e);
					}
				}
			}
			
			showScoreDialog();
			crdMain.show(pnlMain, "score");
			gameLogSpace.clearSpace();
			gameLogSpace.clearLinesScore();
		}
		
		private Color decodeColor(int cc) {
			switch (cc)	{
			case 0:return new Color(0xFF0000);
			case 1:return new Color(0x00FF00);
			case 2:return new Color(0x0000FF);
			case 3:return new Color(0xFF00FF);
			case 4:return new Color(0x00FFFF);
			case 5:return new Color(0x000000);
			default:return new Color(0xABABAB);
			}
		}
		
		protected void paintComponent(Graphics g) {
			maxWidth = getWidth();
			maxHeight = getHeight();
			sizeCub = maxWidth / DIV_WIDTH;
			
			g.clearRect(0, 0, maxWidth, maxHeight);
			
			//draw Game space
			for (int ix=0;ix<gameSpace.SPACE_NX;ix++)
				for (int iy=0;iy<gameSpace.SPACE_NY;iy++) {
					g.setColor(decodeColor(gameSpace.getColorXY(ix, iy)));
					g.fillRect(sizeCub+ix*sizeCub-1, 2*sizeCub+iy*sizeCub-1, sizeCub-2, sizeCub-2);
				}
					
			
			//draw figure space
			GameFigure fg = gameSpace.getCurrentFigure();
			
			for (int i=0;i<4;i++) {
				g.setColor(decodeColor(fg.getCAt(i)));
				g.fillRect(sizeCub+(gameSpace.getCurrentFigureX0()+fg.getXAt(i))*sizeCub-1,
						2*sizeCub+(gameSpace.getCurrentFigureY0()+fg.getYAt(i))*sizeCub-1, 
						sizeCub-2, sizeCub-2);
			}
			
			//draw figure next
			GameFigure fgNext = gameSpace.getNextFigure();
			int offsetNextX = maxWidth - 6*sizeCub;
			
			for (int i=0;i<4;i++) {
				g.setColor(decodeColor(fgNext.getCAt(i)));
				g.fillRect(offsetNextX+fgNext.getXAt(i)*sizeCub-1,
						10*sizeCub+fgNext.getYAt(i)*sizeCub-1,	sizeCub-2, sizeCub-2);
			}
			
			//draw score text
			int offsetTextX = maxWidth - 7*sizeCub;
			int offsetTextY = 3*sizeCub;
			g.setFont(new Font(Font.SANS_SERIF,Font.PLAIN,20));
			g.setColor(Color.BLUE);
			g.drawString(TEXT_SCORE+gameLogSpace.getLinesScore(), offsetTextX, offsetTextY);
		}
	}
	
	TetrisWin() {	
		btnStart = new JButton(TEXT_STARTBTN);
		btnPause = new JButton(TEXT_PAUSEOFF);
		btnExit = new JButton(TEXT_EXITBTN);
		btnBack = new JButton(TEXT_BACKBTN);
		btnStart.addActionListener(this);
		btnPause.addActionListener(this);
		btnExit.addActionListener(this);
		btnBack.addActionListener(this);
		
		pnlIntro = new JPanel();
		IntroCanvas pnlIntroCan = new IntroCanvas();
		pnlIntroCan.setPreferredSize(new Dimension(600,200));
		
		GridBagLayout gbl1 = new GridBagLayout();
		GridBagConstraints gbc1 = new GridBagConstraints();
		
		gbc1.anchor = GridBagConstraints.CENTER;
		gbc1.insets = new Insets(5,5,0,0);
		gbc1.gridwidth = GridBagConstraints.REMAINDER;
		
		gbl1.setConstraints(pnlIntroCan, gbc1);
		gbl1.setConstraints(btnStart, gbc1);
		
		pnlIntro.setLayout(gbl1);
		pnlIntro.add(pnlIntroCan);
		pnlIntro.add(btnStart);
		
		pnlGame = new JPanel();
		JPanel pnlGameBtns = new JPanel();
		pnlGameBtns.setLayout(new FlowLayout());
		pnlGameBtns.add(btnPause);
		pnlGameBtns.add(btnExit);
		
		gameLogSpace = new GameSpace();
		gameCanvas = new GameCanvas(gameLogSpace);
		gameCanvas.setPreferredSize(new Dimension(500,400));
		
		GridBagConstraints gbc2 = new GridBagConstraints();
		GridBagLayout gbl2 = new GridBagLayout();
		
		gbc2.anchor = GridBagConstraints.CENTER;
		gbc2.insets = new Insets(5,5,0,0);
		gbc2.gridwidth = GridBagConstraints.REMAINDER;
		
		gbl2.setConstraints(gameCanvas, gbc2);
		gbl2.setConstraints(pnlGameBtns, gbc2);
		
		pnlGame.setLayout(gbl2);
		pnlGame.add(gameCanvas);
		pnlGame.add(pnlGameBtns);
		
		userScore = loadScoresFromFile(FILE_WITH_SCORES);
		pnlScore = new JPanel();
		//insert Score into panel
		
		JTable tblScore = new JTable(new AbstractTableModel(){
			private final static long serialVersionUID = 0001;
			
			@Override
			public int getColumnCount()	{
				return 2;
			}

			@Override
			public int getRowCount() {
				return MAX_SCOREUSERS;
			}

			@Override
			public Object getValueAt(int row, int col) {
				return (col==0)?userScore[row].playerName:userScore[row].playerScore;
			}
			
			public String getColumnName(int col) {
				return TEXT_COLS[col];
			}
		});
		
		JScrollPane scrTabScore = new JScrollPane(tblScore);
		scrTabScore.setPreferredSize(new Dimension(600,300));
		JLabel lblScores = new JLabel(TEXT_TABLESCORE);
		lblScores.setFont(new Font(Font.SANS_SERIF,Font.PLAIN,30));
		
		GridBagConstraints gbc3 = new GridBagConstraints();
		GridBagLayout gbl3 = new GridBagLayout();
		
		gbc3.anchor = GridBagConstraints.CENTER;
		gbc3.insets = new Insets(5,0,5,0);
		gbc3.gridwidth = GridBagConstraints.REMAINDER;
		
		gbl3.setConstraints(lblScores, gbc3);
		gbl3.setConstraints(scrTabScore, gbc3);
		gbl3.setConstraints(btnBack, gbc3);
		
		pnlScore.setLayout(gbl3);
		pnlScore.add(lblScores);
		pnlScore.add(scrTabScore);
		pnlScore.add(btnBack);
		
		pnlMain = new JPanel();
		crdMain = new CardLayout();
		pnlMain.setLayout(crdMain);
		pnlMain.add(pnlIntro, "intro");
		pnlMain.add(pnlGame, "game");
		pnlMain.add(pnlScore, "score");
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(640,480);
		this.setLocation(100, 100);
		this.setTitle(TEXT_TITLE);
		this.setResizable(false);
		this.setContentPane(pnlMain);
		this.setVisible(true);
	}
	
	@Override
	public void actionPerformed(ActionEvent ae) {
		if ((ae.getSource() == btnStart)||(ae.getSource() == btnBack)) {
			gameLogSpace.genFigure();
			crdMain.show(pnlMain, "game");
			gameCanvas.startGame();
			gameCanvas.setFocusable(true);
		}
		
		if (ae.getSource() == btnPause)	{
			if (btnPause.getText() == TEXT_PAUSEOFF) {
				gameCanvas.setPauseGame(true);
				btnPause.setText(TEXT_PAUSEON);
			} else {
				gameCanvas.setPauseGame(false);
				btnPause.setText(TEXT_PAUSEOFF);
			}
			gameCanvas.requestFocus();
		}
		
		if (ae.getSource() == btnExit) {
			crdMain.show(pnlMain, "score");
			showScoreDialog();
			System.exit(0);
		}
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new TetrisWin();
			}
		});
	}

}
