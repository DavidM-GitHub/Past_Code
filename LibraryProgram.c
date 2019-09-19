/**************************************************************/
/*                                                            */
/*   Program to demonstrate the implmentation of a            */
/*   linked list of numbers.								  */
/*                                                            */
/**************************************************************/
#define _CRT_SECURE_NO_WARNINGS
#define bool int
#define false 0
#define true (!false)

//Libraries
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <ctype.h>


//Preprocessor Variable
#define SIZE 10


//Stucture template for a node
struct LinearNode {
	struct Book *book;
	struct LinearNode *next;
};

//structure template for a book
struct Book {
	char id[10];
	char name[20];
	char author[20];
	int yearPublished;
	bool loaned;
	char customerName[20];
	int timesLoaned;
	char review[100];//*************Personal Feature***********
};


// Function prototypes
void addBook();  //adding nodes to end of the list
void deleteNode(char); // delete a specific node
void returnBook();
void viewSpecificBook();
void takeOutBook();
void viewAllBooks();
void displayBook(struct LinearNode *);
bool isEmpty();
void viewMostPopular();
struct LinearNode *findBook(char);
bool isUnique(char);
bool isValid(char);
void menu();
void writeReview();//*************Personal Feature***********
void getFromFile(FILE *);
void savetoFile(FILE *);
//struct LinearNode *readNextFromFile(FILE *);




// Global Variables
struct LinearNode *front = NULL;
struct LinearNode *last = NULL;
int count = 0;//keeps track of the number of books in the library

/**************MAIN FUNCTION*******************/
int main() {

	printf("Welcome to the library\n");
	//Tried a few different ways for the file handling but couldnt get it to work
	FILE *filePointer;
	filePointer = fopen("E:book.dat", "rb");
	/*if (filePointer == NULL) {
		front = NULL;
		fseek(filePointer, 0, SEEK_END);
		long fileSize = ftell(filePointer);
		rewind(filePointer);
		int num = (int)(fileSize / (sizeof(struct LinearNode)));
		for (int i = 0; i < num; i++) {
			fseek(filePointer, (sizeof(struct LinearNode))*i, SEEK_SET);
			front = readNextFromFile(front, filePointer);
		}
	}
	else {
		printf("FILE OPEN ERROR");
	}*/

	if ((filePointer = fopen("E:book.dat", "rb")) == NULL) {
		printf("No database currently exists.. You must enter the details of the books yourself ");
	}
	else {
		getFromFile(filePointer);
	}

	menu();

	getchar();
	getchar();
}

void getFromFile(FILE *filePointer) {
	struct LinearNode *current = front;
	printf("Retrieving books from file...\n");
	while (fread(&current, sizeof(struct LinearNode), 1, filePointer) != NULL) {
		current = current->next;
	}
	fclose(filePointer);
}
/*
struct LinearNode *readNextFromFile(FILE *filePointer) {
	struct LinearNode *new;
	if (front == NULL) {
		front = malloc(sizeof(struct LinearNode));
		fread(front, sizeof(struct LinearNode), 1, filePointer);
		last = front;
		front->next = NULL;
	}
	else {
		new = malloc(sizeof(struct LinearNode));
		last->next = new;
	}
	return front;
}*/
//Display menu that returns number
void menu() {

	int result;
	printf("\nEnter 1 to add a new book to the library\nEnter 2 to allow a customer take out a book from the library\n");
	printf("Enter 3 to allow a customer return a book\nEnter 4 to delete an old book from stock\n");
	printf("Enter 5 to view all books\nEnter 6 to view a specific book\nEnter 7 to view the most and least popular books in the library\n");
	printf("Enter 8 to fill in a review from a famous author\nEnter 9 to exit the system: ");
	scanf("%d", &result);
	getchar();
	if (result == 1)
	{//1. Add a new book to the library
		addBook();
	}
	else if (result == 2)
	{//2. Allow customer to take out a book
		takeOutBook();
	}
	else if (result == 3)
	{//3. Allow customer to return a book
		returnBook();
	}
	else if (result == 4)
	{//4. Delete an old book from stock
		char deleteid[10];
		printf("\nEnter the id of the book you would like to delete from stock: ");
		fgets(deleteid, 10, stdin);
		deleteNode(deleteid);
	}
	else if (result == 5)
	{//5. View all books
		viewAllBooks();
	}
	else if (result == 6)
	{//6. View a specific book
		viewSpecificBook();
	}
	else if (result == 7)
	{//7. View details of most popular and least popular books
		viewMostPopular();
	}
	else if (result == 8)
	{//8. This should be an appropriate option that you provide
		writeReview();
	}
	else if (result == 9)
	{//9. Exit the system and save the database to a file
		printf("The database of books will now be saved back to a file");
		FILE *filePointer = NULL;
		savetoFile(filePointer);
	}
	else {
		printf("You must choose a valid option...\n Try again");
		getchar();
		menu();
	}
}

void savetoFile(FILE *filePointer) {
	filePointer = fopen("E:books.dat", "wb");
	struct LinearNode *current = front;
	//struct LinearNode *next = NULL;
	for (current = front; current != NULL; current = current->next) {
		fwrite(&current->book, (sizeof(struct Book)), 1, filePointer);
	}
	fclose(filePointer);
	/*if (filePointer != NULL) {
		while (current != NULL) {
			next = current->next;
			current->next = NULL;

			fseek(filePointer, 0, SEEK_END);
			fwrite(current, sizeof(struct LinearNode), 1, filePointer);

			next = NULL;
			current = current->next;
		}
		fclose(filePointer);
	}
	else {
		printf("FILE OPEN ERROR");
	}*/
}

/**********METHOD TO ADD BOOKS******************/
// Each new node is added to the end of the list

void addBook() {


	struct LinearNode *aNode;
	struct Book *aBook;
	bool valid;

	//Create space for Book part of node
	aBook = (struct Book *)malloc(sizeof(struct Book));

	if (aBook == NULL || count == 10)
		printf("Error - no space for the new element\n");
	else
	{
		getchar();
		//Input value into the data part
		do {
			printf("Enter the Book ID: ");
			fgets(aBook->id, 10, stdin);
			//valid = isValid(aBook->id);
		} while (isUnique(aBook->id) == false);//ensure unique
		printf("Enter the name of the book: ");
		fgets(aBook->name, 20, stdin);
		printf("Enter the author of the book: ");
		fgets(aBook->author, 20, stdin);
		do {
			printf("Enter the year the book was published: ");
			scanf("%d", &aBook->yearPublished);
			if (aBook->yearPublished < 2008) {
				printf("The year of publication cant be older than 2008\n");
			}
		} while (aBook->yearPublished < 2008);
		aBook->loaned = false;
		strcpy(aBook->customerName, "");//cant use assignment statement with strings
		aBook->timesLoaned = 0;
		strcpy(aBook->review, "");
		// create space for new node that will contain Book
		aNode = (struct LinearNode *)malloc(sizeof(struct LinearNode));

		if (aNode == NULL)
			printf("Error - no space for the new Book\n");
		else { // add book part to the node
			aNode->book = aBook;
			aNode->next = NULL;

			//add node to end of the list
			if (isEmpty())
			{
				front = aNode;
				last = aNode;
			}
			else {
				last->next = aNode;
				last = aNode;
			} //end else
		}//end else
	}//end else 
	count++;
	menu();
} //end addNodes
/*
//------FUNCTION TO CHECK IF A BOOK ID IS AN 8 DIGIT NUMBER------
bool isValid(char bookID[10]) {
	int i = 0;
	bool valid = true;
	if (bookID[4] == "-")
		{
			do {
				if (isdidget(bookID[i]) == 1) {
					i++;
				}
				else {
					valid=false;
				}
			} while (i != 4);
			i++;
			do {
				if (isdidget(bookID[i]) == 1) {
					i++;
				}
				else {
					valid= false;
				}
			} while (i < 9);
		}
		else {
			valid= false;
		}
	return valid;
	}
	*/
	//------FUNCTION TO CHECK IF A BOOK ID IS UNIQUE-------
bool isUnique(char bookID[10]) {
	bool unique = true;
	struct LinearNode *current = front;
	while (unique && current != NULL) {
		if (strcmp(bookID, current->book->id) == 0) {
			unique = false;
			printf("Sorry there is already a book in our library with this ID... Please try again \n");
		}
		else {
			current = current->next;
		}//end else
	} //end while
	return unique;
}

/**********VIEW ALL BOOKS IN THE LIST******************/
// Each new node is added to the end of the list
void viewAllBooks() {
	struct LinearNode *current;

	printf("\n");
	if (isEmpty())
		printf("Error - there are no books in the library\n");
	else {
		current = front;
		while (current != NULL) {
			displayBook(current);
			current = current->next;
		} //end while
	}//end else
	menu();
} //end viewAllBooks

//-----METHOD TO DISPLAY THE DETAILS OF THE BOOK PASSED IN AS A PARAMETER-------
void displayBook(struct LinearNode *book) {
	struct LinearNode *current = book;
	printf("\nThe Book ID is %s", current->book->id);
	printf("The name of the book is %s", current->book->name);
	printf("The author of the book is %s", current->book->author);
	printf("The book was published in %d\n", current->book->yearPublished);
	if (current->book->loaned == true)
	{
		printf("The book is currently on loan to %s", current->book->customerName);
	}
	else {
		printf("The book is not on loan at the moment\n");
	}
	printf("The book has been loaned %d times\n", current->book->timesLoaned);
	if (strcmp("", current->book->review) == 0) {
		printf("The book has no reviews as of yet\n");
	}
	else {
		printf("A best selling author described the book as... %s", current->book->review);
	}
}

//------METHOD TO VIEW THE BOOK THE USER ENTERS THE ID OF-------
void viewSpecificBook()
{
	getchar();//clears any unintended keystrokes
	char bookID[10];
	struct LinearNode *current = front;
	printf("\nEnter the id of the book: ");//idsearch---------------------
	fgets(bookID, 10, stdin);
	printf("\n");

	/*current = front;
	while (notFound && current != NULL) {
		if (strcmp(bookID, current->book->id) == 0)
			notFound = false;
		else {
			current = current->next;
		}//end else
	} //end while*/
	current = findBook(bookID);

	if (current == NULL) {
		printf("\nWe dont have any books matching this ID in our library\n");
	}
	else {
		displayBook(current);
	}
	menu();
}
//------THIS IS A METHOD THAT DISPALYS THE MOST AND LEAST POPULAR BOOKS IN THE LIBRARY------
void viewMostPopular() {
	struct LinearNode *mostPopular = front;
	struct LinearNode *leastPopular = front;
	struct LinearNode *current = front;

	printf("\n");
	if (isEmpty()) {
		printf("Error - there are no books in the library\n");
	}
	else {
		while (current != NULL) {
			if (current->book->timesLoaned > mostPopular->book->timesLoaned) {
				mostPopular = current;
			}
			else if (current->book->timesLoaned < leastPopular->book->timesLoaned) {
				leastPopular = current;
			}

			current = current->next;
		} //end while
		printf("-----Details of the Most Popular Book-----");
		displayBook(mostPopular);
		printf("\n");
		printf("-----Details of the Least Popular Book-----");
		displayBook(leastPopular);
	}	//end else
	menu();
}
//----THIS IS A METHOD THAT ALLOWS A CUSTOMER RETURN A BOOK-----   
void returnBook() {
	getchar();
	char bookID[10];
	char customerName[20];
	struct LinearNode *current = front;
	printf("\nEnter the id of the book you would like to return: ");//ID SEARCH
	fgets(bookID, 10, stdin);
	printf("\n");
	printf("\nEnter your name: ");
	fgets(customerName, 20, stdin);
	printf("\n");
	current = findBook(bookID);
	if (current == NULL) {
		printf("\nWe dont have any books matching this ID in our library\n");
	}
	if (strcmp(customerName, current->book->customerName) == 0) {
		current->book->loaned = false;
		strcpy(current->book->customerName, "");
		printf("You have succesfully returned the book, I hope you enjoyed the read.\n");
	}
	else {
		printf("The details you have entered are incorect, we could not find the book you rented.\n");
	}
	menu();
}

void writeReview() {
	char bookID[10];
	struct LinearNode *current = front;
	getchar();
	printf("\nEnter the id of the book you would like write a review on: ");
	fgets(bookID, 10, stdin);
	printf("\n");
	current = findBook(bookID);

	if (current == NULL) {
		printf("\nWe dont have any books matching this ID in our library\n");
	}
	else if (strcmp("", current->book->review) == 0) {
		printf("Write a review for the book: ");
		fgets(current->book->review, 100, stdin);
	}
	else if (strcmp("", current->book->review) != 0) {
		printf("This book already had a review written....");
	}
	menu();
}

void takeOutBook() {
	char bookID[10];
	struct LinearNode *current = front;
	printf("\nEnter the id of the book you would like to rent: ");
	fgets(bookID, 10, stdin);
	printf("\n");

	current = findBook(bookID);

	if (current == NULL) {
		printf("\nWe dont have any books matching this ID in our library\n");
	}
	else if (current->book->loaned == false) {
		printf("\nThis book is available to rent, what is your name? ");
		fgets(current->book->customerName, 20, stdin);
		current->book->loaned = true;
		current->book->timesLoaned++;
		printf("\nYou are free to take this book, enjoy the read and please handle it with care\n");
	}
	else {
		printf("\nThis book is not available to rent from our library at the moment\n");
	}
	menu();
}

struct LinearNode *findBook(char *bookID[10]) {
	getchar();
	struct LinearNode *current = front;
	bool notFound = true;
	printf("\n");
	if (isEmpty()) {
		printf("Error - there are no books in the library\n");
	}
	else {
		while (notFound && current != NULL) {
			if (strcmp(bookID, current->book->id) == 0) {
				notFound = false;
			}
			else {
				current = current->next;
			}//end else
		} //end while
	}
	if (notFound == false) {
		return current;
	}
	else {
		return NULL;
	}
}

void deleteNode(char bookID[10]) {
	struct LinearNode *current, *previous;
	bool notFound = true;

	printf("\n");
	if (isEmpty())
		printf("Error - there are no books in the library\n");
	else {
		current = previous = front;

		while (notFound && current != NULL) {
			if (strcmp(bookID, current->book->id) == 0)
				notFound = false;
			else {
				previous = current;
				current = current->next;
			}//end else
		} //end while

		if (notFound) {
			printf("Error - there is no book with the ID %s\n", bookID);
		}
		if (notFound == false && current->book->yearPublished < 2010) {
			count--;
			if (current == front) { //delete front node
				front = front->next;
				free(current);
			} //end else
			else if (current == last) {//delete last node
				free(current);
				previous->next = NULL;
				last = previous;
			}
			else {//delete node in middle of list
				previous->next = current->next;
				free(current);
			} //end else
			printf("The book with the value %s has been deleted\n", bookID);
		}//end else
		else {
			printf("Only books that were published before 2010 can be deleted\n");
		}
	}//end else
	menu();
}// end deleteNode


bool isEmpty() {
	if (front == NULL)
		return true;
	else
		return false;
}

