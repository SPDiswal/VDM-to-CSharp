package org.overture.interpreter.assistant.pattern;

import org.overture.ast.patterns.ASetPattern;
import org.overture.interpreter.assistant.IInterpreterAssistantFactory;

public class ASetPatternAssistantInterpreter
{
	protected static IInterpreterAssistantFactory af;

	@SuppressWarnings("static-access")
	public ASetPatternAssistantInterpreter(IInterpreterAssistantFactory af)
	{
		//super(af);
		this.af = af;
	}

//	public static List<NameValuePairList> getAllNamedValues(
//			ASetPattern pattern, Value expval, Context ctxt)
//			throws PatternMatchException
//	{
//		ValueSet values = null;
//
//		try
//		{
//			values = expval.setValue(ctxt);
//		} catch (ValueException e)
//		{
//			VdmRuntimeError.patternFail(e, pattern.getLocation());
//		}
//
//		if (values.size() != pattern.getPlist().size())
//		{
//			VdmRuntimeError.patternFail(4119, "Wrong number of elements for set pattern", pattern.getLocation());
//		}
//
//		// Since the member patterns may indicate specific set members, we
//		// have to permute through the various set orderings to see
//		// whether there are any which match both sides. If the members
//		// are not constrained however, the initial ordering will be
//		// fine.
//
//		List<ValueSet> allSets;
//
//		if (isConstrained(pattern))
//		{
//			allSets = values.permutedSets();
//		} else
//		{
//			allSets = new Vector<ValueSet>();
//			allSets.add(values);
//		}
//
//		List<NameValuePairList> finalResults = new Vector<NameValuePairList>();
//		int psize = pattern.getPlist().size();
//
//		if (pattern.getPlist().isEmpty())
//		{
//			finalResults.add(new NameValuePairList());
//			return finalResults;
//		}
//
//		for (ValueSet setPerm : allSets)
//		{
//			Iterator<Value> iter = setPerm.iterator();
//
//			List<List<NameValuePairList>> nvplists = new Vector<List<NameValuePairList>>();
//			int[] counts = new int[psize];
//			int i = 0;
//
//			try
//			{
//				for (PPattern p : pattern.getPlist())
//				{
//					List<NameValuePairList> pnvps = PPatternAssistantInterpreter.getAllNamedValues(p, iter.next(), ctxt);
//					nvplists.add(pnvps);
//					counts[i++] = pnvps.size();
//				}
//			} catch (Exception e)
//			{
//				continue;
//			}
//
//			Permutor permutor = new Permutor(counts);
//
//			while (permutor.hasNext())
//			{
//				try
//				{
//					NameValuePairMap results = new NameValuePairMap();
//					int[] selection = permutor.next();
//
//					for (int p = 0; p < psize; p++)
//					{
//						for (NameValuePair nvp : nvplists.get(p).get(selection[p]))
//						{
//							Value v = results.get(nvp.name);
//
//							if (v == null)
//							{
//								results.put(nvp);
//							} else
//							// Names match, so values must also
//							{
//								if (!v.equals(nvp.value))
//								{
//									VdmRuntimeError.patternFail(4120, "Values do not match set pattern", pattern.getLocation());
//								}
//							}
//						}
//					}
//
//					finalResults.add(results.asList());
//				} catch (PatternMatchException pme)
//				{
//					// Try next perm then...
//				}
//			}
//		}
//
//		if (finalResults.isEmpty())
//		{
//			VdmRuntimeError.patternFail(4121, "Cannot match set pattern", pattern.getLocation());
//		}
//
//		return finalResults;
//	}
//
	public static boolean isConstrained(ASetPattern pattern)
	{

		if (af.createPTypeAssistant().isUnion(af.createPPatternListAssistant().getPossibleType(pattern.getPlist(), pattern.getLocation())))
		{
			return true; // Set types are various, so we must permute
		}

		return af.createPPatternListAssistant().isConstrained(pattern.getPlist());
	}

//	public static int getLength(ASetPattern pattern)
//	{
//		return pattern.getPlist().size();
//	}

//	public static List<AIdentifierPattern> findIdentifiers(ASetPattern pattern)
//	{
//		List<AIdentifierPattern> list = new Vector<AIdentifierPattern>();
//
//		for (PPattern p : pattern.getPlist())
//		{
//			list.addAll(PPatternAssistantInterpreter.findIdentifiers(p));
//		}
//
//		return list;
//	}
}
