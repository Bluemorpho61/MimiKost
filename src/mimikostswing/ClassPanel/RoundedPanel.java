/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mimikostswing.ClassPanel;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import javax.swing.JPanel;

/**
 *
 * @author Alkin PC
 */
public class RoundedPanel extends JPanel{
    private int roundtopleft=0;
     private int roundtopright=0;
     private int roundBottomright=0;
     private int roundBottomLeft=0;
    public RoundedPanel(){
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics grphcs) {
        Graphics2D grhp2d = (Graphics2D)grphcs.create();
        grhp2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        grhp2d.setBackground(getBackground());
        Area area = new Area(createRoundTopLeft());
        if (roundtopright>0) {
            area.intersect(new Area(createRoundTopRight()));
        }
        grhp2d.fill(area);
        grhp2d.dispose();
        super.paintComponent(grphcs); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * @return the roundtopleft
     */
    public int getRoundtopleft() {
        return roundtopleft;
    }

    /**
     * @param roundtopleft the roundtopleft to set
     */
    public void setRoundtopleft(int roundtopleft) {
        this.roundtopleft = roundtopleft;
        repaint();
    }

    /**
     * @return the roundtopright
     */
    public int getRoundtopright() {
        return roundtopright;
    }

    /**
     * @param roundtopright the roundtopright to set
     */
    public void setRoundtopright(int roundtopright) {
        this.roundtopright = roundtopright;
        repaint();
    }

    /**
     * @return the roundBottomright
     */
    public int getRoundBottomright() {
        return roundBottomright;
    }

    /**
     * @param roundBottomright the roundBottomright to set
     */
    public void setRoundBottomright(int roundBottomright) {
        this.roundBottomright = roundBottomright;
        repaint();
    }

    /**
     * @return the roundBottomLeft
     */
    public int getRoundBottomLeft() {
        return roundBottomLeft;
    }

    /**
     * @param roundBottomLeft the roundBottomLeft to set
     */
    public void setRoundBottomLeft(int roundBottomLeft) {
        this.roundBottomLeft = roundBottomLeft;
        repaint();
    }
    
    public Shape createRoundTopRight(){
        int width = getWidth();
        int height = getHeight();
        int roundX =Math.min(width, roundtopright);
        int roundY = Math.min(height, roundBottomright);
        Area area = new Area(new RoundRectangle2D.Double(0,0,width,height,roundX,roundY));
        area.add(new Area(new Rectangle2D.Double(0,0, width-roundX/2,height)));
        area.add(new Area(new Rectangle2D.Double(0,roundY/2, width,height-roundY/2)));
        return area;
    }
    public Shape createRoundTopLeft(){
        int width = getWidth();
        int height = getHeight();
        int roundX =Math.min(width, roundtopleft);
        int roundY = Math.min(height, roundBottomLeft);
        Area area = new Area(new RoundRectangle2D.Double(0,0,width,height,roundX,roundY));
        area.add(new Area(new Rectangle2D.Double(roundX/2,0, width-roundX/2,height)));
        area.add(new Area(new Rectangle2D.Double(0,roundY/2, width,height-roundY/2)));
        return area;
    }
    
}
