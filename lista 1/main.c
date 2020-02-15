/***
 * @author Aleksandra Malinowska
 * @version 1.1
 * Source code for the first list for algorithms class.
 */

#include <stdio.h>
#include <stdlib.h>
#include <time.h>

/**
 * @brief Type for list objects.
 */
typedef struct node {
    int value;
    struct node* next;
}list;
/**
 * @brief Prints all the elements of the list in-line.
 * In case of lack of elements function prints information 'empty list'.
 */
void printList(list*);
/**
 * Checks whether node is a leaf or not.
 * @return 1 if node is a leaf, 0 if is not
 */
int isLeaf(list*);
/**
 * Checks whether list is empty or not.
 * @return 1 if list is empty, 0 if is not
 */
int isEmpty(list*);
/**
 * Inserts an element to the end of the list.
 */
void insert(list**, int);
/**
 * Deletes first found element of the list that has passed value.
 */
void delete(list**, int);
/**
 * Looks for the value in the list and (if found) moves it to the front of the list.
 * @return 1 if element was found, 0 if not
 */
int findMTF(list**, int);
/**
 * Looks for the value in the list and (if found) moves it one place closer to the front of the list.
 * @return 1 if element was found, 0 if not
 */
int findTrans(list**, int);
/**
 * Inserts numbers from 1 to 100 to the list in random order.
 */
void insertRandom(list**);
/**
 * Deletes numbers from 100 to 1 from the list.
 */
void deleteMaxWithMTF(list **);
/**
 * Till the list is not empty, finds the numbers from 1 to 100 with {@code findMTF()} function and deletes the maximum found value.
 */
void deleteMaxWithTrans(list **);

/**
 * Counts comparisons made during running (@code deleteMaxWithMTF()} function.
 */
long long counterMTF = 0;
/**
 * Counts comparisons made during running (@code deleteMaxWithTrans()} function.
 */
long long counterTrans = 0;

/**
 * Main function of the document. Runs the tests for deleting maximum functions.
 * @return 0
 */
int main() {
    /// initialization of the list head
    list *head = NULL;
    int i;
    /// initialization of variables to count average number of comparisons
    long long comparisons=0,avg=0, n=100;
    printf("Average number of comparisons for function:\n");
    /// running the test for the first algorithm - deleting maximum with deleteMaxWithMTF()
    for (i = 0; i < n; i++) {
        counterMTF = 0;
        insertRandom(&head);
        deleteMaxWithMTF(&head);
        comparisons += counterMTF;
    }
    avg = comparisons/n;
    printf("\tfindMTF(): %lli\n", avg);
    /// running the test for the fourth algorithm - deleting maximum with deleteMaxWithTrans()
    comparisons=0;
    for (i = 0; i < n; i++) {
        counterTrans = 0;
        insertRandom(&head);
        deleteMaxWithTrans(&head);
        comparisons += counterTrans;
    }
    avg = comparisons/n;
    printf("\tfindTrans(): %lli\n", avg);

    printf("\n\n=========================================\n");
    printf("Examples");
    printf("\n=========================================\n\n");
    printList(head);
    insert(&head, 12);
    insert(&head, 0);
    insert(&head, 47);
    insert(&head, 2);
    insert(&head, 34);
    printf("After insertion of 5 elements:\n");
    printList(head);
    delete(&head, 0);
    printf("After deletion of 0:\n");
    printList(head);
    findMTF(&head, 2);
    printf("After finding 2 with findMTF():\n");
    printList(head);
    findTrans(&head, 34);
    printf("After finding 34 with findTrans():\n");
    printList(head);
    delete(&head, 12);
    delete(&head, 47);
    delete(&head, 2);
    delete(&head, 34);
    printf("After deletion of 12, 47, 2 and 34:\n");
    printList(head);
    return 0;
}

void printList(list* head) {
    /// checks if list is empty
    if (isEmpty(head) == 1) {
        printf("List is empty\n");
    } else {
        /// otherwise
        printf("List: %d ", head->value);
        list * current = head;
        /// till node is not a leaf prints the next value
        while (isLeaf(current) == 0) {
            current = current->next;
            printf("%d ", current->value);
        }
        printf("\n");
    }
}

int isLeaf(list* node) {
    if (node->next == NULL) {
        return 1;
    } else {
        return 0;
    }
}

int isEmpty(list* head) {
    return head == NULL;
}
void insert(list** head, int value) {
    if (isEmpty(*head) == 1) {
        /// if list is empty allocates the memory for the first element
        *head = malloc(sizeof(list));
        /// and assigns the value for it
        (*head)->value = value;
        (*head)->next = NULL;
    } else {
        /// otherwise
        list *current = *head;
        /// finds the last element of the list
        while (isLeaf(current) == 0) {
            current = current->next;
        }
        /// allocates the memory for the next element
        current->next = malloc(sizeof(list));
        /// and assigns the value for it
        current->next->value = value;
        current->next->next = NULL;
    }
}

void delete(list** head, int value) {
    if (isEmpty(*head) == 0) {
        /// if list is not empty
        list *current = *head;
        list *temp = NULL;
        if (current->value == value) {
            /// if the value to delete is the first element of the list
            if (isLeaf(current) == 1) {
                /// if the head is a leaf
                /// frees the memory of the head and deletes the list
                free(*head);
                *head = NULL;
            } else {
                /// otherwise
                /// temporarily saves the rest of the list to {@code temp}
                temp = current->next;
                /// deletes the first element
                free(*head);
                /// and saves the head of temp as new head of the list
                *head = temp;
            }
        } else {
            /// if the value is not in head
            /// till the next element exists
            while (isLeaf(current) == 0) {
                /// checks if it is the looking element
                if (current->next->value == value) {
                    /// if so
                    /// deletes the element and connects two nodes of the list
                    temp = current->next->next;
                    free(current->next);
                    current->next = temp;
                    return;
                } else {
                    /// otherwise
                    /// goes to the next node
                    current = current->next;
                }
            }
        }
    }
}

int findMTF(list** head, int value) {
    if (isEmpty(*head) == 1) {
        /// if the list is empty, element was not found
        return 0;
    } else {
        counterMTF++;
        if ((*head)->value == value) {
            /// if the first element of the list is the looking one, element was found
            return 1;
        } else {
            /// otherwise
            list *current = *head;
            list *temp = NULL;
            list *found = NULL;
            /// till the next node exists
            while (isLeaf(current) == 0) {
                counterMTF++;
                /// checks if it is the looking element
                if (current->next->value == value) {
                    /// if so
                    /// saves it temporarily to {@code found}
                    found = current->next;
                    /// sets next node of the current one to the next of the found
                    temp = current->next->next;
                    current->next = temp;
                    /// and sets the head of the list to the found element
                    temp = *head;
                    *head = found;
                    (*head)->next = temp;
                    /// element was found
                    return 1;
                } else {
                    /// otherwise
                    /// goes to the next node
                    current = current->next;
                }
            }
            /// in case of reaching the end of the list
            /// element was not found
            return 0;
        }

    }
}

int findTrans(list** head, int value){
    if (isEmpty(*head) == 1) {
        /// if the list is empty, element was not found
        return 0;
    } else {
        counterTrans++;
        if ((*head)->value == value) {
            /// if the first element of the list is the looking one, element was found
            return 1;
        } else {
            /// otherwise
            list *current = *head;
            list *temp = NULL;
            list *found = NULL;
            list *beforeFound = NULL;
            if (isLeaf(current) == 1) {
                /// if the head is a leaf, element was not found
                return 0;
            } else {
                /// otherwise
                counterTrans++;
                if (current->next->value == value) {
                    /// if the value of the second node is the looking one
                    /// saves the node to {@code found}
                    found = current->next;
                    /// sets next node of the current one to the next of the found
                    temp = current->next->next;
                    current->next = temp;
                    /// and sets the head of the list to the found element
                    temp = *head;
                    *head = found;
                    (*head)->next = temp;
                    /// element was found
                    return 1;
                } else {
                    /// otherwise
                     do {
                        /// checks if the next node exists
                        if (isLeaf(current->next) == 0) {
                            counterTrans++;
                            /// if so
                            /// checks if the looking value is two nodes from the current one
                            if (current->next->next->value == value) {
                                /// switches the nodes
                                temp = current->next->next->next;
                                found = current->next->next;
                                beforeFound = current->next;
                                current->next = found;
                                current->next->next = beforeFound;
                                current->next->next->next = temp;
                                /// element was found
                                return 1;
                            } else {
                                /// otherwise
                                /// goes to the next node
                                current = current->next;
                            }
                        } else {
                            /// if not
                            /// element was not found
                            return 0;
                        }
                    /// repeats until next node exists
                    }while (isLeaf(current) == 0);
                }
            }

        }

    }
    return 0;
}

void insertRandom(list** head) {
    int i, a, n=100;
    srand((unsigned int) time(NULL));
    /// allocates table of already used numbers to avoid repetition
    int inserted[100];
    for(i = 0; i < n; i++) {
        inserted[i] = i+1;
    }
    for (i = 0; i < n; i++) {
        /// gets the random number
        a = rand() % n + 1;
        if (inserted[a-1] != 0) {
            /// if not already on the list, inserts it
            insert(head, a);
            inserted[a-1] = 0;
        } else {
            i--;
        }
    }
}

void deleteMaxWithMTF(list ** head) {
    int i;
    /// till the list is not empty
    while (isEmpty(*head) == 0) {
        /// looks for number from 1 to 100
        for (i = 1; i <= 100; i++) {
            findMTF(head,i);
        }
        /// and deletes the maximum value
        list * temp = (*head)->next;
        free(*head);
        *head = temp;
    }
}

void deleteMaxWithTrans(list ** head) {
    int i,max;
    /// till the list is not empty
    while (isEmpty(*head) == 0) {
        max = 0;
        /// looks for numbers from 1 to 100
        for (i = 1; i <= 100; i++) {
            if (findTrans(head, i) == 1) {
                /// if found, checks if bigger than maximum found value
                if (i > max) {
                    /// if so, saves new maximum value
                    max = i;
                }
            }
        }
        /// and deletes maximum
        delete(head, max);
    }
}