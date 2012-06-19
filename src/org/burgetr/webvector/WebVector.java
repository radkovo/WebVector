/**
 * WebVector.java
 *
 * Created on 21.5.2009, 13:30:25 by burgetr
 */
package org.burgetr.webvector;

import java.io.FileOutputStream;
import java.net.MalformedURLException;
import java.net.URL;

import org.fit.cssbox.demo.ImageRenderer;

import javax.swing.ButtonGroup;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.GridBagConstraints;
import java.awt.Font;
import java.awt.Insets;
import javax.swing.JButton;
import javax.swing.JRadioButton;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.SwingConstants;
import javax.swing.JCheckBox;
import java.awt.GridLayout;

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
    private JPanel buttonPanel = null;
    private JButton closeButton = null;
    private JPanel settingsPanel;
    private JCheckBox chkLoadImages;
    private JCheckBox chkLoadBackgroundImages;
    
    
    /**
     * @wbp.parser.entryPoint
     */
    public WebVector()
    {
        mainFrame = getMainFrame();
        validateForm();
        mainFrame.setVisible(true);
    }

    private void validateForm()
    {
        boolean valid = true;
        
        String urls = urlText.getText();
        if (urls.isEmpty() || urls.equals("http://"))
        {
            valid = false;
            statusText.setText("Enter source URL");
        }
        
        if (valid)
        {
            try {
                new URL(urlText.getText());
            } catch (MalformedURLException e) {
                valid = false;
                statusText.setText("Invalid URL");
            }
        }
        
        if (valid)
        {
            if (destText.getText().isEmpty())
            {
                valid = false;
                statusText.setText("Choose a destination file");
            }
        }
        
        if (valid)
            statusText.setText("Waiting for start");
        
        statusText.setForeground(valid ? Color.BLACK : Color.RED);
        startButton.setEnabled(valid);
    }
    
    private void execTransformation()
    {
        try {
            startButton.setEnabled(false);
            statusText.setText("Operation in progress...");
            short type = ImageRenderer.TYPE_SVG;
            if (pngRadio.isSelected())
                type = ImageRenderer.TYPE_PNG;
            
            FileOutputStream os = new FileOutputStream(destText.getText());
            
            ImageRenderer r = new ImageRenderer();
            r.setLoadImages(chkLoadImages.isSelected(), chkLoadBackgroundImages.isSelected());
            r.renderURL(urlText.getText(), os, type);
            
            os.close();
            startButton.setEnabled(true);
            statusText.setText("Done");
            JOptionPane.showMessageDialog(mainFrame, "Rendering succeeded.", "Finished", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            startButton.setEnabled(true);
            statusText.setText("Error");
            JOptionPane.showMessageDialog(mainFrame, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
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
            mainFrame.setSize(new Dimension(509, 310));
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
            GridBagConstraints gridBagConstraints11 = new GridBagConstraints();
            gridBagConstraints11.insets = new Insets(0, 0, 10, 0);
            gridBagConstraints11.gridx = 0;
            gridBagConstraints11.fill = GridBagConstraints.HORIZONTAL;
            gridBagConstraints11.gridwidth = 2;
            gridBagConstraints11.gridy = 10;
            GridBagConstraints gridBagConstraints8 = new GridBagConstraints();
            gridBagConstraints8.gridx = 0;
            gridBagConstraints8.anchor = GridBagConstraints.WEST;
            gridBagConstraints8.insets = new Insets(10, 0, 5, 5);
            gridBagConstraints8.gridy = 4;
            typeLabel = new JLabel();
            typeLabel.setText("Output type");
            GridBagConstraints gridBagConstraints7 = new GridBagConstraints();
            gridBagConstraints7.insets = new Insets(0, 0, 5, 0);
            gridBagConstraints7.gridx = 0;
            gridBagConstraints7.gridwidth = 2;
            gridBagConstraints7.fill = GridBagConstraints.BOTH;
            gridBagConstraints7.gridy = 5;
            GridBagConstraints gridBagConstraints6 = new GridBagConstraints();
            gridBagConstraints6.insets = new Insets(0, 0, 5, 0);
            gridBagConstraints6.gridx = 1;
            gridBagConstraints6.gridy = 3;
            GridBagConstraints gridBagConstraints5 = new GridBagConstraints();
            gridBagConstraints5.insets = new Insets(0, 0, 5, 0);
            gridBagConstraints5.gridx = 1;
            gridBagConstraints5.gridy = 1;
            GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
            gridBagConstraints3.fill = GridBagConstraints.BOTH;
            gridBagConstraints3.gridy = 8;
            gridBagConstraints3.weightx = 1.0;
            gridBagConstraints3.insets = new Insets(10, 0, 10, 0);
            gridBagConstraints3.gridwidth = 2;
            gridBagConstraints3.gridx = 0;
            GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
            gridBagConstraints2.insets = new Insets(0, 0, 5, 5);
            gridBagConstraints2.fill = GridBagConstraints.BOTH;
            gridBagConstraints2.gridy = 3;
            gridBagConstraints2.weightx = 1.0;
            gridBagConstraints2.gridx = 0;
            GridBagConstraints gridBagConstraints0 = new GridBagConstraints();
            gridBagConstraints0.insets = new Insets(0, 0, 5, 5);
            gridBagConstraints0.gridx = 0;
            gridBagConstraints0.gridy = 0;
            gridBagConstraints0.anchor = GridBagConstraints.WEST;
            urlLabel = new JLabel();
            urlLabel.setText("Source URL");
            GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
            gridBagConstraints1.gridx = 0;
            gridBagConstraints1.anchor = GridBagConstraints.WEST;
            gridBagConstraints1.insets = new Insets(10, 0, 5, 5);
            gridBagConstraints1.gridy = 2;
            destLabel = new JLabel();
            destLabel.setText("Destination file");
            GridBagConstraints gridBagConstraints = new GridBagConstraints();
            gridBagConstraints.insets = new Insets(0, 0, 5, 5);
            gridBagConstraints.fill = GridBagConstraints.BOTH;
            gridBagConstraints.gridy = 1;
            gridBagConstraints.weightx = 1.0;
            gridBagConstraints.gridx = 0;
            mainPanel = new JPanel();
            GridBagLayout gbl_mainPanel = new GridBagLayout();
            gbl_mainPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0};
            gbl_mainPanel.columnWeights = new double[]{1.0, 0.0};
            mainPanel.setLayout(gbl_mainPanel);
            mainPanel.add(urlLabel, gridBagConstraints0);
            mainPanel.add(getUrlText(), gridBagConstraints);
            mainPanel.add(destLabel, gridBagConstraints1);
            mainPanel.add(getDestText(), gridBagConstraints2);
            GridBagConstraints gbc_settingsPanel = new GridBagConstraints();
            gbc_settingsPanel.gridheight = 2;
            gbc_settingsPanel.gridwidth = 2;
            gbc_settingsPanel.gridx = 0;
            gbc_settingsPanel.gridy = 6;
            mainPanel.add(getSettingsPanel(), gbc_settingsPanel);
            mainPanel.add(getStatusText(), gridBagConstraints3);
            mainPanel.add(getBrowseUrlButton(), gridBagConstraints5);
            mainPanel.add(getBrowseDestButton(), gridBagConstraints6);
            mainPanel.add(getTypePanel(), gridBagConstraints7);
            mainPanel.add(typeLabel, gridBagConstraints8);
            mainPanel.add(getButtonPanel(), gridBagConstraints11);
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
            urlText.setCaretPosition(urlText.getText().length());
            urlText.addCaretListener(new javax.swing.event.CaretListener()
            {
                public void caretUpdate(javax.swing.event.CaretEvent e)
                {
                    validateForm();
                }
            });
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
            String s = System.getProperty("user.home") + System.getProperty("file.separator");
            destText.setText(s + "output.svg");
            destText.addCaretListener(new javax.swing.event.CaretListener()
            {
                public void caretUpdate(javax.swing.event.CaretEvent e)
                {
                    validateForm();
                }
            });
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
            startButton.setEnabled(false);
            startButton.addActionListener(new java.awt.event.ActionListener()
            {
                public void actionPerformed(java.awt.event.ActionEvent e)
                {
                    execTransformation();
                }
            });
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
            browseUrlButton.addActionListener(new java.awt.event.ActionListener()
            {
                public void actionPerformed(java.awt.event.ActionEvent e)
                {
                    JFileChooser chooser = new JFileChooser();
                    chooser.setDialogTitle("Source file");
                    chooser.setAcceptAllFileFilterUsed(true);
                    FileFilter fhtml = new FileNameExtensionFilter("(X)HTML Files", "html");
                    chooser.addChoosableFileFilter(fhtml);
                    chooser.setFileFilter(fhtml);
                    int returnVal = chooser.showOpenDialog(mainFrame);
                    if(returnVal == JFileChooser.APPROVE_OPTION)
                    {
                        String path = chooser.getSelectedFile().getAbsolutePath();
                        urlText.setText("file://" + path);
                    }               
                    
                }
            });
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
            browseDestButton.addActionListener(new java.awt.event.ActionListener()
            {
                public void actionPerformed(java.awt.event.ActionEvent e)
                {
                    JFileChooser chooser = new JFileChooser();
                    chooser.setDialogTitle("Destination file");
                    chooser.setAcceptAllFileFilterUsed(false);
                    FileFilter fall = new FileNameExtensionFilter("All supported images", "svg", "png");
                    FileFilter fsvg = new FileNameExtensionFilter("SVG vector images", "svg");
                    FileFilter fpng = new FileNameExtensionFilter("PNG bitmap images", "png");
                    chooser.addChoosableFileFilter(fall);
                    chooser.addChoosableFileFilter(fsvg);
                    chooser.addChoosableFileFilter(fpng);
                    chooser.setFileFilter(svgRadio.isSelected() ? fsvg : fpng);
                    int returnVal = chooser.showSaveDialog(mainFrame);
                    if(returnVal == JFileChooser.APPROVE_OPTION)
                    {
                        String path = chooser.getSelectedFile().getAbsolutePath();
                        destText.setText(path);
                    }               
                }
            });
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
            ButtonGroup bgroup = new ButtonGroup();
            bgroup.add(getSvgRadio());
            bgroup.add(getPngRadio());
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
            svgRadio.setSelected(true);
            svgRadio.addItemListener(new java.awt.event.ItemListener()
            {
                public void itemStateChanged(java.awt.event.ItemEvent e)
                {
                    String text = destText.getText();
                    if (svgRadio.isSelected() && text.endsWith(".png"))
                    {
                        text = text.substring(0, text.length()-4) + ".svg";
                        destText.setText(text);
                    }
                    if (!svgRadio.isSelected() && text.endsWith(".svg"))
                    {
                        text = text.substring(0, text.length()-4) + ".png";
                        destText.setText(text);
                    }
                }
            });
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

    /**
     * This method initializes buttonPanel	
     * 	
     * @return javax.swing.JPanel	
     */
    private JPanel getButtonPanel()
    {
        if (buttonPanel == null)
        {
            GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
            gridBagConstraints4.gridwidth = 2;
            gridBagConstraints4.gridy = -1;
            gridBagConstraints4.gridx = -1;
            GridBagConstraints gridBagConstraints10 = new GridBagConstraints();
            gridBagConstraints10.gridx = 2;
            gridBagConstraints10.insets = new Insets(0, 30, 0, 0);
            gridBagConstraints10.gridy = 0;
            buttonPanel = new JPanel();
            buttonPanel.setLayout(new GridBagLayout());
            buttonPanel.add(getStartButton(), gridBagConstraints4);
            buttonPanel.add(getCloseButton(), gridBagConstraints10);
        }
        return buttonPanel;
    }

    /**
     * This method initializes closeButton	
     * 	
     * @return javax.swing.JButton	
     */
    private JButton getCloseButton()
    {
        if (closeButton == null)
        {
            closeButton = new JButton();
            closeButton.setText("Close");
            closeButton.setFont(new Font("Dialog", Font.PLAIN, 12));
            closeButton.addActionListener(new java.awt.event.ActionListener()
            {
                public void actionPerformed(java.awt.event.ActionEvent e)
                {
                    mainFrame.setVisible(false);
                    System.exit(0);
                }
            });
        }
        return closeButton;
    }

    private JPanel getSettingsPanel()
    {
        if (settingsPanel == null)
        {
            settingsPanel = new JPanel();
            settingsPanel.setLayout(new GridLayout(1, 1, 0, 0));
            settingsPanel.add(getChkLoadImages());
            settingsPanel.add(getChkLoadBackgroundImages());
        }
        return settingsPanel;
    }

    private JCheckBox getChkLoadImages()
    {
        if (chkLoadImages == null)
        {
            chkLoadImages = new JCheckBox("Include content images");
            chkLoadImages.setSelected(true);
        }
        return chkLoadImages;
    }

    private JCheckBox getChkLoadBackgroundImages()
    {
        if (chkLoadBackgroundImages == null)
        {
            chkLoadBackgroundImages = new JCheckBox(
                    "Include background images");
            chkLoadBackgroundImages.setSelected(true);
        }
        return chkLoadBackgroundImages;
    }

    public static void main(String[] args)
    {
        if (args.length == 0)
        {
            new WebVector();
        }
        else if (args.length != 3)
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
        else
        {
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
    
}
