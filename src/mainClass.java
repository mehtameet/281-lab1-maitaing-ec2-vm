import java.io.IOException;
import java.util.Scanner;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.PropertiesCredentials;
import com.amazonaws.services.ec2.AmazonEC2Client;
import com.amazonaws.services.ec2.model.DescribeInstancesRequest;
import com.amazonaws.services.ec2.model.DescribeInstancesResult;


public class mainClass {

	/**
	 * @param args
	 * @throws IOException 
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws IOException, InterruptedException {

		instance_methods methods = new instance_methods();
		
		
		int option;
		while(true)
		{
			Scanner input = new Scanner(System.in);
			System.out.println("Enter  1. Create Instance  2. Delete Instance  3. Stop Instance   4.Start Instance   5. Restart Instance   6. List Characteristics   7.Create Ami from Instance");
			option = input.nextInt();
			
			switch (option) {
			case 1:
				System.out.println("Create instance query fired");
				methods.createInstance();
				System.out.println("Instance Created");
				break;

			case 2:
				System.out.println("Delete instance query fired");
				methods.deleteInstance();
				System.out.println("Instance is deleted successfully");
				break;
				
			case 3:
				System.out.println("stop instance query fired");
				methods.stopInstance();
				System.out.println("Instance stopped successfully");
				break;
				
			case 4:
				System.out.println("start instance query fired");
				methods.startInstance();
				System.out.println("Instance started successfully");
				break;
			
			case 5:
				System.out.println("restart instance query fired");
				methods.stopInstance();
				//start will has inbuilt try-catch block which checks if the instant is stopped perfectly or not before start
				methods.startInstance();
				System.out.println("Instance restarted successfully");
				break;
				
			case 6:
				System.out.println("Describing instances");
				methods.describeInstance();
				break;
				
			default:
				break;
			}
		}
		
	}

}
