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
package org.jgrasstools.modules;

import static org.jgrasstools.gears.i18n.GearsMessages.OMSRASTERVECTORINTERSECTOR_AUTHORCONTACTS;
import static org.jgrasstools.gears.i18n.GearsMessages.OMSRASTERVECTORINTERSECTOR_AUTHORNAMES;
import static org.jgrasstools.gears.i18n.GearsMessages.OMSRASTERVECTORINTERSECTOR_DESCRIPTION;
import static org.jgrasstools.gears.i18n.GearsMessages.OMSRASTERVECTORINTERSECTOR_KEYWORDS;
import static org.jgrasstools.gears.i18n.GearsMessages.OMSRASTERVECTORINTERSECTOR_LABEL;
import static org.jgrasstools.gears.i18n.GearsMessages.OMSRASTERVECTORINTERSECTOR_LICENSE;
import static org.jgrasstools.gears.i18n.GearsMessages.OMSRASTERVECTORINTERSECTOR_NAME;
import static org.jgrasstools.gears.i18n.GearsMessages.OMSRASTERVECTORINTERSECTOR_STATUS;
import static org.jgrasstools.gears.i18n.GearsMessages.OMSRASTERVECTORINTERSECTOR_DO_INVERSE_DESCRIPTION;
import static org.jgrasstools.gears.i18n.GearsMessages.OMSRASTERVECTORINTERSECTOR_IN_RASTER_DESCRIPTION;
import static org.jgrasstools.gears.i18n.GearsMessages.OMSRASTERVECTORINTERSECTOR_IN_VECTOR_DESCRIPTION;
import static org.jgrasstools.gears.i18n.GearsMessages.OMSRASTERVECTORINTERSECTOR_OUT_RASTER_DESCRIPTION;
import oms3.annotations.Author;
import oms3.annotations.Description;
import oms3.annotations.Execute;
import oms3.annotations.In;
import oms3.annotations.Keywords;
import oms3.annotations.Label;
import oms3.annotations.License;
import oms3.annotations.Name;
import oms3.annotations.Status;
import oms3.annotations.UI;

import org.jgrasstools.gears.libs.modules.JGTConstants;
import org.jgrasstools.gears.libs.modules.JGTModel;
import org.jgrasstools.gears.modules.r.rastervectorintersection.OmsRasterVectorIntersector;

@Description(OMSRASTERVECTORINTERSECTOR_DESCRIPTION)
@Author(name = OMSRASTERVECTORINTERSECTOR_AUTHORNAMES, contact = OMSRASTERVECTORINTERSECTOR_AUTHORCONTACTS)
@Keywords(OMSRASTERVECTORINTERSECTOR_KEYWORDS)
@Label(OMSRASTERVECTORINTERSECTOR_LABEL)
@Name("_" + OMSRASTERVECTORINTERSECTOR_NAME)
@Status(OMSRASTERVECTORINTERSECTOR_STATUS)
@License(OMSRASTERVECTORINTERSECTOR_LICENSE)
public class RasterVectorIntersector extends JGTModel {

    @Description(OMSRASTERVECTORINTERSECTOR_IN_VECTOR_DESCRIPTION)
    @UI(JGTConstants.FILEIN_UI_HINT)
    @In
    public String inVector = null;

    @Description(OMSRASTERVECTORINTERSECTOR_IN_RASTER_DESCRIPTION)
    @UI(JGTConstants.FILEIN_UI_HINT)
    @In
    public String inRaster;

    @Description(OMSRASTERVECTORINTERSECTOR_DO_INVERSE_DESCRIPTION)
    @In
    public boolean doInverse = false;

    @Description(OMSRASTERVECTORINTERSECTOR_OUT_RASTER_DESCRIPTION)
    @UI(JGTConstants.FILEOUT_UI_HINT)
    @In
    public String outRaster;

    @Execute
    public void process() throws Exception {
        OmsRasterVectorIntersector rastervectorintersector = new OmsRasterVectorIntersector();
        rastervectorintersector.inVector = getVector(inVector);
        rastervectorintersector.inRaster = getRaster(inRaster);
        rastervectorintersector.doInverse = doInverse;
        rastervectorintersector.pm = pm;
        rastervectorintersector.doProcess = doProcess;
        rastervectorintersector.doReset = doReset;
        rastervectorintersector.process();
        dumpRaster(rastervectorintersector.outRaster, outRaster);
    }
}
