package org.overture.interpreter.assistant.expression;

import java.util.List;

import org.overture.ast.expressions.ACaseAlternative;
import org.overture.ast.expressions.PExp;
import org.overture.interpreter.assistant.IInterpreterAssistantFactory;
import org.overture.interpreter.runtime.ObjectContext;
import org.overture.interpreter.values.ValueList;
import org.overture.typechecker.assistant.expression.ACaseAlternativeAssistantTC;

public class ACaseAlternativeAssistantInterpreter extends
		ACaseAlternativeAssistantTC
{
	protected static IInterpreterAssistantFactory af;

	@SuppressWarnings("static-access")
	public ACaseAlternativeAssistantInterpreter(IInterpreterAssistantFactory af)
	{
		super(af);
		this.af = af;
	}

//	public static ValueList getValues(ACaseAlternative c, ObjectContext ctxt)
//	{
//		return PExpAssistantInterpreter.getValues(c.getResult(), ctxt);
//	}
//
//	public static List<PExp> getSubExpressions(ACaseAlternative c)
//	{
//		return PExpAssistantInterpreter.getSubExpressions(c.getResult());
//	}

}
