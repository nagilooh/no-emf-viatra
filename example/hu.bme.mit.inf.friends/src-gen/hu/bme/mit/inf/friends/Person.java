/**
 */
package hu.bme.mit.inf.friends;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Person</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link hu.bme.mit.inf.friends.Person#getFriend <em>Friend</em>}</li>
 *   <li>{@link hu.bme.mit.inf.friends.Person#getName <em>Name</em>}</li>
 * </ul>
 *
 * @see hu.bme.mit.inf.friends.FriendsPackage#getPerson()
 * @model
 * @generated
 */
public interface Person extends EObject {
	/**
	 * Returns the value of the '<em><b>Friend</b></em>' reference list.
	 * The list contents are of type {@link hu.bme.mit.inf.friends.Person}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Friend</em>' reference list.
	 * @see hu.bme.mit.inf.friends.FriendsPackage#getPerson_Friend()
	 * @model
	 * @generated
	 */
	EList<Person> getFriend();

	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see hu.bme.mit.inf.friends.FriendsPackage#getPerson_Name()
	 * @model
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link hu.bme.mit.inf.friends.Person#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

} // Person
