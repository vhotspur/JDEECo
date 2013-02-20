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
package cz.cuni.mff.d3s.deeco.demo.cloud;

import cz.cuni.mff.d3s.deeco.annotations.DEECoEnsemble;
import cz.cuni.mff.d3s.deeco.annotations.DEECoEnsembleMapper;
import cz.cuni.mff.d3s.deeco.annotations.DEECoEnsembleMembership;
import cz.cuni.mff.d3s.deeco.annotations.DEECoIn;
import cz.cuni.mff.d3s.deeco.annotations.DEECoOut;
import cz.cuni.mff.d3s.deeco.annotations.DEECoPeriodicScheduling;
import cz.cuni.mff.d3s.deeco.ensemble.Ensemble;
import cz.cuni.mff.d3s.deeco.knowledge.OutWrapper;

/**
 * Sample ensemble class.
 * 
 * @author Michal Kit
 *
 */
@DEECoEnsemble
@DEECoPeriodicScheduling(3000)
public class MigrationEnsemble extends Ensemble {

	@DEECoEnsembleMembership
	public static boolean membership(
			@DEECoIn("member.id") String mId,
			@DEECoIn("member.loadRatio") Float mLoadRatio,
			@DEECoIn("member.maxLoadRatio") Float mMaxLoadRatio,
			@DEECoIn("coord.id") String cId,
			@DEECoIn("coord.loadRatio") Float cLoadRatio,
			@DEECoIn("coord.maxLoadRatio") Float cMaxLoadRatio) {
		//System.out.println("[MigrationEnsemble.membership] mId = " + mId + ", mLoadRatio = " + mLoadRatio + ", mMaxLoadRatio = " + mMaxLoadRatio + ", cId = " + cId + ", cLoadRatio = " + cLoadRatio + ", cMaxLoadRatio = " + cMaxLoadRatio);
		//if (!mId.equals(cId) && mLoadRatio > mMaxLoadRatio && cLoadRatio < cMaxLoadRatio) System.out.println("[MigrationEnsemble.membership] result = true");
		//else System.out.println("[MigrationEnsemble.membership] result = false");
		// INJECTED ERROR
		//assert (Math.abs(mLoadRatio.floatValue() - cLoadRatio.floatValue()) < 0.5f) : "[ERROR] difference between loads >= 50 %";
		return !mId.equals(cId) && mLoadRatio > mMaxLoadRatio && cLoadRatio < cMaxLoadRatio;
	}

	@DEECoEnsembleMapper
	public static void map(@DEECoOut("member.targetNode") OutWrapper<String> mTargetNode,
			@DEECoIn("coord.id") String cId) {
		System.out.println("Move application to " + cId);
		mTargetNode.item = cId;
	}
}