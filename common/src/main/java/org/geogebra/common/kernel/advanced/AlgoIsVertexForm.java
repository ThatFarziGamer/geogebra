package org.geogebra.common.kernel.advanced;

import java.util.Arrays;

import org.geogebra.common.kernel.Construction;
import org.geogebra.common.kernel.algos.AlgoElement;
import org.geogebra.common.kernel.algos.GetCommand;
import org.geogebra.common.kernel.arithmetic.ExpressionNode;
import org.geogebra.common.kernel.arithmetic.FunctionVariable;
import org.geogebra.common.kernel.commands.Commands;
import org.geogebra.common.kernel.geos.GeoBoolean;
import org.geogebra.common.kernel.geos.GeoElement;
import org.geogebra.common.kernel.geos.GeoFunctionable;
import org.geogebra.common.plugin.Operation;
import org.geogebra.common.util.debug.Log;

public class AlgoIsVertexForm extends AlgoElement {
	private final GeoFunctionable function;
	private final GeoBoolean result;

	/**
	 * @param c construction
	 * @param function function or conic
	 */
	public AlgoIsVertexForm(Construction c, GeoFunctionable function) {
		super(c);
		this.function = function;
		this.result = new GeoBoolean(c);
		setInputOutput();
		compute();
	}

	@Override
	protected void setInputOutput() {
		input = new GeoElement[]{function.toGeoElement()};
		setOnlyOutput(result);
		setDependencies();
	}

	@Override
	public void compute() {
		if (!function.isDefined()) {
			result.setValue(false);
			return;
		}
		ExpressionNode fn = function.getFunction().getExpression();

		result.setValue(isVertexForm(fn));
	}

	private boolean isVertexForm(ExpressionNode fn) {
		ExpressionNode term = removeConstant(fn, Operation.PLUS, Operation.MINUS);
		ExpressionNode normalizedTerm = removeConstant(term, Operation.MULTIPLY);
		if (normalizedTerm.getOperation() == Operation.POWER
				&& ExpressionNode.isConstantDouble(normalizedTerm.getRight(), 2)) {
			ExpressionNode simplified = removeConstant(normalizedTerm.getLeftTree(),
					Operation.PLUS, Operation.MINUS);
			Log.debug(simplified);
			return simplified.unwrap() instanceof FunctionVariable;
		}
		return false;
	}

	private ExpressionNode removeConstant(ExpressionNode fn, Operation... ops) {
		if (Arrays.asList(ops).contains(fn.getOperation())) {
			if (fn.getLeft().isConstant()) {
				return removeConstant(fn.getRightTree(), ops);
			}
			if (fn.getRight() != null && fn.getRight().isConstant()) {
				return removeConstant(fn.getLeftTree(), ops);
			}
		}
		return fn;
	}

	@Override
	public GetCommand getClassName() {
		return Commands.IsVertexForm;
	}
}
