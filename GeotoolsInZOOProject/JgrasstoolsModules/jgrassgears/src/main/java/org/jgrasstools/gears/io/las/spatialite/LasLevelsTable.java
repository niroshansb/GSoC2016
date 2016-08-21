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

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.jgrasstools.gears.spatialite.SpatialiteDb;

import com.vividsolutions.jts.geom.Envelope;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.Polygon;
import com.vividsolutions.jts.io.WKBReader;

/**
 * Table to hold all the table sources.
 * 
 * @author Andrea Antonello (www.hydrologis.com)
 */
public class LasLevelsTable {
    public static final String TABLENAME = "laslevels";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_GEOM = "the_geom";
    public static final String COLUMN_SOURCE_ID = "sources_id";

    public static final String COLUMN_AVG_ELEV = "avgelev";
    public static final String COLUMN_MIN_ELEV = "minelev";
    public static final String COLUMN_MAX_ELEV = "maxelev";

    public static final String COLUMN_AVG_INTENSITY = "avgintensity";
    public static final String COLUMN_MIN_INTENSITY = "minintensity";
    public static final String COLUMN_MAX_INTENSITY = "maxintensity";

    /**
     * Checks if the given level table exists.
     * 
     * @param db the database.
     * @param levelNum the level number to check.
     * @return <code>true</code> if the level table exists.
     * @throws SQLException
     */
    public static boolean hasLevel( SpatialiteDb db, int levelNum ) throws SQLException {
        String tablename = TABLENAME + levelNum;
        return db.hasTable(tablename);
    }

    public static void createTable( SpatialiteDb db, int srid, int levelNum ) throws SQLException {
        String tablename = TABLENAME + levelNum;
        if (!db.hasTable(tablename)) {
            String[] creates = {//
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT", //
                    COLUMN_SOURCE_ID + " INTEGER", //
                    COLUMN_AVG_ELEV + " REAL", //
                    COLUMN_MIN_ELEV + " REAL", //
                    COLUMN_MAX_ELEV + " REAL", //
                    COLUMN_AVG_INTENSITY + " INTEGER", //
                    COLUMN_MIN_INTENSITY + " INTEGER", //
                    COLUMN_MAX_INTENSITY + " INTEGER" //
            };
            db.createTable(tablename, creates);
            db.addGeometryXYColumnAndIndex(tablename, COLUMN_GEOM, "POLYGON", String.valueOf(srid));

            db.createIndex(tablename, COLUMN_SOURCE_ID, false);
            // db.createIndex(TABLENAME, COLUMN_MIN_GPSTIME, false);
            // db.createIndex(TABLENAME, COLUMN_MAX_GPSTIME, false);
            // db.createIndex(TABLENAME, COLUMN_MIN_ELEV, false);
            // db.createIndex(TABLENAME, COLUMN_MAX_ELEV, false);
            // db.createIndex(TABLENAME, COLUMN_MIN_INTENSITY, false);
            // db.createIndex(TABLENAME, COLUMN_MAX_INTENSITY, false);
        }
    }

    /**
     * Insert cell values in the table
     * 
     */
    public static void insertLasLevel( SpatialiteDb db, int srid, LasLevel level ) throws SQLException {
        String sql = "INSERT INTO " + TABLENAME + level.level//
                + " (" + //
                COLUMN_GEOM + "," + //
                COLUMN_SOURCE_ID + "," + //
                COLUMN_AVG_ELEV + "," + //
                COLUMN_MIN_ELEV + "," + //
                COLUMN_MAX_ELEV + "," + //
                COLUMN_AVG_INTENSITY + "," + //
                COLUMN_MIN_INTENSITY + "," + //
                COLUMN_MAX_INTENSITY + //
                ") VALUES (GeomFromText(?, " + srid + "),?,?,?,?,?,?,?)";

        Connection conn = db.getConnection();
        try (PreparedStatement pStmt = conn.prepareStatement(sql)) {
            int i = 1;
            pStmt.setString(i++, level.polygon.toText());
            pStmt.setLong(i++, level.sourceId);
            pStmt.setDouble(i++, level.avgElev);
            pStmt.setDouble(i++, level.minElev);
            pStmt.setDouble(i++, level.maxElev);

            pStmt.setShort(i++, level.avgIntensity);
            pStmt.setShort(i++, level.minIntensity);
            pStmt.setShort(i++, level.maxIntensity);

            pStmt.executeUpdate();
        }
    }

    public static void insertLasLevels( SpatialiteDb db, int srid, List<LasLevel> levels ) throws SQLException {
        if (levels.size() == 0)
            return;
        String sql = "INSERT INTO " + TABLENAME + levels.get(0).level//
                + " (" + //
                COLUMN_GEOM + "," + //
                COLUMN_SOURCE_ID + "," + //
                COLUMN_AVG_ELEV + "," + //
                COLUMN_MIN_ELEV + "," + //
                COLUMN_MAX_ELEV + "," + //
                COLUMN_AVG_INTENSITY + "," + //
                COLUMN_MIN_INTENSITY + "," + //
                COLUMN_MAX_INTENSITY + //
                ") VALUES (GeomFromText(?, " + srid + "),?,?,?,?,?,?,?)";

        Connection conn = db.getConnection();
        boolean autoCommit = conn.getAutoCommit();
        conn.setAutoCommit(false);
        try (PreparedStatement pStmt = conn.prepareStatement(sql)) {
            for( LasLevel level : levels ) {
                int i = 1;
                pStmt.setString(i++, level.polygon.toText());
                pStmt.setLong(i++, level.sourceId);
                pStmt.setDouble(i++, level.avgElev);
                pStmt.setDouble(i++, level.minElev);
                pStmt.setDouble(i++, level.maxElev);

                pStmt.setShort(i++, level.avgIntensity);
                pStmt.setShort(i++, level.minIntensity);
                pStmt.setShort(i++, level.maxIntensity);
                pStmt.addBatch();
            }
            pStmt.executeBatch();
            conn.commit();
            conn.setAutoCommit(autoCommit);
        }
    }

    /**
     * Query the las level table.
     *
     * @param db the db to use.
     * @param levelNum the level to query.
     * @param envelope an optional {@link Envelope} to query spatially.
     * @return the list of extracted level cells.
     * @throws Exception
     */
    public static List<LasLevel> getLasLevels( SpatialiteDb db, int levelNum, Envelope envelope ) throws Exception {
        String tableName = TABLENAME + levelNum;
        List<LasLevel> lasLevels = new ArrayList<>();
        String sql = "SELECT ST_AsBinary(" + COLUMN_GEOM + ") AS " + COLUMN_GEOM + "," + //
                COLUMN_ID + "," + COLUMN_SOURCE_ID + "," + COLUMN_AVG_ELEV + "," + //
                COLUMN_MIN_ELEV + "," + //
                COLUMN_MAX_ELEV + "," + //
                COLUMN_AVG_INTENSITY + "," + //
                COLUMN_MIN_INTENSITY + "," + //
                COLUMN_MAX_INTENSITY;

        sql += " FROM " + tableName;

        if (envelope != null) {
            double x1 = envelope.getMinX();
            double y1 = envelope.getMinY();
            double x2 = envelope.getMaxX();
            double y2 = envelope.getMaxY();
            sql += " WHERE " + db.getSpatialindexBBoxWherePiece(tableName, null, x1, y1, x2, y2);
        }

        Connection conn = db.getConnection();
        WKBReader wkbReader = new WKBReader();
        try (Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while( rs.next() ) {
                LasLevel lasLevel = new LasLevel();
                lasLevel.level = levelNum;
                int i = 1;
                byte[] geomBytes = rs.getBytes(i++);
                Geometry geometry = wkbReader.read(geomBytes);
                if (geometry instanceof Polygon) {
                    Polygon polygon = (Polygon) geometry;
                    lasLevel.polygon = polygon;
                    lasLevel.id = rs.getLong(i++);
                    lasLevel.sourceId = rs.getLong(i++);
                    lasLevel.avgElev = rs.getDouble(i++);
                    lasLevel.minElev = rs.getDouble(i++);
                    lasLevel.maxElev = rs.getDouble(i++);
                    lasLevel.avgIntensity = rs.getShort(i++);
                    lasLevel.minIntensity = rs.getShort(i++);
                    lasLevel.maxIntensity = rs.getShort(i++);
                    lasLevels.add(lasLevel);
                }
            }
            return lasLevels;
        }
    }

    /**
     * Query the las level table on a geometry intersection.
     *
     * @param db the db to use.
     * @param levelNum the level to query.
     * @param geometry an optional {@link Geometry} to query spatially.
     * @return the list of extracted points
     * @throws Exception
     */
    public static List<LasLevel> getLasLevels( SpatialiteDb db, int levelNum, Geometry geometry ) throws Exception {
        String tableName = TABLENAME + levelNum;
        List<LasLevel> lasLevels = new ArrayList<>();
        String sql = "SELECT ST_AsBinary(" + COLUMN_GEOM + ") AS " + COLUMN_GEOM + "," + //
                COLUMN_ID + "," + COLUMN_SOURCE_ID + "," + COLUMN_AVG_ELEV + "," + //
                COLUMN_MIN_ELEV + "," + //
                COLUMN_MAX_ELEV + "," + //
                COLUMN_AVG_INTENSITY + "," + //
                COLUMN_MIN_INTENSITY + "," + //
                COLUMN_MAX_INTENSITY;

        sql += " FROM " + tableName;

        if (geometry != null) {
            sql += " WHERE " + db.getSpatialindexGeometryWherePiece(tableName, null, geometry);
        }

        Connection conn = db.getConnection();
        WKBReader wkbReader = new WKBReader();
        try (Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while( rs.next() ) {
                LasLevel lasLevel = new LasLevel();
                int i = 1;
                byte[] geomBytes = rs.getBytes(i++);
                Geometry tmpGeometry = wkbReader.read(geomBytes);
                if (tmpGeometry instanceof Polygon) {
                    Polygon polygon = (Polygon) tmpGeometry;
                    lasLevel.polygon = polygon;
                    lasLevel.id = rs.getLong(i++);
                    lasLevel.sourceId = rs.getLong(i++);
                    lasLevel.avgElev = rs.getDouble(i++);
                    lasLevel.minElev = rs.getDouble(i++);
                    lasLevel.maxElev = rs.getDouble(i++);
                    lasLevel.avgIntensity = rs.getShort(i++);
                    lasLevel.minIntensity = rs.getShort(i++);
                    lasLevel.maxIntensity = rs.getShort(i++);
                    lasLevels.add(lasLevel);
                }
            }
            return lasLevels;
        }
    }

}
