/**
 * WebVector.java
 *
 * Created on 21.5.2009, 13:30:25 by burgetr
 */
package org.burgetr.webvector;

import java.io.FileOutputStream;

import org.fit.cssbox.demo.ImageRenderer;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import javax.swing.JLabel;

/**
 * This is a wrapper class for calling the CSSBox ImageRenderer demo. 
 * 
 * @author burgetr
 */
public class WebVector
{

    private JFrame mainFrame = null;  //  @jve:decl-index=0:visual-constraint="415,61"
    private JPanel mainPanel = null;
    private JLabel urlLabel = null;
    /**
     * This method initializes mainFrame	
     * 	
     * @return javax.swing.JFrame	
     */
    private JFrame getMainFrame()
    {
        if (mainFrame == null)
        {
            mainFrame = new JFrame();
            mainFrame.setSize(new Dimension(509, 270));
            mainFrame.setContentPane(getMainPanel());
        }
        return mainFrame;
    }

    /**
     * This method initializes mainPanel	
     * 	
     * @return javax.swing.JPanel	
     */
    private JPanel getMainPanel()
    {
        if (mainPanel == null)
        {
            mainPanel = new JPanel();
            mainPanel.setLayout(new GridBagLayout());
            mainPanel.add(getUrlLabel());
        }
        return mainPanel;
    }
    
    private JLabel getUrlLabel()
    {
        if (urlLabel == null)
        {
            urlLabel = new JLabel();
            urlLabel.setText("Source URL");
        }
        return urlLabel;
    }

    public static void main(String[] args)
    {
        if (args.length != 3)
        {
            System.err.println("Usage: java -jar WebVector.jar <url> <output_file> <format>");
            System.err.println();
            System.err.println("Renders a document at the specified URL and stores the document image");
            System.err.println("to the specified file.");
            System.err.println();
            System.err.println("Supported formats:");
            System.err.println("png: a Portable Network Graphics file (bitmap image)");
            System.err.println("svg: a SVG file (vector image)");
            System.err.println();
            System.err.println("Examples:");
            System.err.println("java -jar WebVector.jar http://www.nytimes.com/ nytimes.svg svg");
            System.err.println("java -jar WebVector.jar file://C:/myfile.html myfile.png png");
            System.err.println();
            System.err.println("WebVector is based on the CSSBox rendering engine. See http://cssbox.sourceforge.net/");
            System.exit(0);
        }
        
        try {
            short type = -1;
            if (args[2].equalsIgnoreCase("png"))
                type = ImageRenderer.TYPE_PNG;
            else if (args[2].equalsIgnoreCase("svg"))
                type = ImageRenderer.TYPE_SVG;
            else
            {
                System.err.println("Error: unknown format");
                System.exit(0);
            }
            
            FileOutputStream os = new FileOutputStream(args[1]);
            
            ImageRenderer r = new ImageRenderer();
            r.renderURL(args[0], os, type);
            
            os.close();
            System.err.println("Done.");
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
        
    }
    
}
