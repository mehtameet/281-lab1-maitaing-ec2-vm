import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.PropertiesCredentials;
import com.amazonaws.services.ec2.AmazonEC2Client;
import com.amazonaws.services.ec2.model.DescribeImagesResult;
import com.amazonaws.services.ec2.model.DescribeInstancesResult;
import com.amazonaws.services.ec2.model.Image;
import com.amazonaws.services.ec2.model.Instance;
import com.amazonaws.services.ec2.model.InstanceState;
import com.amazonaws.services.ec2.model.Reservation;
import com.amazonaws.services.ec2.model.RunInstancesRequest;
import com.amazonaws.services.ec2.model.RunInstancesResult;
import com.amazonaws.services.ec2.model.StartInstancesRequest;
import com.amazonaws.services.ec2.model.StopInstancesRequest;
import com.amazonaws.services.ec2.model.TerminateInstancesRequest;

public class instance_methods {

	AmazonEC2Client ec2;
	List<String> instanceIds=new LinkedList<String>();
	public instance_methods() throws IOException{
		
		//setting up credentials
		AWSCredentials credentials = new PropertiesCredentials(mainClass.class.getResourceAsStream("AwsCredentials.properties"));
		ec2 = new AmazonEC2Client(credentials);
		
		//setting endpoints for ec2Client
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
	
	public void deleteInstance()
	{

		instanceIds.add("i-a806109c");
		System.out.println("#8 Terminate the Instance");
		System.out.println("instance ids are : "+instanceIds);
        TerminateInstancesRequest tir = new TerminateInstancesRequest(instanceIds);
        ec2.terminateInstances(tir);
	}
	
	public void startInstance() throws InterruptedException
	{
		boolean result=false;
		instanceIds.add("i-a806109c");
		while(result==false)
		{
			try
			{
				StartInstancesRequest startIR = new StartInstancesRequest(instanceIds);
		        ec2.startInstances(startIR);
		        result=true;
			}
			catch(com.amazonaws.AmazonServiceException e)
			{
				System.out.println("amazon instance is yet not stopped, again trying to start in 15 seconds");
				Thread.currentThread().sleep(15000);
			}
		}
		
		
	}
	
	public void stopInstance()
	{
		instanceIds.add("i-a806109c");
		StopInstancesRequest stopIR = new StopInstancesRequest(instanceIds);
        ec2.stopInstances(stopIR);
	}
	
	public void describeInstance()
	{
		 DescribeImagesResult dir = ec2.describeImages();
         List<Image> images = dir.getImages();
         System.out.println("You have " + images.size() + " Amazon images");
         
         DescribeInstancesResult describeInstancesRequest = ec2.describeInstances();
         List<Reservation> reservations = describeInstancesRequest.getReservations();
         Set<Instance> instances = new HashSet<Instance>();
         // add all instances to a Set.
         for (Reservation reservation : reservations) {
          instances.addAll(reservation.getInstances());
         }
        
         System.out.println("You have " + instances.size() + " Amazon EC2 instance(s).");
         for (Instance ins : instances){
        
          // instance id
          String instanceId = ins.getInstanceId();
        
          // instance state
          InstanceState is = ins.getState();
          System.out.println("launch time is "+ins.getLaunchTime());
          System.out.println(ins.getKeyName()+ " "+ ins.getIamInstanceProfile()+ ins.getPlatform()+ ins.getRamdiskId());
          System.out.println(instanceId+" "+is.getName());
         }
	}
}
