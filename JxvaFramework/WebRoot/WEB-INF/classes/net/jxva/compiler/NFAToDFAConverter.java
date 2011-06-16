package net.jxva.compiler;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Vector;

/**
 * 从NFA到DFA的转换(子集法)

kong(S0)是DState的仅有的状态,且是没有标记的.

while(DState里面存在没有标记的状态T)

loop 标记T

   for 每一个输入字符a

   loop U=kong(smove(T,a))

        if(U不在DState里面)

        then U作为未标记的状态加入DStat

        end if

    end loop

end loop

具体的java代码如下:
 * @author Administrator
 *
 */
public class NFAToDFAConverter {
	// 2007.11.13 标记原来一系列的NFA状态到新的DFA状态转化过程要用的数据结构
	private class NFA_DFA {
		boolean flag = false; // 标记是不是被标记过.
		NFAState newNFAState;
		LinkedHashSet originalNFAStates;

		public NFA_DFA() {
			newNFAState = new NFAState();
			originalNFAStates = new LinkedHashSet();
		}
	}

	// 2007.12.13
	private Vector<NFA_DFA> nfaDFA; // 定义中间的临时存储状态
	private int position = 0; // 当前已经标示的状态的位置
	private int extendposition = 0; // 当前正在展开的位置

	public NFAToDFAConverter() {
		nfaDFA = new Vector();
	}

	// 输入nfa，输出转换后的DFA
	public NFA convert(NFA nfa, Vector<Long> vecsymbol) {
		// 最后的返回结果
		NFA dfa = new NFA();

		TransitionTable transitionTable = new TransitionTable();
		Vector<NFAState> vecStates = new Vector();

		// 获得新的起始状态
		{
			NFA_DFA nfadfa = new NFA_DFA();
			LinkedHashSet nfaStates = new LinkedHashSet();

			position = 0;
			extendposition = 0;
			nfaDFA.clear();
			// 插入初始化状态
			nfaStates.add(nfa.getStartState());
			// 初始化第一个空状态
			nfadfa.originalNFAStates = moveSet0(nfa, nfaStates); // 这里0就是为空的情况下的闭包
			nfadfa.flag = true;
			nfadfa.newNFAState.nfa = dfa;

			if (isTerminal(nfadfa.originalNFAStates))
				nfadfa.newNFAState.setAcceptState(true);
			else
				nfadfa.newNFAState.setAcceptState(false);
			// 定义DFA的初始状态
			dfa.setstartState(nfadfa.newNFAState);
			vecStates.add(nfadfa.newNFAState);
			nfaDFA.add(nfadfa);
		}

		// 子集法求DFA
		while (extendposition <= position) {
			// 遍历每一个输入字符

			Iterator<Long> vecsymbol_it = vecsymbol.iterator();

			while (vecsymbol_it.hasNext()) {

				LinkedHashSet tempnfaStates = new LinkedHashSet();
				HashMap<Long, Vector<TransitionTableEntry>> tempsubtoken = new HashMap();

				long keyvalue = vecsymbol_it.next();
				tempnfaStates.addAll(moveSet0(nfa, moveSet(nfa, nfaDFA
						.get(extendposition).originalNFAStates, keyvalue)));
				if (tempnfaStates.size() == 0)
					continue;
				// 判断该集合是不是已经被标记
				int index = isDefined(tempnfaStates);
				if (index != -1) {
					// 已经定义了
					TransitionTableEntry temptransitionTableEntry = new TransitionTableEntry();
					Vector<TransitionTableEntry> tempvectransitionTableEntry = new Vector();

					temptransitionTableEntry.nextState = nfaDFA.get(index).newNFAState;
					tempvectransitionTableEntry.add(temptransitionTableEntry);
					if (transitionTable.tokenMap.containsKey(nfaDFA
							.get(extendposition).newNFAState)) {
						HashMap<Long, Vector<TransitionTableEntry>> tempast = transitionTable.tokenMap
								.get(nfaDFA.get(extendposition).newNFAState);
						tempast.put(keyvalue, tempvectransitionTableEntry);
					} else {
						tempsubtoken.put(keyvalue, tempvectransitionTableEntry);
						transitionTable.tokenMap.put(
								nfaDFA.get(extendposition).newNFAState,
								tempsubtoken);
					}
				} else {
					NFA_DFA tempnfadfa = new NFA_DFA();

					// 还没有定义的情况 ,插入新的纪录
					TransitionTableEntry temptransitionTableEntry = new TransitionTableEntry();
					Vector<TransitionTableEntry> tempvectransitionTableEntry = new Vector();

					position++;
					tempnfadfa.originalNFAStates = tempnfaStates; // 这里100就是为空的情况下的闭包
					tempnfadfa.flag = true;
					tempnfadfa.newNFAState.nfa = dfa;

					if (isTerminal(tempnfadfa.originalNFAStates))
						tempnfadfa.newNFAState.setAcceptState(true);
					else
						tempnfadfa.newNFAState.setAcceptState(false);

					nfaDFA.add(tempnfadfa);
					vecStates.add(tempnfadfa.newNFAState);

					temptransitionTableEntry.nextState = tempnfadfa.newNFAState;
					tempvectransitionTableEntry.add(temptransitionTableEntry);
					if (transitionTable.tokenMap.containsKey(nfaDFA
							.get(extendposition).newNFAState)) {
						HashMap<Long, Vector<TransitionTableEntry>> tempast = transitionTable.tokenMap
								.get(nfaDFA.get(extendposition).newNFAState);
						tempast.put(keyvalue, tempvectransitionTableEntry);
					} else {
						tempsubtoken.put(keyvalue, tempvectransitionTableEntry);
						transitionTable.tokenMap.put(
								nfaDFA.get(extendposition).newNFAState,
								tempsubtoken);
					}
				}
			}
			extendposition++;
		}

		dfa.setfinalStateSet(vecStates);
		dfa.settransitionTable(transitionTable);

		return dfa;
	}

	// 2007.11.13 集合stateset经过value所到达的新的集合。
	private LinkedHashSet moveSet(NFA nfa, LinkedHashSet stateset, long value) {
		// 返回的集合
		LinkedHashSet resultset = new LinkedHashSet();

		Iterator<NFAState> stateset_it = stateset.iterator();
		while (stateset_it.hasNext()) {
			NFAState tempstate = stateset_it.next();
			HashMap<Long, Vector<TransitionTableEntry>> temphash = nfa.transitionTable.tokenMap
					.get(tempstate);
			if (temphash == null)
				continue;
			Vector<TransitionTableEntry> vec_tableentry = temphash.get(value);
			// 把由tempstate状态经过value所到达的状态全部插入到结果集里面.
			if (vec_tableentry == null)
				continue;
			Iterator<TransitionTableEntry> vec_tableentry_it = vec_tableentry
					.iterator();
			while (vec_tableentry_it.hasNext()) {
				resultset.add(vec_tableentry_it.next().nextState);
			}
		}
		return resultset;
	}

	// 2007.11.13 集合stateset经过0所到达的新的集合。注意经过和经过其他的区别是很大的。
	// 要是0的话，那样原来的就的加上，还有传递 的性质。
	private LinkedHashSet moveSet0(NFA nfa, LinkedHashSet<NFAState> stateset) {
		// 返回的集合
		LinkedHashSet resultset = new LinkedHashSet();
		LinkedHashSet tempresultset = new LinkedHashSet();

		resultset.addAll(stateset);
		tempresultset.addAll(moveSet(nfa, resultset, 0l));
		while (!resultset.containsAll(tempresultset)) {
			resultset.addAll(tempresultset);
			tempresultset.addAll(moveSet(nfa, resultset, 0l));
		}
		return resultset;
	}

	// 2007.11.13求出DFA里面的状态是不是终结符.
	private boolean isTerminal(LinkedHashSet stateset) {
		boolean flag = false;

		// 遍历集合看看是不是存在一个NFA的终态.
		Iterator<NFAState> stateset_it = stateset.iterator();
		while (stateset_it.hasNext()) {
			if (stateset_it.next().isAcceptState()) {
				flag = true;
				break;
			}
		}
		return flag;
	}

	// 2007.12.13 是否已经定义过了,要是没有定义就返回-1否则就返回对应的下标.
	private int isDefined(LinkedHashSet stateset) {
		int flag = -1;
		int length = nfaDFA.size();
		// 判断该集合是不是已经被标记
		for (int i = 0; i < length; i++) {
			if (stateset.equals(nfaDFA.get(i).originalNFAStates)) {
				flag = i;
				break;
			}
		}
		return flag;
	}

}