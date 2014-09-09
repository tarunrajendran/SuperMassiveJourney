package com.smjourney.gui;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;

import javax.swing.*;
import javax.swing.border.*;

import com.smjourney.map.*;
import com.smjourney.smjourney.*;
import com.smjourney.audio.*;
import com.smjourney.entity.*;

import java.io.*;
import javax.imageio.*;
import java.util.ArrayList;

public class BattleDisplay extends JPanel implements KeyListener
{
	public final int CELL_SIZE = 27;
	public final int ENTITY_SPACING = 2;
	
	public static final int EASY = 1;
	public static final int AMATUER = 2;
	public static final int MEDIUM = 3;
	public static final int ADVANCED = 4;
	public static final int HARD = 5;
	
	private Planet planet;
	private Map<EntityPartition> map;
	private BattleManager manager;
	
	private JScrollPane scrollPane;
	private MapGrid mapGrid;
	private AllyBattleUI allyUI;
	private EnemyBattleUI enemyUI;
	
	private EntityPartition entityPartSelected;
	
	private Entity entitySelected;
	private Entity entityTargeted;
	private CellGroup entityEnemyAffectedCG;
	private CellGroup entityAllyAffectedCG;
	
	private boolean entityDestroyed;
	private boolean isYourTurn;
	
	public BattleDisplay()
	{
		super();
		Main.debugStream.println("BattleDisplay created.");
	}
	
	public BattleDisplay(Planet planet, int difficulty)
	{
		super();
		arrangeBattle(planet, difficulty);
	}
	
	public void arrangeBattle(Planet planet, int difficulty)
	{
		setLayout(new BorderLayout());
		this.planet = planet;
		
		int solarSystemID = planet.getSolarSystemReference().getSystemID();
		MusicPlayer.stop();
		MusicPlayer.play(BGM.TEMP_BGM_2);
		File file = null;
		
		if (solarSystemID == 1)
		{
			file = new File("res/World1 Scaled.png");
		}
		else
		if (solarSystemID == 2)
		{
			file = new File("res/World2 Scaled.png");
		}
		else
		if (solarSystemID == 3)
		{
			file = new File("res/World3 Scaled.png");
		}
		else
		if (solarSystemID == 4)
		{
			file = new File("res/World4 Scaled.png");
		}
		else
		if (solarSystemID == 5)
		{
			file = new File("res/World5 Scaled.png");
		}
		else
		{
			file = new File("res/World1 Scaled.png");
		}
		
		setBackground(Color.BLACK);
		
		BufferedImage background = null;
		try { background = ImageIO.read(file); } catch (Exception e) {};
		
		JPanel mapInterface = new JPanel();
		mapInterface.setLayout(new BorderLayout());
		
		map = planet.getMap();
		mapGrid = new MapGrid(map, this, background);
		mapGrid.setPreferredSize(new Dimension(CELL_SIZE * map.getNumColumns(), CELL_SIZE * map.getNumRows()));
		mapInterface.add(mapGrid, BorderLayout.CENTER);
		
		Border whiteBorder = BorderFactory.createLineBorder(Color.WHITE);
		mapInterface.setBorder(whiteBorder);
		
		scrollPane = new JScrollPane(mapGrid);
		scrollPane.setForeground(Color.BLACK);
		scrollPane.setBackground(Color.BLACK);
		
		try
		{
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
	        SwingUtilities.updateComponentTreeUI(scrollPane);
		}
		catch (Exception e) {  }
		
		mapInterface.add(scrollPane, BorderLayout.CENTER);
		add(mapInterface, BorderLayout.CENTER);
		
		Panner up = new Panner(this, Panner.UP);
		Panner down = new Panner (this, Panner.DOWN);
		Panner left = new Panner(this, Panner.LEFT);
		Panner right = new Panner(this, Panner.RIGHT);
		
		up.setPreferredSize(new Dimension(1200, 30));
		down.setPreferredSize(new Dimension(1200, 30));
		left.setPreferredSize(new Dimension(30, 700));
		right.setPreferredSize(new Dimension(30, 700));
		
		add(up, BorderLayout.NORTH);
		add(down, BorderLayout.SOUTH);
		add(left, BorderLayout.WEST);
		add(right, BorderLayout.EAST);
		
		allyUI = new AllyBattleUI(this);
		allyUI.setPreferredSize(new Dimension(700, 40));
		//allyUI.setBorder(whiteBorder);
		mapInterface.add(allyUI, BorderLayout.SOUTH);
		
		enemyUI = new EnemyBattleUI(this);
		enemyUI.setPreferredSize(new Dimension(700, 20));
		//enemyUI.setBorder(whiteBorder);
		mapInterface.add(enemyUI, BorderLayout.NORTH);
		
		this.setFocusable(true);
		this.setFocusTraversalKeysEnabled(false);
		this.addKeyListener(this);
		
		isYourTurn = true;
		manager = new BattleManager(planet, this, difficulty);
		this.setDoubleBuffered(true);
		Main.debugStream.println("The battle has been arranged.");
	}
	
	public Planet getPlanet() {
		return planet;
	}

	class MapGrid extends JPanel implements MouseListener, MouseMotionListener, ActionListener
	{
		private Map<EntityPartition> map;
		private BattleDisplay displayReference;
		private BufferedImage background;
		
		private Cell cellClicked;
		private Cell cellOver;
		
		private Timer timer;
		private int timerTicks;
		
		public MapGrid(Map<EntityPartition> map, BattleDisplay displayReference, BufferedImage background)
		{
			super();
			this.addMouseListener(this);
			this.addMouseMotionListener(this);
			
			this.map = map;
			this.background = background;
			this.displayReference = displayReference;
			this.timer = new Timer(30, this); /* 1000 / FRAME_RATE */
		}

		@Override
		public void mouseClicked(MouseEvent arg0) 
		{
			if (manager.isBattleFinished())
			{
				Galaxy g = Memory.getGalaxy();
				if (manager.youWin())
				{
					g.changeCompletionStatus(displayReference.getPlanet().getSolarSystemReference().getSystemID(), displayReference.getPlanet().getPlanetID());
				}
				Memory.setGalaxy(g);
				Memory.saveGalaxy();
				
				Main.cardIncrement++;
				int shipLogs = g.getNumberOfPlanetsCompleted();
				int systems = g.getNumberOfSystemsCleared();
				Main.cards.add(new GalaxySelect(shipLogs, systems), "" + Main.cardIncrement);
				Main.cl.show(Main.cards, "" + Main.cardIncrement);
				
				MusicPlayer.play(BGM.TEMP_BGM_1);
			}
		}

		@Override
		public void mouseEntered(MouseEvent arg0) 
		{
			displayReference.requestFocus();
		}

		@Override
		public void mouseExited(MouseEvent arg0) 
		{

		}

		@Override
		public void mousePressed(MouseEvent arg0) 
		{	
			if (!isYourTurn() || manager.isBattleFinished())
			{
				cellOver = null;
				return;
			}
			SFX.play(SFX.MOUSE_FORWARD);
			Cell c = null;
			
			try
			{
				int cellRow = (int) this.getMousePosition().getY() / CELL_SIZE;
				int cellColumn = (int) this.getMousePosition().getX() / CELL_SIZE;
			
				c = new Cell(cellRow, cellColumn);
			} catch (Exception e) {}

			if (!map.isValid(c))
			{
				return;
			}

			EntityPartition ep = map.get(c);
			if (ep == null)
			{
				displayReference.setEntitySelected(null);
				displayReference.setEntityTargeted(null);
				repaint(); revalidate();
				getEnemyUI().repaint(); getEnemyUI().revalidate();
				getAllyUI().repaint(); getAllyUI().revalidate();
				return;
			}
			
			Entity eSelected = ep.getEntityReference();
			Ship sSelected = null;
			
			if (eSelected instanceof Ship)
			{
				sSelected = (Ship) eSelected;
			}
			
			if (eSelected instanceof Ship && displayReference.getEntityTargeted() == null)
			{
				Ship s = (Ship) eSelected;
				if (s.isEnemy() && isYourTurn())
				{
					displayReference.setEntityTargeted(eSelected);
					
				}
				if (s.isAlly() && !isYourTurn())
				{
					displayReference.setEntityTargeted(eSelected);
					
				}
				repaint(); revalidate();
				getEnemyUI().repaint(); getEnemyUI().revalidate();
				getAllyUI().repaint(); getAllyUI().revalidate();
				//return;
			}
			
			if (eSelected instanceof Ship && displayReference.getEntityTargeted() != null)
			{
				Ship s = (Ship) eSelected;
				if (s.isEnemy() && isYourTurn())
				{
					displayReference.setEntityTargeted(eSelected);
					
				}
				if (s.isAlly() && !isYourTurn())
				{
					displayReference.setEntityTargeted(eSelected);
					
				}
				repaint(); revalidate();
				getEnemyUI().repaint(); getEnemyUI().revalidate();
				getAllyUI().repaint(); getAllyUI().revalidate();
				//return;
			}
			
			if (eSelected instanceof Ship && displayReference.getEntitySelected() != null)
			{
				Ship s = (Ship) eSelected;
				if (s.isAlly() && isYourTurn())
				{
					displayReference.setEntitySelected(eSelected);
					
				}
				if (s.isEnemy() && !isYourTurn())
				{
					displayReference.setEntitySelected(eSelected);
					
				}
				repaint(); revalidate();
				getEnemyUI().repaint(); getEnemyUI().revalidate();
				getAllyUI().repaint(); getAllyUI().revalidate();
				return;
			}
			
			if (eSelected instanceof Ship && displayReference.getEntitySelected() != null)
			{
				Ship s = (Ship) eSelected;
				if (s.isEnemy() && isYourTurn())
				{
					displayReference.setEntityTargeted(eSelected);
					
				}
				if (s.isAlly() && !isYourTurn())
				{
					displayReference.setEntityTargeted(eSelected);
					
				}
				repaint(); revalidate();
				getEnemyUI().repaint(); getEnemyUI().revalidate();
				getAllyUI().repaint(); getAllyUI().revalidate();
				return;
			}
			
			if (eSelected instanceof Debris/* && displayReference.getEntitySelected() != null*/)
			{
				displayReference.setEntityTargeted(eSelected);
				
				repaint(); revalidate();
				getEnemyUI().repaint(); getEnemyUI().revalidate();
				getAllyUI().repaint(); getAllyUI().revalidate();
				return;
			}
			
			
			if (isYourTurn() && !sSelected.isEnemy())
			{
				displayReference.setEntitySelected(eSelected);
			}
			if (isYourTurn() && sSelected.isEnemy())
			{
				displayReference.setEntityTargeted(eSelected);
			}
			if (!isYourTurn() && sSelected.isAlly())
			{
				displayReference.setEntityTargeted(eSelected);
			}
			if (!isYourTurn() && !sSelected.isAlly())
			{
				displayReference.setEntitySelected(eSelected);
			}

						
			repaint(); revalidate();
			getEnemyUI().repaint(); getEnemyUI().revalidate();
			getAllyUI().repaint(); getAllyUI().revalidate();
		}

		@Override
		public void mouseReleased(MouseEvent arg0) 
		{
			if (!isYourTurn() || manager.isBattleFinished())
			{
				return;
			}
			/*repaint(); revalidate();
			getEnemyUI().repaint(); getEnemyUI().revalidate();
			getAllyUI().repaint(); getAllyUI().revalidate();*/
		}

		@Override
		public void mouseDragged(MouseEvent arg0) 
		{
			
			
		}

		@Override
		public void mouseMoved(MouseEvent arg0) 
		{
			int mapWidth = CELL_SIZE * map.getNumColumns();
			int mapHeight = CELL_SIZE * map.getNumRows();
			
			if (!isYourTurn() || manager.isBattleFinished())
			{
				return;
			}
			try
			{
				int cellRow = (int) this.getMousePosition().getY() / CELL_SIZE;
				int cellColumn = (int) this.getMousePosition().getX() / CELL_SIZE;
				
				cellOver = new Cell(cellRow, cellColumn);
				repaint(); revalidate();
			} catch (Exception e) {}
			
			
		}
		
		protected void paintComponent(Graphics g)
		{
			Graphics2D g2 = (Graphics2D) g;
			
			super.paintComponent(g2);
			
			g2.drawImage(background, 0, 0, background.getWidth(), background.getHeight(), this);
			
			if (manager.isBattleFinished())
			{
				setEntitySelected(null);
				setEntityTargeted(null);
				setEntityEnemyAffectedCellGroup(null);
				setEntityAllyAffectedCellGroup(null);
				if (!manager.youWin())
				{
					try
					{
						BufferedImage loss = ImageIO.read(new File("res/lossMarker.png"));
						g2.drawImage(loss, 0, 0, map.getNumColumns() * CELL_SIZE, map.getNumRows() * CELL_SIZE, this);
						MusicPlayer.stop();
						SFX.play(SFX.LOSE);
					}
					catch (Exception e) {}
				}
				if (manager.youWin())
				{
					try
					{
						BufferedImage win = ImageIO.read(new File("res/winMarker.png"));
						g2.drawImage(win, 0, 0, map.getNumColumns() * CELL_SIZE, map.getNumRows() * CELL_SIZE, this);
						MusicPlayer.stop();
						SFX.play(SFX.WIN);
					}
					catch (Exception e) {}
				}
			}

			g2.setColor(new Color(180, 180, 180));
			g2.setStroke(new BasicStroke(3));
			for (int c = 0; c < map.getNumColumns(); c++)
			{
				for (int r = 0; r < map.getNumRows(); r++)
				{
					g2.drawRect(c * CELL_SIZE, r * CELL_SIZE, CELL_SIZE, CELL_SIZE);
				}
			}
			//g2.setStroke(new BasicStroke(1));
			
			if (displayReference.getEntitySelected() != null)
			{	
				try 
				{ 
					BufferedImage attackCellIndicator = null;
					attackCellIndicator = ImageIO.read(new File("res/Attack Range Indicator.png")); 
					
					if (displayReference.getEntitySelected() instanceof Ship)
					{
						Ship s = (Ship) displayReference.getEntitySelected();
						if ((isYourTurn() && s.isAlly()) || (!isYourTurn() && s.isEnemy()))
						{
							ArrayList<Cell> attackCells = s.getAttackCells();
							for (Cell attackCell : attackCells)
							{
								g2.drawImage(attackCellIndicator, attackCell.getColumn() * CELL_SIZE, attackCell.getRow() * CELL_SIZE, CELL_SIZE, CELL_SIZE, this);
							}
						}
					}

				} catch (Exception ex) {}
				
				g2.setColor(Color.GREEN);
				Entity e = displayReference.getEntitySelected();
				Cell eRefCell = e.getCellGroup().getReferenceCell();
				g2.drawRect(eRefCell.getColumn() * CELL_SIZE, eRefCell.getRow() * CELL_SIZE, CELL_SIZE * e.getSize(), CELL_SIZE * e.getSize());
			}
			
			ArrayList<Entity> entities = EntityTracker.getEntities(displayReference.getMap());
			
			for (Entity e : entities)
			{
				Cell c = e.getCellGroup().getReferenceCell();
				g2.drawImage(e.getSprite(), (c.getColumn() * CELL_SIZE) + ENTITY_SPACING, 
										   (c.getRow() * CELL_SIZE) + ENTITY_SPACING, 
						                   (e.getSize() * CELL_SIZE) - (ENTITY_SPACING * 2), 
						                   (e.getSize() * CELL_SIZE) - (ENTITY_SPACING * 2), 
						                    this);
			}
			
			if (cellOver != null && map.isValid(cellOver))
			{
				if (map.get(cellOver) != null)
				{
					g2.setColor(Color.YELLOW);
					Entity e = map.get(cellOver).getEntityReference();
					Cell eRefCell = e.getCellGroup().getReferenceCell();
					g2.drawRect(eRefCell.getColumn() * CELL_SIZE, eRefCell.getRow() * CELL_SIZE, CELL_SIZE * e.getSize(), CELL_SIZE * e.getSize());
				}
				else
				{
					g2.setColor(Color.CYAN);
					g2.drawRect(cellOver.getColumn() * CELL_SIZE, cellOver.getRow() * CELL_SIZE, CELL_SIZE, CELL_SIZE);
				}
			}
			
			if (displayReference.getEntityTargeted() != null)
			{
				g2.setColor(Color.RED);
				Entity e = displayReference.getEntityTargeted();
				Cell eRefCell = e.getCellGroup().getReferenceCell();
				g2.drawRect(eRefCell.getColumn() * CELL_SIZE, eRefCell.getRow() * CELL_SIZE, CELL_SIZE * e.getSize(), CELL_SIZE * e.getSize());
			}
			
			// NOTE: Energy Beam Flash
			if (displayReference.getEntitySelected() != null && timer.isRunning() && (displayReference.getEntitySelected() instanceof BeamShip || displayReference.getEntitySelected() instanceof Mothership))
			{
				Ship s = (Ship) displayReference.getEntitySelected();
				try 
				{ 
					BufferedImage beam = null;
					beam = ImageIO.read(new File("res/Beam.png")); 
					
					if (displayReference.getEntitySelected() instanceof Ship)
					{
						if ((isYourTurn() && s.isAlly()) || (!isYourTurn() && s.isEnemy()))
						{
							ArrayList<Cell> beamCells = s.getAttackCells();
							for (Cell beamCell : beamCells)
							{
								g2.drawImage(beam, beamCell.getColumn() * CELL_SIZE, beamCell.getRow() * CELL_SIZE, CELL_SIZE, CELL_SIZE, this);
							}
						}
					}

				} catch (Exception ex) {}
			}
			
			// NOTE: Laser Beam Flash
			if (displayReference.getEntitySelected() != null && ((displayReference.getEntityTargeted() != null) || (getEntityEnemyAffectedCellGroup() != null || getEntityAllyAffectedCellGroup() != null)) && timer.isRunning())
			{
				Ship s = (Ship) displayReference.getEntitySelected();
				
				BufferedImage hitImg = null;
				int cgSize = 0;
				
				try
				{
					if (isEntityDestroyed() && isYourTurn())
					{
						cgSize = (int) Math.sqrt(getEntityEnemyAffectedCellGroup().toArrayList().size());
						if (cgSize == 1)
						{
							hitImg = ImageIO.read(new File("res/destroyedSM/destroyedSM1.png"));
						}
						if (cgSize == 2)
						{
							hitImg = ImageIO.read(new File("res/destroyedMD/destroyedMD1.png"));
						}
						if (cgSize == 3)
						{
							hitImg = ImageIO.read(new File("res/destroyedLG/destroyedLG2.png"));
						}
						if (cgSize == 4)
						{
							hitImg = ImageIO.read(new File("res/destroyedLG/destroyedLG1.png"));
						}
					}
					else
					if (isEntityDestroyed() && !isYourTurn())
					{
						cgSize = (int) Math.sqrt(getEntityAllyAffectedCellGroup().toArrayList().size());
						if (cgSize == 1)
						{
							hitImg = ImageIO.read(new File("res/destroyedSM/destroyedSM1.png"));
						}
						if (cgSize == 2)
						{
							hitImg = ImageIO.read(new File("res/destroyedMD/destroyedMD1.png"));
						}
						if (cgSize == 3)
						{
							hitImg = ImageIO.read(new File("res/destroyedLG/destroyedLG2.png"));
						}
						if (cgSize == 4)
						{
							hitImg = ImageIO.read(new File("res/destroyedLG/destroyedLG1.png"));
						}
					}
					else
					{
						if (isYourTurn() && getEntityTargeted() instanceof Ship)
						{
							cgSize = (int) Math.sqrt(getEntityEnemyAffectedCellGroup().toArrayList().size());
							if (cgSize == 1)
							{
								hitImg = ImageIO.read(new File("res/damagedSM/damagedSM5.png"));
							}
							if (cgSize == 2)
							{
								hitImg = ImageIO.read(new File("res/damagedMD/damagedMD5.png"));
							}
							if (cgSize == 3)
							{
								hitImg = ImageIO.read(new File("res/damagedLG/damagedLG5.png"));
							}
							if (cgSize == 4)
							{
								hitImg = ImageIO.read(new File("res/damagedLG/damagedLG5.png"));
							}
						}
						if (!isYourTurn() && getEntityTargeted() instanceof Ship)
						{
							cgSize = (int) Math.sqrt(getEntityAllyAffectedCellGroup().toArrayList().size());
							if (cgSize == 1)
							{
								hitImg = ImageIO.read(new File("res/damagedSM/damagedSM5.png"));
							}
							if (cgSize == 2)
							{
								hitImg = ImageIO.read(new File("res/damagedMD/damagedMD5.png"));
							}
							if (cgSize == 3)
							{
								hitImg = ImageIO.read(new File("res/damagedLG/damagedLG5.png"));
							}
							if (cgSize == 4)
							{
								hitImg = ImageIO.read(new File("res/damagedLG/damagedLG5.png"));
							}
						}
					}
				} catch (Exception e) {}
				
				if (s instanceof BeamShip || s instanceof Mothership)
				{
					return;
				}
				
				double s_row = displayReference.getEntitySelected().getCellGroup().getCentroid().get(0);
				double s_col = displayReference.getEntitySelected().getCellGroup().getCentroid().get(1);
				double t_row = 0;
				double t_col = 0;
				
				if (isYourTurn())
				{
					t_row = getEntityEnemyAffectedCellGroup().getCentroid().get(1);
					t_col = getEntityEnemyAffectedCellGroup().getCentroid().get(0);
				}
				if (!isYourTurn())
				{
					t_row = getEntityAllyAffectedCellGroup().getCentroid().get(0);
					t_col = getEntityAllyAffectedCellGroup().getCentroid().get(1);
				}
				
				int dirFacing = displayReference.getEntitySelected().getDirection();
				int eSize = displayReference.getEntitySelected().getSize();
				double rowOffset = 0;
				double colOffset = 0;
				
				if (dirFacing == Direction.NORTH) { rowOffset = -1.0 * (eSize / 2.0); };
				if (dirFacing == Direction.SOUTH) { rowOffset = 1.0  * (eSize / 2.0);};
				if (dirFacing == Direction.EAST) { colOffset = 1.0  * (eSize / 2.0); };
				if (dirFacing == Direction.WEST) { colOffset = -1.0  * (eSize / 2.0); };

				g2.setColor(Color.ORANGE);
				g2.setStroke(new BasicStroke(3));
				if (isYourTurn())
				{
					g2.drawLine(((int) (s_col * CELL_SIZE) + CELL_SIZE / 2) + (int) (colOffset * CELL_SIZE), 
						    ((int) (s_row * CELL_SIZE) + CELL_SIZE / 2) + (int) (rowOffset * CELL_SIZE),
						    ((int) (t_row * CELL_SIZE) + CELL_SIZE / 2), 
						    ((int) (t_col * CELL_SIZE) + CELL_SIZE / 2));
					try
					{
						g2.drawImage(hitImg, (int) (getEntityEnemyAffectedCellGroup().getReferenceCell().getColumn() * CELL_SIZE) , (int) (getEntityEnemyAffectedCellGroup().getReferenceCell().getRow() * CELL_SIZE), CELL_SIZE * cgSize, CELL_SIZE * cgSize, this);
					}
					catch (Exception e) {}
				}
				if (!isYourTurn())
				{
					g2.drawLine(((int) (s_col * CELL_SIZE) + CELL_SIZE / 2) + (int) (colOffset * CELL_SIZE), 
						    ((int) (s_row * CELL_SIZE) + CELL_SIZE / 2) + (int) (rowOffset * CELL_SIZE),
						    ((int) (t_col * CELL_SIZE) + CELL_SIZE / 2), 
						    ((int) (t_row * CELL_SIZE) + CELL_SIZE / 2));
					try
					{
						g2.drawImage(hitImg, (int) (getEntityAllyAffectedCellGroup().getReferenceCell().getColumn() * CELL_SIZE) , (int) (getEntityAllyAffectedCellGroup().getReferenceCell().getRow() * CELL_SIZE), CELL_SIZE * cgSize, CELL_SIZE * cgSize, this);
					} catch (Exception e) {}
					
				}

				if (isYourTurn() && !timer.isRunning())
				{
					setEntityEnemyAffectedCellGroup(null);
				}
				if (!isYourTurn() && !timer.isRunning())
				{
					setEntityAllyAffectedCellGroup(null);
				}
				
				setEntityDestroyed(false);
			}
		}
		
		public Timer getTimer()
		{
			return timer;
		}

		@Override
		public void actionPerformed(ActionEvent e) 
		{
			if (timer.isRunning())
			{
				timerTicks++;
			}
			if (timerTicks >= 1)
			{
				timer.stop();
				timerTicks = 0;
			}
		}
	}

	class AllyBattleUI extends JPanel implements MouseListener, MouseMotionListener
	{
		private boolean mouseOverButton;
		private boolean mousePressed;
		
		private BattleDisplay displayReference;
		
		public AllyBattleUI(BattleDisplay displayReference)
		{
			this.displayReference = displayReference;
			this.addMouseListener(this);
			this.addMouseMotionListener(this);
			this.addMouseListener(this);
		}

		@Override
		public void mouseDragged(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseMoved(MouseEvent e) 
		{
			if (!isYourTurn() || manager.isBattleFinished())
			{
				return;
			}
			if (e.getX() > 1094 && e.getX() < 1170)
			{
				if (!mouseOverButton)
				{
					SFX.play(SFX.MOUSE_OVER);
				}
				mouseOverButton = true;
			}
			else
			{
				mouseOverButton = false;
			}
			repaint(); revalidate();
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			
			
		}

		@Override
		public void mouseEntered(MouseEvent e) 
		{

		}

		@Override
		public void mouseExited(MouseEvent e) 
		{
			if (!isYourTurn() || manager.isBattleFinished())
			{
				return;
			}
			mouseOverButton = false;
			repaint(); revalidate();
		}

		@Override
		public void mousePressed(MouseEvent e) 
		{
			if (!isYourTurn() || manager.isBattleFinished())
			{
				return;
			}
			mousePressed = true;
			repaint(); revalidate();
		}

		@Override
		public void mouseReleased(MouseEvent e) 
		{
			if (!isYourTurn() || manager.isBattleFinished())
			{
				return;
			}
			boolean clickConfirmed = false;
			if (mousePressed && mouseOverButton)
			{
				clickConfirmed = true;
			}
			mousePressed = false;
			repaint(); revalidate();
			if (clickConfirmed)
			{
				SFX.play(SFX.MOUSE_BACK);
				displayReference.toggleTurn();
				repaint(); revalidate();
				displayReference.getEnemyUI().repaint(); displayReference.getEnemyUI().revalidate();
				displayReference.getMapGrid().repaint(); displayReference.getMapGrid().revalidate();
			}
		}
		
		public void paintComponent(Graphics g)
		{
			Graphics2D g2 = (Graphics2D) g;
			super.paintComponent(g);
			
			g2.setColor(Color.BLACK);
			g2.fillRect(0, 0, 1200, 40);
			
			if (displayReference.getManager().isBattleFinished() && displayReference.getManager().youWin())
			{
				try
				{
					BufferedImage winText = ImageIO.read(new File("res/winText.png"));
					g.drawImage(winText, 0, 0, 1140, 40, this);
					return;
				} catch (Exception e) {}
			}
			
			if (displayReference.getEntitySelected() != null)
			{
				Entity e = displayReference.getEntitySelected();
				if (e instanceof Ship)
				{
					Ship s = (Ship) e;
					g.setColor(Color.RED);
					g.fillRect(0, 0, (1094) * ((int) s.statToPercentage(Ship.SHIELDS)) / 100, 20);
					g.setColor(Color.GREEN);
					g.fillRect(0, 20, (1094) * ((int) s.statToPercentage(Ship.CAPACITY)) / 100, 20);
				}
			}
			
				try 
				{ 
					BufferedImage endTurnButton = ImageIO.read(new File("res/End Turn Button.png")); 
					g.drawImage(endTurnButton, (1095), 1, 38, 38, this);
				} 
				catch (Exception e) {}
				
				if (mouseOverButton)
				{
					try 
					{ 
						BufferedImage endTurnButton = ImageIO.read(new File("res/End Turn Button (Pressed).png")); 
						g.drawImage(endTurnButton, (1095), 1, 38, 38, this);
					} 
					catch (Exception e) {}
				}
				
				g2.setStroke(new BasicStroke(3));
				g2.setColor(Color.WHITE);
				g2.drawRect(1154, 0, 39, 39);
				
				if (mousePressed)
				{
					if (isYourTurn())
					{
						g2.setColor(Color.CYAN);
					}
					if (!isYourTurn())
					{
						g2.setColor(Color.RED);
					}
					g2.setStroke(new BasicStroke(3));
					g2.drawRect(1094, 0, 39, 39);
				}
		}
	}
	
	class EnemyBattleUI extends JPanel
	{
		private BattleDisplay displayReference;
		
		public EnemyBattleUI(BattleDisplay displayReference)
		{
			this.displayReference = displayReference;
		}
		
		public void paintComponent(Graphics g)
		{	
			super.paintComponent(g);
			Graphics2D g2 = (Graphics2D) g;
			
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, 1200, 20);
			
			if (displayReference.getEntityTargeted() != null)
			{
				Entity e = displayReference.getEntityTargeted();
				if (e instanceof Ship)
				{
					g.setColor(Color.CYAN);
					Ship s = (Ship) e;
					g.fillRect(0, 0, 1140 * ((int) s.statToPercentage(Ship.SHIELDS)) / 100, 20);
				}
				if (e instanceof Debris)
				{
					g.setColor(Color.YELLOW);
					Debris d = (Debris) e;
					g.fillRect(0, 0, (int) (7.6 * d.getArmorDurability()), 20);
				}
			}
		}
	}
	
	public MapGrid getMapGrid()
	{
		return mapGrid;
	}
	
	public Map<EntityPartition> getMap()
	{
		return map;
	}


	public void keyTyped(KeyEvent e) {}
	public void keyReleased(KeyEvent e) 
	{
		if (manager.isBattleFinished())
		{
			return;
		}
		getMapGrid().repaint(); getMapGrid().revalidate();
	}
	public void keyPressed(KeyEvent e) 
	{	
		if (manager.isBattleFinished())
		{
			return;
		}
		if (e.getKeyCode() == KeyEvent.VK_W)
		{
			if (!isYourTurn())
			{
				return;
			}
			//SFX.play(SFX.MOUSE_FORWARD);
			if (getEntitySelected() == null)
			{
				return;
			}
			moveEntityNorth(getEntitySelected());
		}
		if (e.getKeyCode() == KeyEvent.VK_A)
		{
			if (!isYourTurn())
			{
				return;
			}
			//SFX.play(SFX.MOUSE_FORWARD);
			if (getEntitySelected() == null)
			{
				return;
			}
			moveEntityWest(getEntitySelected());
		}
		if (e.getKeyCode() == KeyEvent.VK_S)
		{
			if (!isYourTurn())
			{
				return;
			}
			//SFX.play(SFX.MOUSE_FORWARD);
			if (getEntitySelected() == null)
			{
				return;
			}
			moveEntitySouth(getEntitySelected());
		}
		if (e.getKeyCode() == KeyEvent.VK_D)
		{
			if (!isYourTurn())
			{
				return;
			}
			//SFX.play(SFX.MOUSE_FORWARD);
			if (getEntitySelected() == null)
			{
				return;
			}
			moveEntityEast(getEntitySelected());
		}
		if (e.getKeyCode() == KeyEvent.VK_SPACE)
		{
			if (!isYourTurn())
			{
				return;
			}
			//SFX.play(SFX.MOUSE_BACK);
			if (getEntitySelected() == null /*|| getEntitySelected() instanceof Mothership || getEntitySelected() instanceof BeamShip*/)
			{
				return;
			}
			operateEntity(getEntitySelected());
		}
	}

	public void moveEntityNorth(Entity eSelected)
	{
		if (eSelected instanceof Ship)
		{
			Ship sSelected = (Ship) eSelected;
			sSelected.faceNorth();
			if (!sSelected.canMove())
			{
				return;
			}
			try{ sSelected.moveForward(); SFX.play(SFX.SHIP_MOVE); } catch (Exception e) { };
		}
		//getMapGrid().repaint();
		//getMapGrid().revalidate();
		repaintPanels();
	}
	
	public void moveEntityEast(Entity eSelected)
	{
		if (eSelected instanceof Ship)
		{
			Ship sSelected = (Ship) eSelected;
			sSelected.faceEast();
			if (!sSelected.canMove())
			{
				return;
			}
			try{ sSelected.moveForward(); SFX.play(SFX.SHIP_MOVE); } catch (Exception e) {  };
		}
		//getMapGrid().repaint();
		//getMapGrid().revalidate();
		repaintPanels();
	}
	
	public void moveEntitySouth(Entity eSelected)
	{
		if (eSelected instanceof Ship)
		{
			Ship sSelected = (Ship) eSelected;
			sSelected.faceSouth();
			if (!sSelected.canMove())
			{
				return;
			}
			try{ sSelected.moveForward(); SFX.play(SFX.SHIP_MOVE); } catch (Exception e) { };
		}
		//getMapGrid().repaint();
		//getMapGrid().revalidate();
		repaintPanels();
	}
	
	public void moveEntityWest(Entity eSelected)
	{
		if (eSelected instanceof Ship)
		{
			Ship sSelected = (Ship) eSelected;
			sSelected.faceWest();
			if (!sSelected.canMove())
			{
				return;
			}
			try{ sSelected.moveForward(); SFX.play(SFX.SHIP_MOVE); } catch (Exception e) {  };
		}
		//getMapGrid().repaint();
		//getMapGrid().revalidate();
		repaintPanels();
	}
	
	public void moveEntityTo(Entity eSelected, CellGroup target)
	{
		if (eSelected instanceof Ship)
		{
			Ship sSelected = (Ship) eSelected;
			if (!sSelected.canMoveTo(target))
			{
				return;
			}
			try
			{ 
				int row1 = sSelected.getCellGroup().getReferenceCell().getRow();
				int col1 = sSelected.getCellGroup().getReferenceCell().getColumn();
				int row2 = target.getReferenceCell().getRow();
				int col2 = target.getReferenceCell().getColumn();
				double unitsMoved = Math.sqrt(Math.pow(row2 - row1, 2) + Math.pow(col2 - col1, 2));
				double energyUsed = (1 / sSelected.getThrusters()) * unitsMoved;
				
				if (energyUsed > sSelected.getCapacity())
				{
					return;
				}
				else
				{
					sSelected.moveTo(target); 
					SFX.play(SFX.SHIP_MOVE); 
					sSelected.decrementStat(Ship.CAPACITY, energyUsed);
					sSelected.incrementStat(Ship.RISK, 0.25 * unitsMoved);
				}
			} 
			catch (Exception e) 
			{ 
			}
		}
		//getMapGrid().repaint();
		//getMapGrid().revalidate();
		repaintPanels();
	}
	
	public void operateEntity(Entity eSelected)
	{
		getMapGrid().getTimer().restart();
		if (eSelected instanceof Ship)
		{
			Ship sSelected = (Ship) eSelected;
			sSelected.setTarget(getEntityTargeted());
			if (getEntityTargeted() != null)
			{
				if (isYourTurn())
				{
					setEntityEnemyAffectedCellGroup(getEntityTargeted().getCellGroup());
				}
				if (!isYourTurn())
				{
					setEntityAllyAffectedCellGroup(getEntityTargeted().getCellGroup());
				}
			}
			try { sSelected.operate(); } catch (Exception ex) { return; }
			SFX.play(sSelected.getFireSfxSrc());
			if (getEntityTargeted() != null && !(getEntitySelected() instanceof BeamShip || getEntitySelected() instanceof Mothership))
			{
				SFX.play(getEntityTargeted().getDamagedSfxSrc());
			}
		}
		if (getEntityTargeted() != null)
		{
			if (getEntityTargeted().isDestroyed())
			{
				SFX.play(getEntityTargeted().getDestroyedSfxSrc());
				setEntityDestroyed(true);
				setEntityTargeted(null);
			}
		}
		//getMapGrid().repaint();
		//getMapGrid().revalidate();
		//getEnemyUI().repaint();
		//getEnemyUI().revalidate();
		repaintPanels();
	}

	public EntityPartition getEntityPartSelected() 
	{
		return entityPartSelected;
	}

	public void setEntityPartSelected(EntityPartition entityPartSelected) 
	{
		this.entityPartSelected = entityPartSelected;
	}

	public Entity getEntitySelected() 
	{
		return entitySelected;
	}
	
	public void setEntitySelected(Entity entitySelected) 
	{
		this.entitySelected = entitySelected;
	}

	public Entity getEntityTargeted() 
	{
		return entityTargeted;
	}

	public void setEntityTargeted(Entity entityTargeted) 
	{
		this.entityTargeted = entityTargeted;
	}

	public boolean isYourTurn() 
	{
		return isYourTurn;
	}
	
	public void setYourTurn(boolean isYourTurn)
	{
		if (this.isYourTurn != isYourTurn)
		{
			this.isYourTurn = isYourTurn;
			return;
		}
		
		this.isYourTurn = isYourTurn;
		if (!isYourTurn)
		{
			setEntitySelected(null);
			setEntityTargeted(null);
			setEntityEnemyAffectedCellGroup(null);
			setEntityAllyAffectedCellGroup(null);
			
			ArrayList<Ship> enemies = EntityTracker.getEnemyShips(map);
			for (Ship enemy : enemies)
			{
				enemy.setCapacity(enemy.getMaxCapacity());
			}
			getMapGrid().repaint(); getMapGrid().revalidate();
			manager.startAiTurn();
			repaintPanels();
		}
		if (isYourTurn)
		{
			setEntitySelected(null);
			setEntityTargeted(null);
			setEntityEnemyAffectedCellGroup(null);
			setEntityAllyAffectedCellGroup(null);
			
			ArrayList<Ship> allies = EntityTracker.getAlliedShips(map);
			for (Ship ally : allies)
			{
				ally.setCapacity(ally.getMaxCapacity());
			}
			getMapGrid().repaint(); getMapGrid().revalidate();
			/*ArrayList<BeamShip> beamShips = EntityTracker.getAlliedBeamShips(map);
			ArrayList<Mothership> motherships = EntityTracker.getAlliedMotherships(map);
			for (BeamShip bs : beamShips)
			{
				operateEntity(bs);
			}
			for (Mothership ms : motherships)
			{
				operateEntity(ms);
			}
			getMapGrid().repaint(); getMapGrid().revalidate();*/
			repaintPanels();
		}
	}
	
	public void toggleTurn() 
	{
		isYourTurn = !isYourTurn;
		setEntitySelected(null);
		setEntityTargeted(null);
		setEntityEnemyAffectedCellGroup(null);
		ArrayList<Ship> allies = EntityTracker.getAlliedShips(map);
		for (Ship ally : allies)
		{
			ally.setCapacity(ally.getMaxCapacity());
		}
		ArrayList<Ship> enemies = EntityTracker.getEnemyShips(map);
		for (Ship enemy : enemies)
		{
			enemy.setCapacity(enemy.getMaxCapacity());
		}
		if (!isYourTurn)
		{
			manager.startAiTurn();
		}
		repaintPanels();
	}
	
	public AllyBattleUI getAllyUI()
	{
		return allyUI;
	}
	
	public EnemyBattleUI getEnemyUI()
	{
		return enemyUI;
	}
	
	public void repaintPanels()
	{
		getMapGrid().repaint();
		getMapGrid().revalidate();
		getEnemyUI().repaint();
		getEnemyUI().revalidate();
		getAllyUI().repaint();
		getAllyUI().revalidate();
	}

	public CellGroup getEntityEnemyAffectedCellGroup() {
		return entityEnemyAffectedCG;
	}

	public void setEntityEnemyAffectedCellGroup(CellGroup entityEnemyAffectedCG) {
		this.entityEnemyAffectedCG = entityEnemyAffectedCG;
	}

	public CellGroup getEntityAllyAffectedCellGroup() {
		return entityAllyAffectedCG;
	}

	public void setEntityAllyAffectedCellGroup(CellGroup entityAllyAffectedCG) {
		this.entityAllyAffectedCG = entityAllyAffectedCG;
	}

	public boolean isEntityDestroyed() {
		return entityDestroyed;
	}

	public void setEntityDestroyed(boolean entityDestroyed) {
		this.entityDestroyed = entityDestroyed;
	}

	public BattleManager getManager() {
		return manager;
	}

	public JScrollPane getScrollPane() {
		return scrollPane;
	}
	
	static class Panner extends JButton implements ActionListener, MouseListener
	{
		private BattleDisplay displayReference;
		private Timer scrollTimer;
		private int scrollDirection;
		
		private static final int SCROLL_SPEED = 50;
		
		public static final int UP = 1;
		public static final int DOWN = 2;
		public static final int LEFT = 3;
		public static final int RIGHT = 4;
		
		public Panner(BattleDisplay displayReference, int scrollDirection)
		{
			super();
			if (scrollDirection == UP)
			{
				this.setIcon(new ImageIcon("res/panUp.png"));
			}
			if (scrollDirection == DOWN)
			{
				this.setIcon(new ImageIcon("res/panDown.png"));
			}
			if (scrollDirection == LEFT)
			{
				this.setIcon(new ImageIcon("res/panLeft.png"));
			}
			if (scrollDirection == RIGHT)
			{
				this.setIcon(new ImageIcon("res/panRight.png"));
			}
			this.displayReference = displayReference;
			this.scrollDirection = scrollDirection;
			setBackground(Color.BLACK);
			setForeground(Color.BLACK);
			setBorder(null);
			setEnabled(false);
			scrollTimer = new Timer(33, this);
			addMouseListener(this);
		}

		@Override
		public void mouseEntered(MouseEvent arg0) 
		{
			scrollTimer.restart();
		}
		
		@Override
		public void mouseExited(MouseEvent arg0) 
		{
			scrollTimer.stop();
		}

		@Override
		public void mouseClicked(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mousePressed(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent arg0) 
		{
			// TODO Auto-generated method stub
			
		}


		@Override
		public void actionPerformed(ActionEvent arg0) 
		{
			if (displayReference.getManager().isBattleFinished())
			{
				return;
			}
			if (scrollDirection == UP)
			{
				displayReference.getScrollPane().getVerticalScrollBar().setValue(displayReference.getScrollPane().getVerticalScrollBar().getValue() - SCROLL_SPEED);
			}
			if (scrollDirection == DOWN)
			{
				displayReference.getScrollPane().getVerticalScrollBar().setValue(displayReference.getScrollPane().getVerticalScrollBar().getValue() + SCROLL_SPEED);
			}
			if (scrollDirection == LEFT)
			{
				displayReference.getScrollPane().getHorizontalScrollBar().setValue(displayReference.getScrollPane().getHorizontalScrollBar().getValue() - SCROLL_SPEED);
			}
			if (scrollDirection == RIGHT)
			{
				displayReference.getScrollPane().getHorizontalScrollBar().setValue(displayReference.getScrollPane().getHorizontalScrollBar().getValue() + SCROLL_SPEED);
			}
		}
	}
}
