package org.golde.forge.gibs.client;

import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.util.HashMap;

import org.golde.forge.gibs.ModGibs;

public class MCPMap {

	private static final HashMap<String, String> mappings = new HashMap<String, String>();
	
	static {
		mappings.put("mainModel", "field_77045_g");
		mappings.put("textureOffsetX", "field_78803_o");
		mappings.put("textureOffsetY", "field_78813_p");
	}
	
	public static String getMapping(String in) {
		
		if(ModGibs.IS_RUNNING_IN_ECLIPSE) {
			return in;
		}
		
		if(mappings.containsKey(in)) {
			return mappings.get(in);
		}
		else {
			ModGibs.logger.error("Failed to get mapping for '" + in + "'");
			return in;
		}
	}
	
}
