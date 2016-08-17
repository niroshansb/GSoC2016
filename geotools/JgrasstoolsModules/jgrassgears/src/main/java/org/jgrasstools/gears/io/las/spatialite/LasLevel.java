/*
 * This file is part of JGrasstools (http://www.jgrasstools.org)
 * (C) HydroloGIS - www.hydrologis.com 
 * 
 * JGrasstools is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.jgrasstools.gears.io.las.spatialite;

import com.vividsolutions.jts.geom.Polygon;

/**
 * A level of las average data.
 * 
 * @author Andrea Antonello (www.hydrologis.com)
 */
public class LasLevel {
    public long id;
    public long sourceId;

    public int level;

    public Polygon polygon;

    public double avgElev;
    public double minElev;
    public double maxElev;

    public short avgIntensity;
    public short minIntensity;
    public short maxIntensity;
}
