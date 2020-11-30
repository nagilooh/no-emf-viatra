/**
 */
package hu.bme.mit.inf.friends;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>People</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link hu.bme.mit.inf.friends.People#getPerson <em>Person</em>}</li>
 * </ul>
 *
 * @see hu.bme.mit.inf.friends.FriendsPackage#getPeople()
 * @model
 * @generated
 */
public interface People extends EObject {
	/**
	 * Returns the value of the '<em><b>Person</b></em>' containment reference list.
	 * The list contents are of type {@link hu.bme.mit.inf.friends.Person}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Person</em>' containment reference list.
	 * @see hu.bme.mit.inf.friends.FriendsPackage#getPeople_Person()
	 * @model containment="true"
	 * @generated
	 */
	EList<Person> getPerson();

} // People
