/*
 * JGrass - Free Open Source Java GIS http://www.jgrass.org 
 * (C) HydroloGIS - www.hydrologis.com 
 * 
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Library General Public License as published by the Free
 * Software Foundation; either version 2 of the License, or (at your option) any
 * later version.
 * 
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Library General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU Library General Public License
 * along with this library; if not, write to the Free Foundation, Inc., 59
 * Temple Place, Suite 330, Boston, MA 02111-1307 USA
 */
package org.jgrasstools.gears.io.adige;

import static org.jgrasstools.gears.i18n.GearsMessages.VEGETATIONLIBRARYREADER_AUTHORCONTACTS;
import static org.jgrasstools.gears.i18n.GearsMessages.VEGETATIONLIBRARYREADER_AUTHORNAMES;
import static org.jgrasstools.gears.i18n.GearsMessages.VEGETATIONLIBRARYREADER_DESCRIPTION;
import static org.jgrasstools.gears.i18n.GearsMessages.VEGETATIONLIBRARYREADER_KEYWORDS;
import static org.jgrasstools.gears.i18n.GearsMessages.VEGETATIONLIBRARYREADER_LABEL;
import static org.jgrasstools.gears.i18n.GearsMessages.VEGETATIONLIBRARYREADER_LICENSE;
import static org.jgrasstools.gears.i18n.GearsMessages.VEGETATIONLIBRARYREADER_NAME;
import static org.jgrasstools.gears.i18n.GearsMessages.VEGETATIONLIBRARYREADER_STATUS;
import static org.jgrasstools.gears.i18n.GearsMessages.VEGETATIONLIBRARYREADER_DATA_DESCRIPTION;
import static org.jgrasstools.gears.i18n.GearsMessages.VEGETATIONLIBRARYREADER_FILE_DESCRIPTION;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import oms3.annotations.Author;
import oms3.annotations.Description;
import oms3.annotations.Execute;
import oms3.annotations.Finalize;
import oms3.annotations.In;
import oms3.annotations.Keywords;
import oms3.annotations.Label;
import oms3.annotations.License;
import oms3.annotations.Name;
import oms3.annotations.Out;
import oms3.annotations.Status;
import oms3.annotations.UI;
import oms3.io.CSTable;
import oms3.io.DataIO;
import oms3.io.TableIterator;

import org.jgrasstools.gears.libs.modules.JGTConstants;
import org.jgrasstools.gears.libs.modules.JGTModel;

@Description(VEGETATIONLIBRARYREADER_DESCRIPTION)
@Author(name = VEGETATIONLIBRARYREADER_AUTHORNAMES, contact = VEGETATIONLIBRARYREADER_AUTHORCONTACTS)
@Keywords(VEGETATIONLIBRARYREADER_KEYWORDS)
@Label(VEGETATIONLIBRARYREADER_LABEL)
@Name(VEGETATIONLIBRARYREADER_NAME)
@Status(VEGETATIONLIBRARYREADER_STATUS)
@License(VEGETATIONLIBRARYREADER_LICENSE)
public class VegetationLibraryReader extends JGTModel {

    @Description(VEGETATIONLIBRARYREADER_FILE_DESCRIPTION)
    @UI(JGTConstants.FILEIN_UI_HINT)
    @In
    public String file = null;

    @Description(VEGETATIONLIBRARYREADER_DATA_DESCRIPTION)
    @Out
    public Map<Integer, VegetationLibraryRecord> data;

    private TableIterator<String[]> rowsIterator;

    private CSTable table;

    private void ensureOpen() throws IOException {
        if (table == null) {
            table = DataIO.table(new File(file), null);
            rowsIterator = (TableIterator<String[]>) table.rows().iterator();
        }
    }

    @Execute
    public void read() throws IOException {
        if (!concatOr(data == null, doReset)) {
            return;
        }
        ensureOpen();
        data = new HashMap<>();
        while( rowsIterator.hasNext() ) {
            String[] row = rowsIterator.next();

            int i = 1;
            int vegetationIndex = (int) Double.parseDouble(row[i++]);
            double architecturalResistance = Double.parseDouble(row[i++]);
            double minStomatalResistanc = Double.parseDouble(row[i++]);

            double[] laiMonths = new double[12];
            for( int j = 0; j < laiMonths.length; j++ ) {
                laiMonths[j] = Double.parseDouble(row[i++]);
            }
            double[] albedoMonths = new double[12];
            for( int j = 0; j < albedoMonths.length; j++ ) {
                albedoMonths[j] = Double.parseDouble(row[i++]);
            }
            double[] roughMonths = new double[12];
            for( int j = 0; j < roughMonths.length; j++ ) {
                roughMonths[j] = Double.parseDouble(row[i++]);
            }
            double[] displMonths = new double[12];
            for( int j = 0; j < displMonths.length; j++ ) {
                displMonths[j] = Double.parseDouble(row[i++]);
            }

            double windHeight = Double.parseDouble(row[i++]);
            double rgl = Double.parseDouble(row[i++]);
            double radAtten = Double.parseDouble(row[i++]);
            double windAtten = Double.parseDouble(row[i++]);
            double trunkRatio = Double.parseDouble(row[i]);

            VegetationLibraryRecord vegetation = new VegetationLibraryRecord(vegetationIndex, architecturalResistance,
                    minStomatalResistanc, laiMonths, albedoMonths, roughMonths, displMonths, windHeight, windAtten, rgl,
                    radAtten, trunkRatio);

            data.put(vegetationIndex, vegetation);
        }
    }

    @Finalize
    public void close() throws IOException {
        rowsIterator.close();
    }
}
