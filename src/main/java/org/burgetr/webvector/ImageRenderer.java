/**
 * ImageRenderer.java
 * Copyright (c) 2019 Radek Burget
 *
 * CSSBox is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *  
 * CSSBox is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 *  
 * You should have received a copy of the GNU Lesser General Public License
 * along with CSSBox. If not, see <http://www.gnu.org/licenses/>.
 *
 * Created on 18.6.2019, 12:39:29 by Radek Burget
 */
package org.burgetr.webvector;

import java.awt.Dimension;
import java.awt.Font;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

import javax.imageio.ImageIO;

import org.fit.cssbox.css.CSSNorm;
import org.fit.cssbox.css.DOMAnalyzer;
import org.fit.cssbox.io.DOMSource;
import org.fit.cssbox.io.DefaultDOMSource;
import org.fit.cssbox.io.DefaultDocumentSource;
import org.fit.cssbox.io.DocumentSource;
import org.fit.cssbox.layout.BrowserCanvas;
import org.fit.cssbox.layout.BrowserConfig;
import org.fit.cssbox.layout.Viewport;
import org.fit.cssbox.pdf.PDFRenderer;
import org.fit.cssbox.svg.render.SVGDOMRenderer;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import cz.vutbr.web.css.MediaSpec;

/**
 * This class provides a rendering interface for obtaining the document image
 * form an URL.
 * 
 * @author burgetr
 */
public class ImageRenderer
{
    public enum Type { PNG, SVG, PDF };
    
    private String mediaType = "screen";
    private Dimension windowSize;
    private boolean cropWindow = false;
    private boolean loadImages = true;
    private boolean loadBackgroundImages = true;

    public ImageRenderer()
    {
        windowSize = new Dimension(1200, 600);
    }
    
    public void setMediaType(String media)
    {
        mediaType = new String(media);
    }
    
    public void setWindowSize(Dimension size, boolean crop)
    {
        windowSize = new Dimension(size);
        cropWindow = crop;
    }
    
    public void setLoadImages(boolean content, boolean background)
    {
        loadImages = content;
        loadBackgroundImages = background;
    }
    
    /**
     * Renders the URL and prints the result to the specified output stream in the specified
     * format.
     * @param urlstring the source URL
     * @param out output stream
     * @param type output type
     * @return true in case of success, false otherwise
     * @throws SAXException 
     */
    public boolean renderURL(String urlstring, OutputStream out, Type type, String pageFormat) throws IOException, SAXException
    {
        if (!urlstring.startsWith("http:") &&
            !urlstring.startsWith("https:") &&
            !urlstring.startsWith("ftp:") &&
            !urlstring.startsWith("file:"))
                urlstring = "http://" + urlstring;
        
        //Open the network connection 
        DocumentSource docSource = new DefaultDocumentSource(urlstring);
      
        //Parse the input document
        DOMSource parser = new DefaultDOMSource(docSource);
        Document doc = parser.parse();
       
        //create the media specification
        MediaSpec media = new MediaSpec(mediaType);
        media.setDimensions(windowSize.width, windowSize.height);
        media.setDeviceDimensions(windowSize.width, windowSize.height);

        //Create the CSS analyzer
        DOMAnalyzer da = new DOMAnalyzer(doc, docSource.getURL());
        da.setMediaSpec(media);
        
        da.attributesToStyles(); //convert the HTML presentation attributes to inline styles
        da.addStyleSheet(null, CSSNorm.stdStyleSheet(), DOMAnalyzer.Origin.AGENT); //use the standard style sheet
        da.addStyleSheet(null, CSSNorm.userStyleSheet(), DOMAnalyzer.Origin.AGENT); //use the additional style sheet
        da.addStyleSheet(null, CSSNorm.formsStyleSheet(), DOMAnalyzer.Origin.AGENT); //render form fields using css
        da.getStyleSheets(); //load the author style sheets

        BrowserCanvas contentCanvas = new BrowserCanvas(da.getRoot(), da, docSource.getURL());
        contentCanvas.setAutoMediaUpdate(false); //we have a correct media specification, do not update
        contentCanvas.getConfig().setClipViewport(cropWindow);
        contentCanvas.getConfig().setLoadImages(loadImages);
        contentCanvas.getConfig().setLoadBackgroundImages(loadBackgroundImages);

        if (type == Type.PNG)
        {
            contentCanvas.createLayout(windowSize);
            ImageIO.write(contentCanvas.getImage(), "png", out);
        }
        else if (type == Type.SVG)
        {
            setDefaultFonts(contentCanvas.getConfig());
            contentCanvas.createLayout(windowSize);
            Writer w = new OutputStreamWriter(out, "utf-8");
            writeSVG(contentCanvas.getViewport(), w);
            w.close();
        }
        else if (type == Type.PDF)
        {
            setDefaultFonts(contentCanvas.getConfig());
            contentCanvas.createLayout(windowSize);
            writePDF(contentCanvas.getViewport(), out, pageFormat);
        }

        docSource.close();

        return true;
    }
    
    /**
     * Sets some common fonts as the defaults for generic font families.
     */
    protected void setDefaultFonts(BrowserConfig config)
    {
        config.setDefaultFont(Font.SERIF, "Times New Roman");
        config.setDefaultFont(Font.SANS_SERIF, "Arial");
        config.setDefaultFont(Font.MONOSPACED, "Courier New");
    }
    
    /**
     * Renders the viewport using an SVGRenderer to the given output writer.
     * @param vp
     * @param out
     * @throws IOException
     */
    protected void writeSVG(Viewport vp, Writer out) throws IOException
    {
        //obtain the viewport bounds depending on whether we are clipping to viewport size or using the whole page
        int w = vp.getClippedContentBounds().width;
        int h = vp.getClippedContentBounds().height;
        
        SVGDOMRenderer render = new SVGDOMRenderer(w, h, out);
        vp.draw(render);
        render.close();
    }

    /**
     * Renders the viewport using an PDFRenderer to the given output writer.
     * @param vp
     * @param out
     * @throws IOException
     */
    protected void writePDF(Viewport vp, OutputStream out, String pageFormat) throws IOException
    {
        //obtain the viewport bounds depending on whether we are clipping to viewport size or using the whole page
        int w = vp.getClippedContentBounds().width;
        int h = vp.getClippedContentBounds().height;
        
        PDFRenderer render = new PDFRenderer(w, h, out, pageFormat);
        vp.draw(render);
        render.close();
    }    

}
