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
package org.jgrasstools.gears.io.generic;

import static org.jgrasstools.gears.i18n.GearsMessages.OMSDATETIME2VALUEMAPREADER_AUTHORCONTACTS;
import static org.jgrasstools.gears.i18n.GearsMessages.OMSDATETIME2VALUEMAPREADER_AUTHORNAMES;
import static org.jgrasstools.gears.i18n.GearsMessages.OMSDATETIME2VALUEMAPREADER_DESCRIPTION;
import static org.jgrasstools.gears.i18n.GearsMessages.OMSDATETIME2VALUEMAPREADER_KEYWORDS;
import static org.jgrasstools.gears.i18n.GearsMessages.OMSDATETIME2VALUEMAPREADER_LABEL;
import static org.jgrasstools.gears.i18n.GearsMessages.OMSDATETIME2VALUEMAPREADER_LICENSE;
import static org.jgrasstools.gears.i18n.GearsMessages.OMSDATETIME2VALUEMAPREADER_NAME;
import static org.jgrasstools.gears.i18n.GearsMessages.OMSDATETIME2VALUEMAPREADER_STATUS;
import static org.jgrasstools.gears.i18n.GearsMessages.OMSDATETIME2VALUEMAPREADER_UI;
import static org.jgrasstools.gears.i18n.GearsMessages.OMSDATETIME2VALUEMAPREADER_DATA_DESCRIPTION;
import static org.jgrasstools.gears.i18n.GearsMessages.OMSDATETIME2VALUEMAPREADER_FILE_NOVALUE_DESCRIPTION;
import static org.jgrasstools.gears.i18n.GearsMessages.OMSDATETIME2VALUEMAPREADER_FILE_DESCRIPTION;
import static org.jgrasstools.gears.i18n.GearsMessages.OMSDATETIME2VALUEMAPREADER_P_COLS_DESCRIPTION;
import static org.jgrasstools.gears.i18n.GearsMessages.OMSDATETIME2VALUEMAPREADER_P_SEPARATOR_DESCRIPTION;
import static org.jgrasstools.gears.libs.modules.JGTConstants.doubleNovalue;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedHashMap;

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

import org.jgrasstools.gears.libs.modules.JGTConstants;
import org.jgrasstools.gears.libs.modules.JGTModel;
import org.joda.time.DateTime;

@Description(OMSDATETIME2VALUEMAPREADER_DESCRIPTION)
@Author(name = OMSDATETIME2VALUEMAPREADER_AUTHORNAMES, contact = OMSDATETIME2VALUEMAPREADER_AUTHORCONTACTS)
@Keywords(OMSDATETIME2VALUEMAPREADER_KEYWORDS)
@Label(OMSDATETIME2VALUEMAPREADER_LABEL)
@Name(OMSDATETIME2VALUEMAPREADER_NAME)
@Status(OMSDATETIME2VALUEMAPREADER_STATUS)
@License(OMSDATETIME2VALUEMAPREADER_LICENSE)
@UI(OMSDATETIME2VALUEMAPREADER_UI)
public class OmsDateTime2ValueMapReader extends JGTModel {

    @Description(OMSDATETIME2VALUEMAPREADER_FILE_DESCRIPTION)
    @UI(JGTConstants.FILEIN_UI_HINT)
    @In
    public String file = null;

    @Description(OMSDATETIME2VALUEMAPREADER_P_COLS_DESCRIPTION)
    @In
    public int pCols = 1;

    @Description(OMSDATETIME2VALUEMAPREADER_P_SEPARATOR_DESCRIPTION)
    @In
    public String pSeparator = ",";

    @Description(OMSDATETIME2VALUEMAPREADER_FILE_NOVALUE_DESCRIPTION)
    @In
    public String fileNovalue = "-9999.0";

    @Description(OMSDATETIME2VALUEMAPREADER_DATA_DESCRIPTION)
    @Out
    public LinkedHashMap<DateTime, double[]> data;

    private BufferedReader csvReader;

    private void ensureOpen() throws IOException {
        if (csvReader == null)
            csvReader = new BufferedReader(new FileReader(file));
    }

    @Execute
    public void readNextLine() throws IOException {
        ensureOpen();
        data = new LinkedHashMap<DateTime, double[]>();
        String line = null;
        if ((line = csvReader.readLine()) != null) {
            String[] lineSplit = line.trim().split(pSeparator);
            for( int i = 0; i < lineSplit.length; i++ ) {
                DateTime dateTime = JGTConstants.dateTimeFormatterYYYYMMDDHHMMSS.parseDateTime(lineSplit[i].trim());

                double[] values = new double[pCols];
                for( int j = i + 1, k = 0; j < i + pCols + 1; j++, k++ ) {
                    double value = Double.parseDouble(lineSplit[j].trim());
                    if (fileNovalue != null) {
                        if (lineSplit[j].trim().equals(fileNovalue)) {
                            // set to internal novalue
                            value = doubleNovalue;
                        }
                    }
                    values[k] = value;
                }
                data.put(dateTime, values);
                i = i + pCols;
            }
        }
    }

    @Finalize
    public void close() throws IOException {
        csvReader.close();
    }
}
