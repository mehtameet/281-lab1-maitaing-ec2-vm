import java.io.IOException;
import java.util.List;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.PropertiesCredentials;
import com.amazonaws.services.ec2.AmazonEC2Client;
import com.amazonaws.services.ec2.model.Instance;
import com.amazonaws.services.ec2.model.RunInstancesRequest;
import com.amazonaws.services.ec2.model.RunInstancesResult;

public class instance_methods {

	AmazonEC2Client ec2;
	public instance_methods() throws IOException{
		AWSCredentials credentials = new PropertiesCredentials(mainClass.class.getResourceAsStream("AwsCredentials.properties"));
		ec2 = new AmazonEC2Client(credentials);
		ec2.setEndpoint("ec2.us-west-2.amazonaws.com");
	}

	public void createInstance() throws InterruptedException{
		System.out.println("#5 Create an Instance");
		String imageId = "ami-68ad3358";//  change to SUSE 64 bit Amazon AMI
		int minInstanceCount = 1; // create 1 instance
		int maxInstanceCount = 1;
		RunInstancesRequest rir = new RunInstancesRequest(imageId, minInstanceCount, maxInstanceCount);
		rir.setInstanceType("t1.micro");
		rir.setKeyName("meet-key");// give the instance the key we just created
		rir.withSecurityGroups("default");// set the instance in the group we just created

		RunInstancesResult result = ec2.runInstances(rir);

		/***********to make sure the instance's state is "running instead of "pending",**********/
		/***********we wait for a while                                                **********/
		System.out.println("waiting");
		Thread.currentThread().sleep(50000);
		System.out.println("OK");


		List<Instance> resultInstance = result.getReservation().getInstances();


		String createdInstanceId = null;
		for (Instance ins : resultInstance){

			createdInstanceId = ins.getInstanceId();
			System.out.println("New instance has been created: "+ins.getInstanceId());//print the instance ID

		}

	}
}
