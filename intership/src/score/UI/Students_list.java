package score.UI;

/**
 * This is a single linked list that only takes care of adding, storing, and
 * deleting data, and the rest is done by the two-dimensional array
 * 
 * @author Bloduc_Spauter
 *
 */
interface Students_list {

	/**
	 * Add a node to a unidirectional linked list
	 * 
	 * @param node
	 */
	public void listadd(score.UI.Students node);

	/** Print linked list */
	public void printList();

	/**
	 * Delete specified node
	 * 
	 * 
	 * @param index
	 * @return
	 */
	public boolean deleteIndexNode(int index);
	

	/** To get the length of the linked list*/
	public int listLength();


}