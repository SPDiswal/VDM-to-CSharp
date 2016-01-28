/**
 * 
 */
package org.overture.codegen.trans.conc;

import java.util.LinkedList;
import java.util.List;

import org.overture.codegen.cgast.analysis.AnalysisException;
import org.overture.codegen.cgast.analysis.DepthFirstAnalysisAdaptor;
import org.overture.codegen.cgast.declarations.ADefaultClassDeclCG;
import org.overture.codegen.cgast.declarations.AFieldDeclCG;
import org.overture.codegen.cgast.declarations.AFormalParamLocalParamCG;
import org.overture.codegen.cgast.declarations.AInterfaceDeclCG;
import org.overture.codegen.cgast.declarations.AMethodDeclCG;
import org.overture.codegen.cgast.declarations.APersyncDeclCG;
import org.overture.codegen.cgast.declarations.SClassDeclCG;
import org.overture.codegen.cgast.expressions.ABoolLiteralExpCG;
import org.overture.codegen.cgast.expressions.ACastUnaryExpCG;
import org.overture.codegen.cgast.expressions.AEqualsBinaryExpCG;
import org.overture.codegen.cgast.expressions.AFieldExpCG;
import org.overture.codegen.cgast.expressions.AIdentifierVarExpCG;
import org.overture.codegen.cgast.expressions.AIntLiteralExpCG;
import org.overture.codegen.cgast.expressions.ANewExpCG;
import org.overture.codegen.cgast.expressions.ASelfExpCG;
import org.overture.codegen.cgast.name.ATokenNameCG;
import org.overture.codegen.cgast.name.ATypeNameCG;
import org.overture.codegen.cgast.patterns.AIdentifierPatternCG;
import org.overture.codegen.cgast.statements.AAssignToExpStmCG;
import org.overture.codegen.cgast.statements.ABlockStmCG;
import org.overture.codegen.cgast.statements.AElseIfStmCG;
import org.overture.codegen.cgast.statements.AIfStmCG;
import org.overture.codegen.cgast.statements.APlainCallStmCG;
import org.overture.codegen.cgast.statements.AReturnStmCG;
import org.overture.codegen.cgast.statements.ATryStmCG;
import org.overture.codegen.cgast.types.ABoolBasicTypeCG;
import org.overture.codegen.cgast.types.AClassTypeCG;
import org.overture.codegen.cgast.types.AExternalTypeCG;
import org.overture.codegen.cgast.types.AIntNumericBasicTypeCG;
import org.overture.codegen.cgast.types.AMethodTypeCG;
import org.overture.codegen.cgast.types.AVoidTypeCG;
import org.overture.codegen.ir.IRConstants;
import org.overture.codegen.ir.IRGeneratedTag;
import org.overture.codegen.ir.IRInfo;

/**
 * @author gkanos
 */
public class MainClassConcTrans extends DepthFirstAnalysisAdaptor
{
	public static final String MULTIPLE_INHERITANCE_WARNING = "Generation of concurrency "
			+ "constructs does not support multiple inheritance";

	private IRInfo info;
	private ConcPrefixes concPrefixes;

	public MainClassConcTrans(IRInfo info, ConcPrefixes concPrefixes)
	{
		this.info = info;
		this.concPrefixes = concPrefixes;
	}

	@Override
	public void caseADefaultClassDeclCG(ADefaultClassDeclCG node) throws AnalysisException
	{
		if (!info.getSettings().generateConc())
		{
			return;
		}

		if (node.getSuperNames().size() > 1)
		{
			info.addTransformationWarning(node, MULTIPLE_INHERITANCE_WARNING);
			return;
		}

		for (AFieldDeclCG fieldCG : node.getFields())
		{
			if (!fieldCG.getFinal())
			{
				fieldCG.setVolatile(true);
			}
		}

		AInterfaceDeclCG interf = new AInterfaceDeclCG();
		interf.setName(concPrefixes.evalPpTypeName());

		node.getInterfaces().add(interf);

		AExternalTypeCG sentType = new AExternalTypeCG();
		sentType.setName(concPrefixes.sentinelClassName());
		AFieldDeclCG sentinelfld = new AFieldDeclCG();
		sentinelfld.setName(concPrefixes.sentinelInstanceName());
		sentinelfld.setType(sentType);
		sentinelfld.setAccess(IRConstants.PUBLIC);
		sentinelfld.setVolatile(true);
		sentinelfld.setStatic(false);

		node.getFields().add(sentinelfld);

		for (AMethodDeclCG methodCG : node.getMethods())
		{
			if (methodCG.getStatic() != null && !methodCG.getStatic() && !isIRGenerated(methodCG))
			{
				if (!methodCG.getIsConstructor())
				{
					ABlockStmCG bodyStm = new ABlockStmCG();

					APlainCallStmCG entering = new APlainCallStmCG();
					APlainCallStmCG leaving = new APlainCallStmCG();

					entering.setName(concPrefixes.enteringMethodName());
					AClassTypeCG sentinel = new AClassTypeCG();
					sentinel.setName(concPrefixes.sentinelInstanceName());

					entering.setClassType(sentinel);
					entering.setType(new AVoidTypeCG());

					AFieldExpCG field = new AFieldExpCG();
					field.setMemberName(methodCG.getName());

					ACastUnaryExpCG cast = new ACastUnaryExpCG();
					AIdentifierVarExpCG varSentinel = new AIdentifierVarExpCG();
					varSentinel.setIsLocal(true);
					varSentinel.setIsLambda(false);
					varSentinel.setName(concPrefixes.sentinelInstanceName());

					AExternalTypeCG etype = new AExternalTypeCG();
					etype.setName(node.getName() + concPrefixes.sentinelClassPostFix());

					cast.setExp(varSentinel);
					cast.setType(etype);
					field.setObject(cast);

					entering.getArgs().add(field);

					leaving.setName(concPrefixes.leavingMethodName());
					leaving.setClassType(sentinel.clone());
					leaving.setType(new AVoidTypeCG());
					leaving.getArgs().add(field.clone());

					bodyStm.getStatements().add(entering);
					ATryStmCG trystm = new ATryStmCG();
					trystm.setStm(methodCG.getBody());
					trystm.setFinally(leaving);
					bodyStm.getStatements().add(trystm);

					methodCG.setBody(bodyStm);
				}
			}

			if (methodCG.getIsConstructor())
			{
				ABlockStmCG bodyConst = new ABlockStmCG();

				AAssignToExpStmCG stm = new AAssignToExpStmCG();

				AIdentifierVarExpCG field = new AIdentifierVarExpCG();

				field.setName(concPrefixes.sentinelInstanceName());
				field.setIsLocal(false);

				ANewExpCG newexp = new ANewExpCG();

				ATypeNameCG classtype = new ATypeNameCG();
				classtype.setName(node.getName() + concPrefixes.sentinelClassPostFix());

				newexp.setName(classtype);
				newexp.getArgs().add(new ASelfExpCG());

				stm.setExp(newexp);
				stm.setTarget(field);

				bodyConst.getStatements().add(stm);
				bodyConst.getStatements().add(methodCG.getBody());

				methodCG.setBody(bodyConst);
			}
		}
		// declaration of the method.

		AIntNumericBasicTypeCG fnr = new AIntNumericBasicTypeCG();
		AIdentifierPatternCG identifier = new AIdentifierPatternCG();
		identifier.setName(concPrefixes.funcNumberParamName());
		AFormalParamLocalParamCG fnrloc = new AFormalParamLocalParamCG();
		fnrloc.setType(fnr);
		fnrloc.setPattern(identifier);
		AMethodTypeCG methType = new AMethodTypeCG();
		methType.setResult(new ABoolBasicTypeCG());

		AMethodDeclCG evaluatePPmethod = new AMethodDeclCG();
		evaluatePPmethod.setAccess(IRConstants.PUBLIC);
		evaluatePPmethod.setName(concPrefixes.evalPpMethodName());
		evaluatePPmethod.setImplicit(false);
		evaluatePPmethod.setMethodType(methType);
		evaluatePPmethod.setIsConstructor(false);

		evaluatePPmethod.getFormalParams().add(fnrloc);

		// Body of the method.
		if (node.getMethods().size() != 0)
		{

			// fixing the overloaded operation problem
			List<AMethodDeclCG> classuniqueMethods = new LinkedList<>();

			for (AMethodDeclCG m : node.getMethods())
			{
				classuniqueMethods.add(m.clone());
			}

			classuniqueMethods.clear();

			List<AMethodDeclCG> allMethods;

			if (!node.getSuperNames().isEmpty())
			{
				allMethods = info.getDeclAssistant().getAllMethods(node, info.getClasses());
			} else
			{
				allMethods = node.getMethods();
			}

			for (AMethodDeclCG method : allMethods)
			{
				if (!classuniqueMethods.contains(method))
				{
					classuniqueMethods.add(method.clone());
				}
			}

			AIfStmCG bodyif = new AIfStmCG();
			for (int i = 0; i < classuniqueMethods.size(); i++)
			{

				AIdentifierVarExpCG testVar = new AIdentifierVarExpCG();
				testVar.setType(new AIntNumericBasicTypeCG());
				testVar.setName(concPrefixes.funcNumberParamName());
				testVar.setIsLocal(true);

				if (i == 0)
				{
					AEqualsBinaryExpCG firstBranch = new AEqualsBinaryExpCG();

					AIntLiteralExpCG methNum = new AIntLiteralExpCG();
					methNum.setValue((long) i);

					firstBranch.setLeft(testVar);
					firstBranch.setRight(methNum);

					AReturnStmCG ret = new AReturnStmCG();
					ABoolLiteralExpCG boolret = new ABoolLiteralExpCG();
					boolret.setValue(true);
					ret.setExp(boolret);

					for (APersyncDeclCG per : node.getPerSyncs())
					{
						if (per.getOpname().equals(classuniqueMethods.get(i).getName()))
						{
							ret.setExp(per.getPred());
						}

					}

					bodyif.setIfExp(firstBranch);
					bodyif.setThenStm(ret);
				}

				else
				{
					AReturnStmCG ret = new AReturnStmCG();
					ABoolLiteralExpCG boolret = new ABoolLiteralExpCG();
					boolret.setValue(true);
					ret.setExp(boolret);

					for (APersyncDeclCG per : node.getPerSyncs())
					{
						if (per.getOpname().equals(classuniqueMethods.get(i).getName()))
						{
							ret.setExp(per.getPred());
						}
					}

					AElseIfStmCG newBranch = new AElseIfStmCG();

					AEqualsBinaryExpCG Branches = new AEqualsBinaryExpCG();

					AIntLiteralExpCG methNum = new AIntLiteralExpCG();
					methNum.setValue((long) i);

					Branches.setLeft(testVar);
					Branches.setRight(methNum);

					newBranch.setElseIf(Branches);
					newBranch.setThenStm(ret.clone());

					bodyif.getElseIf().add(newBranch);
				}
			}
			AReturnStmCG ret = new AReturnStmCG();

			ABoolLiteralExpCG defaultPer = new ABoolLiteralExpCG();
			defaultPer.setValue(true);

			ret.setExp(defaultPer);
			bodyif.setElseStm(ret.clone());

			evaluatePPmethod.setBody(bodyif);
		}

		node.getMethods().add(evaluatePPmethod);

		if (node.getThread() != null)
		{
			makeThread(node);
		}
	}

	private boolean isIRGenerated(AMethodDeclCG method)
	{
		return method.getTag() instanceof IRGeneratedTag;
	}

	private void makeThread(ADefaultClassDeclCG node)
	{
		SClassDeclCG threadClass = getThreadClass(node.getSuperNames(), node);

		threadClass.getSuperNames().clear();

		ATokenNameCG superName = new ATokenNameCG();
		superName.setName(concPrefixes.vdmThreadClassName());
		threadClass.getSuperNames().add(superName);
	}

	private SClassDeclCG getThreadClass(List<ATokenNameCG> superNames, SClassDeclCG classCg)
	{
		if (superNames.isEmpty() || superNames.get(0).getName().equals(concPrefixes.vdmThreadClassName()))
		{
			return classCg;
		} else
		{
			SClassDeclCG superClass = null;

			for (SClassDeclCG c : info.getClasses())
			{
				if (c.getName().equals(superNames.get(0).getName()))
				{
					superClass = c;
					break;
				}
			}

			return getThreadClass(superClass.getSuperNames(), superClass);
		}
	}
}
