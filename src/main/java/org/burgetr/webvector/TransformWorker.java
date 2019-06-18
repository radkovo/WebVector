/*
 * TransformWorker.java
 * Copyright (c) 2009-2019 Radek Burget
 *
 * WebVector is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *  
 * WebVector is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 *  
 * You should have received a copy of the GNU Lesser General Public License
 * along with WebVector. If not, see <http://www.gnu.org/licenses/>.
 *
 * Created on 9. 9. 2014, 15:32:58 by burgetr
 */
package org.burgetr.webvector;

import java.awt.Dimension;
import java.io.FileOutputStream;

import javax.swing.SwingWorker;

/**
 *
 * @author burgetr
 */
public class TransformWorker extends SwingWorker<Integer, String>
{
    private String srcUrl;
    private String destFile;
    private String pageFormat;
    private ImageRenderer.Type type;
    private ImageRenderer renderer;
    
    public TransformWorker(String srcUrl, String destFile, ImageRenderer.Type type, String media,
            Dimension windowSize, boolean cropWindow, boolean loadImages, boolean loadBgImages, String pageFormat)
    {
        this.srcUrl = srcUrl;
        this.destFile = destFile;
        this.type = type;
        this.pageFormat = new String(pageFormat);
        
        renderer = new ImageRenderer();
        renderer.setMediaType(media);
        renderer.setWindowSize(windowSize, cropWindow);
        renderer.setLoadImages(loadImages, loadBgImages);
    }

    @Override
    protected Integer doInBackground() throws Exception
    {
        FileOutputStream os = new FileOutputStream(destFile);
        renderer.renderURL(srcUrl, os, type, pageFormat);
        os.close();
        return 0;
    }

}
