/* ODBC Example Program */
/* The program executes a simple SQL query and displays the results. */
/* Stephen J. Hegner, November 12, 1999; Revised 16.11.1999 */
/* Revised for Linux/Unix 05.11.02 */
/* Minor revision of comments 18.11.04 */
/* Added include for string.h (needed for gcc-4) 13.11.06 */
/* Added alternative for data size on 64-bit systems 31.03.11 */

#include <sql.h>
#include <sqlext.h>
#include <stdio.h>
#include <string.h>

/* These includes are involved in the suppression of input echoing. */
#include <unistd.h>
#include <signal.h>
#include <termios.h>

typedef char c_boolean;
#define TRUE 1
#define FALSE 0
#define DEL '\177'

/* Turn off tty echoing. */
#define ECHO_OFF \
  sigemptyset(&sig); sigaddset(&sig,SIGINT); sigaddset(&sig,SIGTSTP); \
  sigprocmask(SIG_BLOCK,&sig,&sigsave); \
  tcgetattr(STDIN_FILENO,&termsave); term=termsave; \
  term.c_lflag &= ~(ECHO | ECHOE | ECHOK | ECHONL | ICANON | IEXTEN); \
  term.c_cc[VMIN]=1; term.c_cc[VTIME]=0; \
  tcsetattr(STDIN_FILENO,TCSAFLUSH, &term);

/* Restore tty echoing. */
#define ECHO_ON \
  tcsetattr(STDIN_FILENO,TCSAFLUSH,&termsave); \
  sigprocmask(SIG_SETMASK,&sigsave,NULL);

/* Necessary data items for no-echo tty capture */
/* See Stevens' APUE Program 11.8 for details. */
/* Figure 18.17 in the Second edition co-authored with Rago */
  sigset_t	sig, sigsave;
  struct 	termios term,termsave;


SQLHANDLE environment_handle;
SQLHANDLE connection_handle;

/* There must be a statement handle for each SQL statement. */
SQLHANDLE statement_handle, statement_handle1, statement_handle2,statement_handle3;

SQLRETURN return_code,return_code2;

/* Prototypes */
RETCODE display_Dept_table(void);
RETCODE display_Emp_table(void);
RETCODE display_project(void);
RETCODE display_Summary_result(void);
RETCODE display_otEmployees(void);
RETCODE get_input(SQLCHAR *, SQLCHAR *, c_boolean *);
RETCODE get_Locationinput(SQLCHAR *, SQLCHAR *, c_boolean *);
RETCODE get_DeptDisinput(SQLCHAR *, SQLCHAR *, c_boolean *);
RETCODE get_Deptinput(SQLCHAR *, SQLCHAR *, c_boolean *);
RETCODE get_empInput(SQLCHAR *, SQLCHAR *, c_boolean *);
RETCODE get_EmpDelete(SQLCHAR *,SQLINTEGER *, c_boolean *);
RETCODE get_EmpDisinput(SQLCHAR *, SQLCHAR *, c_boolean *);
RETCODE get_hrsInput(SQLCHAR *, SQLCHAR *,SQLCHAR *,c_boolean *);
RETCODE get_summaryInput(SQLCHAR *, c_boolean *);



void changePName();
void changePLocation();
void changeDName();
void addEmployee();
void deleteEmp();
void changeHrs();
void displayProjectSummary();
void displayovertimeemployees(); 

c_boolean terminate;
char c;
int p;


 

/**************************************************************************/
int main() {
    /* Storage for the query string. */
    SQLCHAR sql_statement[255];
    /* Storage for database name and user id */
    SQLCHAR ODBC_database_name[80] = "Company";
    SQLCHAR ODBC_database_user_id[80] = "c5dv052_vt11_mcs10kai";

    char input;
    //SQLINTEGER p_no;
    //SQLINTEGER p_new;
    /* First, allocate an environment handle. */
    return_code =
            SQLAllocHandle(SQL_HANDLE_ENV, SQL_NULL_HANDLE, &environment_handle);


    /* Next, set the ODBC application version. */
    if (return_code == SQL_SUCCESS)
        return_code =
            SQLSetEnvAttr(environment_handle, SQL_ATTR_ODBC_VERSION,
            (SQLPOINTER) SQL_OV_ODBC3, SQL_IS_UINTEGER);

    /* Allocate a connection handle for the database. */
    if (return_code == SQL_SUCCESS)
        return_code =
            SQLAllocHandle(SQL_HANDLE_DBC, environment_handle, &connection_handle);

  

    /* Connect to the database. */
    /* Assume null password here.  (Ignored by PostgreSQL) */
    if (connection_handle != NULL) {
        return_code =
                SQLConnect
                (connection_handle,
                ODBC_database_name, /* String variable containing database name */
                SQL_NTS, /* Database name is null-terminated string. */
                ODBC_database_user_id, /* String variable containing user ID */
                SQL_NTS, /* User ID is null-terminated string. */
                (SQLCHAR *) "", /* Null password */
                SQL_NTS); /* User password is null-terminated string. */

        if ((return_code == SQL_SUCCESS)
                || (return_code == SQL_SUCCESS_WITH_INFO))
            printf("Successful connection to Database %s.\n\n",
                ODBC_database_name);
        else
            printf("Connection to Database %s failed.\n",
                ODBC_database_name);

        /* Allocate the statement handle. */
        return_code =
                SQLAllocHandle
                (SQL_HANDLE_STMT, /* Statement handle. */
                connection_handle, /* Input handle is the connection handle. */
                &statement_handle); /* Pointer to the output handle. */

        printf("/--------------------------------------------------------/ \n\n");
	printf("    ###############USER'S MAIN MENU###############\n\n");
        printf("          0. Exit the Program.\n\n");
        printf("          1. Change the Name of a project.\n\n");
        printf("          2. Change the Location of a project.\n\n");
        printf("          3. Change the Department in which a project is located.\n\n");
        printf("          4. Add an Employee to a project.\n\n");
        printf("          5. Delete an Employee from a project.\n\n");
        printf("          6. Change the number of hours which an Employee works on a given project. \n\n");
        printf("          7. Display summary report for a given project. \n\n");
        printf("          8. Display a list of all overtime Employees. \n\n");
        printf("/--------------------------------------------------------/ \n\n\n");
	printf("Enter the number associated with your choice:  \n");

        scanf("%c", &input);

        switch (input) {
            case '1': changePName();
                break;
            case '2': changePLocation();
                break;
            case '3': changeDName();
                break;
            case '4': addEmployee();
                break;
            case '5': deleteEmp();
                break;
            case '6': changeHrs();
                break;
            case '7': displayProjectSummary();
                break;
            case '8': displayovertimeemployees();
                break;
            case '0': exit(0);
                break;
            default: printf("\n\n", input);
        }





        /* Free the SQL statement handle */
        if (statement_handle != NULL)
            SQLFreeHandle
                (SQL_HANDLE_STMT, /* Statement handle type. */
                statement_handle);

        /* Disconnect from the database. */
        return_code = SQLDisconnect(connection_handle);

        /* Free the connection handle. */
        SQLFreeHandle(SQL_HANDLE_DBC, connection_handle);
    }

    /*Free the environment handle. */
    if (environment_handle != NULL)
        SQLFreeHandle(SQL_HANDLE_ENV, environment_handle);

    /* Return To The Operating System. */
    return (return_code);
}


void changePName() {

    /* Storage for the query string */
    SQLCHAR sql_statement[255];
    /* Stroage for the prompted input data */
    SQLCHAR projectName[30];
    SQLCHAR NewName[30];

    /*Define an SQL query to be executed */
    strcpy((char *) sql_statement,
            "UPDATE project set pname= ? WHERE pname= ?");

    /* Compile the SQL statement. */
    return_code = SQLPrepare
            (statement_handle, /* Handle for the statement. */
            sql_statement, /* Pointer to the string containing the stmt. */
            SQL_NTS); /* String is null terminated. */

    get_input(projectName, NewName, &terminate);
    while (!terminate) {
        /* Bind the first parameter of the query. */
        return_code = SQLBindParameter
                (statement_handle,
                1, /* Parameter marker; begin with 1, left to right */
                SQL_PARAM_INPUT, /* Input parameter */
                SQL_C_CHAR, /* C Parameter type */
                SQL_CHAR, /* SQL parameter type */
                0, /* Value size (not used here) */
                0, /* Digits to right of decimal (not used here) */
                (SQLPOINTER) & NewName, /* Pointer to data value */
                0, /* Value buffer size (0 is OK here.) */
                NULL); /* Value size indicator (not used.) */

        /* Now bind the second parameter. */
        return_code =
                SQLBindParameter(statement_handle, 2, SQL_PARAM_INPUT,
                SQL_C_CHAR, SQL_CHAR, 0, 0,
                (SQLPOINTER) & projectName, 0, NULL);
        
        /* Execute the SQL statement. */
        return_code = SQLExecute
                (statement_handle); /* Just give the statement handle. */
        
        /*Display the results of query execution */
	if (return_code == SQL_SUCCESS)
            display_project();
        else
            printf("The Query didn't Execute Successfully.\n");
            printf("Press Enter to continue\n");

	/* Close the cursor, so the query may be re-used. */
	  return_code = SQLCloseCursor(statement_handle);
        
	get_input(projectName, NewName, &terminate);

    }
}

void changePLocation() {


    SQLCHAR sql_statement[255];
    SQLCHAR ProjectLocation[30];
    SQLCHAR ProjectName[30];

    /*Define an SQL query to be executed */
    strcpy((char *) sql_statement,
            "UPDATE project set plocation= ? WHERE PName= ?");

    /* Compile the SQL statement. */
    return_code = SQLPrepare
            (statement_handle, /* Handle for the statement. */
            sql_statement, /* Pointer to the string containing the stmt. */
            SQL_NTS); /* String is null terminated. */

    get_Locationinput(ProjectLocation, ProjectName, &terminate);
    /* Bind the first parameter of the query. */

    while(!terminate)
    {
    return_code = SQLBindParameter
            (statement_handle,
            1, /* Parameter marker; begin with 1, left to right */
            SQL_PARAM_INPUT, /* Input parameter */
            SQL_C_CHAR, /* C Parameter type */
            SQL_CHAR, /* SQL parameter type */
            0, /* Value size (not used here) */
            0, /* Digits to right of decimal (not used here) */
            ProjectLocation, /* Pointer to data value */
            0, /* Value buffer size (0 is OK here.) */
            NULL); /* Value size indicator (not used.) */

    /* Now bind the second parameter. */
    return_code =
            SQLBindParameter(statement_handle, 2, SQL_PARAM_INPUT,
            SQL_C_CHAR, SQL_CHAR, 0, 0,
            ProjectName, 0, NULL);
    // printf("r3 %s", return_code);
    /* Execute the SQL statement. */
    return_code = SQLExecute
            (statement_handle); /* Just give the statement handle. */
    //printf("r4 %s", return_code);
    /*Display the results of query execution */

    if (return_code == SQL_SUCCESS)
            display_project();
        else
            printf("The Query didn't Execute Successfully.\n");
        printf("Press Enter to continue\n");
	
   /* Close the cursor, so the query may be re-used. */
	return_code = SQLCloseCursor(statement_handle);
       
	get_Locationinput(ProjectLocation, ProjectName, &terminate);

    }
}


void changeDName() {


    SQLCHAR sql_statement[255];
    SQLCHAR Dno[30];
    SQLCHAR pName[30];
    SQLCHAR Dname[30];
     
     get_DeptDisinput(Dname, Dno, &terminate);
    /*Define an SQL query to be executed */
    strcpy((char *) sql_statement,
            "UPDATE PROJECT \
                              SET   DNUM = ? \
                              WHERE  PNAME = ? " );

    /* Compile the SQL statement. */
    return_code = SQLPrepare
            (statement_handle, /* Handle for the statement. */
            sql_statement, /* Pointer to the string containing the stmt. */
            SQL_NTS); /* String is null terminated. */

    get_Deptinput(Dno, pName, &terminate);

    while(!terminate)
    {
    /* Bind the first parameter of the query. */
    return_code = SQLBindParameter
            (statement_handle,
            1, /* Parameter marker; begin with 1, left to right */
            SQL_PARAM_INPUT, /* Input parameter */
            SQL_C_CHAR, /* C Parameter type */
            SQL_CHAR, /* SQL parameter type */
            0, /* Value size (not used here) */
            0, /* Digits to right of decimal (not used here) */
            (SQLPOINTER) & Dno, /* Pointer to data value */
            0, /* Value buffer size (0 is OK here.) */
            NULL); /* Value size indicator (not used.) */

    /* Now bind the second parameter. */
    return_code =
            SQLBindParameter(statement_handle, 2, SQL_PARAM_INPUT,
            SQL_C_CHAR, SQL_CHAR, 0, 0,
            (SQLPOINTER) & pName, 0, NULL);
    
    /* Execute the SQL statement. */
    return_code = SQLExecute
            (statement_handle); /* Just give the statement handle. */
  
    /*Display the results of query execution */

    if (return_code == SQL_SUCCESS)
        printf("The Query  Executed Successfully.\n");
    else
        printf("The Query didn't Execute Successfully.\n");
       // printf("Press enter to continue\n");
    get_Deptinput(Dno, pName, &terminate);
    
    }

}



void addEmployee() {


    SQLCHAR sql_statement[255];
    SQLCHAR empId[30];
    SQLCHAR pNo[30];
    SQLCHAR hrs[30];

    /*Define an SQL query to be executed */
    strcpy((char *) sql_statement,
            "insert into works_on  values(?,?,null)");

    /* Compile the SQL statement. */
    return_code = SQLPrepare
            (statement_handle, /* Handle for the statement. */
            sql_statement, /* Pointer to the string containing the stmt. */
            SQL_NTS); /* String is null terminated. */

    get_empInput(empId, pNo,&terminate);
    while(!terminate)
    {
    /* Bind the first parameter of the query. */
    return_code = SQLBindParameter
            (statement_handle,
            1, /* Parameter marker; begin with 1, left to right */
            SQL_PARAM_INPUT, /* Input parameter */
            SQL_C_CHAR, /* C Parameter type */
            SQL_CHAR, /* SQL parameter type */
            0, /* Value size (not used here) */
            0, /* Digits to right of decimal (not used here) */
            (SQLPOINTER) & empId, /* Pointer to data value */
            0, /* Value buffer size (0 is OK here.) */
            NULL); /* Value size indicator (not used.) */

    /* Now bind the second parameter. */
    return_code =
            SQLBindParameter(statement_handle, 2, SQL_PARAM_INPUT,
            SQL_C_CHAR, SQL_CHAR, 0, 0,
            (SQLPOINTER) & pNo, 0, NULL);
    /* Now bind the third parameter. */
    return_code =
            SQLBindParameter(statement_handle, 3, SQL_PARAM_INPUT,
            SQL_C_CHAR, SQL_CHAR, 0, 0,
            (SQLPOINTER) & hrs, 0, NULL);
    
    /* Execute the SQL statement. */
    return_code = SQLExecute
            (statement_handle); /* Just give the statement handle. */
    
    /*Display the results of query execution */

    if (return_code == SQL_SUCCESS)
        printf("The Query Executed Successfully.\n");
    else
        printf("The Query didn't Execute Successfully.\n");
        printf("Press Enter to continue\n");
   
    /* Close the cursor, so the query may be re-used. */
	  return_code = SQLCloseCursor(statement_handle);
 
      get_empInput(empId, pNo,&terminate);
    }
}



void deleteEmp() {

	SQLCHAR sql_statement[255];
    	SQLCHAR Employee_SSN[30];
    	SQLINTEGER PNo[30];

                 get_EmpDelete(Employee_SSN, PNo, &terminate);


                   // delete statement1
    		      strcpy((char *) sql_statement, "DELETE FROM works_on where ESSN= ? and Pno=?" );    
        
                  /* Compile the SQL statement. */
                  return_code = SQLPrepare
            	  (statement_handle,            /* Handle for the statement. */
            	   sql_statement,         /* Pointer to the string containing the stmt. */
            	   SQL_NTS);                    /* String is null terminated. */     
      	   
            	  /* Now bind the first parameter. */
    	             return_code = 
       	             SQLBindParameter(statement_handle, 
                                               1, 
                                               SQL_PARAM_INPUT, 
                			       SQL_C_CHAR, 
                                               SQL_CHAR, 
                                               0, 
                                               0, 
                			       Employee_SSN, 
                                               0, 
                                               NULL);

                /* Now bind the second parameter. */
                    return_code = 
    	          SQLBindParameter(statement_handle,
            	     2,	   /* Parameter marker; begin with 1, left to right */
            	     SQL_PARAM_INPUT, /* Input parameter */
            	     SQL_C_LONG,	/* C Parameter type */
            	     SQL_INTEGER,	/* SQL parameter type */
            	     0,		/* Value size (not used here) */
            	     0,	   /* Digits to right of decimal (not used here) */
            	     (SQLPOINTER) & PNo, /* Pointer to data value */
            	     0,		/* Value buffer size (0 is OK here.) */
            	     NULL);
            	     
            	  /* Execute the SQL statement. */
            	  return_code = SQLExecute(statement_handle); /* Just give the statement handle. */

            	  /*Display the results of query execution */
            	  if (return_code == SQL_SUCCESS)
            	  {
                    printf("The Query Executed Successfully. \n");
                  }
            	  else
            	  {
            	    printf("The Query didn't Execute Successfully, as the Employee SSN or Project Number is incorrect. \n");
                  }

            	  /* Close the cursor, so the query may be re-used. */
            	  return_code = SQLCloseCursor(statement_handle);
            	  
            	  
            	  
            	  // delete statement2
    		      strcpy((char *) sql_statement, "DELETE FROM employee where ssn= ? " );  
          
                  /* Compile the SQL statement. */
                  return_code2 = SQLPrepare
            	  (statement_handle,            /* Handle for the statement. */
            	   sql_statement,         /* Pointer to the string containing the stmt. */
            	   SQL_NTS);                    /* String is null terminated. */     
      	   
            	  /* Now bind the first parameter. */
    	             return_code2 = 
       	             SQLBindParameter(statement_handle, 
                                               1, 
                                               SQL_PARAM_INPUT, 
                			       SQL_C_CHAR, 
                                               SQL_CHAR, 
                                               0, 
                                               0, 
                			       Employee_SSN, 
                                               0, 
                                               NULL); 

            	  /* Execute the SQL statement. */
            	  return_code2 = SQLExecute(statement_handle); /* Just give the statement handle. */

            	  /*Display the results of query execution */
            	  if (return_code2 = SQL_SUCCESS)
            	  {
                    //printf("          Message: *** Message:Query2 Executed Successfully.***\n");
                  }
            	  else
            	  {
            	    //printf("          Message: *** The program should not make modifications to the instances of the Employee Relation.***\n");
                  }

            	  /* Close the cursor, so the query may be re-used. */
            	  return_code2 = SQLCloseCursor(statement_handle);
            	  
            	  return_code = SQLCloseCursor(statement_handle);      
                        
            	  
    		      
                }





void changeHrs() {


    SQLCHAR sql_statement[255];
    SQLCHAR fName[30];
    SQLCHAR hrs[30];
    SQLCHAR pName[30];
    SQLCHAR ssn[30];
    SQLCHAR fname[30];

    get_EmpDisinput(fname, ssn, &terminate);
    /*Define an SQL query to be executed */
    strcpy((char *) sql_statement,
            " UPDATE works_on SET hours = ? WHERE essn = (SELECT ssn FROM employee WHERE fname = ?)and pno = (SELECT pnumber FROM project WHERE pname = ?)");

    /* Compile the SQL statement. */
    return_code = SQLPrepare
            (statement_handle, /* Handle for the statement. */
            sql_statement, /* Pointer to the string containing the stmt. */
            SQL_NTS); /* String is null terminated. */

    get_hrsInput(fName, hrs,pName,&terminate);
    return_code = 0;
    while(!terminate)
    {
    /* Bind the first parameter of the query. */
    return_code = SQLBindParameter
            (statement_handle,
            1, /* Parameter marker; begin with 1, left to right */
            SQL_PARAM_INPUT, /* Input parameter */
            SQL_C_CHAR, /* C Parameter type */
            SQL_CHAR, /* SQL parameter type */
            0, /* Value size (not used here) */
            0, /* Digits to right of decimal (not used here) */
            (SQLPOINTER)& hrs, /* Pointer to data value */
            0, /* Value buffer size (0 is OK here.) */
            NULL); /* Value size indicator (not used.) */
   
    /* Now bind the second parameter. */
    return_code =
            SQLBindParameter(statement_handle, 2, SQL_PARAM_INPUT,
            SQL_C_CHAR, SQL_CHAR, 0, 0,
            (SQLPOINTER)& fName, 0, NULL);
   
    /* Now bind the third parameter. */
    return_code =
            SQLBindParameter(statement_handle, 3, SQL_PARAM_INPUT,
            SQL_C_CHAR, SQL_CHAR, 0, 0,
            (SQLPOINTER)& pName, 0, NULL);
   
    /* Execute the SQL statement. */
    return_code = SQLExecute
            (statement_handle); /* Just give the statement handle. */
    
    /*Display the results of query execution */

    if (return_code == SQL_SUCCESS)
        printf("The Query Executed Successfully.\n");
    else
        printf("The Query didn't Execute Successfully.\n");
        printf("Press Enter to Continue\n");

     /* Close the cursor, so the query may be re-used. */
      return_code = SQLCloseCursor(statement_handle);        

        get_hrsInput(fName, hrs,pName,&terminate);
    }
}



void displayProjectSummary() {


    SQLCHAR sql_statement[255];
    SQLCHAR pName[30];
    
    /*Define an SQL query to be executed */
    strcpy((char *) sql_statement,
            "SELECT works_on.ESSN as Employee_SSN,employee.fname as Employee_FirstName,employee.minit as Employee_MiddleName,employee.lname as Employee_LastName,works_on.hours as EmployeeHours_Project,works_on.PNo as Project_Number,Project.PName as Project_Name,Project.PLocation as Project_Location,Project.DNum as Project_DepartmentNumber \
                  FROM ((works_on JOIN employee ON(works_on.ESSN=employee.SSN)) JOIN Project ON(works_on.PNo=Project.PNumber))\
                  WHERE Project.PName= ? " );

    /* Compile the SQL statement. */
    return_code = SQLPrepare
            (statement_handle, /* Handle for the statement. */
            sql_statement, /* Pointer to the string containing the stmt. */
            SQL_NTS); /* String is null terminated. */

    get_summaryInput(pName,&terminate);
    
    /* Bind the first parameter of the query. */
    while(!terminate)
    {
    return_code = SQLBindParameter
            (statement_handle,
            1, /* Parameter marker; begin with 1, left to right */
            SQL_PARAM_INPUT, /* Input parameter */
            SQL_C_CHAR, /* C Parameter type */
            SQL_CHAR, /* SQL parameter type */
            0, /* Value size (not used here) */
            0, /* Digits to right of decimal (not used here) */
            (SQLPOINTER) & pName, /* Pointer to data value */
            0, /* Value buffer size (0 is OK here.) */
            NULL); /* Value size indicator (not used.) */

    return_code = SQLExecute
            (statement_handle); /* Just give the statement handle. */
      //printf("The value of return code %d\n", return_code);
    /*Display the results of query execution */

    if (return_code == SQL_SUCCESS) {
        printf("The Query Executed Successfully.\n");
        display_Summary_result();
    } else
        printf("The Query didn't Execute Successfully.\n");
      /* Close the cursor, so the query may be re-used. */
	  return_code = SQLCloseCursor(statement_handle);
    get_summaryInput(pName,&terminate);
    }
}




void displayovertimeemployees() {


    SQLCHAR sql_statement[255];


    /*Define an SQL query to be executed */
     strcpy((char *) sql_statement, 
    	     "SELECT works_on.ESSN,employee.fname,employee.minit,employee.lname,SUM(works_on.Hours) AS Total_hours \
                     FROM (works_on JOIN employee ON(works_on.ESSN=employee.SSN))\
                     GROUP BY works_on.ESSN,employee.fname,employee.minit,employee.lname\
                     HAVING SUM(works_on.Hours) > 40; " );



    /* Compile the SQL statement. */
    return_code = SQLPrepare
            (statement_handle, /* Handle for the statement. */
            sql_statement, /* Pointer to the string containing the stmt. */
            SQL_NTS); /* String is null terminated. */

    /* Execute the SQL statement. */
    return_code = SQLExecute
            (statement_handle); /* Just give the statement handle. */

    /*Display the results of query execution */

    if (return_code == SQL_SUCCESS) {
        printf("The Query Executed Successfully.\n");
        display_otEmployees();
    } else
        printf("The Query didn't Execute Successfully.\n");

    /* Close the cursor, so the query may be re-used. */
      return_code = SQLCloseCursor(statement_handle);        

}










/**************************************************************************/
/**************************************************************************/



/* Function which prompts the user for input */
RETCODE get_input(SQLCHAR * projectName,
        SQLCHAR * NewName, c_boolean * quit) {

     getchar(); /* Gobble up the newline. */
     ECHO_OFF
    /* Now get the user ID and password using basic I/O. */
    p = 0;
    printf("Enter the Name of a Project   (or)  Enter a to Abort\n");

    while ((c = getchar()) != '\n') {
        if (c == '\b' || c == DEL) {
            if (p != 0) {
                p--;
                putchar('\b');
                putchar(' ');
                putchar('\b');
            }
        } else {
            projectName[p++] = c;
            putchar(c);
        }
    }

    if (projectName[0]=='a') {
        *quit = TRUE;
        printf("\nThe Program Aborted, want to continue-RUN AGIAN\n");
    }

     else {
         projectName[p] = 0;
         p = 0;
        printf("\nEnter the New Name for the Project:\n ");
        while ((c = getchar()) != '\n') {
            if (c == '\b' || c == DEL) {
                if (p != 0) {
                    p--;
                    putchar('\b');
                    putchar(' ');
                    putchar('\b');
                }
            } else {
                NewName[p++] = c;
                putchar(c);
            }
        }
        *quit = FALSE;
        NewName[p] = 0;
    }

    ECHO_ON
    printf("\r\n");

    return 0;

}



/* Function which prompts the user for input */
RETCODE get_Locationinput(SQLCHAR * ProjectLocation,
        SQLCHAR * ProjectName, c_boolean * quit) {
    
     SQLCHAR sql_statement2[255];
     SQLCHAR dLocation[50];
     int i = 0;
     getchar(); /* Gobble up the newline. */
     ECHO_OFF
    /* Now get the user ID and password using basic I/O. */
    p = 0;
    printf("Enter the Project Name  (or)  Enter a to Abort\n");

    while ((c = getchar()) != '\n') {
        if (c == '\b' || c == DEL) {
            if (p != 0) {
                p--;
                putchar('\b');
                putchar(' ');
                putchar('\b');
            }
        } else {
            ProjectName[p++] = c;
            putchar(c);
        }
    }

    if (ProjectName[0]=='a') {
        *quit = TRUE;
        printf("\nThe Program Aborted, want to continue-RUN AGIAN\n");
    }

     else {
         ProjectLocation[p] = 0;
         p = 0;
        printf("\nEnter the new Project Location:\n ");
        while ((c = getchar()) != '\n') {
            if (c == '\b' || c == DEL) {
                if (p != 0) {
                    p--;
                    putchar('\b');
                    putchar(' ');
                    putchar('\b');
                }
            } else {
                ProjectLocation[p++] = c;
                putchar(c);
            }
        }
        *quit = FALSE;
        ProjectLocation[p] = 0;
    
    /* Uncomment this during testing to see whether the password get worked. */

     ECHO_ON
     printf("\r\n");
     strcpy((char *) sql_statement2,
            " SELECT dlocation FROM dept_locations WHERE dlocation = ? ");
    
     /* Compile the SQL statement. */
        return_code = SQLPrepare(
    	      statement_handle2,         /* Handle for the statement. */
    	      sql_statement2,    /* Pointer to the string containing the stmt. */
    	      SQL_NTS);                 /* String is null terminated. */
    	      
    	return_code = SQLBindParameter(statement_handle2,
                  1,  /* Parameter marker; begin with 1, left to right */
                  SQL_PARAM_INPUT, /* Input parameter */
                  SQL_C_CHAR,	/* C Parameter type */
                  SQL_CHAR,	/* SQL parameter type */
                  0,		/* Value size (not used here) */
                  0,	   /* Digits to right of decimal (not used here) */
                        	     //(SQLPOINTER) &ProjectNewName, /* Pointer to data value */
                  ProjectLocation, /* Pointer to data value */
                  0,		/* Value buffer size (0 is OK here.) */
                  NULL);		/* Value size indicator (not used.) */
    	      
      /* Execute the SQL statement. */
           return_code = SQLExecute
	         (statement_handle2); /* Just give the statement handle. */

      /*Display the results of query execution */
             if (return_code == SQL_SUCCESS)
             {
	              return_code = SQLFetch(statement_handle2);
                  if (return_code == SQL_NO_DATA_FOUND)
                  {
                         printf("The location of a project must be one of the locations of the department which controls the project.\n");
                         get_Locationinput(ProjectLocation, ProjectName, &terminate);
                         *quit = TRUE;

                  } else 
                    {
                        *quit = FALSE;
                    }

	     }
             else
             {
	             printf("The Query didn't Execute Successfully\n");
             }
              return_code = SQLCloseCursor(statement_handle);
             
            
  	}

    return 0;
}



/* Function which prompts the user for input */
RETCODE get_Deptinput(SQLCHAR * Dno,
        SQLCHAR * pName, c_boolean * quit) 
    {



    printf("Enter the Departmet Number: (or) Enter a to Abort\n");
    scanf("%s", Dno);
    if (Dno[0]=='a') {
        *quit = TRUE;
        printf("\nThe Program Aborted, want to continue-RUN AGIAN\n");
    } else {

        printf("Enter the Project Name:\n ");
        *quit = FALSE;
        scanf("%s", pName);

    }

    
    return 0;

   }




RETCODE get_DeptDisinput(SQLCHAR *Dname, 
	SQLCHAR *Dno, c_boolean *quit)
	{

            SQLCHAR sql_statement[255];
  	
               
        
              /*Define an SQL query to be executed */
               strcpy((char *) sql_statement, 
    	          "SELECT DName, DNumber FROM Department");

    	      /* Compile the SQL statement. */
              return_code = SQLPrepare(
    	      statement_handle,         /* Handle for the statement. */
    	      sql_statement,    /* Pointer to the string containing the stmt. */
    	      SQL_NTS);                 /* String is null terminated. */
    	      
    	      /* Execute the SQL statement. */
              return_code = SQLExecute
	         (statement_handle); /* Just give the statement handle. */

              /*Display the results of query execution */
              if (return_code == SQL_SUCCESS)
              {
	            display_Dept_table();
              }
              else
              { 
	             printf("          The Query didn't Execute, as the department data is not available.\n");
              }
              return_code = SQLCloseCursor(statement_handle);
             
      }





/* Function which prompts the user for input */
RETCODE get_empInput(SQLCHAR * empId,
        SQLCHAR * pNo, c_boolean * quit) {

    SQLCHAR pname[50];
    SQLCHAR pnumber[50];
    SQLCHAR sql_statement2[255];




	 strcpy((char *) sql_statement2,
		    "Select pname,pnumber from  project");
	    return_code =
		    SQLAllocHandle
		    (SQL_HANDLE_STMT, /* Statement handle. */
		    connection_handle, /* Input handle is the connection handle. */
		    &statement_handle2); /* Pointer to the output handle. */
	    return_code = SQLPrepare
		    (statement_handle2, /* Handle for the statement. */
		    sql_statement2, /* Pointer to the string containing the stmt. */
		    SQL_NTS);


	    return_code = SQLExecute
		    (statement_handle2); /* Just give the statement handle. */
	    return_code =
		    SQLBindCol(statement_handle2, 1, SQL_C_CHAR,
		    (SQLPOINTER) pname, sizeof (pname), NULL);
	    return_code =
		    SQLBindCol(statement_handle2, 2, SQL_C_CHAR,
		    (SQLPOINTER) pnumber, sizeof (pnumber), NULL);

	    /* Fetchhandle and display the rows of the answer to the query. */
	    return_code = SQLFetch(statement_handle2);
	    if (return_code == SQL_NO_DATA_FOUND)
		printf("No data match the given Query.\n");
	    else{
		  printf(" PROJECT NAME\t\t\t PROJECT NUMBER \n\n");
		  printf("--------------------------------------------------\n");
	    }

		while (return_code != SQL_NO_DATA_FOUND) {

		    
		    printf("%s \t\t\t%s\n",pname, pnumber);
		   
		    return_code = SQLFetch(statement_handle2);
		};
	    printf("\n");





     getchar(); /* Gobble up the newline. */
     ECHO_OFF
   
    p = 0;
     printf("Enter the Employee ID: or Enter a to Abort\n");

    while ((c = getchar()) != '\n') {
        if (c == '\b' || c == DEL) {
            if (p != 0) {
                p--;
                putchar('\b');
                putchar(' ');
                putchar('\b');
            }
        } else {
            empId[p++] = c;
            putchar(c);
        }
    }
    
    if (empId[0]=='a') {
        *quit = TRUE;
        printf("\nThe Program Aborted, want to continue-RUN AGIAN\n");
    }

     else {
         empId[p] = 0;
         p = 0;
        printf("\n Enter the Project number:\n ");
        while ((c = getchar()) != '\n') {
            if (c == '\b' || c == DEL) {
                if (p != 0) {
                    p--;
                    putchar('\b');
                    putchar(' ');
                    putchar('\b');
                }
            } else {
                pNo[p++] = c;
                putchar(c);
            }
        }
        *quit = FALSE;
        pNo[p] = 0;
    
    /* Uncomment this during testing to see whether the password get worked. */
    ECHO_ON
    printf("\r\n");


   

     }
    return 0;
}









/* Function which prompts the user for input */
RETCODE get_EmpDelete(SQLCHAR  *Employee_SSN , 
                  SQLINTEGER  * PNo,
		  c_boolean   * quit)
{

    SQLCHAR pname[50];
    SQLCHAR pnumber[50];
    SQLCHAR sql_statement2[255];


      /*Define an SQL query to be executed */
	 strcpy((char *) sql_statement2,
		"Select pname,pnumber from  project");

     /* Allocate a connection handle for the database. */
	 return_code =
		    SQLAllocHandle
		    (SQL_HANDLE_STMT, /* Statement handle. */
		    connection_handle, /* Input handle is the connection handle. */
		    &statement_handle2); /* Pointer to the output handle. */

     /*Compile the SQL statement. */
	 return_code = SQLPrepare
		    (statement_handle2, /* Handle for the statement. */
		    sql_statement2, /* Pointer to the string containing the stmt. */
		    SQL_NTS);

     /* Execute the SQL statement. */
	 return_code = SQLExecute
		    (statement_handle2); /* Just give the statement handle. */

     /* Bind the query results to the above string arrays. */
	    return_code =
		    SQLBindCol(statement_handle2, 1, SQL_C_CHAR,
		    (SQLPOINTER) pname, sizeof (pname), NULL);
	    return_code =
		    SQLBindCol(statement_handle2, 2, SQL_C_CHAR,
		    (SQLPOINTER) pnumber, sizeof (pnumber), NULL);

     /* Fetchhandle and display the rows of the answer to the query. */
	    return_code = SQLFetch(statement_handle2);

	    if (return_code == SQL_NO_DATA_FOUND)
		printf("No data match the given Query.\n");
	    else{
		  printf("--------------------------------------------------\n");
		  printf(" PROJECT NAME\t\t\t PROJECT NUMBER \n");
		  printf("--------------------------------------------------\n");
	    }

		while (return_code != SQL_NO_DATA_FOUND) {

		    
		    printf("%s \t\t\t%s\n",pname, pnumber);
		   
		    return_code = SQLFetch(statement_handle2);
		};
	    printf("\n");




     printf("Enter the Employee ID: (or)  Enter a to abort \n");
     scanf("%s", Employee_SSN);

    if (Employee_SSN[0] == 'a') {
        *quit = TRUE;
        printf("The Program Aborted, want to continue-RUN AGIAN\n");
    } else {
          printf("Enter the Project Number:   \n");
          scanf("%d",PNo);       

       
        }
    

 return 0;
}





/* Function which prompts the user for input */
RETCODE get_hrsInput(SQLCHAR * eName, SQLCHAR * hrs, SQLCHAR * pName, c_boolean * quit) {

    int i = 0;
    int j, k = 0;

    printf("Enter the Name of a Employee  (or)  Enter a to Abort\n");
    scanf("%s", eName);

    if (eName[0] == 'a') {
        *quit = TRUE;
        printf("The Program Aborted, want to continue-RUN AGIAN\n");
    } else {
       
        printf("Enter the Project Name, that you want to change the hours of work\n");
        scanf("%s", pName);
        printf("Enter the New  hours of work to the Employee:\n");
        scanf("%s", hrs);
        i = atoi(hrs);
        if (i <= 0) {
            printf("Alphabets, Zeros and Negative values are not allowed\n");
            get_hrsInput(eName, hrs, pName, &terminate);	    
            *quit = TRUE;	
        }else {
            *quit = FALSE;
        }

        SQLCHAR sql_statement1[255];
        SQLCHAR tot[30];
        /*Define an SQL query to be executed */
        strcpy((char *) sql_statement1,
                " SELECT sum(hours) as tot FROM works_on WHERE essn = (SELECT ssn FROM employee WHERE fname =? )");
        return_code =
                SQLAllocHandle
                (SQL_HANDLE_STMT, /* Statement handle. */
                connection_handle, /* Input handle is the connection handle. */
                &statement_handle1); /* Pointer to the output handle. */
        /* Compile the SQL statement. */
        return_code = SQLPrepare
                (statement_handle1, /* Handle for the statement. */
                sql_statement1, /* Pointer to the string containing the stmt. */
                SQL_NTS); /* String is null terminated. */


        /* Bind the first parameter of the query. */
        return_code = SQLBindParameter
                (statement_handle1,
                1, /* Parameter marker; begin with 1, left to right */
                SQL_PARAM_INPUT, /* Input parameter */
                SQL_C_CHAR, /* C Parameter type */
                SQL_CHAR, /* SQL parameter type */
                0, /* Value size (not used here) */
                0, /* Digits to right of decimal (not used here) */
                (SQLPOINTER) eName, /* Pointer to data value */
                0, /* Value buffer size (0 is OK here.) */
                NULL); /* Value size indicator (not used.) */

        return_code = SQLExecute
                (statement_handle1); /* Just give the statement handle. */
        //printf("r4 %s", return_code);
        /*Display the results of query execution */

        return_code =
                SQLBindCol(statement_handle1, 1, SQL_CHAR,
                (SQLPOINTER) tot, sizeof (tot), NULL);
        /* Fetch and display the rows of the answer to the query. */
        return_code = SQLFetch(statement_handle1);
        if (return_code == SQL_NO_DATA_FOUND)
            printf("No data match the given Query.\n");
        else {
            //printf("current total hours:");
            while (return_code != SQL_NO_DATA_FOUND) {

               return_code = SQLFetch(statement_handle1);

            }

            printf("\n");
        }

        j = atoi(tot);
        k = atoi(hrs);

        if (j + k > 80) {
            printf("The Total number of hours is %d :The Time Exceeds 80, SO TRY AGAIN \n ", j + k);
            get_hrsInput(eName, hrs, pName, &terminate);
            *quit = TRUE;

        } else {
            *quit = FALSE;
        }

    }

    return 0;
}

RETCODE get_EmpDisinput(SQLCHAR *fname, 
	SQLCHAR *ssn, c_boolean *quit)
	{

            SQLCHAR sql_statement[255];
  	
               
        
              /*Define an SQL query to be executed */
               strcpy((char *) sql_statement, 
    	          "SELECT fname, ssn FROM Employee");

    	      /* Compile the SQL statement. */
              return_code = SQLPrepare(
    	      statement_handle,         /* Handle for the statement. */
    	      sql_statement,    /* Pointer to the string containing the stmt. */
    	      SQL_NTS);                 /* String is null terminated. */
    	      
    	      /* Execute the SQL statement. */
              return_code = SQLExecute
	         (statement_handle); /* Just give the statement handle. */

              /*Display the results of query execution */
              if (return_code == SQL_SUCCESS)
              {
	            display_Emp_table();
              }
              else
              { 
	             printf("          The Query didn't Execute, as the department data is not available.\n");
              }
              return_code = SQLCloseCursor(statement_handle);
             
      }



/* Function which prompts the user for input */
RETCODE get_summaryInput(SQLCHAR * pName, c_boolean * quit) 
{

    printf("Enter the Project Name  (or)  Enter a to Abort\n");
    scanf("%s", pName);
    
    if (pName[0]=='a') {
        *quit = TRUE;
        printf("\nThe Program Aborted, want to continue-RUN AGIAN\n");
    } else {

        *quit = FALSE;

    }


    return 0;
}





/**************************************************************************/
/**************************************************************************/


/* Function which displays the results of the query */
RETCODE display_Dept_table(void)
{
  RETCODE       return_code;

  /* Storage for query results.
     Note the extra position for the null terminator of a string.
     */
  SQLCHAR       Dname[50];  
  SQLINTEGER    Dno;
	
  /* Bind the query results to the above string arrays. */
  /* First for column 1. */
  return_code = 
    SQLBindCol
    (statement_handle,      /* handle variable */
     1,	                    /* column in result */
     SQL_C_CHAR,            /* data type of column */
     (SQLPOINTER)Dname,  /* pointer to result location */
     sizeof(Dname),      /* size of result location */
     NULL);                 /* location to store data size (Not used here.)*/

  /* Next for columns 2, 3, 4, and 5. */
  return_code =
    SQLBindCol(statement_handle, 
               2, 
               SQL_C_SLONG,
               (SQLPOINTER)&Dno, 
               sizeof(Dno), 
               NULL);
  
 
	
  /* Fetch and display the rows of the answer to the query. */
  return_code = SQLFetch(statement_handle);
  if (return_code == SQL_NO_DATA_FOUND)
    {
    printf("       No data match the given Query.\n");
    }
  else
      {
    printf("    ----------------------------------------------------\n");
    printf("    |      Department Name   |    Department Number    | \n");
    printf("    -------------------------+--------------------------\n");
	while (return_code != SQL_NO_DATA_FOUND)
    {
	       printf("    |      %s          |        %d              | \n", Dname, Dno);  
      
	       return_code = SQLFetch(statement_handle);
    };
    printf("    -------------------------+--------------------------\n\n");
    printf(" If the Department is not in the above mentioned list, enter any number not available in the list  \n\n");
   }
  printf("\n");

  /* Return the ODBC API return code to the calling function */
  return(return_code);
}



/* Function which displays the results of the query */
RETCODE display_Emp_table(void)
{
  RETCODE       return_code;

  /* Storage for query results.
     Note the extra position for the null terminator of a string.
     */
  SQLCHAR       fname[50];  
  SQLINTEGER    ssn;
	
  /* Bind the query results to the above string arrays. */
  /* First for column 1. */
  return_code = 
    SQLBindCol
    (statement_handle,      /* handle variable */
     1,	                    /* column in result */
     SQL_C_CHAR,            /* data type of column */
     (SQLPOINTER)fname,  /* pointer to result location */
     sizeof(fname),      /* size of result location */
     NULL);                 /* location to store data size (Not used here.)*/

  /* Next for columns 2, 3, 4, and 5. */
  return_code =
    SQLBindCol(statement_handle, 
               2, 
               SQL_C_SLONG,
               (SQLPOINTER)&ssn, 
               sizeof(ssn), 
               NULL);
  
 
	
  /* Fetch and display the rows of the answer to the query. */
  return_code = SQLFetch(statement_handle);
  if (return_code == SQL_NO_DATA_FOUND)
    {
    printf("       No data match the given Query.\n");
    }
  else
      {
    printf("    ----------------------------------------------------\n");
    printf("    |      Employee Name     |        Employee ID      | \n");
    printf("    -------------------------+--------------------------\n");
	while (return_code != SQL_NO_DATA_FOUND)
    {
	       printf("          %s                     %d               \n", fname, ssn);  
      
	       return_code = SQLFetch(statement_handle);
    };
    printf("    -------------------------+--------------------------\n");  
   }
  printf("\n");

  /* Return the ODBC API return code to the calling function */
  return(return_code);
}







/* Function which displays the results of the query */
RETCODE display_project(void) {
    RETCODE return_code;

    /* Storage for query results.
       Note the extra position for the null terminator of a string.
     */
    SQLCHAR PName[50];
    SQLCHAR PLocation[50];
    SQLCHAR sql_statement1[255];
    strcpy((char *) sql_statement1,
            "Select pname,plocation from project");

    return_code =
            SQLAllocHandle
            (SQL_HANDLE_STMT, /* Statement handle. */
            connection_handle, /* Input handle is the connection handle. */
            &statement_handle1); /* Pointer to the output handle. */
    return_code = SQLPrepare
            (statement_handle1, /* Handle for the statement. */
            sql_statement1, /* Pointer to the string containing the stmt. */
            SQL_NTS);


    return_code = SQLExecute
            (statement_handle1); /* Just give the statement handle. */
    return_code =
            SQLBindCol(statement_handle1, 1, SQL_C_CHAR,
            (SQLPOINTER) PName, sizeof (PName), NULL);
    return_code =
            SQLBindCol(statement_handle1, 2, SQL_C_CHAR,
            (SQLPOINTER) PLocation, sizeof (PLocation), NULL);

    /* Fetch and display the rows of the answer to the query. */
    return_code = SQLFetch(statement_handle1);
    if (return_code == SQL_NO_DATA_FOUND)
        printf("No data match the given Query.\n");
    else{
         printf("ProjectName\t\t\t ProjectLocation:\n\n");
        
	while (return_code != SQL_NO_DATA_FOUND) {

               printf("%s\t\t %s\n",PName, PLocation);
            
	       return_code = SQLFetch(statement_handle1);
        };
    printf("\n");
    }
    /* Return the ODBC API return code to the calling function */
    return (return_code);
}




 /* Function which displays the results of the query */
RETCODE display_Summary_result(void) {
    RETCODE return_code;

 SQLCHAR       ESSN[50];  
  SQLCHAR       fname[50];  
  SQLCHAR       minit[50];  
  SQLCHAR       lname[50];   
  SQLINTEGER    hours;
  SQLINTEGER    PNo;
  SQLCHAR       PName[50];   
  SQLCHAR       PLocation[50];   
  SQLINTEGER    DNum;
  
  SQLINTEGER    hours_type;
  char		    *Super_ID;
  char		    *indic;
  char		Big_Boss[] = "Unknown value!";
  char		indicator_y[] = "Y";
  char		indicator_n[] = "N";
  // ESSN,fname,minit,lname,hours,PNo,PName,PLocation,DNum
  /* Bind the query results to the above string arrays. */
  
  printf("                                                                          #####Summary Report for the requested Project#####                                                               \n");
  
  
  return_code = SQLBindCol(statement_handle, 1, SQL_C_CHAR,(SQLPOINTER)&ESSN, sizeof(ESSN), NULL);
  return_code = SQLBindCol(statement_handle, 2, SQL_C_CHAR,(SQLPOINTER)&fname, sizeof(fname), NULL);
  return_code = SQLBindCol(statement_handle, 3, SQL_C_CHAR,(SQLPOINTER)&minit, sizeof(minit), NULL);
  return_code = SQLBindCol(statement_handle, 4, SQL_C_CHAR,(SQLPOINTER)&lname, sizeof(lname), NULL);
  return_code = SQLBindCol(statement_handle, 5, SQL_C_SLONG,(SQLPOINTER)&hours, sizeof(hours), (SQLPOINTER)&hours_type);
  return_code = SQLBindCol(statement_handle, 6, SQL_C_SLONG,(SQLPOINTER)&PNo, sizeof(PNo), NULL);
  return_code = SQLBindCol(statement_handle, 7, SQL_C_CHAR,(SQLPOINTER)&PName, sizeof(PName), NULL);
  return_code = SQLBindCol(statement_handle, 8, SQL_C_CHAR,(SQLPOINTER)&PLocation, sizeof(PLocation), NULL);
  return_code = SQLBindCol(statement_handle, 9, SQL_C_SLONG,(SQLPOINTER)&DNum, sizeof(DNum), NULL);
  
  //printf("Summary data:\n\n");
	
  /* Fetch and display the rows of the answer to the query. */
  return_code = SQLFetch(statement_handle);
  if (return_code == SQL_NO_DATA_FOUND)
    {
    printf("          No project data available for the given project name.\n");
    }
  else
      {
   printf("          -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n");
    printf("           Project Name\t\tProject Number\t\tProject Location\t\tProject Department Number\t\tEmployee  SSN\t\tFirstName\t\tMiddleName\t\tLastName\t\tHours\t\tNULL Hours Indicator \n");
    
    printf("          -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n");

	while (return_code != SQL_NO_DATA_FOUND)
    {
           if (hours_type == SQL_NULL_DATA) /* Hours is NULL */
           {
		      Super_ID = Big_Boss;
		      indic= indicator_y;
		      printf("             %s\t\t\t%d\t\t\t%s\t\t\t\t%d\t\t\t%s\t\t%s\t\t\t%s\t\t%s\t\t%s\t\t%s \n", PName,PNo,PLocation,DNum,ESSN,fname,minit,lname,Super_ID,indic);
           }
           else
           {
              indic= indicator_n;
	          printf("             %s\t\t\t%d\t\t\t%s\t\t\t\t%d\t\t\t%s\t\t%s\t\t\t%s\t\t%s\t\t%d\t\t%s \n", PName,PNo,PLocation,DNum,ESSN,fname,minit,lname,hours,indic);
           }   
	       return_code = SQLFetch(statement_handle);
    };
    
   }
  printf("\n");

  /* Return the ODBC API return code to the calling function */
  return(return_code);
}




/* Function which displays the results of the query */
RETCODE display_otEmployees(void) {
    RETCODE return_code;

  
  SQLCHAR       ESSN[50];  
  SQLCHAR       fname[50];  
  SQLCHAR       minit[50];  
  SQLCHAR       lname[50];   
  SQLINTEGER    hours;
    
  SQLINTEGER    hours_type;
  char		*NO_HOUR;
  char		*indic;
  char		S_choice[] = "Unknown";
  char		indicator_y[] = "Y";
  char		indicator_n[] = "N";
  
  /* Bind the query results to the above string arrays. */
  
  printf("      ##########LIST OF ALL Overtime Employees##########\n");
  printf("      --------------------------------------------------\n");
  
  return_code = SQLBindCol(statement_handle, 1, SQL_C_CHAR,(SQLPOINTER)&ESSN, sizeof(ESSN), NULL);
  return_code = SQLBindCol(statement_handle, 2, SQL_C_CHAR,(SQLPOINTER)&fname, sizeof(fname), NULL);
  return_code = SQLBindCol(statement_handle, 3, SQL_C_CHAR,(SQLPOINTER)&minit, sizeof(minit), NULL);
  return_code = SQLBindCol(statement_handle, 4, SQL_C_CHAR,(SQLPOINTER)&lname, sizeof(lname), NULL);
  return_code = SQLBindCol(statement_handle, 5, SQL_C_SLONG,(SQLPOINTER)&hours, sizeof(hours), (SQLPOINTER)&hours_type);
    
  
	
  /* Fetch and display the rows of the answer to the query. */
  return_code = SQLFetch(statement_handle);
  if (return_code == SQL_NO_DATA_FOUND)
    {
    printf("          No Overtime Employees found with hours > 40 \n");
    }
  else
      {
    printf("          Employee SSN\t\t  Employee Firstname\t\t  Employee Middlename\t\t  Employee Lastname\t\t  Employee Hours \n");
                                     
   
	while (return_code != SQL_NO_DATA_FOUND)
    {
           if (hours_type == SQL_NULL_DATA) /* Hours is NULL */
           {
		  NO_HOUR = S_choice;
		  indic= indicator_y;
		  printf("           %s\t    %s\t\t   %s\t\t   %s\t\t   %s \n", ESSN,fname,minit,lname,NO_HOUR);
           }
           else
           {
                  indic= indicator_n;
	          printf("           %s\t     %s\t\t   %s\t\t   %s\t\t   %d \n", ESSN,fname,minit,lname,hours);
           }   
	       return_code = SQLFetch(statement_handle);
    };
    
   }
  printf("\n");

  /* Return the ODBC API return code to the calling function */
  return(return_code);
}





RETCODE display_results_for_delete(void)
{
  RETCODE       return_code;

  /* Storage for query results.
     Note the extra position for the null terminator of a string.
     */
  SQLCHAR       ESSN[50];  
  SQLINTEGER    PNo;
  SQLCHAR       Pname[50];  
	
  /* Bind the query results to the above string arrays. */
  /* First for column 1. */
  return_code = 
    SQLBindCol
    (statement_handle,      /* handle variable */
     1,	                    /* column in result */
     SQL_C_CHAR,            /* data type of column */
     (SQLPOINTER)ESSN,  /* pointer to result location */
     sizeof(ESSN),      /* size of result location */
     NULL);                 /* location to store data size (Not used here.)*/

  /* Next for columns 2, 3*/
  return_code =
    SQLBindCol(statement_handle, 
               2, 
               SQL_C_SLONG,
               (SQLPOINTER)&PNo, 
               sizeof(PNo), 
               NULL);
               
    return_code = 
    SQLBindCol
    (statement_handle,      /* handle variable */
     3,	                    /* column in result */
     SQL_C_CHAR,            /* data type of column */
     (SQLPOINTER)Pname,  /* pointer to result location */
     sizeof(Pname),      /* size of result location */
     NULL);        
  
  printf("Employee-Project data:\n\n");
	
  /* Fetch and display the rows of the answer to the query. */
  return_code = SQLFetch(statement_handle);
  if (return_code == SQL_NO_DATA_FOUND)
    {
    printf("No data match for the given input.\n");
    }
  else
      {
    printf("        ----------------------------------------------------------\n");
    printf("        | Employee SSN   |    Project Number   |    Project Name | \n");
    printf("        -----------------+---------------------+------------------\n");
	while (return_code != SQL_NO_DATA_FOUND)
    {
	       printf("  |        %s         |         %d      |       %s       |\n", ESSN, PNo, Pname);      
	       return_code = SQLFetch(statement_handle);
    };
   }
  printf("\n");

  /* Return the ODBC API return code to the calling function */
  return(return_code);
}














