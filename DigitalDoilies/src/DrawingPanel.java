import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class DrawingPanel extends JPanel {
	
	private int numberOfSectors;
	
	public DrawingPanel(int defaultNumberOfSectors) {
		this.setBackground(Color.BLACK);
		this.setPreferredSize(new Dimension(400,400));
		this.setMinimumSize(new Dimension(200,200));
		changeSectors(defaultNumberOfSectors);
	}
	
	public void changeSectors(int numberOfSectors) {
		
		this.numberOfSectors = numberOfSectors;
		
		repaint();
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		
		Graphics2D g2 = (Graphics2D) g;
		
		g2.setColor(Color.WHITE);
		
		for (int i = 0; i < numberOfSectors; i++) {
			g2.drawLine(this.getWidth()/2, this.getHeight()/2, this.getWidth()/2, this.getHeight()/6);
			g2.rotate(Math.PI*2/numberOfSectors * i, this.getWidth()/2,this.getHeight()/2);
		}
		
		super.paintComponent(g);
	}
	
}