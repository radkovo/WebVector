/*
 * TransformWorker.java
 * Copyright (c) 2009-2014 Radek Burget
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

import java.io.FileOutputStream;

import javax.swing.SwingWorker;

import org.fit.cssbox.demo.ImageRenderer;

/**
 *
 * @author burgetr
 */
public class TransformWorker extends SwingWorker<Integer, String>
{
    private String srcUrl;
    private String destFile;
    private short type;
    private boolean loadImages;
    private boolean loadBgImages;
    
    
    public TransformWorker(String srcUrl, String destFile, short type,
            boolean loadImages, boolean loadBgImages)
    {
        this.srcUrl = srcUrl;
        this.destFile = destFile;
        this.type = type;
        this.loadImages = loadImages;
        this.loadBgImages = loadBgImages;
    }

    @Override
    protected Integer doInBackground() throws Exception
    {
        FileOutputStream os = new FileOutputStream(destFile);
        
        ImageRenderer r = new ImageRenderer();
        r.setLoadImages(loadImages, loadBgImages);
        r.renderURL(srcUrl, os, type);
        
        os.close();

        return 0;
    }

}
