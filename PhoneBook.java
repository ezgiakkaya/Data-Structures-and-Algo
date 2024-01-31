package code;

import java.util.ArrayList;
import java.util.List;

import given.ContactInfo;
import given.DefaultComparator;

/*
 * A phonebook class that should:
 * - Be efficiently (O(log n)) searchable by contact name
 * - Be efficiently (O(log n)) searchable by contact number
 * - Be searchable by e-mail (can be O(n)) 
 * 
 * The phonebook assumes that names and numbers will be unique
 * Extra exercise (not to be submitted): Think about how to remove this assumption
 * 
 * You need to use your own data structures to implement this
 * 
 * Hint: You need to implement a multi-map! 
 * 
 */
public class PhoneBook {
  BinarySearchTree<String, ContactInfo> nameDirectory = new BinarySearchTree<String, ContactInfo>();
  BinarySearchTree<String, ContactInfo> numberDirectory = new BinarySearchTree<String, ContactInfo>();

  public PhoneBook() {
    nameDirectory.setComparator(new DefaultComparator<String>());
    numberDirectory.setComparator(new DefaultComparator<String>());

  }

  // Returns the number of contacts in the phone book
  public int size() {
    return nameDirectory.size();
  }

  // Returns true if the phone book is empty, false otherwise
  public boolean isEmpty() {
    return numberDirectory.isEmpty();
  }

  // Adds a new contact or overwrites an existing contact with the given info
  // Args should be given in the order of e-mail and address which is handled for
  // you
  // Note that it can also be empty. If you do not want to update a field pass
  // null
  public void addContact(String name, String number, String... args) {
    int numArgs = args.length;
    String email = null;
    String address = null;

    if (numArgs > 0)
      if (args[0] != null)
        email = args[0];
    if (numArgs > 1)
      if (args[1] != null)
        address = args[1];
    if (numArgs > 2)
      System.out.println("Ignoring extra arguments");

    ContactInfo newContact = new ContactInfo(name, number);
    newContact.setAddress(address);
    newContact.setEmail(email);
    nameDirectory.put(name, newContact);
    numberDirectory.put(number, newContact);
  }

  // Return the contact info with the given name
  // Return null if it does not exists
  // Should be O(log n)!
  public ContactInfo searchByName(String name) {
    /*
     * 
     * BinaryTreeNode<String, ContactInfo> contact = nameDirectory.getNode(name);
     * if (contact != null && contact.getValue().getName() != null) {
     * return contact.getValue();
     * 
     * } else
     * 
     * return null;
     */

    return nameDirectory.get(name);
  }

  // Return the contact info with the given phone number
  // Return null if it does not exists
  // Should be O(log n)!
  public ContactInfo searchByPhone(String phoneNumber) {
    /*
     * BinaryTreeNode<String, ContactInfo> contact =
     * numberDirectory.getNode(phoneNumber);
     * if (contact != null && contact.getValue().getNumber() != null) {
     * return contact.getValue();
     * } else
     * 
     * return null;
     */

    return numberDirectory.get(phoneNumber);
  }

  // Return the contact info with the given e-mail
  // Return null if it does not exists
  // Can be O(n)
  public ContactInfo searchByEmail(String email) {
    List<BinaryTreeNode<String, ContactInfo>> list = nameDirectory.getNodesInOrder();
    for (BinaryTreeNode<String, ContactInfo> node : list) {
      String nodeMail = node.getValue().getEmail();
      if (nodeMail != null && nodeMail.equals(email)) {
        return node.getValue();
      }
    }
    return null;
  }

  // Removes the contact with the given name
  // Returns true if there is a contact with the given name to delete, false
  // otherwise
  public boolean removeByName(String name) {
    ContactInfo contactToBeRemoved = nameDirectory.remove(name);

    if (contactToBeRemoved != null) {
      numberDirectory.remove(contactToBeRemoved.getNumber());
      return true;
    } else
      return false;

  }

  // Removes the contact with the given name
  // Returns true if there is a contact with the given number to delete, false
  // otherwise
  public boolean removeByNumber(String phoneNumber) {
    ContactInfo contactToBeRemoved = numberDirectory.remove(phoneNumber);
    if (contactToBeRemoved != null) {
      nameDirectory.remove(contactToBeRemoved.getName());
      return true;

    }
    return false;
  }

  // Returns the number associated with the name
  public String getNumber(String name) {
    String number = searchByName(name).getNumber();
    return number;

  }

  // Returns the name associated with the number
  public String getName(String number) {
    String name = searchByPhone(number).getName();
    return name;
  }

  // Update the email of the contact with the given name
  // Returns true if there is a contact with the given name to update, false
  // otherwise
  public boolean updateEmail(String name, String email) {
    ContactInfo contact = searchByName(name);
    if (contact != null) {
      contact.setEmail(email);
      return true;
    }

    return false;
  }

  // Update the address of the contact with the given name
  // Returns true if there is a contact with the given name to update, false
  // otherwise
  public boolean updateAddress(String name, String address) {
    ContactInfo contact = searchByEmail(name);
    if (contact != null) {
      contact.setAddress(address);
      return true;
    } else
      return false;
  }

  // Returns a list containing the contacts in sorted order by name
  public List<ContactInfo> getContacts() {
    List<ContactInfo> contacts = new ArrayList<>();

    List<BinaryTreeNode<String, ContactInfo>> sortedNodes = nameDirectory.getNodesInOrder();
    for (BinaryTreeNode<String, ContactInfo> node : sortedNodes) {
      if (node != null) {
        contacts.add(node.getValue());
      }

    }
    return contacts;

  }

  // Prints the contacts in sorted order by name
  public void printContacts() {
    List<ContactInfo> sortedByName = getContacts();
    for (ContactInfo contact : sortedByName) {
      System.out.println(contact);
    }
  }
}
