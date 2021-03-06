/**
 */
package cz.cuni.mff.d3s.jdeeco.core.model.runtimemodel.impl;

import cz.cuni.mff.d3s.jdeeco.core.model.runtimemodel.Ensemble;
import cz.cuni.mff.d3s.jdeeco.core.model.runtimemodel.KnowledgeExchange;
import cz.cuni.mff.d3s.jdeeco.core.model.runtimemodel.RuntimemodelPackage;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EcoreUtil;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Knowledge Exchange</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link cz.cuni.mff.d3s.jdeeco.core.model.runtimemodel.impl.KnowledgeExchangeImpl#getEnsemble <em>Ensemble</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class KnowledgeExchangeImpl extends ParameterizedMethodImpl implements KnowledgeExchange {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected KnowledgeExchangeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return RuntimemodelPackage.Literals.KNOWLEDGE_EXCHANGE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Ensemble getEnsemble() {
		if (eContainerFeatureID() != RuntimemodelPackage.KNOWLEDGE_EXCHANGE__ENSEMBLE) return null;
		return (Ensemble)eContainer();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetEnsemble(Ensemble newEnsemble, NotificationChain msgs) {
		msgs = eBasicSetContainer((InternalEObject)newEnsemble, RuntimemodelPackage.KNOWLEDGE_EXCHANGE__ENSEMBLE, msgs);
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setEnsemble(Ensemble newEnsemble) {
		if (newEnsemble != eInternalContainer() || (eContainerFeatureID() != RuntimemodelPackage.KNOWLEDGE_EXCHANGE__ENSEMBLE && newEnsemble != null)) {
			if (EcoreUtil.isAncestor(this, newEnsemble))
				throw new IllegalArgumentException("Recursive containment not allowed for " + toString());
			NotificationChain msgs = null;
			if (eInternalContainer() != null)
				msgs = eBasicRemoveFromContainer(msgs);
			if (newEnsemble != null)
				msgs = ((InternalEObject)newEnsemble).eInverseAdd(this, RuntimemodelPackage.ENSEMBLE__KNOWLEDGE_EXCHANGE, Ensemble.class, msgs);
			msgs = basicSetEnsemble(newEnsemble, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RuntimemodelPackage.KNOWLEDGE_EXCHANGE__ENSEMBLE, newEnsemble, newEnsemble));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case RuntimemodelPackage.KNOWLEDGE_EXCHANGE__ENSEMBLE:
				if (eInternalContainer() != null)
					msgs = eBasicRemoveFromContainer(msgs);
				return basicSetEnsemble((Ensemble)otherEnd, msgs);
		}
		return super.eInverseAdd(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case RuntimemodelPackage.KNOWLEDGE_EXCHANGE__ENSEMBLE:
				return basicSetEnsemble(null, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eBasicRemoveFromContainerFeature(NotificationChain msgs) {
		switch (eContainerFeatureID()) {
			case RuntimemodelPackage.KNOWLEDGE_EXCHANGE__ENSEMBLE:
				return eInternalContainer().eInverseRemove(this, RuntimemodelPackage.ENSEMBLE__KNOWLEDGE_EXCHANGE, Ensemble.class, msgs);
		}
		return super.eBasicRemoveFromContainerFeature(msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case RuntimemodelPackage.KNOWLEDGE_EXCHANGE__ENSEMBLE:
				return getEnsemble();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case RuntimemodelPackage.KNOWLEDGE_EXCHANGE__ENSEMBLE:
				setEnsemble((Ensemble)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case RuntimemodelPackage.KNOWLEDGE_EXCHANGE__ENSEMBLE:
				setEnsemble((Ensemble)null);
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case RuntimemodelPackage.KNOWLEDGE_EXCHANGE__ENSEMBLE:
				return getEnsemble() != null;
		}
		return super.eIsSet(featureID);
	}

} //KnowledgeExchangeImpl
