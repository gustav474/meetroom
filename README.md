## MeetRoom Application

#### Requirements
Checked for correctly works with Java 8    

#### Description
This application will allow you to book a meeting room, view its status by week
    
* Main features:
    * to book a meeting room for a period from 30 minutes to 1 week
    * сreate a user and book a meeting room on his behalf via log in
    * events do not overlap      
    * possibility to view the status of a room week by week
        
#### How to install         

1. Create a database who would contain information about events and users
    - First of all u need to check that postgress installed on your computer. 
    If it is true, open your terminal and type (for enter by local user):
    ``` bash
    psql postgres
    ```
            
    ```bash
    create database meetroom;
   ```
   
    - Check thats your new database is exist
    ```bash 
    postgres=# \l
   ```
    
2. Then u will need to create the database tables via Maven liquibase plugin:
    ```bash
    liquibase:update
   ```
            
3. Run App:
    ```bash
    meetrom->src->main->java->com.gustav474.meetroom->MeetroomApplication
   ```
   
4. Enjoy
    




