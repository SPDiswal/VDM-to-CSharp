package org.overture.ast.patterns.assistants;

import java.util.List;
import java.util.Vector;

import org.overture.ast.analysis.QuestionAnswerAdaptor;
import org.overture.ast.definitions.PDefinition;
import org.overture.ast.expressions.ASeqEnumSeqExp;
import org.overture.ast.expressions.PExp;
import org.overture.ast.patterns.ASeqPattern;
import org.overture.ast.patterns.PPattern;
import org.overture.ast.types.ASeqSeqType;
import org.overture.ast.types.AUnknownType;
import org.overture.ast.types.PType;
import org.overture.ast.types.assistants.PTypeAssistant;
import org.overture.typecheck.TypeCheckException;
import org.overture.typecheck.TypeCheckInfo;
import org.overture.typecheck.TypeCheckerErrors;
import org.overturetool.vdmj.typechecker.NameScope;


public class ASeqPatternAssistantTC extends ASeqPatternAssistant {

	public static void typeResolve(ASeqPattern pattern,
			QuestionAnswerAdaptor<TypeCheckInfo, PType> rootVisitor,
			TypeCheckInfo question) {
		
		if (pattern.getResolved()) return; else { pattern.setResolved(true); }

		try
		{
			PPatternListAssistantTC.typeResolve(pattern.getPlist(), rootVisitor, question);
		}
		catch (TypeCheckException e)
		{
			unResolve(pattern);
			throw e;
		}
		
	}

	public static void unResolve(ASeqPattern pattern) {
		PPatternListAssistantTC.unResolve(pattern.getPlist());
		pattern.setResolved(false);
		
	}

//	public static LexNameList getVariableNames(ASeqPattern pattern) {
//		LexNameList list = new LexNameList();
//
//		for (PPattern p: pattern.getPlist())
//		{
//			list.addAll(PPatternTCAssistant.getVariableNames(p));
//		}
//
//		return list;
//	}

	public static List<PDefinition> getDefinitions(ASeqPattern rp, PType type,
			NameScope scope) {
		
		List<PDefinition> defs = new Vector<PDefinition>();

		if (!PTypeAssistant.isSeq(type))
		{
			TypeCheckerErrors.report(3203, "Sequence pattern is matched against " + type,rp.getLocation(),rp);
		}
		else
		{
			PType elem = PTypeAssistant.getSeq(type).getSeqof();

    		for (PPattern p: rp.getPlist())
    		{
    			defs.addAll(PPatternAssistantTC.getDefinitions(p, elem, scope));
    		}
		}

		return defs;
	}

	public static PType getPossibleTypes(ASeqPattern pattern) {
		ASeqSeqType t = new ASeqSeqType(pattern.getLocation(), false, false);
		t.setSeqof( new AUnknownType(pattern.getLocation(), false));
		return t;
	}

	public static PExp getMatchingExpression(ASeqPattern seqp) {
		return new ASeqEnumSeqExp(seqp.getLocation(), PPatternListAssistantTC.getMatchingExpressionList(seqp.getPlist()));
	}

}
