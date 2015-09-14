/**
 * WebVector.java
 *
 * Created on 21.5.2009, 13:30:25 by burgetr
 */
package org.burgetr.webvector;

import java.io.FileOutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.CancellationException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.fit.cssbox.demo.PdfImageRenderer;

import javax.swing.ButtonGroup;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker.StateValue;

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
import javax.swing.JCheckBox;

import java.awt.GridLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import java.awt.FlowLayout;

import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * This is a wrapper class for calling the CSSBox ImageRenderer demo. 
 * 
 * @author burgetr
 */
public class WebVector
{
    private TransformWorker worker = null;

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
    private JPanel windowSizePanel;
    private JLabel windowSizeLabel;
    private JSpinner wwSpinner;
    private JLabel wxLabel;
    private JSpinner whSpinner;
    private JCheckBox chkCropWindow;
    private JLabel mediaLabel;
    private JComboBox<String> mediaCombo;
    private JRadioButton pdfRadio;
    private JComboBox<String> formatCombo;
    
    
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
    
    private Dimension getWindowSize()
    {
        return new Dimension((Integer) wwSpinner.getValue(), (Integer) whSpinner.getValue());
    }
    
    private void execTransformation()
    {
        try {
            PdfImageRenderer.Type type = PdfImageRenderer.Type.SVG;
            if (pngRadio.isSelected())
                type = PdfImageRenderer.Type.PNG;
            else if (pdfRadio.isSelected())
                type = PdfImageRenderer.Type.PDF;
            
            worker = new TransformWorker(urlText.getText(), destText.getText(),
                    type, mediaCombo.getSelectedItem().toString(), getWindowSize(), chkCropWindow.isSelected(),
                    chkLoadImages.isSelected(), chkLoadBackgroundImages.isSelected(), (String) formatCombo.getSelectedItem());

            worker.addPropertyChangeListener(new PropertyChangeListener()
            {
                @Override
                public void propertyChange(final PropertyChangeEvent event)
                {
                    if (event.getPropertyName().equals("state"))
                    {
                        switch ((StateValue) event.getNewValue())
                        {
                            case DONE:
                                try {
                                    worker.get();
                                    JOptionPane.showMessageDialog(mainFrame, "Rendering succeeded.", "Finished", JOptionPane.INFORMATION_MESSAGE);
                                } catch (final CancellationException e) {
                                      
                                } catch (final Exception e) {
                                    JOptionPane.showMessageDialog(mainFrame, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                                    e.printStackTrace();
                                }
                                startButton.setEnabled(true);
                                statusText.setText("Done");
                                break;
                            case STARTED:
                            case PENDING:
                                startButton.setEnabled(false);
                                statusText.setText("Operation in progress...");
                                break;
                        }
                    }
                }
            });            
            
            worker.execute();
            
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
            mainFrame.setSize(new Dimension(600, 358));
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
            gridBagConstraints11.gridy = 11;
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
            gridBagConstraints3.gridy = 9;
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
            gbl_mainPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 1.0, 0.0, 0.0, 0.0};
            gbl_mainPanel.columnWeights = new double[]{1.0, 0.0};
            mainPanel.setLayout(gbl_mainPanel);
            mainPanel.add(urlLabel, gridBagConstraints0);
            mainPanel.add(getUrlText(), gridBagConstraints);
            mainPanel.add(destLabel, gridBagConstraints1);
            mainPanel.add(getDestText(), gridBagConstraints2);
            GridBagConstraints gbc_settingsPanel = new GridBagConstraints();
            gbc_settingsPanel.insets = new Insets(0, 0, 5, 0);
            gbc_settingsPanel.gridwidth = 2;
            gbc_settingsPanel.gridx = 0;
            gbc_settingsPanel.gridy = 6;
            mainPanel.add(getSettingsPanel(), gbc_settingsPanel);
            GridBagConstraints gbc_windowSizePanel = new GridBagConstraints();
            gbc_windowSizePanel.gridwidth = 2;
            gbc_windowSizePanel.insets = new Insets(0, 0, 5, 5);
            gbc_windowSizePanel.fill = GridBagConstraints.BOTH;
            gbc_windowSizePanel.gridx = 0;
            gbc_windowSizePanel.gridy = 7;
            mainPanel.add(getWindowSizePanel(), gbc_windowSizePanel);
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
            gridBagConstraints9.insets = new Insets(0, 10, 0, 5);
            gridBagConstraints9.gridy = 0;
            typePanel = new JPanel();
            typePanel.setLayout(new GridBagLayout());
            ButtonGroup bgroup = new ButtonGroup();
            bgroup.add(getSvgRadio());
            bgroup.add(getPngRadio());
            bgroup.add(getPdfRadio());
            GridBagConstraints gbc_svgRadio = new GridBagConstraints();
            gbc_svgRadio.insets = new Insets(0, 0, 0, 5);
            gbc_svgRadio.gridx = 0;
            gbc_svgRadio.gridy = 0;
            typePanel.add(getSvgRadio(), gbc_svgRadio);
            typePanel.add(getPngRadio(), gridBagConstraints9);
            GridBagConstraints gbc_pdfRadio = new GridBagConstraints();
            gbc_pdfRadio.insets = new Insets(0, 0, 0, 5);
            gbc_pdfRadio.gridx = 2;
            gbc_pdfRadio.gridy = 0;
            typePanel.add(getPdfRadio(), gbc_pdfRadio);
            GridBagConstraints gbc_formatCombo = new GridBagConstraints();
            gbc_formatCombo.gridx = 3;
            gbc_formatCombo.gridy = 0;
            typePanel.add(getFormatCombo(), gbc_formatCombo);
        }
        return typePanel;
    }

    private void updateFileExtension()
    {
        String text = destText.getText();
        if (text.endsWith(".svg") || text.endsWith(".png") || text.endsWith(".pdf"))
        {
            if (svgRadio.isSelected())
            {
                text = text.substring(0, text.length()-4) + ".svg";
            }
            else if (pngRadio.isSelected())
            {
                text = text.substring(0, text.length()-4) + ".png";
            }
            else if (pdfRadio.isSelected())
            {
                text = text.substring(0, text.length()-4) + ".pdf";
            }
            destText.setText(text);
        }
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
                    updateFileExtension();
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
            pngRadio.addItemListener(new java.awt.event.ItemListener()
            {
                public void itemStateChanged(java.awt.event.ItemEvent e)
                {
                    updateFileExtension();
                }
            });
        }
        return pngRadio;
    }

    private JRadioButton getPdfRadio() {
        if (pdfRadio == null) {
            pdfRadio = new JRadioButton("PDF Document");
            pdfRadio.addItemListener(new java.awt.event.ItemListener()
            {
                public void itemStateChanged(java.awt.event.ItemEvent e)
                {
                    updateFileExtension();
                    //change the visibility of page format options
                    if (pdfRadio.isSelected())
                    {
                        formatCombo.setEnabled(true);
                    }
                    else
                    {
                        formatCombo.setEnabled(false);
                    }
                }
            });
        }
        return pdfRadio;
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
            chkLoadBackgroundImages = new JCheckBox("Include background images");
            chkLoadBackgroundImages.setSelected(true);
        }
        return chkLoadBackgroundImages;
    }

    private JPanel getWindowSizePanel()
    {
        if (windowSizePanel == null)
        {
            windowSizePanel = new JPanel();
            windowSizePanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
            windowSizePanel.add(getMediaLabel());
            windowSizePanel.add(getMediaCombo());
            windowSizePanel.add(getWindowSizeLabel());
            windowSizePanel.add(getWwSpinner());
            windowSizePanel.add(getWxLabel());
            windowSizePanel.add(getWhSpinner());
            windowSizePanel.add(getCropCheckbox());
        }
        return windowSizePanel;
    }

    private JLabel getWindowSizeLabel()
    {
        if (windowSizeLabel == null)
        {
            windowSizeLabel = new JLabel("size");
        }
        return windowSizeLabel;
    }

    private JSpinner getWwSpinner()
    {
        if (wwSpinner == null)
        {
            wwSpinner = new JSpinner();
            wwSpinner.setModel(new SpinnerNumberModel(new Integer(1200),
                    new Integer(100), null, new Integer(10)));
        }
        return wwSpinner;
    }

    private JLabel getWxLabel()
    {
        if (wxLabel == null)
        {
            wxLabel = new JLabel("x");
        }
        return wxLabel;
    }

    private JSpinner getWhSpinner()
    {
        if (whSpinner == null)
        {
            whSpinner = new JSpinner();
            whSpinner.setModel(new SpinnerNumberModel(new Integer(600),
                    new Integer(100), null, new Integer(10)));
        }
        return whSpinner;
    }

    private JCheckBox getCropCheckbox()
    {
        if (chkCropWindow == null)
        {
            chkCropWindow = new JCheckBox("Crop to window size");
        }
        return chkCropWindow;
    }

    private JLabel getMediaLabel()
    {
        if (mediaLabel == null)
        {
            mediaLabel = new JLabel("Media");
        }
        return mediaLabel;
    }

    private JComboBox<String> getMediaCombo()
    {
        if (mediaCombo == null)
        {
            mediaCombo = new JComboBox<String>();
            mediaCombo.setEditable(true);
            mediaCombo.setModel(new DefaultComboBoxModel<String>(new String[] {"screen", "print" }));
        }
        return mediaCombo;
    }
    
    private JComboBox<String> getFormatCombo() {
        if (formatCombo == null) {
            formatCombo = new JComboBox<String>();
            formatCombo.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    int[] size = getSizeFromPageFormat(((String) formatCombo.getSelectedItem()).toUpperCase());
                    wwSpinner.setValue(size[0]);
                    whSpinner.setValue(size[1]);
                }
            });
            formatCombo.setEnabled(false);
            formatCombo.setModel(new DefaultComboBoxModel<String>(new String[] {"A4", "LETTER", "A0", "A1", "A2", "A3", "A5", "A6"}));
        }
        return formatCombo;
    }
    
    private int[] getSizeFromPageFormat(String format)
    {
        if ("A0".equals(format))
            return new int[] {2348, 3370};
        else if ("A1".equals(format))
            return new int[] {1648, 2348};
        else if ("A2".equals(format))
            return new int[] {1191, 1648};
        else if ("A3".equals(format))
            return new int[] {842, 1191};
        else if ("A4".equals(format))
            return new int[] {595, 842};
        else if ("A5".equals(format))
            return new int[] {420, 595};
        else if ("A6".equals(format))
            return new int[] {298, 420};
        else if ("LETTER".equals(format))
            return new int[] {612, 792};
        else
            return new int[] {1200, 600};
    }
    
    public static void main(String[] args)
    {
        if (args.length == 0)
        {
            SwingUtilities.invokeLater(new Runnable()
            {
                public void run() 
                {
                    new WebVector();
                }
            });
        }
        else if (args.length != 3 && args.length != 4)
        {
            System.err.println("Usage: java -jar WebVector.jar <url> <output_file> <output_format> [media]");
            System.err.println();
            System.err.println("Renders a document at the specified URL and stores the document image");
            System.err.println("to the specified file.");
            System.err.println();
            System.err.println("Supported formats:");
            System.err.println("png: a Portable Network Graphics file (bitmap image)");
            System.err.println("svg: a SVG file (vector image)");
            System.err.println("pdf: a PDF document (vector paged document), optionally followed by :page_size");
            System.err.println("     (supported page sizes are A0, A1, A2, A3, A4, A5, A6 or LETTER)");
            System.err.println();
            System.err.println("Media:");
            System.err.println("Media type ('screen', 'print', etc.), default is 'screen'");
            System.err.println("Optionally followed by :resolution (width x height)");
            System.err.println("Trailing ! after the height means cropping to the window size.");
            System.err.println();
            System.err.println("Examples:");
            System.err.println("java -jar WebVector.jar http://www.nytimes.com/ nytimes.svg svg");
            System.err.println("java -jar WebVector.jar file://C:/myfile.html myfile.png png");
            System.err.println("java -jar WebVector.jar http://en.wikipedia.org out.png png print:1000x400");
            System.err.println("java -jar WebVector.jar http://en.wikipedia.org out.png png screen:800x400!");
            System.err.println();
            System.err.println("WebVector is based on the CSSBox rendering engine. See http://cssbox.sourceforge.net/");
            System.exit(0);
        }
        else
        {
            try {
                String pageFormat = "A4";
                
                //decode output type
                PdfImageRenderer.Type type = null;
                if (args[2].equalsIgnoreCase("png"))
                    type = PdfImageRenderer.Type.PNG;
                else if (args[2].equalsIgnoreCase("svg"))
                    type = PdfImageRenderer.Type.SVG;
                else if (args[2].equalsIgnoreCase("pdf") || args[2].toLowerCase().startsWith("pdf:"))
                {
                    type = PdfImageRenderer.Type.PDF;
                    if (args[2].length() > 3)
                        pageFormat = args[2].substring(4).toUpperCase();
                }
                else
                {
                    System.err.println("Error: unknown format");
                    System.exit(1);
                }
                
                //decode media
                String media = "screen";
                Dimension windowSize = new Dimension(1200, 600);
                boolean cropWindow = false;
                if (args.length == 4)
                {
                    Pattern pattern = Pattern.compile("^([A-Za-z]+)(:([0-9]+)x([0-9]+)(\\!?))?");
                    Matcher matcher = pattern.matcher(args[3]);
                    boolean ok = matcher.find();
                    if (ok)
                    {
                        media = matcher.group(1);
                        
                        int ww = windowSize.width;
                        int wh = windowSize.height;
                        try {
                            if (matcher.group(3) != null)
                                ww = Integer.valueOf(matcher.group(3));
                            if (matcher.group(4) != null)
                                wh = Integer.valueOf(matcher.group(4));
                            windowSize = new Dimension(ww, wh);
                        } catch (NumberFormatException e) {
                            ok = false;
                        }
                        if ("!".equals(matcher.group(5)))
                            cropWindow = true;
                    }
                    
                    if (!ok)
                    {
                        System.err.println("Error: invalid media specification");
                        System.exit(2);
                    }
                }
                
                
                FileOutputStream os = new FileOutputStream(args[1]);
                
                PdfImageRenderer r = new PdfImageRenderer();
                r.setMediaType(media);
                r.setWindowSize(windowSize, cropWindow);
                r.renderURL(args[0], os, type, pageFormat);
                
                os.close();
                System.err.println("Done.");
            } catch (Exception e) {
                System.err.println("Error: " + e.getMessage());
            }
        }
    }
    
}
