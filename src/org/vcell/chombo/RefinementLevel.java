package org.vcell.chombo;

import java.io.Serializable;

import org.vcell.util.CommentStringTokenizer;
import org.vcell.util.Compare;
import org.vcell.util.DataAccessException;
import org.vcell.util.Matchable;

import cbit.vcell.math.VCML;
import cbit.vcell.parser.Expression;
import cbit.vcell.parser.ExpressionException;
import cbit.vcell.parser.SimpleSymbolTable;

public class RefinementLevel implements Serializable, Matchable {
	private int refineRatio = 2;
	private Expression roiExpression = null;
	
	public RefinementLevel() {
	}
	
	public RefinementLevel(int ratio, String roi) throws ExpressionException {
		refineRatio  = ratio;
		setRoiExpression(roi);
	}
	
	public RefinementLevel(CommentStringTokenizer tokens) throws DataAccessException {
		readVCML(tokens);
	}

	public RefinementLevel(RefinementLevel rl) {
		refineRatio = rl.refineRatio;
		if (rl.roiExpression != null)
		{
			roiExpression = new Expression(rl.roiExpression);
		}
	}

	public int getRefineRatio() {
		return refineRatio;
	}
	
	public void setRefineRatio(int refineRatio) {
		this.refineRatio = refineRatio;
	}
	
	public String getVCML() {
	//      RefinementLevel {
		//      RefineRatio 2
		//      ChomboBox 1 2 3 4
		//      ChomboBox 1 2 3 4	
	//      }
		StringBuilder buffer = new StringBuilder();

		buffer.append(VCML.RefinementLevel + " " + VCML.BeginBlock + "\n");
		buffer.append(VCML.RefineRatio + " " + refineRatio + "\n");
		if (roiExpression != null)
		{
			buffer.append("\t" + VCML.ROIExpression + " " + roiExpression.infix() + ";\n");
		}

		buffer.append(VCML.EndBlock+"\n");
			
		return buffer.toString();
	}
	
	public void readVCML(CommentStringTokenizer tokens) throws DataAccessException {
		//      RefinementLevel {
		//      RefineRatio 2
		//      NumOfBoxes 2
		//      ChomboBox 1 2 3 4
		//      ChomboBox 1 2 3 4	
		//      }
		try {
			String token = tokens.nextToken();
			if (token.equalsIgnoreCase(VCML.RefinementLevel)) {
				token = tokens.nextToken();
				if (!token.equalsIgnoreCase(VCML.BeginBlock)) {
					throw new DataAccessException(
						"unexpected token " + token + " expecting " + VCML.BeginBlock); 
				}
			}
			
			while (tokens.hasMoreTokens()) {
				token = tokens.nextToken();
				if (token.equalsIgnoreCase(VCML.EndBlock)) {
					break;
				}
				else if (token.equalsIgnoreCase(VCML.RefineRatio)) {
					token = tokens.nextToken();
					refineRatio = Integer.parseInt(token);
				}
				else if (token.equalsIgnoreCase(VCML.ROIExpression))
				{
					token = tokens.readToSemicolon();
					try {
						setRoiExpression(token);
					} catch (ExpressionException e) {
						System.err.println("Invalid " + VCML.ROIExpression + ": " + token);
					}
				}
				else
				{
					throw new DataAccessException("unexpected identifier " + token);
				}
			}
		} catch (Throwable e) {
			e.printStackTrace(System.out);
			throw new DataAccessException("line #" + (tokens.lineIndex()+1) + " Exception: " + e.getMessage()); 
		}
	}

	public boolean compareEqual(Matchable object) {
		if (this == object) {
			return (true);
		}
		if (!(object instanceof RefinementLevel)) {
			return false;
		}
		RefinementLevel rl = (RefinementLevel) object;
		if (rl.refineRatio != refineRatio) {
			return false;
		}
		if (!Compare.isEqualOrNull(roiExpression, rl.roiExpression))
		{
			return false;
		}
		return true;
	}
	public Expression getRoiExpression() {
		return roiExpression;
	}

	public void setRoiExpression(String roiExp) throws ExpressionException {
		Expression exp = null;
		if (roiExp != null)
		{
			roiExp = roiExp.trim();
			if (roiExp.length() > 0)
			{
				exp = new Expression(roiExp);
				exp.bindExpression(new SimpleSymbolTable(new String[] { "x", "y", "z" } ));
			}
		}
		this.roiExpression = exp;
	}
}
