package org.zoo_project;

import java.io.*;
import org.geotools.data.*;
import org.geotools.data.simple.*;
import org.geotools.feature.simple.*;
import org.geotools.geometry.jts.*;
import org.opengis.feature.*;
import org.opengis.feature.simple.*;
import org.opengis.feature.type.*;
import java.lang.*;
import java.util.*;
import com.vividsolutions.jts.geom.*;
import com.vividsolutions.jts.*;
import org.geotools.data.collection.*;
import org.opengis.referencing.crs.*;
import org.geotools.referencing.*;
import javax.xml.namespace.*;
import java.net.*;
import org.geotools.*;
import org.geotools.util.*;
import org.geotools.geojson.*;
import org.geotools.geojson.feature.*;
import com.vividsolutions.jts.geom.*;
import org.opengis.referencing.operation.*;
import org.zoo_project.*;
import org.geotools.factory.*;
import org.geotools.validation.xml.*;
import org.geotools.gml.*;
import org.xml.sax.helpers.*;
import org.xml.sax.*;
import org.geotools.geojson.geom.*;
import org.geotools.feature.*;
import org.json.*;
import java.lang.reflect.Method;
import com.vividsolutions.jts.simplify.*;

interface OneArgInterface {
    public Geometry operation(Geometry g);
}
/**
 * Prompts the user for a shapefile and displays the contents on the screen in a map frame.
 * <p>
 * This is the GeoTools Quickstart application used in documentationa and tutorials. *
 */
public class GeotoolsBasicServices {

    public static FeatureIterator parseInputs(HashMap input){
	byte[] value=(byte[])input.get("value");
	String mimeType=input.get("mimeType").toString();
	if(mimeType.indexOf("text/xml")>=0) {
	    try{
		GML gml = new GML(GML.Version.GML2);
		InputStream reader = new ByteArrayInputStream( value );
		SimpleFeatureCollection featureCollection = gml.decodeFeatureCollection(reader);
		return featureCollection.features();
	    }catch(Exception e){
		System.err.println("\n*** ERROR 0 ====\n "+e+"\n==== ERROR 0 ***");
		e.printStackTrace();
	    }
	    return null;
	}else{
	    try{
		if(mimeType.indexOf("application/json")>=0) {
		    FeatureJSON fjson = new FeatureJSON();
		    InputStream reader = new ByteArrayInputStream( value );
		    FeatureIterator featureCollection = fjson.streamFeatureCollection(GeoJSONUtil.toReader(reader));
		    reader.close();
		    return featureCollection;
		}else
		    return null;
	    }catch(Exception e){
		System.err.println("\n*** ERROR 1 ====\n "+e+"\n==== ERROR 1 ***");
		e.printStackTrace();
	    }
	}
	return null;	
    }


     public static int HelloJTS(HashMap conf,HashMap inputs, HashMap outputs) {
	HashMap hm1 = (HashMap)(outputs.get("Result"));
	hm1.put("value",ZOO.translate("Hello from JTS "+com.vividsolutions.jts.JTSVersion.CURRENT_VERSION.toString()+" World !!"));
	return ZOO.SERVICE_SUCCEEDED;
    }

    public static void generateOutput(HashMap output,SimpleFeatureCollection collection,SimpleFeatureType featureType) throws Exception {
	String mimeType=output.get("mimeType").toString();
	if(mimeType.indexOf("text/xml")>=0){

	    File locationFile = new File(featureType.getTypeName()+".xsd");
	    locationFile = locationFile.getCanonicalFile();
	    locationFile.createNewFile();
	    
	    FileOutputStream xsd = new FileOutputStream(locationFile);
	    GML encode = new GML(org.geotools.GML.Version.GML2);
	    encode.setBaseURL(locationFile.getParentFile().toURI().toURL());
	    encode.setNamespace("location", locationFile.toURI().toURL().toExternalForm());
	    encode.encode(xsd, featureType);
	    xsd.close();
	    
	    ByteArrayOutputStream xml = new ByteArrayOutputStream();
	    
	    GML encode1 = new GML(org.geotools.GML.Version.GML2);
	    encode1.setLegacy(true);
	    encode1.setBaseURL(locationFile.getParentFile().toURI().toURL());
	    encode1.setNamespace("location", featureType.getTypeName()+".xsd");
	    encode1.encode(xml,  collection);
	    
	    output.put("value",xml.toString());

	    xml.close();
	    
	    return;
	}else{
	    if(mimeType.indexOf("application/json")>=0){
		FeatureJSON fjson = new FeatureJSON();
		StringWriter writer = new StringWriter();
		fjson.writeFeatureCollection(collection, writer);
		output.put("value",writer.toString());
		return;
	    }else{
		return;
	    }
	}
    }

    public static int JsonOp(HashMap conf,HashMap inputs, HashMap outputs,OneArgInterface func,Class<?> binding) throws Exception {
	CoordinateReferenceSystem targetCRS=null;
	try{
	    HashMap input=(HashMap)inputs.get("InputPolygon");
	    FeatureIterator iterator=parseInputs(input);
	    System.err.println("FC ("+iterator+")");
	    SimpleFeatureType TYPE=null;

	    SimpleFeatureTypeBuilder FTYPE = new SimpleFeatureTypeBuilder();
	    List<SimpleFeature> features0 = new ArrayList<SimpleFeature>();
	    List<SimpleFeature> rFeatures = new ArrayList<SimpleFeature>();

	    GeometryFactory geometryFactory = JTSFactoryFinder.getGeometryFactory();

	    SimpleFeatureType featureType = null;
	    SimpleFeatureBuilder featureBuilder = null;
	    int iter=0;

	    /**
	     * Loop over every feature and run the requested computation
	     */
	    while( iterator.hasNext() ){
		SimpleFeature feature = (SimpleFeature)iterator.next();
		features0.add(feature);
		if(iter==0){
		    /**
		     * Loop over every attributes to create a SimpleFeatureType
		     */
		    TYPE=(SimpleFeatureType)feature.getType();
		    FTYPE.setName( "Result" );
		    FTYPE.setNamespaceURI( "http://www.geotools.org/" );
		    //FTYPE.setSRS( "EPSG:4326");		    
		    for(int i=0;i<TYPE.getAttributeCount();i+=1){
			if(TYPE.getType(i).getBinding().toString().indexOf("com.vividsolutions.jts.geom.")>=0){
			    FTYPE.add(TYPE.getDescriptor(i).getLocalName(),binding);
			}
			else
			    FTYPE.add(TYPE.getDescriptor(i).getLocalName(),TYPE.getType(i).getBinding());
		    }
		    featureType = FTYPE.buildFeatureType();
		    featureBuilder = new SimpleFeatureBuilder(featureType);
		}
		Geometry geom = (Geometry) feature.getDefaultGeometry();
		for(int i=0;i<TYPE.getAttributeCount();i+=1){
		    if(TYPE.getType(i).getBinding().toString().indexOf("com.vividsolutions.jts.geom.")>=0){
			featureBuilder.add(func.operation(geom));
		    }
		    else
			featureBuilder.add(feature.getAttribute(i));
		}
		SimpleFeature feature0 = featureBuilder.buildFeature(null);
		rFeatures.add(feature0);
		iter+=1;
	    }
	    iterator.close();

	    /**
	     * Set the final output["Result"]["value"]
	     */
	    HashMap hm1 = (HashMap)(outputs.get("Result"));
	    SimpleFeatureCollection collection = new ListFeatureCollection(featureType, rFeatures);
	    generateOutput(hm1,collection,featureType);
	    return ZOO.SERVICE_SUCCEEDED;
	}catch(Exception e){
	    ((HashMap)conf.get("main")).put("message",e.toString());
	    System.err.println("\n*** ERROR Final ====\n "+e+"\n==== ERROR Final ***");
	    e.printStackTrace();
	}
	return ZOO.SERVICE_FAILED;
    }

    public static int Centroid(HashMap conf,HashMap inputs, HashMap outputs) throws Exception {
	return JsonOp(conf,inputs,outputs,(Geometry g) -> ((MultiPolygon)g).getCentroid(),Point.class);
    }
    public static int Convexhull(HashMap conf,HashMap inputs, HashMap outputs) throws Exception {
	return JsonOp(conf,inputs,outputs,(Geometry g) -> g.convexHull(),MultiPolygon.class);
    }
    public static int Boundary(HashMap conf,HashMap inputs, HashMap outputs) throws Exception {
	return JsonOp(conf,inputs,outputs,(Geometry g) -> ((MultiPolygon)g).getBoundary(),MultiPolygon.class);
    }
    public static int Buffer(HashMap conf,HashMap inputs, HashMap outputs) throws Exception {
	HashMap size=(HashMap)inputs.get("BufferDistance");
	return JsonOp(conf,inputs,outputs,(Geometry g) -> ((MultiPolygon)g).buffer(Double.parseDouble(size.get("value").toString())),MultiPolygon.class);
    }
    public static int Simplify(HashMap conf,HashMap inputs, HashMap outputs) throws Exception {
	HashMap size=(HashMap)inputs.get("DistanceTolerance");
	return JsonOp(conf,inputs,outputs,(Geometry g) -> (DouglasPeuckerSimplifier.simplify(g,Double.parseDouble(size.get("value").toString()))),MultiPolygon.class);
    }
    public static int SimplifyTopo(HashMap conf,HashMap inputs, HashMap outputs) throws Exception {
	HashMap size=(HashMap)inputs.get("DistanceTolerance");
	return JsonOp(conf,inputs,outputs,(Geometry g) -> (TopologyPreservingSimplifier.simplify(g,Double.parseDouble(size.get("value").toString()))),MultiPolygon.class);
    }


}


