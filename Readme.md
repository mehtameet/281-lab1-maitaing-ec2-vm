Amazon EC2 instance management for command line

Steps:-
1. Copy two files from src folder.
2. Add your "secret key" and "access key" into "AwsCredentials.properties" file and put you file with other two files.
3. Run mainClass.java file.
4. It will ask you to enter following things:
	Enter  1. Create Instance  2. Delete Instance  3. Stop Instance   4.Start Instance   5. Restart Instance   6. List Characteristics   7.Create Ami from Instance
	
Note:-
1. First create the instance, so that it can get instanceID, else you have to pass instanceID manually to all other methods.
2. Pass instanceID for creating AMI manually.

Extra points done