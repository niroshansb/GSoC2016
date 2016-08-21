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

import static org.jgrasstools.hortonmachine.modules.hydrogeomorphology.hecras.OmsHecrasInputBuilder.AUTHORCONTACTS;
import static org.jgrasstools.hortonmachine.modules.hydrogeomorphology.hecras.OmsHecrasInputBuilder.AUTHORNAMES;
import static org.jgrasstools.hortonmachine.modules.hydrogeomorphology.hecras.OmsHecrasInputBuilder.DESCRIPTION;
import static org.jgrasstools.hortonmachine.modules.hydrogeomorphology.hecras.OmsHecrasInputBuilder.KEYWORDS;
import static org.jgrasstools.hortonmachine.modules.hydrogeomorphology.hecras.OmsHecrasInputBuilder.LABEL;
import static org.jgrasstools.hortonmachine.modules.hydrogeomorphology.hecras.OmsHecrasInputBuilder.LICENSE;
import static org.jgrasstools.hortonmachine.modules.hydrogeomorphology.hecras.OmsHecrasInputBuilder.NAME;
import static org.jgrasstools.hortonmachine.modules.hydrogeomorphology.hecras.OmsHecrasInputBuilder.STATUS;
import static org.jgrasstools.hortonmachine.modules.hydrogeomorphology.hecras.OmsHecrasInputBuilder.inRiver_DESCRIPTION;
import static org.jgrasstools.hortonmachine.modules.hydrogeomorphology.hecras.OmsHecrasInputBuilder.inSectionPoints_DESCRIPTION;
import static org.jgrasstools.hortonmachine.modules.hydrogeomorphology.hecras.OmsHecrasInputBuilder.inSections_DESCRIPTION;
import static org.jgrasstools.hortonmachine.modules.hydrogeomorphology.hecras.OmsHecrasInputBuilder.outHecras_DESCRIPTION;
import static org.jgrasstools.hortonmachine.modules.hydrogeomorphology.hecras.OmsHecrasInputBuilder.pTitle_DESCRIPTION;
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
import org.jgrasstools.hortonmachine.modules.hydrogeomorphology.hecras.OmsHecrasInputBuilder;

@Description(DESCRIPTION)
@Author(name = AUTHORNAMES, contact = AUTHORCONTACTS)
@Keywords(KEYWORDS)
@Label(LABEL)
@Name(NAME)
@Status(STATUS)
@License(LICENSE)
public class HecrasInputBuilder extends JGTModel {
    @Description(inRiver_DESCRIPTION)
    @UI(JGTConstants.FILEIN_UI_HINT)
    @In
    public String inRiverPoints = null;

    @Description(inSections_DESCRIPTION)
    @UI(JGTConstants.FILEIN_UI_HINT)
    @In
    public String inSections = null;

    @Description(inSectionPoints_DESCRIPTION)
    @UI(JGTConstants.FILEIN_UI_HINT)
    @In
    public String inSectionPoints = null;

    @Description(pTitle_DESCRIPTION)
    @In
    public String pTitle = "DEFAULTID";

    @Description(outHecras_DESCRIPTION)
    @In
    @UI(JGTConstants.FILEIN_UI_HINT)
    public String outHecras = null;

    @Execute
    public void process() throws Exception {
        OmsHecrasInputBuilder hecrasinputbuilder = new OmsHecrasInputBuilder();
        hecrasinputbuilder.inRiverPoints = getVector(inRiverPoints);
        hecrasinputbuilder.inSections = getVector(inSections);
        hecrasinputbuilder.inSectionPoints = getVector(inSectionPoints);
        hecrasinputbuilder.pTitle = pTitle;
        hecrasinputbuilder.outHecras = outHecras;
        hecrasinputbuilder.pm = pm;
        hecrasinputbuilder.doProcess = doProcess;
        hecrasinputbuilder.doReset = doReset;
        hecrasinputbuilder.process();
    }
}
