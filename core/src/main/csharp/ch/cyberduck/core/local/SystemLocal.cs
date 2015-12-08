﻿// 
// Copyright (c) 2010-2013 Yves Langisch. All rights reserved.
// http://cyberduck.ch/
// 
// This program is free software; you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation; either version 2 of the License, or
// (at your option) any later version.
// 
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
// GNU General Public License for more details.
// 
// Bug fixes, suggestions and comments should be sent to:
// yves@cyberduck.ch
// 

using System;
using System.IO;
using System.Text;
using ch.cyberduck.core;
using org.apache.commons.io;
using org.apache.log4j;
using Path = System.IO.Path;

namespace Ch.Cyberduck.Core.Local
{
    public class SystemLocal : ch.cyberduck.core.Local
    {
        private static readonly Logger Log = Logger.getLogger(typeof (SystemLocal).FullName);

        public SystemLocal(string parent, string name)
            : base(parent, name) {}

        public SystemLocal(ch.cyberduck.core.Local parent, string name)
            : base(parent, name) {}

        public SystemLocal(string path)
            : base(
                Path.Combine(FilenameUtils.getPrefix(path), FilenameUtils.getPath(path)) +
                FilenameUtils.getName(path)) {}

        public override char getDelimiter()
        {
            return '\\';
        }

        public override bool isRoot()
        {
            return getAbsolute().Equals(Directory.GetDirectoryRoot(getAbsolute()));
        }

        public override String getAbbreviatedPath() {
            return getAbsolute();
        }

        public override bool exists()
        {
            string path = getAbsolute();
            if (File.Exists(path))
            {
                return true;
            }
            bool directory = Directory.Exists(path);
            if (directory)
            {
                return true;
            }
            Log.warn(path + " is a non-existing file");
            return false;
        }

        public override bool isSymbolicLink()
        {
            return false;
        }
   }
}