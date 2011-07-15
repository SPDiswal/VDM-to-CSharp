package org.overture.ast.patterns.assistants;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import org.overture.ast.patterns.ASetBind;
import org.overture.ast.patterns.ASetMultipleBind;
import org.overture.ast.patterns.PMultipleBind;
import org.overture.ast.patterns.PPattern;


public class ASetBindAssistant {

	public static List<PMultipleBind> getMultipleBindList(ASetBind bind) {
		
		List<PPattern> plist = new ArrayList<PPattern>();
		plist.add(bind.getPattern());
		List<PMultipleBind> mblist = new Vector<PMultipleBind>();
		mblist.add(new ASetMultipleBind(bind.getLocation(), plist, bind.getSet()));
		return mblist;
	}

}
