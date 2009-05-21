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
import javax.swing.JTextField;
import java.awt.GridBagConstraints;
import java.awt.Font;
import java.awt.Insets;
import javax.swing.JButton;
import javax.swing.JRadioButton;

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
    private JTextField urlText = null;
    private JLabel destLabel = null;
    private JTextField destText = null;
    private JTextField statusText = null;
    private JButton startButton = null;
    private JButton browseUrlButton = null;
    private JButton browseDestButton = null;
    private JPanel typePanel = null;
    private JRadioButton svgRadio = null;
    private JLabel typeLabel = null;
    private JRadioButton pngRadio = null;
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
            mainFrame.setTitle("WebVector");
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
            GridBagConstraints gridBagConstraints8 = new GridBagConstraints();
            gridBagConstraints8.gridx = 0;
            gridBagConstraints8.anchor = GridBagConstraints.WEST;
            gridBagConstraints8.insets = new Insets(10, 0, 0, 0);
            gridBagConstraints8.gridy = 4;
            typeLabel = new JLabel();
            typeLabel.setText("Output type");
            GridBagConstraints gridBagConstraints7 = new GridBagConstraints();
            gridBagConstraints7.gridx = 0;
            gridBagConstraints7.gridwidth = 2;
            gridBagConstraints7.fill = GridBagConstraints.BOTH;
            gridBagConstraints7.gridy = 5;
            GridBagConstraints gridBagConstraints6 = new GridBagConstraints();
            gridBagConstraints6.gridx = 1;
            gridBagConstraints6.gridy = 3;
            GridBagConstraints gridBagConstraints5 = new GridBagConstraints();
            gridBagConstraints5.gridx = 1;
            gridBagConstraints5.gridy = 1;
            GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
            gridBagConstraints4.gridx = 0;
            gridBagConstraints4.gridwidth = 2;
            gridBagConstraints4.gridy = 7;
            GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
            gridBagConstraints3.fill = GridBagConstraints.BOTH;
            gridBagConstraints3.gridy = 6;
            gridBagConstraints3.weightx = 1.0;
            gridBagConstraints3.insets = new Insets(10, 0, 10, 0);
            gridBagConstraints3.gridwidth = 2;
            gridBagConstraints3.gridx = 0;
            GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
            gridBagConstraints2.fill = GridBagConstraints.BOTH;
            gridBagConstraints2.gridy = 3;
            gridBagConstraints2.weightx = 1.0;
            gridBagConstraints2.gridx = 0;
            GridBagConstraints gridBagConstraints0 = new GridBagConstraints();
            gridBagConstraints0.anchor = GridBagConstraints.WEST;
            urlLabel = new JLabel();
            urlLabel.setText("Source URL");
            GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
            gridBagConstraints1.gridx = 0;
            gridBagConstraints1.anchor = GridBagConstraints.WEST;
            gridBagConstraints1.insets = new Insets(10, 0, 0, 0);
            gridBagConstraints1.gridy = 2;
            destLabel = new JLabel();
            destLabel.setText("Destination file");
            GridBagConstraints gridBagConstraints = new GridBagConstraints();
            gridBagConstraints.fill = GridBagConstraints.BOTH;
            gridBagConstraints.gridy = 1;
            gridBagConstraints.weightx = 1.0;
            gridBagConstraints.gridx = 0;
            mainPanel = new JPanel();
            mainPanel.setLayout(new GridBagLayout());
            mainPanel.add(urlLabel, gridBagConstraints0);
            mainPanel.add(getUrlText(), gridBagConstraints);
            mainPanel.add(destLabel, gridBagConstraints1);
            mainPanel.add(getDestText(), gridBagConstraints2);
            mainPanel.add(getStatusText(), gridBagConstraints3);
            mainPanel.add(getStartButton(), gridBagConstraints4);
            mainPanel.add(getBrowseUrlButton(), gridBagConstraints5);
            mainPanel.add(getBrowseDestButton(), gridBagConstraints6);
            mainPanel.add(getTypePanel(), gridBagConstraints7);
            mainPanel.add(typeLabel, gridBagConstraints8);
        }
        return mainPanel;
    }
    
    /**
     * This method initializes urlText	
     * 	
     * @return javax.swing.JTextField	
     */
    private JTextField getUrlText()
    {
        if (urlText == null)
        {
            urlText = new JTextField();
            urlText.setText("http://");
        }
        return urlText;
    }

    /**
     * This method initializes destText	
     * 	
     * @return javax.swing.JTextField	
     */
    private JTextField getDestText()
    {
        if (destText == null)
        {
            destText = new JTextField();
            destText.setText("output.svg");
        }
        return destText;
    }

    /**
     * This method initializes statusText	
     * 	
     * @return javax.swing.JTextField	
     */
    private JTextField getStatusText()
    {
        if (statusText == null)
        {
            statusText = new JTextField();
            statusText.setText("Waiting for start");
            statusText.setFont(new Font("Dialog", Font.PLAIN, 18));
            statusText.setEditable(false);
        }
        return statusText;
    }

    /**
     * This method initializes startButton	
     * 	
     * @return javax.swing.JButton	
     */
    private JButton getStartButton()
    {
        if (startButton == null)
        {
            startButton = new JButton();
            startButton.setText("Start");
        }
        return startButton;
    }

    /**
     * This method initializes browseUrlButton	
     * 	
     * @return javax.swing.JButton	
     */
    private JButton getBrowseUrlButton()
    {
        if (browseUrlButton == null)
        {
            browseUrlButton = new JButton();
            browseUrlButton.setText("Browse...");
            browseUrlButton.setToolTipText("Browse for local files");
        }
        return browseUrlButton;
    }

    /**
     * This method initializes browseDestButton	
     * 	
     * @return javax.swing.JButton	
     */
    private JButton getBrowseDestButton()
    {
        if (browseDestButton == null)
        {
            browseDestButton = new JButton();
            browseDestButton.setText("Browse...");
        }
        return browseDestButton;
    }

    /**
     * This method initializes typePanel	
     * 	
     * @return javax.swing.JPanel	
     */
    private JPanel getTypePanel()
    {
        if (typePanel == null)
        {
            GridBagConstraints gridBagConstraints9 = new GridBagConstraints();
            gridBagConstraints9.gridx = 1;
            gridBagConstraints9.insets = new Insets(0, 10, 0, 0);
            gridBagConstraints9.gridy = 0;
            typePanel = new JPanel();
            typePanel.setLayout(new GridBagLayout());
            typePanel.add(getSvgRadio(), new GridBagConstraints());
            typePanel.add(getPngRadio(), gridBagConstraints9);
        }
        return typePanel;
    }

    /**
     * This method initializes svgRadio	
     * 	
     * @return javax.swing.JRadioButton	
     */
    private JRadioButton getSvgRadio()
    {
        if (svgRadio == null)
        {
            svgRadio = new JRadioButton();
            svgRadio.setText("SVG Vector Graphics");
        }
        return svgRadio;
    }

    /**
     * This method initializes pngRadio	
     * 	
     * @return javax.swing.JRadioButton	
     */
    private JRadioButton getPngRadio()
    {
        if (pngRadio == null)
        {
            pngRadio = new JRadioButton();
            pngRadio.setText("PNG Bitmap Graphics");
        }
        return pngRadio;
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
