/*******************************************************************************
 * Copyright 2012 Charles University in Prague
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package cz.cuni.mff.d3s.deeco.invokable;

import java.lang.reflect.Method;

import cz.cuni.mff.d3s.deeco.exceptions.KMException;
import cz.cuni.mff.d3s.deeco.exceptions.KMNotExistentException;
import cz.cuni.mff.d3s.deeco.knowledge.ConstantKeys;
import cz.cuni.mff.d3s.deeco.knowledge.ISession;
import cz.cuni.mff.d3s.deeco.knowledge.KnowledgeManager;
import cz.cuni.mff.d3s.deeco.logging.Log;
import cz.cuni.mff.d3s.deeco.performance.PeriodicEnsembleInfo;
import cz.cuni.mff.d3s.deeco.performance.PeriodicProcessInfo;
import cz.cuni.mff.d3s.deeco.performance.SchedulableProcessTimeStampsVisitor;
import cz.cuni.mff.d3s.deeco.performance.SchedulableProcessTimeStampsWithActionsVisitor;
import cz.cuni.mff.d3s.deeco.performance.SchedulableProcessVisitor;
import cz.cuni.mff.d3s.deeco.performance.TimeStamp;
import cz.cuni.mff.d3s.deeco.scheduling.ETriggerType;
import cz.cuni.mff.d3s.deeco.scheduling.ProcessPeriodicSchedule;
import cz.cuni.mff.d3s.deeco.scheduling.ProcessSchedule;
import cz.cuni.mff.d3s.deeco.scheduling.ProcessTriggeredSchedule;

/**
 * Class representing schedulable ensemble process, which is used by the system
 * to perform either triggered or periodic ensembling.
 * 
 * @author Michal Kit
 * 
 */
public class SchedulableEnsembleProcess extends SchedulableProcess {

	private static final long serialVersionUID = -726573275082252987L;

	public final ParameterizedMethod knowledgeExchange;
	public final MembershipMethod membership;
	private long release=0;
	private SchedulableProcessTimeStampsWithActionsVisitor visitor=new SchedulableProcessTimeStampsWithActionsVisitor();
	/**
	 * Returns <code>SchedulableEnsembleProcess</code> instance for specified
	 * membership function (in <code>membership</code>), mapping function (in
	 * <code>knowledgeExchange</code>), scheduling type (in <code>scheduling</code>) and
	 * knowledge manager (<code>km</code>).
	 * 
	 * @param scheduling
	 *            describes the type of the schedulability for the ensemble
	 * @param membership
	 *            method used to evaluate the ensemble condition
	 * @param knowledgeExchange
	 *            method used to perform data transfer in case of positive
	 *            membership condition evaluation
	 * @param km
	 *            instance of the knowledge manager that is used for parameter
	 *            retrieval
	 */
	public SchedulableEnsembleProcess(KnowledgeManager km, ProcessSchedule scheduling, MembershipMethod membership,
			ParameterizedMethod knowledgeExchange, ClassLoader contextClassLoader) {
		super(km, scheduling, contextClassLoader);
		
		this.membership = membership;
		this.knowledgeExchange = knowledgeExchange;
		pInfo=new PeriodicEnsembleInfo();
	}

	/*
	 * (non-Javadoc)
	 *  
	 * @see cz.cuni.mff.d3s.deeco.invokable.SchedulableProcess#invoke()
	 */
	@Override
	public void invoke(String triggererId, ETriggerType recipientMode) {
		// LoggerFactory.getLogger().fine("Ensembling starts");
		try {
//			///////////////////////////////////////////////
//			//
//			//////////////////////////////////////////////
//			if(scheduling instanceof ProcessPeriodicSchedule){
//				release=System.nanoTime();
//				((PeriodicEnsembleInfo)pInfo).runningPeriodsCoord.add(time);
//				((PeriodicEnsembleInfo)pInfo).runningPeriodsMem.add(time);
//			}else
//			if(scheduling instanceof ProcessTriggeredSchedule)
//				release=System.nanoTime();
//			System.out.println("ens.... "+release);
//			////////////////////////////////////////////////
//			////////////////////////////////////////////////			
			Object[] ids = (Object[]) km
					.getKnowledge(ConstantKeys.ROOT_KNOWLEDGE_ID);
			if (recipientMode == null)
				periodicInvocation(ids);
			else
				try {

					singleInvocation(triggererId, recipientMode, ids);
				} catch (KMException kme) {
				}
			// LoggerFactory.getLogger().fine("Ensembling ends");
		} catch (KMException kme) {
			return;
		}
	}

	private void periodicInvocation(Object[] rootIds) throws KMException {
		for (Object oid : rootIds) {
			singleInvocation((String) oid, ETriggerType.COORDINATOR, rootIds);
		}
	}

	// to rewrite in order of different ETriggerType
	private void singleInvocation(String outerId, ETriggerType recipientMode,
			Object[] rootIds) throws KMException {
		ISession session = null;
		try {
			String cId = null, mId = null;
			if (recipientMode.equals(ETriggerType.COORDINATOR)) {
				cId = outerId;
			} else {
				mId = outerId;
			}
			mloop: for (Object iid : rootIds) {
				if (recipientMode.equals(ETriggerType.COORDINATOR)) {
					mId = (String) iid;
				} else {
					cId = (String) iid;
				}
				session = km.createSession();
				session.begin();
				while (session.repeat()) {
					try {
						time=new TimeStamp();
						// maybe in another place
						release=System.nanoTime();
						ParametersPair[] parametersMembership = getParameterMethodValues(
								membership.getIn(), membership.getInOut(),
								membership.getOut(), session,
								(String) cId, (String) mId);
						time.start=System.nanoTime();
						if (evaluateMembership(parametersMembership)) {
							ParametersPair[] parametersKnowledgeExchange = getParameterMethodValues(knowledgeExchange.in,
									knowledgeExchange.inOut, knowledgeExchange.out, session,
									(String) cId, (String) mId);
							evaluateKnowledgeExchange(parametersKnowledgeExchange);
							putParameterMethodValues(parametersKnowledgeExchange, knowledgeExchange.inOut,
									knowledgeExchange.out, session, (String) cId,
									(String) mId);
						}
						time.finish=System.nanoTime();
						time.release=release;
						scheduling.acceptEnsemble(visitor, pInfo, time);
						
					} catch (KMNotExistentException kmnee) {
						session.cancel();
						continue mloop;
					}
					session.end();
				}
			}
		} catch (KMException kme) {
			if (session != null)
				session.cancel();
			throw kme;
		}
	}

	private boolean evaluateMembership(ParametersPair[] params) {
		try {
			Object[] parameterValues = ParametersPair.extractValues(params);
			return membership.membership(parameterValues);
		} catch (Exception e) {
			Log.e("Ensemble membership exception",e);
			return false;
		}
	}

	private void evaluateKnowledgeExchange(ParametersPair[] params) {
		try {
			Object[] parameterValues = ParametersPair.extractValues(params);
			knowledgeExchange.invoke(parameterValues);
		} catch (Exception e) {
			Log.e("Ensemble evaluation exception",e);
		}
	}
	
	public Method getKnowledgeExchangeMethod() {
		if (knowledgeExchange == null)
			return null;
		return knowledgeExchange.getMethod();
	}
	
	public Method getMembershipMethod() {
		if (membership == null || membership.method == null)
			return null;
		return membership.method.getMethod();
	}
	
	@Override
	public void accept(SchedulableProcessVisitor visitor) {
		// TODO Auto-generated method stub
		visitor.visit(this);
	}

}
