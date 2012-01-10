package checkers.flow.constantpropagation;

import java.util.List;

import checkers.flow.analysis.ConditionalTransferResult;
import checkers.flow.analysis.TransferFunction;
import checkers.flow.analysis.TransferResult;
import checkers.flow.cfg.node.AssignmentNode;
import checkers.flow.cfg.node.EqualToNode;
import checkers.flow.cfg.node.IntegerLiteralNode;
import checkers.flow.cfg.node.LocalVariableNode;
import checkers.flow.cfg.node.Node;
import checkers.flow.cfg.node.SinkNodeVisitor;
import checkers.flow.constantpropagation.Constant.Type;

import com.sun.source.tree.MethodTree;

public class ConstantPropagationTransfer
		extends
		SinkNodeVisitor<TransferResult<ConstantPropagationStore>, ConstantPropagationStore>
		implements TransferFunction<ConstantPropagationStore> {

	@Override
	public ConstantPropagationStore initialStore(MethodTree tree,
			List<LocalVariableNode> parameters) {
		ConstantPropagationStore store = new ConstantPropagationStore();

		// we have no information about parameters
		for (LocalVariableNode p : parameters) {
			store.addInformation(p, new Constant(Type.TOP));
		}

		return store;
	}

	@Override
	public TransferResult<ConstantPropagationStore> visitNode(Node n,
			ConstantPropagationStore p) {
		return new TransferResult<>(p);
	}

	@Override
	public TransferResult<ConstantPropagationStore> visitAssignment(
			AssignmentNode n, ConstantPropagationStore p) {
		Node target = n.getTarget();
		if (target instanceof LocalVariableNode) {
			LocalVariableNode t = (LocalVariableNode) target;
			p.setInformation(t, p.getInformation(n.getExpression()));
		}
		return new TransferResult<>(p);
	}

	@Override
	public TransferResult<ConstantPropagationStore> visitIntegerLiteral(
			IntegerLiteralNode n, ConstantPropagationStore p) {
		p.setInformation(n, new Constant(n.getValue()));
		return new TransferResult<>(p);
	}
	
	@Override
	public TransferResult<ConstantPropagationStore> visitEqualTo(EqualToNode n,
			ConstantPropagationStore p) {
		ConstantPropagationStore old = p.copy();
		Node left = n.getLeftOperand();
		Node right = n.getRightOperand();
		process(p, left, right);
		process(p, right, left);
		return new ConditionalTransferResult<ConstantPropagationStore>(p, old);
	}

	protected void process(ConstantPropagationStore p, Node a, Node b) {
		Constant val = p.getInformation(a);
		if (b instanceof LocalVariableNode && val.isConstant()) {
			p.setInformation(b, val);
		}
	}

}