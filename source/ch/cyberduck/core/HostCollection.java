package ch.cyberduck.core;

/*
 *  Copyright (c) 2006 David Kocher. All rights reserved.
 *  http://cyberduck.ch/
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  Bug fixes, suggestions and comments should be sent to:
 *  dkocher@cyberduck.ch
 */

import com.apple.cocoa.application.NSWorkspace;
import com.apple.cocoa.foundation.*;

import org.apache.log4j.Logger;

import java.io.File;

/**
 * @version $Id$
 */
public class HostCollection extends Collection {
    private static Logger log = Logger.getLogger(HostCollection.class);

    private static HostCollection instance;

    private HostCollection() {
        ;
    }

    public static HostCollection instance() {
        if(null == instance) {
            instance = new HostCollection();
            instance.load();
        }
        return instance;
    }

    public boolean add(Object host) {
        super.add(host);
        this.save();
        return true;
    }

    public void add(int row, Object host) {
        super.add(row, host);
        this.save();
    }

    public Object remove(int row) {
        super.remove(row);
        this.save();
        return null;
    }

    /**
     *
     */
    private static final File BOOKMARKS_FILE
            = new File(Preferences.instance().getProperty("application.support.path"), "Favorites.plist");

    static {
        BOOKMARKS_FILE.getParentFile().mkdir();
    }

    /**
     *
     */
    public void save() {
        this.save(BOOKMARKS_FILE);
    }

    /**
     * Saves this collection of bookmarks in to a file to the users's application support directory
     * in a plist xml format
     */
    private synchronized void save(File f) {
        if(Preferences.instance().getBoolean("favorites.save")) {
            try {
                NSMutableArray list = new NSMutableArray();
                java.util.Iterator i = this.iterator();
                while(i.hasNext()) {
                    Host bookmark = (Host) i.next();
                    list.addObject(bookmark.getAsDictionary());
                }
                NSMutableData collection = new NSMutableData();
                String[] errorString = new String[]{null};
                collection.appendData(NSPropertyListSerialization.dataFromPropertyList(list,
                        NSPropertyListSerialization.PropertyListXMLFormat,
                        errorString));
                if(errorString[0] != null) {
                    log.error("Problem writing bookmark file: " + errorString[0]);
                }

                if(collection.writeToURL(f.toURL(), true)) {
                    if(log.isInfoEnabled())
                        log.info("Bookmarks sucessfully saved to :" + f.toString());
                }
                else {
                    log.error("Error saving Bookmarks to :" + f.toString());
                }
            }
            catch(java.net.MalformedURLException e) {
                log.error(e.getMessage());
            }
        }
    }

    /**
     *
     */
    public void load() {
        this.load(BOOKMARKS_FILE);
    }

    /**
     * Deserialize all the bookmarks saved previously in the users's application support directory
     */
    private synchronized void load(File f) {
        if(f.exists()) {
            final int pool = NSAutoreleasePool.push();
            log.info("Found Bookmarks file: " + f.toString());
            NSData plistData = new NSData(f);
            String[] errorString = new String[]{null};
            Object propertyListFromXMLData =
                    NSPropertyListSerialization.propertyListFromData(plistData,
                            NSPropertyListSerialization.PropertyListImmutable,
                            new int[]{NSPropertyListSerialization.PropertyListXMLFormat},
                            errorString);
            if(errorString[0] != null) {
                log.error("Problem reading bookmark file: " + errorString[0]);
                return;
            }
            if(propertyListFromXMLData instanceof NSArray) {
                NSArray entries = (NSArray) propertyListFromXMLData;
                java.util.Enumeration i = entries.objectEnumerator();
                Object element;
                while(i.hasMoreElements()) {
                    element = i.nextElement();
                    if(element instanceof NSDictionary) {
                        super.add(new Host((NSDictionary) element));
                    }
                }
            }
            NSAutoreleasePool.pop(pool);
        }
    }

    /**
     * @param file
     * @return
     */
    public Host importBookmark(File file) {
        NSData plistData = new NSData(file);
        String[] errorString = new String[]{null};
        Object propertyListFromXMLData =
                NSPropertyListSerialization.propertyListFromData(plistData,
                        NSPropertyListSerialization.PropertyListImmutable,
                        new int[]{NSPropertyListSerialization.PropertyListXMLFormat},
                        errorString);
        if(errorString[0] != null) {
            log.error("Problem reading bookmark file: " + errorString[0]);
            return null;
        }
        if(propertyListFromXMLData instanceof NSDictionary) {
            return new Host((NSDictionary) propertyListFromXMLData);
        }
        log.error("Invalid file format:" + file);
        return null;
    }

    /**
     * @param bookmark
     * @param file
     */
    public void exportBookmark(Host bookmark, File file) {
        try {
            log.info("Exporting bookmark " + bookmark + " to " + file);
            NSMutableData collection = new NSMutableData();
            String[] errorString = new String[]{null};
            collection.appendData(NSPropertyListSerialization.dataFromPropertyList(bookmark.getAsDictionary(),
                    NSPropertyListSerialization.PropertyListXMLFormat,
                    errorString));
            if(errorString[0] != null) {
                log.error("Problem writing bookmark file: " + errorString[0]);
            }
            if(collection.writeToURL(file.toURL(), true)) {
                log.info("Bookmarks sucessfully saved in :" + file.toString());
                NSWorkspace.sharedWorkspace().noteFileSystemChangedAtPath(file.getAbsolutePath());
            }
            else {
                log.error("Error saving Bookmarks in :" + file.toString());
            }
        }
        catch(java.net.MalformedURLException e) {
            log.error(e.getMessage());
        }
    }
}
