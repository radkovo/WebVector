/**
 * ImageRenderer.java
 * Copyright (c) 2019-2020 Radek Burget
 *
 * CSSBox is free software: you can redistribute it and/or modify it under the
 * terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * CSSBox is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with CSSBox. If not, see <http://www.gnu.org/licenses/>.
 *
 * Created on 18.6.2019, 12:39:29 by Radek Burget
 */

package org.burgetr.webvector;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

import org.fit.cssbox.css.CSSNorm;
import org.fit.cssbox.css.DOMAnalyzer;
import org.fit.cssbox.io.DOMSource;
import org.fit.cssbox.io.DefaultDOMSource;
import org.fit.cssbox.io.DefaultDocumentSource;
import org.fit.cssbox.io.DocumentSource;
import org.fit.cssbox.layout.BrowserConfig;
import org.fit.cssbox.layout.Dimension;
import org.fit.cssbox.layout.GraphicsEngine;
import org.fit.cssbox.layout.Viewport;
import org.fit.cssbox.pdf.PDFEngine;
import org.fit.cssbox.svg.render.SVGDOMRenderer;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import cz.vutbr.web.css.MediaSpec;
import java.util.Arrays;

import javax.imageio.ImageIO;

/**
 * This class provides a rendering interface for obtaining the document image
 * form an URL.
 *
 * @author burgetr
 */
public class ImageRenderer
{

    public enum Type
    {
        PNG, SVG, PDF
    }

    private String mediaType = "screen";
    private Dimension windowSize;
    private boolean cropWindow = false;
    private boolean loadImages = true;
    private boolean loadBackgroundImages = true;

    public ImageRenderer()
    {
        windowSize = new Dimension(1960, 600);
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

    public void setWindowSize(java.awt.Dimension size, boolean crop)
    {
        setWindowSize(new Dimension(size.width, size.height), crop);
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

        if (type == Type.PNG)
        {
            GraphicsEngine engine = renderGraphics(docSource, da, false, false);
            ImageIO.write(engine.getImage(), "png", out);
        }
        else if (type == Type.SVG)
        {
            GraphicsEngine engine = renderGraphics(docSource, da, true, true);
            Writer w = new OutputStreamWriter(out, "utf-8");
            writeSVG(engine.getViewport(), w);
            w.close();
        }
        else if (type == Type.PDF)
        {
            PDFEngine engine = renderPdf(docSource, da, pageFormat);
            engine.saveDocument(out);
            engine.closeDocument();
        }

        docSource.close();

        return true;
    }

    /**
     * Renders a document using the graphics engine. 
     * @param docSource
     * @param da
     * @param useFractionalMetrics
     * @param setDefaultFonts
     * @return
     */
    protected GraphicsEngine renderGraphics(DocumentSource docSource, DOMAnalyzer da, boolean useFractionalMetrics, boolean setDefaultFonts)
    {
        GraphicsEngine engine = new GraphicsEngine(da.getRoot(), da, docSource.getURL());
        engine.setUseFractionalMetrics(useFractionalMetrics); //fractional metrics are useful for vector output 
        engine.setAutoMediaUpdate(false); //we have a correct media specification, do not update
        engine.getConfig().setClipViewport(cropWindow);
        engine.getConfig().setLoadImages(loadImages);
        engine.getConfig().setLoadBackgroundImages(loadBackgroundImages);
        if (setDefaultFonts)
            defineLogicalFonts(engine.getConfig());
        engine.createLayout(windowSize);
        return engine;
    }

    /**
     * Renders a document using the PDF engine.
     * @param docSource
     * @param da
     * @param pageFormat
     * @return
     */
    protected PDFEngine renderPdf(DocumentSource docSource, DOMAnalyzer da, String pageFormat)
    {
        PDFEngine engine = new PDFEngine(pageFormat, da.getRoot(), da, docSource.getURL());
        engine.setAutoMediaUpdate(false); //we have a correct media specification, do not update
        engine.getConfig().setClipViewport(cropWindow);
        engine.getConfig().setLoadImages(loadImages);
        engine.getConfig().setLoadBackgroundImages(loadBackgroundImages);
        defineLogicalFonts(engine.getConfig());
        engine.createLayout(windowSize);
        return engine;
    }
    
    /**
     * Sets some common fonts as the defaults for generic font families.
     */
    protected void defineLogicalFonts(BrowserConfig config)
    {
        config.setLogicalFont(BrowserConfig.SERIF, Arrays.asList("Times", "Times New Roman"));
        config.setLogicalFont(BrowserConfig.SANS_SERIF, Arrays.asList("Arial", "Helvetica"));
        config.setLogicalFont(BrowserConfig.MONOSPACE, Arrays.asList("Courier New", "Courier"));
    }

    /**
     * Renders the viewport using an SVGRenderer to the given output writer.
     *
     * @param vp
     * @param out
     * @throws IOException
     */
    protected void writeSVG(Viewport vp, Writer out) throws IOException
    {

        //obtain the viewport bounds depending on whether we are clipping to viewport size or using the whole page
        float w = vp.getClippedContentBounds().width;
        float h = vp.getClippedContentBounds().height;

        SVGDOMRenderer render = new SVGDOMRenderer(w, h, out);
        vp.draw(render);
        render.close();
    }

}
